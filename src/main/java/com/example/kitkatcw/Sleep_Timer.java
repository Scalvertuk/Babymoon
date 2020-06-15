package com.example.kitkatcw;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Sleep_Timer extends AppCompatActivity {


    //Defines the variables
    private boolean runningsleep = false;
    private long stoppedsleepoffset;
    Chronometer sleeptimer;
    Context context = Sleep_Timer.this;

    // Defines the formats for months, years and the date
    SimpleDateFormat monthForm = new SimpleDateFormat("MMMM", Locale.ENGLISH);
    SimpleDateFormat yearForm = new SimpleDateFormat("yyyy", Locale.ENGLISH);
    SimpleDateFormat recordDateForm = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    SimpleDateFormat hourformat = new SimpleDateFormat("K:mm a ", Locale.ENGLISH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //on creation displays the sleeping timer activity
        setContentView(R.layout.activity_sleep__timer);
        sleeptimer = findViewById(R.id.sleeptimer);
    }

    // On Click methods for when each button is pressed
    public void onClickSleepStart(View view){
        //Upon being clicked will check if the timer is already running. If false it will run
        if (!runningsleep){
            //Sets the timer to be accurate by removing the paused offset
            sleeptimer.setBase(SystemClock.elapsedRealtime() - stoppedsleepoffset);
            runningsleep = true;
            //starts the chronometer
            sleeptimer.start();
        }
    }
    public void onClickSleepStop(View view){
        //Upon being clicked will check if the timer is already running. If true it will run
        if (runningsleep) {
            //calculates the offset of the chronometer for later use
            stoppedsleepoffset = SystemClock.elapsedRealtime() - sleeptimer.getBase();
            runningsleep = false;
            // stops the chronometer
            sleeptimer.stop();

        }
    }
    public void onClickSleepReset(View view){
        //resets the offset and the chronometer to original state
        sleeptimer.setBase(SystemClock.elapsedRealtime());
        stoppedsleepoffset = 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onClickSleepSave(View view){
        //Sets the year, month and date to the standardised format designed
        final String date = recordDateForm.format(Calendar.getInstance().getTime());
        final String month = monthForm.format(Calendar.getInstance().getTime());
        final String year = yearForm.format(Calendar.getInstance().getTime());
        //collects the string of the chronometer
        String duration = sleeptimer.getText().toString();
        String OpenTime = hourformat.format(Calendar.getInstance(Locale.ENGLISH).getTime());
        //calls the save record method below passing all the variables required
        SaveRecord("Sleep",duration,OpenTime,date,month,year);
        //Resets the view to original state
        sleeptimer.setBase(SystemClock.elapsedRealtime());
        stoppedsleepoffset = 0;
    }

    private void SaveRecord (String feed_sleep,String duration, String time, String date, String month, String year){
        //Creates new instance of the SQL Database class
        SQL_DB_Open dbOpen = new SQL_DB_Open(context);
        //Makes the database writable
        SQLiteDatabase record = dbOpen.getWritableDatabase();
        //runs the Save Record class in SQL_DB_Open
        dbOpen.SaveRecord(feed_sleep,duration,time,date,month,year, record);
        dbOpen.close();
        //Create message on phone to confirmed saved sleep record
        Toast.makeText(context, "Sleep Saved", Toast.LENGTH_SHORT).show();

    }

}
