package com.gabdullin.rail.weather;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TheCityWeatherFragment extends Fragment {

    public static TheCityWeatherFragment init(Parcel parcel){
        TheCityWeatherFragment theCityWeatherFragment = new TheCityWeatherFragment();
        Bundle args = new Bundle();
        args.putSerializable("PARCEL", parcel);
        theCityWeatherFragment.setArguments(args);
        return theCityWeatherFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.the_city_weather_fragment, container, false);

        Parcel parcel = getParcel();

        TextView theCity = view.findViewById(R.id.city);
        theCity.setText(parcel.getCity());

        if(parcel.isShowWind()){
            view.findViewById(R.id.showWind).setVisibility(View.VISIBLE);
            view.findViewById(R.id.showWindParam).setVisibility(View.VISIBLE);
        }

        if(parcel.isShowPressure()){
            view.findViewById(R.id.showPressure).setVisibility(View.VISIBLE);
            view.findViewById(R.id.showPressureParam).setVisibility(View.VISIBLE);
        }

        if(parcel.isShowHumidity()){
            view.findViewById(R.id.showHumidity).setVisibility(View.VISIBLE);
            view.findViewById(R.id.showHumidityParam).setVisibility(View.VISIBLE);
        }
        return view;
    }

    private Parcel getParcel() {
        Parcel parcel = (Parcel) getArguments().getSerializable("PARCEL");
        return parcel;
    }
}
