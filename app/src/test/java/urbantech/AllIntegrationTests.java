package urbantech;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import urbantech.business.*;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        AccessCartItemsIT.class,
        CartManagerIT.class,
        AccessCartItemsIT.class,
        AccountManagerIT.class,
        SearchItemsIT.class,
        AccessItemsIT.class,
})


public class AllIntegrationTests {
}
