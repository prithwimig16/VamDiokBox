package com.prithwiraj.vamdiokerp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.prithwiraj.vamdiokerp.utils.Utils;

import org.json.JSONObject;

/**
 * Created by Prithwi on 02/05/17.
 */

public class VamUser implements Parcelable {



  protected String strPushToken;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    protected String firstName;

    private String user_id;

    protected VamUser(Parcel in) {
        initialize();


        this.firstName = in.readString();
        this.user_id = in.readString();

        this.company = in.readString();

        this.contactNumber = in.readString();

        this.email = in.readString();


        this.userRole = in.readString();

        this.country = in.readString();
        this.state = in.readString();
        this.location = in.readString();
        this.countryId = in.readString();

        this.currency = in.readString();

    }

    public String getUser_id() {
        return user_id;
    }
    protected String company;




     protected String contactNumber;

    public String getEmail() {
        return email;
    }

    protected String email;


    protected String userRole;

    protected String country;
    protected String state;
    protected String location;
    protected String countryId;
    protected String currency;





    public String getFirstName() {
        return firstName;
    }




    public String getCompany() {
        return company;
    }


    public String getContactNumber() {
        return contactNumber;
    }

    public String getUserRole() {
        return userRole;
    }



    public String getCountry() {
        return country;
    }

    public String getState() {
        return state;
    }

    public String getLocation() {
        return location;
    }

    public String getCountryId() {
        return countryId;
    }

    public String getCurrency() {
        return currency;
    }

    protected VamUser() {
        initialize();
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public VamUser(JSONObject object) {

        this.initialize(object);

    }

    private void initialize() {
        this.firstName = "";
        this.user_id = "";
        this.company = "";
        this.email = "";
        this.userRole = "";
        this.country = "";
        this.state = "";
        this.location = "";
        this.currency = "";
        this.countryId = "";
        this.contactNumber = "";

    }

    private void initialize(JSONObject object) {
        try {

            this.firstName = object.optString("v_full_name");
            this.user_id = object.optString("id");

//            if (this.avatar.length() > 0 && this.avatar.startsWith("http") == false) {
//                this.avatar = "https://cre.clamhub.com" + this.avatar;
//            }

            this.company = object.optString("v_company_name");
            this.email = object.optString("v_email_id");
            this.userRole = object.optString("e_user_role_type");
            this.country = object.optString("v_device_param_country_name");


            this.location = object.optString("location");

            this.currency = object.optString("v_currency");
            this.countryId=object.optString("i_country_id");


        } catch (Exception e) {

            Utils.consoleLog(VamUser.class, e.getLocalizedMessage());
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(this.firstName);
        dest.writeString(this.user_id);
        dest.writeString(this.company);
        dest.writeString(this.contactNumber);
        dest.writeString(this.email);

        dest.writeString(this.userRole);

        dest.writeString(this.country);
        dest.writeString(this.state);
        dest.writeString(this.location);
        dest.writeString(this.countryId);

        dest.writeString(this.currency);

    }

    public static final Creator<VamUser> CREATOR = new Creator<VamUser>() {
        @Override
        public VamUser createFromParcel(Parcel in) {
            return new VamUser(in);
        }
         @Override
        public VamUser[] newArray(int size) {
            return new VamUser[0];
        }
    };

    @Override
    public int describeContents() {
        return hashCode();
    }


}