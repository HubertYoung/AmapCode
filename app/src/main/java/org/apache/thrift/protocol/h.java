package org.apache.thrift.protocol;

public class h {
    private static int a = Integer.MAX_VALUE;

    public static void a(e eVar, byte b) {
        a(eVar, b, a);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x004c, code lost:
        r4 = r3.b();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0052, code lost:
        if (r4.b == 0) goto L_0x005c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0054, code lost:
        a(r3, r4.b, r5 - 1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x005c, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void a(org.apache.thrift.protocol.e r3, byte r4, int r5) {
        /*
            if (r5 > 0) goto L_0x000a
            org.apache.thrift.f r3 = new org.apache.thrift.f
            java.lang.String r4 = "Maximum skip depth exceeded"
            r3.<init>(r4)
            throw r3
        L_0x000a:
            r0 = 0
            switch(r4) {
                case 2: goto L_0x0075;
                case 3: goto L_0x0071;
                case 4: goto L_0x006d;
                case 5: goto L_0x000e;
                case 6: goto L_0x0069;
                case 7: goto L_0x000e;
                case 8: goto L_0x0065;
                case 9: goto L_0x000e;
                case 10: goto L_0x0061;
                case 11: goto L_0x005d;
                case 12: goto L_0x004c;
                case 13: goto L_0x0034;
                case 14: goto L_0x0021;
                case 15: goto L_0x000f;
                default: goto L_0x000e;
            }
        L_0x000e:
            return
        L_0x000f:
            org.apache.thrift.protocol.c r4 = r3.d()
        L_0x0013:
            int r1 = r4.b
            if (r0 >= r1) goto L_0x0078
            byte r1 = r4.a
            int r2 = r5 + -1
            a(r3, r1, r2)
            int r0 = r0 + 1
            goto L_0x0013
        L_0x0021:
            org.apache.thrift.protocol.i r4 = r3.e()
        L_0x0025:
            int r1 = r4.b
            if (r0 >= r1) goto L_0x0033
            byte r1 = r4.a
            int r2 = r5 + -1
            a(r3, r1, r2)
            int r0 = r0 + 1
            goto L_0x0025
        L_0x0033:
            return
        L_0x0034:
            org.apache.thrift.protocol.d r4 = r3.c()
        L_0x0038:
            int r1 = r4.c
            if (r0 >= r1) goto L_0x004b
            byte r1 = r4.a
            int r2 = r5 + -1
            a(r3, r1, r2)
            byte r1 = r4.b
            a(r3, r1, r2)
            int r0 = r0 + 1
            goto L_0x0038
        L_0x004b:
            return
        L_0x004c:
            org.apache.thrift.protocol.b r4 = r3.b()
            byte r0 = r4.b
            if (r0 == 0) goto L_0x005c
            byte r4 = r4.b
            int r0 = r5 + -1
            a(r3, r4, r0)
            goto L_0x004c
        L_0x005c:
            return
        L_0x005d:
            r3.m()
            return
        L_0x0061:
            r3.j()
            return
        L_0x0065:
            r3.i()
            return
        L_0x0069:
            r3.h()
            return
        L_0x006d:
            r3.k()
            return
        L_0x0071:
            r3.g()
            return
        L_0x0075:
            r3.f()
        L_0x0078:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.thrift.protocol.h.a(org.apache.thrift.protocol.e, byte, int):void");
    }
}
