package com.amap.bundle.drive.ajx.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import org.json.JSONArray;
import org.json.JSONObject;

public class DriveRouteAjxTools {
    private static final String TAG = "DriveRouteAjxTools";

    public static void startCarSettingPage() {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putInt("amap.extra.prefer.from", 1);
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            pageContext.startPageForResult((String) "amap.drive.action.navigation.prefer", pageBundle, 1000);
        }
    }

    public static void startMotorSettingPage() {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putInt("amap.extra.prefer.from", 4);
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            pageContext.startPageForResult((String) "amap.drive.action.navigation.prefer", pageBundle, 1000);
        }
    }

    public static void startTruckSettingPage() {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putInt("amap.extra.prefer.from", 3);
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            pageContext.startPageForResult((String) "amap.drive.action.navigation.prefer", pageBundle, 1000);
        }
    }

    public static void getCarToolBox(Context context, JsFunctionCallback jsFunctionCallback) {
        String str;
        SharedPreferences sharedPreferences = context.getSharedPreferences("route_drive_toolbox", 0);
        sharedPreferences.getString("md5", "");
        String string = sharedPreferences.getString("data", "");
        long j = sharedPreferences.getLong("time", 0);
        sharedPreferences.edit().remove("md5").remove("data").remove("time").apply();
        if (TextUtils.isEmpty(string)) {
            if (jsFunctionCallback != null) {
                jsFunctionCallback.callback("");
            }
            return;
        }
        try {
            JSONArray jSONArray = new JSONArray(string);
            JSONArray jSONArray2 = new JSONArray();
            JSONObject jSONObject = new JSONObject();
            if (jSONArray.length() > 0) {
                for (int i = 0; i < jSONArray.length(); i++) {
                    JSONObject optJSONObject = jSONArray.optJSONObject(i);
                    if (optJSONObject != null) {
                        jSONArray2.put(TravelTripNearbyConfModel.fromJSON(optJSONObject).toServerJSON());
                    }
                }
            }
            jSONObject.put("time", j);
            jSONObject.put("data", jSONArray2);
            str = jSONObject.toString();
        } catch (Exception e) {
            AMapLog.e(TAG, String.valueOf(e));
            str = string;
        }
        if (jsFunctionCallback != null) {
            jsFunctionCallback.callback(str);
        }
    }
}
