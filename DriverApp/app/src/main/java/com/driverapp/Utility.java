package com.driverapp;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class Utility {

    public boolean getNetworkInfo(ConnectivityManager connectivityManager) {

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return false;
        }
        if (!networkInfo.isConnected()) {
            return false;
        }
        if (!networkInfo.isAvailable()) {
            return false;
        }

        return true;
    }
}
