package com.autonavi.miniapp.monitor.api;

import android.os.Bundle;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.monitor.BatteryID;
import com.alipay.mobile.common.logging.api.monitor.BatteryModel;
import com.alipay.mobile.common.logging.api.monitor.DataflowID;
import com.alipay.mobile.common.logging.api.monitor.DataflowModel;
import com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus;
import com.autonavi.miniapp.monitor.api.analysis.power.TrafficConsumeInfo;

public class AmapMonitorFactory {
    public static final String TAG = "AmapMonitorFactory";
    private static AmapMonitorContext sMonitorContext = new NullMonitorContext();
    private static AmapTimestampInfo sTimestampInfo = new NullTimestampInfo();

    static class NullMonitorContext implements AmapMonitorContext {
        private NullMonitorContext() {
        }

        public void autoWakeupReceiver() {
            AmapMonitorFactory.reportNoInitialization();
        }

        public void notePowerConsume(BatteryModel batteryModel) {
            AmapMonitorFactory.reportNoInitialization();
        }

        public boolean isPowerConsumeAccept(BatteryID batteryID, String str) {
            AmapMonitorFactory.reportNoInitialization();
            return true;
        }

        public void noteTraficConsume(DataflowModel dataflowModel) {
            AmapMonitorFactory.reportNoInitialization();
        }

        public boolean isTraficConsumeAccept(DataflowID dataflowID, String str) {
            AmapMonitorFactory.reportNoInitialization();
            return true;
        }

        public boolean isTraficConsumeAccept(String str) {
            AmapMonitorFactory.reportNoInitialization();
            return true;
        }

        public void updateTraficDegradeCfg(String str) {
            AmapMonitorFactory.reportNoInitialization();
        }

        public TrafficConsumeInfo loadTrafficConsumeInfo() {
            AmapMonitorFactory.reportNoInitialization();
            return null;
        }

        public void kickOnNetworkBindService(String str, boolean z, String str2) {
            AmapMonitorFactory.reportNoInitialization();
        }

        public void kickOnNetworkDiagnose(boolean z, String str) {
            AmapMonitorFactory.reportNoInitialization();
        }

        public void kickOnSystemBroadcastReceived(String str) {
            AmapMonitorFactory.reportNoInitialization();
        }

        public int autoStartWhitelistStatus() {
            AmapMonitorFactory.reportNoInitialization();
            return 0;
        }

        public int keepAliveWhitelistStatus() {
            AmapMonitorFactory.reportNoInitialization();
            return 0;
        }

        public int recentLockedWhitelistStatus() {
            AmapMonitorFactory.reportNoInitialization();
            return 0;
        }

        public int notificationWhitelistStatus() {
            AmapMonitorFactory.reportNoInitialization();
            return 0;
        }

        public boolean isSmoothnessSampleWork() {
            AmapMonitorFactory.reportNoInitialization();
            return false;
        }

        public void handleSmoothnessEvent(Bundle bundle) {
            AmapMonitorFactory.reportNoInitialization();
        }

        public void uploadLogByManualTrigger(Bundle bundle, UploadTaskStatus uploadTaskStatus) {
            AmapMonitorFactory.reportNoInitialization();
        }

        public void uploadLogByTasks(String str, String str2) {
            AmapMonitorFactory.reportNoInitialization();
        }

        public String[] collectAliveStatus() {
            AmapMonitorFactory.reportNoInitialization();
            return new String[0];
        }

        public void notifyReceiveBootComplete() {
            AmapMonitorFactory.reportNoInitialization();
        }

        public void flushMonitorData() {
            AmapMonitorFactory.reportNoInitialization();
        }
    }

    static class NullTimestampInfo implements AmapTimestampInfo {
        private NullTimestampInfo() {
        }

        public long getDeviceCurrentRebootExactTime() {
            AmapMonitorFactory.reportNoInitialization();
            return 0;
        }

        public long getDeviceCurrentRebootFuzzyTime() {
            AmapMonitorFactory.reportNoInitialization();
            return 0;
        }

        public long getDeviceLatestRebootExactTime() {
            AmapMonitorFactory.reportNoInitialization();
            return 0;
        }

        public long getDeviceLatestRebootFuzzyTime() {
            AmapMonitorFactory.reportNoInitialization();
            return 0;
        }

        public boolean isDeviceRebootRecently() {
            AmapMonitorFactory.reportNoInitialization();
            return false;
        }

        public long getProcessCurrentLaunchNaturalTime() {
            AmapMonitorFactory.reportNoInitialization();
            return 0;
        }

        public long getProcessCurrentLaunchElapsedTime() {
            AmapMonitorFactory.reportNoInitialization();
            return 0;
        }

        public long getProcessPreviousLaunchTime() {
            AmapMonitorFactory.reportNoInitialization();
            return 0;
        }

        public long getClientCurrentStartupTime() {
            AmapMonitorFactory.reportNoInitialization();
            return 0;
        }

        public long getClientPreviousStartupTime() {
            AmapMonitorFactory.reportNoInitialization();
            return 0;
        }

        public boolean isProcessLaunchFirstly() {
            AmapMonitorFactory.reportNoInitialization();
            return false;
        }
    }

    public static AmapMonitorContext getMonitorContext() {
        return sMonitorContext;
    }

    public static AmapTimestampInfo getTimestampInfo() {
        return sTimestampInfo;
    }

    public static void bind(AmapMonitorContext amapMonitorContext, AmapTimestampInfo amapTimestampInfo) {
        if (amapMonitorContext != null) {
            sMonitorContext = amapMonitorContext;
        }
        if (amapTimestampInfo != null) {
            sTimestampInfo = amapTimestampInfo;
        }
        LoggerFactory.getTraceLogger().info(TAG, "AmapMonitorFactory.bind invoked by ".concat(String.valueOf(amapMonitorContext)));
    }

    /* access modifiers changed from: private */
    public static void reportNoInitialization() {
        new IllegalMonitorStateException("need invoke bind before use");
    }
}
