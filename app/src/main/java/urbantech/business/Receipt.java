package urbantech.business;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Locale;

import urbantech.objects.Item;

@SuppressWarnings("rawtypes")
public class Receipt {
    String name;
    double total;
    int amountItems;
    List items;
    int orderNumber;
    String itemsString;
    String orderNumberString;
    String amountItemsString;

    Receipt() {
    }

    public Receipt(String name, double total, int amountItems, List<Item> items, int orderNumber) {
        this.name = name;
        this.amountItems = amountItems;
        this.items = items;
        this.orderNumber = orderNumber;
        this.total = total;
        itemsString = itemsConvert(items);
        orderNumberString = orderNumberConvert(orderNumber);
        amountItemsString = amountItemsConvert(amountItems);
    }

    String itemsConvert(List items) {
        StringBuilder build = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            Item item = (Item) items.get(i);
            if (i == items.size() - 1)//if its the last item in list
                build.append(item.getModelName());
            else//if there's more items to go
                build.append(item.getModelName()).append(", ");
        }
        return build.toString();
    }

    String orderNumberConvert(int orderNumber) {
        return String.valueOf(orderNumber);
    }

    String amountItemsConvert(int amountItems) {
        return String.valueOf(amountItems);
    }

    public List getItems() {
        return items;
    }

    public void setItems(List items) {
        this.items = items;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getAmountItems() {
        return amountItems;
    }

    public void setAmountItems(int amountItems) {
        this.amountItems = amountItems;
    }

    public String getItemsString() {
        return itemsString;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.ENGLISH, " Thanks for your order %s! \n Your order number is:" +
                        " %s \n You bought %s item(s) for a total of %.2f \n Here were your items:" +
                        " %s \n Thanks for shopping with us!\n",
                name, orderNumberString, amountItemsString, total, itemsString);
    }
}