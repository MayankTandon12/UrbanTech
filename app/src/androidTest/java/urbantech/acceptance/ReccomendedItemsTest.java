package urbantech.acceptance;


import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.containsString;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.urbantech.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import urbantech.presentation.mainactivity.MainActivity;

public class ReccomendedItemsTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp() {
        ActivityScenario.launch(MainActivity.class);
    }

    @Test
    public void testReccomened() {
        // Perform a click on the first item in the RecyclerView
        Espresso.onView(withId(R.id.recyclerviewMain)).perform(click());
        // Click the "Add to cart" button
        Espresso.onView(withId(R.id.homeButton)).perform(scrollTo());

        Espresso.onView(withId(R.id.title_text_view)).check(matches(withText(containsString("RECOMMENDED ITEMS"))));

    }

}
