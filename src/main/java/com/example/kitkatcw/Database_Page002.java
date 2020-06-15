package com.example.kitkatcw;


import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


public class Database_Page002 extends LinearLayout {
    ImageButton nextButton, prevButton;
    TextView CurrentDate;
    GridView gridCalendar;
    //Creates local instance of the class
    SQL_DB_Open dbOpen;

    Context context;
    Calendar calendar = Calendar.getInstance(Locale.ENGLISH);

    //Maximum days to be displayed by the grid view calendar
    private static final int Cal_Max_Date =42;

    //Sets the date formats required to be read by the database and written.
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH );
    SimpleDateFormat monthForm = new SimpleDateFormat("MMMM", Locale.ENGLISH);
    SimpleDateFormat yearForm = new SimpleDateFormat("yyyy", Locale.ENGLISH);
    SimpleDateFormat recordDateForm = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    //Grid Adapter is for initialising the calendar Grid view
    GridAdapter GridAdapter;
    //Alert Dialog box used for the adding and viewing of records
    AlertDialog alert;
    //Array list for dates in the calendar and a separate one for the records of feeding and sleeping
    List<Data> recordList = new ArrayList();
    List<Date> dates = new ArrayList<>();

 public Database_Page002(Context context) {
     super(context);
 }

    public Database_Page002(final Context context, @Nullable AttributeSet attrs) {

        super(context, attrs);
        //Set Context to the activity running (Calendar_show)
        this.context = context;
        //Sets the layout of the activity and defines all the buttons for later use upon creation
        InitaliseLayout();
        //Initialises the calendar Grid view to local month.
        IntCalendar();

        //Iterates the month to change the calendar grid view respective per button
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH, -1);
                IntCalendar();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH, +1);
                IntCalendar();
            }
        });

        //Enables adding of records upon clicking a date on the calendar by opening an alert dialog.
        gridCalendar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                alertBuilder.setCancelable(true);
                //inflates the layout of add record activity for user to manually input data.
                final View addGridView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_addrecord_past, null);
                // Defines the variables from the activity.
                final EditText RecordType = addGridView.findViewById(R.id.sleeporfeedid);
                final EditText RecordTimer = addGridView.findViewById(R.id.setduration);
                final TextView RecordStartTime = addGridView.findViewById(R.id.starttime);
                ImageButton SetRecordTime = addGridView.findViewById(R.id.setTime);

                Button SaveRecord = addGridView.findViewById(R.id.saverecord);

                //Sets an on click listener to set the time of day the record should be recorded as
                SetRecordTime.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //gets calendar instance
                        Calendar calendar = Calendar.getInstance();
                        //Sets integer variable for hours and mins based on calendars time of day
                        int hours = calendar.get(Calendar.HOUR_OF_DAY);
                        int mins = calendar.get(Calendar.MINUTE);
                        //Creates another dialog box where the user can select the time
                        TimePickerDialog pickTime = new TimePickerDialog(addGridView.getContext(), R.style.Theme_AppCompat_Dialog
                                , new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                Calendar c = Calendar.getInstance();
                                c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                c.set(Calendar.MINUTE,minute);
                                c.setTimeZone(TimeZone.getDefault());
                               //Sets format for the time picker as hh:mm
                                SimpleDateFormat hourformat = new SimpleDateFormat("K:mm a ", Locale.ENGLISH);
                                String start_Time = hourformat.format(c.getTime());
                                RecordStartTime.setText(start_Time);
                            }
                        },hours, mins,false);
                        //Displays the time picker dialog box for the user
                        pickTime.show();
                        }
                    });
                //Defines the variables for date, month and year that will be saved into the database
                // these records take the format of the predefined formats above and take  position from the array list of dates
                final String date = recordDateForm.format(dates.get(position));
                final String month = monthForm.format(dates.get(position));
                final String year = yearForm.format(dates.get(position));

                //Sets the listener for the Save button and calls the SaveRecord method
                //Then reinitialise the calendar and removes the alert dialog
                SaveRecord.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Takes the variables set above provided by the user in the alert dialog
                        SaveRecord(RecordType.getText().toString(), RecordTimer.getText().toString(),RecordStartTime.getText().toString(),date,month,year);
                        IntCalendar();
                        alert.dismiss();
                    }
                });

                alertBuilder.setView(addGridView);
                alert = alertBuilder.create();
                alert.show();


                }


            });

        //Sets on click when user holds click on a date in the calendar.
        gridCalendar.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //Gets date the user clicked on
                String date = recordDateForm.format(dates.get(position)) ;
                //Creating Alert Dialog using the view records activity
                AlertDialog.Builder AlertBuild = new AlertDialog.Builder(context);
                AlertBuild.setCancelable(true);
                //Inflates the activity
                View showview = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_view_records, null);

                //Calls the Recyclerview widget to use the Recycler View Class attached to the activity
                RecyclerView recyclerView = showview.findViewById(R.id.RecordRecyc);
                RecyclerView.LayoutManager layout = new LinearLayoutManager(showview.getContext());
                recyclerView.setLayoutManager(layout);
                //Makes the alert dialog layout have a set size.
                recyclerView.setHasFixedSize(true);
                //Calls method Collect Record By Date to view all by the date selected earlier
                RecordRecycAdapter RecRecycAdapt = new RecordRecycAdapter(showview.getContext(), CollectRecordByDate(date));
                recyclerView.setAdapter(RecRecycAdapt);
                RecRecycAdapt.notifyDataSetChanged();

                //Displays the alert dialog
                AlertBuild.setView(showview);
                alert = AlertBuild.create();
                alert.show();
                //updates the calendar when the dialog ends to update the record number under the date.
                alert.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        IntCalendar();
                    }
                });

                return true;
                }
            });

        }

        //Method to collect data array list entries by date
    private ArrayList<Data> CollectRecordByDate(String date){
     //Create new array list to store dates on a selected day only
        ArrayList<Data> recordList = new ArrayList<>();
        //New instance of the SQL database is opened
        dbOpen = new SQL_DB_Open(context);
        //Gets a readable version of the existing database
        SQLiteDatabase recorder = dbOpen.getReadableDatabase();
        //Create a cursor to read the entries and collect the correct matching records
        Cursor cursor = dbOpen.ReadRecord(date, recorder);
        while  (cursor.moveToNext()){
            String record = cursor.getString(cursor.getColumnIndex(SQL_DB_Structure.Feed_Sleep));
            String Duration = cursor.getString(cursor.getColumnIndex(SQL_DB_Structure.Duration));
            String time = cursor.getString(cursor.getColumnIndex(SQL_DB_Structure.Time));
            String Date = cursor.getString(cursor.getColumnIndex(SQL_DB_Structure.Date));
            String Month = cursor.getString(cursor.getColumnIndex(SQL_DB_Structure.Month));
            String Year = cursor.getString(cursor.getColumnIndex(SQL_DB_Structure.Year));
            Data data = new Data(record,Duration,time,Date,Month,Year);
            //Adds the matching records to the new arraylist of records for the date
            recordList.add(data);

        }

        //Closes the cursor and database instance and returns the records matching the date
        cursor.close();
        dbOpen.close();
        return recordList;
    }

       public Database_Page002(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
         super(context, attrs, defStyleAttr); }

         //Method used to save records when the on click save record button is clicked
    private void SaveRecord (String feed_sleep,String duration, String time, String date, String month, String year){
        //Calls a new insatance of the SQLite database class
        dbOpen = new SQL_DB_Open(context);
        //Makes the database writable
        SQLiteDatabase records = dbOpen.getWritableDatabase();
        //Calls the method Save Record in the SQL_DB_Open class passing the variables through.
        dbOpen.SaveRecord(feed_sleep,duration,time,date,month,year, records);
        //closes the instance
        dbOpen.close();
        //Creates message saying the record has been saved
        Toast.makeText(context, "Record Saved", Toast.LENGTH_SHORT).show();
    }

    //Creates the layout of the activity
    private void InitaliseLayout() {
     //Inflates the layout of the activity database page.
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_database__page002, this);

        //defines the buttons and grid in the activity
        nextButton = view.findViewById(R.id.nextBtn);
        prevButton = view.findViewById(R.id.backBtn);
        CurrentDate = view.findViewById(R.id.currentDate);
        gridCalendar = view.findViewById(R.id.gridCalendar);
    }

    //Creates the calendar in the gridview
    private void IntCalendar() {
     //Gets the current month and year to be displayed at the top of the activity
        String currDate = dateFormat.format(calendar.getTime());
        CurrentDate.setText(currDate);
        //clears the list of dates to be repopulated.
        dates.clear();
        //copies the calendar
        Calendar monthsInCal = (Calendar) calendar.clone();
        //Adds one to the count of the month as it starts at 0 to 11
        monthsInCal.set(Calendar.DAY_OF_MONTH, 1);
        int FirstDay = monthsInCal.get(Calendar.DAY_OF_WEEK) -1;
        monthsInCal.add(Calendar.DAY_OF_MONTH, -FirstDay);
        //Runs the Collected records per month method that collects all records for the month that the user is viewing
        CollectRecordsPerMonth(monthForm.format(calendar.getTime()),yearForm.format(calendar.getTime()));

        //While statement to iterate to 42 days so that all are displayed.
        while(dates.size() < Cal_Max_Date){
        dates.add(monthsInCal.getTime());
        monthsInCal.add(Calendar.DAY_OF_MONTH, 1);
        }

        GridAdapter = new GridAdapter(context,dates,calendar,recordList);
        gridCalendar.setAdapter(GridAdapter);
    }

    //Method to collect all records in a month.
    private void CollectRecordsPerMonth (String month, String year){
     //clears the list of private records for that month
        recordList.clear();
        //calls new instance of the database class
         dbOpen = new SQL_DB_Open(context);
         //Makes the record list readable
        SQLiteDatabase recorder = dbOpen.getReadableDatabase();
        //Scan reads all the records for matching records to the month being viewed
        Cursor cursor = dbOpen.ReadRecordMonth(month,year,recorder);
        while (cursor.moveToNext()){
            String record = cursor.getString(cursor.getColumnIndex(SQL_DB_Structure.Feed_Sleep));
            String Duration = cursor.getString(cursor.getColumnIndex(SQL_DB_Structure.Duration));
            String time = cursor.getString(cursor.getColumnIndex(SQL_DB_Structure.Time));
            String date = cursor.getString(cursor.getColumnIndex(SQL_DB_Structure.Date));
            String Month = cursor.getString(cursor.getColumnIndex(SQL_DB_Structure.Month));
            String Year = cursor.getString(cursor.getColumnIndex(SQL_DB_Structure.Year));
            Data data = new Data(record,Duration,time,date,Month,Year);
            //Adds the selected matching records to the list to be viewed
            recordList.add(data);
        }
        //closes all instances
        cursor.close();
        dbOpen.close();
    }



}
