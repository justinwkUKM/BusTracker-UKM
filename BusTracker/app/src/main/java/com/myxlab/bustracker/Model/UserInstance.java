package com.myxlab.bustracker.Model;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.myxlab.bustracker.Controller.VolleyApp;
import com.myxlab.bustracker.Utility;
import com.myxlab.bustracker.View.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

/**
 * The type User instance.
 */
public class UserInstance {

    private Auth auth;
    private VolleyApp volleyApp;
    private Utility utility;
    private long lastRequestTime;
    private RequestQueue queue;
    private List<BusStop> busStopList;
    private List<AlertsData> alertsDataList;
    private List<Bus> buses;
    private Route route;
    /**
     * The Route list.
     */
    List<Route> routeList;
    private MainActivity mainActivity;

    private int nearestBusStopIndex = 0;



    private static final UserInstance holder = new UserInstance();

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static UserInstance getInstance() {return holder;}

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
     * Gets bus stop list.
     *
     * @return the bus stop list
     */
    public List<BusStop> getBusStopList() {
        return busStopList;
    }

    /**
     * Sets bus stop list.
     *
     * @param busStopList the bus stop list
     */
    public void setBusStopList(List<BusStop> busStopList) {
        this.busStopList = busStopList;
    }

    /**
     * Gets alerts data list.
     *
     * @return the alerts data list
     */
    public List<AlertsData> getAlertsDataList() {
        return alertsDataList;
    }

    /**
     * Sets alerts data list.
     *
     * @param alertsDataList the alerts data list
     */
    public void setAlertsDataList(List<AlertsData> alertsDataList) {
        this.alertsDataList = alertsDataList;
    }

    /**
     * Gets main activity.
     *
     * @return the main activity
     */
    public MainActivity getMainActivity() {
        return mainActivity;
    }

    /**
     * Sets main activity.
     *
     * @param mainActivity the main activity
     */
    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    /**
     * Gets buses.
     *
     * @return the buses
     */
    public List<Bus> getBuses() {
        return buses;
    }

    /**
     * Sets buses.
     *
     * @param buses the buses
     */
    public void setBuses(List<Bus> buses) {
        this.buses = buses;
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
    /**
     * Gets nearest bus stop index.
     *
     * @return the nearest bus stop index
     */
    public int getNearestBusStopIndex() {
        return nearestBusStopIndex;
    }

    /**
     * Sets nearest bus stop index.
     *
     * @param nearestBusStopIndex the nearest bus stop index
     */
    public void setNearestBusStopIndex(int nearestBusStopIndex) {
        this.nearestBusStopIndex = nearestBusStopIndex;
    }

    /**
     * Gets route list.
     *
     * @return the route list
     */
    public List<Route> getRouteList() {
        return routeList;
    }

    /**
     * Sets route list.
     *
     * @param routeList the route list
     */
    public void setRouteList(List<Route> routeList) {
        this.routeList = routeList;
    }
}
