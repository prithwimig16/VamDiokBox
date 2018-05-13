package com.prithwiraj.vamdiokerp.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;


import com.prithwiraj.vamdiokerp.R;
import com.prithwiraj.vamdiokerp.adapters.SelectFieldAdapter;
import com.prithwiraj.vamdiokerp.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class SelectFieldActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private Button save;
    //private TextView itemName;
    private ListView list;
    private SelectFieldAdapter adapter;
    private SearchView edtSearch;
    private Button buttonCancel;


    String optionsStringArray;

    private JSONObject networkMethod,optionObject;
    private String selection;
    private String dataKey;
    private int originalIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.access_list);

        Intent intent = getIntent();
       // this.itemName = (TextView) findViewById(R.id.select_list_item);
        this.list = (ListView) findViewById(R.id.select_itemList);
        this.edtSearch = (SearchView) findViewById(R.id.select_etsearch);


        String optionsJson = intent.getStringExtra("options");
        this.dataKey = intent.getStringExtra("data_key");



        String networkMethodStr = intent.getStringExtra("network_method");
        if (networkMethodStr != null && networkMethodStr.length() > 0) {
            try {
                this.networkMethod = new JSONObject(networkMethodStr);
                this.optionObject=new JSONObject(optionsJson);
            } catch (JSONException e) {
                Utils.consoleLog(SelectFieldActivity.class, e.getLocalizedMessage());
            }
        }

        this.selection = intent.getStringExtra("selection");

        this.originalIndex = intent.getIntExtra("original_index", -1);


        if (this.networkMethod != null && this.networkMethod.length() > 0) {
            Utils.getInstance().displayLoading(this);
            //PyoHttpComm.getNewInstance(this).callGetMultiChoiceData(this.networkMethod);

        }
        else {
            try {

                JSONArray optionsJsonArray = new JSONArray(optionsJson);



                this.adapter = new SelectFieldAdapter(this, R.layout.activity_select_field, optionsJsonArray, this.dataKey, this.selection);


                list.setAdapter(adapter);

                list.setOnItemClickListener(this);
                // this.edtSearch.setOnQueryTextListener(this);


            } catch (JSONException e) {
                Utils.consoleLog(SelectFieldActivity.class, e.getLocalizedMessage());
            }
        }

        this.edtSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                if (adapter.isJsonArray == true) {
                    if (newText.length() > 0) {
                        adapter.displayArray = new JSONArray();
                        adapter.setSelectedIndex(-1);
                        for (int i = 0; i < adapter.optionsJsonArray.length(); i++) {


                            JSONObject optionJsonObject = adapter.optionsJsonArray.optJSONObject(i);
                            if (optionJsonObject != null) {

                                String displayValue = optionJsonObject.optString(dataKey);

                                String tempDisplayValueLowerCase = optionJsonObject.optString(dataKey);
                                tempDisplayValueLowerCase = tempDisplayValueLowerCase.toLowerCase();

                                String tempSearchText = newText.toLowerCase();

                                if (tempDisplayValueLowerCase.contains(tempSearchText)) {
                                    adapter.displayArray.put(optionJsonObject);

                                    if(selection!=null&&displayValue.equalsIgnoreCase(selection))
                                    {
                                        adapter.setSelectedIndex(adapter.displayArray.length()-1);

                                    }



                                }

                            }
                        }
                    } else {
                        adapter.displayArray = adapter.optionsJsonArray;
                        adapter.setSelectedIndex(-1);
                        if ( selection != null) {
                            for (int i = 0; i < adapter.optionsJsonArray.length(); i++) {

                                try {
                                    JSONObject obj = adapter.optionsJsonArray.getJSONObject(i);
                                    String valueStr = obj.optString(dataKey);
                                    if (valueStr != null && valueStr.equalsIgnoreCase(selection)) {
                                        adapter.setSelectedIndex(adapter.displayArray.length()-1);
                                        break;
                                    }
                                } catch (JSONException e) {
                                    Utils.consoleLog(SelectFieldAdapter.class, e.getLocalizedMessage());
                                }

                            }
                        }
                    }
                } else {

                    if (newText.length() > 0) {
                        adapter.displayArray = new JSONArray();
                        adapter.setSelectedIndex(-1);
                        for (int i = 0; i < adapter.optionsJsonArray.length(); i++) {


                            String displayValue = adapter.optionsJsonArray.optString(i);

                            String tempDisplayValueLowerCase = adapter.optionsJsonArray.optString(i);
                            tempDisplayValueLowerCase = tempDisplayValueLowerCase.toLowerCase();

                            String tempSearchText = newText.toLowerCase();

                            if (tempDisplayValueLowerCase.contains(tempSearchText)) {
                                adapter.displayArray.put(displayValue);

                                if(selection!=null&&displayValue.equalsIgnoreCase(selection))
                                {
                                    adapter.setSelectedIndex(adapter.displayArray.length()-1);

                                }

                            }

                        }
                    } else {

                        adapter.displayArray = adapter.optionsJsonArray;
                        adapter.setSelectedIndex(-1);
                        if ( selection != null) {
                            for (int i = 0; i < adapter.optionsJsonArray.length(); i++) {


                                String valueStr = adapter.optionsJsonArray.optString(i);
                                if (valueStr != null && valueStr.equalsIgnoreCase(selection)) {
                                    adapter.setSelectedIndex(adapter.displayArray.length()-1);
                                    break;
                                }


                            }
                        }
                    }

                }
                adapter.notifyDataSetChanged();
                return true;
            }
        });


        this.buttonCancel = (Button)

                findViewById(R.id.b_cancel);
        this.buttonCancel.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }


    protected void onResume() {
        super.onResume();
        getWindow().setSoftInputMode(WindowManager.
                LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }

    private void finishSelection(boolean cancelled) {
        if (cancelled == false) {
            Intent intent = new Intent();


            if (this.adapter != null) {
                intent.putExtra("options", adapter.displayArray.toString());

                intent.putExtra("selected_index", this.adapter.getSelectedIndex());
                if (adapter.isJsonArray == true) {
                    intent.putExtra("data_key", this.dataKey);
                }
                intent.putExtra("is_json_array", adapter.isJsonArray);
            }


            setResult(Activity.RESULT_OK,intent);
            finish();

        }
        else
        {
            final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("ClamHub Business");
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setMessage("There are no more option available at the moment, please try again later");

            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    alertDialog.dismiss();
                    setResult(Activity.RESULT_CANCELED);
                    finish();
                }
            });
            alertDialog.show();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.adapter.setSelectedIndex(position);

        finishSelection(false);
    }

