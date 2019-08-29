package com.amap.location.sdk.e;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.os.Process;
import android.support.annotation.NonNull;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;

/* compiled from: ProcessUtil */
public class f {
    public static String a(@NonNull Context context) {
        int myPid = Process.myPid();
        String str = "";
        for (RunningAppProcessInfo next : ((ActivityManager) context.getSystemService(WidgetType.ACTIVITY)).getRunningAppProcesses()) {
            if (next.pid == myPid) {
                str = next.processName;
            }
        }
        return str;
    }

    public static boolean b(@NonNull Context context) {
        return context.getPackageName().equals(a(context));
    }
}
