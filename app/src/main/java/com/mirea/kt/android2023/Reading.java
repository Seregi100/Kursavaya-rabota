package com.mirea.kt.android2023;

public class Reading {

    private String data;
    private Double reading;
    private Double tarif;
    private int meterNum;

    public Reading(String data, Double reading, Double tarif) {
        this.data = data;
        this.reading = reading;
        this.tarif = tarif;
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

    public Double getTarif() {
        return tarif;
    }

    public void setTarif(Double tarif) {
        this.tarif = tarif;
    }

    public int getMeterNum() {
        return meterNum;
    }

    public void setMeterNum(int meterNum) {
        this.meterNum = meterNum;
    }
}
