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

    /**
     * Instantiates a new My cluster item.
     *
     * @param lat the lat
     * @param lng the lng
     */
    public MyClusterItem(double lat, double lng) {
        mPosition = new LatLng(lat, lng);
    }

    /**
     * Instantiates a new My cluster item.
     *
     * @param lat     the lat
     * @param lng     the lng
     * @param title   the title
     * @param snippet the snippet
     */
    public MyClusterItem(double lat, double lng, String title, String snippet) {
        mPosition = new LatLng(lat, lng);
        mTitle = title;
        mSnippet = snippet;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }


    /**
     * Gets title.
     *
     * @return the title
     */
    public String getmTitle() {
        return mTitle;
    }

    /**
     * Gets snippet.
     *
     * @return the snippet
     */
    public String getmSnippet() {
        return mSnippet;
    }

    /**
     * Sets title.
     *
     * @param mTitle the m title
     */
    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    /**
     * Sets snippet.
     *
     * @param mSnippet the m snippet
     */
    public void setmSnippet(String mSnippet) {
        this.mSnippet = mSnippet;
    }
}