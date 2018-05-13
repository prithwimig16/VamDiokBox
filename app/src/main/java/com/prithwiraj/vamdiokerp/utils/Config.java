package com.prithwiraj.vamdiokerp.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by prithwi on 5/2/17.
 */

public class Config {

    public String STARTUP_API ="https://www.vamdiok.com/vamdiok_api/api/v1.0/users";//dev



           // "http://appmaster.clamhub.com/TablespaceAdminStartup/master";  //prod

         // "http://192.168.1.24/cre-webservice-local/index.php/tablespaceAdminStartup/master";
//          "http://localhost/OfficeManagerWebService/index.php/tablespaceAdminStartUp/master";
            //"http://192.168.1.31/OfficeManagerWebService/index.php/tablespaceAdminStartUp/master";



    public String TRANSACTION_API="";
    public int selectedTab;

   public String TRANSACTION_ACCESS_KEY="";

    public String USER_ROLE="";

    public String EMAIL="";

    public String CONTROLLER_METHOD="";

    public String ASSET_BASE_URL="";

    public String THUMBNAIL_FOLDER = "";

    public String MAIN_FOLDER = "";

    public String REACHABILITY_URL="";

    public  String OFFICE_ID="";

    public int MAX_COUNT = 20;

    public boolean debugMode = true;
    //public boolean debugMode = false;

    public boolean suggestUpdate = false;

    public boolean forceUpdate = false;

    public String UPDATE_MESSAGE="New Version of VamDiokBox is available, please update now!";

    public boolean maintenanceMode = false;

    public String MAINTENANCE_MESSAGE = "";

    public String B_AUTH="";

    public String PLAYSTORE_URL="";

    public String PYO_URL="http://www.vamdiok.com";

    public String APP_ID="";

    public Context applicationContext=null;

    public static final int TAB_DASHBOARD       = 0;
    public static final int TAB_LEADS           = 1;
    public static final int TAB_REPORT         = 2;
    public static final int TAB_PROPERTIES     = 3;
    public static final int TAB_SETTINGS        = 4;



    public static final int[] graphColor = {
            Color.rgb(17, 150, 227), Color.rgb(35, 52, 93), Color.rgb(111, 221, 216),
            Color.rgb(23, 164, 155),
    };

    public JSONObject propertyForOptions;
    public JSONObject propertyTypeOptions;
     public JSONArray probablityOptions;
     public JSONArray officeNameArray;
    public JSONArray branchArray;
    public JSONObject constructionStatusOptions;
    public JSONArray brokerStatusOptions;
    public JSONArray cityArray;
    public JSONArray locationArray;
    public JSONArray userArray;
    public  JSONArray roomsArray;


    public boolean allowLandscape;

    public boolean allowNotification;
    public boolean allowNotificationSound;
    public boolean allowNotificationBadge;
    public boolean allowNotificationAlert;

    public HashMap<String,JSONArray> globalOptions;

    public static Typeface OpenSans_Bold;
    public static Typeface OpenSans_Regular;
    public static Typeface OpenSans_Light;

    public boolean isInternetAvailable;

    private static Config _instance = null;

    public static Config getSharedInstance()
    {
        if(_instance==null)
        {
            _instance = new Config();
            _instance.isInternetAvailable = true;


        }
        return _instance;
    }

    public void setMasterData(JSONObject object)
    {
        if(object!=null)
        {
            this.TRANSACTION_API = object.optString("api");
            this.TRANSACTION_ACCESS_KEY = object.optString("api_key");
            this.CONTROLLER_METHOD = object.optString("main_method");
            this.ASSET_BASE_URL = object.optString("asset_base_url");
            this.MAX_COUNT = object.optInt("page_max_count",10);
            this.REACHABILITY_URL = object.optString("reachability_url");
            this.B_AUTH = object.optString("b_auth");
            this.UPDATE_MESSAGE = object.optString("update_message");
            this.MAINTENANCE_MESSAGE = object.optString("maintenance_message");
            this.THUMBNAIL_FOLDER = object.optString("thumbnail_folder");
            this.MAIN_FOLDER = object.optString("main_folder");
            if(object.optString("suggest_update").equalsIgnoreCase("1"))
                this.suggestUpdate = true;
            if(object.optString("force_update").equalsIgnoreCase("1"))
                this.forceUpdate = true;
            if(object.optString("maintenance_mode").equalsIgnoreCase("1"))
                this.maintenanceMode = true;
            this.PLAYSTORE_URL = object.optString("appstore_url");
            this.APP_ID = object.optString("appstore_id");
        }
    }


}
