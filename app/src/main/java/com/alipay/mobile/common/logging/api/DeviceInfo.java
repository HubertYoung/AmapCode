package com.alipay.mobile.common.logging.api;

import android.content.Context;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;

public class DeviceInfo {
    private static DeviceInfo a;
    private int b;
    private int c;
    private Context d;
    private boolean e = false;

    private DeviceInfo(Context context) {
        this.d = context;
    }

    public static DeviceInfo getInstance(Context context) {
        if (a == null) {
            synchronized (DeviceInfo.class) {
                try {
                    if (a == null) {
                        DeviceInfo deviceInfo = new DeviceInfo(context);
                        a = deviceInfo;
                        deviceInfo.a();
                    }
                }
            }
        }
        return a;
    }

    private void a() {
        DisplayMetrics displayMetrics;
        if (VERSION.SDK_INT >= 17) {
            try {
                Display display = ((WindowManager) this.d.getSystemService(TemplateTinyApp.WINDOW_KEY)).getDefaultDisplay();
                displayMetrics = new DisplayMetrics();
                display.getRealMetrics(displayMetrics);
            } catch (Throwable e2) {
                LoggerFactory.getTraceLogger().error((String) "DeviceInfo_log", e2);
            }
            this.b = displayMetrics.widthPixels;
            this.c = displayMetrics.heightPixels;
            updateAccessibilityState();
        }
        displayMetrics = this.d.getResources().getDisplayMetrics();
        this.b = displayMetrics.widthPixels;
        this.c = displayMetrics.heightPixels;
        updateAccessibilityState();
    }

    public void updateAccessibilityState() {
        try {
            AccessibilityManager am = (AccessibilityManager) this.d.getSystemService("accessibility");
            this.e = am.isEnabled() && am.isTouchExplorationEnabled();
        } catch (Throwable e2) {
            LoggerFactory.getTraceLogger().error((String) "DeviceInfo_log", e2);
        }
    }

    public boolean getIsAccessibilityEnabled() {
        return this.e;
    }

    public int getScreenWidth() {
        return this.b;
    }

    public int getScreenHeight() {
        return this.c;
    }

    public String getResolution() {
        return this.c + DictionaryKeys.CTRLXY_X + this.b;
    }
}
