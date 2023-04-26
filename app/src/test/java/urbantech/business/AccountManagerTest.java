package urbantech.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import urbantech.objects.Item;

public class AccountManagerTest {

    private AccountManager accountManager;

    @Before
    public void setUp() {
        String testPath = "src/main/assets/dbTest/SC";
        accountManager = new AccountManager(testPath);

        String username = "batman";
        String password = "111111";
        // create an account for test
        accountManager.CreateAcc(username, password);
        accountManager.loginAcc(testPath, username, password);

    }

    @Test
    public void successLogin() {
        assertTrue(accountManager.isLoginSuccess());
    }

    @Test
    public void testUsername() {
        assertEquals("BATMAN", accountManager.getUserName());
    }



}
