package com.example.kashishgupta.hungerbite;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Belal on 10/18/2017.
 */

public class MenuRecycler extends RecyclerView.Adapter<MenuRecycler.ProductViewHolder1> {


    ContentValues values;
    ContentResolver resolver;
    boolean updateMode;
    private MenuActivity mCtx;
    private List<Menu> menuList;
    int i = 0;
    int q=0;
    int quan=0;
    int pr;




    public MenuRecycler(MenuActivity mCtx, List<Menu> menuList) {
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

        int veg =Integer.parseInt(menu.getVeg());

        String veg1="https://www.image.amazerecipe.com/2016/06/vegetarian-symbol.png";
        String nonveg="http://www.iec.edu.in/app/webroot/img/Icons/84246.png";
        String drinks="http://www.eatlogos.com/food_and_drinks/png/vector_orange_drinks_logo.png";
holder.itemView.setOnTouchListener(new View.OnTouchListener() {
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mCtx.recycle(menu.getCategory().toString().trim().toLowerCase());
        return false;
    }
});
        if(veg==0){
            Glide.with(mCtx)
                    .load(veg1)
                    .into(holder.Logo);
        }
        else if(veg==1){
            Glide.with(mCtx)
                    .load(nonveg)
                    .into(holder.Logo);
        }
        else{
            Glide.with(mCtx)
                    .load(drinks)
                    .into(holder.Logo);
        }

        String url = "http://hungerbite.com/admin/uploads/";
        String url2 = menu.getLogo();
        String url3 = url+url2;

        //loading the image
        //Glide.with(mCtx)
        //      .load(url3)
        //    .into(holder.Logo);

        //  holder.textViewResname.setText(menu.getResName());
        //holder.textViewResAddress.setText(menu.getResAddress());
        //holder.textViewResLocation.setText(menu.getLocation());
        //  holder.textViewMinorder.setText(menu.getMinorder());
        //holder.textViewCategory.setText(menu.getCategory());

        holder.textViewFoodname.setText(menu.getFoodname());
        holder.textViewFpricediscou.setText(menu.getFpricediscounted());
        holder.textViewForiginal.setText(menu.getFpriceoriginal());
        holder.textViewFdescription.setText(menu.getFooddescription().toLowerCase().trim());





        holder.bcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quan++;
                holder.bdec.setVisibility(View.VISIBLE);
                holder.qty.setVisibility(View.VISIBLE);

                String b= menu.getLogo();

             // mCtx.showCart(b);

                if (holder.bcart.getText() == "+") {
                    ++q;
                }
                else {
                    q=1;
                }


                holder.qty.setText(""+q);

