package com.example.kashishgupta.hungerbite;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;

import java.io.IOException;
import java.util.List;
import java.util.Locale;




public class LocationActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,ActivityCompat.OnRequestPermissionsResultCallback , hellofragment.OnFragmentInteractionListener{
    GoogleApiClient mGoogleApiClient;
    private static final String TAG = LocationActivity.class.getSimpleName();
    double latitude;
    double longitude;
    private Location mLastLocation;

    Button btn1,btn2;
    TextView t1,t2;
    LocationHelper locationHelper;
    String ab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        btn1=(Button) findViewById(R.id.button23);
        btn2=(Button) findViewById(R.id.button3);
        t1=(TextView) findViewById(R.id.textView2);
        t2=(TextView) findViewById(R.id.textView3);


        locationHelper=new LocationHelper(this);
        locationHelper.checkpermission();




        LocationRequest mLocationRequest =  LocationRequest.create();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), "one", Toast.LENGTH_SHORT).show();
                mLastLocation=locationHelper.getLocation();

                if (mLastLocation != null) {
                    latitude = mLastLocation.getLatitude();
                    longitude = mLastLocation.getLongitude();
                    getAddress();
                    Toast.makeText(getApplicationContext(), "two", Toast.LENGTH_SHORT).show();
                } else {

                    if(btn2.isEnabled())
                        btn2.setEnabled(false);


                }
            }
        });



        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("Lpk", ab);

// Your fragment
                hellofragment obj = new hellofragment();
                obj.setArguments(bundle);


                //showToast("Proceed to the next step");
            }
        });

        // check availability of play services
        if (locationHelper.checkPlayServices()) {

            // Building the GoogleApi client
            locationHelper.buildGoogleApiClient();
        }

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
            ab=parts[1];

            System.out.println("parts : " + ab);

            Intent i= new Intent(LocationActivity.this,FirstActivity.class);
            i.putExtra("Lpk", ab);
            startActivity(i);

// set MyFragment Arguments
           // FragmentManager fragmentManager = getFragmentManager();
            //android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            // Fragment fragment = new hellofragment();
            //fragment.setArguments(bundle);
            //fragmentTransaction.add(R.id.abe,fragment);
            //fragmentTransaction.commit();





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

                t1.setVisibility(View.GONE);
                t2.setText(currentLocation);
                t2.setVisibility(View.VISIBLE);

                if(!btn2.isEnabled())
                   btn2.setEnabled(true);
            }

        }
        else{
            Toast.makeText(getApplicationContext(), "wrong", Toast.LENGTH_SHORT).show();

           //   showToast("Something went wrong");
    }


}

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}


