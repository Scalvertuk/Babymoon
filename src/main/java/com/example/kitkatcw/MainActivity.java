package com.example.kitkatcw;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    private Button Feed_button, Sleep_button, Info_button, Database_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Finding buttons by Id to be assigned
        Feed_button= findViewById(R.id.Feed_button);
        Sleep_button= findViewById(R.id.Sleep_button);
        Info_button = findViewById(R.id.Info_button);
        Database_button = findViewById(R.id.Database_button);

        //Sets on click listeners to call appropriate methods below
        Feed_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openFeeding_Timer();

            }
        });
        Sleep_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openSleep_Timer();

            }
        });
        Info_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openInfo_Page();

            }
        });
        Database_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                openDatabase_Page();
            }
        });
    }

    //Creates new intent for each page being and starts their activity and class
    public void openFeeding_Timer() {
        Intent Feed = new Intent(this, Feeding_Timer.class);

        startActivity(Feed);

    }

    public void openSleep_Timer(){
        Intent Sleep = new Intent(this, Sleep_Timer.class);

        startActivity(Sleep);
    }

    public void openInfo_Page(){
        Intent Info = new Intent(this, Info_Page.class);
        startActivity(Info);
    }

    public void openDatabase_Page(){
        Intent Data = new Intent (this, Calendar_show.class);
        startActivity(Data);
    }
}
