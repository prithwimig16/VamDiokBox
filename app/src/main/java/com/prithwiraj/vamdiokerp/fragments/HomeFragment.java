package com.prithwiraj.vamdiokerp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prithwiraj.vamdiokerp.R;
import com.prithwiraj.vamdiokerp.utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    TextView tvWelcome;
    public HomeFragment() {
        // Required empty public constructor
    }
    private static final String ARG_PARAM = "";
    public static HomeFragment newInstance(String param) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onResume() {
        super.onResume();

        //do it afetr implemnting get user

//        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(ErpCurrentUser.getSharedInstance().getCompany());
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(Utils.getValueFromPref("v_full_name"));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_home, container, false);
        init(v);
        updateUi();
        return v;
    }

    private void init(View v){
        this.tvWelcome = v.findViewById(R.id.tvWelcome);
    }
    private void updateUi(){
        // this.tvWelcome.setText("Welcome "+ ErpCurrentUser.getSharedInstance().getFirstName()+" !");//+ ErpCurrentUser.getSharedInstance().name+" !"
        this.tvWelcome.setText("Welcome " + Utils.getValueFromPref("v_full_name"));
    }

}
