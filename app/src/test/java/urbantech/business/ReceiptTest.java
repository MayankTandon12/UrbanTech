package urbantech.business;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import urbantech.objects.Item;
import urbantech.objects.ItemBuilder;


public class ReceiptTest {
    private Receipt receipt1;
    private Receipt receipt2;
    private Item item1;
    private Item item2;

    @Before
    public void setUp() {

        ArrayList<Item> items1 = new ArrayList<>();
        ArrayList<Item> items2 = new ArrayList<>();

        item1 =mock(Item.class);
        when(item1.getModelName()).thenReturn("Test Item 1");

        item2 =mock(Item.class);
        when(item2.getModelName()).thenReturn("Test Item 2");

        items1.add(item1);//not testing if the items can properly give a toString, so just use a String for tests
        items1.add(item2);

        items2.add(item1);
        items2.add(item2);

        receipt1 = new Receipt("Jayden", 42.50, 2,items1, 112);
        receipt2 = new Receipt("name",123.21, 2, items2, 510);
    }

    @Test
    public void testItemsConvert() {
        String expectedString1 = "Test Item 1, Test Item 2";
        assertEquals(expectedString1, receipt1.getItemsString());//the getItemsString method uses itemsConvert
    }

    @Test
    public void testToString() {
        String expectedString1 = " Thanks for your order Jayden! \n Your order number is: 112 \n You bought " +
                "2 item(s) for a total of 42.50 \n Here were your items: Test Item 1, Test Item 2 \n Thanks for shopping with us!\n";
        assertEquals(expectedString1, receipt1.toString());

        String expectedString2 = " Thanks for your order name! \n Your order number is: 510 \n You bought " +
                "2 item(s) for a total of 123.21 \n Here were your items: Test Item 1, Test Item 2 \n Thanks for shopping with us!\n";
        assertEquals(expectedString2, receipt2.toString());
    }
}