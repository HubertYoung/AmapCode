package defpackage;

import android.text.TextUtils;
import java.util.ArrayList;

/* renamed from: cva reason: default package */
/* compiled from: AppConfig */
public final class cva {
    public static String a = null;
    public static String b = null;
    public static String c = null;
    public static String d = null;
    public static Boolean e = Boolean.FALSE;
    public static String f = null;
    public static String g = null;
    public static String h = null;
    public static String i = null;
    public static String j = null;
    public static String k = "";
    public static ArrayList<String> l = new ArrayList<>();

    public static void a(cvb cvb) {
        if (TextUtils.isEmpty(cvb.a)) {
            throw new RuntimeException("AppParam Error : appKey is necessary!");
        }
        a = cvb.a;
        b = cvb.b;
        c = cvb.c;
        e = cvb.e;
        d = cvb.d;
        f = cvb.g;
        g = cvb.h;
        h = cvb.i;
        i = cvb.j;
        j = cvb.k;
        k = cvb.f;
    }
}
