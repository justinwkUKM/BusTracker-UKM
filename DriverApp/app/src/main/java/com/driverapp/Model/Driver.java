package com.driverapp.Model;

/**
 * The type Driver.
 */
public class Driver {

    /**
     * The Driver id.
     */
    String driver_id, /**
     * The Driver password.
     */
    driver_password, /**
     * The Drive route.
     */
    drive_route, /**
     * The Bus id.
     */
    bus_id;

    /**
     * Instantiates a new Driver.
     */
    public Driver() {
    }

    /**
     * Gets driver id.
     *
     * @return the driver id
     */
    public String getDriver_id() {
        return driver_id;
    }

    /**
     * Gets driver password.
     *
     * @return the driver password
     */
    public String getDriver_password() {
        return driver_password;
    }

    /**
     * Gets drive route.
     *
     * @return the drive route
     */
    public String getDrive_route() {
        return drive_route;
    }

    /**
     * Gets bus id.
     *
     * @return the bus id
     */
    public String getBus_id() {
        return bus_id;
    }

    /**
     * Save driver info.
     *
     * @param username the username
     * @param password the password
     */
    public void saveDriverInfo(String username, String password){
        this.driver_id = username;
        this.driver_password = password;
    }

    /**
     * Sets drive route.
     *
     * @param drive_route the drive route
     */
    public void setDrive_route(String drive_route) {
        this.drive_route = drive_route;
    }

    /**
     * Sets bus id.
     *
     * @param bus_id the bus id
     */
    public void setBus_id(String bus_id) {
        this.bus_id = bus_id;
    }
}
