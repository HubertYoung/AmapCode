package defpackage;

import java.util.Collections;
import java.util.Map;

/* renamed from: zc reason: default package */
/* compiled from: CdnCloudConfig */
public final class zc {
    private static zc a;

    public static synchronized zc a() {
        zc zcVar;
        synchronized (zc.class) {
            if (a == null) {
                a = new zc();
            }
            zcVar = a;
        }
        return zcVar;
    }

    private zc() {
    }

    public static Map<String, String> b() {
        e g = aaf.g();
        return g == null ? Collections.EMPTY_MAP : g.b();
    }

    public static boolean c() {
        e g = aaf.g();
        if (g == null) {
            return false;
        }
        return g.a();
    }
}
