package com.cse110devteam;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import com.cse110devteam.Activity.Login;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by vidur on 3/11/2016.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class testLoginInputFields {
    private String testUsername;

    @Rule
    public ActivityTestRule<Login> loginActivityTestRule = new ActivityTestRule<>(Login.class);

    @Before
    public void initString() {
        testUsername = "vidurbutalia@gmail.com";
    }

    @Test
    public void changeUsername_loginActivity() {
        onView(withId(R.id.email)).perform(typeText(testUsername), closeSoftKeyboard());
        onView(withId(R.id.email)).check(matches(withText(testUsername)));

    }
}
