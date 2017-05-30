package com.example.guest.weatherapp.models;

import java.util.HashMap;

public class Weather {
    private String city;
    private HashMap<String, String> condition = new HashMap<>();
    private double temp;
    private double minTemp;
    private double maxTemp;
    private long date;

    public Weather(String city, HashMap<String, String> condition, double temp, double minTemp, double maxTemp, long date) {
        this.city = city;
        this.condition = condition;
        this.temp = temp;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.date = date;
    }

    public String getCity() {
        return city;
    }

    public HashMap<String, String> getCondition() {
        return condition;
    }

    public double getTemp() {
        return temp;
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
