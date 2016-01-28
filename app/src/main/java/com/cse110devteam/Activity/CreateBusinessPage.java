package com.cse110devteam.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cse110devteam.Global.User;
import com.cse110devteam.R;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * Created by anthonyaltieri on 1/26/16.
 */
public class CreateBusinessPage extends Activity{
    private TextView header;
    private TextView subheader;
    private EditText businessName;
    private Button create;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        header = (TextView) findViewById(R.id.header);
        subheader = (TextView) findViewById(R.id.subheader);
        businessName = (EditText) findViewById(R.id.businessname);
        create = (Button) findViewById(R.id.create);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean validName = false;
                String textName = businessName.getText().toString();
                if(textName.length() > 0){
                    validName = true;
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Enter a business name!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.show();
                }
                if(validName){
                    //ParseQuery<ParseUser> query = ParseQuery.getQuery("Users");
                    //query.whereEqualTo(User.USERNAME);
                }


            }
        });
    }
}
