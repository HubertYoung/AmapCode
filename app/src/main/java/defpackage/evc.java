package defpackage;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.os.Bundle;

@SuppressLint({"NewApi"})
/* renamed from: evc reason: default package */
/* compiled from: HMTActivityLifecycleCallbacks */
public class evc implements ActivityLifecycleCallbacks {
    private static final String a = "evc";

    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    public void onActivityStarted(Activity activity) {
    }

    public void onActivityStopped(Activity activity) {
    }

    public void onActivityCreated(Activity activity, Bundle bundle) {
        if (evd.a) {
            eve.a(activity.getIntent(), activity.getApplicationContext());
        }
    }

    public void onActivityDestroyed(Activity activity) {
        euv.b(activity);
    }

    public void onActivityPaused(Activity activity) {
        euv.a((Context) activity);
    }

    public void onActivityResumed(Activity activity) {
        euv.c(activity);
    }
}
