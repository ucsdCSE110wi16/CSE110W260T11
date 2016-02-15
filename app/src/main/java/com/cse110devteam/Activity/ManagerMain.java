package com.cse110devteam.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.cse110devteam.Fragment.ManManagerial;
import com.cse110devteam.Fragment.ManMessaging;
import com.cse110devteam.Fragment.ManSchedule;
import com.cse110devteam.R;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * Created by anthonyaltieri on 1/15/16.
 */
public class ManagerMain  extends FragmentActivity {
    public static int NUM_ITEMS = 3;

    private ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.manager_main);
            mPager = (ViewPager) findViewById(R.id.pager);
            mPager.setAdapter(new ManagerPagerAdapter(getSupportFragmentManager()));
            mPager.setCurrentItem(1);
        } catch (Error e){
            e.printStackTrace();
        }
    }

    public static class ManagerPagerAdapter extends FragmentPagerAdapter{
        public ManagerPagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch (position){
                default: return new ManManagerial();
                case 0: return new ManSchedule();
                case 1: return new ManManagerial();
                case 2: return new ManMessaging();

            }
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }
    }


}

