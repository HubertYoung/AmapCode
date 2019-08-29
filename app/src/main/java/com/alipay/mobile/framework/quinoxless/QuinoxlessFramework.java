package com.alipay.mobile.framework.quinoxless;

import android.app.Application;
import android.content.res.Configuration;
import android.os.SystemClock;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.app.ui.ActivityHelper;
import com.alipay.mobile.quinox.utils.ReflectUtil;
import com.alipay.mobile.quinox.utils.TraceLogger;
import java.util.concurrent.atomic.AtomicBoolean;

public class QuinoxlessFramework {
    private static final String a = QuinoxlessFramework.class.getSimpleName();
    private static AtomicBoolean b = new AtomicBoolean(false);
    private static Application c;
    private static OnInitListener d;
    private static AtomicBoolean e = new AtomicBoolean(false);

    public interface OnInitListener {
        void postInit();

        void preInit();
    }

    public static boolean isQuinoxlessMode() {
        return b.get();
    }

    public static void setup(Application application, OnInitListener initListener) {
        b.compareAndSet(false, true);
        c = application;
        d = initListener;
    }

    public static synchronized void init() {
        synchronized (QuinoxlessFramework.class) {
            if (c == null) {
                throw new IllegalStateException("QuinoxlessFramework.setup need invoke in Application.onCreate or Application.attachContext");
            } else if (!e.get()) {
                e.set(true);
                long clockStart = System.currentTimeMillis();
                long threadStart = SystemClock.currentThreadTimeMillis();
                new LauncherApplicationAgent(c, new QuinoxlessBundleContext(c));
                if (d != null) {
                    d.preInit();
                }
                a(c);
                LauncherApplicationAgent.getInstance().preInit();
                LauncherApplicationAgent.getInstance().init();
                LauncherApplicationAgent.getInstance().postLoad();
                LauncherApplicationAgent.getInstance().loadServices();
                LauncherApplicationAgent.getInstance().restoreState();
                LauncherApplicationAgent.getInstance().postInit();
                LauncherApplicationAgent.getInstance().getMicroApplicationContext().setStartActivityContext(c);
                ActivityHelper.startFrameworkSecondPipeLine();
                ActivityHelper.startClientStartedPipeline();
                if (d != null) {
                    d.postInit();
                }
                TraceLogger.d(a, "init finish, clock cost:" + (System.currentTimeMillis() - clockStart) + " ms, thread cost:" + (SystemClock.currentThreadTimeMillis() - threadStart) + " ms");
            }
        }
    }

    public void onTerminate() {
        LauncherApplicationAgent.getInstance().onTerminate();
    }

    public void onLowMemory() {
        LauncherApplicationAgent.getInstance().onLowMemory();
    }

    public void onConfigurationChanged(Configuration newConfig) {
        LauncherApplicationAgent.getInstance().onConfigurationChanged(newConfig);
    }

    private static void a(Application application) {
        try {
            ReflectUtil.invokeMethod(ReflectUtil.invokeStaticMethod("com.alipay.mobile.framework.locale.LocaleHelper", "getInstance"), (String) "initSavedLocale", Application.class, (Object) application);
        } catch (Exception e2) {
            TraceLogger.w(a, (Throwable) e2);
        }
    }
}
