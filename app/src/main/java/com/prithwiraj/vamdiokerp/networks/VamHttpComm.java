package com.prithwiraj.vamdiokerp.networks;

import android.content.Context;
import android.util.Base64;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.prithwiraj.vamdiokerp.model.ErpCurrentUser;
import com.prithwiraj.vamdiokerp.utils.Config;
import com.prithwiraj.vamdiokerp.utils.LocationHandler;
import com.prithwiraj.vamdiokerp.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VamHttpComm {
    public static final int STARTUP_SERVICE = 1000;
    public static final int LOGIN_SERVICE = 1001;
    public static final int DASHBOARD_SERVICE = 1002;
    public static final int LOGOUT_SERVICE = 1003;
    public static final int SIGNUP_SERVICE = 1004;
    public static final int GET_USER_SERVICE = 1005;

    public static final String GENERIC_ERROR_MESSAGE = "There was some problem in connecting to our server, " +
            "please try again!\nIf problem persist please " +
            "contact your Relationship Manager.";

    public static final int INVALID_ACCESS_TOKEN = 401;

    private Context context;
    private VamHttpCommCallback callback;
    private int tag;

    private VamHttpComm() {
    }

    private JSONObject values;
    private String finalUrl;

    public VamHttpComm(Context context) {
        this.context = context;
        this.callback = (VamHttpCommCallback) context;
        this.finalUrl = Config.getSharedInstance().TRANSACTION_API + Config.getSharedInstance().CONTROLLER_METHOD;
        this.values = new JSONObject();
    }

    public VamHttpComm(Context context, VamHttpCommCallback fragment) {
        this.context = context;
        this.callback = fragment;
        this.finalUrl = Config.getSharedInstance().TRANSACTION_API + Config.getSharedInstance().CONTROLLER_METHOD;
        this.values = new JSONObject();
    }


    public static VamHttpComm getNewInstance(Context context)
    {
        return new VamHttpComm(context);
    }

    public static VamHttpComm getNewInstance(Context context, VamHttpCommCallback fragment)
    {
        return new VamHttpComm(context,fragment);
    }

    private void processConnection() {

//        String s=VamCurrentUser.getSharedInstance().getUserID();
//        String s1=VamCurrentUser.getSharedInstance().getAccessToken();
        try {

            if(Config.getSharedInstance().TRANSACTION_ACCESS_KEY.length()>0) {
                String[] access= Config.getSharedInstance().TRANSACTION_ACCESS_KEY.split(":");
                if(access!=null&&access.length>1) {
                    this.values.put(access[0], access[1]);
                }
            }
//            if(VamCurrentUser.getSharedInstance().getUserID().length()>0&&VamCurrentUser.getSharedInstance().getAccessToken().length()>0)
//            {
//
//
//                this.values.put("user_id",VamCurrentUser.getSharedInstance().getUserID());
//                this.values.put("access_token",VamCurrentUser.getSharedInstance().getAccessToken());
//            }
//            else{
//                int i=0;
//            }

        }
        catch (JSONException e) {
            Utils.consoleLog(getClass(),e.getLocalizedMessage());
            if(this.callback!=null) {
                this.callback.onFailure(null, tag);
                this.callback = null;
            }
        }



        JSONObject jsonObject=this.values;
        Utils.consoleLog(VamHttpComm.class,"Post Data:"+this.values.toString());
        final JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, this.finalUrl, this.values, new Response.Listener<JSONObject>()
        {

            @Override
            public void onResponse(JSONObject response) {

                Utils.consoleLog(getClass(), response.toString());
                try {
                    boolean status = response.getBoolean("flag");

                    if(tag==STARTUP_SERVICE)
                    {
                        if(status)
                        {
                            Config.getSharedInstance().setMasterData(response.optJSONObject("data"));
                        }
                    }
                    else if(!status)
                    {
                        String error = response.optString("msg");
                        String errorCode = response.optString("code");
                        if(Integer.parseInt(errorCode)==INVALID_ACCESS_TOKEN)
                        {
                            ErpCurrentUser.getSharedInstance().displayLoginScreen(context);
                        }
                    }


                    if(callback!=null) {
                        callback.onSuccess(status, tag, response);
                        callback = null;
                    }
                } catch (JSONException e) {
                    Utils.consoleLog(getClass(),e.getLocalizedMessage());
                    if(callback!=null) {
                        callback.onFailure(null, tag);
                        callback = null;
                    }
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub

                Utils.consoleLog(getClass(), error.toString());

                //JSONObject rootObj = new JSONObject();
                String rootObj="";
                JSONObject errorObj = new JSONObject();
                try {
                    errorObj.put("error_code", "400");
                    errorObj.put("error_msg", error.toString());
                    //rootObj.put("error",errorObj);
                } catch (JSONException e) {
                    Utils.consoleLog(getClass(),e.getLocalizedMessage());
                    if(callback!=null) {
                        callback.onFailure(null, tag);
                        callback = null;
                    }
                }
                if(error.networkResponse!=null) {
                    if (callback != null) {

                        callback.onFailure(rootObj, tag);
                        callback = null;
                    }
                }
                else {
                    if (callback != null) {

                        callback.onFailure(null, tag);
                        callback = null;
                    }
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                if(tag!=STARTUP_SERVICE) {

                    String auth = "Basic "
                            + Base64.encodeToString(Config.getSharedInstance().B_AUTH.getBytes(), Base64.NO_WRAP);
                    headers.put("Authorization", auth);
                }

//                headers.put("Content-Type", "application/json");
//                headers.put("Host","appdev.clamhub.com");

                return headers;
            }
        };


        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsObjRequest.setRetryPolicy(policy);
        Utils.consoleLog(VamHttpComm.class,jsObjRequest.toString());
        Utils.volleyRequestQueue.add(jsObjRequest);

    }

    public void callStartupService()
    {
        try {
            this.tag = STARTUP_SERVICE;
            this.finalUrl = Config.getSharedInstance().STARTUP_API;
            this.values.put("action","startupLog");
            this.values.put("odid",Utils.getODID());
            //this.values.put("user_id",VamCurrentUser.getSharedInstance().getUserID());
            this.values.put("idfa",Utils.getIDFA());
            int deviceType = 4;//androidp
            if(Utils.isPhone()==false)
                deviceType = 5;//androidt

            this.values.put("device",""+deviceType);
            this.values.put("build",Utils.getAppVersion());
            //this.values.put("push_token","");
            //this.values.put("push_token_enabled", ErpCurrentUser.getSharedInstance().pushNotifyEnableStatus());
            this.values.put("device_model",Utils.deviceModel());
            this.values.put("properties",Utils.deviceName());
            this.values.put("os_name",Utils.systemName());
            this.values.put("os_version",Utils.systemVersion());
            this.values.put("os_other_info",Utils.getMachineName());
            this.values.put("device_localized_model",Utils.localizedModel());
            if(ErpCurrentUser.getSharedInstance().getCurrentDeviceMobileNumber()==null)
                this.values.put("mobilenumber", ErpCurrentUser.getSharedInstance().getCurrentDeviceMobileNumber());
            this.values.put("lat", LocationHandler.getSharedInstance().getLatitude());
            this.values.put("long",LocationHandler.getSharedInstance().getLongitude());
            this.values.put("device_location_permission", LocationHandler.getSharedInstance().isLocationPermissionGiven());
            this.processConnection();
        } catch (JSONException e) {
            Utils.consoleLog(getClass(),e.getLocalizedMessage());
            if(this.callback!=null) {
                callback.onFailure(null, tag);
                callback = null;
            }
        }

    }

    public void callGetUserDataService()
    {
        try {
            this.tag = GET_USER_SERVICE;
            this.values.put("action","getUser");
            this.values.put("odid",Utils.getODID());
            this.values.put("idfa",Utils.getIDFA());

            int deviceType = 4;//androidp
            if(Utils.isPhone()==false)
                deviceType = 5;//androidt

            this.values.put("device",""+deviceType);
            this.values.put("build",Utils.getAppVersion());
            // this.values.put("push_token","");
            //this.values.put("push_token_enabled",ErpCurrentUser.getSharedInstance().pushNotifyEnableStatus());
            this.values.put("device_model",Utils.deviceModel());
            this.values.put("properties",Utils.deviceName());
            this.values.put("os_name",Utils.systemName());
            this.values.put("os_version",Utils.systemVersion());
            this.values.put("os_other_info",Utils.getMachineName());
            this.values.put("device_localized_model",Utils.localizedModel());

            this.processConnection();
        } catch (JSONException e) {
            Utils.consoleLog(getClass(),e.getLocalizedMessage());
            if(this.callback!=null) {
                callback.onFailure(null, tag);
                callback = null;
            }
        }
    }

    public void callLoginService(String email,String password)
    {
        try
        {

            this.tag = LOGIN_SERVICE;
           // this.values.put("action","loginDetail");
            this.finalUrl=Config.getSharedInstance().STARTUP_API+"/signin";
            this.values.put("v_email_id",email);
            this.values.put("v_password",password);
            this.values.put("v_device_token",Config.getSharedInstance().TRANSACTION_ACCESS_KEY);
            this.values.put("v_app_version","v"+Utils.getAppVersion());
            this.values.put("v_device_param_odid",Utils.getODID());
            this.values.put("v_device_param_country_code",Utils.getCountryCode());
            this.values.put("v_device_param_country_name",Utils.getCountryName());
            this.values.put("v_device_param_system_locale",Utils.getSystemLocale());
            this.values.put("v_device_param_idfa",Utils.getIDFA());
            this.values.put("v_device_param_device_model",Utils.deviceModel());
            this.values.put("v_device_param_properties",Utils.deviceName());
            this.values.put("v_device_param_os_name",Utils.systemName());
            this.values.put("v_device_param_os_version",Utils.systemVersion());
            this.values.put("v_device_param_os_other_info",Utils.getMachineName());
            this.values.put("v_device_param_device_localized_model",Utils.localizedModel());

//            String deviceType = "android_phone";//androidp
//            if(Utils.isTablet(context)) {
//                deviceType = "android_tablet";//androidt
//            }
//            this.values.put("e_device_type",deviceType);





            //this.values.put("current_locale",Utils.getCurrentLocale());



            this.processConnection();
        }
        catch (JSONException e) {
            Utils.consoleLog(getClass(),e.getLocalizedMessage());
            if(this.callback!=null) {
                callback.onFailure(null, tag);
                callback = null;
            }
        }
    }

    public void callSignUpService(String email, String password,String  company,String country,String name,String currency) {
        try
        {

            this.tag = SIGNUP_SERVICE;
            // this.values.put("action","loginDetail");
            this.finalUrl=Config.getSharedInstance().STARTUP_API+"/signup";
            this.values.put("v_full_name",name);
            this.values.put("v_email_id",email);
            this.values.put("v_password",password);
            this.values.put("v_device_token",Config.getSharedInstance().TRANSACTION_ACCESS_KEY);
            this.values.put("v_app_version","v"+Utils.getAppVersion());
            this.values.put("e_register_type","");
            this.values.put("v_company_name",company);
            this.values.put("v_currency",currency);
            this.values.put("e_user_role_type","Admin");
            this.values.put("e_register_type","App");
            String deviceType = "Android";//androidp
            if(Utils.isTablet(context)) {
                deviceType = "Android";//androidt
            }
            this.values.put("e_device_type",deviceType);
            this.values.put("i_country_id","8");


//            this.values.put("v_device_param_odid",Utils.getODID());
//
//
//
//
//            this.values.put("v_device_param_country_code",Utils.getCountryCode());
//            this.values.put("v_device_param_country_name",Utils.getCountryName());
//            //this.values.put("current_locale",Utils.getCurrentLocale());
//            this.values.put("v_device_param_system_locale",Utils.getSystemLocale());
//            this.values.put("v_device_param_idfa",Utils.getIDFA());
//
//            this.values.put("v_device_param_device_model",Utils.deviceModel());
//            this.values.put("v_device_param_properties",Utils.deviceName());
//            this.values.put("v_device_param_os_name",Utils.systemName());
//            this.values.put("v_device_param_os_version",Utils.systemVersion());
//            this.values.put("v_device_param_os_other_info",Utils.getMachineName());
//            this.values.put("v_device_param_device_localized_model",Utils.localizedModel());
            this.processConnection();
        }
        catch (JSONException e) {
            Utils.consoleLog(getClass(),e.getLocalizedMessage());
            if(this.callback!=null) {
                callback.onFailure(null, tag);
                callback = null;
            }
        }
    }
}
