package com.prithwiraj.vamdiokerp.fragments;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.prithwiraj.vamdiokerp.adapters.CustomerListAdapter;
import com.prithwiraj.vamdiokerp.R;
import com.prithwiraj.vamdiokerp.activities.CreateCustomerActivity;
import com.prithwiraj.vamdiokerp.model.Customer;
import com.prithwiraj.vamdiokerp.utils.Utils;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerFragment extends ListFragment implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {

    FloatingActionButton btFab;
//    private FirebaseAuth mAuth;
//    DatabaseReference databaseReference;
     ArrayList<Customer>customerArrayList;
     TextView emptytextview;
  CustomerListAdapter adapter;
    String[] spinnerItems = {"All", "Active", "Inactive"};


    public CustomerFragment() {
        // Required empty public constructor
    }
    private static final String ARG_PARAM = "";
    public static CustomerFragment newInstance(String param) {
        CustomerFragment fragment = new CustomerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }
    private ActionBar actionBar;

    @Override
    public void onResume() {
        super.onResume();

        //((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Customers");
//        actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
//        actionBar.setDisplayShowHomeEnabled(true);
//        actionBar.setDisplayUseLogoEnabled(true);
//        actionBar.setLogo(R.drawable.ic_arrow_drop_down_white_36dp);
//        actionBar.setTitle("Customers");

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Customers");


    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.drop_down_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.all_contact:
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("All Contacts");
                break;
            case R.id.customers :
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Customers");
                break;
            case R.id.vendor :
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Vendors");
                break;
            case R.id.active_contacts:
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Active Contacts");
                break;

            case R.id.inactive_contacts:
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Inactive Contacts");
                break;
        }
        return true;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_customer, container, false);
        init(v);
        return v;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeControl();
        if(adapter.getCount() == 0)
            emptytextview.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private void initializeControl(){

        this.adapter = new CustomerListAdapter(getActivity(), this, R.layout.single_customer_list_view,customerArrayList);
        this.setListAdapter(this.adapter);
        this.getListView().setOnItemClickListener(this);
//        databaseReference.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
////                Customer cs=dataSnapshot.getValue(Customer.class);
////                adapter.customerListArray.add(cs);
//                adapter.notifyDataSetChanged();
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

    }

    private void fetchDataFromFirebase(){

    }

    private void init(View v){
        this.btFab=(FloatingActionButton)v.findViewById(R.id.bt_fab);
        this.btFab.setOnClickListener(this);
        this.customerArrayList=new ArrayList<Customer>();
//        mAuth = FirebaseAuth.getInstance();
//        String id = mAuth.getCurrentUser().getUid();
//        databaseReference = FirebaseDatabase.getInstance().getReference();
//        databaseReference = databaseReference.child("users").child(id).child("customers");
        this.emptytextview=(TextView)v.findViewById(R.id.EmptytextView_cs);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_fab:
                Intent i=new Intent(getActivity(), CreateCustomerActivity.class);
                startActivity(i);
                break;

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
