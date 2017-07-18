package com.driverapp.Model;

import java.util.List;

public class Route {

    private String routeId, routeName;
    private List<BusStop> busStopList;

    public Route() {
    }

    public Route(String routeId, String routeName, List<BusStop> busStopList) {
        this.routeId = routeId;
        this.routeName = routeName;
        this.busStopList = busStopList;
    }

    public String getRouteId() {
        return routeId;
    }

    public String getRouteName() {
        return routeName;
    }

    public List<BusStop> getBusStopList() {
        return busStopList;
    }

}
