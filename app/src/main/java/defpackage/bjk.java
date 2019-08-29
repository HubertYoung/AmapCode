package defpackage;

import android.graphics.Bitmap;
import com.autonavi.common.imageloader.Dispatcher;
import com.autonavi.common.imageloader.IDownloader.ResponseException;
import com.autonavi.common.imageloader.ImageLoader;
import com.autonavi.common.imageloader.ImageLoader.LoadedFrom;
import com.autonavi.common.imageloader.ImageLoader.Priority;
import com.autonavi.common.imageloader.NetworkRequestHandler.ContentLengthException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/* renamed from: bjk reason: default package */
/* compiled from: BitmapHunter */
public final class bjk implements Runnable {
    private static final Object t = new Object();
    private static final ThreadLocal<StringBuilder> u = new ThreadLocal<StringBuilder>() {
        /* access modifiers changed from: protected */
        public final /* synthetic */ Object initialValue() {
            return new StringBuilder("ImageLoader-");
        }
    };
    private static final AtomicInteger v = new AtomicInteger();
    private static final bkb w = new bkb() {
        public final boolean a(bjz bjz) {
            return true;
        }

        public final a a(bjz bjz, int i) throws IOException {
            throw new IllegalStateException("Unrecognized type of request: ".concat(String.valueOf(bjz)));
        }
    };
    final int a = v.incrementAndGet();
    public final ImageLoader b;
    final Dispatcher c;
    final bjv d;
    final bkd e;
    public final String f;
    final bjz g;
    public final int h;
    public int i;
    public final bkb j;
    public bji k;
    public List<bji> l;
    public Bitmap m;
    public Future<?> n;
    public LoadedFrom o;
    public Exception p;
    int q;
    public int r;
    public Priority s;

    private static boolean a(boolean z, int i2, int i3, int i4, int i5) {
        return !z || (i4 != 0 && i2 > i4) || (i5 != 0 && i3 > i5);
    }

    private bjk(ImageLoader imageLoader, Dispatcher dispatcher, bjv bjv, bkd bkd, bji bji, bkb bkb) {
        this.b = imageLoader;
        this.c = dispatcher;
        this.d = bjv;
        this.e = bkd;
        this.k = bji;
        this.f = bji.i;
        this.g = bji.b;
        this.s = bji.b.s;
        this.h = bji.e;
        this.i = bji.f;
        this.j = bkb;
        this.r = bkb.a();
    }

