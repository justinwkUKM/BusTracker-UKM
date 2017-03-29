package com.myxlab.bustracker.Controller;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v4.util.LruCache;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.myxlab.bustracker.DBHandler;
import com.myxlab.bustracker.Model.Auth;
import com.myxlab.bustracker.Model.Bus;
import com.myxlab.bustracker.Model.BusStop;
import com.myxlab.bustracker.Model.POI;
import com.myxlab.bustracker.Model.UserInstance;
import com.myxlab.bustracker.R;
import com.myxlab.bustracker.View.LoginActivity;
import com.myxlab.bustracker.View.MainActivity;
import com.myxlab.bustracker.View.MapsFragment;
import com.myxlab.bustracker.View.SplashActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.myxlab.bustracker.View.MainActivity.BUS;
import static com.myxlab.bustracker.View.MainActivity.BUS_STOP;

public class VolleyApp {
    private VolleyApp volleyApp;
    private Context context;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
    private int delay = 1200;

    public VolleyApp() {
    }

    private VolleyApp(Context context) {
        this.context = context;
        this.requestQueue = getRequestQueue();
        imageLoader = new ImageLoader(requestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }

    public synchronized VolleyApp getInstance(Context context) {
        if (volleyApp == null) {
            volleyApp = new VolleyApp(context);
        }
        return volleyApp;
    }

    private RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            Cache cache = new DiskBasedCache(context.getCacheDir(), 10 * 1024 * 1024);
            Network network = new BasicNetwork(new HurlStack());
            requestQueue = new RequestQueue(cache, network);
            requestQueue.start();
        }
        return requestQueue;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    public void UserLoginTask(final String Url, final String username, final String password, final Context context, final View view, final LoginActivity loginActivity) {

        Map<String, String> params = new HashMap<>();
        params.put(Auth.KEY_USERNAME, username);
        params.put(Auth.KEY_PASSWORD, password);
        JSONObject parameters = new JSONObject(params);

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, Url, parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        UserInstance.getInstance().getAuth().setAuth_token(response.optString("token"));
                        UserInstance.getInstance().getAuth().saveAuth(context, username, password);
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        loginActivity.finish();
                        context.startActivity(intent);
                        view.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        view.setVisibility(View.GONE);
                        volleyErrorResponse(error, context);
                    }
                });

        if (!checkQueueServeTime()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (loginActivity.getApplication() != null) {
                        addQueue(jsonRequest);
                    }
                }
            }, delay);

        } else {
            addQueue(jsonRequest);
        }
    }

    public void autoLogin(String Url, final Context context, final SplashActivity splashActivity) {

        JSONObject parameters = new JSONObject(UserInstance.getInstance().getAuth().getCredential());

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, Url, parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        UserInstance.getInstance().getAuth().setAuth_token(response.optString("token"));
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        splashActivity.finish();
                        context.startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        volleyErrorResponse(error, context);
                    }
                });

        if (!checkQueueServeTime()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    addQueue(jsonRequest);
                }
            }, delay);

        } else {
            addQueue(jsonRequest);
        }
    }

