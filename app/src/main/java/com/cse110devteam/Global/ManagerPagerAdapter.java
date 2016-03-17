package com.cse110devteam.Global;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cse110devteam.Fragment.ChatFragment;
import com.cse110devteam.Fragment.ManManagerial;
import com.cse110devteam.Fragment.ManManagerialNoBusiness;
import com.cse110devteam.Fragment.ManMessaging;
import com.cse110devteam.Fragment.ManSchedule;
import com.parse.ParseUser;

import java.util.Locale;

/**
 * Created by anthonyaltieri on 2/18/16.
 */
public class ManagerPagerAdapter extends FragmentPagerAdapter {
    private int NUM_ITEMS;
    boolean hasBusinessPage;
    private Fragment schedule = null;
    private Fragment messaging = null;

    public ManagerPagerAdapter(FragmentManager fm, int NUM_ITEMS) {
        super(fm);
        this.NUM_ITEMS = NUM_ITEMS;
        hasBusinessPage = (NUM_ITEMS == 3);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        if(hasBusinessPage){
            switch (position){
                case 0:
                    if ( schedule == null )
                    {
                        schedule = new ManSchedule();

                    }
                    return schedule;
                case 1: return new ManManagerial();
                case 2:
                    if ( messaging == null )
                    {
                        messaging = new ManMessaging();
                    }
                    return messaging;
            }
        } else {
            return new ManManagerialNoBusiness();
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position){
        if(hasBusinessPage){
            switch (position){
                case 0: return "SCHEDULE";
                case 1: return "MANAGERIAL";
                case 2: return "MESSAGING";
            }
        }
        return "MANAGERIAL";
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }
}
