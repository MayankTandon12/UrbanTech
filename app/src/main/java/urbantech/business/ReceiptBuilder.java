package urbantech.business;

import java.util.List;

import urbantech.business.Receipt;

public class ReceiptBuilder extends Receipt {
    private final Receipt receipt;

    public ReceiptBuilder() {
        super();
        this.receipt = new Receipt();
    }

    public ReceiptBuilder name(String name) {
        this.receipt.name = name;
        return this;
    }

    public ReceiptBuilder total(double total) {
        this.receipt.total = total;
        return this;
    }

    public ReceiptBuilder amountItems(int amountItems) {
        this.receipt.amountItems = amountItems;
        return this;
    }

    public ReceiptBuilder items(List items) {
        this.receipt.items = items;
        return this;
    }

    public ReceiptBuilder orderNumber(int orderNumber) {
        this.receipt.orderNumber = orderNumber;
        return this;
    }

    public ReceiptBuilder itemsString(List items) {
        this.receipt.itemsString = receipt.itemsConvert(items);
        return this;
    }

    public ReceiptBuilder orderNumberString(int orderNumber) {
        this.receipt.orderNumberString = receipt.orderNumberConvert(orderNumber);
        return this;
    }

    public ReceiptBuilder amountItemsString(int amountItems) {
        this.receipt.amountItemsString = receipt.amountItemsConvert(amountItems);
        return this;
    }

    public Receipt build() {
        return this.receipt;
    }
}