package com.cse110devteam.Global;

import android.content.Intent;

import com.cse110devteam.Activity.Login;
import com.parse.ParseUser;

/**
 * Created by Vidur on 3/8/2016.
 */
public class util {

    public static void logOut() {
        Thread logoutThread = new Thread(new Runnable() {
            @Override
            public void run() {
                ParseUser.logOut();
                Intent logOutIntent = new Intent(ChatApplication.getContext(), Login.class);
            }
        });
    }
}
