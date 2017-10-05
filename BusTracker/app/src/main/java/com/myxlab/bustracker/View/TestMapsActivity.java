package com.myxlab.bustracker.View;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;

import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.ClusterRenderer;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;
import com.myxlab.bustracker.Model.BusStop;
import com.myxlab.bustracker.Model.UserInstance;
import com.myxlab.bustracker.Model.maps.MyClusterItem;
import com.myxlab.bustracker.R;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;


/**
 * The type Test maps activity.
 */
public class TestMapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, GoogleMap.OnPolylineClickListener {

    private GoogleMap mMap;
    /**
     * The M google api client.
     */
    GoogleApiClient mGoogleApiClient;
    /**
     * The M last location.
     */
    Location mLastLocation;

    /**
     * The M curr location marker.
     */
    Marker mCurrLocationMarker;
    /**
     * The M location request.
     */
    LocationRequest mLocationRequest;

    private ArrayList<LatLng> points; //added
    /**
     * The Line.
     */
    Polyline line; //added



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_maps);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        ////////////////////////////////
        createLocationRequest();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        points = new ArrayList<LatLng>();


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);


        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);

            }
        }
        else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
        mMap.setOnPolylineClickListener(this);
    }

    /**
     * Build google api client.
     */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();

        setUpClusterer();

    }

    private void initMarkers() {
    }

    // Declare a variable for the cluster manager.
    private ClusterManager<MyClusterItem> mClusterManager;

    private void setUpClusterer() {


        // Position the map.
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(2.9214,101.7720), 10));

        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        mClusterManager = new ClusterManager<MyClusterItem>(this, mMap);
        mClusterManager.setRenderer(new CustomRenderer<MyClusterItem>(this,mMap,mClusterManager));

        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        mMap.setOnCameraIdleListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);

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



    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = new LocationRequest();

        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,mLocationRequest, (com.google.android.gms.location.LocationListener) this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }
///////////////////////////////////////////////////////////////////////////////////
    private String city = "kajang";
    private String country = "malaysia";
    private String area = "";
    private String title;
    private String requiredArea = "RA";
    private Location mCurrentLocation;
    private String mLastUpdateTime;

    private static final String TAG = "TestMapsActivity";
    private static final long INTERVAL = 1000 * 60 * 1; //1 minute
    private static final long FASTEST_INTERVAL = 1000 * 60 * 1; // 1 minute
    private static final float SMALLEST_DISPLACEMENT = 0.25F; //quarter of a meter

    @Override
    public void onLocationChanged(Location location) {

/*        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));*/

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude); //you already have this
        mCurrentLocation = location;

        //stop location updates
        if (mGoogleApiClient == null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

        points.add(latLng);
        redrawLine();

    }

    private void redrawLine(){

        mMap.clear();  //clears all Markers and Polylines

        PolylineOptions options = new PolylineOptions().width(20).color(Color.BLUE);
        for (int i = 0; i < points.size(); i++) {
            LatLng point = points.get(i);
            options.add(point);
        }

        addMarker(); //add Marker in current position
        line = mMap.addPolyline(options); //add Polyline
    }



    private void addMarker() {
        MarkerOptions options = new MarkerOptions();

        // following four lines requires 'Google Maps Android API Utility Library'
        // https://developers.google.com/maps/documentation/android/utility/
        // I have used this to display the time as title for location markers
        // you can safely comment the following four lines but for this info
        IconGenerator iconFactory = new IconGenerator(this);
        iconFactory.setStyle(IconGenerator.STYLE_DEFAULT);
        iconFactory.setBackground(getResources().getDrawable(R.drawable.ic_directions));
        Bitmap bitmapp= iconFactory.makeIcon();
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmapp,220, 220, false);

        // options.icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(mLastUpdateTime + requiredArea + city)));
        options.icon(BitmapDescriptorFactory.fromBitmap(scaledBitmap));
        options.anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV());
        LatLng currentLatLng = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
        options.position(currentLatLng);
        Marker mapMarker = mMap.addMarker(options);
        long atTime = mCurrentLocation.getTime();
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date(atTime));
        String title = mLastUpdateTime.concat(", " + requiredArea).concat(", " + city).concat(", " + country);
        mapMarker.setTitle(title);


        /*TextView mapTitle = (TextView) findViewById(R.id.textViewTitle);
        mapTitle.setText(title);
*/
        Log.e("Test", "Marker added.............................");
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng,20));
        Log.e("Test", "Zoom done.............................");
    }

    /**
     * Create location request.
     */
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setSmallestDisplacement(SMALLEST_DISPLACEMENT); //added
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }


    private static final PatternItem DOT = new Dot();
    private static final PatternItem GAP = new Gap(20);
    //
// Create a stroke pattern of a gap followed by a dot.
    private static final List<PatternItem> PATTERN_POLYLINE_DOTTED = Arrays.asList(GAP, DOT);

    @Override
    public void onPolylineClick(Polyline polyline) {
        // Flip from solid stroke to dotted stroke pattern.
        if ((polyline.getPattern() == null) || (!polyline.getPattern().contains(DOT))) {
            polyline.setPattern(PATTERN_POLYLINE_DOTTED);
        } else {
            // The default pattern is a solid stroke.
            polyline.setPattern(null);
        }

  /*      Toast.makeText(this, "Route type " + polyline.getTag().toString(),
                Toast.LENGTH_SHORT).show();*/
    }



    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    /**
     * The constant MY_PERMISSIONS_REQUEST_LOCATION.
     */
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    /**
     * Check location permission boolean.
     *
     * @return the boolean
     */
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }


}


