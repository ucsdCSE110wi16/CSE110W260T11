package com.cse110devteam.Activity;

import com.cse110devteam.Global.ChatApplication;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by vidur on 3/9/2016.
 */
public class EmailValidatorTest  {
    @Test
    public void emailValidator_test_server() {
        ChatApplication ChatApp = new ChatApplication();
        assertTrue(Login.emailInSystem("vidurbutalia@gmail.com"));
    }
}
