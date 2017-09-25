package com.myxlab.bustracker.Model.maps;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by MyXLab on 23/9/2017.
 */

public class MyClusterItem implements ClusterItem {
    private final LatLng mPosition;
    private  String mTitle;
    private  String mSnippet;

    public MyClusterItem(double lat, double lng) {
        mPosition = new LatLng(lat, lng);
    }

    public MyClusterItem(double lat, double lng, String title, String snippet) {
        mPosition = new LatLng(lat, lng);
        mTitle = title;
        mSnippet = snippet;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }


    public String getmTitle() {
        return mTitle;
    }

    public String getmSnippet() {
        return mSnippet;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setmSnippet(String mSnippet) {
        this.mSnippet = mSnippet;
    }
}