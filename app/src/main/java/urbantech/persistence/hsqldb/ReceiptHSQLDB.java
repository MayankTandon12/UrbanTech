package urbantech.persistence.hsqldb;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import urbantech.application.Login;
import urbantech.business.Receipt;
import urbantech.objects.Item;

public class ReceiptHSQLDB {

    private final String dbPath;

    public ReceiptHSQLDB(final String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    public ArrayList<Receipt> getReceipts() {
        ArrayList<Receipt> result = new ArrayList<>();
        try (final Connection c = connection()) {
            final Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM RECEIPT WHERE USER_ID = " + Login.getLoginID());
            if (rs.next()) {
                result.add(fromResultSet(rs));
                while (rs.next()) {
                    result.add(fromResultSet(rs));
                }
            }
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
        return result;
    }

    private Receipt fromResultSet(ResultSet rs) throws SQLException {
        Receipt result;
        int userID = rs.getInt("USER_ID");
        String userName = rs.getString("USER_NAME");
        double total = rs.getDouble("TOTAL");
        int amount = rs.getInt("ITEM_QUANTITY");
        Array items = rs.getArray("ITEM_ID");
        int orderNum = rs.getInt("ORDER_NUM");

        ArrayList<Item> idk = new ArrayList<>();
        Object[] test = (Object[]) items.getArray();

        int[] intArray = new int[test.length];

        for (int i = 0; i < test.length; i++) {
            intArray[i] = Integer.parseInt(test[i].toString());
        }

        ItemPersistenceHSQLDB idk2 = new ItemPersistenceHSQLDB(dbPath);
        for (int i = 0; i < test.length; i++) {
            idk.add(idk2.getItemsSequential().get(intArray[i]));
        }
        result = new Receipt(userName, total, amount, idk, orderNum);
        return result;
    }

    public void addReceipt(Receipt receipt) {
        StringBuilder itemsBuilder = new StringBuilder("ARRAY[");
        for (int i = 0; i < receipt.getItems().size(); i++) {
            if (i == receipt.getItems().size() - 1)
                itemsBuilder.append(((Item) receipt.getItems().get(i)).getItemID());
            else
                itemsBuilder.append(((Item) receipt.getItems().get(i)).getItemID()).append(",");
        }
        String items = itemsBuilder.toString();
        items += "]";

        try (final Connection c = connection()) {
            final Statement st = c.createStatement();
            st.executeQuery("INSERT INTO RECEIPT VALUES(" + Login.getLoginID() + ", '" + receipt.getName() + "' , " + receipt.getTotal() + ", " + receipt.getAmountItems() + ", " + items + ", " + receipt.getOrderNumber() + ")");

        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

}
