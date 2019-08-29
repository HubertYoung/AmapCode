package defpackage;

import java.util.ArrayList;

/* renamed from: dxm reason: default package */
/* compiled from: BusRemindSuspendViewConfig */
public final class dxm implements dxn {
    private static final ArrayList<Integer> a;

    static {
        ArrayList<Integer> arrayList = new ArrayList<>();
        a = arrayList;
        arrayList.add(Integer.valueOf(3));
        a.add(Integer.valueOf(1));
        a.add(Integer.valueOf(9));
        a.add(Integer.valueOf(4));
        a.add(Integer.valueOf(6));
        a.add(Integer.valueOf(7));
        a.add(Integer.valueOf(8));
    }

    public final ArrayList<Integer> a() {
        return a;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0020, code lost:
        r1 = true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public dxm(boolean r3) {
        /*
            r2 = this;
            r2.<init>()
            esb r0 = defpackage.esb.a.a
            java.lang.Class<cuh> r1 = defpackage.cuh.class
            esc r0 = r0.a(r1)
            cuh r0 = (defpackage.cuh) r0
            r1 = 0
            if (r0 == 0) goto L_0x001b
            cug r0 = r0.c()
            boolean r0 = r0.c()
            goto L_0x001c
        L_0x001b:
            r0 = 0
        L_0x001c:
            if (r0 == 0) goto L_0x0021
            if (r3 != 0) goto L_0x0021
            r1 = 1
        L_0x0021:
            r3 = 8
            if (r1 == 0) goto L_0x003b
            java.util.ArrayList<java.lang.Integer> r0 = a
            java.lang.Integer r1 = java.lang.Integer.valueOf(r3)
            boolean r0 = r0.contains(r1)
            if (r0 != 0) goto L_0x0056
            java.util.ArrayList<java.lang.Integer> r0 = a
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            r0.add(r3)
            return
        L_0x003b:
            java.util.ArrayList<java.lang.Integer> r0 = a
            java.lang.Integer r1 = java.lang.Integer.valueOf(r3)
            boolean r0 = r0.contains(r1)
            if (r0 == 0) goto L_0x0056
            java.util.ArrayList<java.lang.Integer> r0 = a
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            int r3 = r0.indexOf(r3)
            java.util.ArrayList<java.lang.Integer> r0 = a
            r0.remove(r3)
        L_0x0056:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dxm.<init>(boolean):void");
    }
}
