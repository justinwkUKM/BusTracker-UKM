package com.driverapp.Model;

/**
 * The type Bus stop.
 */
public class BusStop {

    private int id;
    private String name;
    private Double lat, lon;

    /**
     * Instantiates a new Bus stop.
     */
    public BusStop() {
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
}
