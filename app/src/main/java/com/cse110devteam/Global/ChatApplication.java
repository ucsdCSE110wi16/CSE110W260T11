package com.cse110devteam.Global;

import android.app.Application;
import android.graphics.Typeface;
import android.util.Log;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

/**
 * Created by anthonyaltieri on 2/14/16.
 */
public class ChatApplication  extends Application{
    public static String TEST_URI_EMULATOR = "http://10.0.2.2:3620/";
    public static String TEST_URI_DEVICE = "http://localhost:3620/";
    public static String CHAT_SERVER_BASIC = "http://52.32.198.121:8080";


    private Socket socket;
    {
        try{
            socket = IO.socket(CHAT_SERVER_BASIC);
            Log.d("Socket Set To:", CHAT_SERVER_BASIC);
        } catch (URISyntaxException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Socket getSocket(){
        return socket;
    }
}
