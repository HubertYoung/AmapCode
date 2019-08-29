package com.amap.location.icecream;

import android.content.Context;
import android.text.TextUtils;
import com.amap.location.icecream.a.c;
import java.lang.Thread.UncaughtExceptionHandler;

/* compiled from: IcecreamCrashController */
public class d implements UncaughtExceptionHandler {
    /* access modifiers changed from: private */
    public static UncaughtExceptionHandler a;
    /* access modifiers changed from: private */
    public static Context b;

    public void uncaughtException(final Thread thread, final Throwable th) {
        new Thread("IcecreamCrashTask") {
            public void run() {
                StackTraceElement[] stackTrace;
                try {
                    if (th == null) {
                        UncaughtExceptionHandler b2 = d.a;
                        if (b2 != null) {
                            b2.uncaughtException(thread, th);
                        }
                        return;
                    }
                    for (StackTraceElement stackTraceElement : th.getStackTrace()) {
                        if (!(stackTraceElement == null || stackTraceElement.getClassName() == null)) {
                            String className = stackTraceElement.getClassName();
                            if (className.startsWith("com.amap.icecream")) {
                                String[] split = className.split("\\.");
                                if (split.length >= 4) {
                                    String a2 = f.a(split[3]);
                                    if (!TextUtils.isEmpty(a2)) {
                                        c.a(d.b, split[3], a2);
                                    }
                                    UncaughtExceptionHandler b3 = d.a;
                                    if (b3 != null) {
                                        b3.uncaughtException(thread, th);
                                    }
                                    return;
                                }
                            } else {
                                continue;
                            }
                        }
                    }
                    UncaughtExceptionHandler b4 = d.a;
                    if (b4 != null) {
                        b4.uncaughtException(thread, th);
                    }
                } catch (Throwable th) {
                    UncaughtExceptionHandler b5 = d.a;
                    if (b5 != null) {
                        b5.uncaughtException(thread, th);
                    }
                    throw th;
                }
            }
        }.start();
    }

    public static void a(Context context) {
        if (context != null) {
            b = context.getApplicationContext();
        }
        UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        if (defaultUncaughtExceptionHandler != null && !(defaultUncaughtExceptionHandler instanceof d)) {
            a = defaultUncaughtExceptionHandler;
            Thread.setDefaultUncaughtExceptionHandler(new d());
        }
    }
}
