package com.squareup.leakcanary;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Build.VERSION;
import android.os.Bundle;
import com.alipay.mobile.framework.util.xml.MetaInfoXmlParser;

@TargetApi(14)
public final class ActivityRefWatcher {
    private final Application application;
    private final ActivityLifecycleCallbacks lifecycleCallbacks = new ActivityLifecycleCallbacks() {
        public void onActivityCreated(Activity activity, Bundle bundle) {
        }

        public void onActivityPaused(Activity activity) {
        }

        public void onActivityResumed(Activity activity) {
        }

        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        public void onActivityStarted(Activity activity) {
        }

        public void onActivityStopped(Activity activity) {
        }

        public void onActivityDestroyed(Activity activity) {
            ActivityRefWatcher.this.onActivityDestroyed(activity);
        }
    };
    private final RefWatcher refWatcher;

    public static void installOnIcsPlus(Application application2, RefWatcher refWatcher2) {
        if (VERSION.SDK_INT >= 14) {
            new ActivityRefWatcher(application2, refWatcher2).watchActivities();
        }
    }

    public ActivityRefWatcher(Application application2, RefWatcher refWatcher2) {
        this.application = (Application) Preconditions.checkNotNull(application2, MetaInfoXmlParser.KEY_APPLICATION);
        this.refWatcher = (RefWatcher) Preconditions.checkNotNull(refWatcher2, "refWatcher");
    }

    /* access modifiers changed from: 0000 */
    public final void onActivityDestroyed(Activity activity) {
        this.refWatcher.watch(activity);
    }

    public final void watchActivities() {
        stopWatchingActivities();
        this.application.registerActivityLifecycleCallbacks(this.lifecycleCallbacks);
    }

    public final void stopWatchingActivities() {
        this.application.unregisterActivityLifecycleCallbacks(this.lifecycleCallbacks);
    }
}
