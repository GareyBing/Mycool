package com.example.hugb.mycool.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by hugb on 1/25/18.
 */

public class HttpUtil {
    public static void sendOkHttpRequest(String address, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
