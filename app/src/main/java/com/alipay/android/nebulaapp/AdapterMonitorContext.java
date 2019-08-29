package com.alipay.android.nebulaapp;

import android.os.Bundle;
import com.alipay.mobile.common.logging.api.monitor.BatteryID;
import com.alipay.mobile.common.logging.api.monitor.BatteryModel;
import com.alipay.mobile.common.logging.api.monitor.DataflowID;
import com.alipay.mobile.common.logging.api.monitor.DataflowModel;
import com.alipay.mobile.monitor.analysis.diagnose.UploadTaskStatus;
import com.alipay.mobile.monitor.analysis.power.TrafficConsumeInfo;
import com.alipay.mobile.monitor.api.MonitorContext;

public class AdapterMonitorContext implements MonitorContext {
    public int autoStartWhitelistStatus() {
        return 0;
    }

    public void autoWakeupReceiver() {
    }

    public void flushMonitorData() {
    }

    public void handleSmoothnessEvent(Bundle bundle) {
    }

    public boolean isPowerConsumeAccept(BatteryID batteryID, String str) {
        return true;
    }

    public boolean isSmoothnessSampleWork() {
        return false;
    }

    public boolean isTraficConsumeAccept(DataflowID dataflowID, String str) {
        return true;
    }

    public boolean isTraficConsumeAccept(String str) {
        return true;
    }

    public int keepAliveWhitelistStatus() {
        return 0;
    }

    public void kickOnNetworkBindService(String str, boolean z, String str2) {
    }

    public void kickOnNetworkDiagnose(boolean z, String str) {
    }

    public void kickOnSystemBroadcastReceived(String str) {
    }

    public TrafficConsumeInfo loadTrafficConsumeInfo() {
        return null;
    }

    public void notePowerConsume(BatteryModel batteryModel) {
    }

    public void noteTraficConsume(DataflowModel dataflowModel) {
    }

    public int notificationWhitelistStatus() {
        return 0;
    }

    public void notifyReceiveBootComplete() {
    }

    public int recentLockedWhitelistStatus() {
        return 0;
    }

    public void updateTraficDegradeCfg(String str) {
    }

    public void uploadLogByManualTrigger(Bundle bundle, UploadTaskStatus uploadTaskStatus) {
    }

    public String[] collectAliveStatus() {
        return new String[0];
    }
}
