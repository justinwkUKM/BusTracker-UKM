package com.myxlab.bustracker.Model;

import java.util.List;

/**
 * The type Bus stop.
 */
public class BusStop {

    private String name,code;
    private Double lat, lon;
    private List<String> bus;
    private int id;


    /**
     * Instantiates a new Bus stop.
     */
    public BusStop() {
    }

    /**
     * Instantiates a new Bus stop.
     *
     * @param name the name
     * @param lat  the lat
     * @param lon  the lon
     * @param bus  the bus
     */
    public BusStop(String name, Double lat, Double lon, List<String> bus) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.bus = bus;
    }

    /**
     * Instantiates a new Bus stop.
     *
     * @param name the name
     * @param code the code
     * @param lat  the lat
     * @param lon  the lon
     * @param bus  the bus
     */
    public BusStop(String name, String code, Double lat, Double lon, List<String> bus) {
        this.name = name;
        this.code = code;
        this.lat = lat;
        this.lon = lon;
        this.bus = bus;
    }

    /**
     * Instantiates a new Bus stop.
     *
     * @param name the name
     * @param code the code
     * @param lat  the lat
     * @param lon  the lon
     */
    public BusStop(String name, String code, Double lat, Double lon) {
        this.name = name;
        this.code = code;
        this.lat = lat;
        this.lon = lon;
    }

    /**
     * Instantiates a new Bus stop.
     *
     * @param id   the id
     * @param name the name
     * @param lat  the lat
     * @param lon  the lon
     */
    public BusStop(int id, String name, Double lat, Double lon) {
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }

    /**
     * Gets code.
     *
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets code.
     *
     * @param code the code
     */
    public void setCode(String code) {
        this.code = code;
    }


    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets lat.
     *
     * @return the lat
     */
    public Double getLat() {
        return lat;
    }

    /**
     * Sets lat.
     *
     * @param lat the lat
     */
    public void setLat(Double lat) {
        this.lat = lat;
    }

    /**
     * Gets lon.
     *
     * @return the lon
     */
    public Double getLon() {
        return lon;
    }

    /**
     * Sets lon.
     *
     * @param lon the lon
     */
    public void setLon(Double lon) {
        this.lon = lon;
    }

    /**
     * Gets bus.
     *
     * @return the bus
     */
    public List<String> getBus() {
        return bus;
    }

    /**
     * Sets bus.
     *
     * @param bus the bus
     */
    public void setBus(List<String> bus) {
        this.bus = bus;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }
}
