package com.autonavi.miniapp.monitor.biz.logmonitor;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.LogContext;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.trace.TraceLogger;
import com.alipay.mobile.monitor.util.TransUtils;
import com.alipay.tianyan.mobilesdk.TianyanLoggingStatus;
import com.autonavi.miniapp.monitor.biz.apm.util.APMTimer;
import com.autonavi.miniapp.monitor.biz.logmonitor.util.MonitorSPCache;
import com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician;
import java.util.concurrent.TimeUnit;

public class ClientMonitorService extends IntentService {
    /* access modifiers changed from: private */
    public static final String TAG = "ClientMonitorService";

    public ClientMonitorService() {
        super(TAG);
    }

    public void onLowMemory() {
        TianyanLoggingStatus.acceptTimeTicksMadly();
        super.onLowMemory();
    }

    /* access modifiers changed from: protected */
    public void onHandleIntent(Intent intent) {
        TianyanLoggingStatus.acceptTimeTicksMadly();
        if (intent != null) {
            try {
                String packageName = getPackageName();
                StringBuilder sb = new StringBuilder();
                sb.append(packageName);
                sb.append(".monitor.action.upload.mdaplog");
                if (sb.toString().equals(intent.getAction())) {
                    handleIntent_ACTION_UPLOAD_MDAPLOG(intent);
                    return;
                }
                StringBuilder sb2 = new StringBuilder();
                sb2.append(packageName);
                sb2.append(".monitor.action.sync.mdaplog");
                if (sb2.toString().equals(intent.getAction())) {
                    handleIntent_ACTION_SYNC_MDAPLOG(intent);
                    return;
                }
                StringBuilder sb3 = new StringBuilder();
                sb3.append(packageName);
                sb3.append(".push.action.MONITOR_RECEIVED");
                if (sb3.toString().equals(intent.getAction())) {
                    handleIntent_ACTION_DIAGNOSTICIAN(intent);
                    return;
                }
                StringBuilder sb4 = new StringBuilder();
                sb4.append(packageName);
                sb4.append(".monitor.action.UPDATE_LOG_CONTEXT");
                if (sb4.toString().equals(intent.getAction())) {
                    handleIntent_ACTION_UPDATE_LOG_CONTEXT(intent);
                    return;
                }
                StringBuilder sb5 = new StringBuilder();
                sb5.append(packageName);
                sb5.append(".monitor.action.UPDATE_LOG_CONTEXT_BATCH");
                if (sb5.toString().equals(intent.getAction())) {
                    handleIntent_ACTION_UPDATE_LOG_CONTEXT_BATCH(intent);
                    return;
                }
                StringBuilder sb6 = new StringBuilder();
                sb6.append(packageName);
                sb6.append(".monitor.action.UPDATE_LOG_STRATEGY");
                if (sb6.toString().equals(intent.getAction())) {
                    handleIntent_ACTION_UPDATE_LOG_STRATEGY(intent);
                    return;
                }
                StringBuilder sb7 = new StringBuilder();
                sb7.append(packageName);
                sb7.append(".monitor.action.TRACE_NATIVE_CRASH");
                if (sb7.toString().equals(intent.getAction())) {
                    hanldeIntent_ACTION_TRACE_NATIVECRASH(intent);
                }
            } catch (Throwable th) {
                LoggerFactory.getTraceLogger().error(TAG, "intent service", th);
            }
        }
    }

    private void handleIntent_ACTION_UPLOAD_MDAPLOG(Intent intent) {
        LoggerFactory.getLogContext().upload(intent.getExtras().getString("logCategory"));
    }

