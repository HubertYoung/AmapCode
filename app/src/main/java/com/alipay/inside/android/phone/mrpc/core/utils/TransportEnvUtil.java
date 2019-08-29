package com.alipay.inside.android.phone.mrpc.core.utils;

import android.content.Context;
import com.alipay.android.phone.inside.log.api.LoggerFactory;

public class TransportEnvUtil {
    private static final String TAG = "TransportEnvUtil";
    private static Context sContext;

    public static final void setContext(Context context) {
        sContext = context;
    }

    public static final Context getContext() {
        if (sContext != null) {
            return sContext;
        }
        try {
            Context context = (Context) Class.forName("com.alipay.android.phone.inside.framework.LauncherApplication").getMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
            sContext = context;
            if (context == null) {
                LoggerFactory.f().c((String) TAG, (String) "context from LauncherApplication is null");
            }
        } catch (Throwable th) {
            LoggerFactory.f().b(TAG, "context from LauncherApplication exception", th);
        }
        return sContext;
    }
}
