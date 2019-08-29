package defpackage;

/* renamed from: eun reason: default package */
/* compiled from: SpeexRecorder */
public final class eun implements Runnable {
    public a a = null;
    private volatile boolean b;
    private final Object c = new Object();
    private String d = null;
    private int e = 8000;

    /* renamed from: eun$a */
    /* compiled from: SpeexRecorder */
    public interface a {
        void a(double d);

        void a(int i);
    }

    public eun(String str) {
        this.d = str;
        this.e = 16000;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(8:5|6|7|8|9|10|3|2) */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x002c, code lost:
        android.os.Process.setThreadPriority(-19);
        r2 = new short[160];
        r3 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:?, code lost:
        r4 = new android.media.AudioRecord(1, r12.e, 16, 2, android.media.AudioRecord.getMinBufferSize(r12.e, 16, 2));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
        r4.startRecording();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0051, code lost:
        if (r12.b == false) goto L_0x0089;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0053, code lost:
        r3 = r4.read(r2, 0, 160);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0057, code lost:
        if (r3 >= 0) goto L_0x0063;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x005b, code lost:
        if (r12.a == null) goto L_0x0089;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x005d, code lost:
        r12.a.a(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0063, code lost:
        r4 = new defpackage.eup.a(r0);
        r5 = r0.a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x006a, code lost:
        monitor-enter(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
        r4.a = r3;
        java.lang.System.arraycopy(r2, 0, r4.b, 0, r3);
        r0.b.add(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0077, code lost:
        monitor-exit(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x007a, code lost:
        if (r12.a == null) goto L_0x004f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x007c, code lost:
        r12.a.a(a(r2));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0089, code lost:
        r4.release();
        r0.a(false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x008f, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0090, code lost:
        r3 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x0091, code lost:
        if (r3 != null) goto L_0x0093;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0093, code lost:
        r3.release();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0096, code lost:
        r0.a(false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x0099, code lost:
        return;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0022 */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x0093  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r12 = this;
            eup r0 = new eup
            java.lang.String r1 = r12.d
            int r2 = r12.e
            r0.<init>(r1, r2)
            java.lang.Thread r1 = new java.lang.Thread
            r1.<init>(r0)
            r2 = 1
            r0.a(r2)
            r1.start()
            java.lang.Object r1 = r12.c
            monitor-enter(r1)
        L_0x0018:
            boolean r2 = r12.b     // Catch:{ all -> 0x009a }
            if (r2 != 0) goto L_0x002b
            java.lang.Object r2 = r12.c     // Catch:{ InterruptedException -> 0x0022 }
            r2.wait()     // Catch:{ InterruptedException -> 0x0022 }
            goto L_0x0018
        L_0x0022:
            java.lang.Thread r0 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x009a }
            r0.interrupt()     // Catch:{ all -> 0x009a }
            monitor-exit(r1)     // Catch:{ all -> 0x009a }
            return
        L_0x002b:
            monitor-exit(r1)     // Catch:{ all -> 0x009a }
            r1 = -19
            android.os.Process.setThreadPriority(r1)
            int r1 = r12.e
            r2 = 16
            r3 = 2
            int r9 = android.media.AudioRecord.getMinBufferSize(r1, r2, r3)
            r1 = 160(0xa0, float:2.24E-43)
            short[] r2 = new short[r1]
            r3 = 0
            r10 = 0
            android.media.AudioRecord r11 = new android.media.AudioRecord     // Catch:{ Exception -> 0x0091 }
            r5 = 1
            int r6 = r12.e     // Catch:{ Exception -> 0x0091 }
            r7 = 16
            r8 = 2
            r4 = r11
            r4.<init>(r5, r6, r7, r8, r9)     // Catch:{ Exception -> 0x0091 }
            r11.startRecording()     // Catch:{ Exception -> 0x0090 }
        L_0x004f:
            boolean r3 = r12.b
            if (r3 == 0) goto L_0x0089
            int r3 = r11.read(r2, r10, r1)
            if (r3 >= 0) goto L_0x0063
            eun$a r1 = r12.a
            if (r1 == 0) goto L_0x0089
            eun$a r1 = r12.a
            r1.a(r3)
            goto L_0x0089
        L_0x0063:
            eup$a r4 = new eup$a
            r4.<init>()
            java.lang.Object r5 = r0.a
            monitor-enter(r5)
            r4.a = r3     // Catch:{ all -> 0x0086 }
            short[] r6 = r4.b     // Catch:{ all -> 0x0086 }
            java.lang.System.arraycopy(r2, r10, r6, r10, r3)     // Catch:{ all -> 0x0086 }
            java.util.List<eup$a> r3 = r0.b     // Catch:{ all -> 0x0086 }
            r3.add(r4)     // Catch:{ all -> 0x0086 }
            monitor-exit(r5)     // Catch:{ all -> 0x0086 }
            eun$a r3 = r12.a
            if (r3 == 0) goto L_0x004f
            eun$a r3 = r12.a
            double r4 = a(r2)
            r3.a(r4)
            goto L_0x004f
        L_0x0086:
            r0 = move-exception
            monitor-exit(r5)     // Catch:{ all -> 0x0086 }
            throw r0
        L_0x0089:
            r11.release()
            r0.a(r10)
            return
        L_0x0090:
            r3 = r11
        L_0x0091:
            if (r3 == 0) goto L_0x0096
            r3.release()
        L_0x0096:
            r0.a(r10)
            return
        L_0x009a:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x009a }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.eun.run():void");
    }

    public final void a(boolean z) {
        synchronized (this.c) {
            this.b = z;
            if (this.b) {
                this.c.notify();
            }
        }
    }

    private static double a(short[] sArr) {
        double d2 = 0.0d;
        for (int i = 0; i < 160; i++) {
            d2 += (double) Math.abs(sArr[i]);
        }
        return Math.log10((d2 / 160.0d) + 1.0d) * 10.0d;
    }
}
