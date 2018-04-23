package com.example.kashishgupta.hungerbite;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuActivity extends Activity {
    RecyclerView recyclerView;

    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    private RecyclerView mRVFish;
    private MenuRecycler mAdapter;
    List<Menu> data;


    View view ;

    String name;
    String URL_PRODUCTS="http://hungerbite.com/hungerbite_app/menu1.php";
    RecyclerView.Adapter recyclerViewadapter;

    RequestQueue requestQueue;
    StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
Intent rcv = getIntent();

 name=rcv.getStringExtra("resn");

        mRVFish = (RecyclerView) findViewById(R.id.recycle2);

        retrieveMenu();
        Toast.makeText(getApplicationContext(),name,Toast.LENGTH_LONG).show();
        //Toast.makeText(getApplicationContext(),ids,Toast.LENGTH_LONG).show();



    }
    void retrieveMenu(){
        //pd.show();
        requestQueue = Volley.newRequestQueue(this);
        stringRequest = new StringRequest(Request.Method.POST, URL_PRODUCTS
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // Toast.makeText(getApplicationContext(),"Response: "+response,Toast.LENGTH_LONG).show();
                data = new ArrayList<>();



                try{

                    //  JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject json_data = jsonArray.getJSONObject(i);

                        String r1 = json_data.optString("resname");
                        String r2 = json_data.optString("resaddress");
                        String r3 = json_data.optString("food_id");
                        String r4 = json_data.optString("logo");
                        String r5 = json_data.optString("location");
                        String r6 = json_data.optString("minimum");
                        String r7 = json_data.optString("category");
                        String r8 = json_data.optString("foodname");
                        String r9 = json_data.optString("fprice1");
                        String r10 = json_data.optString("fprice2");
                        String r11 = json_data.optString("desc");






                        Menu mdata = new Menu(r1, r2, r3, r4, r5,r6,r7,r8,r9,r10,r11);

                        data.add(mdata);
                    }

                    mAdapter = new MenuRecycler(MenuActivity.this, data);
                    mRVFish.setAdapter(mAdapter);
                    mRVFish.setLayoutManager(new LinearLayoutManager(MenuActivity.this));

                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Exception: "+e.getMessage(),Toast.LENGTH_LONG).show();

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error: "+error.getMessage(),Toast.LENGTH_LONG).show();

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("resn",name);
                return map;
            }}
        ;
        requestQueue.add(stringRequest);
    }

}
