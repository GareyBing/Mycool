package com.example.hugb.mycool.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hugb on 1/28/18.
 */

public class Weather {

    public String status;
    public Basic basic;
    public AQI aqi;
    public Now now;
    public Suggestion suggestion;

    @SerializedName("daily_forecast")
    public List<Forecast> forecastList;
}