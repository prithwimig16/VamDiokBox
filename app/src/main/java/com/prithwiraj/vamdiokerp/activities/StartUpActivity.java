package com.prithwiraj.vamdiokerp.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.toolbox.Volley;
import com.prithwiraj.vamdiokerp.R;
import com.prithwiraj.vamdiokerp.model.ErpCurrentUser;
import com.prithwiraj.vamdiokerp.networks.VamHttpComm;
import com.prithwiraj.vamdiokerp.networks.VamHttpCommCallback;
import com.prithwiraj.vamdiokerp.utils.Config;
import com.prithwiraj.vamdiokerp.utils.Utils;

import org.json.JSONObject;

public class StartUpActivity extends AppCompatActivity implements VamHttpCommCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);
        Utils.volleyRequestQueue = Volley.newRequestQueue(this);
        Config.getSharedInstance().applicationContext = this.getApplicationContext();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // VamHttpComm.getNewInstance(this).callStartupService();
        //Utils.getInstance().displayLoading(this);
        String v = Utils.getValueFromPref("access_token");
        if (Utils.getValueFromPref("access_token") != null && Utils.getValueFromPref("access_token").length() > 0) {
//            VamHttpComm.getNewInstance(this).callGetUserDataService();
            loadDashboardScreen(); //for test wil change it later

        } else {
            this.loadLoginScreen();
        }
    }

    private void nextStep()
    {
        Utils.getInstance().displayLoading(this);
        if(ErpCurrentUser.getSharedInstance().getAccessToken().length()>0 )
        {
//            VamHttpComm.getNewInstance(this).callGetUserDataService();
            this.loadLoginScreen(); //for test wil change it later

        }
        else
        {
            this.loadLoginScreen();
        }
    }

    private void loadLoginScreen()
    {


        Utils.getInstance().hideLoading();
        Intent loginactivity = new Intent(StartUpActivity.this, LoginActivity.class);
        startActivity(loginactivity);
        finish();
    }

    private void loadDashboardScreen()
    {
        Utils.getInstance().hideLoading();
        Intent dashboardActivity = new Intent(StartUpActivity.this, MainActivity.class);
        startActivity(dashboardActivity);
        finish();
    }


    @Override
    public void onSuccess(boolean status, int tag, JSONObject jsonResponse) {
        //this.loadingView.setVisibility(View.INVISIBLE);
        Utils.getInstance().hideLoading();
        if(tag==VamHttpComm.STARTUP_SERVICE)
        {
//            JSONObject data=jsonResponse.optJSONObject("data");
//            Config.getSharedInstance().cityArray=data.optJSONArray("locations");
            if(Config.getSharedInstance().maintenanceMode)
            {
                final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.setTitle("VamDiok Box Maintenance");
                alertDialog.setMessage(Config.getSharedInstance().MAINTENANCE_MESSAGE);


                alertDialog.show();
            }
            else if(Config.getSharedInstance().forceUpdate)
            {
                final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("VamDiok Box Update");
                alertDialog.setMessage(Config.getSharedInstance().UPDATE_MESSAGE);
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + Config.getSharedInstance().APP_ID)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Config.getSharedInstance().PLAYSTORE_URL)));
                        }
                    }
                });
                alertDialog.show();
            }
            else if(Config.getSharedInstance().suggestUpdate)
            {
                final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("VamDiok Box Update");
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.setMessage(Config.getSharedInstance().UPDATE_MESSAGE);
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                        nextStep();
                    }
                });
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + Config.getSharedInstance().APP_ID)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Config.getSharedInstance().PLAYSTORE_URL)));
                        }
                    }
                });
                alertDialog.show();
            }
            else
            {
                nextStep();
            }
        }

        else if(tag==VamHttpComm.GET_USER_SERVICE)
        {
            if(jsonResponse!=null&&status)
            {
                //Config.IS_LOGIN_DONE=true;
                JSONObject userInfo = jsonResponse.optJSONObject("data");
                ErpCurrentUser.getSharedInstance().setupCurrentUser(userInfo);
                if(ErpCurrentUser.getSharedInstance().getLoggedIn())
                    this.loadDashboardScreen();

            }

            else
            {

                this.loadLoginScreen();

            }
        }

    }

    @Override
    public void onFailure(String error, int tag) {

        Utils.getInstance().hideLoading();
        if(tag==VamHttpComm.STARTUP_SERVICE)
        {
            final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Error");
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setMessage(VamHttpComm.GENERIC_ERROR_MESSAGE);
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Retry", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    VamHttpComm.getNewInstance(StartUpActivity.this).callStartupService();
                    alertDialog.dismiss();
                }
            });
        }


        loadLoginScreen();

    }
}
