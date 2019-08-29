package defpackage;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build.VERSION;
import android.os.Debug;
import android.os.Debug.MemoryInfo;
import android.os.Process;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.text.TextUtils;
import com.autonavi.common.tool.CrashLogUtil;
import com.autonavi.common.tool.dumpcrash;
import com.autonavi.common.tool.thirdparty.df.AmapDf;
import com.autonavi.common.tool.thirdparty.df.AmapUInfo;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;

/* renamed from: bmo reason: default package */
/* compiled from: CommonCollector */
public final class bmo {
    private static Object a;

    public static void a(Application application) {
        try {
            if (VERSION.SDK_INT < 19) {
                a = Class.forName("android.app.ActivityThread", true, application.getClassLoader()).getDeclaredMethod("currentActivityThread", new Class[0]).invoke(null, new Object[0]);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e2) {
            e2.printStackTrace();
        } catch (InvocationTargetException e3) {
            e3.printStackTrace();
        } catch (IllegalAccessException e4) {
            e4.printStackTrace();
        } catch (Throwable unused) {
        }
    }

    public static String a(Application application, String str) {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("\n\nVerifyDex:\n");
            sb.append("\tbuildNum:");
            sb.append(CrashLogUtil.getControler().l());
            sb.append("\n");
            sb.append("\tjobName:");
            sb.append(CrashLogUtil.getControler().m());
            sb.append("\n");
            String str2 = null;
            try {
                str2 = new File(str).getCanonicalPath();
            } catch (Throwable unused) {
            }
            sb.append("\nSOlib:");
            sb.append(str);
            sb.append(" -> ");
            sb.append(str2);
            sb.append("\n");
            ArrayList arrayList = new ArrayList();
            arrayList.add(application);
            if (!(CrashLogUtil.getControler().y() == null || application == CrashLogUtil.getControler().y().getApplication())) {
                arrayList.add(application);
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                Application application2 = (Application) it.next();
                sb.append("\napkInfo:\n");
                ApplicationInfo applicationInfo = application2.getApplicationInfo();
                if (applicationInfo != null) {
                    sb.append("\tlibDir=");
                    sb.append(applicationInfo.nativeLibraryDir);
                    sb.append("\n");
                    sb.append("\tappUid=");
                    sb.append(applicationInfo.uid);
                    sb.append(" myUid=");
                    sb.append(Process.myUid());
                    sb.append("\n");
                    sb.append("\tsourceDir=");
                    sb.append(applicationInfo.sourceDir);
                    sb.append("\n");
                    sb.append("\tpublicSourceDir=");
                    sb.append(applicationInfo.publicSourceDir);
                    sb.append("\n");
                    sb.append("\tkeystore=");
                    sb.append(bmx.a((Context) application2));
                    sb.append("\n");
                    sb.append("\t");
                    sb.append(bmx.a(applicationInfo.sourceDir));
                    CrashLogUtil.getControler();
                }
            }
            sb.append(a());
        } catch (Throwable unused2) {
        }
        return sb.toString();
    }

