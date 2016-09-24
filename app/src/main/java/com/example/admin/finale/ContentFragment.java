package com.example.admin.finale;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ContentFragment extends Fragment implements View.OnClickListener {

    EditText amount;
    EditText category;
    PersonDatabaseHelper helper;
    account a1;
    private Calendar calendar;
    private TextView dateset;
    private int year, month, day;
    EditText dateofshop;
    Toolbar toolbar;
    Button b1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.content_fragment, container, false);
        toolbar = (Toolbar)v.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Add Expense");
        amount=(EditText)v.findViewById(R.id.amount);
        category=(EditText)v.findViewById(R.id.category);
        b1=(Button)v.findViewById(R.id.button2);
        long r_id=getArguments().getLong("id");
        a1=new account(r_id);
        dateofshop=(EditText)v.findViewById(R.id.dateText);
        helper=new PersonDatabaseHelper(this.getActivity());
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month + 1, day);


        dateofshop.setOnClickListener(this);
        b1.setOnClickListener(this);



        return v;



    }





    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(getActivity(), myDateListener, year, month, day);
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            showDate(arg1, arg2+1, arg3);
        }
    };
    private void showDate(int year, int month, int day) {
        dateofshop.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button2:
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Date dateObject;
                String dob_var=(dateofshop.getText().toString());
                try {
                    int Amount = Integer.parseInt(amount.getText().toString());
                    dateObject = formatter.parse(dob_var);
                    String dateofshop = new SimpleDateFormat("dd/MM/yyyy").format(dateObject);
                    String Category = category.getText().toString();
                    long id_3 = a1.getid(a1);
                    Cursor res = helper.getincome(id_3);
                    if (res.moveToFirst()) {
                        do {
                            int income = res.getInt(res.getColumnIndex(res.getColumnName(2)));
                            // do what ever you want here
                            a1.setincome(income);
                        } while (res.moveToNext());
                    }
                    res.close();


                    if (Amount <= a1.getincome()) {
                        helper.insertData1(Amount, Category, id_3,dateofshop);
                        int bal_left = (a1.getincome() - Amount);
                        helper.update(bal_left, id_3);

                    } else {
                        Toast.makeText(getActivity(), "income is low", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (java.text.ParseException e){

                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    Log.i("E11111111111", e.toString());

                }
                break;

            case R.id.dateText:
                Dialog dialog=onCreateDialog(999);
                if(dialog!=null){
                    dialog.show();
                }
                break;

        }





    }
}