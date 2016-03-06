package com.cse110devteam.Fragment;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.R.id;
import android.support.v4.app.Fragment;

import com.cse110devteam.R;
import com.parse.Parse;
import com.parse.ParseUser;
import com.roomorama.caldroid.CaldroidFragment;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by anthonyaltieri on 1/22/16.
 */
public class EmpSchedule extends android.support.v4.app.Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();
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
        int[] limit = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        JSONArray schedArray = ParseUser.getCurrentUser().getJSONArray("boolSched");
        if(schedArray != null) {
            HashMap<Date, Integer> schedHM = new HashMap<>();
            Calendar workCalendar = Calendar.getInstance();
            for (int i = 0; i < 12; i++) {
                JSONArray thisMonth = null;
                try {
                    thisMonth = schedArray.getJSONArray(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int j = 0; j < limit[i]; j++) {
                    boolean working = thisMonth.optBoolean(j);
                    if (working) {
                        workCalendar.set(2016, i, j);
                        Date workingDay = workCalendar.getTime();
                        schedHM.put(workingDay, R.color.caldroid_light_red);
                    }
                }
            }
            caldroidFragment.setTextColorForDates(schedHM);
            caldroidFragment.refreshView();
        }
        FragmentManager fragManager = myContext.getSupportFragmentManager();

        android.support.v4.app.FragmentTransaction t = fragManager.beginTransaction();
        t.replace(R.id.fragment_place, caldroidFragment);
        t.commit();
        return rootView;
    }

}
