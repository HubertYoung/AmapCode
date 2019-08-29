package defpackage;

import android.graphics.Color;

/* renamed from: ebn reason: default package */
/* compiled from: RouteViewUtil */
public final class ebn {
    public static int a(double d, int i) {
        if (d >= 0.0d && d <= 100.0d) {
            return Color.argb((int) ((d * 255.0d) / 100.0d), Color.red(i), Color.green(i), Color.blue(i));
        }
        throw new IllegalArgumentException("illegal alphaPercent [0 - 100]: ".concat(String.valueOf(d)));
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(8:4|5|6|7|8|9|10|11) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0028 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void a(android.view.View r5) {
        /*
            boolean r0 = a()
            if (r0 != 0) goto L_0x0007
            return
        L_0x0007:
            r0 = 1
            java.lang.Class[] r1 = new java.lang.Class[r0]     // Catch:{ Exception -> 0x002c }
            java.lang.Class r2 = java.lang.Integer.TYPE     // Catch:{ Exception -> 0x002c }
            r3 = 0
            r1[r3] = r2     // Catch:{ Exception -> 0x002c }
            java.lang.Class<android.view.View> r2 = android.view.View.class
            java.lang.String r4 = "setSystemUiVisibility"
            java.lang.reflect.Method r1 = r2.getMethod(r4, r1)     // Catch:{ Exception -> 0x002c }
            java.lang.Class<android.view.View> r2 = android.view.View.class
            java.lang.String r4 = "SYSTEM_UI_FLAG_HIDE_NAVIGATION"
            java.lang.reflect.Field r2 = r2.getField(r4)     // Catch:{ Exception -> 0x002c }
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ Exception -> 0x002c }
            r4 = 0
            java.lang.Object r2 = r2.get(r4)     // Catch:{ Exception -> 0x0028 }
            r0[r3] = r2     // Catch:{ Exception -> 0x0028 }
        L_0x0028:
            r1.invoke(r5, r0)     // Catch:{ Exception -> 0x002c }
            return
        L_0x002c:
            r5 = move-exception
            r5.printStackTrace()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ebn.a(android.view.View):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x0030  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0032  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean a() {
        /*
            r0 = 0
            java.lang.String r1 = "android.os.Build"
            java.lang.Class r1 = java.lang.Class.forName(r1)     // Catch:{ RuntimeException -> 0x0022, Exception -> 0x001d }
            java.lang.String r2 = "hasSmartBar"
            java.lang.Class[] r3 = new java.lang.Class[r0]     // Catch:{ RuntimeException -> 0x0022, Exception -> 0x001d }
            java.lang.reflect.Method r1 = r1.getMethod(r2, r3)     // Catch:{ RuntimeException -> 0x0022, Exception -> 0x001d }
            r2 = 0
            java.lang.Object[] r3 = new java.lang.Object[r0]     // Catch:{ RuntimeException -> 0x0022, Exception -> 0x001d }
            java.lang.Object r1 = r1.invoke(r2, r3)     // Catch:{ RuntimeException -> 0x0022, Exception -> 0x001d }
            java.lang.Boolean r1 = (java.lang.Boolean) r1     // Catch:{ RuntimeException -> 0x0022, Exception -> 0x001d }
            boolean r1 = r1.booleanValue()     // Catch:{ RuntimeException -> 0x0022, Exception -> 0x001d }
            return r1
        L_0x001d:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x0026
        L_0x0022:
            r1 = move-exception
            r1.printStackTrace()
        L_0x0026:
            java.lang.String r1 = android.os.Build.DEVICE
            java.lang.String r2 = "mx2"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0032
            r0 = 1
            return r0
        L_0x0032:
            java.lang.String r1 = android.os.Build.DEVICE
            java.lang.String r2 = "mx"
            boolean r1 = r1.equals(r2)
            if (r1 != 0) goto L_0x0048
            java.lang.String r1 = android.os.Build.DEVICE
            java.lang.String r2 = "m9"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0047
            goto L_0x0048
        L_0x0047:
            return r0
        L_0x0048:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ebn.a():boolean");
    }
}
