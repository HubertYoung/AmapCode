package defpackage;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import java.io.File;
import java.io.IOException;
import java.util.List;

/* renamed from: dgq reason: default package */
/* compiled from: StorageService */
public final class dgq implements Callback, com.autonavi.common.Callback.a {
    private final byte[] a;
    private a b;
    private Handler c = new Handler(this);
    /* access modifiers changed from: private */
    public volatile boolean d;
    private long e;
    /* access modifiers changed from: private */
    public long f;
    /* access modifiers changed from: private */
    public int g;

    /* renamed from: dgq$a */
    /* compiled from: StorageService */
    public interface a {
        void a(int i);

        void b(int i);

        void c(int i);
    }

    private dgq(a aVar) {
        this.b = aVar;
        this.a = new byte[4096];
    }

    public static dgq a(a aVar) {
        return new dgq(aVar);
    }

    public final void cancel() {
        this.d = true;
    }

    public final boolean isCancelled() {
        return this.d;
    }

    public final com.autonavi.common.Callback.a a(final String str, final String str2, final List<String> list) {
        ahl.a(new defpackage.dgw.a() {
            public final Object doBackground() throws Exception {
                dgq.this.d = false;
                dgq.this.f = 0;
                dgq.this.g = 0;
                dgq.a(dgq.this, str, str2, list);
                return null;
            }
        });
        return this;
    }

    private void a(int i, int i2) {
        this.c.sendMessage(this.c.obtainMessage(i, i2, 0));
    }

