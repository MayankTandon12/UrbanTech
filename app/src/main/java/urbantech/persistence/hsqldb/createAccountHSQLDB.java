package urbantech.persistence.hsqldb;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import urbantech.business.CardPayment;
import urbantech.business.ValidateDeliveryAddress;

public class createAccountHSQLDB {
    private final ArrayList<String> userNames = new ArrayList<>();
    private final ArrayList<Integer> userIDs = new ArrayList<>();
    private final String path;
    private int userID;
    private String userName;
    private String password;
    private boolean createSuccess = false;

    public createAccountHSQLDB(String path, String userName, String password) {

        this.path = path;
        fillData();
        // if Values are valid create new User
        if (validateUserName(userName, userNames) && validatePassword(password)) {
            this.userID = setuserID(userIDs);
            this.userName = userName;
            this.password = password;
            createSuccess = true;

            if (userID != 0)
                addAccount();
        }

    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + path + ";shutdown=true", "SA", "");
    }

    private void fillData() {
        userNames.clear();
        userIDs.clear();
        try (final Connection c = connection()) {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM ACCOUNT");

            while (rs.next()) {
                userNames.add(rs.getString("USERNAME"));
                userIDs.add(rs.getInt("USER_ID"));
            }
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }


    private void addAccount() {

        try (final Connection c = connection()) {
            final Statement st = c.createStatement();
            st.executeQuery("INSERT INTO ACCOUNT VALUES (" + userID + ",'" + userName.toUpperCase() + "','" + password + "',FALSE)");
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }


    private int setuserID(ArrayList<Integer> userIDs) {
        int result = 1;
        // if there are existing users create a new user ID or set it to 1
        if (!userIDs.isEmpty())
            result = userIDs.get(userIDs.size() - 1) + 1; // get last item and add 1 to it

        return result;
    }

    private boolean validateUserName(String userName, ArrayList<String> users) {
        boolean result = false;
        if (userName.contains("--") || userName.contains("/*") || userName.toUpperCase().contains("SELECT"))
            System.out.println("Invalid Username");
        else if (userNames.contains(userName.toUpperCase()))
            System.out.println("Username Already Exists");
        else
            result = true;

        return result;
    }

    private boolean validatePassword(String password) {
        boolean result = false;
        if (password.contains("--") || password.contains("/*") || password.toUpperCase().contains("SELECT"))
            System.out.println("Invalid Password");
        else
            result = true;

        return result;
    }

    public boolean isCreateSuccess() {
        return createSuccess;
    }

}
