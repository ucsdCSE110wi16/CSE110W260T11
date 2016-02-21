package com.cse110devteam.Activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.cse110devteam.Global.ManagerPagerAdapter;
import com.cse110devteam.Global.TypefaceGenerator;
import com.cse110devteam.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by anthonyaltieri on 1/15/16.
 */
public class ManagerMain  extends FragmentActivity {
    public static int NUM_ITEMS = 3;
    private ViewPager mPager;

    Toolbar mToolbar;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.manager_main);
            mPager = (ViewPager) findViewById(R.id.pager);
            mPager.setAdapter(new ManagerPagerAdapter(getSupportFragmentManager(), NUM_ITEMS));
            mPager.setCurrentItem(1);

            mToolbar = (Toolbar) findViewById(R.id.toolbar);

            title = (TextView) findViewById(R.id.title);
            title.setTypeface(TypefaceGenerator.get("robotoBold", getAssets()));

            TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
            tabLayout.addTab(tabLayout.newTab().setText("SCHEDULE"));
            tabLayout.addTab(tabLayout.newTab().setText("MANAGERIAL"));
            tabLayout.addTab(tabLayout.newTab().setText("MESSAGING"));
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

            mPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
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


}

