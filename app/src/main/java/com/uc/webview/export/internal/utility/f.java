package com.uc.webview.export.internal.utility;

import android.os.Bundle;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* compiled from: ProGuard */
public final class f {
    public static void a(StringBuffer stringBuffer, String str) {
        Object obj;
        try {
            Class<?> cls = Class.forName("com.uc.crashsdk.export.CrashApi");
            try {
                Method declaredMethod = cls.getDeclaredMethod("getInstance", new Class[0]);
                Method method = cls.getMethod("generateCustomLog", new Class[]{StringBuffer.class, String.class, Bundle.class});
                try {
                    obj = declaredMethod.invoke(null, null);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException unused) {
                    obj = null;
                }
                if (obj != null) {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("mAddHeader", true);
                    bundle.putBoolean("mAddFooter", true);
                    bundle.putBoolean("mAddLogcat", true);
                    bundle.putBoolean("mUploadNow", true);
                    bundle.putBoolean("mAddThreadsDump", false);
                    try {
                        method.invoke(obj, new Object[]{stringBuffer, str, bundle});
                    } catch (IllegalAccessException unused2) {
                    } catch (IllegalArgumentException unused3) {
                    } catch (InvocationTargetException unused4) {
                    }
                }
            } catch (Exception unused5) {
            }
        } catch (Exception unused6) {
        }
    }
}
