package com.example.hugb.mycool.gson;

/**
 * Created by hugb on 1/28/18.
 */

public class AQI {
    public AQICity city;

    public class AQICity {
        public String aqi;
        public String pm25;
    }
}