    private void handleIntent_ACTION_SYNC_MDAPLOG(Intent intent) {
        long currentTimeMillis = System.currentTimeMillis();
        long j = MonitorSPCache.getInstance().getLong(MonitorSPCache.KEY_LAST_SYNC_LOG, 0);
        if ((intent.getExtras() != null && intent.getExtras().getBoolean("isForce")) || Math.abs(currentTimeMillis - j) >= TimeUnit.HOURS.toMillis(3)) {
            MonitorSPCache.getInstance().putLongCommit(MonitorSPCache.KEY_LAST_SYNC_LOG, currentTimeMillis);
            LoggerFactory.getLogContext().traceNativeCrash(null, null, true);
            LoggerFactory.getLogContext().flush(null, false);
            LoggerFactory.getLogContext().flush("applog", false);
            if (!"true".equals(TianyanLoggingStatus.getConfigValueByKey("Monitor_3h_periodCheck_upload_disable", ""))) {
                Bundle bundle = new Bundle();
                bundle.putString("event", LogContext.CLIENT_ENVENT_PERIODCHECK);
                bundle.putBoolean(LogContext.SYNC_ALL_LOG, true);
                LoggerFactory.getLogContext().uploadAfterSync(null, null, bundle);
            }
            return;
        }
        LoggerFactory.getTraceLogger().print(TAG, "sync skip, lastSyncTime=".concat(String.valueOf(j)));
    }

    private void handleIntent_ACTION_DIAGNOSTICIAN(Intent intent) {
        final String action = intent.getAction();
        final String string = intent.getExtras().getString("push_msg_data");
        final String string2 = intent.getExtras().getString("config_msg_tasks");
        final String string3 = intent.getExtras().getString("config_msg_userid");
        APMTimer instance = APMTimer.getInstance();
        AnonymousClass1 r0 = new Runnable() {
            public void run() {
                try {
                    if (!TextUtils.isEmpty(string)) {
                        LoggerFactory.getLogContext().flush(null, false);
                        LoggerFactory.getLogContext().flush("applog", false);
                        UserDiagnostician.getInstance().processPushMsg(string);
                        LoggerFactory.getLogContext().uploadAfterSync(null);
                    } else if (TextUtils.isEmpty(string2) || TextUtils.isEmpty(string3)) {
                        TraceLogger traceLogger = LoggerFactory.getTraceLogger();
                        String access$000 = ClientMonitorService.TAG;
                        StringBuilder sb = new StringBuilder();
                        sb.append(action);
                        sb.append(", but params are incorrect");
                        traceLogger.error(access$000, sb.toString());
                    } else {
                        UserDiagnostician.getInstance().processConfigMsg(string3, string2);
                    }
                } catch (Throwable th) {
                    LoggerFactory.getTraceLogger().error(ClientMonitorService.TAG, ".push.action.MONITOR_RECEIVED", th);
                }
            }
        };
        instance.post(r0);
    }

    private void handleIntent_ACTION_UPDATE_LOG_CONTEXT(Intent intent) {
        String string = intent.getExtras().getString("type");
        String string2 = intent.getExtras().getString("value");
        TraceLogger traceLogger = LoggerFactory.getTraceLogger();
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append(intent.getAction());
        sb.append(", type: ");
        sb.append(string);
        traceLogger.info(str, sb.toString());
        TransUtils.updateLogContext(string, string2);
    }

    private void handleIntent_ACTION_UPDATE_LOG_CONTEXT_BATCH(Intent intent) {
        TraceLogger traceLogger = LoggerFactory.getTraceLogger();
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append(intent.getAction());
        sb.append(", size: ");
        sb.append(intent.getExtras().size());
        traceLogger.info(str, sb.toString());
        for (String str2 : intent.getExtras().keySet()) {
            TransUtils.updateLogContext(str2, intent.getExtras().getString(str2));
        }
        LoggerFactory.getLogContext().resetExtrasToSet();
    }

    private void handleIntent_ACTION_UPDATE_LOG_STRATEGY(Intent intent) {
        String string = intent.getExtras().getString("strategy");
        if (!TextUtils.isEmpty(string)) {
            LoggerFactory.getLogContext().updateLogStrategyCfg(string);
        }
    }

    private void hanldeIntent_ACTION_TRACE_NATIVECRASH(Intent intent) {
        LoggerFactory.getLogContext().traceNativeCrash(intent.getExtras().getString("filePath"), intent.getExtras().getString("callStack"), intent.getExtras().getBoolean("isBoot"));
    }
}
