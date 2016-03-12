package com.cse110devteam;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.cse110devteam.Activity.Login;

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
 * Created by Zee on 3/11/16.
 */
@RunWith(AndroidJUnit4.class)

public class testLoginFail {
    String username,password;

    @Rule
    public ActivityTestRule<Login> loginActivityTestRule = new ActivityTestRule<>(Login.class);

    @Before
    public void initStrings() {
        username = "12345thisisatestingemail@test.com";
        password = "passworddoesnotexist";
    }

    @Test
    public void testUnsuccessfulLogin() {
        onView(withId(R.id.email)).perform(typeText(username), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText(password), closeSoftKeyboard());

        onView(withId(R.id.login)).perform(click());

        // Below checks that if the app is still in the login screen
        // if so, then this test is passed
        onView(withId(R.id.login)).check(matches(isDisplayed()));

    }
}