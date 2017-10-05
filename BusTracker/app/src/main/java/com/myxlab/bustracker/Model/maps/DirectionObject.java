package com.myxlab.bustracker.Model.maps;

/**
 * Created by MyXLab on 4/8/2017.
 */

import java.util.List;

/**
 * The type Direction object.
 */
public class DirectionObject {
    private List<RouteObject> routes;
    private String status;

    /**
     * Instantiates a new Direction object.
     *
     * @param routes the routes
     * @param status the status
     */
    public DirectionObject(List<RouteObject> routes, String status) {
        this.routes = routes;
        this.status = status;
    }

    /**
     * Gets routes.
     *
     * @return the routes
     */
    public List<RouteObject> getRoutes() {
        return routes;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }
}