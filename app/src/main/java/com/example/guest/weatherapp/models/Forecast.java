package com.example.guest.weatherapp.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Forecast {
    Map<String, Object> temp = new HashMap<>();
    List<Map<String, Object>> weather = new ArrayList<Map<String, Object>>();
    long dt;

    public Forecast(){}

    public Forecast(HashMap<String, Object> temp, List<Map<String, Object>> weather, long dt) {
        this.temp = temp;
        this.weather = weather;
        this.dt = dt;
    }

    public Map<String, Object> getTemp() {
        return temp;
    }

    public List<Map<String, Object>> getWeather() {
        return weather;
    }

    public long getDt() {
        return dt;
    }
}
