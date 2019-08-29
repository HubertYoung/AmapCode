package com.alipay.mobile.nebulacore.core;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.WindowManager;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.app.ui.ActivityHelper;
import com.alipay.mobile.h5container.api.H5Flag;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.ui.H5Activity;
import com.alipay.mobile.nebulacore.ui.H5ViewHolder;
import com.alipay.mobile.nebulacore.util.H5TimeUtil;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class H5PagePreloader {
    /* access modifiers changed from: private */
    public static H5PagePreloadFactoryImpl a;
    /* access modifiers changed from: private */
    public static H5ViewHolder b;
    /* access modifiers changed from: private */
    public static boolean c = false;
    private static boolean d = false;
    /* access modifiers changed from: private */
    public static Activity e;
    /* access modifiers changed from: private */
    public static BroadcastReceiver f;

    private H5PagePreloader() {
    }

    public static void startPreload() {
        H5Log.d("H5PagePreloader", "startPreload enter");
        if (ActivityHelper.isBackgroundRunning()) {
            H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
            if (h5ConfigProvider != null && "yes".equalsIgnoreCase(h5ConfigProvider.getConfig("h5_preloadH5Page"))) {
                precreateH5Activity();
            }
        }
    }

    private static void f() {
        H5Log.d("H5PagePreloader", "registerUcInitSuccessReceiver");
        if (f == null) {
            f = new BroadcastReceiver() {
                public final void onReceive(Context context, Intent intent) {
                    H5Log.d("H5PagePreloader", "registerUcInitSuccessReceiver onReceive");
                    if (intent.getBooleanExtra("result", false)) {
                        H5PagePreloader.g();
                        LocalBroadcastManager.getInstance(LauncherApplicationAgent.getInstance().getApplicationContext()).unregisterReceiver(H5PagePreloader.f);
                    }
                }
            };
            LocalBroadcastManager.getInstance(LauncherApplicationAgent.getInstance().getApplicationContext()).registerReceiver(f, new IntentFilter(H5Param.H5_ACTION_UC_INIT_FINISH));
        }
    }

    /* access modifiers changed from: private */
    public static void g() {
        H5Log.d("H5PagePreloader", "preloadH5Page enter");
        if (!H5Flag.ucReady) {
            f();
        } else if (ActivityHelper.isBackgroundRunning()) {
            H5Log.d("H5PagePreloader", "preloadH5Page do preload");
            H5Utils.runOnMain(new Runnable() {
                public final void run() {
                    try {
                        H5PagePreloadFactoryImpl pageFactory = new H5PagePreloadFactoryImpl(H5PagePreloader.e);
                        if (!H5PagePreloader.c) {
                            H5ViewHolder viewHolder = pageFactory.createPage(H5PagePreloader.e, null);
                            if (!H5PagePreloader.c) {
                                H5PagePreloader.a = pageFactory;
                                H5PagePreloader.b = viewHolder;
                                H5Log.d("H5PagePreloader", "preloadH5Page success");
                            }
                        }
                    } catch (Throwable thr) {
                        H5PagePreloader.b("h5page_preload_error");
                        H5Log.e("H5PagePreloader", "preload H5Page error!", thr);
                    }
                }
            });
        }
    }

    public static void abandon() {
        H5Log.d("H5PagePreloader", "abandon preload h5page");
        c = true;
        a = null;
        b = null;
    }

    public static void onH5ActivityCreated(Activity activity) {
        try {
            if (e != null && activity != e) {
                abandon();
                e = null;
                if (activity instanceof H5Activity) {
                    b("activity_not_start");
                }
                Object quinoxInstrumentation = h();
                if (quinoxInstrumentation != null) {
                    H5Log.d("H5PagePreloader", "instrumentation class type = " + quinoxInstrumentation.getClass().getName());
                }
                H5Log.d("H5PagePreloader", "onH5ActivityCreated not hit");
            } else if (activity == e) {
                d = true;
            }
        } catch (Throwable e2) {
            H5Log.e("H5PagePreloader", "onH5ActivityCreated error!", e2);
        }
    }

    /* access modifiers changed from: private */
    public static void b(String exception) {
        try {
            Method logMethod = Class.forName("com.alipay.mobile.liteprocess.perf.PerformanceLogger").getDeclaredMethod("logH5PreloadException", new Class[]{String.class});
            logMethod.setAccessible(true);
            logMethod.invoke(null, new Object[]{exception});
        } catch (Throwable th) {
        }
    }

    public static H5ViewHolder getPreloadedViewHolder(Bundle params) {
        try {
            if (!d) {
                return null;
            }
            if (c || b == null) {
                return null;
            }
            H5PageImpl h5Page = b.getH5Page();
            if (h5Page == null) {
                return null;
            }
            long time = System.currentTimeMillis();
            h5Page.setUpPage(params);
            H5TimeUtil.timeLog(H5TimeUtil.CREATE_PAGE, "h5Page.setUpPage", time);
            a.setUpPage(b, params);
            H5TimeUtil.timeLog(H5TimeUtil.CREATE_PAGE, "h5PageFactory.setUpPage", time);
            H5ViewHolder h5ViewHolder = b;
            b = null;
            a = null;
            e = null;
            return h5ViewHolder;
        } catch (Throwable throwable) {
            H5Log.e("H5PagePreloader", "set up preloaded H5Page error!", throwable);
            b("set_up_page_error");
            return null;
        }
    }

    public static void precreateH5Activity() {
        if (ActivityHelper.isBackgroundRunning()) {
            H5Utils.runOnMain(new Runnable() {
                public final void run() {
                    boolean z = true;
                    try {
                        Object quinoxInstrumentation = H5PagePreloader.h();
                        if (quinoxInstrumentation != null) {
                            H5Log.d("H5PagePreloader", "instrumentation class type = " + quinoxInstrumentation.getClass().getName());
                            if (quinoxInstrumentation.getClass().getName().startsWith("com.alipay.mobile")) {
                                WindowManager wm = (WindowManager) LauncherApplicationAgent.getInstance().getApplicationContext().getSystemService(TemplateTinyApp.WINDOW_KEY);
                                if (wm != null) {
                                    Method precreateActivityMethod = quinoxInstrumentation.getClass().getDeclaredMethod("precreateActivity", new Class[]{String.class, ClassLoader.class});
                                    precreateActivityMethod.setAccessible(true);
                                    Class targetActivity = Nebula.LITE_PROCESS_H5_ACTIVITY[Nebula.getH5EventHandler().getLitePid() - 1];
                                    Activity activity = (Activity) precreateActivityMethod.invoke(quinoxInstrumentation, new Object[]{targetActivity.getName(), targetActivity.getClassLoader()});
                                    StringBuilder sb = new StringBuilder("activity create success ? ");
                                    if (activity == null) {
                                        z = false;
                                    }
                                    H5Log.d("H5PagePreloader", sb.append(z).toString());
                                    H5PagePreloader.e = activity;
                                    if (H5PagePreloader.e != null) {
                                        Field field = Activity.class.getDeclaredField("mWindowManager");
                                        field.setAccessible(true);
                                        field.set(H5PagePreloader.e, wm);
                                        H5PagePreloader.g();
                                        return;
                                    }
                                    H5PagePreloader.b("H5Activity_create_failed");
                                }
                            }
                        }
                    } catch (Throwable thr) {
                        H5Log.e("H5PagePreloader", "precreateH5Activity error", thr);
                        H5PagePreloader.b("H5Activity_create_failed");
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public static Object h() {
        boolean z = false;
        try {
            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            Method currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread", new Class[0]);
            currentActivityThreadMethod.setAccessible(true);
            Object currentActivityThread = currentActivityThreadMethod.invoke(null, new Object[0]);
            Field mInstrumentationField = activityThreadClass.getDeclaredField("mInstrumentation");
            mInstrumentationField.setAccessible(true);
            return mInstrumentationField.get(currentActivityThread);
        } catch (Throwable thr) {
            H5Log.e("H5PagePreloader", "getCurrentInstrumentation error!", thr);
            return z;
        }
    }
}
