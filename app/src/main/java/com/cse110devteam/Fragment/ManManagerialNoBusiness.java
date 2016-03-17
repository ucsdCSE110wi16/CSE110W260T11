package com.cse110devteam.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cse110devteam.Activity.CreateBusinessPage;
import com.cse110devteam.Activity.Login;
import com.cse110devteam.Global.TypefaceGenerator;
import com.cse110devteam.R;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by anthonyaltieri on 1/16/16.
 */
public class ManManagerialNoBusiness extends android.support.v4.app.Fragment{
    ParseUser user;

    Button createBusiness;

    TextView greeting;
    TextView instructions;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = ParseUser.getCurrentUser();
        ParseObject business = (ParseObject) user.get("business");
        if ( business != null )
        {
            Intent intent = new Intent( getActivity().getApplicationContext(), Login.class );
            startActivity( intent );
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.manager_managerial_nobusiness, container, false);
    }

    @Override
    public void onViewCreated(View v, Bundle bundle) {
        super.onViewCreated(v, bundle);
        user = ParseUser.getCurrentUser();

        greeting = (TextView) getActivity().findViewById(R.id.greeting);
        instructions = (TextView) getActivity().findViewById(R.id.instructions);
        greeting.setTypeface(TypefaceGenerator.get("robotoBlack", getActivity().getAssets()));
        instructions.setTypeface(TypefaceGenerator.get("roboto", getActivity().getAssets()));

        String firstname = (String) user.get("firstname");
        greeting.setText("Hello " + firstname.substring(0, 1).toUpperCase()
                + firstname.substring(1) + "!");

        createBusiness = (Button) getActivity().findViewById(R.id.createBusiness);

        createBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateBusinessPage.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onPause() {
        super.onPause();
    }

}
