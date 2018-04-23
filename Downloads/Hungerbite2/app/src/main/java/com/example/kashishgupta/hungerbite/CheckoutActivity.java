package com.example.kashishgupta.hungerbite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
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
import java.util.Map;

public class CheckoutActivity extends AppCompatActivity {
StringRequest stringRequest;
RequestQueue requestQueue;
String URL = "http://hungerbite.com/hungerbite_app/placeorder.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        retrieveMenu();
    }
    void retrieveMenu(){
        //pd.show();
        requestQueue = Volley.newRequestQueue(this);
        stringRequest = new StringRequest(Request.Method.POST, URL
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(getApplicationContext(),"Response: "+response,Toast.LENGTH_LONG).show();
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
                map.put("subtotalres", "45"); // subtotal
                map.put("MEMBER", "593");  // member id
                map.put("nettotal", "75");  // without tax total
                map.put("login", "kashishgup9211@gmail.com");
                map.put("password", "kashish9211");
                map.put("fname", "kashish");
                map.put("lname", "gupta");
                map.put("sAddress", "999");
                map.put("box", "99");
                map.put("city", "ludhiana");
                map.put("houseno", "9");
                map.put("mNo", "13");
                map.put("lNo", "44");
                map.put("billing_location", "999");
                map.put("discountrescode", "999"); // dis code
                map.put("discountamount", "999"); // dis amount
                map.put("vat", "999");    // gst
                map.put("shipcharge", "999");  // shipping
                map.put("count", "999");  // no of items
                map.put("food_id", "999");
                map.put("total", "999");     // with tax
                map.put("food_price", "999");   // without tax discount
                map.put("food_specialprice", "999"); // without tax with discount
                map.put("quantity_id", "999"); // quantity
                map.put("restaurants_id", "999"); // restaurant_id

                return map;
            }};

        requestQueue.add(stringRequest);
    }


}
