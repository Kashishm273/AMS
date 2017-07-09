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
 * Created by Kashish on 7/15/2016.
 */
public class BackgroundWorker1 extends AsyncTask<String, Void, String>{

    Context context;
    String type, email_id, password, loginstudent_url, post_data;
    String result="",line="";
    ProgressDialog progressDialog;


    BackgroundWorker1(Context context){
        this.context=context;
    }

    @Override
    protected String doInBackground(String... params) {
        type=params[0];
        //loginstudent_url="http://172.17.140.214/delete/login_student.php";
        loginstudent_url="http://kashishm27.netne.net/login_student.php";
        if(type.equals("student_login")){
            try {
                email_id=params[1];
                password=params[2];
                URL url=new URL(loginstudent_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                post_data= URLEncoder.encode("email_id","UTF-8")+"="+URLEncoder.encode(email_id,"UTF-8")+"&"+
                        URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");

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


        }return null;

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
    protected void onPostExecute(String result) {
        progressDialog.dismiss();
        if(result!=null && result.equals("Log In successful")){
            Toast.makeText(context,"Log In successful",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(context, StudentAttendanceChoose.class);
            context.startActivity(intent);
        }

        else if(result.equals("Invalid Details")){
            Toast.makeText(context,result,Toast.LENGTH_SHORT).show();
        }

        else{
            Toast.makeText(context,result,Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
