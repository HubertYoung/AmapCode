package com.alipay.mobile.common.logging.process;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.LogContext;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.util.ToolThreadUtils;
import com.alipay.tianyan.mobilesdk.TianyanLoggingStatus;

public class LogReceiverInToolsProcess extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        ToolThreadUtils.getInstance(LoggerFactory.getLogContext().getApplicationContext()).start(true);
        TianyanLoggingStatus.acceptTimeTicksMadly();
        if (context != null && intent != null) {
            String action = intent.getAction();
            Bundle extras = intent.getExtras();
            if (!TextUtils.isEmpty(action) && extras != null) {
                LoggerFactory.getTraceLogger().info("LogReceiverInTools", "action: " + action);
                if (action.equals(context.getPackageName() + LogContext.ACTION_MONITOR_COMMAND)) {
                    String cmdAction = extras.getString("action");
                    if (TextUtils.isEmpty(cmdAction)) {
                        LoggerFactory.getTraceLogger().error((String) "LogReceiverInTools", "none extra action: " + action);
                    } else {
                        String cmdFilePath = extras.getString("filePath");
                        String cmdCallStack = extras.getString("callStack");
                        boolean cmdIsBoot = extras.getBoolean("isBoot");
                        Intent cmdIntent = new Intent();
                        cmdIntent.setClassName(context, LogContext.TOOLS_SERVICE_CLASS_NAME);
                        cmdIntent.setAction(cmdAction);
                        cmdIntent.putExtra("filePath", cmdFilePath);
                        cmdIntent.putExtra("callStack", cmdCallStack);
                        cmdIntent.putExtra("isBoot", cmdIsBoot);
                        try {
                            cmdIntent.setPackage(context.getPackageName());
                        } catch (Throwable th) {
                        }
                        try {
                            if (context.startService(cmdIntent) == null) {
                                LoggerFactory.getTraceLogger().error((String) "LogReceiverInTools", "fail to start LogServiceInToolsProcess: " + action);
                            }
                        } catch (Throwable t) {
                            LoggerFactory.getTraceLogger().error((String) "LogReceiverInTools", t);
                        }
                    }
                } else {
                    LoggerFactory.getTraceLogger().error((String) "LogReceiverInTools", "no such action: " + action);
                }
                ToolThreadUtils.getInstance(context).end();
            }
        }
    }
}
