package com.autonavi.miniapp.monitor.biz;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.monitor.BatteryID;
import com.alipay.mobile.common.logging.api.monitor.BatteryModel;
import com.alipay.mobile.common.logging.api.monitor.DataflowID;
import com.alipay.mobile.common.logging.api.monitor.DataflowModel;
import com.alipay.tianyan.mobilesdk.TianyanLoggingDelegator;
import com.alipay.tianyan.mobilesdk.TianyanLoggingDelegator.CommonSimpleDelegate;
import com.alipay.tianyan.mobilesdk.TianyanLoggingDelegator.MonitorContextDelegate;
import com.autonavi.miniapp.monitor.api.AmapMonitorContext;
import com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus;
import com.autonavi.miniapp.monitor.api.analysis.power.TrafficConsumeInfo;
import com.autonavi.miniapp.monitor.biz.logmonitor.analysis.ClassToBundleHandler;
import com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician;
import com.autonavi.miniapp.monitor.util.NetUtils;
import java.util.concurrent.TimeUnit;

public class MonitorContextImpl implements AmapMonitorContext {
    private static final String ACTION_PUSH_CHECK_TIMER = ".action.monitor.wakeup";
    private static final long NEXT_WAKEUP_TIME = TimeUnit.MINUTES.toMillis(20);
    private static final String TAG = "AmapMonitorContext";
    private Context mContext;

