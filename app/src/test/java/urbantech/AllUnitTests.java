package urbantech;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import urbantech.business.*;
import urbantech.objects.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AddressTesting.class,
        ReceiptTest.class,
        CardValidTest.class,
        CompareTest.class,
        AccessItemsTest.class,
        CartItemTest.class,
        CategoryTest.class
})

public class AllUnitTests
{

}
