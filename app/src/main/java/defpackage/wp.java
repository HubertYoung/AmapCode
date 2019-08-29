package defpackage;

import java.util.ArrayList;
import java.util.List;

/* renamed from: wp reason: default package */
/* compiled from: GroupID */
public final class wp {
    public static final long a;
    private static final long b;
    private static final long c = (b + 10002);
    private static final long d = (b + 10003);
    private static final long e = (b + 10004);
    private static final long f = (b + 10005);
    private static final long g = (b + 10006);
    private static List<Long> h = new ArrayList();

    static {
        long pow = (long) Math.pow(2.0d, 31.0d);
        b = pow;
        a = pow + 10001;
        b();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x003e, code lost:
        if (r6.equals("paas") != false) goto L_0x006a;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static long a(java.lang.String r5, boolean r6) {
        /*
            boolean r6 = b(r5, r6)
            r0 = -1
            if (r6 != 0) goto L_0x001a
            java.lang.String r6 = "paas.logs"
            java.lang.String r2 = "GroupID"
            java.lang.String r3 = "error groupName:"
            java.lang.String r5 = java.lang.String.valueOf(r5)
            java.lang.String r5 = r3.concat(r5)
            com.amap.bundle.logs.AMapLog.warning(r6, r2, r5)
            return r0
        L_0x001a:
            java.lang.String r6 = "."
            int r6 = r5.indexOf(r6)
            r2 = 0
            java.lang.String r6 = r5.substring(r2, r6)
            r3 = -1
            int r4 = r6.hashCode()
            switch(r4) {
                case -1652137401: goto L_0x005f;
                case -1581073276: goto L_0x0055;
                case -332603157: goto L_0x004b;
                case 2995364: goto L_0x0041;
                case 3432931: goto L_0x0038;
                case 108704329: goto L_0x002e;
                default: goto L_0x002d;
            }
        L_0x002d:
            goto L_0x0069
        L_0x002e:
            java.lang.String r2 = "route"
            boolean r6 = r6.equals(r2)
            if (r6 == 0) goto L_0x0069
            r2 = 1
            goto L_0x006a
        L_0x0038:
            java.lang.String r4 = "paas"
            boolean r6 = r6.equals(r4)
            if (r6 == 0) goto L_0x0069
            goto L_0x006a
        L_0x0041:
            java.lang.String r2 = "ajx3"
            boolean r6 = r6.equals(r2)
            if (r6 == 0) goto L_0x0069
            r2 = 5
            goto L_0x006a
        L_0x004b:
            java.lang.String r2 = "basemap"
            boolean r6 = r6.equals(r2)
            if (r6 == 0) goto L_0x0069
            r2 = 3
            goto L_0x006a
        L_0x0055:
            java.lang.String r2 = "sharetrip"
            boolean r6 = r6.equals(r2)
            if (r6 == 0) goto L_0x0069
            r2 = 2
            goto L_0x006a
        L_0x005f:
            java.lang.String r2 = "infoservice"
            boolean r6 = r6.equals(r2)
            if (r6 == 0) goto L_0x0069
            r2 = 4
            goto L_0x006a
        L_0x0069:
            r2 = -1
        L_0x006a:
            switch(r2) {
                case 0: goto L_0x008e;
                case 1: goto L_0x008b;
                case 2: goto L_0x0088;
                case 3: goto L_0x0085;
                case 4: goto L_0x0082;
                case 5: goto L_0x007f;
                default: goto L_0x006d;
            }
        L_0x006d:
            java.lang.String r6 = "paas.logs"
            java.lang.String r2 = "GroupID"
            java.lang.String r3 = "error groupName:"
            java.lang.String r5 = java.lang.String.valueOf(r5)
            java.lang.String r5 = r3.concat(r5)
            com.amap.bundle.logs.AMapLog.warning(r6, r2, r5)
            goto L_0x0090
        L_0x007f:
            long r0 = g
            goto L_0x0090
        L_0x0082:
            long r0 = f
            goto L_0x0090
        L_0x0085:
            long r0 = e
            goto L_0x0090
        L_0x0088:
            long r0 = d
            goto L_0x0090
        L_0x008b:
            long r0 = c
            goto L_0x0090
        L_0x008e:
            long r0 = a
        L_0x0090:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.wp.a(java.lang.String, boolean):long");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:29:0x005b, code lost:
        r3 = 65535;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x005c, code lost:
        switch(r3) {
            case 0: goto L_0x0060;
            case 1: goto L_0x0060;
            case 2: goto L_0x0060;
            case 3: goto L_0x0060;
            case 4: goto L_0x0060;
            case 5: goto L_0x0060;
            default: goto L_0x005f;
        };
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0060, code lost:
        r1 = true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean b(java.lang.String r3, boolean r4) {
        /*
            r0 = 1
            if (r4 != 0) goto L_0x0004
            return r0
        L_0x0004:
            boolean r4 = android.text.TextUtils.isEmpty(r3)
            r1 = 0
            if (r4 == 0) goto L_0x000c
            return r1
        L_0x000c:
            java.lang.String r4 = "."
            int r4 = r3.indexOf(r4)     // Catch:{ IndexOutOfBoundsException -> 0x0061 }
            java.lang.String r3 = r3.substring(r1, r4)     // Catch:{ IndexOutOfBoundsException -> 0x0061 }
            r4 = -1
            int r2 = r3.hashCode()     // Catch:{ IndexOutOfBoundsException -> 0x0061 }
            switch(r2) {
                case -1652137401: goto L_0x0051;
                case -1581073276: goto L_0x0047;
                case -332603157: goto L_0x003d;
                case 2995364: goto L_0x0033;
                case 3432931: goto L_0x0029;
                case 108704329: goto L_0x001f;
                default: goto L_0x001e;
            }     // Catch:{ IndexOutOfBoundsException -> 0x0061 }
        L_0x001e:
            goto L_0x005b
        L_0x001f:
            java.lang.String r2 = "route"
            boolean r3 = r3.equals(r2)     // Catch:{ IndexOutOfBoundsException -> 0x0061 }
            if (r3 == 0) goto L_0x005b
            r3 = 1
            goto L_0x005c
        L_0x0029:
            java.lang.String r2 = "paas"
            boolean r3 = r3.equals(r2)     // Catch:{ IndexOutOfBoundsException -> 0x0061 }
            if (r3 == 0) goto L_0x005b
            r3 = 0
            goto L_0x005c
        L_0x0033:
            java.lang.String r2 = "ajx3"
            boolean r3 = r3.equals(r2)     // Catch:{ IndexOutOfBoundsException -> 0x0061 }
            if (r3 == 0) goto L_0x005b
            r3 = 5
            goto L_0x005c
        L_0x003d:
            java.lang.String r2 = "basemap"
            boolean r3 = r3.equals(r2)     // Catch:{ IndexOutOfBoundsException -> 0x0061 }
            if (r3 == 0) goto L_0x005b
            r3 = 3
            goto L_0x005c
        L_0x0047:
            java.lang.String r2 = "sharetrip"
            boolean r3 = r3.equals(r2)     // Catch:{ IndexOutOfBoundsException -> 0x0061 }
            if (r3 == 0) goto L_0x005b
            r3 = 2
            goto L_0x005c
        L_0x0051:
            java.lang.String r2 = "infoservice"
            boolean r3 = r3.equals(r2)     // Catch:{ IndexOutOfBoundsException -> 0x0061 }
            if (r3 == 0) goto L_0x005b
            r3 = 4
            goto L_0x005c
        L_0x005b:
            r3 = -1
        L_0x005c:
            switch(r3) {
                case 0: goto L_0x0060;
                case 1: goto L_0x0060;
                case 2: goto L_0x0060;
                case 3: goto L_0x0060;
                case 4: goto L_0x0060;
                case 5: goto L_0x0060;
                default: goto L_0x005f;
            }
        L_0x005f:
            goto L_0x0061
        L_0x0060:
            r1 = 1
        L_0x0061:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.wp.b(java.lang.String, boolean):boolean");
    }

    public static List<Long> a() {
        return h;
    }

    private static void b() {
        for (long j = a; j <= g; j++) {
            h.add(Long.valueOf(j));
        }
    }
}
