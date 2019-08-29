package com.alipay.mobile.common.logging.process;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.util.LoggingSPCache;
import com.alipay.mobile.common.logging.util.ToolThreadUtils;
import com.alipay.mobile.h5container.api.H5PageData;
import com.alipay.tianyan.mobilesdk.TianyanLoggingStatus;
import java.lang.reflect.Method;

public class LogServiceInToolsProcess extends IntentService {
    public LogServiceInToolsProcess() {
        super("LogServiceInTools");
    }

    public void onLowMemory() {
        TianyanLoggingStatus.acceptTimeTicksMadly();
        super.onLowMemory();
    }

    public void onDestroy() {
        TianyanLoggingStatus.acceptTimeTicksMadly();
        LoggerFactory.getLogContext().flush("applog", false);
        LoggerFactory.getLogContext().flush(null, false);
        super.onDestroy();
    }

    /* access modifiers changed from: protected */
    public void onHandleIntent(Intent intent) {
        ToolThreadUtils.getInstance(LoggerFactory.getLogContext().getApplicationContext()).start(true);
        TianyanLoggingStatus.acceptTimeTicksMadly();
        if (intent != null) {
            String action = intent.getAction();
            Bundle extras = intent.getExtras();
            if (!TextUtils.isEmpty(action) && extras != null) {
                Log.v("LogServiceInTools", "action: " + action);
                if (action.equals(getPackageName() + ".monitor.action.upload.mdaplog")) {
                    try {
                        VariableStoreInToolsProcess.a = extras.getBoolean("isMonitorBackground");
                        VariableStoreInToolsProcess.b = extras.getBoolean("isStrictBackground");
                        VariableStoreInToolsProcess.c = extras.getBoolean("isRelaxedBackground");
                        VariableStoreInToolsProcess.d = extras.getString("invokerProcessAlias");
                    } catch (Throwable t) {
                        LoggerFactory.getTraceLogger().error((String) "LogServiceInTools", "ACTION_UPLOAD_MDAPLOG: " + t.toString());
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
                    LoggerFactory.getTraceLogger().info("LogServiceInTools", action + ", type: " + type);
                    a(type, value);
                } else if (action.equals(getPackageName() + ".monitor.action.UPDATE_LOG_CONTEXT_BATCH")) {
                    LoggerFactory.getTraceLogger().info("LogServiceInTools", action + ", size: " + extras.size());
                    for (String type2 : extras.keySet()) {
                        a(type2, extras.getString(type2));
                    }
                    LoggerFactory.getLogContext().resetExtrasToSet();
                } else if (action.equals(getPackageName() + ".monitor.action.TRACE_NATIVE_CRASH")) {
                    LoggerFactory.getLogContext().traceNativeCrash(extras.getString("filePath"), extras.getString("callStack"), extras.getBoolean("isBoot"));
                } else if (action.equals(getPackageName() + ".monitor.action.DYNAMIC_RELEASE")) {
                    boolean isForce = extras.getBoolean("isForce");
                    String hotpatchVersion = null;
                    if (VERSION.SDK_INT >= 12) {
                        hotpatchVersion = extras.getString(LoggingSPCache.STORAGE_HOTPATCHVERSION, "0");
                    }
                    try {
                        Class clazz = getClassLoader().loadClass("com.alipay.android.phone.mobilecommon.dynamicrelease.processor.DynamicReleaseProcessor");
                        Method getInstanceMethod = clazz.getDeclaredMethod("getInstance", new Class[]{Context.class});
                        getInstanceMethod.setAccessible(true);
                        Object instance = getInstanceMethod.invoke(null, new Object[]{this});
                        Method startMethod = clazz.getDeclaredMethod(H5PageData.KEY_UC_START, new Class[]{Boolean.TYPE, String.class});
                        startMethod.setAccessible(true);
                        startMethod.invoke(instance, new Object[]{Boolean.valueOf(isForce), hotpatchVersion});
                    } catch (Throwable e) {
                        LoggerFactory.getTraceLogger().error((String) "LogServiceInTools", e);
                    }
                } else {
                    LoggerFactory.getTraceLogger().error((String) "LogServiceInTools", "no such action: " + action);
                }
                ToolThreadUtils.getInstance(LoggerFactory.getLogContext().getApplicationContext()).end();
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
            LoggerFactory.getTraceLogger().error((String) "LogServiceInTools", "not mapping, type: " + type + ", value: " + value);
        }
    }
}
