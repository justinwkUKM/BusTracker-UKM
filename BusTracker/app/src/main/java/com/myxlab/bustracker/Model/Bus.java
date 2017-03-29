package com.myxlab.bustracker.Model;

public class Bus {

    private String name, plate;
    private Double lat, lon;

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
