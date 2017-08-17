package com.driverapp.Controller;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.driverapp.Model.Auth;
import com.driverapp.Model.Bus;
import com.driverapp.Model.BusStop;
import com.driverapp.Model.Route;
import com.driverapp.Model.UserInstance;
import com.driverapp.R;
import com.driverapp.View.JourneyActivity;
import com.driverapp.View.LoginActivity;
import com.driverapp.View.SearchFragment;
import com.driverapp.View.SetupFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class VolleyApp {

    private static final String ROUTE_ID = "route_id";
    private static final String REGISTERED_BUS_ID = "registered_bus_id";
    private static final String DEVICE_ID = "device_id";
    private static final String DRIVER_ID = "driver_id";
    private static final String LONGITUDE = "bl_long";
    private static final String LATITUDE = "bl_lat";
    private static final String BUS_ID = "bus_id";
    private static final String ACTIVE = "active";
    private static final String BUS_LOCATION_DOES_NOT_EXIST = "Bus_Location does not exist.";
    private static final String BUS_ROUTE = "route";
    private static final String BUS_POSITION = "position";
    private static final String BUS_PLATE = "plate";
    private static final String BUS_DRIVER = "driver";
    private static final String BUS_NEXT_BUS_STOP = "next_bus_stop";
    private int delay = 1200;

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
                        UserInstance.getInstance().getAuth().saveAuth(username, password);
                        UserInstance.getInstance().getDriver().saveDriverInfo(username, password);
                        Intent intent = new Intent(context, JourneyActivity.class);
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

        if (!checkQueueServeTime()){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (loginActivity.getApplication() != null){
                        addQueue(jsonRequest);
                    }
                }
            }, delay);

        } else {
            addQueue(jsonRequest);
        }
    }

    public void getBusList(final String url, final View view, final Context context, final SearchFragment searchFragment) {

        String api = url + "?limit=all&token=" + UserInstance.getInstance().getAuth().getAuth_token();

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, api,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        List<Bus> busList= new LinkedList<>();

                        try {
                            JSONArray resultArray = response.getJSONArray("registered_bus");

                            if (resultArray.length() != 0) {
                                for (int i = 0; i < resultArray.length(); i++) {
                                    JSONObject json = resultArray.getJSONObject(i);
                                    Bus bus = new Bus(String.valueOf(json.getInt("bus_id")),String.valueOf(json.getString("plate_number")),String.valueOf(json.getString("bus_name")));
                                    busList.add(bus);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        searchFragment.populateBus(busList);
                        view.setVisibility(View.GONE);
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
                    if (searchFragment.getActivity() != null){
                        addQueue(jsonRequest);
                    }
                }
            }, delay);

        } else {
            addQueue(jsonRequest);
        }
    }

    public void getRouteList(final String url, final View view, final Context context, final SearchFragment searchFragment) {

        String api = url + "?limit=all&token=" + UserInstance.getInstance().getAuth().getAuth_token();

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, api,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        List<Route> routeList = new LinkedList<>();
                        Log.e("RouteListResponse", response.toString());
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

                        searchFragment.populateRoute(routeList);
                        view.setVisibility(View.GONE);
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
                    if (searchFragment.getActivity() != null){
                        addQueue(jsonRequest);
                    }
                }
            }, delay);

        } else {
            addQueue(jsonRequest);
        }
    }

    public void setupDriver(final String url, final Context context, final TextView statusText, final SetupFragment setupFragment) {

        String api = url + "?token=" + UserInstance.getInstance().getAuth().getAuth_token();
        final String dummy = "505";
        //change rest api//
        int rand = randomInt();
        int rand2 = randomInt();
        String driverID = UserInstance.getInstance().getDriver().getDriver_id();
        String deviceID = getUniquePhoneIdentity();
        Map<String, String> params = new HashMap<>();
        params.put(DRIVER_ID, driverID+"");
        params.put(DEVICE_ID, deviceID+"");
        params.put(REGISTERED_BUS_ID, UserInstance.getInstance().getBus().getBusId());
        params.put(ROUTE_ID, UserInstance.getInstance().getRoute().getRouteId());
        params.put("plate_no", String.valueOf(UserInstance.getInstance().getBus().getBusPlate()));
        JSONObject parameters = new JSONObject(params);

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, api, parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        statusText.setText(R.string.success);
                        statusText.setTextColor(ContextCompat.getColor(context, R.color.green));
                        statusText.setVisibility(View.VISIBLE);
                        setupFragment.nextStep(statusText);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        setupFragment.failClose(statusText);

                        volleyErrorResponse(error, context);

                    }
                }){

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response){

                return volleyParseNetworkResponse(response);
            }

        };

        if (!checkQueueServeTime()){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (setupFragment.getActivity() != null){
                        addQueue(jsonRequest);
                    }
                }
            }, delay);

        } else {
            addQueue(jsonRequest);
        }
    }

    private int randomInt() {
        Random random = new Random();
        int rand  = random.nextInt(19999);
        return rand;
    }

    public void checkingBus(final String url, final Context context, final TextView statusText, final SetupFragment setupFragment) {

        String api = url + "/" + UserInstance.getInstance().getBus().getBusId() +"/?token=" + UserInstance.getInstance().getAuth().getAuth_token();

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, api,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        statusText.setText(R.string.success);
                        statusText.setTextColor(ContextCompat.getColor(context, R.color.green));
                        statusText.setVisibility(View.VISIBLE);
                        setupFragment.nextStep(statusText);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        try {
                            if(error.networkResponse.data!=null) {
                                try {
                                    String errorRespond = new String(error.networkResponse.data,"UTF-8");

                                    if (errorRespond.contains(BUS_LOCATION_DOES_NOT_EXIST)){
                                        volleyParseNetworkResponse(error.networkResponse);
                                        UserInstance.getInstance().getVolleyApp().setBus(context.getResources().getString(R.string.url_bus_location),context,false,statusText,setupFragment);
                                    } else {
                                        volleyErrorResponse(error, context);
                                        setupFragment.failClose(statusText);
                                    }
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }){

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response){

                return volleyParseNetworkResponse(response);
            }

        };

        if (!checkQueueServeTime()){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (setupFragment.getActivity() != null){
                        addQueue(jsonRequest);
                    }
                }
            }, delay);

        } else {
            addQueue(jsonRequest);
        }
    }

    private void setBus(final String url, final Context context, boolean status, final TextView statusText, final SetupFragment setupFragment) {

        String api = url + "?token=" + UserInstance.getInstance().getAuth().getAuth_token();

        Map<String, String> params = new HashMap<>();
        params.put(BUS_ID, UserInstance.getInstance().getBus().getBusId());
        params.put(ACTIVE, String.valueOf(statusValue(status)));
        params.put(LATITUDE, "0");
        params.put(LONGITUDE, "0");
        params.put(BUS_ROUTE,UserInstance.getInstance().getRoute().getRouteId());
        params.put(BUS_POSITION, String.valueOf(UserInstance.getInstance().getBusLocation()));
        params.put(BUS_PLATE, String.valueOf(UserInstance.getInstance().getBus().getBusPlate()));
        params.put(BUS_DRIVER, String.valueOf(UserInstance.getInstance().getDriver().getDriver_id()));
        params.put(BUS_NEXT_BUS_STOP, String.valueOf(UserInstance.getInstance().getRoute().getBusStopList().get(1).getName()));

        JSONObject parameters = new JSONObject(params);


        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, api, parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        statusText.setText(R.string.success);
                        statusText.setTextColor(ContextCompat.getColor(context, R.color.green));
                        statusText.setVisibility(View.VISIBLE);
                        setupFragment.nextStep(statusText);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        volleyErrorResponse(error, context);
                        setupFragment.failClose(statusText);
                    }
                }){

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response){

                return volleyParseNetworkResponse(response);
            }

        };

        if (!checkQueueServeTime()){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (setupFragment.getActivity() != null){
                        addQueue(jsonRequest);
                    }
                }
            }, delay);

        } else {
            addQueue(jsonRequest);
        }
    }

    public void setStatusBus(final String url, final Context context, final boolean status, final double lat, final double lon) {

        String api = url + "?token=" + UserInstance.getInstance().getAuth().getAuth_token();

        Map<String, String> params = new HashMap<>();
        params.put(BUS_ID, UserInstance.getInstance().getBus().getBusId());
        params.put(ACTIVE, String.valueOf(statusValue(status)));
        params.put(LATITUDE, String.valueOf(lat));
        params.put(LONGITUDE, String.valueOf(lon));
        params.put(BUS_ROUTE,UserInstance.getInstance().getRoute().getRouteId());
        params.put(BUS_POSITION, String.valueOf(UserInstance.getInstance().getBusLocation()));
        params.put(BUS_PLATE, String.valueOf(UserInstance.getInstance().getBus().getBusPlate()));
        params.put(BUS_DRIVER, String.valueOf(UserInstance.getInstance().getDriver().getDriver_id()));
        params.put(BUS_NEXT_BUS_STOP, String.valueOf(UserInstance.getInstance().getRoute().getBusStopList().get(1).getName()));
        JSONObject parameters = new JSONObject(params);


        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, api, parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context, ""+response, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        setStatusBus(url,context,status,lat,lon);
                        volleyErrorResponse(error, context);
                    }
                }){

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response){

                return volleyParseNetworkResponse(response);
            }

        };

        if (!checkQueueServeTime()){
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

    public void getNextBusStop(final Context context, final TextView statusText, final SetupFragment setupFragment) {

        final int nextBusStopIndex = UserInstance.getInstance().getBusLocation() + 1;
        Log.e("apiIndex", "" +nextBusStopIndex);
        String api = context.getResources().getString(R.string.url_bus_stop) + "/" + UserInstance.getInstance().getRoute().getBusStopList().get(nextBusStopIndex).getName()+"/?token=" + UserInstance.getInstance().getAuth().getAuth_token();
        Log.e("api", api);
        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, api,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        UserInstance.getInstance().setBusLocation(nextBusStopIndex);

                        try {
                            JSONObject jsonObject = response.getJSONObject("station");
                            double lat = Double.parseDouble(jsonObject.getString("lat"));
                            double lon = Double.parseDouble(jsonObject.getString("lon"));
                            UserInstance.getInstance().getRoute().getBusStopList().get(nextBusStopIndex).setLat(lat);
                            UserInstance.getInstance().getRoute().getBusStopList().get(nextBusStopIndex).setLon(lon);
                            setupFragment.nextBusStopLabel(UserInstance.getInstance().getRoute().getBusStopList().get(nextBusStopIndex).getName());
                            setupFragment.nextStep(statusText);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        volleyErrorResponse(error, context);
                        setupFragment.failClose(statusText);
                    }
                }){

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response){

                return volleyParseNetworkResponse(response);
            }
        };

        if (!checkQueueServeTime()){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (setupFragment.getActivity() != null){
                        addQueue(jsonRequest);
                    }
                }
            }, delay);

        } else {
            addQueue(jsonRequest);
        }
    }

    public void getAllBusStopJourney(final Context context, final int nextBusStopIndex) {

        String api = context.getResources().getString(R.string.url_bus_stop) + "/" + UserInstance.getInstance().getRoute().getBusStopList().get(nextBusStopIndex).getName()+"/?token=" + UserInstance.getInstance().getAuth().getAuth_token();


        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, api,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONObject jsonObject = response.getJSONObject("station");
                            double lat = Double.parseDouble(jsonObject.getString("lat"));
                            double lon = Double.parseDouble(jsonObject.getString("lon"));
                            UserInstance.getInstance().getRoute().getBusStopList().get(nextBusStopIndex).setLat(lat);
                            UserInstance.getInstance().getRoute().getBusStopList().get(nextBusStopIndex).setLon(lon);
                            if (nextBusStopIndex+1 < UserInstance.getInstance().getRoute().getBusStopList().size()){
                                getAllBusStopJourney(context, nextBusStopIndex+1);
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
                        getAllBusStopJourney(context, nextBusStopIndex);

                    }
                }){

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response){

                return volleyParseNetworkResponse(response);
            }

        };

        if (!checkQueueServeTime()){
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

    public void submitAlert(final String url, final String subject,final String message, final String report_type, final int reporter_id, final Context context) {

        //String api = url + "?token=" + UserInstance.getInstance().getAuth().getAuth_token();
        String api = url ;

        Map<String, String> params = new HashMap<>();
        params.put("subject",subject);
        params.put("message", message);
        params.put("report_type",report_type );
        params.put("reporter_id", String.valueOf(reporter_id));
       
        JSONObject parameters = new JSONObject(params);


        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, api, parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(context, "Success " + response.getString("status"), Toast.LENGTH_SHORT).show();
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
                }){


            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response){

                return volleyParseNetworkResponse(response);
            }

        };

        if (!checkQueueServeTime()){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (context != null){
                        addQueue(jsonRequest);
                    }
                }
            }, delay);

        } else {
            addQueue(jsonRequest);
        }
    }


    public void trackBus(final String url, final Context context, double lat, double lon) {

        String api = url + "?token=" + UserInstance.getInstance().getAuth().getAuth_token();

        Map<String, String> params = new HashMap<>();
        params.put("bus_id", UserInstance.getInstance().getBus().getBusId());
        params.put("created", "2016-11-09 00:00:00");
        params.put("bll_lat", String.valueOf(lat));
        params.put("bll_long", String.valueOf(lon));
        params.put("route",UserInstance.getInstance().getRoute().getRouteId());
        params.put("position", String.valueOf(UserInstance.getInstance().getBusLocation()));
        JSONObject parameters = new JSONObject(params);


        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, api, parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context, ""+response, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        volleyErrorResponse(error, context);
                    }
                }){

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response){

                return volleyParseNetworkResponse(response);
            }

        };

        if (!checkQueueServeTime()){
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

    private void volleyErrorResponse(VolleyError error, Context context){

        try {
            if (error.networkResponse != null){
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
                newToken(context.getResources().getString(R.string.url_login),context);
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

    private Response<JSONObject> volleyParseNetworkResponse(NetworkResponse response){

        try {
            String jsonString = null;
            try {
                jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (response.headers.get("Authorization") != null){
                UserInstance.getInstance().getAuth().setAuth_token(response.headers.get("Authorization"));
            }
            return Response.success(new JSONObject(jsonString),HttpHeaderParser.parseCacheHeaders(response));
        }catch (JSONException je) {
            return Response.error(new ParseError(je));
        }

    }

    private boolean checkQueueServeTime(){

        return System.currentTimeMillis() > UserInstance.getInstance().getLastRequestTime()+ delay ;

    }

    private void addQueue(JsonRequest jsonRequest){

        UserInstance.getInstance().getQueue().add(jsonRequest);
        UserInstance.getInstance().setLastRequestTime(System.currentTimeMillis());
    }

    private void newToken(String Url, final Context context) {

        JSONObject parameters = new JSONObject(UserInstance.getInstance().getAuth().getCredential());

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, Url, parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        UserInstance.getInstance().getAuth().setAuth_token(response.optString("token"));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        volleyErrorResponse(error, context);
                    }
                });

        if (!checkQueueServeTime()){
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

    private int statusValue(boolean status){
        return (status) ? 1 : 0;
    }

    public String getUniquePhoneIdentity() {
        String m_szDevIDShort = "35" + (Build.BOARD.length() % 10) + (Build.BRAND.length() % 10) + (Build.CPU_ABI.length() % 10) + (Build.DEVICE.length() % 10) + (Build.MANUFACTURER.length() % 10) + (Build.MODEL.length() % 10) + (Build.PRODUCT.length() % 10);

        String serial = null;
        try {
            serial = android.os.Build.class.getField("SERIAL").get(null).toString();

            // Go ahead and return the serial for api => 9
            return "android-" + new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception exception) {
            // String needs to be initialized
            serial = "serial"; // some value
        }
        return "android-" + new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }
}
