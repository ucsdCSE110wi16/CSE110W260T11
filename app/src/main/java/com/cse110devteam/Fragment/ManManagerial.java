package com.cse110devteam.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import android.support.v4.view.ViewPager;
import android.support.v4.app.Fragment;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.EditText;

import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.Parse;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import android.app.AlertDialog;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.app.Dialog;
import java.util.Calendar;
import android.view.View.OnClickListener;




import com.cse110devteam.Global.Message;
import com.cse110devteam.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anthonyaltieri on 2/20/16.
 */
public class ManManagerial extends Fragment{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<Message> mMessages = new ArrayList<Message>();


    private Spinner spinner_month, spinner_day, spinner_time;
    private Button btnStartDay;
    private Button btnStartTime;
    private Button btnEnd;
    private Button btnInvite;
    private int mYear;
    private int mMonth;
    private int mDay;
    private String m_Text;
    private TextView txtDate;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {


        final View rootView = inflater.inflate(R.layout.man_managerial_shifts, container, false);


        btnStartDay = (Button)rootView.findViewById(R.id.btnStartDay);
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
                                //MAYBE do parse stuff here


                            }
                        }, mYear, mMonth, mDay);
                dpd.show();
            }

        });

        btnStartTime = (Button)rootView.findViewById(R.id.btnStartTime);
        btnStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                boolean is24hour = true;


                TimePickerDialog tpd = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int selectedhour,
                                                  int selectedminute) {
                                //MAYBE do parse stuff here


                            }
                        }, hour, minute, is24hour);
                tpd.show();
            }

        });


        btnEnd = (Button)rootView.findViewById(R.id.btnEnd);
        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                boolean is24hour = true;


                TimePickerDialog tpd = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int selectedhour,
                                                  int selectedminute) {
                                //MAYBE do parse stuff here


                            }
                        }, hour, minute, is24hour);
                tpd.show();
            }

        });






        btnInvite=(Button)rootView.findViewById(R.id.btnInvite);
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
                        if (alreadyInvited(ParseUser.getCurrentUser())) {
                            //The employee has already been invited, there is nothing to do
                            return;
                        } else {
                            //Need to add the employee into the Business page (add into employee
                            //array)
                            addToBusiness(ParseUser.getCurrentUser());

                        }

                    }
                });
                alertDialog.show();


            }
        });



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
        //need to check if the user is listed in the Business's employees array
        ParseObject business = user.getParseObject("Business");
        if(business != null) {
            List<ParseUser> employees = business.getList("employees");
            if (employees.contains(user)) {
                return true;
            }
        }
        return false;


    }

    private void addToBusiness(ParseUser user){
        ParseObject business = user.getParseObject("Business");
        if(business != null) {
            business.getList("employees").add(user);
        }

        return;
    }


}

