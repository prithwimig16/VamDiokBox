package com.prithwiraj.vamdiokerp.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.prithwiraj.vamdiokerp.R;
import com.prithwiraj.vamdiokerp.activities.CreateCustomerActivity;
import com.prithwiraj.vamdiokerp.adapters.UserListAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class UsersFragment extends ListFragment implements View.OnClickListener, AdapterView.OnItemClickListener  {
    UserListAdapter adapter;
    FloatingActionButton btFab;



    public UsersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.users_fragment, container, false);
        init(v);
        return v;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeControl();
    }

    private void init(View v){
        this.btFab=(FloatingActionButton)v.findViewById(R.id.bt_fab_user);
        this.btFab.setOnClickListener(this);

    }

    private void initializeControl(){

    }
    @Override
    public void onResume() {
        super.onResume();

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Users");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_fab_user:
                Intent i=new Intent(getActivity(), CreateCustomerActivity.class);
                startActivity(i);
                break;

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
