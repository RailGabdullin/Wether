package com.gabdullin.rail.weather;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor sensorTemperature;
    private Sensor sensorHumidity;
    private TextView temperatureSensorInfo;
    private TextView humiditySensorInfo;
    private Button showSensorsInfo;

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

        temperatureSensorInfo = findViewById(R.id.temperatureSensorInfo);
        temperatureSensorInfo.setVisibility(View.INVISIBLE);

        humiditySensorInfo = findViewById(R.id.humiditySensorInfo);
        humiditySensorInfo.setVisibility(View.INVISIBLE);

        showSensorsInfo = findViewById(R.id.showSensorsInfo);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorTemperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        sensorHumidity = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        sensorManager.registerListener(listenerTemperature, sensorTemperature,
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(listenerHumidity, sensorHumidity,
                SensorManager.SENSOR_DELAY_NORMAL);


        showSensorsInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sensorTemperature != null) {
                    temperatureSensorInfo.setVisibility(View.VISIBLE);
                } else {
                    temperatureSensorInfo.setText("Нет датчика");
                }
                if (sensorHumidity != null) {
                    humiditySensorInfo.setVisibility(View.VISIBLE);
                } else {
                    humiditySensorInfo.setText("Нет датчика");
                }
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

    SensorEventListener listenerTemperature = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            showTemperatureSensors(event);
        }

    };

    SensorEventListener listenerHumidity = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            showHumiditySensors(event);
        }

    };

    private void showTemperatureSensors(SensorEvent event) {
        temperatureSensorInfo.setText("Температура: " + event.values[0]);
    }

    private void showHumiditySensors(SensorEvent event) {
        humiditySensorInfo.setText("Влажность: " + event.values[0]);
    }
}

