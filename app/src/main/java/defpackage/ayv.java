package defpackage;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;

/* renamed from: ayv reason: default package */
/* compiled from: CommuteGeoUtils */
public final class ayv {
    public static boolean a(ayx ayx, String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return a(ayx, (Iterable<ayx>) new ArrayList<ayx>(a(str)));
    }

    /* JADX WARNING: Removed duplicated region for block: B:45:0x0103 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x0104 A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean a(defpackage.ayx r13, java.lang.Iterable<defpackage.ayx> r14) {
        /*
            r0 = 0
            if (r13 != 0) goto L_0x0004
            return r0
        L_0x0004:
            ayy$a r1 = new ayy$a
            r1.<init>()
            java.util.Iterator r14 = r14.iterator()
        L_0x000d:
            boolean r2 = r14.hasNext()
            if (r2 == 0) goto L_0x0044
            java.lang.Object r2 = r14.next()
            ayx r2 = (defpackage.ayx) r2
            if (r2 == 0) goto L_0x000d
            double r3 = r2.b
            double r5 = r1.a
            double r3 = java.lang.Math.min(r3, r5)
            r1.a = r3
            double r3 = r2.a
            double r5 = r1.b
            double r3 = java.lang.Math.min(r3, r5)
            r1.b = r3
            double r3 = r2.b
            double r5 = r1.c
            double r3 = java.lang.Math.max(r3, r5)
            r1.c = r3
            double r2 = r2.a
            double r4 = r1.d
            double r2 = java.lang.Math.max(r2, r4)
            r1.d = r2
            goto L_0x000d
        L_0x0044:
            double r2 = r1.a
            r4 = 4746794007244308480(0x41dfffffffc00000, double:2.147483647E9)
            int r14 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r14 == 0) goto L_0x007c
            double r2 = r1.b
            int r14 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r14 == 0) goto L_0x007c
            double r2 = r1.c
            r4 = -4476578029606273024(0xc1e0000000000000, double:-2.147483648E9)
            int r14 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r14 == 0) goto L_0x007c
            double r2 = r1.d
            int r14 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r14 != 0) goto L_0x0064
            goto L_0x007c
        L_0x0064:
            ayy r14 = new ayy
            ayx r2 = new ayx
            double r3 = r1.b
            double r5 = r1.a
            r2.<init>(r3, r5)
            ayx r3 = new ayx
            double r4 = r1.d
            double r6 = r1.c
            r3.<init>(r4, r6)
            r14.<init>(r2, r3, r0)
            goto L_0x007d
        L_0x007c:
            r14 = 0
        L_0x007d:
            if (r14 != 0) goto L_0x0080
            return r0
        L_0x0080:
            azn r1 = defpackage.azn.a.a
            double r1 = r1.a()
            double r1 = java.lang.Math.abs(r1)
            ayx r3 = r14.a
            if (r3 == 0) goto L_0x00cd
            ayx r3 = r14.b
            if (r3 != 0) goto L_0x0093
            goto L_0x00cd
        L_0x0093:
            ayx r3 = r14.a
            double r3 = r3.b
            r5 = 4531438181811273797(0x3ee2e7048c80c045, double:9.013372974292616E-6)
            double r5 = r5 * r1
            double r3 = r3 - r5
            ayx r7 = r14.a
            double r7 = r7.a
            ayx r9 = r14.a
            double r9 = r9.b
            double r9 = defpackage.ayw.a(r9, r1)
            double r7 = r7 - r9
            ayx r9 = r14.b
            double r9 = r9.b
            double r9 = r9 + r5
            ayx r5 = r14.b
            double r5 = r5.a
            ayx r14 = r14.b
            double r11 = r14.b
            double r1 = defpackage.ayw.a(r11, r1)
            double r5 = r5 + r1
            ayy r14 = new ayy
            ayx r1 = new ayx
            r1.<init>(r7, r3)
            ayx r2 = new ayx
            r2.<init>(r5, r9)
            r14.<init>(r1, r2)
        L_0x00cd:
            r1 = 1
            if (r13 == 0) goto L_0x0100
            double r2 = r13.b
            ayx r4 = r14.a
            double r4 = r4.b
            int r4 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r4 > 0) goto L_0x00e4
            ayx r4 = r14.b
            double r4 = r4.b
            int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r2 > 0) goto L_0x00e4
            r2 = 1
            goto L_0x00e5
        L_0x00e4:
            r2 = 0
        L_0x00e5:
            if (r2 == 0) goto L_0x0100
            double r2 = r13.a
            ayx r13 = r14.a
            double r4 = r13.a
            int r13 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r13 > 0) goto L_0x00fb
            ayx r13 = r14.b
            double r13 = r13.a
            int r13 = (r2 > r13 ? 1 : (r2 == r13 ? 0 : -1))
            if (r13 > 0) goto L_0x00fb
            r13 = 1
            goto L_0x00fc
        L_0x00fb:
            r13 = 0
        L_0x00fc:
            if (r13 == 0) goto L_0x0100
            r13 = 1
            goto L_0x0101
        L_0x0100:
            r13 = 0
        L_0x0101:
            if (r13 != 0) goto L_0x0104
            return r1
        L_0x0104:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ayv.a(ayx, java.lang.Iterable):boolean");
    }

    @NonNull
    private static List<ayx> a(@NonNull String str) {
        String[] split = str.split(",");
        ArrayList arrayList = new ArrayList();
        int length = split.length;
        if (length == 0 || length % 2 != 0) {
            return arrayList;
        }
        for (int i = 0; i < length - 1; i += 2) {
            arrayList.add(new ayx(ayr.a(split[i]), ayr.a(split[i + 1])));
        }
        return arrayList;
    }
}
