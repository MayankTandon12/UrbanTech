package urbantech.acceptance;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.core.app.ActivityScenario;
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
    Feature:Universal navigation
 */
@RunWith(AndroidJUnit4.class)
public class UserInterfaceTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp() {
        ActivityScenario.launch(MainActivity.class);
    }

    @Test
    public void itemDisplayTest() {
        onView(withId(R.id.recyclerviewMain)).perform(RecyclerViewActions.actionOnItemAtPosition(0, scrollTo()));

        onView(withId(R.id.recyclerviewMain)).perform(RecyclerViewActions.actionOnItemAtPosition(10, scrollTo()));

        onView(withId(R.id.recyclerviewMain)).check(matches(isDisplayed()));

    }
}
