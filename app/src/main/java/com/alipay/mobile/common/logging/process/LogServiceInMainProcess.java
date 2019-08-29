package com.alipay.mobile.common.logging.process;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.util.avail.ExceptionCollector;

public class LogServiceInMainProcess extends IntentService {
    public LogServiceInMainProcess() {
        super("LogServiceInMainProcess");
    }

    public LogServiceInMainProcess(String name) {
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
                Log.v("LogServiceInMainProcess", "action: " + action);
                if (action.equals(getPackageName() + ".monitor.action.upload.mdaplog")) {
                    try {
                        VariableStoreInToolsProcess.a = extras.getBoolean("isMonitorBackground");
                        VariableStoreInToolsProcess.b = extras.getBoolean("isStrictBackground");
                        VariableStoreInToolsProcess.c = extras.getBoolean("isRelaxedBackground");
                        VariableStoreInToolsProcess.d = extras.getString("invokerProcessAlias");
                    } catch (Throwable t) {
                        LoggerFactory.getTraceLogger().error((String) "LogServiceInMainProcess", "ACTION_UPLOAD_MDAPLOG: " + t.toString());
                    }
                    LoggerFactory.getLogContext().adjustUploadCoreByCategoryDirectly(extras.getString("logCategory"), extras.getString("uploadUrl"), extras);
                    VariableStoreInToolsProcess.a = true;
                    VariableStoreInToolsProcess.b = true;
                    VariableStoreInToolsProcess.c = true;
                    VariableStoreInToolsProcess.d = null;
                } else if (action.equals("ExceptionCollector_recordException")) {
                    try {
                        String exceptionType = extras.getString("exceptionType");
                        long crashLaunchTime = extras.getLong("crashLaunchTime");
                        if (TextUtils.isEmpty(exceptionType)) {
                            return;
                        }
                        if (crashLaunchTime > 0) {
                            ExceptionCollector.getInstance(getApplicationContext()).recordException(exceptionType, crashLaunchTime);
                        } else {
                            ExceptionCollector.getInstance(getApplicationContext()).recordException(exceptionType);
                        }
                    } catch (Throwable tr) {
                        LoggerFactory.getTraceLogger().warn((String) "LogServiceInMainProcess", tr);
                    }
                } else {
                    LoggerFactory.getTraceLogger().error((String) "LogServiceInMainProcess", "no such action: " + action);
                }
            }
        }
    }
}
