package com.cse110devteam.Fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.cse110devteam.Global.ChatApplication;
import com.cse110devteam.Global.Message;
import com.cse110devteam.Global.MessageAdapter;
import com.cse110devteam.R;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by anthonyaltieri on 2/15/16.
 */
public class ChatFragment extends android.support.v4.app.Fragment{
    private Socket socket;

    private String username;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<Message> mMessages = new ArrayList<Message>();

    private EditText input;
    private ImageButton send;

    private ParseUser user;

    private String businessId;


    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);

        // Get the socket associated with the chat application
        ChatApplication chatApp = (ChatApplication) getActivity().getApplication();
        socket = chatApp.getSocket();
        socket.on("new message", onNewMessage);
        socket.on("user joined", onUserJoined);
        socket.on("user left", onUserLeft);
        socket.connect();

        user = ParseUser.getCurrentUser();
        username = (String) user.get("firstname");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle){
        return inflater.inflate(R.layout.chat, container, false);
    }

    @Override
    public void onViewCreated(View v, Bundle bundle){
        super.onViewCreated(v, bundle);
        mAdapter = new MessageAdapter(getActivity().getApplicationContext(), mMessages);
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);

        input = (EditText) getActivity().findViewById(R.id.inputMessage);
        send = (ImageButton) getActivity().findViewById(R.id.send_button);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("send.OnClick", "Inside method");
                // If no connection, immediately return
                if(!socket.connected()){
                    Log.d("SOCKET ERROR:", "Socket not connected");
                    return;
                }

                String message = input.getText().toString();
                // If message is length 0 after trim return
                if(message.trim().length() == 0) return;

                /** json is a JSON object that contains the following keys
                 *  message - The message that is being recieved
                 *  username - The username of the sender
                 *  businessId - The object id for the ParseObject business this
                 *             chat is associated with
                 *  userId - The object id for the ParseUser sending the message
                 *  time - A JSON object with the following keys:
                 *          isAM - boolean, true if it is in the AM
                 *          hour - current hour in 12 hour am/pm format
                 *          minute - current minute
                 */
                JSONObject json = new JSONObject();
                try {
                    json.put("message", message);
                    json.put("username", username);
                    ParseObject business = (ParseObject) user.get("business");
                    businessId = business.getObjectId();
                    json.put("businessId", businessId);
                    json.put("userId", user.get("objectId"));

                    Date date = new Date();
                    JSONObject time = new JSONObject();
                    int hours = date.getHours();
                    time.put("isAM", hours < 12);
                    time.put("hour", ++hours % 12);
                    time.put("minute", date.getMinutes());

                    json.put("time", time);
                } catch (JSONException e) {
                    Log.d("JSONException", e.toString());
                    e.printStackTrace();
                }

                try {
                    socket.emit("new message", json);
                } catch (Error e){
                    Log.d("ERROR new message: ", e.toString());
                    e.printStackTrace();
                }
                input.setText("");
            }
        });
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        socket.disconnect();
        socket.off("new message:" + businessId, onNewMessage);
        socket.off("user left", onUserLeft);
        socket.off("user joined", onUserJoined);
    }

    private void addMessage(String username, String message){
        int type = (username.equals(this.username))
                ? Message.TYPE_MESSAGE_SELF : Message.TYPE_MESSAGE_OTHER;
        mMessages.add(new Message.Builder(type)
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
                    ParseObject chatMain = user.getParseObject("chatMain");
                    try{
                        username = data.getString("username");
                        numUsers = data.getInt("numUsers");
                        JSONObject[] chatLog = (JSONObject[]) user.get("log");
                        for (JSONObject json : chatLog) {
                            addMessage((String) json.get("username"), (String) json.get("message"));
                        }
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
                    Log.d("onNewMessage", "inside method");
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
