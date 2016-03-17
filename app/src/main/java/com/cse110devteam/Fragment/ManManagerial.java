package com.cse110devteam.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.EditText;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cse110devteam.Activity.ShiftList;
import com.cse110devteam.Global.TypefaceGenerator;
import com.cse110devteam.Global.Util;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import android.app.AlertDialog;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import java.util.Calendar;
import android.view.View.OnClickListener;




import com.cse110devteam.Models.Message;
import com.cse110devteam.R;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by anthonyaltieri on 2/20/16.
 */
public class ManManagerial extends Fragment{

    private RecyclerView rv;
    private RecyclerView.Adapter mAdapter;
    private List<Message> mMessages = new ArrayList<Message>();


    private Spinner spinner_month, spinner_day, spinner_time;
    private Button btnStartDay;
    private Button btnStartTime;
    private Button btnEnd;
    private Button btnInvite;
    private Button btnShifts;
    private Button btnShiftList;
    private Button btnSaveShift;

    private int mYear;
    private int mMonth;
    private int mDay;
    private String m_Text;
    private TextView txtDate;

    private TextView worker_name;
    private TextView worker_email;
    private TextView startTime;
    private TextView endTime;
    private TextView startDate;


    private Typeface roboto;
    private Typeface robotoMedium;
    private Typeface robotoBold;

    ParseUser user;
    ParseObject business;

    private Date shiftStart;
    private Date shiftEnd;

    private boolean doneStartDay;
    private boolean doneStartTime;
    private boolean doneEndTime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        user = ParseUser.getCurrentUser();
        business = (ParseObject) user.get( "business" );

        shiftStart = new Date();
        shiftEnd = new Date();

        doneStartDay = false;
        doneStartTime = false;
        doneEndTime = false;

        roboto = TypefaceGenerator.get( "roboto", getActivity().getAssets() );
        robotoBold = TypefaceGenerator.get( "robotoBold", getActivity().getAssets() );
        robotoMedium = TypefaceGenerator.get( "robotoMedium", getActivity().getAssets() );
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        startTime = (TextView) getActivity().findViewById(R.id.starttime);
        startTime.setText( "--:--");
        endTime = (TextView) getActivity().findViewById(R.id.endtime);
        endTime.setText( "--:--");
        startDate = (TextView) getActivity().findViewById(R.id.startdate);
        startDate.setText( "--/--/--");


        btnStartDay = (Button) getActivity().findViewById(R.id.btnStartDay);
        btnStartDay.setTypeface( robotoMedium );
        btnStartDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                ParseObject shift = new ParseObject( "Shift" );
                                shiftStart.setYear(year - 1900);
                                shiftStart.setMonth(monthOfYear);
                                shiftStart.setDate(dayOfMonth);

                                String setStartDate = dayOfMonth + "/" + monthOfYear + "/"
                                        + year;

                                Log.d( "shiftDateStart", setStartDate );
                                startDate.setText( setStartDate );


