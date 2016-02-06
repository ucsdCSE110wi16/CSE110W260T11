package com.cse110devteam.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.cse110devteam.Fragment.EmpMessaging;
import com.cse110devteam.Fragment.EmpSchedule;
import com.cse110devteam.Fragment.ManManagerial;
import com.cse110devteam.Fragment.ManMessaging;
import com.cse110devteam.Fragment.ManSchedule;
import com.cse110devteam.R;

import java.util.ArrayList;

/**
 * Created by anthonyaltieri on 1/15/16.
 */
public class EmployeeMain extends FragmentActivity{
    Button messaging, schedule;

    public static int NUM_ITEMS = 2;

    //public EmployeePagerAdapter myAdapter;
    //private ViewPager mPager;
    //ViewPager mPager;
    //EmployeePagerAdapter myAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_main);


        messaging = (Button) findViewById(R.id.messaging);
        schedule = (Button) findViewById(R.id.schedule);
        //mPager = (ViewPager) findViewById(R.id.pager);
        //mPager.setAdapter(new EmployeePagerAdapter(getSupportFragmentManager()));

        //myAdapter = new EmployeePagerAdapter(getSupportFragmentManager());
        //mPager.setAdapter(myAdapter);

        //mPager.setCurrentItem(0);

       /*schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //myAdapter.getItem(0);
                mPager.setCurrentItem(0);
            }
        });*/

        /*messaging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClickView(View v){
                myAdapter.getItem(1);
            }

        });*/


    }



     /*public static class EmployeePagerAdapter extends FragmentPagerAdapter{
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
        }
    }*/



}

