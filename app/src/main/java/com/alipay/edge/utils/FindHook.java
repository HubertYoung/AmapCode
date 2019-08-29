package com.alipay.edge.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import com.alipay.android.phone.inside.log.api.LoggerFactory;

public class FindHook {
    private static boolean a() {
        try {
            throw new Exception("findhook");
        } catch (Exception e) {
            StackTraceElement[] stackTrace = e.getStackTrace();
            int length = stackTrace.length;
            int i = 0;
            while (i < length) {
                StackTraceElement stackTraceElement = stackTrace[i];
                if (stackTraceElement.getClassName().equals("com.saurik.substrate.MS$2") && stackTraceElement.getMethodName().equals("invoked")) {
                    LoggerFactory.f().b((String) "t0dbg", (String) "findHook com.saurik.substrate.MS$2");
                    return true;
                } else if (stackTraceElement.getClassName().equals("de.robv.android.xposed.XposedBridge") && stackTraceElement.getMethodName().equals("main")) {
                    LoggerFactory.f().b((String) "t0dbg", (String) "findHook de.robv.android.xposed.XposedBridge");
                    return true;
                } else if (!stackTraceElement.getClassName().equals("de.robv.android.xposed.XposedBridge") || !stackTraceElement.getMethodName().equals("handleHookedMethod")) {
                    i++;
                } else {
                    LoggerFactory.f().b((String) "t0dbg", (String) "findHook de.robv.android.xposed.XposedBridge");
                    return true;
                }
            }
            return false;
        }
    }

    public static boolean a(Context context) {
        boolean z;
        for (ApplicationInfo next : context.getPackageManager().getInstalledApplications(128)) {
            if (next.packageName.equals("com.saurik.substrate")) {
                LoggerFactory.f().b((String) "t0dbg", (String) "findHook com.saurik.substrate");
            } else if (next.packageName.equals("de.robv.android.xposed.installer")) {
                LoggerFactory.f().b((String) "t0dbg", (String) "findHook de.robv.android.xposed.installer");
            }
            z = true;
        }
        z = false;
        return z || a();
    }
}
