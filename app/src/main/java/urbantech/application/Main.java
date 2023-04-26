package urbantech.application;

public class Main {
    private static String dbName = "SC";

    public static void main(String[] args) {
        System.out.println("All done");
    }

    public static String getDBPathName() {
        return dbName;
    }

    public static void setDBPathName(final String name) {
        try {
            Class.forName("org.hsqldb.jdbcDriver").newInstance();
        } catch (InstantiationException | ClassNotFoundException | IllegalAccessException e) {
            e.printStackTrace();
        }
        dbName = name;
    }
}
