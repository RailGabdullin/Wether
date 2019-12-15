package com.gabdullin.rail.weather;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static android.content.Context.SENSOR_SERVICE;

public class MainFragment extends Fragment {

    private CheckBox showWind;
    private CheckBox showPressure;
    private CheckBox showHumidity;
    private EditText theCity;

    private SensorManager sensorManager;
    private Sensor sensorTemperature;
    private Sensor sensorHumidity;

    private TextView temperatureSensorInfo;
    private TextView humiditySensorInfo;

    private SharedPreferences preferences;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main_fragment,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        initPreferences(view);
        view.findViewById(R.id.showWeather).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePreferences();
                startSecondActivity(theCity.getText().toString(), showWind.isChecked(), showPressure.isChecked(), showHumidity.isChecked());
            }
        });

        temperatureSensorInfo = view.findViewById(R.id.temperatureSensorInfo);
        humiditySensorInfo = view.findViewById(R.id.humiditySensorInfo);

        sensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);
        sensorTemperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        sensorHumidity = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        sensorManager.registerListener(listenerTemperature, sensorTemperature,
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(listenerHumidity, sensorHumidity,
                SensorManager.SENSOR_DELAY_NORMAL);


        view.findViewById(R.id.showSensorsInfo).setOnClickListener(new View.OnClickListener() {
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

        view.findViewById(R.id.starService).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startService(new Intent(getActivity(), WeatherService.class));
                getActivity().getSupportFragmentManager().beginTransaction().add(android.R.id.content, new FragmentBrowser()).commit();

            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    private void savePreferences() {
        preferences.edit().putString("THE_CITY", theCity.getText().toString()).apply();
        preferences.edit().putBoolean("WIND_IS_CHECKED", showWind.isChecked()).apply();
        preferences.edit().putBoolean("PRESSURE_IS_CHECKED", showPressure.isChecked()).apply();
        preferences.edit().putBoolean("HUMIDITY_IS_CHECKED", showHumidity.isChecked()).apply();
    }

    private void initPreferences (View view) {
        preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        theCity = view.findViewById(R.id.theCity);
        theCity.setText(preferences.getString("THE_CITY", "Введите ваш город"));
        showWind = view.findViewById(R.id.showWind);
        showWind.setChecked(preferences.getBoolean("WIND_IS_CHECKED", true));
        showPressure = view.findViewById(R.id.showPressure);
        showPressure.setChecked(preferences.getBoolean("PRESSURE_IS_CHECKED", true));
        showHumidity = view.findViewById(R.id.showHumidity);
        showHumidity.setChecked(preferences.getBoolean("HUMIDITY_IS_CHECKED", true));
    }

    void startSecondActivity(String city, boolean isShowWindChecked, boolean isShowPressureChecked, boolean isShowHumidityChecked){
        Intent showTheCityWeather = new Intent(getContext(), TheCityWeather.class);
        Parcel parcel = new Parcel();
        parcel.setParams(city, isShowWindChecked, isShowPressureChecked, isShowHumidityChecked);
        showTheCityWeather.putExtra("PARCEL", parcel);
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
