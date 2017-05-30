package com.example.guest.weatherapp.models;

import java.util.HashMap;

public class Forecast {
    private HashMap<String, String> condition = new HashMap<>();
    private double minTemp;
    private double maxTemp;
    private long date;

    public Forecast(HashMap<String, String> condition, double minTemp, double maxTemp, long date) {
        this.condition = condition;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.date = date;
    }

    public HashMap<String, String> getCondition() {
        return condition;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public long getDate() {
        return date;
    }
}