    private void a(int i) {
        this.f += (long) i;
        if (this.f <= this.e) {
            int i2 = (int) ((this.f * 100) / this.e);
            if (this.g < i2) {
                this.g = i2;
                this.c.removeMessages(2);
                a(2, i2);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:7:0x003c, code lost:
        if (r8 >= 0) goto L_0x0041;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean b(java.lang.String r7, java.lang.String r8, java.util.List<java.lang.String> r9) {
        /*
            r6 = this;
            r0 = 0
            r6.e = r0
            java.util.Iterator r9 = r9.iterator()
        L_0x0008:
            boolean r2 = r9.hasNext()
            if (r2 == 0) goto L_0x0023
            java.lang.Object r2 = r9.next()
            java.lang.String r2 = (java.lang.String) r2
            java.io.File r3 = new java.io.File
            r3.<init>(r7, r2)
            long r4 = r6.e
            long r2 = defpackage.dgu.b(r3)
            long r4 = r4 + r2
            r6.e = r4
            goto L_0x0008
        L_0x0023:
            boolean r7 = android.text.TextUtils.isEmpty(r8)
            if (r7 != 0) goto L_0x003f
            android.os.StatFs r7 = new android.os.StatFs
            r7.<init>(r8)
            int r8 = r7.getBlockSize()
            long r8 = (long) r8
            int r7 = r7.getAvailableBlocks()
            long r2 = (long) r7
            long r8 = r8 * r2
            int r7 = (r8 > r0 ? 1 : (r8 == r0 ? 0 : -1))
            if (r7 < 0) goto L_0x003f
            goto L_0x0041
        L_0x003f:
            r8 = -1
        L_0x0041:
            long r0 = r6.e
            int r7 = (r0 > r8 ? 1 : (r0 == r8 ? 0 : -1))
            if (r7 < 0) goto L_0x0049
            r7 = 1
            return r7
        L_0x0049:
            r7 = 0
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dgq.b(java.lang.String, java.lang.String, java.util.List):boolean");
    }

    private boolean c(String str, String str2, List<String> list) {
        if (!(str == null || str2 == null || list == null)) {
            try {
                for (String next : list) {
                    if (!a(new File(str, next), new File(str2, next))) {
                        return false;
                    }
                }
                return true;
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        return false;
    }

    private static void a(String str, List<String> list) {
        if (str != null && list != null) {
            for (String file : list) {
                dgu.a(new File(str, file), true);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:87:0x00f2 A[SYNTHETIC, Splitter:B:87:0x00f2] */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x00fc A[SYNTHETIC, Splitter:B:92:0x00fc] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean a(java.io.File r9, java.io.File r10) throws java.io.IOException {
        /*
            r8 = this;
            boolean r0 = r9.exists()
            r1 = 1
            if (r0 != 0) goto L_0x0008
            return r1
        L_0x0008:
            boolean r0 = r8.d
            r2 = 0
            if (r0 == 0) goto L_0x000e
            return r2
        L_0x000e:
            boolean r0 = r9.isDirectory()
            if (r0 == 0) goto L_0x003e
            boolean r0 = r10.exists()
            if (r0 != 0) goto L_0x001d
            r10.mkdirs()
        L_0x001d:
            java.lang.String[] r0 = r9.list()
            if (r0 == 0) goto L_0x003d
            int r3 = r0.length
            r4 = 0
        L_0x0025:
            if (r4 >= r3) goto L_0x003d
            r5 = r0[r4]
            java.io.File r6 = new java.io.File
            r6.<init>(r9, r5)
            java.io.File r7 = new java.io.File
            r7.<init>(r10, r5)
            boolean r5 = r8.a(r6, r7)
            if (r5 != 0) goto L_0x003a
            return r2
        L_0x003a:
            int r4 = r4 + 1
            goto L_0x0025
        L_0x003d:
            return r1
        L_0x003e:
            java.io.File r0 = new java.io.File
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r3 = r10.getAbsolutePath()
            r1.append(r3)
            java.lang.String r3 = ".copy"
            r1.append(r3)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            boolean r1 = r0.exists()
            if (r1 == 0) goto L_0x0061
            defpackage.dgu.a(r0)
        L_0x0061:
            r1 = 0
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ IOException -> 0x00eb, all -> 0x00e7 }
            r3.<init>(r9)     // Catch:{ IOException -> 0x00eb, all -> 0x00e7 }
            java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x00e3, all -> 0x00e0 }
            r4.<init>(r0)     // Catch:{ IOException -> 0x00e3, all -> 0x00e0 }
        L_0x006c:
            r1 = 0
        L_0x006d:
            byte[] r5 = r8.a     // Catch:{ IOException -> 0x00de, all -> 0x00dc }
            int r5 = r3.read(r5)     // Catch:{ IOException -> 0x00de, all -> 0x00dc }
            if (r5 <= 0) goto L_0x0099
            boolean r6 = r8.d     // Catch:{ IOException -> 0x00de, all -> 0x00dc }
            if (r6 == 0) goto L_0x008a
            r3.close()     // Catch:{ Exception -> 0x007d }
            goto L_0x0081
        L_0x007d:
            r9 = move-exception
            r9.printStackTrace()
        L_0x0081:
            r4.close()     // Catch:{ Exception -> 0x0085 }
            goto L_0x0089
        L_0x0085:
            r9 = move-exception
            r9.printStackTrace()
        L_0x0089:
            return r2
        L_0x008a:
            byte[] r6 = r8.a     // Catch:{ IOException -> 0x00de, all -> 0x00dc }
            r4.write(r6, r2, r5)     // Catch:{ IOException -> 0x00de, all -> 0x00dc }
            int r1 = r1 + r5
            r5 = 102400(0x19000, float:1.43493E-40)
            if (r1 <= r5) goto L_0x006d
            r8.a(r1)     // Catch:{ IOException -> 0x00de, all -> 0x00dc }
            goto L_0x006c
        L_0x0099:
            if (r1 <= 0) goto L_0x009e
            r8.a(r1)     // Catch:{ IOException -> 0x00de, all -> 0x00dc }
        L_0x009e:
            r3.close()     // Catch:{ Exception -> 0x00a2 }
            goto L_0x00a6
        L_0x00a2:
            r1 = move-exception
            r1.printStackTrace()
        L_0x00a6:
            r4.close()     // Catch:{ Exception -> 0x00aa }
            goto L_0x00ae
        L_0x00aa:
            r1 = move-exception
            r1.printStackTrace()
        L_0x00ae:
            java.lang.String r9 = defpackage.dhd.a(r9)
            java.lang.String r1 = defpackage.dhd.a(r0)
            boolean r3 = android.text.TextUtils.isEmpty(r1)
            if (r3 != 0) goto L_0x00d6
            boolean r9 = r1.equals(r9)
            if (r9 != 0) goto L_0x00c3
            goto L_0x00d6
        L_0x00c3:
            boolean r9 = r10.exists()
            if (r9 == 0) goto L_0x00cc
            defpackage.dgu.a(r10)
        L_0x00cc:
            boolean r9 = r8.d
            if (r9 == 0) goto L_0x00d1
            return r2
        L_0x00d1:
            boolean r9 = r0.renameTo(r10)
            return r9
        L_0x00d6:
            java.io.IOException r9 = new java.io.IOException
            r9.<init>()
            throw r9
        L_0x00dc:
            r9 = move-exception
            goto L_0x00f0
        L_0x00de:
            r9 = move-exception
            goto L_0x00e5
        L_0x00e0:
            r9 = move-exception
            r4 = r1
            goto L_0x00f0
        L_0x00e3:
            r9 = move-exception
            r4 = r1
        L_0x00e5:
            r1 = r3
            goto L_0x00ed
        L_0x00e7:
            r9 = move-exception
            r3 = r1
            r4 = r3
            goto L_0x00f0
        L_0x00eb:
            r9 = move-exception
            r4 = r1
        L_0x00ed:
            throw r9     // Catch:{ all -> 0x00ee }
        L_0x00ee:
            r9 = move-exception
            r3 = r1
        L_0x00f0:
            if (r3 == 0) goto L_0x00fa
            r3.close()     // Catch:{ Exception -> 0x00f6 }
            goto L_0x00fa
        L_0x00f6:
            r10 = move-exception
            r10.printStackTrace()
        L_0x00fa:
            if (r4 == 0) goto L_0x0104
            r4.close()     // Catch:{ Exception -> 0x0100 }
            goto L_0x0104
        L_0x0100:
            r10 = move-exception
            r10.printStackTrace()
        L_0x0104:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dgq.a(java.io.File, java.io.File):boolean");
    }

    public final boolean handleMessage(Message message) {
        if (this.b == null) {
            return false;
        }
        switch (message.what) {
            case 1:
                this.b.b(message.arg1);
                break;
            case 2:
                this.b.a(message.arg1);
                break;
            case 3:
                this.b.c(message.arg1);
                break;
        }
        return true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0036  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x003a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ void a(defpackage.dgq r4, java.lang.String r5, java.lang.String r6, java.util.List r7) {
        /*
            r0 = 1
            r4.a(r0, r0)
            if (r5 == 0) goto L_0x0032
            if (r6 == 0) goto L_0x0032
            java.io.File r1 = new java.io.File
            r1.<init>(r5)
            boolean r2 = r1.exists()
            if (r2 == 0) goto L_0x0032
            boolean r1 = r1.canRead()
            if (r1 == 0) goto L_0x0032
            java.io.File r1 = new java.io.File
            r1.<init>(r6)
            boolean r2 = r1.exists()
            if (r2 == 0) goto L_0x0032
            boolean r2 = r1.canRead()
            if (r2 == 0) goto L_0x0032
            boolean r1 = r1.canWrite()
            if (r1 == 0) goto L_0x0032
            r1 = 1
            goto L_0x0033
        L_0x0032:
            r1 = 0
        L_0x0033:
            r2 = 3
            if (r1 != 0) goto L_0x003a
            r4.a(r2, r0)
            return
        L_0x003a:
            r1 = 2
            r4.a(r0, r1)
            boolean r3 = r4.b(r5, r6, r7)
            if (r3 == 0) goto L_0x0048
            r4.a(r2, r1)
            return
        L_0x0048:
            r4.a(r0, r2)
            boolean r6 = r4.c(r5, r6, r7)
            boolean r1 = r4.d
            if (r1 != 0) goto L_0x0078
            if (r6 != 0) goto L_0x0059
            r4.a(r2, r2)
            return
        L_0x0059:
            java.lang.Class<dfm> r6 = defpackage.dfm.class
            defpackage.ank.a(r6)
            java.lang.Class<dhf> r6 = defpackage.dhf.class
            java.lang.Object r6 = defpackage.ank.a(r6)
            dhf r6 = (defpackage.dhf) r6
            if (r6 == 0) goto L_0x0074
            r6 = 4
            r4.a(r0, r6)
            a(r5, r7)
            r5 = 5
            r4.a(r0, r5)
            return
        L_0x0074:
            r5 = 6
            r4.a(r0, r5)
        L_0x0078:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dgq.a(dgq, java.lang.String, java.lang.String, java.util.List):void");
    }
}
