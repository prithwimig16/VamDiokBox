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
public class SalesFragment extends Fragment {


    public SalesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Sales");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sales, container, false);
    }

}
