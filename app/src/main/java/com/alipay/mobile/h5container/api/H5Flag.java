package com.alipay.mobile.h5container.api;

import android.text.TextUtils;
import java.util.HashMap;

public class H5Flag {
    public static boolean hasShowLoading = false;
    public static boolean initUcNormal = true;
    public static boolean isInChane = true;
    public static boolean isUploadLog = true;
    public static long lastTouchTime;
    public static Boolean sInjectDebugConsoleJS = Boolean.valueOf(false);
    public static HashMap<String, Boolean> sOpenAuthGrantFlag = new HashMap<>();
    public static boolean ucReady = false;
    public static volatile boolean useSysWebView = false;

    public static synchronized boolean getOpenAuthGrantFlag(String sessionId) {
        boolean flag;
        synchronized (H5Flag.class) {
            flag = false;
            if (sOpenAuthGrantFlag.containsKey(sessionId)) {
                flag = sOpenAuthGrantFlag.get(sessionId).booleanValue();
            }
        }
        return flag;
    }

    public static synchronized void setOpenAuthGrantFlag(String sessionId, boolean flag) {
        synchronized (H5Flag.class) {
            if (!TextUtils.isEmpty(sessionId)) {
                sOpenAuthGrantFlag.put(sessionId, Boolean.valueOf(flag));
            }
        }
    }
}
