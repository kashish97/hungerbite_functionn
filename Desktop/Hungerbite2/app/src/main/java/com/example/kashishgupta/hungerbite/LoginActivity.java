package com.example.kashishgupta.hungerbite;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

public class LoginActivity extends AppCompatActivity {
    EditText eemail, epass;
    String s1;
    Error e;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        eemail = (EditText) findViewById(R.id.editText);
        epass = (EditText) findViewById(R.id.editText2);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void checkLogin(View arg0) {

        // Get text from email and passord field
        final String email = eemail.getText().toString();
        final String password = epass.getText().toString();

        // Initialize  AsyncLogin() class with email and password
        new AsyncLogin().execute(email, password);

    }

        public class AsyncLogin extends AsyncTask<String, String, String>
        {

            ProgressDialog pdLoading = new ProgressDialog(LoginActivity.this);
            HttpURLConnection conn;
            URL url = null;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                //this method will be running on UI thread
                pdLoading.setMessage("\tLoading...");
                pdLoading.setCancelable(false);
               pdLoading.show();

            }
            @Override
            protected String doInBackground(String... params) {

                try {

                    // Enter URL address where your php file resides
                    url = new URL("http://hungerbite.com/abe.php");

                } catch (MalformedURLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    s1=e.getMessage();
                    return "exception";
                }
                try {
                    // Setup HttpURLConnection class to send and receive data from php and mysql
                    conn = (HttpURLConnection)url.openConnection();

                    conn.setReadTimeout(50000);
                    conn.setConnectTimeout(100000);
                  //  conn.getInputStream();
                  conn.getRequestMethod();
                    conn.setRequestMethod("POST");

                    // setDoInput and setDoOutput method depict handling of both send and receive
                  // conn.setDoInput(true);
                    conn.setDoOutput(true);

                    // Append parameters to URL
                    Uri.Builder builder = new Uri.Builder()
                            .appendQueryParameter("username", params[0])
                            .appendQueryParameter("password", params[1]);
                    String query = builder.build().getEncodedQuery();


                    // Open connection for sending data
                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(os, "UTF-8"));
                    writer.write(query);
                    writer.flush();
                    writer.close();
                    os.close();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                    return "exception2";
                }

                try {

                    int response_code = conn.getResponseCode();

                    // Check if successful connection made
                    if (response_code == HttpURLConnection.HTTP_OK) {

                        // Read data sent from server
                        InputStream input = conn.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                        StringBuilder result = new StringBuilder();
                        String line;

                        while ((line = reader.readLine()) != null) {
                            result.append(line);
                        }

                        // Pass data to onPostExecute method
                        return(result.toString());

                    }else{

                       s1= e.getMessage();
                        return("unsuccessful");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    s1=e.getMessage();
                    return "exception";
                } finally {
                    conn.disconnect();
                }


            }

            @Override
            protected void onPostExecute(String result) {

                //this method will be running on UI thread

                pdLoading.dismiss();

                if(result.equalsIgnoreCase("Yes"))
                {
                /* Here launching another activity when login successful. If you persist login state
                use sharedPreferences of Android. and logout button to clear sharedPreferences.
                 */

                    Intent intent = new Intent(LoginActivity.this,FirstActivity.class);
                    startActivity(intent);
                    LoginActivity.this.finish();

                }else if (result.equalsIgnoreCase("No")){

                    // If username and password does not match display a error message
                    Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_LONG).show();

                } else if (result.equalsIgnoreCase("unsuccessful")|| result.equalsIgnoreCase("exception")){


Toast.makeText(LoginActivity.this, "OOPs!"+s1, Toast.LENGTH_LONG).show();

                }
            }

        }
    }