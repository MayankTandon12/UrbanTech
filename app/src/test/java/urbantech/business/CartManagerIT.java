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

public class CartManagerIT {
    private AccessItems accessItems;
    private File tempDB;

    private ItemPersistence itemPersistence;

    private CartManager cartManager;

    private AccessCartItems accessCartItems;
    private CartItemPersistence cartItemPersistence;

    private Item item;
    private  CartItem cartItem;

    @Before
    public void setUp() throws IOException {
        tempDB = TestUtils.copyDB();
        //accessItems = AccessItems.getInstance();

        String dbPath = this.tempDB.getAbsolutePath().replace(".script", "");

        itemPersistence = new ItemPersistenceHSQLDB(dbPath);
        cartItemPersistence = new CartItemPersistenceHSQLDB(dbPath);

        //accessItems = AccessItems.getInstance();
        //accessItems.init(itemPersistence);
        accessItems = new AccessItems(itemPersistence);

        //accessCartItems = AccessCartItems.getInstance();
        //accessCartItems.init(cartItemPersistence);
        accessCartItems = new AccessCartItems(cartItemPersistence);

        //cartManager = CartManager.getInstance();
        //cartManager.init(accessCartItems, accessItems);

        cartManager = new CartManager(accessCartItems, accessItems);

        item = accessItems.getRecommendedItems(1).get(0);
        cartItem = new CartItem(item,1);

    }


    @Test
    public void test() {
        try {
            assertEquals(0, cartManager.getCartList().size());
            cartManager.addItemToCart(cartItem);
            assertEquals(1099.99, cartManager.getTotalPrice(), 0);
            assertEquals(1, cartManager.getCartList().size());

            cartManager.incrementItemQuantity(item.getItemID());
            assertEquals(2199.98, cartManager.getTotalPrice(), 0);
            System.out.println("Increment Item Quantity success");

            cartManager.decrementItemQuantity(item.getItemID());
            assertEquals(1099.99, cartManager.getTotalPrice(), 0);
            System.out.println("Decrement Item Quantity success");

            cartManager.removeItemFromCart(item.getItemID());
            assertEquals(0, cartManager.getCartList().size());
        } catch (Exception e){

        }
    }
    @After
    public void tearDown() {
        this.tempDB.delete();
    }
}
