package urbantech;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import urbantech.acceptance.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AccountTest.class,
        AddItemToCartTest.class,
        CheckoutTest.class,
        CompareTest.class,
        UserInterfaceTest.class,
        CategorizingItemTest.class,
        SearchTest.class,
        UniversalNavigationTest.class,
        ReccomendedItemsTest.class

})
public class AllAcceptanceTests {
}


