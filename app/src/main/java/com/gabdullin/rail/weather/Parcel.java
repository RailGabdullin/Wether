package com.gabdullin.rail.weather;

import java.io.Serializable;

public class Parcel implements Serializable {

    private String city = "Москва";
    private Boolean showWind = false;
    private Boolean showPressure = false;
    private Boolean showHumidity = false;

    public void setParams(String city, Boolean showWind, Boolean showPressure, Boolean showHumidity) {
        this.city = city;
        this.showWind = showWind;
        this.showPressure = showPressure;
        this.showHumidity = showHumidity;
    }

    public String getCity() {
        return city;
    }

    public Boolean isShowWind() {
        return showWind;
    }

    public Boolean isShowPressure() {
        return showPressure;
    }

    public Boolean isShowHumidity() {
        return showHumidity;
    }
}
