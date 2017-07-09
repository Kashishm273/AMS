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
 * Created by Kashish on 7/18/2016.
 */
public class BackgroundWorker2 extends AsyncTask<String,Void,String> {

    Context context;
    String email_id, password, loginteacher_url, type, result = "", line = "", post_data;
    ProgressDialog progressDialog;

    BackgroundWorker2(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        type = params[0];
        //loginteacher_url = "http://172.17.140.214/delete/login_teacher.php";
        loginteacher_url="http://kashishm27.netne.net/login_teacher.php";
        if (type.equals("teacher_login")) {
            try {
                email_id = params[1];
                password = params[2];
                URL url = new URL(loginteacher_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                post_data = URLEncoder.encode("email_id", "UTF-8") + "=" + URLEncoder.encode(email_id, "UTF-8") + "&" +
                        URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                while ((line = bufferedReader.readLine()) != null) {
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
        }return result;
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
        if(result!=null && result.equals("Log In successful")){
            Toast.makeText(context,"Log In successful",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(context, TeacherAttendanceChoose.class);
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