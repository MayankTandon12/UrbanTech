package urbantech.persistence.hsqldb;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import urbantech.application.Login;
import urbantech.business.CardPayment;
import urbantech.business.Receipt;
import urbantech.business.ValidateDeliveryAddress;
import urbantech.objects.Item;

public class loginAccountHSQLDB {
    private final String userName;
    private final String password;
    private final String path;
    private int userID;
    private boolean loginSuccess = false;


    public loginAccountHSQLDB(String path, String username, String password) {
        this.path = path;
        this.userName = username.toUpperCase();
        this.password = password;
        try (final Connection c = connection()) {
            final Statement st = c.createStatement();
            // search for the username in the database and get that password
            ResultSet rs = st.executeQuery("SELECT * FROM ACCOUNT WHERE USERNAME = '" + this.userName + "'");
            if (rs.next()) {
                String accPassword = rs.getString("PASSWORD");
                if (accPassword.equals(password)) {
                    userID = rs.getInt("USER_ID");
                    loginSuccessfull();
                    loginSuccess = true;
                } else
                    System.out.println("Invalid password");
            } else
                System.out.println("Invalid Username");

        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }


    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + path + ";shutdown=true", "SA", "");
    }

    private void loginSuccessfull() {
        try (final Connection c = connection()) {
            final Statement st = c.createStatement();
            st.executeQuery("UPDATE ACCOUNT SET LOGIN = TRUE WHERE USER_ID = " + userID);
            System.out.println("login successful");

        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    // Getters
    public int getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public boolean isLoginSuccess() {
        return loginSuccess;
    }

    // Set new Password maybe work on this later
//    public void setPassword(String password) {
//        if(validatePassword(password))
//            this.password = password;
//    }


    public void addAddressToAccount(ValidateDeliveryAddress address) {

        try (final Connection c = connection()) {
            final Statement st = c.createStatement();
            st.executeQuery("INSERT INTO ADDRESS(USER_ID,LINE,CITY,PROVINCE,COUNTRY,POSTAL_CODE) VALUES(" + userID + ",'" + address.getAddressLine() + "','" + address.getCity() + "','" + address.getProvince() + "','" + address.getCountry() + "','" + address.getPostalCode() + "')");
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    public void addCardToAccount(CardPayment card) {

        try (final Connection c = connection()) {
            final Statement st = c.createStatement();
            st.executeQuery("INSERT INTO CARD(USER_ID,CARD_NUM,CVV,EXPIRE_DATE) VALUES(" + userID + ",'" + card.getCardNum() + "','" + card.getCvv() + "','" + card.getExpireDate() + "')");
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }


    public ValidateDeliveryAddress getAddress() {
        ValidateDeliveryAddress result = null;

        try (final Connection c = connection()) {
            final Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM ADDRESS WHERE USER_ID = '" + this.userID + "'");

            if (rs.next()) {
                String addressLine = rs.getString("LINE");
                String city = rs.getString("CITY");
                String province = rs.getString("PROVINCE");
                String country = rs.getString("COUNTRY");
                String postalCode = rs.getString("POSTAL_CODE");

                result = new ValidateDeliveryAddress(addressLine, city, province, country, postalCode);
            }

        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

        return result;
    }

    public ArrayList<CardPayment> getCards() {
        ArrayList<CardPayment> result = new ArrayList<>();

        try (final Connection c = connection()) {
            final Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM CARD WHERE USER_ID = '" + this.userID + "'");

            while (rs.next()) {
                String cardNum = rs.getString("CARD_NUM");
                String cvv = rs.getString("CVV");
                String expry = rs.getString("EXPIRE_DATE");

                result.add(new CardPayment(cardNum, cvv, expry));
            }


        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

        return result;
    }

    public void logout() {
        try (final Connection c = connection()) {
            final Statement st = c.createStatement();
            st.executeQuery("UPDATE ACCOUNT SET LOGIN = FALSE WHERE USER_ID = " + userID);
            Login.setAccount(null);//update in Login back to null
            System.out.println("logout successful");

        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    public ArrayList<Receipt> getReceipts() {
        ArrayList<Receipt> result = new ArrayList<>();
        try (final Connection c = connection()) {
            final Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM RECEIPT WHERE USER_ID = " + userID);
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
        Array arr = rs.getArray("ITEM_ID");
        int orderNum = rs.getInt("ORDER_NUM");

        ArrayList<Item> items = new ArrayList<>();
        Object[] obj = (Object[]) arr.getArray();

        int[] intArray = new int[obj.length];

        for (int i = 0; i < obj.length; i++) {
            intArray[i] = Integer.parseInt(obj[i].toString());
        }

        ItemPersistenceHSQLDB idk2 = new ItemPersistenceHSQLDB(path);
        for (int i = 0; i < obj.length; i++) {
            items.add(idk2.getItemsSequential().get(intArray[i]-1)); // forgot arraylist starts from zero
        }
        result = new Receipt(userName, total, amount, items, orderNum);
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
            st.executeQuery("INSERT INTO RECEIPT VALUES(" + Login.getLoginID() + ", '"
                    + receipt.getName() + "' , " + receipt.getTotal() + ", " + receipt.getAmountItems() +
                    ", " + items + ", " + receipt.getOrderNumber() + ")");

        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

}
