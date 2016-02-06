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
import com.cse110devteam.R;
import android.content.Intent;

/**
 * Created by anthonyaltieri on 1/15/16.
 */
public class CreateAccountUserType extends Activity{
    Button manager, employee;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account_type);

        manager = (Button) findViewById(R.id.manager);
        employee = (Button) findViewById(R.id.employee);

        manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCreateAccount(true);
            }
        });

        employee.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                goToCreateAccount(false);
            }
        });

    }


    private void goToCreateAccount(boolean isManager){
        Intent intent = new Intent(this , CreateAccount.class);
        intent.putExtra("isManager", isManager);
        startActivity(intent);
    }


    //Called when the user creates an employee account.
    public void goToEmployeeMain(View view){
        Intent intent = new Intent(this, EmployeeMain.class);

    }

    public void goToManagerMain(View view){
        Intent intent = new Intent(this, ManagerMain.class);
    }
}
