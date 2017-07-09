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


public class BackgroundWorker4 extends AsyncTask<String,Void,String> {

        Context context;
        String email_id, subject,time, type, markStudentAttendance_url, post_data, line="",result="";
        ProgressDialog progressDialog;
        BackgroundWorker4(Context context){
            this.context=context;
        }


    @Override
    protected String doInBackground(String... params) {

        type=params[0];
        //markStudentAttendance_url="http://172.17.140.214/delete/mark_student_attendance.php";
        markStudentAttendance_url="http://kashishm27.netne.net/mark_student_attendance.php";
        if(type.equals("Mark Attendance")) {

            email_id=params[1];
            subject=params[2];
            time=params[3];
            try {
                URL url=new URL(markStudentAttendance_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                post_data= URLEncoder.encode("email_id", "UTF-8")+"="+URLEncoder.encode(email_id,"UTF-8")+"&"+
                        URLEncoder.encode("subject","UTF-8")+"="+URLEncoder.encode(subject,"UTF-8")+"&"+
                        URLEncoder.encode("time","UTF-8")+"="+URLEncoder.encode(time,"UTF-8");

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                while((line=bufferedReader.readLine())!=null){
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
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
        progressDialog.setTitle("Marking your attendance");
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(String result) {
        progressDialog.dismiss();
        if(result.equals("You have already marked your attendance")){
            Toast.makeText(context,"You have already marked your attendance",Toast.LENGTH_SHORT).show();
        }
        else if(result.equals("Success")){
            Toast.makeText(context,"Attendance Marked!",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context,"ERROR! Try Again or Contact Admin",Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}