//    private void newToken(String Url, final Context context) {
//
//        JSONObject parameters = new JSONObject(UserInstance.getInstance().getAuth().getCredential());
//
//        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, Url, parameters,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        UserInstance.getInstance().getAuth().setAuth_token(response.optString("token"));
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        volleyErrorResponse(error, context);
//                    }
//                });
//
//        if (!checkQueueServeTime()){
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    addQueue(jsonRequest);
//                }
//            }, delay);
//
//        } else {
//            addQueue(jsonRequest);
//        }
//    }

    public void getBusStop(String url, final Context context, final MapsFragment mapsFragment) {
        String api = url + "?limit=all&token=" + UserInstance.getInstance().getAuth().getAuth_token();

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, api,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        try {
                            JSONArray resultArray = response.getJSONArray("station");

                            List<BusStop> busStops = new LinkedList<>();

                            for (int i = 0; i < resultArray.length(); i++) {

                                try {
                                    JSONObject json = resultArray.getJSONObject(i);

                                    String name = String.valueOf(json.getString("name"));
                                    Double lat = Double.valueOf(json.getString("lat"));
                                    Double lon = Double.valueOf(json.getString("lon"));
                                    JSONArray bus = json.getJSONArray("buses");
                                    List<String> busItem = new LinkedList<>();

                                    for (int j = 0; j < bus.length(); j++) {
                                        busItem.add(bus.getString(j));
                                    }

                                    BusStop busStop = new BusStop(name, lat, lon, busItem);
                                    busStops.add(busStop);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            UserInstance.getInstance().setBusStopList(busStops);
                            mapsFragment.setBusStopMarker();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        volleyErrorResponse(error, context);
                    }
                }) {

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {

                return volleyParseNetworkResponse(response);
            }

        };

        if (!checkQueueServeTime()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    addQueue(jsonRequest);
                }
            }, delay);

        } else {
            addQueue(jsonRequest);
        }
    }

    public void getETA(String url, final String busStop, final String bus, final MainActivity mainActivity) {

        Map<String, String> params = new HashMap<>();
        params.put(BUS_STOP, busStop);
        params.put(BUS, bus);
        JSONObject parameters = new JSONObject(params);

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        String eta = null;
                        String etaTo = null;
                        Double lat = null;
                        Double lon = null;

                        try {
                            JSONArray resultArray = response.getJSONArray("results");

                            JSONObject obj = resultArray.getJSONObject(0);

                            eta = obj.getString("eta");
                            etaTo = "to " + busStop;
                            lat = Double.valueOf(obj.getString("lat"));
                            lon = Double.valueOf(obj.getString("lon"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        assert eta != null;
                        if (!eta.equals("Please wait for next Trip")) {
                            mainActivity.setETA(eta, etaTo, lat, lon, true);

                        } else {
                            mainActivity.setETA("Next", eta, lat, lon, false);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        if (!checkQueueServeTime()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    addQueue(jsonRequest);
                }
            }, delay);

        } else {
            addQueue(jsonRequest);
        }
    }

    public void getBuses(String url, final Context context, final MapsFragment mapsFragment) {

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        List<Bus> buses = new LinkedList<>();

                        try {
                            JSONArray resultArray = response.getJSONArray("results");

                            for (int i = 0; i < resultArray.length(); i++) {

                                try {
                                    JSONObject json = resultArray.getJSONObject(i);
                                    Double lat = Double.valueOf(json.getString("lat"));
                                    Double lon = Double.valueOf(json.getString("lon"));
                                    String name = String.valueOf(json.getString("bus"));
                                    String plate = String.valueOf(json.getString("plate"));

                                    Bus bus= new Bus(name, lat, lon, plate);
                                    buses.add(bus);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            UserInstance.getInstance().setBuses(buses);
                            mapsFragment.setBus();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        volleyErrorResponse(error, context);
                    }
                }) {

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {

                return volleyParseNetworkResponse(response);
            }

        };

        if (!checkQueueServeTime()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    addQueue(jsonRequest);
                }
            }, delay);

        } else {
            addQueue(jsonRequest);
        }
    }

    public void updateDB(String url, final Context context) {

        String api = url + "?limit=all&token=" + UserInstance.getInstance().getAuth().getAuth_token();

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, api,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray resultArray = response.getJSONArray("point_of_interest");

                            if (resultArray.length() != 0) {
                                DBHandler dbHandler = new DBHandler(context, null);
                                dbHandler.delAllData();

                                for (int i = 0; i < resultArray.length(); i++) {
                                    JSONObject json;
                                    POI poi;

                                    try {
                                        json = resultArray.getJSONObject(i);
                                        poi = new POI();
                                        poi.setName(String.valueOf(json.getString("name")));
                                        poi.setLat(String.valueOf(json.getString("latitud")));
                                        poi.setLon(String.valueOf(json.getString("longitud")));
                                        poi.setType(String.valueOf(json.getString("category")));
                                        poi.setCode(String.valueOf(json.getString("code")));
                                        dbHandler.addPOI(poi);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        volleyErrorResponse(error, context);
                    }
                }) {

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {

                return volleyParseNetworkResponse(response);
            }

        };

        if (!checkQueueServeTime()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    addQueue(jsonRequest);
                }
            }, delay);

        } else {
            addQueue(jsonRequest);
        }
    }

    private void volleyErrorResponse(VolleyError error, Context context) {

        try {
            if (error.networkResponse != null) {
                volleyParseNetworkResponse(error.networkResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                Toast.makeText(context, R.string.error_connectivity, Toast.LENGTH_LONG).show();
            } else if (error instanceof AuthFailureError) {
                Toast.makeText(context, R.string.error_credential, Toast.LENGTH_LONG).show();
            } else if (error instanceof ServerError) {
                Toast.makeText(context, R.string.error_server, Toast.LENGTH_LONG).show();
            } else if (error instanceof NetworkError) {
                Toast.makeText(context, R.string.error_connectivity, Toast.LENGTH_LONG).show();
            } else if (error instanceof ParseError) {
                Toast.makeText(context, R.string.error_opss, Toast.LENGTH_LONG).show();
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    private Response<JSONObject> volleyParseNetworkResponse(NetworkResponse response) {

        try {
            String jsonString = null;
            try {
                jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (response.headers.get("Authorization") != null) {
                UserInstance.getInstance().getAuth().setAuth_token(response.headers.get("Authorization"));
            }
            return Response.success(new JSONObject(jsonString), HttpHeaderParser.parseCacheHeaders(response));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    private boolean checkQueueServeTime() {

        return System.currentTimeMillis() > UserInstance.getInstance().getLastRequestTime() + delay;

    }

    private void addQueue(JsonRequest jsonRequest) {

        UserInstance.getInstance().getQueue().add(jsonRequest);
        UserInstance.getInstance().setLastRequestTime(System.currentTimeMillis());
    }
}
