package com.gabdullin.rail.weather;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class TheCityWeather extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        Parcel parcel = (Parcel) bundle.getSerializable("PARCEL");

        getSupportFragmentManager().beginTransaction().add(android.R.id.content, TheCityWeatherFragment.init(parcel)).commit();
    }
}
