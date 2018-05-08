package com.example.kashishgupta.hungerbite;


import android.content.ContentResolver;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

    /**
     * Created by ishantkumar on 20/07/17.
     */

    public class CartAdapter extends ArrayAdapter<Cart> {

        ContentResolver resolver;

        ShowCartActivity context;
        int resource,a;
        ArrayList<Cart> cartList1,cartList2;
Button bdel,badd;
        public CartAdapter(ShowCartActivity context, int resource, ArrayList<Cart> objects) {
            super(context, resource, objects);

            this.context = context;
            this.resource = resource;
            cartList1 = objects;


            cartList2 = new ArrayList<>();
            cartList2.addAll(cartList1);
        }


        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = null;

            view = LayoutInflater.from(context).inflate(resource,parent,false);

            TextView txtName = (TextView)view.findViewById(R.id.tvnamef);
            TextView txtEmail = (TextView)view.findViewById(R.id.tvpricef);
bdel = (Button)view.findViewById(R.id.button2);
            badd = (Button)view.findViewById(R.id.button5);

            final Cart cart = cartList1.get(position);
            txtName.setText(cart.getNamef());
            txtEmail.setText(cart.getPricef() +""+"X" + cart.getQuantityf()+ ""+"="+cart.getTotal());
            a = Integer.parseInt(cart.getQuantityf().toString().trim());

            badd.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        ++a;
        context.updateUser(cart.getIdf(),cart.getNamef(),cart.getPrices(),a,cart.getPricef());

        notifyDataSetChanged();
    }
});

            bdel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    context.askForDeletion(cart.getIdf(), cart.getNamef());
                }
            });

            return view;
        }

        public void filter(String str){

            cartList1.clear();

            if(str.length()==0){
                cartList1.addAll(cartList2);
            }else{
                for(int i=0;i<cartList2.size();i++){
                    if(cartList2.get(i).getNamef().toLowerCase().contains(str.toLowerCase())){
                        cartList1.add(cartList2.get(i));
                    }
                }
            }


            notifyDataSetChanged();
        }
    }




