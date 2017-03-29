package com.driverapp.Model;

import com.android.volley.RequestQueue;
import com.driverapp.Controller.VolleyApp;
import com.driverapp.Utility;

public class UserInstance{

    private Auth auth;
    private Bus bus;
    private Route route;
    private Driver driver;
    private VolleyApp volleyApp;
    private Utility utility;
    private long lastRequestTime;
    private RequestQueue queue;
    private int busLocation;

    private static final UserInstance holder = new UserInstance();

    public Auth getAuth() {
        return auth;
    }

    public void setAuth(Auth auth) {
        this.auth = auth;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public VolleyApp getVolleyApp() {
        return volleyApp;
    }

    public void setVolleyApp(VolleyApp volleyApp) {
        this.volleyApp = volleyApp;
    }

    public Utility getUtility() {
        return utility;
    }

    public void setUtility(Utility utility) {
        this.utility = utility;
    }

    public long getLastRequestTime() {
        return lastRequestTime;
    }

    public void setLastRequestTime(long lastRequestTime) {
        this.lastRequestTime = lastRequestTime;
    }

    public RequestQueue getQueue() {
        return queue;
    }

    public void setQueue(RequestQueue queue) {
        this.queue = queue;
    }

    public int getBusLocation() {
        return busLocation;
    }

    public void setBusLocation(int busLocation) {
        this.busLocation = busLocation;
    }

    public static UserInstance getInstance() {return holder;}
}
