package com.example.kashish.ams;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class StudentLogIn extends AppCompatActivity {

    EditText etxtemailid, etxtpassword;
    TextView txtlogin, txtsignup1, txtsignup2, txtforgot;
    Button btnlogin;
    String email_id, password, type;


    void initviews(){
        etxtemailid=(EditText)findViewById(R.id.etxtemailid);
        etxtpassword=(EditText)findViewById(R.id.etxtpassword1);
        txtlogin=(TextView)findViewById(R.id.txtlogin1);
        txtsignup1=(TextView)findViewById(R.id.txtsignup11);
        txtsignup2=(TextView)findViewById(R.id.txtsignup12);
        btnlogin=(Button)findViewById(R.id.btnlogin1);
        txtforgot=(TextView)findViewById(R.id.txtforgot);

        btnlogin.setOnClickListener(clickListener);
        txtsignup2.setOnClickListener(clickListener);
        txtforgot.setOnClickListener(clickListener);

    }

    View.OnClickListener clickListener=new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.txtsignup12){
                Intent intent=new Intent(StudentLogIn.this,ContactAdmin.class);
                startActivity(intent);
            }
            else if(v.getId()==R.id.txtforgot){
                Toast.makeText(StudentLogIn.this,"Contact admin for a new password.",Toast.LENGTH_SHORT).show();
            }

            else if(v.getId()==R.id.btnlogin1){

                email_id=etxtemailid.getText().toString().trim();
                password=etxtpassword.getText().toString().trim();
                if(email_id.length()==0 && password.length()==0){
                    Toast.makeText(StudentLogIn.this, "Please enter your email id and password", Toast.LENGTH_SHORT).show();
                }
                else if(email_id.length()==0){
                    Toast.makeText(StudentLogIn.this,"Please enter your email id",Toast.LENGTH_SHORT).show();
                }
                else if(password.length()==0){
                    Toast.makeText(StudentLogIn.this,"Please enter your password",Toast.LENGTH_SHORT).show();
                }
                else {
                    StaticFields.setStudent_email_id(email_id);
                    type = "student_login";

                    BackgroundWorker1 backgroundWorker1 = new BackgroundWorker1(StudentLogIn.this);
                    backgroundWorker1.execute(type, email_id, password);
                }


            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_log_in);
        initviews();
    }
}
