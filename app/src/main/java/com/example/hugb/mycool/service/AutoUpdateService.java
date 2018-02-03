package com.example.hugb.mycool.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;

import com.example.hugb.mycool.gson.Weather;
import com.example.hugb.mycool.util.HttpUtil;
import com.example.hugb.mycool.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AutoUpdateService extends Service {
    public AutoUpdateService() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        updateWeather();
        updateBingPic();

        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int anHour = 1*60*60*1000;//update frequence one hour
        long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
        Intent myIntent = new Intent(this, AutoUpdateService.class);
        PendingIntent pi = PendingIntent.getService(this, 0, myIntent, 0);
        manager.cancel(pi);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
       return null;
    }

    /*update weather*/
    public void updateWeather() {
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = prefs.getString("weather", null);

        if(weatherString != null) {
            //read date from cache

            Weather weather = Utility.handleWeatherResponse(weatherString);
            String weatherId = weather.basic.weatherId;
            String weatherUrl = "http://guolin.tech/api/weather?city=" + weatherId +
                    "&key=ec0447168bce407c85c28acbd37a739c";

            HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseText = response.body().string();
                    Weather weather = Utility.handleWeatherResponse(responseText);
                    if(weather != null && "ok".equals(weather.status)){
                        SharedPreferences.Editor editor = PreferenceManager.
                                getDefaultSharedPreferences(AutoUpdateService.this).edit();
                        editor.putString("weather", responseText);
                        editor.apply();
                    }
                }
            });
        }
    }

    /*update bingimg*/

    public void updateBingPic() {
        String requestBingPic = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestBingPic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String bingPic = response.body().string();
                SharedPreferences.Editor editor = PreferenceManager.
                        getDefaultSharedPreferences(AutoUpdateService.this).edit();
                editor.putString("bing_pic", bingPic);
                editor.apply();
            }
        });
    }


}
