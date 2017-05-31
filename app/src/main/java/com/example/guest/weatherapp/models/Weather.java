package com.example.guest.weatherapp.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Weather {
    String name;
    Map<String, Object> main = new HashMap<>();
    List<Map<String, Object>> weather = new ArrayList<Map<String, Object>>();
    Map<String, Object> coord = new HashMap<>();
    Map<String, Object> wind = new HashMap<>();
    Map<String, Object> clouds = new HashMap<>();
    Map<String, Object> rain = new HashMap<>();
    Map<String, Object> sys = new HashMap<>();
    String base;
    long id;
    int cod;
    long dt;

    public Weather() {}

    public Weather(String name, HashMap<String, Object> main, List<Map<String, Object>> weather, HashMap<String, Object> coord, HashMap<String, Object> wind, HashMap<String, Object> clouds, HashMap<String, Object> rain, HashMap<String, Object> sys, String base, long id, int cod, long dt) {
        this.name = name;
        this.main = main;
        this.weather = weather;
        this.coord = coord;
        this.wind = wind;
        this.clouds = clouds;
        this.rain = rain;
        this.sys = sys;
        this.base = base;
        this.id = id;
        this.cod = cod;
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

    public Map<String, Object> getCoord() {
        return coord;
    }

    public Map<String, Object> getWind() {
        return wind;
    }

    public Map<String, Object> getClouds() {
        return clouds;
    }

    public Map<String, Object> getRain() {
        return rain;
    }

    public Map<String, Object> getSys() {
        return sys;
    }

    public String getBase() {
        return base;
    }

    public long getId() {
        return id;
    }

    public int getCod() {
        return cod;
    }

    public long getDt() {
        return dt;
    }
}
