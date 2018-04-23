package com.example.kashishgupta.hungerbite;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.HashMap;

public class RegisterationActivity extends AppCompatActivity {

    Button register, log_in;
    EditText First_Name, Last_Name, Email, Password ,cPass,eknow,Phone1;
    String F_Name_Holder, L_Name_Holder, EmailHolder, PasswordHolder,cPassHolder,PhoneNoHolder,AnswerHolder;
    String finalResult ;
    String HttpURL = "http://hungerbite.com/hungerbite_app/register.php";
    Boolean CheckEditText ;
    ProgressDialog progressDialog;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);

        //Assign Id'S
        First_Name = (EditText)findViewById(R.id.editTextFname);
        Last_Name = (EditText)findViewById(R.id.editTextlname);
        Email = (EditText)findViewById(R.id.ediemail);
        Password = (EditText)findViewById(R.id.edipass);
        Phone1 = (EditText)findViewById(R.id.editTextphone);
        cPass = (EditText)findViewById(R.id.editTextcpass);
        eknow = (EditText)findViewById(R.id.editTextknow);


        register = (Button)findViewById(R.id.buttonsubmit);
        //log_in = (Button)findViewById(R.id.Login);

        //Adding Click Listener on button.
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Checking whether EditText is Empty or Not
                CheckEditTextIsEmptyOrNot();

                if(CheckEditText){

                    // If EditText is not empty and CheckEditText = True then this block will execute.

                    UserRegisterFunction(F_Name_Holder,L_Name_Holder, EmailHolder, PasswordHolder, PhoneNoHolder,cPassHolder,AnswerHolder);

                }
                else {

                    // If EditText is empty then this block will execute .
                    Toast.makeText(RegisterationActivity.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();

                }


            }
        });

        /*log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
*/
    }

    public void CheckEditTextIsEmptyOrNot(){

        F_Name_Holder = First_Name.getText().toString();
        L_Name_Holder = Last_Name.getText().toString();
        EmailHolder = Email.getText().toString();
        PasswordHolder = Password.getText().toString();
        cPassHolder = cPass.getText().toString();

        PhoneNoHolder = Phone1.getText().toString();
        PasswordHolder = Password.getText().toString();
        AnswerHolder = eknow.getText().toString();






        if(TextUtils.isEmpty(F_Name_Holder) || TextUtils.isEmpty(L_Name_Holder) || TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder))
        {

            CheckEditText = false;

        }
        else {

            CheckEditText = true ;
        }

    }

    public void UserRegisterFunction(final String F_Name, final String L_Name, final String email, final String password, final String phone_no, final String cpassword, final String answer){

        class UserRegisterFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(RegisterationActivity.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                Toast.makeText(RegisterationActivity.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

                System.out.print(httpResponseMsg.toString());
            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("fname",params[0]);

                hashMap.put("lname",params[1]);

                hashMap.put("login",params[2]);

              hashMap.put("phone_no",params[3]);
                hashMap.put("password",params[4]);
                hashMap.put("cpassword",params[5]);
                hashMap.put("answer",params[6]);




                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        UserRegisterFunctionClass userRegisterFunctionClass = new UserRegisterFunctionClass();

        userRegisterFunctionClass.execute(F_Name,L_Name,email,password,phone_no,cpassword,answer);

    }

}