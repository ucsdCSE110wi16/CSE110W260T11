package com.cse110devteam;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import com.cse110devteam.Activity.Login;
import com.cse110devteam.Global.ChatApplication;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.Test;
import static org.junit.Assert.assertTrue;


/**
 * Created by vidur on 3/10/2016.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class testEmailVerification {
    private ChatApplication myApp;

    @Before
    public void createApp() {
        myApp = new ChatApplication();
    }

    @Test
    public void testEmailVerifier() {
        assertTrue(Login.emailInSystem("AnthonyRAltieri@gmail.com"));

    }
}
