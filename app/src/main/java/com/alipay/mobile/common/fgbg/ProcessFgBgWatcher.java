package com.alipay.mobile.common.fgbg;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.quinox.utils.ReflectUtil;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@TargetApi(16)
public class ProcessFgBgWatcher {
    private static ProcessFgBgWatcher INSTANCE = new ProcessFgBgWatcher();
    private static final String TAG = "ProcessFgBgWatcher";
    private static boolean inited = false;
    @Nullable
    static Context mAppContext;
    private static Map<?, ?> sActivities = null;
    private static Object sActivityThread;
    private ActivityLifecycleCallbacks mActivityLifecycleCallbacks = new ActivityLifecycleCallbacks() {
        {
            if (Boolean.FALSE.booleanValue()) {
                Log.v("hackbyte ", ClassVerifier.class.toString());
            }
        }

        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        }

        public void onActivityDestroyed(Activity activity) {
        }

        public void onActivityStarted(Activity activity) {
            ProcessFgBgWatcher.this.mVisible = ProcessFgBgWatcher.this.mVisible + 1;
        }

        public void onActivityStopped(Activity activity) {
            ProcessFgBgWatcher.this.mVisible = ProcessFgBgWatcher.this.mVisible - 1;
            if (ProcessFgBgWatcher.this.mCurIsForeground && !ProcessFgBgWatcher.this.isProcessForeground(activity, activity)) {
                ProcessFgBgWatcher.this.mCurIsForeground = false;
                ProcessFgBgWatcher.this.notifyMoveToBackground(activity.getClass().getName());
            }
        }

        public void onActivityResumed(Activity activity) {
            if (!ProcessFgBgWatcher.this.mCurIsForeground) {
                ProcessFgBgWatcher.this.mCurIsForeground = true;
                ProcessFgBgWatcher.this.notifyMoveToForeground(activity.getClass().getName());
            }
        }

        public void onActivityPaused(Activity activity) {
        }

        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }
    };
    /* access modifiers changed from: private */
    public boolean mCurIsForeground = false;
    private final Set<FgBgCallback> mFgBgCallbacks = new HashSet();
    /* access modifiers changed from: private */
    public int mVisible;

    public interface FgBgCallback {
        public static final Class sInjector = (Boolean.TRUE.booleanValue() ? String.class : ClassVerifier.class);

        void onMoveToBackground(String str);

        void onMoveToForeground(String str);
    }

    public ProcessFgBgWatcher() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public static void init(Application application) {
        if (!inited) {
            inited = true;
            application.registerActivityLifecycleCallbacks(getInstance().mActivityLifecycleCallbacks);
            FgBgMonitor.getInstance(application).onProcessFgBgWatcherInited();
            mAppContext = application;
        }
    }

    public static ProcessFgBgWatcher getInstance() {
        return INSTANCE;
    }

    public void registerCallback(FgBgCallback fgBgCallback) {
        if (fgBgCallback != null) {
            synchronized (this.mFgBgCallbacks) {
                this.mFgBgCallbacks.add(fgBgCallback);
            }
        }
    }

    public void unregisterCallback(FgBgCallback fgBgCallback) {
        if (fgBgCallback != null) {
            synchronized (this.mFgBgCallbacks) {
                this.mFgBgCallbacks.remove(fgBgCallback);
            }
        }
    }

    public boolean isProcessForeground(@NonNull Context context) {
        return isProcessForeground(context, null);
    }

    /* access modifiers changed from: private */
    public boolean isProcessForeground(@NonNull Context context, @Nullable Activity excludeActivity) {
        try {
            return hasNoStoppedActivities(context, excludeActivity);
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().warn((String) TAG, "hasNoStoppedActivities() invoke failed! " + e.toString());
            return this.mVisible > 0;
        }
    }

    public boolean isProcessBackground(@NonNull Context context) {
        return !isProcessForeground(context);
    }

    /* access modifiers changed from: private */
    public void notifyMoveToForeground(String activityName) {
        Set<FgBgCallback> copy;
        synchronized (this.mFgBgCallbacks) {
            copy = new HashSet<>(this.mFgBgCallbacks);
        }
        for (FgBgCallback onMoveToForeground : copy) {
            onMoveToForeground.onMoveToForeground(activityName);
        }
    }

    /* access modifiers changed from: private */
    public void notifyMoveToBackground(String activityName) {
        Set<FgBgCallback> copy;
        synchronized (this.mFgBgCallbacks) {
            copy = new HashSet<>(this.mFgBgCallbacks);
        }
        for (FgBgCallback onMoveToBackground : copy) {
            onMoveToBackground.onMoveToBackground(activityName);
        }
    }

    private static boolean hasNoStoppedActivities(@NonNull Context context, @Nullable Activity excludeActivity) {
        if (sActivities == null) {
            sActivities = getActivities(context);
        }
        Collection activityRecords = sActivities.values();
        if (!activityRecords.isEmpty()) {
            for (Object obj : activityRecords) {
                if (obj != null && !((Boolean) ReflectUtil.getFieldValue(obj, (String) "stopped")).booleanValue()) {
                    if (excludeActivity == null || ReflectUtil.getFieldValue(obj, (String) WidgetType.ACTIVITY) != excludeActivity) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static Map<?, ?> getActivities(@NonNull Context context) {
        if (sActivityThread == null) {
            sActivityThread = ReflectUtil.getFieldValue((Object) getContextImpl(context), (String) "mMainThread");
        }
        return (Map) ReflectUtil.getFieldValue(sActivityThread, (String) "mActivities");
    }

    private static Context getContextImpl(Context context) {
        if (context instanceof ContextWrapper) {
            return getContextImpl(((ContextWrapper) context).getBaseContext());
        }
        return context;
    }
}
