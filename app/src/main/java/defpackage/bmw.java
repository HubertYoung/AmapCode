package defpackage;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.os.Debug.MemoryInfo;
import android.text.TextUtils;
import android.text.format.Formatter;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Callable;

/* renamed from: bmw reason: default package */
/* compiled from: SystemUtils */
public final class bmw {
    public static String a(Context context) {
        try {
            long j = Runtime.getRuntime().totalMemory();
            long freeMemory = Runtime.getRuntime().freeMemory();
            StringBuilder sb = new StringBuilder();
            sb.append(Formatter.formatFileSize(context, j - freeMemory));
            sb.append("/");
            sb.append(Formatter.formatFileSize(context, j));
            return sb.toString();
        } catch (Exception e) {
            return bmx.a((Throwable) e);
        }
    }

    public static MemoryInfo a(Context context, int i) {
        try {
            int[] iArr = {i};
            ActivityManager activityManager = (ActivityManager) context.getSystemService(WidgetType.ACTIVITY);
            MemoryInfo[] memoryInfoArr = new MemoryInfo[0];
            if (activityManager != null) {
                memoryInfoArr = activityManager.getProcessMemoryInfo(iArr);
            }
            if (memoryInfoArr != null && memoryInfoArr.length > 0) {
                return memoryInfoArr[0];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String b(Context context, int i) {
        String str = null;
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(WidgetType.ACTIVITY);
            if (activityManager != null) {
                Iterator<RunningAppProcessInfo> it = activityManager.getRunningAppProcesses().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    RunningAppProcessInfo next = it.next();
                    if (next.pid == i) {
                        str = next.processName;
                        break;
                    }
                }
            }
            return !TextUtils.isEmpty(str) ? str : "";
        } catch (Exception e) {
            return bmx.a((Throwable) e);
        }
    }

    public static Map<Thread, StackTraceElement[]> a() {
        final HashMap hashMap = new HashMap();
        ArrayList arrayList = new ArrayList();
        arrayList.add(new Callable<Object>() {
            public final Object call() throws Exception {
                hashMap.putAll(Thread.getAllStackTraces());
                return null;
            }
        });
        bmx.a(arrayList, 2000, 1);
        return hashMap;
    }
}
