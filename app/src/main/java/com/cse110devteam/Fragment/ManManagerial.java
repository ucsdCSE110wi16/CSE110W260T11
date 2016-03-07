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
import com.parse.ParseObject;
import com.parse.Parse;
import com.parse.ParseUser;
import android.app.AlertDialog;
import android.widget.DatePicker;
import android.app.DatePickerDialog;
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
    private Button btnStart;
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


        btnStart = (Button)rootView.findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
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
//                                    txtDate.setText(dayOfMonth + "-"
//                                            + (monthOfYear + 1) + "-" + year);

                                }
                            }, mYear, mMonth, mDay);
                    dpd.show();
                }

        });

        btnEnd = (Button)rootView.findViewById(R.id.btnEnd);
        btnEnd.setOnClickListener(new View.OnClickListener() {
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
//

                            }
                        }, mYear, mMonth, mDay);
                dpd.show();
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

                    }
                });
                alertDialog.show();


            }
        });



        return rootView;
    }





}
