package urbantech.business;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import urbantech.persistence.ItemPersistence;
import urbantech.persistence.hsqldb.ItemPersistenceHSQLDB;
import urbantech.utils.TestUtils;

public class SearchItemsIT {

    private ItemPersistence itemPersistence;
    private SearchItems searchItems;
    private File tempDB;

    @Before
    public void setUp() throws IOException {
        tempDB = TestUtils.copyDB();
        String dbPath = this.tempDB.getAbsolutePath().replace(".script", "");

        itemPersistence = new ItemPersistenceHSQLDB(dbPath);

        searchItems = new SearchItems(itemPersistence);
    }

    @Test
    public void test() {
        try {
            assertEquals(16, searchItems.getSearchResult().size());

            searchItems.addProcessor("i5");
            assertEquals(2, searchItems.getList().size());

            searchItems.setSearchName("XPS");
            assertEquals(1, searchItems.getList().size());

            searchItems.setSearchName("");
            assertEquals(2, searchItems.getList().size());

            searchItems.addProcessor("i5"); // this should remove the i5 now.
            assertEquals(16, searchItems.getList().size());

            searchItems.addProcessor("i7"); // add i7 to filter
            assertEquals(5, searchItems.getList().size());

            searchItems.addProcessor("i5"); // add i7 to filter
            assertEquals(7, searchItems.getList().size());

            searchItems.addGraphicCard("4090"); // add i7 to filter
            assertEquals(1, searchItems.getList().size());

            searchItems.setSearchName("XPS");
            assertEquals(0, searchItems.getList().size());
        } catch(Exception e){

        }
    }


    @After
    public void tearDown() {
        this.tempDB.delete();
    }
}
