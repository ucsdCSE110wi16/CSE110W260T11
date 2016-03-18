package com.cse110devteam.Activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cse110devteam.Fragment.ForgotPassDialog;
import com.cse110devteam.Global.ChatApplication;
import com.cse110devteam.Global.TypefaceGenerator;
import com.cse110devteam.Global.Util;
import com.cse110devteam.R;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by anthonyaltieri on 1/14/16.
 */
public class Login extends Activity{
    private EditText email, password;
    private Button login;
    private Button forgotPassword;
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

        ParseUser.getCurrentUser().logOutInBackground();

        setContentView(R.layout.login);
        header = (TextView) findViewById(R.id.header);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        forgotPassword = (Button) findViewById(R.id.forgotpass);
        login = (Button) findViewById(R.id.login);
        createaccount = (Button) findViewById(R.id.createaccount);
        attribution = (Button) findViewById(R.id.attribution);

        email.setTypeface(TypefaceGenerator.get("roboto", getAssets()));
        password.setTypeface(TypefaceGenerator.get("roboto", getAssets()));
        login.setTypeface(TypefaceGenerator.get("robotoMedium", getAssets()));
        createaccount.setTypeface(TypefaceGenerator.get("robotoMedium", getAssets()));

        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                loginPD = new ProgressDialog(Login.this);
                loginPD.setTitle("Logging In");
                loginPD.setMessage("Please wait while you are logged in...");
                loginPD.setIndeterminate(true);
                loginPD.setCancelable(false);
                loginPD.show();


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        // Email is not case-sensitive
                        final String textEmail = email.getText().toString().toLowerCase().trim();
                        String textPassword = password.getText().toString();
                        boolean validEmail = Util.isEmailValid(textEmail);
                        boolean validPassword = Util.isPasswordValid(textPassword);
                        if (!validEmail)
                        {
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    "Enter an Email!", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.BOTTOM, 0, 0);
                            if (loginPD != null) loginPD.dismiss();
                            toast.show();
                            email.setText("");
                        }
                        if (!validPassword)
                        {
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    "Enter a valid password", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.BOTTOM, 0, 0);
                            if (loginPD != null) loginPD.dismiss();
                            toast.show();
                            password.setText("");
                        }
                        if (validEmail && validPassword)
                        {
                            Toast toast;
                            boolean emailInSystem = emailInSystem(textEmail);
                            if (!emailInSystem)
                            {
                                toast = Toast.makeText(getApplicationContext(),
                                        "Email not in registered!",
                                        Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.BOTTOM, 0, 0);
                                if (loginPD != null) loginPD.dismiss();
                                toast.show();
                                return;
                            }

                            ChatApplication chatApplication = (ChatApplication) getApplication();
                            chatApplication.loadedChat = false;

                            ParseUser.logInInBackground(textEmail, textPassword, new LogInCallback()
                            {
                                @Override
                                public void done(final ParseUser user, ParseException e)
                                {
                                    if (user != null)
                                    {
                                        final ParseUser pU = ParseUser.getCurrentUser();
                                        mUsername = pU.get("firsname") + " " + pU.get("lastname");
                                        // Add user to chat server
                                        mSocket.emit("add user", mUsername);

                                        if (user.getBoolean("ismanager"))
                                        {
                                            Intent intent = new Intent(getApplicationContext(),
                                                    ManagerMain.class);
                                            startActivity(intent);
                                        }
                                        else
                                        {
                                            Intent intent = new Intent(getApplicationContext(),
                                                    EmployeeMain.class);
                                            startActivity(intent);
                                            loginPD.dismiss();
                                        }
                                    }
                                    else
                                    {
                                        Toast noPassToast;
                                        if (e.getCode() == ParseException.OBJECT_NOT_FOUND)
                                        {
                                            noPassToast = Toast.makeText(getApplicationContext(),
                                                    "Password Incorrect!", Toast.LENGTH_SHORT);
                                            noPassToast.setGravity(Gravity.BOTTOM, 0, 0);
                                            loginPD.dismiss();
                                            noPassToast.show();
                                        }
                                        else
                                        {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            });
                        }

                    }
                });

            }
        });

        createaccount.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(), CreateAccountUserType.class);
                        startActivity(intent);

                    }
                });
                thread.start();
            }
        });

        attribution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(), Attribution.class);
                        startActivity(intent);

                    }
                });
                thread.start();
            }
        });


        forgotPassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FragmentManager fragmentManager = getFragmentManager();
                ForgotPassDialog forgotPassDialog = new ForgotPassDialog();
                forgotPassDialog.show(fragmentManager, "forgotpass_fragment");

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

    public static boolean emailInSystem(String username){
        username = username.toLowerCase();
        ParseQuery<ParseUser> userList = ParseUser.getQuery();
        Log.d("EmailInSystem", "username: " + username );
        userList.whereEqualTo("username", username);
        try {
            Log.d("userList.count()", "" + userList.count() );
        } catch (ParseException e) {
            e.printStackTrace();
            Log.d("ParseException", e.toString() );
        }
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
