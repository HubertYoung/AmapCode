package com.squareup.leakcanary.internal;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ServiceInfo;
import android.os.Environment;
import android.os.Process;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import java.io.File;
import java.util.Iterator;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public final class LeakCanaryInternals {
    public static final String LG = "LGE";
    public static final int LOLLIPOP_MR1 = 22;
    public static final String MOTOROLA = "motorola";
    public static final String NVIDIA = "NVIDIA";
    public static final String SAMSUNG = "samsung";
    private static final Executor fileIoExecutor = Executors.newSingleThreadExecutor();

    public static void executeOnFileIoThread(Runnable runnable) {
        fileIoExecutor.execute(runnable);
    }

    public static File storageDirectory() {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "leakcanary");
        file.mkdirs();
        return file;
    }

    public static File detectedLeakDirectory() {
        File file = new File(storageDirectory(), "detected_leaks");
        file.mkdirs();
        return file;
    }

    public static File leakResultFile(File file) {
        File parentFile = file.getParentFile();
        StringBuilder sb = new StringBuilder();
        sb.append(file.getName());
        sb.append(".result");
        return new File(parentFile, sb.toString());
    }

    public static boolean isExternalStorageWritable() {
        return "mounted".equals(Environment.getExternalStorageState());
    }

    public static File findNextAvailableHprofFile(int i) {
        File detectedLeakDirectory = detectedLeakDirectory();
        for (int i2 = 0; i2 < i; i2++) {
            StringBuilder sb = new StringBuilder("heap_dump_");
            sb.append(i2);
            sb.append(".hprof");
            File file = new File(detectedLeakDirectory, sb.toString());
            if (!file.exists()) {
                return file;
            }
        }
        return null;
    }

    public static String classSimpleName(String str) {
        int lastIndexOf = str.lastIndexOf(46);
        if (lastIndexOf == -1) {
            return str;
        }
        return str.substring(lastIndexOf + 1);
    }

    public static void setEnabled(Context context, final Class<?> cls, final boolean z) {
        final Context applicationContext = context.getApplicationContext();
        executeOnFileIoThread(new Runnable() {
            public final void run() {
                applicationContext.getPackageManager().setComponentEnabledSetting(new ComponentName(applicationContext, cls), z ? 1 : 2, 1);
            }
        });
    }

    public static boolean isInServiceProcess(Context context, Class<? extends Service> cls) {
        PackageManager packageManager = context.getPackageManager();
        try {
            String str = packageManager.getPackageInfo(context.getPackageName(), 4).applicationInfo.processName;
            try {
                ServiceInfo serviceInfo = packageManager.getServiceInfo(new ComponentName(context, cls), 0);
                if (serviceInfo.processName.equals(str)) {
                    StringBuilder sb = new StringBuilder("Did not expect service ");
                    sb.append(cls);
                    sb.append(" to run in main process ");
                    sb.append(str);
                    return false;
                }
                int myPid = Process.myPid();
                RunningAppProcessInfo runningAppProcessInfo = null;
                Iterator<RunningAppProcessInfo> it = ((ActivityManager) context.getSystemService(WidgetType.ACTIVITY)).getRunningAppProcesses().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    RunningAppProcessInfo next = it.next();
                    if (next.pid == myPid) {
                        runningAppProcessInfo = next;
                        break;
                    }
                }
                if (runningAppProcessInfo == null) {
                    return false;
                }
                return runningAppProcessInfo.processName.equals(serviceInfo.processName);
            } catch (NameNotFoundException unused) {
                return false;
            }
        } catch (Exception unused2) {
            new StringBuilder("Could not get package info for ").append(context.getPackageName());
            return false;
        }
    }

    private LeakCanaryInternals() {
        throw new AssertionError();
    }
}
