package urbantech.acceptance;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


import com.example.urbantech.R;

import urbantech.presentation.mainactivity.MainActivity;


/*
    Feature: cart
    add an item to cart
 */
@RunWith(AndroidJUnit4.class)
public class AddItemToCartTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp() {
        ActivityScenario.launch(MainActivity.class);
    }

    @Test
    public void addItemToCartTest() {
        // Perform a click on the first item in the RecyclerView
        Espresso.onView(withId(R.id.recyclerviewMain)).perform(click());
        // Click the "Add to cart" button
        Espresso.onView(withId(R.id.addToCart)).perform(click());

        // Navigate to the cart
        Espresso.onView(withId(R.id.cartButton)).perform(scrollTo(),click());

        // Check if the item is displayed in the cart
        Espresso.onView(withId(R.id.recyclerviewCart)).check(matches(isDisplayed()));
    }
}