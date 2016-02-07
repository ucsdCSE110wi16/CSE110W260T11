package com.cse110devteam.Fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cse110devteam.R;
import com.roomorama.caldroid.CaldroidFragment;

import android.R.id;
import java.util.Calendar;

/**
 * Created by anthonyaltieri on 1/16/16.
 */
public class ManSchedule extends android.support.v4.app.Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private FragmentActivity myContext;

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.employee_schedule, container, false);

        CaldroidFragment caldroidFragment = new CaldroidFragment();
        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        caldroidFragment.setArguments(args);

        FragmentManager fragManager = myContext.getSupportFragmentManager();

        android.support.v4.app.FragmentTransaction t = fragManager.beginTransaction();
        t.replace(R.id.schedule, caldroidFragment);
        t.commit();

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