    public final void run() {
        bjk bjk;
        String str;
        try {
            bjz bjz = this.g;
            if (bjz.d != null) {
                str = String.valueOf(bjz.d.getPath());
            } else {
                str = Integer.toHexString(bjz.e);
            }
            StringBuilder sb = u.get();
            sb.ensureCapacity(str.length() + 12);
            sb.replace(12, sb.length(), str);
            Thread.currentThread().setName(sb.toString());
            if (this.b.n) {
                bkh.a("Hunter", "executing", bkh.a(this));
            }
            this.m = c();
            if (this.m == null) {
                this.c.b(this);
            } else {
                Dispatcher dispatcher = this.c;
                dispatcher.i.sendMessage(dispatcher.i.obtainMessage(4, this));
            }
        } catch (ResponseException e2) {
            bjk = this;
            ResponseException responseException = e2;
            if (!responseException.a || responseException.b != 504) {
                bjk.p = responseException;
            }
            bjk.c.b(bjk);
        } catch (ContentLengthException e3) {
            bjk = this;
            bjk.p = e3;
            bjk.c.a(bjk);
        } catch (IOException e4) {
            bjk = this;
            bjk.p = e4;
            bjk.c.a(bjk);
        } catch (OutOfMemoryError e5) {
            OutOfMemoryError outOfMemoryError = e5;
            StringWriter stringWriter = new StringWriter();
            bkd bkd = this.e;
            int b2 = bkd.b.b();
            int a2 = bkd.b.a();
            long j2 = bkd.d;
            long j3 = bkd.e;
            long j4 = bkd.f;
            OutOfMemoryError outOfMemoryError2 = outOfMemoryError;
            long j5 = bkd.g;
            long j6 = bkd.h;
            long j7 = bkd.i;
            long j8 = bkd.j;
            long j9 = bkd.k;
            int i2 = bkd.l;
            int i3 = bkd.m;
            bke bke = r5;
            int i4 = i2;
            long j10 = j5;
            long j11 = j6;
            long j12 = j7;
            long j13 = j8;
            long j14 = j9;
            bke bke2 = new bke(b2, a2, j2, j3, j4, j10, j11, j12, j13, j14, i4, i3, bkd.n, System.currentTimeMillis());
            PrintWriter printWriter = new PrintWriter(stringWriter);
            printWriter.println("===============BEGIN ImageLoader STATS ===============");
            printWriter.println("Memory Cache Stats");
            printWriter.print("  Max Cache Size: ");
            bke bke3 = bke;
            printWriter.println(bke3.a);
            printWriter.print("  Cache Size: ");
            printWriter.println(bke3.b);
            printWriter.print("  Cache % Full: ");
            printWriter.println((int) Math.ceil((double) ((((float) bke3.b) / ((float) bke3.a)) * 100.0f)));
            printWriter.print("  Cache Hits: ");
            printWriter.println(bke3.c);
            printWriter.print("  Cache Misses: ");
            printWriter.println(bke3.d);
            printWriter.println("Network Stats");
            printWriter.print("  Download Count: ");
            printWriter.println(bke3.k);
            printWriter.print("  Total Download Size: ");
            printWriter.println(bke3.e);
            printWriter.print("  Average Download Size: ");
            printWriter.println(bke3.h);
            printWriter.println("Bitmap Stats");
            printWriter.print("  Total Bitmaps Decoded: ");
            printWriter.println(bke3.l);
            printWriter.print("  Total Bitmap Size: ");
            printWriter.println(bke3.f);
            printWriter.print("  Total Transformed Bitmaps: ");
            printWriter.println(bke3.m);
            printWriter.print("  Total Transformed Bitmap Size: ");
            printWriter.println(bke3.g);
            printWriter.print("  Average Bitmap Size: ");
            printWriter.println(bke3.i);
            printWriter.print("  Average Transformed Bitmap Size: ");
            printWriter.println(bke3.j);
            printWriter.println("===============END ImageLoader STATS ===============");
            printWriter.flush();
            bjk = this;
            bjk.p = new RuntimeException(stringWriter.toString(), outOfMemoryError2);
            bjk.c.b(bjk);
        } catch (Exception e6) {
            this.p = e6;
            this.c.b(this);
        } catch (Throwable th) {
            th = th;
            Throwable th2 = th;
            Thread.currentThread().setName("ImageLoader-Idle");
            throw th2;
        }
        Thread.currentThread().setName("ImageLoader-Idle");
        bjk = this;
        if (bjk.p != null && bjk.b != null && bjk.b.n) {
            bjk.p.printStackTrace();
            return;
        }
        return;
        Thread.currentThread().setName("ImageLoader-Idle");
        if (bjk.p != null) {
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:122:0x0343 A[Catch:{ all -> 0x02f5, all -> 0x0421 }] */
    /* JADX WARNING: Removed duplicated region for block: B:190:0x041c A[SYNTHETIC, Splitter:B:190:0x041c] */
    /* JADX WARNING: Removed duplicated region for block: B:200:0x042e A[Catch:{ all -> 0x0476 }] */
    /* JADX WARNING: Removed duplicated region for block: B:203:0x0444 A[Catch:{ all -> 0x0476 }] */
    /* JADX WARNING: Removed duplicated region for block: B:208:0x0465  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.graphics.Bitmap c() throws java.io.IOException {
        /*
            r45 = this;
            r1 = r45
            int r2 = r1.h
            boolean r2 = com.autonavi.common.imageloader.MemoryPolicy.shouldReadFromMemoryCache(r2)
            r3 = 0
            if (r2 == 0) goto L_0x0034
            bjv r2 = r1.d
            java.lang.String r4 = r1.f
            android.graphics.Bitmap r2 = r2.a(r4)
            if (r2 == 0) goto L_0x0035
            bkd r3 = r1.e
            r3.a()
            com.autonavi.common.imageloader.ImageLoader$LoadedFrom r3 = com.autonavi.common.imageloader.ImageLoader.LoadedFrom.MEMORY
            r1.o = r3
            com.autonavi.common.imageloader.ImageLoader r3 = r1.b
            boolean r3 = r3.n
            if (r3 == 0) goto L_0x0033
            java.lang.String r3 = "Hunter"
            java.lang.String r4 = "decoded"
            bjz r5 = r1.g
            java.lang.String r5 = r5.a()
            java.lang.String r6 = "from cache"
            defpackage.bkh.a(r3, r4, r5, r6)
        L_0x0033:
            return r2
        L_0x0034:
            r2 = r3
        L_0x0035:
            bjz r4 = r1.g
            int r5 = r1.r
            if (r5 != 0) goto L_0x0040
            com.autonavi.common.imageloader.NetworkPolicy r5 = com.autonavi.common.imageloader.NetworkPolicy.OFFLINE
            int r5 = r5.a
            goto L_0x0042
        L_0x0040:
            int r5 = r1.i
        L_0x0042:
            r4.c = r5
            bkb r4 = r1.j
            bjz r5 = r1.g
            int r6 = r1.i
            bkb$a r4 = r4.a(r5, r6)
            r5 = 1
            r6 = 0
            if (r4 == 0) goto L_0x00d5
            com.autonavi.common.imageloader.ImageLoader$LoadedFrom r2 = r4.a
            r1.o = r2
            int r2 = r4.d
            r1.q = r2
            android.graphics.Bitmap r2 = r4.b
            if (r2 != 0) goto L_0x00d5
            java.io.InputStream r2 = r4.c
            bjz r4 = r1.g     // Catch:{ all -> 0x00cf }
            bjw r7 = new bjw     // Catch:{ all -> 0x00cf }
            r7.<init>(r2)     // Catch:{ all -> 0x00cf }
            r8 = 1024(0x400, float:1.435E-42)
            long r8 = r7.a(r8)     // Catch:{ all -> 0x00cf }
            boolean r10 = defpackage.bkh.b(r7)     // Catch:{ all -> 0x00cf }
            boolean r11 = r4.q     // Catch:{ all -> 0x00cf }
            if (r11 == 0) goto L_0x007d
            int r11 = android.os.Build.VERSION.SDK_INT     // Catch:{ all -> 0x00cf }
            r12 = 21
            if (r11 >= r12) goto L_0x007d
            r11 = 1
            goto L_0x007e
        L_0x007d:
            r11 = 0
        L_0x007e:
            android.graphics.BitmapFactory$Options r12 = defpackage.bkb.c(r4)     // Catch:{ all -> 0x00cf }
            boolean r13 = defpackage.bkb.a(r12)     // Catch:{ all -> 0x00cf }
            r7.a(r8)     // Catch:{ all -> 0x00cf }
            if (r10 != 0) goto L_0x00b4
            if (r11 == 0) goto L_0x008e
            goto L_0x00b4
        L_0x008e:
            if (r13 == 0) goto L_0x00a1
            r7.a = r6     // Catch:{ all -> 0x00cf }
            android.graphics.BitmapFactory.decodeStream(r7, r3, r12)     // Catch:{ all -> 0x00cf }
            int r3 = r4.h     // Catch:{ all -> 0x00cf }
            int r10 = r4.i     // Catch:{ all -> 0x00cf }
            defpackage.bkb.a(r3, r10, r12, r4)     // Catch:{ all -> 0x00cf }
            r7.a(r8)     // Catch:{ all -> 0x00cf }
            r7.a = r5     // Catch:{ all -> 0x00cf }
        L_0x00a1:
            byte[] r3 = defpackage.bkh.a(r7)     // Catch:{ all -> 0x00cf }
            int r4 = r3.length     // Catch:{ all -> 0x00cf }
            android.graphics.Bitmap r3 = android.graphics.BitmapFactory.decodeByteArray(r3, r6, r4, r12)     // Catch:{ all -> 0x00cf }
            if (r3 != 0) goto L_0x00ca
            java.io.IOException r3 = new java.io.IOException     // Catch:{ all -> 0x00cf }
            java.lang.String r4 = "Failed to decode stream."
            r3.<init>(r4)     // Catch:{ all -> 0x00cf }
            throw r3     // Catch:{ all -> 0x00cf }
        L_0x00b4:
            byte[] r3 = defpackage.bkh.a(r7)     // Catch:{ all -> 0x00cf }
            if (r13 == 0) goto L_0x00c5
            int r7 = r3.length     // Catch:{ all -> 0x00cf }
            android.graphics.BitmapFactory.decodeByteArray(r3, r6, r7, r12)     // Catch:{ all -> 0x00cf }
            int r7 = r4.h     // Catch:{ all -> 0x00cf }
            int r8 = r4.i     // Catch:{ all -> 0x00cf }
            defpackage.bkb.a(r7, r8, r12, r4)     // Catch:{ all -> 0x00cf }
        L_0x00c5:
            int r4 = r3.length     // Catch:{ all -> 0x00cf }
            android.graphics.Bitmap r3 = android.graphics.BitmapFactory.decodeByteArray(r3, r6, r4, r12)     // Catch:{ all -> 0x00cf }
        L_0x00ca:
            defpackage.bkh.a(r2)
            r2 = r3
            goto L_0x00d5
        L_0x00cf:
            r0 = move-exception
            r3 = r0
            defpackage.bkh.a(r2)
            throw r3
        L_0x00d5:
            if (r2 == 0) goto L_0x0478
            com.autonavi.common.imageloader.ImageLoader r3 = r1.b
            boolean r3 = r3.n
            if (r3 == 0) goto L_0x00ea
            java.lang.String r3 = "Hunter"
            java.lang.String r4 = "decoded"
            bjz r7 = r1.g
            java.lang.String r7 = r7.a()
            defpackage.bkh.a(r3, r4, r7)
        L_0x00ea:
            com.autonavi.common.imageloader.ImageLoader$LoadedFrom r3 = com.autonavi.common.imageloader.ImageLoader.LoadedFrom.NETWORK
            com.autonavi.common.imageloader.ImageLoader$LoadedFrom r4 = r1.o
            if (r3 != r4) goto L_0x0129
            int r3 = r1.i
            boolean r3 = com.autonavi.common.imageloader.NetworkPolicy.shouldWriteToDiskCache(r3)
            if (r3 == 0) goto L_0x0129
            com.autonavi.common.imageloader.ImageLoader r3 = r1.b
            bjr r3 = r3.l
            if (r3 == 0) goto L_0x0129
            bjz r3 = r1.g
            android.net.Uri r3 = r3.d
            java.lang.String r3 = r3.toString()
            java.lang.String r3 = defpackage.bkh.a(r3)
            com.autonavi.common.imageloader.ImageLoader r4 = r1.b
            bjr r4 = r4.l
            boolean r3 = r4.a(r3, r2)
            java.lang.String r4 = "Hunter"
            java.lang.String r7 = "disk_cache"
            bjz r8 = r1.g
            java.lang.String r8 = r8.a()
            java.lang.String r9 = "Disk cache "
            java.lang.String r3 = java.lang.String.valueOf(r3)
            java.lang.String r3 = r9.concat(r3)
            defpackage.bkh.a(r4, r7, r8, r3)
        L_0x0129:
            bkd r3 = r1.e
            r4 = 2
            r3.a(r2, r4)
            bjz r3 = r1.g
            boolean r7 = r3.d()
            if (r7 != 0) goto L_0x0140
            boolean r3 = r3.e()
            if (r3 == 0) goto L_0x013e
            goto L_0x0140
        L_0x013e:
            r3 = 0
            goto L_0x0141
        L_0x0140:
            r3 = 1
        L_0x0141:
            if (r3 != 0) goto L_0x0147
            int r3 = r1.q
            if (r3 == 0) goto L_0x0478
        L_0x0147:
            java.lang.Object r3 = t
            monitor-enter(r3)
            bjz r7 = r1.g     // Catch:{ all -> 0x0470 }
            boolean r7 = r7.d()     // Catch:{ all -> 0x0470 }
            if (r7 != 0) goto L_0x0161
            int r7 = r1.q     // Catch:{ all -> 0x015b }
            if (r7 == 0) goto L_0x0157
            goto L_0x0161
        L_0x0157:
            r39 = r3
            goto L_0x043c
        L_0x015b:
            r0 = move-exception
            r2 = r0
            r39 = r3
            goto L_0x0474
        L_0x0161:
            bjz r7 = r1.g     // Catch:{ all -> 0x0470 }
            int r8 = r1.q     // Catch:{ all -> 0x0470 }
            int r9 = r2.getWidth()     // Catch:{ all -> 0x0470 }
            int r10 = r2.getHeight()     // Catch:{ all -> 0x0470 }
            boolean r11 = r7.l     // Catch:{ all -> 0x0470 }
            android.graphics.Matrix r12 = new android.graphics.Matrix     // Catch:{ all -> 0x0470 }
            r12.<init>()     // Catch:{ all -> 0x0470 }
            boolean r13 = r7.d()     // Catch:{ all -> 0x0470 }
            if (r13 != 0) goto L_0x0186
            if (r8 == 0) goto L_0x017d
            goto L_0x0186
        L_0x017d:
            r18 = r2
            r39 = r3
            r2 = r9
            r3 = r10
            r9 = r12
            goto L_0x040d
        L_0x0186:
            int r13 = r7.h     // Catch:{ all -> 0x0470 }
            int r14 = r7.i     // Catch:{ all -> 0x0470 }
            float r15 = r7.m     // Catch:{ all -> 0x0470 }
            r16 = 0
            int r16 = (r15 > r16 ? 1 : (r15 == r16 ? 0 : -1))
            if (r16 == 0) goto L_0x02fa
            double r13 = (double) r15     // Catch:{ all -> 0x0470 }
            double r5 = java.lang.Math.toRadians(r13)     // Catch:{ all -> 0x0470 }
            double r5 = java.lang.Math.cos(r5)     // Catch:{ all -> 0x0470 }
            double r13 = java.lang.Math.toRadians(r13)     // Catch:{ all -> 0x0470 }
            double r13 = java.lang.Math.sin(r13)     // Catch:{ all -> 0x0470 }
            boolean r4 = r7.p     // Catch:{ all -> 0x0470 }
            if (r4 == 0) goto L_0x0265
            float r4 = r7.n     // Catch:{ all -> 0x015b }
            float r1 = r7.o     // Catch:{ all -> 0x025f }
            r12.setRotate(r15, r4, r1)     // Catch:{ all -> 0x025f }
            float r1 = r7.n     // Catch:{ all -> 0x025f }
            r18 = r2
            double r1 = (double) r1     // Catch:{ all -> 0x025f }
            r15 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            double r15 = r15 - r5
            double r1 = r1 * r15
            float r4 = r7.o     // Catch:{ all -> 0x025f }
            r19 = r10
            r20 = r11
            double r10 = (double) r4     // Catch:{ all -> 0x025f }
            double r10 = r10 * r13
            double r1 = r1 + r10
            float r4 = r7.o     // Catch:{ all -> 0x025f }
            double r10 = (double) r4     // Catch:{ all -> 0x025f }
            double r10 = r10 * r15
            float r4 = r7.n     // Catch:{ all -> 0x025f }
            r21 = r8
            r22 = r9
            double r8 = (double) r4     // Catch:{ all -> 0x025f }
            double r8 = r8 * r13
            double r10 = r10 - r8
            int r4 = r7.h     // Catch:{ all -> 0x025f }
            double r8 = (double) r4     // Catch:{ all -> 0x025f }
            double r8 = r8 * r5
            double r8 = r8 + r1
            int r4 = r7.h     // Catch:{ all -> 0x025f }
            r23 = r8
            double r8 = (double) r4     // Catch:{ all -> 0x025f }
            double r8 = r8 * r13
            double r8 = r8 + r10
            int r4 = r7.h     // Catch:{ all -> 0x025f }
            r25 = r8
            double r8 = (double) r4     // Catch:{ all -> 0x025f }
            double r8 = r8 * r5
            double r8 = r8 + r1
            int r4 = r7.i     // Catch:{ all -> 0x025f }
            r27 = r1
            double r1 = (double) r4     // Catch:{ all -> 0x025f }
            double r1 = r1 * r13
            double r8 = r8 - r1
            int r1 = r7.h     // Catch:{ all -> 0x025f }
            double r1 = (double) r1     // Catch:{ all -> 0x025f }
            double r1 = r1 * r13
            double r1 = r1 + r10
            int r4 = r7.i     // Catch:{ all -> 0x025f }
            r29 = r8
            double r8 = (double) r4     // Catch:{ all -> 0x025f }
            double r8 = r8 * r5
            double r1 = r1 + r8
            int r4 = r7.i     // Catch:{ all -> 0x025f }
            double r8 = (double) r4     // Catch:{ all -> 0x025f }
            double r8 = r8 * r13
            double r8 = r27 - r8
            int r4 = r7.i     // Catch:{ all -> 0x025f }
            double r13 = (double) r4     // Catch:{ all -> 0x025f }
            double r13 = r13 * r5
            double r13 = r13 + r10
            r31 = r7
            r32 = r12
            r33 = r13
            r6 = r23
            r4 = r27
            double r12 = java.lang.Math.max(r4, r6)     // Catch:{ all -> 0x025f }
            r14 = r29
            double r12 = java.lang.Math.max(r14, r12)     // Catch:{ all -> 0x025f }
            double r12 = java.lang.Math.max(r8, r12)     // Catch:{ all -> 0x025f }
            double r4 = java.lang.Math.min(r4, r6)     // Catch:{ all -> 0x025f }
            double r4 = java.lang.Math.min(r14, r4)     // Catch:{ all -> 0x025f }
            double r4 = java.lang.Math.min(r8, r4)     // Catch:{ all -> 0x025f }
            r8 = r25
            double r6 = java.lang.Math.max(r10, r8)     // Catch:{ all -> 0x025f }
            double r6 = java.lang.Math.max(r1, r6)     // Catch:{ all -> 0x025f }
            r14 = r33
            double r6 = java.lang.Math.max(r14, r6)     // Catch:{ all -> 0x025f }
            double r8 = java.lang.Math.min(r10, r8)     // Catch:{ all -> 0x025f }
            double r1 = java.lang.Math.min(r1, r8)     // Catch:{ all -> 0x025f }
            double r1 = java.lang.Math.min(r14, r1)     // Catch:{ all -> 0x025f }
            r8 = 0
            double r12 = r12 - r4
            double r4 = java.lang.Math.floor(r12)     // Catch:{ all -> 0x025f }
            int r13 = (int) r4     // Catch:{ all -> 0x025f }
            double r6 = r6 - r1
            double r1 = java.lang.Math.floor(r6)     // Catch:{ all -> 0x025f }
            int r14 = (int) r1
            r39 = r3
            r40 = r31
            r41 = r32
            goto L_0x030a
        L_0x025f:
            r0 = move-exception
            r2 = r0
            r39 = r3
            goto L_0x0423
        L_0x0265:
            r18 = r2
            r31 = r7
            r21 = r8
            r22 = r9
            r19 = r10
            r20 = r11
            r1 = r12
            r1.setRotate(r15)     // Catch:{ all -> 0x02f5 }
            r2 = r31
            int r4 = r2.h     // Catch:{ all -> 0x02f5 }
            double r7 = (double) r4     // Catch:{ all -> 0x02f5 }
            double r7 = r7 * r5
            int r4 = r2.h     // Catch:{ all -> 0x02f5 }
            double r9 = (double) r4     // Catch:{ all -> 0x02f5 }
            double r9 = r9 * r13
            int r4 = r2.h     // Catch:{ all -> 0x02f5 }
            double r11 = (double) r4     // Catch:{ all -> 0x02f5 }
            double r11 = r11 * r5
            int r4 = r2.i     // Catch:{ all -> 0x02f5 }
            r35 = r9
            double r9 = (double) r4     // Catch:{ all -> 0x02f5 }
            double r9 = r9 * r13
            double r11 = r11 - r9
            int r4 = r2.h     // Catch:{ all -> 0x02f5 }
            double r9 = (double) r4     // Catch:{ all -> 0x02f5 }
            double r9 = r9 * r13
            int r4 = r2.i     // Catch:{ all -> 0x02f5 }
            r37 = r11
            double r11 = (double) r4     // Catch:{ all -> 0x02f5 }
            double r11 = r11 * r5
            double r9 = r9 + r11
            int r4 = r2.i     // Catch:{ all -> 0x02f5 }
            double r11 = (double) r4     // Catch:{ all -> 0x02f5 }
            double r11 = r11 * r13
            double r11 = -r11
            int r4 = r2.i     // Catch:{ all -> 0x02f5 }
            double r13 = (double) r4
            double r13 = r13 * r5
            r4 = 0
            r40 = r2
            r39 = r3
            double r2 = java.lang.Math.max(r4, r7)     // Catch:{ all -> 0x0421 }
            r4 = r37
            double r2 = java.lang.Math.max(r4, r2)     // Catch:{ all -> 0x0421 }
            double r2 = java.lang.Math.max(r11, r2)     // Catch:{ all -> 0x0421 }
            r41 = r1
            r42 = r2
            r1 = 0
            double r6 = java.lang.Math.min(r1, r7)     // Catch:{ all -> 0x0421 }
            double r3 = java.lang.Math.min(r4, r6)     // Catch:{ all -> 0x0421 }
            double r3 = java.lang.Math.min(r11, r3)     // Catch:{ all -> 0x0421 }
            r5 = r35
            double r7 = java.lang.Math.max(r1, r5)     // Catch:{ all -> 0x0421 }
            double r7 = java.lang.Math.max(r9, r7)     // Catch:{ all -> 0x0421 }
            double r7 = java.lang.Math.max(r13, r7)     // Catch:{ all -> 0x0421 }
            double r1 = java.lang.Math.min(r1, r5)     // Catch:{ all -> 0x0421 }
            double r1 = java.lang.Math.min(r9, r1)     // Catch:{ all -> 0x0421 }
            double r1 = java.lang.Math.min(r13, r1)     // Catch:{ all -> 0x0421 }
            r5 = 0
            double r3 = r42 - r3
            double r3 = java.lang.Math.floor(r3)     // Catch:{ all -> 0x0421 }
            int r13 = (int) r3     // Catch:{ all -> 0x0421 }
            double r7 = r7 - r1
            double r1 = java.lang.Math.floor(r7)     // Catch:{ all -> 0x0421 }
            int r14 = (int) r1     // Catch:{ all -> 0x0421 }
            goto L_0x030a
        L_0x02f5:
            r0 = move-exception
            r39 = r3
            goto L_0x0422
        L_0x02fa:
            r18 = r2
            r39 = r3
            r40 = r7
            r21 = r8
            r22 = r9
            r19 = r10
            r20 = r11
            r41 = r12
        L_0x030a:
            if (r21 == 0) goto L_0x034a
            r6 = 90
            r1 = 270(0x10e, float:3.78E-43)
            switch(r21) {
                case 3: goto L_0x031b;
                case 4: goto L_0x031b;
                case 5: goto L_0x0318;
                case 6: goto L_0x0318;
                case 7: goto L_0x0315;
                case 8: goto L_0x0315;
                default: goto L_0x0313;
            }     // Catch:{ all -> 0x0421 }
        L_0x0313:
            r2 = 0
            goto L_0x031d
        L_0x0315:
            r2 = 270(0x10e, float:3.78E-43)
            goto L_0x031d
        L_0x0318:
            r2 = 90
            goto L_0x031d
        L_0x031b:
            r2 = 180(0xb4, float:2.52E-43)
        L_0x031d:
            r3 = r21
            r4 = 2
            if (r3 == r4) goto L_0x032a
            r4 = 7
            if (r3 == r4) goto L_0x032a
            switch(r3) {
                case 4: goto L_0x032a;
                case 5: goto L_0x032a;
                default: goto L_0x0328;
            }     // Catch:{ all -> 0x0421 }
        L_0x0328:
            r5 = 1
            goto L_0x032b
        L_0x032a:
            r5 = -1
        L_0x032b:
            if (r2 == 0) goto L_0x033e
            float r3 = (float) r2     // Catch:{ all -> 0x0421 }
            r9 = r41
            r9.preRotate(r3)     // Catch:{ all -> 0x0421 }
            if (r2 == r6) goto L_0x0337
            if (r2 != r1) goto L_0x0340
        L_0x0337:
            r1 = 1
            r44 = r14
            r14 = r13
            r13 = r44
            goto L_0x0341
        L_0x033e:
            r9 = r41
        L_0x0340:
            r1 = 1
        L_0x0341:
            if (r5 == r1) goto L_0x034c
            float r1 = (float) r5     // Catch:{ all -> 0x0421 }
            r2 = 1065353216(0x3f800000, float:1.0)
            r9.postScale(r1, r2)     // Catch:{ all -> 0x0421 }
            goto L_0x034c
        L_0x034a:
            r9 = r41
        L_0x034c:
            r1 = r40
            boolean r2 = r1.j     // Catch:{ all -> 0x046c }
            if (r2 == 0) goto L_0x03be
            if (r13 == 0) goto L_0x035c
            float r1 = (float) r13
            r2 = r22
            float r3 = (float) r2
            float r1 = r1 / r3
            r3 = r19
            goto L_0x0363
        L_0x035c:
            r2 = r22
            float r1 = (float) r14
            r3 = r19
            float r4 = (float) r3
            float r1 = r1 / r4
        L_0x0363:
            if (r14 == 0) goto L_0x0369
            float r4 = (float) r14
            float r5 = (float) r3
        L_0x0367:
            float r4 = r4 / r5
            goto L_0x036c
        L_0x0369:
            float r4 = (float) r13
            float r5 = (float) r2
            goto L_0x0367
        L_0x036c:
            int r5 = (r1 > r4 ? 1 : (r1 == r4 ? 0 : -1))
            if (r5 <= 0) goto L_0x0388
            float r5 = (float) r3
            float r4 = r4 / r1
            float r5 = r5 * r4
            double r4 = (double) r5
            double r4 = java.lang.Math.ceil(r4)     // Catch:{ all -> 0x0421 }
            int r10 = (int) r4     // Catch:{ all -> 0x0421 }
            int r4 = r3 - r10
            r5 = 2
            int r4 = r4 / r5
            float r5 = (float) r14     // Catch:{ all -> 0x0421 }
            float r6 = (float) r10     // Catch:{ all -> 0x0421 }
            float r5 = r5 / r6
            r7 = r2
            r17 = r4
            r4 = r20
            r6 = 0
            goto L_0x03b0
        L_0x0388:
            int r5 = (r1 > r4 ? 1 : (r1 == r4 ? 0 : -1))
            if (r5 >= 0) goto L_0x03a7
            float r5 = (float) r2     // Catch:{ all -> 0x0421 }
            float r1 = r1 / r4
            float r5 = r5 * r1
            double r5 = (double) r5     // Catch:{ all -> 0x0421 }
            double r5 = java.lang.Math.ceil(r5)     // Catch:{ all -> 0x0421 }
            int r1 = (int) r5     // Catch:{ all -> 0x0421 }
            int r5 = r2 - r1
            r6 = 2
            int r5 = r5 / r6
            float r6 = (float) r13     // Catch:{ all -> 0x0421 }
            float r7 = (float) r1     // Catch:{ all -> 0x0421 }
            float r6 = r6 / r7
            r7 = r1
            r10 = r3
            r1 = r6
            r17 = 0
            r6 = r5
            r5 = r4
            r4 = r20
            goto L_0x03b0
        L_0x03a7:
            r7 = r2
            r10 = r3
            r1 = r4
            r5 = r1
            r4 = r20
            r6 = 0
            r17 = 0
        L_0x03b0:
            boolean r2 = a(r4, r2, r3, r13, r14)     // Catch:{ all -> 0x0421 }
            if (r2 == 0) goto L_0x03b9
            r9.preScale(r1, r5)     // Catch:{ all -> 0x0421 }
        L_0x03b9:
            r5 = r6
            r8 = r10
            r6 = r17
            goto L_0x0411
        L_0x03be:
            r3 = r19
            r4 = r20
            r2 = r22
            boolean r1 = r1.k     // Catch:{ all -> 0x046c }
            if (r1 == 0) goto L_0x03ea
            if (r13 == 0) goto L_0x03ce
            float r1 = (float) r13
            float r5 = (float) r2
        L_0x03cc:
            float r1 = r1 / r5
            goto L_0x03d1
        L_0x03ce:
            float r1 = (float) r14
            float r5 = (float) r3
            goto L_0x03cc
        L_0x03d1:
            if (r14 == 0) goto L_0x03d7
            float r5 = (float) r14
            float r6 = (float) r3
        L_0x03d5:
            float r5 = r5 / r6
            goto L_0x03da
        L_0x03d7:
            float r5 = (float) r13
            float r6 = (float) r2
            goto L_0x03d5
        L_0x03da:
            int r6 = (r1 > r5 ? 1 : (r1 == r5 ? 0 : -1))
            if (r6 >= 0) goto L_0x03df
            goto L_0x03e0
        L_0x03df:
            r1 = r5
        L_0x03e0:
            boolean r4 = a(r4, r2, r3, r13, r14)     // Catch:{ all -> 0x0421 }
            if (r4 == 0) goto L_0x040d
            r9.preScale(r1, r1)     // Catch:{ all -> 0x0421 }
            goto L_0x040d
        L_0x03ea:
            if (r13 != 0) goto L_0x03ee
            if (r14 == 0) goto L_0x040d
        L_0x03ee:
            if (r13 != r2) goto L_0x03f2
            if (r14 == r3) goto L_0x040d
        L_0x03f2:
            if (r13 == 0) goto L_0x03f8
            float r1 = (float) r13
            float r5 = (float) r2
        L_0x03f6:
            float r1 = r1 / r5
            goto L_0x03fb
        L_0x03f8:
            float r1 = (float) r14
            float r5 = (float) r3
            goto L_0x03f6
        L_0x03fb:
            if (r14 == 0) goto L_0x0401
            float r5 = (float) r14
            float r6 = (float) r3
        L_0x03ff:
            float r5 = r5 / r6
            goto L_0x0404
        L_0x0401:
            float r5 = (float) r13
            float r6 = (float) r2
            goto L_0x03ff
        L_0x0404:
            boolean r4 = a(r4, r2, r3, r13, r14)     // Catch:{ all -> 0x046c }
            if (r4 == 0) goto L_0x040d
            r9.preScale(r1, r5)     // Catch:{ all -> 0x0421 }
        L_0x040d:
            r7 = r2
            r8 = r3
            r5 = 0
            r6 = 0
        L_0x0411:
            r10 = 1
            r4 = r18
            android.graphics.Bitmap r1 = android.graphics.Bitmap.createBitmap(r4, r5, r6, r7, r8, r9, r10)     // Catch:{ all -> 0x046c }
            r2 = r18
            if (r1 == r2) goto L_0x0426
            r2.recycle()     // Catch:{ all -> 0x0421 }
            r2 = r1
            goto L_0x0426
        L_0x0421:
            r0 = move-exception
        L_0x0422:
            r2 = r0
        L_0x0423:
            r1 = r45
            goto L_0x0474
        L_0x0426:
            r1 = r45
            com.autonavi.common.imageloader.ImageLoader r3 = r1.b     // Catch:{ all -> 0x0476 }
            boolean r3 = r3.n     // Catch:{ all -> 0x0476 }
            if (r3 == 0) goto L_0x043c
            java.lang.String r3 = "Hunter"
            java.lang.String r4 = "transformed"
            bjz r5 = r1.g     // Catch:{ all -> 0x0476 }
            java.lang.String r5 = r5.a()     // Catch:{ all -> 0x0476 }
            defpackage.bkh.a(r3, r4, r5)     // Catch:{ all -> 0x0476 }
        L_0x043c:
            bjz r3 = r1.g     // Catch:{ all -> 0x0476 }
            boolean r3 = r3.e()     // Catch:{ all -> 0x0476 }
            if (r3 == 0) goto L_0x0462
            bjz r3 = r1.g     // Catch:{ all -> 0x0476 }
            java.util.List<bjo> r3 = r3.g     // Catch:{ all -> 0x0476 }
            android.graphics.Bitmap r2 = a(r3, r2)     // Catch:{ all -> 0x0476 }
            com.autonavi.common.imageloader.ImageLoader r3 = r1.b     // Catch:{ all -> 0x0476 }
            boolean r3 = r3.n     // Catch:{ all -> 0x0476 }
            if (r3 == 0) goto L_0x0462
            java.lang.String r3 = "Hunter"
            java.lang.String r4 = "transformed"
            bjz r5 = r1.g     // Catch:{ all -> 0x0476 }
            java.lang.String r5 = r5.a()     // Catch:{ all -> 0x0476 }
            java.lang.String r6 = "from custom transformations"
            defpackage.bkh.a(r3, r4, r5, r6)     // Catch:{ all -> 0x0476 }
        L_0x0462:
            monitor-exit(r39)     // Catch:{ all -> 0x0476 }
            if (r2 == 0) goto L_0x0478
            bkd r3 = r1.e
            r4 = 3
            r3.a(r2, r4)
            goto L_0x0478
        L_0x046c:
            r0 = move-exception
            r1 = r45
            goto L_0x0473
        L_0x0470:
            r0 = move-exception
            r39 = r3
        L_0x0473:
            r2 = r0
        L_0x0474:
            monitor-exit(r39)     // Catch:{ all -> 0x0476 }
            throw r2
        L_0x0476:
            r0 = move-exception
            goto L_0x0473
        L_0x0478:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.bjk.c():android.graphics.Bitmap");
    }

    public final void a(bji bji) {
        boolean z;
        boolean z2 = true;
        if (this.k == bji) {
            this.k = null;
            z = true;
        } else {
            z = this.l != null ? this.l.remove(bji) : false;
        }
        if (z && bji.b.s == this.s) {
            Priority priority = Priority.LOW;
            boolean z3 = this.l != null && !this.l.isEmpty();
            if (this.k == null && !z3) {
                z2 = false;
            }
            if (z2) {
                if (this.k != null) {
                    priority = this.k.b.s;
                }
                if (z3) {
                    int size = this.l.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        Priority priority2 = this.l.get(i2).b.s;
                        if (priority2.ordinal() > priority.ordinal()) {
                            priority = priority2;
                        }
                    }
                }
            }
            this.s = priority;
        }
        if (this.b.n) {
            bkh.a("Hunter", "removed", bji.b.a(), bkh.a(this, (String) "from "));
        }
    }

    public final boolean a() {
        if (this.k != null || ((this.l != null && !this.l.isEmpty()) || this.n == null || !this.n.cancel(false))) {
            return false;
        }
        return true;
    }

    public final boolean b() {
        return this.n != null && this.n.isCancelled();
    }

    private static Bitmap a(List<bjo> list, Bitmap bitmap) {
        int size = list.size();
        int i2 = 0;
        while (i2 < size) {
            final bjo bjo = list.get(i2);
            try {
                Bitmap a2 = bjo.a(bitmap);
                if (a2 == null) {
                    final StringBuilder sb = new StringBuilder("DrawableFactory ");
                    sb.append(bjo.a());
                    sb.append(" returned null after ");
                    sb.append(i2);
                    sb.append(" previous transformation(s).\n\nDrawableFactory list:\n");
                    for (bjo a3 : list) {
                        sb.append(a3.a());
                        sb.append(10);
                    }
                    ImageLoader.a.post(new Runnable() {
                        public final void run() {
                            throw new NullPointerException(sb.toString());
                        }
                    });
                    return null;
                } else if (a2 == bitmap && bitmap.isRecycled()) {
                    ImageLoader.a.post(new Runnable() {
                        public final void run() {
                            StringBuilder sb = new StringBuilder("DrawableFactory ");
                            sb.append(bjo.a());
                            sb.append(" returned input Bitmap but recycled it.");
                            throw new IllegalStateException(sb.toString());
                        }
                    });
                    return null;
                } else if (a2 == bitmap || bitmap.isRecycled()) {
                    i2++;
                    bitmap = a2;
                } else {
                    ImageLoader.a.post(new Runnable() {
                        public final void run() {
                            StringBuilder sb = new StringBuilder("DrawableFactory ");
                            sb.append(bjo.a());
                            sb.append(" mutated input Bitmap but failed to recycle the original.");
                            throw new IllegalStateException(sb.toString());
                        }
                    });
                    return null;
                }
            } catch (RuntimeException e2) {
                ImageLoader.a.post(new Runnable() {
                    public final void run() {
                        StringBuilder sb = new StringBuilder("DrawableFactory ");
                        sb.append(bjo.a());
                        sb.append(" crashed with exception.");
                        throw new RuntimeException(sb.toString(), e2);
                    }
                });
                return null;
            }
        }
        return bitmap;
    }

    public static bjk a(ImageLoader imageLoader, Dispatcher dispatcher, bjv bjv, bkd bkd, bji bji) {
        bjz bjz = bji.b;
        List<bkb> list = imageLoader.c;
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            bkb bkb = list.get(i2);
            if (bkb.a(bjz)) {
                bjk bjk = new bjk(imageLoader, dispatcher, bjv, bkd, bji, bkb);
                return bjk;
            }
        }
        bjk bjk2 = new bjk(imageLoader, dispatcher, bjv, bkd, bji, w);
        return bjk2;
    }
}
