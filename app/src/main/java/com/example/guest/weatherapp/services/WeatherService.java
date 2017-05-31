package com.example.guest.weatherapp.services;

import com.example.guest.weatherapp.Constants;
import com.example.guest.weatherapp.models.Forecast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherService {

    public static String buildUrl(String location, boolean forecast) {
        HttpUrl.Builder urlBuilder;
        if (forecast) {
            urlBuilder = HttpUrl.parse(Constants.FORECAST_BASE_URL).newBuilder();
            urlBuilder.addQueryParameter(Constants.COUNT_PARAM, "7");
        } else {
            urlBuilder = HttpUrl.parse(Constants.WEATHER_BASE_URL).newBuilder();
        }
        urlBuilder.addQueryParameter(isZip(location), location);
        urlBuilder.addQueryParameter(Constants.UNIT_PARAM, "imperial");
        urlBuilder.addQueryParameter(Constants.API_KEY_PARAM, Constants.WEATHER_KEY);
        return urlBuilder.build().toString();
    }

    public static Observable<String> getWeatherForecast(final String url) {
        final OkHttpClient client = new OkHttpClient.Builder().build();
        return Observable.defer(new Callable<ObservableSource<? extends String>>() {
            @Override
            public ObservableSource<? extends String> call() throws Exception {
                try {
                    Response response = client.newCall(new Request.Builder().url(url).build()).execute();
                    return Observable.just(response.body().string());
                } catch (IOException e) {
                    return Observable.error(e);
                }
            }
        });
    }

    private static String isZip(String input) {
        String result = Constants.CITY_PARAM;
        if (input.matches("\\d{5}")) {
            result = Constants.ZIP_PARAM;
        }
        return result;
    }
}
