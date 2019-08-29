package com.autonavi.minimap.route.train.stationlist;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.autonavi.sdk.location.LocationInstrument;
import org.json.JSONObject;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class Station {
    public String a1;
    public String coachAgentID;
    public String index;
    public String initial;
    public double lat;
    public double lon;
    public String name;
    public String pinyin;
    public String poiid;

    public Station() {
    }

    public Station(lj ljVar) {
        GeoPoint geoPoint = new GeoPoint(ljVar.f, ljVar.g);
        this.lon = geoPoint.getLongitude();
        this.lat = geoPoint.getLatitude();
        this.poiid = "";
        this.name = ljVar.a;
        if (this.name.endsWith("市")) {
            this.name = this.name.substring(0, this.name.length() - 1);
        } else if (this.name.endsWith("地区")) {
            this.name = this.name.substring(0, this.name.length() - 2);
        }
        this.pinyin = ljVar.b;
        this.initial = ljVar.c;
        StringBuilder sb = new StringBuilder();
        sb.append(ljVar.j);
        this.a1 = sb.toString();
        this.index = ljVar.c.substring(0, 1).toLowerCase();
    }

    public Station(JSONObject jSONObject) {
        this.lon = jSONObject.optDouble(LocationParams.PARA_FLP_AUTONAVI_LON);
        this.lat = jSONObject.optDouble("lat");
        this.poiid = jSONObject.optString(LocationInstrument.LOCATION_EXTRAS_KEY_POIID);
        this.name = jSONObject.optString("name");
        this.pinyin = jSONObject.optString(AutoJsonUtils.JSON_PINYIN);
        this.initial = jSONObject.optString("initial");
        this.index = jSONObject.optString("index");
        if (!TextUtils.isEmpty(jSONObject.optString("a1"))) {
            this.a1 = jSONObject.optString("a1");
        }
        String optString = jSONObject.optString("a2");
        if (!TextUtils.isEmpty(optString)) {
            this.a1 = optString;
        }
        if (!TextUtils.isEmpty(optString)) {
            this.a1 = optString;
        }
        this.coachAgentID = jSONObject.optString("coachAgentID");
    }

    public String valueOfA1() {
        if (TextUtils.isEmpty(this.a1)) {
            this.a1 = revertAdCode(this.lon, this.lat);
        }
        return this.a1;
    }

    @Nullable
    public String revertAdCode(double d, double d2) {
        li a = li.a();
        if (!(a == null || d == 0.0d || d2 == 0.0d)) {
            GeoPoint geoPoint = new GeoPoint();
            geoPoint.setLonLat(d, d2);
            lj b = a.b(geoPoint.x, geoPoint.y);
            if (b != null) {
                StringBuilder sb = new StringBuilder();
                sb.append(b.j);
                return sb.toString();
            }
        }
        return "";
    }
}
