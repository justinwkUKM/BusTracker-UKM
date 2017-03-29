package com.driverapp.Model;

public class Driver {

    String driver_id, driver_password, drive_route, bus_id;

    public Driver() {
    }

    public String getDriver_id() {
        return driver_id;
    }

    public String getDriver_password() {
        return driver_password;
    }

    public String getDrive_route() {
        return drive_route;
    }

    public String getBus_id() {
        return bus_id;
    }

    public void saveDriverInfo(String username, String password){
        this.driver_id = username;
        this.driver_password = password;
    }

    public void setDrive_route(String drive_route) {
        this.drive_route = drive_route;
    }

    public void setBus_id(String bus_id) {
        this.bus_id = bus_id;
    }
}
