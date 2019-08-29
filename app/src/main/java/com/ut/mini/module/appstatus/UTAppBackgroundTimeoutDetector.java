package com.ut.mini.module.appstatus;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import com.alibaba.analytics.AnalyticsMgr;
import com.ut.mini.UTAnalytics;
import java.util.HashMap;

public class UTAppBackgroundTimeoutDetector implements UTAppStatusCallbacks {
    private static final long TIMEOUT = 600000;
    private static UTAppBackgroundTimeoutDetector s_instance;
    private long mSwitchBackgroundTimestamp = 0;

    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    public void onActivityDestroyed(Activity activity) {
    }

    public void onActivityPaused(Activity activity) {
    }

    public void onActivityResumed(Activity activity) {
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    public void onActivityStarted(Activity activity) {
    }

    public void onActivityStopped(Activity activity) {
    }

    private UTAppBackgroundTimeoutDetector() {
    }

    public static synchronized UTAppBackgroundTimeoutDetector getInstance() {
        UTAppBackgroundTimeoutDetector uTAppBackgroundTimeoutDetector;
        synchronized (UTAppBackgroundTimeoutDetector.class) {
            try {
                if (s_instance == null) {
                    s_instance = new UTAppBackgroundTimeoutDetector();
                }
                uTAppBackgroundTimeoutDetector = s_instance;
            }
        }
        return uTAppBackgroundTimeoutDetector;
    }

    public void onSwitchBackground() {
        this.mSwitchBackgroundTimestamp = SystemClock.elapsedRealtime();
    }

    public void onSwitchForeground() {
        if (0 != this.mSwitchBackgroundTimestamp && SystemClock.elapsedRealtime() - this.mSwitchBackgroundTimestamp > TIMEOUT) {
            UTAnalytics.getInstance().sessionTimeout();
            AnalyticsMgr.setSessionProperties(new HashMap());
        }
        this.mSwitchBackgroundTimestamp = 0;
    }
}
