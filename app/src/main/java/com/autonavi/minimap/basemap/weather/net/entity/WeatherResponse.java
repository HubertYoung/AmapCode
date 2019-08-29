package com.autonavi.minimap.basemap.weather.net.entity;

import com.amap.bundle.network.response.AosParserResponse;
import org.json.JSONObject;

public class WeatherResponse extends AosParserResponse {
    public String a;
    public String l;
    public String m;
    public String n;
    public JSONObject o;

    public final String b() {
        return "";
    }

    /* renamed from: a */
    public final byte[] parseResult() {
        super.parseResult();
        if (this.j) {
            this.a = this.k.optString("weather_condition");
            this.l = this.k.optString("aqi_quality_level");
            this.m = this.k.optString("update_time");
            this.o = this.k.optJSONObject("animation");
            JSONObject optJSONObject = this.k.optJSONObject("aqi");
            if (optJSONObject != null) {
                this.n = optJSONObject.optString("value");
            }
        }
        return (byte[]) getResult();
    }
}
