package urbantech.business;

import java.util.List;

import urbantech.application.Services;
import urbantech.objects.CartItem;
import urbantech.persistence.CartItemPersistence;

public class AccessCartItems { //singleton
    private static final AccessCartItems accessCartItems = new AccessCartItems();
    private CartItemPersistence cartItemPersistence;

    private AccessCartItems() {
        cartItemPersistence = Services.getCartItemPersistence();
    }

    public AccessCartItems(CartItemPersistence persistence){
        cartItemPersistence = persistence;
    }

    public static AccessCartItems getInstance() {
        return accessCartItems;
    }

    // used for test
    public void init(CartItemPersistence cart) {
        this.cartItemPersistence = cart;
    }

    public List<CartItem> getCartItems() {
        return cartItemPersistence.getCartItemsSequential();
    }

    public void updateCartItemQuantity(CartItem item) {
        cartItemPersistence.updateCartItemQuantity(item);
    }

    public void addCartItem(CartItem item) {
        cartItemPersistence.addCartItem(item);
    }

    public void removeCartItem(CartItem item) {
        cartItemPersistence.removeCartItem(item);
    }

}
