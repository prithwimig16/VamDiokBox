package com.prithwiraj.vamdiokerp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.prithwiraj.vamdiokerp.R;
import com.prithwiraj.vamdiokerp.activities.InviteAndManageActivity;
import com.prithwiraj.vamdiokerp.model.ErpCurrentUser;
import com.prithwiraj.vamdiokerp.model.User;

import java.util.ArrayList;

/**
 * Created by Prithwi on 20/07/17.
 */

public class InviteAndManageAdapter extends ArrayAdapter implements Filterable {

    public ArrayList<User> teamMemberListArray;
    public ArrayList<User> orig;
    int resource;
    Context context;
    LayoutInflater vi;
    InviteAndManageActivity inviteAndManageActivity;


    public InviteAndManageAdapter(@NonNull InviteAndManageActivity context, @LayoutRes int resource) {
        super(context, resource);
        this.teamMemberListArray = new ArrayList<User>();
        this.resource = resource;
        this.inviteAndManageActivity = context;
        this.context = (Context) context;
        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return this.teamMemberListArray.size();

    }

    public Filter getFilter() {
        return new Filter()
        {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<User> results = new ArrayList<User>();
                if (orig == null)
                    orig = teamMemberListArray;
                if (constraint != null) {
                    if (orig != null && orig.size() > 0) {
                        for (final User g : orig) {
                            if (g.getName().toLowerCase()
                                    .contains(constraint.toString()))
                                results.add(g);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                teamMemberListArray = (ArrayList<User>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = vi.inflate(resource, null);
            holder = new ViewHolder();

            holder.name = (TextView) convertView.findViewById(R.id.iv_tv_name);
            holder.deleteButton = (Button) convertView.findViewById(R.id.iv_b_delete);
            holder.status = (Button) convertView.findViewById(R.id.iv_b_status);
            holder.imgInvite = (ImageView) convertView.findViewById(R.id.iv_img_invite);
            holder.role = (TextView) convertView.findViewById(R.id.iv_tv_role);
            holder.relativeLayout = (RelativeLayout) convertView.findViewById(R.id.iv_main_rel);


            convertView.setTag(holder);


        } else {
            holder = (ViewHolder) convertView.getTag();


            System.gc();

        }

        holder.deleteButton.setTag("" + position);

        if (this.teamMemberListArray.size() > position) {
            User pyoUser = this.teamMemberListArray.get(position);
            holder.name.setText(pyoUser.getName());
            holder.role.setText(pyoUser.getRole());
            if (pyoUser.getRole().matches("Account Administrator") && ErpCurrentUser.getSharedInstance().getFirstName().equals(pyoUser.getName())) {

                holder.deleteButton.setVisibility(View.INVISIBLE);
            } else

            {
                holder.deleteButton.setVisibility(View.VISIBLE);
                holder.deleteButton.setOnClickListener(inviteAndManageActivity);
            }


            String str = pyoUser.getRole();
            if (str.matches("Active")) {
                // holder.layout.setBackgroundColor(ContextCompat.getColor(context, R.color.greencolor));
                holder.status.setText("A");
                holder.status.setBackgroundColor(ContextCompat.getColor(context, R.color.greencolor));
                holder.relativeLayout.setBackgroundResource(R.color.transparent_white_hex_11);
            } else if (str.matches("Pending")) {
                //holder.layout.setBackgroundColor(ContextCompat.getColor(context, R.color.yellow));
                holder.status.setText("P");
                holder.status.setBackgroundColor(ContextCompat.getColor(context, R.color.redcolor));
                holder.relativeLayout.setBackgroundColor(Color.rgb(154, 154, 154));
            }

                holder.imgInvite.setImageResource(R.drawable.partner_logo);



        }


        return convertView;
    }


    static class ViewHolder {

        public TextView name;
        public TextView role;
        public Button status;
        public Button deleteButton;
        ImageView imgInvite;
        RelativeLayout relativeLayout;


    }
}
