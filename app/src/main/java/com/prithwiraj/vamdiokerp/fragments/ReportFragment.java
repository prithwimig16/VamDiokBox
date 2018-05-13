package com.prithwiraj.vamdiokerp.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prithwiraj.vamdiokerp.R;
import com.prithwiraj.vamdiokerp.activities.CreateCustomerActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportFragment extends Fragment implements View.OnClickListener {

    FloatingActionButton btFab;



    public ReportFragment() {
        // Required empty public constructor
    }

    private static final String ARG_PARAM = "";

    public static ReportFragment newInstance(String param) {
        ReportFragment fragment = new ReportFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onResume() {
        super.onResume();

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Reports");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_customer, container, false);
        init(v);
        return v;
    }

    private void init(View v){
        this.btFab=(FloatingActionButton)v.findViewById(R.id.bt_fab);
        this.btFab.setOnClickListener(this);

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
}
