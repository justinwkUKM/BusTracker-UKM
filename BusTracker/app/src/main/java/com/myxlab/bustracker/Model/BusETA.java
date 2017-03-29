package com.myxlab.bustracker.Model;

public class BusETA {

    private String name, eta;

    public BusETA(String name, String eta) {
        this.name = name;
        this.eta = eta;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEta() {
        return eta;
    }

    public void setEta(String eta) {
        this.eta = eta;
    }
}
