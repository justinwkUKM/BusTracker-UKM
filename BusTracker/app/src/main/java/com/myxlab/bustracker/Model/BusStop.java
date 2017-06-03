package com.myxlab.bustracker.Model;

import java.util.List;

public class BusStop {

    private String name,code;
    private Double lat, lon;
    private List<String> bus;

    public BusStop() {
    }

    public BusStop(String name, Double lat, Double lon, List<String> bus) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.bus = bus;
    }

    public BusStop(String name, String code, Double lat, Double lon, List<String> bus) {
        this.name = name;
        this.code = code;
        this.lat = lat;
        this.lon = lon;
        this.bus = bus;
    }

    public BusStop(String name, String code, Double lat, Double lon) {
        this.name = name;
        this.code = code;
        this.lat = lat;
        this.lon = lon;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public List<String> getBus() {
        return bus;
    }

    public void setBus(List<String> bus) {
        this.bus = bus;
    }
}
