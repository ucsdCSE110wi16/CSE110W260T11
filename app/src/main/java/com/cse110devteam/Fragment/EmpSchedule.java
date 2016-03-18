package com.cse110devteam.Fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cse110devteam.Activity.DayShiftList;
import com.cse110devteam.Models.Shift;
import com.cse110devteam.R;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by anthonyaltieri on 1/16/16.
 */
public class EmpSchedule extends android.support.v4.app.Fragment {
    public ArrayList<String> initShifts;
    public ArrayAdapter<String> shiftAdapter;
    public ArrayList< ParseObject > mShifts = null;
    private ParseObject business;
    private ParseUser user;
    private HashMap< Date, ArrayList<Shift> > dateShiftMap;

    private CaldroidFragment cdf;

    private ProgressDialog loadingCal;

    private TextView behindCalendar;

    private Toolbar toolbar;

    private Button refresh;


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
        setHasOptionsMenu(true);


        if ( cdf != null )
        {
            cdf.refreshView();
        }

        behindCalendar = ( TextView ) rootView.findViewById( R.id.behindcal );

        user = ParseUser.getCurrentUser();
        business = (ParseObject) user.get("business");

        if ( business == null )
        {
            behindCalendar.setText( "You must be a part of a business to sign up for shifts."
                    + "Have your manager invite you." );
        }