    public int autoStartWhitelistStatus() {
        return 0;
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

    public MonitorContextImpl(Context context) {
        this.mContext = context;
    }

    public void onProcessLaunch() {
        if (LoggerFactory.getProcessInfo().isMainProcess()) {
            registerForMainProcess();
        } else if (LoggerFactory.getProcessInfo().isPushProcess()) {
            registerForPushProcess();
        }
        registerForAllProcess();
    }

    private void registerForMainProcess() {
        AnonymousClass1 r0 = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                MonitorContextImpl.this.onCommonReceive(context, intent);
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.ACTION_SHUTDOWN");
        try {
            this.mContext.registerReceiver(r0, intentFilter);
        } catch (Throwable th) {
            LoggerFactory.getTraceLogger().error(TAG, "register common receiver", th);
        }
    }

    private void registerForPushProcess() {
        AnonymousClass2 r0 = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                MonitorContextImpl.this.onCommonReceive(context, intent);
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.ACTION_SHUTDOWN");
        intentFilter.addAction("android.intent.action.ACTION_POWER_CONNECTED");
        intentFilter.addAction("android.intent.action.ACTION_POWER_DISCONNECTED");
        try {
            this.mContext.registerReceiver(r0, intentFilter);
        } catch (Throwable th) {
            LoggerFactory.getTraceLogger().error(TAG, "register common receiver", th);
        }
    }

    private void registerForAllProcess() {
        final ClassToBundleHandler classToBundleHandler = new ClassToBundleHandler(this.mContext);
        TianyanLoggingDelegator.putCommonSimpleDelegate(new CommonSimpleDelegate() {
            public void acceptTimeTicksMadly() {
            }

            public String getBundleByClass(String str) {
                return classToBundleHandler.getBundleByClass(str);
            }
        });
        TianyanLoggingDelegator.putMonitorContextDelegate(new MonitorContextDelegate() {
            public void notePowerConsume(BatteryModel batteryModel) {
                MonitorContextImpl.this.notePowerConsume(batteryModel);
            }

            public boolean isPowerConsumeAccept(BatteryID batteryID, String str) {
                return MonitorContextImpl.this.isPowerConsumeAccept(batteryID, str);
            }

            public void noteTraficConsume(DataflowModel dataflowModel) {
                MonitorContextImpl.this.noteTraficConsume(dataflowModel);
            }

            public boolean isTraficConsumeAccept(DataflowID dataflowID, String str) {
                return MonitorContextImpl.this.isTraficConsumeAccept(dataflowID, str);
            }
        });
    }

    /* access modifiers changed from: private */
    public void onCommonReceive(Context context, Intent intent) {
        if (intent != null) {
            LoggerFactory.getTraceLogger().info(TAG, "onCommonReceive: ".concat(String.valueOf(intent)));
            String action = intent.getAction();
            if ("android.intent.action.ACTION_SHUTDOWN".equals(action)) {
                LoggerFactory.getLogContext().flush("applog", false);
                LoggerFactory.getLogContext().flush(null, false);
                return;
            }
            LoggerFactory.getTraceLogger().error((String) TAG, "no such action: ".concat(String.valueOf(action)));
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(8:1|2|3|4|5|6|(1:9)|10) */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0061, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:?, code lost:
        com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger().warn((java.lang.String) TAG, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x006c, code lost:
        return;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x003a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void autoWakeupReceiver() {
        /*
            r8 = this;
            monitor-enter(r8)
            com.alipay.mobile.common.logging.api.trace.TraceLogger r0 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x006d }
            java.lang.String r1 = "AmapMonitorContext"
            java.lang.String r2 = "autoWakeupReceiver"
            r0.warn(r1, r2)     // Catch:{ all -> 0x006d }
            android.content.Intent r0 = new android.content.Intent     // Catch:{ all -> 0x006d }
            r0.<init>()     // Catch:{ all -> 0x006d }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x006d }
            r1.<init>()     // Catch:{ all -> 0x006d }
            android.content.Context r2 = r8.mContext     // Catch:{ all -> 0x006d }
            java.lang.String r2 = r2.getPackageName()     // Catch:{ all -> 0x006d }
            r1.append(r2)     // Catch:{ all -> 0x006d }
            java.lang.String r2 = ".action.monitor.wakeup"
            r1.append(r2)     // Catch:{ all -> 0x006d }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x006d }
            r0.setAction(r1)     // Catch:{ all -> 0x006d }
            java.lang.String r1 = "autoWakeup"
            r2 = 1
            r0.putExtra(r1, r2)     // Catch:{ all -> 0x006d }
            android.content.Context r1 = r8.mContext     // Catch:{ Throwable -> 0x003a }
            java.lang.String r1 = r1.getPackageName()     // Catch:{ Throwable -> 0x003a }
            r0.setPackage(r1)     // Catch:{ Throwable -> 0x003a }
        L_0x003a:
            android.content.Context r1 = r8.mContext     // Catch:{ Throwable -> 0x0061 }
            java.lang.String r2 = "alarm"
            java.lang.Object r1 = r1.getSystemService(r2)     // Catch:{ Throwable -> 0x0061 }
            android.app.AlarmManager r1 = (android.app.AlarmManager) r1     // Catch:{ Throwable -> 0x0061 }
            android.content.Context r2 = r8.mContext     // Catch:{ Throwable -> 0x0061 }
            r3 = 100
            r4 = 0
            android.app.PendingIntent r0 = android.app.PendingIntent.getBroadcast(r2, r3, r0, r4)     // Catch:{ Throwable -> 0x0061 }
            if (r1 == 0) goto L_0x005f
            if (r0 == 0) goto L_0x005f
            r1.cancel(r0)     // Catch:{ Throwable -> 0x0061 }
            long r2 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0061 }
            long r5 = NEXT_WAKEUP_TIME     // Catch:{ Throwable -> 0x0061 }
            r7 = 0
            long r2 = r2 + r5
            r1.set(r4, r2, r0)     // Catch:{ Throwable -> 0x0061 }
        L_0x005f:
            monitor-exit(r8)
            return
        L_0x0061:
            r0 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r1 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x006d }
            java.lang.String r2 = "AmapMonitorContext"
            r1.warn(r2, r0)     // Catch:{ all -> 0x006d }
            monitor-exit(r8)
            return
        L_0x006d:
            r0 = move-exception
            monitor-exit(r8)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.miniapp.monitor.biz.MonitorContextImpl.autoWakeupReceiver():void");
    }

    public void kickOnNetworkBindService(String str, boolean z, String str2) {
        StringBuilder sb = new StringBuilder("kickOnNetworkBindService");
        sb.append(", serviceName: ");
        sb.append(str);
        sb.append(", isBindFailed: ");
        sb.append(z);
        sb.append(", failedReason: ");
        sb.append(str2);
        LoggerFactory.getTraceLogger().info(TAG, sb.toString());
        if (z) {
        }
    }

    public void kickOnNetworkDiagnose(boolean z, String str) {
        StringBuilder sb = new StringBuilder("kickOnNetworkDiagnose");
        sb.append(", isNetworkBroken: ");
        sb.append(z);
        sb.append(", brokenReason: ");
        sb.append(str);
        LoggerFactory.getTraceLogger().info(TAG, sb.toString());
        if (z && NetUtils.isNetworkConnected()) {
        }
    }

    public void kickOnSystemBroadcastReceived(String str) {
        LoggerFactory.getTraceLogger().info(TAG, "kickOnSystemBroadcastReceived, actionName: ".concat(String.valueOf(str)));
    }

    public void uploadLogByManualTrigger(Bundle bundle, UploadTaskStatus uploadTaskStatus) {
        UserDiagnostician.getInstance().processManualTrigger(bundle, uploadTaskStatus);
    }

    public void uploadLogByTasks(String str, String str2) {
        UserDiagnostician.getInstance().processConfigMsg(str, str2);
    }

    public String[] collectAliveStatus() {
        return new String[0];
    }
}
