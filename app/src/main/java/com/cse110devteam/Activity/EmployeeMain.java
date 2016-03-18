package com.cse110devteam.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import com.cse110devteam.Global.EmployeePagerAdapter;
import com.cse110devteam.Global.TypefaceGenerator;
import com.cse110devteam.Global.Util;
import com.cse110devteam.R;
import com.parse.ParseUser;

import junit.framework.Assert;


/**
 * Created by anthonyaltieri on 1/15/16.
 */
public class EmployeeMain extends FragmentActivity{
    public static int NUM_ITEMS = 2;

    ViewPager mPager;

    Toolbar mToolbar;
    TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState){

        try{
            super.onCreate(savedInstanceState);
            setContentView(R.layout.employee_main);

            mPager = (ViewPager) findViewById(R.id.pager);
            mPager.setAdapter(new EmployeePagerAdapter(getSupportFragmentManager(), NUM_ITEMS));
            mPager.setCurrentItem(0);

            mToolbar = (Toolbar) findViewById(R.id.toolbar);
            title = (TextView) findViewById(R.id.title);
            title.setTypeface(TypefaceGenerator.get("robotoBold", getAssets()));

            TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
            tabLayout.addTab(tabLayout.newTab().setText("SCHEDULE"));
            tabLayout.addTab(tabLayout.newTab().setText("MESSAGING"));
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

            mPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    mPager.setCurrentItem(tab.getPosition());
                    mPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                    // Nothing
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                    // Nothing
                }
            });


        } catch (Error e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Util.logOut();
    }
}

