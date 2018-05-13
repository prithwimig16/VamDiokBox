package com.prithwiraj.vamdiokerp.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.prithwiraj.vamdiokerp.R;
import com.prithwiraj.vamdiokerp.model.User;
import com.prithwiraj.vamdiokerp.networks.VamHttpComm;
import com.prithwiraj.vamdiokerp.networks.VamHttpCommCallback;
import com.prithwiraj.vamdiokerp.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener,VamHttpCommCallback {
    private static final int COUNTRY = 630;
    private static final int CURRENCY = 645;
    private EditText editTextEmail;
    private EditText editTextPassword,editTextName,editTextComapnyName;
    private Button buttonSignup,btCountry,btCurrency;
   // private ProgressDialog progressDialog;
    private String company,country,name,currency,email;
    JSONArray countrySelectedJson;
    JSONArray currencySelectedJson;
    TextView tv_already_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Utils.volleyRequestQueue = Volley.newRequestQueue(this);
        initView();
        registerUser();
    }
    private void initView(){
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        editTextComapnyName=(EditText)findViewById(R.id.editTextCompanyName);
        editTextName=(EditText)findViewById(R.id.editTextname);
        btCountry=(Button)findViewById(R.id.b_country);
        btCountry.setOnClickListener(this);
        btCurrency=(Button)findViewById(R.id.b_currency);
        btCurrency.setOnClickListener(this);
        buttonSignup = (Button) findViewById(R.id.buttonSignup);

        this.tv_already_user=(TextView)findViewById(R.id.tv_already_user);
        this.tv_already_user.setOnClickListener(this);

       // progressDialog = new ProgressDialog(this);

        //attaching listener to button
        buttonSignup.setOnClickListener(this);
        String[] countrydata = {"INDIA", "NIGERIA","USA"};
        String[] currencydata = {"₹(INR)", "₦(NAIRA)","$(USD)"};
        this.countrySelectedJson=new JSONArray(Arrays.asList(countrydata));
        this.currencySelectedJson=new JSONArray(Arrays.asList(currencydata));


    }

    private void registerUser(){

        //getting email and password from edit texts
        this. email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();
        this. company=editTextComapnyName.getText().toString().trim();
        this. country=btCountry.getText().toString().trim();
        this. name=editTextName.getText().toString().trim();
        this. currency=btCurrency.getText().toString().trim();

        //checking if email and passwords are empty

        if(TextUtils.isEmpty(name)){
            Toast.makeText(this,"Please enter your Name",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }
        if(country.equalsIgnoreCase("Tap here to select Country")){
            Toast.makeText(this,"Please enter country",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(company)){
            Toast.makeText(this,"Please enter company Name",Toast.LENGTH_LONG).show();
            return;
        }
        if(currency.equalsIgnoreCase("Tap here to select Currency")){
            Toast.makeText(this,"Please enter currency",Toast.LENGTH_LONG).show();
            return;
        }

        else{
            Utils.getInstance().displayLoading(this);
            VamHttpComm.getNewInstance(this).callSignUpService(email,password,company,country,name,currency);

        }


//        progressDialog.setMessage("Registering Please Wait...");
//        progressDialog.show();

        }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_already_user:
                Intent loginIntent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                finish();
                break;

            case R.id.buttonSignup:
                registerUser();
                break;

            case R.id.b_country:
                Intent countryIntent = new Intent(SignUpActivity.this, SelectFieldActivity.class);
                countryIntent.putExtra("options", countrySelectedJson.toString());
                countryIntent.putExtra("data_key", "country");
                countryIntent.putExtra("is_json_array", true);
                startActivityForResult(countryIntent, COUNTRY);
                overridePendingTransition(R.anim.righttoleft, R.anim.stable);
                break;


            case R.id.b_currency:
                Intent currencyIntent = new Intent(SignUpActivity.this, SelectFieldActivity.class);
                currencyIntent.putExtra("options", currencySelectedJson.toString());
                currencyIntent.putExtra("data_key", "currency");
                currencyIntent.putExtra("is_json_array", true);
                startActivityForResult(currencyIntent, CURRENCY);
                overridePendingTransition(R.anim.righttoleft, R.anim.stable);
                break;

        }
    }

    @Override
    public void onSuccess(boolean status, int tag, JSONObject jsonResponse) {
        Utils.getInstance().hideLoading();
        if(tag==VamHttpComm.SIGNUP_SERVICE)
        {
            if(jsonResponse!=null)
            {
                if (status) {
                    try {

                        JSONObject data = jsonResponse.getJSONObject("data");
                        if (data != null) {
                            JSONObject userDetails=data.optJSONObject("user_details");

                            String sucess = jsonResponse.getString("msg");

                            final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                            alertDialog.setTitle("VamDiokBox");
                            alertDialog.setCancelable(false);
                            alertDialog.setCanceledOnTouchOutside(false);
                            alertDialog.setMessage("SignUp Successfull.Please click ok to login with your Email and Password ");
                            if(jsonResponse.optString("msg").length()<0){
                                Toast.makeText(SignUpActivity.this, "please check your mail", Toast.LENGTH_SHORT).show();
                            }
                            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    alertDialog.dismiss();
                                    Intent login = new Intent(SignUpActivity.this, LoginActivity.class);
                                    startActivity(login);
                                    finish();
                                    //AnalyticsHandler.getSharedInstance().logEvent(CreateAccount.this,"CreateAccount","Signup_Email","Succesful",1);

                                }
                            });
                            alertDialog.show();
                            //finish();


//prithwiraj.nath@purpleyo.com

                        } else {
                            Utils.alert(VamHttpComm.GENERIC_ERROR_MESSAGE, this);
                        }
                    } catch (JSONException e) {

                        Utils.consoleLog(getClass(), e.getLocalizedMessage());
                        Utils.alert(VamHttpComm.GENERIC_ERROR_MESSAGE, this);
                    }
                } else {
                    Utils.displayServerErrorMessage(jsonResponse, this);



                }

            }
            else {
                Utils.alert(VamHttpComm.GENERIC_ERROR_MESSAGE, this);

            }
        }
    }

    @Override
    public void onFailure(String error, int tag) {
        Utils.alert(error,this);
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
            if (requestCode == COUNTRY) {

                this.btCountry.setText(value);
                //this.buttonAccessRole.setTag(id);

//                if(this.pyoUserObj!=null)
//                {
//                    this.pyoUserObj.setRole(value);
//                    this.pyoUserObj.setRoleId(id);
//                }

            } else if (requestCode == CURRENCY) {
                // fetch the message String
                this.btCurrency.setText(value);
//
//                this.pyoUserObj.setAccessStatus(value);

            }

        }
    }


}
