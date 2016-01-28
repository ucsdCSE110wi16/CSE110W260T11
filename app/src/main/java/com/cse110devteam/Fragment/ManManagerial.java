package com.cse110devteam.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cse110devteam.R;

/**
 * Created by anthonyaltieri on 1/16/16.
 */
public class ManManagerial extends android.support.v4.app.Fragment{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.manager_managerial, container, false);
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
