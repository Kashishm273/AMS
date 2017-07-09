package com.example.kashish.ams;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ChooseActivity extends AppCompatActivity {

    Button btnStudent, btnTeacher;

    View.OnClickListener clickListener =new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.btnstudent){
                Intent intent =new Intent(ChooseActivity.this,StudentLogIn.class);
                startActivity(intent);
            }

            else if(v.getId()==R.id.btnteacher){
                Intent intent =new Intent(ChooseActivity.this,TeacherLogIn.class);
                startActivity(intent);
            }
        }
    };

    void initViews(){
        btnStudent=(Button)findViewById(R.id.btnstudent);
        btnTeacher=(Button)findViewById(R.id.btnteacher);

        btnTeacher.setOnClickListener(clickListener);
        btnStudent.setOnClickListener(clickListener);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        initViews();
    }
}
