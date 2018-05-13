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
import com.prithwiraj.vamdiokerp.model.User;

import java.util.ArrayList;

/**
 * Created by Prithwi on 29/04/18.
 */

public class UserListAdapter extends ArrayAdapter {
    int resource;
    Context context;
    LayoutInflater layoutInflater;
    public ArrayList<User> userListArray;

    public UserListAdapter(@NonNull Context context, CustomerFragment customerFragment, @LayoutRes int resource, ArrayList<User> customerArrayList) {
        super(context, resource);

        this.resource = resource;
        this.context =context;
        this.userListArray = new ArrayList<User>();
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return this.userListArray.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            convertView = this.layoutInflater.inflate(resource, null);

//            holder.name = (TextView) convertView.findViewById(R.id.tv_user_name);
//            holder.email = (TextView) convertView.findViewById(R.id.tv_user_email);
//            holder.role = (TextView) convertView.findViewById(R.id.tv_user_role);
//            holder.activeOrNot=(TextView)convertView.findViewById(R.id.tv_active_or_not);




            convertView.setTag(holder);
        } else {
            holder = (UserListAdapter.ViewHolder) convertView.getTag();

            System.gc();

        }

        if(this.userListArray.size()>position) {
            User user = this.userListArray.get(position);

            holder.name.setText(user.getName());
            holder.email.setText(user.getEmail());
            holder.role.setText(user.getRole());


        }



        return convertView;
    }


    static class ViewHolder {
        public TextView name;
        public TextView email;
        public TextView role;
        public TextView activeOrNot;

    }
}
