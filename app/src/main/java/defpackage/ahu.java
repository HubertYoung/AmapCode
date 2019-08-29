package defpackage;

import android.content.Context;

/* renamed from: ahu reason: default package */
/* compiled from: SystemPropertiesProxy */
public final class ahu {
    public static String a(Context context, String str, String str2) throws IllegalArgumentException {
        try {
            Class<?> loadClass = context.getClassLoader().loadClass("android.os.SystemProperties");
            return (String) loadClass.getMethod("get", new Class[]{String.class, String.class}).invoke(loadClass, new Object[]{str, str2});
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception unused) {
            return str2;
        }
    }
}
