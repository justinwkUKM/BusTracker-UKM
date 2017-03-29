package com.myxlab.bustracker.Model;

public class POI {

    private String name, lat, lon, type, code;

    public POI() {
    }

    public POI(String name, String lat, String lon, String type, String code) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.type = type;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
