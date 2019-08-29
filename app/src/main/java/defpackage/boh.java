package defpackage;

import com.autonavi.minimap.offline.model.FilePathHelper;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/* renamed from: boh reason: default package */
/* compiled from: DiskLruCache */
public final class boh implements Closeable {
    static final Pattern a = Pattern.compile("[a-z0-9_-]{1,64}");
    /* access modifiers changed from: private */
    public static final OutputStream r = new OutputStream() {
        public final void write(int i) throws IOException {
        }
    };
    final ThreadPoolExecutor b;
    /* access modifiers changed from: private */
    public final File c;
    private final File d;
    private final File e;
    private final File f;
    private final int g;
    private long h;
    private int i;
    /* access modifiers changed from: private */
    public final int j;
    private long k = 0;
    private int l = 0;
    /* access modifiers changed from: private */
    public Writer m;
    private final LinkedHashMap<String, b> n = new LinkedHashMap<>(0, 0.75f, true);
    /* access modifiers changed from: private */
    public int o;
    private long p = 0;
    private final Callable<Void> q;

    /* renamed from: boh$a */
    /* compiled from: DiskLruCache */
    public final class a {
        final b a;
        final boolean[] b;
        boolean c;
        private boolean e;

        /* renamed from: boh$a$a reason: collision with other inner class name */
        /* compiled from: DiskLruCache */
        class C0058a extends FilterOutputStream {
            /* synthetic */ C0058a(a aVar, OutputStream outputStream, byte b) {
                this(outputStream);
            }

            private C0058a(OutputStream outputStream) {
                super(outputStream);
            }

            public final void write(int i) {
                try {
                    this.out.write(i);
                } catch (IOException unused) {
                    a.this.c = true;
                }
            }

            public final void write(byte[] bArr, int i, int i2) {
                try {
                    this.out.write(bArr, i, i2);
                } catch (IOException unused) {
                    a.this.c = true;
                }
            }

            public final void close() {
                try {
                    this.out.close();
                } catch (IOException unused) {
                    a.this.c = true;
                }
            }

            public final void flush() {
                try {
                    this.out.flush();
                } catch (IOException unused) {
                    a.this.c = true;
                }
            }
        }

        /* synthetic */ a(boh boh, b bVar, byte b2) {
            this(bVar);
        }

        private a(b bVar) {
            this.a = bVar;
            this.b = bVar.c ? null : new boolean[boh.this.j];
        }

