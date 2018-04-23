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

import com.android.volley.AuthFailureError;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

    RequestQueue requestQueue;
    StringRequest stringRequest;


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
       // Toast.makeText(getActivity(), Loc, Toast.LENGTH_LONG).show();
    }





    void retrieveRestaurants(){
        //pd.show();
        requestQueue = Volley.newRequestQueue(getActivity());
        stringRequest = new StringRequest(Request.Method.POST, URL_PRODUCTS
        , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // Toast.makeText(getActivity(),"Response: "+response,Toast.LENGTH_LONG).show();
                data = new ArrayList<>();


                try{

                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject json_data = jsonArray.getJSONObject(i);
                        // accessing each item in the array
                        String r1 = json_data.optString("name");
                        String r2 = json_data.optString("res_address");
                        String r3 = json_data.optString("");
                        String r4 = json_data.optString("minimum_order");
                        String r5 = json_data.optString("logo");
                        String r6 = json_data.optString("locid");
                        String r7 = json_data.optString("restid");

                        Restaurant fishData = new Restaurant(r1, r2, r3, r4, r5 ,r6,r7 );

                        data.add(fishData);
                    }

                    // Setup and Handover data to recyclerview
                    mAdapter = new RestaurantRecycler(getActivity(), data);
                    mRVFish.setAdapter(mAdapter);
                    mRVFish.setLayoutManager(new LinearLayoutManager(getActivity()));
                    //...

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"Error: "+error.getMessage(),Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("location",Loc);
                return map;
            }
        }
        ;
        requestQueue.add(stringRequest);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_restaurant, container, false);
        System.out.print("View created");

        mRVFish = (RecyclerView) v.findViewById(R.id.recycle1);

        retrieveRestaurants();


        return v;
    }


    }


