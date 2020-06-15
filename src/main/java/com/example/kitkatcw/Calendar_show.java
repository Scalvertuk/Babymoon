package com.example.kitkatcw;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Calendar_show extends AppCompatActivity {
    Database_Page002 Calendar_runner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Creates the layout for the calendar and runs the Database page which contains all the code for it.
        setContentView(R.layout.activity_calendar_show);

        Calendar_runner = (Database_Page002)findViewById(R.id.Show_Calendar);


    }

}
