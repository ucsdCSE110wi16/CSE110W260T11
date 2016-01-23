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

import java.util.ArrayList;

/**
 * Created by anthonyaltieri on 1/15/16.
 */
public class EmployeeMain extends FragmentActivity {
    public static int NUM_ITEMS = 2;

    private ManagerPagerAdapter myAdapter;
    private ViewPager mPager;

    private Fragment empMessaging, empSchedule;
    public static ArrayList<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: setContentView();
        // TODO: Implement ManMessaging, ManSchedule, ManManagerial fragments
        empMessaging = new EmpMessaging();
        empSchedule = new EmpSchedule();
        fragmentList.add(empMessaging);
        fragmentList.add(empSchedule);

    }

    public static class ManagerPagerAdapter extends FragmentPagerAdapter{
        public ManagerPagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }
    }


}

