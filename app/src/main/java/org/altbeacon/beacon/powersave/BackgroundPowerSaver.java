package org.altbeacon.beacon.powersave;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.NonNull;
import org.altbeacon.beacon.b.d;
import org.altbeacon.beacon.g;

@TargetApi(18)
public class BackgroundPowerSaver implements ActivityLifecycleCallbacks {
    @NonNull
    private final g a;
    private int b = 0;

    public BackgroundPowerSaver(Context context) {
        if (VERSION.SDK_INT < 18) {
            d.c("BackgroundPowerSaver", "BackgroundPowerSaver requires API 18 or higher.", new Object[0]);
        }
        this.a = g.a(context);
        ((Application) context.getApplicationContext()).registerActivityLifecycleCallbacks(this);
    }

    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    public void onActivityStarted(Activity activity) {
    }

    public void onActivityResumed(Activity activity) {
        this.b++;
        if (this.b <= 0) {
            d.a("BackgroundPowerSaver", "reset active activity count on resume.  It was %s", Integer.valueOf(this.b));
            this.b = 1;
        }
        this.a.a(false);
        d.a("BackgroundPowerSaver", "activity resumed: %s active activities: %s", activity, Integer.valueOf(this.b));
    }

    public void onActivityPaused(Activity activity) {
        this.b--;
        d.a("BackgroundPowerSaver", "activity paused: %s active activities: %s", activity, Integer.valueOf(this.b));
        if (this.b <= 0) {
            d.a("BackgroundPowerSaver", "setting background mode", new Object[0]);
            this.a.a(true);
        }
    }

    public void onActivityStopped(Activity activity) {
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    public void onActivityDestroyed(Activity activity) {
    }
}
