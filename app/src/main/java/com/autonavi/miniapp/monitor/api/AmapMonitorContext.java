package com.autonavi.miniapp.monitor.api;

import android.os.Bundle;
import com.alipay.mobile.common.logging.api.monitor.BatteryID;
import com.alipay.mobile.common.logging.api.monitor.BatteryModel;
import com.alipay.mobile.common.logging.api.monitor.DataflowID;
import com.alipay.mobile.common.logging.api.monitor.DataflowModel;
import com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus;
import com.autonavi.miniapp.monitor.api.analysis.power.TrafficConsumeInfo;

public interface AmapMonitorContext {
    public static final int RESULT_ACCURATE = 1;
    public static final int RESULT_ROUGH = 0;
    public static final int STATUS_BE_SURE_NO = 2;
    public static final int STATUS_BE_SURE_YES = 1;
    public static final int STATUS_NOT_SURE = 0;

    int autoStartWhitelistStatus();

    void autoWakeupReceiver();

    String[] collectAliveStatus();

    void flushMonitorData();

    void handleSmoothnessEvent(Bundle bundle);

    boolean isPowerConsumeAccept(BatteryID batteryID, String str);

    boolean isSmoothnessSampleWork();

    boolean isTraficConsumeAccept(DataflowID dataflowID, String str);

    boolean isTraficConsumeAccept(String str);

    int keepAliveWhitelistStatus();

    void kickOnNetworkBindService(String str, boolean z, String str2);

    void kickOnNetworkDiagnose(boolean z, String str);

    void kickOnSystemBroadcastReceived(String str);

    TrafficConsumeInfo loadTrafficConsumeInfo();

    void notePowerConsume(BatteryModel batteryModel);

    void noteTraficConsume(DataflowModel dataflowModel);

    int notificationWhitelistStatus();

    void notifyReceiveBootComplete();

    int recentLockedWhitelistStatus();

    void updateTraficDegradeCfg(String str);

    void uploadLogByManualTrigger(Bundle bundle, UploadTaskStatus uploadTaskStatus);

    void uploadLogByTasks(String str, String str2);
}
