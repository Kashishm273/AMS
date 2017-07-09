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

/**
 * Created by Kashish on 7/26/2016.
 */
public class BackgroundWorker9 extends AsyncTask<String,Void,String> {

    Context context;
    String ViewCourse_url, ViewSubject_url, type;
    String result ="", line ="",result1="", line1="", final_string;
    ProgressDialog progressDialog;

    BackgroundWorker9(Context context) {
        this.context = context;
    }


    @Override
    protected String doInBackground(String... params) {

        type = params[0];
        //ViewCourse_url = "http://172.17.140.214/delete/view_course.php";
        //ViewSubject_url = "http://172.17.140.214/delete/view_subject.php";

        ViewCourse_url = "http://kashishm27.netne.net/view_course.php";
        ViewSubject_url = "http://kashishm27.netne.net/view_subject.php";
        if (type.equals("ClassAttendance")) {

            try {
                URL url = new URL(ViewCourse_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                while ((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();


                URL url1 = new URL(ViewSubject_url);
                HttpURLConnection httpURLConnection1 = (HttpURLConnection)url1.openConnection();
                InputStream inputStream1 = httpURLConnection1.getInputStream();
                BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(inputStream1, "UTF-8"));
                while ((line1 = bufferedReader1.readLine())!= null) {
                    result1 += line1;
                }
                bufferedReader1.close();
                inputStream1.close();
                httpURLConnection1.disconnect();

                final_string=result+"-"+result1;
                return final_string;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } return final_string;
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
        progressDialog.setTitle("Fetching results");
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(String s) {
        progressDialog.dismiss();
        Intent intent=new Intent(context,ClassAttendanceChoose.class);
        intent.putExtra("json_string",s);
        context.startActivity(intent);
    }


    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

}



