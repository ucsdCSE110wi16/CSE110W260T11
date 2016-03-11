package com.cse110devteam.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import com.cse110devteam.Global.ContactAdapter;
import com.cse110devteam.Global.ContactInfo;
import com.cse110devteam.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anthonyaltieri on 3/8/16.
 */
public class ShiftList  extends Activity {

    private RecyclerView rv;
    private RecyclerView.Adapter mAdapter;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shift_list);


        rv = (RecyclerView) findViewById( R.id.cardList );
        final LinearLayoutManager llm = new LinearLayoutManager( this );

        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        ContactAdapter ca = new ContactAdapter(createList(30));
        rv.setAdapter(ca);
        rv.setVisibility(View.GONE);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

//        toolbar.setNavigationIcon( R.drawable.abc_ic_ab_back_mtrl_am_alpha );
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent( getApplicationContext(),
                                                    ManagerMain.class );
                        startActivity( intent );
                    }
                });
                thread.start();
            }
        });
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
