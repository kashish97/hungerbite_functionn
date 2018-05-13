package com.example.kashishgupta.hungerbite;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.clans.fab.FloatingActionMenu;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
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
    ListView listView1;
    String[] str1,str2;
    String contacts[]={"Ajay","Sachin","Sumit","Tarun","Yogesh"};



    View view;
    private FloatingActionMenu fam;
    private com.github.clans.fab.FloatingActionButton fabEdit, fabDelete, fabAdd;
    String name;
    String URL_PRODUCTS = "http://hungerbite.com/hungerbite_app/menu1.php";
    RecyclerView.Adapter recyclerViewadapter;

    RequestQueue requestQueue;
    StringRequest stringRequest;

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, v.getId(), 0, "Call");//groupId, itemId, order, title 
        menu.add(0, v.getId(), 0, "SMS");
    }



        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
      listView1 = (ListView) findViewById(R.id.listview1);
               // Register the ListView  for Context menu 


            final com.github.clans.fab.FloatingActionButton fab = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    registerForContextMenu(listView1);
                    listView1.setVisibility(View.VISIBLE);
                    fab.setVisibility(View.INVISIBLE);
                }
            });



        Intent rcv = getIntent();

        name = rcv.getStringExtra("resn");

        mRVFish = (RecyclerView)

                findViewById(R.id.recycle2);

        retrieveMenu();
        Toast.makeText(getApplicationContext(), name, Toast.LENGTH_LONG).show();
        /*func();
        //Toast.makeText(getApplicationContext(),ids,Toast.LENGTH_LONG).show();
        fabAdd = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab2);
        fabDelete = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab3);
        fabEdit = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab1);
        fam = (FloatingActionMenu) findViewById(R.id.fab_menu);

        //handling menu status (open or close)
        fam.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {
                if (opened) {
                    showToast("Menu is opened");
                } else {
                    showToast("Menu is closed");
                }
            }
        });

        //handling each floating action button clicked
      //  fabDelete.setOnClickListener(onButtonClick());
        fabEdit.setOnClickListener(onButtonClick());
        fabAdd.setOnClickListener(onButtonClick());

        fam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list1.add("neww");

            }
        });
    }

    private View.OnClickListener onButtonClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == fabAdd) {
                    showToast("Button Add clicked");
                } else if (view == fabDelete) {
                    showToast("Button Delete clicked");
                } else {
                    showToast("Button Edit clicked");
                }
                fam.close(true);
            }
        };*/
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
                     str1 = new String[jsonArray.length()];

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


                        str1[i]=json_data.optString("category");




                        Menu mdata = new Menu(r1, r2, r3, r4, r5,r6,r7,r8,r9,r10,r11);

                        data.add(mdata);
                    }

                    List<String> arrList = new ArrayList<String>();
                    List<String>  arr1 = new ArrayList<String>();
                    int cnt= 0;
                    //List<String> arrList = Arrays.asList(arr);
                    List<String> lenList = new ArrayList<String>();
                    for(int i=0;i<str1.length;i++){
                        for(int j=i+1;j<str1.length;j++){
                            if(str1[i].equals(str1[j])){
                                cnt+=1;
                            }
                        }
                        if(cnt<1){
                            arrList.add(str1[i]);
                        }
                        cnt=0;
                    }

                    for(int k=0;k<arrList.size();k++){
                        System.out.println("Array without Duplicates: "+arrList.get(k));
                        arr1.add(arrList.get(k));
                    }

                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,arr1);
                    listView1.setAdapter(adapter);

                    mAdapter = new MenuRecycler(MenuActivity.this, data);
                    mRVFish.setAdapter(mAdapter);
                    mRVFish.setLayoutManager(new LinearLayoutManager(MenuActivity.this));

                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Exception: "+e.getMessage(),Toast.LENGTH_LONG).show();

                    e.printStackTrace();
                }
                finally {

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
