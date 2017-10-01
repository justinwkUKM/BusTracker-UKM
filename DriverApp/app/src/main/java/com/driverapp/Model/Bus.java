package com.driverapp.Model;

/**
 * The type Bus.
 */
public class Bus {

    private String busId, busPlate, busName;

    /**
     * Instantiates a new Bus.
     */
    public Bus() {
    }

    /**
     * Instantiates a new Bus.
     *
     * @param busId    the bus id
     * @param busPlate the bus plate
     * @param busName  the bus name
     */
    public Bus(String busId, String busPlate, String busName) {
        this.busId = busId;
        this.busPlate = busPlate;
        this.busName = busName;
    }

    /**
     * Gets bus id.
     *
     * @return the bus id
     */
    public String getBusId() {
        return busId;
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
