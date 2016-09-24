package com.example.admin.finale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.sql.Date;

public class PersonDatabaseHelper {

    private static final String TAG = PersonDatabaseHelper.class.getSimpleName();

    // database configuration
    // if you want the onUpgrade to run then change the database_version
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "mydatabase71.db";

    // table configuration
    private static final String TABLE_NAME = "person_table";         // Table name
    private static final String PERSON_TABLE_COLUMN_ID = "_id";     // a column named "_id" is required for cursor
    private static final String PERSON_TABLE_COLUMN_NAME = "person_name";
    private static final String PERSON_TABLE_COLUMN_PIN = "person_pin";
    private static final String PERSON_TABLE_DATE = "person_date";




    // table configuration
    private static final String TABLE_NAME1 = "Expense";         // Table name
    private static final String TABLE1_ID = "_id";     // a column named "_id" is required for cursor
    static final String TABLE1_COL1 = "amount";
    static final String TABLE1_COL2 = "category";
    private static final String TABLE1_COL3 ="myrowid";
     static final String TABLE1_COL4 ="dos";










    private DatabaseOpenHelper openHelper;
    private SQLiteDatabase database;
    Context context;

    // this is a wrapper class. that means, from outside world, anyone will communicate with PersonDatabaseHelper,
    // but under the hood actually DatabaseOpenHelper class will perform database CRUD operations
    public PersonDatabaseHelper(Context aContext) {

        openHelper = new DatabaseOpenHelper(aContext);
        database = openHelper.getWritableDatabase();
        this.context=aContext;
    }

    public void insertData (String aPersonName, int aPersonPin ,String date) {

        // we are using ContentValues to avoid sql format errors

        ContentValues contentValues = new ContentValues();

        contentValues.put(PERSON_TABLE_COLUMN_NAME, aPersonName);
        contentValues.put(PERSON_TABLE_COLUMN_PIN, aPersonPin);
        contentValues.put(PERSON_TABLE_DATE,date);

        database.insert(TABLE_NAME, null, contentValues);
    }
    public void insertData1 (int amount, String category,long ID,String dateos) {

        // we are using ContentValues to avoid sql format errors

        ContentValues contentValues = new ContentValues();

        contentValues.put(TABLE1_COL1, amount);
        contentValues.put(TABLE1_COL2, category);
        contentValues.put(TABLE1_COL3, ID);
        contentValues.put(TABLE1_COL4, dateos);
        Log.d(TAG, "data inserted: " + TABLE1_ID);

        long rowInserted = database.insert(TABLE_NAME1, null, contentValues);
        if(rowInserted != -1)
            Toast.makeText(context, "New row added, row id: " + rowInserted, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Something wrong", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "date inserted: " + TABLE1_COL4);


    }

    public Cursor getincome(long id2){
        String inc="SELECT * FROM "+TABLE_NAME+" WHERE "+PERSON_TABLE_COLUMN_ID+"="+id2;
        Cursor  cursor = database.rawQuery(inc, null);
        return cursor;

    }
    public int getexpense(long id){
        String query = "SELECT TOTAL ("+TABLE1_COL1+") FROM " + TABLE_NAME1 +" WHERE "+TABLE1_COL3+"="+id;


        Cursor c = database.rawQuery(query, null);

        //Add in the movetofirst etc here? see SO
        c.moveToFirst();
        int i=c.getInt(0);

        return i;

    }

    public int getrowcount(long id) {
        String countQuery = "SELECT  * FROM " + TABLE_NAME1+" WHERE "+TABLE1_COL3+"="+id;

        Cursor cursor = database.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }




    public Cursor getAllData () {

        String buildSQL = "SELECT * FROM " + TABLE_NAME;

        Log.d(TAG, "getAllData SQL: " + buildSQL);


        return database.rawQuery(buildSQL, null);
    }

    public Cursor getalldata1(Long id,String tdate){


        String buildSQL11 = "SELECT * FROM " +TABLE_NAME1+" WHERE "+TABLE1_COL4+"="+"'"+tdate+"'"+" AND "+TABLE1_COL3+"="+id;

        Log.d(TAG, "getAllData SQL11: " + buildSQL11);


        return database.rawQuery(buildSQL11, null);

    }

    public Cursor getrecentdata(Long id){
        String builsql12="SELECT * FROM (SELECT * FROM  "+ TABLE_NAME1 +"  WHERE "+ TABLE1_COL3 +" = "+ id +" LIMIT 10 ) ORDER BY "+ TABLE1_COL4 +" DESC ";
        Log.d(TAG, "getAllData SQL11: " + builsql12);
        return database.rawQuery(builsql12, null);

    }
    public void delete_byID(long id){

        database.delete(TABLE_NAME, PERSON_TABLE_COLUMN_ID+"="+id, null);
        Log.d(TAG, "delete data SQL: ");
    }

    public void update(int uincome,long id_3){
        String sql = "UPDATE "+TABLE_NAME +" SET " +PERSON_TABLE_COLUMN_PIN+ " = '"+uincome+"' WHERE "+PERSON_TABLE_COLUMN_ID+ " = "+id_3;
        database.execSQL(sql);
    }



    // this DatabaseOpenHelper class will actually be used to perform database related operation

    private class DatabaseOpenHelper extends SQLiteOpenHelper {

        public DatabaseOpenHelper(Context aContext) {
            super(aContext, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            // Create your tables here

            String buildSQL = "CREATE TABLE " + TABLE_NAME + "( " + PERSON_TABLE_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    PERSON_TABLE_COLUMN_NAME + " TEXT, " + PERSON_TABLE_COLUMN_PIN + " INT, "+ PERSON_TABLE_DATE +" TEXT )";

            sqLiteDatabase.execSQL(buildSQL);

            String SQL = "CREATE TABLE " + TABLE_NAME1 + "( " + TABLE1_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TABLE1_COL1 + " INT, " + TABLE1_COL2 + " TEXT ,"+TABLE1_COL3+ " INT ,"+TABLE1_COL4+" TEXT )";
            Log.d(TAG, "data CREATED: "+TABLE1_ID );

            sqLiteDatabase.execSQL(SQL);



        }

        public void getEntry(int position)
        {

        }


        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
            // Database schema upgrade code goes here

            String buildSQL = "DROP TABLE IF EXISTS " + TABLE_NAME;

            Log.d(TAG, "onUpgrade SQL: " + buildSQL);

            sqLiteDatabase.execSQL(buildSQL);       // drop previous table

            onCreate(sqLiteDatabase);               // create the table from the beginning
        }
    }
}