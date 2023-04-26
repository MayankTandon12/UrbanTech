package urbantech.objects;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.Locale;

public class Item implements Parcelable {

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
    int itemID = 1, inCartQuantity, inStock, discountThreshold;
    String ModelName, Brand, Category, Storage, RAM, Processor, GraphicCard, ScreenSize, TouchScreen, Overview;
    double price, discountPercentage;
    int image = 0;

    Item() {
    }

    public Item(final int itemID, final String name, final String brand, final String category, double price, final String storage, final String ram, final String processor, final String graphicCard,
                final String size, final String touch, final String overview, int image, double discountPercent, int discountQuantity, int inCartQuantity, int inStock) {
        this.itemID = itemID;
        this.ModelName = name;
        this.Brand = brand;
        this.Category = category;
        this.price = price;
        this.Storage = storage;
        this.RAM = ram;
        this.Processor = processor;
        this.GraphicCard = graphicCard;
        this.ScreenSize = size;
        this.TouchScreen = touch;
        this.image = image;
        this.Overview = overview;
        this.discountPercentage = discountPercent;
        this.discountThreshold = discountQuantity;
        this.inCartQuantity = inCartQuantity;
        this.inStock = inStock;
    }

    protected Item(Parcel in) {
        itemID = in.readInt();
        ModelName = in.readString();
        Brand = in.readString();
        Category = in.readString(); //added here
        price = in.readDouble();
        Storage = in.readString();
        RAM = in.readString();
        Processor = in.readString();
        GraphicCard = in.readString();
        ScreenSize = in.readString();
        TouchScreen = in.readString();
        Overview = in.readString();
        image = in.readInt();
        discountThreshold = in.readInt();
        discountPercentage = in.readDouble();
        inCartQuantity = in.readInt();
        inStock = in.readInt();
    }

    public void setInCartQuantity(int quantity) {
        this.inCartQuantity = quantity;
        if (this.inCartQuantity > this.inStock) {
            this.inCartQuantity = this.inStock;
            Log.d("TAG", "Item in limited in stock");
        }
    }

    public double calculatePrice() {
        return price * inCartQuantity;
    }

    //getters and setters
    public String getModelName() {
        return ModelName;
    }

    public int getItemID() {
        return itemID;
    }

    public String getBrand() {
        return Brand;
    }

    public double getPrice() {
        return price;
    }

    public String getStorage() {
        return Storage;
    }

    public String getRAM() {
        return RAM;
    }

    public String getProcessor() {
        return Processor;
    }

    public String getGraphicCard() {
        return GraphicCard;
    }

    public String getScreenSize() {
        return ScreenSize;
    }

    public String isTouchScreen() {
        return TouchScreen;
    }

    public String getOverview() {
        return Overview;
    }

    public int getImage() {
        return image;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercent) {
        this.discountPercentage = discountPercent;
    }

    public int getDiscountThreshold() {
        return discountThreshold;
    }

    public void setDiscountThreshold(int discountAmount) {
        this.discountThreshold = discountAmount;
    }

    public int getCartQuantity() {
        return inCartQuantity;
    }

    public int getStock() {
        return inStock;
    }

    public void setStock(int stock) {
        this.inStock = stock;
    }

    @NonNull
    public String toString() {
        return String.format(Locale.ENGLISH,"Model Name: %s \nBrand: %s \nPrice: %.2f \nOverview: %s \nStorage: %s \nRAM: %s \nProcessor: %s \nGraphic Card: %s \n" +
                        "Screen Size: %s \nTouch Screen %s\nStock: %d",
                ModelName, Brand, price, Overview, Storage, RAM, Processor, GraphicCard, ScreenSize, TouchScreen, inStock);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(itemID);
        parcel.writeString(ModelName);
        parcel.writeString(Brand);
        parcel.writeString(Category); // added here
        parcel.writeDouble(price);
        parcel.writeString(Storage);
        parcel.writeString(RAM);
        parcel.writeString(Processor);
        parcel.writeString(GraphicCard);
        parcel.writeString(ScreenSize);
        parcel.writeString(TouchScreen);
        parcel.writeString(Overview);
        parcel.writeInt(image);
        parcel.writeInt(discountThreshold);
        parcel.writeDouble(discountPercentage);
        parcel.writeInt(inCartQuantity);
        parcel.writeInt(inStock);
    }
}

