package com.driverapp.Service;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.driverapp.Model.UserInstance;
import com.driverapp.R;
import com.driverapp.ServiceCallbacks;
import com.driverapp.View.TrackActivity;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.SphericalUtil;

import java.text.DateFormat;
import java.util.Date;

public class LocationListenerService extends Service {

    public LocationManager locationManager;
    public DriverLocationListener listener;
    boolean startUP = true;
    private final IBinder binder = new LocalBinder();
    private ServiceCallbacks serviceCallbacks;
    int count = 1;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    public void onCreate() {
        super.onCreate();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        listener = new DriverLocationListener();
        UserInstance.getInstance().getVolleyApp().getAllBusStopJourney(getApplicationContext(),2);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 3, listener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 3, listener);

        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("coordinate").push();
        myRef.child("Details").child("Bus").setValue(UserInstance.getInstance().getBus().getBusName()+ " "+UserInstance.getInstance().getBus().getBusPlate());
        myRef.child("Details").child("Driver").setValue(UserInstance.getInstance().getDriver().getDriver_id());
        myRef.child("Details").child("Route").setValue(UserInstance.getInstance().getRoute().getRouteName());
        myRef.child("Details").child("Time").setValue(currentDateTimeString);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.removeUpdates(listener);
    }

    public class LocalBinder extends Binder {
        public LocationListenerService getService() {
            return LocationListenerService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public void setCallbacks(ServiceCallbacks callbacks) {
        serviceCallbacks = callbacks;
    }

    public class DriverLocationListener implements LocationListener {

        public void onLocationChanged(final Location location) {

            UserInstance.getInstance().getVolleyApp().trackBus(getString(R.string.url_bus_log),getApplicationContext(),location.getLatitude(),location.getLongitude());
            myRef.child("Coordinate").child(""+count).setValue(""+count+","+location.getLatitude()+","+location.getLongitude());
            Toast.makeText(LocationListenerService.this, ""+count+" firebase", Toast.LENGTH_SHORT).show();
            count++;
            checkNextBusStop(location);
            if (startUP){
                startUP = false;
                UserInstance.getInstance().getVolleyApp().setStatusBus(getString(R.string.url_bus_status),getApplicationContext(),true,location.getLatitude(),location.getLongitude());
            }

        }

        public void onProviderDisabled(String provider) {
        }


        public void onProviderEnabled(String provider) {
        }


        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

    }

    public void checkNextBusStop(Location location){
        int nextBusStopIndex = UserInstance.getInstance().getBusLocation();
        LatLng currentLocation = new LatLng( location.getLatitude(), location.getLongitude());
        LatLng nexBusStop = new LatLng(UserInstance.getInstance().getRoute().getBusStopList().get(nextBusStopIndex).getLat(),UserInstance.getInstance().getRoute().getBusStopList().get(nextBusStopIndex).getLon());
        double distance = SphericalUtil.computeDistanceBetween( currentLocation, nexBusStop);
        Toast.makeText(this, ""+UserInstance.getInstance().getBusLocation(), Toast.LENGTH_SHORT).show();
        if( distance <= 25 ) {
            if (nextBusStopIndex+1 < UserInstance.getInstance().getRoute().getBusStopList().size()){
                nextBusStop();
                Toast.makeText(this, String.format( "%4.2f%s", distance, "m" ), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Your Journey has finish", Toast.LENGTH_SHORT).show();
                finishJourney(location);
            }
        } else {
            Toast.makeText(this, String.format("%4.2f%s", distance, "m"), Toast.LENGTH_LONG).show();
        }
    }

    public void nextBusStop(){
        if (serviceCallbacks != null) {
            serviceCallbacks.nextBusStop();
        }
    }

    public void finishJourney(Location location){
        if (serviceCallbacks != null) {
            serviceCallbacks.finishJourney(location);
        }
    }
}
