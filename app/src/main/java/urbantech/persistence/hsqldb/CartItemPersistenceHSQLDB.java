package urbantech.persistence.hsqldb;

import com.example.urbantech.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import urbantech.application.Login;
import urbantech.objects.CartItem;
import urbantech.objects.Item;
import urbantech.persistence.CartItemPersistence;

public class CartItemPersistenceHSQLDB implements CartItemPersistence {
    private final String dbPath;

    public CartItemPersistenceHSQLDB(final String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    public void addCartItem(CartItem item) {
        try (final Connection c = connection()) {
            final Statement st = c.createStatement();
            st.executeQuery("INSERT INTO CART (USER_ID, ITEM_ID, ITEM_QUANTITY) VALUES (" + Login.getLoginID() + "," + item.getItem().getItemID() + "," + item.getQuantity() + ")");
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    public void removeCartItem(CartItem item) {
        try (final Connection c = connection()) {
            final Statement st = c.createStatement();
            st.executeQuery("DELETE FROM CART WHERE USER_ID = " + Login.getLoginID() + " AND CART.ITEM_ID = " + item.getItem().getItemID());
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    public List<CartItem> getCartItemsSequential() {
        List<CartItem> items = new ArrayList<>();
        try (final Connection c = connection()) {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM CART LEFT JOIN PRODUCTS ON PRODUCTS.ITEM_ID = CART.ITEM_ID AND USER_ID = " + Login.getLoginID());
            while (rs.next()) {
                CartItem item = fromResultSet(rs);
                items.add(item);
            }
            rs.close();
            st.close();

        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

        return items;
    }

    public void updateCartItemQuantity(CartItem item) {
        try (final Connection c = connection()) {
            final Statement st = c.createStatement();
            st.executeQuery("UPDATE CART SET CART.ITEM_QUANTITY = " + item.getQuantity() + "WHERE USER_ID = " + Login.getLoginID() + "AND CART.ITEM_ID = " + item.getItem().getItemID());
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    private CartItem fromResultSet(final ResultSet rs) throws SQLException {
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
        final int cartQuantity = rs.getInt("item_quantity");

        Item item = new Item(itemID, modelName, brand, category, price, storage, ram, processor, graphicCard, size, touch, overview, image, discountPercent, discountQuantity, inCartQuantity, inStock);
        return (new CartItem(item, cartQuantity));
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
