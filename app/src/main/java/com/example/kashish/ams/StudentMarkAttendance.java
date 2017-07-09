package com.example.kashish.ams;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class StudentMarkAttendance extends AppCompatActivity {

    ListView lv;
    ArrayList<ListBean> arrayList;
    MyArrayAdapter adapter;
    String json_string, subject;
    String  time, current_time, current_date,start_time, email_id, type="Mark Attendance";
    JSONObject json_object;
    JSONArray json_array;
    int check;

    AdapterView.OnItemClickListener clickListener= new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ListBean lb=arrayList.get(position);
            subject=lb.getSubject();
            time=lb.getTime();
            String[] parts =time.split(":");
            start_time=parts[0];
            current_time=getTime();
            current_date=getDate();
            email_id=StaticFields.getStudent_email_id();
            check=start_time.compareTo(current_time);
                if (check==0) {
                    BackgroundWorker4 backgroundWorker4=new BackgroundWorker4(StudentMarkAttendance.this);
                    backgroundWorker4.execute(type, email_id ,subject,time);
                }else if(check<0){
                    Toast.makeText(StudentMarkAttendance.this,"The Lecture is already over!",Toast.LENGTH_SHORT).show();
                }else if(check>0){
                    Toast.makeText(StudentMarkAttendance.this,"The Lecture has not started yet!",Toast.LENGTH_SHORT).show();
                }
        }

        };

    String getTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
        String time = dateFormat.format(new Date()).toString();
        return time;
    }

    String getDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
        String date = dateFormat.format(new Date()).toString();
        return date;
    }


    void initList(){
        arrayList=new ArrayList<ListBean>();
        try {
            json_object=new JSONObject(json_string);
            json_array=json_object.getJSONArray("server_response");
            int count=0;
            String subject,time;

            while(count<json_array.length()){

                JSONObject jo=json_array.getJSONObject(count);
                subject=jo.getString("subject_name");
                time=jo.getString("time");

                arrayList.add(new ListBean(subject,time));
                count++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapter= new MyArrayAdapter(this, R.layout.timetable_item,arrayList);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(clickListener);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_mark_attendance);
        lv=(ListView)findViewById(R.id.listView);

        Intent rcv = getIntent();
        json_string=rcv.getStringExtra("json_string");

        initList();
    }
}
