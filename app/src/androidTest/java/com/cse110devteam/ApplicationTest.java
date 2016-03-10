package com.cse110devteam;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.cse110devteam.Fragment.ChatFragment;
import com.cse110devteam.Global.ChatApplication;
import com.github.nkzawa.socketio.client.Socket;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }


}