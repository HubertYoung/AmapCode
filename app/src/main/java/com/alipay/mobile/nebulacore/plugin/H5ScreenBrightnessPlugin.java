package com.alipay.mobile.nebulacore.plugin;

import android.app.Activity;
import android.provider.Settings.System;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.common.logging.api.LogCategory;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.util.H5Log;

public class H5ScreenBrightnessPlugin extends H5SimplePlugin {
    public static final String TAG = "H5ScreenBrightnessPlugin";
    private Activity a;

    public void onPrepare(H5EventFilter filter) {
        filter.addAction("setScreenBrightness");
        filter.addAction("getScreenBrightness");
    }

    public void onRelease() {
        this.a = null;
    }

    public boolean handleEvent(H5Event event, H5BridgeContext bridgeContext) {
        String action = event.getAction();
        this.a = event.getActivity();
        if ("getScreenBrightness".equals(action)) {
            JSONObject result = new JSONObject();
            float currentBrightness = a();
            H5Log.d(TAG, "currentBrightness: " + currentBrightness);
            result.put((String) "brightness", (Object) Float.valueOf(currentBrightness));
            result.put((String) "success", (Object) Boolean.valueOf(true));
            bridgeContext.sendBridgeResult(result);
        } else if ("setScreenBrightness".equals(action)) {
            JSONObject param = event.getParam();
            if (!param.containsKey("brightness")) {
                bridgeContext.sendError(event, Error.INVALID_PARAM);
            } else {
                try {
                    float setBrightness = param.getFloat("brightness").floatValue();
                    H5Log.d(TAG, "setBrightness: " + setBrightness);
                    if (setBrightness < 0.0f || setBrightness > 1.0f) {
                        bridgeContext.sendError(event, Error.INVALID_PARAM);
                    } else {
                        a(setBrightness);
                        bridgeContext.sendBridgeResult("success", Boolean.valueOf(true));
                    }
                } catch (Exception e) {
                    H5Log.e(TAG, LogCategory.CATEGORY_EXCEPTION, e);
                    bridgeContext.sendError(event, Error.INVALID_PARAM);
                }
            }
        }
        return true;
    }

    private void a(float value) {
        try {
            H5Log.d(TAG, "value before" + value + " value after" + (value * 255.0f));
            float value2 = value * 255.0f;
            Window mWindow = this.a.getWindow();
            LayoutParams mParams = mWindow.getAttributes();
            mParams.screenBrightness = value2 / 255.0f;
            mWindow.setAttributes(mParams);
            System.putInt(this.a.getContentResolver(), "screen_brightness", (int) value2);
        } catch (Exception e) {
            H5Log.e(TAG, LogCategory.CATEGORY_EXCEPTION, e);
        }
    }

    private float a() {
        try {
            return ((float) System.getInt(this.a.getContentResolver(), "screen_brightness")) / 255.0f;
        } catch (Exception e) {
            H5Log.e(TAG, LogCategory.CATEGORY_EXCEPTION, e);
            return 0.0f;
        }
    }
}
