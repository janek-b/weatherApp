package com.example.guest.weatherapp.services;


import android.util.Log;

import com.example.guest.weatherapp.Constants;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class WeatherService {

    public static void getWeather(String zipcode, Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder().build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.WEATHER_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.ZIP_PARAM, zipcode);
        urlBuilder.addQueryParameter(Constants.API_KEY_PARAM, Constants.WEATHER_KEY);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder().url(url).build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }
}
