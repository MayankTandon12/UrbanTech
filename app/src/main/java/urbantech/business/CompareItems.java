package urbantech.business;


import java.util.ArrayList;
import java.util.List;

import urbantech.objects.Item;

public class CompareItems {

    private final List<Item> items;

    public CompareItems(List<Item> items) {
        this.items = items;
    }

    public CompareItems() {
        items = new ArrayList<>();
    }

    /**
     * add a new item to be compared
     */
    public void addItem(Item newItem) {
        if (newItem == null) {
            return;
        }
        items.add(newItem);
    }

    /**
     * remove an item
     */
    public void removeItem(Item removed) {
        items.remove(removed);
    }

    public Item comparePrice() {
        try {
            double lowPrice;
            // if the item is on discount
            if (items.get(0).getDiscountThreshold() > 0) {
                lowPrice = items.get(0).getPrice() * items.get(0).getDiscountPercentage();
            } else {
                lowPrice = items.get(0).getPrice();
            }
            int lowestNum = 0;
            for (int i = 0; i < items.size(); i++) {
                double price;
                if (items.get(i).getDiscountThreshold() > 0) {
                    price = items.get(i).getPrice() * items.get(i).getDiscountPercentage();
                } else {
                    price = items.get(i).getPrice();
                }
                if (lowPrice > price) {
                    lowPrice = price;
                    lowestNum = i;
                }
            }
            return items.get(lowestNum);
        } catch (NullPointerException e) {
            System.out.println("null list");
            return null;
        }
    }

    //compare items in stock
    public Item compareStock() {
        try {
            int itemInStock = items.get(0).getStock();
            int maxItemInStock = 0;
            for (int i = 0; i < items.size(); i++) {
                int stockNum = items.get(i).getStock();
                if (stockNum > itemInStock) {
                    itemInStock = stockNum;
                    maxItemInStock = i;
                }
            }
            return items.get(maxItemInStock);
        } catch (NullPointerException e) {
            System.out.println("null list");
            return null;
        }
    }

    public Item comparePriceAfterDiscount() {
        try {
            double discPrice = items.get(0).getPrice() * items.get(0).getDiscountPercentage();
            int lowestNum = 0;
            for (int i = 0; i < items.size(); i++) {
                double dPrice = items.get(i).getPrice() * items.get(i).getDiscountPercentage();
                if (discPrice > dPrice) {
                    discPrice = dPrice;
                    lowestNum = i;
                }
            }
            return items.get(lowestNum);
        } catch (NullPointerException e) {
            System.out.println("null list");
            return null;
        }
    }

    public int size() {
        return items.size();
    }

    public Item getItem(int pos) {
        return items.get(pos);
    }

    public void removeFromPos(int pos) {
        items.remove(pos);
    }
}