//    @Override
//    public void onSuccess(boolean status, int tag, JSONObject jsonResponse) {
//
//        Utils.getInstance().hideLoading();
//        if (status == true) {
//            if (jsonResponse != null) {
//                try {
//                    JSONArray optionsJsonArray = jsonResponse.getJSONArray("data");
//                    this.adapter = new SelectFieldAdapter(this, R.layout.activity_select_field, optionsJsonArray, this.dataKey, this.selection);
//                    list.setAdapter(adapter);
//
//                    list.setOnItemClickListener(this);
//
//                } catch (JSONException e) {
//                    Utils.consoleLog(SelectFieldActivity.class, e.getLocalizedMessage());
//
//                    this.finishSelection(true);
//                }
//            } else {
//                Utils.consoleLog(SelectFieldActivity.class, jsonResponse.toString());
//
//                this.finishSelection(true);
//            }
//        } else {
//            Utils.consoleLog(SelectFieldActivity.class, jsonResponse.toString());
//
//            this.finishSelection(true);
//        }
//    }
//
//    @Override
//    public void onFailure(JSONObject error, int tag) {
//
//        Utils.getInstance().hideLoading();
//        if(error!=null)
//            Utils.consoleLog(SelectFieldActivity.class, error.toString());
//
//        this.finishSelection(true);
//
//
//    }


    @Override
    public void onClick(View v) {
        this.adapter.setSelectedIndex(Integer.parseInt(v.getTag().toString()));

        finishSelection(false);
    }
}

