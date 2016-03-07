package com.cse110devteam.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import android.support.v4.view.ViewPager;
import android.support.v4.app.Fragment;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.support.v7.widget.RecyclerView;
<<<<<<< HEAD
import android.text.InputType;
=======
>>>>>>> 0b0d0814c02803975dad9ac4de25abac281dc19b
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
<<<<<<< HEAD
import android.widget.EditText;
=======
>>>>>>> 0b0d0814c02803975dad9ac4de25abac281dc19b
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.parse.ParseObject;
import com.parse.Parse;
import com.parse.ParseUser;
import android.app.AlertDialog;



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
<<<<<<< HEAD
    private String m_Text;
=======

>>>>>>> 0b0d0814c02803975dad9ac4de25abac281dc19b


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {


        final View rootView = inflater.inflate(R.layout.man_managerial_shifts, container, false);

<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> 0b0d0814c02803975dad9ac4de25abac281dc19b

        ArrayAdapter<CharSequence> adapter_month = ArrayAdapter.createFromResource(rootView.getContext(), R.array.month_array,
                android.R.layout.simple_spinner_item);
        adapter_month.setDropDownViewResource(android.R.layout.simple_spinner_item);
<<<<<<< HEAD

=======
=======
>>>>>>> 0b0d0814c02803975dad9ac4de25abac281dc19b
        btnStart = (Button)rootView.findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                spinner_month = (Spinner)rootView.findViewById(R.id.spinner_month);
                ArrayAdapter<CharSequence> adapter_month = ArrayAdapter.createFromResource(rootView.getContext(), R.array.month_array,
                        android.R.layout.simple_spinner_item);
                adapter_month.setDropDownViewResource(android.R.layout.simple_spinner_item);
                spinner_month.setAdapter(adapter_month);

                spinner_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                        adapterView.getItemAtPosition(pos);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                spinner_day = (Spinner)rootView.findViewById(R.id.spinner_day);

                ArrayAdapter<CharSequence> adapter_day = ArrayAdapter.createFromResource(rootView.getContext(), R.array.days_array,
                        android.R.layout.simple_spinner_item);
                adapter_day.setDropDownViewResource(android.R.layout.simple_spinner_item);
                spinner_day.setAdapter(adapter_day);

                spinner_day.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                        adapterView.getItemAtPosition(pos);
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                spinner_time = (Spinner)rootView.findViewById(R.id.spinner_time);
                ArrayAdapter<CharSequence> adapter_time = ArrayAdapter.createFromResource(rootView.getContext(), R.array.time_array,
                        android.R.layout.simple_spinner_item);
                adapter_time.setDropDownViewResource(android.R.layout.simple_spinner_item);
                spinner_time.setAdapter(adapter_time);

                spinner_time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                        adapterView.getItemAtPosition(pos);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });


            }
        });

        btnEnd = (Button)rootView.findViewById(R.id.btnEnd);
        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




            }
        });

<<<<<<< HEAD

=======
>>>>>>> 0b0d0814c02803975dad9ac4de25abac281dc19b
        btnInvite=(Button)rootView.findViewById(R.id.btnInvite);
        btnInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


<<<<<<< HEAD
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
=======
>>>>>>> 0b0d0814c02803975dad9ac4de25abac281dc19b

            }
        });


<<<<<<< HEAD
=======
>>>>>>> d086ed9b888e91d33568cd493bbb8fe8d52b89c0

>>>>>>> 0b0d0814c02803975dad9ac4de25abac281dc19b
        return rootView;
    }




<<<<<<< HEAD
=======


>>>>>>> 0b0d0814c02803975dad9ac4de25abac281dc19b
}
