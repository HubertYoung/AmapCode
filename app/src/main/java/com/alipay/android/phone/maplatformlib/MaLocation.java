package com.alipay.android.phone.maplatformlib;

import android.text.TextUtils;
import com.autonavi.gdtaojin.camera.CameraControllerManager;
import org.json.JSONObject;

public class MaLocation {
    public String accuracy;
    public String altitude;
    public String latitude;
    public String longitude;
    public long time;

    public String toJsonString() {
        JSONObject jsonObject = new JSONObject();
        try {
            if (!TextUtils.isEmpty(this.accuracy)) {
                jsonObject.put(CameraControllerManager.MY_POILOCATION_ACR, this.accuracy);
            }
            if (!TextUtils.isEmpty(this.latitude)) {
                jsonObject.put("latitude", this.latitude);
            }
            if (!TextUtils.isEmpty(this.longitude)) {
                jsonObject.put("longitude", this.longitude);
            }
            if (!TextUtils.isEmpty(this.altitude)) {
                jsonObject.put("altitude", this.altitude);
            }
            jsonObject.put("time", this.time);
            return jsonObject.toString();
        } catch (Exception e) {
            return null;
        }
    }
}
