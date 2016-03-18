package com.cse110devteam.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cse110devteam.Activity.Login;
import com.cse110devteam.Global.ChatApplication;
import com.cse110devteam.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

/**
 * Created by anthonyaltieri on 1/14/16.
 */
public class ForgotPassDialog extends DialogFragment {
    private EditText forgotEmail;
    private Context mContext;
    private Button submit;
    private Button cancel;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_forgotpass, null);

        submit = ( Button ) view.findViewById(R.id.submit);
        cancel = ( Button ) view.findViewById( R.id.cancel );

        forgotEmail = ( EditText ) view.findViewById(R.id.forgot_email_address);

        submit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = forgotEmail.getText().toString();

                ParseUser.requestPasswordResetInBackground(input, new
                            RequestPasswordResetCallback() {
                        @Override
                        public void done(ParseException e) {
                            if ( e == null )
                            {
                                Toast toast = Toast.makeText( getActivity().getApplicationContext(),
                                        "Email has been sent", Toast.LENGTH_LONG );
                                toast.setGravity( Gravity.BOTTOM, 0, 0 );
                                toast.show();
                                dismiss();
                            }
                            else
                            {
                                Toast toast = Toast.makeText( getActivity().getApplicationContext(),
                                        ( "An error occured. Are you sure you typed the right"
                                                + " address?" ), Toast.LENGTH_LONG );
                                toast.setGravity( Gravity.BOTTOM, 0, 0 );
                                toast.show();
                            }
                        }
                    });


            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        builder.setView(view);

        return builder.create();

    }


}

