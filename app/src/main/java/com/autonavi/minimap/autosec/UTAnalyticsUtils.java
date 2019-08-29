package com.autonavi.minimap.autosec;

import android.app.Application;
import java.util.Map;

public class UTAnalyticsUtils {
    public static final String KET_TIMESTAMP = "timestamp";
    public static final String KEY_NAME = "name";
    private static volatile UTAnalyticsUtils sInstance;
    private volatile boolean mIsInit = false;

    public void initUT(Application application) {
    }

    public void userDefinedTracker(String str, Map<String, String> map) {
    }

    private UTAnalyticsUtils() {
    }

    public static UTAnalyticsUtils getInstance() {
        if (sInstance == null) {
            synchronized (UTAnalyticsUtils.class) {
                try {
                    sInstance = new UTAnalyticsUtils();
                }
            }
        }
        return sInstance;
    }
}
