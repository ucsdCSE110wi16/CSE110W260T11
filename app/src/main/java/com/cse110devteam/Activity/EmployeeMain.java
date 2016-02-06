package com.cse110devteam.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

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
public class EmployeeMain extends FragmentActivity {
    public static int NUM_ITEMS = 2;

    private EmployeePagerAdapter myAdapter;
    private ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_main);
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new EmployeePagerAdapter(getSupportFragmentManager()));
        mPager.setCurrentItem(0);
    }

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
        }
    }



}

