package urbantech.persistence;

import java.util.List;

import urbantech.objects.CartItem;

public interface CartItemPersistence {

    List<CartItem> getCartItemsSequential();

    void updateCartItemQuantity(CartItem item);

    void removeCartItem(CartItem item);

    void addCartItem(CartItem item);
}
