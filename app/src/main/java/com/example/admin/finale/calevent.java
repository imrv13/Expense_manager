package com.example.admin.finale;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.R;


public class calevent extends Activity {


    private PersonDatabaseHelper databaseHelper1;
    SimpleCursorAdapter cal_adapter;
    ListView l1;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.caleventlist);
        databaseHelper1 = new PersonDatabaseHelper(this);
        l1 = (ListView) findViewById(R.id.listView1);

        Intent in = getIntent();
        Bundle bundle2 = getIntent().getExtras();
        Long value = bundle2.getLong("myint");
        String tdate1 = bundle2.getString("tdate");
        Log.v("in calevent", "" + value + tdate1);

        displaylist(value,tdate1);






    }
    public void displaylist(Long value,String tdate1){

        Cursor c=databaseHelper1.getalldata1(value,tdate1);
        String from [] = new String[]{databaseHelper1.TABLE1_COL1,databaseHelper1.TABLE1_COL2};
        int to [] = new int[] {R.id.t3,R.id.t4};
        cal_adapter=new SimpleCursorAdapter(this,R.layout.calevent_item,c, from, to, 0);
        l1.setAdapter(cal_adapter);

    }
}
