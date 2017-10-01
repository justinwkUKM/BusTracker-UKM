package com.myxlab.bustracker.Model.maps;

/**
 * Created by MyXLab on 4/8/2017.
 */

import android.app.Application;
import com.android.volley.RequestQueue;

/**
 * The type Custom application.
 */
public class CustomApplication extends Application{
    private RequestQueue requestQueue;
    @Override
    public void onCreate() {
        super.onCreate();
        requestQueue = VolleySingleton.getInstance(getApplicationContext()).getRequestQueue();
    }

    /**
     * Get volley request queue request queue.
     *
     * @return the request queue
     */
    public RequestQueue getVolleyRequestQueue(){
        return requestQueue;
    }
}
