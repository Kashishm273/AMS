package com.example.kashish.ams;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ContactAdmin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_admin);
        TextView txtAdmin = (TextView)findViewById(R.id.txtadmin);
        TextView txtAdmin1 = (TextView)findViewById(R.id.txtadmin1);
        TextView txtAdmin2 = (TextView)findViewById(R.id.txtadmin2);
        TextView txtAdmin3 = (TextView)findViewById(R.id.txtadmin3);
        TextView txtAdmin4 = (TextView)findViewById(R.id.txtadmin4);
    }
}
