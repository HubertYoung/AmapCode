package com.alipay.mobile.logging;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;

public class TinyAppCreateReceiver extends BroadcastReceiver {
    private static String TAG = "TinyAppCreateReceiver";

    public void onReceive(Context context, Intent intent) {
        if (!LoggerFactory.getProcessInfo().isLiteProcess()) {
            LoggerFactory.getTraceLogger().info(TAG, "!isLiteProcess");
        }
        if (intent == null) {
            LoggerFactory.getTraceLogger().info(TAG, "intent is null");
            return;
        }
        String appId = null;
        String appVersion = null;
        try {
            appId = intent.getStringExtra("appId");
            appVersion = intent.getStringExtra("appVersion");
        } catch (Exception e) {
            LoggerFactory.getTraceLogger().error(TAG, Log.getStackTraceString(e));
        }
        LoggerFactory.getTraceLogger().info(TAG, "syncRequestLogConfig appId = " + appId + " appVersion = " + appVersion);
        final String finalAppId = appId;
        final String finalAppVersion = appVersion;
        H5Utils.runNotOnMain(H5ThreadType.URGENT, new Runnable() {
            public void run() {
                TinyLoggingConfigManager.getInstance().syncRequestLogConfig(finalAppId, finalAppVersion);
            }
        });
    }
}
