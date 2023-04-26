package urbantech.objects;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartItemTest {
    private Item item;
    private CartItem cartItem;

    @Before
    public void setUp() {
        item = mock(Item.class);
        when(item.getStock()).thenReturn(3);
        cartItem = new CartItem(item, 2);
    }

    @Test
    public void testGetItem() {
        assertEquals(item, cartItem.getItem());
    }

    @Test
    public void testGetQuantity() {
        assertEquals(2, cartItem.getQuantity());
    }

    @Test
    public void testIncrementItemQuantity() {
        int initialQuantity = cartItem.getQuantity()+1;
        cartItem.incrementItemQuantity();
        assertEquals(initialQuantity, cartItem.getQuantity());
    }

    @Test
    public void testIncrementItemQuantityNotExceedingStock() {
        int maxStock = item.getStock();
        cartItem = new CartItem(item, maxStock);
        cartItem.incrementItemQuantity();
        assertEquals(maxStock, cartItem.getQuantity());
    }

    @Test
    public void testDecrementQuantity() {
        int initialQuantity = cartItem.getQuantity();
        cartItem.decrementQuantity();
        assertEquals(initialQuantity - 1, cartItem.getQuantity());
    }


    @Test
    public void testCalculatePrice() {
        double expectedPrice = item.getPrice() * cartItem.getQuantity();
        assertEquals(expectedPrice, cartItem.calculatePrice(), 0.001);
    }
}