package defpackage;

import android.content.Context;
import java.io.File;

/* renamed from: eni reason: default package */
/* compiled from: SoHotfix */
public class eni {
    public enj a;
    public enk b = new enk(this.a);
    public String c = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCIBb9Y6TnI/BUjWZXOKyygiwXvLsKYVAK9wtvEpk02c/EP5hjUWBWjBs+wN3OFZDN/lQy6nT1uBiw5a+U9bAUAiaW+9Zc21pYavI2nY1F/h4NYvMpLa3E2CTbii/wVTD7vZ835/b65oVFNFiCxkX5njry4PFk6tTnZ1WylEYLFnQIDAQAB";
    public String d;
    public eng e;
    private enl f = new enl(this.a);
    private enm g;

    public eni(Context context) {
        this.a = new enj(context);
    }

    /* access modifiers changed from: 0000 */
    public final long a() {
        if (this.d == null || this.d.length() <= 0) {
            return 0;
        }
        return new File(this.d).lastModified();
    }

    public final String b() {
        if (this.g != null) {
            return this.g.b;
        }
        enm b2 = this.b.b();
        if (b2 == null) {
            return null;
        }
        int i = b2.a;
        if (this.b.a(i) == null && this.b.b(i) != null) {
            return b2.b;
        }
        return null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:42:0x009c, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void c() {
        /*
            r22 = this;
            r1 = r22
            monitor-enter(r22)
            enm r2 = r1.g     // Catch:{ all -> 0x00e8 }
            if (r2 == 0) goto L_0x0009
            monitor-exit(r22)
            return
        L_0x0009:
            enk r2 = r1.b     // Catch:{ all -> 0x00e8 }
            enm r2 = r2.b()     // Catch:{ all -> 0x00e8 }
            if (r2 != 0) goto L_0x0013
            monitor-exit(r22)
            return
        L_0x0013:
            int r3 = r2.a     // Catch:{ all -> 0x00e8 }
            enk r4 = r1.b     // Catch:{ all -> 0x00e8 }
            java.io.File r4 = r4.a(r3)     // Catch:{ all -> 0x00e8 }
            if (r4 == 0) goto L_0x001f
            monitor-exit(r22)
            return
        L_0x001f:
            long r4 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x00e8 }
            enk r6 = r1.b     // Catch:{ all -> 0x00e8 }
            java.io.File r6 = r6.b(r3)     // Catch:{ all -> 0x00e8 }
            r7 = 240000(0x3a980, double:1.18576E-318)
            r9 = 0
            if (r6 != 0) goto L_0x0045
            enk r6 = r1.b     // Catch:{ all -> 0x00e8 }
            java.io.File r10 = new java.io.File     // Catch:{ all -> 0x00e8 }
            enj r6 = r6.a     // Catch:{ all -> 0x00e8 }
            java.lang.String r6 = defpackage.enn.a(r6, r3)     // Catch:{ all -> 0x00e8 }
            java.lang.String r11 = "first-run"
            r10.<init>(r6, r11)     // Catch:{ all -> 0x00e8 }
            defpackage.enn.a(r10, r4)     // Catch:{ all -> 0x00e8 }
            eng r6 = r1.e     // Catch:{ all -> 0x00e8 }
            goto L_0x00c2
        L_0x0045:
            enk r10 = r1.b     // Catch:{ all -> 0x00e8 }
            java.io.File r11 = new java.io.File     // Catch:{ all -> 0x00e8 }
            enj r10 = r10.a     // Catch:{ all -> 0x00e8 }
            java.lang.String r10 = defpackage.enn.a(r10, r3)     // Catch:{ all -> 0x00e8 }
            java.lang.String r12 = "ok"
            r11.<init>(r10, r12)     // Catch:{ all -> 0x00e8 }
            boolean r10 = r11.isFile()     // Catch:{ all -> 0x00e8 }
            if (r10 != 0) goto L_0x005b
            goto L_0x005c
        L_0x005b:
            r9 = r11
        L_0x005c:
            if (r9 != 0) goto L_0x00c2
            long r10 = r22.a()     // Catch:{ all -> 0x00e8 }
            r12 = 0
            int r14 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
            if (r14 <= 0) goto L_0x00c2
            long r14 = r6.lastModified()     // Catch:{ all -> 0x00e8 }
            int r6 = (r14 > r12 ? 1 : (r14 == r12 ? 0 : -1))
            if (r6 <= 0) goto L_0x00c2
            long r16 = r10 - r14
            r18 = 60000(0xea60, double:2.9644E-319)
            int r6 = (r16 > r18 ? 1 : (r16 == r18 ? 0 : -1))
            if (r6 <= 0) goto L_0x009d
            long r20 = r4 - r14
            int r6 = (r20 > r18 ? 1 : (r20 == r18 ? 0 : -1))
            if (r6 <= 0) goto L_0x009d
            int r6 = (r16 > r7 ? 1 : (r16 == r7 ? 0 : -1))
            if (r6 >= 0) goto L_0x009d
            enj r6 = r1.a     // Catch:{ all -> 0x00e8 }
            android.content.Context r6 = r6.a     // Catch:{ all -> 0x00e8 }
            boolean r6 = defpackage.enn.a(r6)     // Catch:{ all -> 0x00e8 }
            if (r6 == 0) goto L_0x009d
            enk r2 = r1.b     // Catch:{ all -> 0x00e8 }
            r2.a(r3, r4)     // Catch:{ all -> 0x00e8 }
            eng r2 = r1.e     // Catch:{ all -> 0x00e8 }
            if (r2 == 0) goto L_0x009b
            eng r2 = r1.e     // Catch:{ all -> 0x00e8 }
            r2.a(r3)     // Catch:{ all -> 0x00e8 }
        L_0x009b:
            monitor-exit(r22)
            return
        L_0x009d:
            r6 = 0
            long r14 = r4 - r14
            r16 = 1800000(0x1b7740, double:8.89318E-318)
            int r6 = (r14 > r16 ? 1 : (r14 == r16 ? 0 : -1))
            if (r6 <= 0) goto L_0x00c2
            long r10 = r4 - r10
            int r6 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
            if (r6 <= 0) goto L_0x00c2
            int r6 = (r10 > r18 ? 1 : (r10 == r18 ? 0 : -1))
            if (r6 >= 0) goto L_0x00c2
            enj r6 = r1.a     // Catch:{ all -> 0x00e8 }
            android.content.Context r6 = r6.a     // Catch:{ all -> 0x00e8 }
            boolean r6 = defpackage.enn.a(r6)     // Catch:{ all -> 0x00e8 }
            if (r6 == 0) goto L_0x00c2
            enk r2 = r1.b     // Catch:{ all -> 0x00e8 }
            r2.a(r3, r4)     // Catch:{ all -> 0x00e8 }
            monitor-exit(r22)
            return
        L_0x00c2:
            r1.g = r2     // Catch:{ all -> 0x00e8 }
            enl r2 = r1.f     // Catch:{ all -> 0x00e8 }
            enm r6 = r1.g     // Catch:{ all -> 0x00e8 }
            r10 = 1
            enm[] r10 = new defpackage.enm[r10]     // Catch:{ all -> 0x00e8 }
            r11 = 0
            r10[r11] = r6     // Catch:{ all -> 0x00e8 }
            r2.a(r10)     // Catch:{ all -> 0x00e8 }
            if (r9 == 0) goto L_0x00d5
            monitor-exit(r22)
            return
        L_0x00d5:
            android.os.Handler r2 = new android.os.Handler     // Catch:{ all -> 0x00e8 }
            android.os.Looper r6 = android.os.Looper.getMainLooper()     // Catch:{ all -> 0x00e8 }
            r2.<init>(r6)     // Catch:{ all -> 0x00e8 }
            eni$1 r6 = new eni$1     // Catch:{ all -> 0x00e8 }
            r6.<init>(r3, r4)     // Catch:{ all -> 0x00e8 }
            r2.postDelayed(r6, r7)     // Catch:{ all -> 0x00e8 }
            monitor-exit(r22)
            return
        L_0x00e8:
            r0 = move-exception
            r2 = r0
            monitor-exit(r22)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.eni.c():void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0067, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00bc, code lost:
        return 1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized int a(java.io.File r5, java.lang.String r6, int r7) {
        /*
            r4 = this;
            monitor-enter(r4)
            android.os.Looper r0 = android.os.Looper.myLooper()     // Catch:{ all -> 0x00bd }
            android.os.Looper r1 = android.os.Looper.getMainLooper()     // Catch:{ all -> 0x00bd }
            if (r0 != r1) goto L_0x0013
            java.lang.RuntimeException r5 = new java.lang.RuntimeException     // Catch:{ all -> 0x00bd }
            java.lang.String r6 = "You can not call on main thread"
            r5.<init>(r6)     // Catch:{ all -> 0x00bd }
            throw r5     // Catch:{ all -> 0x00bd }
        L_0x0013:
            enk r0 = r4.b     // Catch:{ all -> 0x00bd }
            java.io.File r1 = new java.io.File     // Catch:{ all -> 0x00bd }
            enj r0 = r0.a     // Catch:{ all -> 0x00bd }
            java.lang.String r0 = defpackage.enn.a(r0, r7)     // Catch:{ all -> 0x00bd }
            r1.<init>(r0)     // Catch:{ all -> 0x00bd }
            boolean r0 = r1.exists()     // Catch:{ all -> 0x00bd }
            r1 = 1
            if (r0 == 0) goto L_0x0029
            r0 = 7
            goto L_0x002a
        L_0x0029:
            r0 = 1
        L_0x002a:
            java.lang.String r2 = r4.c     // Catch:{ all -> 0x00bd }
            boolean r6 = defpackage.ent.a(r5, r6, r2)     // Catch:{ all -> 0x00bd }
            if (r6 != 0) goto L_0x0033
            r0 = 3
        L_0x0033:
            enk r6 = r4.b     // Catch:{ all -> 0x00bd }
            java.io.File r2 = new java.io.File     // Catch:{ all -> 0x00bd }
            enj r6 = r6.a     // Catch:{ all -> 0x00bd }
            java.lang.String r6 = defpackage.enn.b(r6, r7)     // Catch:{ all -> 0x00bd }
            java.lang.String r3 = "zip"
            r2.<init>(r6, r3)     // Catch:{ all -> 0x00bd }
            boolean r5 = defpackage.enu.a(r5, r2)     // Catch:{ all -> 0x00bd }
            if (r5 != 0) goto L_0x0049
            r0 = 5
        L_0x0049:
            enk r5 = r4.b     // Catch:{ all -> 0x00bd }
            boolean r5 = r5.c(r7)     // Catch:{ all -> 0x00bd }
            if (r5 != 0) goto L_0x0052
            r0 = 4
        L_0x0052:
            enk r5 = r4.b     // Catch:{ all -> 0x00bd }
            boolean r5 = r5.d(r7)     // Catch:{ all -> 0x00bd }
            if (r5 != 0) goto L_0x005b
            r0 = 6
        L_0x005b:
            if (r0 == r1) goto L_0x0068
            eng r5 = r4.e     // Catch:{ all -> 0x00bd }
            if (r5 == 0) goto L_0x0066
            eng r5 = r4.e     // Catch:{ all -> 0x00bd }
            r5.a(r7, r0)     // Catch:{ all -> 0x00bd }
        L_0x0066:
            monitor-exit(r4)
            return r0
        L_0x0068:
            enk r5 = r4.b     // Catch:{ all -> 0x00bd }
            java.io.File r6 = new java.io.File     // Catch:{ all -> 0x00bd }
            enj r5 = r5.a     // Catch:{ all -> 0x00bd }
            java.lang.String r5 = defpackage.enn.b(r5, r7)     // Catch:{ all -> 0x00bd }
            java.lang.String r0 = "zip"
            r6.<init>(r5, r0)     // Catch:{ all -> 0x00bd }
            boolean r5 = r6.exists()     // Catch:{ all -> 0x00bd }
            if (r5 == 0) goto L_0x0080
            defpackage.enp.b(r6)     // Catch:{ all -> 0x00bd }
        L_0x0080:
            enk r5 = r4.b     // Catch:{ all -> 0x00bd }
            r5.a()     // Catch:{ all -> 0x00bd }
            enk r5 = r4.b     // Catch:{ all -> 0x00bd }
            java.io.File r6 = new java.io.File     // Catch:{ all -> 0x00bd }
            enj r0 = r5.a     // Catch:{ all -> 0x00bd }
            java.lang.String r0 = r0.c     // Catch:{ all -> 0x00bd }
            r6.<init>(r0)     // Catch:{ all -> 0x00bd }
            boolean r0 = r6.exists()     // Catch:{ all -> 0x00bd }
            if (r0 != 0) goto L_0x0099
            r6.mkdirs()     // Catch:{ all -> 0x00bd }
        L_0x0099:
            java.io.File r6 = new java.io.File     // Catch:{ all -> 0x00bd }
            enj r0 = r5.a     // Catch:{ all -> 0x00bd }
            java.lang.String r0 = defpackage.enn.b(r0, r7)     // Catch:{ all -> 0x00bd }
            r6.<init>(r0)     // Catch:{ all -> 0x00bd }
            java.io.File r0 = new java.io.File     // Catch:{ all -> 0x00bd }
            enj r5 = r5.a     // Catch:{ all -> 0x00bd }
            java.lang.String r5 = defpackage.enn.a(r5, r7)     // Catch:{ all -> 0x00bd }
            r0.<init>(r5)     // Catch:{ all -> 0x00bd }
            r6.renameTo(r0)     // Catch:{ all -> 0x00bd }
            eng r5 = r4.e     // Catch:{ all -> 0x00bd }
            if (r5 == 0) goto L_0x00bb
            eng r5 = r4.e     // Catch:{ all -> 0x00bd }
            r5.c(r7)     // Catch:{ all -> 0x00bd }
        L_0x00bb:
            monitor-exit(r4)
            return r1
        L_0x00bd:
            r5 = move-exception
            monitor-exit(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.eni.a(java.io.File, java.lang.String, int):int");
    }
}
