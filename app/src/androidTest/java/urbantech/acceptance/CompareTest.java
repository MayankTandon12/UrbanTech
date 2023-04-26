package urbantech.acceptance;


import static androidx.test.espresso.Espresso.onView;


import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.view.View;

import androidx.test.core.app.ActivityScenario;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.urbantech.R;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import urbantech.presentation.mainactivity.MainActivity;

/*
    Feature: compare items
    compare two items
 */
@RunWith(AndroidJUnit4.class)
public class CompareTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp() {
        ActivityScenario.launch(MainActivity.class);
    }

    @Test
    public void compareTest() {
        // Perform a click on the first item compare button in the RecyclerView

        onView(withId(R.id.recyclerviewMain)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, clickChildViewWithId(R.id.compareButton)));

        // Perform a click on the second item compare button in the RecyclerView
        onView(withId(R.id.recyclerviewMain)).perform(
                RecyclerViewActions.actionOnItemAtPosition(1, clickChildViewWithId(R.id.compareButton)));

        //check if on the compare screen
        Espresso.onView(withId(R.id.activity_compare)).check(matches(isDisplayed()));

    }

    private ViewAction clickChildViewWithId(final int id) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return null;
            }

            @Override
            public String getDescription() {
                return "Click on a child view with specified id.";
            }

            @Override
            public void perform(UiController uiController, View view) {
                View v = view.findViewById(id);
                v.performClick();
            }
        };
    }
}

