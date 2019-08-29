package com.alipay.mobile.framework.quinoxless;

import android.app.Application;
import android.os.Handler;
import com.alipay.mobile.quinox.utils.ReflectUtil;
import com.alipay.mobile.quinox.utils.TraceLogger;
import java.util.concurrent.TimeUnit;

public class QuinoxUtilsDecorator {
    /* access modifiers changed from: private */
    public static final String a = QuinoxUtilsDecorator.class.getSimpleName();

    public static class ActivityLifecycleCallback {
        public static boolean isApplicationInForeground() {
            if (QuinoxlessFramework.isQuinoxlessMode()) {
                return false;
            }
            try {
                return ((Boolean) ReflectUtil.invokeStaticMethod("com.alipay.mobile.quinox.ActivityLifecycleCallback", "isApplicationInForeground")).booleanValue();
            } catch (Throwable t) {
                TraceLogger.w(QuinoxUtilsDecorator.a, t);
                return false;
            }
        }

        public static void setBackgroundCallback(Object callback) {
            if (!QuinoxlessFramework.isQuinoxlessMode()) {
                try {
                    ReflectUtil.invokeStaticMethod((String) "com.alipay.mobile.quinox.ActivityLifecycleCallback", (String) "setBackgroundCallback", Object.class, callback);
                } catch (Throwable t) {
                    TraceLogger.w(QuinoxUtilsDecorator.a, t);
                }
            }
        }
    }

    public static class AppCheckUtil {
        public static void frameBackgroundCalled() {
            if (!QuinoxlessFramework.isQuinoxlessMode()) {
                try {
                    ReflectUtil.invokeStaticMethod("com.alipay.mobile.quinox.utils.AppCheckUtil", "afterStartApp");
                } catch (Throwable t) {
                    TraceLogger.w(QuinoxUtilsDecorator.a, t);
                }
            }
        }
    }

    public static class CrashCenter {
        public static void updateLastJavaCrashTime() {
            if (!QuinoxlessFramework.isQuinoxlessMode()) {
                try {
                    ReflectUtil.setStaticFieldValue("com.alipay.mobile.quinox.utils.crash.CrashCenter", "sLastJavaCrashTime", Long.valueOf(System.currentTimeMillis()));
                } catch (Throwable t) {
                    TraceLogger.w(QuinoxUtilsDecorator.a, t);
                }
            }
        }
    }

    public static class ExceptionHandler {
        public static void stop() {
            if (!QuinoxlessFramework.isQuinoxlessMode()) {
                try {
                    ReflectUtil.invokeMethod(ReflectUtil.invokeStaticMethod("com.alipay.mobile.quinox.ExceptionHandler", "getInstance"), (String) "onExit");
                } catch (Throwable t) {
                    TraceLogger.w(QuinoxUtilsDecorator.a, t);
                }
            }
        }
    }

    public static class PerformanceHelper {
        public static void beforeStartApp(Application application) {
            if (!QuinoxlessFramework.isQuinoxlessMode()) {
                try {
                    ReflectUtil.invokeStaticMethod((String) "com.alipay.mobile.quinox.perfhelper.PerformanceHelper", (String) "beforeStartApp", Application.class, (Object) application);
                } catch (Throwable t) {
                    TraceLogger.w(QuinoxUtilsDecorator.a, t);
                }
            }
        }

        public static void afterStartApp(Application application) {
            if (!QuinoxlessFramework.isQuinoxlessMode()) {
                try {
                    ReflectUtil.invokeStaticMethod((String) "com.alipay.mobile.quinox.perfhelper.PerformanceHelper", (String) "afterStartApp", Application.class, (Object) application);
                } catch (Throwable t) {
                    TraceLogger.w(QuinoxUtilsDecorator.a, t);
                }
            }
        }
    }

    public static class StartupSafeguard {
        private static Object a;

