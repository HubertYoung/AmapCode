package com.amap.bundle.statistics.util;

import android.app.Application;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import org.json.JSONException;
import org.json.JSONObject;

public class LogUtil {
    public static String switchActionLogInfo(boolean z) {
        Application application;
        int i;
        if (z) {
            application = AMapAppGlobal.getApplication();
            i = R.string.actionlog_on;
        } else {
            application = AMapAppGlobal.getApplication();
            i = R.string.actionlog_off;
        }
        return application.getString(i);
    }

    public static void actionLogV2(String str, String str2, JSONObject jSONObject) {
        if (jSONObject == null) {
            LogManager.actionLogV2(str, str2);
        } else {
            LogManager.actionLogV2(str, str2, jSONObject);
        }
    }

    public static JSONObject createPairJSONObj(String str, String str2) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(str, str2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    public static JSONObject createJSONObj(int i) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("type", i);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    public static JSONObject createJSONObj(String str) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("type", str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    public static JSONObject createJSONObj(String str, String str2) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("type", str);
            jSONObject.put(TrafficUtil.KEYWORD, str2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    public static JSONObject createJSONObj(String str, String str2, String str3) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("type", str);
            jSONObject.put(TrafficUtil.KEYWORD, str2);
            jSONObject.put("status", str3);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }
}
