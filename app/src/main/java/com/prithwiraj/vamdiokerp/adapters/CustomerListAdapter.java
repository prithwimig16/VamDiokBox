package com.prithwiraj.vamdiokerp.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.prithwiraj.vamdiokerp.R;
import com.prithwiraj.vamdiokerp.fragments.CustomerFragment;
import com.prithwiraj.vamdiokerp.model.Customer;

import java.util.ArrayList;

/**
 * Created by Prithwi on 29/04/18.
 */

public class CustomerListAdapter extends ArrayAdapter {

    int resource;
    Context context;
    LayoutInflater layoutInflater;
    CustomerFragment customerFragment;
    public ArrayList<Customer> customerListArray;

    public CustomerListAdapter(@NonNull Context context, CustomerFragment customerFragment, @LayoutRes int resource, ArrayList<Customer> customerArrayList) {
        super(context, resource);

        this.resource = resource;
        this.customerFragment=customerFragment;
        this.context =context;
        this.customerListArray = new ArrayList<Customer>();
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return this.customerListArray.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            convertView = this.layoutInflater.inflate(resource, null);

            holder.name = (TextView) convertView.findViewById(R.id.tv_customer_name);
            holder.receivables = (TextView) convertView.findViewById(R.id.tv_receivables);
            holder.payables = (TextView) convertView.findViewById(R.id.tv_payables);




            convertView.setTag(holder);
        } else {
            holder = (CustomerListAdapter.ViewHolder) convertView.getTag();

            System.gc();

        }

        if(this.customerListArray.size()>position) {
            Customer customer = this.customerListArray.get(position);

            holder.name.setText(customer.getFirstName()+" "+customer.getLastName());
            holder.receivables.setText(""+customer.getReceivables());
            holder.payables.setText(""+customer.getReceivables());


        }



        return convertView;
    }


    static class ViewHolder {
        public TextView name;
        public TextView receivables;
        public TextView payables;

    }
}
