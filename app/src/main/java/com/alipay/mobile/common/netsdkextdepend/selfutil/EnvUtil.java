package com.alipay.mobile.common.netsdkextdepend.selfutil;

import android.content.Context;
import android.os.Build.VERSION;
import com.alipay.mobile.common.logging.api.LoggerFactory;

public class EnvUtil {
    private static Context a = null;

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
                LoggerFactory.getTraceLogger().warn((String) "EnvUtil", (String) "[getContext] context from ActivityThread is null");
            } catch (Throwable e) {
                LoggerFactory.getTraceLogger().error("EnvUtil", "[getContext] context from ActivityThread exception", e);
            }
        }
        try {
            Context context2 = (Context) Class.forName("com.alipay.mobile.quinox.LauncherApplication").getMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
            a = context2;
            if (context2 == null) {
                LoggerFactory.getTraceLogger().warn((String) "EnvUtil", (String) "[getContext] context from LauncherApplication is null");
            }
        } catch (Throwable e2) {
            LoggerFactory.getTraceLogger().error("EnvUtil", "[getContext] ccontext from LauncherApplication exception", e2);
        }
        return a;
    }
}
