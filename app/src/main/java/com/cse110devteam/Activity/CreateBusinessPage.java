package com.cse110devteam.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cse110devteam.Global.User;
import com.cse110devteam.R;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * Created by anthonyaltieri on 1/26/16.
 */
public class CreateBusinessPage extends Activity{
    private TextView header;
    private TextView subheader;
    private EditText businessName;
    private Button create;

    private ParseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_business_page);

        header = (TextView) findViewById(R.id.header);
        subheader = (TextView) findViewById(R.id.subheader);
        businessName = (EditText) findViewById(R.id.businessname);
        create = (Button) findViewById(R.id.create);

        user = ParseUser.getCurrentUser();
        // TODO: deal with user == null in a more elegant way
        if(user == null) {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        }


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean validName = false;
                // businessName is not case-sensitive
                String textName = businessName.getText().toString().trim();
                if (textName.length() > 0) {
                    validName = true;
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Enter a business name!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.show();
                }
                if (validName) {
                    final ParseObject business = new ParseObject("Business");
                    business.put("mainManager", user);
                    business.put("name", textName);
                    user.put("businessName", textName);
                    business.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                user.put("business", business);
                                Intent intent = new Intent(getApplicationContext(), ManagerMain.class);
                                startActivity(intent);
                            } else {
                                e.printStackTrace();
                            }
                        }
                    });
                }


            }
        });
    }



}
