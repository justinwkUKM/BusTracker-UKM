package com.myxlab.bustracker.Model.maps;

/**
 * Created by MyXLab on 4/8/2017.
 */

import java.util.List;
public class DirectionObject {
    private List<RouteObject> routes;
    private String status;
    public DirectionObject(List<RouteObject> routes, String status) {
        this.routes = routes;
        this.status = status;
    }
    public List<RouteObject> getRoutes() {
        return routes;
    }
    public String getStatus() {
        return status;
    }
}