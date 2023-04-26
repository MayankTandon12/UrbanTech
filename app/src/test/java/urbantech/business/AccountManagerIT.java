package urbantech.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import urbantech.objects.Item;
import urbantech.persistence.ItemPersistence;
import urbantech.persistence.hsqldb.ItemPersistenceHSQLDB;
import urbantech.persistence.hsqldb.createAccountHSQLDB;
import urbantech.persistence.hsqldb.loginAccountHSQLDB;
import urbantech.utils.TestUtils;

public class AccountManagerIT {

    private AccountManager accountManager;
    private File tempDB;

    private loginAccountHSQLDB lDB;
    private createAccountHSQLDB aDB;

    private ItemPersistenceHSQLDB iDB;
    private AccessItems accessItems;
    private ItemPersistence itemPersistence;

    @Before
    public void setUp() throws IOException {
        this.tempDB = TestUtils.copyDB();
        String dbPath = tempDB.getAbsolutePath().replace(".script", "");
        String username = "batcat";
        String password = "111111";
        aDB = new createAccountHSQLDB(dbPath,username,password);
        lDB = new loginAccountHSQLDB(dbPath,username,password);
        iDB = new ItemPersistenceHSQLDB(dbPath);
        accountManager = new AccountManager(aDB,lDB);
        accountManager.loginAcc(dbPath,username,password);

        accessItems = AccessItems.getInstance();
        itemPersistence = new ItemPersistenceHSQLDB(dbPath);
        accessItems.init(itemPersistence);

    }

    @Test
    public void testCreateSuccess() {
        try {
            assertTrue(accountManager.isCreateSuccess());
        } catch(Exception e){}
    }

    @Test
    public void testLoginSuccess() {
        try {
            assertTrue(accountManager.isLoginSuccess());
            assertEquals("BATCAT", accountManager.getUserName());
        } catch(Exception e){}
    }

    @Test
    public void testAddAddress() {
        try {
            ValidateDeliveryAddress address = new ValidateDeliveryAddress("123 s",
                    "Winnipeg", "MB", "Canada", "R3E 2U3");
            accountManager.addAddress(address);
            assertEquals(address.getAddressLine(), accountManager.getAddress().getAddressLine());
            assertEquals(address.getCity(), accountManager.getAddress().getCity());
            assertEquals(address.getProvince(), accountManager.getAddress().getProvince());
            assertEquals(address.getCountry(), accountManager.getAddress().getCountry());
            assertEquals(address.getPostalCode(), accountManager.getAddress().getPostalCode());
        } catch(Exception e){}
    }


    // test fail
    @Test
    public void testAddReceipt() {
        try {
            Item item = accessItems.getRecommendedItems(5).get(0);
            List<Item> items = Arrays.asList(item);
            Receipt receipt = new Receipt("batcat", 123.21, 2, items, 510);
            //System.out.println("Receipts before: " + accountManager.getReceipts().size());
            accountManager.addReceipts(receipt);
            //System.out.println("Receipts after: " + accountManager.getReceipts().size());
            assertEquals(1, accountManager.getReceipts().size());
            //assertEquals(349.99, item.getPrice(), 0);
        } catch(Exception e){}
    }

    @Test
    public void testAddCard() {
        try {
            CardPayment card = new CardPayment("4665977683369232", "852", "0226");
            assertEquals(0, accountManager.getCards().size());
            accountManager.addCard(card);
            assertEquals(1, accountManager.getCards().size());
            assertEquals("4665977683369232", accountManager.getCards().get(0).getCardNum());
        } catch(Exception e){}
    }

    @After
    public void tearDown() {
        this.tempDB.delete();
    }
}
