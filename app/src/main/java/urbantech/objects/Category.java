package urbantech.objects;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class Category implements Parcelable {
    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };
    private String name;
    private int image;
    private List<Item> items;

    public Category(String name, int image, List<Item> items) {
        this.name = name;
        this.image = image;
        this.items = items;
    }

    protected Category(@NonNull Parcel in) {
        name = in.readString();
        image = in.readInt();
        items = in.createTypedArrayList(Item.CREATOR);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(image);
        parcel.writeList(items);
    }

    @NonNull
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", items=" + items +
                '}';
    }
}
