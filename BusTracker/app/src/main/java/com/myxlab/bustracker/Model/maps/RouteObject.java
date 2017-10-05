package com.myxlab.bustracker.Model.maps;

/**
 * Created by MyXLab on 4/8/2017.
 */

import java.util.List;

/**
 * The type Route object.
 */
public class RouteObject {
    private List<LegsObject> legs;

    /**
     * Instantiates a new Route object.
     *
     * @param legs the legs
     */
    public RouteObject(List<LegsObject> legs) {
        this.legs = legs;
    }

    /**
     * Gets legs.
     *
     * @return the legs
     */
    public List<LegsObject> getLegs() {
        return legs;
    }
}