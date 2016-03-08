package com.cse110devteam.Activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cse110devteam.Activity.CreateAccount;
import com.cse110devteam.Global.TypefaceGenerator;
import com.cse110devteam.R;
import android.content.Intent;
import android.widget.TextView;

/**
 * Created by anthonyaltieri on 1/15/16.
 */
public class CreateAccountUserType extends Activity{
    Button manager, employee;
    TextView header;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account_type);

        header = (TextView) findViewById(R.id.header);
        header.setTypeface(TypefaceGenerator.get("robotoBlack", getAssets()));

        manager = (Button) findViewById(R.id.manager);
        employee = (Button) findViewById(R.id.employee);
        manager.setTypeface(TypefaceGenerator.get("robotoMedium", getAssets()));
        employee.setTypeface(TypefaceGenerator.get("robotoMedium", getAssets()));

        manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getApplicationContext() , CreateAccount.class);
                        intent.putExtra("isManager", true);
                        startActivity(intent);
                    }
                });
                thread.start();
            }
        });

        employee.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(), CreateAccount.class);
                        intent.putExtra("isManer", false);
                        startActivity(intent);

                    }
                });
                thread.start();
            }
        });

    }

}
