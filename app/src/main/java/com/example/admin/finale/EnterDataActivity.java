package com.example.admin.finale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EnterDataActivity extends AppCompatActivity {

    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;

    EditText editTextPersonName;
    EditText editTextPersionPIN;
    EditText startdateofacc;
    Toolbar toolbar;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_data);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Create Account");

        editTextPersonName = (EditText) findViewById(R.id.et_person_name);
        editTextPersionPIN = (EditText) findViewById(R.id.et_person_pin);
        startdateofacc=(EditText)findViewById(R.id.startdate);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month + 1, day);
    }
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca", Toast.LENGTH_SHORT)
                .show();
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
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
        startdateofacc.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }


    public void onClickAdd (View btnAdd) {

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date dateObject;
        String dob_var=(startdateofacc.getText().toString());
    try {
        String personName = editTextPersonName.getText().toString();
        int personincome = Integer.parseInt(editTextPersionPIN.getText().toString());
        dateObject = formatter.parse(dob_var);
        String date = new SimpleDateFormat("dd/MM/yyyy").format(dateObject);

        if ( personName.length() != 0 ) {

            Intent newIntent = getIntent();
            newIntent.putExtra("tag_person_name", personName);
            newIntent.putExtra("tag_person_pin", personincome);
            newIntent.putExtra("tag_person_sdate",date);

            this.setResult(RESULT_OK, newIntent);
            Toast.makeText(getBaseContext(),date, Toast.LENGTH_LONG).show();

            finish();
        }

    } catch (java.text.ParseException e){
        // TODO Auto-generated catch block
        e.printStackTrace();
        Log.i("E11111111111", e.toString());
    }


    }
}