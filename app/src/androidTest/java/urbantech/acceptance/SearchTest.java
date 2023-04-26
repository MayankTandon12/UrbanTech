package urbantech.acceptance;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.containsString;


import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.urbantech.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import urbantech.presentation.mainactivity.MainActivity;

/*
    Feature: Search
    search an particular item
 */
@RunWith(AndroidJUnit4.class)
public class SearchTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp() {
        ActivityScenario.launch(MainActivity.class);
    }

    @Test
    public void searchTest() {
        // Perform a click on the first item in the RecyclerView
        Espresso.onView(withId(R.id.search_view)).perform(click());
        Espresso.onView(withId(R.id.search_view)).perform(typeText("S22"));
        Espresso.onView(withId(R.id.recyclerviewMain)).perform(click());

        Espresso.onView(withId(R.id.productTextView)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.productTextView)).check(matches(withText(containsString("S22"))));


    }
}
