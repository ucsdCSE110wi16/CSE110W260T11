package com.cse110devteam.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import android.provider.Contacts;
import android.support.v4.view.ViewPager;
import android.support.v4.app.Fragment;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
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

import com.cse110devteam.Global.ContactAdapter;
import com.cse110devteam.Global.ContactInfo;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
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

    private int mYear;
    private int mMonth;
    private int mDay;
    private String m_Text;
    private TextView txtDate;

    private TextView worker_name;
    private TextView worker_email;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {



        rv = (RecyclerView)getActivity().findViewById(R.id.cardList);
        final LinearLayoutManager llm = new LinearLayoutManager(getActivity());



        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        ContactAdapter ca = new ContactAdapter(createList(30));
        rv.setAdapter(ca);
        rv.setVisibility(View.GONE);
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


        btnShifts = (Button)rootView.findViewById(R.id.btnShifts);
        btnShifts.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                rv.setVisibility(View.VISIBLE);
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
                        Toast toast2;
                        if (alreadyInvited(ParseUser.getCurrentUser())) {
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
                            addToBusiness(ParseUser.getCurrentUser());
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

        if(user.get("businessName").toString().length()==0){
            //employee is not in a business page yet
            return false;
        }
        return true;



    }

    private void addToBusiness(ParseUser user){
        ParseObject workplace = new ParseObject("business");
            user.put("business", workplace );

        return;
    }



    private List<ContactInfo> createList(int size) {

        List<ContactInfo> result = new ArrayList<ContactInfo>();
        for (int i=1; i <= size; i++) {
            ContactInfo ci = new ContactInfo();
            ci.name = ContactInfo.NAME_PREFIX + i;
            ci.surname = ContactInfo.SURNAME_PREFIX + i;
            ci.email = ContactInfo.EMAIL_PREFIX + i + "@test.com";

            result.add(ci);

        }

        return result;
    }
}

