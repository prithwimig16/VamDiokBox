package com.prithwiraj.vamdiokerp.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;


import com.prithwiraj.vamdiokerp.R;
import com.prithwiraj.vamdiokerp.activities.SelectFieldActivity;
import com.prithwiraj.vamdiokerp.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Prithwi on 26/07/17.
 */

public class SelectFieldAdapter extends ArrayAdapter {
    Context context;
    int resource;
    public JSONArray optionsJsonArray;
    public JSONArray displayArray;

    LayoutInflater vi;
    String dataKey;
    int selectedIndex;
    String selection;
    public boolean isJsonArray;

    SelectFieldActivity selectFieldActivity;

    public int getSelectedIndex() {
        return selectedIndex;
    }


    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }

    public SelectFieldAdapter(@NonNull SelectFieldActivity context, @LayoutRes int resource, JSONArray optionsJsonArrayObj, String dataKey, String selection) {
        super(context, resource);
        this.isJsonArray = false;
        this.selectedIndex = -1;
        this.dataKey = dataKey;
        this.selectFieldActivity = context;
        this.context = (Context) context;
        this.resource = resource;
        this.optionsJsonArray = new JSONArray();
        if (optionsJsonArrayObj != null)
            this.optionsJsonArray = optionsJsonArrayObj;
        this.selection = selection;
        this.vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(this.optionsJsonArray!=null&&this.optionsJsonArray.length()>0)
        {
            try {
                if(optionsJsonArray.get(0).getClass()==JSONObject.class)
                {
                    this.isJsonArray = true;
                }

            } catch (JSONException e) {
                Utils.consoleLog(SelectFieldAdapter.class,e.getLocalizedMessage());
            }
        }

        if (this.isJsonArray == true) {
            if (this.optionsJsonArray != null && this.dataKey != null && this.selection != null) {
                for (int i = 0; i < optionsJsonArray.length(); i++) {

                    try {
                        JSONObject obj = optionsJsonArray.getJSONObject(i);
                        String valueStr = obj.optString(this.dataKey);
                        if (valueStr != null && valueStr.equalsIgnoreCase(this.selection)) {
                            this.selectedIndex = i;
                            break;
                        }
                    } catch (JSONException e) {
                        Utils.consoleLog(SelectFieldAdapter.class, e.getLocalizedMessage());
                    }

                }
            }
        } else {
            if (this.optionsJsonArray != null && this.selection != null) {
                for (int i = 0; i < optionsJsonArray.length(); i++) {


                    String valueStr = optionsJsonArray.optString(i);
                    if (valueStr != null && valueStr.equalsIgnoreCase(this.selection)) {
                        this.selectedIndex = i;
                        break;
                    }


                }
            }
        }
        this.displayArray = this.optionsJsonArray;

    }

    @Override
    public int getCount() {

        return this.displayArray.length();

    }


    private void refresh() {
        // if(ViewHolder v =new)
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        if (convertView == null) {
            convertView = vi.inflate(resource, null);
            holder = new ViewHolder();

            holder.itemName = (TextView) convertView.findViewById(R.id.tv_select_list_item);
            holder.chk = (CheckBox) convertView.findViewById(R.id.sub_chk);
            convertView.setTag(holder);

            holder.chk.setOnClickListener(this.selectFieldActivity);


        } else {
            holder = (ViewHolder) convertView.getTag();
            System.gc();
        }


        String displayValue = "";

        if (this.displayArray.length() > position) {
            if (this.isJsonArray == true) {

                JSONObject optionJsonObject = this.displayArray.optJSONObject(position);
                if (optionJsonObject != null) {
                    displayValue = optionJsonObject.optString(this.dataKey);

                } else {
                    Utils.consoleLog(SelectFieldActivity.class, "Error in fetching json object from json array");
                }
            } else {

                displayValue = this.displayArray.optString(position);

            }
        }

        if (position == this.selectedIndex)
            holder.chk.setChecked(true);
        else
            holder.chk.setChecked(false);

        holder.itemName.setText(displayValue);

        holder.chk.setTag("" + position);


        return convertView;
    }


    static class ViewHolder {

        public TextView itemName;
        public CheckBox chk;


    }


}
