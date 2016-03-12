package com.cse110devteam;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.cse110devteam.Activity.*;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Jeremy Cruz on 3/11/2016.
 */
public class cancleCreateAccount {
    private CreateAccount todo;

    @Rule
    public ActivityTestRule<Login> cancleAccountActivityTestRule = new ActivityTestRule<>(CreateAccount.class);

    @Before
    public void createApp() {
        todo = new CreateAccount();
    }

    @Test
    public void testCancelAccount() {
        onView(withId(R.id.cancel)).perform(click());
    }
}
