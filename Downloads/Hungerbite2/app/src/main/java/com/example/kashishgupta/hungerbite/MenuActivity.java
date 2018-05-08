package com.example.kashishgupta.hungerbite;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MenuActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    ContentResolver resolver;

    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    private RecyclerView mRVFish;
    private MenuRecycler mAdapter;
    List<Menu> data;
    ListView listView1;
    String[] str1,str2;
    List<String>  arr1;
    TextView tvv;
    Button fab;
    String deli, img,locid, minorder, resid, rescity, locname, resname, restime;
    Dialog listDialog;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbar;
    private AppBarLayout appBarLayout;

    boolean appBarExpanded = true;
    Button button;
    String[] catg;
int r;

LinearLayoutManager manager;

    View view;
    private FloatingActionMenu fam;
    private com.github.clans.fab.FloatingActionButton fabEdit, fabDelete, fabAdd;
    String name;
    String URL_PRODUCTS = "http://hungerbite.com/hungerbite_app/menu1.php";
    RecyclerView.Adapter recyclerViewadapter;

    RequestQueue requestQueue;
    StringRequest stringRequest;
    int dis;

    void Helo(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.foodd);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {

            @SuppressWarnings("ResourceType")
            @Override
            public void onGenerated(Palette palette) {
                int vibrantColor = palette.getVibrantColor(R.color.white);
                collapsingToolbar.setTitle(resname);
                collapsingToolbar.setContentScrimColor(R.color.black_semi_transparent);
                collapsingToolbar.setStatusBarScrimColor(R.color.black);
            }
        });

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(Math.abs(verticalOffset) > 200){
                    appBarExpanded = true;
                }else{
                    appBarExpanded = false;
                }

            }
        });

    }
int ite=0;
    int pri=0;

    public void showCart(final String gst, final int items, final int total, final String name){
        button.setVisibility(View.VISIBLE);

ite= ite+items;
pri=pri+total;

        Toast.makeText(this, "items in cart", Toast.LENGTH_SHORT).show();
        button.setText("items in cart:"+items+"\n"+"Total"+total+"\n"+"item added:"+name);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MenuActivity.this, ShowCartActivity.class);
                intent.putExtra("Gst", gst);
                startActivity(intent);
            }
        });
    }

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
        ImageView imageView=(ImageView) findViewById(R.id.food);
        button= (Button) findViewById(R.id.butn);
        listDialog = new Dialog(MenuActivity.this);

        //TextView textView=(TextView) findViewById(R.id.tvrname);
        Helo();
        mRVFish = (RecyclerView) findViewById(R.id.recycle2);
        fab = (Button) findViewById(R.id.fab);
        CoordinatorLayout coordinatorLayout=(CoordinatorLayout) findViewById(R.id.abccc);



        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        Intent rcv = getIntent();

        name = rcv.getStringExtra("resn");
        deli = rcv.getStringExtra("del");
        img = rcv.getStringExtra("img");
        locid = rcv.getStringExtra("locid");
        minorder = rcv.getStringExtra("minorder");
        resid = rcv.getStringExtra("resid");
        rescity = rcv.getStringExtra("rescity");
        locname = rcv.getStringExtra("locname");
        resname = rcv.getStringExtra("resname");
        restime = rcv.getStringExtra("restime");

        String url = "http://hungerbite.com/admin/uploads/";
        String url2 = img;
        String url3 = url+url2;
        //textView.setText(resname);
        collapsingToolbar.setTitle(resname);

        retrieveMenu();

    }




    void retrieveMenu(){
        requestQueue = Volley.newRequestQueue(this);
        stringRequest = new StringRequest(Request.Method.POST, URL_PRODUCTS
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                data = new ArrayList<>();



                try{

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
                        String r12= json_data.optString("veg");
                        int fdis= Integer.parseInt(r10)- (Integer.parseInt(r10)*(dis/100));

                        String a = r7;

                        String b = a.substring(0,1).toUpperCase();
                        String c =  a.substring(1).toLowerCase();
                        str1[i]=b+c;



                        Menu mdata = new Menu(r1, r2, r3, r4, r5,r6,r7,r8,r9,r10,r11, r12);

                        data.add(mdata);
                    }

                    final List<String> arrList = new ArrayList<String>();
                    arr1 = new ArrayList<String>();
                    int cnt= 0;
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
                        if(arrList.get(k)!="Null"){
                        arr1.add(arrList.get(k));}
                    }
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //Toast.makeText(getApplicationContext(), arr1.toString(), Toast.LENGTH_LONG).show();


                            listDialog.setTitle("Menu");

                            LayoutInflater li = (LayoutInflater) MenuActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View v = li.inflate(R.layout.layout, null, false);
                            listDialog.setContentView(v);
                            listDialog.setCancelable(true);
                            ListView list1 = (ListView) listDialog.findViewById(R.id.listview1);

                            registerForContextMenu(list1);


                            list1.setAdapter(new ArrayAdapter<String>(getApplicationContext(),R.layout.layout_list,arr1));
                            list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    tvv = (TextView) view.findViewById(R.id.text1);
                                    listClick(tvv.getText().toString().trim().toLowerCase());
                                    tvv.setTextColor(getResources().getColor(R.color.g));
                                }
                            });
                            //now that the dialog is set up, it's tieme to show it
                           final Window window=listDialog.getWindow();
                           window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                           window.setGravity(Gravity.BOTTOM | Gravity.RIGHT);
                            listDialog.show();
                        }
                    });


                    //ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.layout_list,arr1);

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

    void listClick(final String category){
      for(Menu menu1 : data){
          if(menu1.getCategory().toString().trim().toLowerCase().contains(category)){
            r = data.indexOf(menu1);

          }
      }
mRVFish.smoothScrollToPosition(r);
      listDialog.cancel();

    }

void recycle(final String ctg){
        if(listDialog.isShowing()==true){
        if(tvv.getText().toString().trim().toLowerCase()==ctg){
            tvv.setTextColor(getResources().getColor(R.color.g));
        }}}
}