        refresh = ( Button ) rootView.findViewById( R.id.refresh );
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( cdf != null && business != null  && getActivity() != null )
                {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ParseQuery<ParseObject> query = ParseQuery.getQuery("Business");
                            query.getInBackground(business.getObjectId(), new GetCallback<ParseObject>() {
                                @Override
                                public void done(ParseObject object, ParseException e) {
                                    if (e == null) {
                                        try {
                                            Log.d("business name", object.getString("name"));
                                            mShifts = (ArrayList<ParseObject>) object.fetchIfNeeded().get("shifts");
                                            paintCalendar(mShifts, cdf);
                                            FragmentTransaction t = getActivity().getSupportFragmentManager().beginTransaction();
                                            t.replace(R.id.calendar, cdf);
                                            t.commit();
                                            Toast toast = Toast.makeText( getActivity().getApplicationContext(),
                                                    "Calendar Refreshed", Toast.LENGTH_SHORT );
                                            toast.setGravity( Gravity.BOTTOM, 0, 0 );
                                            toast.show();
                                        } catch (ParseException er) {
                                            Log.d("ERROR [fetching from shifts] refresh onclick", er.toString());
                                            er.printStackTrace();
                                        }


                                    } else {
                                        Log.d("Refresh", "In onclick listener");
                                        Log.d("ERROR [query business]", "bid: " + business.getObjectId() + " " + e.toString());
                                    }


                                }
                            });

                        }
                    });
                }
            }
        });



        toolbar = (Toolbar) getActivity().findViewById( R.id.toolbar );

        cdf = new CaldroidFragment();
        if ( savedInstanceState != null )
        {
            cdf.restoreStatesFromKey(savedInstanceState,
                    "CALDROID_SAVED_STATE");
        }
        else
        {
            Bundle args = new Bundle();
            Calendar cal = Calendar.getInstance();
            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
            cdf.setArguments(args);

        }



        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if ( business != null )
                {
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Business");
                    query.getInBackground(business.getObjectId(), new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject object, ParseException e) {
                            if (e == null) {
                                try {
                                    mShifts = (ArrayList<ParseObject>) object.fetchIfNeeded().get("shifts");
                                    paintCalendar(mShifts, cdf);
                                    FragmentTransaction t = getActivity().getSupportFragmentManager().beginTransaction();
                                    t.replace(R.id.calendar, cdf);
                                    t.commit();
                                } catch (ParseException er) {
                                    Log.d("ERROR [fetching from shifts] onCreateView", er.toString());
                                    er.printStackTrace();
                                }



                            } else {
                                Log.d("EmpSchedule", "In body of onCreateView");
                                Log.d("ERROR [query business]", "bid: " + business.getObjectId() + e.toString());
                            }


                        }
                    });

                }

            }
        });

        final CaldroidListener listener = new CaldroidListener() {
            @Override
            public void onSelectDate(Date dateSelected, View view) {
                final Date date = dateSelected;
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent( getActivity().getApplicationContext(),
                                DayShiftList.class );
                        Bundle bundle = new Bundle();
                        bundle.putBoolean( "isManager", false );
                        bundle.putSerializable( "dateSelected", date );
                        intent.putExtras(bundle);
                        startActivity( intent );

                    }
                });

                thread.start();
            }
        };

        cdf.setCaldroidListener( listener );


        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();



        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if ( business != null )
                {
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Business");
                    query.getInBackground(business.getObjectId(), new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject object, ParseException e) {
                            if (e == null) {
                                Log.d("object.getObjectID()", object.getObjectId());
                                mShifts = (ArrayList<ParseObject>) object.get("shifts");
                                Log.d("about to iterate through shift", "starting");
                                if ( mShifts != null )
                                {
                                    for ( int i = 0 ; i < mShifts.size() ; i++ )
                                    {
                                        Log.d("i:", i + "");
                                        Log.d("mShifts.get(i).getObjectID()",
                                                mShifts.get(i).getObjectId() + "");
                                    }
                                    paintCalendar(mShifts, cdf);
                                    FragmentTransaction t = getActivity()
                                            .getSupportFragmentManager()
                                            .beginTransaction();
                                    t.replace(R.id.calendar, cdf);
                                    t.commit();
                                }


                            } else {
                                Log.d("query error [business]", e.toString());
                            }


                        }
                    });

                }

            }
        });
    }

    private void paintCalendar( ArrayList< ParseObject > mShifts, final CaldroidFragment caldroidFragment)
    {
        if ( mShifts == null ) return;
        if ( business == null ) return;


        dateShiftMap = new HashMap<>();

        // Add all of the shifts to the dateShiftMap
        for ( int i = 0 ; i < mShifts.size() ; i++ ) {
            ParseObject shift = mShifts.get(i);
            Log.d("mShifts.size()", "" + mShifts.size() );
            Log.d("mShifts.get(i).getobjectid()", mShifts.get(i).getObjectId() + "");
            Log.d("i =", i + "");

            ParseQuery<ParseObject> query = ParseQuery.getQuery("Shift");
            query.getInBackground(shift.getObjectId(), new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    if ( object != null )
                    {
                        Date start = null;
                        Date end = null;
                        ParseUser employee = null;
                        int status = -1;

                        try {
                            start = (Date) object.fetchIfNeeded().get("start");
                            end = (Date) object.fetchIfNeeded().get("end");
                            employee = (ParseUser) object.fetchIfNeeded().get("employee");
                            status = (employee == null) ? Shift.VACANT : Shift.FILLED;
                        } catch (ParseException pe) {
                            Log.d("ParseException", pe.toString());
                        }

                        Shift shiftModel = new Shift.Builder(status)
                                .business(business)
                                .employee(employee)
                                .start(start)
                                .end(end)
                                .build();

                        int month = start.getMonth();
                        int day = start.getDate();
                        int year = start.getYear();

                        Date key = new Date();
                        key.setDate(day);
                        key.setMonth(month);
                        key.setYear(year);



                        ArrayList<Shift> list = dateShiftMap.get(key);

                        if (list == null) {
                            list = new ArrayList<>();
                            list.add(shiftModel);
                            dateShiftMap.put(key, list);
                        } else {
                            list.add(shiftModel);
                        }


                        ColorDrawable vacant = new ColorDrawable(Color.parseColor("#4CAF50"));
                        ColorDrawable filled = new ColorDrawable(Color.parseColor("#F44336"));

                        // Iterate over every date and set color
                        for (Date d : dateShiftMap.keySet() )

                        {
                            ArrayList<Shift> shiftsThisDay = dateShiftMap.get(d);
                            boolean hasVacantShift = false;
                            for (Shift s : shiftsThisDay) {

                                if (s.getEmployee() == null) {
                                    hasVacantShift = true;
                                }

                            }
                            if (hasVacantShift) {
                                caldroidFragment.setBackgroundDrawableForDate(vacant, d);
                                caldroidFragment.refreshView();
                                Log.d("set vacant", "" + d.getDate() + "/" + d.getMonth() + "/" + d.getYear() );
                            } else {
                                caldroidFragment.setBackgroundDrawableForDate(filled, d);
                                caldroidFragment.refreshView();
                                Log.d("set filled", "" + d.getDate() + "/" + d.getMonth() + "/" + d.getYear() );
                            }
                        }

                    }
                }

            });
        }

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
                shiftString = dateFormatter.format(beginning) + " - " + dateFormatter.format(end);
                if ( employee != null )
                {
                    shiftString = shiftString + ": " +
                            employee.getString("firstname") + " " + employee.getString("lastname");
                }
                shifts.add(shiftString);
            }
        }
        return shifts;
    }
}
