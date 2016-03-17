package com.cse110devteam.Global;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cse110devteam.Fragment.EmpMessaging;
import com.cse110devteam.Fragment.EmpSchedule;

import java.util.Locale;


/**
 * Created by anthonyaltieri on 2/18/16.
 */
public class EmployeePagerAdapter extends FragmentPagerAdapter {
    private int NUM_ITEMS;
    private Fragment messaging;

    public EmployeePagerAdapter(FragmentManager fm, int NUM_ITEMS) {
        super(fm);
        this.NUM_ITEMS = NUM_ITEMS;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            default:
                return new EmpSchedule();
            case 0:
                return new EmpSchedule();
            case 1:
                if (messaging == null)
                {
                    messaging = new EmpMessaging();
                }

                return messaging;
        }
    }

    @Override
    public CharSequence getPageTitle(int position){
        Locale l = Locale.getDefault();
        switch (position){
            case 0: return "SCHEDULE";
            case 1: return "MESSAGING";
        }
        return null;
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }
}
