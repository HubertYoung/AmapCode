package com.alipay.mobile.core.impl;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Build.VERSION;
import android.os.Bundle;
import com.alipay.mobile.framework.MicroApplicationContext;
import com.alipay.mobile.framework.app.ui.ActivityCollections;
import com.alipay.mobile.framework.app.ui.BaseActivity;
import com.alipay.mobile.framework.app.ui.BaseFragmentActivity;
import com.alipay.mobile.framework.quinoxless.QuinoxUtilsDecorator.StartupSafeguard;
import com.alipay.mobile.quinox.utils.LogUtil;
import com.alipay.mobile.quinox.utils.TraceLogger;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

public class AppExitHelper {
    private ActivityLifecycleCallbacks a;
    /* access modifiers changed from: private */
    public MicroApplicationContext b;
    Application mTargetApp;

    @TargetApi(14)
    public void init(Application app, MicroApplicationContext microApplicationContext) {
        if (app == null) {
            throw new IllegalArgumentException("The app can't be null.");
        }
        this.mTargetApp = app;
        this.b = microApplicationContext;
        if (VERSION.SDK_INT >= 14) {
            a();
        }
    }

    @TargetApi(14)
    private void a() {
        this.a = new ActivityLifecycleCallbacks() {
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                LogUtil.d("MicroAppContextImpl", "onActivityCreated(activity=" + activity.getClass().getSimpleName() + ")");
                String name = activity.getClass().getSimpleName();
                if (!(activity instanceof BaseActivity) && !(activity instanceof BaseFragmentActivity)) {
                    TraceLogger.e((String) "MicroAppContextImpl", "Debug Mode Warning: " + name + " is not a subclass of BaseActivity or BaseFragmentActivity.");
                    ActivityCollections.getInstance().recordActivity(activity);
                }
            }

            public void onActivityStarted(Activity activity) {
                LogUtil.d("MicroAppContextImpl", "onActivityStarted(activity=" + activity.getClass().getSimpleName() + ")");
            }

            public void onActivityResumed(Activity activity) {
                LogUtil.d("MicroAppContextImpl", "onActivityResumed(activity=" + activity.getClass().getSimpleName() + ")");
                String className = activity.getClass().getName();
                if (!"com.eg.android.AlipayGphone.AlipayLogin".equals(className) && !"com.alipay.mobile.quinox.LauncherActivity".equals(className) && !"com.alipay.mobile.quinox.LauncherActivity.alias".equals(className) && !"com.alipay.mobile.quinox.SchemeLauncherActivity".equals(className)) {
                    StartupSafeguard.eraseStartupSafeguardFlags();
                    if (!(activity instanceof BaseActivity) && !(activity instanceof BaseFragmentActivity)) {
                        AppExitHelper.this.b.updateActivity(activity);
                    }
                }
            }

            public void onActivityPaused(Activity activity) {
                LogUtil.d("MicroAppContextImpl", "onActivityPaused(activity=" + activity.getClass().getSimpleName() + ")");
            }

            public void onActivityStopped(Activity activity) {
                LogUtil.d("MicroAppContextImpl", "onActivityStopped(activity=" + activity.getClass().getSimpleName() + ")");
            }

            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                LogUtil.d("MicroAppContextImpl", "onActivitySaveInstanceState(activity=" + activity.getClass().getSimpleName() + ")");
            }

            public void onActivityDestroyed(Activity activity) {
                LogUtil.d("MicroAppContextImpl", "onActivityDestroyed(activity=" + activity.getClass().getSimpleName() + ")");
            }
        };
        TraceLogger.i((String) "MicroAppContextImpl", "registerActivityLifecycleCallbacks(" + this.mTargetApp + ")");
        this.mTargetApp.registerActivityLifecycleCallbacks(this.a);
    }

    public void finishAllActivities(Activity activity, Activity exclude) {
        if (VERSION.SDK_INT >= 14) {
            if (exclude == null) {
                b();
            }
            ActivityCollections.destroy(exclude);
        } else if (activity != null) {
            a(activity, exclude);
        }
    }

    @TargetApi(14)
    private void b() {
        TraceLogger.i((String) "MicroAppContextImpl", (String) "unregisterActivityLifecycleCallbacks()");
        this.mTargetApp.unregisterActivityLifecycleCallbacks(this.a);
    }

    private static void a(Activity activity, Activity exclude) {
        try {
            Field field_mMainThread = Class.forName("android.app.Activity").getDeclaredField("mMainThread");
            field_mMainThread.setAccessible(true);
            Object mMainThread = field_mMainThread.get(activity);
            Field field_mActivities = Class.forName("android.app.ActivityThread").getDeclaredField("mActivities");
            field_mActivities.setAccessible(true);
            Collection collections = ((Map) field_mActivities.get(mMainThread)).values();
            if (!collections.isEmpty()) {
                Field fieldActivity = Class.forName("android.app.ActivityThread$ActivityClientRecord").getDeclaredField(WidgetType.ACTIVITY);
                fieldActivity.setAccessible(true);
                for (Object obj : collections) {
                    if (obj != null) {
                        Activity _activity = (Activity) fieldActivity.get(obj);
                        if (_activity != null && _activity != exclude && !(_activity instanceof BaseActivity) && !(_activity instanceof BaseFragmentActivity) && !_activity.isFinishing()) {
                            TraceLogger.i((String) "MicroAppContextImpl", "activity.name=" + _activity.getClass().getName() + " not finish.");
                            _activity.finish();
                        }
                    }
                }
            }
        } catch (Throwable e) {
            TraceLogger.w((String) "MicroAppContextImpl", e);
        }
    }
}
