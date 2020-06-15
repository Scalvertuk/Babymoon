package com.example.kitkatcw;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Feeding_Timer extends AppCompatActivity {
    //Defines the variables
    Chronometer feedtimer;
    private boolean runningfeed = false;
    private long stoppedfeedoffset;
    String feedduration;
    Context context = Feeding_Timer.this;


    // Defines the formats for months, years and the date
    SimpleDateFormat monthForm = new SimpleDateFormat("MMMM", Locale.ENGLISH);
    SimpleDateFormat yearForm = new SimpleDateFormat("yyyy", Locale.ENGLISH);
    SimpleDateFormat recordDateForm = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    SimpleDateFormat hourformat = new SimpleDateFormat("K:mm a ", Locale.ENGLISH);
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //on creation displays the feeding timer activity
        setContentView(R.layout.activity_feeding__timer);
        feedtimer = findViewById(R.id.FeedTimer);
    }

    // On Click methods for when each button is pressed
    public void onClickFeedStart (View view) {
    //Upon being clicked will check if the timer is already running. If false it will run
        if (!runningfeed){
            //Sets the timer to be accurate by removing the paused offset
            feedtimer.setBase(SystemClock.elapsedRealtime() - stoppedfeedoffset);
            runningfeed = true;
            //starts the chronometer
            feedtimer.start();

        }
    }

    public void onClickFeedStop(View view){
        //Upon being clicked will check if the timer is already running. If true it will run
        if (runningfeed){
            //Sets the timer to be accurate by removing the paused offset
            feedtimer.setBase(SystemClock.elapsedRealtime() - stoppedfeedoffset);
            runningfeed = true;
            //starts the chronometer
            feedtimer.start();
        }
    }
    public void onClickFeedReset(View view){
    //resets the offset and the chronometer to original state
        feedtimer.setBase(SystemClock.elapsedRealtime());
        stoppedfeedoffset = 0;
    }
    public void onClickFeedSave(View view){
        //collects the string of the chronometer
        feedduration = feedtimer.getText().toString();
        //Sets the year, month and date to the standardised format designed
        final String date = recordDateForm.format(Calendar.getInstance().getTime());
        final String month = monthForm.format(Calendar.getInstance().getTime());
        final String year = yearForm.format(Calendar.getInstance().getTime());
        //gets the current time as a string to be added to the record
        String Opentime = hourformat.format(Calendar.getInstance(Locale.ENGLISH).getTime());
        //calls the save record method below passing all the variables required
        SaveRecord("Feed", feedduration,Opentime,date,month,year);
        //Resets the view to original state
        setContentView(R.layout.activity_feeding__timer);
    }



    private void SaveRecord (String feed_sleep,String duration, String time, String date, String month, String year){
        //Creates new instance of the SQL Database class
        SQL_DB_Open dbOpen = new SQL_DB_Open(context);
        //Makes the database writable
        SQLiteDatabase records = dbOpen.getWritableDatabase();
        //runs the Save Record class in SQL_DB_Open
        dbOpen.SaveRecord(feed_sleep,duration,time,date,month,year, records);
        //closes the instance
        dbOpen.close();
        //Displays message saying Feed saved on screen
        Toast.makeText(context, "Feed Saved", Toast.LENGTH_SHORT).show();

    }
}
