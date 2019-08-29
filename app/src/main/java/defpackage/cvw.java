package defpackage;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Debug;
import android.os.Debug.MemoryInfo;
import android.os.Process;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.minimap.bundle.apm.data.DeviceInfoManager.a;

/* renamed from: cvw reason: default package */
/* compiled from: MemoryTracker */
public final class cvw {
    public static volatile cvv a;

    public static cvv a(Context context) {
        MemoryInfo b = b(context);
        if (b != null) {
            cvv cvv = new cvv();
            cvv.a = cwm.a();
            cvv.b = b.getTotalPss() / 1024;
            int i = b.dalvikPss / 1024;
            if (i == 0) {
                i = (((int) Runtime.getRuntime().totalMemory()) / 1024) / 1024;
            }
            cvv.c = i;
            int i2 = b.nativePss / 1024;
            if (i2 == 0) {
                i2 = (((int) Debug.getNativeHeapSize()) / 1024) / 1024;
            }
            cvv.d = i2;
            cwl.b("memory", "memoryRecord.dalvikPss:", Integer.valueOf(cvv.c), "memoryRecord.nativePss:", Integer.valueOf(cvv.d), "memoryRecord.totalPss:", Integer.valueOf(cvv.b));
            a = cvv;
            return cvv;
        }
        a = null;
        return null;
    }

    private static MemoryInfo b(Context context) {
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(WidgetType.ACTIVITY);
            if (activityManager != null) {
                if (a.a.s.intValue() >= 23) {
                    MemoryInfo[] processMemoryInfo = activityManager.getProcessMemoryInfo(new int[]{Process.myPid()});
                    if (processMemoryInfo != null) {
                        return processMemoryInfo[0];
                    }
                } else {
                    MemoryInfo memoryInfo = new MemoryInfo();
                    try {
                        Debug.getMemoryInfo(memoryInfo);
                        if (memoryInfo.getTotalPrivateDirty() != 0) {
                            return memoryInfo;
                        }
                        Debug.getMemoryInfo(memoryInfo);
                        return memoryInfo;
                    } catch (Exception unused) {
                        return memoryInfo;
                    }
                }
            }
        } catch (Exception unused2) {
        }
        return null;
    }
}
