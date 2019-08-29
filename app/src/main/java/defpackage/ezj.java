package defpackage;

import android.content.Context;
import android.text.TextUtils;

/* renamed from: ezj reason: default package */
/* compiled from: ClientConfigManagerImpl */
public final class ezj implements ezn {
    private static final Object b = new Object();
    private static volatile ezj c;
    public ezk a = new ezk(this.d);
    private Context d;
    private ezo e = new ezo(this.d);

    private ezj(Context context) {
        this.d = context.getApplicationContext();
    }

    public static ezj a(Context context) {
        if (c == null) {
            synchronized (b) {
                try {
                    if (c == null) {
                        c = new ezj(context);
                    }
                }
            }
        }
        return c;
    }

    public final boolean a() {
        f();
        ezs c2 = this.a.c(this.d.getPackageName());
        if (c2 != null) {
            return "1".equals(c2.b);
        }
        return true;
    }

    private void f() {
        if (this.a == null) {
            this.a = new ezk(this.d);
        } else {
            this.a.c();
        }
    }

    public final boolean a(long j) {
        String[] split;
        String c2 = g().c("BL");
        if (!TextUtils.isEmpty(c2)) {
            for (String str : c2.split(",")) {
                try {
                    if (!TextUtils.isEmpty(str) && Long.parseLong(str) == j) {
                        return true;
                    }
                } catch (NumberFormatException e2) {
                    e2.printStackTrace();
                }
            }
        }
        return false;
    }

    public final int b() {
        try {
            String c2 = g().c("DPL");
            if (!TextUtils.isEmpty(c2)) {
                try {
                    return Integer.parseInt(c2);
                } catch (NumberFormatException e2) {
                    e2.printStackTrace();
                }
            }
        } catch (NumberFormatException e3) {
            e3.printStackTrace();
        }
        return 0;
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0021 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x001f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean c() {
        /*
            r3 = this;
            ezo r0 = r3.g()
            java.lang.String r1 = "PSM"
            java.lang.String r0 = r0.c(r1)
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            r2 = 0
            if (r1 != 0) goto L_0x001a
            int r0 = java.lang.Integer.parseInt(r0)     // Catch:{ NumberFormatException -> 0x0016 }
            goto L_0x001b
        L_0x0016:
            r0 = move-exception
            r0.printStackTrace()
        L_0x001a:
            r0 = 0
        L_0x001b:
            r0 = r0 & 4
            if (r0 == 0) goto L_0x0021
            r0 = 1
            return r0
        L_0x0021:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ezj.c():boolean");
    }

    private ezo g() {
        if (this.e == null) {
            this.e = new ezo(this.d);
        } else {
            this.e.c();
        }
        return this.e;
    }

    public final String d() {
        return g().c("CSPT");
    }

    public final boolean e() {
        this.a.c();
        return ezk.a(this.a.b());
    }

    public static boolean a(int i) {
        return ezk.a(i);
    }
}
