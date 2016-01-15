package com.cse110devteam.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.cse110devteam.R;

/**
 * Created by anthonyaltieri on 1/14/16.
 */
public class ForgotPassDialog extends DialogFragment{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(R.layout.dialog_forgotpass)
                .setPositiveButton("Submit", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){

                    }
                });
        return builder.create();

    }
}
