package com.cse110devteam.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.widget.GridView;
import com.cse110devteam.R;
import android.widget.TextView;
import android.content.Context;

/**
 * Created by anthonyaltieri on 1/22/16.
 */
public class EmpSchedule extends android.support.v4.app.Fragment,LinearLayout{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.employee_schedule, container, false);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

        // internal components
        private LinearLayout header;
        private ImageView btnPrev;
        private ImageView btnNext;
        private TextView txtDate;
        private GridView grid;

        public EmpSchedule(Context context)
        {
            super(context);
            initControl(context);
        }

        /**
         * Load component XML layout
         */
        private void initControl(Context context)
        {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            inflater.inflate(R.layout.employee_schedule, this);

            // layout is inflated, assign local variables to components
            header = (LinearLayout)findViewById(R.id.calendar_header);
            btnPrev = (ImageView)findViewById(R.id.calendar_prev_button);
            btnNext = (ImageView)findViewById(R.id.calendar_next_button);
            txtDate = (TextView)findViewById(R.id.calendar_date_display);
            grid = (GridView)findViewById(R.id.calendar_grid);
        }
}
