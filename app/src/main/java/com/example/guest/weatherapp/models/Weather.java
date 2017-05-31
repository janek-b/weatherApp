package com.example.guest.weatherapp.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Weather {
    String name;
    Map<String, Object> main = new HashMap<>();
    List<Map<String, Object>> weather = new ArrayList<Map<String, Object>>();
    long dt;

    public Weather() {}

    public Weather(String name, HashMap<String, Object> main, List<Map<String, Object>> weather, long dt) {
        this.name = name;
        this.main = main;
        this.weather = weather;
        this.dt = dt;
    }

    public String getName() {
        return name;
    }

    public Map<String, Object> getMain() {
        return main;
    }

    public List<Map<String, Object>> getWeather() {
        return weather;
    }

    public long getDt() {
        return dt;
    }
}
