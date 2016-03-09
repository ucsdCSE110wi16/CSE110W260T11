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

    @Test
    public void testSendMessage(){
        ParseQuery<ParseUser> query = ParseQuery.getQuery("Parseuser");
        query.whereEqualTo( "username", "m@m.com" );
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> list, ParseException e) {
                if ( e == null )
                {
                    // Get the user queried
                    ParseUser user = list.get( 0 );
                    ChatFragment chatFragment = new ChatFragment();
                    JSONObject json = new JSONObject();
                    try {
                        json.put("message", "Test Message");
                        json.put("username", user.getString( "firstname" ));
                        ParseObject business = (ParseObject) user.get("business");
                        String businessId = business.getObjectId();
                        json.put("businessId", businessId);
                        json.put("userId", user.getObjectId());

                        Date date = new Date();
                        int hours = (date.getHours()) % 12;
                        int minutes = date.getMinutes();
                        boolean isPm = (hours < 12);
                        String ampm = (isPm) ? "pm" : "am";
                        String timeString = "" + hours + ":" + minutes + " " + ampm;

                        json.put("time", timeString);
                    } catch (JSONException err) {
                        Log.d("JSONException", e.toString());
                        e.printStackTrace();
                    }

                    ChatApplication chatapp = new ChatApplication();
                    Socket socket = chatapp.getSocket();

                    try {

                        socket.emit("new message", json);
                    } catch (Error errEmit) {
                        Log.d("ERROR new message: ", e.toString());
                        e.printStackTrace();
                    }

                    Date currentDate = new Date();
                    int hours = (currentDate.getHours() + 1) % 12;
                    int minutes = currentDate.getMinutes();
                    boolean isPm = (hours < 12);
                    String ampm = (isPm) ? "pm" : "am";
                    String timeString = "" + hours + ":" + minutes + " " + ampm;


                    final ParseObject chatMain = (ParseObject) user.get("chatMain");
                    final ParseObject messageObject = new ParseObject("Message");
                    messageObject.put("message", "Test Message");
                    messageObject.put("username", "m@m.com");
                    messageObject.put("time", timeString);
                    messageObject.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            final ArrayList<ParseObject> oldLog =
                                    (ArrayList<ParseObject>) chatMain.get( "log" );
                            chatMain.add("log", messageObject);
                            final int oldLogSize = oldLog.size();
                            chatMain.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    ArrayList<ParseObject> log =
                                            (ArrayList<ParseObject>) chatMain.get("log");
                                    assertTrue( oldLogSize + 1 == log.size() );
                                    ParseObject message = log.get(log.size() - 1);
                                    String messageText = message.getString("message");
                                    assertEquals(true, messageText.equals("Test Message"));
                                    String usernameText = message.getString("username");
                                    assertEquals(true, usernameText.equals("m@m.com"));
                                    chatMain.put( "log", oldLog );
                                    chatMain.saveInBackground();
                                }
                            });

                        }
                    });

                }
                else
                {
                    e.printStackTrace();
                }
            }
        });
    }

}