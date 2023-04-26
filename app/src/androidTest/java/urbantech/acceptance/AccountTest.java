package urbantech.acceptance;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.matcher.RootMatchers.withDecorView;


import android.view.View;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.urbantech.R;

import org.junit.Before;
import org.junit.runner.RunWith;


import urbantech.presentation.mainactivity.MainActivity;


/*
    Feature: Account
    1. This test create an account, if the account is existing, just login.
    2. Then save personal info
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class AccountTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);
    private View decorView;
    @Before
    public void setUp() {
//        ActivityScenario.launch(MainActivity.class);
//        activityRule.getScenario().onActivity(new ActivityScenario.ActivityAction<MainActivity>() {
//            @Override
//            public void perform(MainActivity activity) {
//                decorView = activity.getWindow().getDecorView();
//            }
//        });

    }

    @Test
    public void accountTest1() {
        commonProcess();
        onView(withId(R.id.accountButton)).perform(scrollTo(), click());
        onView(withId(R.id.activity_account)).check(matches(isDisplayed()));
    }

    @Test
    public void accountTest2() {

        commonProcess();

        //onView(withId(R.id.homeButton)).perform(scrollTo(), click());

        onView(withId(R.id.recyclerviewMain)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        Espresso.onView(withId(R.id.addToCart)).perform(click());

        // Navigate to the cart
        Espresso.onView(withId(R.id.cartButton)).perform(scrollTo(),click());

        Espresso.onView(withId(R.id.checkoutButton)).perform(click());


        // save personal info

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


        // checkout
        Espresso.closeSoftKeyboard();

        // save personal info
        Espresso.onView(withId(R.id.save_button)).perform(scrollTo(),click());
        Espresso.onView(withId(R.id.accountButton)).perform(scrollTo(), click());
        Espresso.onView(withId(R.id.activity_account)).check(matches(isDisplayed()));

    }


    private void commonProcess() {
        onView(withId(R.id.accountButton)).perform(scrollTo(),click());
        boolean isActivityAccountDisplayed = false;
        // check if it already login
        try {
            onView(withId(R.id.activity_account)).check(matches(isDisplayed()));
            isActivityAccountDisplayed = true;
        } catch (Exception e) {
            // Do nothing
        }
        // if it is already login, logout first
        if(isActivityAccountDisplayed) {
            onView(withId(R.id.logout)).perform(scrollTo(),click());
            onView(withId(R.id.accountButton)).perform(scrollTo(),click());
        }

        onView(withId(R.id.createAccountButton)).perform(click());

        // enter the account
        onView(withId(R.id.email)).perform(click());
        onView(withId(R.id.email)).perform(typeText("crazydog1"));

        // enter the password
        onView(withId(R.id.password)).perform(click());
        onView(withId(R.id.password)).perform(typeText("111111"));

        // confirm the password
        onView(withId(R.id.et_confirm_password)).perform(click());
        onView(withId(R.id.et_confirm_password)).perform(typeText("111111"));

        // create an account
        onView(withId(R.id.btn_create_account)).perform(click());

        boolean isExist = false;
        try {
            // check if the account is already existing
            onView(withText("Account email already exists!"))
                    .inRoot(withDecorView(Matchers.not(decorView)))// Here you use decorView
                    .check(matches(isDisplayed()));

            isExist = true;
        } catch (Exception e) {
            // do nothing
        }
        if(isExist) {
            closeSoftKeyboard();
            onView(withId(R.id.accountButton)).perform(scrollTo(),click());
        }
        // log in
        onView(withId(R.id.email)).perform(click());
        onView(withId(R.id.email)).perform(typeText("crazydog1"));
        onView(withId(R.id.password)).perform(click());
        onView(withId(R.id.password)).perform(typeText("111111"));

        onView(withId(R.id.loginButton)).perform(click());

    }

    @After
    public void tearDown() {
        activityRule.getScenario().close();
        ActivityScenario.launch(MainActivity.class);
    }
}
