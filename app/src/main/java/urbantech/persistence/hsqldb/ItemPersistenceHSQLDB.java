package urbantech.persistence.hsqldb;

import androidx.annotation.NonNull;

import com.example.urbantech.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import urbantech.objects.Category;
import urbantech.objects.Item;
import urbantech.persistence.ItemPersistence;

public class ItemPersistenceHSQLDB implements ItemPersistence {
    private final String dbPath;
    private final List<Item> items = new ArrayList<>();
    private final List<String> Brand = new ArrayList<>();
    private final List<String> Processor = new ArrayList<>();
    private final List<String> GraphicCard = new ArrayList<>();
    private String searchName = "";
    private String Category = "";


    public ItemPersistenceHSQLDB(final String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    private Item fromResultSet(final ResultSet rs) throws SQLException {
        final int itemID = rs.getInt("item_ID");
        final String modelName = rs.getString("name");
        final String brand = rs.getString("brand");
        final String category = rs.getString("category");
        final double price = rs.getDouble("price");
        final String storage = rs.getString("storage");
        final String ram = rs.getString("ram");
        final String processor = rs.getString("processor");
        final String graphicCard = rs.getString("graphic_card");
        final String size = rs.getString("size");
        final String touch = rs.getString("touch");
        final String overview = rs.getString("overview");
        final double discountPercent = rs.getDouble("discount_percent");
        final int image = getResource(itemID);
        final int discountQuantity = rs.getInt("discount_quantity");
        final int inCartQuantity = rs.getInt("in_cart_quantity");
        final int inStock = rs.getInt("in_stock");

        return new Item(itemID, modelName, brand, category, price, storage, ram, processor, graphicCard, size, touch, overview, image, discountPercent, discountQuantity, inCartQuantity, inStock);

    }

    public List<Category> getCategories() {
        ArrayList<Category> categories = new ArrayList<>();
        try (final Connection c = connection()) {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT DISTINCT CATEGORY FROM PRODUCTS");
            while (rs.next()) {
                String categoryName = rs.getString("category");
                List<Item> items = getItemsByCategory(categoryName);
                int imageResourceId = getResource(categoryName);
                Category category = new Category(categoryName, imageResourceId, items);
                categories.add(category);
            }
            rs.close();
            st.close();

        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
        return categories;
    }

    @NonNull
    private List<Item> getItemsByCategory(String categoryName) {
        List<Item> items = new ArrayList<>();
        try (final Connection c = connection()) {
            final PreparedStatement ps = c.prepareStatement("SELECT * FROM PRODUCTS WHERE CATEGORY = ?");
            ps.setString(1, categoryName);
            final ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                final Item item = fromResultSet(rs);
                items.add(item);
            }
            rs.close();
            ps.close();

        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
        return items;
    }


    public List<Item> getItemsSequential() {
        items.clear();
        try (final Connection c = connection()) {
            final Statement st = c.createStatement();
            final ResultSet rs;
            if (Category.length() > 0)
                rs = st.executeQuery("SELECT * FROM PRODUCTS WHERE (UPPER(NAME) LIKE '%" + searchName.toUpperCase() + "%' OR UPPER(BRAND) LIKE '%" + searchName.toUpperCase() + "%') AND UPPER(CATEGORY) LIKE '%" + Category.toUpperCase() + "%'" + filterItem(Brand, Processor, GraphicCard));
            else
                rs = st.executeQuery("SELECT * FROM PRODUCTS WHERE (UPPER(NAME) LIKE '%" + searchName.toUpperCase() + "%' OR UPPER(BRAND) LIKE '%" + searchName.toUpperCase() + "%')" + filterItem(Brand, Processor, GraphicCard));
            while (rs.next()) {
                final Item item = fromResultSet(rs);
                items.add(item);
            }
            rs.close();
            st.close();

        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

        return items;
    }

    public List<Item> getRecommendedItems(int id){
        List<Item> recommended = new ArrayList<>();
        try (final Connection c = connection()) {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM RECOMMENDED LEFT JOIN PRODUCTS ON RECOMMENDED.ITEM_ID_2 = PRODUCTS.ITEM_ID WHERE RECOMMENDED.ITEM_ID_1 = " + id +
                    "UNION SELECT * FROM RECOMMENDED LEFT JOIN PRODUCTS ON RECOMMENDED.ITEM_ID_1 = PRODUCTS.ITEM_ID WHERE RECOMMENDED.ITEM_ID_2 = " + id );
            while (rs.next()) {
                final Item item = fromResultSet(rs);
                recommended.add(item);
            }
        }catch (final SQLException e) {
            throw new PersistenceException(e);
        }
        return recommended;
    }

    public void updateStock(Item item) {
        try (final Connection c = connection()) {
            final Statement st = c.createStatement();
            st.executeQuery("UPDATE PRODUCTS SET PRODUCTS.IN_STOCK = " + item.getStock() + "WHERE PRODUCTS.ITEM_ID = " + item.getItemID());
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    private String filterItem(List<String> brand, List<String> processor, List<String> graphicCard) {
        String result;
        String brands = "";
        String processors = "";
        String graphicCards = "";
        if (brand.size() > 0)
            brands = " AND " + multifilter("BRAND", brand);
        if (processor.size() > 0)
            processors = " AND " + multifilter("PROCESSOR", processor);
        if (graphicCard.size() > 0)
            graphicCards = " AND " + multifilter("GRAPHIC_CARD", graphicCard);

        result = brands + processors + graphicCards;

        return result;
    }

    private String multifilter(String type, List<String> filter) {
        String result = "";
        if (filter.size() > 1) {
            StringBuilder resultBuilder = new StringBuilder("(");
            for (int i = 0; i < filter.size(); i++) {
                resultBuilder.append(" UPPER(").append(type.toUpperCase()).append(") LIKE'%").append(filter.get(i).toUpperCase()).append("%'");
                if (i < filter.size() - 1) { // if there are more filters
                    resultBuilder.append(" OR ");
                }
            }
            result = resultBuilder.toString();
            result += ")";
        } else if (filter.size() == 1) {
            result = " UPPER(" + type.toUpperCase() + ") LIKE '%" + filter.get(0).toUpperCase() + "%'";
        }
        return result;
    }

    public void setSearchName(String search) {
        searchName = search;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public void addBrands(String brand) {

        if (Brand.contains(brand))
            Brand.remove(brand);
        else
            Brand.add(brand);
    }

    public void addProcessor(String processor) {
        if (Processor.contains(processor))
            Processor.remove(processor);
        else
            Processor.add(processor);
    }

    public void addGraphicCard(String graphicCard) {
        if (GraphicCard.contains(graphicCard))
            GraphicCard.remove(GraphicCard);
        else
            GraphicCard.add(graphicCard);
    }

    public void resetFilter() {
        Brand.clear();
        Processor.clear();
        GraphicCard.clear();
    }

    public int SizeOfResult() {
        return items.size();
    }

    public int getResource(@NonNull String categoryName) {
        int resID;
        switch (categoryName) {
            case "DESKTOP":
                resID = R.drawable.rogstrix_g15;
                break;
            case "LAPTOP":
                resID = R.drawable.macbookpro14;
                break;
            case "PHONE":
                resID = R.drawable.iphone_14_pro;
                break;
            case "TABLET":
                resID = R.drawable.s8tablet;
                break;
            default:
                resID = R.drawable.categories;
                break;
        }
        return resID;
    }

    public int getResource(int id) { //temporary solution until we can store blob in database
        int resID = 0;
        switch (id) {
            case 1:
                resID = R.drawable.iphone_14_pro;
                break;
            case 2:
                resID = R.drawable.s22;
                break;
            case 3:
                resID = R.drawable.pixel7;
                break;
            case 4:
                resID = R.drawable.ipadpro;
                break;
            case 5:
                resID = R.drawable.s8tablet;
                break;
            case 6:
                resID = R.drawable.p11;
                break;
            case 7:
                resID = R.drawable.dell;
                break;
            case 8:
                resID = R.drawable.asusvivobook;
                break;
            case 9:
                resID = R.drawable.macbookpro14;
                break;
            case 10:
                resID = R.drawable.msi_gs66;
                break;
            case 11:
                resID = R.drawable.zephyrusg15;
                break;
            case 12:
                resID = R.drawable.legion5i;
                break;
            case 13:
                resID = R.drawable.rogstrix_g15;
                break;
            case 14:
                resID = R.drawable.aurorar13;
                break;
            case 15:
                resID = R.drawable.msiinfinite;
                break;
            case 16:
                resID = R.drawable.nitropc;
                break;
        }
        return resID;
    }
}


























































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































