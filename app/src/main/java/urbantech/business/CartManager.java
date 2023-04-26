package urbantech.business;

import java.util.List;

import urbantech.objects.CartItem;
import urbantech.objects.Item;

public class CartManager { //singleton though modified slightly to allow dependency injection (public constructor instead of private)
    private static final CartManager cart = new CartManager(AccessCartItems.getInstance(), AccessItems.getInstance());
    //functional
    private List<CartItem> cartItems;
    private AccessCartItems accessCartItems;
    private AccessItems accessItems;
    public CartManager(AccessCartItems accessCartItems, AccessItems accessItems) {
        try {
            this.accessItems = accessItems;
            this.accessCartItems = accessCartItems;
            this.cartItems = accessCartItems.getCartItems(); //receive items from database
        } catch(Exception e){

        }
    }

    public static CartManager getInstance() {
        return cart;
    }

    public void init(AccessCartItems aCart, AccessItems aItems) {
        this.accessCartItems = aCart;
        this.accessItems = aItems;
        this.cartItems = aCart.getCartItems();
    }

    public void addItemToCart(CartItem item) {
        CartItem currItem = findItem(item.getItem().getItemID());
        if (currItem == null && item.getItem().getStock() > 0) {
            cartItems.add(item);
            accessCartItems.addCartItem(item);
        }
    }

    private CartItem findItem(int itemID) {
        CartItem currItem = null;
        for (int i = 0; i < cartItems.size() && currItem == null; i++) {
            if (itemID == cartItems.get(i).getItem().getItemID()) {
                currItem = cartItems.get(i);
            }
        }
        return currItem;
    }

    public List<CartItem> getCartList() {
        return cartItems;
    }

    public double getTotalPrice() {
        double totalPrice = 0;
        for (CartItem item : cartItems)
            totalPrice += item.calculatePrice();
        return totalPrice;
    }

    public void removeItemFromCart(int itemID) {
        for (int i = 0; i < cartItems.size(); i++) {
            if (itemID == cartItems.get(i).getItem().getItemID()) {
                accessCartItems.removeCartItem(cartItems.get(i));
                cartItems.remove(i);
                break;
            }
        }
    }

    public void checkout() {
        while (!cartItems.isEmpty()) {
            CartItem cartItem = cartItems.get(0);
            Item item = cartItem.getItem();
            item.setStock(item.getStock() - cartItem.getQuantity());
            accessItems.updateStock(item);
            removeItemFromCart(item.getItemID());
        }
    }

    public void incrementItemQuantity(int itemID) {
        CartItem item = findItem(itemID);
        if (item != null) {
            item.incrementItemQuantity();
            accessCartItems.updateCartItemQuantity(item);
        }
    }

    public void decrementItemQuantity(int itemID) {
        CartItem item = findItem(itemID);
        if (item != null) {
            item.decrementQuantity();
            accessCartItems.updateCartItemQuantity(item);
        }
    }

    public int getItemQuantity(int itemID) {
        int itemCount = 0;
        CartItem item = findItem(itemID);
        if (item != null)
            itemCount = item.getQuantity();
        return itemCount;
    }


}