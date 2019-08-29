package com.autonavi.minimap.ajx3.util;

import android.text.TextUtils;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.AjxConstant;
import org.json.JSONException;
import org.json.JSONObject;

public class AjxDebugConfig {
    public static final String AJX_BLUE_BALL_CONFIG = "AJX_BLUE_BALL_CONFIG";
    private static final String DEBUG_SO_FLAG = "100";
    private static final String DEBUG_SO_FLAG_NEW = "debug";
    public static final String JS_DEBUG = "js_debug";
    public static final String PERFORMANCE = "performance";
    public static final String UI_DEBUG = "ui_debug";
    private static volatile AjxDebugConfig mInstance;
    private boolean mJsDebug;
    private String mJsDebugSoPath;
    private boolean mPerformance;
    private boolean mUiDebug;

    public static AjxDebugConfig getInstance() {
        if (mInstance == null) {
            synchronized (AjxDebugConfig.class) {
                try {
                    if (mInstance == null) {
                        mInstance = new AjxDebugConfig();
                    }
                }
            }
        }
        return mInstance;
    }

    private AjxDebugConfig() {
        StringBuilder sb = new StringBuilder();
        sb.append(AMapAppGlobal.getApplication().getFilesDir().getPath());
        sb.append("/libajx_v3.so");
        this.mJsDebugSoPath = sb.toString();
    }

    /* access modifiers changed from: 0000 */
    public void save() {
        new MapSharePreference(SharePreferenceName.SharedPreferences).putStringValue(AJX_BLUE_BALL_CONFIG, toJson());
    }

    /* access modifiers changed from: 0000 */
    public void restore() {
        String stringValue = new MapSharePreference(SharePreferenceName.SharedPreferences).getStringValue(AJX_BLUE_BALL_CONFIG, "");
        if (!TextUtils.isEmpty(stringValue)) {
            try {
                JSONObject jSONObject = new JSONObject(stringValue);
                this.mPerformance = jSONObject.optBoolean(PERFORMANCE, false);
                this.mUiDebug = jSONObject.optBoolean(UI_DEBUG, false);
                this.mJsDebug = jSONObject.optBoolean(JS_DEBUG, false);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        apply();
    }

    private void apply() {
        setPerformanceSwitch(this.mPerformance);
        setUiDebugSwitch(this.mUiDebug);
        setJsDebugSwitch(this.mJsDebug);
    }

    private void setPerformanceSwitch(boolean z) {
        Ajx.getInstance().setPerformanceLogEnabled(z);
    }

    private void setUiDebugSwitch(boolean z) {
        Ajx.getInstance().setEagleEyeEnable(z);
    }

    private void setJsDebugSwitch(boolean z) {
        if (!z) {
            if (Ajx.getInstance().isDebuggerSupported() && (AjxConstant.AAR_VERSION.contains(DEBUG_SO_FLAG) || AjxConstant.AAR_VERSION.contains("debug"))) {
                this.mJsDebug = true;
            }
        } else if (!Ajx.getInstance().isDebuggerSupported()) {
            this.mJsDebug = false;
        }
    }

    private String toJson() {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(PERFORMANCE, this.mPerformance);
            jSONObject.put(UI_DEBUG, this.mUiDebug);
            jSONObject.put(JS_DEBUG, this.mJsDebug);
            return jSONObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    public boolean getPerformance() {
        return this.mPerformance;
    }

    public void setPerformance(boolean z) {
        if (this.mPerformance != z) {
            this.mPerformance = z;
            save();
        }
    }

    public boolean getUiDebug() {
        return this.mUiDebug;
    }

    public void setUiDebug(boolean z) {
        if (this.mUiDebug != z) {
            this.mUiDebug = z;
            save();
        }
    }

    public boolean getJsDebug() {
        return this.mJsDebug;
    }

    public void setJsDebug(boolean z) {
        if (this.mJsDebug != z) {
            this.mJsDebug = z;
            save();
        }
    }
}
