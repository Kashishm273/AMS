package com.example.kashish.ams;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TeacherAttendanceChoose extends AppCompatActivity implements LocationListener {

    TextView txtdo;
    Button btnmark,btnviewyou, btnviewclass;
    LocationManager locationManager;
    String email_id,type;

    void initViews(){

        txtdo=(TextView)findViewById(R.id.txtdo2);
        btnmark=(Button)findViewById(R.id.btnmark2);
        btnviewyou=(Button)findViewById(R.id.btnview2);
        btnviewclass=(Button)findViewById(R.id.btnview3);

        btnmark.setOnClickListener(clickListener);
        btnviewyou.setOnClickListener(clickListener);
        btnviewclass.setOnClickListener(clickListener);

        locationManager=(LocationManager)getSystemService(LOCATION_SERVICE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_attendance_choose);
        initViews();
    }

    void getLocation(){
        boolean check = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        try {
            if (check) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5, 60, TeacherAttendanceChoose.this);
            } else {
                Toast.makeText(TeacherAttendanceChoose.this, "Please enable your GPS", Toast.LENGTH_SHORT).show();
            }
        } catch (SecurityException e) {
            e.printStackTrace();
            //Toast.makeText(StudentAttendanceChoose.this, "Please check your GPS", Toast.LENGTH_LONG).show();
        }
    }

    View.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.btnmark2){
                if (ContextCompat.checkSelfPermission(TeacherAttendanceChoose.this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(TeacherAttendanceChoose.this,
                            new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }
                else{
                    getLocation();
                }
            }
            else if(v.getId()==R.id.btnview2){
                email_id=StaticFields.getTeacher_email_id();
                BackgroundWorker8 backgroundWorker8 =new BackgroundWorker8(TeacherAttendanceChoose.this);
                type="TeacherAttendance";
                backgroundWorker8.execute(type,email_id);
            }
            else if(v.getId()==R.id.btnview3){
                email_id=StaticFields.getTeacher_email_id();
                BackgroundWorker9 backgroundWorker9=new BackgroundWorker9(TeacherAttendanceChoose.this);
                type="ClassAttendance";
                backgroundWorker9.execute(type);

            }

        }
    };

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    Toast.makeText(this, "You need to Allow Access to location to continue", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        double lat = location.getLatitude();
        double lon = location.getLongitude();
        String latitude = String.valueOf(lat);
        String longitude = String.valueOf(lon);
        String type="location";
        String email_id=StaticFields.getTeacher_email_id();

        BackgroundWorker5 backgroundWorker5 = new BackgroundWorker5(TeacherAttendanceChoose.this);
        backgroundWorker5.execute(type,latitude,longitude,email_id);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }



}
