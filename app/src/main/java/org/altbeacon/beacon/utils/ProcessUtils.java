package org.altbeacon.beacon.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.os.Process;
import android.support.annotation.NonNull;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;

public class ProcessUtils {
    Context a;

    public ProcessUtils(@NonNull Context context) {
        this.a = context;
    }

    public final String a() {
        for (RunningAppProcessInfo processInfo : ((ActivityManager) this.a.getSystemService(WidgetType.ACTIVITY)).getRunningAppProcesses()) {
            if (processInfo.pid == c()) {
                return processInfo.processName;
            }
        }
        return null;
    }

    public final String b() {
        return this.a.getApplicationContext().getPackageName();
    }

    public static int c() {
        return Process.myPid();
    }

    public final boolean d() {
        return b().equals(a());
    }
}
