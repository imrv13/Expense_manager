package com.example.admin.finale;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;



import com.example.R;

/**
 * Created by ADMIN on 10/4/2015.
 */
public class Overview extends android.support.v4.app.Fragment {
    PersonDatabaseHelper helper13;
    SimpleCursorAdapter over_adapter;
    ListView lv;
    TextView t7;
    TextView t8;
    account a11;
    private static final String TAG = PersonDatabaseHelper.class.getSimpleName();


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.overview, container, false);
        helper13=new PersonDatabaseHelper(getActivity());
        lv=(ListView)v.findViewById(R.id.l5);
        t7=(TextView)v.findViewById(R.id.textView7);
        t8=(TextView)v.findViewById(R.id.textView8);
        long rid=getArguments().getLong("id");
        Log.d(TAG, "id of long " +rid);
        a11=new account(rid);
        Cursor c1=helper13.getrecentdata(rid);
        String From [] = new String[]{helper13.TABLE1_COL1,helper13.TABLE1_COL2,helper13.TABLE1_COL4};
        int To []=new int[]{R.id.textView7,R.id.textView8,R.id.textView2};
        over_adapter=new SimpleCursorAdapter(this.getActivity(),R.layout.over_item,c1,From,To,0);
        lv.setAdapter(over_adapter);
        Cursor ui=helper13.getincome(rid);
        if (ui.moveToFirst()) {
            do {
                int income = ui.getInt(ui.getColumnIndex(ui.getColumnName(2)));
                // do what ever you want here
                a11.setincome(income);
            } while (ui.moveToNext());
        }
        ui.close();



        return v;
    }
}
