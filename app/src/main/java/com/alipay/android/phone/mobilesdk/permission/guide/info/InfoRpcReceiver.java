package com.alipay.android.phone.mobilesdk.permission.guide.info;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.service.common.TaskScheduleService;

public class InfoRpcReceiver extends BroadcastReceiver {
    private static boolean a = true;

    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            LoggerFactory.getTraceLogger().info("Permissions", "InfoRpcReceiver.onReceive: action=" + action);
            if (action == null) {
                return;
            }
            if ("com.alipay.mobile.framework.BROUGHT_TO_FOREGROUND".equals(action)) {
                if (a) {
                    a = false;
                    LoggerFactory.getTraceLogger().warn((String) "Permissions", (String) "InfoRpcReceiver: this time is startup, give up.");
                    return;
                }
                a();
            } else if ("com.alipay.security.login".equals(action)) {
                boolean switchAccount = intent.getBooleanExtra("switchaccount", false);
                Log.d("Permissions", "InfoRpcReceiver.onReceive() action=" + action + ", switchAccount=" + switchAccount);
                if (switchAccount) {
                    a();
                }
            }
        }
    }

    private static void a() {
        try {
            if (!LoggerFactory.getProcessInfo().isMainProcess()) {
                LoggerFactory.getTraceLogger().info("Permissions", "cancel rpc task because it's not in process.");
            } else {
                ((TaskScheduleService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(TaskScheduleService.class.getName())).parallelExecute(new a(), "PGInfoRpcTask");
            }
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().warn((String) "Permissions", e);
        }
    }
}
