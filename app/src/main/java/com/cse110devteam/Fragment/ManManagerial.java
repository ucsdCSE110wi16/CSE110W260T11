package com.cse110devteam.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import android.support.v4.view.ViewPager;
import android.support.v4.app.Fragment;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.parse.ParseObject;
import com.parse.Parse;
import com.parse.ParseUser;



import com.cse110devteam.Global.Message;
import com.cse110devteam.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anthonyaltieri on 2/20/16.
 */
public class ManManagerial extends Fragment{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<Message> mMessages = new ArrayList<Message>();


    //private Spinner spinner1, spinner2;
    private Button btnStart;
    private Button btnEnd;
    private Button btnInvite;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {

        final View rootView = inflater.inflate(R.layout.man_managerial_shifts, container, false);

        btnStart = (Button)rootView.findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //update parse




            }
        });

        btnEnd = (Button)rootView.findViewById(R.id.btnEnd);
        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //update parse




            }
        });

        btnInvite=(Button)rootView.findViewById(R.id.btnInvite);
        btnInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //update parse



            }
        });



        return rootView;
    }






}
