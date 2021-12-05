package com.example.backgroundtasksyncapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    public class BGTask extends AsyncTask<String,Void,String> { //AsyncTask<String,Void,String>==AsyncTask<parameter,progress,result>
        @Override
        protected void onPreExecute() { //not-important
            super.onPreExecute();
            Log.d("pritamBGTask","onPreExecute==run");
        }
        @Override
        protected String doInBackground(String... strings) { //very-very-important
            Log.d("pritamBGTask","doInBackground==run");
            String finalResult="";
            try {
                URL url=new URL(strings[0]);
                HttpURLConnection connection=(HttpURLConnection) url.openConnection();
                InputStream inputStream=connection.getInputStream();
                InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
                int urlData=inputStreamReader.read();
                while(urlData>=0) {
                    char a1=(char) urlData;
                    finalResult=finalResult+a1;
                    urlData=inputStreamReader.read();
                }
            } catch(Exception e) {
                e.printStackTrace();
                return "Oops...!! Something Went Wrong...";
            }
            return finalResult;
        }
        @Override
        protected void onPostExecute(String s) { //not-important
            super.onPostExecute(s);
            Log.d("pritamBGTask","onPostExecute==run");
            Log.d("pritamBGTask",s);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((Button) findViewById(R.id.button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Selfi Click Successfully", Toast.LENGTH_SHORT).show();
            }
        });
        BGTask ob1=new BGTask();
        ob1.execute("http://e-mindsolutions.com/");
    }
}
