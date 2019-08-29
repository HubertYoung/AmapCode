package defpackage;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.ActivityManager.AppTask;
import android.app.ActivityManager.RecentTaskInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.common.utils.Constant;
import java.util.List;

/* renamed from: cgz reason: default package */
/* compiled from: AppUtils */
public final class cgz {
    public static boolean a(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(WidgetType.ACTIVITY);
        boolean z = false;
        if (activityManager == null) {
            return false;
        }
        if (VERSION.SDK_INT < 23) {
            List<RunningTaskInfo> runningTasks = activityManager.getRunningTasks(Integer.MAX_VALUE);
            if (runningTasks != null) {
                for (RunningTaskInfo next : runningTasks) {
                    if (a(context, next)) {
                        activityManager.moveTaskToFront(next.id, 2);
                        z = true;
                    }
                }
            }
        } else {
            List<AppTask> appTasks = activityManager.getAppTasks();
            if (appTasks != null) {
                for (AppTask next2 : appTasks) {
                    if (a(next2.getTaskInfo())) {
                        next2.moveToFront();
                        z = true;
                    }
                }
            }
        }
        return z;
    }

    @TargetApi(23)
    private static boolean a(RecentTaskInfo recentTaskInfo) {
        if (recentTaskInfo == null || recentTaskInfo.numActivities <= 0 || recentTaskInfo.baseActivity == null) {
            return false;
        }
        return TextUtils.equals(recentTaskInfo.baseActivity.getClassName(), Constant.LAUNCHER_ACTIVITY_NAME);
    }

    private static boolean a(Context context, RunningTaskInfo runningTaskInfo) {
        if (runningTaskInfo == null || runningTaskInfo.numActivities <= 0 || runningTaskInfo.baseActivity == null) {
            return false;
        }
        String packageName = runningTaskInfo.baseActivity.getPackageName();
        String className = runningTaskInfo.baseActivity.getClassName();
        if (!TextUtils.equals(packageName, context.getPackageName()) || !TextUtils.equals(className, Constant.LAUNCHER_ACTIVITY_NAME)) {
            return false;
        }
        return true;
    }
}
