package com.cse110devteam.Activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.cse110devteam.Fragment.EmpMessaging;
import com.cse110devteam.Fragment.EmpSchedule;
import com.cse110devteam.Fragment.ManManagerial;
import com.cse110devteam.Fragment.ManMessaging;
import com.cse110devteam.Fragment.ManSchedule;
import com.cse110devteam.R;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by anthonyaltieri on 1/15/16.
 */
public class EmployeeMain extends FragmentActivity{
   // Button messaging, schedule;

  //  public static int NUM_ITEMS = 2;

      //private?
      /*ViewPager mPager;
      EmployeePagerAdapter myAdapter;*/



    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_main);


        messaging = (Button) findViewById(R.id.messaging);
        schedule = (Button) findViewById(R.id.schedule);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new EmployeePagerAdapter(getSupportFragmentManager()));

        myAdapter = new EmployeePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(myAdapter);

        //mPager.setCurrentItem(0);*/

       /*schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.schedule:
                        //what to put here
                        EmpSchedule empSchedule = new EmpSchedule();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                        transaction.replace(R.id.calendar1, empSchedule);
                        transaction.addToBackStack(null);

                        transaction.commit();

                    }

            }

       });

        messaging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                myAdapter.getItem(1);
            }

        });*/


   // }

      /*  public void selectFrag(View view) {
            Fragment fr;

            if(view == findViewById(R.id.calendar1)) {
                fr = new EmpSchedule();

            }else {
            fr = new EmpMessaging();
            }


            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            fragmentTransaction.replace(R.id.calendar1, fr);

            fragmentTransaction.commit();



        }*/
/*
     public static class EmployeePagerAdapter extends FragmentPagerAdapter{
        public EmployeePagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

       @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch (position){
                default: return new EmpSchedule();
                case 0: return new EmpSchedule();
                case 1: return new EmpMessaging();

            }
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }*/
   // }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.employee_main);

        boolean[][] boolArr = new boolean[12][31];
        JSONArray boolArrJSON = new JSONArray();
        for(int i = 0; i < 12; i++){
            JSONArray subArray = new JSONArray();
            for(int j = 0; j < 31; j++) {
                try {
                    subArray.put(j, boolArr[i][j]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            try {
                boolArrJSON.put(i, subArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        ParseUser currentUser = ParseUser.getCurrentUser();
        currentUser.put("boolSched", boolArrJSON);
        currentUser.saveInBackground();

    }

    public void selectFrag(View view){
        Fragment fr;

        if(view == findViewById(R.id.schedule)){
            fr = new EmpSchedule();

        }else{
            fr = new EmpMessaging();
        }


        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_place, fr);
        ft.commit();
    }

}

