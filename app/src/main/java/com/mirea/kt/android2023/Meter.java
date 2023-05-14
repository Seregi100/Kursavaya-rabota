package com.mirea.kt.android2023;

import java.util.HashMap;

public class Meter {
    private double reading;
    private String type;

    public Meter(double reading, String type) {
        this.reading = reading;
        this.type = type;
    }

    public double getReading() {
        return reading;
    }

    public void setReading(double reading) {
        this.reading = reading;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
