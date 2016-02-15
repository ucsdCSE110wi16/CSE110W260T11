package com.cse110devteam.Fragment;


import android.app.Activity;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Layout;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.cse110devteam.Global.ChatApplication;
import com.cse110devteam.Global.Message;
import com.cse110devteam.Global.MessageAdapter;
import com.cse110devteam.R;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.parse.ParseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by anthonyaltieri on 1/16/16.
 */
public class ManMessaging extends android.support.v4.app.Fragment {
    private RecyclerView mRecyclerView;
    private List<Message> mMessages = new ArrayList<Message>();
    private RecyclerView.Adapter mAdapter;
    Socket socket;
    private String username;
    private EditText inputMessage;
    private ImageButton send;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the socket associated with the chat application
        ChatApplication chatApp = (ChatApplication) getActivity().getApplication();
        socket = chatApp.getSocket();
        socket.on("new message", onNewMessage);
        socket.on("user joined", onUserJoined);
        socket.on("user left", onUserLeft);
        socket.connect();

        username = ParseUser.getCurrentUser().getString("firstname");
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        mAdapter = new MessageAdapter(activity, mMessages);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.manager_messaging, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);

        inputMessage = (EditText) view.findViewById(R.id.inputMessage);
        send = (ImageButton) view.findViewById(R.id.send_button);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If no connection, immediately return
                if(!socket.connected()) return;

                String message = inputMessage.getText().toString();
                // If message is length 0 after trim return
                if(message.trim().length() == 0) return;

                JSONObject json = new JSONObject();
                try {
                    /** json is a JSON object that contains the following keys
                     *  message - The message that is being recieved
                     *  username - The username of the sender
                     *  time - A JSON object with the following keys:
                     *          isAM - boolean, true if it is in the AM
                     *          hour - current hour in 12 hour am/pm format
                     *          minute - current minute
                     */
                    json.put("message", message);
                    json.put("username", username);
                    Date date = new Date();
                    JSONObject time = new JSONObject();
                    int hours = date.getHours();
                    time.put("isAM", hours < 12);
                    time.put("hour", ++hours % 12);
                    time.put("minute", date.getMinutes());
                    json.put("time", time);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    socket.emit("new message", json);
                } catch (Error e){
                    Log.d("ERROR new message: ", e.toString());
                    e.printStackTrace();
                }
                inputMessage.setText("");
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        socket.disconnect();
        socket.off("new message", onNewMessage);
        socket.off("user left", onUserLeft);
        socket.off("user joined", onUserJoined);
    }


    private void addMessage(String username, String message){
        mMessages.add(new Message.Builder()
                .username(username)
                .message(message)
                .build());
        mAdapter.notifyItemInserted(mMessages.size() - 1);
        scrollToBottom();
    }

    private void scrollToBottom(){
        mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
    }

    private Emitter.Listener onUserJoined = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String username;
                    int numUsers;
                    try{
                        username = data.getString("username");
                        numUsers = data.getInt("numUsers");
                    } catch (JSONException e){
                        // Silently fail
                        return;
                    }


                    // TODO: Keep a list of users in chat
                }
            });
        }
    };

    private Emitter.Listener onUserLeft = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String username;
                    int numUsers;
                    try {
                        username = data.getString("username");
                        numUsers = data.getInt("numUsers");
                    } catch (JSONException e) {
                        return;
                    }
                    // TODO: Update list of users in chat
                }
            });
        }
    };

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable(){
                @Override
                public void run() {
                    // TODO: Add support for time
                    JSONObject data = (JSONObject) args[0];
                    String username;
                    String message;
                    try{
                        username = data.getString("username");
                        message = data.getString("message");
                    } catch (JSONException e){
                        // Silently fail and log
                        Log.d("Error new Message:", e.toString());
                        return;
                    }
                    addMessage(username, message);
                }
            });
        }
    };
}

