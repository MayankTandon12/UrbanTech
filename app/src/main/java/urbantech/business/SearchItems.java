package urbantech.business;

import java.util.List;

import urbantech.application.Services;
import urbantech.objects.Item;
import urbantech.persistence.ItemPersistence;

public class SearchItems {

    private final ItemPersistence itemPersistence;

    public SearchItems() {
        itemPersistence = Services.getItemPersistence();
    }

    public SearchItems(final ItemPersistence itemPersistence) {
        this.itemPersistence = itemPersistence;
    }

    public List<Item> getSearchResult() {
        return itemPersistence.getItemsSequential();
    }

    public void setSearchName(String search) {
        itemPersistence.setSearchName(search);
    }

    public List<Item> getList() {
        return itemPersistence.getItemsSequential();
    }

    public void setCategory(String category) {
        itemPersistence.setCategory(category);
    }

    public void addBrands(String brand) {
        itemPersistence.addBrands(brand);
    }

    public void addProcessor(String processor) {
        itemPersistence.addProcessor(processor);
    }

    public void addGraphicCard(String graphicCard) {
        itemPersistence.addGraphicCard(graphicCard);
    }

    public void clearFilters() {
        itemPersistence.resetFilter();
    }

    public int getSize() {
        return itemPersistence.SizeOfResult();
    }

}