        public static void eraseStartupSafeguardFlags() {
            if (!QuinoxlessFramework.isQuinoxlessMode()) {
                try {
                    ReflectUtil.invokeMethod(a(), (String) "setStartupPending", Boolean.TYPE, (Object) Boolean.valueOf(false));
                    new Handler().postDelayed(new Runnable() {
                        public final void run() {
                            try {
                                ReflectUtil.invokeMethod(StartupSafeguard.a(), (String) "setStartupCrash", Boolean.TYPE, (Object) Boolean.valueOf(false));
                            } catch (Throwable t) {
                                TraceLogger.w(QuinoxUtilsDecorator.a, t);
                            }
                        }
                    }, TimeUnit.SECONDS.toMillis(5));
                } catch (Throwable t) {
                    TraceLogger.w(QuinoxUtilsDecorator.a, t);
                }
            }
        }

        public static boolean isShouldOptimizeBootFinishSpeed() {
            if (QuinoxlessFramework.isQuinoxlessMode()) {
                return false;
            }
            try {
                return ((Boolean) ReflectUtil.getStaticFieldValue("com.alipay.mobile.quinox.startup.StartupSafeguard", "mShouldOptimizeBootFinishSpeed")).booleanValue();
            } catch (Throwable t) {
                TraceLogger.w(QuinoxUtilsDecorator.a, t);
                return false;
            }
        }

        public static void setShouldOptimizeBootFinishSpeed(boolean shouldOptimizeBootFinishSpeed) {
            if (!QuinoxlessFramework.isQuinoxlessMode()) {
                try {
                    ReflectUtil.setStaticFieldValue("com.alipay.mobile.quinox.startup.StartupSafeguard", "mShouldOptimizeBootFinishSpeed", Boolean.valueOf(shouldOptimizeBootFinishSpeed));
                } catch (Throwable t) {
                    TraceLogger.w(QuinoxUtilsDecorator.a, t);
                }
            }
        }

        public static void stopOptHostClassLoader() {
            if (!QuinoxlessFramework.isQuinoxlessMode()) {
                try {
                    ReflectUtil.invokeMethod(a(), (String) "stopOptHostClassLoader");
                } catch (Throwable t) {
                    TraceLogger.w(QuinoxUtilsDecorator.a, t);
                }
            }
        }

        public static void handleFrameworkCrash(Throwable ex) {
            if (!QuinoxlessFramework.isQuinoxlessMode()) {
                try {
                    ReflectUtil.invokeMethod(a(), (String) "handleFrameworkCrash", Throwable.class, (Object) ex);
                } catch (Throwable t) {
                    TraceLogger.w(QuinoxUtilsDecorator.a, t);
                }
            }
        }

        /* access modifiers changed from: private */
        public static Object a() {
            if (a == null) {
                try {
                    a = ReflectUtil.invokeStaticMethod("com.alipay.mobile.quinox.startup.StartupSafeguard", "getInstance");
                } catch (Throwable e) {
                    TraceLogger.w(QuinoxUtilsDecorator.a, e);
                }
            }
            return a;
        }
    }

    public static class UcNativeCrashApi {
        public static void onExit() {
            if (!QuinoxlessFramework.isQuinoxlessMode()) {
                try {
                    ReflectUtil.invokeStaticMethod("com.alipay.mobile.quinox.utils.crash.UcNativeCrashApi", "onExit");
                } catch (Throwable t) {
                    TraceLogger.w(QuinoxUtilsDecorator.a, t);
                }
            }
        }

        public static void setForeground(boolean foreground) {
            if (!QuinoxlessFramework.isQuinoxlessMode()) {
                try {
                    ReflectUtil.invokeStaticMethod((String) "com.alipay.mobile.quinox.utils.crash.UcNativeCrashApi", (String) "setForeground", Boolean.TYPE, (Object) Boolean.valueOf(foreground));
                } catch (Throwable t) {
                    TraceLogger.w(QuinoxUtilsDecorator.a, t);
                }
            }
        }
    }
}
