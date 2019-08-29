package com.alipay.mobile.logging;

import android.support.annotation.NonNull;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.monitor.Performance;
import com.alipay.mobile.common.logging.api.monitor.PerformanceID;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.rome.longlinkservice.ISyncCallback;
import com.alipay.mobile.rome.longlinkservice.LongLinkSyncService;
import com.alipay.mobile.rome.longlinkservice.syncmodel.SyncCommand;
import com.alipay.mobile.rome.longlinkservice.syncmodel.SyncMessage;
import org.json.JSONArray;
import org.json.JSONObject;

public class TinyAppConfigSyncCallback implements ISyncCallback {
    private static final String TAG = "TinyAppConfigSyncCallback";
    public static final String TINY_APP_CONFIG_USER = "TINYAPP-CONFIG";
    /* access modifiers changed from: private */
    public LongLinkSyncService syncService;

    public void onReceiveCommand(SyncCommand arg0) {
    }

    public void onReceiveMessage(SyncMessage message) {
        try {
            long currentTimeMillis = System.currentTimeMillis();
            LoggerFactory.getTraceLogger().info(TAG, "onReceiveMessage  , time = " + currentTimeMillis + " , message = " + message.msgData + " biz = " + message.biz);
            footPrint("onReceiveMessage  , time = " + currentTimeMillis + " , message = " + message.msgData + " biz" + message.biz);
            H5Utils.runNotOnMain(H5ThreadType.IO, getRunnable(message));
        } catch (Throwable e) {
            footPrint("onReceiveMessage Exception = " + e.getMessage());
        }
    }

    @NonNull
    private Runnable getRunnable(final SyncMessage message) {
        return new Runnable() {
            public void run() {
                if (TinyAppConfigSyncCallback.this.syncService == null) {
                    TinyAppConfigSyncCallback.this.syncService = (LongLinkSyncService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(LongLinkSyncService.class.getName());
                }
                if (TinyAppConfigSyncCallback.this.syncService == null) {
                    TinyAppConfigSyncCallback.this.footPrint("syncService == null  can not reportMsgReceived");
                    LoggerFactory.getTraceLogger().info(TinyAppConfigSyncCallback.TAG, "syncService == null can not reportMsgReceived");
                } else {
                    TinyAppConfigSyncCallback.this.syncService.reportMsgReceived(message);
                }
                TinyAppConfigSyncCallback.this.syncLogConfig(message.msgData);
            }
        };
    }

    /* access modifiers changed from: private */
    public void syncLogConfig(String msg) {
        try {
            String data = ((JSONObject) new JSONArray(msg).get(0)).getString(H5Param.PREFETCH_LOCATION);
            LoggerFactory.getTraceLogger().info(TAG, "tinyApp data = " + data);
            String appId = new JSONObject(data).getString("appId");
            LoggerFactory.getTraceLogger().info(TAG, "appId = " + appId);
            TinyLoggingConfigManager.getInstance().saveEventConfig(appId, data);
            TinyLoggingConfigManager.getInstance().notifyLogConfigChange(appId, data);
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().error((String) TAG, e);
        }
    }

    /* access modifiers changed from: private */
    public void footPrint(String msg) {
        Performance performance = new Performance();
        performance.setParam1(msg);
        LoggerFactory.getMonitorLogger().performance(PerformanceID.MONITORPOINT_FOOTPRINT, performance);
    }
}
