package com.example.kashishgupta.hungerbite;

import android.net.Uri;

public class Util {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "hungerbns.db";

    public static final String TABLE_NAME = "Cart_Hungers";
    public static final String COL_FID = "food_id";
    public static final String COL_fNAME = "fNAME";
    public static final String COL_SplPrice = "SplPrice";

    public static final String COL_Price = "Price";
    public static final String COL_quantity = "quantity";
    public static final String COL_TOTAL = "Total";



    public static final String CREATE_TAB_QUERY = "create table Cart_Hungers(" +
            "food_id text," +
            "fNAME text," +
            "SplPrice text," +
            "Price text," +
            "quantity text," +
            "Total text" +
            ")";

    public static final Uri USER_URI = Uri.parse("content://com.example.kashishgupta.hungerbite.usercontentprovider/"+TABLE_NAME);

    public static final String KEY_USER = "keyUser";
}
