package com.prithwiraj.vamdiokerp.model;

import android.content.Context;


import com.prithwiraj.vamdiokerp.utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Prithwi on 02/05/17.
 */

public class ErpCurrentUser extends VamUser {

    private String accessToken;
    private  boolean isLoggedIn;
    private String currentDeviceMobileNumber;
    private static ErpCurrentUser _instance = null;




    public void logOut(){

        this.firstName="";
        this.accessToken="";
//        this.email="";
        this.company="";

        Utils.saveValueInPref("api_token","");
        Utils.saveValueInPref("v_full_name","");
    }

    public static ErpCurrentUser getSharedInstance()
    {
        if(_instance == null)
        {
            _instance = new ErpCurrentUser();
        }
        return _instance;
    }


    private ErpCurrentUser() {
        super();
        init();
    }

    private ErpCurrentUser(JSONObject object) {
        super(object);
        init(object);
    }

    private void init() {

    }

    private void init(JSONObject object) {
        this.isLoggedIn = false;
        if(this.getFirstName().length()>0) {

            try {
                String tempAccessToken = object.optString("api_token");
                if(tempAccessToken!=null&&!tempAccessToken.isEmpty())
                {
                    this.accessToken = tempAccessToken;
                    Utils.saveValueInPref("v_full_name", this.firstName);
                    Utils.saveValueInPref("v_email_id", this.getEmail());
                    //Utils.saveValueInPref("mobile",this.getContactNumber());
                    //Utils.saveValueInPref("userid", this.uId);
                   // Utils.saveValueInPref("country",this.country);
                    Utils.saveValueInPref("v_currency",this.currency);
                    Utils.saveValueInPref("v_company_name",this.company);
                    this.isLoggedIn = true;
                }
                else
                {
                    this.accessToken = Utils.getValueFromPref("api_token");
                    if(this.accessToken==null) {
                        this.accessToken = "";
                        this.isLoggedIn = false;
                    }
                    else
                        this.isLoggedIn = true;
                }
            } catch (Exception e) {
                Utils.consoleLog(ErpCurrentUser.class,e.getLocalizedMessage());
            }

        }




    }

    public void setupCurrentUser(JSONObject userInfo)
    {
        if(userInfo!=null) {

            _instance = new ErpCurrentUser(userInfo);


        }
    }

    public String getAccessToken() {
        return accessToken;
    }

    public Boolean getLoggedIn() {
        return isLoggedIn;
    }

    public String getCurrentDeviceMobileNumber() {
        return currentDeviceMobileNumber;
    }

    public void setCurrentDeviceMobileNumber(String currentDeviceMobileNumber) {
        this.currentDeviceMobileNumber = currentDeviceMobileNumber;
    }



    public void displayLoginScreen(Context context)
    {


    }
}
