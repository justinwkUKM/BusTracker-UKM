package com.driverapp.Model;

public class Bus {

    private String busId, busPlate, busName;

    public Bus() {
    }

    public Bus(String busId, String busPlate, String busName) {
        this.busId = busId;
        this.busPlate = busPlate;
        this.busName = busName;
    }

    public String getBusId() {
        return busId;
    }

    public String getBusPlate() {
        return busPlate;
    }

    public String getBusName() {
        return busName;
    }
}
