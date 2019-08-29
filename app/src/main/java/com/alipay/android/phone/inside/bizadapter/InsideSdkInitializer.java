package com.alipay.android.phone.inside.bizadapter;

import android.content.Context;
import com.alipay.android.phone.inside.bizadapter.ex.InsideExceptionHandler;
import com.alipay.android.phone.inside.bizadapter.service.IInteractionProxy;
import com.alipay.android.phone.inside.bizadapter.service.InteractionManager;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.android.phone.inside.framework.plugin.PluginManager;
import com.alipay.android.phone.inside.log.api.LoggerFactory;

public class InsideSdkInitializer {
    private static boolean a = false;

    public static synchronized void a(Context context, IInteractionProxy iInteractionProxy) {
        synchronized (InsideSdkInitializer.class) {
            LauncherApplication.a(context);
            try {
                new StringBuilder("InsideSdkInitializer::initialize start: ").append(System.currentTimeMillis());
                if (!a) {
                    InsideFramework.a();
                    PluginManager.a();
                    InsideBizAdapter.a(context);
                    a = true;
                }
                InsideExceptionHandler.a(context).a();
                new StringBuilder("InsideSdkInitializer::initialize end: ").append(System.currentTimeMillis());
            } catch (Throwable th) {
                LoggerFactory.e().a((String) "inside", (String) "InsideInitializeEx", th);
            }
            InteractionManager.a(iInteractionProxy);
        }
    }

    public static synchronized void a(Context context) {
        synchronized (InsideSdkInitializer.class) {
            try {
                InsideExceptionHandler.a(context);
                InsideExceptionHandler.b();
            } catch (Throwable th) {
                LoggerFactory.e().a((String) "inside", (String) "InsideUnInitializeEx", th);
            }
        }
    }
}