                holder.bdec.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(q>0){
                            --q;


                            String where = Util.COL_FID+" = "+menu.getCity();
                            int i = resolver.update(Util.USER_URI,values,where,null);
                            if(i>0){
                                Toast.makeText(mCtx,menu.getFoodname()+ " updated successfully "+i,Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(mCtx,menu.getFoodname()+ " could not be updated successfully "+i,Toast.LENGTH_LONG).show();
                            }

                            holder.qty.setText(""+q);
                            if(q==0){
                                holder.bdec.setVisibility(View.INVISIBLE);
                                holder.qty.setVisibility(View.INVISIBLE);
                                holder.bcart.setText("Add");


                                String where1 = Util.COL_FID+" = "+menu.getCity();
                                //String where = Util.COL_ID+" = '"+user.getName()+"'";

                                int i1 = resolver.delete(Util.USER_URI,where1,null);

                               if(i1>0){

                                    Toast.makeText(mCtx,menu.getFoodname()+" deleted... ", Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(mCtx,menu.getFoodname()+" not deleted... ", Toast.LENGTH_LONG).show();
                                }


                            }

                        }
                    }
                });


                String s = menu.getFoodname();
                String s1 = menu.getFpricediscounted();
                String s2 = menu.getFpriceoriginal();
                String id = menu.getCity();
                int a = Integer.parseInt(s2);
                int r = a * q;
                pr=pr+a;
                mCtx.showCart(b,quan, pr, s);
                 values = new ContentValues();
                values.put(com.example.kashishgupta.hungerbite.Util.COL_fNAME, s);
                values.put(com.example.kashishgupta.hungerbite.Util.COL_SplPrice, s1);

                values.put(com.example.kashishgupta.hungerbite.Util.COL_Price, s2);

                values.put(com.example.kashishgupta.hungerbite.Util.COL_quantity, q);
                values.put(com.example.kashishgupta.hungerbite.Util.COL_FID, id);
                values.put(com.example.kashishgupta.hungerbite.Util.COL_TOTAL, r);

                String[] projection = {Util.COL_FID,Util.COL_fNAME,Util.COL_Price,Util.COL_SplPrice, Util.COL_quantity, Util.COL_TOTAL};

                String where1 = Util.COL_FID+" = "+menu.getCity();

                Cursor cursor = resolver.query(Util.USER_URI,projection,where1,null,null);


                if(cursor!=null){
                    Toast.makeText(mCtx,"exists", Toast.LENGTH_LONG).show();
                }


                holder.qty.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {


                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });



                if(holder.bcart.getText()!="+") {

                    Uri uri = resolver.insert(com.example.kashishgupta.hungerbite.Util.USER_URI, values);
                    Toast.makeText(mCtx, " registered successfully with id" + s, Toast.LENGTH_LONG).show();

                    holder.bcart.setText("+");
                }

                else{

                    String where = Util.COL_FID+" = "+menu.getCity();
                    int i = resolver.update(Util.USER_URI,values,where,null);
                    if(i>0){
                        Toast.makeText(mCtx,menu.getFoodname()+ " updated successfully "+i,Toast.LENGTH_LONG).show();

                    }else{
                        Toast.makeText(mCtx,menu.getFoodname()+ " could not be updated successfully "+i,Toast.LENGTH_LONG).show();
                    }
                }

            }


        });

      /*  holder.bshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a= menu.getLogo();
                Intent i = new Intent(mCtx,ShowCartActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("Gst",a);
                mCtx.startActivity(i);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return menuList.size();    }

    class ProductViewHolder1 extends RecyclerView.ViewHolder {


        TextView textViewResname, textViewResAddress, textViewCity, textViewResLocation,
                textViewMinorder,textViewCategory,textViewFoodname,textViewFpricediscou,textViewForiginal,textViewFdescription,qty;
        ImageView Logo;
        Button bcart,bshow,binc,bdec,bset;
        public ProductViewHolder1(View itemView) {
            super(itemView);

            // textViewResname = itemView.findViewById(R.id.textViewResname);
            //textViewResAddress = itemView.findViewById(R.id.textViewResAddress);
            // textViewCity = itemView.findViewById(R.id.textViewCity);
            //textViewResLocation = itemView.findViewById(R.id.textViewResLocation);
            // textViewMinorder = itemView.findViewById(R.id.textViewMinorder);
            // textViewCategory = itemView.findViewById(R.id.textViewCategory);
            textViewFoodname = itemView.findViewById(R.id.textViewFoodname);
            textViewFpricediscou = itemView.findViewById(R.id.textViewFpricediscou);
            textViewForiginal = itemView.findViewById(R.id.textViewForiginal);
            textViewFdescription = itemView.findViewById(R.id.textViewFdescription);
           // qty = itemView.findViewById(R.id.editText5);
           Logo = itemView.findViewById(R.id.imageView4);
            bcart = itemView.findViewById(R.id.buttoncart);
            qty = itemView.findViewById(R.id.textView);
            textViewFpricediscou.setPaintFlags(textViewFpricediscou.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            // bshow = itemView.findViewById(R.id.button2);
            bdec = itemView.findViewById(R.id.buttondec);
            //bset = itemView.findViewById(R.id.buttonset);

        }
    }
}