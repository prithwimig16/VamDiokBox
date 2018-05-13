package com.prithwiraj.vamdiokerp.utils;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.prithwiraj.vamdiokerp.BuildConfig;
import com.prithwiraj.vamdiokerp.model.ErpCurrentUser;
import com.prithwiraj.vamdiokerp.networks.VamHttpComm;

import org.OpenUDID.OpenUDID_manager;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.Random;
import java.util.StringTokenizer;


public class Utils {

    public static String ODID_STRING_PATH = "zgkpad";
    public static final int REQUEST_READ_PHONE_STATE = 1;
    public static final int SHOW_ITEMS_PER_PAGE=20;

    public static RequestQueue volleyRequestQueue;
    private static Utils _singletonObj = null;

    ProgressDialog progressDialog;

    public static Utils getInstance() {
        if (_singletonObj == null) {
            _singletonObj = new Utils();
        }
        return _singletonObj;
    }
    public static void consoleLog(Class classobj, String msg) {
        if (Config.getSharedInstance().debugMode == true) {
            Log.d(classobj.getCanonicalName(), msg);
        }
    }

    public static void displayServerErrorMessage(JSONObject jsonObject, Context context) {
        if (jsonObject != null) {
            try {
               // JSONObject errorObj = jsonObject.getJSONObject("error");
                String errorCode = jsonObject.getString("code");
                String errorMessage = jsonObject.getString("msg");
                String displayMessage = errorMessage;
                if (Config.getSharedInstance().debugMode == true) {
                    displayMessage += "\n";
                    displayMessage += errorCode;
                }


                Utils.alert(displayMessage, context);

            } catch (JSONException e) {
                Utils.consoleLog(context.getClass(), e.getLocalizedMessage());
                Utils.alert(VamHttpComm.GENERIC_ERROR_MESSAGE, context);
            }
        } else {
            Utils.alert(VamHttpComm.GENERIC_ERROR_MESSAGE, context);
        }
    }


    public static void alert(String message, Context context) {

        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("VamDiokBox");

        alertDialog.setMessage(message);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        if(!((Activity) context).isFinishing())
        {
            //show dialog
            alertDialog.show();
        }



    }

    public static boolean saveValueInPref(String key, String value) {
        if (key != null && value != null) {

            SharedPreferences pref = Config.getSharedInstance().applicationContext.getSharedPreferences("VamDiokBox", Application.MODE_PRIVATE);
            pref.edit().putString(key, value).apply();
            return true;
        } else {
            return false;
        }

    }



//
//    public void displayLoading(Context context) {
//
//        this.displayLoading(context,"Loading. Please wait...",ProgressDialog.STYLE_SPINNER);
//    }

    public static String getValueFromPref(String key) {
        if (key != null) {
            SharedPreferences pref = Config.getSharedInstance().applicationContext.getSharedPreferences("VamDiokBox", Application.MODE_PRIVATE);
            return pref.getString(key, null);
        }

        return "";
    }



    public static boolean saveFile(String fileName, String content) {

        FileOutputStream outputStream = null;
        try {
            outputStream = Config.getSharedInstance().applicationContext.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(content.getBytes());
            outputStream.close();
        } catch (Exception e) {
//            e.printStackTrace();
            Utils.consoleLog(Utils.class, e.getLocalizedMessage());
            return false;
        }
        return true;
    }

    public static String readFromFile(String fileName) {
        BufferedReader input = null;
        File file = null;
        try {
            file = new File(Config.getSharedInstance().applicationContext.getFilesDir(), fileName);

            input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            StringBuffer buffer = new StringBuffer();
            while ((line = input.readLine()) != null) {
                buffer.append(line);
            }
            return buffer.toString();
        } catch (IOException e) {
//            e.printStackTrace();
            Utils.consoleLog(Utils.class, e.getLocalizedMessage());
            return "";
        }

    }


