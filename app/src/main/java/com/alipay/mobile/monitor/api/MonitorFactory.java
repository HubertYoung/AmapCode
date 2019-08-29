package com.alipay.mobile.monitor.api;

import android.os.Bundle;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.monitor.BatteryID;
import com.alipay.mobile.common.logging.api.monitor.BatteryModel;
import com.alipay.mobile.common.logging.api.monitor.DataflowID;
import com.alipay.mobile.common.logging.api.monitor.DataflowModel;
import com.alipay.mobile.monitor.analysis.diagnose.UploadTaskStatus;
import com.alipay.mobile.monitor.analysis.power.TrafficConsumeInfo;

public class MonitorFactory {
    public static final String TAG = "MonitorFactory";
    private static MonitorContext sMonitorContext = new NullMonitorContext();
    private static TimestampInfo sTimestampInfo = new NullTimestampInfo();

    static class NullMonitorContext implements MonitorContext {
        private NullMonitorContext() {
        }

        public void autoWakeupReceiver() {
            MonitorFactory.reportNoInitialization();
        }

        public void notePowerConsume(BatteryModel batteryModel) {
            MonitorFactory.reportNoInitialization();
        }

        public boolean isPowerConsumeAccept(BatteryID batteryID, String str) {
            MonitorFactory.reportNoInitialization();
            return true;
        }

        public void noteTraficConsume(DataflowModel dataflowModel) {
            MonitorFactory.reportNoInitialization();
        }

        public boolean isTraficConsumeAccept(DataflowID dataflowID, String str) {
            MonitorFactory.reportNoInitialization();
            return true;
        }

        public boolean isTraficConsumeAccept(String str) {
            MonitorFactory.reportNoInitialization();
            return true;
        }

        public void updateTraficDegradeCfg(String str) {
            MonitorFactory.reportNoInitialization();
        }

        public TrafficConsumeInfo loadTrafficConsumeInfo() {
            MonitorFactory.reportNoInitialization();
            return null;
        }

        public void kickOnNetworkBindService(String str, boolean z, String str2) {
            MonitorFactory.reportNoInitialization();
        }

        public void kickOnNetworkDiagnose(boolean z, String str) {
            MonitorFactory.reportNoInitialization();
        }

        public void kickOnSystemBroadcastReceived(String str) {
            MonitorFactory.reportNoInitialization();
        }

        public int autoStartWhitelistStatus() {
            MonitorFactory.reportNoInitialization();
            return 0;
        }

        public int keepAliveWhitelistStatus() {
            MonitorFactory.reportNoInitialization();
            return 0;
        }

        public int recentLockedWhitelistStatus() {
            MonitorFactory.reportNoInitialization();
            return 0;
        }

        public int notificationWhitelistStatus() {
            MonitorFactory.reportNoInitialization();
            return 0;
        }

        public boolean isSmoothnessSampleWork() {
            MonitorFactory.reportNoInitialization();
            return false;
        }

        public void handleSmoothnessEvent(Bundle bundle) {
            MonitorFactory.reportNoInitialization();
        }

        public void uploadLogByManualTrigger(Bundle bundle, UploadTaskStatus uploadTaskStatus) {
            MonitorFactory.reportNoInitialization();
        }

        public String[] collectAliveStatus() {
            MonitorFactory.reportNoInitialization();
            return new String[0];
        }

        public void notifyReceiveBootComplete() {
            MonitorFactory.reportNoInitialization();
        }

        public void flushMonitorData() {
            MonitorFactory.reportNoInitialization();
        }
    }

    static class NullTimestampInfo implements TimestampInfo {
        private NullTimestampInfo() {
        }

        public long getDeviceCurrentRebootExactTime() {
            MonitorFactory.reportNoInitialization();
            return 0;
        }

        public long getDeviceCurrentRebootFuzzyTime() {
            MonitorFactory.reportNoInitialization();
            return 0;
        }

        public long getDeviceLatestRebootExactTime() {
            MonitorFactory.reportNoInitialization();
            return 0;
        }

        public long getDeviceLatestRebootFuzzyTime() {
            MonitorFactory.reportNoInitialization();
            return 0;
        }

        public boolean isDeviceRebootRecently() {
            MonitorFactory.reportNoInitialization();
            return false;
        }

        public long getProcessCurrentLaunchNaturalTime() {
            MonitorFactory.reportNoInitialization();
            return 0;
        }

        public long getProcessCurrentLaunchElapsedTime() {
            MonitorFactory.reportNoInitialization();
            return 0;
        }

        public long getProcessPreviousLaunchTime() {
            MonitorFactory.reportNoInitialization();
            return 0;
        }

        public long getClientCurrentStartupTime() {
            MonitorFactory.reportNoInitialization();
            return 0;
        }

        public long getClientPreviousStartupTime() {
            MonitorFactory.reportNoInitialization();
            return 0;
        }

        public boolean isProcessLaunchFirstly() {
            MonitorFactory.reportNoInitialization();
            return false;
        }
    }

    public static MonitorContext getMonitorContext() {
        return sMonitorContext;
    }

    public static TimestampInfo getTimestampInfo() {
        return sTimestampInfo;
    }

    public static void bind(MonitorContext monitorContext, TimestampInfo timestampInfo) {
        if (monitorContext != null) {
            sMonitorContext = monitorContext;
        }
        if (timestampInfo != null) {
            sTimestampInfo = timestampInfo;
        }
        LoggerFactory.getTraceLogger().info(TAG, "MonitorFactory.bind invoked by ".concat(String.valueOf(monitorContext)));
    }

    /* access modifiers changed from: private */
    public static void reportNoInitialization() {
        new IllegalMonitorStateException("need invoke bind before use");
    }
}
