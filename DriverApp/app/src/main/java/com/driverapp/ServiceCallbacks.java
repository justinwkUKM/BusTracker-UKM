package com.driverapp;

import android.location.Location;

public interface ServiceCallbacks {

    void nextBusStop();

    void finishJourney(Location location);

}