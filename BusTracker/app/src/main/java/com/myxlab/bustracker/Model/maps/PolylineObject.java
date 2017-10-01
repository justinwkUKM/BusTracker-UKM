package com.myxlab.bustracker.Model.maps;

/**
 * Created by MyXLab on 4/8/2017.
 */
public class PolylineObject {
    private String points;

    /**
     * Instantiates a new Polyline object.
     *
     * @param points the points
     */
    public PolylineObject(String points) {
        this.points = points;
    }

    /**
     * Gets points.
     *
     * @return the points
     */
    public String getPoints() {
        return points;
    }
}