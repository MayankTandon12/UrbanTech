package urbantech.objects;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;


@RunWith(MockitoJUnitRunner.class)
public class CategoryTest {

    private Category category;
    private List<Item> items;

    @Before
    public void setUp() {
        items = new ArrayList<>();
        Item item1 = mock(Item.class);
        Item item2 = mock(Item.class);
        items.add(item1);
        items.add(item2);

        category = new Category("TestCategory", 1, items);
    }

    @Test
    public void getName() {
        assertEquals("TestCategory", category.getName());
    }

    @Test
    public void getImage() {
        assertEquals(1, category.getImage());
    }

    @Test
    public void getItems() {
        assertEquals(items, category.getItems());
    }

    @Test
    public void setName() {
        category.setName("NewCategory");
        assertEquals("NewCategory", category.getName());
    }

    @Test
    public void setImage() {
        category.setImage(2);
        assertEquals(2, category.getImage());
    }

}