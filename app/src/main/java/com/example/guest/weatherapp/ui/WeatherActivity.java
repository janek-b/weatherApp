package com.example.guest.weatherapp.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guest.weatherapp.Constants;
import com.example.guest.weatherapp.R;
import com.example.guest.weatherapp.adapters.ForecastAdapter;
import com.example.guest.weatherapp.models.Forecast;
import com.example.guest.weatherapp.models.Weather;
import com.example.guest.weatherapp.services.WeatherService;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {
    @Bind(R.id.locationTextView) TextView mlocationTextView;
    @Bind(R.id.conditionTextView) TextView mConditionTextView;
    @Bind(R.id.dateTextView) TextView mDateTextView;
    @Bind(R.id.tempTextView) TextView mTempTextView;
    @Bind(R.id.minMaxTempTextView) TextView mMinMaxTempTextView;
    @Bind(R.id.conditionIcon) ImageView mConditionIcon;
    @Bind(R.id.weatherBackground) ImageView mWeatherBackground;
    @Bind(R.id.forecastView) RecyclerView mRecyclerView;

    public static String tempForWidget = "";

    private ForecastAdapter mAdapter;
    public static final String TAG = WeatherActivity.class.getSimpleName();

    private final CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String zipcode = intent.getStringExtra("zipcode");
        getWeather(zipcode);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.clear();
    }


    private void getWeather(String zipcode) {
        final WeatherService weatherService = new WeatherService();

        disposables.add(weatherService.getWeatherForecast(weatherService.buildUrl(zipcode, false))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<String>() {
                    @Override
                    public void onNext(@NonNull String s) { processWeather(s); }
                    @Override
                    public void onError(@NonNull Throwable e) { e.printStackTrace(); }
                    @Override
                    public void onComplete() { Log.d(TAG, "observable complete"); }
                }));

        disposables.add(weatherService.getWeatherForecast(weatherService.buildUrl(zipcode, true))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<String>() {
                    @Override
                    public void onNext(@NonNull String s) {
                        mAdapter = new ForecastAdapter(getApplicationContext(), weatherService.processForecast(s));
                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(WeatherActivity.this);
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);
                    }
                    @Override
                    public void onError(@NonNull Throwable e) { e.printStackTrace(); }
                    @Override
                    public void onComplete() { Log.d(TAG, "observable complete"); }
                }));
    }

    public void processWeather(String response) {
        try {
            JSONObject weatherJSON = new JSONObject(response);
            Calendar date = Calendar.getInstance();
            date.setTimeInMillis(weatherJSON.getLong("dt")*1000);

            tempForWidget = String.format("%.1f%s", weatherJSON.getJSONObject("main").getDouble("temp"), (char) 0x00B0);

            mlocationTextView.setText(weatherJSON.getString("name"));
            mDateTextView.setText(date.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US));
            mTempTextView.setText(String.format("%.1f%s", weatherJSON.getJSONObject("main").getDouble("temp"), (char) 0x00B0));
            mMinMaxTempTextView.setText(String.format("%.1f%s / %.1f%s",
                    weatherJSON.getJSONObject("main").getDouble("temp_min"), (char) 0x00B0,
                    weatherJSON.getJSONObject("main").getDouble("temp_max"), (char) 0x00B0));
            mConditionTextView.setText(weatherJSON.getJSONArray("weather").getJSONObject(0).getString("description"));

            Picasso.with(getApplicationContext())
                    .load(String.format("%s%s.png", Constants.ICON_BASE_URL, weatherJSON.getJSONArray("weather").getJSONObject(0).getString("icon")))
                    .into(mConditionIcon);

            String conditionId = weatherJSON.getJSONArray("weather").getJSONObject(0).getString("id");
            if (conditionId.equals("800")) {
                mWeatherBackground.setImageDrawable(ResourcesCompat.getDrawable(getResources(), getResources().getIdentifier("img800", "drawable", getPackageName()), null));
            } else {
                mWeatherBackground.setImageDrawable(ResourcesCompat.getDrawable(getResources(), getResources().getIdentifier("img"+conditionId.charAt(0), "drawable", getPackageName()), null));
            }
        } catch(JSONException e) {
            e.printStackTrace();
        }

    }
}
