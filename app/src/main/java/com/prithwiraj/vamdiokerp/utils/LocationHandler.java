package com.prithwiraj.vamdiokerp.utils;

/**
 * Created by prithwi on 5/4/17.
 */

public class LocationHandler {

    private double latitude;
    private double longitude;
    private boolean locationPermissionGiven;

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    private static LocationHandler _instance = null;

    public static LocationHandler getSharedInstance()
    {
        if(_instance==null)
        {
            _instance = new LocationHandler();

        }
        return  _instance;
    }

    private LocationHandler()
    {

    }

    public boolean isLocationPermissionGiven() {
        return locationPermissionGiven;
    }
}
