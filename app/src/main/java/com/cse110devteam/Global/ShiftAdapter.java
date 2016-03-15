package com.cse110devteam.Global;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cse110devteam.R;

import java.util.Date;
import java.util.List;

/**
 * Created by anthonyaltieri on 3/11/16.
 */
public class ShiftAdapter extends RecyclerView.Adapter<ShiftAdapter.ViewHolder>{

    private List< Shift > mShifts;

    public ShiftAdapter( Context context, List< Shift > shifts )
    {
        mShifts = shifts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = R.layout.shift_item_man;
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position )
    {
        Shift shift = mShifts.get( position );
        holder.setFirst(shift.getFirstname());
        holder.setLast( shift.getLastname());
        Date start = shift.getStart();
        Date end = shift.getEnd();
        holder.setStart(Util.prettyHourMin(start));
        holder.setEnd( Util.prettyHourMin( end ) );
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
        private LinearLayout banner;

        private String colorVacant = "#ff9cc00";
        private String colorFilled = "#ffff4444";

        public ViewHolder( View view )
        {
            super( view );

            first = ( TextView ) view.findViewById( R.id.firstname );
            last = ( TextView ) view.findViewById( R.id.lastname );
            start = ( TextView ) view.findViewById( R.id.start );
            end = ( TextView ) view.findViewById( R.id.end );
            status = ( TextView ) view.findViewById( R.id.status );
            banner = (LinearLayout) view.findViewById( R.id.banner );

            if( status.getText().toString().equals("FILLED") )
            {
                banner.setBackgroundColor(Color.parseColor("#F44336"));
            }
            else
            {
                banner.setBackgroundColor( Color.parseColor( "#4CAF50" ) );
            }
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
            this.end.setText( status );
        }

    }
}
