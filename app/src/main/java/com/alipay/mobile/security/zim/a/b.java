package com.alipay.mobile.security.zim.a;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.os.Bundle;
import com.alipay.mobile.security.bio.utils.BioLog;

/* compiled from: ZimActivityLifecycleCallbacks */
public class b implements ActivityLifecycleCallbacks {
    private static b b;
    protected Context a;

    private b(Context context) {
        this.a = context.getApplicationContext();
    }

    public static b a(Application application) {
        if (b == null) {
            synchronized (b.class) {
                if (b == null) {
                    b bVar = new b(application);
                    BioLog.w((String) "ZimPlatform", (String) "application.registerActivityLifecycleCallbacks(ZimActivityLifecycleCallbacks)");
                    application.registerActivityLifecycleCallbacks(bVar);
                    b = bVar;
                }
            }
        }
        return b;
    }

    public void onActivityCreated(Activity activity, Bundle bundle) {
        BioLog.d("ZimPlatform", activity.getClass().getSimpleName() + ".onActivityCreated(activity=" + activity + ", savedInstanceState=" + bundle + ")");
    }

    public void onActivityStarted(Activity activity) {
        BioLog.d("ZimPlatform", activity.getClass().getSimpleName() + ".onActivityStarted(activity=" + activity + ")");
    }

    public void onActivityResumed(Activity activity) {
        BioLog.d("ZimPlatform", activity.getClass().getSimpleName() + ".onActivityResumed(activity=" + activity + ")");
    }

    public void onActivityPaused(Activity activity) {
        BioLog.d("ZimPlatform", activity.getClass().getSimpleName() + ".onActivityPaused(activity=" + activity + ")");
    }

    public void onActivityStopped(Activity activity) {
        BioLog.d("ZimPlatform", activity.getClass().getSimpleName() + ".onActivityStopped(activity=" + activity + ")");
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        BioLog.d("ZimPlatform", activity.getClass().getSimpleName() + ".onActivitySaveInstanceState(activity=" + activity + ", outState=" + bundle + ")");
    }

    public void onActivityDestroyed(Activity activity) {
        BioLog.d("ZimPlatform", activity.getClass().getSimpleName() + ".onActivityDestroyed(activity=" + activity + ")");
    }
}
