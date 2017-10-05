package com.myxlab.bustracker.Model;

/**
 * The type Bus eta.
 */
public class BusETA {

    private String name, eta;

    /**
     * Instantiates a new Bus eta.
     *
     * @param name the name
     * @param eta  the eta
     */
    public BusETA(String name, String eta) {
        this.name = name;
        this.eta = eta;
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
     * Gets eta.
     *
     * @return the eta
     */
    public String getEta() {
        return eta;
    }

    /**
     * Sets eta.
     *
     * @param eta the eta
     */
    public void setEta(String eta) {
        this.eta = eta;
    }
}
