package com.prithwiraj.vamdiokerp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.prithwiraj.vamdiokerp.R;
import com.prithwiraj.vamdiokerp.adapters.InviteAndManageAdapter;
import com.prithwiraj.vamdiokerp.model.User;
import com.prithwiraj.vamdiokerp.utils.Utils;

public class InviteAndManageActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener {
    InviteAndManageAdapter inviteAndManageAdapter;
    ListView inviteList;
    Button buttonBack, buttonInvite;
    SearchView searchView;
    User selectedUserObj;
    private int page = 0;
    private int totalCustomer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_and_manage);
        initializationcontrol();
        //refresh();
        this.inviteAndManageAdapter = new InviteAndManageAdapter(this, R.layout.single_invite_view);

        this.inviteList.setAdapter(this.inviteAndManageAdapter);
        this.inviteList.setOnItemClickListener(this);

        this.inviteList.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                if (firstVisibleItem + visibleItemCount > totalItemCount - 1 && totalItemCount < totalCustomer) {

                    if (page * Utils.SHOW_ITEMS_PER_PAGE < totalCustomer) {
                        page++;
                        //refresh();
                    }
                }


            }
        });

        final android.widget.Filter filter = inviteAndManageAdapter.getFilter();

        this.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.isEmpty()) {
                    inviteList.clearTextFilter();
                } else {
                    filter.filter(newText);
                }
                return true;
            }
        });
    }
    private void initializationcontrol() {
        this.buttonBack = (Button) findViewById(R.id.iv_b_back);
        this.buttonBack.setOnClickListener(this);

        this.buttonInvite = (Button) findViewById(R.id.iv_b_invite);
        this.buttonInvite.setOnClickListener(this);
        this.inviteList = (ListView) findViewById(R.id.invite_list);

        this.searchView=(SearchView)findViewById(R.id.iv_searchView);

        TextView emptyTextView = (TextView) findViewById(R.id.empty_team_list_text);
        this.inviteList.setEmptyView(emptyTextView);
        this.inviteList.setTextFilterEnabled(false);

    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        User userObj = this.inviteAndManageAdapter.teamMemberListArray.get(position);
        if (!userObj.getRole().matches("Pending")) {
            Intent intent = new Intent(this, InviteDetailsActivity.class);
            //intent.putExtra("PyoUser", userObj);
            this.startActivity(intent);
        }
    }


    @Override
    public void onClick(View v) {
        switch ((v.getId())) {
            case R.id.iv_b_back:
                finish();
                break;

//            case R.id.iv_b_delete:
//                Utils.consoleLog(InviteAndManageActivity.class, "tapped on button positon=" + v.getTag());
//                this.selectedUserObj = this.inviteAndManageAdapter.teamMemberListArray.get(Integer.parseInt(v.getTag().toString()));
//                Utils.consoleLog(InviteAndManageActivity.class, "User name:" + selectedUserObj.getName());
//                deletePopUp();
//                break;


            case R.id.iv_b_invite:
                Intent inviteIntent = new Intent(InviteAndManageActivity.this, InviteDetailsActivity.class);
                startActivity(inviteIntent);
                this.overridePendingTransition(R.anim.righttoleft, R.anim.stable);
                break;
        }
    }
}
