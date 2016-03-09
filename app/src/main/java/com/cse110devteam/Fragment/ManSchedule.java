package com.cse110devteam.Fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cse110devteam.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.roomorama.caldroid.CaldroidFragment;

import android.R.id;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by anthonyaltieri on 1/16/16.
 */
public class ManSchedule extends android.support.v4.app.Fragment {
    public ArrayList<String> initShifts;
    public ArrayAdapter<String> shiftAdapter;
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

        View rootView = inflater.inflate(R.layout.manager_schedule , container, false);

        CalendarView calendarView = (CalendarView) rootView.findViewById(R.id.manager_calendar);
        ListView listView = (ListView) rootView.findViewById(R.id.manager_shift_list);
        Calendar today = Calendar.getInstance();
        initShifts = getShifts(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH));
        shiftAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),R.layout.listitem, R.id.textview, initShifts);
        listView.setAdapter(shiftAdapter);
        shiftAdapter.setNotifyOnChange(true);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                initShifts.clear();
                initShifts.addAll(getShifts(year, month, dayOfMonth));
                shiftAdapter.notifyDataSetChanged();
            }
        });
        shiftAdapter.notifyDataSetChanged();
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public ArrayList<String> getShifts(int year, int month, int day) {
        final ArrayList<String> shifts = new ArrayList<>();
        List<ParseObject> ParseShifts = null;
        Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();
        startCalendar.set(year, month, day, 0, 0);
        endCalendar.set(year, month, day + 1, 0, 0);
        Date startDate = startCalendar.getTime();
        Date endDate = endCalendar.getTime();

        final SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm", Locale.US);

        ParseQuery<ParseObject> shiftQuery = ParseQuery.getQuery("Shift");
        shiftQuery.whereEqualTo("business", ParseUser.getCurrentUser().get("business"));
        shiftQuery.whereGreaterThan("start", startDate);
        shiftQuery.whereLessThan("end", endDate);
        shiftQuery.include("employee");
        try {
            ParseShifts = shiftQuery.find();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(ParseShifts != null){
            for (ParseObject shift : ParseShifts) {
                String shiftString;
                Date beginning = shift.getDate("start");
                Date end = shift.getDate("end");
                ParseObject employee = shift.getParseObject("employee");
                shiftString = dateFormatter.format(beginning) + " - " + dateFormatter.format(end) + ": " + employee.getString("firstname") + " " + employee.getString("lastname");
                shifts.add(shiftString);
            }
        }
        return shifts;
    }
}
