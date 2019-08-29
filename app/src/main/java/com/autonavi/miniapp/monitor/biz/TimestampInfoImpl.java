package com.autonavi.miniapp.monitor.biz;

import android.content.Context;
import android.os.SystemClock;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.autonavi.miniapp.monitor.api.AmapTimestampInfo;
import com.autonavi.miniapp.monitor.biz.logmonitor.util.MonitorSPMulti;
import com.autonavi.miniapp.monitor.biz.logmonitor.util.MonitorSPPrivate;
import com.autonavi.miniapp.monitor.util.MonitorUtils;

public class TimestampInfoImpl implements AmapTimestampInfo {
    private static final String TAG = "AmapTimestampInfo";
    private long clientCurrentStartupTime = -1;
    private long clientPreviousStartupTime = -1;
    private long deviceCurrentRebootExactTime = -1;
    private long deviceCurrentRebootFuzzyTime = -1;
    private long deviceLatestRebootExactTime = -1;
    private long deviceLatestRebootFuzzyTime = -1;
    private Context mContext;
    private long processCurrentLaunchElapsedTime = -1;
    private long processCurrentLaunchNaturalTime = -1;
    private int processLaunchFirstly = -1;
    private long processPreviousLaunchTime = -1;

    public TimestampInfoImpl(Context context) {
        this.mContext = context;
    }

    public long getDeviceCurrentRebootExactTime() {
        if (this.deviceCurrentRebootExactTime < 0) {
            this.deviceCurrentRebootExactTime = System.currentTimeMillis() - SystemClock.elapsedRealtime();
        }
        return this.deviceCurrentRebootExactTime;
    }

    public long getDeviceCurrentRebootFuzzyTime() {
        if (this.deviceCurrentRebootFuzzyTime < 0) {
            this.deviceCurrentRebootFuzzyTime = (getDeviceCurrentRebootExactTime() / TIME_FUZZ_SCALE) * TIME_FUZZ_SCALE;
        }
        return this.deviceCurrentRebootFuzzyTime;
    }

    public long getDeviceLatestRebootExactTime() {
        if (this.deviceLatestRebootExactTime < 0) {
            this.deviceLatestRebootExactTime = MonitorSPMulti.getInstance().getLong(MonitorSPPrivate.DEVICE_REBOOT_TIME, 0);
            MonitorSPMulti.getInstance().putLongCommit(MonitorSPPrivate.DEVICE_REBOOT_TIME, getDeviceCurrentRebootExactTime());
        }
        return this.deviceLatestRebootExactTime;
    }

    public long getDeviceLatestRebootFuzzyTime() {
        if (this.deviceLatestRebootFuzzyTime < 0) {
            this.deviceLatestRebootFuzzyTime = (getDeviceLatestRebootExactTime() / TIME_FUZZ_SCALE) * TIME_FUZZ_SCALE;
        }
        return this.deviceLatestRebootFuzzyTime;
    }

    public boolean isDeviceRebootRecently() {
        return Math.abs(getDeviceCurrentRebootExactTime() - getDeviceLatestRebootExactTime()) > TIME_FUZZ_SCALE;
    }

    public long getProcessCurrentLaunchNaturalTime() {
        if (this.processCurrentLaunchNaturalTime < 0) {
            this.processCurrentLaunchNaturalTime = System.currentTimeMillis();
        }
        return this.processCurrentLaunchNaturalTime;
    }

    public long getProcessCurrentLaunchElapsedTime() {
        if (this.processCurrentLaunchElapsedTime < 0) {
            this.processCurrentLaunchElapsedTime = SystemClock.elapsedRealtime();
        }
        return this.processCurrentLaunchElapsedTime;
    }

    public long getProcessPreviousLaunchTime() {
        if (this.processPreviousLaunchTime < 0) {
            this.processPreviousLaunchTime = MonitorSPPrivate.getInstance().getLong(MonitorSPPrivate.PROCESS_LAUNCH_TIME, 0);
            MonitorSPPrivate.getInstance().putLongApply(MonitorSPPrivate.PROCESS_LAUNCH_TIME, getProcessCurrentLaunchNaturalTime());
        }
        return this.processPreviousLaunchTime;
    }

    public long getClientCurrentStartupTime() {
        if (isntMainProcessAndPrintTraces("getClientCurrentStartupTime")) {
            return 0;
        }
        if (this.clientCurrentStartupTime < 0) {
            this.clientCurrentStartupTime = System.currentTimeMillis();
        }
        return this.clientCurrentStartupTime;
    }

    public long getClientPreviousStartupTime() {
        if (isntMainProcessAndPrintTraces("getClientPreviousStartupTime")) {
            return 0;
        }
        if (this.clientPreviousStartupTime < 0) {
            this.clientPreviousStartupTime = MonitorSPPrivate.getInstance().getLong(MonitorSPPrivate.CLIENT_STARTUP_TIME, 0);
            MonitorSPPrivate.getInstance().putLongApply(MonitorSPPrivate.CLIENT_STARTUP_TIME, getClientCurrentStartupTime());
        }
        return this.clientPreviousStartupTime;
    }

    public boolean isProcessLaunchFirstly() {
        if (this.processLaunchFirstly < 0) {
            this.processLaunchFirstly = MonitorSPPrivate.getInstance().getInt(MonitorSPPrivate.PROCESS_LAUNCH_FIRSTLY, 1);
            if (this.processLaunchFirstly == 1) {
                MonitorSPPrivate.getInstance().putIntApply(MonitorSPPrivate.PROCESS_LAUNCH_FIRSTLY, 0);
            }
        }
        return this.processLaunchFirstly == 1;
    }

    private boolean isntMainProcessAndPrintTraces(String str) {
        if (LoggerFactory.getProcessInfo().isMainProcess()) {
            return false;
        }
        if (!LoggerFactory.getProcessInfo().isLiteProcess()) {
            StringBuilder sb = new StringBuilder();
            sb.append(LoggerFactory.getProcessInfo().getProcessAlias());
            sb.append(" process should not invoke this, ");
            sb.append(str);
            sb.append(", isntMainProcess");
            LoggerFactory.getTraceLogger().warn((String) TAG, MonitorUtils.stackTraceToString(sb.toString()));
        }
        return true;
    }
}
