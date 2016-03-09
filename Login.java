package com.cse110devteam.Activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cse110devteam.Global.ChatApplication;
import com.cse110devteam.Global.TypefaceGenerator;
import com.cse110devteam.R;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Manager;
import com.github.nkzawa.socketio.client.Socket;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * Created by anthonyaltieri on 1/14/16.
 */
public class Login extends Activity{
    private EditText email, password;
    private Button login;
    private Button createaccount;
    private Button attribution;
    private Socket mSocket;
    private String mUsername;
    private TextView header;

    ProgressDialog loginPD = null;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        // Get the Chat Application
        ChatApplication chatApp = (ChatApplication) getApplication();
        mSocket = chatApp.getSocket();

        setContentView(R.layout.login);
        header = (TextView) findViewById(R.id.header);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        createaccount = (Button) findViewById(R.id.createaccount);
        attribution = (Button) findViewById(R.id.attribution);

        email.setTypeface(TypefaceGenerator.get("roboto", getAssets()));
        password.setTypeface(TypefaceGenerator.get("roboto", getAssets()));
        login.setTypeface(TypefaceGenerator.get("robotoMedium", getAssets()));
        createaccount.setTypeface(TypefaceGenerator.get("robotoMedium", getAssets()));

        /* BUTTON ONCLICK LISTENERS */
        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                loginPD = new ProgressDialog( Login.this );
                loginPD.setTitle("Logging In");
                loginPD.setMessage("Please wait while you are logged in...");
                loginPD.setIndeterminate(true);
                loginPD.setCancelable(false);
                loginPD.show();

                Thread loginThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // Email is not case-sensitive
                        final String textEmail = email.getText().toString().toLowerCase().trim();
                        String textPassword = password.getText().toString();
                        boolean validEmail = false;
                        boolean validPassword = false;

                        // Safe guard login prompt
                        if(textEmail.length() == 0 && textPassword.length() == 0) {
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    "Email and password required", Toast.LENGTH_LONG);
                                    toast.show();
                        }
                        if(textEmail.length() > 0){
                            validEmail = true;
                        } else {
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    "Enter an Email!", Toast.LENGTH_LONG);
                            email.setText("");
                        }
                        if(textPassword.length() > 0){
                            validPassword = true;
                        } else {
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    "Enter a password", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.BOTTOM, 0, 0);
                            toast.show();
                            password.setText("");
                        }
                        if(validEmail && validPassword){
                            Toast toast;
                            boolean emailInSystem = emailInSystem(textEmail);
                            if(!emailInSystem){
                                toast = Toast.makeText(getApplicationContext(),
                                        "Email not in registered!",
                                        Toast.LENGTH_LONG);
                                toast.show();
                                return;
                            }

                            ParseUser.logInInBackground(textEmail, textPassword,
                                    new LogInCallback() {
                                        @Override
                                        public void done(final ParseUser user, ParseException e) {
                                            if(user != null){
                                                final ParseUser pU = ParseUser.getCurrentUser();
                                                mUsername = pU.get("firsname") + " " + pU.get("lastname");
                                                // Add user to chat server
                                                mSocket.emit("add user", mUsername);

                                                if(user.getBoolean("ismanager")){
                                                    Intent intent = new Intent(getApplicationContext(),
                                                            ManagerMain.class);
                                                    startActivity(intent);
                                                    loginPD.dismiss();
                                                } else {
                                                    Intent intent = new Intent(getApplicationContext(),
                                                            EmployeeMain.class);
                                                    startActivity(intent);
                                                    loginPD.dismiss();
                                                }

                                            } else {
                                                Toast noPassToast;
                                                if(e.getCode() == ParseException.OBJECT_NOT_FOUND){
                                                    noPassToast = Toast.makeText(getApplicationContext(),
                                                            "Password Incorrect!", Toast.LENGTH_LONG);
                                                    noPassToast.setGravity(Gravity.BOTTOM, 0, 0);
                                                    noPassToast.show();
                                                    loginPD.dismiss();
                                                } else {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                    });
                        }

                    }
                });
                loginThread.start();

            }
        });

        createaccount.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), CreateAccountUserType.class);
                startActivity(intent);
            }
        });

        attribution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Attribution.class);
                startActivity(intent);
            }
        });


        mSocket.on("login", onLogin);

    }

    private Emitter.Listener onLogin = new Emitter.Listener(){
        @Override
        public void call(Object... args) {
            JSONObject data = (JSONObject) args[0];
            int numUsers;
            try {
                numUsers = data.getInt("numUsers");
            } catch (JSONException e){
                return;
            }
        }
    };

    private boolean emailInSystem(String username){
        ParseQuery<ParseUser> userList = ParseUser.getQuery();
        userList.whereEqualTo("username", username);
        try{
            return (userList.count() != 0);

        } catch (ParseException e) {
            return false;
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if ( loginPD == null ) return;
        loginPD.dismiss();
    }
}
