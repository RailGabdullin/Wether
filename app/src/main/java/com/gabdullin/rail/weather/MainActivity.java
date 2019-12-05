package com.gabdullin.rail.weather;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button moscowButton = findViewById(R.id.Moscow);
        Button spbButton = findViewById(R.id.SPb);
        Button kazanButton = findViewById(R.id.Kazan);
        Button ufaButton = findViewById(R.id.Ufa);

        final CheckBox showWind = findViewById(R.id.showWind);
        final CheckBox showPressure = findViewById(R.id.showPressure);
        final CheckBox showHumidity = findViewById(R.id.showHumidity);

        moscowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSecondActivity("Москва", showWind.isChecked(), showPressure.isChecked(), showHumidity.isChecked());
            }
        });
        spbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSecondActivity("Санкт-Петербург", showWind.isChecked(), showPressure.isChecked(), showHumidity.isChecked());
            }
        });
        kazanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSecondActivity("Казань", showWind.isChecked(), showPressure.isChecked(), showHumidity.isChecked());
            }
        });
        ufaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSecondActivity("Уфа", showWind.isChecked(), showPressure.isChecked(), showHumidity.isChecked());
            }
        });

    }

    void startSecondActivity(String city, boolean isShowWindChecked, boolean isShowPressureChecked, boolean isShowHumidityChecked){
        Intent showTheCityWeather = new Intent(this, TheCityWeather.class);
        showTheCityWeather.putExtra("THE_CITY", city);
        showTheCityWeather.putExtra("SHOW_WIND", isShowWindChecked);
        showTheCityWeather.putExtra("SHOW_PRESSURE", isShowPressureChecked);
        showTheCityWeather.putExtra("SHOW_HUMIDITY", isShowHumidityChecked);
        startActivity(showTheCityWeather);
    }
}

