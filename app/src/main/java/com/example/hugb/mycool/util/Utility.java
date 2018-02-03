package com.example.hugb.mycool.util;

import android.text.TextUtils;
import android.util.Log;

import com.example.hugb.mycool.db.City;
import com.example.hugb.mycool.db.County;
import com.example.hugb.mycool.db.Province;
import com.example.hugb.mycool.gson.Weather;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hugb on 1/25/18.
 */

public class Utility {
    /*parse data and province data from server return*/

    public static boolean handleProvinceResponse(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allProvince = new JSONArray(response);
                for (int i = 0; i < allProvince.length(); i++) {
                    JSONObject provinceObject = allProvince.getJSONObject(i);
                    Province province = new Province();
                    province.setProvinceName(provinceObject.getString("name"));
                    province.setProvinceCode(provinceObject.getInt("id"));
                    province.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();

            }
        }

        return false;
    }

    /*parse data and City data from server data*/

    public static boolean handleCityResponse(String response, int provinceId) {
        Log.i("hgb", "handleCityResponse" + "response = "+response
        +"!TextUtils.isEmpty(response) = "+(!TextUtils.isEmpty(response)));

        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allCities = new JSONArray(response);
                for (int i = 0; i < allCities.length(); i++) {
                    JSONObject cityObject = allCities.getJSONObject(i);
                    City city = new City();
                    city.setCityName(cityObject.getString("name"));
                    city.setCityCode(cityObject.getInt("id"));
                    city.setProvinceId(provinceId);
                    city.save();
                }

                return true;
            } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return false;
        }

    /*parse data and county data from server return*/
    public static boolean handleCountyResponse(String response, int cityId) {
        Log.i("hgb", "handleCountyResponse" + "  response = "+response
                +"!TextUtils.isEmpty(response) = "+(!TextUtils.isEmpty(response)));

        if(!TextUtils.isEmpty(response)) {
            try {
                JSONArray allCounties = new JSONArray(response);
                for (int i = 0; i < response.length(); i++) {
                    JSONObject countyObject = allCounties.getJSONObject(i);
                    County county = new County();
                    county.setCountyName(countyObject.getString("name"));
                    county.setWeatherId(countyObject.getString("weather_id"));
                    county.setCityId(cityId);
                    county.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    /*
    * 将返回的json数据解析成weather 实体类
    * */

    public static Weather handleWeatherResponse (String respone) {
        try {
            JSONObject jsonObject = new JSONObject(respone);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather");
            String weatherContent = jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(weatherContent, Weather.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}


