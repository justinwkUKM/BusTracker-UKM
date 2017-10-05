package com.myxlab.bustracker.View;


import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.SphericalUtil;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.myxlab.bustracker.FontChangeCrawler;
import com.myxlab.bustracker.Model.Bus;
import com.myxlab.bustracker.Model.BusStop;
import com.myxlab.bustracker.Model.UserInstance;
import com.myxlab.bustracker.Model.maps.MyClusterItem;
import com.myxlab.bustracker.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import me.toptas.fancyshowcase.FancyShowCaseView;
import me.toptas.fancyshowcase.FocusShape;

import static android.content.Context.LOCATION_SERVICE;

/**
 * The type Maps fragment.
 */
public class MapsFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerClickListener, LocationListener {

    private GoogleApiClient mGoogleApiClient;
    /**
     * The current location.
     */
    public Location mCurrentLocation;
    private CameraPosition position;
    /**
     * The Map.
     */
    public GoogleMap map;
    private Context context;
    /**
     * The Duration loop for bus.
     */
    static long duration = 5000;
    private List<Bus> bus = new ArrayList<>();
    /**
     * The Hash map for all markers.
     */
    public HashMap<Marker, String> hashMapMarker;

    public HashMap<String, String> hashMapTitle;
    private HashMap<Marker, Integer> hashMapBusStopListPosition;
    private HashMap<BusStop, Marker> hashMapBusStopMarker;
    /**
     * The Hash map bus used for looping.
     */
    public HashMap<Marker, Bus> hashMapBus;
    private LocationManager locationManager;
    private Marker greenMarker;
    /**
     * The Maps fragment.
     */
    MapsFragment mapsFragment;
    /**
     * The Buses marker.
     */
    List<Marker> busesMarker, /**
     * The Start end marker.
     */
    startEndMarker;
    /**
     * The Is nearest.
     */
    Boolean isNearest = false;

    /**
     * Instantiates a new Maps fragment.
     */
    public MapsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        FontChangeCrawler fontChanger = new FontChangeCrawler(getActivity().getAssets(), "fonts/sensation.ttf");
        fontChanger.replaceFonts((ViewGroup) this.getView());

