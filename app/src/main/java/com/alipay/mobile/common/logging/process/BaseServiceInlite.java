package com.alipay.mobile.common.logging.process;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.util.LoggingSPCache;

public class BaseServiceInlite extends IntentService {
    public BaseServiceInlite(String name) {
        super(name);
    }

    public void onDestroy() {
        LoggerFactory.getLogContext().flush("applog", false);
        LoggerFactory.getLogContext().flush(null, false);
        super.onDestroy();
    }

    /* access modifiers changed from: protected */
    public void onHandleIntent(Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            Bundle extras = intent.getExtras();
            if (!TextUtils.isEmpty(action) && extras != null) {
                Log.v("BaseServiceInlite", "action: " + action);
                if (action.equals(getPackageName() + ".monitor.action.upload.mdaplog")) {
                    try {
                        VariableStoreInToolsProcess.a = extras.getBoolean("isMonitorBackground");
                        VariableStoreInToolsProcess.b = extras.getBoolean("isStrictBackground");
                        VariableStoreInToolsProcess.c = extras.getBoolean("isRelaxedBackground");
                        VariableStoreInToolsProcess.d = extras.getString("invokerProcessAlias");
                    } catch (Throwable t) {
                        LoggerFactory.getTraceLogger().error((String) "BaseServiceInlite", "ACTION_UPLOAD_MDAPLOG: " + t.toString());
                    }
                    LoggerFactory.getLogContext().upload(extras.getString("logCategory"), extras.getString("uploadUrl"), extras);
                    VariableStoreInToolsProcess.a = true;
                    VariableStoreInToolsProcess.b = true;
                    VariableStoreInToolsProcess.c = true;
                    VariableStoreInToolsProcess.d = null;
                } else if (action.equals(getPackageName() + ".monitor.action.UPDATE_LOG_STRATEGY")) {
                    LoggerFactory.getLogContext().updateLogStrategyCfg(extras.getString("strategy"));
                } else if (action.equals(getPackageName() + ".monitor.action.UPDATE_LOG_CONTEXT")) {
                    String type = extras.getString("type");
                    String value = extras.getString("value");
                    LoggerFactory.getTraceLogger().info("BaseServiceInlite", action + ", type: " + type);
                    a(type, value);
                } else if (action.equals(getPackageName() + ".monitor.action.UPDATE_LOG_CONTEXT_BATCH")) {
                    LoggerFactory.getTraceLogger().info("BaseServiceInlite", action + ", size: " + extras.size());
                    for (String type2 : extras.keySet()) {
                        a(type2, extras.getString(type2));
                    }
                    LoggerFactory.getLogContext().resetExtrasToSet();
                } else {
                    LoggerFactory.getTraceLogger().error((String) "BaseServiceInlite", "no such action: " + action);
                }
            }
        }
    }

    private static void a(String type, String value) {
        if (LoggingSPCache.STORAGE_CHANNELID.equals(type)) {
            LoggerFactory.getLogContext().setChannelIdNoCommit(value);
        } else if (LoggingSPCache.STORAGE_RELEASETYPE.equals(type)) {
            LoggerFactory.getLogContext().setReleaseTypeNoCommit(value);
        } else if (LoggingSPCache.STORAGE_RELEASECODE.equals(type)) {
            LoggerFactory.getLogContext().setReleaseCodeNoCommit(value);
        } else if (LoggingSPCache.STORAGE_PRODUCTID.equals(type)) {
            LoggerFactory.getLogContext().setProductIdNoCommit(value);
        } else if (LoggingSPCache.STORAGE_PRODUCTVERSION.equals(type)) {
            LoggerFactory.getLogContext().setProductVersionNoCommit(value);
        } else if (LoggingSPCache.STORAGE_USERID.equals(type)) {
            LoggerFactory.getLogContext().setUserIdNoCommit(value);
        } else if (LoggingSPCache.STORAGE_CLIENTID.equals(type)) {
            LoggerFactory.getLogContext().setClientIdNoCommit(value);
        } else if ("utdid".equals(type)) {
            LoggerFactory.getLogContext().setDeviceIdNoCommit(value);
        } else if ("language".equals(type)) {
            LoggerFactory.getLogContext().setLanguageNoCommit(value);
        } else if (LoggingSPCache.STORAGE_HOTPATCHVERSION.equals(type)) {
            LoggerFactory.getLogContext().setHotpatchVersionNoCommit(value);
        } else if (LoggingSPCache.STORAGE_BUNDLEVERSION.equals(type)) {
            LoggerFactory.getLogContext().setBundleVersionNoCommit(value);
        } else if (LoggingSPCache.STORAGE_BIRDNESTVERSION.equals(type)) {
            LoggerFactory.getLogContext().setBirdNestVersionNoCommit(value);
        } else if (LoggingSPCache.STORAGE_PACKAGEID.equals(type)) {
            LoggerFactory.getLogContext().setPackageIdNoCommit(value);
        } else if (LoggingSPCache.STORAGE_USERSESSIONID.equals(type)) {
            LoggerFactory.getLogContext().setUserIdNoCommit(value);
        } else if (LoggingSPCache.STORAGE_LOGHOST.equals(type)) {
            LoggerFactory.getLogContext().setLogHostNoCommit(value);
        } else {
            LoggerFactory.getTraceLogger().error((String) "BaseServiceInlite", "not mapping, type: " + type + ", value: " + value);
        }
    }
}
