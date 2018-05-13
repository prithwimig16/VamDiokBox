package org.OpenUDID;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.IBinder;
import android.os.RemoteException;


import com.prithwiraj.vamdiokerp.utils.Utils;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;


public class OpenUDID_manager implements ServiceConnection {

    public final static String PREF_KEY = "openudid";
    public final static String PREFS_NAME = "purpleyo_openudid_prefs";
    public final static String TAG = "OpenUDID";

    private final Context mContext;
    private List<ResolveInfo> mMatchingIntents; // List of available OpenUDID Intents
    private Map<String, Integer> mReceivedOpenUDIDs; // Map of OpenUDIDs found so far
    public final SharedPreferences mPreferences;
    private final Random mRandom;

    public OpenUDID_manager(Context context) {
        mPreferences = context.getSharedPreferences(PREFS_NAME,	Context.MODE_PRIVATE);
        mContext = context;
        mRandom = new Random();
        mReceivedOpenUDIDs = new HashMap<String, Integer>();
    }

    @Override
    public void onServiceConnected(ComponentName className, IBinder service) {
        // Get the OpenUDID from the remote service
        try {
            // Send a random number to the service
            android.os.Parcel data = android.os.Parcel.obtain();
            data.writeInt(mRandom.nextInt());
            android.os.Parcel reply = android.os.Parcel.obtain();
            service.transact(1, android.os.Parcel.obtain(), reply, 0);
            if (data.readInt() == reply.readInt()) // Check if the service
            // returns us this number
            {
                final String _openUDID = reply.readString();
                if (_openUDID != null) { // if valid OpenUDID, save it
                    Utils.consoleLog(OpenUDID_manager.class,"Received " + _openUDID);
                    if (mReceivedOpenUDIDs.containsKey(_openUDID))
                        mReceivedOpenUDIDs.put(_openUDID, mReceivedOpenUDIDs.get(_openUDID) + 1);
                    else
                        mReceivedOpenUDIDs.put(_openUDID, 1);
                }
            }
            data.recycle();
        } catch (RemoteException e) {
            Utils.consoleLog(OpenUDID_manager.class, "RemoteException: " + e.getMessage());
        }
        mContext.unbindService(this);

        // startService(); //Try the next one
    }

    @Override
    public void onServiceDisconnected(ComponentName className) {
    }

    private void storeOpenUDID() {
        final Editor e = mPreferences.edit();
        e.putString(PREF_KEY, OpenUDID);
        e.commit();
    }

    /*
     * Generate a new OpenUDID
     */

    private void generateOpenUDID() {
        Utils.consoleLog(OpenUDID_manager.class, "Generating openUDID");
        if (OpenUDID == null 	|| OpenUDID.length() != 40) {
            final SecureRandom random = new SecureRandom();
            OpenUDID = new BigInteger(64, random).toString(16);

            try
            {
                long seconds = System.nanoTime();
                long microseconds = seconds / 1000;
                Utils.consoleLog(OpenUDID_manager.class, "microseconds:"+microseconds);
                String tempODID = OpenUDID + microseconds;
                OpenUDID = Utils.SHA1(tempODID);

            }
            catch(Exception e)
            {
                Utils.consoleLog(OpenUDID_manager.class, "Error in creating sha1:"+e.getMessage());
            }
        }
    }

    /*
     * Start the oldest service
     */
    @SuppressWarnings("unused")
    private void startService() {
        if (mMatchingIntents.size() > 0) { // There are some Intents untested
            Utils.consoleLog(OpenUDID_manager.class,"Trying service " + mMatchingIntents.get(0).loadLabel(mContext.getPackageManager()));
            final ServiceInfo servInfo = mMatchingIntents.get(0).serviceInfo;
            final Intent i = new Intent();
            i.setComponent(new ComponentName(servInfo.applicationInfo.packageName, servInfo.name));
            mContext.bindService(i, this, Context.BIND_AUTO_CREATE);
            mMatchingIntents.remove(0);
        } else { // No more service to test

            getMostFrequentOpenUDID(); // Choose the most frequent
            if (OpenUDID == null) // No OpenUDID was chosen, generate one
                generateOpenUDID();
            Utils.consoleLog(OpenUDID_manager.class, "OpenUDID: " + OpenUDID);
            storeOpenUDID();// Store it locally
            mInitialized = true;
        }
    }

    private void getMostFrequentOpenUDID() {
        if (mReceivedOpenUDIDs.isEmpty() == false) {

            final TreeMap<String, Integer> sorted_OpenUDIDS = new TreeMap<String, Integer>(new ValueComparator());
            sorted_OpenUDIDS.putAll(mReceivedOpenUDIDs);

            OpenUDID = sorted_OpenUDIDS.firstKey();
        }
    }

    private static String OpenUDID = null;
    private static boolean mInitialized = false;

    /**
     * The Method to call to get OpenUDID
     *
     * @return the OpenUDID
     */
    public static String getOpenUDID() {
        if (!mInitialized)
            Utils.consoleLog(OpenUDID_manager.class, "Initialisation isn't done");
        return OpenUDID;
    }

    /**
     * The Method to call to get OpenUDID
     *
     * @return the OpenUDID
     */
    public static boolean isInitialized() {
        return mInitialized;
    }

    /**
     * The Method the call at the init of your app
     *
     * @param context
     *            you current context
     */
    public static void sync(Context context) {
        // Initialise the Manager
        OpenUDID_manager manager = new OpenUDID_manager(context);

        // Try to get the openudid from local preferences
        OpenUDID = manager.mPreferences.getString(PREF_KEY, null);
        if (OpenUDID == null  || OpenUDID.length() != 40) // Not found
        {
             manager.generateOpenUDID();


            manager.storeOpenUDID();// Store it locally
            mInitialized = true;

        } else {// Got it, you can now call getOpenUDID()
            Utils.consoleLog(OpenUDID_manager.class, "OpenUDID: " + OpenUDID);
            mInitialized = true;
        }
    }

    /*
     * Used to sort the OpenUDIDs collected by occurrence
     */
    private class ValueComparator implements Comparator<Object> {
        public int compare(Object a, Object b) {

            if (mReceivedOpenUDIDs.get(a) < mReceivedOpenUDIDs.get(b)) {
                return 1;
            } else if (mReceivedOpenUDIDs.get(a) == mReceivedOpenUDIDs.get(b)) {
                return 0;
            } else {
                return -1;
            }
        }
    }
}