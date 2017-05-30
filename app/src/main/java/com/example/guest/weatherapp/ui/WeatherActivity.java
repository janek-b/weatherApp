package com.example.guest.weatherapp.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.guest.weatherapp.R;
import com.example.guest.weatherapp.models.Weather;
import com.example.guest.weatherapp.services.WeatherService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {
    @Bind(R.id.locationTextView) TextView mlocationTextView;
    @Bind(R.id.conditionTextView) TextView mConditionTextView;
    @Bind(R.id.dateTextView) TextView mDateTextView;
    @Bind(R.id.tempTextView) TextView mTempTextView;
    @Bind(R.id.minTempTextView) TextView mMinTempTextView;
    @Bind(R.id.maxTempTextView) TextView mMaxTempTextView;

    public ArrayList<Weather> mWeather = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String zipcode = intent.getStringExtra("zipcode");
        getWeather(zipcode);
    }

    private void getWeather(String zipcode) {
        final WeatherService weatherService = new WeatherService();
        weatherService.getWeather(zipcode, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mWeather = weatherService.processWeather(response);

                WeatherActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Weather currentWeather = mWeather.get(0);
                        Calendar date = Calendar.getInstance();
                        date.setTimeInMillis(currentWeather.getDate()*1000);
                        mlocationTextView.setText(currentWeather.getCity());
                        mTempTextView.setText(Double.toString(currentWeather.getTemp()));
                        mMinTempTextView.setText(Double.toString(currentWeather.getMinTemp()));
                        mMaxTempTextView.setText(Double.toString(currentWeather.getMaxTemp()));
//                        mDateTextView.setText(new Date(currentWeather.getDate()*1000).toString());
                        mDateTextView.setText(date.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US));
                        mConditionTextView.setText(android.text.TextUtils.join(", ", currentWeather.getCondition()));
                    }
                });

            }
        });
    }
}
