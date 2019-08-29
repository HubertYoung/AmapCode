package com.autonavi.minimap.nativesupport.amap;

import android.text.TextUtils;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.minimap.ackor.ackoramap.IBehavior;
import org.json.JSONException;
import org.json.JSONObject;

class Behavior implements IBehavior {
    Behavior() {
    }

    public void log(String str, String str2, String str3) {
        if (TextUtils.isEmpty(str3)) {
            LogManager.actionLogV2(str, str2);
            return;
        }
        try {
            LogManager.actionLogV2(str, str2, new JSONObject(str3));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
