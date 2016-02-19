package com.cse110devteam.Global;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cse110devteam.Fragment.ManManagerial;
import com.cse110devteam.Fragment.ManMessaging;
import com.cse110devteam.Fragment.ManSchedule;

import java.util.Locale;

/**
 * Created by anthonyaltieri on 2/18/16.
 */
public class ManagerPagerAdapter extends FragmentPagerAdapter {
    private int NUM_ITEMS;

    public ManagerPagerAdapter(FragmentManager fm, int NUM_ITEMS) {
        super(fm);
        this.NUM_ITEMS = NUM_ITEMS;
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
    public CharSequence getPageTitle(int position){
        Locale l = Locale.getDefault();
        switch (position){
            case 0: return "SCHEDULE";
            case 1: return "MANAGERIAL";
            case 2: return "MESSAGING";
        }
        return null;
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }
}
