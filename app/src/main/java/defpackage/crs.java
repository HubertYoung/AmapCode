package defpackage;

import com.autonavi.bundle.entity.infolite.external.CitySuggestion;
import java.util.ArrayList;

/* renamed from: crs reason: default package */
/* compiled from: PoiResultHelper */
public final class crs {
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x00bd, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x00c2, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized java.util.ArrayList<com.autonavi.common.model.POI> a(int r6, defpackage.aud r7) {
        /*
            java.lang.Class<crs> r0 = defpackage.crs.class
            monitor-enter(r0)
            r1 = 0
            if (r7 == 0) goto L_0x00c1
            auc r2 = r7.b     // Catch:{ all -> 0x00be }
            if (r2 == 0) goto L_0x00c1
            auc r2 = r7.b     // Catch:{ all -> 0x00be }
            java.util.ArrayList<com.autonavi.common.model.POI> r2 = r2.d     // Catch:{ all -> 0x00be }
            if (r2 == 0) goto L_0x00c1
            auc r2 = r7.b     // Catch:{ all -> 0x00be }
            java.util.ArrayList<com.autonavi.common.model.POI> r2 = r2.d     // Catch:{ all -> 0x00be }
            int r2 = r2.size()     // Catch:{ all -> 0x00be }
            if (r2 != 0) goto L_0x001c
            goto L_0x00c1
        L_0x001c:
            r2 = 0
            if (r7 == 0) goto L_0x0060
            auc r3 = r7.b     // Catch:{ all -> 0x00be }
            if (r3 == 0) goto L_0x0060
            auc r3 = r7.b     // Catch:{ all -> 0x00be }
            java.util.ArrayList<com.autonavi.common.model.POI> r3 = r3.d     // Catch:{ all -> 0x00be }
            if (r3 != 0) goto L_0x002a
            goto L_0x0060
        L_0x002a:
            r3 = 0
        L_0x002b:
            auc r4 = r7.b     // Catch:{ all -> 0x00be }
            java.util.ArrayList<com.autonavi.common.model.POI> r4 = r4.d     // Catch:{ all -> 0x00be }
            int r4 = r4.size()     // Catch:{ all -> 0x00be }
            if (r3 >= r4) goto L_0x0056
            auc r4 = r7.b     // Catch:{ all -> 0x00be }
            java.util.ArrayList<com.autonavi.common.model.POI> r4 = r4.d     // Catch:{ all -> 0x00be }
            java.lang.Object r4 = r4.get(r3)     // Catch:{ all -> 0x00be }
            com.autonavi.common.model.POI r4 = (com.autonavi.common.model.POI) r4     // Catch:{ all -> 0x00be }
            if (r4 == 0) goto L_0x0053
            java.lang.String r4 = r4.getId()     // Catch:{ all -> 0x00be }
            boolean r4 = android.text.TextUtils.isEmpty(r4)     // Catch:{ all -> 0x00be }
            if (r4 == 0) goto L_0x0053
            auc r4 = r7.b     // Catch:{ all -> 0x00be }
            java.util.ArrayList<com.autonavi.common.model.POI> r4 = r4.d     // Catch:{ all -> 0x00be }
            r4.remove(r3)     // Catch:{ all -> 0x00be }
            goto L_0x0056
        L_0x0053:
            int r3 = r3 + 1
            goto L_0x002b
        L_0x0056:
            auc r3 = r7.b     // Catch:{ all -> 0x00be }
            boolean r3 = r3.g     // Catch:{ all -> 0x00be }
            if (r3 != 0) goto L_0x0060
            auc r3 = r7.b     // Catch:{ all -> 0x00be }
            r3.f = r1     // Catch:{ all -> 0x00be }
        L_0x0060:
            auc r3 = r7.b     // Catch:{ all -> 0x00be }
            java.util.ArrayList<com.autonavi.common.model.POI> r3 = r3.d     // Catch:{ all -> 0x00be }
            int r3 = r3.size()     // Catch:{ all -> 0x00be }
            if (r6 <= 0) goto L_0x00bc
            r4 = 1
            if (r7 == 0) goto L_0x008f
            auc r5 = r7.b     // Catch:{ all -> 0x00be }
            if (r5 == 0) goto L_0x008f
            auc r5 = r7.b     // Catch:{ all -> 0x00be }
            java.util.ArrayList<com.autonavi.common.model.POI> r5 = r5.d     // Catch:{ all -> 0x00be }
            if (r5 == 0) goto L_0x008f
            auc r5 = r7.b     // Catch:{ all -> 0x00be }
            java.util.ArrayList<com.autonavi.common.model.POI> r5 = r5.d     // Catch:{ all -> 0x00be }
            int r5 = r5.size()     // Catch:{ all -> 0x00be }
            if (r5 <= 0) goto L_0x008f
            auc r5 = r7.b     // Catch:{ all -> 0x00be }
            java.util.ArrayList<com.autonavi.common.model.POI> r5 = r5.d     // Catch:{ all -> 0x00be }
            int r5 = r5.size()     // Catch:{ all -> 0x00be }
            int r5 = r5 + 10
            int r5 = r5 - r4
            int r5 = r5 / 10
            goto L_0x0090
        L_0x008f:
            r5 = 1
        L_0x0090:
            if (r6 <= r5) goto L_0x0093
            goto L_0x00bc
        L_0x0093:
            int r6 = r6 - r4
            int r6 = r6 * 10
            if (r6 < r3) goto L_0x009a
            monitor-exit(r0)
            return r1
        L_0x009a:
            int r1 = r6 + 10
            int r1 = r1 - r4
            int r3 = r3 - r4
            if (r1 <= r3) goto L_0x00a1
            r1 = r3
        L_0x00a1:
            int r1 = r1 - r6
            int r1 = r1 + r4
            java.util.ArrayList r3 = new java.util.ArrayList     // Catch:{ all -> 0x00be }
            r3.<init>()     // Catch:{ all -> 0x00be }
        L_0x00a8:
            if (r2 >= r1) goto L_0x00ba
            auc r4 = r7.b     // Catch:{ all -> 0x00be }
            java.util.ArrayList<com.autonavi.common.model.POI> r4 = r4.d     // Catch:{ all -> 0x00be }
            int r5 = r6 + r2
            java.lang.Object r4 = r4.get(r5)     // Catch:{ all -> 0x00be }
            r3.add(r4)     // Catch:{ all -> 0x00be }
            int r2 = r2 + 1
            goto L_0x00a8
        L_0x00ba:
            monitor-exit(r0)
            return r3
        L_0x00bc:
            monitor-exit(r0)
            return r1
        L_0x00be:
            r6 = move-exception
            monitor-exit(r0)
            throw r6
        L_0x00c1:
            monitor-exit(r0)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.crs.a(int, aud):java.util.ArrayList");
    }

    public static ArrayList<CitySuggestion> a(int i, ArrayList<CitySuggestion> arrayList, int i2) {
        if (arrayList == null || arrayList.size() == 0) {
            return null;
        }
        int size = arrayList.size();
        if (i <= 0 || i > a(arrayList, 10)) {
            return null;
        }
        int i3 = (i - 1) * i2;
        if (i3 >= size) {
            return null;
        }
        int i4 = (i2 + i3) - 1;
        int i5 = size - 1;
        if (i4 > i5) {
            i4 = i5;
        }
        int i6 = (i4 - i3) + 1;
        ArrayList<CitySuggestion> arrayList2 = new ArrayList<>();
        for (int i7 = 0; i7 < i6; i7++) {
            arrayList2.add(arrayList.get(i3 + i7));
        }
        return arrayList2;
    }

    public static int a(ArrayList<CitySuggestion> arrayList, int i) {
        if (arrayList == null || arrayList.size() == 0) {
            return 0;
        }
        int size = arrayList.size() / i;
        if (arrayList.size() % i != 0) {
            size++;
        }
        return size;
    }
}
