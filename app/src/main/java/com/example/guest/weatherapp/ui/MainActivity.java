package com.example.guest.weatherapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.guest.weatherapp.R;
import com.example.guest.weatherapp.services.WeatherService;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.search_field) EditText mSeachField;
    @Bind(R.id.search_button) Button mSearchButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchInput = mSeachField.getText().toString();
                getWeather(searchInput);
            }
        });
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
                try {
                    String jsonData = response.body().string();
                    Log.d("MainActivity", jsonData);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
