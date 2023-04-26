package urbantech.business;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import urbantech.objects.CartItem;
import urbantech.objects.Item;
import urbantech.persistence.CartItemPersistence;

public class AccessCartItemsTest {

    private AccessCartItems accessCartItems;
    private CartItemPersistence cartItemPersistence;

    @Before
    public void setUp() {
        accessCartItems = AccessCartItems.getInstance();
        cartItemPersistence = mock(CartItemPersistence.class);
        accessCartItems.init(cartItemPersistence);
    }

    @Test
    public void test() {
        CartItem cartItem = mock(CartItem.class);
        when(cartItem.getQuantity()).thenReturn(1);

        List<CartItem> cartItems = new ArrayList<>();
        when(cartItemPersistence.getCartItemsSequential()).thenReturn(cartItems);

        accessCartItems.addCartItem(cartItem);
        cartItems.add(cartItem); // Simulate adding the cart item to the list

        assertEquals(1, accessCartItems.getCartItems().size());

        // Verify the interaction with the mock objects
        verify(cartItemPersistence).addCartItem(cartItem);
        verify(cartItemPersistence).getCartItemsSequential();
    }
}
