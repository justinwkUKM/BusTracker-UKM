package com.myxlab.bustracker.Model.maps;

/**
 * Created by MyXLab on 4/8/2017.
 */
public class StepsObject {
    private PolylineObject polyline;

    /**
     * Instantiates a new Steps object.
     *
     * @param polyline the polyline
     */
    public StepsObject(PolylineObject polyline) {
        this.polyline = polyline;
    }

    /**
     * Gets polyline.
     *
     * @return the polyline
     */
    public PolylineObject getPolyline() {
        return polyline;
    }
}