package com.driverapp.Model;

import com.android.volley.RequestQueue;
import com.driverapp.Controller.VolleyApp;
import com.driverapp.Utility;

/**
 * The type User instance.
 */
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

    /**
     * Gets auth.
     *
     * @return the auth
     */
    public Auth getAuth() {
        return auth;
    }

    /**
     * Sets auth.
     *
     * @param auth the auth
     */
    public void setAuth(Auth auth) {
        this.auth = auth;
    }

    /**
     * Gets bus.
     *
     * @return the bus
     */
    public Bus getBus() {
        return bus;
    }

    /**
     * Sets bus.
     *
     * @param bus the bus
     */
    public void setBus(Bus bus) {
        this.bus = bus;
    }

    /**
     * Gets route.
     *
     * @return the route
     */
    public Route getRoute() {
        return route;
    }

    /**
     * Sets route.
     *
     * @param route the route
     */
    public void setRoute(Route route) {
        this.route = route;
    }

    /**
     * Gets driver.
     *
     * @return the driver
     */
    public Driver getDriver() {
        return driver;
    }

    /**
     * Sets driver.
     *
     * @param driver the driver
     */
    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    /**
     * Gets volley app.
     *
     * @return the volley app
     */
    public VolleyApp getVolleyApp() {
        return volleyApp;
    }

    /**
     * Sets volley app.
     *
     * @param volleyApp the volley app
     */
    public void setVolleyApp(VolleyApp volleyApp) {
        this.volleyApp = volleyApp;
    }

    /**
     * Gets utility.
     *
     * @return the utility
     */
    public Utility getUtility() {
        return utility;
    }

    /**
     * Sets utility.
     *
     * @param utility the utility
     */
    public void setUtility(Utility utility) {
        this.utility = utility;
    }

    /**
     * Gets last request time.
     *
     * @return the last request time
     */
    public long getLastRequestTime() {
        return lastRequestTime;
    }

    /**
     * Sets last request time.
     *
     * @param lastRequestTime the last request time
     */
    public void setLastRequestTime(long lastRequestTime) {
        this.lastRequestTime = lastRequestTime;
    }

    /**
     * Gets queue.
     *
     * @return the queue
     */
    public RequestQueue getQueue() {
        return queue;
    }

    /**
     * Sets queue.
     *
     * @param queue the queue
     */
    public void setQueue(RequestQueue queue) {
        this.queue = queue;
    }

    /**
     * Gets bus location.
     *
     * @return the bus location
     */
    public int getBusLocation() {
        return busLocation;
    }

    /**
     * Sets bus location.
     *
     * @param busLocation the bus location
     */
    public void setBusLocation(int busLocation) {
        this.busLocation = busLocation;
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static UserInstance getInstance() {return holder;}
}
