package defpackage;

import android.os.Environment;
import java.text.SimpleDateFormat;

/* renamed from: cjo reason: default package */
/* compiled from: AGroupDebugLog */
public final class cjo {
    private static int a = 1000;
    private static final Object b = new Object();
    private static final String c;
    private static final SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
    private static long e = 0;
    private static String f = "";

    public static void a() {
    }

    public static void b() {
    }

    public static void c() {
    }

    public static void d() {
    }

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory());
        sb.append("/autonavi/agrouplog/");
        c = sb.toString();
    }
}
