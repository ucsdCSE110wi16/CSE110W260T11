package com.cse110devteam.Fragment;


import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListAdapter;

import com.cse110devteam.R;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by anthonyaltieri on 1/16/16.
 */
public class ManMessaging extends android.support.v4.app.Fragment {
    private EditText inMsg;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSocket.on("new message", onNewMessage);
        mSocket.connect();
        inMsg = (EditText) getActivity().findViewById(R.id.inputMessage);
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.recyclerview);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.manager_messaging, container, false);
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    // Socket.io setup
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://chat.socket.io");
        } catch (URISyntaxException e){}
    }

    // Socket.io Emission/Listening
    private void attemptSend(){
        String message = inMsg.getText().toString().trim();
        if (TextUtils.isEmpty(message)){
            return;
        }
        inMsg.setText("");
        mSocket.emit("new message", message);
    }

    private Emitter.Listener onNewMessage = new Emitter.Listener(){
        @Override
        public void call(final Object... args){
            getActivity().runOnUiThread(new Runnable(){
                @Override
                public void run(){
                    JSONObject data = (JSONObject) args[0];
                    String username;
                    String message;
                    try{
                        username = data.getString("username");
                        message = data.getString("message");
                    } catch (JSONException e){
                        return;
                    }

                    HashMap<String, String> msgMap = new HashMap<String, String>(2);
                    msgMap.put("msg", message);
                    msgMap.put("username", username);
                    JSONObject msgObj = new JSONObject(msgMap);
                }
            });
        }
    };
}