    public static String a(Application application, String str, Thread thread) {
        String str2;
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("\n\nVerifyDex:\n");
            sb.append("\tbuildNum:");
            sb.append(CrashLogUtil.getControler().l());
            sb.append("\n");
            sb.append("\tjobName:");
            sb.append(CrashLogUtil.getControler().m());
            sb.append("\n");
        } catch (Throwable unused) {
        }
        try {
            sb.append("\n");
            sb.append(dumpcrash.getMaps(10));
        } catch (Throwable unused2) {
        }
        String str3 = null;
        try {
            str2 = new File(str).getCanonicalPath();
        } catch (Throwable unused3) {
            str2 = null;
        }
        try {
            sb.append("\nSOlib:");
            sb.append(str);
            sb.append(" -> ");
            sb.append(str2);
            sb.append("\n");
            ArrayList arrayList = new ArrayList();
            arrayList.add(application);
            if (!(CrashLogUtil.getControler().y() == null || application == CrashLogUtil.getControler().y().getApplication())) {
                arrayList.add(application);
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                Application application2 = (Application) it.next();
                sb.append("\napkInfo:\n");
                ApplicationInfo applicationInfo = application2.getApplicationInfo();
                if (applicationInfo != null) {
                    sb.append("\tlibDir=");
                    sb.append(applicationInfo.nativeLibraryDir);
                    sb.append("\n");
                    sb.append("\tappUid=");
                    sb.append(applicationInfo.uid);
                    sb.append(" myUid=");
                    sb.append(Process.myUid());
                    sb.append("\n");
                    sb.append("\tsourceDir=");
                    sb.append(applicationInfo.sourceDir);
                    sb.append("\n");
                    sb.append("\tpublicSourceDir=");
                    sb.append(applicationInfo.publicSourceDir);
                    sb.append("\n");
                    sb.append("\tkeystore=");
                    sb.append(bmx.a((Context) application2));
                    sb.append("\n");
                    sb.append("\t");
                    sb.append(bmx.a(applicationInfo.sourceDir));
                    CrashLogUtil.getControler();
                }
            }
            String F = CrashLogUtil.getControler().F();
            if (!TextUtils.isEmpty(F)) {
                sb.append("NavigationPath:");
                sb.append(F);
                sb.append(10);
            }
            AmapDf amapDf = new AmapDf();
            AmapUInfo uInfo = amapDf.getUInfo("/data", 2);
            StringBuilder sb2 = new StringBuilder();
            if (uInfo != null && uInfo.getCode() == 0 && uInfo.getPrecentUsed() >= 98) {
                sb2.append("\tinodes: ");
                sb2.append(uInfo.getDevice());
                sb2.append("  ");
                sb2.append(uInfo.getTotal());
                sb2.append("  ");
                sb2.append(uInfo.getAvail());
                sb2.append("  ");
                sb2.append(uInfo.getPrecentUsed());
                sb2.append("%  ");
                sb2.append(uInfo.getMountPoint());
                sb2.append("\n");
            }
            AmapUInfo uInfo2 = amapDf.getUInfo("/data", 1);
            if (uInfo2 != null && uInfo2.getCode() == 0 && uInfo2.getPrecentUsed() >= 98) {
                sb2.append("\tkblocks: ");
                sb2.append(uInfo2.getDevice());
                sb2.append("  ");
                sb2.append(uInfo2.getTotal());
                sb2.append("  ");
                sb2.append(uInfo2.getAvail());
                sb2.append("  ");
                sb2.append(uInfo2.getPrecentUsed());
                sb2.append("%  ");
                sb2.append(uInfo2.getMountPoint());
                sb2.append("\n");
            }
            String sb3 = sb2.toString();
            if (!TextUtils.isEmpty(sb3)) {
                str3 = "DF Info:  \n".concat(String.valueOf(sb3));
            }
            if (!TextUtils.isEmpty(str3)) {
                sb.append(str3);
            }
            sb.append(a());
            int myPid = Process.myPid();
            sb.append("ProcessName: ");
            sb.append(bmw.b(application, myPid));
            sb.append(10);
            if (thread != null) {
                sb.append("ThreadID: ");
                sb.append(thread.getId());
                sb.append(10);
                sb.append("ThreadName: ");
                sb.append(thread.getName());
                sb.append(10);
                sb.append("ThreadPriority: ");
                sb.append(thread.getPriority());
                sb.append(10);
                sb.append("ThreadState: ");
                sb.append(thread.getState().toString());
                sb.append(10);
                sb.append("ThreadGroup: ");
                sb.append(thread.getThreadGroup().getName());
                sb.append(10);
            }
            sb.append("SystemMem: ");
            sb.append(bmw.a(application));
            sb.append(10);
            sb.append(10);
            sb.append("App Memory Info:\n");
            MemoryInfo a2 = bmw.a(application, myPid);
            if (a2 != null) {
                a(sb, a2);
            } else {
                sb.append("\tMemory Info is null\n");
            }
            sb.append(10);
            sb.append("NativeHeapSize: ");
            sb.append(Debug.getNativeHeapSize());
            sb.append(10);
            sb.append("NativeHeapAllocatedSize: ");
            sb.append(Debug.getNativeHeapAllocatedSize());
            sb.append(10);
            sb.append("NativeHeapFreeSize: ");
            sb.append(Debug.getNativeHeapFreeSize());
            sb.append(10);
        } catch (Throwable unused4) {
        }
        return sb.toString();
    }

    private static void a(StringBuilder sb, MemoryInfo memoryInfo) {
        sb.append("\tDalvikPSS: ");
        sb.append(memoryInfo.dalvikPss);
        sb.append(10);
        sb.append("\tDalvikPrivateDirty: ");
        sb.append(memoryInfo.dalvikPrivateDirty);
        sb.append(10);
        sb.append("\tDalvikShareDirty: ");
        sb.append(memoryInfo.dalvikSharedDirty);
        sb.append(10);
        sb.append("\tNativePSS: ");
        sb.append(memoryInfo.nativePss);
        sb.append(10);
        sb.append("\tNativePrivateDirty: ");
        sb.append(memoryInfo.nativePrivateDirty);
        sb.append(10);
        sb.append("\tNativeShareDirty: ");
        sb.append(memoryInfo.nativeSharedDirty);
        sb.append(10);
        sb.append("\tOtherPSS: ");
        sb.append(memoryInfo.otherPss);
        sb.append(10);
        sb.append("\tOtherShareDirty: ");
        sb.append(memoryInfo.otherSharedDirty);
        sb.append(10);
        sb.append("\tOtherPrivateDirty: ");
        sb.append(memoryInfo.otherPrivateDirty);
        sb.append(10);
        sb.append("\tTotalPSS: ");
        sb.append(memoryInfo.getTotalPss());
        sb.append(10);
        sb.append("\tTotalPrivateDirty: ");
        sb.append(memoryInfo.getTotalPrivateDirty());
        sb.append(10);
        sb.append("\tTotalSharedDirty: ");
        sb.append(memoryInfo.getTotalSharedDirty());
        sb.append(10);
        if (VERSION.SDK_INT >= 19) {
            sb.append("\tTotalPrivateClean: ");
            sb.append(memoryInfo.getTotalPrivateClean());
            sb.append(10);
            sb.append("\tTotalSharedClean: ");
            sb.append(memoryInfo.getTotalSharedClean());
            sb.append(10);
            sb.append("\tTotalSwappablePss: ");
            sb.append(memoryInfo.getTotalSwappablePss());
            sb.append(10);
        }
        sb.append(10);
        sb.append("Memory Summary (API 23+): \n");
        if (VERSION.SDK_INT >= 23) {
            for (Entry next : memoryInfo.getMemoryStats().entrySet()) {
                sb.append("\t\t");
                sb.append((String) next.getKey());
                sb.append(": ");
                sb.append((String) next.getValue());
                sb.append(10);
            }
        }
    }

    private static String a() {
        StringBuilder sb = new StringBuilder();
        String str = SystemProperties.get("ro.product.model", "unknown");
        String str2 = SystemProperties.get("ro.product.brand", "unknown");
        sb.append("product_model: ");
        sb.append(str);
        sb.append("\n");
        sb.append("product_brand: ");
        sb.append(str2);
        sb.append("\n");
        sb.append("emulator: ");
        sb.append(bmx.a(CrashLogUtil.getApplication()));
        sb.append("\n");
        sb.append("BootTime: ");
        sb.append(CrashLogUtil.SIMPLE_DATE_FORMAT.format(Long.valueOf(System.currentTimeMillis() - SystemClock.elapsedRealtime())));
        sb.append(10);
        return sb.toString();
    }
}
