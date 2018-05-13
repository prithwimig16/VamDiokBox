package com.prithwiraj.vamdiokerp.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseUser;
import com.prithwiraj.vamdiokerp.R;
import com.prithwiraj.vamdiokerp.model.ErpCurrentUser;
import com.prithwiraj.vamdiokerp.networks.VamHttpComm;
import com.prithwiraj.vamdiokerp.networks.VamHttpCommCallback;
import com.prithwiraj.vamdiokerp.utils.Config;
import com.prithwiraj.vamdiokerp.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,VamHttpCommCallback {
    Button btSignIn;
    TextView tvSignUp, tvForgotPassword;
    EditText inputEmail, inputPassword;
    //ProgressBar progressBar;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Config.getSharedInstance().applicationContext = this.getApplicationContext();
        Utils.volleyRequestQueue = Volley.newRequestQueue(this);
        String c=ErpCurrentUser.getSharedInstance().getEmail();
        init();
        try {
            if (ErpCurrentUser.getSharedInstance().getEmail() != null) {

                this.inputEmail.setText(ErpCurrentUser.getSharedInstance().getEmail());
            }
        } catch (Exception e) {
            Utils.alert("Something went wrong", this);

        }

    }


    @Override
    public void onStart() {
        super.onStart();


    }

    private void init() {
        this.btSignIn = (Button) findViewById(R.id.bt_signin);
        this.btSignIn.setOnClickListener(this);
        this.tvSignUp = (TextView) findViewById(R.id.tv_signup);
        this.tvForgotPassword = (TextView) findViewById(R.id.tv_fprgot_password);
        this.tvForgotPassword.setOnClickListener(this);
        this.tvSignUp.setOnClickListener(this);
        inputEmail = (EditText) findViewById(R.id.editTextLoginUSerName);
        inputPassword = (EditText) findViewById(R.id.editTextLoginPassword);
        //progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_signup:
                Intent signup = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(signup);
                finish();
                break;

            case R.id.bt_signin:
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
                loginUser();

                break;
            case R.id.tv_fprgot_password:
                showForgotPasswordAlert();
                break;
        }
    }

    private void loginUser() {


        String email = inputEmail.getText().toString();
        final String password = inputPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }

        else{
            Utils.getInstance().displayLoading(this);
            VamHttpComm.getNewInstance(this).callLoginService(email,password);
        }

//        progressBar.setVisibility(View.VISIBLE);
//        btSignIn.setVisibility(View.INVISIBLE);


    }

    @Override
    public void onSuccess(boolean status, int tag, JSONObject jsonResponse) {

        Utils.getInstance().hideLoading();
        if(tag==VamHttpComm.LOGIN_SERVICE) {
            if (jsonResponse != null) {

                if (status) {
                    try {

                        JSONObject data = jsonResponse.getJSONObject("data");
                        if (data != null) {
                            JSONObject userDetails = data.getJSONObject("user_details");

                            ErpCurrentUser.getSharedInstance().setupCurrentUser(userDetails);
                            Intent login = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(login);
                            finish();

                        } else {
                            Utils.alert(VamHttpComm.GENERIC_ERROR_MESSAGE, this);
                        }
                    } catch (JSONException e) {

                        Utils.consoleLog(getClass(), e.getLocalizedMessage());
                        Utils.alert(VamHttpComm.GENERIC_ERROR_MESSAGE, this);
                    }
                }
                else {
                    Utils.displayServerErrorMessage(jsonResponse, this);

                }
            } else {
                Utils.alert(VamHttpComm.GENERIC_ERROR_MESSAGE, this);

            }
        }


    }

    @Override
    public void onFailure(String error, int tag) {

        Utils.alert(error,this);

    }

    private void showForgotPasswordAlert() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginActivity.this);
        alertDialog.setTitle("FORGOT PASSWORD");
        alertDialog.setMessage("Enter your Email");

        final EditText input = new EditText(LoginActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);
        // alertDialog.setIcon(R.drawable.key);

        alertDialog.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String password = input.getText().toString();

                        if (password.length() > 0) {
                            Toast.makeText(getApplicationContext(),
                                    "Password Matched", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Please enter Password!", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }



}






