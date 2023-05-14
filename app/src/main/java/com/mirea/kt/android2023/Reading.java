package com.mirea.kt.android2023;

public class Reading {

    private String data;
    private Double reading;

    public Reading(String data, Double reading) {
        this.data = data;
        this.reading = reading;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Double getReading() {
        return reading;
    }

    public void setReading(Double reading) {
        this.reading = reading;
    }
}
