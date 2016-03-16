package com.cse110devteam.Activity;

import android.app.Activity;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.cse110devteam.Models.Shift;
import com.cse110devteam.Global.ShiftAdapter;
import com.cse110devteam.Global.TypefaceGenerator;
import com.cse110devteam.R;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by anthonyaltieri on 3/8/16.
 */
public class ShiftList  extends Activity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<Shift> mShifts = new ArrayList< Shift >();
    private ArrayList< ParseObject > shifts = null;
    private VerticalSpaceItemDeocration mItemDecoration;

    private Toolbar toolbar;


    private Typeface roboto;
    private Typeface robotoBlack;
    private Typeface robotoMedium;

    private ParseUser user;
    private ParseObject business;


    @Override
    public void onCreate( Bundle bundle )
    {
        super.onCreate(bundle);

        setContentView(R.layout.shift_list);

        mAdapter = new ShiftAdapter( getApplicationContext(), mShifts, ShiftAdapter.TYPE_MANAGER,
                user, business );
        mRecyclerView = ( RecyclerView ) findViewById( R.id.shiftlist );
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mItemDecoration = new VerticalSpaceItemDeocration( 30 );

        user = ParseUser.getCurrentUser();
        business = (ParseObject) user.get("business");
        if ( business != null )
        {
            try {
                shifts = (ArrayList<ParseObject>) business.fetchIfNeeded().get("shifts");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if ( shifts != null && shifts.size() > 0 )
            {
                fillShifts( shifts );
            }
        }

        // Get typefaces
        roboto = TypefaceGenerator.get("roboto", getAssets() );
        robotoBlack = TypefaceGenerator.get( "robotoBlack", getAssets() );
        robotoMedium = TypefaceGenerator.get( "robotoMedium", getAssets() );
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

    private void addShift( ParseUser employee, Date start, Date end )
    {
        int type = ( employee == null ) ? Shift.VACANT : Shift.FILLED;
        if ( employee != null )
        {
            mShifts.add( new Shift.Builder( type )
                    .employee( employee )
                    .business( business )
                    .start( start )
                    .end( end )
                    .build());
            mAdapter.notifyItemInserted( mShifts.size() - 1 );
        }
        else
        {
            mShifts.add( new Shift.Builder( type )
                    .business(business)
                    .start( start )
                    .end( end )
                    .build());
            mAdapter.notifyItemInserted( mShifts.size() - 1 );

        }
        scrollToBottom();
    }

    private void scrollToBottom()
    {
        mRecyclerView.scrollToPosition( mAdapter.getItemCount() - 1 );
    }

    private void fillShifts( ArrayList< ParseObject > shifts )
    {
        if ( shifts == null ) return;

        for ( ParseObject shift : shifts )
        {
            try {
                ParseUser employee = ( ParseUser ) shift.fetchIfNeeded().get( "employee" );
                boolean isVacant = ( employee == null );
                Date start = ( Date ) shift.fetchIfNeeded().get( "start" );
                Date end = ( Date ) shift.fetchIfNeeded().get( "end" );
                addShift( employee, start, end );

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
