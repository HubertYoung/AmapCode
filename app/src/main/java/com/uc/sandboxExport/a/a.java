package com.uc.sandboxExport.a;

import android.os.Process;
import java.lang.reflect.Method;

/* compiled from: ProGuard */
public final class a {
    private static boolean a = false;
    private static boolean b = false;
    private static final Object c = new Object();

    public static boolean a() {
        if (!b) {
            synchronized (c) {
                try {
                    if (!b) {
                        a = b();
                        b = true;
                    }
                }
            }
        }
        return a;
    }

    private static boolean b() {
        try {
            Method declaredMethod = Process.class.getDeclaredMethod("isIsolated", new Class[0]);
            if (declaredMethod != null) {
                declaredMethod.setAccessible(true);
                Object invoke = declaredMethod.invoke(null, new Object[0]);
                if (invoke != null && (invoke instanceof Boolean)) {
                    return ((Boolean) invoke).booleanValue();
                }
            }
        } catch (Throwable th) {
        }
        int myUid = Process.myUid() % 100000;
        return myUid >= 99000 && myUid <= 99999;
    }
}