        context = getActivity();
        mapsFragment = this;
        hashMapBusStopMarker = new HashMap<>();
        hashMapMarker = new HashMap<>();
        hashMapBusStopListPosition = new HashMap<>();
        hashMapTitle = new HashMap<>();
        hashMapBus = new HashMap<>();
        locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        greenMarker = null;
        busesMarker = new LinkedList<>();
        startEndMarker = new LinkedList<>();
        ((MainActivity) getActivity()).mapsFragment = this;

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }



    @Override
    public void onMapReady(GoogleMap maps) {
        map = maps;
        map.setOnMarkerClickListener(this);
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        map.setMyLocationEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(false);
        map.setTrafficEnabled(false);
        map.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, R.raw.mapstyle));

        UserInstance.getInstance().getVolleyApp().getRouteList(getString(R.string.url_route),context, this);
        UserInstance.getInstance().getVolleyApp().getBusStop(getString(R.string.url_bus_stop_list), getActivity(), this);




        /*       map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {

                Log.i("centerLat",cameraPosition.target.latitude+"");

                Log.i("centerLong",cameraPosition.target.longitude+"");
            }
        });*/
    }

    /**
     * Gets data.
     */
    public void getData() {



        busStopGreenIndex=0;
        clearAllMapMarkers();

        onResume();
        onMapReady(map);
        if (greenMarker!=null)
            greenMarker=null;
       setBus();
    }

    /**
     * Alternative to myMap.clear() to avoid undesired exceptions
     */
    private void clearAllMapMarkers() {
        // Clearing the current map markers being shown
        // Note that we do not use myMap.clear() because that incur in the exception
        // "java.lang.IllegalArgumentException: Released unknown bitmap reference"
        try {
            for (Map.Entry<BusStop, Marker> markerEntry : hashMapBusStopMarker.entrySet())
                markerEntry.getValue().remove();
        } catch (IllegalArgumentException e) {
            // Manage here the exception (never raised but who knows...)
        }
    }


    private void initCamera(Location location) {
        if (location != null) {
            position = CameraPosition.builder()
                    .target(new LatLng(location.getLatitude(),
                            location.getLongitude()))
                    .zoom(16f)
                    .bearing(0.0f)
                    .tilt(0.0f)
                    .build();
        } else {
            Toast.makeText(context, "Please check your GPS", Toast.LENGTH_SHORT).show();
            position = CameraPosition.builder()
                    .target(new LatLng(2.930107,
                            101.777434))
                    .zoom(16f)
                    .bearing(0.0f)
                    .tilt(0.0f)
                    .build();
        }

        map.animateCamera(CameraUpdateFactory.newCameraPosition(position), null);

    }

    /**
     * Map camera.
     */
    public void mapCamera() {
        if (mCurrentLocation != null) {
            position = CameraPosition.builder()
                    .target(new LatLng(mCurrentLocation.getLatitude(),
                            mCurrentLocation.getLongitude()))
                    .zoom(16f)
                    .bearing(0.0f)
                    .tilt(0.0f)
                    .build();
            map.animateCamera(CameraUpdateFactory.newCameraPosition(position), null);
        }else
        {
            Log.e("Location", "Null");
        }
    }

    /**
     * Focus camera.
     *
     * @param latLng the lat lng
     */
    public void focusCamera(LatLng latLng) {
        position = CameraPosition.builder()
                .target(new LatLng(latLng.latitude,
                        latLng.longitude))
                .zoom(16f)
                .bearing(0.0f)
                .tilt(0.0f)
                .build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(position), null);
    }

    /*This method for add/remove bus marker base on current bus list*/
    public void setBus() {
        /*Checking Bus List is NOT empty*/
        if (!UserInstance.getInstance().getBuses().isEmpty()) {
            /*Checking Bus Marker is empty*/
            if (busesMarker.isEmpty()) {
                /*Loop the bus list*/
                for (int i = 0; UserInstance.getInstance().getBuses().size() > i; i++) {
                    /*String for bus maker title*/
                    String title =  UserInstance.getInstance().getBuses().get(i).getPlate();
                    /*Set bus marker*/
                    Marker busMarker = map.addMarker(busMarkerOptions(UserInstance.getInstance().getBuses().get(i).getName(),title, new LatLng(UserInstance.getInstance().getBuses().get(i).getLat(), UserInstance.getInstance().getBuses().get(i).getLon())));
                    /*Add Bus Marker*/
                    busesMarker.add(busMarker);
                    /*Getting proper index for bus marker array to match bus list*/
                    int index = busesMarker.size() - 1;
                    /*Hash Map bus marker and bus list*/
                    hashMapBus.put(busesMarker.get(index), UserInstance.getInstance().getBuses().get(i));
                    /*Hash Map map marker to differentiate marker type*/
                    hashMapMarker.put(busesMarker.get(index), "Bus");
                }
                // iterate map using entryset in for loop
                for(Map.Entry<Marker, Bus> entry : hashMapBus.entrySet())
                {   //print keys and values
                    if (entry.getValue().getName().equals("Bus Zone 2")){
                    Log.e("V", entry.getKey().getTitle()+":"+entry.getValue().getPlate());
                    }
                }
            }
            /*If Bus Marker Not empty*/
            else {
                /*Compare Bus List and Bus Marker size is same*/
                if (UserInstance.getInstance().getBuses().size() == busesMarker.size()) {
                    Log.d("MAPSFRAGMENT", "/*Compare Bus List and Bus Marker size is same*/");

                    /*Loop Bus List animate current bus marker*/
                    for (int i = 0; i < UserInstance.getInstance().getBuses().size(); i++) {
                        /*Loop Bus Marker to get same Bus and Marker*/
                        for (int j = 0; j < busesMarker.size(); j++) {
                            /*Getting Bus Object using Hash Map*/
                            Bus bus = hashMapBus.get(busesMarker.get(j));
                            /*Compare Current Loop Bus List is same with Current Loop Bus Marker that converted to Bus Object*/
                            if (UserInstance.getInstance().getBuses().get(i).getPlate().equals(bus.getPlate())) {
                                /*Get distance before and after of same bus marker location*/
                                double distance = checkDistance(new LatLng(bus.getLat(), bus.getLon()), new LatLng(UserInstance.getInstance().getBuses().get(i).getLat(), UserInstance.getInstance().getBuses().get(i).getLon()));
                                /*Check if distance more than 10*/
                                if (distance >= 10) {
                                    /*Animate Marker to new Location that has been set in Bus List*/
                                    animateMarker(busesMarker.get(j), new LatLng(UserInstance.getInstance().getBuses().get(i).getLat(), UserInstance.getInstance().getBuses().get(i).getLon()), false);
                                }
                                ////////////////////////////////////////////////////////

                                ///////////////////////////////////////////////////////
                               /* String hashMapMarkervalues = String.valueOf(hashMapMarker.values());
                                Log.e("hashMapMarkervalues", hashMapMarkervalues);
                                String hashMapMarkerSize = String.valueOf(hashMapMarker.size());
                                Log.e("hashMapMarkerSize", hashMapMarkerSize);
                                String hashMapBusValues = String.valueOf(hashMapBus.values().iterator());
                                Log.e("hashMapBusValues", hashMapBusValues);
                                String hashMapBusSize = String.valueOf(hashMapBus.size());
                                Log.e("hashMapBusSize", hashMapBusSize);*/
                            }
                        }
                    }

                }
                /*If the Bus List is bigger than Bus Maker size*/
                else if ((UserInstance.getInstance().getBuses().size() > busesMarker.size())) {
                    Log.d("MAPSFRAGMENT", "/*If the Bus List is bigger than Bus Maker size*/");
                    /*New Link list of Bus*/ /*TODO this should be change and make it much simpler by removing the deactivate bus when we found it (check the 3rd TODO)*/
                    List<Integer> newBuses = new LinkedList<>();
                    /*Loop Bus List animate current bus marker*/
                    for (int i = 0; i < UserInstance.getInstance().getBuses().size(); i++) {
                        /*This boolean that tell us we found the marker we want to animate*/
                        boolean checker = false;
                        /*Loop Bus Marker to get same Bus and Marker*/
                        for (int j = 0; j < busesMarker.size(); j++) {
                            /*Getting Bus Object using Hash Map*/
                            Bus bus = hashMapBus.get(busesMarker.get(j));
                            /*Compare Current Loop Bus List is same with Current Loop Bus Marker that converted to Bus Object*/
                            if (UserInstance.getInstance().getBuses().get(i).getPlate().equals(bus.getPlate())) {
                                /*Get distance before and after of same bus marker location*/
                                double distance = checkDistance(new LatLng(bus.getLat(), bus.getLon()), new LatLng(UserInstance.getInstance().getBuses().get(i).getLat(), UserInstance.getInstance().getBuses().get(i).getLon()));
                                /*Check if distance more than 10*/
                                if (distance >= 10) {
                                    /*Animate Marker to new Location that has been set in Bus List*/
                                    animateMarker(busesMarker.get(j), new LatLng(UserInstance.getInstance().getBuses().get(i).getLat(), UserInstance.getInstance().getBuses().get(i).getLon()), false);
                                }
                                /*Set the boolean to true because we found the marker and bus*/
                                checker = true;
                            }
                            /*Check is this and of the Loop and if the boolean is false*/
                            if (!checker && j == busesMarker.size() - 1) {
                                /*Add new object in List*/ /*@TODO here is the flaw we should add the new bus using new function, animate it and make sure hash map it*/
                                newBuses.add(i);
                            }

                        }
                        /*Check the bus loop is at the end*/ /*@TODO need to check if this redundant because we already animated it and still not set new bus marker we just get. If this redundant it means we animate two time (Check The Above TODO)*/
                        if (i == UserInstance.getInstance().getBuses().size() - 1) {
                            /*Loop the new Link list*/
                            for (int j = 0; j < newBuses.size(); j++) {
                                /*String for bus maker title*/
                                String title = UserInstance.getInstance().getBuses().get(newBuses.get(j)).getName() + " (" + UserInstance.getInstance().getBuses().get(newBuses.get(j)).getPlate() + ")";
                                /*Set bus marker*/
                                Marker busMarker = map.addMarker(busMarkerOptions(UserInstance.getInstance().getBuses().get(j).getName(),title, new LatLng(UserInstance.getInstance().getBuses().get(newBuses.get(j)).getLat(), UserInstance.getInstance().getBuses().get(newBuses.get(j)).getLon())));
                                /*Add Bus Marker*/
                                busesMarker.add(busMarker);
                                /*Getting proper index for bus marker array to match bus list*/
                                int index = busesMarker.size() - 1;
                                /*Hash Map bus marker and bus list*/
                                hashMapBus.put(busesMarker.get(index), UserInstance.getInstance().getBuses().get(newBuses.get(j)));
                                /*Hash Map map marker to differentiate marker type*/
                                hashMapMarker.put(busesMarker.get(index), "Bus");
                            }
                        }
                    }
                }
                /*If the Bus List is less than Bus Marker size*/
                else {
                    Log.d("MAPSFRAGMENT", "/*If the Bus List is less than Bus Marker size*/");
                    /*New Link list of Bus*/
                    List<Integer> onBuses = new LinkedList<>();
                    /*Loop Bus List animate current bus marker*/
                    for (int i = 0; i < UserInstance.getInstance().getBuses().size(); i++) {
                        /*Loop Bus Marker to get same Bus and Marker*/
                        for (int j = 0; j < busesMarker.size(); j++) {
                            /*Getting Bus Object using Hash Map*/
                            Bus bus = hashMapBus.get(busesMarker.get(j));
                            if (UserInstance.getInstance().getBuses().get(i).getPlate().equals(bus.getPlate())) {
                                /*Get distance before and after of same bus marker location*/
                                double distance = checkDistance(new LatLng(bus.getLat(), bus.getLon()), new LatLng(UserInstance.getInstance().getBuses().get(i).getLat(), UserInstance.getInstance().getBuses().get(i).getLon()));
                                /*Check if distance more than 10*/
                                if (distance >= 10) {
                                    /*Animate Marker to new Location that has been set in Bus List*/
                                    animateMarker(busesMarker.get(j), new LatLng(UserInstance.getInstance().getBuses().get(i).getLat(), UserInstance.getInstance().getBuses().get(i).getLon()), false);
                                }
                                /*Add new object in List*/
                                onBuses.add(j);
                            }
                        }
                    }
                    /*New Link list for compare 'onBuses' for removing deactivate bus*/ /*TODO here we can create new method*/
                    List<Integer> tmpIndex = new LinkedList<>();
                    /*Loop bus list to populate 'tmpIndex'*/
                    for (int j = 0; j < busesMarker.size(); j++) {
                        /*Populating 'tmpIndex'*/
                        tmpIndex.add(j);
                    }
                    /*Loop 'tmpIndex'*/
                    for (Integer index : tmpIndex) {
                        /*Compare 'onBuses' with 'tmpIndex' is not equal*/
                        if (!onBuses.contains(index)) {
                            /*Remove deactivate bus*/
                            busesMarker.get(index).remove();
                            /*Remove deactivate bus*/ /*@TODO Seem Like redundant too here*/
                            busesMarker.remove(index);
                        }
                    }
                }

            }
        }
        /*Checking Bus List is empty*/
        else {
            /*Checking Bus Marker is not Empty*/
            if (!busesMarker.isEmpty()) {
                /*Loop Bus Marker */
                for (int i = 0; i < busesMarker.size(); i++) {
                    /*Remove current Marker*/
                    busesMarker.get(i).remove();
                }
                /*Clear Bus Marker*/
                busesMarker.clear();
            }
        }
    }



    private MarkerOptions busMarkerOptions(String name, String title, LatLng latLng) {
        MarkerOptions marker = new MarkerOptions().position(latLng).title(title);

        int markerIcon;

        switch (name) {
            case "Bus Zone 3U":
                markerIcon = R.drawable.ic_bus_3u;
                break;
            case "Bus Zone 2":
                markerIcon = R.drawable.ic_bus_2;
                break;
            case "Bus Zone 6":
                markerIcon = R.drawable.ic_bus_6;
                break;
            default:
                markerIcon = R.drawable.ic_directions;
                break;
        }

        marker.icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(markerIcon)));
        return marker;
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.e("OnConnected","Active");
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET}, 4);
                    return;
                }
                /*mCurrentLocation = LocationServices
                        .FusedLocationApi
                        .getLastLocation(mGoogleApiClient);
                initCamera(mCurrentLocation);
                loopBus();*/
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        mCurrentLocation = LocationServices
                .FusedLocationApi
                .getLastLocation(mGoogleApiClient);
        initCamera(mCurrentLocation);
        loopBus();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.e("Req Code", "" + requestCode);
        if (requestCode == 4) {
            if (grantResults.length >0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED ) {
                // Success Stuff here

            }
            else{
                // Failure Stuff
                Log.e("Failure Stuff here", "" + requestCode);
            }
        }

    }




    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        if (!(hashMapMarker != null && hashMapMarker.get(marker).equals("Bus"))) {

            switch (hashMapMarker.get(marker)) {
                case "Bus Stop":
                    Log.e("INDEX", "#Map" + (hashMapBusStopListPosition.get(marker))+ "    GrIndex"+busStopGreenIndex);
                    if (hashMapBusStopListPosition.get(marker)==busStopGreenIndex){
                       isNearest = true;
                    }else{
                        isNearest =false;
                    }

                    ((MainActivity) getActivity()).BusStopBottomSheetCall(hashMapBusStopListPosition.get(marker), isNearest );

                    break;
                case "building":
                    Log.e("INDEXXX", "#Map"+ marker.getTitle());
                    for(Map.Entry<Marker, String> entry : hashMapMarker.entrySet())
                    {   //print keys and values
                        if (entry.getValue().equals("building")){
                            Log.e("VXXX", entry.getKey().getTitle()+":"+entry.getValue().toString());
                        }
                    }



                default:
                    break;
            }

        } else {

            List<Bus> bus  = new LinkedList<>();
            bus = UserInstance.getInstance().getBuses();
            for (int i = 0; i < bus.size(); i++){
                String buss = bus.get(i).getPlate();
                String bussName = bus.get(i).getName();
                Log.e(bussName,buss);
            }

            String title = marker.getTitle();
            ((MainActivity) getActivity()).BusBottomSheetCall(title, marker);

        }

        return false;
    }

    /**
     * Animate marker.
     *
     * @param marker     the marker
     * @param toPosition the to position
     * @param hideMarker the hide marker
     */
    public void animateMarker(final Marker marker, final LatLng toPosition,
                              final boolean hideMarker) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = map.getProjection();
        Point startPoint = proj.toScreenLocation(marker.getPosition());
        final LatLng startLatLng = proj.fromScreenLocation(startPoint);

        final Interpolator interpolator = new LinearInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * toPosition.longitude + (1 - t)
                        * startLatLng.longitude;
                double lat = t * toPosition.latitude + (1 - t)
                        * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));

                if (t < 1.0) {
                    handler.postDelayed(this, 16);
                } else {
                    if (hideMarker) {
                        marker.setVisible(false);
                    } else {
                        marker.setVisible(true);
                    }
                }
            }
        });
    }

    /**
     * Check distance double.
     *
     * @param oldPos the old pos
     * @param newPos the new pos
     * @return the double
     */
    public static double checkDistance(LatLng oldPos, LatLng newPos) {

        return SphericalUtil.computeDistanceBetween(oldPos, newPos);
    }

    private void loopBus() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isAdded() && getActivity() != null) {
                    UserInstance.getInstance().getVolleyApp().getBuses(getString(R.string.url_bus), getActivity(), mapsFragment);
                    loopBus();
                }
            }
        }, duration);
    }

    /**
     * Sets bus stop marker.
     */
    public void setBusStopMarker() {

        hashMapBusStopListPosition.clear();

        for (int i = 0; i < UserInstance.getInstance().getBusStopList().size(); i++) {

            MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(UserInstance.getInstance().getBusStopList().get(i).getLat(), UserInstance.getInstance().getBusStopList().get(i).getLon()));
            markerOptions.title(UserInstance.getInstance().getBusStopList().get(i).getName());
            //markerOptions.icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.ic_bus_stops_red)));
            //markerOptions.icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("ic_busstop",10,10)));
            Bitmap smallStartMarker = Bitmap.createScaledBitmap(getMarkerBitmapFromView(R.drawable.ic_bus_stops_red),200, 200, false);
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallStartMarker));
            Marker marker = map.addMarker(markerOptions);

            hashMapMarker.put(marker, "Bus Stop");
            hashMapBusStopMarker.put(UserInstance.getInstance().getBusStopList().get(i), marker);
            hashMapBusStopListPosition.put(marker, i);
        }

        findBusStop();
    }

    /**
     * Resize map icons bitmap.
     *
     * @param iconName the icon name
     * @param width    the width
     * @param height   the height
     * @return the bitmap
     */
    public Bitmap resizeMapIcons(String iconName,int width, int height){
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(),getResources().getIdentifier(iconName, "drawable", context.getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;

    }

    /**
     * Find bus stop.
     */
    public void findBusStop() {
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 5, this);

        //setUpClusterer();
        checkBusStopDistance(mCurrentLocation);
    }


    /**
     * The Bus stop green index.
     */
    int busStopGreenIndex = 0;

    /**
     * Check bus stop distance.
     *
     * @param location the location
     */
    public void checkBusStopDistance(Location location) {

        mCurrentLocation = location;
        double distance = 0;
        if (mCurrentLocation != null){ LatLng myDistance = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());

            for (int i = 0; i < UserInstance.getInstance().getBusStopList().size(); i++) {

                LatLng latLng = new LatLng(UserInstance.getInstance().getBusStopList().get(i).getLat(), UserInstance.getInstance().getBusStopList().get(i).getLon());

                if (busStopGreenIndex != 0) {

                    double distanceTmp = SphericalUtil.computeDistanceBetween(latLng, myDistance);

                    if (distanceTmp < distance) {
                        distance = distanceTmp;
                        busStopGreenIndex = i;
                    }

                } else {
                    distance = SphericalUtil.computeDistanceBetween(latLng, myDistance);
                    busStopGreenIndex = i;
                }
            }

            if (greenMarker != null) {
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(getMarkerBitmapFromView(R.drawable.ic_bus_stops_red),200, 200, false);
                greenMarker.setIcon(BitmapDescriptorFactory.fromBitmap(scaledBitmap));
                greenMarker = hashMapBusStopMarker.get(UserInstance.getInstance().getBusStopList().get(busStopGreenIndex));
                Bitmap scaledGreenBitmap = Bitmap.createScaledBitmap(getMarkerBitmapFromView(R.drawable.ic_bus_stop_green), 256, 256, false);
                if (greenMarker != null) {
                    greenMarker.setIcon(BitmapDescriptorFactory.fromBitmap(scaledGreenBitmap));
                }
                //greenMarker.setIcon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.ic_bus_stop_green)));
            } else {
                greenMarker = hashMapBusStopMarker.get(UserInstance.getInstance().getBusStopList().get(busStopGreenIndex));
                Bitmap scaledGreenBitmap = Bitmap.createScaledBitmap(getMarkerBitmapFromView(R.drawable.ic_bus_stop_green),256, 256, false);
                if (greenMarker!=null){
                    greenMarker.setIcon(BitmapDescriptorFactory.fromBitmap(scaledGreenBitmap));
                }
            }

            if (busStopGreenIndex!=0){
                UserInstance.getInstance().setNearestBusStopIndex(busStopGreenIndex);
            }

            Log.e("nearestBusStop", UserInstance.getInstance().getBusStopList().get(busStopGreenIndex).getName());

        }else {
            Toast.makeText(context, "Location Not Available ", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        checkBusStopDistance(location);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    /**
     * Add marker.
     *
     * @param lat   the lat
     * @param lon   the lon
     * @param name  the name
     * @param type  the type
     * @param focus the focus
     */
    public void addMarker(Double lat, Double lon, String name, String type, Boolean focus) {

        MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(lat, lon));
        markerOptions.title(name);

        switch (type) {
            case "building":
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.ic_building)));
                break;
            case "faculty":
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.ic_faculty)));
                break;
            case "hall":
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.ic_account_balance)));
                break;
            case "health center":
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.ic_local_hospital)));
                break;
            case "hostel":
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.ic_hotel)));
                break;
            case "library":
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.ic_local_library)));
                break;
            case "recreation":
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.ic_local_florist)));
                break;
            case "start":
                Bitmap smallStartMarker = Bitmap.createScaledBitmap(getMarkerBitmapFromView(R.drawable.ic_start_polyline),48, 48, false);
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallStartMarker));
                break;
            case "end":
                Bitmap smallEndMarker = Bitmap.createScaledBitmap(getMarkerBitmapFromView(R.drawable.ic_end_polyline),48, 48, false);
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallEndMarker));
                break;
        }
        //map.clear();
        final Marker marker = map.addMarker(markerOptions.anchor(0.5f,0.5f));

        ValueAnimator ani = ValueAnimator.ofFloat(0, 1); //change for (0,1) if you want a fade in
        ani.setDuration(3000);
        ani.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                marker.setAlpha((float) animation.getAnimatedValue());
            }
        });
        ani.start();

        hashMapMarker.put(marker, type);
        for (Map.Entry<Marker, String> entry : hashMapMarker.entrySet()) {
            String key = entry.getKey().getTitle();
            String value = entry.getValue();
            Log.e("key:"+ key, "value:"+ value);
            if (value.equals("start")|| value.equals("end")){
                startEndMarker.add(entry.getKey());
            }
        }


        if (focus) {
            focusCamera(new LatLng(lat, lon));
        }
    }

    /**
     * Focus cam on map.
     *
     * @param lat   the lat
     * @param lon   the lon
     * @param focus the focus
     */
    public void focusCam(Double lat, Double lon, Boolean focus){
        if (focus) {
            focusCamera(new LatLng(lat, lon));
        }
    }

    private Bitmap getMarkerBitmapFromView(@DrawableRes int resId) {

        View customMarkerView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_custom_marker, null);
        ImageView markerImageView = (ImageView) customMarkerView.findViewById(R.id.custom_marker_view_icon);
        markerImageView.setImageResource(resId);
        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();


        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        customMarkerView.draw(canvas);
        return returnedBitmap;
    }

    /**
     * Selected marker.
     *
     * @param lat  the lat
     * @param lon  the lon
     * @param name the name
     */
    public void selectedMarker(Double lat, Double lon, String name) {

        MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(lat,lon));
        markerOptions.title(name);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(getMarkerBitmapFromView(R.drawable.ic_bus_stops_red),220, 220, false);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(scaledBitmap));

        Marker marker = map.addMarker(markerOptions);
        marker.showInfoWindow();
        int zoom = (int)map.getCameraPosition().zoom;
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), zoom), 4000, null);

        //Log.e("selectedMarker","Lat:"+lat.toString()+ "   Long:"+lon.toString()+"    Name:"+name);

    }


