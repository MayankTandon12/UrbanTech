package urbantech.application;

import urbantech.persistence.CartItemPersistence;
import urbantech.persistence.ItemPersistence;
import urbantech.persistence.hsqldb.CartItemPersistenceHSQLDB;
import urbantech.persistence.hsqldb.ItemPersistenceHSQLDB;

public class Services {
    private static ItemPersistence itemPersistence = null;

    private static CartItemPersistence cartItemPersistence = null;

    public static synchronized ItemPersistence getItemPersistence() {
        if (itemPersistence == null) {
            //itemPersistence = new ItemPersistenceStub();
            itemPersistence = new ItemPersistenceHSQLDB(Main.getDBPathName());
        }
        return itemPersistence;
    }

    public static synchronized CartItemPersistence getCartItemPersistence() {
        if (cartItemPersistence == null) {
            //itemPersistence = new ItemPersistenceStub();
            cartItemPersistence = new CartItemPersistenceHSQLDB(Main.getDBPathName());
        }
        return cartItemPersistence;
    }
}

