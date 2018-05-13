package com.prithwiraj.vamdiokerp.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.prithwiraj.vamdiokerp.R;

public class AddItemActivity extends AppCompatActivity implements View.OnClickListener {
EditText etItemName,etUnit,etRate,etAccount,etDescription,etPurchaseRate;
TextView tvBack,tvSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        init();
    }
    private void init(){

        this.etItemName=(EditText)findViewById(R.id.et_item_name);
        this.etUnit=(EditText)findViewById(R.id.et_item_unit);
        this.etRate=(EditText)findViewById(R.id.et_item_rate);
        this.etAccount=(EditText)findViewById(R.id.et_item_account);
        this.etDescription=(EditText)findViewById(R.id.et_item_description);
        this.etPurchaseRate=(EditText)findViewById(R.id.et_purchase_rate);
        this.tvBack=(TextView)findViewById(R.id.tv_back_item);
        this.tvBack.setOnClickListener(this);
        this.tvSave=(TextView)findViewById(R.id.tv_save_item);
        this.tvSave.setOnClickListener(this);

        }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.tv_back_item:
                finish();
                break;

            case R.id.tv_save_item:

                saveData();
                break;

        }
    }

    private void saveData(){
        final AlertDialog alertDialog = new AlertDialog.Builder(AddItemActivity.this).create();
        alertDialog.setTitle("VamDiok ERP");
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setMessage("We have successfully Added Item for you. Thank you.");

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "great", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();

            }
        });
        alertDialog.show();
    }

}
