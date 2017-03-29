package com.myxlab.bustracker;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utility {

    public boolean getNetworkInfo(ConnectivityManager connectivityManager) {

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected() && networkInfo.isAvailable();

    }

}
