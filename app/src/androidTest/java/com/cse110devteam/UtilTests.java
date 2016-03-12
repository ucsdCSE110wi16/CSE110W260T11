package com.cse110devteam;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.cse110devteam.Global.Util;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

/**
 * Created by anthonyaltieri on 3/11/16.
 */
@RunWith(AndroidJUnit4.class)
public class UtilTests {

    @Test
    public void testPrettyHourMin()
    {
        Date date = new Date();
        date.setHours(13);
        date.setMinutes(30);

        String first = Util.prettyHourMin( date );

        Log.d( "1st", first );
        Assert.assertTrue(first.equals("1:30 pm"));

        date.setHours(0);
        date.setMinutes(58);

        String second = Util.prettyHourMin( date );
        Log.d( "2nd", second );

        Assert.assertTrue(second.equals("12:58 am"));

        date.setHours(12);
        date.setMinutes( 40 );

        String third = Util.prettyHourMin( date );
        Log.d( "3rd", third );

        Assert.assertTrue( third.equals( "12:40 pm") );

    }


}
