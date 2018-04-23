package com.example.kashishgupta.hungerbite;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShowCartActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {


    ListView listView;

float a;
    EditText eTxtSearch;

    ContentResolver resolver;
    ArrayList<Cart> userList;
    Button login, register;

    int count;
    CartAdapter adapter;
    Cart cart;
    int pos;
    int fact = 0;
    float Total;
    List<Integer> test ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_cart);
        resolver = getContentResolver();

        listView = (ListView) findViewById(R.id.lv1);
        Intent rcv = getIntent();
        String s = rcv.getStringExtra("Gst");
        a = Integer.parseInt(s);
       // Toast.makeText(getApplicationContext(), "Gst is " + s, Toast.LENGTH_LONG).show();
         Button fab = (Button) findViewById(R.id.fab);
        login =(Button) findViewById(R.id.buttonLogin);
        register=(Button) findViewById(R.id.buttonRegister);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowCartActivity.this, LoginActivity.class);

                Bundle args = new Bundle();
                args.putSerializable("ARRAYLIST",(Serializable)userList);
                args.putString("nettotal", String.valueOf(Total));
                args.putString("subtotal",String.valueOf(fact));
                args.putString("gst", String.valueOf(a));
                args.putString("count", String.valueOf(count));
                intent.putExtra("BUNDLE",args);


              //  Toast.makeText(getApplicationContext(), "cart"+userList, Toast.LENGTH_LONG).show();

                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowCartActivity.this, RegisterationActivity.class);
                intent.putExtra("list", userList);
                intent.putExtra("nettotal", Total);
                intent.putExtra("subtotal", fact);
                intent.putExtra("gst", a);
                intent.putExtra("count", count);
                startActivity(intent);
            }
        });


        retrieveUsers();
        listView.setOnItemClickListener(this);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        listView=null;
    }

    void retrieveUsers(){
        String[] projection = {Util.COL_FID,Util.COL_fNAME,Util.COL_Price,Util.COL_SplPrice, Util.COL_quantity, Util.COL_TOTAL};

        Cursor cursor = resolver.query(Util.USER_URI,projection,null,null,null);

        if(cursor!=null){

            userList = new ArrayList<>();
            //test = new ArrayList<>();

            int id=0; String t="";
            String n="",e="",p="",g="",c="", l="";
            while (cursor.moveToNext()){
                id = cursor.getInt(cursor.getColumnIndex(Util.COL_FID));
                n = cursor.getString(cursor.getColumnIndex(Util.COL_fNAME));
                e = cursor.getString(cursor.getColumnIndex(Util.COL_Price));
                l= cursor.getString(cursor.getColumnIndex(Util.COL_SplPrice));

                p = cursor.getString(cursor.getColumnIndex(Util.COL_quantity));

                t=cursor.getString(cursor.getColumnIndex(Util.COL_TOTAL));

                int b = Integer.parseInt(l);
                fact = fact+b;


               // Toast.makeText(getApplicationContext(),"Subtotal" + l,Toast.LENGTH_LONG).show();

             //User user = new User(id,n,e,p,g,c);
                //userList.add(user);

               // test.add()

                userList.add(new Cart(id,n,e,l,p, Integer.parseInt(t)));
            }

         //   String a =cursor.getString(cursor.getColumnIndex(Util.COL_Price));
           //     fact = fact + Integer.valueOf(a);


            adapter = new CartAdapter(this,R.layout.layout_cart,userList);
            listView.setAdapter(adapter);
            count= listView.getAdapter().getCount();





            Toast.makeText(getApplicationContext(),"count" + listView.getAdapter().getCount(),Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(),"Subtotal" + fact,Toast.LENGTH_LONG).show();
           // Toast.makeText(getApplicationContext(),"Total" + Integer.parseInt(t),Toast.LENGTH_LONG).show();
            GSt();





        }
        else {
            Toast.makeText(getApplicationContext(),"nullll",Toast.LENGTH_LONG).show();
        }

    }

    void showUser(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(cart.getNamef());
        builder.setMessage(cart.toString());
        builder.setPositiveButton("Done",null);
        builder.create().show();
    }


    void askForDeletion(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete: "+cart.getNamef());
        builder.setMessage("Are you Sure ?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteUser();
            }
        });
        builder.setNegativeButton("Cancel",null);
        builder.create().show();
    }

    void deleteUser(){

        String where = Util.COL_FID+" = "+cart.getIdf();
        //String where = Util.COL_ID+" = '"+user.getName()+"'";

        int i = resolver.delete(Util.USER_URI,where,null);

        if(i>0){
            userList.remove(pos);
            adapter.notifyDataSetChanged();
            Toast.makeText(this,cart.getNamef()+" deleted... ", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,cart.getNamef()+" not deleted... ", Toast.LENGTH_LONG).show();
        }

    }
    void GSt(){
     Total = fact + fact*a/100;
        Toast.makeText(getApplicationContext(),"Total after gst" + Total,Toast.LENGTH_LONG).show();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        pos = position;
        cart = userList.get(position);
askForDeletion();    }
}
