package com.myxlab.bustracker.Model.maps;

/**
 * Created by MyXLab on 4/8/2017.
 */

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * The type Helper.
 */
public class Helper {
    private static final String DIRECTION_API = "https://maps.googleapis.com/maps/api/directions/json?origin=";
    /**
     * The constant API_KEY.
     */
    public static final String API_KEY = "AIzaSyCuZCfoPPUV1upJT10kJbCbV71LUqwhFCM";
    /**
     * The constant MY_SOCKET_TIMEOUT_MS.
     */
    public static final int MY_SOCKET_TIMEOUT_MS = 5000;

    /**
     * Get url string.
     *
     * @param originLat      the origin lat
     * @param originLon      the origin lon
     * @param destinationLat the destination lat
     * @param destinationLon the destination lon
     * @param mode           the mode
     * @return the string
     */
    public static String getUrl(String originLat, String originLon, String destinationLat, String destinationLon, String mode){
        return Helper.DIRECTION_API + originLat+","+originLon+"&destination="+destinationLat+","+destinationLon+"&mode="+mode+"&key="+API_KEY;
    }

    /**
     * Is network available boolean.
     *
     * @param context the context
     * @return the boolean
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}