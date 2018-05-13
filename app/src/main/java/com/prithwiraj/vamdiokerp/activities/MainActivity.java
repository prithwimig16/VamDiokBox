package com.prithwiraj.vamdiokerp.activities;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.prithwiraj.vamdiokerp.R;
import com.prithwiraj.vamdiokerp.fragments.SubItemFragment;
import com.prithwiraj.vamdiokerp.model.ErpCurrentUser;
import com.prithwiraj.vamdiokerp.model.NavMenuModel;
import com.prithwiraj.vamdiokerp.navigationdrawer.NavMenuAdapter;
import com.prithwiraj.vamdiokerp.navigationdrawer.SubTitle;
import com.prithwiraj.vamdiokerp.navigationdrawer.TitleMenu;
import com.prithwiraj.vamdiokerp.utils.Constant;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavMenuAdapter.MenuItemClickListener {
    Toolbar toolbar;
    DrawerLayout drawer;
    ArrayList<NavMenuModel> menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToolbar();

        drawer = (DrawerLayout) findViewById(R.id.main_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        TextView tv=(TextView)findViewById(R.id.navigation_header_text);
        TextView tvEmail=(TextView)findViewById(R.id.textView);

        //set Navigation drawer Title and Email..START

        tv.setText(ErpCurrentUser.getSharedInstance().getFirstName());
        tvEmail.setText(ErpCurrentUser.getSharedInstance().getEmail());

        //set Navigation drawer Title and Email..END

        toggle.syncState();

        setNavigationDrawerMenu();

//        View headerView = .inflateHeaderView(R.layout.navigation_header);
//        headerView.findViewById(R.id.navigation_header_text);
    }

    private void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    private void setNavigationDrawerMenu() {
        NavMenuAdapter adapter = new NavMenuAdapter(this, getMenuList(), this);
        RecyclerView navMenuDrawer = (RecyclerView) findViewById(R.id.main_nav_menu_recyclerview);
        navMenuDrawer.setAdapter(adapter);
        navMenuDrawer.setLayoutManager(new LinearLayoutManager(this));
        navMenuDrawer.setAdapter(adapter);

//        INITIATE SELECT MENU
        adapter.selectedItemParent = menu.get(0).menuTitle;
        onMenuItemClick(adapter.selectedItemParent);
        adapter.notifyDataSetChanged();
    }


    private List<TitleMenu> getMenuList() {
        List<TitleMenu> list = new ArrayList<>();

        menu = Constant.getMenuNavigasi();
        for (int i = 0; i < menu.size(); i++) {
            ArrayList<SubTitle> subMenu = new ArrayList<>();
            if (menu.get(i).subMenu.size() > 0){
                for (int j = 0; j < menu.get(i).subMenu.size(); j++) {
                    subMenu.add(new SubTitle(menu.get(i).subMenu.get(j).subMenuTitle));
                }
            }

            list.add(new TitleMenu(menu.get(i).menuTitle, subMenu, menu.get(i).menuIconDrawable));
        }

        return list;
    }

    @Override
    public void onMenuItemClick(String itemString) {
        for (int i = 0; i < menu.size(); i++) {
            if (itemString.equals(menu.get(i).menuTitle)){
                getSupportFragmentManager().beginTransaction().replace(R.id.main_content, menu.get(i).fragment).commit();
                break;
            }else{
                for (int j = 0; j < menu.get(i).subMenu.size(); j++) {
                    if (itemString.equals(menu.get(i).subMenu.get(j).subMenuTitle)){
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, menu.get(i).subMenu.get(j).fragment).commit();


                            SubItemFragment.newInstance(menu.get(i).subMenu.get(j).subMenuTitle);

                        break;
                    }
                }
            }
        }

        if (drawer != null){
            drawer.closeDrawer(GravityCompat.START);
        }
    }
    @Override
    public void onResume() {
        super.onResume();

        ((AppCompatActivity) this).getSupportActionBar().setTitle("Your Company");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.main_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            drawer.openDrawer(GravityCompat.START);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