        /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x0027 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final java.io.OutputStream a() throws java.io.IOException {
            /*
                r4 = this;
                boh r0 = defpackage.boh.this
                monitor-enter(r0)
                boh$b r1 = r4.a     // Catch:{ all -> 0x0042 }
                boh$a r1 = r1.d     // Catch:{ all -> 0x0042 }
                if (r1 == r4) goto L_0x000f
                java.lang.IllegalStateException r1 = new java.lang.IllegalStateException     // Catch:{ all -> 0x0042 }
                r1.<init>()     // Catch:{ all -> 0x0042 }
                throw r1     // Catch:{ all -> 0x0042 }
            L_0x000f:
                boh$b r1 = r4.a     // Catch:{ all -> 0x0042 }
                boolean r1 = r1.c     // Catch:{ all -> 0x0042 }
                r2 = 0
                if (r1 != 0) goto L_0x001b
                boolean[] r1 = r4.b     // Catch:{ all -> 0x0042 }
                r3 = 1
                r1[r2] = r3     // Catch:{ all -> 0x0042 }
            L_0x001b:
                boh$b r1 = r4.a     // Catch:{ all -> 0x0042 }
                java.io.File r1 = r1.b(r2)     // Catch:{ all -> 0x0042 }
                java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch:{ FileNotFoundException -> 0x0027 }
                r3.<init>(r1)     // Catch:{ FileNotFoundException -> 0x0027 }
                goto L_0x0035
            L_0x0027:
                boh r3 = defpackage.boh.this     // Catch:{ all -> 0x0042 }
                java.io.File r3 = r3.c     // Catch:{ all -> 0x0042 }
                r3.mkdirs()     // Catch:{ all -> 0x0042 }
                java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch:{ FileNotFoundException -> 0x003c }
                r3.<init>(r1)     // Catch:{ FileNotFoundException -> 0x003c }
            L_0x0035:
                boh$a$a r1 = new boh$a$a     // Catch:{ all -> 0x0042 }
                r1.<init>(r4, r3, r2)     // Catch:{ all -> 0x0042 }
                monitor-exit(r0)     // Catch:{ all -> 0x0042 }
                return r1
            L_0x003c:
                java.io.OutputStream r1 = defpackage.boh.r     // Catch:{ all -> 0x0042 }
                monitor-exit(r0)     // Catch:{ all -> 0x0042 }
                return r1
            L_0x0042:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x0042 }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: defpackage.boh.a.a():java.io.OutputStream");
        }

        public final void b() throws IOException {
            if (this.c) {
                boh.this.a(this, false);
                boh.this.c(this.a.a);
            } else {
                boh.this.a(this, true);
            }
            this.e = true;
        }

        public final void c() throws IOException {
            boh.this.a(this, false);
        }
    }

    /* renamed from: boh$b */
    /* compiled from: DiskLruCache */
    final class b {
        final String a;
        final long[] b;
        boolean c;
        a d;
        long e;

        /* synthetic */ b(boh boh, String str, byte b2) {
            this(str);
        }

        private b(String str) {
            this.a = str;
            this.b = new long[boh.this.j];
        }

        public final String a() throws IOException {
            StringBuilder sb = new StringBuilder();
            for (long append : this.b) {
                sb.append(' ');
                sb.append(append);
            }
            return sb.toString();
        }

        /* access modifiers changed from: 0000 */
        public final void a(String[] strArr) throws IOException {
            if (strArr.length != boh.this.j) {
                throw b(strArr);
            }
            int i = 0;
            while (i < strArr.length) {
                try {
                    this.b[i] = Long.parseLong(strArr[i]);
                    i++;
                } catch (NumberFormatException unused) {
                    throw b(strArr);
                }
            }
        }

        private static IOException b(String[] strArr) throws IOException {
            StringBuilder sb = new StringBuilder("unexpected journal line: ");
            sb.append(Arrays.toString(strArr));
            throw new IOException(sb.toString());
        }

        public final File a(int i) {
            File h = boh.this.c;
            StringBuilder sb = new StringBuilder();
            sb.append(this.a);
            sb.append(i);
            return new File(h, sb.toString());
        }

        public final File b(int i) {
            File h = boh.this.c;
            StringBuilder sb = new StringBuilder();
            sb.append(this.a);
            sb.append(i);
            sb.append(FilePathHelper.SUFFIX_DOT_TMP);
            return new File(h, sb.toString());
        }
    }

    /* renamed from: boh$c */
    /* compiled from: DiskLruCache */
    public final class c implements Closeable {
        public final InputStream[] a;
        private final String c;
        private final long d;
        private File[] e;
        private final long[] f;

        /* synthetic */ c(boh boh, String str, long j, File[] fileArr, InputStream[] inputStreamArr, long[] jArr, byte b2) {
            this(str, j, fileArr, inputStreamArr, jArr);
        }

        private c(String str, long j, File[] fileArr, InputStream[] inputStreamArr, long[] jArr) {
            this.c = str;
            this.d = j;
            this.e = fileArr;
            this.a = inputStreamArr;
            this.f = jArr;
        }

        public final void close() {
            for (InputStream a2 : this.a) {
                boj.a((Closeable) a2);
            }
        }
    }

    private boh(File file, long j2, int i2) {
        File file2 = file;
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue());
        this.b = threadPoolExecutor;
        this.q = new Callable<Void>() {
            /* access modifiers changed from: private */
            /* JADX WARNING: Code restructure failed: missing block: B:11:0x002b, code lost:
                return null;
             */
            /* renamed from: a */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public java.lang.Void call() throws java.lang.Exception {
                /*
                    r3 = this;
                    boh r0 = defpackage.boh.this
                    monitor-enter(r0)
                    boh r1 = defpackage.boh.this     // Catch:{ all -> 0x002c }
                    java.io.Writer r1 = r1.m     // Catch:{ all -> 0x002c }
                    r2 = 0
                    if (r1 != 0) goto L_0x000e
                    monitor-exit(r0)     // Catch:{ all -> 0x002c }
                    return r2
                L_0x000e:
                    boh r1 = defpackage.boh.this     // Catch:{ all -> 0x002c }
                    r1.i()     // Catch:{ all -> 0x002c }
                    boh r1 = defpackage.boh.this     // Catch:{ all -> 0x002c }
                    r1.j()     // Catch:{ all -> 0x002c }
                    boh r1 = defpackage.boh.this     // Catch:{ all -> 0x002c }
                    boolean r1 = r1.g()     // Catch:{ all -> 0x002c }
                    if (r1 == 0) goto L_0x002a
                    boh r1 = defpackage.boh.this     // Catch:{ all -> 0x002c }
                    r1.f()     // Catch:{ all -> 0x002c }
                    boh r1 = defpackage.boh.this     // Catch:{ all -> 0x002c }
                    r1.o = 0     // Catch:{ all -> 0x002c }
                L_0x002a:
                    monitor-exit(r0)     // Catch:{ all -> 0x002c }
                    return r2
                L_0x002c:
                    r1 = move-exception
                    monitor-exit(r0)     // Catch:{ all -> 0x002c }
                    throw r1
                */
                throw new UnsupportedOperationException("Method not decompiled: defpackage.boh.AnonymousClass1.call():java.lang.Void");
            }
        };
        this.c = file2;
        this.g = 1;
        this.d = new File(file2, "journal");
        this.e = new File(file2, "journal.tmp");
        this.f = new File(file2, "journal.bkp");
        this.j = 1;
        this.h = j2;
        this.i = i2;
    }

