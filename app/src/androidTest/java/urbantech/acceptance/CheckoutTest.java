package urbantech.acceptance;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.urbantech.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import urbantech.presentation.mainactivity.MainActivity;

/*
    Feature: checkout
    add an item to cart and then checkout
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class CheckoutTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp() {
        ActivityScenario.launch(MainActivity.class);
    }

    @Test
    public void addItemToCartTest() {
        // Perform a click on the first item in the RecyclerView
        Espresso.onView(withId(R.id.recyclerviewMain)).perform(scrollTo(),click());
        // Click the "Add to cart" button
        Espresso.onView(withId(R.id.addToCart)).perform(scrollTo(),click());

        // Navigate to the cart
        Espresso.onView(withId(R.id.cartButton)).perform(scrollTo(),click());

        Espresso.onView(withId(R.id.checkoutButton)).perform(click());

        // Enter the address
        String[] address = {"123 street", "Winnipeg", "R2M 7G5", "MB", "Canada"};

        Espresso.onView(withId(R.id.shipping_street_name)).perform(scrollTo(),typeText(address[0]));
        Espresso.onView(withId(R.id.shipping_city)).perform(scrollTo(),typeText(address[1]));
        Espresso.onView(withId(R.id.shipping_postal_code)).perform(scrollTo(),typeText(address[2]));
        Espresso.onView(withId(R.id.shipping_province)).perform(scrollTo(),typeText(address[3]));
        Espresso.onView(withId(R.id.shipping_country)).perform(scrollTo(),typeText(address[4]));

        // Enter the card
        String[] card = {"4665977683369232", "0125", "852"};
        Espresso.onView(withId(R.id.card_number)).perform(scrollTo(),typeText(card[0]));
        Espresso.onView(withId(R.id.expiry_date)).perform(scrollTo(),typeText(card[1]));
        Espresso.onView(withId(R.id.cvv)).perform(scrollTo(),typeText(card[2]));

        // Enter the info
        String[] info = {"1234567891","info@email.com"};
        Espresso.onView(withId(R.id.phone_number)).perform(scrollTo(),typeText(info[0]));
        Espresso.onView(withId(R.id.email_address)).perform(scrollTo(),typeText(info[1]));


        //checkout
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.checkout_button)).perform(scrollTo(),click());
        // Check if got to order placed screen
        Espresso.onView(withId(R.id.order_placed)).check(matches(isDisplayed()));
    }
}
