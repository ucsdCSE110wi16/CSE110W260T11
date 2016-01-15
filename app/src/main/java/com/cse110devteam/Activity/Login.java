package com.cse110devteam.Activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cse110devteam.R;

/**
 * Created by anthonyaltieri on 1/14/16.
 */
public class Login extends Activity{
    private EditText email, password;
    private Button login, createaccount;
    /* forgotPass is a button, but in order to get the visual effect
     * that I wanted it is a clickable RelativeLayout. You can bind an
     * onClick listener to this and I made it clickable so it should
     * not be a problem
     */
    private RelativeLayout forgotPass;
    private TextView header;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        header = (TextView) findViewById(R.id.header);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        createaccount = (Button) findViewById(R.id.createaccount);
        forgotPass = (RelativeLayout) findViewById(R.id.forgotpass);

        /* BUTTON ONCLICK LISTENERS */
        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // Asynch task to query parse to see if these credentials are right

                // We use asynch because we don't want any methods used for aesthetic
                // purposses to be interupted

                /* PSUEDO CODE FOR LOGIN:
                 * If (correctCredentials){
                 *  Start asynch task to log in
                 * } else {
                 *  Visual popup (either shake view or field that is wrong turns red,
                 *  or some other type of popup)
                 *  Prompt use to re-enter credentials
                 * }
                 */
            }
        });

        createaccount.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                goToCreateAccount();
            }
        });

        forgotPass.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // Open a dialogue box prompting to enter in an email that has
                // an account associated with it.
            }
        });
    }

    @Override
    protected void onPause(){

    }

    public void goToCreateAccount(){
        Intent intent = new Intent(this, CreateAccount.class);
        startActivity(intent);
    }
}
