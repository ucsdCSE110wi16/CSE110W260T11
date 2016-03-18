package com.cse110devteam.Activity;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.cse110devteam.Global.ShiftAdapter;
import com.cse110devteam.Models.Shift;
import com.cse110devteam.R;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by anthonyaltieri on 3/15/16.
 */
public class DayShiftList extends Activity{
    private ParseUser user;
    private ParseObject business;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private VerticalSpaceItemDeocration mItemDecoration;
    int type;

    private ArrayList< Shift > mShifts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView( R.layout.shift_list );
        boolean isManager = ( boolean ) getIntent().getExtras().getBoolean( "isManager" );
        Date selected = (Date) getIntent().getExtras().getSerializable( "dateSelected" );
        final int ssDay = selected.getDate();
        final int ssMonth = selected.getMonth();
        final int ssYear = selected.getYear();

        if ( isManager )
        {
            type = ShiftAdapter.TYPE_MANAGER;
        }
        else
        {
            type = ShiftAdapter.TYPE_EMPLOYEE;
        }

        mShifts = new ArrayList<>();

        mAdapter = new ShiftAdapter( getApplicationContext(), mShifts, type,
                user, business );
        mRecyclerView = ( RecyclerView ) findViewById( R.id.shiftlist );
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mItemDecoration = new VerticalSpaceItemDeocration( 30 );

        user = ParseUser.getCurrentUser();

        business = (ParseObject) user.get("business");
        String businessID = business.getObjectId();

        ParseQuery< ParseObject > businessQuery = new ParseQuery<ParseObject>( "Business" );
        businessQuery.getInBackground(businessID, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if ( e == null )
                {
                    try {
                        ArrayList< ParseObject > allShifts
                                = ( ArrayList<ParseObject> ) object.fetchIfNeeded().get( "shifts" );
                        if ( allShifts != null )
                        {
                            for ( ParseObject shift : allShifts )
                            {
                                ParseQuery<ParseObject> shiftQuery = new ParseQuery<ParseObject>( "Shift" );
                                shiftQuery.getInBackground(shift.getObjectId(), new GetCallback<ParseObject>() {
                                    @Override
                                    public void done(ParseObject object, ParseException e) {
                                        if ( object !=null )
                                        {
                                            Date shiftStart = (Date) object.get( "start" );
                                            int day = shiftStart.getDate();
                                            int month = shiftStart.getMonth();
                                            int year = shiftStart.getYear();

                                            Log.d( "day", "" + day );
                                            Log.d( "month", "" + month );
                                            Log.d( "year", "" + year );
                                            Log.d( "ssDay", "" + ssDay );
                                            Log.d( "ssMonth", "" + ssMonth );
                                            Log.d( "ssYear", "" + ssYear );

                                            int type = Shift.FILLED;
                                            if ( object.get( "employee" ) == null )
                                            {
                                                type = Shift.VACANT;
                                            }

                                            if ( ssDay == day && ssMonth == month && ssYear == year ) {
                                                if (mShifts == null)
                                                {
                                                    mShifts = new ArrayList< Shift >();
                                                }
                                                Shift shiftModel = new Shift.Builder( type )
                                                        .employee((ParseUser) object.get( "employee" ))
                                                        .business( business )
                                                        .id( object.getObjectId() )
                                                        .start((Date) object.get( "start" ))
                                                        .end((Date) object.get( "end" ))
                                                        .build();

                                                mShifts.add( shiftModel );
                                                mAdapter.notifyItemInserted( mShifts.size() - 1 );
                                                scrollToBottom();
                                            }

                                        }

                                    }
                                });


                            }

                        }
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                }
                else
                {
                    Log.d( "business query", e.toString() );
                }

            }
        });
    }

    private void scrollToBottom()
    {
        mRecyclerView.scrollToPosition( mAdapter.getItemCount() - 1 );
    }


    public class VerticalSpaceItemDeocration extends RecyclerView.ItemDecoration {

        private final int mSpaceHeight;

        public VerticalSpaceItemDeocration(int mSpaceHeight) {
            this.mSpaceHeight = mSpaceHeight;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            outRect.bottom = mSpaceHeight;
        }
    }
}
