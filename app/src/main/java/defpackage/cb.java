package defpackage;

import android.text.TextUtils;

/* renamed from: cb reason: default package */
/* compiled from: DispatchConstants */
public final class cb {
    public static String[] a = new String[0];
    public static String[] b = {"amdc.m.taobao.com", "amdc.wapa.taobao.com", "amdc.taobao.net"};
    public static String[][] c = {new String[]{ci.a(203119206064L), ci.a(203119211219L)}, new String[]{ci.a(106011052006L)}, null};

    public static String a() {
        return b[m.d().getEnvMode()];
    }

    public static boolean a(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return str.equalsIgnoreCase(a());
    }

    public static String[] b() {
        return c[m.d().getEnvMode()];
    }
}
