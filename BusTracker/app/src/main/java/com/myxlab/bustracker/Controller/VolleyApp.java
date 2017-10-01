package com.myxlab.bustracker.Controller;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v4.util.LruCache;
import android.util.Log;
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
import com.myxlab.bustracker.Model.AlertsData;
import com.myxlab.bustracker.Model.Auth;
import com.myxlab.bustracker.Model.Bus;
import com.myxlab.bustracker.Model.BusStop;
import com.myxlab.bustracker.Model.POI;
import com.myxlab.bustracker.Model.Route;
import com.myxlab.bustracker.Model.UserInstance;
import com.myxlab.bustracker.Model.maps.Helper;
import com.myxlab.bustracker.R;
import com.myxlab.bustracker.View.AlertsFragment;
import com.myxlab.bustracker.View.Login.Login_Fragment;
import com.myxlab.bustracker.View.Login.MainLoginActivity;
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

/**
 * The type Volley app.
 */
public class VolleyApp {
    private VolleyApp volleyApp;
    private Context context;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
    private int delay = 1200;

    /**
     * Instantiates a new Volley app.
     */
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

    /**
     * Gets instance.
     *
     * @param context the context
     * @return the instance
     */
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

    /**
     * Gets image loader.
     *
     * @return the image loader
     */
    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    /**
     * User registration task.
     *
     * @param Url           the url
     * @param username      the username
     * @param password      the password
     * @param name          the name
     * @param email         the email
     * @param context       the context
     * @param view          the view
     * @param loginActivity the login activity
     */
    public void UserRegistrationTask(final String Url, final String username, final String password, final String name, final String email, final Context context, final View view, final MainLoginActivity loginActivity) {

        Map<String, String> params = new HashMap<>();
        params.put("U_Name", name);
        params.put("U_Username", username);
        params.put("U_Password", password);
        params.put("U_Email", email);
        params.put("U_Created", "2016-10-12 00:00:00");
        params.put("U_Active", "1");
        params.put("U_Last_Login", "2016-10-12 00:00:00");
        params.put("U_Last_Password", "");
        params.put("U_Address", "");
        params.put("U_Phone", "");
        params.put("U_Occup", "");
        params.put("U_Device_Id", "");
        params.put("U_Last_Latitud", "");
        params.put("U_Last_Longitud", "");
        params.put("U_Last_Origin", "");
        params.put("U_Last_Destination", "");
        params.put("U_Update", "2016-10-12 00:00:00");
        params.put("U_Updated_By", "1");
        params.put("U_Point", "1");
        params.put("U_Refer_Id", "1");
        params.put("U_In_Journey", "1");
        params.put("U_Fav_Origin", "");
        params.put("U_Fav_Destination", "");
        params.put("U_Gender", "1");
        params.put("U_Nationality", "");
        params.put("U_Avatar", "");

        JSONObject parameters = new JSONObject(params);

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, Url, parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                /*      UserInstance.getInstance().getAuth().setAuth_token(response.optString("token"));
                        UserInstance.getInstance().getAuth().saveAuth(context, username, password);
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        UserInstance.getInstance().getVolleyApp().updatePOIDB(loginActivity.getString(R.string.url_poi_list), loginActivity.getApplicationContext());
                        UserInstance.getInstance().getVolleyApp().updateBSDB(loginActivity.getString(R.string.url_bus_stop_list), loginActivity.getApplicationContext() );*/
                        Log.e("Res",response.toString());
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

    /**
     * User login task.
     *
     * @param Url            the url
     * @param username       the username
     * @param password       the password
     * @param context        the context
     * @param view           the view
     * @param loginActivity  the login activity
     * @param login_fragment the login fragment
     */
    public void UserLoginTask(final String Url, final String username, final String password, final Context context, final View view, final MainLoginActivity loginActivity, final Login_Fragment login_fragment) {

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

                        UserInstance.getInstance().getVolleyApp().updatePOIDB(loginActivity.getString(R.string.url_poi_list), loginActivity.getApplicationContext());
                        UserInstance.getInstance().getVolleyApp().updateBSDB(loginActivity.getString(R.string.url_bus_stop_list), loginActivity.getApplicationContext() );


                        loginActivity.finish();
                        context.startActivity(intent);
                        view.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        view.setVisibility(View.GONE);
                        if (error instanceof AuthFailureError) {
                            login_fragment.createCustomToast("Invalid Username and Password");
                        }
                        //volleyErrorResponse(error, context);
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

/*    public void UserLoginTask(final String Url, final String username, final String password, final Context context, final View view, final MainLoginActivity loginActivity) {

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

                        UserInstance.getInstance().getVolleyApp().updatePOIDB(loginActivity.getString(R.string.url_poi_list), loginActivity.getApplicationContext());
                        UserInstance.getInstance().getVolleyApp().updateBSDB(loginActivity.getString(R.string.url_bus_stop_list), loginActivity.getApplicationContext() );


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
    }*/


    /**
     * Auto login.
     *
     * @param Url            the url
     * @param context        the context
     * @param splashActivity the splash activity
     */
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

    /**
     * Gets bus stop.
     *
     * @param url          the url
     * @param context      the context
     * @param mapsFragment the maps fragment
     */
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
                                    String code = String.valueOf(json.getString("code"));
                                    String latitude = json.getString("lat");
                                    String longitude = json.getString("lon");
                                    Double lat;
                                    Double lon;
                                    if (latitude.equals("null") && longitude.equals("null")){
                                        lat = 2.998489;
                                        lon = 101.712087;
                                    }else{
                                        lat = Double.parseDouble(latitude);
                                        lon = Double.parseDouble(longitude);
                                    }

                                    List<String> busItem = new LinkedList<>();
                                    /*if (json.getString("buses") != null) {
                                        String buses = String.valueOf(json.getString("buses"));
                                        busItem.add(buses);
                                    }else{
                                        String buses = "N/A";
                                        busItem.add(buses);

                                    }*/

                                    if (json.getJSONArray("buses") != null){
                                        JSONArray bus = json.getJSONArray("buses");
                                        for (int j = 0; j < bus.length(); j++) {
                                            busItem.add(bus.getString(j));
                                        }
                                    } else {
                                        busItem.add("N/A");
                                    }


                                    BusStop busStop = new BusStop(name, code, lat, lon, busItem);
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

    /**
     * Gets eta.
     *
     * @param url          the url
     * @param busStop      the bus stop
     * @param bus          the bus
     * @param mainActivity the main activity
     * @param busLat       the bus lat
     * @param busLon       the bus lon
     */
    public void getETA(String url, final String busStop, final String bus, final MainActivity mainActivity, final Double busLat, final Double busLon) {

        Map<String, String> params = new HashMap<>();
        params.put(BUS_STOP, busStop);
        params.put(BUS, bus);
        JSONObject parameters = new JSONObject(params);
       // Log.e("getETAParams",bus +"/"+ busStop);
        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                 //       Log.e("ResponseETA", response.toString());
                        Toast.makeText(mainActivity, response.toString() , Toast.LENGTH_SHORT).show();
                        String eta = null;
                        String etaTo = null;
                        Double lat = null;
                        Double lon = null;
                        String polyline = null;

                        try {
                            JSONObject obj = response.getJSONObject("results");

                            //JSONObject obj = resultArray.getJSONObject(0);
                            polyline = obj.optString("polyline");

                            eta = obj.getString("eta");
                            etaTo = "to " + busStop;
                            String latitude = obj.getString("lat");
                            String longitude = obj.getString("lon");
                            if (latitude.equals("null") && longitude.equals("null")){
                                lat = 2.998489;
                                lon = 101.712087;
                            }else{
                                lat = Double.valueOf(latitude);
                                lon = Double.valueOf(longitude);
                            }



                 //           Log.e("getETA",eta + " "+ etaTo +" "+ lat + " "+lon+ " ");

                        } catch (JSONException e) {
                            e.printStackTrace();
                //            Log.e("getETA",e.toString());
                        }

                        assert eta != null;
                        assert etaTo != null;
                        assert lat != null;
                        assert lon != null;

                        if (!eta.equals("Please wait for next Trip")) {
                            mainActivity.setETA(bus, eta, etaTo, lat, lon, polyline, true, busLat, busLon);

                        } else {
                            mainActivity.setETA("Next", eta, etaTo, lat, lon, polyline, false, busLat, busLon);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.e("getETA onErrorResponse",error.toString());
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


    /**
     * The constant KEY_POI.
     */
/*public void getPOIBusStops(String url, final String poi, final MainActivity mainActivity) {
        String api = url + "?token=" + UserInstance.getInstance().getAuth().getAuth_token();

        Map<String, String> params = new HashMap<>();
        params.put("poi", poi);
        JSONObject parameters = new JSONObject(params);

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, api, parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("VolleyAPP Response",response.toString());
                       *//* String eta = null;
                        String etaTo = null;
                        Double lat = null;
                        Double lon = null;

                        try {
                            JSONArray resultArray = response.getJSONArray("results");

                            JSONObject obj = resultArray.getJSONObject(0);

                            eta = obj.getString("eta");
                            etaTo = "to " + poi;
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
                        }*//*

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
*/
    public static final String KEY_POI = "poi";

    /**
     * Gets poi bus stops.
     *
     * @param url          the url
     * @param poi          the poi
     * @param mainActivity the main activity
     */
    public void getPoiBusStops(String url, final String poi, final MainActivity mainActivity) {
        String api = url + "?token=" + UserInstance.getInstance().getAuth().getAuth_token();
        Map<String, String> params = new HashMap<>();
        params.put(KEY_POI, poi);
        JSONObject parameters = new JSONObject(params);
        Log.e("url",api);
        Log.e("poi",poi);

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, api, parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

               //         Log.e("VolleyAPP Response",response.toString());
                        try {
                            JSONArray resultArray = response.getJSONArray("results");

                            List<String> busStops = new LinkedList<>();

                            for (int j = 0; j < resultArray.length(); j++) {
                                busStops.add(resultArray.getString(j));
                            }

                            mainActivity.setPOIBusStops(busStops);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                       /* assert eta != null;
                        if (!eta.equals("Please wait for next Trip")) {
                            mainActivity.setETA(eta, etaTo, lat, lon, true);

                        } else {
                            mainActivity.setETA("Next", eta, lat, lon, false);
                        }*/

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VolleyAPP ERR",error.toString());
                    }
                });

        if (!checkQueueServeTime()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mainActivity.getApplication() != null) {
                        addQueue(jsonRequest);
                    }
                }
            }, delay);

        } else {
            addQueue(jsonRequest);
        }
    }

    /**
     * Gets buses.
     *
     * @param url          the url
     * @param context      the context
     * @param mapsFragment the maps fragment
     */
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
                                    String currentBusStop = String.valueOf(json.getString("currentbusstop"));

                                    Bus bus= new Bus(name, lat, lon, plate,currentBusStop);
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


    /**
     * Update poidb.
     *
     * @param url     the url
     * @param context the context
     */
    public void updatePOIDB(String url, final Context context) {

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
                                        String phone = String.valueOf(json.getString("phone"));
                                        String email =String.valueOf(json.getString("email"));
                                        String website = String.valueOf(json.getString("website"));

                                        if (phone!= null){
                                            poi.setPhone(phone);
                                        }else{
                                            poi.setPhone("999");
                                        }
                                        if (email!= null){
                                            poi.setEmail(email);
                                        }else{
                                            poi.setEmail("abc");
                                        }
                                        if (website!= null){
                                            poi.setWebsite(website);
                                        }else{
                                            poi.setWebsite("zxc");
                                        }



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

    /**
     * Update bsdb.
     *
     * @param url     the url
     * @param context the context
     */
    public void updateBSDB(String url, final Context context) {

        String api = url + "?limit=all&token=" + UserInstance.getInstance().getAuth().getAuth_token();

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, api,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray resultArray = response.getJSONArray("station");

                            if (resultArray.length() != 0) {
                                DBHandler dbHandler = new DBHandler(context, null);
                                dbHandler.delAllBusStopsData();

                                for (int i = 0; i < resultArray.length(); i++) {
                                    JSONObject json;
                                    BusStop bs;

                                    try {
                                        json = resultArray.getJSONObject(i);
                                        bs = new BusStop();
                                        bs.setName(String.valueOf(json.getString("name")));
                                        bs.setLat(Double.valueOf((json.getString("lat"))));
                                        bs.setLon(Double.valueOf(json.getString("lon")));
                                        bs.setCode(String.valueOf(json.getString("code")));
                                        dbHandler.addBusStop(bs);
                                        //Log.e("json", json.toString());
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
                Log.e("server_error", "VolleyApp.java");
              //  Toast.makeText(context, R.string.error_server, Toast.LENGTH_LONG).show();
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


    /**
     * Gets alerts data.
     *
     * @param url            the url
     * @param context        the context
     * @param alertsFragment the alerts fragment
     */
    public void getAlertsData(String url, final Context context, final AlertsFragment alertsFragment) {
        String api = url + "?limit=all&token=" + UserInstance.getInstance().getAuth().getAuth_token();

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, api,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        try {
                            JSONArray resultArray = response.getJSONArray("report");

                            List<AlertsData> alertsDatas = new LinkedList<>();

                            for (int i = 0; i < resultArray.length(); i++) {

                                try {
                                    JSONObject json = resultArray.getJSONObject(i);

                                    String id = String.valueOf(json.getInt("id"));
                                    String reporter_id = String.valueOf(json.getInt("reporter_id"));
                                    String subject = String.valueOf(json.getString("subject"));
                                    String message = String.valueOf(json.getString("message"));
                                    String type = String.valueOf(json.getString("type"));
                                    String created = String.valueOf(json.getString("created"));


                                    AlertsData alertsData = new AlertsData(subject,type,message,reporter_id,id,created);
                                    alertsDatas.add(alertsData);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            //UserInstance.getInstance().setAlertsDataList(alertsDatas);
                            alertsFragment.populateData(alertsDatas);

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




    private boolean checkQueueServeTime() {

        return System.currentTimeMillis() > UserInstance.getInstance().getLastRequestTime() + delay;

    }

    private void addQueue(JsonRequest jsonRequest) {

        UserInstance.getInstance().getQueue().add(jsonRequest);
        UserInstance.getInstance().setLastRequestTime(System.currentTimeMillis());
    }

    /**
     * Get walking data.
     *
     * @param directionPath the direction path
     * @param mainActivity  the main activity
     */
    public void getWalkingData(String directionPath, final MainActivity mainActivity){
        String api = directionPath;
        final String[] distance = {""};
        final String[] duration = {""};
        final String[] polyline = {""};
        Log.e("DirectionPath",directionPath);
        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, api,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {



                        try {
                            JSONArray resultArray = response.getJSONArray("routes");

                            for (int i = 0; i < resultArray.length(); i++) {

                                try {
                                    JSONObject jsonRoute = resultArray.getJSONObject(i);


                                    JSONArray legs = jsonRoute.getJSONArray("legs");
                              //      Log.e("ResponseDirectionAPI",legs.toString());
                                    for (int j = 0; j < legs.length(); j++) {
                                        JSONObject jsonLegs = legs.getJSONObject(i);
                                        distance[0] = jsonLegs.getJSONObject("distance").getString("text");
                                        duration[0] = jsonLegs.getJSONObject("duration").getString("text");
                                    }

                                   polyline[0] = jsonRoute.getJSONObject("overview_polyline").getString("points");

                                    mainActivity.show_getWalk(distance[0], duration[0], polyline[0]);
                                    Log.e("ASD",distance[0] +"//"+duration[0]+"//"+ polyline[0]);
                                } catch (JSONException e) {
                                    e.printStackTrace();
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

    /**
     * Gets route list.
     *
     * @param url          the url
     * @param context      the context
     * @param mapsFragment the maps fragment
     */
    public void getRouteList(final String url, final Context context, final MapsFragment mapsFragment) {

        String api = url + "?limit=all&token=" + UserInstance.getInstance().getAuth().getAuth_token();

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, api,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        List<Route> routeList = new LinkedList<>();
                    //    Log.e("RouteListResponse", response.toString());
                        try {
                            JSONArray resultArray = response.getJSONArray("route");

                            if (resultArray.length() != 0) {
                                for (int i = 0; i < resultArray.length(); i++) {
                                    JSONObject json = resultArray.getJSONObject(i);

                                    List<BusStop> busStopList = new LinkedList<>();

                                    for (int j = 0; j < json.getJSONArray("route").length(); j ++){
                                        BusStop busStop = new BusStop(0,json.getJSONArray("route").getString(j),0.00,0.00);
                                        busStopList.add(busStop);
                                    }

                                    Route route = new Route(String.valueOf(json.get("id")),String.valueOf(json.getString("name")), busStopList);
                                    routeList.add(route);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (routeList!=null){
                            UserInstance.getInstance().setRouteList(routeList);
                        }
                        //searchFragment.populateRoute(routeList);
                        //view.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        volleyErrorResponse(error, context);
                    }
                }) {

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response){
                return volleyParseNetworkResponse(response);
            }
        };

        if (!checkQueueServeTime()){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mapsFragment.getActivity() != null){
                        addQueue(jsonRequest);
                    }
                }
            }, delay);
        } else {
            addQueue(jsonRequest);
        }
    }


}
