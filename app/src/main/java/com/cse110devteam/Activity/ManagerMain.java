package com.cse110devteam.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.cse110devteam.Fragment.ManManagerial;
import com.cse110devteam.Fragment.ManMessaging;
import com.cse110devteam.Fragment.ManSchedule;

import java.util.ArrayList;

/**
 * Created by anthonyaltieri on 1/15/16.
 */
public class ManagerMain  extends FragmentActivity {
    public static int NUM_ITEMS = 3;

    private ManagerPagerAdapter myAdapter;
    private ViewPager mPager;

    private Fragment manMessaging, manSchedule, manManagerial;
    public static ArrayList<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: setContentView();
        // TODO: Implement ManMessaging, ManSchedule, ManManagerial fragments
        manMessaging = new ManMessaging();
        manSchedule = new ManSchedule();
        manManagerial = new ManManagerial();
        fragmentList.add(manMessaging);
        fragmentList.add(manSchedule);
        fragmentList.add(manManagerial);

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

