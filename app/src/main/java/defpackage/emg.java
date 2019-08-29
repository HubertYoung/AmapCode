package defpackage;

import java.io.RandomAccessFile;

/* renamed from: emg reason: default package */
/* compiled from: CPUSamplerAction */
public final class emg extends emf {
    private int a;
    private long b;
    private long c;
    private RandomAccessFile d;
    private RandomAccessFile e;
    private emq f;

    public emg(emq emq) {
        this.f = emq;
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x00b5 A[Catch:{ FileNotFoundException -> 0x0100, IOException -> 0x00fb }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a() {
        /*
            r10 = this;
            int r0 = r10.a
            if (r0 != 0) goto L_0x000a
            int r0 = android.os.Process.myPid()
            r10.a = r0
        L_0x000a:
            java.io.RandomAccessFile r0 = r10.d     // Catch:{ FileNotFoundException -> 0x0100, IOException -> 0x00fb }
            if (r0 == 0) goto L_0x0012
            java.io.RandomAccessFile r0 = r10.e     // Catch:{ FileNotFoundException -> 0x0100, IOException -> 0x00fb }
            if (r0 != 0) goto L_0x003b
        L_0x0012:
            java.io.RandomAccessFile r0 = new java.io.RandomAccessFile     // Catch:{ FileNotFoundException -> 0x0100, IOException -> 0x00fb }
            java.lang.String r1 = "/proc/stat"
            java.lang.String r2 = "r"
            r0.<init>(r1, r2)     // Catch:{ FileNotFoundException -> 0x0100, IOException -> 0x00fb }
            r10.d = r0     // Catch:{ FileNotFoundException -> 0x0100, IOException -> 0x00fb }
            java.io.RandomAccessFile r0 = new java.io.RandomAccessFile     // Catch:{ FileNotFoundException -> 0x0100, IOException -> 0x00fb }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ FileNotFoundException -> 0x0100, IOException -> 0x00fb }
            java.lang.String r2 = "/proc/"
            r1.<init>(r2)     // Catch:{ FileNotFoundException -> 0x0100, IOException -> 0x00fb }
            int r2 = r10.a     // Catch:{ FileNotFoundException -> 0x0100, IOException -> 0x00fb }
            r1.append(r2)     // Catch:{ FileNotFoundException -> 0x0100, IOException -> 0x00fb }
            java.lang.String r2 = "/stat"
            r1.append(r2)     // Catch:{ FileNotFoundException -> 0x0100, IOException -> 0x00fb }
            java.lang.String r1 = r1.toString()     // Catch:{ FileNotFoundException -> 0x0100, IOException -> 0x00fb }
            java.lang.String r2 = "r"
            r0.<init>(r1, r2)     // Catch:{ FileNotFoundException -> 0x0100, IOException -> 0x00fb }
            r10.e = r0     // Catch:{ FileNotFoundException -> 0x0100, IOException -> 0x00fb }
        L_0x003b:
            java.io.RandomAccessFile r0 = r10.d     // Catch:{ FileNotFoundException -> 0x0100, IOException -> 0x00fb }
            r1 = 0
            r0.seek(r1)     // Catch:{ FileNotFoundException -> 0x0100, IOException -> 0x00fb }
            java.io.RandomAccessFile r0 = r10.e     // Catch:{ FileNotFoundException -> 0x0100, IOException -> 0x00fb }
            r0.seek(r1)     // Catch:{ FileNotFoundException -> 0x0100, IOException -> 0x00fb }
            java.io.RandomAccessFile r0 = r10.d     // Catch:{ FileNotFoundException -> 0x0100, IOException -> 0x00fb }
            java.lang.String r0 = r0.readLine()     // Catch:{ FileNotFoundException -> 0x0100, IOException -> 0x00fb }
            java.io.RandomAccessFile r3 = r10.e     // Catch:{ FileNotFoundException -> 0x0100, IOException -> 0x00fb }
            java.lang.String r3 = r3.readLine()     // Catch:{ FileNotFoundException -> 0x0100, IOException -> 0x00fb }
            r4 = 0
            if (r0 != 0) goto L_0x005b
            if (r3 == 0) goto L_0x0059
            goto L_0x005b
        L_0x0059:
            r0 = r4
            goto L_0x006f
        L_0x005b:
            java.lang.String r0 = r0.trim()     // Catch:{ FileNotFoundException -> 0x0100, IOException -> 0x00fb }
            java.lang.String r4 = " "
            java.lang.String[] r4 = r0.split(r4)     // Catch:{ FileNotFoundException -> 0x0100, IOException -> 0x00fb }
            java.lang.String r0 = r3.trim()     // Catch:{ FileNotFoundException -> 0x0100, IOException -> 0x00fb }
            java.lang.String r3 = " "
            java.lang.String[] r0 = r0.split(r3)     // Catch:{ FileNotFoundException -> 0x0100, IOException -> 0x00fb }
        L_0x006f:
            if (r4 == 0) goto L_0x008b
            int r3 = r4.length     // Catch:{ FileNotFoundException -> 0x0100, IOException -> 0x00fb }
            r5 = 9
            if (r3 < r5) goto L_0x008b
            r3 = 2
            r5 = r1
        L_0x0078:
            r7 = 8
            if (r3 > r7) goto L_0x008c
            r7 = r4[r3]     // Catch:{ FileNotFoundException -> 0x0100, IOException -> 0x00fb }
            java.lang.Long r7 = java.lang.Long.valueOf(r7)     // Catch:{ FileNotFoundException -> 0x0100, IOException -> 0x00fb }
            long r7 = r7.longValue()     // Catch:{ FileNotFoundException -> 0x0100, IOException -> 0x00fb }
            r9 = 0
            long r5 = r5 + r7
            int r3 = r3 + 1
            goto L_0x0078
        L_0x008b:
            r5 = r1
        L_0x008c:
            if (r0 == 0) goto L_0x00ae
            int r3 = r0.length     // Catch:{ FileNotFoundException -> 0x0100, IOException -> 0x00fb }
            r4 = 15
            if (r3 < r4) goto L_0x00ae
            r3 = 13
            r3 = r0[r3]     // Catch:{ FileNotFoundException -> 0x0100, IOException -> 0x00fb }
            java.lang.Long r3 = java.lang.Long.valueOf(r3)     // Catch:{ FileNotFoundException -> 0x0100, IOException -> 0x00fb }
            long r3 = r3.longValue()     // Catch:{ FileNotFoundException -> 0x0100, IOException -> 0x00fb }
            r7 = 14
            r0 = r0[r7]     // Catch:{ FileNotFoundException -> 0x0100, IOException -> 0x00fb }
            java.lang.Long r0 = java.lang.Long.valueOf(r0)     // Catch:{ FileNotFoundException -> 0x0100, IOException -> 0x00fb }
            long r7 = r0.longValue()     // Catch:{ FileNotFoundException -> 0x0100, IOException -> 0x00fb }
            r0 = 0
            long r3 = r3 + r7
            goto L_0x00af
        L_0x00ae:
            r3 = r1
        L_0x00af:
            long r7 = r10.b     // Catch:{ FileNotFoundException -> 0x0100, IOException -> 0x00fb }
            int r0 = (r7 > r1 ? 1 : (r7 == r1 ? 0 : -1))
            if (r0 == 0) goto L_0x00f6
            long r7 = r10.c     // Catch:{ FileNotFoundException -> 0x0100, IOException -> 0x00fb }
            int r0 = (r7 > r1 ? 1 : (r7 == r1 ? 0 : -1))
            if (r0 != 0) goto L_0x00bc
            goto L_0x00f6
        L_0x00bc:
            long r0 = r10.c     // Catch:{ FileNotFoundException -> 0x0100, IOException -> 0x00fb }
            r2 = 0
            long r0 = r3 - r0
            double r0 = (double) r0     // Catch:{ FileNotFoundException -> 0x0100, IOException -> 0x00fb }
            long r7 = r10.b     // Catch:{ FileNotFoundException -> 0x0100, IOException -> 0x00fb }
            r2 = 0
            long r7 = r5 - r7
            double r7 = (double) r7     // Catch:{ FileNotFoundException -> 0x0100, IOException -> 0x00fb }
            double r0 = r0 / r7
            r10.b = r5     // Catch:{ FileNotFoundException -> 0x0100, IOException -> 0x00fb }
            r10.c = r3     // Catch:{ FileNotFoundException -> 0x0100, IOException -> 0x00fb }
            emq r2 = r10.f     // Catch:{ FileNotFoundException -> 0x0100, IOException -> 0x00fb }
            if (r2 == 0) goto L_0x00f5
            r2 = 4636737291354636288(0x4059000000000000, double:100.0)
            double r0 = r0 * r2
            int r0 = (int) r0     // Catch:{ FileNotFoundException -> 0x0100, IOException -> 0x00fb }
            emq r1 = r10.f     // Catch:{ FileNotFoundException -> 0x0100, IOException -> 0x00fb }
            java.lang.String r2 = "cpu_rate"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ FileNotFoundException -> 0x0100, IOException -> 0x00fb }
            r3.<init>()     // Catch:{ FileNotFoundException -> 0x0100, IOException -> 0x00fb }
            r3.append(r0)     // Catch:{ FileNotFoundException -> 0x0100, IOException -> 0x00fb }
            java.lang.String r4 = "%"
            r3.append(r4)     // Catch:{ FileNotFoundException -> 0x0100, IOException -> 0x00fb }
            java.lang.String r3 = r3.toString()     // Catch:{ FileNotFoundException -> 0x0100, IOException -> 0x00fb }
            r4 = 50
            if (r0 >= r4) goto L_0x00f1
            r0 = 1
            goto L_0x00f2
        L_0x00f1:
            r0 = 0
        L_0x00f2:
            r1.a(r2, r3, r0)     // Catch:{ FileNotFoundException -> 0x0100, IOException -> 0x00fb }
        L_0x00f5:
            return
        L_0x00f6:
            r10.b = r5     // Catch:{ FileNotFoundException -> 0x0100, IOException -> 0x00fb }
            r10.c = r3     // Catch:{ FileNotFoundException -> 0x0100, IOException -> 0x00fb }
            return
        L_0x00fb:
            r0 = move-exception
            r0.printStackTrace()
            return
        L_0x0100:
            r0 = move-exception
            r0.printStackTrace()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.emg.a():void");
    }
}
