package com.cse110devteam.Activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cse110devteam.Global.ManagerPagerAdapter;
import com.cse110devteam.Global.TypefaceGenerator;
import com.cse110devteam.Global.Util;
import com.cse110devteam.R;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by anthonyaltieri on 1/15/16.
 */
public class ManagerMain  extends FragmentActivity {
    public static int HAS_BUSINESS_NUM_ITEMS = 3;
    public static int NO_BUSINESS_NUM_ITEMS = 1;
    private ViewPager mPager;

    public int pagerPosition;

    Toolbar mToolbar;
    TextView title;

    ParseUser user;

    boolean setTabs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_main);
        setTabs = false;

    }

    @Override
    protected void onStart(){
        super.onStart();
        user = ParseUser.getCurrentUser();
        Log.d("ManMain", "");
        Log.d("user", user.toString());
        Log.d("user.oid", user.getObjectId());
        ParseObject business = (ParseObject) user.get("business");
        boolean hasBusinessPage = (business != null);
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new ManagerPagerAdapter(getSupportFragmentManager(),
                (hasBusinessPage) ? HAS_BUSINESS_NUM_ITEMS : NO_BUSINESS_NUM_ITEMS));

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        title = (TextView) findViewById(R.id.title);
        title.setTypeface(TypefaceGenerator.get("robotoBold", getAssets()));

        if ( !setTabs )
        {
            TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
            if(hasBusinessPage) {
                tabLayout.addTab(tabLayout.newTab().setText("SCHEDULE"));
                tabLayout.addTab(tabLayout.newTab().setText("MANAGERIAL"));
                tabLayout.addTab(tabLayout.newTab().setText("MESSAGING"));
            } else {
                tabLayout.setVisibility(View.GONE);
            }
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

            mPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    mPager.setCurrentItem(tab.getPosition());
                    pagerPosition = tab.getPosition();
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

            setTabs = true;

        }
        mPager.setCurrentItem(1);
        pagerPosition = 1;
        mToolbar.showOverflowMenu();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Util.logOut();
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent intent = new Intent( getApplicationContext(), Login.class );
        startActivity( intent );
    }
}

