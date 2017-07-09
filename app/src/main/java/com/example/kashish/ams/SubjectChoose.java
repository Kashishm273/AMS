package com.example.kashish.ams;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SubjectChoose extends AppCompatActivity {

    ListView listView;
    TextView txtClick;
    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;
    JSONObject json_object;
    JSONArray json_array;
    String json_string, type,email_id;

    AdapterView.OnItemClickListener clickListener= new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String subject = arrayList.get(position);
            type="PieChart";
            email_id=StaticFields.getStudent_email_id();
            BackgroundWorker7 backgroundWorker7=new BackgroundWorker7(SubjectChoose.this);
            backgroundWorker7.execute(type,subject,email_id);

            }
        };

    void initList(){
        arrayList=new ArrayList<String>();
        try {
            json_object=new JSONObject(json_string);
            json_array=json_object.getJSONArray("server_response");
            int count=0;
            String subject;

            while(count<json_array.length()){

                JSONObject jo=json_array.getJSONObject(count);
                subject=jo.getString("subject_name");

                arrayList.add(subject);
                count++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(clickListener);

    }


    void initviews(){
        listView=(ListView)findViewById(R.id.listView2);
        txtClick=(TextView)findViewById(R.id.txtchoose);

        initList();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_choose);
        Intent rcv = getIntent();
        json_string=rcv.getStringExtra("json_string");

        initviews();
    }



}

