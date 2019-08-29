package com.alipay.mobile.security.bio.utils;

import android.app.Activity;
import android.content.Context;
import android.provider.Settings.System;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

public class ScreenUtil {
    public static int getScreenBrightness(Context context) {
        char c = 255;
        try {
            return System.getInt(context.getContentResolver(), "screen_brightness");
        } catch (Exception e) {
            BioLog.e(e.toString());
            return c;
        }
    }

    public static void setScreenMode(Context context, int i) {
        try {
            System.putInt(context.getContentResolver(), "screen_brightness_mode", i);
        } catch (Exception e) {
            BioLog.e(e.toString());
        }
    }

    public static void saveScreenBrightness(Context context, int i) {
        try {
            System.putInt(context.getContentResolver(), "screen_brightness", i);
        } catch (Exception e) {
            BioLog.e(e.toString());
        }
    }

    public static void setScreenBrightness(Activity activity, int i) {
        if (activity != null) {
            Window window = activity.getWindow();
            if (window != null) {
                LayoutParams attributes = window.getAttributes();
                attributes.screenBrightness = ((float) i) / 255.0f;
                window.setAttributes(attributes);
            }
        }
    }
}
