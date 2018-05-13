package com.prithwiraj.vamdiokerp.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.prithwiraj.vamdiokerp.R;
import com.prithwiraj.vamdiokerp.model.Customer;
import com.prithwiraj.vamdiokerp.model.User;
import com.prithwiraj.vamdiokerp.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateCustomerActivity extends AppCompatActivity implements View.OnClickListener  {
   // private FirebaseAuth firebaseAuth;
    JSONObject jobj;
    TextView tvSave,tvBack;
    Customer customer;
    EditText etFirstName,etLastName,etCompany,etEmail,etCurrency,etBillingAddress,etShippingAddress,etRemarks,etPhone,etDisplayName,etPaymentTerms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_customer);
       // firebaseAuth = FirebaseAuth.getInstance();
        init();
    }

    private void init(){
        this.tvBack=(TextView)findViewById(R.id.tv_back);
        this.tvBack.setOnClickListener(this);

        this.tvSave=(TextView)findViewById(R.id.tv_save);
        this.tvSave.setOnClickListener(this);

        this. etFirstName=(EditText)findViewById(R.id.et_first_name);
        this. etLastName=(EditText)findViewById(R.id.et_last_name);
        this. etCompany=(EditText)findViewById(R.id.et_comapany_name);
        this. etDisplayName=(EditText)findViewById(R.id.et_display_name);
        this. etEmail=(EditText)findViewById(R.id.et_email);
        this. etPhone=(EditText)findViewById(R.id.et_mobile);
        this. etCurrency=(EditText)findViewById(R.id.et_currency);
        this. etPaymentTerms=(EditText)findViewById(R.id.et_payment_terms);
        this. etBillingAddress=(EditText)findViewById(R.id.et_billing_address);
        this. etShippingAddress=(EditText)findViewById(R.id.et_shipping_address);
        this. etRemarks=(EditText)findViewById(R.id.et_remark);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_save:

                saveData();
                break;

            case R.id.tv_back:
                finish();
                break;
        }
    }

    private void saveData(){
        this. jobj=new JSONObject();

        if(TextUtils.isEmpty(etFirstName.getText().toString())){
            Toast.makeText(this,"Please enter first name",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(etLastName.getText().toString())){
            Toast.makeText(this,"Please enter last name",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(etCompany.getText().toString())){
            Toast.makeText(this,"Please enter company",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(etDisplayName.getText().toString())){
            Toast.makeText(this,"Please enter Diasplay name",Toast.LENGTH_LONG).show();
            return;
        }if(TextUtils.isEmpty(etEmail.getText().toString())){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }if(TextUtils.isEmpty(etPhone.getText().toString())){
            Toast.makeText(this,"Please enter Phone numbmer",Toast.LENGTH_LONG).show();
            return;
        }if(TextUtils.isEmpty(etCurrency.getText().toString())){
            Toast.makeText(this,"Please enter currency",Toast.LENGTH_LONG).show();
            return;
        }if(TextUtils.isEmpty(etPaymentTerms.getText().toString())){
            Toast.makeText(this,"Please enter payment terms",Toast.LENGTH_LONG).show();
            return;
        }



        try {
            jobj.put("firstName",etFirstName.getText().toString());
            jobj.put("lastName",etLastName.getText().toString());
            jobj.put("company",etCompany.getText().toString());
            jobj.put("displayName",etDisplayName.getText().toString());
            jobj.put("email",etEmail.getText().toString());
            jobj.put("phoneNumber",etPhone.getText().toString());
            jobj.put("currency",etCurrency.getText().toString());
            jobj.put("paymentTerms",etPaymentTerms.getText().toString());
            jobj.put("billingAddress",etBillingAddress.getText().toString());
            jobj.put("shippingAdrss",etShippingAddress.getText().toString());
            jobj.put("receivables",0.00);
            jobj.put("payables",0.00);
            if(etRemarks.getText().toString()!=null||etRemarks.getText().toString().length()>2){
                jobj.put("remarks",etRemarks.getText().toString());
            }
            else{
                jobj.put("remarks","None");
            }

            this.customer=new Customer(jobj);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        crateFirebaseDb();
    }

    private  void crateFirebaseDb(){
//        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
//        String id=currentUser.getUid();
//        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users").child(id).child("customers");
        this.customer=new Customer(jobj);
        //mDatabase.child(etFirstName.getText().toString()+" "+etLastName.getText().toString()+" ("+
       // etCompany.getText().toString()+")").setValue(this.customer);
//
//        mDatabase.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//
//                final AlertDialog alertDialog = new AlertDialog.Builder(CreateCustomerActivity.this).create();
//                alertDialog.setTitle("VamDiok ERP");
//                alertDialog.setCancelable(false);
//                alertDialog.setCanceledOnTouchOutside(false);
//                alertDialog.setMessage("We have successfully Added customer for you. Thank you.");
//
//                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "great", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        finish();
//
//                    }
//                });
//                alertDialog.show();
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

        final AlertDialog alertDialog = new AlertDialog.Builder(CreateCustomerActivity.this).create();
                alertDialog.setTitle("VamDiok ERP");
                alertDialog.setCancelable(false);
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.setMessage("We have successfully Added customer for you. Thank you.");

                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "great", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();

                    }
                });
                alertDialog.show();
    }
}
