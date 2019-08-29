package defpackage;

import android.content.Context;

/* renamed from: faf reason: default package */
/* compiled from: NotifyUtil */
public final class faf {
    private static fac a = null;
    private static fad b = null;
    private static String c = "com.vivo.push.util.NotifyDataAdapter";
    private static String d = "com.vivo.push.util.NotifyLayoutAdapter";

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0011  */
    /* JADX WARNING: Removed duplicated region for block: B:13:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.Object a(java.lang.String r1, java.lang.Object r2) {
        /*
            r0 = 0
            java.lang.Class r1 = java.lang.Class.forName(r1)     // Catch:{ Exception -> 0x0006 }
            goto L_0x0007
        L_0x0006:
            r1 = r0
        L_0x0007:
            if (r1 == 0) goto L_0x000e
            java.lang.Object r1 = r1.newInstance()     // Catch:{ Exception -> 0x000e }
            goto L_0x000f
        L_0x000e:
            r1 = r0
        L_0x000f:
            if (r1 != 0) goto L_0x0012
            r1 = r2
        L_0x0012:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.faf.a(java.lang.String, java.lang.Object):java.lang.Object");
    }

    private static synchronized void c(Context context) {
        synchronized (faf.class) {
            if (a == null) {
                fac fac = (fac) a(c, new fam());
                a = fac;
                fac.a(context);
            }
            if (b == null) {
                fad fad = (fad) a(d, new fan());
                b = fad;
                fad.a(context);
            }
        }
    }

    public static fac a(Context context) {
        c(context);
        return a;
    }

    public static fad b(Context context) {
        c(context);
        return b;
    }
}
