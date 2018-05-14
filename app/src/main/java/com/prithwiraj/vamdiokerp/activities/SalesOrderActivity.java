package com.prithwiraj.vamdiokerp.activities;

import android.app.DatePickerDialog;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.prithwiraj.vamdiokerp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SalesOrderActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etDate,etShippingDate;
    Button btBack;
    public static final String DATE_SERVER_PATTERN = "dd MMM yyyy";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(this ,R.color.colorPrimaryDark));
        setContentView(R.layout.activity_sales_order);
        init();
    }
    private void init(){
        this.etDate=(EditText)findViewById(R.id.et_date);
        this.etDate.setOnClickListener(this);
        this.etShippingDate=(EditText)findViewById(R.id.et_shipment_date);
        this.etShippingDate.setOnClickListener(this);

        this.btBack=(Button)findViewById(R.id.bt_back_order);
        this.btBack.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.et_date:
                selectDate();
                break;

            case R.id.et_shipment_date:
                selectShippingDate();
                break;

            case R.id.bt_back_order:
               finish();
                break;
        }
    }

    private void selectDate(){
        final Calendar c = Calendar.getInstance();
      int  mYear = c.get(Calendar.YEAR);
      int   mMonth = c.get(Calendar.MONTH);
       int mDay = c.get(Calendar.DAY_OF_MONTH);



        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        Date date = calendar.getTime();

                        SimpleDateFormat formatter = new SimpleDateFormat(DATE_SERVER_PATTERN);
                        etDate.setText(formatter.format(date));

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void selectShippingDate(){
       final Calendar c = Calendar.getInstance();
        int  mYear = c.get(Calendar.YEAR);
        int  mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        Date date = calendar.getTime();

                        SimpleDateFormat formatter = new SimpleDateFormat(DATE_SERVER_PATTERN);
                        etShippingDate.setText(formatter.format(date));
                        //etShippingDate.setText(dayOfMonth + " " + (monthOfYear + 1) + " " + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}
