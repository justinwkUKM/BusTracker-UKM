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

public class UserInstance {

    private Auth auth;
    private VolleyApp volleyApp;
    private Utility utility;
    private long lastRequestTime;
    private RequestQueue queue;
    private List<BusStop> busStopList;
    private List<AlertsData> alertsDataList;
    private List<Bus> buses;
    List<Route> routeList;
    private MainActivity mainActivity;

    private int nearestBusStopIndex = 0;



    private static final UserInstance holder = new UserInstance();

    public static UserInstance getInstance() {return holder;}

    public Auth getAuth() {
        return auth;
    }

    public void setAuth(Auth auth) {
        this.auth = auth;
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

    public List<BusStop> getBusStopList() {
        return busStopList;
    }

    public void setBusStopList(List<BusStop> busStopList) {
        this.busStopList = busStopList;
    }

    public List<AlertsData> getAlertsDataList() {
        return alertsDataList;
    }

    public void setAlertsDataList(List<AlertsData> alertsDataList) {
        this.alertsDataList = alertsDataList;
    }

    public MainActivity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public List<Bus> getBuses() {
        return buses;
    }

    public void setBuses(List<Bus> buses) {
        this.buses = buses;
    }

    public int getNearestBusStopIndex() {
        return nearestBusStopIndex;
    }

    public void setNearestBusStopIndex(int nearestBusStopIndex) {
        this.nearestBusStopIndex = nearestBusStopIndex;
    }

    public List<Route> getRouteList() {
        return routeList;
    }

    public void setRouteList(List<Route> routeList) {
        this.routeList = routeList;
    }
}
