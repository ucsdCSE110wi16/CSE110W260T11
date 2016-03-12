package com.cse110devteam;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import com.cse110devteam.Global.Util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;


@RunWith(AndroidJUnit4.class)
public class ValidationTests {
    String validEmail1 = "test@test.com";
    String validEmail2 = "Hellothere@sample.com";
    String validEmail3 = "student@ucsd.edu";
    String validEmail4 = "activist@helpstuff.org";
    String validEmail5 = "person@usa.gov";
    String invalidEmail1 = "thisisbad";
    String invalidEmail2 = "mostdefinatelynotanemail";
    String invalidEmail3 = "this.should.not.work.";
    String invalidEmail4 = "Sorta@works.coom";
    String invalidEmail5 = "weird@@not.com";


    String validPassword = "123456";
    String invalidPassword = "12345";

    @Test
    public void testValidEmail()
    {
        ArrayList<String> validEmails = new ArrayList<>();
        validEmails.add( validEmail1 );
        validEmails.add( validEmail2 );
        validEmails.add( validEmail3 );
        validEmails.add( validEmail4 );
        validEmails.add( validEmail5 );

        ArrayList<String> invalidEmails = new ArrayList<>();
        invalidEmails.add( invalidEmail1 );
        invalidEmails.add( invalidEmail2 );
        invalidEmails.add( invalidEmail3 );
        invalidEmails.add( invalidEmail4 );
        invalidEmails.add( invalidEmail5 );

        for ( String vEmail : validEmails )
        {
            Assert.assertTrue( Util.isEmailValid( vEmail ) );
        }

        for ( String ivEmail : invalidEmails )
        {
            Assert.assertFalse( Util.isEmailValid( ivEmail ) );
        }

    }

    @Test
    public void testValidPassword()
    {
        Assert.assertTrue( Util.isPasswordValid( validPassword ) );
        Assert.assertFalse( Util.isPasswordValid( invalidPassword ) );

    }







}
