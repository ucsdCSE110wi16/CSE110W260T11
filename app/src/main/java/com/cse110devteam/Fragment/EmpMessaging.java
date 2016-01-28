package com.cse110devteam.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cse110devteam.R;

/**
 * Created by anthonyaltieri on 1/22/16.
 */
public class EmpMessaging extends android.support.v4.app.Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.employee_messaging, container, false);
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
