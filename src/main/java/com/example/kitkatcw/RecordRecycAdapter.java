package com.example.kitkatcw;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//Creates a holder and recycling view for all the records on specific dates
public class RecordRecycAdapter extends RecyclerView.Adapter<RecordRecycAdapter.MyViewHolder> {

    Context context;
    ArrayList<Data> recordsList;

    public RecordRecycAdapter(Context context, ArrayList<Data> recordList) {
        //sets context to = RecordRecycAdapter
        this.context = context;
        //Takes in the record list of all records
        this.recordsList = recordList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflates the record view activity upon creation of the class
        View inflatedview = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_record_viewrows, parent,false);
        return new MyViewHolder(inflatedview);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        //Binds the following variables below that exist in the array to be displayed
        final Data record = recordsList.get(position);
        holder.RecordTypeTxt.setText(record.getFeed_Sleep());
        holder.DurationTxt.setText(record.getDuration());
        holder.DateTxt.setText(record.getDate());
        holder.TimeTxt.setText(record.getTime());
        //upon clicking the delete button will run the delete record method below from the record list
        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            deleteRecordedEvent(record.getFeed_Sleep(),record.getDuration(),record.getTime(),record.getDate());
            recordsList.remove(position);
            notifyDataSetChanged();
            //Creates message saying the record has been deleted
                Toast.makeText(context, "Record Deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Gets the size of the record list
    @Override
    public int getItemCount() {
        return recordsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView DateTxt, RecordTypeTxt, TimeTxt, DurationTxt;
        Button Delete;
        //Defines the variables and defines them by their corresponding views in the activity page
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            DateTxt = itemView.findViewById(R.id.recordDate);
            RecordTypeTxt = itemView.findViewById(R.id.recordType);
            DurationTxt = itemView.findViewById(R.id.recordDuration);
            TimeTxt = itemView.findViewById(R.id.recordedTime);
            Delete = itemView.findViewById(R.id.delete_record);

        }
    }

    private void deleteRecordedEvent(String feed_sleep,String duration, String time, String date){
        //Opens new instance of SQL_DB_Open
        SQL_DB_Open dbOpen = new SQL_DB_Open(context);
        //makes the database writable
        SQLiteDatabase record = dbOpen.getWritableDatabase();
        //runs delete Record method from the other class
        dbOpen.deleteRecord(feed_sleep,duration,time,date,record);
        //closes the class instance
        dbOpen.close();
    }

}
