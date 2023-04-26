**Urban Tech Architecture**

Our architecture diagram reflects the current state of our project, though we have chosen to exclude future iteration features. Each layer in the architecture diagram has an associated package within the project, and the classes within each indicated layer belong to their respective package (apart from Item, which belongs to the Object package). 
We have chosen to exclude helper classes, as this would result in unnecessary clutter. Thus, the current functionality of our project is as follows:

***UI Layer***

MainActivity is the home screen of the application and consists of a list of items with their prices, brand, image and name. ProductActivity is the screen that appears when an item is clicked, and consists of an image, price, stock and an add to cart button. CartActivity consists of a list of items that have been added to the cart along with their quantity and total price. Checkout activity allows the user to input their personal data, and order the items in their cart. Category Activity displays the categories of items, CompareActivity shows item comparisons and AccountActivity allows for account management.

***Business Layer***

AccessItems retrieves the list of items to be displayed in MainActivity from the database. AccessCartItems retrieves the list of items in the user’s cart and adds them to CartManager. CartManager receives the item specified in ProductActivity and adds it to the cart, it manages the quantity of an item, and prevents a user from adding more than the total stock of an item to their cart. When the information entered in checkout activity is validated by ValidateDeliveryAddress, Cardpayment and Receipt, CartManager will execute the checkout, and reduce the stock of contained items, CompareItems performs logical comparison, between items and search sorts all items based on user inputted parameters. 

***Application Layer***

Services is the synchronous interface between the logic layer and the persistence layer, it prevents the database from being instantiated more than once.

***Persistence***

The various HSQLDB accessors provide database access for their respective business functions, i.e. item descriptions, item prices and item stock for ItemPersistenceHSQLDB, item-cart quantities for CartItemPersistenceHSQLDB, etc.

![ARCHITECTURE_DIAGRAM](https://code.cs.umanitoba.ca/comp3350-winter2023/A01-G12-UrbanTech/-/raw/master/ARCHITECTURE_DIAGRAM.png)
