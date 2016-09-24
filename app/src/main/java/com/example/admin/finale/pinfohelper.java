package com.example.admin.finale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class pinfohelper {

    private static final String TAG = pinfohelper.class.getSimpleName();

    // database configuration
    // if you want the onUpgrade to run then change the database_version
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "mydatabase3.db";

    // table configuration
    private static final String TABLE_NAME1 = "Expense";         // Table name
    private static final String TABLE1_ID = "_id1";     // a column named "_id" is required for cursor
    private static final String TABLE1_COL1 = "amount";
    private static final String TABLE1_COL2 = "category";
    private static final String TABLE1_COL3 ="Id";







    private DataOpenHelper openHelper;
    private SQLiteDatabase database;
    Context context;

    // this is a wrapper class. that means, from outside world, anyone will communicate with PersonDatabaseHelper,
    // but under the hood actually DatabaseOpenHelper class will perform database CRUD operations
    public pinfohelper(Context aContext) {

        openHelper = new DataOpenHelper(aContext);
        database = openHelper.getWritableDatabase();
        this.context=aContext;
    }

    public void insertData1 (int amount, String category) {

        // we are using ContentValues to avoid sql format errors

        ContentValues contentValues = new ContentValues();

        contentValues.put(TABLE1_COL1, amount);
        contentValues.put(TABLE1_COL2, category);
        Log.d(TAG, "data inserted: " + TABLE1_ID);

        long rowInserted = database.insert(TABLE_NAME1, null, contentValues);
        if(rowInserted != -1)
            Toast.makeText(context, "New row added, row id: " + rowInserted, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Something wrong", Toast.LENGTH_SHORT).show();

    }


    public Cursor getAllData1 () {

        String buildSQL = "SELECT * FROM " + TABLE_NAME1;

        Log.d(TAG, "getAllData SQL: " + buildSQL);


        return database.rawQuery(buildSQL, null);
    }
    public void delete_byID1(long id){


    }



    // this DatabaseOpenHelper class will actually be used to perform database related operation

    private class DataOpenHelper extends SQLiteOpenHelper {

        public DataOpenHelper(Context aContext) {
            super(aContext, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            // Create your tables here

            String buildSQL = "CREATE TABLE " + TABLE_NAME1 + "( " + TABLE1_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TABLE1_COL1 + " INT, " + TABLE1_COL2 + " TEXT ,"+TABLE1_COL3+ "INT)";
            Log.d(TAG, "data CREATED: "+TABLE1_ID );

            sqLiteDatabase.execSQL(buildSQL);



        }

        public void getEntry(int position)
        {

        }


        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
            // Database schema upgrade code goes here

            String buildSQL = "DROP TABLE IF EXISTS " + TABLE_NAME1;

            Log.d(TAG, "onUpgrade SQL: " + buildSQL);

            sqLiteDatabase.execSQL(buildSQL);       // drop previous table

            onCreate(sqLiteDatabase);               // create the table from the beginning
        }
    }
}
