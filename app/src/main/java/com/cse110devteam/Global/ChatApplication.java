package com.cse110devteam.Global;

import android.app.Application;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

/**
 * Created by anthonyaltieri on 2/14/16.
 */
public class ChatApplication  extends Application{
    public static String TEST_URI_EMULATOR = "http://10.0.2.2:3000/";
    public static String TEST_URI_DEVICE = "http://localhost:3000/";


    private Socket socket;
    {
        try{
            socket = IO.socket(TEST_URI_EMULATOR);
        } catch (URISyntaxException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Socket getSocket(){
        return socket;
    }
}
