package com.example.admin.finale;

import android.app.Activity;
import android.database.Cursor;


public class account extends Activity {

    PersonDatabaseHelper helper9;
    long id;
    int income;
    String name;


    public account(long id) {
        this.id=id;
    }

   public long getid(account a){
       return a.id;
   }
    public void setincome(int inc){

        this.income=inc;
    }
    public void setname(String name){
        this.name=name;
    }
    public String getname(account a){
        return a.name;
    }

    public int getincome(){
        return this.income;
    }





}
