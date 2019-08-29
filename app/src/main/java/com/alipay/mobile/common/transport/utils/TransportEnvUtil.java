package com.alipay.mobile.common.transport.utils;

import android.content.Context;
import android.os.Build.VERSION;
import android.text.TextUtils;

public class TransportEnvUtil {
    private static Context a;

    public static final void setContext(Context context) {
        a = context;
    }

    public static final Context getContext() {
        if (a != null) {
            return a;
        }
        if (VERSION.SDK_INT >= 14) {
            try {
                Context context = (Context) Class.forName("android.app.ActivityThread").getMethod("currentApplication", new Class[0]).invoke(null, new Object[0]);
                a = context;
                if (context != null) {
                    return a;
                }
                LogCatUtil.warn((String) "TransportEnvUtil", (String) "context from ActivityThread is null");
            } catch (Throwable e) {
                LogCatUtil.error("TransportEnvUtil", "context from ActivityThread exception", e);
            }
        }
        try {
            Context context2 = (Context) Class.forName("com.alipay.mobile.quinox.LauncherApplication").getMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
            a = context2;
            if (context2 == null) {
                LogCatUtil.warn((String) "TransportEnvUtil", (String) "context from LauncherApplication is null");
            }
        } catch (Throwable e2) {
            LogCatUtil.error("TransportEnvUtil", "context from LauncherApplication exception", e2);
        }
        return a;
    }

    public static final boolean isRunningJunitEnv() {
        return TextUtils.equals(System.getProperty("OS_ENV"), "OS_ENV_JUNIT");
    }

    public static final boolean isRunningWalletEnv() {
        String osEnv = System.getProperty("OS_ENV");
        if (TextUtils.isEmpty(osEnv)) {
            return true;
        }
        return TextUtils.equals(osEnv, "OS_ENV_WALLET");
    }
}
