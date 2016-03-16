package com.cse110devteam.Global;

import android.content.Intent;
import android.util.Log;

import com.cse110devteam.Activity.Login;
import com.parse.ParseUser;

import java.util.Date;
import java.util.regex.Pattern;

/**
 * Created by Vidur on 3/8/2016.
 */
public class Util {

    public static void logOut() {
        Thread logoutThread = new Thread(new Runnable() {
            @Override
            public void run() {
                ParseUser.logOut();
                Intent logOutIntent = new Intent(ChatApplication.getContext(), Login.class);
            }
        });
    }

    public static boolean isEmailValid(String email)
    {
        if ( email.length() == 0 ) return false;
        String[] twoPartEmail = email.split( "@" );
        if ( twoPartEmail.length != 2 ) return false;
        String secondPart = twoPartEmail[ 1 ];
        String[] rhs = secondPart.split( Pattern.quote(".") );
        if ( rhs.length != 2 ) return false;

        String[] validEndings = { "com", "org", "biz", "net", "edu", "gov" };
        boolean validEnding = false;
        for (String ending : validEndings)
        {
            if ( rhs[ 1 ].equals( ending ) )
            {
                validEnding = true;
            }

        }

        return validEnding;

    }

    public static String prettyHourMin( Date date )
    {
        int minutes = date.getMinutes();
        String sMin = ( minutes == 0 ) ? "00" : ( "" + minutes );

        int hours = date.getHours();
        boolean isPm = (hours >= 12);
        hours = hours % 12;
        if ( hours == 0 ) hours = 12;
        String ampm = (isPm) ? "pm" : "am";
        String timeString = "" + hours + ":" + sMin + " " + ampm;
        return timeString;
    }

    public static boolean isPasswordValid(String password)
    {
        return password.length() >= 6;
    }

    public static String firstLetterCapitalize( String string )
    {
        if (string == null || string.length() == 0) {
            return string;
        }
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }
}