    public static boh a(File file, long j2, int i2) throws IOException {
        if (j2 <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        } else if (i2 <= 0) {
            throw new IllegalArgumentException("maxFileCount <= 0");
        } else {
            File file2 = new File(file, "journal.bkp");
            if (file2.exists()) {
                File file3 = new File(file, "journal");
                if (file3.exists()) {
                    file2.delete();
                } else {
                    a(file2, file3, false);
                }
            }
            boh boh = new boh(file, j2, i2);
            if (boh.d.exists()) {
                try {
                    boh.d();
                    boh.e();
                    boh.m = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(boh.d, true), boj.a));
                    return boh;
                } catch (IOException e2) {
                    PrintStream printStream = System.out;
                    StringBuilder sb = new StringBuilder("DiskLruCache ");
                    sb.append(file);
                    sb.append(" is corrupt: ");
                    sb.append(e2.getMessage());
                    sb.append(", removing");
                    boh.close();
                    boj.a(boh.c);
                }
            }
            file.mkdirs();
            boh boh2 = new boh(file, j2, i2);
            boh2.f();
            return boh2;
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(4:49|50|51|52) */
    /* JADX WARNING: Code restructure failed: missing block: B:50:?, code lost:
        r10.o = r2 - r10.n.size();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x010f, code lost:
        return;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:49:0x0103 */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:53:0x0110=Splitter:B:53:0x0110, B:13:0x0056=Splitter:B:13:0x0056} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void d() throws java.io.IOException {
        /*
            r10 = this;
            boi r0 = new boi
            java.io.FileInputStream r1 = new java.io.FileInputStream
            java.io.File r2 = r10.d
            r1.<init>(r2)
            java.nio.charset.Charset r2 = defpackage.boj.a
            r0.<init>(r1, r2)
            java.lang.String r1 = r0.a()     // Catch:{ all -> 0x0142 }
            java.lang.String r2 = r0.a()     // Catch:{ all -> 0x0142 }
            java.lang.String r3 = r0.a()     // Catch:{ all -> 0x0142 }
            java.lang.String r4 = r0.a()     // Catch:{ all -> 0x0142 }
            java.lang.String r5 = r0.a()     // Catch:{ all -> 0x0142 }
            java.lang.String r6 = "libcore.io.DiskLruCache"
            boolean r6 = r6.equals(r1)     // Catch:{ all -> 0x0142 }
            if (r6 == 0) goto L_0x0110
            java.lang.String r6 = "1"
            boolean r6 = r6.equals(r2)     // Catch:{ all -> 0x0142 }
            if (r6 == 0) goto L_0x0110
            int r6 = r10.g     // Catch:{ all -> 0x0142 }
            java.lang.String r6 = java.lang.Integer.toString(r6)     // Catch:{ all -> 0x0142 }
            boolean r3 = r6.equals(r3)     // Catch:{ all -> 0x0142 }
            if (r3 == 0) goto L_0x0110
            int r3 = r10.j     // Catch:{ all -> 0x0142 }
            java.lang.String r3 = java.lang.Integer.toString(r3)     // Catch:{ all -> 0x0142 }
            boolean r3 = r3.equals(r4)     // Catch:{ all -> 0x0142 }
            if (r3 == 0) goto L_0x0110
            java.lang.String r3 = ""
            boolean r3 = r3.equals(r5)     // Catch:{ all -> 0x0142 }
            if (r3 != 0) goto L_0x0054
            goto L_0x0110
        L_0x0054:
            r1 = 0
            r2 = 0
        L_0x0056:
            java.lang.String r3 = r0.a()     // Catch:{ EOFException -> 0x0103 }
            r4 = 32
            int r5 = r3.indexOf(r4)     // Catch:{ EOFException -> 0x0103 }
            r6 = -1
            if (r5 != r6) goto L_0x0074
            java.io.IOException r1 = new java.io.IOException     // Catch:{ EOFException -> 0x0103 }
            java.lang.String r4 = "unexpected journal line: "
            java.lang.String r3 = java.lang.String.valueOf(r3)     // Catch:{ EOFException -> 0x0103 }
            java.lang.String r3 = r4.concat(r3)     // Catch:{ EOFException -> 0x0103 }
            r1.<init>(r3)     // Catch:{ EOFException -> 0x0103 }
            throw r1     // Catch:{ EOFException -> 0x0103 }
        L_0x0074:
            int r7 = r5 + 1
            int r4 = r3.indexOf(r4, r7)     // Catch:{ EOFException -> 0x0103 }
            if (r4 != r6) goto L_0x0091
            java.lang.String r7 = r3.substring(r7)     // Catch:{ EOFException -> 0x0103 }
            r8 = 6
            if (r5 != r8) goto L_0x0095
            java.lang.String r8 = "REMOVE"
            boolean r8 = r3.startsWith(r8)     // Catch:{ EOFException -> 0x0103 }
            if (r8 == 0) goto L_0x0095
            java.util.LinkedHashMap<java.lang.String, boh$b> r3 = r10.n     // Catch:{ EOFException -> 0x0103 }
            r3.remove(r7)     // Catch:{ EOFException -> 0x0103 }
            goto L_0x00ee
        L_0x0091:
            java.lang.String r7 = r3.substring(r7, r4)     // Catch:{ EOFException -> 0x0103 }
        L_0x0095:
            java.util.LinkedHashMap<java.lang.String, boh$b> r8 = r10.n     // Catch:{ EOFException -> 0x0103 }
            java.lang.Object r8 = r8.get(r7)     // Catch:{ EOFException -> 0x0103 }
            boh$b r8 = (defpackage.boh.b) r8     // Catch:{ EOFException -> 0x0103 }
            if (r8 != 0) goto L_0x00a9
            boh$b r8 = new boh$b     // Catch:{ EOFException -> 0x0103 }
            r8.<init>(r10, r7, r1)     // Catch:{ EOFException -> 0x0103 }
            java.util.LinkedHashMap<java.lang.String, boh$b> r9 = r10.n     // Catch:{ EOFException -> 0x0103 }
            r9.put(r7, r8)     // Catch:{ EOFException -> 0x0103 }
        L_0x00a9:
            r7 = 5
            if (r4 == r6) goto L_0x00cc
            if (r5 != r7) goto L_0x00cc
            java.lang.String r9 = "CLEAN"
            boolean r9 = r3.startsWith(r9)     // Catch:{ EOFException -> 0x0103 }
            if (r9 == 0) goto L_0x00cc
            int r4 = r4 + 1
            java.lang.String r3 = r3.substring(r4)     // Catch:{ EOFException -> 0x0103 }
            java.lang.String r4 = " "
            java.lang.String[] r3 = r3.split(r4)     // Catch:{ EOFException -> 0x0103 }
            r4 = 1
            r8.c = r4     // Catch:{ EOFException -> 0x0103 }
            r4 = 0
            r8.d = r4     // Catch:{ EOFException -> 0x0103 }
            r8.a(r3)     // Catch:{ EOFException -> 0x0103 }
            goto L_0x00ee
        L_0x00cc:
            if (r4 != r6) goto L_0x00e0
            if (r5 != r7) goto L_0x00e0
            java.lang.String r7 = "DIRTY"
            boolean r7 = r3.startsWith(r7)     // Catch:{ EOFException -> 0x0103 }
            if (r7 == 0) goto L_0x00e0
            boh$a r3 = new boh$a     // Catch:{ EOFException -> 0x0103 }
            r3.<init>(r10, r8, r1)     // Catch:{ EOFException -> 0x0103 }
            r8.d = r3     // Catch:{ EOFException -> 0x0103 }
            goto L_0x00ee
        L_0x00e0:
            if (r4 != r6) goto L_0x00f2
            r4 = 4
            if (r5 != r4) goto L_0x00f2
            java.lang.String r4 = "READ"
            boolean r4 = r3.startsWith(r4)     // Catch:{ EOFException -> 0x0103 }
            if (r4 != 0) goto L_0x00ee
            goto L_0x00f2
        L_0x00ee:
            int r2 = r2 + 1
            goto L_0x0056
        L_0x00f2:
            java.io.IOException r1 = new java.io.IOException     // Catch:{ EOFException -> 0x0103 }
            java.lang.String r4 = "unexpected journal line: "
            java.lang.String r3 = java.lang.String.valueOf(r3)     // Catch:{ EOFException -> 0x0103 }
            java.lang.String r3 = r4.concat(r3)     // Catch:{ EOFException -> 0x0103 }
            r1.<init>(r3)     // Catch:{ EOFException -> 0x0103 }
            throw r1     // Catch:{ EOFException -> 0x0103 }
        L_0x0103:
            java.util.LinkedHashMap<java.lang.String, boh$b> r1 = r10.n     // Catch:{ all -> 0x0142 }
            int r1 = r1.size()     // Catch:{ all -> 0x0142 }
            int r2 = r2 - r1
            r10.o = r2     // Catch:{ all -> 0x0142 }
            defpackage.boj.a(r0)
            return
        L_0x0110:
            java.io.IOException r3 = new java.io.IOException     // Catch:{ all -> 0x0142 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x0142 }
            java.lang.String r7 = "unexpected journal header: ["
            r6.<init>(r7)     // Catch:{ all -> 0x0142 }
            r6.append(r1)     // Catch:{ all -> 0x0142 }
            java.lang.String r1 = ", "
            r6.append(r1)     // Catch:{ all -> 0x0142 }
            r6.append(r2)     // Catch:{ all -> 0x0142 }
            java.lang.String r1 = ", "
            r6.append(r1)     // Catch:{ all -> 0x0142 }
            r6.append(r4)     // Catch:{ all -> 0x0142 }
            java.lang.String r1 = ", "
            r6.append(r1)     // Catch:{ all -> 0x0142 }
            r6.append(r5)     // Catch:{ all -> 0x0142 }
            java.lang.String r1 = "]"
            r6.append(r1)     // Catch:{ all -> 0x0142 }
            java.lang.String r1 = r6.toString()     // Catch:{ all -> 0x0142 }
            r3.<init>(r1)     // Catch:{ all -> 0x0142 }
            throw r3     // Catch:{ all -> 0x0142 }
        L_0x0142:
            r1 = move-exception
            defpackage.boj.a(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.boh.d():void");
    }

    private void e() throws IOException {
        a(this.e);
        Iterator<b> it = this.n.values().iterator();
        while (it.hasNext()) {
            b next = it.next();
            int i2 = 0;
            if (next.d == null) {
                while (i2 < this.j) {
                    this.k += next.b[i2];
                    this.l++;
                    i2++;
                }
            } else {
                next.d = null;
                while (i2 < this.j) {
                    a(next.a(i2));
                    a(next.b(i2));
                    i2++;
                }
                it.remove();
            }
        }
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: private */
    public synchronized void f() throws IOException {
        if (this.m != null) {
            this.m.close();
        }
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.e), boj.a));
        try {
            bufferedWriter.write("libcore.io.DiskLruCache");
            bufferedWriter.write("\n");
            bufferedWriter.write("1");
            bufferedWriter.write("\n");
            bufferedWriter.write(Integer.toString(this.g));
            bufferedWriter.write("\n");
            bufferedWriter.write(Integer.toString(this.j));
            bufferedWriter.write("\n");
            bufferedWriter.write("\n");
            for (b next : this.n.values()) {
                if (next.d != null) {
                    StringBuilder sb = new StringBuilder("DIRTY ");
                    sb.append(next.a);
                    sb.append(10);
                    bufferedWriter.write(sb.toString());
                } else {
                    StringBuilder sb2 = new StringBuilder("CLEAN ");
                    sb2.append(next.a);
                    sb2.append(next.a());
                    sb2.append(10);
                    bufferedWriter.write(sb2.toString());
                }
            }
            bufferedWriter.close();
            if (this.d.exists()) {
                a(this.d, this.f, true);
            }
            a(this.e, this.d, false);
            this.f.delete();
            this.m = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.d, true), boj.a));
        } catch (Throwable th) {
            bufferedWriter.close();
            throw th;
        }
    }

    private static void a(File file) throws IOException {
        if (file.exists() && !file.delete()) {
            throw new IOException();
        }
    }

    private static void a(File file, File file2, boolean z) throws IOException {
        if (z) {
            a(file2);
        }
        if (!file.renameTo(file2)) {
            throw new IOException();
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(4:32|33|28|27) */
    /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
        r12.o++;
        r1 = r12.m;
        r2 = new java.lang.StringBuilder("READ ");
        r2.append(r13);
        r2.append(10);
        r1.append(r2.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x005a, code lost:
        if (g() == false) goto L_0x0063;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x005c, code lost:
        r12.b.submit(r12.q);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0063, code lost:
        r3 = new defpackage.boh.c(r12, r13, r0.e, r8, r9, r0.b, 0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0071, code lost:
        return r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0083, code lost:
        return null;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:27:0x0072 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized defpackage.boh.c a(java.lang.String r13) throws java.io.IOException {
        /*
            r12 = this;
            monitor-enter(r12)
            r12.h()     // Catch:{ all -> 0x0084 }
            d(r13)     // Catch:{ all -> 0x0084 }
            java.util.LinkedHashMap<java.lang.String, boh$b> r0 = r12.n     // Catch:{ all -> 0x0084 }
            java.lang.Object r0 = r0.get(r13)     // Catch:{ all -> 0x0084 }
            boh$b r0 = (defpackage.boh.b) r0     // Catch:{ all -> 0x0084 }
            r1 = 0
            if (r0 != 0) goto L_0x0014
            monitor-exit(r12)
            return r1
        L_0x0014:
            boolean r2 = r0.c     // Catch:{ all -> 0x0084 }
            if (r2 != 0) goto L_0x001a
            monitor-exit(r12)
            return r1
        L_0x001a:
            int r2 = r12.j     // Catch:{ all -> 0x0084 }
            java.io.File[] r8 = new java.io.File[r2]     // Catch:{ all -> 0x0084 }
            int r2 = r12.j     // Catch:{ all -> 0x0084 }
            java.io.InputStream[] r9 = new java.io.InputStream[r2]     // Catch:{ all -> 0x0084 }
            r2 = 0
            r3 = 0
        L_0x0024:
            int r4 = r12.j     // Catch:{ FileNotFoundException -> 0x0072 }
            if (r3 >= r4) goto L_0x0038
            java.io.File r4 = r0.a(r3)     // Catch:{ FileNotFoundException -> 0x0072 }
            r8[r3] = r4     // Catch:{ FileNotFoundException -> 0x0072 }
            java.io.FileInputStream r5 = new java.io.FileInputStream     // Catch:{ FileNotFoundException -> 0x0072 }
            r5.<init>(r4)     // Catch:{ FileNotFoundException -> 0x0072 }
            r9[r3] = r5     // Catch:{ FileNotFoundException -> 0x0072 }
            int r3 = r3 + 1
            goto L_0x0024
        L_0x0038:
            int r1 = r12.o     // Catch:{ all -> 0x0084 }
            int r1 = r1 + 1
            r12.o = r1     // Catch:{ all -> 0x0084 }
            java.io.Writer r1 = r12.m     // Catch:{ all -> 0x0084 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0084 }
            java.lang.String r3 = "READ "
            r2.<init>(r3)     // Catch:{ all -> 0x0084 }
            r2.append(r13)     // Catch:{ all -> 0x0084 }
            r3 = 10
            r2.append(r3)     // Catch:{ all -> 0x0084 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0084 }
            r1.append(r2)     // Catch:{ all -> 0x0084 }
            boolean r1 = r12.g()     // Catch:{ all -> 0x0084 }
            if (r1 == 0) goto L_0x0063
            java.util.concurrent.ThreadPoolExecutor r1 = r12.b     // Catch:{ all -> 0x0084 }
            java.util.concurrent.Callable<java.lang.Void> r2 = r12.q     // Catch:{ all -> 0x0084 }
            r1.submit(r2)     // Catch:{ all -> 0x0084 }
        L_0x0063:
            boh$c r1 = new boh$c     // Catch:{ all -> 0x0084 }
            long r6 = r0.e     // Catch:{ all -> 0x0084 }
            long[] r10 = r0.b     // Catch:{ all -> 0x0084 }
            r11 = 0
            r3 = r1
            r4 = r12
            r5 = r13
            r3.<init>(r4, r5, r6, r8, r9, r10, r11)     // Catch:{ all -> 0x0084 }
            monitor-exit(r12)
            return r1
        L_0x0072:
            int r13 = r12.j     // Catch:{ all -> 0x0084 }
            if (r2 >= r13) goto L_0x0082
            r13 = r9[r2]     // Catch:{ all -> 0x0084 }
            if (r13 == 0) goto L_0x0082
            r13 = r9[r2]     // Catch:{ all -> 0x0084 }
            defpackage.boj.a(r13)     // Catch:{ all -> 0x0084 }
            int r2 = r2 + 1
            goto L_0x0072
        L_0x0082:
            monitor-exit(r12)
            return r1
        L_0x0084:
            r13 = move-exception
            monitor-exit(r12)
            throw r13
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.boh.a(java.lang.String):boh$c");
    }

    public final synchronized a b(String str) throws IOException {
        try {
            h();
            d(str);
            b bVar = this.n.get(str);
            if (bVar == null) {
                bVar = new b(this, str, 0);
                this.n.put(str, bVar);
            } else if (bVar.d != null) {
                return null;
            }
            a aVar = new a(this, bVar, 0);
            bVar.d = aVar;
            Writer writer = this.m;
            StringBuilder sb = new StringBuilder("DIRTY ");
            sb.append(str);
            sb.append(10);
            writer.write(sb.toString());
            this.m.flush();
            return aVar;
        }
    }

    public final synchronized long a() {
        try {
        }
        return (long) this.l;
    }

    /* access modifiers changed from: private */
    public boolean g() {
        return this.o >= 2000 && this.o >= this.n.size();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0084, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0086, code lost:
        return false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized boolean c(java.lang.String r9) throws java.io.IOException {
        /*
            r8 = this;
            monitor-enter(r8)
            r8.h()     // Catch:{ all -> 0x0087 }
            d(r9)     // Catch:{ all -> 0x0087 }
            java.util.LinkedHashMap<java.lang.String, boh$b> r0 = r8.n     // Catch:{ all -> 0x0087 }
            java.lang.Object r0 = r0.get(r9)     // Catch:{ all -> 0x0087 }
            boh$b r0 = (defpackage.boh.b) r0     // Catch:{ all -> 0x0087 }
            r1 = 0
            if (r0 == 0) goto L_0x0085
            boh$a r2 = r0.d     // Catch:{ all -> 0x0087 }
            if (r2 == 0) goto L_0x0017
            goto L_0x0085
        L_0x0017:
            int r2 = r8.j     // Catch:{ all -> 0x0087 }
            r3 = 1
            if (r1 >= r2) goto L_0x0054
            java.io.File r2 = r0.a(r1)     // Catch:{ all -> 0x0087 }
            boolean r4 = r2.exists()     // Catch:{ all -> 0x0087 }
            if (r4 == 0) goto L_0x003c
            boolean r4 = r2.delete()     // Catch:{ all -> 0x0087 }
            if (r4 != 0) goto L_0x003c
            java.io.IOException r9 = new java.io.IOException     // Catch:{ all -> 0x0087 }
            java.lang.String r0 = "failed to delete "
            java.lang.String r1 = java.lang.String.valueOf(r2)     // Catch:{ all -> 0x0087 }
            java.lang.String r0 = r0.concat(r1)     // Catch:{ all -> 0x0087 }
            r9.<init>(r0)     // Catch:{ all -> 0x0087 }
            throw r9     // Catch:{ all -> 0x0087 }
        L_0x003c:
            long r4 = r8.k     // Catch:{ all -> 0x0087 }
            long[] r2 = r0.b     // Catch:{ all -> 0x0087 }
            r6 = r2[r1]     // Catch:{ all -> 0x0087 }
            r2 = 0
            long r4 = r4 - r6
            r8.k = r4     // Catch:{ all -> 0x0087 }
            int r2 = r8.l     // Catch:{ all -> 0x0087 }
            int r2 = r2 - r3
            r8.l = r2     // Catch:{ all -> 0x0087 }
            long[] r2 = r0.b     // Catch:{ all -> 0x0087 }
            r3 = 0
            r2[r1] = r3     // Catch:{ all -> 0x0087 }
            int r1 = r1 + 1
            goto L_0x0017
        L_0x0054:
            int r0 = r8.o     // Catch:{ all -> 0x0087 }
            int r0 = r0 + r3
            r8.o = r0     // Catch:{ all -> 0x0087 }
            java.io.Writer r0 = r8.m     // Catch:{ all -> 0x0087 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0087 }
            java.lang.String r2 = "REMOVE "
            r1.<init>(r2)     // Catch:{ all -> 0x0087 }
            r1.append(r9)     // Catch:{ all -> 0x0087 }
            r2 = 10
            r1.append(r2)     // Catch:{ all -> 0x0087 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0087 }
            r0.append(r1)     // Catch:{ all -> 0x0087 }
            java.util.LinkedHashMap<java.lang.String, boh$b> r0 = r8.n     // Catch:{ all -> 0x0087 }
            r0.remove(r9)     // Catch:{ all -> 0x0087 }
            boolean r9 = r8.g()     // Catch:{ all -> 0x0087 }
            if (r9 == 0) goto L_0x0083
            java.util.concurrent.ThreadPoolExecutor r9 = r8.b     // Catch:{ all -> 0x0087 }
            java.util.concurrent.Callable<java.lang.Void> r0 = r8.q     // Catch:{ all -> 0x0087 }
            r9.submit(r0)     // Catch:{ all -> 0x0087 }
        L_0x0083:
            monitor-exit(r8)
            return r3
        L_0x0085:
            monitor-exit(r8)
            return r1
        L_0x0087:
            r9 = move-exception
            monitor-exit(r8)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.boh.c(java.lang.String):boolean");
    }

    private void h() {
        if (this.m == null) {
            throw new IllegalStateException("cache is closed");
        }
    }

    public final synchronized void b() throws IOException {
        h();
        i();
        j();
        this.m.flush();
    }

    public final synchronized void close() throws IOException {
        if (this.m != null) {
            Iterator it = new ArrayList(this.n.values()).iterator();
            while (it.hasNext()) {
                b bVar = (b) it.next();
                if (bVar.d != null) {
                    bVar.d.c();
                }
            }
            i();
            j();
            this.m.close();
            this.m = null;
        }
    }

    /* access modifiers changed from: private */
    public void i() throws IOException {
        while (this.k > this.h) {
            c((String) this.n.entrySet().iterator().next().getKey());
        }
    }

    /* access modifiers changed from: private */
    public void j() throws IOException {
        while (this.l > this.i) {
            c((String) this.n.entrySet().iterator().next().getKey());
        }
    }

    private static void d(String str) {
        if (!a.matcher(str).matches()) {
            StringBuilder sb = new StringBuilder("keys must match regex [a-z0-9_-]{1,64}: \"");
            sb.append(str);
            sb.append("\"");
            throw new IllegalArgumentException(sb.toString());
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00f7, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void a(defpackage.boh.a r11, boolean r12) throws java.io.IOException {
        /*
            r10 = this;
            monitor-enter(r10)
            boh$b r0 = r11.a     // Catch:{ all -> 0x00f8 }
            boh$a r1 = r0.d     // Catch:{ all -> 0x00f8 }
            if (r1 == r11) goto L_0x000d
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException     // Catch:{ all -> 0x00f8 }
            r11.<init>()     // Catch:{ all -> 0x00f8 }
            throw r11     // Catch:{ all -> 0x00f8 }
        L_0x000d:
            r1 = 0
            if (r12 == 0) goto L_0x0044
            boolean r2 = r0.c     // Catch:{ all -> 0x00f8 }
            if (r2 != 0) goto L_0x0044
            r2 = 0
        L_0x0015:
            int r3 = r10.j     // Catch:{ all -> 0x00f8 }
            if (r2 >= r3) goto L_0x0044
            boolean[] r3 = r11.b     // Catch:{ all -> 0x00f8 }
            boolean r3 = r3[r2]     // Catch:{ all -> 0x00f8 }
            if (r3 != 0) goto L_0x0032
            r11.c()     // Catch:{ all -> 0x00f8 }
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException     // Catch:{ all -> 0x00f8 }
            java.lang.String r12 = "Newly created entry didn't create value for index "
            java.lang.String r0 = java.lang.String.valueOf(r2)     // Catch:{ all -> 0x00f8 }
            java.lang.String r12 = r12.concat(r0)     // Catch:{ all -> 0x00f8 }
            r11.<init>(r12)     // Catch:{ all -> 0x00f8 }
            throw r11     // Catch:{ all -> 0x00f8 }
        L_0x0032:
            java.io.File r3 = r0.b(r2)     // Catch:{ all -> 0x00f8 }
            boolean r3 = r3.exists()     // Catch:{ all -> 0x00f8 }
            if (r3 != 0) goto L_0x0041
            r11.c()     // Catch:{ all -> 0x00f8 }
            monitor-exit(r10)
            return
        L_0x0041:
            int r2 = r2 + 1
            goto L_0x0015
        L_0x0044:
            int r11 = r10.j     // Catch:{ all -> 0x00f8 }
            r2 = 1
            if (r1 >= r11) goto L_0x007b
            java.io.File r11 = r0.b(r1)     // Catch:{ all -> 0x00f8 }
            if (r12 == 0) goto L_0x0075
            boolean r3 = r11.exists()     // Catch:{ all -> 0x00f8 }
            if (r3 == 0) goto L_0x0078
            java.io.File r3 = r0.a(r1)     // Catch:{ all -> 0x00f8 }
            r11.renameTo(r3)     // Catch:{ all -> 0x00f8 }
            long[] r11 = r0.b     // Catch:{ all -> 0x00f8 }
            r4 = r11[r1]     // Catch:{ all -> 0x00f8 }
            long r6 = r3.length()     // Catch:{ all -> 0x00f8 }
            long[] r11 = r0.b     // Catch:{ all -> 0x00f8 }
            r11[r1] = r6     // Catch:{ all -> 0x00f8 }
            long r8 = r10.k     // Catch:{ all -> 0x00f8 }
            r11 = 0
            long r8 = r8 - r4
            long r8 = r8 + r6
            r10.k = r8     // Catch:{ all -> 0x00f8 }
            int r11 = r10.l     // Catch:{ all -> 0x00f8 }
            int r11 = r11 + r2
            r10.l = r11     // Catch:{ all -> 0x00f8 }
            goto L_0x0078
        L_0x0075:
            a(r11)     // Catch:{ all -> 0x00f8 }
        L_0x0078:
            int r1 = r1 + 1
            goto L_0x0044
        L_0x007b:
            int r11 = r10.o     // Catch:{ all -> 0x00f8 }
            int r11 = r11 + r2
            r10.o = r11     // Catch:{ all -> 0x00f8 }
            r11 = 0
            r0.d = r11     // Catch:{ all -> 0x00f8 }
            boolean r11 = r0.c     // Catch:{ all -> 0x00f8 }
            r11 = r11 | r12
            r1 = 10
            if (r11 == 0) goto L_0x00b7
            r0.c = r2     // Catch:{ all -> 0x00f8 }
            java.io.Writer r11 = r10.m     // Catch:{ all -> 0x00f8 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x00f8 }
            java.lang.String r3 = "CLEAN "
            r2.<init>(r3)     // Catch:{ all -> 0x00f8 }
            java.lang.String r3 = r0.a     // Catch:{ all -> 0x00f8 }
            r2.append(r3)     // Catch:{ all -> 0x00f8 }
            java.lang.String r3 = r0.a()     // Catch:{ all -> 0x00f8 }
            r2.append(r3)     // Catch:{ all -> 0x00f8 }
            r2.append(r1)     // Catch:{ all -> 0x00f8 }
            java.lang.String r1 = r2.toString()     // Catch:{ all -> 0x00f8 }
            r11.write(r1)     // Catch:{ all -> 0x00f8 }
            if (r12 == 0) goto L_0x00d6
            long r11 = r10.p     // Catch:{ all -> 0x00f8 }
            r1 = 1
            long r1 = r1 + r11
            r10.p = r1     // Catch:{ all -> 0x00f8 }
            r0.e = r11     // Catch:{ all -> 0x00f8 }
            goto L_0x00d6
        L_0x00b7:
            java.util.LinkedHashMap<java.lang.String, boh$b> r11 = r10.n     // Catch:{ all -> 0x00f8 }
            java.lang.String r12 = r0.a     // Catch:{ all -> 0x00f8 }
            r11.remove(r12)     // Catch:{ all -> 0x00f8 }
            java.io.Writer r11 = r10.m     // Catch:{ all -> 0x00f8 }
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ all -> 0x00f8 }
            java.lang.String r2 = "REMOVE "
            r12.<init>(r2)     // Catch:{ all -> 0x00f8 }
            java.lang.String r0 = r0.a     // Catch:{ all -> 0x00f8 }
            r12.append(r0)     // Catch:{ all -> 0x00f8 }
            r12.append(r1)     // Catch:{ all -> 0x00f8 }
            java.lang.String r12 = r12.toString()     // Catch:{ all -> 0x00f8 }
            r11.write(r12)     // Catch:{ all -> 0x00f8 }
        L_0x00d6:
            java.io.Writer r11 = r10.m     // Catch:{ all -> 0x00f8 }
            r11.flush()     // Catch:{ all -> 0x00f8 }
            long r11 = r10.k     // Catch:{ all -> 0x00f8 }
            long r0 = r10.h     // Catch:{ all -> 0x00f8 }
            int r11 = (r11 > r0 ? 1 : (r11 == r0 ? 0 : -1))
            if (r11 > 0) goto L_0x00ef
            int r11 = r10.l     // Catch:{ all -> 0x00f8 }
            int r12 = r10.i     // Catch:{ all -> 0x00f8 }
            if (r11 > r12) goto L_0x00ef
            boolean r11 = r10.g()     // Catch:{ all -> 0x00f8 }
            if (r11 == 0) goto L_0x00f6
        L_0x00ef:
            java.util.concurrent.ThreadPoolExecutor r11 = r10.b     // Catch:{ all -> 0x00f8 }
            java.util.concurrent.Callable<java.lang.Void> r12 = r10.q     // Catch:{ all -> 0x00f8 }
            r11.submit(r12)     // Catch:{ all -> 0x00f8 }
        L_0x00f6:
            monitor-exit(r10)
            return
        L_0x00f8:
            r11 = move-exception
            monitor-exit(r10)
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.boh.a(boh$a, boolean):void");
    }
}
