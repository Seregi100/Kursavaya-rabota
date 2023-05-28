package com.mirea.kt.android2023;

import java.util.HashMap;

public class Meter {
    private String type;
    private int meterNum;

    public Meter(String type, int meterNum) {
        this.type = type;
        this.meterNum = meterNum;
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
