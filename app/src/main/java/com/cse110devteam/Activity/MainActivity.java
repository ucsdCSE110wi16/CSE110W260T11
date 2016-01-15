package com.cse110devteam.Activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.cse110devteam.R;
import com.parse.Parse;

import java.util.zip.Inflater;


public class MainActivity extends Activity{
    /* Don't need this unless we are going to use local data store and I don't think
     * we need to.
     *
     * private String PARSE_APPLICATION_ID = "2FqCbEuIPBR6oiinIuClh8ewd9w0cDngdmtTuPgG";
     * private String PARSE_CLIENT_KEY = "RLULeJwGHFP35asTfd00tWAMC1oBNV7CJs22LPqR";
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* This is for local data store, but I don't think we need it for MVP
         *
         * Parse.enableLocalDatastore(this);
         * Parse.initialize(this, PARSE_APPLICATION_ID, PARSE_CLIENT_KEY);
         */
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
