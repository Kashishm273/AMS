package com.example.kashish.ams;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class BackgroundWorker5 extends AsyncTask<String,Void,String> {
    Context context;
    String  location_url, markTeacherAttendance_url, latitude, email_id, longitude, post_data, type;
    String  result="", line="", result1="", line1="";
    ProgressDialog progressDialog;
    BackgroundWorker5(Context context) {
        this.context = context;
    }


    @Override
    protected String doInBackground(String... params) {

        type=params[0];
        //location_url="http://172.17.140.214/delete/location_mark_teacher_attendance.php";
        location_url="http://kashishm27.netne.net/location_mark_teacher_attendance.php";
        if(type.equals("location")){

            try {
                latitude=params[1];
                longitude=params[2];
                email_id=params[3];

                URL url1=new URL(location_url);
                HttpURLConnection httpURLConnection1=(HttpURLConnection)url1.openConnection();
                httpURLConnection1.setRequestMethod("POST");
                httpURLConnection1.setDoOutput(true);
                httpURLConnection1.setDoInput(true);
                OutputStream outputStream=httpURLConnection1.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                post_data= URLEncoder.encode("latitude", "UTF-8")+"="+URLEncoder.encode(latitude,"UTF-8")+"&"+
                        URLEncoder.encode("longitude","UTF-8")+"="+URLEncoder.encode(longitude,"UTF-8")+"&"+
                        URLEncoder.encode("email_id","UTF-8")+"="+URLEncoder.encode(email_id,"UTF-8");

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream1=httpURLConnection1.getInputStream();
                BufferedReader bufferedReader1=new BufferedReader(new InputStreamReader(inputStream1,"UTF-8"));
                while((line=bufferedReader1.readLine())!=null){
                    result += line;
                }
                bufferedReader1.close();
                inputStream1.close();
                httpURLConnection1.disconnect();

                return result;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context) {
            @Override
            public void onBackPressed() {
                progressDialog.cancel();
                progressDialog.dismiss();
            }
        };
        progressDialog.setTitle("Verifying details");
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(String s) {
        progressDialog.dismiss();

        if(s.equals("You have already marked your attendance")){
            Toast.makeText(context,"You have already marked your attendance",Toast.LENGTH_SHORT).show();
        }
        else if(s.equals("Failure")){
            Toast.makeText(context,"ERROR! Try Again or Contact Admin",Toast.LENGTH_SHORT).show();
        }

        else if(s.equals("No lectures today")){
            Toast.makeText(context,"No lectures today",Toast.LENGTH_SHORT).show();
        }

        else if(s.equals("Success")){
            Toast.makeText(context, "Attendance Marked!", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

}
