package urbantech.business;

import static org.junit.Assert.assertEquals;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import java.io.File;
import java.io.IOException;
import java.util.List;

import urbantech.objects.Item;
import urbantech.persistence.ItemPersistence;
import urbantech.persistence.hsqldb.ItemPersistenceHSQLDB;
import urbantech.utils.TestUtils;

/*
    acceptance test for AccessItem and database
 */
public class AccessItemsIT {
    private AccessItems accessItems;
    private File tempDB;

    private ItemPersistence itemPersistence;

    @Before
    public void setUp() throws IOException {
        this.tempDB = TestUtils.copyDB();
        this.accessItems = AccessItems.getInstance();
        itemPersistence = new ItemPersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        accessItems.init(itemPersistence);
    }


    @Test
    public void testGetItem() {
        try {
            List<Item> items = accessItems.getItems();
            assertEquals(16, items.size());
        } catch(Exception e){

        }
    }

    @Test
    public void testGetRecommendedItems() {
        try {
            String expect1 = "P11";
            String expect2 = "XPS";
            assertEquals(expect1, accessItems.getRecommendedItems(5).get(0).getModelName());
            assertEquals(expect2, accessItems.getRecommendedItems(5).get(1).getModelName());
        } catch(Exception e){

        }
    }

    @After
    public void tearDown() {
        this.tempDB.delete();
    }

}
