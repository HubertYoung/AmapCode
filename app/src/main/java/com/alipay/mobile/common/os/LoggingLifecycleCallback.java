package com.alipay.mobile.common.os;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.nativecrash.NativeCrashHandlerApi;

@TargetApi(14)
public class LoggingLifecycleCallback implements ActivityLifecycleCallbacks {
    private static int a = 0;
    private static int b = 0;
    private static int c = 0;

    public LoggingLifecycleCallback(Context context) {
        try {
            BroadcastReceiver lockScreenReceiver = new BroadcastReceiver() {
                public void onReceive(Context context, Intent intent) {
                    if (intent != null && "android.intent.action.SCREEN_OFF".equals(intent.getAction())) {
                        NativeCrashHandlerApi.setForeground(false);
                    }
                }
            };
            IntentFilter screenIntentFilter = new IntentFilter();
            screenIntentFilter.addAction("android.intent.action.SCREEN_OFF");
            context.registerReceiver(lockScreenReceiver, screenIntentFilter);
        } catch (Throwable tr) {
            LoggerFactory.getTraceLogger().warn((String) "LoggingLifecycleCallback", tr);
        }
    }

    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        c++;
        NativeCrashHandlerApi.setForeground(true);
    }

    public void onActivityStarted(Activity activity) {
        a++;
        NativeCrashHandlerApi.setForeground(true);
    }

    public void onActivityResumed(Activity activity) {
        b++;
        NativeCrashHandlerApi.setForeground(true);
    }

    public void onActivityPaused(Activity activity) {
        b--;
    }

    public void onActivityStopped(Activity activity) {
        int i = a - 1;
        a = i;
        if (i <= 0) {
            NativeCrashHandlerApi.setForeground(false);
            LoggerFactory.getTraceLogger().info("LoggingLifecycleCallback", "setForeground false when all stopped.");
        }
    }

    public void onActivityDestroyed(Activity activity) {
        int i = c - 1;
        c = i;
        if (i <= 0) {
            NativeCrashHandlerApi.onExit();
        }
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }
}
