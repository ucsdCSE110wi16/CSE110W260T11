package com.cse110devteam.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
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



    @Override
    public void onCreate(Bundle savedInstanceState){
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


        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                boolean validEmail = false;
                boolean validFname = false;
                boolean validLname = false;
                boolean passMatch = false;
                String textEmail = email.getText().toString().toLowerCase().trim();
                String textFname = fname.getText().toString().toLowerCase().trim();
                String textLname = lname.getText().toString().toLowerCase().trim();
                String textPass1 = password1.getText().toString();
                String textPass2 = password2.getText().toString();
                /* We might want to test to see if the email is valid
                 * but for right now I'm just going to assume it is if
                 * it is longer than 0 length
                 */
                if(textEmail.length() > 0){
                    validEmail = true;
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Enter an email!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.show();
                }
                if(textPass1.equals(textPass2)){
                    passMatch = true;
                } else {
                    Log.d("Password1Text", textPass1);
                    Log.d("Password2Text", textPass2);
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Passwords don't match!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.show();
                    password1.setText("");
                    password2.setText("");
                }
                if(textFname.length() > 0){
                    validFname = true;
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Enter a First Name!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.show();
                }
                if(textLname.length() > 0){
                    validLname = true;
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Enter a Last Name!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.show();
                }
                if(validEmail && passMatch && validFname && validLname){
                    ParseUser currentUser = ParseUser.getCurrentUser();
                    if(currentUser != null) currentUser.logOut();
                    ParseUser newUser = new ParseUser();
                    newUser.setUsername(textEmail);
                    newUser.put("firstname", textFname);
                    newUser.put("lastname", textLname);
                    newUser.put("ismanager", isManagerAccount);
                    newUser.setPassword(textPass1);
                    Log.d("Create_Account", "About to signUpInBackground");
                    newUser.signUpInBackground(new SignUpCallback(){
                        public void done(com.parse.ParseException e){
                            if(e == null){
                                goToLogin();
                            } else{
                                e.printStackTrace();
                                Log.d("CREATE_ACCOUNT_ERROR", e.toString() );
                                Toast toast;
                                switch (e.getCode()){
                                    case com.parse.ParseException.ACCOUNT_ALREADY_LINKED:
                                        toast = Toast.makeText(getApplicationContext(),
                                                "Email already used, try another one.",
                                                Toast.LENGTH_LONG);
                                        toast.setGravity(Gravity.BOTTOM, 0, 0);
                                        toast.show();
                                        email.setText("");

                                    case com.parse.ParseException.USERNAME_TAKEN:
                                        toast = Toast.makeText(getApplicationContext(),
                                                "Email already used, try another one.",
                                                Toast.LENGTH_LONG);
                                        toast.setGravity(Gravity.BOTTOM, 0, 0);
                                        toast.show();
                                        email.setText("");

                                    case com.parse.ParseException.CONNECTION_FAILED:
                                        toast = Toast.makeText(getApplicationContext(),
                                                ("Connection failed, check internet connection "
                                                        + " and try again!"), Toast.LENGTH_LONG);
                                        toast.setGravity(Gravity.BOTTOM, 0 ,0);
                                        toast.show();
                                }
                            }
                        }
                    });

                }


            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLogin();
            }
        });
    }

    @Override
    public void onPause(){
        super.onPause();

    }

    private void goToLogin(){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}