/*


    public static void setAnimation(GoogleMap myMap, final List<LatLng> directionPoint, final Bitmap bitmap) {
        myMap.clear();
        Marker marker = myMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
                .position(directionPoint.get(0))
                .flat(true));
        myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(directionPoint.get(0), 16));
        animateMarker(myMap, marker, directionPoint, false);
    }


    List<LatLng> markerPoints = new ArrayList<LatLng>();

    public void onCheckAnimation() {

        markerPoints.add(new LatLng(23.049543, 72.517195));
        markerPoints.add(new LatLng(23.058457, 72.516787));
        markerPoints.add(new LatLng(23.068989, 72.516973));
        markerPoints.add(new LatLng(23.078263, 72.516667));
        markerPoints.add(new LatLng(23.087409, 72.516281));
        markerPoints.add(new LatLng(23.096219, 72.515696));

        Bitmap Icon = BitmapFactory.decodeResource(getResources(), R.drawable.bus_zone_6);
        setAnimation(map,markerPoints,Icon);
    }

    private static void animateMarker(GoogleMap myMap, final Marker marker, final List<LatLng> directionPoint,
                                      final boolean hideMarker) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = myMap.getProjection();
        final long duration = 30000;

        final Interpolator interpolator = new LinearInterpolator();

        handler.post(new Runnable() {
            int i = 0;

            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                if (i < directionPoint.size())
                    marker.setPosition(directionPoint.get(i));
                i++;


                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 150);
                } else {
                    if (hideMarker) {
                        marker.setVisible(false);
                    } else {
                        marker.setVisible(true);
                    }
                }
            }
        });
    }
*/

    // Declare a variable for the cluster manager.
    private ClusterManager<MyClusterItem> mClusterManager;

    private void setUpClusterer() {


        // Position the map.
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(2.9214,101.7720), 10));

        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        mClusterManager = new ClusterManager<MyClusterItem>(getActivity(), map);
        mClusterManager.setRenderer(new CustomRenderer<MyClusterItem>(getActivity(),map,mClusterManager));

        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        map.setOnCameraIdleListener(mClusterManager);
        map.setOnMarkerClickListener(mClusterManager);

        // Add cluster items (markers) to the cluster manager.
        addItems();
    }

    /**
     * The type Custom renderer.
     *
     * @param <T> the type parameter
     */
    class CustomRenderer<T extends ClusterItem> extends DefaultClusterRenderer<T> {
        /**
         * Instantiates a new Custom renderer.
         *
         * @param context        the context
         * @param map            the map
         * @param clusterManager the cluster manager
         */
        public CustomRenderer(Context context, GoogleMap map, ClusterManager<T> clusterManager) {
            super(context, map, clusterManager);
        }

        @Override
        protected boolean shouldRenderAsCluster(Cluster<T> cluster) {
            //start clustering if at least 3 items overlap
            return cluster.getSize() > 2;
        }

    }

    private void addItems() {

        List<BusStop> BusStopsList = UserInstance.getInstance().getBusStopList();

        // Add ten cluster items in close proximity, for purposes of this example.
        for (int i = 0; i < BusStopsList.size(); i++) {
            double lat = BusStopsList.get(i).getLat();
            double lng = BusStopsList.get(i).getLon();
            MyClusterItem offsetItem = new MyClusterItem(lat, lng);
            mClusterManager.addItem(offsetItem);

/*            // Set the title and snippet strings.
            String titleee = "Title";
            String snippet = "Snippet";
            // Create a cluster item for the marker and set the title and snippet using the constructor.
            ClusterMarkerLocation infoWindowItem = new ClusterMarkerLocation(lat, lng, titleee, snippet);
            // Add the cluster item (marker) to the cluster manager.
            mClusterManager.addItem(infoWindowItem);*/
        }
    }
}