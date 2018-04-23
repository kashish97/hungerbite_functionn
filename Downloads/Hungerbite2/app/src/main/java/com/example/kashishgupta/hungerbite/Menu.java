package com.example.kashishgupta.hungerbite;

public class Menu {
String ResName;
String ResAddress;
String city;
String Logo;
String location;
String minorder;
String category;
String foodname;
String fpricediscounted;
String fpriceoriginal;
String fooddescription;


    public Menu(String resName, String resAddress, String city, String logo, String location, String minorder, String category, String foodname, String fpricediscounted, String fpriceoriginal, String fooddescription) {
        ResName = resName;
        ResAddress = resAddress;
        this.city = city;
        Logo = logo;
        this.location = location;
        this.minorder = minorder;
        this.category = category;
        this.foodname = foodname;
        this.fpricediscounted = fpricediscounted;
        this.fpriceoriginal = fpriceoriginal;
        this.fooddescription = fooddescription;
    }

    public Menu() {
    }

    public String getResName() {
        return ResName;
    }

    public void setResName(String resName) {
        ResName = resName;
    }

    public String getResAddress() {
        return ResAddress;
    }

    public void setResAddress(String resAddress) {
        ResAddress = resAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLogo() {
        return Logo;
    }

    public void setLogo(String logo) {
        Logo = logo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMinorder() {
        return minorder;
    }

    public void setMinorder(String minorder) {
        this.minorder = minorder;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public String getFpricediscounted() {
        return fpricediscounted;
    }

    public void setFpricediscounted(String fpricediscounted) {
        this.fpricediscounted = fpricediscounted;
    }

    public String getFpriceoriginal() {
        return fpriceoriginal;
    }

    public void setFpriceoriginal(String fpriceoriginal) {
        this.fpriceoriginal = fpriceoriginal;
    }

    public String getFooddescription() {
        return fooddescription;
    }

    public void setFooddescription(String fooddescription) {
        this.fooddescription = fooddescription;
    }
}
