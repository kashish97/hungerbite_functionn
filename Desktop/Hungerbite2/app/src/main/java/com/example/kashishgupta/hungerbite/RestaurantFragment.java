package com.example.kashishgupta.hungerbite;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import java.util.ArrayList;
import java.util.List;


public class RestaurantFragment extends Fragment {

    RecyclerView recyclerView;

    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    private RecyclerView mRVFish;
    private RestaurantRecycler mAdapter;
    List<Restaurant> data;


    View view ;

    String Loc;

String URL_PRODUCTS="http://hungerbite.com/hungerbite_app/abc.php";
    RecyclerView.Adapter recyclerViewadapter;



    public static RestaurantFragment newInstance(String param1, String param2) {
        RestaurantFragment fragment = new RestaurantFragment();
        Bundle args = new Bundle();
        args.putString("bjjh", param1);
        args.putString("bhbh", param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            Loc = getArguments().getString("Lpk");
            //mParam2 = getArguments().getString(ARG_PARAM2);

        }
        Toast.makeText(getActivity(), Loc, Toast.LENGTH_LONG).show();
    }







    private class AsyncFetch extends AsyncTask<String, String, String> {

        ProgressDialog pdLoading = new ProgressDialog(getActivity());
        HttpURLConnection conn;
        URL url = null;
        String searchQuery;

        public AsyncFetch(String searchQuery) {
            this.searchQuery = searchQuery;
        }

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
                url = new URL("http://hungerbite.com/hungerbite_app/abc.php");

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {

                System.out.print("preee k try m");
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(30000);
                conn.setConnectTimeout(30000);
                conn.setRequestMethod("GET");

                // setDoInput and setDoOutput to true as we send and recieve data
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // add parameter to our above url
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("location", Loc);
                String query = builder.build().getEncodedQuery();

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
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
                    return (result.toString());

                } else {
                    return ("Connection error");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                conn.disconnect();
                System.out.print("m yha se gya");
            }


        }
        @Override
        protected void onPostExecute(String result) {

            //this method will be running on UI thread
            pdLoading.dismiss();

            System.out.print("m here..");

            pdLoading.dismiss();
            data = new ArrayList<>();


            if (result.equals("No data found")) {
                Toast.makeText(getActivity(), "No Results found for entered query", Toast.LENGTH_LONG).show();
            } else {

                try {

                    String abc = result.toString();
                    JSONObject json = new JSONObject(result);
                    org.json.JSONArray jArray = json.getJSONArray("results");


                    // Extract data from json and store into ArrayList as class objects
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json_data = jArray.getJSONObject(i);
                        // accessing each item in the array
                        String r1 = json_data.optString("name");
                        String r2 = json_data.optString("res_address");
                        String r3 = json_data.optString("city");
                        String r4 = json_data.optString("minimum_order");
                        String r5 = json_data.optString("logo");
                        Restaurant fishData = new Restaurant(r1, r2, r3, r4, r5);

                        data.add(fishData);
                    }

                    // Setup and Handover data to recyclerview
                    mAdapter = new RestaurantRecycler(getActivity(), data);
                    mRVFish.setAdapter(mAdapter);
                    mRVFish.setLayoutManager(new LinearLayoutManager(getActivity()));

                } catch (JSONException e) {
                    // You to understand what actually error is and handle it appropriately
                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                    System.out.println(e.toString());
                    Toast.makeText(getActivity(), result.toString(), Toast.LENGTH_LONG).show();
                }

            }

        }}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_restaurant, container, false);
        System.out.print("View created");

        mRVFish = (RecyclerView) v.findViewById(R.id.recycle1);


        new AsyncFetch(Loc).execute();





    return v;
    }




    }


