package com.prithwiraj.vamdiokerp.activities;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.prithwiraj.vamdiokerp.R;

public class SalesOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(this ,R.color.colorPrimaryDark));
        setContentView(R.layout.activity_sales_order);
    }
}
