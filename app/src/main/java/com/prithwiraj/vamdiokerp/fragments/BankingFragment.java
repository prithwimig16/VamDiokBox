package com.prithwiraj.vamdiokerp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prithwiraj.vamdiokerp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BankingFragment extends Fragment {


    public BankingFragment() {
        // Required empty public constructor
    }
    private static final String ARG_PARAM = "";

    public static BankingFragment newInstance(String param) {
        BankingFragment fragment = new BankingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onResume() {
        super.onResume();

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Banking");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_banking, container, false);
    }

}
