package com.autonavi.amap.app;

import android.app.Activity;
import android.app.Application;
import java.lang.ref.WeakReference;

public class AMapAppGlobal {
    private static Application sApplication;
    private static WeakReference<Activity> sLastActivityRef;

    private AMapAppGlobal() {
    }

    public static void setActivity(Activity activity) {
        if (activity != null) {
            sLastActivityRef = new WeakReference<>(activity);
        }
    }

    static void setApplication(Application application) {
        sApplication = application;
    }

    public static Application getApplication() {
        return sApplication;
    }

    public static Activity getTopActivity() {
        if (sLastActivityRef == null) {
            return null;
        }
        return (Activity) sLastActivityRef.get();
    }
}
