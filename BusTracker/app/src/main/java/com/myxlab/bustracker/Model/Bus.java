package com.myxlab.bustracker.Model;

public class Bus {


    private String name, plate, id, currentBusStop;
    private Double lat, lon;

    public Bus(String name, Double lat, Double lon, String plate, String currentBusStop) {
        this.name = name;
        this.plate = plate;
        this.currentBusStop = currentBusStop;
        this.lat = lat;
        this.lon = lon;
    }

    public String getCurrentBusStop() {
        return currentBusStop;
    }

    public void setCurrentBusStop(String currentBusStop) {
        this.currentBusStop = currentBusStop;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Bus(String name, Double lat, Double lon, String plate) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.plate = plate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }
}
