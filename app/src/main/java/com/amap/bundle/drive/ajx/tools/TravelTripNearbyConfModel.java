package com.amap.bundle.drive.ajx.tools;

import android.text.TextUtils;
import com.alipay.mobile.h5container.api.H5Param;
import org.json.JSONObject;

public class TravelTripNearbyConfModel {
    public String icon;
    public String id;
    public int is_new = 1;
    public String label;
    public Action mAction = new Action();
    public String name;
    public int position;
    public int type;

    public static class Action {
        public int type;
        public String url;
    }

    public boolean isNews() {
        return this.is_new == 1;
    }

    public void setIsNews(boolean z) {
        if (z) {
            this.is_new = 1;
        } else {
            this.is_new = 0;
        }
    }

    public static TravelTripNearbyConfModel fromJSON(JSONObject jSONObject) {
        String optString = jSONObject.optString("id", "");
        String optString2 = jSONObject.optString("name", "");
        String optString3 = jSONObject.optString(H5Param.MENU_ICON, "");
        int optInt = jSONObject.optInt("position", -1);
        int optInt2 = jSONObject.optInt("type", -1);
        int optInt3 = jSONObject.optInt("is_new", 1);
        JSONObject optJSONObject = jSONObject.optJSONObject("action");
        int optInt4 = optJSONObject.optInt("type", -1);
        String optString4 = optJSONObject.optString("url", "");
        String optString5 = jSONObject.optString("label", "");
        TravelTripNearbyConfModel travelTripNearbyConfModel = new TravelTripNearbyConfModel();
        travelTripNearbyConfModel.id = optString;
        travelTripNearbyConfModel.name = optString2;
        travelTripNearbyConfModel.icon = optString3;
        travelTripNearbyConfModel.position = optInt;
        travelTripNearbyConfModel.type = optInt2;
        travelTripNearbyConfModel.is_new = optInt3;
        travelTripNearbyConfModel.label = optString5;
        Action action = new Action();
        action.type = optInt4;
        action.url = optString4;
        travelTripNearbyConfModel.mAction = action;
        return travelTripNearbyConfModel;
    }

    public JSONObject toJSON() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("id", this.id);
            jSONObject.put("name", this.name);
            jSONObject.put(H5Param.MENU_ICON, this.icon);
            jSONObject.put("position", this.position);
            jSONObject.put("type", this.type);
            jSONObject.put("is_new", this.is_new);
            jSONObject.put("label", this.label);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("type", this.mAction.type);
            jSONObject2.put("url", this.mAction.url);
            jSONObject.put("action", jSONObject2);
        } catch (Exception e) {
            kf.a((Throwable) e);
        }
        return jSONObject;
    }

    public JSONObject toServerJSON() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("id", getInt(this.id));
            jSONObject.put("name", this.name);
            jSONObject.put(H5Param.MENU_ICON, this.icon);
            jSONObject.put("position", this.position);
            jSONObject.put("type", this.type);
            jSONObject.put("is_new", this.is_new);
            jSONObject.put("label", this.label);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("type", this.mAction.type);
            jSONObject2.put("url", this.mAction.url);
            jSONObject.put("action", jSONObject2);
        } catch (Exception e) {
            kf.a((Throwable) e);
        }
        return jSONObject;
    }

    private int getInt(String str) {
        int i;
        if (TextUtils.isEmpty(str)) {
            return -1;
        }
        try {
            i = Integer.parseInt(str);
        } catch (Exception unused) {
            i = -1;
        }
        return i;
    }
}
