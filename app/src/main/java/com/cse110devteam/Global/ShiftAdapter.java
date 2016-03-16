package com.cse110devteam.Global;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
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
        if ( type == TYPE_EMPLOYEE )
        {
            layout = R.layout.shift_item_emp;
        }
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(layout, parent, false);

        TextView tvOid = (TextView) v.findViewById(R.id.shift_id);
        final String oid = tvOid.getText().toString();

        final TextView fName = ( TextView ) v.findViewById( R.id.firstname );
        final TextView lName = ( TextView ) v.findViewById( R.id.lastname );

        if ( type == TYPE_EMPLOYEE )
        {
            requestShift = ( Button ) v.findViewById( R.id.request );
            status = ( TextView ) v.findViewById( R.id.status );

            String statusText = status.getText().toString().toLowerCase();
            final boolean isVacant = ( statusText.equals( "vacant" ) );


            requestShift.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ( !isVacant )
                    {
                        Toast toast = Toast.makeText( context, "Shift is taken",
                                Toast.LENGTH_SHORT );
                        toast.setGravity( Gravity.BOTTOM, 0, 0 );
                    }

                    ParseQuery< ParseObject > shiftQuery = new ParseQuery<ParseObject>( "Shift" );
                    shiftQuery.getInBackground(oid, new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject object, ParseException e) {
                            Toast toast = Toast.makeText( context, "Signed up!",
                                    Toast.LENGTH_SHORT );
                            toast.setGravity( Gravity.BOTTOM, 0, 0 );
                            toast.show();

                            fName.setText(
                                    Util.firstLetterCapitalize( user.getString("firstname") ));
                            lName.setText(
                                    Util.firstLetterCapitalize( user.getString("lastname") ));

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
        holder.setFirst(shift.getFirstname());
        holder.setLast(shift.getLastname());
        Date start = shift.getStart();
        Date end = shift.getEnd();
        holder.setStart(Util.prettyHourMin(start));
        holder.setEnd(Util.prettyHourMin(end));

        String oId = shift.getId();
        holder.setID( oId );

        int day = start.getDate();
        int month = start.getMonth() + 1;
        int year = start.getYear();

        String sDMY = "" + day + "/" + month + "/" + year;
        holder.setDMY( sDMY );

        String status = null;
        switch ( shift.getStatus() )
        {
            case Shift.FILLED:
                status = "FILLED";


            case Shift.VACANT:
                status = "VACANT";
        }
        holder.setStatus(status);
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


            if( status.getText().toString().equals("FILLED") )
            {
                banner.setBackgroundColor(Color.parseColor("#F44336"));
            }
            else
            {
                banner.setBackgroundColor( Color.parseColor( "#4CAF50" ) );
            }

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

    }
}
