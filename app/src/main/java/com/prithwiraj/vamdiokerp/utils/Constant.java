package com.prithwiraj.vamdiokerp.utils;


import com.prithwiraj.vamdiokerp.R;
import com.prithwiraj.vamdiokerp.fragments.BankingFragment;
import com.prithwiraj.vamdiokerp.fragments.CustomerFragment;
import com.prithwiraj.vamdiokerp.fragments.DashBoardFragment;
import com.prithwiraj.vamdiokerp.fragments.HomeFragment;
import com.prithwiraj.vamdiokerp.fragments.ItemFragment;
import com.prithwiraj.vamdiokerp.fragments.ReportFragment;
import com.prithwiraj.vamdiokerp.fragments.SettingsFragment;
import com.prithwiraj.vamdiokerp.fragments.SubItemFragment;
import com.prithwiraj.vamdiokerp.model.NavMenuModel;

import java.util.ArrayList;

/**
 * Created by miki on 7/7/17.
 */

public class Constant {

    public static ArrayList<NavMenuModel> getMenuNavigasi(){
        ArrayList<NavMenuModel> menu = new ArrayList<>();

        menu.add(new NavMenuModel("Home", R.drawable.ic_home_black_24dp, HomeFragment.newInstance("Home")));
        menu.add(new NavMenuModel("Dashboard", R.drawable.ic_dashboard_black_24dp, DashBoardFragment.newInstance("Dashboard")));
        menu.add(new NavMenuModel("Customers", R.drawable.ic_assignment_ind_black_24dp, CustomerFragment.newInstance("Customers")));
        menu.add(new NavMenuModel("Item", R.drawable.ic_shopping_basket_black_24dp, ItemFragment.newInstance("Item")));
        menu.add(new NavMenuModel("Banking", R.drawable.ic_credit_card_black_24dp, BankingFragment.newInstance("Banking")));

        menu.add(new NavMenuModel("Sales", R.drawable.ic_shopping_cart_black_24dp, new ArrayList<NavMenuModel.SubMenuModel>() {{
                    add(new NavMenuModel.SubMenuModel("Estimate", SubItemFragment.newInstance("Estimate")));
                    add(new NavMenuModel.SubMenuModel("Sales Orders", SubItemFragment.newInstance("salesOrder")));
            add(new NavMenuModel.SubMenuModel("Invoice", SubItemFragment.newInstance("Invoice")));
            add(new NavMenuModel.SubMenuModel("Payment Received", SubItemFragment.newInstance("Payment Received")));

        }}));

        menu.add(new NavMenuModel("Purchase", R.drawable.ic_local_shipping_black_24dp, new ArrayList<NavMenuModel.SubMenuModel>() {{
            add(new NavMenuModel.SubMenuModel("Expenses", SubItemFragment.newInstance("Expenses")));
            add(new NavMenuModel.SubMenuModel("Purchase Order", SubItemFragment.newInstance("Purchase Order")));
            add(new NavMenuModel.SubMenuModel("Bills", SubItemFragment.newInstance("Bills")));

        }}));

        menu.add(new NavMenuModel("Accountant", R.drawable.ic_person_black_24dp, new ArrayList<NavMenuModel.SubMenuModel>() {{
            add(new NavMenuModel.SubMenuModel("Manual Journals", SubItemFragment.newInstance("Manual Journals")));
        }}));

//        menu.add(new NavMenuModel("Time Tracking", R.drawable.ic_timer_black_24dp, new ArrayList<NavMenuModel.SubMenuModel>() {{
//            add(new NavMenuModel.SubMenuModel("Projects", SubItemFragment.newInstance("Projects")));
//            add(new NavMenuModel.SubMenuModel("Time Entries", SubItemFragment.newInstance("Time Entries")));
//            add(new NavMenuModel.SubMenuModel("Timer", SubItemFragment.newInstance("Timer")));
//
//        }}));

        menu.add(new NavMenuModel("Goods Tracking", R.drawable.ic_timer_black_24dp, SubItemFragment.newInstance("Goods Tracking")));

        menu.add(new NavMenuModel("Documents", R.drawable.ic_folder_black_24dp, new ArrayList<NavMenuModel.SubMenuModel>() {{
            add(new NavMenuModel.SubMenuModel("Inbox", SubItemFragment.newInstance("Inbox")));
            add(new NavMenuModel.SubMenuModel("All Files", SubItemFragment.newInstance("All Files ")));
            add(new NavMenuModel.SubMenuModel("Folders", SubItemFragment.newInstance("Folders")));

        }}));
        menu.add(new NavMenuModel("Reports", R.drawable.ic_insert_chart_black_24dp, ReportFragment.newInstance("Reports")));
        menu.add(new NavMenuModel("Settings", R.drawable.ic_settings_black_24dp, SettingsFragment.newInstance("Settings")));
        return menu;

    }
}
