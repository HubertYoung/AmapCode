package defpackage;

import android.content.Context;
import com.danikula.videocache.ProxyCacheException;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* renamed from: etm reason: default package */
/* compiled from: HttpProxyCacheServer */
public final class etm {
    final ExecutorService a;
    final ServerSocket b;
    public final int c;
    public final etj d;
    public final etq e;
    private final Object f;
    private final Map<String, etn> g;
    private final Thread h;

    /* renamed from: etm$a */
    /* compiled from: HttpProxyCacheServer */
    public static final class a {
        public File a;
        public etz b = new euc();
        public etx c = new eud(IjkMediaMeta.AV_CH_STEREO_LEFT);
        public eui d;
        public euf e = new eue();

        public a(Context context) {
            this.d = new eug(context);
            this.a = new File(etw.a(context), "video-cache");
        }
    }

    /* renamed from: etm$b */
    /* compiled from: HttpProxyCacheServer */
    final class b implements Runnable {
        private final Socket b;

        public b(Socket socket) {
            this.b = socket;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:10:0x003b, code lost:
            r2 = r0;
            r1 = r3;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:119:0x01d1, code lost:
            r1 = r3;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:7:0x0035, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:8:0x0036, code lost:
            r2 = r0;
            r1 = r3;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x003a, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Removed duplicated region for block: B:120:? A[ExcHandler: SocketException (unused java.net.SocketException), SYNTHETIC, Splitter:B:1:0x0006] */
        /* JADX WARNING: Removed duplicated region for block: B:75:0x017f A[ADDED_TO_REGION, Catch:{ all -> 0x00c0 }] */
        /* JADX WARNING: Removed duplicated region for block: B:83:0x0191 A[Catch:{ all -> 0x00c0 }] */
        /* JADX WARNING: Removed duplicated region for block: B:84:0x0195 A[SYNTHETIC, Splitter:B:84:0x0195] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void run() {
            /*
                r20 = this;
                r1 = r20
                etm r2 = defpackage.etm.this
                java.net.Socket r3 = r1.b
                java.io.InputStream r4 = r3.getInputStream()     // Catch:{ SocketException -> 0x01d1, ProxyCacheException | IOException -> 0x01bd, all -> 0x01b9 }
                etk r4 = defpackage.etk.a(r4)     // Catch:{ SocketException -> 0x01d1, ProxyCacheException | IOException -> 0x01bd, all -> 0x01b9 }
                java.lang.String r5 = r4.a     // Catch:{ SocketException -> 0x01d1, ProxyCacheException | IOException -> 0x01bd, all -> 0x01b9 }
                java.lang.String r5 = defpackage.ett.b(r5)     // Catch:{ SocketException -> 0x01d1, ProxyCacheException | IOException -> 0x01bd, all -> 0x01b9 }
                java.lang.String r6 = "ping"
                boolean r6 = r6.equals(r5)     // Catch:{ SocketException -> 0x01d1, ProxyCacheException | IOException -> 0x01bd, all -> 0x01b9 }
                if (r6 == 0) goto L_0x003f
                java.io.OutputStream r2 = r3.getOutputStream()     // Catch:{ SocketException -> 0x01d1, ProxyCacheException | IOException -> 0x003a, all -> 0x0035 }
                java.lang.String r4 = "HTTP/1.1 200 OK\n\n"
                byte[] r4 = r4.getBytes()     // Catch:{ SocketException -> 0x01d1, ProxyCacheException | IOException -> 0x003a, all -> 0x0035 }
                r2.write(r4)     // Catch:{ SocketException -> 0x01d1, ProxyCacheException | IOException -> 0x003a, all -> 0x0035 }
                java.lang.String r4 = "ping ok"
                byte[] r4 = r4.getBytes()     // Catch:{ SocketException -> 0x01d1, ProxyCacheException | IOException -> 0x003a, all -> 0x0035 }
                r2.write(r4)     // Catch:{ SocketException -> 0x01d1, ProxyCacheException | IOException -> 0x003a, all -> 0x0035 }
                r1 = r3
                goto L_0x019d
            L_0x0035:
                r0 = move-exception
                r2 = r0
                r1 = r3
                goto L_0x01cd
            L_0x003a:
                r0 = move-exception
                r2 = r0
                r1 = r3
                goto L_0x01c0
            L_0x003f:
                etn r2 = r2.c(r5)     // Catch:{ SocketException -> 0x01d1, ProxyCacheException | IOException -> 0x01bd, all -> 0x01b9 }
                r2.a()     // Catch:{ SocketException -> 0x01d1, ProxyCacheException | IOException -> 0x01bd, all -> 0x01b9 }
                java.util.concurrent.atomic.AtomicInteger r5 = r2.a     // Catch:{ all -> 0x01b0 }
                r5.incrementAndGet()     // Catch:{ all -> 0x01b0 }
                etl r5 = r2.b     // Catch:{ all -> 0x01b0 }
                java.io.BufferedOutputStream r6 = new java.io.BufferedOutputStream     // Catch:{ all -> 0x01b0 }
                java.io.OutputStream r7 = r3.getOutputStream()     // Catch:{ all -> 0x01b0 }
                r6.<init>(r7)     // Catch:{ all -> 0x01b0 }
                eto r7 = r5.a     // Catch:{ all -> 0x01b0 }
                java.lang.String r7 = r7.c()     // Catch:{ all -> 0x01b0 }
                boolean r8 = android.text.TextUtils.isEmpty(r7)     // Catch:{ all -> 0x01b0 }
                r9 = 1
                r8 = r8 ^ r9
                ety r10 = r5.b     // Catch:{ all -> 0x01b0 }
                boolean r10 = r10.d()     // Catch:{ all -> 0x01b0 }
                if (r10 == 0) goto L_0x0071
                ety r10 = r5.b     // Catch:{ all -> 0x01b0 }
                long r10 = r10.a()     // Catch:{ all -> 0x01b0 }
                goto L_0x0077
            L_0x0071:
                eto r10 = r5.a     // Catch:{ all -> 0x01b0 }
                long r10 = r10.a()     // Catch:{ all -> 0x01b0 }
            L_0x0077:
                r12 = 0
                int r14 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
                if (r14 < 0) goto L_0x007f
                r14 = 1
                goto L_0x0080
            L_0x007f:
                r14 = 0
            L_0x0080:
                boolean r12 = r4.c     // Catch:{ all -> 0x01b0 }
                if (r12 == 0) goto L_0x008b
                long r12 = r4.b     // Catch:{ all -> 0x01b0 }
                r17 = 0
                long r12 = r10 - r12
                goto L_0x008c
            L_0x008b:
                r12 = r10
            L_0x008c:
                if (r14 == 0) goto L_0x0094
                boolean r15 = r4.c     // Catch:{ all -> 0x01b0 }
                if (r15 == 0) goto L_0x0094
                r15 = 1
                goto L_0x0095
            L_0x0094:
                r15 = 0
            L_0x0095:
                java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ all -> 0x01b0 }
                r9.<init>()     // Catch:{ all -> 0x01b0 }
                boolean r1 = r4.c     // Catch:{ all -> 0x01b0 }
                if (r1 == 0) goto L_0x00a1
                java.lang.String r1 = "HTTP/1.1 206 PARTIAL CONTENT\n"
                goto L_0x00a3
            L_0x00a1:
                java.lang.String r1 = "HTTP/1.1 200 OK\n"
            L_0x00a3:
                r9.append(r1)     // Catch:{ all -> 0x01b0 }
                java.lang.String r1 = "Accept-Ranges: bytes\n"
                r9.append(r1)     // Catch:{ all -> 0x01b0 }
                if (r14 == 0) goto L_0x00c6
                java.lang.String r1 = "Content-Length: %d\n"
                r19 = r3
                r14 = 1
                java.lang.Object[] r3 = new java.lang.Object[r14]     // Catch:{ all -> 0x00c0 }
                java.lang.Long r12 = java.lang.Long.valueOf(r12)     // Catch:{ all -> 0x00c0 }
                r13 = 0
                r3[r13] = r12     // Catch:{ all -> 0x00c0 }
                java.lang.String r1 = defpackage.etl.a(r1, r3)     // Catch:{ all -> 0x00c0 }
                goto L_0x00ca
            L_0x00c0:
                r0 = move-exception
                r3 = r0
                r1 = r19
                goto L_0x01b3
            L_0x00c6:
                r19 = r3
                java.lang.String r1 = ""
            L_0x00ca:
                r9.append(r1)     // Catch:{ all -> 0x01ac }
                if (r15 == 0) goto L_0x00f4
                java.lang.String r1 = "Content-Range: bytes %d-%d/%d\n"
                r3 = 3
                java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ all -> 0x00c0 }
                long r12 = r4.b     // Catch:{ all -> 0x00c0 }
                java.lang.Long r12 = java.lang.Long.valueOf(r12)     // Catch:{ all -> 0x00c0 }
                r13 = 0
                r3[r13] = r12     // Catch:{ all -> 0x00c0 }
                r12 = 1
                long r12 = r10 - r12
                java.lang.Long r12 = java.lang.Long.valueOf(r12)     // Catch:{ all -> 0x00c0 }
                r13 = 1
                r3[r13] = r12     // Catch:{ all -> 0x00c0 }
                r12 = 2
                java.lang.Long r10 = java.lang.Long.valueOf(r10)     // Catch:{ all -> 0x00c0 }
                r3[r12] = r10     // Catch:{ all -> 0x00c0 }
                java.lang.String r1 = defpackage.etl.a(r1, r3)     // Catch:{ all -> 0x00c0 }
                goto L_0x00f6
            L_0x00f4:
                java.lang.String r1 = ""
            L_0x00f6:
                r9.append(r1)     // Catch:{ all -> 0x01ac }
                if (r8 == 0) goto L_0x0109
                java.lang.String r1 = "Content-Type: %s\n"
                r14 = 1
                java.lang.Object[] r3 = new java.lang.Object[r14]     // Catch:{ all -> 0x00c0 }
                r18 = 0
                r3[r18] = r7     // Catch:{ all -> 0x00c0 }
                java.lang.String r1 = defpackage.etl.a(r1, r3)     // Catch:{ all -> 0x00c0 }
                goto L_0x010e
            L_0x0109:
                r14 = 1
                r18 = 0
                java.lang.String r1 = ""
            L_0x010e:
                r9.append(r1)     // Catch:{ all -> 0x01ac }
                java.lang.String r1 = "\n"
                r9.append(r1)     // Catch:{ all -> 0x01ac }
                java.lang.String r1 = r9.toString()     // Catch:{ all -> 0x01ac }
                java.lang.String r3 = "UTF-8"
                byte[] r1 = r1.getBytes(r3)     // Catch:{ all -> 0x01ac }
                r6.write(r1)     // Catch:{ all -> 0x01ac }
                long r7 = r4.b     // Catch:{ all -> 0x01ac }
                eto r1 = r5.a     // Catch:{ all -> 0x01ac }
                long r9 = r1.a()     // Catch:{ all -> 0x01ac }
                r11 = 0
                int r1 = (r9 > r11 ? 1 : (r9 == r11 ? 0 : -1))
                if (r1 <= 0) goto L_0x0133
                r1 = 1
                goto L_0x0134
            L_0x0133:
                r1 = 0
            L_0x0134:
                ety r3 = r5.b     // Catch:{ all -> 0x01ac }
                long r15 = r3.a()     // Catch:{ all -> 0x01ac }
                float r3 = (float) r9     // Catch:{ all -> 0x01ac }
                r9 = 1045220557(0x3e4ccccd, float:0.2)
                float r3 = r3 * r9
                int r3 = (int) r3     // Catch:{ all -> 0x01ac }
                r9 = 262144(0x40000, float:3.67342E-40)
                int r3 = java.lang.Math.min(r3, r9)     // Catch:{ all -> 0x01ac }
                boolean r9 = defpackage.eua.a()     // Catch:{ all -> 0x01ac }
                if (r9 == 0) goto L_0x017c
                java.lang.String r9 = "mounted"
                java.lang.String r10 = android.os.Environment.getExternalStorageState()     // Catch:{ all -> 0x00c0 }
                boolean r9 = r9.equals(r10)     // Catch:{ all -> 0x00c0 }
                if (r9 == 0) goto L_0x0173
                java.io.File r9 = android.os.Environment.getExternalStorageDirectory()     // Catch:{ all -> 0x00c0 }
                android.os.StatFs r10 = new android.os.StatFs     // Catch:{ all -> 0x00c0 }
                java.lang.String r9 = r9.getPath()     // Catch:{ all -> 0x00c0 }
                r10.<init>(r9)     // Catch:{ all -> 0x00c0 }
                int r9 = r10.getBlockSize()     // Catch:{ all -> 0x00c0 }
                long r11 = (long) r9     // Catch:{ all -> 0x00c0 }
                int r9 = r10.getAvailableBlocks()     // Catch:{ all -> 0x00c0 }
                long r9 = (long) r9     // Catch:{ all -> 0x00c0 }
                long r12 = r11 * r9
                r11 = r12
            L_0x0173:
                r9 = 104857600(0x6400000, double:5.1806538E-316)
                int r9 = (r11 > r9 ? 1 : (r11 == r9 ? 0 : -1))
                if (r9 <= 0) goto L_0x017c
                r9 = 1
                goto L_0x017d
            L_0x017c:
                r9 = 0
            L_0x017d:
                if (r9 == 0) goto L_0x018e
                if (r1 == 0) goto L_0x018f
                boolean r1 = r4.c     // Catch:{ all -> 0x00c0 }
                if (r1 == 0) goto L_0x018f
                long r9 = r4.b     // Catch:{ all -> 0x00c0 }
                long r3 = (long) r3     // Catch:{ all -> 0x00c0 }
                long r15 = r15 + r3
                int r1 = (r9 > r15 ? 1 : (r9 == r15 ? 0 : -1))
                if (r1 > 0) goto L_0x018e
                goto L_0x018f
            L_0x018e:
                r14 = 0
            L_0x018f:
                if (r14 == 0) goto L_0x0195
                r5.a(r6, r7)     // Catch:{ all -> 0x00c0 }
                goto L_0x0198
            L_0x0195:
                r5.b(r6, r7)     // Catch:{ all -> 0x01ac }
            L_0x0198:
                r2.b()     // Catch:{ SocketException -> 0x01a9, ProxyCacheException | IOException -> 0x01a5, all -> 0x01a1 }
                r1 = r19
            L_0x019d:
                defpackage.etm.a(r1)
                return
            L_0x01a1:
                r0 = move-exception
                r1 = r19
                goto L_0x01bb
            L_0x01a5:
                r0 = move-exception
                r1 = r19
                goto L_0x01bf
            L_0x01a9:
                r1 = r19
                goto L_0x01d2
            L_0x01ac:
                r0 = move-exception
                r1 = r19
                goto L_0x01b2
            L_0x01b0:
                r0 = move-exception
                r1 = r3
            L_0x01b2:
                r3 = r0
            L_0x01b3:
                r2.b()     // Catch:{ SocketException -> 0x01d2, ProxyCacheException | IOException -> 0x01b7 }
                throw r3     // Catch:{ SocketException -> 0x01d2, ProxyCacheException | IOException -> 0x01b7 }
            L_0x01b7:
                r0 = move-exception
                goto L_0x01bf
            L_0x01b9:
                r0 = move-exception
                r1 = r3
            L_0x01bb:
                r2 = r0
                goto L_0x01cd
            L_0x01bd:
                r0 = move-exception
                r1 = r3
            L_0x01bf:
                r2 = r0
            L_0x01c0:
                com.danikula.videocache.ProxyCacheException r3 = new com.danikula.videocache.ProxyCacheException     // Catch:{ all -> 0x01cb }
                java.lang.String r4 = "Error processing request"
                r3.<init>(r4, r2)     // Catch:{ all -> 0x01cb }
                defpackage.etm.a(r1)
                return
            L_0x01cb:
                r0 = move-exception
                goto L_0x01bb
            L_0x01cd:
                defpackage.etm.a(r1)
                throw r2
            L_0x01d1:
                r1 = r3
            L_0x01d2:
                defpackage.etm.a(r1)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: defpackage.etm.b.run():void");
        }
    }

    /* renamed from: etm$c */
    /* compiled from: HttpProxyCacheServer */
    final class c implements Runnable {
        private final CountDownLatch b;

        public c(CountDownLatch countDownLatch) {
            this.b = countDownLatch;
        }

        public final void run() {
            this.b.countDown();
            etm etm = etm.this;
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    etm.a.submit(new b(etm.b.accept()));
                } catch (IOException e) {
                    new ProxyCacheException("Error during waiting connection", e);
                    return;
                }
            }
        }
    }

