package com.autonavi.miniapp.monitor.api;

import java.util.concurrent.TimeUnit;

public interface AmapTimestampInfo {
    public static final long TIME_FUZZ_SCALE = TimeUnit.MINUTES.toMillis(1);

    long getClientCurrentStartupTime();

    long getClientPreviousStartupTime();

    long getDeviceCurrentRebootExactTime();

    long getDeviceCurrentRebootFuzzyTime();

    long getDeviceLatestRebootExactTime();

    long getDeviceLatestRebootFuzzyTime();

    long getProcessCurrentLaunchElapsedTime();

    long getProcessCurrentLaunchNaturalTime();

    long getProcessPreviousLaunchTime();

    boolean isDeviceRebootRecently();

    boolean isProcessLaunchFirstly();
}
