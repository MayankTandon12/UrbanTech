package urbantech.persistence;

import java.util.List;

import urbantech.objects.Category;
import urbantech.objects.Item;

public interface ItemPersistence {
    List<Item> getItemsSequential();

    void updateStock(Item item);

    void setSearchName(String search);

    void setCategory(String category);

    void addBrands(String brand);

    void addProcessor(String processor);

    void addGraphicCard(String graphicCard);

    void resetFilter();

    int SizeOfResult();

    List<Category> getCategories();

    List<Item> getRecommendedItems(int id);

}
