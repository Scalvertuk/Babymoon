package com.example.kitkatcw;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class SQL_DB_Open extends SQLiteOpenHelper {

    private static final String Create_Record_Table = " CREATE TABLE " + SQL_DB_Structure.Feed_Sleep_Table_Name + " ( " + " ID " + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            SQL_DB_Structure.Feed_Sleep + " TEXT, " + SQL_DB_Structure.Duration + " TEXT, " + SQL_DB_Structure.Time + " TEXT, " + SQL_DB_Structure.Date + " TEXT, " + SQL_DB_Structure.Month + " TEXT, "
            + SQL_DB_Structure.Year + " TEXT) ";

    public static final String Drop_Record_Table = " DROP TABLE IF EXISTS " + SQL_DB_Structure.Feed_Sleep_Table_Name;


    public SQL_DB_Open(@Nullable Context context) {
        super(context, SQL_DB_Structure.DB_Name, null, SQL_DB_Structure.DB_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Creates the record database upon creation of the class instance
        db.execSQL(Create_Record_Table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Drop_Record_Table);
        onCreate(db);
    }

    //Method that adds information to the database ready for viewing later
    public void SaveRecord(String Feed_Sleep, String Duration, String Time, String Date, String Month, String Year, SQLiteDatabase Feed_Sleep_Records) {

        ContentValues contentValues = new ContentValues();
        //Selects the values required for the saving of a record
        contentValues.put(SQL_DB_Structure.Feed_Sleep, Feed_Sleep);
        contentValues.put(SQL_DB_Structure.Duration, Duration);
        contentValues.put(SQL_DB_Structure.Time, Time);
        contentValues.put(SQL_DB_Structure.Date, Date);
        contentValues.put(SQL_DB_Structure.Month, Month);
        contentValues.put(SQL_DB_Structure.Year, Year);
        Feed_Sleep_Records.insert(SQL_DB_Structure.Feed_Sleep_Table_Name, null, contentValues);
    }
    //Creates the cursor that reads records based on the date
    public Cursor ReadRecord(String Date, SQLiteDatabase Feed_Sleep_Records) {
        //Looks for the variables to be read.
        String[] Projections = {SQL_DB_Structure.Feed_Sleep, SQL_DB_Structure.Duration, SQL_DB_Structure.Time, SQL_DB_Structure.Date, SQL_DB_Structure.Month, SQL_DB_Structure.Year};
        String Selection = SQL_DB_Structure.Date + "=?";
        //makes the method constrained by the date
        String[] SelectionArgs = {Date};

        return Feed_Sleep_Records.query(SQL_DB_Structure.Feed_Sleep_Table_Name, Projections, Selection, SelectionArgs, null, null, null);
    }

    //Creates the cursor that reads records based on month and year.
    public Cursor ReadRecordMonth(String Month, String Year, SQLiteDatabase Feed_Sleep_Records) {
        String[] Projections = {SQL_DB_Structure.Feed_Sleep, SQL_DB_Structure.Duration, SQL_DB_Structure.Time, SQL_DB_Structure.Date, SQL_DB_Structure.Month, SQL_DB_Structure.Year};
        String Selection = SQL_DB_Structure.Month + "=? and " + SQL_DB_Structure.Year + "=?";
        String[] SelectionArgs = {Month, Year};

        return Feed_Sleep_Records.query(SQL_DB_Structure.Feed_Sleep_Table_Name, Projections, Selection, SelectionArgs, null, null, null);
    }

    //scans the record list for the matching parts and deletes them
    public void deleteRecord (String Feed_Sleep, String duration, String time, String date, SQLiteDatabase database){
        //selects the following fields in the Database
        String Selection = SQL_DB_Structure.Feed_Sleep + "=? and " + SQL_DB_Structure.Duration + "=? and " + SQL_DB_Structure.Time + "=? and " + SQL_DB_Structure.Date + "=?";
        //Requires these arguments
        String[] SelectionArgs = {Feed_Sleep,duration,time,date};
        // deletes the information from the database
        database.delete(SQL_DB_Structure.Feed_Sleep_Table_Name, Selection, SelectionArgs);
    }

}
