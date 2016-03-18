package com.cse110devteam.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cse110devteam.Global.User;
import com.cse110devteam.R;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by anthonyaltieri on 1/26/16.
 */
public class CreateBusinessPage extends Activity{
    private TextView header;
    private TextView subheader;
    private EditText businessName;
    private Button create;

    private ParseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_business_page);

        header = (TextView) findViewById(R.id.header);
        subheader = (TextView) findViewById(R.id.subheader);
        businessName = (EditText) findViewById(R.id.businessname);
        create = (Button) findViewById(R.id.create);

        user = ParseUser.getCurrentUser();
        // TODO: deal with user == null in a more elegant way
        if(user == null) {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        }


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean validName = false;
                // businessName is not case-sensitive
                String textName = businessName.getText().toString().trim();
                if (textName.length() > 0) {
                    validName = true;
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Enter a business name!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.show();
                }
                if (validName) createBusiness(textName, user);
            }
        });
    }

    private void createBusiness(String name, final ParseObject mainManager){
        final ParseObject business = new ParseObject("Business");
        ParseACL acl = new ParseACL();
        acl.setPublicReadAccess( true );
        acl.setPublicWriteAccess( true );
        business.setACL( acl );
        business.put("mainManager", mainManager);
        business.put("name", name);
        mainManager.put("business", business);
        mainManager.put("businessName", name);
        business.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    createBusinessChat(business, mainManager);
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    private void createBusinessChat(final ParseObject business, final ParseObject mainManger){
        final ParseObject businessChat = new ParseObject("BusinessChat");
        ParseACL acl = new ParseACL();
        acl.setPublicReadAccess( true );
        acl.setPublicWriteAccess( true );
        businessChat.setACL( acl );
        businessChat.put("business", business);
        business.put("chat", businessChat);
        business.saveInBackground();
        businessChat.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null){
                    createChatRoom(businessChat, mainManger);
                } else {
                    e.printStackTrace();
                }
            }
        });
        businessChat.put("business", business);
        business.put("chat", businessChat);
    }

    private void createChatRoom(final ParseObject businessChat, final ParseObject mainManager){
        final ParseObject chatRoom = new ParseObject("ChatRoom");
        ParseACL acl = new ParseACL();
        acl.setPublicReadAccess( true );
        acl.setPublicWriteAccess( true );
        chatRoom.setACL( acl );
        chatRoom.put("type", "main");
        ArrayList<ParseObject> rooms = new ArrayList<ParseObject>();
        rooms.add( chatRoom );
        businessChat.put("rooms", rooms);
        ArrayList<JSONObject> log = new ArrayList<JSONObject>();
        // Put an empty array of jsonobjects into log
        chatRoom.put("log", log);
        Log.d("chatRoom", chatRoom.toString());
        chatRoom.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null){
                    mainManager.put("chatMain", chatRoom);
                    mainManager.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Intent intent = new Intent(getApplicationContext(), ManagerMain.class);
                                startActivity(intent);
                            } else {
                                e.printStackTrace();
                            }


                        }
                    });
                } else {
                    e.printStackTrace();
                }
            }
        });
    }



}
