package com.mirea.kt.android2023;

import java.util.HashMap;

public class Meter {
    private double reading;
    private String type;
    private int meterNum;

    public Meter(double reading, String type, int meterNum) {
        this.reading = reading;
        this.type = type;
        this.meterNum = meterNum;
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

    public int getMeterNum() {
        return meterNum;
    }

    public void setMeterNum(int meterNum) {
        this.meterNum = meterNum;
    }
}
