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


public class StudentAttendanceChoose extends AppCompatActivity implements LocationListener {


    TextView txtdo;
    Button btnmark, btnview;
    LocationManager locationManager;
    String type,email;

    void initViews(){
        txtdo=(TextView)findViewById(R.id.txtdo1);
        btnmark=(Button)findViewById(R.id.btnmark1);
        btnview=(Button)findViewById(R.id.btnview1);

        btnmark.setOnClickListener(clickListener);
        btnview.setOnClickListener(clickListener);

        locationManager=(LocationManager)getSystemService(LOCATION_SERVICE);
    }

    View.OnClickListener clickListener=new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btnmark1) {

                if (ContextCompat.checkSelfPermission(StudentAttendanceChoose.this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(StudentAttendanceChoose.this,
                            new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }
                else{
                    getLocation();
                }
            }
            else if(v.getId()==R.id.btnview1){
                type="Choose";
                email=StaticFields.getStudent_email_id();
                BackgroundWorker6 backgroundWorker6=new BackgroundWorker6(StudentAttendanceChoose.this);
                backgroundWorker6.execute(type,email);

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
                    Toast.makeText(this,"You need to Allow Access to location to continue",Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    void getLocation() {
        boolean check = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        try {
            if (check) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5, 60, StudentAttendanceChoose.this);
            } else {
                Toast.makeText(StudentAttendanceChoose.this, "Please enable your GPS", Toast.LENGTH_SHORT).show();
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        //Toast.makeText(StudentAttendanceChoose.this, "Please check your GPS", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attendance_choose);
        initViews();
    }

    @Override
    public void onLocationChanged(Location location) {
        double lat = location.getLatitude();
        double lon = location.getLongitude();
        String latitude = String.valueOf(lat);
        String longitude = String.valueOf(lon);
        String type="location";
        String email_id=StaticFields.getStudent_email_id();

        BackgroundWorker3 backgroundWorker3 = new BackgroundWorker3(StudentAttendanceChoose.this);
        backgroundWorker3.execute(type,latitude,longitude,email_id);

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


