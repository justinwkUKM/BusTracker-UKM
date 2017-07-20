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
import com.myxlab.bustracker.FontChangeCrawler;
import com.myxlab.bustracker.Model.Bus;
import com.myxlab.bustracker.Model.BusStop;
import com.myxlab.bustracker.Model.UserInstance;
import com.myxlab.bustracker.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import me.toptas.fancyshowcase.FancyShowCaseView;
import me.toptas.fancyshowcase.FocusShape;

import static android.content.Context.LOCATION_SERVICE;

public class MapsFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerClickListener, LocationListener {

    private GoogleApiClient mGoogleApiClient;
    public Location mCurrentLocation;
    private CameraPosition position;
    public GoogleMap map;
    private Context context;
    static long duration = 5000;
    private List<Bus> bus = new ArrayList<>();
    public HashMap<Marker, String> hashMapMarker;
    public HashMap<String, String> hashMapTitle;
    private HashMap<Marker, Integer> hashMapBusStopListPosition;
    private HashMap<BusStop, Marker> hashMapBusStopMarker;
    private HashMap<Marker, Bus> hashMapBus;
    private LocationManager locationManager;
    private Marker greenMarker;
    MapsFragment mapsFragment;
    List<Marker> busesMarker;

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
            // TODO: Consider calling
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

        UserInstance.getInstance().getVolleyApp().getBusStop(getString(R.string.url_bus_stop_list), getActivity(), this);

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

    public void setBus() {

        if (!UserInstance.getInstance().getBuses().isEmpty()) {

            if (busesMarker.isEmpty()) {
                for (int i = 0; UserInstance.getInstance().getBuses().size() > i; i++) {

                    String title = UserInstance.getInstance().getBuses().get(i).getName() + " (" + UserInstance.getInstance().getBuses().get(i).getPlate() + ")";
                    Marker busMarker = map.addMarker(busMarkerOptions(UserInstance.getInstance().getBuses().get(i).getName(), title, new LatLng(UserInstance.getInstance().getBuses().get(i).getLat(), UserInstance.getInstance().getBuses().get(i).getLon())));
                    busesMarker.add(busMarker);

                    int index = busesMarker.size() - 1;
                    hashMapBus.put(busesMarker.get(index), UserInstance.getInstance().getBuses().get(i));
                    hashMapMarker.put(busesMarker.get(index), "Bus");

                }
            } else {

                if (UserInstance.getInstance().getBuses().size() == busesMarker.size()) {

                    for (int i = 0; i < UserInstance.getInstance().getBuses().size(); i++) {
                        for (int j = 0; j < busesMarker.size(); j++) {
                            Bus bus = hashMapBus.get(busesMarker.get(j));
                            if (UserInstance.getInstance().getBuses().get(i).getPlate().equals(bus.getPlate())) {
                                double distance = checkDistance(new LatLng(bus.getLat(), bus.getLat()), new LatLng(UserInstance.getInstance().getBuses().get(i).getLat(), UserInstance.getInstance().getBuses().get(i).getLon()));

                                if (distance >= 10) {
                                    animateMarker(busesMarker.get(j), new LatLng(UserInstance.getInstance().getBuses().get(i).getLat(), UserInstance.getInstance().getBuses().get(i).getLon()), false);
                                }
                            }
                        }
                    }

                } else if ((UserInstance.getInstance().getBuses().size() > busesMarker.size())) {
                    List<Integer> newBuses = new LinkedList<>();
                    for (int i = 0; i < UserInstance.getInstance().getBuses().size(); i++) {
                        boolean checker = false;
                        for (int j = 0; j < busesMarker.size(); j++) {
                            Bus bus = hashMapBus.get(busesMarker.get(j));
                            if (UserInstance.getInstance().getBuses().get(i).getPlate().equals(bus.getPlate())) {
                                double distance = checkDistance(new LatLng(bus.getLat(), bus.getLat()), new LatLng(UserInstance.getInstance().getBuses().get(i).getLat(), UserInstance.getInstance().getBuses().get(i).getLon()));

                                if (distance >= 10) {
                                    animateMarker(busesMarker.get(j), new LatLng(UserInstance.getInstance().getBuses().get(i).getLat(), UserInstance.getInstance().getBuses().get(i).getLon()), false);
                                }
                                checker = true;
                            }

                            if (!checker && j == busesMarker.size() - 1) {
                                newBuses.add(i);
                            }

                        }

                        if (i == UserInstance.getInstance().getBuses().size() - 1) {
                            for (int j = 0; j < newBuses.size(); j++) {

                                String title = UserInstance.getInstance().getBuses().get(newBuses.get(j)).getName() + " (" + UserInstance.getInstance().getBuses().get(newBuses.get(j)).getPlate() + ")";
                                Marker busMarker = map.addMarker(busMarkerOptions(UserInstance.getInstance().getBuses().get(j).getName(), title, new LatLng(UserInstance.getInstance().getBuses().get(newBuses.get(j)).getLat(), UserInstance.getInstance().getBuses().get(newBuses.get(j)).getLon())));
                                busesMarker.add(busMarker);

                                int index = busesMarker.size() - 1;
                                hashMapBus.put(busesMarker.get(index), UserInstance.getInstance().getBuses().get(newBuses.get(j)));
                                hashMapMarker.put(busesMarker.get(index), "Bus");

                            }
                        }
                    }
                } else {
                    List<Integer> onBuses = new LinkedList<>();
                    for (int i = 0; i < UserInstance.getInstance().getBuses().size(); i++) {
                        for (int j = 0; j < busesMarker.size(); j++) {
                            Bus bus = hashMapBus.get(busesMarker.get(j));
                            if (UserInstance.getInstance().getBuses().get(i).getPlate().equals(bus.getPlate())) {
                                double distance = checkDistance(new LatLng(bus.getLat(), bus.getLat()), new LatLng(UserInstance.getInstance().getBuses().get(i).getLat(), UserInstance.getInstance().getBuses().get(i).getLon()));

                                if (distance >= 10) {
                                    animateMarker(busesMarker.get(j), new LatLng(UserInstance.getInstance().getBuses().get(i).getLat(), UserInstance.getInstance().getBuses().get(i).getLon()), false);
                                }
                                onBuses.add(j);
                            }
                        }
                    }
                    List<Integer> tmpIndex = new LinkedList<>();
                    for (int j = 0; j < busesMarker.size(); j++) {
                        tmpIndex.add(j);
                    }

                    for (Integer index : tmpIndex) {
                        if (!onBuses.contains(index)) {
                            busesMarker.get(index).remove();
                            busesMarker.remove(index);
                        }
                    }
                }

            }
        } else {

            if (!busesMarker.isEmpty()) {
                for (int i = 0; i < busesMarker.size(); i++) {
                    busesMarker.get(i).remove();
                }
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

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET}, 4);
                    return;
                }
                mCurrentLocation = LocationServices
                        .FusedLocationApi
                        .getLastLocation(mGoogleApiClient);
                initCamera(mCurrentLocation);
                loopBus();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

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

