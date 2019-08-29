package com.alipay.mobile.framework.locale;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.MicroApplicationContext;
import com.alipay.mobile.quinox.utils.TraceLogger;

public class LocaleUtils {
    /* access modifiers changed from: private */
    public static final String a = LocaleHelper.class.getSimpleName();

    public static boolean refreshHomeActivity(Activity localeSetting, final String appId, final Bundle bundle) {
        LauncherApplicationAgent agent = LauncherApplicationAgent.getInstance();
        if (agent == null) {
            return false;
        }
        final MicroApplicationContext context = agent.getMicroApplicationContext();
        if (context == null) {
            return false;
        }
        boolean isSuccess = context.finishAllActivities(localeSetting);
        TraceLogger.i(a, "refreshHomeActivity: " + isSuccess);
        if (!isSuccess) {
            return false;
        }
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public final void run() {
                try {
                    Bundle bundleMain = new Bundle();
                    bundleMain.putBoolean("disableWelcomeFromLocaleHelper", true);
                    context.startApp(null, "20000001", bundleMain);
                    if (!TextUtils.isEmpty(appId)) {
                        context.startApp(null, appId, bundle);
                    }
                } catch (Exception e) {
                    TraceLogger.w(LocaleUtils.a, "refreshHomeActivity", e);
                }
            }
        });
        return true;
    }
}
