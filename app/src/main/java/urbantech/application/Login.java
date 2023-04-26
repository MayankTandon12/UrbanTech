package urbantech.application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import urbantech.persistence.hsqldb.PersistenceException;
import urbantech.persistence.hsqldb.loginAccountHSQLDB;

public class Login {
    private static final String testPath = "src/main/assets/dbTest/SC";
    private static final String path = Main.getDBPathName();
    private static loginAccountHSQLDB account = null;

    public static int getLoginID() {
        int userID = 0;
        String username;
        String password;
        try (final Connection c = connection()) {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM ACCOUNT WHERE LOGIN = TRUE");

            if (rs.next()) {
                userID = rs.getInt("USER_ID");
                username = rs.getString("USERNAME");
                password = rs.getString("PASSWORD");
                account = new loginAccountHSQLDB(path, username, password); // change to main path
            } else
                System.out.println("No one is logged in");
        } catch (final SQLException e) {
            throw new PersistenceException(e);

        }
        return userID;
    }

    //change to path later
    private static Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + path + ";shutdown=true", "SA", "");
    }

    public static loginAccountHSQLDB getAccount() {
        return account;
    }

    public static void setAccount(loginAccountHSQLDB account) {
        Login.account = account;
    }
}
