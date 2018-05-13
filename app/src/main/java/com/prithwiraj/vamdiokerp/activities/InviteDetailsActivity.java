package com.prithwiraj.vamdiokerp.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.prithwiraj.vamdiokerp.R;
import com.prithwiraj.vamdiokerp.model.User;
import com.prithwiraj.vamdiokerp.utils.Config;
import com.prithwiraj.vamdiokerp.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class InviteDetailsActivity extends AppCompatActivity implements  View.OnClickListener {
    private static final int USER_ROLE = 634;
    private static final int USER_STATUS = 635;
    User pyoUserObj;
    EditText etUserName, etEmail;
    Editable editUserName, editEmail;
    Button buttonBack, buttonAccessRole, buttonAccessStatus, buttonSaveInvite;
    boolean allViewsLoaded,isNewLeads;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_details);

        Intent intent = getIntent();
        this.pyoUserObj = intent.getParcelableExtra("PyoUser");
        initialaizeControl();
        if(this.pyoUserObj==null){
            taskForInviteMembers();
        }

        else if(pyoUserObj!=null){
            refreshView();
        }
    }


    private void initialaizeControl() {

        this.etUserName = (EditText) findViewById(R.id.e_user_name);
        this.etEmail = (EditText) findViewById(R.id.e_email_address);
        this.buttonBack = (Button) findViewById(R.id.b_cancel);
        this.buttonBack.setOnClickListener(this);
        this.buttonAccessRole = (Button) findViewById(R.id.b_access_role);
        this.buttonAccessRole.setOnClickListener(this);


        this.buttonAccessRole .setShadowLayer(0,0,0,R.color.white);
        this.buttonAccessRole .setTypeface(Config.OpenSans_Regular);
        this.buttonAccessRole .setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
        this.buttonAccessRole .setPadding(20,10,20,10);
        this.buttonAccessRole .setGravity(Gravity.CENTER_VERTICAL|Gravity.LEFT);
        this.buttonAccessRole .setTextColor(getResources().getColor(R.color.Blue));
        this.buttonAccessRole .setBackgroundColor(getResources().getColor(R.color.white));
        this.buttonAccessRole .setBackgroundResource(R.drawable.et_state);



        this.buttonAccessStatus = (Button) findViewById(R.id.b_access_status);
        this.buttonAccessStatus.setOnClickListener(this);


        this.buttonAccessStatus .setShadowLayer(0,0,0,R.color.white);
        this.buttonAccessStatus .setTypeface(Config.OpenSans_Regular);
        this.buttonAccessStatus .setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
        this.buttonAccessStatus .setPadding(20,10,20,10);
        this.buttonAccessStatus .setGravity(Gravity.CENTER_VERTICAL|Gravity.LEFT);
        this.buttonAccessStatus .setTextColor(getResources().getColor(R.color.Blue));
        this.buttonAccessStatus .setBackgroundColor(getResources().getColor(R.color.white));
        this.buttonAccessStatus .setBackgroundResource(R.drawable.et_state);


        this.buttonSaveInvite = (Button) findViewById(R.id.b_save_invite);
        this.buttonSaveInvite.setOnClickListener(this);

        this.allViewsLoaded = true;


    }

    private void taskForInviteMembers(){
        this.buttonAccessStatus.setVisibility(View.INVISIBLE);
        this.buttonSaveInvite.setText("Save Invite");
        TextView textView=(TextView)findViewById(R.id.tv_access_status);
        textView.setVisibility(View.INVISIBLE);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b_cancel:
                finish();
                break;
            case R.id.b_access_role:

                Intent accessRoleIntent = new Intent(InviteDetailsActivity.this, SelectFieldActivity.class);
                accessRoleIntent.putExtra("options", "ADMIN,USER");

                accessRoleIntent.putExtra("data_key", "role");
                if(this.pyoUserObj!=null) {
                    accessRoleIntent.putExtra("selection", this.pyoUserObj.getRole());
                }
                accessRoleIntent.putExtra("is_json_array", true);
//                if(accessRoleIntent==null){
//                    accessRoleIntent.putExtra("selection", "Admin");
//                }
                startActivityForResult(accessRoleIntent, USER_ROLE);
                overridePendingTransition(R.anim.righttoleft, R.anim.stable);
                break;

            case R.id.b_access_status:
                Intent accessStatusIntent = new Intent(InviteDetailsActivity.this, SelectFieldActivity.class);

                accessStatusIntent.putExtra("options", Config.getSharedInstance().globalOptions.get("broker_status_options").toString());
                Utils.consoleLog(InviteDetailsActivity.class, Config.getSharedInstance().globalOptions.get("broker_status_options").toString());

                accessStatusIntent.putExtra("selection", this.pyoUserObj.getRole());
                accessStatusIntent.putExtra("is_json_array", false);

                startActivityForResult(accessStatusIntent, USER_STATUS);
                overridePendingTransition(R.anim.righttoleft, R.anim.stable);
                break;
            case R.id.b_save_invite:

//                if(pyoUserObj!=null){
//                    setData();
//                }
//                else if(pyoUserObj==null){
//                    saveInviteTask();
//                }

                final AlertDialog alertDialog = new AlertDialog.Builder(InviteDetailsActivity.this).create();
                alertDialog.setTitle("VamDiok ERP");
                alertDialog.setCancelable(false);
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.setMessage("Invitation send. Thank you.");

                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "great", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();

                    }
                });
                alertDialog.show();
                break;


        }
    }

    private void refreshView() {

        if (this.pyoUserObj != null) {
            this.etUserName.setText(this.pyoUserObj.getName());
            this.etEmail.setText(this.pyoUserObj.getEmail());


            if (this.pyoUserObj.getRole()!= null && this.pyoUserObj.getRole().length() > 0 || this.pyoUserObj.getRole().equalsIgnoreCase("null"))
                this.buttonAccessStatus.setText(this.pyoUserObj.getRole());
            else
                this.buttonAccessStatus.setText("Tap here to select");





            if (this.pyoUserObj.getRole()!= null && this.pyoUserObj.getRole().length() > 0 || this.pyoUserObj.getRole().equalsIgnoreCase("null"))
                this.buttonAccessRole.setText(this.pyoUserObj.getRole());
            else
                this.buttonAccessRole.setText("Tap here to select");

        }
        updateView();
    }
    private void updateView() {

        if (this.etUserName != null && this.etEmail != null) {

            this.editEmail = this.etEmail.getText();
            this.editUserName = this.etUserName.getText();
//
//            this.pyoUserObj.setUserName(this.editUserName.toString());
//            this.pyoUserObj.setEmail(this.editEmail.toString());
        } else {
            refreshView();

        }
    }
    @Override
    public void onStop() {
        super.onStop();
        this.allViewsLoaded = false;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (resultCode == Activity.RESULT_OK) {

            if (intent == null)
                return;

            String optionsJson = intent.getStringExtra("options");
            String dataKey = intent.getStringExtra("data_key");
            int selectedIndex = intent.getIntExtra("selected_index", -1);

            boolean isJsonArray = intent.getBooleanExtra("is_json_array", false);
            if (selectedIndex < 0)
                return;

            String value = "";
            String id = "";

            try {

                JSONArray jsonArray = new JSONArray(optionsJson);
                if (jsonArray.length() > selectedIndex) {
                    if (isJsonArray == true) {


                        JSONObject jsonObject = jsonArray.getJSONObject(selectedIndex);
                        value = jsonObject.optString(dataKey);
                        id = jsonObject.optString("id");

                    } else {

                        value = jsonArray.optString(selectedIndex);
                    }

                }

            } catch (JSONException e) {
                Utils.consoleLog(SelectFieldActivity.class, e.getLocalizedMessage());
                Utils.alert("Something went wrong while selecting option, please try again", this);
            }

            // check if the request code is same as what is passed  here it is 2
            if (requestCode == USER_ROLE) {

                this.buttonAccessRole.setText(value);
                this.buttonAccessRole.setTag(id);

//                if(this.pyoUserObj!=null)
//                {
//                    this.pyoUserObj.setRole(value);
//                    this.pyoUserObj.setRoleId(id);
//                }

            } else if (requestCode == USER_STATUS) {
                // fetch the message String
//                this.buttonAccessStatus.setText(value);
//
//                this.pyoUserObj.setAccessStatus(value);

            }

        }
    }


}
