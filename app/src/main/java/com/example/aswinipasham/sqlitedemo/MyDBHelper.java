package com.example.aswinipasham.sqlitedemo;

/**
 * Created by aswinipasham on 8/18/16.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by aswinipasham on 8/17/16.
 */
public class MyDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME= "mydb.db";
    private static final int Version = 1;

    public static final String TABLE_NAME= "employees";
    public static final String ID= "id";
    public static final String FIRST_NAME= "fName";
    public static final String LAST_NAME= "lName";
    public  static final String Address= "address";
    public static final String Salary= "salary";

    SQLiteDatabase myDB;


    public MyDBHelper(Context context) {
        super(context, DB_NAME, null, Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String queryTable = " Create Table " + TABLE_NAME + "( " +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FIRST_NAME + " TEXT NOT NULL, " +
                LAST_NAME + " TEXT NOT NULL, " +
                Address + " TEXT NOT NULL, " +
                Salary + " Real NOT NULL " +
                ")" ;

        db.execSQL(queryTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void openDB(){
        myDB = getWritableDatabase();
    }

    public void closeDB(){
        if(myDB !=null && myDB.isOpen())
        {
            myDB.close();
        }
    }

    public long insert(int id, String fName, String lName, String address, Double salary)
    {
        ContentValues values = new ContentValues();
        if(id!= 1) {
            values.put(ID, id);
            values.put(FIRST_NAME, fName);
            values.put(LAST_NAME, lName);
            values.put(Address, address);
            values.put(Salary, salary);
        }

        return myDB.insert(TABLE_NAME, null, values);

    }

    public long update(int id, String fName, String lName, String address, Double salary)
    {
        ContentValues values = new ContentValues();
        if(id!= 1) {
            values.put(ID, id);
            values.put(FIRST_NAME, fName);
            values.put(LAST_NAME, lName);
            values.put(Address, address);
            values.put(Salary, salary);
        }

        String where = ID + "= " + id;

        return myDB.update(TABLE_NAME, values,where, null );

    }

    public long delete(int id)
    {

        String where = ID + "= " + id;

        return myDB.delete(TABLE_NAME,where, null);
    }

    public Cursor getAllRecords()
    {
        //myDB.query(TABLE_NAME, null, null, null, null, null,null);

        String query = "SELECT * from " + TABLE_NAME;
        return myDB.rawQuery(query, null);
    }

    public Cursor getDataBasedOnQuery(String query)
    {
        return myDB.rawQuery(query, null);
    }
}
