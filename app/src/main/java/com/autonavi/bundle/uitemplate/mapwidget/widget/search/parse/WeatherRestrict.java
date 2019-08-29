package com.autonavi.bundle.uitemplate.mapwidget.widget.search.parse;

import android.text.TextUtils;
import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherRestrict implements Serializable {
    public String city_flag;
    public String image_url;
    public String jumpSchema;
    public String plate_no;
    public String temp_high;
    public String temp_low;
    public String temperature;

    static class Parser {
        private Parser() {
        }

        public static WeatherRestrict parse(String str) {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            WeatherRestrict weatherRestrict = new WeatherRestrict();
            try {
                JSONObject jSONObject = new JSONObject(str);
                weatherRestrict.image_url = jSONObject.optString("image_url", "");
                weatherRestrict.temperature = jSONObject.optString("temperature", "");
                weatherRestrict.jumpSchema = jSONObject.optString("jump_schema", "");
                JSONObject optJSONObject = jSONObject.optJSONObject("traffic_restrict");
                if (optJSONObject != null) {
                    weatherRestrict.city_flag = optJSONObject.optString("city_flag");
                    weatherRestrict.plate_no = optJSONObject.optString("plate_no");
                }
                weatherRestrict.temp_high = jSONObject.optString("temp_high", "");
                weatherRestrict.temp_low = jSONObject.optString("temp_low", "");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return weatherRestrict;
        }
    }

    private WeatherRestrict() {
    }

    public static WeatherRestrict createInstance(String str) {
        return Parser.parse(str);
    }

    public boolean isTodayNotRestrict() {
        return "1".equals(this.city_flag) && TextUtils.isEmpty(this.plate_no);
    }

    public boolean isHasTrafficRestrict() {
        return ("1".equals(this.city_flag) && !TextUtils.isEmpty(this.plate_no)) || isTodayNotRestrict();
    }

    public boolean isHasWeatherState() {
        return !TextUtils.isEmpty(this.temperature);
    }

    public boolean isHaveTempHighAndLow() {
        return !TextUtils.isEmpty(this.temp_low) && !TextUtils.isEmpty(this.temp_high);
    }

    public String getTempHighAndLow() {
        if (TextUtils.isEmpty(this.temp_low) || TextUtils.isEmpty(this.temp_high)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.temp_low);
        sb.append("°~");
        sb.append(this.temp_high);
        sb.append("°");
        return sb.toString();
    }
}
