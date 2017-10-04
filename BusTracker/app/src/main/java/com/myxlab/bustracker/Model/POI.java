package com.myxlab.bustracker.Model;

/**
 * The type Poi.
 */
public class POI {

    private String name, lat, lon, type, code, phone, email, website;

    /**
     * Instantiates a new Poi.
     */
    public POI() {
    }

    /**
     * Instantiates a new Poi.
     *
     * @param name the name
     * @param lat  the lat
     * @param lon  the lon
     * @param type the type
     * @param code the code
     */
    public POI(String name, String lat, String lon, String type, String code) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.type = type;
        this.code = code;
    }

    /**
     * Instantiates a new Poi.
     *
     * @param name    the name
     * @param lat     the lat
     * @param lon     the lon
     * @param type    the type
     * @param code    the code
     * @param phone   the phone
     * @param email   the email
     * @param website the website
     */
    public POI(String name, String lat, String lon, String type, String code, String phone, String email, String website) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.type = type;
        this.code = code;
        this.phone = phone;
        this.email = email;
        this.website = website;
    }

    /**
     * Gets phone.
     *
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets phone.
     *
     * @param phone the phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets website.
     *
     * @return the website
     */
    public String getWebsite() {
        return website;
    }

    /**
     * Sets website.
     *
     * @param website the website
     */
    public void setWebsite(String website) {
        this.website = website;
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
    public String getLat() {
        return lat;
    }

    /**
     * Sets lat.
     *
     * @param lat the lat
     */
    public void setLat(String lat) {
        this.lat = lat;
    }

    /**
     * Gets lon.
     *
     * @return the lon
     */
    public String getLon() {
        return lon;
    }

    /**
     * Sets lon.
     *
     * @param lon the lon
     */
    public void setLon(String lon) {
        this.lon = lon;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(String type) {
        this.type = type;
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
}
