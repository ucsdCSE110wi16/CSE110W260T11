package com.cse110devteam.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cse110devteam.Global.TypefaceGenerator;
import com.cse110devteam.Global.Util;
import com.cse110devteam.R;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.zip.Inflater;

/**
 * Created by anthonyaltieri on 1/14/16.
 */
public class CreateAccount extends Activity {
    private EditText email, fname, lname, password1, password2;
    private Button submit, cancel;
    private boolean isManagerAccount;

    ProgressDialog caPD = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isManagerAccount = getIntent().getExtras().getBoolean("isManager");
        setContentView(R.layout.create_account);

        email = (EditText) findViewById(R.id.email);
        fname = (EditText) findViewById(R.id.fname);
        lname = (EditText) findViewById(R.id.lname);
        password1 = (EditText) findViewById(R.id.password1);
        password2 = (EditText) findViewById(R.id.password2);
        email.setTypeface(TypefaceGenerator.get("roboto", getAssets()));
        fname.setTypeface(TypefaceGenerator.get("roboto", getAssets()));
        lname.setTypeface(TypefaceGenerator.get("roboto", getAssets()));
        password1.setTypeface(TypefaceGenerator.get("roboto", getAssets()));
        password2.setTypeface(TypefaceGenerator.get("roboto", getAssets()));

        submit = (Button) findViewById(R.id.submit);
        cancel = (Button) findViewById(R.id.cancel);
        submit.setTypeface(TypefaceGenerator.get("robotoMedium", getAssets()));
        cancel.setTypeface(TypefaceGenerator.get("robotoMedium", getAssets()));


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                caPD = new ProgressDialog(CreateAccount.this);
                caPD.setTitle("Logging In");
                caPD.setMessage("Please wait while you are logged in...");
                caPD.setIndeterminate(true);
                caPD.setCancelable(false);
                caPD.show();


                String textFname = fname.getText().toString().toLowerCase().trim();
                String textLname = lname.getText().toString().toLowerCase().trim();
                String textPass1 = password1.getText().toString();
                String textPass2 = password2.getText().toString();
                String textEmail = email.getText().toString().toLowerCase().trim();

                boolean validEmail = Util.isEmailValid(textEmail);
                boolean validFname = false;
                boolean validLname = false;
                boolean validPass = false;

                Log.d("credential check", "about to begin");

                if (!validEmail) {
                    Log.d("credentials", "invalid email");
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Enter a valid email!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    if (caPD != null) caPD.dismiss();
                    toast.show();
                }
                else
                {
                    Log.d("credentials", "valid email");
                }
                if (!(Util.isPasswordValid(textPass1)
                        && textPass1.equals(textPass2))) {
                    Log.d("Password1Text", textPass1);
                    Log.d("Password2Text", textPass2);
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Passwords don't match!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    if (caPD != null) caPD.dismiss();
                    toast.show();
                    password1.setText("");
                    password2.setText("");
                }
                else
                {
                    Log.d("credentials", "valid password");
                    validPass = true;
                }
                if (textFname.length() > 0) {
                    Log.d("credentials", "valid first name");
                    validFname = true;
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Enter a First Name!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    if (caPD != null) caPD.dismiss();
                    toast.show();
                }
                if (textLname.length() > 0) {
                    Log.d("credentials", "valid last name");
                    validLname = true;
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Enter a Last Name!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    if (caPD != null) caPD.dismiss();
                    toast.show();
                }
                if (validEmail && validPass && validFname && validLname) {
                    ParseUser currentUser = ParseUser.getCurrentUser();
                    Log.d("new user", "valid credentials");

                    if (currentUser != null) currentUser.logOut();

                    ParseUser newUser = new ParseUser();

                    newUser.setUsername(textEmail);
                    newUser.put("firstname", textFname);
                    newUser.put("lastname", textLname);
                    newUser.put("ismanager", isManagerAccount);
                    newUser.setPassword(textPass1);
                    Log.d("newuser", "about to signUpInBackground");

                    newUser.signUpInBackground(new SignUpCallback() {
                        public void done(com.parse.ParseException e) {
                            if (e == null) {
                                Intent intent = new Intent(getApplicationContext(),
                                        Login.class);
                                startActivity(intent);
                                if (caPD != null) caPD.dismiss();
                            } else {
                                e.printStackTrace();

                                Toast toast;
                                switch (e.getCode()) {

                                    case com.parse.ParseException.ACCOUNT_ALREADY_LINKED:
                                        toast = Toast.makeText(getApplicationContext(),
                                                "Email already used, try another one.",
                                                Toast.LENGTH_LONG);
                                        toast.setGravity(Gravity.BOTTOM, 0, 0);
                                        if (caPD != null) caPD.dismiss();
                                        toast.show();
                                        email.setText("");

                                    case com.parse.ParseException.USERNAME_TAKEN:
                                        toast = Toast.makeText(getApplicationContext(),
                                                "Email already used, try another one.",
                                                Toast.LENGTH_LONG);
                                        toast.setGravity(Gravity.BOTTOM, 0, 0);
                                        if (caPD != null) caPD.dismiss();
                                        toast.show();
                                        email.setText("");

                                    case com.parse.ParseException.CONNECTION_FAILED:
                                        toast = Toast.makeText(getApplicationContext(),
                                                ("Connection failed, check internet connection "
                                                        + " and try again!"), Toast.LENGTH_LONG);
                                        toast.setGravity(Gravity.BOTTOM, 0, 0);
                                        if (caPD != null) caPD.dismiss();
                                        toast.show();
                                }
                            }
                        }
                    });

                }
                else
                {
                    Toast toast;
                    if ( caPD != null ) caPD.dismiss();
                    toast = Toast.makeText(getApplicationContext(),
                            "Error, try again", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    if (caPD != null ) caPD.dismiss();
                    toast.show();
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread activitySwitch = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent( getApplicationContext(), Login.class );
                        startActivity( intent );

                    }
                });
                activitySwitch.start();
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent( getApplicationContext(),
                        Login.class );
                startActivity( intent );
            }
        });
        thread.start();
    }
}
