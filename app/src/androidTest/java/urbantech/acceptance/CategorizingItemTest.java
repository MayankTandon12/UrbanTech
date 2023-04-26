package urbantech.acceptance;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.urbantech.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import urbantech.presentation.mainactivity.MainActivity;


/*
    Feature: Categorizing Items
    1. find a category
    2. search an item which is not in the category
 */
@RunWith(AndroidJUnit4.class)
public class CategorizingItemTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp() {
//        ActivityScenario.launch(MainActivity.class);
    }

    @Test
    public void categoryTest1() {
        onView(withId(R.id.categoryButton)).perform(scrollTo(),click());

        onView(withId(R.id.activity_category)).check(matches(isDisplayed()));
        onView(withId(R.id.recyclerViewCategory)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.recyclerviewMain)).check(matches(isDisplayed()));


    }

    @Test
    public void categoryTest2() {
        onView(withId(R.id.categoryButton)).perform(scrollTo(),click());

        onView(withId(R.id.activity_category)).check(matches(isDisplayed()));
        onView(withId(R.id.recyclerViewCategory)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        // search an item which is not in the category
        onView(withId(R.id.search_view)).perform(click());
        onView(withId(R.id.search_view)).perform(typeText("S22"));

        onView(withId(R.id.recyclerviewMain)).check(matches(not(withText(containsString("S22")))));

    }

}
