package com.gabdullin.rail.weather;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TheCityWeather extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.the_city_weather);

        Bundle bundle = getIntent().getExtras();

        TextView theCity = findViewById(R.id.city);
        if(bundle.containsKey("THE_CITY")) theCity.setText(bundle.getString("THE_CITY"));

        TextView wind = findViewById(R.id.showWind);
        TextView windParam = findViewById(R.id.showWindParam);
        if(!bundle.getBoolean("SHOW_WIND")){
            wind.setVisibility(View.GONE);
            windParam.setVisibility(View.GONE);
        }

        TextView pressure = findViewById(R.id.showPressure);
        TextView pressureParam = findViewById(R.id.showPressureParam);
        if(!bundle.getBoolean("SHOW_PRESSURE")){
            pressure.setVisibility(View.GONE);
            pressureParam.setVisibility(View.GONE);
        }


        TextView humidity = findViewById(R.id.showHumidity);
        TextView humidityParam = findViewById(R.id.showHumidityParam);
        if(!bundle.getBoolean("SHOW_HUMIDITY")){
            humidity.setVisibility(View.GONE);
            humidityParam.setVisibility(View.GONE);
        }

    }
}
