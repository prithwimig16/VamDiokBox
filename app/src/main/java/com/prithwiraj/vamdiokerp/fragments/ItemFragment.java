package com.prithwiraj.vamdiokerp.fragments;


import android.content.Intent;
import android.graphics.Color;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;


import com.prithwiraj.vamdiokerp.R;
import com.prithwiraj.vamdiokerp.activities.AddItemActivity;
import com.prithwiraj.vamdiokerp.activities.CreateCustomerActivity;
import com.prithwiraj.vamdiokerp.utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemFragment extends Fragment implements View.OnClickListener{

    private  FloatingActionButton btFab;

   TextView emptytextview;
    Spinner statusSpinner;
    String[] statusType = {"All", "Active", "Inactive"};

    public ItemFragment() {
        // Required empty public constructor
    }
    private static final String ARG_PARAM = "";
    public static ItemFragment newInstance(String param) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onResume() {
        super.onResume();

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Items");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_item, container, false);
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
        return super.onOptionsItemSelected(item);
    }

    private void init(View v){

        this.btFab=(FloatingActionButton)v.findViewById(R.id.bt_fab_item);
        this.btFab.setOnClickListener(this);
//        this.emptytextview=(TextView)v.findViewById(R.id.EmptytextView_item);
//        this.statusSpinner = (Spinner) v.findViewById(R.id.status_spinner);
//       // this.statusSpinner.setOnItemSelectedListener(this);
//        ArrayAdapter spinAdapterPropertyType = new ArrayAdapter(getActivity(), R.layout.spinner_item, this.statusType);
//        this.statusSpinner.setSelection(0);
//        this.statusSpinner.setAdapter(spinAdapterPropertyType);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_fab_item:
                Intent i=new Intent(getActivity(), AddItemActivity.class);
                startActivity(i);
                break;

        }
    }

}
