package com.alipay.android.nebulaapp;

import com.alipay.mobile.monitor.api.TimestampInfo;

public class AdapterTimestampInfo implements TimestampInfo {
    public long getClientCurrentStartupTime() {
        return 0;
    }

    public long getClientPreviousStartupTime() {
        return 0;
    }

    public long getDeviceCurrentRebootExactTime() {
        return 0;
    }

    public long getDeviceCurrentRebootFuzzyTime() {
        return 0;
    }

    public long getDeviceLatestRebootExactTime() {
        return 0;
    }

    public long getDeviceLatestRebootFuzzyTime() {
        return 0;
    }

    public long getProcessCurrentLaunchElapsedTime() {
        return 0;
    }

    public long getProcessCurrentLaunchNaturalTime() {
        return 0;
    }

    public long getProcessPreviousLaunchTime() {
        return 0;
    }

    public boolean isDeviceRebootRecently() {
        return false;
    }

    public boolean isProcessLaunchFirstly() {
        return false;
    }
}
