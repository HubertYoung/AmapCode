package defpackage;

import android.text.TextUtils;

/* renamed from: bgs reason: default package */
/* compiled from: VersionChecker */
public final class bgs {
    public static boolean a;
    private static boolean b;

    public bgs() {
        a();
    }

    public static void a() {
        if (!b) {
            b = true;
            String a2 = bgt.a((String) "vui_version_name", (String) "");
            int a3 = bgt.a((String) "vui_version_code", 0);
            String str = a.a().a;
            int i = a.a().b;
            if (TextUtils.isEmpty(a2) || a3 == 0) {
                a = true;
            } else if (a(a2, str) || a3 != i) {
                a = true;
            }
            if (a) {
                bgt.b((String) "vui_version_name", str);
                bgt.b((String) "vui_version_code", i);
            }
        }
    }

    private static boolean a(String str, String str2) {
        int lastIndexOf = str.lastIndexOf(".");
        return lastIndexOf < 0 || !str.substring(0, lastIndexOf).equals(str2.substring(0, lastIndexOf));
    }
}
