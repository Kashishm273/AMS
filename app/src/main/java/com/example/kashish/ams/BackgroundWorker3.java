package com.example.kashish.ams;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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

/**
 * Created by Kashish on 7/15/2016.
 */
public class BackgroundWorker3 extends AsyncTask<String,Void,String> {


    Context context;
    String  location_url, timetable_day_url, latitude, longitude, post_data, email_id, type, result="", line1="", json_result="", line2="";
    ProgressDialog progressDialog;
    BackgroundWorker3(Context context) {
        this.context = context;
    }


    @Override
    protected String doInBackground(String... params) {

        type=params[0];
        //location_url="http://172.17.140.214/delete/location_timetable_day.php";
        location_url="http://kashishm27.netne.net/location_timetable_day.php";
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
                OutputStream outputStream1=httpURLConnection1.getOutputStream();
                BufferedWriter bufferedWriter1=new BufferedWriter(new OutputStreamWriter(outputStream1,"UTF-8"));

                post_data= URLEncoder.encode("latitude","UTF-8")+"="+URLEncoder.encode(latitude,"UTF-8")+"&"+
                        URLEncoder.encode("longitude","UTF-8")+"="+URLEncoder.encode(longitude,"UTF-8") +"&"+
                        URLEncoder.encode("email_id","UTF-8")+"="+URLEncoder.encode(email_id,"UTF-8");

                bufferedWriter1.write(post_data);
                bufferedWriter1.flush();
                bufferedWriter1.close();
                outputStream1.flush();
                outputStream1.close();

                InputStream inputStream1=httpURLConnection1.getInputStream();
                BufferedReader bufferedReader1=new BufferedReader(new InputStreamReader(inputStream1,"UTF-8"));
                while((line1=bufferedReader1.readLine())!=null){
                    result += line1;
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
        progressDialog.setTitle("Verifying location");
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }


    @Override
    protected void onPostExecute(String s) {
            progressDialog.dismiss();
            if(s.equals("You need to be in the classroom")){
                Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
            }
            else if(s.equals("No lectures today")){
                Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
            }
            else {
                Intent intent = new Intent(context, StudentMarkAttendance.class);
                intent.putExtra("json_string",s);
                context.startActivity(intent);
            }
        }


    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }


}
