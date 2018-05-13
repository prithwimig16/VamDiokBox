package com.prithwiraj.vamdiokerp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.prithwiraj.vamdiokerp.R;


public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        final int SPLASH_TIME_OUT = 2000;
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer.
             *
             */

            @Override
            public void run() {
                Intent startUpactivity = new Intent(SplashScreenActivity.this, StartUpActivity.class);
                startActivity(startUpactivity);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out );
                finish();
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
    }