                                doneStartDay = true;
                            }
                        }, mYear, mMonth, mDay);
                dpd.show();
            }

        });

        btnStartTime = (Button) getActivity().findViewById(R.id.btnStartTime);
        btnStartTime.setTypeface(robotoMedium);
        btnStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                boolean is24hour = false;


                TimePickerDialog tpd = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int selectedhour,
                                                  int selectedminute) {
                                //MAYBE do parse stuff here

                                shiftStart.setHours(selectedhour);
                                shiftStart.setMinutes(selectedminute);

                                String sStart = Util.prettyHourMin( shiftStart );

                                Log.v("Start Time", "set time: " + sStart );

                                startTime.setText( sStart );

                                doneStartTime = true;
                            }
                        }, hour, minute, is24hour);
                tpd.show();
            }

        });

        btnSaveShift = (Button) getActivity().findViewById(R.id.shift_save);
        btnSaveShift.setTypeface(robotoMedium);
        btnSaveShift.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final ParseObject shift = new ParseObject( "Shift" );
                        shift.put("start", shiftStart);
                        shift.put("end", shiftEnd);
                        shift.put("business", business);
                        shift.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                business.add("shifts", shift);
                                business.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                                                "Shift Saved!", Toast.LENGTH_LONG);
                                        toast.setGravity(Gravity.BOTTOM, 0, 0);
                                        toast.show();
                                    }
                                });
                            }
                        });
                    }
                });
                thread.start();
                startDate.setText( "--/--/--" );
                startTime.setText( "--:--" );
                endTime.setText("--:--");
            }
        });

        btnEnd = (Button) getActivity().findViewById(R.id.btnEnd);
        btnEnd.setTypeface( robotoMedium );
        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                boolean is24hour = false;


                TimePickerDialog tpd = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int selectedhour,
                                                  int selectedminute) {
                                //MAYBE do parse stuff here
                                shiftEnd.setHours(selectedhour);
                                shiftEnd.setMinutes(selectedminute);

                                String sEnd = Util.prettyHourMin( shiftEnd );

                                Log.v("End Time", "set time: " + sEnd );

                                endTime.setText( sEnd );

                                doneEndTime = true;
                            }
                        }, hour, minute, is24hour);
                tpd.show();
            }

        });



        btnShifts = (Button) getActivity().findViewById(R.id.btnShifts);
        btnShifts.setTypeface( robotoMedium );
        btnShifts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getActivity().getApplicationContext(),
                        ShiftList.class );
                startActivity( intent );
            }

        });



        btnInvite=(Button) getActivity().findViewById(R.id.btnInvite);
        btnInvite.setTypeface( robotoMedium );
        btnInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle("Employee e-mail");
                alertDialog.setMessage("Enter your e-mail");

                //Setting up the input
                final EditText input = new EditText(getActivity());

                input.setInputType(InputType.TYPE_CLASS_TEXT);
                alertDialog.setView(input);


                //setting up button
                alertDialog.setButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //this is when the employee enters e-mail
                        m_Text = input.getText().toString();

                        //check if the email is in the system
                        Toast toast;
                        boolean emailInSystem = emailInSystem(m_Text);
                        if (!emailInSystem) {
                            toast = Toast.makeText(getActivity().getApplicationContext(),
                                    "Email not registered to an employee",
                                    Toast.LENGTH_LONG);
                            toast.show();
                            return;
                        }
                        //Now, we know the email belongs to an employee. We check if the employee is already
                        //in the business page. Make a helper method
                        Toast toast2;
                        ParseQuery<ParseUser> query = ParseUser.getQuery();
                        query.whereEqualTo("username", m_Text);
                        ParseUser employee = null;
                        try {
                            employee = query.getFirst();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if (alreadyInvited(employee)) {
                            //The employee has already been invited, there is nothing to do
                            toast2 = Toast.makeText(getActivity().getApplicationContext(),
                                    "Employee has already been invited to the business page",
                                    Toast.LENGTH_LONG);
                            toast2.show();
                            return;
                        } else {
                            //Need to add the employee into the Business page (add into employee
                            //array)
                            Toast toast3;
                            addToBusiness(employee);
                            toast3 = Toast.makeText(getActivity().getApplicationContext(),
                                    "Employee successfully invited to the business page",
                                    Toast.LENGTH_LONG);
                            toast3.show();
                            return;

                        }

                    }
                });
                alertDialog.show();


            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {


        final View rootView = inflater.inflate(R.layout.man_managerial, container, false);




        return rootView;
    }

    private boolean emailInSystem(String username){
        ParseQuery<ParseUser> userList = ParseUser.getQuery();
        userList.whereEqualTo("username", username);
        try{
            return (userList.count() != 0);

        } catch (ParseException e) {
            return false;
        }
    }

    private boolean alreadyInvited(ParseUser user){

        if(user.get("businessName") == null) {
            return false;
        }
        if(user.get("businessName").toString().length()==0){
            //employee is not in a business page yet
            return false;
        }
        return true;



    }

    private void addToBusiness(ParseUser user){

        ParseObject workplace = ParseUser.getCurrentUser().getParseObject("business");
        try {
            workplace.fetch();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("username", m_Text);
        params.put("businessID", workplace.getObjectId());
        params.put("businessName", workplace.getString("name"));
        params.put("businessChat", workplace.getParseObject("chat"));
        ParseCloud.callFunctionInBackground("inviteEmployee", params);
        return;
    }

}

