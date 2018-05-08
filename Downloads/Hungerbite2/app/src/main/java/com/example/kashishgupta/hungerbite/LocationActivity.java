package com.example.kashishgupta.hungerbite;

import android.content.Intent;
import android.location.Address;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LocationActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,ActivityCompat.OnRequestPermissionsResultCallback , hellofragment.OnFragmentInteractionListener{
    GoogleApiClient mGoogleApiClient;
    private static final String TAG = LocationActivity.class.getSimpleName();
    double latitude;
    double longitude;
    private Location mLastLocation;
    InputStream is=null;
    String result=null;
    String location;
    String line=null;
    Button btn1;
    Button btn2;
    final List<String> list = new ArrayList<String>();

    TextView t1,t2;
    LocationHelper locationHelper;
    String ab;
    RequestQueue requestQueue;
    StringRequest stringRequest;
     AutoCompleteTextView text;
     String[] str1;
     ConstraintLayout c1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        text = (AutoCompleteTextView)
                findViewById(R.id.autoCompleteTextView1);
        c1 = (ConstraintLayout) findViewById(R.id.abe);

        btn1 = (Button) findViewById(R.id.button23);
       // btn2 = (Button) findViewById(R.id.button3);
        //t1 = (TextView) findViewById(R.id.textView2);
        //t2 = (TextView) findViewById(R.id.textView3);

        retriveLocation();btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String ab = text.getText().toString().trim();
                Intent i= new Intent(LocationActivity.this,FirstActivity.class);
                 i.putExtra("Lpk", ab);
                 i.putStringArrayListExtra("name", (ArrayList<String>) list);
                 startActivity(i);
            }
        });

        /*

        locationHelper = new LocationHelper(this);
        locationHelper.checkpermission();

        LocationRequest mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();


        mLastLocation = locationHelper.getLocation();

        if (mLastLocation != null) {
            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();
        } else {

            if (btn2.isEnabled())
                btn2.setEnabled(false);


        }




        /* try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://hungerbite.com/hungerbite_app/search.php");
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
            Log.e("Pass 1", "connection success ");
        } catch (Exception e) {
            Log.e("Fail 1", e.toString());
            Toast.makeText(getApplicationContext(), "Invalid IP Address",
                    Toast.LENGTH_LONG).show();
        }

        try {
            BufferedReader reader = new BufferedReader
                    (new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }

            is.close();
            result = sb.toString();
            Log.e("Pass 2", "connection success ");
        } catch (Exception e) {
            Log.e("Fail 2", e.toString());
        }

        try {
            JSONArray JA = new JSONArray(result);
            JSONObject json = null;
            final String[] str1 = new String[JA.length()];

            for (int i = 0; i < JA.length(); i++) {
                json = JA.getJSONObject(i);
                str1[i] = json.getString("name");
            }

            final AutoCompleteTextView text = (AutoCompleteTextView)
                    findViewById(R.id.autoCompleteTextView1);
            final List<String> list = new ArrayList<String>();

            for (int i = 0; i < str1.length; i++) {
                list.add(str1[i]);
            }

            Collections.sort(list);

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                    (getApplicationContext(), android.R.layout.simple_spinner_item, list);

            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            text.setThreshold(1);
            text.setAdapter(dataAdapter);

            text.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
               public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    // TODO Auto-generated method stub
                    Toast.makeText(getBaseContext(), list.get(arg2).toString(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Log.e("Fail 3", e.toString());
        }

        if (locationHelper.checkPlayServices()) {

            locationHelper.buildGoogleApiClient();
     }*/


    }



    void retriveLocation(){
        requestQueue = Volley.newRequestQueue(this);
        stringRequest = new StringRequest(Request.Method.POST, "http://hungerbite.com/hungerbite_app/search1.php"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //Toast.makeText(getApplicationContext(),"Response: "+response,Toast.LENGTH_LONG).show();

                if (response.equalsIgnoreCase("No data found")) {
                    Toast.makeText(getApplicationContext(), "Login Failed " + response, Toast.LENGTH_LONG).show();

                } else {

                    // Toast.makeText(getApplicationContext(), "Response: " + response, Toast.LENGTH_LONG).show();

                    System.out.println(response);
                    JSONArray JA = null;
                    try {
                        JA = new JSONArray(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JSONObject json = null;
                    final String[] str1 = new String[JA.length()];

                    for (int i = 0; i < JA.length(); i++) {
                        try {
                            json = JA.getJSONObject(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            str1[i] = json.getString("name");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    for (int i = 0; i < str1.length; i++) {
                        list.add(str1[i]);
                    }


                    ArrayAdapter<String> ad = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, list);


                    text.setThreshold(1);
                    text.setAdapter(ad);

                }

            }





        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error: "+error.getMessage(),Toast.LENGTH_LONG).show();

            }
        })

        ;

        requestQueue.add(stringRequest);
    }




    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG,"Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());

    }
    public void getAddress()
    {
        Address locationAddress;

        locationAddress=locationHelper.getAddress(latitude,longitude);

        if(locationAddress!=null)
        {
            String address = locationAddress.getAddressLine(0);
            String address1 = locationAddress.getAddressLine(1);
            String city = locationAddress.getLocality();
            String state = locationAddress.getAdminArea();
            String country = locationAddress.getCountryName();
            String postalCode = locationAddress.getPostalCode();


            String blah = address;

            String [] parts = blah.split(","); //all parts stored in an array
            //Printing the array to show you the array content.
            for(String s : parts) {
                System.out.println("parts : " + s);
            }
            ab=parts[2].toLowerCase().toString().trim();

            System.out.println("parts : " + ab);

         //   Intent i= new Intent(LocationActivity.this,FirstActivity.class);
           // i.putExtra("Lpk", "aman park");
           // startActivity(i);


            String currentLocation;

            if(!TextUtils.isEmpty(address))
            {
                currentLocation=address;

                if (!TextUtils.isEmpty(address1))
                    currentLocation+=""+address1;

                if (!TextUtils.isEmpty(city))
                {
                    currentLocation+="\n"+city;

                    if (!TextUtils.isEmpty(postalCode))
                        currentLocation+=" - "+postalCode;
                }
                else
                {
                    if (!TextUtils.isEmpty(postalCode))
                        currentLocation+="\n"+postalCode;
                }

                if (!TextUtils.isEmpty(state))
                    currentLocation+="\n"+state;

                if (!TextUtils.isEmpty(country))
                 currentLocation+="\n"+country;

                //t1.setVisibility(View.GONE);
               // t2.setText(currentLocation);
//                t2.setVisibility(View.VISIBLE);
                text.setText(ab);
                text.setEnabled(false);

                Arrays.asList().contains("any");


                if(!btn2.isEnabled())
                   btn2.setEnabled(true);
            }

        }
        else{
          //  Snackbar.make(getApplicationContext(),"Unable to Fetch Location",Snackbar.LENGTH_LONG).show();
            Snackbar snackbar = Snackbar
                    .make(c1, "Unable to Fetch Location", Snackbar.LENGTH_LONG);
            snackbar.show();
    }


}

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}


