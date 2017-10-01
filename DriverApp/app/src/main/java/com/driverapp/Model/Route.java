package com.driverapp.Model;

import java.util.List;

/**
 * The type Route.
 */
public class Route {

    private String routeId, routeName;
    private List<BusStop> busStopList;

    /**
     * Instantiates a new Route.
     */
    public Route() {
    }

    /**
     * Instantiates a new Route.
     *
     * @param routeId     the route id
     * @param routeName   the route name
     * @param busStopList the bus stop list
     */
    public Route(String routeId, String routeName, List<BusStop> busStopList) {
        this.routeId = routeId;
        this.routeName = routeName;
        this.busStopList = busStopList;
    }

    /**
     * Gets route id.
     *
     * @return the route id
     */
    public String getRouteId() {
        return routeId;
    }

    /**
     * Gets route name.
     *
     * @return the route name
     */
    public String getRouteName() {
        return routeName;
    }

    /**
     * Gets bus stop list.
     *
     * @return the bus stop list
     */
    public List<BusStop> getBusStopList() {
        return busStopList;
    }

}
