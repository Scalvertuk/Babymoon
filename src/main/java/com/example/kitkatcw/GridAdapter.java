package com.example.kitkatcw;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GridAdapter extends ArrayAdapter {

    List<Date> dates;
    Calendar CurrDate;
    List<Data> records;
    LayoutInflater inflater;

    //creates constructor to assign variables
    public GridAdapter(@NonNull Context context, List<Date> dates, Calendar CurrDate, List<Data> records) {
        super(context, R.layout.activity_single_cell);

        this.dates = dates;
        this.CurrDate = CurrDate;
        this.records = records;
        inflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Date monthdate = dates.get(position);
        Calendar dateCal = Calendar.getInstance();
        dateCal.setTime(monthdate);
        int Day = dateCal.get(Calendar.DAY_OF_MONTH);
        int Month = dateCal.get(Calendar.MONTH)+1;
        int Year = dateCal.get(Calendar.YEAR);
        int CurrMon = CurrDate.get(Calendar.MONTH)+1;
        int CurrYear = CurrDate.get(Calendar.YEAR);


        View view = convertView;
        //Adds the number of records to the box of each date if a record exists by inflating the single cell activity
        if (view == null){
            view = inflater.inflate(R.layout.activity_single_cell, parent,false);
        }
        //colours the date green if it belongs to the current month and year being view
        if(Month == CurrMon && Year == CurrYear){
            view.setBackgroundColor(getContext().getResources().getColor(R.color.green));
        }
        //else it colours the date grey and wont add record number
        else
        {
            view.setBackgroundColor(Color.parseColor("#cccccc"));
        }

        TextView Day_Num = view.findViewById(R.id.cal_day);
        TextView Record_Num = view.findViewById(R.id.recordId);
       //Adds the date number to the grid view calendar
        Day_Num.setText(String.valueOf(Day));
        Calendar recordInCal = Calendar.getInstance();
        ArrayList<String> recordedInCal = new ArrayList<>();

        for (int i = 0; i < records.size(); i++){
            recordInCal.setTime(ConvertDate(records.get(i).getDate()));

            if (Day == recordInCal.get(Calendar.DAY_OF_MONTH) && Month == recordInCal.get(Calendar.MONTH)+1 &&
            Year == recordInCal.get(Calendar.YEAR)){
                recordedInCal.add(records.get(i).getFeed_Sleep());
                Record_Num.setText(recordedInCal.size()+ " Records");


            }


        }

        return view;
    }

    //Crates the standardised date format for the activities that will be displayed
    private Date ConvertDate (String recordDate){
        SimpleDateFormat dateForm = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = null;
        try {
            date = dateForm.parse(recordDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    //gets the size of the dates array list
    @Override
    public int getCount() {
        return dates.size();
    }

    //gets the date of the the object
    @Override
    public int getPosition(@Nullable Object item) {
        return dates.indexOf(item);
    }

    //gets the item from the current date
    @Nullable
    @Override
    public Object getItem(int position) {
        return dates.get(position);
    }

}