        if (!hashMapMarker.get(marker).equals("Bus")) {

            switch (hashMapMarker.get(marker)) {
                case "Bus Stop":
                    ((MainActivity) getActivity()).BusStopBottomSheetCall(hashMapBusStopListPosition.get(marker));
                    break;
                default:
                    break;
            }

        } else {

            String title = marker.getTitle();
            ((MainActivity) getActivity()).BusBottomSheetCall(title);

        }

        return false;
    }

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

    public void setBusStopMarker() {

        hashMapBusStopListPosition.clear();

        for (int i = 0; i < UserInstance.getInstance().getBusStopList().size(); i++) {

            MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(UserInstance.getInstance().getBusStopList().get(i).getLat(), UserInstance.getInstance().getBusStopList().get(i).getLon()));
            markerOptions.title(UserInstance.getInstance().getBusStopList().get(i).getName());
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.ic_busstop)));
//          markerOptions.icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("ic_busstop",10,10)));

            Marker marker = map.addMarker(markerOptions);

            hashMapMarker.put(marker, "Bus Stop");
            hashMapBusStopMarker.put(UserInstance.getInstance().getBusStopList().get(i), marker);
            hashMapBusStopListPosition.put(marker, i);
        }

        findBusStop();

    }
    public Bitmap resizeMapIcons(String iconName,int width, int height){
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(),getResources().getIdentifier(iconName, "drawable", context.getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }
    private void findBusStop() {
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 5, this);
        checkBusStopDistance();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void checkBusStopDistance() {

        int busStopGreenIndex = 0;
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
                greenMarker.setIcon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.ic_busstop)));
                greenMarker = hashMapBusStopMarker.get(UserInstance.getInstance().getBusStopList().get(busStopGreenIndex));
                greenMarker.setIcon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.ic_busstop_green)));
            } else {
                greenMarker = hashMapBusStopMarker.get(UserInstance.getInstance().getBusStopList().get(busStopGreenIndex));
                greenMarker.setIcon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.ic_busstop_green)));
            }
        }else {
            Toast.makeText(context, "Location Not Available", Toast.LENGTH_SHORT).show();
        }




    }

    @Override
    public void onLocationChanged(Location location) {
        checkBusStopDistance();
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
        }
        //map.clear();
        final Marker marker = map.addMarker(markerOptions);

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
}