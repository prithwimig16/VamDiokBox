package com.prithwiraj.vamdiokerp.model;

import org.json.JSONObject;

/**
 * Created by Prithwi on 29/04/18.
 */

public class Customer {
    
    public Customer(){}
    public  Customer(JSONObject jsonObject){
        this. firstName=jsonObject.optString("firstName");
        this. lastName=jsonObject.optString("lastName");
        this. companyName=jsonObject.optString("company");
        this. displayName=jsonObject.optString("displayName");
        this. email=jsonObject.optString("email");
        this. phoneNumber=jsonObject.optString("phoneNumber");
        this. currency=jsonObject.optString("currency");
        this. paymentTerms=jsonObject.optString("paymentTerms");
        this. billingAddress=jsonObject.optString("billingAddress");
        this. shippingaddress=jsonObject.optString("shippingAdrss");
        this.receivables=jsonObject.optDouble("receivables");
        this.payables=jsonObject.optDouble("payables");

        this. remarks=jsonObject.optString("remarks");
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCurrency() {
        return currency;
    }

    public String getPaymentTerms() {
        return paymentTerms;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public String getShippingaddress() {
        return shippingaddress;
    }
    public double getReceivables() {
        return receivables;
    }

    public double getPayables() {
        return payables;
    }


    public String getRemarks() {
        return remarks;
    }

    protected String firstName;
    protected String lastName;
    protected String companyName;
    protected String displayName;
    protected String email;
    protected String phoneNumber;
    protected String currency;
    protected String paymentTerms;
    protected String billingAddress;
    protected String shippingaddress;
    protected double receivables;
    protected double payables;
    protected String remarks;
    
}