    public static String[] getStringArray(String commaSeparatedString) {
        StringTokenizer tokenizer = new StringTokenizer(commaSeparatedString,
                ",");
        String[] array = new String[tokenizer.countTokens()];
        for (int i = 0; i < array.length && tokenizer.hasMoreTokens(); i++) {
            array[i] = tokenizer.nextToken();
        }
        return array;
    }

    public static String md5(String s) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");

        md.update(s.getBytes());

        byte digest[] = md.digest();
        StringBuffer result = new StringBuffer();

        for (int i = 0; i < digest.length; i++) {
            result.append(Integer.toHexString(0xFF & digest[i]));
        }
        return (result.toString());
    }

    public static String getExceptionStackTraceAsString(Exception exception) {
        StringWriter sw = new StringWriter();
        exception.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    public static String formatSize(long size) {
        String suffix = null;

        if (size >= 1024) {
            suffix = "KB";
            size /= 1024;
            if (size >= 1024) {
                suffix = "MB";
                size /= 1024;
            }
        }

        StringBuilder resultBuffer = new StringBuilder(Long.toString(size));

        int commaOffset = resultBuffer.length() - 3;
        while (commaOffset > 0) {
            resultBuffer.insert(commaOffset, ',');
            commaOffset -= 3;
        }

        if (suffix != null)
            resultBuffer.append(suffix);
        return resultBuffer.toString();
    }

    public static boolean externalMemoryAvailable() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }


    private static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte b : data) {
            int halfbyte = (b >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte)
                        : (char) ('a' + (halfbyte - 10)));
                halfbyte = b & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static String SHA1(String text) throws NoSuchAlgorithmException,
            UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        byte[] sha1hash = md.digest();
        return convertToHex(sha1hash);
    }



    public static void writeToSDFile(String data, String fileName) {
        try {
            if (Utils.externalMemoryAvailable()) {
                File kpDir = new File(
                        Environment.getExternalStorageDirectory(), "Android");
                if (!kpDir.mkdirs()) {
                    Utils.consoleLog(Utils.class,"Directory not created");
                    if (kpDir.exists() == false)
                        return;
                }
                File file = new File(kpDir, fileName);
                if (file.exists())
                    file.delete();
                try {
                    FileOutputStream out = new FileOutputStream(file);
                    out.write(data.getBytes());
                    out.flush();
                    out.close();

                } catch (Exception e) {

                    Utils.consoleLog(Utils.class,"File write failed: " + e.toString());
                }
            }
        } catch (Exception e) {
            Utils.consoleLog(Utils.class,"File write failed: " + e.toString());
        }
    }

    public static String readFromSDFile(String fileName) {

        String ret = "";

        try {
            if (Utils.externalMemoryAvailable()) {
                File kpDir = new File(Environment.getExternalStorageDirectory(), "Android");
                if (!kpDir.mkdirs()) {
                    Utils.consoleLog(Utils.class,"Directory not created");
                    if (kpDir.exists() == false)
                        return "";
                }
                File file = new File(kpDir, fileName);
                try {
                    FileInputStream in = new FileInputStream(file);
                    byte[] buffer = new byte[in.available()];
                    in.read(buffer);
                    ret = new String(buffer);
                    in.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            Utils.consoleLog(Utils.class,"File write failed: " + e.toString());
        }

        return ret;
    }

//    public static boolean validateODID(String odid) {
//        boolean isValid = false;
//
//        if (odid == null || odid.trim().length() == 0)
//            return false;
//
//        try {
//
//            String[] idsFromFile = odid.split("-");
//            if (idsFromFile.length < 2)
//                return false;
//
//            String fileODID = idsFromFile[1];
//            String fileHashODID = idsFromFile[0];
//
//            OpenUDID_manager manager = new OpenUDID_manager(Config.getSharedInstance().applicationContext);
//
//            // Try to get the openudid from local preferences
//            String localOpenUDID = manager.mPreferences.getString(
//                    OpenUDID_manager.PREF_KEY, null);
//            if (localOpenUDID == null) {
//                final SharedPreferences.Editor e = manager.mPreferences.edit();
//                e.putString(OpenUDID_manager.PREF_KEY, fileODID);
//                e.commit();
//                return true;
//            }
//
//            String hashOfLocalODID = Utils.SHA1(localOpenUDID);
//
//            if (fileODID.equalsIgnoreCase(localOpenUDID)
//                    && hashOfLocalODID.equalsIgnoreCase(fileHashODID))
//                isValid = true;
//
//        } catch (Exception e) {
//            Utils.consoleLog(Utils.class,"Exception in validating ODID:" + e.getMessage());
//        }
//
//        return isValid;
//    }


//    public static String getODID() {
//
//        String openUDID = "";
//        try {
//            String idsFromFile = Utils.readFromFile(Utils.ODID_STRING_PATH);
//            if (Utils.validateODID(idsFromFile)) {
//                String[] idsArray = idsFromFile.split("-");
//                openUDID = idsArray[1];
//
//            }
//
//            Utils.consoleLog(Utils.class,"ODID from file:" + openUDID + "--length:" + openUDID.length());
//
//            if (openUDID == null || openUDID.trim().length() != 40) {
//
//                Utils.consoleLog(Utils.class, "ODID from file is null or length is 0" + openUDID);
//
//                OpenUDID_manager.sync(Config.getSharedInstance().applicationContext);
//
//                openUDID = OpenUDID_manager.getOpenUDID();
//
//                String anotherHash = Utils.SHA1(openUDID);
//                String idToSave = anotherHash + "-" + openUDID;
//                Utils.saveFile( Utils.ODID_STRING_PATH,idToSave);
//            }
//
//        } catch (Exception e) {
//            Utils.consoleLog(Utils.class, e.getMessage());
//
//            return "";
//        }
//        return openUDID;
//    }

    public static boolean isFTU() {
        boolean result = true;
        try {
            String val = Utils.readFromFile("ftu");

            if (val == null || val.trim().length() == 0) {
                result = false;
            }

        } catch (Exception e) {
            Utils.consoleLog(Utils.class, e.getMessage());

            return false;
        }
        return result;
    }

    public static boolean validateODID(String odid) {
        boolean isValid = false;

        if (odid == null || odid.trim().length() == 0)
            return false;

        try {

            String[] idsFromFile = odid.split("-");
            if (idsFromFile.length < 2)
                return false;

            String fileODID = idsFromFile[1];
            String fileHashODID = idsFromFile[0];

            OpenUDID_manager manager = new OpenUDID_manager(Config.getSharedInstance().applicationContext);

            // Try to get the openudid from local preferences
            String localOpenUDID = manager.mPreferences.getString(
                    OpenUDID_manager.PREF_KEY, null);
            if (localOpenUDID == null) {
                final SharedPreferences.Editor e = manager.mPreferences.edit();
                e.putString(OpenUDID_manager.PREF_KEY, fileODID);
                e.commit();
                return true;
            }

            String hashOfLocalODID = Utils.SHA1(localOpenUDID);

            if (fileODID.equalsIgnoreCase(localOpenUDID)
                    && hashOfLocalODID.equalsIgnoreCase(fileHashODID))
                isValid = true;

        } catch (Exception e) {
            Utils.consoleLog(Utils.class,"Exception in validating ODID:" + e.getMessage());
        }

        return isValid;
    }

    public static String getODID() {

        String openUDID = "";
        try {
            String idsFromFile = Utils.readFromFile(Utils.ODID_STRING_PATH);
            if (Utils.validateODID(idsFromFile)) {
                String[] idsArray = idsFromFile.split("-");
                openUDID = idsArray[1];

            }

            Utils.consoleLog(Utils.class,"ODID from file:" + openUDID + "--length:" + openUDID.length());

            if (openUDID == null || openUDID.trim().length() != 40) {

                Utils.consoleLog(Utils.class, "ODID from file is null or length is 0" + openUDID);

                OpenUDID_manager.sync(Config.getSharedInstance().applicationContext);

                openUDID = OpenUDID_manager.getOpenUDID();

                String anotherHash = Utils.SHA1(openUDID);
                String idToSave = anotherHash + "-" + openUDID;
                Utils.saveFile( Utils.ODID_STRING_PATH,idToSave);
            }

        } catch (Exception e) {
            Utils.consoleLog(Utils.class, e.getMessage());

            return "";
        }
        return openUDID;
    }
//
    public static String getIDFA() {

        AdvertisingIdClient.Info adInfo = null;
        String AdId = "";

        try {
            adInfo = AdvertisingIdClient.getAdvertisingIdInfo(Config.getSharedInstance().applicationContext);
            if (adInfo.isLimitAdTrackingEnabled())
                AdId = "";
            else
                AdId = adInfo.getId();
        } catch (GooglePlayServicesNotAvailableException e) {

                Utils.consoleLog(Utils.class, e.getMessage());


        } catch (GooglePlayServicesRepairableException e) {

                Utils.consoleLog(Utils.class,e.getMessage());


            return "";
        } catch (IOException e) {

                Utils.consoleLog(Utils.class, e.getMessage());

            return "";
        } catch (Exception e) {

                Utils.consoleLog(Utils.class, e.getMessage());

            return "";
        }
        return AdId;
    }

    public static String getCountryCode() {
        String val = Locale.getDefault().getCountry();
        if (val == null)
            return "";

        return val;
    }

    public static String getCountryName() {
        String val = Locale.getDefault().getDisplayCountry();
        if (val == null)
            return "";

        return val;
    }

    public static String getSystemLocale() {
        String val = Locale.getDefault().getDisplayLanguage();
        if (val == null)
            return "";

        return val;
    }

    public static String getCurrentLocale() {
        String val = Config.getSharedInstance().applicationContext.getResources().getConfiguration().locale.getLanguage();
        if (val == null)
            return "";

        return val;
    }

    public static String getMachineName() {
        String val = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val = TextUtils.join(",", Build.SUPPORTED_ABIS);
        } else {
            val = Build.CPU_ABI;
        }
        if (val == null)
            return "";

        return val;
    }

    public static String deviceName() {
        String val = null;
        val = Build.DEVICE;

        if (val == null)
            return "";

        return val;
    }

    public static String systemName() {
        String val = null;
        val = "Android OS";

        if (val == null)
            return "";

        return val;
    }

    public static String systemVersion() {
        String val = null;
        val = Build.VERSION.RELEASE;

        if (val == null)
            return "";

        return val;
    }

    public static String deviceModel() {
        String val = null;
        val = Build.MODEL;

        if (val == null)
            return "";

        return val;
    }

    public static String localizedModel() {
        String val = null;
        val = Build.BRAND;

        if (val == null)
            return "";

        return val;
    }

    public static String getAppVersion() {
//        //String val = null;
//        String val=null;
//        //var=BuildConfig.VERSION_NAME;
//        PackageManager myPackageMgr = Config.getSharedInstance().applicationContext.getPackageManager();
//
//        try {
//           // PackageInfo pInfo = myPackageMgr.getPackageInfo("com.purpleyo.purpleyoapp", 0);
//
//           PackageInfo pInfo = myPackageMgr.getPackageInfo("com.prithwiraj.vamdiokerp", 0);
//            val = pInfo.versionName;
//
//        } catch (PackageManager.NameNotFoundException e) {
//
//                Utils.consoleLog(Utils.class, e.getLocalizedMessage());
//
//           return "1.0.1";  //have to change the val value accordingly
//
//
//        }

        int versionCode = BuildConfig.VERSION_CODE;
        String val = BuildConfig.VERSION_NAME;
        return val;
    }

    public static boolean isPhone() {
//        boolean result = ((Config.getSharedInstance().applicationContext.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) < Configuration.SCREENLAYOUT_SIZE_LARGE);
//        return result;

        TelephonyManager manager = (TelephonyManager)(Config.getSharedInstance().applicationContext.getSystemService(Context.TELEPHONY_SERVICE));
        if(manager.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE){
            return false;
        }else{
            return true;
        }
        }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static String getMainGoogleAccount() {
        String val = null;

        Account[] accounts = AccountManager.get(Config.getSharedInstance().applicationContext).getAccountsByType("google");

        for (Account account : accounts) {
            if (account.type.contains("google")) {
                val = account.name;
                break;
            }
        }

        return val;
    }

    public static String getManufacturer() {
        String val = null;
        val = Build.MANUFACTURER;

        if (val == null)
            return "";

        return val;
    }

    public static String getCacheDirectoryPath() {
        String val = null;

        File fileObj = Config.getSharedInstance().applicationContext.getCacheDir();

        if (fileObj == null)
            fileObj = Config.getSharedInstance().applicationContext.getFilesDir();

        val = fileObj.getAbsolutePath();

        if (val == null)
            return "";

        return val;
    }

    public static String getTotalRAMSize() {
        String val = null;

        try {
            RandomAccessFile reader = new RandomAccessFile("/proc/meminfo", "r");
            String load = reader.readLine();

            String[] toks = load.split(" ");  // Split on one or more spaces

            long availableMegs = Long.valueOf(toks[9]) / 1024L;
            reader.close();
            val = String.valueOf(availableMegs);

        } catch (IOException ex) {

                Utils.consoleLog(Utils.class, ex.getLocalizedMessage());

            return "";
        }

        if (val == null)
            return "";

        return val;
    }

    public static String getTotalInternalMemorySize() {
        String val = null;

        try {
            File path = Environment.getDataDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = 0;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {

                blockSize = stat.getBlockSizeLong();
            } else {
                blockSize = stat.getBlockSize();
            }

            long totalBlocks = 0;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {

                blockSize = stat.getBlockCountLong();
            } else {
                blockSize = stat.getBlockCount();
            }

            val = Utils.formatSize(totalBlocks * blockSize);


        } catch (Exception ex) {

                Utils.consoleLog(Utils.class, ex.getLocalizedMessage());

            return "";
        }

        if (val == null)
            return "";

        return val;
    }

    public static String getTotalExternalMemorySize() {
        String val = null;

        try {
            if (Utils.externalMemoryAvailable()) {
                File path = Environment.getExternalStorageDirectory();
                StatFs stat = new StatFs(path.getPath());
                long blockSize = 0;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {

                    blockSize = stat.getBlockSizeLong();
                } else {
                    blockSize = stat.getBlockSize();
                }

                long totalBlocks = 0;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {

                    blockSize = stat.getBlockCountLong();
                } else {
                    blockSize = stat.getBlockCount();
                }
                val = Utils.formatSize(totalBlocks * blockSize);
            }

        } catch (Exception ex) {

            Utils.consoleLog(Utils.class, ex.getLocalizedMessage());

            return "";
        }

        if (val == null)
            return "";

        return val;
    }

    public static String getAvailableRAMSize() {
        String val = null;

        try {
            ActivityManager actManager = (ActivityManager) Config.getSharedInstance().applicationContext.getSystemService(Context.ACTIVITY_SERVICE);
            ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
            actManager.getMemoryInfo(memInfo);
            long totalMemory = memInfo.availMem;
            val = Utils.formatSize(totalMemory);


        } catch (Exception ex) {

                Utils.consoleLog(Utils.class, ex.getLocalizedMessage());

            return "";
        }

        if (val == null)
            return "";

        return val;
    }


    public static String getRealPathFromURI(Context context, Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);

        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public void updateProgressValueOfLoading(int progress)
    {
        if(this.progressDialog!=null)
        {
            this.progressDialog.setProgress(progress);
        }
    }

    public void hideLoading() {
        try {


            if (this.progressDialog != null && this.progressDialog.isShowing()) {
                this.progressDialog.dismiss();

            }
            this.progressDialog = null;
        } catch (Exception e) {
            Utils.consoleLog(Utils.class, e.getLocalizedMessage());
        }
    }

    public void displayLoading(Context context) {

        this.displayLoading(context,"Loading. Please wait...", ProgressDialog.STYLE_SPINNER);

    }


    public void displayLoading(Context context, String message, int style) {

        try {
            if (this.progressDialog != null && this.progressDialog.isShowing()) {
                return;
            }
            this.progressDialog = new ProgressDialog(context);
            this.progressDialog.setProgressStyle(style);
            this.progressDialog.setMessage(message);

            this.progressDialog.setIndeterminate(false);
            this.progressDialog.setCanceledOnTouchOutside(false);
            this.progressDialog.show();

        } catch (Exception e) {
            Utils.consoleLog(Utils.class, e.getLocalizedMessage());
        }
    }


    public static String getAvailableInternalMemorySize() {
        String val = null;

        try {
            File path = Environment.getDataDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = 0;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {

                blockSize = stat.getBlockSizeLong();
            } else {
                blockSize = stat.getBlockSize();
            }

            long availableBlocks = 0;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {

                availableBlocks = stat.getAvailableBlocksLong();
            } else {
                availableBlocks = stat.getAvailableBlocks();
            }

            val = Utils.formatSize(availableBlocks * blockSize);


        } catch (Exception ex) {

                Utils.consoleLog(Utils.class, ex.getLocalizedMessage());

            return "";
        }

        if (val == null)
            return "";

        return val;
    }

    public static String getAvailableExternalMemorySize() {
        String val = null;

        try {
            if (Utils.externalMemoryAvailable()) {
                File path = Environment.getExternalStorageDirectory();
                StatFs stat = new StatFs(path.getPath());
                long blockSize = 0;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {

                    blockSize = stat.getBlockSizeLong();
                } else {
                    blockSize = stat.getBlockSize();
                }

                long availableBlocks = 0;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {

                    availableBlocks = stat.getAvailableBlocksLong();
                } else {
                    availableBlocks = stat.getAvailableBlocks();
                }
                val = Utils.formatSize(availableBlocks * blockSize);
            }

        } catch (Exception ex) {

                Utils.consoleLog(Utils.class, ex.getLocalizedMessage());

            return "";
        }

        if (val == null)
            return "";

        return val;
    }

    public static void shareDefault(String textToShare, String website) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, textToShare + "\n" + website);

        Config.getSharedInstance().applicationContext.startActivity(Intent.createChooser(share, "Share \"TableSpace\""));

    }


    public static void getMobileNumber(Activity activity) {
        int permissionCheck = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
        } else {

            if(ErpCurrentUser.getSharedInstance().getCurrentDeviceMobileNumber()==null) {
                TelephonyManager tMgr = (TelephonyManager) Config.getSharedInstance().applicationContext.getSystemService(Context.TELEPHONY_SERVICE);
                ErpCurrentUser.getSharedInstance().setCurrentDeviceMobileNumber(tMgr.getLine1Number());
            }
        }

    }

    public final static boolean isValidEmail(CharSequence email) {
        if (email == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }


    public static int randomNumberBetween(int min, int max)
    {
        Random random = new Random();
        return random.nextInt(max-min)+min;
    }


    public void displayLoadingAnimation()
    {

    }

    public void hideLoadingAnimation()
    {

    }




    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     */
//    public void verifyStoragePermissions() {
//        // Check if we have write permission
//        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
//
//        if (permission != PackageManager.PERMISSION_GRANTED) {
//            // We don't have permission so prompt the user
//            ActivityCompat.requestPermissions(
//                    this,
//                    PERMISSIONS_STORAGE,
//                    REQUEST_EXTERNAL_STORAGE
//            );
//        }
//    }
}
