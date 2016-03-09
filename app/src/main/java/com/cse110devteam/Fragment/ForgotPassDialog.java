package com.cse110devteam.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cse110devteam.Activity.Login;
import com.cse110devteam.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

/**
 * Created by anthonyaltieri on 1/14/16.
 */
public class ForgotPassDialog extends DialogFragment{
    private EditText forgotEmail;
    private Context mContext;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_forgotpass, null);
        mContext = getActivity().getApplicationContext();
        builder.setView(view)
                .setPositiveButton("Submit", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        forgotEmail = (EditText)view.findViewById(R.id.forgot_email_address);
                        ParseUser.requestPasswordResetInBackground(forgotEmail.getText().toString(), new RequestPasswordResetCallback() {
                            public void done(ParseException e) {
                                if (e == null) {
                                    CharSequence text = "An email with instructions to reset your password has been sent.";
                                    int duration = Toast.LENGTH_SHORT;
                                    Toast.makeText(mContext, text, duration).show();

                                } else {
                                    CharSequence text = "An error was encountered. Are you sure you typed the right address?";
                                    int duration = Toast.LENGTH_SHORT;
                                    Toast.makeText(mContext, text, duration).show();
                                }
                            }
                        });
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){

                    }
                });
        return builder.create();

    }


}

