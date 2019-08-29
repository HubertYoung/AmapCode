package defpackage;

import android.text.TextUtils;

/* renamed from: eul reason: default package */
/* compiled from: RomUtil */
public final class eul {
    public static boolean a() {
        return !TextUtils.isEmpty(a("ro.build.version.emui", ""));
    }

    private static String a(String str, String str2) {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            return (String) cls.getMethod("get", new Class[]{String.class, String.class}).invoke(cls, new Object[]{str, str2});
        } catch (Exception e) {
            e.printStackTrace();
            return str2;
        }
    }
}
