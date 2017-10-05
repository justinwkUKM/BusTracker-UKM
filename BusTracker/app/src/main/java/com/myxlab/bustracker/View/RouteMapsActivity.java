package com.myxlab.bustracker.View;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
/*import com.inducesmile.androidmapdrawroute.entityObjects.DirectionObject;
import com.inducesmile.androidmapdrawroute.entityObjects.LegsObject;
import com.inducesmile.androidmapdrawroute.entityObjects.PolylineObject;
import com.inducesmile.androidmapdrawroute.entityObjects.RouteObject;
import com.inducesmile.androidmapdrawroute.entityObjects.StepsObject;
import com.inducesmile.androidmapdrawroute.network.GsonRequest;
import com.inducesmile.androidmapdrawroute.network.VolleySingleton;*/
import java.util.ArrayList;
import java.util.List;

import com.myxlab.bustracker.Model.maps.DirectionObject;
import com.myxlab.bustracker.Model.maps.GsonRequest;
import com.myxlab.bustracker.Model.maps.Helper;
import com.myxlab.bustracker.Model.maps.LegsObject;
import com.myxlab.bustracker.Model.maps.PolylineObject;
import com.myxlab.bustracker.Model.maps.RouteObject;
import com.myxlab.bustracker.Model.maps.StepsObject;
import com.myxlab.bustracker.Model.maps.VolleySingleton;
import com.myxlab.bustracker.R;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Route maps activity.
 */
public class RouteMapsActivity extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnMapClickListener, GoogleApiClient.ConnectionCallbacks  {

    /*private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    *//**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     *//*


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
*/
    private static final String TAG = RouteMapsActivity.class.getSimpleName();
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private LocationRequest mLocationRequest;
    private double latitudeValue = 0.0;
    private double longitudeValue = 0.0;
    private GoogleMap mMap;
    private static final int PERMISSION_LOCATION_REQUEST_CODE = 100;
    private List<LatLng> latLngList;
    private MarkerOptions yourLocationMarker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_maps);
        latLngList = new ArrayList<LatLng>();
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        mLocationRequest = createLocationRequest();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
    }
    @Override
    public void onMapClick(LatLng latLng) {
        if(latLngList.size() > 0){
            refreshMap(mMap);
            latLngList.clear();
        }
        latLngList.add(latLng);
        Log.d(TAG, "Marker number " + latLngList.size());
        mMap.addMarker(yourLocationMarker);
        mMap.addMarker(new MarkerOptions().position(latLng));
        LatLng defaultLocation = yourLocationMarker.getPosition();
        LatLng destinationLocation = latLng;
        //use Google Direction API to get the route between these Locations
        String directionApiPath = Helper.getUrl(String.valueOf(defaultLocation.latitude), String.valueOf(defaultLocation.longitude),
                String.valueOf(destinationLocation.latitude), String.valueOf(destinationLocation.longitude),"walking");
        Log.d(TAG, "Path " + directionApiPath);
        getDirectionFromDirectionApiServer(directionApiPath);
    }
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.d(TAG, "Connection method has been called");
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                                && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                            assignLocationValues(mLastLocation);
                            setDefaultMarkerOption(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()));
                        }else{
                            ActivityCompat.requestPermissions(RouteMapsActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_LOCATION_REQUEST_CODE);
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });
    }
    @Override
    public void onConnectionSuspended(int i) {
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_LOCATION_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    // permission was denied, show alert to explain permission
                    showPermissionAlert();
                }else{
                    //permission is granted now start a background service
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                        assignLocationValues(mLastLocation);
                        setDefaultMarkerOption(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()));
                    }
                }
            }
        }
    }
    private void assignLocationValues(Location currentLocation){
        if (currentLocation != null) {
            latitudeValue = currentLocation.getLatitude();
            longitudeValue = currentLocation.getLongitude();
            Log.d(TAG, "Latitude: " + latitudeValue + " Longitude: " + longitudeValue);
            markStartingLocationOnMap(mMap, new LatLng(latitudeValue, longitudeValue));
            addCameraToMap(new LatLng(latitudeValue, longitudeValue));
        }
    }
    private void addCameraToMap(LatLng latLng){
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)
                .zoom(16)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
    private void showPermissionAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.permission_request_title);
        builder.setMessage(R.string.app_permission_notice);
        builder.create();
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (ActivityCompat.checkSelfPermission(RouteMapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(RouteMapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(RouteMapsActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_LOCATION_REQUEST_CODE);
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(RouteMapsActivity.this, R.string.permission_refused, Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }
    private void markStartingLocationOnMap(GoogleMap mapObject, LatLng location){
        mapObject.addMarker(new MarkerOptions().position(location).title("Current location"));
        mapObject.moveCamera(CameraUpdateFactory.newLatLng(location));
    }
    private void refreshMap(GoogleMap mapInstance){
        mapInstance.clear();
    }

    /**
     * Create location request location request.
     *
     * @return the location request
     */
    protected LocationRequest createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(3000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return mLocationRequest;
    }
    private void setDefaultMarkerOption(LatLng location){
        if(yourLocationMarker == null){
            yourLocationMarker = new MarkerOptions();
        }
        yourLocationMarker.position(location);
    }
    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }
    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }
    private void getDirectionFromDirectionApiServer(String url){
        GsonRequest<DirectionObject> serverRequest = new GsonRequest<DirectionObject>(
                Request.Method.GET,
                url,
                DirectionObject.class,
                createRequestSuccessListener(),
                createRequestErrorListener());
        serverRequest.setRetryPolicy(new DefaultRetryPolicy(
                Helper.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(serverRequest);
    }
    private Response.Listener<DirectionObject> createRequestSuccessListener() {
        return new Response.Listener<DirectionObject>() {
            @Override
            public void onResponse(DirectionObject response) {
                try {
                    Log.d("JSON Response", response.toString());
                    if(response.getStatus().equals("OK")){
                        List<LatLng> mDirections = getDirectionPolylines(response.getRoutes());
                        drawRouteOnMap(mMap, mDirections);
                    }else{
                        Toast.makeText(RouteMapsActivity.this, R.string.server_error, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
        };
    }
    private List<LatLng> getDirectionPolylines(List<RouteObject> routes){
        List<LatLng> directionList = new ArrayList<LatLng>();
        for(RouteObject route : routes){
            List<LegsObject> legs = route.getLegs();
            for(LegsObject leg : legs){
                List<StepsObject> steps = leg.getSteps();
                for(StepsObject step : steps){
                    PolylineObject polyline = step.getPolyline();
                    String points = polyline.getPoints();
                    List<LatLng> singlePolyline = decodePoly(points);
                    for (LatLng direction : singlePolyline){
                        directionList.add(direction);
                    }
                }
            }
        }
        return directionList;
    }
    private Response.ErrorListener createRequestErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        };
    }
    private void drawRouteOnMap(GoogleMap map, List<LatLng> positions){
        PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
        options.addAll(positions);
        Polyline polyline = map.addPolyline(options);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(positions.get(1).latitude, positions.get(1).longitude))
                .zoom(17)
                .build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
    /**
     * Method to decode polyline points
     * Courtesy : http://jeffreysambells.com/2010/05/27/decoding-polylines-from-google-maps-direction-api-with-java
     * */
    private List<LatLng> decodePoly(String encoded) {
        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;
        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;
            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;
            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }
        return poly;
    }

    /**
     * The Double back to exit pressed once.
     */
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;


        //Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}