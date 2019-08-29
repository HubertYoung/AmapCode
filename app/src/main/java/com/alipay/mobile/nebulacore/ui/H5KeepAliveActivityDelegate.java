package com.alipay.mobile.nebulacore.ui;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.AppTask;
import android.app.ActivityManager.RecentTaskInfo;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.api.H5Session;
import com.alipay.mobile.nebula.util.H5KeepAliveUtil;
import com.alipay.mobile.nebula.util.H5KeepAliveUtil.DestroyRunnable;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.wallet.H5WalletWrapper;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import java.util.concurrent.TimeUnit;

public class H5KeepAliveActivityDelegate {
    private final String a = H5KeepAliveUtil.TAG;
    private DestroyRunnable b = null;

    public void afterCreate(Activity activity) {
        H5Log.d(H5KeepAliveUtil.TAG, "afterCreate hasStartActivity" + H5KeepAliveUtil.hasStartActivity);
        if (!H5KeepAliveUtil.hasStartActivity) {
            try {
                H5Log.w(H5KeepAliveUtil.TAG, "afterCreate with fatal! restart 20000001");
                Bundle bundle = new Bundle();
                bundle.putString("actionType", "20000002");
                LauncherApplicationAgent.getInstance().getMicroApplicationContext().startApp(null, "20000001", bundle);
                activity.finish();
            } catch (Throwable t) {
                H5Log.w(H5KeepAliveUtil.TAG, "afterCreate!", t);
            }
        }
    }

    public void removeDestroyRunnable() {
        H5Log.d(H5KeepAliveUtil.TAG, "removeDestroyRunnable " + this.b);
        if (this.b != null) {
            this.b.setInvalid();
            H5Utils.getScheduledExecutor().remove(this.b);
            this.b = null;
        }
    }

    public void putRunningTinyActivity(String appId, Activity activity) {
        H5KeepAliveUtil.putRunningTinyActivity(appId, activity);
    }

    public void onUserLeaveHint(H5Session session) {
        H5Log.d(H5KeepAliveUtil.TAG, "onUserLeaveHint, schedule destroy");
        removeDestroyRunnable();
        this.b = new DestroyRunnable(session);
        long keepAliveTime = 0;
        String keepAliveConfig = H5WalletWrapper.getConfig("h5_mainProcKeepAliveMS");
        if (!TextUtils.isEmpty(keepAliveConfig)) {
            keepAliveTime = H5Utils.parseLong(keepAliveConfig);
        }
        if (keepAliveTime <= 0) {
            keepAliveTime = 300000;
        }
        H5Utils.getScheduledExecutor().schedule(this.b, keepAliveTime, TimeUnit.MILLISECONDS);
    }

    public void onDestroy(Activity activity) {
        H5Log.d(H5KeepAliveUtil.TAG, "onDestroy with activity: " + activity);
        a(activity);
        removeDestroyRunnable();
        H5KeepAliveUtil.removeRunningTinyActivity();
    }

    private static void a(Activity activity) {
        H5Log.d(H5KeepAliveUtil.TAG, "try removeTask");
        try {
            if (VERSION.SDK_INT >= 21) {
                for (AppTask appTask : ((ActivityManager) LauncherApplicationAgent.getInstance().getApplicationContext().getSystemService(WidgetType.ACTIVITY)).getAppTasks()) {
                    RecentTaskInfo recentTaskInfo = null;
                    recentTaskInfo = appTask.getTaskInfo();
                    if (recentTaskInfo != null) {
                        if (!(recentTaskInfo.baseIntent == null || recentTaskInfo.baseIntent.getComponent() == null || !activity.getClass().getName().equals(recentTaskInfo.baseIntent.getComponent().getClassName()))) {
                            LoggerFactory.getTraceLogger().warn((String) H5KeepAliveUtil.TAG, (String) "removeFromRecentTasksList");
                            appTask.finishAndRemoveTask();
                        }
                    }
                }
            }
        } catch (Throwable t) {
            H5Log.e((String) H5KeepAliveUtil.TAG, t);
        }
    }
}
