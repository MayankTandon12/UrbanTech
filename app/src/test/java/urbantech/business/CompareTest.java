package urbantech.business;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


import org.junit.Before;
import org.junit.Test;


import urbantech.business.CompareItems;
import urbantech.objects.Item;

public class CompareTest {

    private CompareItems items;
    private Item item1;
    private Item item3;
    @Before
    public void init() {
        items = new CompareItems();
        //item1 = new Item(1,"item1","P","C1",23.25, //added here
        //                "S1","R1","P1","G1","S1","T1",
         //               "O1",1,0.002,0,1,900);
        //item2 = test.getFirst();

        //item3 = new Item(10,"item3","P","C1",0.25, //added here
          //      "S1","R1","P1","G1","S1","T1",
           //     "O3d",1,0.2,0,1,8);

        item1 = mock(Item.class);
        item3 = mock(Item.class);
        when(item1.getPrice()).thenReturn(38.121);
        when(item3.getPrice()).thenReturn(23.5);
        when(item1.getDiscountPercentage()).thenReturn(0.01);
        when(item3.getDiscountPercentage()).thenReturn(0.8);
        when(item1.getStock()).thenReturn(8);
        when(item3.getStock()).thenReturn(5);
        items.addItem(item1);
        //items.addItem(item2);
        items.addItem(item3);
    }

    @Test
    public void testComparedSize() {
        assertEquals(2, items.size());
    }

    @Test
    public void testComparePrice() {
        assertEquals(item3, items.comparePrice());
    }

    @Test
    public void testCompareStock() {
        assertEquals(item1, items.compareStock());
    }

    @Test
    public void testComparePriceAfterDiscount() {
        assertEquals(item1, items.comparePriceAfterDiscount());
    }
}

