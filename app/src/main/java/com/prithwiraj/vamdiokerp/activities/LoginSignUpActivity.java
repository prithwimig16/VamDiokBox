package com.prithwiraj.vamdiokerp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.prithwiraj.vamdiokerp.R;

public class LoginSignUpActivity extends AppCompatActivity implements View.OnClickListener{
Button btRegister,btLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sign_up);
        init();
    }

    private void init(){

        this.btRegister=(Button)findViewById(R.id.bt_register);
        this.btRegister.setOnClickListener(this);

        this.btLogin=(Button)findViewById(R.id.bt_login);
        this.btLogin.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_login:
                Intent i=new Intent(LoginSignUpActivity.this,LoginActivity.class);
                startActivity(i);
                break;

            case R.id.bt_register:
                Intent signUp=new Intent(LoginSignUpActivity.this,SignUpActivity.class);
                startActivity(signUp);
                break;


        }
    }
}
