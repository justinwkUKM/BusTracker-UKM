package com.myxlab.bustracker.Model;

/**
 * The type Bus.
 */
public class Bus {


    private String name, plate, id, currentBusStop, busPlate, busName;
    private Double lat, lon;

    /**
     * Instantiates a new Bus.
     *
     * @param name           the name
     * @param lat            the lat
     * @param lon            the lon
     * @param plate          the plate
     * @param currentBusStop the current bus stop
     */
    public Bus(String name, Double lat, Double lon, String plate, String currentBusStop) {
        this.name = name;
        this.plate = plate;
        this.currentBusStop = currentBusStop;
        this.lat = lat;
        this.lon = lon;
    }

    /**
     * Gets current bus stop.
     *
     * @return the current bus stop
     */
    public String getCurrentBusStop() {
        return currentBusStop;
    }

    /**
     * Sets current bus stop.
     *
     * @param currentBusStop the current bus stop
     */
    public void setCurrentBusStop(String currentBusStop) {
        this.currentBusStop = currentBusStop;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Instantiates a new Bus.
     *
     * @param name  the name
     * @param lat   the lat
     * @param lon   the lon
     * @param plate the plate
     */
    public Bus(String name, Double lat, Double lon, String plate) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.plate = plate;
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
     * Gets plate.
     *
     * @return the plate
     */
    public String getPlate() {
        return plate;
    }

    /**
     * Sets plate.
     *
     * @param plate the plate
     */
    public void setPlate(String plate) {
        this.plate = plate;
    }


    /**
     * Gets bus plate.
     *
     * @return the bus plate
     */
    public String getBusPlate() {
        return busPlate;
    }

    /**
     * Gets bus name.
     *
     * @return the bus name
     */
    public String getBusName() {
        return busName;
    }
}
