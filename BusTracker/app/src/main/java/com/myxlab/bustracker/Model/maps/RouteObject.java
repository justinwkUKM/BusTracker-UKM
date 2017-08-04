package com.myxlab.bustracker.Model.maps;

/**
 * Created by MyXLab on 4/8/2017.
 */

import java.util.List;
public class RouteObject {
    private List<LegsObject> legs;
    public RouteObject(List<LegsObject> legs) {
        this.legs = legs;
    }
    public List<LegsObject> getLegs() {
        return legs;
    }
}