    public /* synthetic */ etm(etj etj, byte b2) {
        this(etj);
    }

    private etm(etj etj) {
        this.f = new Object();
        this.a = Executors.newFixedThreadPool(8);
        this.g = new ConcurrentHashMap();
        this.d = (etj) etr.a(etj);
        try {
            this.b = new ServerSocket(0, 8, InetAddress.getByName("127.0.0.1"));
            this.c = this.b.getLocalPort();
            etp.a("127.0.0.1", this.c);
            CountDownLatch countDownLatch = new CountDownLatch(1);
            this.h = new Thread(new c(countDownLatch));
            this.h.start();
            countDownLatch.await();
            this.e = new etq("127.0.0.1", this.c);
        } catch (IOException | InterruptedException e2) {
            this.a.shutdown();
            throw new IllegalStateException("Error starting local proxy server", e2);
        }
    }

    public final boolean a(String str) {
        etr.a(str, (String) "Url can't be null!");
        return b(str).exists();
    }

    public final File b(String str) {
        return new File(this.d.a, this.d.b.generate(str));
    }

    /* access modifiers changed from: 0000 */
    public final etn c(String str) throws ProxyCacheException {
        etn etn;
        synchronized (this.f) {
            etn = this.g.get(str);
            if (etn == null) {
                etn = new etn(str, this.d);
                this.g.put(str, etn);
            }
        }
        return etn;
    }

    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:10:0x001b */
    /* JADX WARNING: Missing exception handler attribute for start block: B:6:0x0012 */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0021 A[Catch:{ IOException -> 0x0025 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static void a(java.net.Socket r3) {
        /*
            boolean r0 = r3.isInputShutdown()     // Catch:{ SocketException -> 0x0012, IOException -> 0x000a }
            if (r0 != 0) goto L_0x0012
            r3.shutdownInput()     // Catch:{ SocketException -> 0x0012, IOException -> 0x000a }
            goto L_0x0012
        L_0x000a:
            r0 = move-exception
            com.danikula.videocache.ProxyCacheException r1 = new com.danikula.videocache.ProxyCacheException
            java.lang.String r2 = "Error closing socket input stream"
            r1.<init>(r2, r0)
        L_0x0012:
            boolean r0 = r3.isOutputShutdown()     // Catch:{ IOException -> 0x001b }
            if (r0 != 0) goto L_0x001b
            r3.shutdownOutput()     // Catch:{ IOException -> 0x001b }
        L_0x001b:
            boolean r0 = r3.isClosed()     // Catch:{ IOException -> 0x0025 }
            if (r0 != 0) goto L_0x0024
            r3.close()     // Catch:{ IOException -> 0x0025 }
        L_0x0024:
            return
        L_0x0025:
            r3 = move-exception
            com.danikula.videocache.ProxyCacheException r0 = new com.danikula.videocache.ProxyCacheException
            java.lang.String r1 = "Error closing socket"
            r0.<init>(r1, r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.etm.a(java.net.Socket):void");
    }
}
