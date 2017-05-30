package com.example.guest.weatherapp.services;


import android.util.Log;

import com.example.guest.weatherapp.Constants;
import com.example.guest.weatherapp.models.Forecast;
import com.example.guest.weatherapp.models.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherService {

    public static void getWeather(String zipcode, Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder().build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.WEATHER_BASE_URL).newBuilder();
        if (isZip(zipcode)) {
            urlBuilder.addQueryParameter(Constants.ZIP_PARAM, zipcode);
        } else {
            urlBuilder.addQueryParameter(Constants.CITY_PARAM, zipcode);
        }
        urlBuilder.addQueryParameter(Constants.UNIT_PARAM, "imperial");
        urlBuilder.addQueryParameter(Constants.API_KEY_PARAM, Constants.WEATHER_KEY);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder().url(url).build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public static void getForecast(String zipcode, Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder().build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.FORECAST_BASE_URL).newBuilder();
        if (isZip(zipcode)) {
            urlBuilder.addQueryParameter(Constants.ZIP_PARAM, zipcode);
        } else {
            urlBuilder.addQueryParameter(Constants.CITY_PARAM, zipcode);
        }
        urlBuilder.addQueryParameter(Constants.UNIT_PARAM, "imperial");
        urlBuilder.addQueryParameter(Constants.COUNT_PARAM, "7");
        urlBuilder.addQueryParameter(Constants.API_KEY_PARAM, Constants.WEATHER_KEY);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder().url(url).build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public ArrayList<Weather> processWeather(Response response) {
        ArrayList<Weather> weatherArray = new ArrayList<>();
        try {
            String jsonData = response.body().string();
            if (response.isSuccessful()) {
                JSONObject weatherJSON = new JSONObject(jsonData);
                String city = weatherJSON.getString("name");
                long date = weatherJSON.getLong("dt");
                double temp = weatherJSON.getJSONObject("main").getDouble("temp");
                double minTemp = weatherJSON.getJSONObject("main").getDouble("temp_min");
                double maxTemp = weatherJSON.getJSONObject("main").getDouble("temp_max");
                HashMap<String, String> condition = new HashMap<>();
                condition.put("id", weatherJSON.getJSONArray("weather").getJSONObject(0).getString("id"));
                condition.put("main", weatherJSON.getJSONArray("weather").getJSONObject(0).getString("main"));
                condition.put("description", weatherJSON.getJSONArray("weather").getJSONObject(0).getString("description"));
                condition.put("icon", weatherJSON.getJSONArray("weather").getJSONObject(0).getString("icon"));
                Weather weather = new Weather(city, condition, temp, minTemp, maxTemp, date);
                weatherArray.add(weather);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return weatherArray;
    }

    public ArrayList<Forecast> processForecast(Response response) {
        ArrayList<Forecast> forecastArray = new ArrayList<>();
        try {
            String jsonData = response.body().string();
            JSONObject forecastJSON = new JSONObject(jsonData);
            JSONArray forecastJSONArray = forecastJSON.getJSONArray("list");
            for (int i = 0; i < forecastJSONArray.length(); i++) {
                JSONObject forecastItem = forecastJSONArray.getJSONObject(i);
                long date = forecastItem.getLong("dt");
                double minTemp = forecastItem.getJSONObject("temp").getDouble("min");
                double maxTemp = forecastItem.getJSONObject("temp").getDouble("max");
                HashMap<String, String> condition = new HashMap<>();
                condition.put("id", forecastItem.getJSONArray("weather").getJSONObject(0).getString("id"));
                condition.put("main", forecastItem.getJSONArray("weather").getJSONObject(0).getString("main"));
                condition.put("description", forecastItem.getJSONArray("weather").getJSONObject(0).getString("description"));
                condition.put("icon", forecastItem.getJSONArray("weather").getJSONObject(0).getString("icon"));
                Forecast forecast = new Forecast(condition, minTemp, maxTemp, date);
                forecastArray.add(forecast);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return forecastArray;
    }

    private static boolean isZip(String input) {
        boolean result = true;
        try {
            Integer zip = Integer.parseInt(input);
        } catch(NumberFormatException e) {
            result = false;
        }
        return result;
    }
}
