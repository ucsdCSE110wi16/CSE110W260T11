package com.cse110devteam.Global;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cse110devteam.Models.Shift;
import com.cse110devteam.R;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by anthonyaltieri on 3/11/16.
 */
public class ShiftAdapter extends RecyclerView.Adapter<ShiftAdapter.ViewHolder>{
    public static int TYPE_EMPLOYEE = 0;
    public static int TYPE_MANAGER = 1;

    private List<Shift> mShifts;
    private int type;

    private Button requestShift;
    private TextView status;

    private Context context;

    private ParseUser user;
    private ParseObject business;

    public ShiftAdapter( Context context, List< Shift > shifts, int type, ParseUser user,
                         ParseObject business )
    {
        mShifts = shifts;
        this.type = type;
        this.context = context;
        this.user = user;
        this.business = business;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = R.layout.shift_item_man;
        if ( type == TYPE_EMPLOYEE)
        {
            layout = R.layout.shift_item_emp;
        }
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(layout, parent, false);


        user = ParseUser.getCurrentUser();
        business = (ParseObject) user.get( "business" );


        final TextView fName = ( TextView ) v.findViewById( R.id.firstname );
        final TextView lName = ( TextView ) v.findViewById( R.id.lastname );
        final TextView tvOid = (TextView) v.findViewById(R.id.shift_id);
        final LinearLayout banner = (LinearLayout) v.findViewById(R.id.banner);
        final TextView status = (TextView) v.findViewById(R.id.status);

        if ( type == TYPE_EMPLOYEE )
        {
            requestShift = ( Button ) v.findViewById( R.id.request );


            requestShift.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("requestShift", "onClick");
                    Log.d("requestShift", "testtest");
                    String oid = tvOid.getText().toString();
                    Log.d("requestShift", "oid: " + oid);



                    ParseQuery< ParseObject > shiftQuery = new ParseQuery<ParseObject>( "Shift" );
                    shiftQuery.getInBackground(oid, new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject object, ParseException e) {
                            if ( object != null )
                            {
                                ParseObject emp = (ParseObject) object.get("employee");
                                if ( emp != null )
                                {
                                    Toast toast = Toast.makeText( context, "Shift is taken",
                                            Toast.LENGTH_SHORT );
                                    toast.setGravity( Gravity.BOTTOM, 0, 0 );
                                    toast.show();
                                }
                                else {
                                    Toast toast = Toast.makeText(context, "Signed up!",
                                            Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                                    toast.show();

                                    if (fName != null) {
                                        fName.setText(
                                                Util.firstLetterCapitalize(user.getString("firstname")));

                                    }
                                    if (lName != null) {
                                        lName.setText(
                                                Util.firstLetterCapitalize(user.getString("lastname")));

                                    }
                                    if (banner != null) {
                                        banner.setBackgroundColor(Color.parseColor("#F44336"));
                                    }

                                    if (status != null)
                                    {
                                        status.setText("FILLED");
                                    }

                                    object.put( "employee", user );
                                    object.saveInBackground();



                                }

                            }
                        }
                    });


                }
            });
        }

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder( final ViewHolder holder, int position )
    {
        Shift shift = mShifts.get(position);
        Date start = shift.getStart();
        Date end = shift.getEnd();
        holder.setStart(Util.prettyHourMin(start));
        holder.setEnd(Util.prettyHourMin(end));

        String oId = shift.getId();
        holder.setID(oId);


        int day = start.getDate();
        int month = start.getMonth() + 1;
        int year = start.getYear() + 1900;

        String sDMY = "" + day + "/" + month + "/" + year;
        holder.setDMY(sDMY);



        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>( "Shift" );
        Log.d("oid about to be queried", oId + "");
        query.getInBackground(oId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if ( object != null )
                {
                    ParseObject emp = (ParseObject) object.get("employee");
                    String status = null;
                    int stat = 0;

                    if (emp != null) {
                        try {
                            holder.setFirst((String) emp.fetchIfNeeded().get("firstname"));
                            holder.setLast((String) emp.fetchIfNeeded().get("lastname"));
                            status = "FILLED";
                            stat = Shift.FILLED;
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }
                    } else {
                        status = "VACANT";
                        stat = Shift.VACANT;
                    }

                    holder.setStatus(status);
                    holder.setColor(stat);


                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mShifts.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView first;
        private TextView last;
        private TextView start;
        private TextView end;
        private TextView status;
        private TextView dmy;
        private TextView id;
        private LinearLayout banner;

        public ViewHolder( View view )
        {
            super( view );

            first = ( TextView ) view.findViewById( R.id.firstname );
            last = ( TextView ) view.findViewById( R.id.lastname );
            start = ( TextView ) view.findViewById( R.id.start );
            end = ( TextView ) view.findViewById( R.id.end );
            status = ( TextView ) view.findViewById( R.id.status );
            dmy = ( TextView ) view.findViewById( R.id.date );
            banner = (LinearLayout) view.findViewById( R.id.banner );
            id = ( TextView ) view.findViewById( R.id.shift_id );



        }

        public void setID( String id )
        {
            if ( id == null ) return;
            this.id.setText( id );
        }

        public void setFirst( String first )
        {
            if ( first == null ) return;
            this.first.setText(first);
        }

        public void setLast( String last )
        {
            if ( last == null ) return;
            this.last.setText(last);
        }

        public void setStart( String start )
        {
            if ( start == null ) return;
            this.start.setText(start);
        }

        public void setEnd( String end )
        {
            if ( end == null ) return;
            this.end.setText(end);
        }

        public void setStatus( String status )
        {
            if ( status == null ) return;
            this.status.setText( status );
        }

        public void setDMY( String dmy )
        {
            if ( dmy == null ) return;
            this.dmy.setText( dmy );
        }

        public void setColor( int status )
        {
            if( status == Shift.FILLED )
            {
                banner.setBackgroundColor(Color.parseColor("#F44336"));
            }
            else
            {
                banner.setBackgroundColor( Color.parseColor( "#4CAF50" ) );
            }

        }

    }
}
