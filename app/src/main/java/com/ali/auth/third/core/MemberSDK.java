package com.ali.auth.third.core;

import android.app.Activity;
import android.content.Context;
import com.ali.auth.third.core.callback.InitResultCallback;
import com.ali.auth.third.core.callback.ResultCallback;
import com.ali.auth.third.core.config.AuthOption;
import com.ali.auth.third.core.config.ConfigManager;
import com.ali.auth.third.core.config.Environment;
import com.ali.auth.third.core.context.KernelContext;
import com.ali.auth.third.core.model.Constants;
import com.ali.auth.third.core.task.a;
import com.ali.auth.third.core.util.CommonUtils;
import java.util.Collections;
import java.util.Map;

public class MemberSDK {
    private static final Map<String, String> a = Collections.singletonMap(Constants.ISV_SCOPE_FLAG, "true");
    private static Environment b;
    public static String ttid;

    private static a a(Context context, InitResultCallback initResultCallback) {
        KernelContext.context = context.getApplicationContext();
        if (b == null) {
            b = Environment.ONLINE;
        }
        a aVar = new a(initResultCallback, Integer.valueOf(b.ordinal()));
        KernelContext.executorService.postHandlerTask(aVar);
        return aVar;
    }

    public static <T> T getService(Class<T> cls) {
        if (cls == null) {
            return null;
        }
        return KernelContext.serviceRegistry.a(cls, a);
    }

    public static <T> void getService(Activity activity, Class<T> cls, ResultCallback<T> resultCallback) {
        CommonUtils.startInitWaitTask(activity, resultCallback, new a(cls, resultCallback), "");
    }

    public static void init(Context context, InitResultCallback initResultCallback) {
        a(context, initResultCallback);
    }

    public static void setAuthOption(AuthOption authOption) {
        KernelContext.authOption = authOption;
    }

    public static void setEnvironment(Environment environment) {
        b = environment;
    }

    public static void setPackageName(String str) {
        KernelContext.packageName = str;
    }

    public static void setTtid(String str) {
        ttid = str;
    }

    public static void setUUID(String str) {
        KernelContext.UUID = str;
    }

    @Deprecated
    public static void turnOffDebug() {
    }

    public static void turnOnDebug() {
        ConfigManager.DEBUG = true;
    }
}
