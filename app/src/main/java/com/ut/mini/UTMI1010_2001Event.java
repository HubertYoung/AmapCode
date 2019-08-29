package com.ut.mini;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import com.alibaba.analytics.AnalyticsMgr;
import com.alibaba.analytics.core.ClientVariables;
import com.alibaba.analytics.core.Constants.LogContentKeys;
import com.alibaba.analytics.utils.Logger;
import com.alibaba.analytics.utils.TaskExecutor;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.ut.mini.internal.UTOriginalCustomHitBuilder;
import com.ut.mini.module.appstatus.UTAppStatusDelayCallbacks;
import java.util.List;

class UTMI1010_2001Event implements UTAppStatusDelayCallbacks {
    private long mHowLongForegroundStay = 0;
    private long mToBackgroundTimestamp = 0;
    private long mToForegroundTimestamp = 0;

    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    public void onActivityStarted(Activity activity) {
    }

    public void onActivityStopped(Activity activity) {
    }

    public void onSwitchBackground() {
    }

    UTMI1010_2001Event() {
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0051  */
    /* JADX WARNING: Removed duplicated region for block: B:18:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onEventArrive(java.lang.Object r8) {
        /*
            r7 = this;
            java.util.Map r8 = (java.util.Map) r8
            com.alibaba.analytics.core.model.LogField r0 = com.alibaba.analytics.core.model.LogField.EVENTID
            java.lang.String r0 = r0.toString()
            boolean r0 = r8.containsKey(r0)
            if (r0 == 0) goto L_0x0058
            com.alibaba.analytics.core.model.LogField r0 = com.alibaba.analytics.core.model.LogField.EVENTID
            java.lang.String r0 = r0.toString()
            java.lang.Object r0 = r8.get(r0)
            java.lang.String r0 = (java.lang.String) r0
            java.lang.String r1 = "2001"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0058
            com.alibaba.analytics.core.model.LogField r0 = com.alibaba.analytics.core.model.LogField.ARG3
            java.lang.String r0 = r0.toString()
            boolean r0 = r8.containsKey(r0)
            r1 = 0
            if (r0 == 0) goto L_0x0045
            com.alibaba.analytics.core.model.LogField r0 = com.alibaba.analytics.core.model.LogField.ARG3
            java.lang.String r0 = r0.toString()
            java.lang.Object r8 = r8.get(r0)
            java.lang.String r8 = (java.lang.String) r8
            long r3 = java.lang.Long.parseLong(r8)     // Catch:{ Exception -> 0x0041 }
            goto L_0x0046
        L_0x0041:
            r8 = move-exception
            r8.printStackTrace()
        L_0x0045:
            r3 = r1
        L_0x0046:
            long r5 = r7.mHowLongForegroundStay
            long r5 = r5 + r3
            r7.mHowLongForegroundStay = r5
            boolean r8 = _isSwitchBackgroundByGetTask()
            if (r8 == 0) goto L_0x0058
            long r3 = r7.mHowLongForegroundStay
            r7._send1010Hit(r3)
            r7.mHowLongForegroundStay = r1
        L_0x0058:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ut.mini.UTMI1010_2001Event.onEventArrive(java.lang.Object):void");
    }

    private static boolean _isSwitchBackgroundByGetTask() {
        try {
            Context context = ClientVariables.getInstance().getContext();
            if (context != null) {
                String packageName = context.getPackageName();
                if (packageName != null) {
                    ActivityManager activityManager = (ActivityManager) context.getSystemService(WidgetType.ACTIVITY);
                    if (activityManager != null) {
                        try {
                            List<RunningTaskInfo> runningTasks = activityManager.getRunningTasks(1);
                            if (runningTasks != null && runningTasks.size() > 0) {
                                ComponentName componentName = runningTasks.get(0).topActivity;
                                return componentName == null || !packageName.contains(componentName.getPackageName());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (Exception unused) {
            return false;
        }
    }

    private void _send1010Hit(long j) {
        if (!ClientVariables.getInstance().is1010AutoTrackClosed()) {
            long j2 = 0;
            if (j > 0) {
                if (0 != this.mToBackgroundTimestamp) {
                    j2 = SystemClock.elapsedRealtime() - this.mToBackgroundTimestamp;
                }
                UTOriginalCustomHitBuilder uTOriginalCustomHitBuilder = new UTOriginalCustomHitBuilder("UT", 1010, String.valueOf(j), String.valueOf(j2), null, null);
                uTOriginalCustomHitBuilder.setProperty(LogContentKeys.PRIORITY, "5");
                UTTracker defaultTracker = UTAnalytics.getInstance().getDefaultTracker();
                if (defaultTracker != null) {
                    defaultTracker.send(uTOriginalCustomHitBuilder.build());
                } else {
                    Logger.d((String) "Record app display event error", "Fatal Error,must call setRequestAuthentication method first.");
                }
            }
        }
    }

    public void onSwitchBackgroundDelay() {
        UTPageHitHelper.getInstance().pageSwitchBackground();
        _send1010Hit(SystemClock.elapsedRealtime() - this.mToForegroundTimestamp);
        this.mToBackgroundTimestamp = SystemClock.elapsedRealtime();
        AnalyticsMgr.dispatchSaveCacheDataToLocal();
        TaskExecutor.getInstance().schedule(null, new Runnable() {
            public void run() {
                AnalyticsMgr.dispatchLocalHits();
            }
        }, 2000);
    }

    public void onSwitchForeground() {
        this.mToForegroundTimestamp = SystemClock.elapsedRealtime();
    }

    public void onActivityDestroyed(Activity activity) {
        UTPageHitHelper.getInstance().pageDestroyed(activity);
    }

    public void onActivityPaused(Activity activity) {
        UTPageHitHelper.getInstance().pageDisAppearByAuto(activity);
    }

    public void onActivityResumed(Activity activity) {
        UTPageHitHelper.getInstance().pageAppearByAuto(activity);
    }
}
