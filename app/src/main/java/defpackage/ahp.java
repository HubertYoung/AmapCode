package defpackage;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import java.util.ArrayList;
import java.util.List;

/* renamed from: ahp reason: default package */
/* compiled from: AppOps */
public final class ahp {

    /* renamed from: ahp$a */
    /* compiled from: AppOps */
    public static class a {
        private static volatile a c;
        public String a = "";
        public int b;

        private a() {
            try {
                PackageInfo packageInfo = AMapAppGlobal.getApplication().getPackageManager().getPackageInfo(AMapAppGlobal.getApplication().getPackageName(), 0);
                if (packageInfo != null) {
                    this.b = packageInfo.versionCode;
                    this.a = packageInfo.versionName;
                }
            } catch (NameNotFoundException e) {
                e.printStackTrace();
            }
        }

        public static a a() {
            if (c == null) {
                synchronized (a.class) {
                    try {
                        if (c == null) {
                            c = new a();
                        }
                    }
                }
            }
            return c;
        }
    }

    public static boolean a(Context context, String str) {
        List<PackageInfo> installedPackages = context.getPackageManager().getInstalledPackages(0);
        ArrayList arrayList = new ArrayList();
        if (installedPackages != null) {
            for (int i = 0; i < installedPackages.size(); i++) {
                arrayList.add(installedPackages.get(i).packageName);
            }
        }
        return arrayList.contains(str);
    }

    public static boolean a(String str) {
        try {
            AMapAppGlobal.getApplication().getPackageManager().getPackageInfo(str, 1);
            return true;
        } catch (NameNotFoundException unused) {
            return false;
        }
    }

    public static boolean a(Context context) {
        if (context == null) {
            return false;
        }
        ActivityManager activityManager = (ActivityManager) context.getSystemService(WidgetType.ACTIVITY);
        if (activityManager == null) {
            return false;
        }
        try {
            List<RunningTaskInfo> runningTasks = activityManager.getRunningTasks(1);
            if (runningTasks == null || runningTasks.size() <= 0) {
                return false;
            }
            RunningTaskInfo runningTaskInfo = runningTasks.get(0);
            if (runningTaskInfo == null || runningTaskInfo.topActivity == null) {
                return false;
            }
            return TextUtils.equals(context.getPackageName(), runningTaskInfo.topActivity.getPackageName());
        } catch (Exception unused) {
            return false;
        }
    }

    public static void b(Context context, String str) {
        try {
            context.startActivity(context.getPackageManager().getLaunchIntentForPackage(str));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
