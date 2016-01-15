package com.cse110devteam.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cse110devteam.R;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.text.ParseException;
import java.util.zip.Inflater;

/**
 * Created by anthonyaltieri on 1/14/16.
 */
public class CreateAccount extends Activity {
    private EditText email, fname, lname, password1, password2;
    private Button submit, cancel;



    @Override
    public void onCreate(Bundle savedInstanceState){
        email = (EditText) findViewById(R.id.email);
        fname = (EditText) findViewById(R.id.fname);
        lname = (EditText) findViewById(R.id.lname);
        password1 = (EditText) findViewById(R.id.password1);
        password2 = (EditText) findViewById(R.id.password2);
        submit = (Button) findViewById(R.id.submit);
        cancel = (Button) findViewById(R.id.cancel);

        final int editTextBackgroundColor = password1.getDrawingCacheBackgroundColor();

        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                boolean validEmail = false;
                boolean validFname = false;
                boolean validLname = false;
                boolean passMatch = false;
                String textEmail = email.toString();
                String textFname = fname.toString();
                String textLname = lname.toString();
                String textPass1 = password1.toString();
                String textPass2 = password2.toString();
                /* We might want to test to see if the email is valid
                 * but for right now I'm just going to assume it is if
                 * it is longer than 0 length
                 */
                if(textEmail.length() > 0){
                    validEmail = true;
                    email.setBackgroundColor(editTextBackgroundColor);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Enter an email!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.show();
                    email.setBackgroundColor(0xFFCDD2);
                }
                if(textPass1.equals(textPass2)){
                    passMatch = true;
                    password1.setBackgroundColor(editTextBackgroundColor);
                    password2.setBackgroundColor(editTextBackgroundColor);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Passwords don't match!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.show();
                    password1.setText("");
                    password2.setText("");
                    password1.setBackgroundColor(0xFFCDD2);
                    password2.setBackgroundColor(0xFFCDD2);
                }
                if(textFname.length() > 0){
                    validFname = true;
                    fname.setBackgroundColor(editTextBackgroundColor);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Enter a First Name!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.show();
                    fname.setBackgroundColor(0xFFCDD2);
                }
                if(textLname.length() > 0){
                    validLname = true;
                    fname.setBackgroundColor(editTextBackgroundColor);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Enter a Last Name!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.show();
                    lname.setBackgroundColor(0xFFCDD2);
                }
                if(validEmail && passMatch && validFname && validLname){
                    ParseUser newUser = new ParseUser();
                    newUser.setUsername(textEmail);
                    newUser.put("firstname", textFname);
                    newUser.put("lastname", textLname);
                    newUser.setPassword(textPass1);
                    newUser.signUpInBackground(new SignUpCallback(){
                        public void done(com.parse.ParseException e){
                            if(e == null){
                                goToLogin();
                            } else{
                                Toast toast;
                                switch (e.getCode()){
                                    case com.parse.ParseException.ACCOUNT_ALREADY_LINKED:
                                        toast = Toast.makeText(getApplicationContext(),
                                                "Email already used, try another one.",
                                                Toast.LENGTH_LONG);
                                        toast.setGravity(Gravity.BOTTOM, 0, 0);
                                        toast.show();
                                        email.setText("");
                                        email.setBackgroundColor(0xFFCDD2);

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

    }

    private void goToLogin(){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}
