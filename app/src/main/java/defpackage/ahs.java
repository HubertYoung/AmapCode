package defpackage;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.os.Process;
import android.text.TextUtils;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import java.util.List;

/* renamed from: ahs reason: default package */
/* compiled from: ProcessUtils */
public class ahs {
    private static Boolean a;

    public static boolean a(Context context) {
        boolean booleanValue;
        if (a != null) {
            return a.booleanValue();
        }
        if (context == null) {
            return false;
        }
        synchronized (ahs.class) {
            try {
                if (a == null) {
                    a = Boolean.valueOf(TextUtils.equals(context.getPackageName(), b(context)));
                }
                booleanValue = a.booleanValue();
            }
        }
        return booleanValue;
    }

    public static boolean a(String str, Context context) {
        String b = b(context);
        if (b != null) {
            return b.equalsIgnoreCase(str);
        }
        return false;
    }

    public static String b(Context context) {
        int myPid = Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(WidgetType.ACTIVITY);
        if (activityManager == null) {
            return null;
        }
        List<RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
        if (runningAppProcesses == null) {
            return null;
        }
        for (RunningAppProcessInfo next : runningAppProcesses) {
            if (next.pid == myPid) {
                return next.processName;
            }
        }
        return null;
    }
}
