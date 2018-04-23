package com.example.kashishgupta.hungerbite;

import android.util.JsonReader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

public class Cart implements Serializable {

    int idf;

    String namef;
    String pricef;
    String prices;
    String quantityf;
    int total;


    public Cart() {
    }

    public String getPrices() {
        return prices;
    }

    public void setPrices(String prices) {
        this.prices = prices;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Cart(int idf, String namef, String pricef, String prices, String quantityf, int total) {
        this.idf = idf;
        this.namef = namef;
        this.pricef = pricef;
        this.prices = prices;
        this.quantityf = quantityf;
        this.total= total;
    }

    public int getIdf() {
        return idf;
    }

    public void setIdf(int idf) {
        this.idf = idf;
    }

    public String getNamef() {
        return namef;
    }

    public void setNamef(String namef) {
        this.namef = namef;
    }

    public String getPricef() {
        return pricef;
    }

    public void setPricef(String pricef) {
        this.pricef = pricef;
    }

    public String getQuantityf() {
        return quantityf;
    }

    public void setQuantityf(String quantityf) {
        this.quantityf = quantityf;
    }

    @Override
    public String toString() {

        Map<String, String> data = new HashMap<>();
        data.put("id", String.valueOf(idf));
        data.put("namef", namef );
        data.put("pricef", pricef );
        data.put("prices", prices );
        data.put("quantityf", quantityf );
        data.put("total", String.valueOf(total) );






        JSONObject jsonData = new JSONObject(data);
        return jsonData.toString();

    }

}
