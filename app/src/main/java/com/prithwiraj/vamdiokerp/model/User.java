package com.prithwiraj.vamdiokerp.model;

import org.json.JSONObject;

/**
 * Created by Prithwi on 28/04/18.
 */

public class User {

    public User(JSONObject object) {
        //this.uId=object.optString("uId");
        this.name=object.optString("v_full_name");
        this.companyName=object.optString("v_company_name");
        this.email=object.optString("v_email_id");
        this.country=object.optString("country");
        this.currency=object.optString("v_currency");
        this.role=object.optString("e_user_role_type");

    }

//    public String getuId() {
//        return uId;
//    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCountry() {
        return country;
    }

    public String getCurrency() {
        return currency;
    }
    public String getRole() {
        return role;
    }

    public String name;
    public String email;
    public String companyName;
    public String country;
    public String currency;
   // public String uId;
    public String role;

    public User() {
    }

    public User(String uId,String name, String email,String companyName,String country,String currency,String role ) {
        //this.uId = uId;
        this.name = name;
        this.email = email;
        this.companyName=companyName;
        this.country=country;
        this.currency=currency;
        this.role=role;

    }
}
