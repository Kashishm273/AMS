package com.example.kashish.ams;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TeacherLogIn extends AppCompatActivity {

    EditText etxtemailid1, etxtpassword;
    TextView txtlogin, txtsignup1, txtsignup2, txtforgot;
    Button btnlogin;
    String email_id, password, type;


    void initviews(){
        etxtemailid1=(EditText)findViewById(R.id.etxtemailid1);
        etxtpassword=(EditText)findViewById(R.id.etxtpassword2);
        txtlogin=(TextView)findViewById(R.id.txtlogin2);
        txtsignup1=(TextView)findViewById(R.id.txtsignup21);
        txtsignup2=(TextView)findViewById(R.id.txtsignup22);
        btnlogin=(Button)findViewById(R.id.btnlogin2);
        txtforgot=(TextView)findViewById(R.id.txtforgot1);

        btnlogin.setOnClickListener(clickListener);
        txtsignup2.setOnClickListener(clickListener);
        txtforgot.setOnClickListener(clickListener);

    }

    View.OnClickListener clickListener=new View.OnClickListener(){
        @Override
        public void onClick(View v) {

            if(v.getId()==R.id.txtsignup22){
                Intent intent=new Intent(TeacherLogIn.this,ContactAdmin.class);
                startActivity(intent);
            }

            else if(v.getId()==R.id.txtforgot){
                Toast.makeText(TeacherLogIn.this,"Contact admin for a new password.",Toast.LENGTH_SHORT).show();
            }

            else if(v.getId()==R.id.btnlogin2){

                email_id=etxtemailid1.getText().toString().trim();
                password=etxtpassword.getText().toString().trim();
                if(email_id.length()==0 && password.length()==0){
                    Toast.makeText(TeacherLogIn.this, "Please enter your email id and password", Toast.LENGTH_SHORT).show();
                }
                else if(email_id.length()==0){
                    Toast.makeText(TeacherLogIn.this,"Please enter your email id",Toast.LENGTH_SHORT).show();
                }
                else if(password.length()==0){
                    Toast.makeText(TeacherLogIn.this,"Please enter your password",Toast.LENGTH_SHORT).show();
                }
                else {
                    StaticFields.setTeacher_email_id(email_id);
                    type = "teacher_login";

                    BackgroundWorker2 backgroundWorker2 = new BackgroundWorker2(TeacherLogIn.this);
                    backgroundWorker2.execute(type, email_id, password);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_log_in);
        initviews();
    }
}
