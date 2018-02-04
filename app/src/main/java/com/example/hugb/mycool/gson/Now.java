package com.example.hugb.mycool.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hugb on 1/28/18.
 */

public class Now {
    @SerializedName("tmp")
    public  String temperature;

    @SerializedName("cond")
    public More more;

    public class More {
        @SerializedName("txt")
        public String info;
    }
}
