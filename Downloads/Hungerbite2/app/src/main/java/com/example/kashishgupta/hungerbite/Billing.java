package com.example.kashishgupta.hungerbite;

public class Billing {
    String memberid;
    String Houseno;
    String streetaddress;
    String pobox;
    String city;
    String mobile;
    String landline;

    public Billing() {
    }


    public Billing(String memberid, String houseno, String streetaddress, String pobox, String city, String mobile, String landline) {
        this.memberid = memberid;
        Houseno = houseno;
        this.streetaddress = streetaddress;
        this.pobox = pobox;
        this.city = city;
        this.mobile = mobile;
        this.landline = landline;
    }


    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    public String getHouseno() {
        return Houseno;
    }

    public void setHouseno(String houseno) {
        Houseno = houseno;
    }

    public String getStreetaddress() {
        return streetaddress;
    }

    public void setStreetaddress(String streetaddress) {
        this.streetaddress = streetaddress;
    }

    public String getPobox() {
        return pobox;
    }

    public void setPobox(String pobox) {
        this.pobox = pobox;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLandline() {
        return landline;
    }

    public void setLandline(String landline) {
        this.landline = landline;
    }
}
