package com.ali.auth.third.core.context;

import android.app.Application;
import android.content.Context;
import com.ali.auth.third.core.WebViewProxy;
import com.ali.auth.third.core.config.AuthOption;
import com.ali.auth.third.core.config.ConfigManager;
import com.ali.auth.third.core.config.Environment;
import com.ali.auth.third.core.registry.ServiceRegistration;
import com.ali.auth.third.core.registry.a.b;
import com.ali.auth.third.core.service.CredentialService;
import com.ali.auth.third.core.service.MemberExecutorService;
import com.ali.auth.third.core.service.RpcService;
import com.ali.auth.third.core.service.StorageService;
import com.ali.auth.third.core.service.UserTrackerService;
import com.ali.auth.third.core.service.impl.a;
import com.ali.auth.third.core.util.SystemUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class KernelContext {
    public static final String SDK_VERSION_MINI;
    public static final String SDK_VERSION_STD;
    public static final String TAG = "kernel";
    public static String UUID = null;
    public static AuthOption authOption = AuthOption.NORMAL;
    public static volatile Context context = null;
    public static CredentialService credentialService = null;
    public static MemberExecutorService executorService = new a();
    public static final ReentrantLock initLock = new ReentrantLock();
    public static volatile Boolean isInitOk = null;
    public static boolean isMini = true;
    public static WebViewProxy mWebViewProxy;
    public static String packageName;
    public static String sdkVersion = SDK_VERSION_MINI;
    public static volatile com.ali.auth.third.core.registry.a serviceRegistry = new com.ali.auth.third.core.registry.a.a();
    public static StorageService storageService;
    public static volatile boolean syncInitialized;
    public static long timestamp;

    static {
        StringBuilder sb = new StringBuilder("a_");
        sb.append(ConfigManager.SDK_VERSION.toString());
        sb.append("-mini");
        SDK_VERSION_MINI = sb.toString();
        StringBuilder sb2 = new StringBuilder("a_");
        sb2.append(ConfigManager.SDK_VERSION.toString());
        sb2.append("-std");
        SDK_VERSION_STD = sb2.toString();
    }

    public static Boolean checkInitStatus() {
        try {
            initLock.lock();
            return isInitOk;
        } finally {
            initLock.unlock();
        }
    }

    public static boolean checkServiceValid() {
        return (context == null || serviceRegistry == null || getServices(RpcService.class) == null || getServices(StorageService.class) == null || getServices(UserTrackerService.class) == null || getServices(CredentialService.class) == null || storageService == null || credentialService == null) ? false : true;
    }

    public static String getAppKey() {
        return storageService.getAppKey();
    }

    public static synchronized Context getApplicationContext() {
        synchronized (KernelContext.class) {
            if (context != null) {
                Context context2 = context;
                return context2;
            }
            Application systemApp = SystemUtils.getSystemApp();
            return systemApp;
        }
    }

    public static Environment getEnvironment() {
        return ConfigManager.getInstance().getEnvironment();
    }

    public static <T> T getService(Class<T> cls) {
        return serviceRegistry.a(cls, null);
    }

    public static <T> T getService(Class<T> cls, Map<String, String> map) {
        return serviceRegistry.a(cls, map);
    }

    public static <T> T[] getServices(Class<T> cls) {
        return serviceRegistry.b(cls, null);
    }

    public static ServiceRegistration registerService(Class<?>[] clsArr, Object obj, Map<String, String> map) {
        return serviceRegistry.a(clsArr, obj, map == null ? new HashMap() : new HashMap(map));
    }

    public static void wrapServiceRegistry() {
        if (!(serviceRegistry instanceof b)) {
            serviceRegistry = new b(serviceRegistry);
        }
    }
}
