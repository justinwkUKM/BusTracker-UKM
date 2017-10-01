package com.myxlab.bustracker;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * The type Utility.
 */
public class Utility {

    /**
     * Gets network info.
     *
     * @param connectivityManager the connectivity manager
     * @return the network info
     */
    public boolean getNetworkInfo(ConnectivityManager connectivityManager) {

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected() && networkInfo.isAvailable();

    }

}
