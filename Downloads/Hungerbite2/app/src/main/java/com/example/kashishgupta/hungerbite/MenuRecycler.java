package com.example.kashishgupta.hungerbite;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Belal on 10/18/2017.
 */

public class MenuRecycler extends RecyclerView.Adapter<MenuRecycler.ProductViewHolder1> {


    ContentResolver resolver;
    boolean updateMode;
    private Context mCtx;
    private List<Menu> menuList;

    public MenuRecycler(Context mCtx, List<Menu> menuList) {
        this.mCtx = mCtx;
        this.menuList = menuList;
    }

    @Override
    public ProductViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_menurecycler, null);
        resolver = mCtx.getContentResolver();

        return new ProductViewHolder1(view);
    }

    @Override
    public void onBindViewHolder(final ProductViewHolder1 holder, int position) {
        final Menu menu = menuList.get(position);

        String url = "http://hungerbite.com/admin/uploads/";
        String url2 = menu.getLogo();
        String url3 = url+url2;

        //loading the image
        //Glide.with(mCtx)
          //      .load(url3)
            //    .into(holder.Logo);

        holder.qty.setText("1");
        holder.textViewResname.setText(menu.getResName());
        holder.textViewResAddress.setText(menu.getResAddress());
        holder.textViewResLocation.setText(menu.getLocation());
        holder.textViewMinorder.setText(menu.getMinorder());
        holder.textViewCategory.setText(menu.getCategory());
        holder.textViewFoodname.setText(menu.getFoodname());
        holder.textViewFpricediscou.setText(menu.getFpricediscounted());
        holder.textViewForiginal.setText(menu.getFpriceoriginal());
        holder.textViewFdescription.setText(menu.getFooddescription());
        holder.bcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = menu.getFoodname();
                String s1 = menu.getFpricediscounted();
                String s2= menu.getFpriceoriginal();
                String id = menu.getCity();
                int a = Integer.parseInt(s1);
                int q= Integer.parseInt(holder.qty.getText().toString().trim());
                int r= a*q;
                ContentValues values = new ContentValues();
                values.put(com.example.kashishgupta.hungerbite.Util.COL_fNAME,s);
                values.put(com.example.kashishgupta.hungerbite.Util.COL_SplPrice,s1);

                values.put(com.example.kashishgupta.hungerbite.Util.COL_Price,s2);

                values.put(com.example.kashishgupta.hungerbite.Util.COL_quantity,q);
                values.put(com.example.kashishgupta.hungerbite.Util.COL_FID,id);
                values.put(com.example.kashishgupta.hungerbite.Util.COL_TOTAL, r);



                //if(!updateMode) {
                    Uri uri = resolver.insert(com.example.kashishgupta.hungerbite.Util.USER_URI, values);
              //  Intent i = new Intent(mCtx,ShowCartActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //mCtx.startActivity(i);

                   Toast.makeText(mCtx,  " registered successfully with id"+s, Toast.LENGTH_LONG).show();
                //}else{
                    //String where = com.example.kashishgupta.hungerbite.Util.COL_ID+" = "+rcvUser.getId();
                  //  int i = resolver.update(com.example.kashishgupta.hungerbite.Util.USER_URI,values,where,null);
                    //if(i>0){
                      //Toast.makeText(this,rcvUser.getName()+ " updated...", Toast.LENGTH_LONG).show();

                }


        });

holder.bshow.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String a= menu.getLogo();
        Intent i = new Intent(mCtx,ShowCartActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("Gst",a);
        mCtx.startActivity(i);
    }
});
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    class ProductViewHolder1 extends RecyclerView.ViewHolder {

        EditText qty;

        TextView textViewResname, textViewResAddress, textViewCity, textViewResLocation,
                textViewMinorder,textViewCategory,textViewFoodname,textViewFpricediscou,textViewForiginal,textViewFdescription;
        ImageView Logo;
Button bcart,bshow;
        public ProductViewHolder1(View itemView) {
            super(itemView);

            textViewResname = itemView.findViewById(R.id.textViewResname);
            textViewResAddress = itemView.findViewById(R.id.textViewResAddress);
           // textViewCity = itemView.findViewById(R.id.textViewCity);
            textViewResLocation = itemView.findViewById(R.id.textViewResLocation);
            textViewMinorder = itemView.findViewById(R.id.textViewMinorder);
            textViewCategory = itemView.findViewById(R.id.textViewCategory);
            textViewFoodname = itemView.findViewById(R.id.textViewFoodname);
            textViewFpricediscou = itemView.findViewById(R.id.textViewFpricediscou);
            textViewForiginal = itemView.findViewById(R.id.textViewForiginal);
            textViewFdescription = itemView.findViewById(R.id.textViewFdescription);
            qty = itemView.findViewById(R.id.editText5);
            Logo = itemView.findViewById(R.id.Logo);
            bcart = itemView.findViewById(R.id.buttoncart);
            bshow = itemView.findViewById(R.id.button2);

        }
    }
}