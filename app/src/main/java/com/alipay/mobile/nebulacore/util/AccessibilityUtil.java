package com.alipay.mobile.nebulacore.util;

import android.content.ContentResolver;
import android.provider.Settings.Secure;
import android.text.TextUtils;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebulacore.env.H5Environment;

public class AccessibilityUtil {
    private static String a = null;

    public static String getEnabledAccessibilities() {
        if (!TextUtils.isEmpty(a)) {
            return a;
        }
        ContentResolver contentResolver = H5Environment.getContext().getContentResolver();
        try {
            int accessibilityEnabled = Secure.getInt(contentResolver, "accessibility_enabled");
            H5Log.d("AccessibilityUtil", "accessibilityEnabled: " + accessibilityEnabled);
            if (accessibilityEnabled == 1) {
                String enabledServices = Secure.getString(contentResolver, "enabled_accessibility_services");
                String displayInversion = Secure.getString(contentResolver, "accessibility_display_inversion_enabled");
                String speakPassword = Secure.getString(contentResolver, "speak_password");
                String touchExplore = Secure.getString(contentResolver, "touch_exploration_enabled");
                H5Log.d("AccessibilityUtil", "all enabled accessibility services: " + enabledServices);
                H5Log.d("AccessibilityUtil", "displayInversion: " + displayInversion);
                H5Log.d("AccessibilityUtil", "touchExplore: " + touchExplore);
                if ("1".equals(touchExplore)) {
                    a = a(a, "V|");
                }
                if ("1".equals(displayInversion)) {
                    a = a(a, "I|");
                }
                if ("1".equals(speakPassword)) {
                    a = a(a, "sp|");
                }
                if (a != null) {
                    if (a.endsWith(MergeUtil.SEPARATOR_KV)) {
                        a = a.substring(0, a.length() - 1);
                    }
                    if (a.length() > 0) {
                        a = ",ac:" + a;
                    }
                }
            }
            return a;
        } catch (Exception e) {
            H5Log.e((String) "AccessibilityUtil", (String) "get accessibilityEnabled setting exception");
            return a;
        }
    }

    private static String a(String ac, String destS) {
        return ac == null ? destS : ac + destS;
    }
}
