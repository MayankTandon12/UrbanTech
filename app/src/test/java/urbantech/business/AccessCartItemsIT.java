package urbantech.business;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import urbantech.objects.CartItem;
import urbantech.objects.Item;
import urbantech.persistence.CartItemPersistence;
import urbantech.persistence.ItemPersistence;
import urbantech.persistence.hsqldb.CartItemPersistenceHSQLDB;
import urbantech.persistence.hsqldb.ItemPersistenceHSQLDB;
import urbantech.utils.TestUtils;

public class AccessCartItemsIT {
    private AccessItems accessItems;
    private AccessCartItems accessCartItems;
    private File tempDB;

    private ItemPersistence itemPersistence;

    private CartItemPersistence cartItemPersistence;

    @Before
    public void setUp() throws IOException {
        this.tempDB = TestUtils.copyDB();

        String dpPath = this.tempDB.getAbsolutePath().replace(".script", "");
        itemPersistence = new ItemPersistenceHSQLDB(dpPath);
        cartItemPersistence = new CartItemPersistenceHSQLDB(dpPath);

        accessItems = AccessItems.getInstance();
        accessItems.init(itemPersistence);

        accessCartItems = AccessCartItems.getInstance();
        accessCartItems.init(cartItemPersistence);

    }

    @Test
    public void test() {
        try {
            Item item = accessItems.getRecommendedItems(5).get(0);
            CartItem cartItem = new CartItem(item, 1);
            assertEquals(0, accessCartItems.getCartItems().size());
            accessCartItems.addCartItem(cartItem);
            assertEquals(1, accessCartItems.getCartItems().size());
        } catch(Exception e){

        }
    }

    @After
    public void tearDown() {
        this.tempDB.delete();
    }

}
