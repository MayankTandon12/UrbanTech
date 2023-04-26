package urbantech.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import urbantech.objects.Item;
import urbantech.persistence.ItemPersistence;
import urbantech.persistence.hsqldb.ItemPersistenceHSQLDB;
import urbantech.utils.TestUtils;

public class AccessItemsTest {
    private ItemPersistence itemPersistence;
    private AccessItems accessItems;
    File tempDB;

    @Before
    public void setUp() throws IOException {
        itemPersistence = mock(ItemPersistence.class);

        ArrayList<Item> items = new ArrayList<>();
        Item item = mock(Item.class);


        //tempDB = TestUtils.copyDB();
        //String dp = this.tempDB.getAbsolutePath().replace(".script", "");
        //ItemPersistence ow = new ItemPersistenceHSQLDB(dp);
        //Item item1 = ow.getRecommendedItems(1).get(0);
        //when(item.getModelName()).thenReturn("S22");
        items.add(item);
        when(itemPersistence.getRecommendedItems(1)).thenReturn(items);
        accessItems = AccessItems.getInstance();
        accessItems.init(itemPersistence);
    }

    @Test
    public void testAC() {
        assertEquals(1,accessItems.getRecommendedItems(1).size());
    }
}
