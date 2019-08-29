package com.alipay.mobile.quinox.utils;

import java.util.HashSet;
import java.util.Set;

public class AppState {
    private static volatile boolean sPreloadActivityEntry = false;
    private static final Set<Callback<Void>> sPreloadActivityFinishCallback = new HashSet();
    private static volatile boolean sPreloadActivityLaunch = false;
    private static volatile boolean sPreloadingActivity = false;

    public static void setPreloadActivityLaunch(boolean z) {
        sPreloadActivityLaunch = z;
    }

    public static boolean isPreloadActivityLaunch() {
        return sPreloadActivityLaunch;
    }

    public static synchronized void setPreloadingActivity(boolean z) {
        synchronized (AppState.class) {
            if (sPreloadingActivity != z) {
                sPreloadingActivity = z;
                if (isPreloadActivityLaunch() && !sPreloadingActivity) {
                    for (Callback<Void> onCallback : sPreloadActivityFinishCallback) {
                        onCallback.onCallback(null);
                    }
                    sPreloadActivityFinishCallback.clear();
                }
            }
        }
    }

    public static synchronized boolean isPreloadingActivity() {
        boolean z;
        synchronized (AppState.class) {
            z = sPreloadingActivity;
        }
        return z;
    }

    public static boolean isPreloadActivityEntry() {
        return sPreloadActivityEntry;
    }

    public static void setPreloadActivityEntry(boolean z) {
        sPreloadActivityEntry = z;
    }

    public static synchronized void addPreloadActivityFinishCallback(Callback<Void> callback) {
        synchronized (AppState.class) {
            sPreloadActivityFinishCallback.add(callback);
        }
    }
}
