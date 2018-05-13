package com.prithwiraj.vamdiokerp.fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.prithwiraj.vamdiokerp.R;
import com.prithwiraj.vamdiokerp.activities.InviteAndManageActivity;
import com.prithwiraj.vamdiokerp.activities.LoginActivity;
import com.prithwiraj.vamdiokerp.model.ErpCurrentUser;
import com.prithwiraj.vamdiokerp.networks.VamHttpComm;
import com.prithwiraj.vamdiokerp.utils.Config;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment implements View.OnClickListener {


    TextView logOut,tvInvite,tvCompanyName,tvVersion;
    int serviceType=0;
    RelativeLayout myProfile,companyProfile,plan,rlContactUS,rlInvite,rlFeedBack,rlshareApp,rlTermsAndCondition,rlPrivacyPolicy;
    LayoutInflater inflator;
    TextView tvName;
    ImageView settingProfilePic,companyPic;
    TextView profileName,companyName,terms,privacy,shareApp;
   // PyoCompany pyoCompany;
    public SettingsFragment() {
        // Required empty public constructor
    }
    private static final String ARG_PARAM = "";

    public static SettingsFragment newInstance(String param) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }
    @Override
    public void onResume() {
        super.onResume();

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Settings");
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeControls();
    }
    private void initializeControls(){
        this.settingProfilePic=(ImageView)this.getActivity().findViewById(R.id.profilepic);
        this.companyPic=(ImageView)this.getActivity().findViewById(R.id.img_companypic);
        this.myProfile=(RelativeLayout)this.getActivity().findViewById(R.id.rl_myprofile);
        this.myProfile.setOnClickListener(this);
        this.companyProfile=(RelativeLayout)this.getActivity().findViewById(R.id.rl_company_profile);
        this.companyProfile.setOnClickListener(this);
        this.logOut= (TextView)this.getActivity(). findViewById(R.id.tv_logout);
        this.logOut.setOnClickListener(this);
        this.rlshareApp=(RelativeLayout) this.getActivity().findViewById(R.id.relativeLayout4);
        this.rlshareApp.setOnClickListener(this);
        this.rlPrivacyPolicy=(RelativeLayout) this.getActivity().findViewById(R.id.relativeLayout6);
        this.rlPrivacyPolicy.setOnClickListener(this);
        this.rlTermsAndCondition=(RelativeLayout) this.getActivity().findViewById(R.id.relativeLayout5);
        this.rlTermsAndCondition.setOnClickListener(this);
        this.tvInvite=(TextView)this.getActivity().findViewById(R.id.tv_invite);
        this.tvInvite.setOnClickListener(this);
        this.tvName=(TextView) this.getActivity().findViewById(R.id.tv_name_settings);
        this.tvCompanyName=(TextView)this.getActivity().findViewById(R.id.tv_companyname);

        this.rlContactUS=(RelativeLayout)this.getActivity().findViewById(R.id.relativeLayout11);
        this.rlContactUS.setOnClickListener(this);

        this.rlFeedBack=(RelativeLayout)this.getActivity().findViewById(R.id.relativeLayout9);
        this.rlFeedBack.setOnClickListener(this);
        this.rlInvite=(RelativeLayout)this.getActivity().findViewById(R.id.relativeLayout3);
        this.rlInvite.setOnClickListener(this);

        this.tvVersion=(TextView)getActivity().findViewById(R.id.tv_version);

    }
    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.rl_myprofile:
//                Intent i=new Intent(this.getActivity(),MyProfileActivity.class);
//                startActivity(i);
                break;

            case R.id.tv_logout:
                logOutAction();
                break;
            case R.id.rl_company_profile:
//                Intent companyIntent= new Intent(this.getActivity(),CompanyProfileActivity.class);
//                companyIntent.putExtra("pyoCompany",this.pyoCompany);
//                startActivity(companyIntent);
                break;
            case R.id.relativeLayout9:
               // new FinestWebView.Builder(this.getActivity()).show("http://businessdemo.clamhub.com/contact");
                break;
            case R.id.relativeLayout11:
                Intent intent=new Intent(Intent.ACTION_SEND);
                String[] recipients={"support@clamhub.com"};
                intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                intent.putExtra(Intent.EXTRA_SUBJECT,"abc");
                intent.putExtra(Intent.EXTRA_TEXT,"def");
                intent.putExtra(Intent.EXTRA_CC,"ghi");
                intent.setType("text/html");
                startActivity(Intent.createChooser(intent, "Send mail"));
                break;

            case R.id.relativeLayout4:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Hey check out my app at:"+ Config.getSharedInstance().PLAYSTORE_URL);
                sendIntent.setType("text/plain");
                //  startActivity(Intent.createChooser(sendIntent, "Share via"));
                startActivity(Intent.createChooser(sendIntent,"Share using"));
                break;

            case R.id.relativeLayout6:
//                new FinestWebView.Builder(this.getActivity()).show("https://www.purpleyo.com/privacypolicy");
//                break;
//
//            case R.id.relativeLayout5:
//                new FinestWebView.Builder(this.getActivity()).show("https://www.purpleyo.com/termsofuse");
                break;

            case R.id.tv_invite:
                Intent inviteIntent=new Intent(this.getActivity(),InviteAndManageActivity.class);
                startActivity(inviteIntent);
                break;
            case R.id.relativeLayout3:
                Intent rlinviteIntent=new Intent(this.getActivity(),InviteAndManageActivity.class);
                startActivity(rlinviteIntent);
                break;
        }
    }
    private  void logOutAction(){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this.getActivity());

        alertDialog.setTitle("Confirm"); // Sets title for your alertbox

        alertDialog.setMessage("Do you want to Logout ?"); // Message to be displayed on alertbox

        // alertDialog.setIcon(R.drawable.logout1); // Icon for your alertbox

        /* When positive (yes/ok) is clicked */
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                getActivity().finish();
                logout();

            }
        });

        /* When negative (No/cancel) button is clicked*/
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        //alertDialog.show();

        AlertDialog alert = alertDialog.create();
        alert.show();


        TextView nbutton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        nbutton.setTextColor(getResources().getColor(R.color.skycolor));
        TextView pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setTextColor(getResources().getColor(R.color.skycolor));


    }


    private void logout(){


       // VamHttpComm.getNewInstance(this.getActivity(),this).callLogoutService();
        ErpCurrentUser.getSharedInstance().logOut();
        Intent startctivity = new Intent(getActivity(), LoginActivity.class);
        startActivity(startctivity);
        getActivity().finish();

    }

}
