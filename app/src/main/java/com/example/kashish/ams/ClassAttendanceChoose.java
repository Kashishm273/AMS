package com.example.kashish.ams;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ClassAttendanceChoose extends AppCompatActivity {

    TextView txtToday, txtCourse, txtSubject;
    Spinner spCourse, spSubject;
    Button btnSubmit;
    String json_string, json_course, json_subject;
    JSONObject json_object1, json_object2;
    JSONArray json_array1, json_array2;
    String course, subject, type;

    ArrayList<String> courseList, subjectList;
    ArrayAdapter<String> courseAdapter, subjectAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_attendance_choose);
        initViews();
        Intent rcv=getIntent();
        json_string=rcv.getStringExtra("json_string");

        loadDataInSpinner();
    }

    void initViews(){
        txtToday=(TextView)findViewById(R.id.txttoday);
        txtCourse=(TextView)findViewById(R.id.txtcourse);
        txtSubject=(TextView)findViewById(R.id.txtsubject1);
        spCourse=(Spinner)findViewById(R.id.spinner1);
        spSubject=(Spinner)findViewById(R.id.spinner2);
        btnSubmit=(Button)findViewById(R.id.btnsubmit);

        btnSubmit.setOnClickListener(clickListener);
    }

    View.OnClickListener clickListener=new View.OnClickListener(){


        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.btnsubmit){
                type="ClassPieChart";
                //Toast.makeText(ClassAttendanceChoose.this,course+" "+subject,Toast.LENGTH_SHORT).show();
                BackgroundWorker10 backgroundWorker10=new BackgroundWorker10(ClassAttendanceChoose.this);
                backgroundWorker10.execute(type,course,subject);
            }
        }
    };


    void loadDataInSpinner() {

        String[] parts=json_string.split("-");
        json_course = parts[0];
        json_subject = parts[1];

        courseList=new ArrayList<String>();
        subjectList=new ArrayList<String>();

        try {
            json_object1=new JSONObject(json_course);
            json_array1=json_object1.getJSONArray("server_response");
            int count1=0;
            String course;

            while(count1<json_array1.length()){

                JSONObject jo1=json_array1.getJSONObject(count1);
                course=jo1.getString("course_name");

                courseList.add(course);
                count1++;
            }

            json_object2=new JSONObject(json_subject);
            json_array2=json_object2.getJSONArray("server_response");
            int count2=0;
            String subject;

            while(count2<json_array2.length()){

                JSONObject jo2=json_array2.getJSONObject(count2);
                subject=jo2.getString("subject_name");

                subjectList.add(subject);
                count2++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        courseAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,courseList);
        subjectAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,subjectList);
        spCourse.setAdapter(courseAdapter);
        spSubject.setAdapter(subjectAdapter);

        spCourse.setOnItemSelectedListener(itemSelectedListener1);
        spSubject.setOnItemSelectedListener(itemSelectedListener2);
    }

    AdapterView.OnItemSelectedListener itemSelectedListener1 = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            course=courseList.get(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    AdapterView.OnItemSelectedListener itemSelectedListener2 = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            subject=subjectList.get(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };



}
