package defpackage;

import android.text.TextUtils;

/* renamed from: dhw reason: default package */
/* compiled from: CalcRouteMethod */
public final class dhw {
    private static int a;

    public static boolean a(String str) {
        if (str == null || str.length() <= 0) {
            return false;
        }
        if (str.contains("0") || str.contains("1") || str.contains("2") || str.contains("4") || str.contains("8") || str.contains("16")) {
            return true;
        }
        return false;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0016, code lost:
        r0 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x001d, code lost:
        r0 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0010, code lost:
        r1 = false;
     */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0033  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x003d  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x004a  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0057  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0065  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0068  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a(int r5) {
        /*
            r0 = 0
            r1 = 1
            if (r5 == r1) goto L_0x0028
            r2 = 20
            if (r5 == r2) goto L_0x0024
            r2 = 24
            if (r5 == r2) goto L_0x0021
            switch(r5) {
                case 3: goto L_0x001f;
                case 4: goto L_0x001c;
                case 5: goto L_0x001a;
                case 6: goto L_0x0018;
                case 7: goto L_0x0015;
                case 8: goto L_0x0013;
                default: goto L_0x000f;
            }
        L_0x000f:
            r5 = 0
        L_0x0010:
            r1 = 0
        L_0x0011:
            r2 = 0
            goto L_0x002a
        L_0x0013:
            r5 = 1
            goto L_0x0016
        L_0x0015:
            r5 = 0
        L_0x0016:
            r0 = 1
            goto L_0x0011
        L_0x0018:
            r5 = 1
            goto L_0x001d
        L_0x001a:
            r5 = 1
            goto L_0x0011
        L_0x001c:
            r5 = 0
        L_0x001d:
            r0 = 1
            goto L_0x0010
        L_0x001f:
            r5 = 1
            goto L_0x0010
        L_0x0021:
            r5 = 0
            r0 = 1
            goto L_0x0025
        L_0x0024:
            r5 = 0
        L_0x0025:
            r1 = 0
            r2 = 1
            goto L_0x002a
        L_0x0028:
            r5 = 0
            goto L_0x0011
        L_0x002a:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = ""
            if (r0 == 0) goto L_0x003b
            java.lang.String r0 = "2"
            r3.append(r0)
            java.lang.String r4 = "|"
        L_0x003b:
            if (r1 == 0) goto L_0x0048
            r3.append(r4)
            java.lang.String r0 = "4"
            r3.append(r0)
            java.lang.String r4 = "|"
        L_0x0048:
            if (r5 == 0) goto L_0x0055
            r3.append(r4)
            java.lang.String r5 = "8"
            r3.append(r5)
            java.lang.String r4 = "|"
        L_0x0055:
            if (r2 == 0) goto L_0x005f
            r3.append(r4)
            java.lang.String r5 = "16"
            r3.append(r5)
        L_0x005f:
            int r5 = r3.length()
            if (r5 != 0) goto L_0x0068
            java.lang.String r5 = "1"
            return r5
        L_0x0068:
            java.lang.String r5 = r3.toString()
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dhw.a(int):java.lang.String");
    }

    public static int b(String str) {
        int i = a;
        boolean contains = str.contains("2");
        boolean contains2 = str.contains("4");
        boolean contains3 = str.contains("8");
        boolean contains4 = str.contains("16");
        if (contains3 && contains && contains2) {
            return 8;
        }
        if (contains && contains2) {
            return 7;
        }
        if (contains && contains3) {
            return 6;
        }
        if (contains2 && contains3) {
            return 5;
        }
        if (contains4 && contains) {
            return 24;
        }
        if (contains4) {
            return 20;
        }
        if (contains3) {
            return 3;
        }
        if (contains2) {
            return 1;
        }
        if (contains) {
            return 4;
        }
        return i;
    }

    public static int c(String str) {
        int i = 0;
        if (str == null || str.length() == 0) {
            return 0;
        }
        if (str.contains("8")) {
            i = 1;
        } else if (str.contains("16")) {
            i = 64;
        }
        return i;
    }

    public static int d(String str) {
        if (!TextUtils.isEmpty(str)) {
            boolean contains = str.contains("2");
            boolean contains2 = str.contains("4");
            if (contains2 && contains) {
                return 12;
            }
            if (contains) {
                return 4;
            }
            if (contains2) {
                return 1;
            }
        }
        return 0;
    }
}
