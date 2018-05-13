package com.prithwiraj.vamdiokerp.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prithwiraj.vamdiokerp.R;
import com.prithwiraj.vamdiokerp.activities.AddItemActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubItemFragment extends Fragment implements View.OnClickListener {

    private  FloatingActionButton btFab;
   private TextView emptytextview;
    public SubItemFragment() {
        // Required empty public constructor
    }

    private static final String ARG_PARAM = "";
    private static String strScreenName="";

    public static SubItemFragment newInstance(String param) {
        strScreenName=param;
        SubItemFragment fragment = new SubItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_sub_item, container, false);
        init(v);
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.drop_down_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int i=0;
                return true;

            }
        });
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(strScreenName);
    }

    private void init(View v){

        this.btFab=(FloatingActionButton)v.findViewById(R.id.bt_fab_sub_item);
        this.btFab.setOnClickListener(this);
        this.emptytextview=(TextView)v.findViewById(R.id.EmptytextView_sub_item);
        this.emptytextview.setText("There are no "+strScreenName+" on your list.");

//        this.statusSpinner = (Spinner) v.findViewById(R.id.status_spinner);
//       // this.statusSpinner.setOnItemSelectedListener(this);
//        ArrayAdapter spinAdapterPropertyType = new ArrayAdapter(getActivity(), R.layout.spinner_item, this.statusType);
//        this.statusSpinner.setSelection(0);
//        this.statusSpinner.setAdapter(spinAdapterPropertyType);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_fab_sub_item:
                Intent i=new Intent(getActivity(), AddItemActivity.class);
                startActivity(i);
                break;

        }
    }
}
