package com.cse110devteam.Fragment;

import android.content.res.AssetManager;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cse110devteam.Global.ChatApplication;
import com.cse110devteam.Models.Message;
import com.cse110devteam.Global.MessageAdapter;
import com.cse110devteam.Global.TypefaceGenerator;
import com.cse110devteam.Global.Util;
import com.cse110devteam.R;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

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
    private VerticalSpaceItemDeocration mItemDecoration;
    private ArrayList<ParseObject> log = null;

    RelativeLayout containerChatLoading;
    TextView chatLoading;

    private EditText input;
    private ImageButton send;

    private ParseUser user;
    private ParseObject business;
    private ParseObject chatMain;

    private String businessId;

    private Typeface roboto;
    private Typeface robotoBlack;
    private Typeface robotoMedium;
    private String newMessageSignature;

    private boolean logLoaded;

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);

        // Get the socket associated with the chat application
        ChatApplication chatApp = (ChatApplication) getActivity().getApplication();
        socket = chatApp.getSocket();

        user = ParseUser.getCurrentUser();
        username = (String) user.get("firstname");

        business = (ParseObject) user.get("business");
        if ( business != null )
        {
            String businessId = business.getObjectId();
            newMessageSignature = "new message:" + businessId;
            socket.on( newMessageSignature, onNewMessage);
        }


        socket.on("user joined", onUserJoined);
        socket.on("user left", onUserLeft);
        socket.connect();


        AssetManager assestManager = getActivity().getAssets();
        roboto = TypefaceGenerator.get( "roboto", assestManager );
        robotoBlack = TypefaceGenerator.get( "robotoBlack", assestManager );
        robotoMedium = TypefaceGenerator.get( "robotoMedium", assestManager );
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
        mItemDecoration = new VerticalSpaceItemDeocration( 40 );
        mRecyclerView.addItemDecoration(mItemDecoration);

        chatMain = (ParseObject) user.get("chatMain");

        Log.v("chatMain", chatMain.toString() );

        chatLoading = ( TextView ) v.findViewById( R.id.chatloading );
        containerChatLoading = ( RelativeLayout ) v.findViewById( R.id.container_chatloading );

        mRecyclerView.setVisibility( View.GONE );

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (chatMain != null) {
                    if (log == null) {
                        try {
                            log = (ArrayList<ParseObject>) chatMain.fetchIfNeeded().get("log");
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    fillChat(log);

                }
                containerChatLoading.setVisibility(View.GONE);
                chatLoading.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
            }
        });


        input = (EditText) getActivity().findViewById(R.id.inputMessage);
        send = (ImageButton) getActivity().findViewById(R.id.send_button);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (business == null) {
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                            ("You are not a part of a business! Have your manager"
                                    + " invite you to start chatting."), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.show();
                } else {
                    Log.d("send.OnClick", "Inside method");
                    // If no connection, immediately return
                    if (!socket.connected()) {
                        Log.d("SOCKET ERROR:", "Socket not connected");
                        return;
                    }

                    String message = input.getText().toString();
                    // If message is length 0 after trim return
                    if (message.trim().length() == 0) return;

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
                        json.put("userId", user.getObjectId());

                        Date date = new Date();
                        String timeString = Util.prettyHourMin(date);

                        json.put("time", timeString);
                    } catch (JSONException e) {
                        Log.d("JSONException", e.toString());
                        e.printStackTrace();
                    }

                    try {
                        socket.emit("new message", json);
                    } catch (Error e) {
                        Log.d("ERROR new message: ", e.toString());
                        e.printStackTrace();
                    }
                    input.setText("");

                    Date currentDate = new Date();
                    int hours = (currentDate.getHours() + 1) % 12;
                    int minutes = currentDate.getMinutes();
                    boolean isPm = (hours < 12);
                    String ampm = (isPm) ? "pm" : "am";
                    String timeString = "" + hours + ":" + minutes + " " + ampm;


                    final ParseObject chatMain = (ParseObject) user.get("chatMain");
                    final ParseObject messageObject = new ParseObject("Message");
                    messageObject.put("message", message);
                    messageObject.put("username", username);
                    messageObject.put("time", timeString);
                    messageObject.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            chatMain.add("log", messageObject);
                            chatMain.saveInBackground();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        socket.disconnect();
        socket.off(newMessageSignature, onNewMessage);
        socket.off("user left", onUserLeft);
        socket.off("user joined", onUserJoined);
    }


    private void addMessage(String username, String message, String time){
        int type = ( username.equals( this.username ) )
                ? Message.TYPE_MESSAGE_SELF : Message.TYPE_MESSAGE_OTHER;
        mMessages.add(new Message.Builder(type)
                .username(username)
                .message(message)
                .time(time)
                .typefaceMessage(roboto)
                .typefaceTime(robotoMedium)
                .typefaceUsername(robotoBlack)
                .build());
        mAdapter.notifyItemInserted( mMessages.size() - 1 );
        scrollToBottom();
    }

    private void fillChat( ArrayList<ParseObject> log ) {
        if ( log == null ) return;

        for ( int i = 0 ; i < log.size() ; i++ )
        {
            ParseObject msg = log.get( i );
            Log.d( "log.get(" + i + ") classname", msg.getClassName() );
            Log.d( "log.get(" + i + ") oid", msg.getObjectId() );
            try{
                String message = msg.fetchIfNeeded().getString("message");
                String usrname = msg.fetchIfNeeded().getString("username");
                String time = msg.fetchIfNeeded().getString("time");
                addMessage(usrname, message, time);

            } catch (ParseException pe){
                pe.printStackTrace();
            }
        }
    }

    public class VerticalSpaceItemDeocration extends RecyclerView.ItemDecoration {

        private final int mSpaceHeight;

        public VerticalSpaceItemDeocration(int mSpaceHeight) {
            this.mSpaceHeight = mSpaceHeight;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            outRect.bottom = mSpaceHeight;
        }
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

                        Toast userJoinedToast =
                                Toast.makeText(getActivity().getApplicationContext(),
                                        ( username + " joined!" ), Toast.LENGTH_LONG);
                        userJoinedToast.setGravity(Gravity.BOTTOM, 0, 0);
                        userJoinedToast.show();
                    } catch (JSONException e){
                        e.printStackTrace();
                    }

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
                    String time;
                    try{
                        username = data.getString("username");
                        message = data.getString("message");
                        time = data.getString("time");
                    } catch (JSONException e){
                        // Silently fail and log
                        Log.d("Error new Message:", e.toString());
                        return;
                    }
                    addMessage(username, message, time);
                }
            });
        }
    };
}
