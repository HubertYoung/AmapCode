package com.uc.webview.export.internal.utility;

import com.uc.webview.export.annotations.Interface;
import com.uc.webview.export.cyclone.UCLogger;

@Interface
/* compiled from: ProGuard */
public class Log {
    public static boolean sPrintLog = false;

    private Log() {
    }

    public static void d(String str, String str2) {
        UCLogger create = UCLogger.create("d", str);
        if (create != null) {
            create.print(str2, new Throwable[0]);
        }
    }

    public static void d(String str, String str2, Throwable th) {
        UCLogger create = UCLogger.create("d", str);
        if (create != null) {
            create.print(str2, th);
        }
    }

    public static void e(String str, String str2) {
        UCLogger create = UCLogger.create("e", str);
        if (create != null) {
            create.print(str2, new Throwable[0]);
        }
    }

    public static void e(String str, String str2, Throwable th) {
        UCLogger create = UCLogger.create("e", str);
        if (create != null) {
            create.print(str2, th);
        }
    }

    public static void i(String str, String str2) {
        UCLogger create = UCLogger.create("i", str);
        if (create != null) {
            create.print(str2, new Throwable[0]);
        }
    }

    public static void i(String str, String str2, Throwable th) {
        UCLogger create = UCLogger.create("i", str);
        if (create != null) {
            create.print(str2, th);
        }
    }

    public static void w(String str, String str2) {
        UCLogger create = UCLogger.create("w", str);
        if (create != null) {
            create.print(str2, new Throwable[0]);
        }
    }

    public static void w(String str, String str2, Throwable th) {
        UCLogger create = UCLogger.create("w", str);
        if (create != null) {
            create.print(str2, th);
        }
    }
}
