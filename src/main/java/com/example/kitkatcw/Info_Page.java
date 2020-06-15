package com.example.kitkatcw;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

public class Info_Page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Displays the main info page layout when class is initialised from main activity
        setContentView(R.layout.activity_info__page);
    }

    public void onClickFeedInfo (View view){
        //Moves view to feeding info activity
        setContentView(R.layout.activity_info_feeding);
        //creates Web link to designated website found in strings.xml
        TextView feedLink = findViewById(R.id.feedlink);
        feedLink.setMovementMethod(LinkMovementMethod.getInstance());
    }
    public void onClickSleepInfo (View view){
        //Moves view to sleeping info activity
        setContentView(R.layout.activity_info_sleeping);
        //creates Web link to designated website found in strings.xml
        TextView sleepLink = findViewById(R.id.sleeplink);
        sleepLink.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void onClickClothingInfo (View view){
    setContentView(R.layout.activity_clothing_info);
    }

    //This sets a on click method on the return button to get back to the info page
    // All the return buttons have the same method as they do the same thing
    public void onClickClothingReturn (View view){
        setContentView(R.layout.activity_info__page);


    }

}
