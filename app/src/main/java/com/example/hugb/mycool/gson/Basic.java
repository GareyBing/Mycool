package com.example.hugb.mycool.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hugb on 1/28/18.
 */

public class Basic {
    @SerializedName("city")
    public String cityName;

    @SerializedName("id")
    public String weatherId;

    public Update update;

    public class Update {
        @SerializedName("loc")
        public String updateTime;
    }

}
