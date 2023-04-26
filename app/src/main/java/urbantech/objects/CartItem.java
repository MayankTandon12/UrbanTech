package urbantech.objects;

public class CartItem {
    private final Item item;
    private int quantity;

    public CartItem(Item item, int quantity) { //initialize cart item from persistence
        this.item = item;
        this.quantity = quantity;
    }

    public Item getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void incrementItemQuantity() {
        if (quantity < item.getStock()) {
            quantity += 1;
        }
    }

    public void decrementQuantity() {
        if (quantity > 1) {
            quantity -= 1;
        }
    }

    public double calculatePrice() {
        return item.getPrice() * quantity;
    }
}
