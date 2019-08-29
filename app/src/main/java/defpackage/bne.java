package defpackage;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;
import com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool;

/* renamed from: bne reason: default package */
/* compiled from: ActivityStatusMonitor */
public final class bne implements ActivityLifecycleCallbacks {
    public final Application a;
    public boolean b;
    private int c;

    public final void onActivityPaused(Activity activity) {
    }

    public final void onActivityResumed(Activity activity) {
    }

    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    public bne(Application application) {
        this.a = application;
    }

    public final void onActivityCreated(Activity activity, Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        sb.append(activity.getClass().getSimpleName());
        sb.append(AUScreenAdaptTool.PREFIX_ID);
        sb.append(activity.hashCode());
        bmp.b();
    }

    public final void onActivityStarted(Activity activity) {
        if (this.c == 0) {
            bmp.a((String) "RealForeground", true);
        }
        this.c++;
        StringBuilder sb = new StringBuilder();
        sb.append(activity.getClass().getSimpleName());
        sb.append(AUScreenAdaptTool.PREFIX_ID);
        sb.append(activity.hashCode());
        String sb2 = sb.toString();
        bmp.a((String) "CurrentActivity", sb2);
        bmp.a((String) "LastActivity", sb2);
        bmp.b();
    }

    public final void onActivityStopped(Activity activity) {
        this.c--;
        if (this.c == 0) {
            bmp.a((String) "RealForeground", false);
            bmp.a((String) "CurrentActivity", (String) null);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(activity.getClass().getSimpleName());
        sb.append(AUScreenAdaptTool.PREFIX_ID);
        sb.append(activity.hashCode());
        bmp.b();
    }

    public final void onActivityDestroyed(Activity activity) {
        StringBuilder sb = new StringBuilder();
        sb.append(activity.getClass().getSimpleName());
        sb.append(AUScreenAdaptTool.PREFIX_ID);
        sb.append(activity.hashCode());
        bmp.b();
    }
}
