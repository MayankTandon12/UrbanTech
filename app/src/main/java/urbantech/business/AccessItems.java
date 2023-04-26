package urbantech.business;

import java.util.List;

import urbantech.application.Services;
import urbantech.objects.Item;
import urbantech.persistence.ItemPersistence;

public class AccessItems { //singleton
    private static final AccessItems accessItems = new AccessItems();
    private ItemPersistence itemPersistence;
    private List<Item> items;
    private AccessItems() {
        try {
            itemPersistence = Services.getItemPersistence();
            items = itemPersistence.getItemsSequential();
        } catch(Exception e){}
    }

    public static AccessItems getInstance() {
        return accessItems;
    }


    public AccessItems(ItemPersistence persistence){
        itemPersistence = persistence;
        items = itemPersistence.getItemsSequential();
    }

    //this is used for test
    public void init(ItemPersistence itemPersistence1) {
        this.itemPersistence = itemPersistence1;
        this.items = itemPersistence1.getItemsSequential();
    }

    public List<Item> getItems() {
        return items;
    }

    public void updateStock(Item item) {
        itemPersistence.updateStock(item);
    }

    public List<Item> getRecommendedItems(int id){
        return itemPersistence.getRecommendedItems(id);
    }
}
