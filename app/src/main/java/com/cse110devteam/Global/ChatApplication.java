package com.cse110devteam.Global;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseInstallation;

import java.net.URISyntaxException;

/**
 * Created by anthonyaltieri on 2/14/16.
 */
public class ChatApplication  extends Application{
    private static ChatApplication instance;
    public static String TEST_URI_EMULATOR = "http://10.0.2.2:3620/";
    public static String TEST_URI_DEVICE = "http://localhost:3620/";
    public static String CHAT_SERVER_BASIC = "http://52.32.198.121:8080";
    public boolean loadedChat = false;


    private Socket socket;
    {
        try{
            socket = IO.socket(CHAT_SERVER_BASIC);
        } catch (URISyntaxException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void onCreate() {
        super.onCreate();
        instance = this;
        Parse.initialize(this);
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }

    public Socket getSocket(){
        return socket;
    }
    public static ChatApplication getInstance() {return instance;}
    public static Context getContext(){return instance.getApplicationContext();}



}
