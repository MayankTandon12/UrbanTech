package urbantech.objects;

import urbantech.objects.Item;

public class ItemBuilder extends Item {
    private final Item item;

    public ItemBuilder() {
        super();
        this.item = new Item();
    }

    public ItemBuilder ID(int ID) {
        this.item.itemID = ID;
        return this;
    }

    public ItemBuilder name(String name) {
        this.item.ModelName = name;
        return this;
    }

    public ItemBuilder brand(String brand) {
        this.item.Brand = brand;
        return this;
    }

    public ItemBuilder category(String category) {
        this.item.Category = category;
        return this;
    }

    public ItemBuilder price(double price) {
        this.item.price = price;
        return this;
    }

    public ItemBuilder storage(String storage) {
        this.item.Storage = storage;
        return this;
    }

    public ItemBuilder ram(String ram) {
        this.item.RAM = ram;
        return this;
    }

    public ItemBuilder processor(String processor) {
        this.item.Processor = processor;
        return this;
    }

    public ItemBuilder graphicCard(String graphicCard) {
        this.item.GraphicCard = graphicCard;
        return this;
    }

    public ItemBuilder size(String size) {
        this.item.ScreenSize = size;
        return this;
    }

    public ItemBuilder touch(String touch) {
        this.item.TouchScreen = touch;
        return this;
    }

    public ItemBuilder overview(String overview) {
        this.item.Overview = overview;
        return this;
    }

    public ItemBuilder image(int image) {
        this.item.image = image;
        return this;
    }

    public ItemBuilder discountPercentage(double discountPercent) {
        this.item.discountPercentage = discountPercent;
        return this;
    }

    public ItemBuilder discountQuantity(int discountQuantity) {
        this.item.discountThreshold = discountQuantity;
        return this;
    }

    public ItemBuilder inCartQuantity(int inCartQuantity) {
        this.item.inCartQuantity = inCartQuantity;
        return this;
    }

    public ItemBuilder inStock(int inStock) {
        this.item.inStock = inStock;
        return this;
    }

    public Item build() {
        return this.item;
    }
}