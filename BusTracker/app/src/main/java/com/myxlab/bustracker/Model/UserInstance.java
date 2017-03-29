package com.myxlab.bustracker.Model;

import com.android.volley.RequestQueue;
import com.myxlab.bustracker.Controller.VolleyApp;
import com.myxlab.bustracker.Utility;
import com.myxlab.bustracker.View.MainActivity;

import java.util.List;

public class UserInstance {

    private Auth auth;
    private VolleyApp volleyApp;
    private Utility utility;
    private long lastRequestTime;
    private RequestQueue queue;
    private List<BusStop> busStopList;
    private List<Bus> buses;
    private MainActivity mainActivity;

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
}
