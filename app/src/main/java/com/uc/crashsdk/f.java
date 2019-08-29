package com.uc.crashsdk;

import android.os.Build;
import android.os.Build.VERSION;
import android.os.Process;
import android.os.StatFs;
import com.alibaba.wireless.security.SecExceptionCode;
import com.alipay.mobile.h5container.api.H5Param;
import com.uc.crashsdk.a.h;
import com.uc.crashsdk.a.i;
import com.uc.crashsdk.export.CrashApi;
import com.uc.crashsdk.export.LogType;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

/* compiled from: ProGuard */
public class f implements UncaughtExceptionHandler {
    private static boolean A = false;
    private static Runnable B = null;
    private static Object C = new Object();
    static String a = null;
    static final /* synthetic */ boolean b = (!f.class.desiredAssertionStatus());
    private static long c;
    private static volatile boolean d = false;
    private static boolean e = false;
    /* access modifiers changed from: private */
    public static boolean g = true;
    private static String h;
    private static int i = 8;
    private static int j = 128;
    private static boolean k = false;
    private static String l = null;
    /* access modifiers changed from: private */
    public static Object m = new Object();
    private static Object n = new Object();
    private static Object o = new Object();
    private static Object p = new Object();
    private static ArrayList<String> q = new ArrayList<>();
    private static int r = 0;
    private static String s = null;
    private static boolean t = false;
    private static String u = null;
    private static String v = null;
    private static String w = null;
    private static Object x = new Object();
    private static UncaughtExceptionHandler y = null;
    private static Throwable z = null;
    private final List<FileInputStream> f = new ArrayList();

    /* compiled from: ProGuard */
    final class a implements Runnable {
        private final String a;
        private boolean b;
        private boolean c;

        /* synthetic */ a(String str, boolean z, boolean z2, byte b2) {
            this(str, z, z2);
        }

        private a(String str, boolean z, boolean z2) {
            this.b = true;
            this.c = false;
            this.a = str;
            this.b = z;
            this.c = z2;
        }

        public final void run() {
            com.uc.crashsdk.a.c.b("CrashHandler uploading logs");
            synchronized (f.m) {
                try {
                    if (h.b(this.a)) {
                        f.a(this.a, this.b, false);
                    } else {
                        com.uc.crashsdk.a.c.b("CrashHandler url is empty!");
                    }
                    if (this.c) {
                        f.m.notify();
                    }
                } catch (Throwable th) {
                    if (this.c) {
                        f.m.notify();
                    }
                    throw th;
                }
            }
        }
    }

    /* compiled from: ProGuard */
    final class b extends OutputStream {
        private final OutputStream a;
        private int b = 0;
        private int c = 0;
        private boolean d = false;

        public b(OutputStream outputStream) {
            this.a = outputStream;
        }

        private int a(byte[] bArr, int i, int i2) {
            int i3;
            this.c += i2;
            if (this.d) {
                return 0;
            }
            int w = p.w();
            if (w <= 0 || this.b + i2 <= w) {
                i3 = i2;
            } else {
                i3 = w - this.b;
            }
            this.b += i3;
            this.a.write(bArr, i, i3);
            if (i3 >= i2) {
                return i3;
            }
            this.d = true;
            return i3;
        }

        public final void a() {
            try {
                if (this.c - this.b > 0) {
                    a("\n".getBytes("UTF-8"));
                    a("--- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---\n".getBytes("UTF-8"));
                }
                int w = p.w();
                a(String.format(Locale.US, "Full: %d bytes, write: %d bytes, limit: %d bytes, reject: %d bytes.\n", new Object[]{Integer.valueOf(this.c), Integer.valueOf(this.b), Integer.valueOf(w), Integer.valueOf(this.c - this.b)}).getBytes("UTF-8"));
            } catch (Throwable th) {
                com.uc.crashsdk.a.a.a(th, false);
            }
        }

        public final void a(byte[] bArr) {
            if (f.g && p.G()) {
                com.uc.crashsdk.a.c.a("DEBUG", new String(bArr));
            }
            this.a.write(bArr);
        }

        public final void write(int i) {
            if (f.g && p.G()) {
                com.uc.crashsdk.a.c.a("DEBUG", String.format("%c", new Object[]{Integer.valueOf(i)}));
            }
            this.a.write(i);
            this.b++;
            this.c++;
        }

        public final void write(byte[] bArr, int i, int i2) {
            if (f.g && p.G()) {
                byte[] bArr2 = new byte[i2];
                System.arraycopy(bArr, i, bArr2, 0, i2);
                com.uc.crashsdk.a.c.a("DEBUG", new String(bArr2));
            }
            a(bArr, i, i2);
        }

        public final void write(byte[] bArr) {
            if (f.g && p.G() && !(bArr.length == 1 && bArr[0] == 10)) {
                com.uc.crashsdk.a.c.a("DEBUG", new String(bArr));
            }
            a(bArr, 0, bArr.length);
        }
    }

    /* compiled from: ProGuard */
    final class c implements Comparator<File> {
        private c() {
        }

        /* synthetic */ c(byte b) {
            this();
        }

        public final /* synthetic */ int compare(Object obj, Object obj2) {
            File file = (File) obj;
            File file2 = (File) obj2;
            if (file.lastModified() > file2.lastModified()) {
                return 1;
            }
            return file.lastModified() < file2.lastModified() ? -1 : 0;
        }
    }

    /* compiled from: ProGuard */
    final class d implements Runnable {
        static final /* synthetic */ boolean a = (!f.class.desiredAssertionStatus());
        private int b;
        private String c;

        d(int i) {
            this.b = 0;
            this.c = null;
            this.b = i;
        }

        d(String str) {
            this.b = 0;
            this.c = null;
            this.b = 4;
            this.c = str;
        }

        public final void run() {
            switch (this.b) {
                case 1:
                    f.r();
                    return;
                case 2:
                    f.s();
                    return;
                case 3:
                    f.B();
                    return;
                case 4:
                    f.e(this.c);
                    return;
                default:
                    if (!a) {
                        throw new AssertionError();
                    }
                    return;
            }
        }
    }

    /* compiled from: ProGuard */
    final class e {
        long a = 0;
        long b = 0;
        int c = 0;
        int d = 0;
        boolean e = false;
        boolean f = false;
        boolean g = false;

        e() {
        }
    }

    /* renamed from: com.uc.crashsdk.f$f reason: collision with other inner class name */
    /* compiled from: ProGuard */
    abstract class C0106f {
        protected String b;
        protected e c;

        public abstract boolean a();

        public C0106f(String str, e eVar) {
            this.b = str;
            this.c = eVar;
        }
    }

    static /* synthetic */ void e(String str) {
        File file = new File(b.e());
        if (!k) {
            k = true;
            String str2 = null;
            if (file.exists()) {
                str2 = a(com.uc.crashsdk.a.b.a(file, j, true));
            }
            JNIBridge.nativeSyncInfo("mLogTypeSuffix", str2, 1, 0);
        }
        if (h.a(str)) {
            com.uc.crashsdk.a.b.a(file);
        } else {
            com.uc.crashsdk.a.b.a(file, str.getBytes());
        }
    }

    static /* synthetic */ void r() {
        JNIBridge.nativePrepareUnexpInfos(CrashApi.getInstance().getLastExitType() == 5);
        a.a(false);
    }

    /* JADX INFO: used method not loaded: com.uc.crashsdk.k.a(int):null, types can be incorrect */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0019, code lost:
        com.uc.crashsdk.k.a(11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001f, code lost:
        if (r0 != 2) goto L_0x0037;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0021, code lost:
        com.uc.crashsdk.k.a(10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0026, code lost:
        c(true);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0029, code lost:
        r1 = C;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x002b, code lost:
        monitor-enter(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
        B = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x002f, code lost:
        monitor-exit(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0038, code lost:
        if (r0 != 3) goto L_0x0040;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x003a, code lost:
        com.uc.crashsdk.k.a(29);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0041, code lost:
        if (r0 != 4) goto L_0x0049;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0043, code lost:
        com.uc.crashsdk.k.a(30);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x004a, code lost:
        if (r0 != 5) goto L_0x0026;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x004c, code lost:
        com.uc.crashsdk.k.a(31);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x000e, code lost:
        r0 = com.uc.crashsdk.JNIBridge.nativeGenerateUnexpLog((long) com.uc.crashsdk.p.o());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0017, code lost:
        if (r0 == 0) goto L_0x0029;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ void s() {
        /*
            r2 = 1
            java.lang.Object r1 = C
            monitor-enter(r1)
            java.lang.Runnable r0 = B     // Catch:{ all -> 0x0034 }
            if (r0 != 0) goto L_0x000a
            monitor-exit(r1)     // Catch:{ all -> 0x0034 }
        L_0x0009:
            return
        L_0x000a:
            r0 = 1
            A = r0     // Catch:{ all -> 0x0034 }
            monitor-exit(r1)     // Catch:{ all -> 0x0034 }
            int r0 = com.uc.crashsdk.p.o()
            long r0 = (long) r0
            int r0 = com.uc.crashsdk.JNIBridge.nativeGenerateUnexpLog(r0)
            if (r0 == 0) goto L_0x0029
            r1 = 11
            com.uc.crashsdk.k.a(r1)
            r1 = 2
            if (r0 != r1) goto L_0x0037
            r0 = 10
            com.uc.crashsdk.k.a(r0)
        L_0x0026:
            c(r2)
        L_0x0029:
            java.lang.Object r1 = C
            monitor-enter(r1)
            r0 = 0
            B = r0     // Catch:{ all -> 0x0031 }
            monitor-exit(r1)     // Catch:{ all -> 0x0031 }
            goto L_0x0009
        L_0x0031:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0031 }
            throw r0
        L_0x0034:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0034 }
            throw r0
        L_0x0037:
            r1 = 3
            if (r0 != r1) goto L_0x0040
            r0 = 29
            com.uc.crashsdk.k.a(r0)
            goto L_0x0026
        L_0x0040:
            r1 = 4
            if (r0 != r1) goto L_0x0049
            r0 = 30
            com.uc.crashsdk.k.a(r0)
            goto L_0x0026
        L_0x0049:
            r1 = 5
            if (r0 != r1) goto L_0x0026
            r0 = 31
            com.uc.crashsdk.k.a(r0)
            goto L_0x0026
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.crashsdk.f.s():void");
    }

    public f() {
        try {
            v();
        } catch (Throwable th) {
            com.uc.crashsdk.a.a.a(th, false);
        }
    }

    private void v() {
        int D = p.D();
        int i2 = 0;
        while (i2 < D) {
            try {
                this.f.add(new FileInputStream("/dev/null"));
                i2++;
            } catch (Exception e2) {
                com.uc.crashsdk.a.a.a(e2, false);
                return;
            }
        }
    }

    private static String f(String str) {
        try {
            return str.replaceAll("[^0-9a-zA-Z-.]", "-");
        } catch (Throwable th) {
            return "unknown";
        }
    }

    private static String w() {
        return p.e() + "_";
    }

    public static void a() {
        h = null;
    }

    public static String b() {
        if (h != null) {
            return h;
        }
        String g2 = g((String) null);
        h = g2;
        return g2;
    }

    private static String g(String str) {
        if (str == null) {
            str = String.valueOf(System.currentTimeMillis());
        }
        return String.format(Locale.US, "%s%s_%s_%s_%s_%s_", new Object[]{w(), p.K(), p.M(), f(Build.MODEL), f(VERSION.RELEASE), str});
    }

    private static String x() {
        return b.o() ? "fg" : "bg";
    }

    private static byte[] y() {
        byte[] bArr = null;
        int i2 = 1024;
        while (bArr == null && i2 > 0) {
            try {
                bArr = new byte[i2];
            } catch (Throwable th) {
                i2 /= 2;
                if (i2 < 16) {
                    break;
                }
            }
        }
        return bArr;
    }

    private static String h(String str) {
        return String.format(Locale.US, "%s%s_%s_%s%s.log", new Object[]{b(), g(), x(), str, a(p.H())});
    }

    static String a(String str) {
        if (h.a(str)) {
            return "";
        }
        if (str.length() > j) {
            str = str.substring(0, j);
        }
        String f2 = f(str);
        return !f2.startsWith(".") ? "." + f2 : f2;
    }

    static void b(String str) {
        i.a(0, new d(str));
    }

    private static String z() {
        if (!b.s() || e) {
            return LogType.JAVA_TYPE;
        }
        return "ucebujava";
    }

    private static void c(boolean z2) {
        int i2;
        int i3;
        try {
            if (b.n()) {
                File[] listFiles = new File(p.O()).listFiles();
                if (listFiles != null) {
                    int l2 = p.l();
                    int m2 = p.m();
                    if (listFiles.length >= Math.min(l2, m2)) {
                        int i4 = 0;
                        int i5 = 0;
                        for (File b2 : listFiles) {
                            if (b(b2)) {
                                i5++;
                            } else {
                                i4++;
                            }
                        }
                        if (!z2 || i5 < l2) {
                            i2 = 0;
                        } else {
                            i2 = (i5 - l2) + 1;
                        }
                        if (z2 || i4 < m2) {
                            i3 = 0;
                        } else {
                            i3 = (i4 - m2) + 1;
                        }
                        if (i2 != 0 || i3 != 0) {
                            Arrays.sort(listFiles, new c(0));
                            int i6 = i3;
                            int i7 = i2;
                            for (File file : listFiles) {
                                boolean b3 = b(file);
                                if (b3 && i7 > 0) {
                                    com.uc.crashsdk.a.c.b("Delete oldest crash log: " + file.getPath());
                                    file.delete();
                                    i7--;
                                } else if (!b3 && i6 > 0) {
                                    com.uc.crashsdk.a.c.b("Delete oldest custom log: " + file.getPath());
                                    file.delete();
                                    i6--;
                                }
                                if (i7 == 0 && i6 == 0) {
                                    break;
                                }
                            }
                            k.a(16, i2 + i3);
                            if (i2 > 0) {
                                k.a(22, i2);
                            }
                            if (i3 > 0) {
                                k.a(23, i3);
                            }
                        }
                    }
                }
            }
        } catch (Throwable th) {
            com.uc.crashsdk.a.a.a(th, false);
        }
    }

    private static void a(OutputStream outputStream, String str, String str2) {
        try {
            outputStream.write("*** *** *** *** *** *** *** *** *** *** *** *** *** *** *** ***\n".getBytes("UTF-8"));
        } catch (Throwable th) {
            a(th, outputStream);
        }
        String str3 = "-";
        try {
            str3 = Build.HARDWARE;
        } catch (Throwable th2) {
            com.uc.crashsdk.a.a.a(th2, false);
        }
        try {
            outputStream.write(String.format(Locale.US, "Basic Information: 'pid: %d/tid: %d/logver: 2/time: %s/cpu: %s/cpu hardware: %s'\n", new Object[]{Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()), g(), Build.CPU_ABI, str3}).getBytes("UTF-8"));
        } catch (Throwable th3) {
            a(th3, outputStream);
        }
        try {
            outputStream.write(String.format(Locale.US, "Mobile Information: 'model: %s/version: %s/sdk: %d'\n", new Object[]{Build.MODEL, VERSION.RELEASE, Integer.valueOf(VERSION.SDK_INT)}).getBytes("UTF-8"));
            outputStream.write(("Build fingerprint: '" + Build.FINGERPRINT + "'\n").getBytes("UTF-8"));
            outputStream.write(String.format("Runtime Information: 'start: %s/maxheap: %s'\n", new Object[]{a(new Date(c)), Long.valueOf(Runtime.getRuntime().maxMemory())}).getBytes("UTF-8"));
        } catch (Throwable th4) {
            a(th4, outputStream);
        }
        try {
            outputStream.write(String.format("Application Information: 'version: %s/subversion: %s/buildseq: %s'\n", new Object[]{p.K(), p.L(), p.M()}).getBytes("UTF-8"));
            String str4 = "0";
            if (b.d) {
                str4 = JNIBridge.nativeGetNativeBuildseq();
            }
            outputStream.write(String.format("CrashSDK Information: 'version: %s/nativeseq: %s/javaseq: %s/target: %s'\n", new Object[]{"2.0.0.4", str4, "170706161743", "release"}).getBytes("UTF-8"));
            if (str == null) {
                str = "";
            }
            outputStream.write(("Report Name: " + str.substring(str.lastIndexOf(47) + 1) + "\n").getBytes("UTF-8"));
        } catch (Throwable th5) {
            a(th5, outputStream);
        }
        try {
            outputStream.write(("UUID: " + a + "\n").getBytes("UTF-8"));
            outputStream.write(("Log Type: " + str2 + "\n").getBytes("UTF-8"));
        } catch (Throwable th6) {
            a(th6, outputStream);
        }
        a(outputStream);
        try {
            a.a(outputStream, (String) "UTF-8");
        } catch (Throwable th7) {
            a(th7, outputStream);
        }
        a(outputStream);
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String c() {
        /*
            r7 = 0
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00f5 }
            r1.<init>()     // Catch:{ Throwable -> 0x00f5 }
            java.lang.String r0 = "JavaMax:    "
            java.lang.StringBuilder r0 = r1.append(r0)     // Catch:{ Throwable -> 0x00f5 }
            java.lang.Runtime r2 = java.lang.Runtime.getRuntime()     // Catch:{ Throwable -> 0x00f5 }
            long r2 = r2.maxMemory()     // Catch:{ Throwable -> 0x00f5 }
            r4 = 1024(0x400, double:5.06E-321)
            long r2 = r2 / r4
            java.lang.StringBuilder r0 = r0.append(r2)     // Catch:{ Throwable -> 0x00f5 }
            java.lang.String r2 = " kB\n"
            r0.append(r2)     // Catch:{ Throwable -> 0x00f5 }
            java.lang.String r0 = "JavaTotal:  "
            java.lang.StringBuilder r0 = r1.append(r0)     // Catch:{ Throwable -> 0x00f5 }
            java.lang.Runtime r2 = java.lang.Runtime.getRuntime()     // Catch:{ Throwable -> 0x00f5 }
            long r2 = r2.totalMemory()     // Catch:{ Throwable -> 0x00f5 }
            r4 = 1024(0x400, double:5.06E-321)
            long r2 = r2 / r4
            java.lang.StringBuilder r0 = r0.append(r2)     // Catch:{ Throwable -> 0x00f5 }
            java.lang.String r2 = " kB\n"
            r0.append(r2)     // Catch:{ Throwable -> 0x00f5 }
            java.lang.String r0 = "JavaFree:   "
            java.lang.StringBuilder r0 = r1.append(r0)     // Catch:{ Throwable -> 0x00f5 }
            java.lang.Runtime r2 = java.lang.Runtime.getRuntime()     // Catch:{ Throwable -> 0x00f5 }
            long r2 = r2.freeMemory()     // Catch:{ Throwable -> 0x00f5 }
            r4 = 1024(0x400, double:5.06E-321)
            long r2 = r2 / r4
            java.lang.StringBuilder r0 = r0.append(r2)     // Catch:{ Throwable -> 0x00f5 }
            java.lang.String r2 = " kB\n"
            r0.append(r2)     // Catch:{ Throwable -> 0x00f5 }
            java.lang.String r0 = "NativeHeap: "
            java.lang.StringBuilder r0 = r1.append(r0)     // Catch:{ Throwable -> 0x00f5 }
            long r2 = android.os.Debug.getNativeHeapSize()     // Catch:{ Throwable -> 0x00f5 }
            r4 = 1024(0x400, double:5.06E-321)
            long r2 = r2 / r4
            java.lang.StringBuilder r0 = r0.append(r2)     // Catch:{ Throwable -> 0x00f5 }
            java.lang.String r2 = " kB\n"
            r0.append(r2)     // Catch:{ Throwable -> 0x00f5 }
            java.lang.String r0 = "NativeAllocated: "
            java.lang.StringBuilder r0 = r1.append(r0)     // Catch:{ Throwable -> 0x00f5 }
            long r2 = android.os.Debug.getNativeHeapAllocatedSize()     // Catch:{ Throwable -> 0x00f5 }
            r4 = 1024(0x400, double:5.06E-321)
            long r2 = r2 / r4
            java.lang.StringBuilder r0 = r0.append(r2)     // Catch:{ Throwable -> 0x00f5 }
            java.lang.String r2 = " kB\n"
            r0.append(r2)     // Catch:{ Throwable -> 0x00f5 }
            java.lang.String r0 = "NativeFree: "
            java.lang.StringBuilder r0 = r1.append(r0)     // Catch:{ Throwable -> 0x00f5 }
            long r2 = android.os.Debug.getNativeHeapFreeSize()     // Catch:{ Throwable -> 0x00f5 }
            r4 = 1024(0x400, double:5.06E-321)
            long r2 = r2 / r4
            java.lang.StringBuilder r0 = r0.append(r2)     // Catch:{ Throwable -> 0x00f5 }
            java.lang.String r2 = " kB\n"
            r0.append(r2)     // Catch:{ Throwable -> 0x00f5 }
            android.content.Context r0 = com.uc.crashsdk.e.a()     // Catch:{ Throwable -> 0x00ef }
            java.lang.String r2 = "activity"
            java.lang.Object r0 = r0.getSystemService(r2)     // Catch:{ Throwable -> 0x00ef }
            android.app.ActivityManager r0 = (android.app.ActivityManager) r0     // Catch:{ Throwable -> 0x00ef }
            if (r0 == 0) goto L_0x00ea
            android.app.ActivityManager$MemoryInfo r2 = new android.app.ActivityManager$MemoryInfo     // Catch:{ Throwable -> 0x00ef }
            r2.<init>()     // Catch:{ Throwable -> 0x00ef }
            r0.getMemoryInfo(r2)     // Catch:{ Throwable -> 0x00ef }
            java.lang.String r0 = "\n"
            r1.append(r0)     // Catch:{ Throwable -> 0x00ef }
            java.lang.String r0 = "availMem:   "
            java.lang.StringBuilder r0 = r1.append(r0)     // Catch:{ Throwable -> 0x00ef }
            long r3 = r2.availMem     // Catch:{ Throwable -> 0x00ef }
            r5 = 1024(0x400, double:5.06E-321)
            long r3 = r3 / r5
            java.lang.StringBuilder r0 = r0.append(r3)     // Catch:{ Throwable -> 0x00ef }
            java.lang.String r3 = " kB\n"
            r0.append(r3)     // Catch:{ Throwable -> 0x00ef }
            java.lang.String r0 = "threshold:  "
            java.lang.StringBuilder r0 = r1.append(r0)     // Catch:{ Throwable -> 0x00ef }
            long r3 = r2.threshold     // Catch:{ Throwable -> 0x00ef }
            r5 = 1024(0x400, double:5.06E-321)
            long r3 = r3 / r5
            java.lang.StringBuilder r0 = r0.append(r3)     // Catch:{ Throwable -> 0x00ef }
            java.lang.String r3 = " kB\n"
            r0.append(r3)     // Catch:{ Throwable -> 0x00ef }
            java.lang.String r0 = "lowMemory:  "
            java.lang.StringBuilder r0 = r1.append(r0)     // Catch:{ Throwable -> 0x00ef }
            boolean r2 = r2.lowMemory     // Catch:{ Throwable -> 0x00ef }
            java.lang.StringBuilder r0 = r0.append(r2)     // Catch:{ Throwable -> 0x00ef }
            java.lang.String r2 = "\n"
            r0.append(r2)     // Catch:{ Throwable -> 0x00ef }
        L_0x00ea:
            java.lang.String r0 = r1.toString()     // Catch:{ Throwable -> 0x00f5 }
        L_0x00ee:
            return r0
        L_0x00ef:
            r0 = move-exception
            r2 = 0
            com.uc.crashsdk.a.a.a(r0, r2)     // Catch:{ Throwable -> 0x00f5 }
            goto L_0x00ea
        L_0x00f5:
            r0 = move-exception
            com.uc.crashsdk.a.a.a(r0, r7)
            java.lang.String r0 = "exception exists."
            goto L_0x00ee
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.crashsdk.f.c():java.lang.String");
    }

    public static String a(String str, String str2) {
        boolean z2;
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec(new String[]{H5Param.PRESSO_LOGIN}).getInputStream()));
            boolean b2 = h.b(str);
            boolean b3 = h.b(str2);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    return byteArrayOutputStream.toString("UTF-8");
                }
                if ((b2 && readLine.indexOf(str) >= 0) || (b3 && readLine.indexOf(str2) >= 0)) {
                    z2 = true;
                } else if (readLine.indexOf(47) >= 0 || readLine.indexOf(46) <= 0) {
                    z2 = false;
                } else {
                    z2 = true;
                }
                if (z2) {
                    byteArrayOutputStream.write(readLine.getBytes("UTF-8"));
                    byteArrayOutputStream.write("\n".getBytes("UTF-8"));
                }
            }
        } catch (Throwable th) {
            com.uc.crashsdk.a.a.a(th, false);
            return "exception exists.";
        }
    }

    private static BufferedReader a(InputStreamReader inputStreamReader) {
        BufferedReader bufferedReader = null;
        int i2 = 8192;
        while (bufferedReader == null && i2 > 0) {
            try {
                bufferedReader = new BufferedReader(inputStreamReader, i2);
            } catch (Throwable th) {
                i2 /= 2;
                if (i2 < 512) {
                    break;
                }
            }
        }
        return bufferedReader;
    }

    private static void a(OutputStream outputStream) {
        try {
            outputStream.write("--- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---\n".getBytes("UTF-8"));
        } catch (Throwable th) {
            a(th, outputStream);
        }
    }

    /* JADX WARNING: Unknown top exception splitter block from list: {B:25:0x0097=Splitter:B:25:0x0097, B:7:0x001e=Splitter:B:7:0x001e} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void b(java.io.OutputStream r7) {
        /*
            r0 = 0
            r1 = 0
            java.lang.String r2 = "logcat: \n"
            java.lang.String r3 = "UTF-8"
            byte[] r2 = r2.getBytes(r3)     // Catch:{ Throwable -> 0x0027 }
            r7.write(r2)     // Catch:{ Throwable -> 0x0027 }
            int r2 = com.uc.crashsdk.p.n()     // Catch:{ Throwable -> 0x0027 }
            if (r2 > 0) goto L_0x0037
            java.lang.String r0 = "[DEBUG] custom java logcat lines count is 0!\n"
            java.lang.String r2 = "UTF-8"
            byte[] r0 = r0.getBytes(r2)     // Catch:{ Throwable -> 0x0022 }
            r7.write(r0)     // Catch:{ Throwable -> 0x0022 }
        L_0x001e:
            a(r7)     // Catch:{ Throwable -> 0x0027 }
        L_0x0021:
            return
        L_0x0022:
            r0 = move-exception
            a(r0, r7)     // Catch:{ Throwable -> 0x0027 }
            goto L_0x001e
        L_0x0027:
            r0 = move-exception
            r2 = 1
            g = r2     // Catch:{ all -> 0x00ab }
            a(r0, r7)     // Catch:{ all -> 0x00ab }
            if (r1 == 0) goto L_0x0033
            r1.close()     // Catch:{ Throwable -> 0x010e }
        L_0x0033:
            a(r7)
            goto L_0x0021
        L_0x0037:
            int r3 = com.uc.crashsdk.p.n()     // Catch:{ Throwable -> 0x0027 }
            java.lang.Runtime r2 = java.lang.Runtime.getRuntime()     // Catch:{ Throwable -> 0x0027 }
            r4 = 10
            java.lang.String[] r4 = new java.lang.String[r4]     // Catch:{ Throwable -> 0x0027 }
            r5 = 0
            java.lang.String r6 = "logcat"
            r4[r5] = r6     // Catch:{ Throwable -> 0x0027 }
            r5 = 1
            java.lang.String r6 = "-d"
            r4[r5] = r6     // Catch:{ Throwable -> 0x0027 }
            r5 = 2
            java.lang.String r6 = "-b"
            r4[r5] = r6     // Catch:{ Throwable -> 0x0027 }
            r5 = 3
            java.lang.String r6 = "events"
            r4[r5] = r6     // Catch:{ Throwable -> 0x0027 }
            r5 = 4
            java.lang.String r6 = "-b"
            r4[r5] = r6     // Catch:{ Throwable -> 0x0027 }
            r5 = 5
            java.lang.String r6 = "main"
            r4[r5] = r6     // Catch:{ Throwable -> 0x0027 }
            r5 = 6
            java.lang.String r6 = "-v"
            r4[r5] = r6     // Catch:{ Throwable -> 0x0027 }
            r5 = 7
            java.lang.String r6 = "threadtime"
            r4[r5] = r6     // Catch:{ Throwable -> 0x0027 }
            r5 = 8
            java.lang.String r6 = "-t"
            r4[r5] = r6     // Catch:{ Throwable -> 0x0027 }
            r5 = 9
            java.lang.String r6 = java.lang.String.valueOf(r3)     // Catch:{ Throwable -> 0x0027 }
            r4[r5] = r6     // Catch:{ Throwable -> 0x0027 }
            java.lang.Process r2 = r2.exec(r4)     // Catch:{ Throwable -> 0x0027 }
            java.io.InputStreamReader r4 = new java.io.InputStreamReader     // Catch:{ Throwable -> 0x0027 }
            java.io.InputStream r2 = r2.getInputStream()     // Catch:{ Throwable -> 0x0027 }
            r4.<init>(r2)     // Catch:{ Throwable -> 0x0027 }
            java.io.BufferedReader r1 = a(r4)     // Catch:{ Throwable -> 0x0027 }
            if (r1 != 0) goto L_0x00b2
            java.lang.String r0 = "[DEBUG] alloc buffer failed!\n"
            java.lang.String r2 = "UTF-8"
            byte[] r0 = r0.getBytes(r2)     // Catch:{ Throwable -> 0x00a6 }
            r7.write(r0)     // Catch:{ Throwable -> 0x00a6 }
        L_0x0097:
            a(r7)     // Catch:{ Throwable -> 0x0027 }
            if (r1 == 0) goto L_0x0021
            r1.close()     // Catch:{ Throwable -> 0x00a0 }
            goto L_0x0021
        L_0x00a0:
            r0 = move-exception
            a(r0, r7)
            goto L_0x0021
        L_0x00a6:
            r0 = move-exception
            a(r0, r7)     // Catch:{ Throwable -> 0x0027 }
            goto L_0x0097
        L_0x00ab:
            r0 = move-exception
            if (r1 == 0) goto L_0x00b1
            r1.close()     // Catch:{ Throwable -> 0x0114 }
        L_0x00b1:
            throw r0
        L_0x00b2:
            r2 = 0
            g = r2     // Catch:{ Throwable -> 0x0027 }
            r2 = r0
        L_0x00b6:
            java.lang.String r4 = r1.readLine()     // Catch:{ Throwable -> 0x0027 }
            if (r4 == 0) goto L_0x00d7
            int r0 = r0 + 1
            if (r2 >= r3) goto L_0x00b6
            java.lang.String r5 = "UTF-8"
            byte[] r4 = r4.getBytes(r5)     // Catch:{ Throwable -> 0x0027 }
            r7.write(r4)     // Catch:{ Throwable -> 0x0027 }
            java.lang.String r4 = "\n"
            java.lang.String r5 = "UTF-8"
            byte[] r4 = r4.getBytes(r5)     // Catch:{ Throwable -> 0x0027 }
            r7.write(r4)     // Catch:{ Throwable -> 0x0027 }
            int r2 = r2 + 1
            goto L_0x00b6
        L_0x00d7:
            java.util.Locale r3 = java.util.Locale.US     // Catch:{ Throwable -> 0x0109 }
            java.lang.String r4 = "[DEBUG] Read %d lines, wrote %d lines.\n"
            r5 = 2
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ Throwable -> 0x0109 }
            r6 = 0
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ Throwable -> 0x0109 }
            r5[r6] = r0     // Catch:{ Throwable -> 0x0109 }
            r0 = 1
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch:{ Throwable -> 0x0109 }
            r5[r0] = r2     // Catch:{ Throwable -> 0x0109 }
            java.lang.String r0 = java.lang.String.format(r3, r4, r5)     // Catch:{ Throwable -> 0x0109 }
            java.lang.String r2 = "UTF-8"
            byte[] r0 = r0.getBytes(r2)     // Catch:{ Throwable -> 0x0109 }
            r7.write(r0)     // Catch:{ Throwable -> 0x0109 }
        L_0x00f9:
            r0 = 1
            g = r0     // Catch:{ Throwable -> 0x0027 }
            if (r1 == 0) goto L_0x0033
            r1.close()     // Catch:{ Throwable -> 0x0103 }
            goto L_0x0033
        L_0x0103:
            r0 = move-exception
            a(r0, r7)
            goto L_0x0033
        L_0x0109:
            r0 = move-exception
            a(r0, r7)     // Catch:{ Throwable -> 0x0027 }
            goto L_0x00f9
        L_0x010e:
            r0 = move-exception
            a(r0, r7)
            goto L_0x0033
        L_0x0114:
            r1 = move-exception
            a(r1, r7)
            goto L_0x00b1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.crashsdk.f.b(java.io.OutputStream):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x003f A[Catch:{ Throwable -> 0x0066 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void c(java.io.OutputStream r6) {
        /*
            r3 = 0
            java.lang.String r0 = "disk info:\n"
            r1 = 0
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ Throwable -> 0x005b }
            java.lang.String r0 = java.lang.String.format(r0, r1)     // Catch:{ Throwable -> 0x005b }
            java.lang.String r1 = "UTF-8"
            byte[] r0 = r0.getBytes(r1)     // Catch:{ Throwable -> 0x005b }
            r6.write(r0)     // Catch:{ Throwable -> 0x005b }
        L_0x0013:
            r2 = 0
            java.util.HashSet r1 = new java.util.HashSet     // Catch:{ Throwable -> 0x0060 }
            r1.<init>()     // Catch:{ Throwable -> 0x0060 }
            java.io.File r0 = new java.io.File     // Catch:{ Throwable -> 0x006e }
            java.lang.String r2 = com.uc.crashsdk.e.a     // Catch:{ Throwable -> 0x006e }
            r0.<init>(r2)     // Catch:{ Throwable -> 0x006e }
            java.lang.String r0 = a(r0)     // Catch:{ Throwable -> 0x006e }
            a(r6, r0, r1)     // Catch:{ Throwable -> 0x006e }
        L_0x0027:
            java.io.File r0 = android.os.Environment.getExternalStorageDirectory()     // Catch:{ Throwable -> 0x0066 }
            java.lang.String r0 = a(r0)     // Catch:{ Throwable -> 0x0066 }
            a(r6, r0, r1)     // Catch:{ Throwable -> 0x0066 }
            java.io.File r0 = new java.io.File     // Catch:{ Throwable -> 0x0066 }
            java.lang.String r2 = "/storage"
            r0.<init>(r2)     // Catch:{ Throwable -> 0x0066 }
            boolean r2 = r0.exists()     // Catch:{ Throwable -> 0x0066 }
            if (r2 == 0) goto L_0x006a
            java.io.File[] r2 = r0.listFiles()     // Catch:{ Throwable -> 0x0066 }
            if (r2 == 0) goto L_0x006a
            int r4 = r2.length     // Catch:{ Throwable -> 0x0066 }
            r0 = r3
        L_0x0047:
            if (r0 >= r4) goto L_0x006a
            r3 = r2[r0]     // Catch:{ Throwable -> 0x0066 }
            boolean r5 = r3.isDirectory()     // Catch:{ Throwable -> 0x0066 }
            if (r5 == 0) goto L_0x0058
            java.lang.String r3 = a(r3)     // Catch:{ Throwable -> 0x0066 }
            a(r6, r3, r1)     // Catch:{ Throwable -> 0x0066 }
        L_0x0058:
            int r0 = r0 + 1
            goto L_0x0047
        L_0x005b:
            r0 = move-exception
            a(r0, r6)
            goto L_0x0013
        L_0x0060:
            r0 = move-exception
            r1 = r2
        L_0x0062:
            a(r0, r6)
            goto L_0x0027
        L_0x0066:
            r0 = move-exception
            a(r0, r6)
        L_0x006a:
            a(r6)
            return
        L_0x006e:
            r0 = move-exception
            goto L_0x0062
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.crashsdk.f.c(java.io.OutputStream):void");
    }

    private static String a(File file) {
        String str = null;
        try {
            str = file.getCanonicalPath();
        } catch (Throwable th) {
        }
        if (h.a(str)) {
            return file.getPath();
        }
        return str;
    }

    private static void a(OutputStream outputStream, String str, Set<String> set) {
        if (!h.a(str) && !set.contains(str) && !str.equals("/storage/emulated")) {
            set.add(str);
            try {
                StatFs statFs = new StatFs(str);
                long a2 = a(statFs, (String) "getBlockCountLong", (String) "getBlockCount");
                long a3 = a(statFs, (String) "getBlockSizeLong", (String) "getBlockSize");
                if ((a2 / 1024) * a3 >= 10240) {
                    long a4 = a(statFs, (String) "getAvailableBlocksLong", (String) "getAvailableBlocks");
                    long a5 = a(statFs, (String) "getFreeBlocksLong", (String) "getFreeBlocks");
                    try {
                        outputStream.write(String.format("%s:\n", new Object[]{str}).getBytes("UTF-8"));
                        outputStream.write(String.format("  total:      %d kB\n", new Object[]{Long.valueOf((long) (((((double) a2) * 1.0d) * ((double) a3)) / 1024.0d))}).getBytes("UTF-8"));
                        outputStream.write(String.format("  available:  %d kB\n", new Object[]{Long.valueOf((long) (((((double) a4) * 1.0d) * ((double) a3)) / 1024.0d))}).getBytes("UTF-8"));
                        outputStream.write(String.format("  free:       %d kB\n", new Object[]{Long.valueOf((long) (((1.0d * ((double) a5)) * ((double) a3)) / 1024.0d))}).getBytes("UTF-8"));
                        outputStream.write(String.format("  block size: %d B\n\n", new Object[]{Long.valueOf(a3)}).getBytes("UTF-8"));
                    } catch (Throwable th) {
                        a(th, outputStream);
                    }
                }
            } catch (Throwable th2) {
            }
        }
    }

    private static long a(StatFs statFs, String str, String str2) {
        try {
            if (VERSION.SDK_INT >= 18) {
                Method declaredMethod = StatFs.class.getDeclaredMethod(str, new Class[0]);
                declaredMethod.setAccessible(true);
                Object invoke = declaredMethod.invoke(statFs, new Object[0]);
                if (invoke != null && (invoke instanceof Long)) {
                    return ((Long) invoke).longValue();
                }
            }
        } catch (Throwable th) {
        }
        try {
            Method declaredMethod2 = StatFs.class.getDeclaredMethod(str2, new Class[0]);
            declaredMethod2.setAccessible(true);
            Object invoke2 = declaredMethod2.invoke(statFs, new Object[0]);
            if (invoke2 != null && (invoke2 instanceof Integer)) {
                return (long) ((Integer) invoke2).intValue();
            }
        } catch (Throwable th2) {
            com.uc.crashsdk.a.a.a(th2, false);
        }
        return 0;
    }

    private static void d(OutputStream outputStream) {
        File[] fileArr = null;
        int i2 = 900;
        try {
            i2 = p.E();
            fileArr = new File("/proc/self/fd").listFiles();
            if (fileArr != null) {
                outputStream.write(String.format(Locale.US, "opened file count: %d, write limit: %d.\n", new Object[]{Integer.valueOf(fileArr.length), Integer.valueOf(i2)}).getBytes("UTF-8"));
            } else {
                outputStream.write("[DEBUG] listFiles failed!\n".getBytes("UTF-8"));
            }
        } catch (Throwable th) {
            a(th, outputStream);
        }
        if (fileArr != null) {
            try {
                if (fileArr.length >= i2) {
                    outputStream.write("opened files:\n".getBytes("UTF-8"));
                    StringBuilder sb = new StringBuilder();
                    for (File file : fileArr) {
                        sb.append(file.getName());
                        sb.append(" -> ");
                        sb.append(file.getCanonicalPath());
                        sb.append("\n");
                    }
                    outputStream.write(sb.toString().getBytes("UTF-8"));
                }
            } catch (Throwable th2) {
                a(th2, outputStream);
            }
        }
        a(outputStream);
    }

    private static void e(OutputStream outputStream) {
        int i2;
        File[] fileArr = null;
        int i3 = 300;
        try {
            i3 = p.F();
            fileArr = new File("/proc/self/task").listFiles();
            if (fileArr != null) {
                i2 = fileArr.length;
                if (i2 < i3) {
                    return;
                }
                if (fileArr != null) {
                    try {
                        outputStream.write("threads info:\n".getBytes("UTF-8"));
                        outputStream.write(String.format(Locale.US, "threads count: %d, dump limit: %d.\n", new Object[]{Integer.valueOf(i2), Integer.valueOf(i3)}).getBytes("UTF-8"));
                        outputStream.write(" tid     name\n".getBytes("UTF-8"));
                        for (File file : fileArr) {
                            outputStream.write(String.format(Locale.US, "%5s %s\n", new Object[]{file.getName(), i(com.uc.crashsdk.a.b.a(new File(file.getPath(), "comm"), 128, true))}).getBytes("UTF-8"));
                        }
                    } catch (Throwable th) {
                        a(th, outputStream);
                    }
                    a(outputStream);
                }
            }
        } catch (Throwable th2) {
            com.uc.crashsdk.a.a.a(th2, false);
            i2 = 0;
        }
    }

    private static void a(b bVar) {
        try {
            bVar.a(String.format("log end: %s\n", new Object[]{g()}).getBytes("UTF-8"));
        } catch (Throwable th) {
            a(th, (OutputStream) bVar);
        }
    }

    public static void a(OutputStream outputStream, String str) {
        if (str == null) {
            a(outputStream);
            return;
        }
        try {
            String a2 = com.uc.crashsdk.a.d.a(str);
            if (a2 == null) {
                a2 = "file: '" + str + "' not found or decode failed!";
            }
            outputStream.write(a2.getBytes("UTF-8"));
            outputStream.write("\n".getBytes("UTF-8"));
        } catch (Throwable th) {
            a(th, outputStream);
        }
        a(outputStream);
    }

    /* JADX WARNING: Removed duplicated region for block: B:36:0x0083 A[SYNTHETIC, Splitter:B:36:0x0083] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void b(java.io.OutputStream r6, java.lang.String r7) {
        /*
            r5 = 0
            r1 = 0
            java.io.File r0 = new java.io.File     // Catch:{ Throwable -> 0x0079 }
            r0.<init>(r7)     // Catch:{ Throwable -> 0x0079 }
            boolean r2 = r0.exists()     // Catch:{ Throwable -> 0x0079 }
            if (r2 == 0) goto L_0x005a
            byte[] r3 = y()     // Catch:{ Throwable -> 0x0079 }
            if (r3 != 0) goto L_0x001f
            java.lang.String r0 = "[DEBUG] alloc buffer failed!\n"
            java.lang.String r2 = "UTF-8"
            byte[] r0 = r0.getBytes(r2)     // Catch:{ Throwable -> 0x0079 }
            r6.write(r0)     // Catch:{ Throwable -> 0x0079 }
        L_0x001e:
            return
        L_0x001f:
            java.io.DataInputStream r2 = new java.io.DataInputStream     // Catch:{ Throwable -> 0x0079 }
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x0079 }
            r4.<init>(r0)     // Catch:{ Throwable -> 0x0079 }
            r2.<init>(r4)     // Catch:{ Throwable -> 0x0079 }
        L_0x0029:
            int r0 = r2.read(r3)     // Catch:{ Throwable -> 0x0035, all -> 0x008c }
            r1 = -1
            if (r0 == r1) goto L_0x0043
            r1 = 0
            r6.write(r3, r1, r0)     // Catch:{ Throwable -> 0x0035, all -> 0x008c }
            goto L_0x0029
        L_0x0035:
            r0 = move-exception
            r1 = r2
        L_0x0037:
            a(r0, r6)     // Catch:{ all -> 0x0080 }
            if (r1 == 0) goto L_0x003f
            r1.close()     // Catch:{ Throwable -> 0x007b }
        L_0x003f:
            a(r6)
            goto L_0x001e
        L_0x0043:
            r1 = r2
        L_0x0044:
            java.lang.String r0 = "\n"
            java.lang.String r2 = "UTF-8"
            byte[] r0 = r0.getBytes(r2)     // Catch:{ Throwable -> 0x0079 }
            r6.write(r0)     // Catch:{ Throwable -> 0x0079 }
            if (r1 == 0) goto L_0x003f
            r1.close()     // Catch:{ Throwable -> 0x0055 }
            goto L_0x003f
        L_0x0055:
            r0 = move-exception
            com.uc.crashsdk.a.a.a(r0, r5)
            goto L_0x003f
        L_0x005a:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0079 }
            java.lang.String r2 = "file: '"
            r0.<init>(r2)     // Catch:{ Throwable -> 0x0079 }
            java.lang.StringBuilder r0 = r0.append(r7)     // Catch:{ Throwable -> 0x0079 }
            java.lang.String r2 = "' not exists!\n"
            java.lang.StringBuilder r0 = r0.append(r2)     // Catch:{ Throwable -> 0x0079 }
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x0079 }
            java.lang.String r2 = "UTF-8"
            byte[] r0 = r0.getBytes(r2)     // Catch:{ Throwable -> 0x0079 }
            r6.write(r0)     // Catch:{ Throwable -> 0x0079 }
            goto L_0x0044
        L_0x0079:
            r0 = move-exception
            goto L_0x0037
        L_0x007b:
            r0 = move-exception
            com.uc.crashsdk.a.a.a(r0, r5)
            goto L_0x003f
        L_0x0080:
            r0 = move-exception
        L_0x0081:
            if (r1 == 0) goto L_0x0086
            r1.close()     // Catch:{ Throwable -> 0x0087 }
        L_0x0086:
            throw r0
        L_0x0087:
            r1 = move-exception
            com.uc.crashsdk.a.a.a(r1, r5)
            goto L_0x0086
        L_0x008c:
            r0 = move-exception
            r1 = r2
            goto L_0x0081
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.crashsdk.f.b(java.io.OutputStream, java.lang.String):void");
    }

    private static void f(OutputStream outputStream) {
        try {
            outputStream.write("meminfo:\n".getBytes("UTF-8"));
            b(outputStream, (String) "/proc/meminfo");
        } catch (Throwable th) {
            a(th, outputStream);
        }
        try {
            String format = String.format(Locale.US, "/proc/%d/status", new Object[]{Integer.valueOf(Process.myPid())});
            outputStream.write("status:\n".getBytes("UTF-8"));
            b(outputStream, format);
        } catch (Throwable th2) {
            a(th2, outputStream);
        }
        try {
            outputStream.write(("Dalvik:\nMaxMemory: " + Runtime.getRuntime().maxMemory() + " TotalMemory: " + Runtime.getRuntime().totalMemory() + " FreeMemory: " + Runtime.getRuntime().freeMemory() + "\n").getBytes("UTF-8"));
        } catch (Throwable th3) {
            a(th3, outputStream);
        }
        a(outputStream);
    }

    public static String a(int i2) {
        try {
            String a2 = com.uc.crashsdk.a.b.a(new File(String.format(Locale.US, "/proc/%d/cmdline", new Object[]{Integer.valueOf(i2)})), 128, true);
            if (h.b(a2)) {
                return i(a2);
            }
        } catch (Throwable th) {
            com.uc.crashsdk.a.a.a(th, false);
        }
        return "unknown";
    }

    private static String i(String str) {
        if (!h.b(str)) {
            return str;
        }
        int indexOf = str.indexOf(0);
        if (indexOf >= 0) {
            str = str.substring(0, indexOf);
        }
        return str.trim();
    }

    public static String d() {
        if (l != null) {
            return l;
        }
        String a2 = a(Process.myPid());
        l = a2;
        return a2;
    }

    private static boolean a(String str, String str2, int i2) {
        if (b.d) {
            return JNIBridge.nativeSyncStatus(str, str2, i2);
        }
        return false;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:102:?, code lost:
        r3.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:104:0x0189, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:107:?, code lost:
        com.uc.crashsdk.a.a.a(r0, false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:108:0x0190, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:111:?, code lost:
        com.uc.crashsdk.a.a.a(r0, false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:112:0x0197, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:115:?, code lost:
        com.uc.crashsdk.a.a.a(r0, false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:116:0x019e, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:117:0x019f, code lost:
        com.uc.crashsdk.a.a.a(r0, false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:118:0x01a5, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:119:0x01a6, code lost:
        com.uc.crashsdk.a.a.a(r0, false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:120:0x01ac, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:121:0x01ad, code lost:
        com.uc.crashsdk.a.a.a(r0, false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:122:0x01b3, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:123:0x01b4, code lost:
        com.uc.crashsdk.a.a.a(r0, false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:124:0x01ba, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:125:0x01bb, code lost:
        com.uc.crashsdk.a.a.a(r0, false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:126:0x01c1, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:127:0x01c2, code lost:
        a(r0, (java.io.OutputStream) r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:128:0x01c7, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:129:0x01c8, code lost:
        com.uc.crashsdk.a.a.a(r0, false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:132:0x01d5, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:133:0x01d6, code lost:
        a(r0, (java.io.OutputStream) r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:134:0x01db, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:135:0x01dc, code lost:
        com.uc.crashsdk.a.a.a(r0, false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:136:0x01e2, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:137:0x01e3, code lost:
        a(r0, (java.io.OutputStream) r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:138:0x01e8, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:139:0x01e9, code lost:
        com.uc.crashsdk.a.a.a(r0, false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:142:0x01f6, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:143:0x01f7, code lost:
        com.uc.crashsdk.a.a.a(r1, false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:146:0x0201, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:147:0x0202, code lost:
        r3 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:152:0x020d, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:153:0x020e, code lost:
        r7 = r0;
        r0 = r1;
        r1 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x0168, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x0169, code lost:
        r2 = r3;
        r7 = r1;
        r1 = r0;
        r0 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:?, code lost:
        r2.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:93:0x0176, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:0x0177, code lost:
        com.uc.crashsdk.a.a.a(r1, false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:0x017b, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:98:?, code lost:
        com.uc.crashsdk.a.a.a(r0, false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:99:0x0182, code lost:
        r0 = th;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:101:0x0185 A[SYNTHETIC, Splitter:B:101:0x0185] */
    /* JADX WARNING: Removed duplicated region for block: B:146:0x0201 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:1:0x0003] */
    /* JADX WARNING: Removed duplicated region for block: B:91:0x0172 A[SYNTHETIC, Splitter:B:91:0x0172] */
    /* JADX WARNING: Removed duplicated region for block: B:99:0x0182 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:14:0x0045] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String a(java.lang.Throwable r8, boolean r9) {
        /*
            r2 = 0
            r6 = 0
            r0 = 1
            c(r0)     // Catch:{ Throwable -> 0x0208, all -> 0x0201 }
            java.lang.String r0 = com.uc.crashsdk.p.g()     // Catch:{ Throwable -> 0x0208, all -> 0x0201 }
            if (r0 == 0) goto L_0x0014
            java.lang.String r1 = ""
            boolean r1 = r0.equals(r1)     // Catch:{ Throwable -> 0x0208, all -> 0x0201 }
            if (r1 == 0) goto L_0x001c
        L_0x0014:
            java.lang.String r0 = z()     // Catch:{ Throwable -> 0x0208, all -> 0x0201 }
            java.lang.String r0 = h(r0)     // Catch:{ Throwable -> 0x0208, all -> 0x0201 }
        L_0x001c:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0208, all -> 0x0201 }
            r1.<init>()     // Catch:{ Throwable -> 0x0208, all -> 0x0201 }
            java.lang.String r3 = com.uc.crashsdk.p.O()     // Catch:{ Throwable -> 0x0208, all -> 0x0201 }
            java.lang.StringBuilder r1 = r1.append(r3)     // Catch:{ Throwable -> 0x0208, all -> 0x0201 }
            java.lang.StringBuilder r0 = r1.append(r0)     // Catch:{ Throwable -> 0x0208, all -> 0x0201 }
            java.lang.String r1 = r0.toString()     // Catch:{ Throwable -> 0x0208, all -> 0x0201 }
            com.uc.crashsdk.f$b r3 = new com.uc.crashsdk.f$b     // Catch:{ Throwable -> 0x020d, all -> 0x0201 }
            java.io.FileOutputStream r0 = new java.io.FileOutputStream     // Catch:{ Throwable -> 0x020d, all -> 0x0201 }
            r0.<init>(r1)     // Catch:{ Throwable -> 0x020d, all -> 0x0201 }
            r3.<init>(r0)     // Catch:{ Throwable -> 0x020d, all -> 0x0201 }
            boolean r0 = com.uc.crashsdk.b.d     // Catch:{ Throwable -> 0x0161, all -> 0x0182 }
            if (r0 == 0) goto L_0x0045
            java.lang.String r0 = "logj"
            r2 = 0
            a(r0, r1, r2)     // Catch:{ Throwable -> 0x0161, all -> 0x0182 }
        L_0x0045:
            java.lang.String r0 = z()     // Catch:{ Throwable -> 0x0168, all -> 0x0182 }
            a(r3, r1, r0)     // Catch:{ Throwable -> 0x0168, all -> 0x0182 }
            if (r9 == 0) goto L_0x0051
            r3.flush()     // Catch:{ Throwable -> 0x017b, all -> 0x0182 }
        L_0x0051:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0189, all -> 0x0182 }
            java.lang.String r2 = "Process Name: '"
            r0.<init>(r2)     // Catch:{ Throwable -> 0x0189, all -> 0x0182 }
            java.lang.String r2 = d()     // Catch:{ Throwable -> 0x0189, all -> 0x0182 }
            java.lang.StringBuilder r0 = r0.append(r2)     // Catch:{ Throwable -> 0x0189, all -> 0x0182 }
            java.lang.String r2 = "'\n"
            java.lang.StringBuilder r0 = r0.append(r2)     // Catch:{ Throwable -> 0x0189, all -> 0x0182 }
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x0189, all -> 0x0182 }
            java.lang.String r2 = "UTF-8"
            byte[] r0 = r0.getBytes(r2)     // Catch:{ Throwable -> 0x0189, all -> 0x0182 }
            r3.write(r0)     // Catch:{ Throwable -> 0x0189, all -> 0x0182 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0189, all -> 0x0182 }
            java.lang.String r2 = "Thread Name: '"
            r0.<init>(r2)     // Catch:{ Throwable -> 0x0189, all -> 0x0182 }
            java.lang.Thread r2 = java.lang.Thread.currentThread()     // Catch:{ Throwable -> 0x0189, all -> 0x0182 }
            java.lang.String r2 = r2.getName()     // Catch:{ Throwable -> 0x0189, all -> 0x0182 }
            java.lang.StringBuilder r0 = r0.append(r2)     // Catch:{ Throwable -> 0x0189, all -> 0x0182 }
            java.lang.String r2 = "'\n"
            java.lang.StringBuilder r0 = r0.append(r2)     // Catch:{ Throwable -> 0x0189, all -> 0x0182 }
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x0189, all -> 0x0182 }
            java.lang.String r2 = "UTF-8"
            byte[] r0 = r0.getBytes(r2)     // Catch:{ Throwable -> 0x0189, all -> 0x0182 }
            r3.write(r0)     // Catch:{ Throwable -> 0x0189, all -> 0x0182 }
        L_0x0099:
            java.lang.String r0 = "Back traces starts.\n"
            java.lang.String r2 = "UTF-8"
            byte[] r0 = r0.getBytes(r2)     // Catch:{ Throwable -> 0x0197, all -> 0x0182 }
            r3.write(r0)     // Catch:{ Throwable -> 0x0197, all -> 0x0182 }
            java.lang.Class<java.lang.Throwable> r0 = java.lang.Throwable.class
            java.lang.String r2 = "detailMessage"
            java.lang.reflect.Field r2 = r0.getDeclaredField(r2)     // Catch:{ Throwable -> 0x0190, all -> 0x0182 }
            r0 = 1
            r2.setAccessible(r0)     // Catch:{ Throwable -> 0x0190, all -> 0x0182 }
            java.lang.Object r0 = r2.get(r8)     // Catch:{ Throwable -> 0x0190, all -> 0x0182 }
            if (r0 == 0) goto L_0x00c3
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Throwable -> 0x0190, all -> 0x0182 }
            java.lang.String r4 = "\n\t"
            java.lang.String r5 = "\n->  "
            java.lang.String r0 = r0.replaceAll(r4, r5)     // Catch:{ Throwable -> 0x0190, all -> 0x0182 }
            r2.set(r8, r0)     // Catch:{ Throwable -> 0x0190, all -> 0x0182 }
        L_0x00c3:
            java.lang.String r0 = r8.getMessage()     // Catch:{ Throwable -> 0x0197, all -> 0x0182 }
            if (r0 == 0) goto L_0x00f1
            java.lang.String r2 = r8.getLocalizedMessage()     // Catch:{ Throwable -> 0x0197, all -> 0x0182 }
            boolean r2 = r0.equals(r2)     // Catch:{ Throwable -> 0x0197, all -> 0x0182 }
            if (r2 != 0) goto L_0x00f1
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0197, all -> 0x0182 }
            java.lang.String r4 = "Message: "
            r2.<init>(r4)     // Catch:{ Throwable -> 0x0197, all -> 0x0182 }
            java.lang.StringBuilder r0 = r2.append(r0)     // Catch:{ Throwable -> 0x0197, all -> 0x0182 }
            java.lang.String r2 = "\n"
            java.lang.StringBuilder r0 = r0.append(r2)     // Catch:{ Throwable -> 0x0197, all -> 0x0182 }
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x0197, all -> 0x0182 }
            java.lang.String r2 = "UTF-8"
            byte[] r0 = r0.getBytes(r2)     // Catch:{ Throwable -> 0x0197, all -> 0x0182 }
            r3.write(r0)     // Catch:{ Throwable -> 0x0197, all -> 0x0182 }
        L_0x00f1:
            java.io.PrintStream r0 = new java.io.PrintStream     // Catch:{ Throwable -> 0x019e, all -> 0x0182 }
            r0.<init>(r3)     // Catch:{ Throwable -> 0x019e, all -> 0x0182 }
            r8.printStackTrace(r0)     // Catch:{ Throwable -> 0x019e, all -> 0x0182 }
        L_0x00f9:
            java.lang.String r0 = "Back traces ends.\n"
            java.lang.String r2 = "UTF-8"
            byte[] r0 = r0.getBytes(r2)     // Catch:{ Throwable -> 0x01a5, all -> 0x0182 }
            r3.write(r0)     // Catch:{ Throwable -> 0x01a5, all -> 0x0182 }
        L_0x0104:
            a(r3)     // Catch:{ Throwable -> 0x0168, all -> 0x0182 }
            r3.flush()     // Catch:{ Throwable -> 0x01ac, all -> 0x0182 }
        L_0x010a:
            java.lang.String r0 = "UTF-8"
            java.lang.String r2 = "--- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---\n"
            com.uc.crashsdk.a.a(r3, r0, r2)     // Catch:{ Throwable -> 0x01b3, all -> 0x0182 }
        L_0x0111:
            if (r9 == 0) goto L_0x0116
            r3.flush()     // Catch:{ Throwable -> 0x01ba, all -> 0x0182 }
        L_0x0116:
            f(r3)     // Catch:{ Throwable -> 0x0168, all -> 0x0182 }
            g(r3)     // Catch:{ Throwable -> 0x0168, all -> 0x0182 }
            java.lang.String r0 = "UTF-8"
            r2 = 0
            com.uc.crashsdk.a.a(r3, r0, r2)     // Catch:{ Throwable -> 0x01c1, all -> 0x0182 }
        L_0x0122:
            r3.flush()     // Catch:{ Throwable -> 0x01c7, all -> 0x0182 }
        L_0x0125:
            b(r3)     // Catch:{ Throwable -> 0x0168, all -> 0x0182 }
            r3.flush()     // Catch:{ Throwable -> 0x01ce, all -> 0x0182 }
        L_0x012b:
            c(r3)     // Catch:{ Throwable -> 0x0168, all -> 0x0182 }
            d(r3)     // Catch:{ Throwable -> 0x0168, all -> 0x0182 }
            e(r3)     // Catch:{ Throwable -> 0x0168, all -> 0x0182 }
            java.lang.String r0 = "UTF-8"
            java.lang.String r2 = "--- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---\n"
            r4 = 0
            com.uc.crashsdk.a.b(r3, r0, r2, r4)     // Catch:{ Throwable -> 0x01d5, all -> 0x0182 }
        L_0x013c:
            r3.flush()     // Catch:{ Throwable -> 0x01db, all -> 0x0182 }
        L_0x013f:
            java.lang.String r0 = "UTF-8"
            java.lang.String r2 = "--- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---\n"
            r4 = 0
            com.uc.crashsdk.a.a(r3, r0, r2, r4)     // Catch:{ Throwable -> 0x01e2, all -> 0x0182 }
        L_0x0147:
            r3.a()     // Catch:{ Throwable -> 0x0168, all -> 0x0182 }
            a(r3)     // Catch:{ Throwable -> 0x0168, all -> 0x0182 }
            r3.flush()     // Catch:{ Throwable -> 0x01e8, all -> 0x0182 }
        L_0x0150:
            r3.close()     // Catch:{ Throwable -> 0x01ef }
            r0 = r1
        L_0x0154:
            m(r0)
            java.lang.String r1 = j(r0)     // Catch:{ Throwable -> 0x01fb }
            java.lang.String r2 = "java"
            b(r1, r2)     // Catch:{ Throwable -> 0x01fb }
        L_0x0160:
            return r0
        L_0x0161:
            r0 = move-exception
            r2 = 0
            com.uc.crashsdk.a.a.a(r0, r2)     // Catch:{ Throwable -> 0x0168, all -> 0x0182 }
            goto L_0x0045
        L_0x0168:
            r0 = move-exception
            r2 = r3
            r7 = r1
            r1 = r0
            r0 = r7
        L_0x016d:
            a(r1, r2)     // Catch:{ all -> 0x0204 }
            if (r2 == 0) goto L_0x0154
            r2.close()     // Catch:{ Throwable -> 0x0176 }
            goto L_0x0154
        L_0x0176:
            r1 = move-exception
            com.uc.crashsdk.a.a.a(r1, r6)
            goto L_0x0154
        L_0x017b:
            r0 = move-exception
            r2 = 0
            com.uc.crashsdk.a.a.a(r0, r2)     // Catch:{ Throwable -> 0x0168, all -> 0x0182 }
            goto L_0x0051
        L_0x0182:
            r0 = move-exception
        L_0x0183:
            if (r3 == 0) goto L_0x0188
            r3.close()     // Catch:{ Throwable -> 0x01f6 }
        L_0x0188:
            throw r0
        L_0x0189:
            r0 = move-exception
            r2 = 0
            com.uc.crashsdk.a.a.a(r0, r2)     // Catch:{ Throwable -> 0x0168, all -> 0x0182 }
            goto L_0x0099
        L_0x0190:
            r0 = move-exception
            r2 = 0
            com.uc.crashsdk.a.a.a(r0, r2)     // Catch:{ Throwable -> 0x0197, all -> 0x0182 }
            goto L_0x00c3
        L_0x0197:
            r0 = move-exception
            r2 = 0
            com.uc.crashsdk.a.a.a(r0, r2)     // Catch:{ Throwable -> 0x0168, all -> 0x0182 }
            goto L_0x00f1
        L_0x019e:
            r0 = move-exception
            r2 = 0
            com.uc.crashsdk.a.a.a(r0, r2)     // Catch:{ Throwable -> 0x0168, all -> 0x0182 }
            goto L_0x00f9
        L_0x01a5:
            r0 = move-exception
            r2 = 0
            com.uc.crashsdk.a.a.a(r0, r2)     // Catch:{ Throwable -> 0x0168, all -> 0x0182 }
            goto L_0x0104
        L_0x01ac:
            r0 = move-exception
            r2 = 0
            com.uc.crashsdk.a.a.a(r0, r2)     // Catch:{ Throwable -> 0x0168, all -> 0x0182 }
            goto L_0x010a
        L_0x01b3:
            r0 = move-exception
            r2 = 0
            com.uc.crashsdk.a.a.a(r0, r2)     // Catch:{ Throwable -> 0x0168, all -> 0x0182 }
            goto L_0x0111
        L_0x01ba:
            r0 = move-exception
            r2 = 0
            com.uc.crashsdk.a.a.a(r0, r2)     // Catch:{ Throwable -> 0x0168, all -> 0x0182 }
            goto L_0x0116
        L_0x01c1:
            r0 = move-exception
            a(r0, r3)     // Catch:{ Throwable -> 0x0168, all -> 0x0182 }
            goto L_0x0122
        L_0x01c7:
            r0 = move-exception
            r2 = 0
            com.uc.crashsdk.a.a.a(r0, r2)     // Catch:{ Throwable -> 0x0168, all -> 0x0182 }
            goto L_0x0125
        L_0x01ce:
            r0 = move-exception
            r2 = 0
            com.uc.crashsdk.a.a.a(r0, r2)     // Catch:{ Throwable -> 0x0168, all -> 0x0182 }
            goto L_0x012b
        L_0x01d5:
            r0 = move-exception
            a(r0, r3)     // Catch:{ Throwable -> 0x0168, all -> 0x0182 }
            goto L_0x013c
        L_0x01db:
            r0 = move-exception
            r2 = 0
            com.uc.crashsdk.a.a.a(r0, r2)     // Catch:{ Throwable -> 0x0168, all -> 0x0182 }
            goto L_0x013f
        L_0x01e2:
            r0 = move-exception
            a(r0, r3)     // Catch:{ Throwable -> 0x0168, all -> 0x0182 }
            goto L_0x0147
        L_0x01e8:
            r0 = move-exception
            r2 = 0
            com.uc.crashsdk.a.a.a(r0, r2)     // Catch:{ Throwable -> 0x0168, all -> 0x0182 }
            goto L_0x0150
        L_0x01ef:
            r0 = move-exception
            com.uc.crashsdk.a.a.a(r0, r6)
            r0 = r1
            goto L_0x0154
        L_0x01f6:
            r1 = move-exception
            com.uc.crashsdk.a.a.a(r1, r6)
            goto L_0x0188
        L_0x01fb:
            r1 = move-exception
            com.uc.crashsdk.a.a.a(r1, r6)
            goto L_0x0160
        L_0x0201:
            r0 = move-exception
            r3 = r2
            goto L_0x0183
        L_0x0204:
            r0 = move-exception
            r3 = r2
            goto L_0x0183
        L_0x0208:
            r0 = move-exception
            r1 = r0
            r0 = r2
            goto L_0x016d
        L_0x020d:
            r0 = move-exception
            r7 = r0
            r0 = r1
            r1 = r7
            goto L_0x016d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.crashsdk.f.a(java.lang.Throwable, boolean):java.lang.String");
    }

    private static void g(OutputStream outputStream) {
        try {
            outputStream.write("Recent Status:\n".getBytes("UTF-8"));
        } catch (Throwable th) {
            a(th, outputStream);
        }
        try {
            outputStream.write(String.format("last version: '%s'\n", new Object[]{a.g()}).getBytes("UTF-8"));
        } catch (Throwable th2) {
            a(th2, outputStream);
        }
        try {
            synchronized (q) {
                if (s != null) {
                    outputStream.write(String.format("generating log: %s\n", new Object[]{s.toString()}).getBytes("UTF-8"));
                }
                if (r > 0 || q.size() > 0) {
                    outputStream.write(String.format(Locale.US, "generated %d logs, recent are:\n", new Object[]{Integer.valueOf(r)}).getBytes("UTF-8"));
                    Iterator<String> it = q.iterator();
                    while (it.hasNext()) {
                        outputStream.write(String.format("* %s\n", new Object[]{it.next()}).getBytes("UTF-8"));
                    }
                }
            }
            outputStream.write(String.format("dumping all threads: %s\n", new Object[]{Boolean.valueOf(t)}).getBytes("UTF-8"));
            if (u != null) {
                outputStream.write(String.format("dumping threads: %s\n", new Object[]{u}).getBytes("UTF-8"));
            }
        } catch (Throwable th3) {
            a(th3, outputStream);
        }
        a(outputStream);
    }

    private static String j(String str) {
        String a2 = com.uc.crashsdk.a.d.a(str, p.v(), p.u());
        if (!str.equals(a2)) {
            File file = new File(str);
            if (file.exists()) {
                file.delete();
            }
        }
        return a2;
    }

    public static void a(Throwable th, OutputStream outputStream) {
        if (outputStream != null) {
            try {
                outputStream.write("[DEBUG] CrashHandler occurred new exception:\n".getBytes("UTF-8"));
                th.printStackTrace(new PrintStream(outputStream));
                outputStream.write("\n\n".getBytes("UTF-8"));
            } catch (Throwable th2) {
                com.uc.crashsdk.a.a.a(th2, false);
            }
        }
        com.uc.crashsdk.a.a.a(th, false);
    }

    /* JADX WARNING: Removed duplicated region for block: B:104:0x0380  */
    /* JADX WARNING: Removed duplicated region for block: B:137:0x042b  */
    /* JADX WARNING: Removed duplicated region for block: B:141:0x0436  */
    /* JADX WARNING: Removed duplicated region for block: B:142:0x0443  */
    /* JADX WARNING: Removed duplicated region for block: B:143:0x0446  */
    /* JADX WARNING: Removed duplicated region for block: B:147:0x03d2 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x01bd  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x01cb  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x01d8  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x01db  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x0208 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x0224  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void a(java.lang.String r26, boolean r27, boolean r28) {
        /*
            java.lang.String r2 = com.uc.crashsdk.p.O()
            java.io.File r4 = new java.io.File
            r4.<init>(r2)
            boolean r3 = r4.exists()
            if (r3 != 0) goto L_0x0022
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "Folder not exist: "
            r3.<init>(r4)
            java.lang.StringBuilder r2 = r3.append(r2)
            java.lang.String r2 = r2.toString()
            com.uc.crashsdk.a.c.b(r2)
        L_0x0021:
            return
        L_0x0022:
            r13 = 0
            r12 = 0
            r3 = 0
            r11 = 0
            r6 = 0
            r10 = 0
            r9 = 0
            r8 = 0
            java.io.File[] r15 = r4.listFiles()
            if (r15 != 0) goto L_0x0043
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "List folder failed: "
            r3.<init>(r4)
            java.lang.StringBuilder r2 = r3.append(r2)
            java.lang.String r2 = r2.toString()
            com.uc.crashsdk.a.c.c(r2)
            goto L_0x0021
        L_0x0043:
            r7 = 0
            int r0 = r15.length
            r16 = r0
            r2 = 0
            r14 = r2
        L_0x0049:
            r0 = r16
            if (r14 >= r0) goto L_0x0456
            r5 = r15[r14]
            boolean r2 = r5.isFile()
            if (r2 != 0) goto L_0x0072
            com.uc.crashsdk.a.b.a(r5)
            r2 = r7
            r4 = r9
            r5 = r10
            r7 = r11
            r9 = r12
            r10 = r13
            r24 = r8
            r8 = r3
            r3 = r24
        L_0x0063:
            int r11 = r14 + 1
            r14 = r11
            r12 = r9
            r13 = r10
            r9 = r4
            r10 = r5
            r11 = r7
            r7 = r2
            r24 = r8
            r8 = r3
            r3 = r24
            goto L_0x0049
        L_0x0072:
            if (r28 == 0) goto L_0x0080
            java.lang.String r2 = r5.getName()
            java.lang.String r4 = "ucebu"
            boolean r2 = r2.contains(r4)
            if (r2 == 0) goto L_0x0449
        L_0x0080:
            long r17 = r5.length()
            r19 = 0
            int r2 = (r17 > r19 ? 1 : (r17 == r19 ? 0 : -1))
            if (r2 != 0) goto L_0x009b
            int r2 = r13 + 1
            com.uc.crashsdk.a.b.a(r5)
            r4 = r9
            r5 = r10
            r9 = r12
            r10 = r2
            r2 = r7
            r7 = r11
            r24 = r3
            r3 = r8
            r8 = r24
            goto L_0x0063
        L_0x009b:
            if (r27 == 0) goto L_0x0112
            long r17 = java.lang.System.currentTimeMillis()
            long r19 = r5.lastModified()
            long r17 = r17 - r19
            r19 = 1000(0x3e8, double:4.94E-321)
            long r17 = r17 / r19
            r2 = 1
            r19 = 0
            int r4 = (r17 > r19 ? 1 : (r17 == r19 ? 0 : -1))
            if (r4 < 0) goto L_0x00b9
            r19 = 2
            int r4 = (r17 > r19 ? 1 : (r17 == r19 ? 0 : -1))
            if (r4 >= 0) goto L_0x00fc
            r2 = 0
        L_0x00b9:
            java.util.Locale r4 = java.util.Locale.US
            java.lang.String r19 = "file: %s, modify interval: %d s, safe upload: %s"
            r20 = 3
            r0 = r20
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r20 = r0
            r21 = 0
            java.lang.String r22 = r5.getName()
            r20[r21] = r22
            r21 = 1
            java.lang.Long r17 = java.lang.Long.valueOf(r17)
            r20[r21] = r17
            r17 = 2
            java.lang.Boolean r18 = java.lang.Boolean.valueOf(r2)
            r20[r17] = r18
            r0 = r19
            r1 = r20
            java.lang.String r4 = java.lang.String.format(r4, r0, r1)
            com.uc.crashsdk.a.c.a(r4)
            if (r2 != 0) goto L_0x0112
            int r2 = r11 + 1
            r4 = r9
            r5 = r10
            r9 = r12
            r10 = r13
            r24 = r2
            r2 = r7
            r7 = r24
            r25 = r3
            r3 = r8
            r8 = r25
            goto L_0x0063
        L_0x00fc:
            r19 = 5
            int r4 = (r17 > r19 ? 1 : (r17 == r19 ? 0 : -1))
            if (r4 >= 0) goto L_0x00b9
            java.lang.String r4 = r5.getName()
            java.lang.String r19 = ".log"
            r0 = r19
            boolean r4 = r4.endsWith(r0)
            if (r4 == 0) goto L_0x00b9
            r2 = 0
            goto L_0x00b9
        L_0x0112:
            boolean r2 = com.uc.crashsdk.p.k()     // Catch:{ Throwable -> 0x0201 }
            if (r2 == 0) goto L_0x01ff
            java.lang.String r2 = r5.getName()     // Catch:{ Throwable -> 0x0201 }
            java.lang.String r4 = "([^_]+)_([^_]+)_([^_]+)\\.crashsdk"
            java.util.regex.Pattern r4 = java.util.regex.Pattern.compile(r4)     // Catch:{ Throwable -> 0x0201 }
            java.util.regex.Matcher r2 = r4.matcher(r2)     // Catch:{ Throwable -> 0x0201 }
            boolean r4 = r2.matches()     // Catch:{ Throwable -> 0x0201 }
            if (r4 == 0) goto L_0x01ff
            r4 = 1
            java.lang.String r4 = r2.group(r4)     // Catch:{ Throwable -> 0x0201 }
            r17 = 2
            r0 = r17
            java.lang.String r17 = r2.group(r0)     // Catch:{ Throwable -> 0x0201 }
            r18 = 3
            r0 = r18
            java.lang.String r2 = r2.group(r0)     // Catch:{ Throwable -> 0x0201 }
            java.util.Locale r18 = java.util.Locale.US     // Catch:{ Throwable -> 0x0201 }
            java.lang.String r19 = "%s%s_%s_%s.%s"
            r20 = 5
            r0 = r20
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ Throwable -> 0x0201 }
            r20 = r0
            r21 = 0
            java.lang.String r17 = g(r17)     // Catch:{ Throwable -> 0x0201 }
            r20[r21] = r17     // Catch:{ Throwable -> 0x0201 }
            r17 = 1
            java.lang.String r21 = g()     // Catch:{ Throwable -> 0x0201 }
            r20[r17] = r21     // Catch:{ Throwable -> 0x0201 }
            r17 = 2
            java.lang.String r21 = x()     // Catch:{ Throwable -> 0x0201 }
            r20[r17] = r21     // Catch:{ Throwable -> 0x0201 }
            r17 = 3
            r20[r17] = r4     // Catch:{ Throwable -> 0x0201 }
            r4 = 4
            r20[r4] = r2     // Catch:{ Throwable -> 0x0201 }
            java.lang.String r2 = java.lang.String.format(r18, r19, r20)     // Catch:{ Throwable -> 0x0201 }
            java.io.File r4 = new java.io.File     // Catch:{ Throwable -> 0x0201 }
            java.lang.StringBuilder r17 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0201 }
            r17.<init>()     // Catch:{ Throwable -> 0x0201 }
            java.lang.String r18 = com.uc.crashsdk.p.O()     // Catch:{ Throwable -> 0x0201 }
            java.lang.StringBuilder r17 = r17.append(r18)     // Catch:{ Throwable -> 0x0201 }
            r0 = r17
            java.lang.StringBuilder r2 = r0.append(r2)     // Catch:{ Throwable -> 0x0201 }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x0201 }
            r4.<init>(r2)     // Catch:{ Throwable -> 0x0201 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0201 }
            java.lang.String r17 = "File "
            r0 = r17
            r2.<init>(r0)     // Catch:{ Throwable -> 0x0201 }
            java.lang.String r17 = r5.getPath()     // Catch:{ Throwable -> 0x0201 }
            r0 = r17
            java.lang.StringBuilder r2 = r2.append(r0)     // Catch:{ Throwable -> 0x0201 }
            java.lang.String r17 = " matches, rename to "
            r0 = r17
            java.lang.StringBuilder r2 = r2.append(r0)     // Catch:{ Throwable -> 0x0201 }
            java.lang.String r17 = r4.getPath()     // Catch:{ Throwable -> 0x0201 }
            r0 = r17
            java.lang.StringBuilder r2 = r2.append(r0)     // Catch:{ Throwable -> 0x0201 }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x0201 }
            com.uc.crashsdk.a.c.b(r2)     // Catch:{ Throwable -> 0x0201 }
            r5.renameTo(r4)     // Catch:{ Throwable -> 0x0201 }
        L_0x01bb:
            if (r4 == r5) goto L_0x0446
            int r2 = r3 + 1
        L_0x01bf:
            r3 = r2
            r2 = r4
        L_0x01c1:
            java.lang.String r4 = r2.getPath()
            java.lang.String r5 = k(r4)
            if (r4 == r5) goto L_0x0443
            int r4 = r6 + 1
            java.io.File r2 = new java.io.File
            r2.<init>(r5)
        L_0x01d2:
            java.io.File r5 = com.uc.crashsdk.d.a(r2)
            if (r5 != 0) goto L_0x0208
            r5 = 0
        L_0x01d9:
            if (r5 != 0) goto L_0x0224
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "onBeforeUploadLog return null, skip upload: "
            r5.<init>(r6)
            java.lang.String r2 = r2.getAbsolutePath()
            java.lang.StringBuilder r2 = r5.append(r2)
            java.lang.String r2 = r2.toString()
            com.uc.crashsdk.a.c.c(r2)
            r2 = r7
            r5 = r10
            r6 = r4
            r4 = r9
            r7 = r11
            r10 = r13
            r9 = r12
            r24 = r3
            r3 = r8
            r8 = r24
            goto L_0x0063
        L_0x01ff:
            r4 = r5
            goto L_0x01bb
        L_0x0201:
            r2 = move-exception
            r4 = 0
            com.uc.crashsdk.a.a.a(r2, r4)
            r2 = r5
            goto L_0x01c1
        L_0x0208:
            if (r2 == r5) goto L_0x01d9
            java.lang.String r6 = r2.getName()
            java.lang.String r17 = r5.getName()
            r0 = r17
            boolean r6 = r6.equals(r0)
            if (r6 != 0) goto L_0x01d9
            boolean r6 = r2.exists()
            if (r6 == 0) goto L_0x01d9
            r2.delete()
            goto L_0x01d9
        L_0x0224:
            int r2 = com.uc.crashsdk.p.x()
            if (r2 <= 0) goto L_0x0248
            long r17 = r5.length()
            long r0 = (long) r2
            r19 = r0
            int r2 = (r17 > r19 ? 1 : (r17 == r19 ? 0 : -1))
            if (r2 < 0) goto L_0x0248
            int r2 = r12 + 1
            com.uc.crashsdk.a.b.a(r5)
            r5 = r10
            r6 = r4
            r4 = r9
            r10 = r13
            r9 = r2
            r2 = r7
            r7 = r11
            r24 = r8
            r8 = r3
            r3 = r24
            goto L_0x0063
        L_0x0248:
            com.uc.crashsdk.f$e r6 = new com.uc.crashsdk.f$e
            r6.<init>()
            r17 = 0
            r0 = r17
            r6.b = r0
            long r17 = java.lang.System.currentTimeMillis()
            r0 = r17
            r6.a = r0
            java.lang.String r2 = A()
            java.io.File r17 = new java.io.File
            r0 = r17
            r0.<init>(r2)
            boolean r18 = r17.exists()
            if (r18 == 0) goto L_0x027a
            com.uc.crashsdk.g r18 = new com.uc.crashsdk.g
            r0 = r18
            r1 = r17
            r0.<init>(r2, r6, r1)
            r0 = r18
            a(r2, r0)
        L_0x027a:
            long r17 = com.uc.crashsdk.p.y()
            int r2 = com.uc.crashsdk.p.z()
            int r19 = com.uc.crashsdk.p.A()
            r20 = 0
            int r20 = (r17 > r20 ? 1 : (r17 == r20 ? 0 : -1))
            if (r20 < 0) goto L_0x02c6
            long r0 = r6.b
            r20 = r0
            long r22 = r5.length()
            long r20 = r20 + r22
            int r20 = (r20 > r17 ? 1 : (r20 == r17 ? 0 : -1))
            if (r20 <= 0) goto L_0x02c6
            r2 = 1
            r6.e = r2
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r19 = "Reach max upload bytes: "
            r0 = r19
            r2.<init>(r0)
            r0 = r17
            java.lang.StringBuilder r2 = r2.append(r0)
            java.lang.String r2 = r2.toString()
            com.uc.crashsdk.a.c.c(r2)
        L_0x02b3:
            boolean r2 = r6.e
            if (r2 == 0) goto L_0x0319
            r2 = 1
            r5 = r2
            r6 = r4
            r10 = r13
            r2 = r7
            r4 = r9
            r7 = r11
            r9 = r12
            r24 = r3
            r3 = r8
            r8 = r24
            goto L_0x0063
        L_0x02c6:
            boolean r17 = com.uc.crashsdk.p.f()
            if (r17 != 0) goto L_0x02b3
            boolean r17 = b(r5)
            if (r17 == 0) goto L_0x02f7
            if (r2 < 0) goto L_0x02b3
            int r0 = r6.c
            r17 = r0
            r0 = r17
            if (r0 < r2) goto L_0x02b3
            r17 = 1
            r0 = r17
            r6.g = r0
            java.lang.StringBuilder r17 = new java.lang.StringBuilder
            java.lang.String r18 = "Reach max upload crash log count: "
            r17.<init>(r18)
            r0 = r17
            java.lang.StringBuilder r2 = r0.append(r2)
            java.lang.String r2 = r2.toString()
            com.uc.crashsdk.a.c.c(r2)
            goto L_0x02b3
        L_0x02f7:
            if (r19 < 0) goto L_0x02b3
            int r2 = r6.d
            r0 = r19
            if (r2 < r0) goto L_0x02b3
            r2 = 1
            r6.f = r2
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r17 = "Reach max upload custom log count: "
            r0 = r17
            r2.<init>(r0)
            r0 = r19
            java.lang.StringBuilder r2 = r2.append(r0)
            java.lang.String r2 = r2.toString()
            com.uc.crashsdk.a.c.c(r2)
            goto L_0x02b3
        L_0x0319:
            boolean r2 = r6.g
            if (r2 == 0) goto L_0x032c
            r2 = 1
            r5 = r10
            r6 = r4
            r9 = r12
            r4 = r2
            r10 = r13
            r2 = r7
            r7 = r11
            r24 = r3
            r3 = r8
            r8 = r24
            goto L_0x0063
        L_0x032c:
            boolean r2 = r6.f
            if (r2 == 0) goto L_0x033c
            r2 = 1
            r5 = r10
            r6 = r4
            r8 = r3
            r3 = r2
            r4 = r9
            r10 = r13
            r2 = r7
            r9 = r12
            r7 = r11
            goto L_0x0063
        L_0x033c:
            java.lang.String r2 = r5.getName()
            java.lang.String r17 = w()
            r0 = r17
            boolean r17 = r2.startsWith(r0)
            if (r17 == 0) goto L_0x041e
            java.lang.String r17 = "_"
            r0 = r17
            java.lang.String[] r2 = r2.split(r0)
            int r0 = r2.length
            r17 = r0
            r18 = 9
            r0 = r17
            r1 = r18
            if (r0 != r1) goto L_0x041e
            r17 = 1
            r2 = r2[r17]
        L_0x0363:
            if (r2 == 0) goto L_0x0421
            java.lang.String r17 = com.uc.crashsdk.p.K()
            r0 = r17
            boolean r2 = r2.equals(r0)
            if (r2 == 0) goto L_0x0421
            r2 = 1
        L_0x0372:
            java.lang.String r17 = r5.getName()
            r0 = r17
            r1 = r26
            boolean r17 = com.uc.crashsdk.q.a(r5, r0, r1)
            if (r17 == 0) goto L_0x042b
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            java.lang.String r17 = "Uploaded log: "
            r0 = r17
            r7.<init>(r0)
            java.lang.String r17 = r5.getName()
            r0 = r17
            java.lang.StringBuilder r7 = r7.append(r0)
            java.lang.String r7 = r7.toString()
            java.lang.String r17 = "crashsdk"
            r0 = r17
            com.uc.crashsdk.a.c.a(r0, r7)
            if (r2 == 0) goto L_0x03a5
            r2 = 13
            com.uc.crashsdk.k.a(r2)
        L_0x03a5:
            long r0 = r6.b
            r17 = r0
            long r19 = r5.length()
            long r17 = r17 + r19
            r0 = r17
            r6.b = r0
            boolean r2 = b(r5)
            if (r2 == 0) goto L_0x0424
            int r2 = r6.c
            int r2 = r2 + 1
            r6.c = r2
        L_0x03bf:
            java.lang.String r2 = A()
            com.uc.crashsdk.h r7 = new com.uc.crashsdk.h
            r7.<init>(r2, r6)
            a(r2, r7)
            r5.delete()
            r2 = 0
        L_0x03cf:
            r5 = 3
            if (r2 < r5) goto L_0x0436
            java.lang.String r2 = "Upload failed 3 times continuously, abort upload!"
            java.lang.String r5 = "crashsdk"
            com.uc.crashsdk.a.c.a(r5, r2)
        L_0x03d9:
            if (r13 <= 0) goto L_0x03e0
            r2 = 15
            com.uc.crashsdk.k.a(r2, r13)
        L_0x03e0:
            if (r12 <= 0) goto L_0x03e7
            r2 = 17
            com.uc.crashsdk.k.a(r2, r12)
        L_0x03e7:
            if (r10 == 0) goto L_0x03ee
            r2 = 19
            com.uc.crashsdk.k.a(r2)
        L_0x03ee:
            if (r9 == 0) goto L_0x03f5
            r2 = 20
            com.uc.crashsdk.k.a(r2)
        L_0x03f5:
            if (r8 == 0) goto L_0x03fc
            r2 = 21
            com.uc.crashsdk.k.a(r2)
        L_0x03fc:
            if (r10 != 0) goto L_0x0402
            if (r9 != 0) goto L_0x0402
            if (r8 == 0) goto L_0x0407
        L_0x0402:
            r2 = 18
            com.uc.crashsdk.k.a(r2)
        L_0x0407:
            if (r4 <= 0) goto L_0x040e
            r2 = 24
            com.uc.crashsdk.k.a(r2, r4)
        L_0x040e:
            if (r3 <= 0) goto L_0x0415
            r2 = 25
            com.uc.crashsdk.k.a(r2, r3)
        L_0x0415:
            if (r11 <= 0) goto L_0x0021
            r2 = 26
            com.uc.crashsdk.k.a(r2, r11)
            goto L_0x0021
        L_0x041e:
            r2 = 0
            goto L_0x0363
        L_0x0421:
            r2 = 0
            goto L_0x0372
        L_0x0424:
            int r2 = r6.d
            int r2 = r2 + 1
            r6.d = r2
            goto L_0x03bf
        L_0x042b:
            int r5 = r7 + 1
            if (r2 == 0) goto L_0x0434
            r2 = 14
            com.uc.crashsdk.k.a(r2)
        L_0x0434:
            r2 = r5
            goto L_0x03cf
        L_0x0436:
            r5 = r10
            r6 = r4
            r7 = r11
            r4 = r9
            r10 = r13
            r9 = r12
            r24 = r3
            r3 = r8
            r8 = r24
            goto L_0x0063
        L_0x0443:
            r4 = r6
            goto L_0x01d2
        L_0x0446:
            r2 = r3
            goto L_0x01bf
        L_0x0449:
            r2 = r7
            r4 = r9
            r5 = r10
            r7 = r11
            r9 = r12
            r10 = r13
            r24 = r8
            r8 = r3
            r3 = r24
            goto L_0x0063
        L_0x0456:
            r4 = r6
            goto L_0x03d9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.crashsdk.f.a(java.lang.String, boolean, boolean):void");
    }

    public static boolean e() {
        return e;
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean a(boolean r7) {
        /*
            r1 = 1
            r0 = 0
            if (r7 == 0) goto L_0x000d
            e = r1
            boolean r2 = com.uc.crashsdk.b.d
            if (r2 == 0) goto L_0x000d
            com.uc.crashsdk.JNIBridge.nativeSetCrashLogFilesUploaded()
        L_0x000d:
            java.lang.String r2 = f()     // Catch:{ Throwable -> 0x0047 }
            boolean r3 = com.uc.crashsdk.a.h.a(r2)     // Catch:{ Throwable -> 0x0047 }
            if (r3 == 0) goto L_0x001d
            java.lang.String r1 = "CrashHandler url is empty!"
            com.uc.crashsdk.a.c.b(r1)     // Catch:{ Throwable -> 0x0047 }
        L_0x001c:
            return r0
        L_0x001d:
            java.lang.Object r4 = m     // Catch:{ Throwable -> 0x0047 }
            monitor-enter(r4)     // Catch:{ Throwable -> 0x0047 }
            if (r7 != 0) goto L_0x003c
            r3 = r1
        L_0x0023:
            com.uc.crashsdk.f$a r5 = new com.uc.crashsdk.f$a     // Catch:{ all -> 0x0044 }
            r6 = 0
            r5.<init>(r2, r7, r3, r6)     // Catch:{ all -> 0x0044 }
            if (r7 == 0) goto L_0x004c
            r2 = r0
        L_0x002c:
            boolean r2 = com.uc.crashsdk.a.i.a(r2, r5)     // Catch:{ all -> 0x0044 }
            if (r2 == 0) goto L_0x0039
            if (r3 == 0) goto L_0x0039
            java.lang.Object r2 = m     // Catch:{ InterruptedException -> 0x003e }
            r2.wait()     // Catch:{ InterruptedException -> 0x003e }
        L_0x0039:
            monitor-exit(r4)     // Catch:{ all -> 0x0044 }
            r0 = r1
            goto L_0x001c
        L_0x003c:
            r3 = r0
            goto L_0x0023
        L_0x003e:
            r2 = move-exception
            r3 = 0
            com.uc.crashsdk.a.a.a(r2, r3)     // Catch:{ all -> 0x0044 }
            goto L_0x0039
        L_0x0044:
            r1 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0044 }
            throw r1     // Catch:{ Throwable -> 0x0047 }
        L_0x0047:
            r1 = move-exception
            com.uc.crashsdk.a.a.a(r1, r0)
            goto L_0x001c
        L_0x004c:
            r2 = r1
            goto L_0x002c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.crashsdk.f.a(boolean):boolean");
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void b(boolean r6) {
        /*
            r5 = 0
            boolean r0 = com.uc.crashsdk.p.r()     // Catch:{ Throwable -> 0x004a }
            if (r0 != 0) goto L_0x0008
        L_0x0007:
            return
        L_0x0008:
            java.lang.String r0 = com.uc.crashsdk.a.a     // Catch:{ Throwable -> 0x004a }
            java.lang.String r1 = d()     // Catch:{ Throwable -> 0x004a }
            boolean r0 = r0.equals(r1)     // Catch:{ Throwable -> 0x004a }
            if (r0 == 0) goto L_0x0007
            boolean r0 = com.uc.crashsdk.b.s()     // Catch:{ Throwable -> 0x004a }
            if (r0 != 0) goto L_0x0007
            boolean r0 = e     // Catch:{ Throwable -> 0x004a }
            if (r0 != 0) goto L_0x0007
            if (r6 == 0) goto L_0x0055
            java.lang.String r1 = f()     // Catch:{ Throwable -> 0x004a }
            boolean r0 = com.uc.crashsdk.a.h.a(r1)     // Catch:{ Throwable -> 0x004a }
            if (r0 != 0) goto L_0x0007
            android.os.StrictMode$ThreadPolicy r0 = android.os.StrictMode.getThreadPolicy()     // Catch:{ Throwable -> 0x004f }
            android.os.StrictMode$ThreadPolicy$Builder r2 = new android.os.StrictMode$ThreadPolicy$Builder     // Catch:{ Throwable -> 0x004f }
            r2.<init>(r0)     // Catch:{ Throwable -> 0x004f }
            android.os.StrictMode$ThreadPolicy$Builder r0 = r2.permitNetwork()     // Catch:{ Throwable -> 0x004f }
            android.os.StrictMode$ThreadPolicy r0 = r0.build()     // Catch:{ Throwable -> 0x004f }
            android.os.StrictMode.setThreadPolicy(r0)     // Catch:{ Throwable -> 0x004f }
        L_0x003e:
            com.uc.crashsdk.f$a r0 = new com.uc.crashsdk.f$a     // Catch:{ Throwable -> 0x004a }
            r2 = 0
            r3 = 0
            r4 = 0
            r0.<init>(r1, r2, r3, r4)     // Catch:{ Throwable -> 0x004a }
            r0.run()     // Catch:{ Throwable -> 0x004a }
            goto L_0x0007
        L_0x004a:
            r0 = move-exception
            com.uc.crashsdk.a.a.a(r0, r5)
            goto L_0x0007
        L_0x004f:
            r0 = move-exception
            r2 = 0
            com.uc.crashsdk.a.a.a(r0, r2)     // Catch:{ Throwable -> 0x004a }
            goto L_0x003e
        L_0x0055:
            r0 = 0
            a(r0)     // Catch:{ Throwable -> 0x004a }
            goto L_0x0007
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.crashsdk.f.b(boolean):void");
    }

    private static String A() {
        return p.N() + "bytes";
    }

    /* JADX WARNING: Code restructure failed: missing block: B:37:0x004a, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x004b, code lost:
        r6 = r1;
        r1 = r0;
        r0 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x005e, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:?, code lost:
        com.uc.crashsdk.a.a.a(r1, false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x0064, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x0065, code lost:
        r6 = r1;
        r1 = r0;
        r0 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x006e, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x006f, code lost:
        if (r1 != 0) goto L_0x0071;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:?, code lost:
        r1.release();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:?, code lost:
        throw r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x0075, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x0076, code lost:
        com.uc.crashsdk.a.a.a(r1, false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x0081, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x0082, code lost:
        com.uc.crashsdk.a.a.a(r1, false);
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x0064 A[ExcHandler: all (r1v5 'th' java.lang.Throwable A[CUSTOM_DECLARE]), Splitter:B:10:0x0020] */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x006a A[SYNTHETIC, Splitter:B:56:0x006a] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:20:0x0032=Splitter:B:20:0x0032, B:58:0x006d=Splitter:B:58:0x006d} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean a(java.lang.String r7, com.uc.crashsdk.f.C0106f r8) {
        /*
            r1 = 0
            r2 = 0
            java.lang.Object r3 = n
            monitor-enter(r3)
            java.io.File r0 = new java.io.File     // Catch:{ all -> 0x003a }
            r0.<init>(r7)     // Catch:{ all -> 0x003a }
            boolean r4 = r0.exists()     // Catch:{ all -> 0x003a }
            if (r4 != 0) goto L_0x0013
            r0.createNewFile()     // Catch:{ Exception -> 0x0034 }
        L_0x0013:
            java.io.RandomAccessFile r4 = new java.io.RandomAccessFile     // Catch:{ Exception -> 0x003d }
            java.lang.String r5 = "rw"
            r4.<init>(r0, r5)     // Catch:{ Exception -> 0x003d }
            java.nio.channels.FileChannel r0 = r4.getChannel()     // Catch:{ Exception -> 0x003d }
        L_0x001e:
            if (r0 == 0) goto L_0x0024
            java.nio.channels.FileLock r1 = r0.lock()     // Catch:{ Exception -> 0x0044, all -> 0x0064 }
        L_0x0024:
            boolean r2 = r8.a()     // Catch:{ all -> 0x006e }
            if (r1 == 0) goto L_0x002d
            r1.release()     // Catch:{ Exception -> 0x005e, all -> 0x0064 }
        L_0x002d:
            if (r0 == 0) goto L_0x0032
            r0.close()     // Catch:{ Exception -> 0x007b }
        L_0x0032:
            monitor-exit(r3)     // Catch:{ all -> 0x003a }
            return r2
        L_0x0034:
            r4 = move-exception
            r5 = 0
            com.uc.crashsdk.a.a.a(r4, r5)     // Catch:{ all -> 0x003a }
            goto L_0x0013
        L_0x003a:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x003a }
            throw r0
        L_0x003d:
            r0 = move-exception
            r4 = 0
            com.uc.crashsdk.a.a.a(r0, r4)     // Catch:{ Exception -> 0x0089 }
            r0 = r1
            goto L_0x001e
        L_0x0044:
            r4 = move-exception
            r5 = 0
            com.uc.crashsdk.a.a.a(r4, r5)     // Catch:{ Exception -> 0x004a, all -> 0x0064 }
            goto L_0x0024
        L_0x004a:
            r1 = move-exception
            r6 = r1
            r1 = r0
            r0 = r6
        L_0x004e:
            r4 = 0
            com.uc.crashsdk.a.a.a(r0, r4)     // Catch:{ all -> 0x0087 }
            if (r1 == 0) goto L_0x0032
            r1.close()     // Catch:{ Exception -> 0x0058 }
            goto L_0x0032
        L_0x0058:
            r0 = move-exception
            r1 = 0
            com.uc.crashsdk.a.a.a(r0, r1)     // Catch:{ all -> 0x003a }
            goto L_0x0032
        L_0x005e:
            r1 = move-exception
            r4 = 0
            com.uc.crashsdk.a.a.a(r1, r4)     // Catch:{ Exception -> 0x004a, all -> 0x0064 }
            goto L_0x002d
        L_0x0064:
            r1 = move-exception
            r6 = r1
            r1 = r0
            r0 = r6
        L_0x0068:
            if (r1 == 0) goto L_0x006d
            r1.close()     // Catch:{ Exception -> 0x0081 }
        L_0x006d:
            throw r0     // Catch:{ all -> 0x003a }
        L_0x006e:
            r4 = move-exception
            if (r1 == 0) goto L_0x0074
            r1.release()     // Catch:{ Exception -> 0x0075, all -> 0x0064 }
        L_0x0074:
            throw r4     // Catch:{ Exception -> 0x004a, all -> 0x0064 }
        L_0x0075:
            r1 = move-exception
            r5 = 0
            com.uc.crashsdk.a.a.a(r1, r5)     // Catch:{ Exception -> 0x004a, all -> 0x0064 }
            goto L_0x0074
        L_0x007b:
            r0 = move-exception
            r1 = 0
            com.uc.crashsdk.a.a.a(r0, r1)     // Catch:{ all -> 0x003a }
            goto L_0x0032
        L_0x0081:
            r1 = move-exception
            r2 = 0
            com.uc.crashsdk.a.a.a(r1, r2)     // Catch:{ all -> 0x003a }
            goto L_0x006d
        L_0x0087:
            r0 = move-exception
            goto L_0x0068
        L_0x0089:
            r0 = move-exception
            goto L_0x004e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.crashsdk.f.a(java.lang.String, com.uc.crashsdk.f$f):boolean");
    }

    private static boolean b(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(95);
        if (lastIndexOf <= 0) {
            return false;
        }
        int indexOf = name.indexOf(46, lastIndexOf);
        if (indexOf <= 0) {
            return false;
        }
        String substring = name.substring(lastIndexOf + 1, indexOf);
        if (LogType.JAVA_TYPE.equals(substring) || "ucebujava".equals(substring) || LogType.NATIVE_TYPE.equals(substring) || "ucebujni".equals(substring) || LogType.UNEXP_TYPE.equals(substring)) {
            return true;
        }
        return false;
    }

    private static String k(String str) {
        int i2;
        int i3;
        if (!p.u() || h.a(p.v())) {
            return str;
        }
        int lastIndexOf = str.lastIndexOf(".log");
        if (lastIndexOf <= 0 || lastIndexOf + 4 != str.length()) {
            return str;
        }
        int lastIndexOf2 = str.lastIndexOf(File.separatorChar);
        if (lastIndexOf2 < 0) {
            lastIndexOf2 = 0;
            i2 = 0;
        } else {
            i2 = 0;
        }
        do {
            i3 = str.indexOf(95, i3);
            if (i3 >= 0) {
                i2++;
                i3++;
                continue;
            }
        } while (i3 >= 0);
        if (i2 != i) {
            return str;
        }
        int lastIndexOf3 = str.lastIndexOf(95);
        if (!b && lastIndexOf3 <= 0) {
            throw new AssertionError();
        } else if (str.indexOf(".log", lastIndexOf3) != lastIndexOf) {
            return str;
        } else {
            try {
                return j(str);
            } catch (Throwable th) {
                com.uc.crashsdk.a.a.a(th, false);
                return str;
            }
        }
    }

    public static boolean a(StringBuffer stringBuffer, String str, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, ArrayList<String> arrayList, ArrayList<String> arrayList2, ArrayList<String> arrayList3, String str2) {
        String a2;
        if (d) {
            com.uc.crashsdk.a.c.c("Processing java crash, skip generate custom log: " + str);
            return false;
        } else if (!com.uc.crashsdk.a.f.d()) {
            com.uc.crashsdk.a.c.b("DEBUG", com.uc.crashsdk.a.f.b());
            return false;
        } else if (b.d && JNIBridge.nativeIsCrashing()) {
            com.uc.crashsdk.a.c.c("Processing native crash, skip generate custom log: " + str);
            return false;
        } else if (stringBuffer == null || str == null) {
            return false;
        } else {
            if (l(str)) {
                com.uc.crashsdk.a.c.c(String.format("Custom log '%s' has reach max count!", new Object[]{str}));
                return false;
            }
            c(false);
            synchronized (o) {
                try {
                    a2 = a(stringBuffer, str, z2, z3, z4, z5, arrayList, arrayList2, arrayList3, str2);
                }
            }
            if (a2 == null || a2.length() == 0) {
                return false;
            }
            m(a2);
            b(j(a2), str);
            if (z6) {
                try {
                    a(false);
                } catch (Throwable th) {
                    com.uc.crashsdk.a.a.a(th, false);
                }
            }
            return true;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0057, code lost:
        r3 = java.lang.Long.parseLong(r10.group(2));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0067, code lost:
        if ((r5 - r3) >= 86400000) goto L_0x00de;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
        r0 = java.lang.Integer.parseInt(r10.group(3));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00d7, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00d8, code lost:
        com.uc.crashsdk.a.a.a(r0, false);
        r0 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00de, code lost:
        r0 = 0;
        r3 = r5;
     */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x00a8 A[Catch:{ Exception -> 0x00d7 }] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00f2 A[SYNTHETIC, Splitter:B:49:0x00f2] */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x0100 A[SYNTHETIC, Splitter:B:58:0x0100] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean l(java.lang.String r15) {
        /*
            r1 = 1
            r2 = 0
            java.lang.Object r7 = p
            monitor-enter(r7)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x00e7 }
            r0.<init>()     // Catch:{ all -> 0x00e7 }
            java.lang.String r3 = com.uc.crashsdk.p.N()     // Catch:{ all -> 0x00e7 }
            java.lang.StringBuilder r0 = r0.append(r3)     // Catch:{ all -> 0x00e7 }
            java.lang.String r3 = "customlog"
            java.lang.StringBuilder r0 = r0.append(r3)     // Catch:{ all -> 0x00e7 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x00e7 }
            java.io.File r8 = new java.io.File     // Catch:{ all -> 0x00e7 }
            r8.<init>(r0)     // Catch:{ all -> 0x00e7 }
            r0 = 1024(0x400, float:1.435E-42)
            r3 = 1
            java.lang.String r0 = com.uc.crashsdk.a.b.a(r8, r0, r3)     // Catch:{ all -> 0x00e7 }
            long r5 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x00e7 }
            java.lang.StringBuffer r9 = new java.lang.StringBuffer     // Catch:{ all -> 0x00e7 }
            r9.<init>()     // Catch:{ all -> 0x00e7 }
            if (r0 == 0) goto L_0x010e
            r9.append(r0)     // Catch:{ all -> 0x00e7 }
            java.lang.String r0 = "([^\\n\\r\\t\\s]+) (\\d+) (\\d+)"
            java.util.regex.Pattern r0 = java.util.regex.Pattern.compile(r0)     // Catch:{ all -> 0x00e7 }
            java.util.regex.Matcher r10 = r0.matcher(r9)     // Catch:{ all -> 0x00e7 }
            r0 = r2
        L_0x0041:
            boolean r0 = r10.find(r0)     // Catch:{ all -> 0x00e7 }
            if (r0 == 0) goto L_0x010e
            r0 = 1
            java.lang.String r0 = r10.group(r0)     // Catch:{ all -> 0x00e7 }
            boolean r0 = r15.equals(r0)     // Catch:{ all -> 0x00e7 }
            if (r0 != 0) goto L_0x0057
            int r0 = r10.end()     // Catch:{ all -> 0x00e7 }
            goto L_0x0041
        L_0x0057:
            r0 = 2
            java.lang.String r0 = r10.group(r0)     // Catch:{ all -> 0x00e7 }
            long r3 = java.lang.Long.parseLong(r0)     // Catch:{ all -> 0x00e7 }
            long r11 = r5 - r3
            r13 = 86400000(0x5265c00, double:4.2687272E-316)
            int r0 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1))
            if (r0 >= 0) goto L_0x00de
            r0 = 3
            java.lang.String r0 = r10.group(r0)     // Catch:{ Exception -> 0x00d7 }
            int r0 = java.lang.Integer.parseInt(r0)     // Catch:{ Exception -> 0x00d7 }
        L_0x0072:
            int r11 = com.uc.crashsdk.p.B()     // Catch:{ all -> 0x00e7 }
            if (r11 < 0) goto L_0x007b
            if (r0 < r11) goto L_0x007b
            r2 = r1
        L_0x007b:
            int r0 = r0 + 1
            java.util.Locale r11 = java.util.Locale.US     // Catch:{ all -> 0x00e7 }
            java.lang.String r12 = "%s %d %d"
            r13 = 3
            java.lang.Object[] r13 = new java.lang.Object[r13]     // Catch:{ all -> 0x00e7 }
            r14 = 0
            r13[r14] = r15     // Catch:{ all -> 0x00e7 }
            r14 = 1
            java.lang.Long r3 = java.lang.Long.valueOf(r3)     // Catch:{ all -> 0x00e7 }
            r13[r14] = r3     // Catch:{ all -> 0x00e7 }
            r3 = 2
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ all -> 0x00e7 }
            r13[r3] = r0     // Catch:{ all -> 0x00e7 }
            java.lang.String r0 = java.lang.String.format(r11, r12, r13)     // Catch:{ all -> 0x00e7 }
            int r3 = r10.start()     // Catch:{ all -> 0x00e7 }
            int r4 = r10.end()     // Catch:{ all -> 0x00e7 }
            r9.replace(r3, r4, r0)     // Catch:{ all -> 0x00e7 }
            r0 = r1
            r3 = r2
        L_0x00a6:
            if (r0 != 0) goto L_0x00c0
            java.util.Locale r0 = java.util.Locale.US     // Catch:{ all -> 0x00e7 }
            java.lang.String r1 = "%s %d 1\n"
            r2 = 2
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ all -> 0x00e7 }
            r4 = 0
            r2[r4] = r15     // Catch:{ all -> 0x00e7 }
            r4 = 1
            java.lang.Long r5 = java.lang.Long.valueOf(r5)     // Catch:{ all -> 0x00e7 }
            r2[r4] = r5     // Catch:{ all -> 0x00e7 }
            java.lang.String r0 = java.lang.String.format(r0, r1, r2)     // Catch:{ all -> 0x00e7 }
            r9.append(r0)     // Catch:{ all -> 0x00e7 }
        L_0x00c0:
            r2 = 0
            java.io.FileWriter r1 = new java.io.FileWriter     // Catch:{ Exception -> 0x00ea, all -> 0x00fc }
            r1.<init>(r8)     // Catch:{ Exception -> 0x00ea, all -> 0x00fc }
            java.lang.String r0 = r9.toString()     // Catch:{ Exception -> 0x010c }
            r2 = 0
            int r4 = r0.length()     // Catch:{ Exception -> 0x010c }
            r1.write(r0, r2, r4)     // Catch:{ Exception -> 0x010c }
            r1.close()     // Catch:{ Exception -> 0x00e1 }
        L_0x00d5:
            monitor-exit(r7)     // Catch:{ all -> 0x00e7 }
            return r3
        L_0x00d7:
            r0 = move-exception
            r11 = 0
            com.uc.crashsdk.a.a.a(r0, r11)     // Catch:{ all -> 0x00e7 }
            r0 = r2
            goto L_0x0072
        L_0x00de:
            r0 = r2
            r3 = r5
            goto L_0x0072
        L_0x00e1:
            r0 = move-exception
            r1 = 0
            com.uc.crashsdk.a.a.a(r0, r1)     // Catch:{ all -> 0x00e7 }
            goto L_0x00d5
        L_0x00e7:
            r0 = move-exception
            monitor-exit(r7)     // Catch:{ all -> 0x00e7 }
            throw r0
        L_0x00ea:
            r0 = move-exception
            r1 = r2
        L_0x00ec:
            r2 = 0
            com.uc.crashsdk.a.a.a(r0, r2)     // Catch:{ all -> 0x010a }
            if (r1 == 0) goto L_0x00d5
            r1.close()     // Catch:{ Exception -> 0x00f6 }
            goto L_0x00d5
        L_0x00f6:
            r0 = move-exception
            r1 = 0
            com.uc.crashsdk.a.a.a(r0, r1)     // Catch:{ all -> 0x00e7 }
            goto L_0x00d5
        L_0x00fc:
            r0 = move-exception
            r1 = r2
        L_0x00fe:
            if (r1 == 0) goto L_0x0103
            r1.close()     // Catch:{ Exception -> 0x0104 }
        L_0x0103:
            throw r0     // Catch:{ all -> 0x00e7 }
        L_0x0104:
            r1 = move-exception
            r2 = 0
            com.uc.crashsdk.a.a.a(r1, r2)     // Catch:{ all -> 0x00e7 }
            goto L_0x0103
        L_0x010a:
            r0 = move-exception
            goto L_0x00fe
        L_0x010c:
            r0 = move-exception
            goto L_0x00ec
        L_0x010e:
            r0 = r2
            r3 = r2
            goto L_0x00a6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.crashsdk.f.l(java.lang.String):boolean");
    }

    private static void a(b bVar, String str) {
        String str2;
        String str3 = null;
        if (b.d) {
            str2 = JNIBridge.nativeDumpThreads(str);
            if (str2 == null || str2.length() >= 512 || !str2.startsWith("/") || str2.indexOf(10) >= 0) {
                str3 = str2;
            } else if (!new File(str2).exists()) {
                str3 = "Can not found " + str2;
            }
        } else {
            str2 = null;
            str3 = "Native not initialized, skip dump!";
        }
        if (str3 != null) {
            try {
                bVar.write(str3.getBytes("UTF-8"));
                bVar.write("\n".getBytes("UTF-8"));
            } catch (Throwable th) {
                com.uc.crashsdk.a.a.a(th, false);
            }
            a((OutputStream) bVar);
        } else if (str2 != null) {
            b((OutputStream) bVar, str2);
            File file = new File(str2);
            if (file.exists()) {
                file.delete();
            }
        }
        try {
            bVar.flush();
        } catch (Throwable th2) {
            com.uc.crashsdk.a.a.a(th2, false);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x0034 A[Catch:{ Throwable -> 0x0039 }] */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x003f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String a(java.lang.StringBuffer r6, java.lang.String r7, boolean r8, boolean r9, boolean r10, boolean r11, java.util.ArrayList<java.lang.String> r12, java.util.ArrayList<java.lang.String> r13, java.util.ArrayList<java.lang.String> r14, java.lang.String r15) {
        /*
            r3 = 0
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = com.uc.crashsdk.p.O()
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r1 = h(r7)
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r0 = r0.toString()
            com.uc.crashsdk.f$b r2 = new com.uc.crashsdk.f$b     // Catch:{ Throwable -> 0x016f }
            java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch:{ Throwable -> 0x016f }
            r1.<init>(r0)     // Catch:{ Throwable -> 0x016f }
            r2.<init>(r1)     // Catch:{ Throwable -> 0x016f }
            java.util.ArrayList<java.lang.String> r3 = q     // Catch:{ Throwable -> 0x0039 }
            monitor-enter(r3)     // Catch:{ Throwable -> 0x0039 }
            s = r0     // Catch:{ all -> 0x0036 }
            java.lang.String r1 = "logb"
            java.lang.String r4 = s     // Catch:{ all -> 0x0036 }
            r5 = 0
            a(r1, r4, r5)     // Catch:{ all -> 0x0036 }
            monitor-exit(r3)     // Catch:{ all -> 0x0036 }
        L_0x0032:
            if (r2 != 0) goto L_0x003f
            r0 = 0
        L_0x0035:
            return r0
        L_0x0036:
            r1 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0036 }
            throw r1     // Catch:{ Throwable -> 0x0039 }
        L_0x0039:
            r1 = move-exception
        L_0x003a:
            r3 = 0
            com.uc.crashsdk.a.a.a(r1, r3)
            goto L_0x0032
        L_0x003f:
            if (r8 == 0) goto L_0x0044
            a(r2, r0, r7)     // Catch:{ Throwable -> 0x0124 }
        L_0x0044:
            java.lang.String r1 = r6.toString()     // Catch:{ Throwable -> 0x011e }
            byte[] r1 = r1.getBytes()     // Catch:{ Throwable -> 0x011e }
            r2.write(r1)     // Catch:{ Throwable -> 0x011e }
            java.lang.String r1 = "\n"
            java.lang.String r3 = "UTF-8"
            byte[] r1 = r1.getBytes(r3)     // Catch:{ Throwable -> 0x011e }
            r2.write(r1)     // Catch:{ Throwable -> 0x011e }
            r2.flush()     // Catch:{ Throwable -> 0x011e }
        L_0x005d:
            a(r2)     // Catch:{ Throwable -> 0x0124 }
            if (r10 == 0) goto L_0x0068
            b(r2)     // Catch:{ Throwable -> 0x0124 }
            r2.flush()     // Catch:{ Throwable -> 0x0132 }
        L_0x0068:
            if (r12 == 0) goto L_0x0075
            int r1 = r12.size()     // Catch:{ Throwable -> 0x0124 }
            if (r1 <= 0) goto L_0x0075
            java.lang.String r1 = "UTF-8"
            com.uc.crashsdk.a.a(r2, r1, r12)     // Catch:{ Throwable -> 0x0124 }
        L_0x0075:
            if (r13 == 0) goto L_0x0084
            int r1 = r13.size()     // Catch:{ Throwable -> 0x0124 }
            if (r1 <= 0) goto L_0x0084
            java.lang.String r1 = "UTF-8"
            java.lang.String r3 = "--- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---\n"
            com.uc.crashsdk.a.a(r2, r1, r3, r13)     // Catch:{ Throwable -> 0x0124 }
        L_0x0084:
            if (r14 == 0) goto L_0x0093
            int r1 = r14.size()     // Catch:{ Throwable -> 0x0124 }
            if (r1 <= 0) goto L_0x0093
            java.lang.String r1 = "UTF-8"
            java.lang.String r3 = "--- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---\n"
            com.uc.crashsdk.a.b(r2, r1, r3, r14)     // Catch:{ Throwable -> 0x0124 }
        L_0x0093:
            if (r15 == 0) goto L_0x00b1
            r2.flush()     // Catch:{ Throwable -> 0x013e }
        L_0x0098:
            java.lang.String r1 = "threads dump:\n"
            java.lang.String r3 = "UTF-8"
            byte[] r1 = r1.getBytes(r3)     // Catch:{ Throwable -> 0x0144 }
            r2.write(r1)     // Catch:{ Throwable -> 0x0144 }
        L_0x00a3:
            r1 = 0
            g = r1     // Catch:{ Throwable -> 0x0124 }
            u = r15     // Catch:{ Throwable -> 0x0124 }
            a(r2, r15)     // Catch:{ Throwable -> 0x014a }
        L_0x00ab:
            r1 = 0
            u = r1     // Catch:{ Throwable -> 0x0124 }
            r1 = 1
            g = r1     // Catch:{ Throwable -> 0x0124 }
        L_0x00b1:
            if (r11 == 0) goto L_0x00cc
            r2.flush()     // Catch:{ Throwable -> 0x0150 }
        L_0x00b6:
            java.lang.String r1 = "all threads dump:\n"
            java.lang.String r3 = "UTF-8"
            byte[] r1 = r1.getBytes(r3)     // Catch:{ Throwable -> 0x0156 }
            r2.write(r1)     // Catch:{ Throwable -> 0x0156 }
        L_0x00c1:
            r1 = 1
            t = r1     // Catch:{ Throwable -> 0x0124 }
            java.lang.String r1 = "all"
            a(r2, r1)     // Catch:{ Throwable -> 0x015c }
        L_0x00c9:
            r1 = 0
            t = r1     // Catch:{ Throwable -> 0x0124 }
        L_0x00cc:
            if (r9 == 0) goto L_0x00d4
            r2.a()     // Catch:{ Throwable -> 0x0124 }
            a(r2)     // Catch:{ Throwable -> 0x0124 }
        L_0x00d4:
            r2.close()     // Catch:{ Throwable -> 0x0162 }
        L_0x00d7:
            java.util.ArrayList<java.lang.String> r2 = q     // Catch:{ Throwable -> 0x0117 }
            monitor-enter(r2)     // Catch:{ Throwable -> 0x0117 }
            int r1 = r     // Catch:{ all -> 0x0114 }
            int r1 = r1 + 1
            r = r1     // Catch:{ all -> 0x0114 }
            java.lang.String r1 = s     // Catch:{ all -> 0x0114 }
            if (r1 == 0) goto L_0x0109
            java.util.ArrayList<java.lang.String> r1 = q     // Catch:{ all -> 0x0114 }
            java.lang.String r3 = s     // Catch:{ all -> 0x0114 }
            r1.add(r3)     // Catch:{ all -> 0x0114 }
            java.util.ArrayList<java.lang.String> r1 = q     // Catch:{ all -> 0x0114 }
            int r1 = r1.size()     // Catch:{ all -> 0x0114 }
            r3 = 3
            if (r1 <= r3) goto L_0x00fa
            java.util.ArrayList<java.lang.String> r1 = q     // Catch:{ all -> 0x0114 }
            r3 = 0
            r1.remove(r3)     // Catch:{ all -> 0x0114 }
        L_0x00fa:
            java.lang.String r1 = "loge"
            java.lang.String r3 = s     // Catch:{ all -> 0x0114 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0114 }
            r4 = 0
            a(r1, r3, r4)     // Catch:{ all -> 0x0114 }
            r1 = 0
            s = r1     // Catch:{ all -> 0x0114 }
        L_0x0109:
            java.lang.String r1 = "logct"
            r3 = 0
            int r4 = r     // Catch:{ all -> 0x0114 }
            a(r1, r3, r4)     // Catch:{ all -> 0x0114 }
            monitor-exit(r2)     // Catch:{ all -> 0x0114 }
            goto L_0x0035
        L_0x0114:
            r1 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0114 }
            throw r1     // Catch:{ Throwable -> 0x0117 }
        L_0x0117:
            r1 = move-exception
            r2 = 0
            com.uc.crashsdk.a.a.a(r1, r2)
            goto L_0x0035
        L_0x011e:
            r1 = move-exception
            a(r1, r2)     // Catch:{ Throwable -> 0x0124 }
            goto L_0x005d
        L_0x0124:
            r1 = move-exception
            a(r1, r2)     // Catch:{ all -> 0x0139 }
            r2.close()     // Catch:{ Throwable -> 0x012c }
            goto L_0x00d7
        L_0x012c:
            r1 = move-exception
            r2 = 0
            com.uc.crashsdk.a.a.a(r1, r2)
            goto L_0x00d7
        L_0x0132:
            r1 = move-exception
            r3 = 0
            com.uc.crashsdk.a.a.a(r1, r3)     // Catch:{ Throwable -> 0x0124 }
            goto L_0x0068
        L_0x0139:
            r0 = move-exception
            r2.close()     // Catch:{ Throwable -> 0x0169 }
        L_0x013d:
            throw r0
        L_0x013e:
            r1 = move-exception
            a(r1, r2)     // Catch:{ Throwable -> 0x0124 }
            goto L_0x0098
        L_0x0144:
            r1 = move-exception
            a(r1, r2)     // Catch:{ Throwable -> 0x0124 }
            goto L_0x00a3
        L_0x014a:
            r1 = move-exception
            a(r1, r2)     // Catch:{ Throwable -> 0x0124 }
            goto L_0x00ab
        L_0x0150:
            r1 = move-exception
            a(r1, r2)     // Catch:{ Throwable -> 0x0124 }
            goto L_0x00b6
        L_0x0156:
            r1 = move-exception
            a(r1, r2)     // Catch:{ Throwable -> 0x0124 }
            goto L_0x00c1
        L_0x015c:
            r1 = move-exception
            a(r1, r2)     // Catch:{ Throwable -> 0x0124 }
            goto L_0x00c9
        L_0x0162:
            r1 = move-exception
            r2 = 0
            com.uc.crashsdk.a.a.a(r1, r2)
            goto L_0x00d7
        L_0x0169:
            r1 = move-exception
            r2 = 0
            com.uc.crashsdk.a.a.a(r1, r2)
            goto L_0x013d
        L_0x016f:
            r1 = move-exception
            r2 = r3
            goto L_0x003a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.crashsdk.f.a(java.lang.StringBuffer, java.lang.String, boolean, boolean, boolean, boolean, java.util.ArrayList, java.util.ArrayList, java.util.ArrayList, java.lang.String):java.lang.String");
    }

    public static String f() {
        String str = null;
        if (v != null && v.length() > 0) {
            return v;
        }
        synchronized (x) {
            try {
                if (new File(b.f()).exists()) {
                    String a2 = com.uc.crashsdk.a.d.a(b.f());
                    if (a2 != null) {
                        a2 = a2.trim();
                        if (a2.length() != 0) {
                            if (!a2.toLowerCase().startsWith("http")) {
                                return null;
                            }
                        }
                    }
                    str = a2;
                }
                if (str == null) {
                    str = w;
                }
                v = str;
                return str;
            }
        }
    }

    public static void a(String str, boolean z2) {
        if (z2) {
            w = str;
            return;
        }
        v = str;
        synchronized (x) {
            com.uc.crashsdk.a.d.a(b.f(), str + "\n");
        }
    }

    public static String g() {
        return a(new Date());
    }

    private static String a(Date date) {
        return String.format(Locale.US, "%d%02d%02d%02d%02d%02d", new Object[]{Integer.valueOf(date.getYear() + SecExceptionCode.SEC_ERROR_AVMP), Integer.valueOf(date.getMonth() + 1), Integer.valueOf(date.getDate()), Integer.valueOf(date.getHours()), Integer.valueOf(date.getMinutes()), Integer.valueOf(date.getSeconds())});
    }

    public static void h() {
        c = System.currentTimeMillis();
    }

    private static void m(String str) {
        if (p.p()) {
            try {
                B();
            } catch (Throwable th) {
                com.uc.crashsdk.a.a.a(th, false);
            }
            if (str != null && !"".equals(str)) {
                try {
                    File file = new File(p.P());
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    com.uc.crashsdk.a.c.b("copy log to: " + file);
                    com.uc.crashsdk.a.b.a(new File(str), file, new byte[1048576]);
                } catch (Throwable th2) {
                    com.uc.crashsdk.a.a.a(th2, false);
                }
            }
        }
    }

    private static void b(String str, String str2) {
        try {
            d.a(str, str2);
        } catch (Throwable th) {
            com.uc.crashsdk.a.a.a(th, false);
        }
    }

    public static void i() {
        Throwable th;
        String str;
        if (h.a(a)) {
            try {
                File file = new File(p.N() + "unique");
                if (file.exists()) {
                    String a2 = com.uc.crashsdk.a.b.a(file, 48, true);
                    if (a2 != null) {
                        try {
                            str = a2.length() != 36 ? null : a2.replaceAll("[^0-9a-zA-Z-]", "-");
                        } catch (Exception e2) {
                            com.uc.crashsdk.a.a.a(e2, false);
                        } catch (Throwable th2) {
                            Throwable th3 = th2;
                            str = a2;
                            th = th3;
                            com.uc.crashsdk.a.a.a(th, false);
                            a = str;
                        }
                    }
                    str = a2;
                } else {
                    str = null;
                }
                try {
                    if (h.a(str)) {
                        str = UUID.randomUUID().toString();
                        if (!h.a(str)) {
                            com.uc.crashsdk.a.b.a(file, str.getBytes());
                        }
                    }
                } catch (Throwable th4) {
                    th = th4;
                    com.uc.crashsdk.a.a.a(th, false);
                    a = str;
                }
            } catch (Throwable th5) {
                th = th5;
                str = null;
                com.uc.crashsdk.a.a.a(th, false);
                a = str;
            }
            a = str;
        }
    }

    public static String j() {
        return a;
    }

    public static void k() {
        y = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new f());
    }

    public static void l() {
        Thread.setDefaultUncaughtExceptionHandler(y);
    }

    public void uncaughtException(Thread thread, Throwable th) {
        a(thread, th, false);
    }

    /* JADX WARNING: Removed duplicated region for block: B:110:0x0154 A[Catch:{ Throwable -> 0x0337 }] */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x015d  */
    /* JADX WARNING: Removed duplicated region for block: B:115:0x0166  */
    /* JADX WARNING: Removed duplicated region for block: B:150:0x01bd A[Catch:{ all -> 0x02a8, Throwable -> 0x0309, Throwable -> 0x0356, Throwable -> 0x0310 }] */
    /* JADX WARNING: Removed duplicated region for block: B:152:0x01c6  */
    /* JADX WARNING: Removed duplicated region for block: B:155:0x01cf  */
    /* JADX WARNING: Removed duplicated region for block: B:189:0x028a A[Catch:{ Throwable -> 0x0344 }] */
    /* JADX WARNING: Removed duplicated region for block: B:191:0x0293  */
    /* JADX WARNING: Removed duplicated region for block: B:194:0x029c  */
    /* JADX WARNING: Removed duplicated region for block: B:219:0x02e4 A[Catch:{ Throwable -> 0x02fe, Throwable -> 0x0359, Throwable -> 0x0304 }] */
    /* JADX WARNING: Removed duplicated region for block: B:221:0x02ed  */
    /* JADX WARNING: Removed duplicated region for block: B:224:0x02f6  */
    /* JADX WARNING: Removed duplicated region for block: B:268:0x035c  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x004e A[Catch:{ Throwable -> 0x031d }] */
    /* JADX WARNING: Removed duplicated region for block: B:270:0x0362  */
    /* JADX WARNING: Removed duplicated region for block: B:272:0x0368  */
    /* JADX WARNING: Removed duplicated region for block: B:274:0x036e  */
    /* JADX WARNING: Removed duplicated region for block: B:276:0x0374  */
    /* JADX WARNING: Removed duplicated region for block: B:278:0x037a  */
    /* JADX WARNING: Removed duplicated region for block: B:284:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:285:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:286:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:287:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:288:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0057  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0060  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00b7 A[Catch:{ Throwable -> 0x032a }] */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00c0  */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x00c9  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(java.lang.Thread r9, java.lang.Throwable r10, boolean r11) {
        /*
            r8 = this;
            r2 = 1
            r3 = 0
            boolean r0 = d     // Catch:{ Throwable -> 0x017d }
            if (r0 == 0) goto L_0x0068
            int r0 = android.os.Process.myPid()     // Catch:{ Throwable -> 0x017d }
            if (r0 <= 0) goto L_0x0068
            int r0 = android.os.Process.myPid()     // Catch:{ Throwable -> 0x017d }
            android.os.Process.killProcess(r0)     // Catch:{ Throwable -> 0x017d }
            if (r11 == 0) goto L_0x0371
            boolean r0 = com.uc.crashsdk.p.q()     // Catch:{ Throwable -> 0x0316 }
            if (r0 == 0) goto L_0x0371
            r0 = 0
            a(r0)     // Catch:{ Throwable -> 0x0353 }
            r0 = r2
        L_0x0020:
            r1 = r0
        L_0x0021:
            boolean r0 = com.uc.crashsdk.p.i()     // Catch:{ Throwable -> 0x031d }
            boolean r4 = com.uc.crashsdk.a.f.d()     // Catch:{ Throwable -> 0x031d }
            if (r4 != 0) goto L_0x036e
        L_0x002b:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x031d }
            java.lang.String r4 = "Call java default handler: "
            r0.<init>(r4)     // Catch:{ Throwable -> 0x031d }
            java.lang.StringBuilder r0 = r0.append(r2)     // Catch:{ Throwable -> 0x031d }
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x031d }
            com.uc.crashsdk.a.c.b(r0)     // Catch:{ Throwable -> 0x031d }
            if (r2 == 0) goto L_0x0048
            java.lang.Thread$UncaughtExceptionHandler r0 = y     // Catch:{ Throwable -> 0x031d }
            if (r0 == 0) goto L_0x0048
            java.lang.Thread$UncaughtExceptionHandler r0 = y     // Catch:{ Throwable -> 0x031d }
            r0.uncaughtException(r9, r10)     // Catch:{ Throwable -> 0x031d }
        L_0x0048:
            boolean r0 = com.uc.crashsdk.b.o()     // Catch:{ Throwable -> 0x031d }
            if (r0 == 0) goto L_0x0055
            android.content.Context r0 = com.uc.crashsdk.e.a()     // Catch:{ Throwable -> 0x031d }
            com.uc.crashsdk.r.a(r0)     // Catch:{ Throwable -> 0x031d }
        L_0x0055:
            if (r1 != 0) goto L_0x005a
            b(r3)
        L_0x005a:
            int r0 = android.os.Process.myPid()
            if (r0 <= 0) goto L_0x0067
            int r0 = android.os.Process.myPid()
        L_0x0064:
            android.os.Process.killProcess(r0)
        L_0x0067:
            return
        L_0x0068:
            r0 = 1
            d = r0     // Catch:{ Throwable -> 0x017d }
            z = r10     // Catch:{ Throwable -> 0x017d }
            boolean r0 = com.uc.crashsdk.a.f.d()     // Catch:{ Throwable -> 0x017d }
            if (r0 != 0) goto L_0x00ce
            java.lang.String r0 = "DEBUG"
            java.lang.String r1 = com.uc.crashsdk.a.f.b()     // Catch:{ Throwable -> 0x017d }
            com.uc.crashsdk.a.c.b(r0, r1)     // Catch:{ Throwable -> 0x017d }
            if (r11 == 0) goto L_0x036b
            boolean r0 = com.uc.crashsdk.p.q()     // Catch:{ Throwable -> 0x0323 }
            if (r0 == 0) goto L_0x036b
            r0 = 0
            a(r0)     // Catch:{ Throwable -> 0x0350 }
            r0 = r2
        L_0x0089:
            r1 = r0
        L_0x008a:
            boolean r0 = com.uc.crashsdk.p.i()     // Catch:{ Throwable -> 0x032a }
            boolean r4 = com.uc.crashsdk.a.f.d()     // Catch:{ Throwable -> 0x032a }
            if (r4 != 0) goto L_0x0368
        L_0x0094:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x032a }
            java.lang.String r4 = "Call java default handler: "
            r0.<init>(r4)     // Catch:{ Throwable -> 0x032a }
            java.lang.StringBuilder r0 = r0.append(r2)     // Catch:{ Throwable -> 0x032a }
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x032a }
            com.uc.crashsdk.a.c.b(r0)     // Catch:{ Throwable -> 0x032a }
            if (r2 == 0) goto L_0x00b1
            java.lang.Thread$UncaughtExceptionHandler r0 = y     // Catch:{ Throwable -> 0x032a }
            if (r0 == 0) goto L_0x00b1
            java.lang.Thread$UncaughtExceptionHandler r0 = y     // Catch:{ Throwable -> 0x032a }
            r0.uncaughtException(r9, r10)     // Catch:{ Throwable -> 0x032a }
        L_0x00b1:
            boolean r0 = com.uc.crashsdk.b.o()     // Catch:{ Throwable -> 0x032a }
            if (r0 == 0) goto L_0x00be
            android.content.Context r0 = com.uc.crashsdk.e.a()     // Catch:{ Throwable -> 0x032a }
            com.uc.crashsdk.r.a(r0)     // Catch:{ Throwable -> 0x032a }
        L_0x00be:
            if (r1 != 0) goto L_0x00c3
            b(r3)
        L_0x00c3:
            int r0 = android.os.Process.myPid()
            if (r0 <= 0) goto L_0x0067
            int r0 = android.os.Process.myPid()
            goto L_0x0064
        L_0x00ce:
            java.lang.String r0 = "DEBUG"
            java.lang.String r1 = "begin to generate java report"
            com.uc.crashsdk.a.c.c(r0, r1)     // Catch:{ Throwable -> 0x017d }
            java.util.List<java.io.FileInputStream> r0 = r8.f     // Catch:{ Throwable -> 0x00f1 }
            java.util.Iterator r1 = r0.iterator()     // Catch:{ Throwable -> 0x00f1 }
        L_0x00db:
            boolean r0 = r1.hasNext()     // Catch:{ Throwable -> 0x00f1 }
            if (r0 == 0) goto L_0x00f6
            java.lang.Object r0 = r1.next()     // Catch:{ Throwable -> 0x00f1 }
            java.io.FileInputStream r0 = (java.io.FileInputStream) r0     // Catch:{ Throwable -> 0x00f1 }
            r0.close()     // Catch:{ Exception -> 0x00eb }
            goto L_0x00db
        L_0x00eb:
            r0 = move-exception
            r4 = 0
            com.uc.crashsdk.a.a.a(r0, r4)     // Catch:{ Throwable -> 0x00f1 }
            goto L_0x00db
        L_0x00f1:
            r0 = move-exception
            r1 = 0
            com.uc.crashsdk.a.a.a(r0, r1)     // Catch:{ Throwable -> 0x017d }
        L_0x00f6:
            boolean r0 = com.uc.crashsdk.b.o()     // Catch:{ Throwable -> 0x0171 }
            if (r0 == 0) goto L_0x016c
            r0 = 3
            com.uc.crashsdk.k.a(r0)     // Catch:{ Throwable -> 0x0171 }
        L_0x0100:
            java.io.File r0 = new java.io.File     // Catch:{ Throwable -> 0x0177 }
            java.lang.String r1 = com.uc.crashsdk.b.b()     // Catch:{ Throwable -> 0x0177 }
            r0.<init>(r1)     // Catch:{ Throwable -> 0x0177 }
            r0.createNewFile()     // Catch:{ Throwable -> 0x0177 }
        L_0x010c:
            boolean r0 = com.uc.crashsdk.p.s()     // Catch:{ Throwable -> 0x017d }
            if (r0 == 0) goto L_0x01d5
            java.lang.String r0 = "DEBUG"
            java.lang.String r1 = "omit java crash"
            com.uc.crashsdk.a.c.c(r0, r1)     // Catch:{ Throwable -> 0x017d }
            if (r11 == 0) goto L_0x0365
            boolean r0 = com.uc.crashsdk.p.q()     // Catch:{ Throwable -> 0x0330 }
            if (r0 == 0) goto L_0x0365
            r0 = 0
            a(r0)     // Catch:{ Throwable -> 0x034d }
            r0 = r2
        L_0x0126:
            r1 = r0
        L_0x0127:
            boolean r0 = com.uc.crashsdk.p.i()     // Catch:{ Throwable -> 0x0337 }
            boolean r4 = com.uc.crashsdk.a.f.d()     // Catch:{ Throwable -> 0x0337 }
            if (r4 != 0) goto L_0x0362
        L_0x0131:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0337 }
            java.lang.String r4 = "Call java default handler: "
            r0.<init>(r4)     // Catch:{ Throwable -> 0x0337 }
            java.lang.StringBuilder r0 = r0.append(r2)     // Catch:{ Throwable -> 0x0337 }
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x0337 }
            com.uc.crashsdk.a.c.b(r0)     // Catch:{ Throwable -> 0x0337 }
            if (r2 == 0) goto L_0x014e
            java.lang.Thread$UncaughtExceptionHandler r0 = y     // Catch:{ Throwable -> 0x0337 }
            if (r0 == 0) goto L_0x014e
            java.lang.Thread$UncaughtExceptionHandler r0 = y     // Catch:{ Throwable -> 0x0337 }
            r0.uncaughtException(r9, r10)     // Catch:{ Throwable -> 0x0337 }
        L_0x014e:
            boolean r0 = com.uc.crashsdk.b.o()     // Catch:{ Throwable -> 0x0337 }
            if (r0 == 0) goto L_0x015b
            android.content.Context r0 = com.uc.crashsdk.e.a()     // Catch:{ Throwable -> 0x0337 }
            com.uc.crashsdk.r.a(r0)     // Catch:{ Throwable -> 0x0337 }
        L_0x015b:
            if (r1 != 0) goto L_0x0160
            b(r3)
        L_0x0160:
            int r0 = android.os.Process.myPid()
            if (r0 <= 0) goto L_0x0067
            int r0 = android.os.Process.myPid()
            goto L_0x0064
        L_0x016c:
            r0 = 4
            com.uc.crashsdk.k.a(r0)     // Catch:{ Throwable -> 0x0171 }
            goto L_0x0100
        L_0x0171:
            r0 = move-exception
            r1 = 0
            com.uc.crashsdk.a.a.a(r0, r1)     // Catch:{ Throwable -> 0x0177 }
            goto L_0x0100
        L_0x0177:
            r0 = move-exception
            r1 = 0
            com.uc.crashsdk.a.a.a(r0, r1)     // Catch:{ Throwable -> 0x017d }
            goto L_0x010c
        L_0x017d:
            r0 = move-exception
            r1 = 0
            com.uc.crashsdk.a.a.a(r0, r1)     // Catch:{ all -> 0x02a8 }
            if (r11 == 0) goto L_0x0377
            boolean r0 = com.uc.crashsdk.p.q()     // Catch:{ Throwable -> 0x0309 }
            if (r0 == 0) goto L_0x0377
            r0 = 0
            a(r0)     // Catch:{ Throwable -> 0x0356 }
            r0 = r2
        L_0x018f:
            r1 = r0
        L_0x0190:
            boolean r0 = com.uc.crashsdk.p.i()     // Catch:{ Throwable -> 0x0310 }
            boolean r4 = com.uc.crashsdk.a.f.d()     // Catch:{ Throwable -> 0x0310 }
            if (r4 != 0) goto L_0x0374
        L_0x019a:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0310 }
            java.lang.String r4 = "Call java default handler: "
            r0.<init>(r4)     // Catch:{ Throwable -> 0x0310 }
            java.lang.StringBuilder r0 = r0.append(r2)     // Catch:{ Throwable -> 0x0310 }
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x0310 }
            com.uc.crashsdk.a.c.b(r0)     // Catch:{ Throwable -> 0x0310 }
            if (r2 == 0) goto L_0x01b7
            java.lang.Thread$UncaughtExceptionHandler r0 = y     // Catch:{ Throwable -> 0x0310 }
            if (r0 == 0) goto L_0x01b7
            java.lang.Thread$UncaughtExceptionHandler r0 = y     // Catch:{ Throwable -> 0x0310 }
            r0.uncaughtException(r9, r10)     // Catch:{ Throwable -> 0x0310 }
        L_0x01b7:
            boolean r0 = com.uc.crashsdk.b.o()     // Catch:{ Throwable -> 0x0310 }
            if (r0 == 0) goto L_0x01c4
            android.content.Context r0 = com.uc.crashsdk.e.a()     // Catch:{ Throwable -> 0x0310 }
            com.uc.crashsdk.r.a(r0)     // Catch:{ Throwable -> 0x0310 }
        L_0x01c4:
            if (r1 != 0) goto L_0x01c9
            b(r3)
        L_0x01c9:
            int r0 = android.os.Process.myPid()
            if (r0 <= 0) goto L_0x0067
            int r0 = android.os.Process.myPid()
            goto L_0x0064
        L_0x01d5:
            boolean r0 = r10 instanceof java.lang.OutOfMemoryError     // Catch:{ Throwable -> 0x017d }
            java.lang.String r1 = a(r10, r0)     // Catch:{ Throwable -> 0x017d }
            java.lang.String r4 = "DEBUG"
            java.lang.String r5 = "generate java report finished"
            com.uc.crashsdk.a.c.c(r4, r5)     // Catch:{ Throwable -> 0x017d }
            if (r0 == 0) goto L_0x024f
            boolean r0 = com.uc.crashsdk.p.j()     // Catch:{ Throwable -> 0x017d }
            if (r0 == 0) goto L_0x024f
            java.io.File r0 = new java.io.File     // Catch:{ Throwable -> 0x017d }
            r0.<init>(r1)     // Catch:{ Throwable -> 0x017d }
            java.lang.String r0 = r0.getName()     // Catch:{ Throwable -> 0x017d }
            java.lang.String r1 = com.uc.crashsdk.p.P()     // Catch:{ Throwable -> 0x017d }
            java.io.File r4 = new java.io.File     // Catch:{ Throwable -> 0x017d }
            r4.<init>(r1)     // Catch:{ Throwable -> 0x017d }
            boolean r5 = r4.exists()     // Catch:{ Throwable -> 0x017d }
            if (r5 != 0) goto L_0x0205
            r4.mkdirs()     // Catch:{ Throwable -> 0x017d }
        L_0x0205:
            java.lang.String r4 = "%s%s.hprof"
            r5 = 2
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ Throwable -> 0x017d }
            r6 = 0
            r5[r6] = r1     // Catch:{ Throwable -> 0x017d }
            r1 = 1
            r5[r1] = r0     // Catch:{ Throwable -> 0x017d }
            java.lang.String r0 = java.lang.String.format(r4, r5)     // Catch:{ Throwable -> 0x017d }
            java.lang.String r1 = "DEBUG"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x017d }
            java.lang.String r5 = "begin dump hprof: "
            r4.<init>(r5)     // Catch:{ Throwable -> 0x017d }
            java.lang.StringBuilder r4 = r4.append(r0)     // Catch:{ Throwable -> 0x017d }
            java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x017d }
            com.uc.crashsdk.a.c.c(r1, r4)     // Catch:{ Throwable -> 0x017d }
            long r4 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x017d }
            android.os.Debug.dumpHprofData(r0)     // Catch:{ Throwable -> 0x02a2 }
        L_0x022f:
            java.lang.String r0 = "DEBUG"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x017d }
            java.lang.String r6 = "end dump hprof, use "
            r1.<init>(r6)     // Catch:{ Throwable -> 0x017d }
            long r6 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x017d }
            long r4 = r6 - r4
            java.lang.StringBuilder r1 = r1.append(r4)     // Catch:{ Throwable -> 0x017d }
            java.lang.String r4 = " ms"
            java.lang.StringBuilder r1 = r1.append(r4)     // Catch:{ Throwable -> 0x017d }
            java.lang.String r1 = r1.toString()     // Catch:{ Throwable -> 0x017d }
            com.uc.crashsdk.a.c.c(r0, r1)     // Catch:{ Throwable -> 0x017d }
        L_0x024f:
            if (r11 == 0) goto L_0x035f
            boolean r0 = com.uc.crashsdk.p.q()     // Catch:{ Throwable -> 0x033d }
            if (r0 == 0) goto L_0x035f
            r0 = 0
            a(r0)     // Catch:{ Throwable -> 0x034a }
            r0 = r2
        L_0x025c:
            r1 = r0
        L_0x025d:
            boolean r0 = com.uc.crashsdk.p.i()     // Catch:{ Throwable -> 0x0344 }
            boolean r4 = com.uc.crashsdk.a.f.d()     // Catch:{ Throwable -> 0x0344 }
            if (r4 != 0) goto L_0x035c
        L_0x0267:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0344 }
            java.lang.String r4 = "Call java default handler: "
            r0.<init>(r4)     // Catch:{ Throwable -> 0x0344 }
            java.lang.StringBuilder r0 = r0.append(r2)     // Catch:{ Throwable -> 0x0344 }
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x0344 }
            com.uc.crashsdk.a.c.b(r0)     // Catch:{ Throwable -> 0x0344 }
            if (r2 == 0) goto L_0x0284
            java.lang.Thread$UncaughtExceptionHandler r0 = y     // Catch:{ Throwable -> 0x0344 }
            if (r0 == 0) goto L_0x0284
            java.lang.Thread$UncaughtExceptionHandler r0 = y     // Catch:{ Throwable -> 0x0344 }
            r0.uncaughtException(r9, r10)     // Catch:{ Throwable -> 0x0344 }
        L_0x0284:
            boolean r0 = com.uc.crashsdk.b.o()     // Catch:{ Throwable -> 0x0344 }
            if (r0 == 0) goto L_0x0291
            android.content.Context r0 = com.uc.crashsdk.e.a()     // Catch:{ Throwable -> 0x0344 }
            com.uc.crashsdk.r.a(r0)     // Catch:{ Throwable -> 0x0344 }
        L_0x0291:
            if (r1 != 0) goto L_0x0296
            b(r3)
        L_0x0296:
            int r0 = android.os.Process.myPid()
            if (r0 <= 0) goto L_0x0067
            int r0 = android.os.Process.myPid()
            goto L_0x0064
        L_0x02a2:
            r0 = move-exception
            r1 = 0
            com.uc.crashsdk.a.a.a(r0, r1)     // Catch:{ Throwable -> 0x017d }
            goto L_0x022f
        L_0x02a8:
            r0 = move-exception
            if (r11 == 0) goto L_0x037d
            boolean r1 = com.uc.crashsdk.p.q()     // Catch:{ Throwable -> 0x02fe }
            if (r1 == 0) goto L_0x037d
            r1 = 0
            a(r1)     // Catch:{ Throwable -> 0x0359 }
            r1 = r2
        L_0x02b6:
            r4 = r1
        L_0x02b7:
            boolean r1 = com.uc.crashsdk.p.i()     // Catch:{ Throwable -> 0x0304 }
            boolean r5 = com.uc.crashsdk.a.f.d()     // Catch:{ Throwable -> 0x0304 }
            if (r5 != 0) goto L_0x037a
        L_0x02c1:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0304 }
            java.lang.String r5 = "Call java default handler: "
            r1.<init>(r5)     // Catch:{ Throwable -> 0x0304 }
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Throwable -> 0x0304 }
            java.lang.String r1 = r1.toString()     // Catch:{ Throwable -> 0x0304 }
            com.uc.crashsdk.a.c.b(r1)     // Catch:{ Throwable -> 0x0304 }
            if (r2 == 0) goto L_0x02de
            java.lang.Thread$UncaughtExceptionHandler r1 = y     // Catch:{ Throwable -> 0x0304 }
            if (r1 == 0) goto L_0x02de
            java.lang.Thread$UncaughtExceptionHandler r1 = y     // Catch:{ Throwable -> 0x0304 }
            r1.uncaughtException(r9, r10)     // Catch:{ Throwable -> 0x0304 }
        L_0x02de:
            boolean r1 = com.uc.crashsdk.b.o()     // Catch:{ Throwable -> 0x0304 }
            if (r1 == 0) goto L_0x02eb
            android.content.Context r1 = com.uc.crashsdk.e.a()     // Catch:{ Throwable -> 0x0304 }
            com.uc.crashsdk.r.a(r1)     // Catch:{ Throwable -> 0x0304 }
        L_0x02eb:
            if (r4 != 0) goto L_0x02f0
            b(r3)
        L_0x02f0:
            int r1 = android.os.Process.myPid()
            if (r1 <= 0) goto L_0x02fd
            int r1 = android.os.Process.myPid()
            android.os.Process.killProcess(r1)
        L_0x02fd:
            throw r0
        L_0x02fe:
            r1 = move-exception
            r4 = r3
        L_0x0300:
            com.uc.crashsdk.a.a.a(r1, r3)
            goto L_0x02b7
        L_0x0304:
            r1 = move-exception
            com.uc.crashsdk.a.a.a(r1, r3)
            goto L_0x02eb
        L_0x0309:
            r0 = move-exception
            r1 = r3
        L_0x030b:
            com.uc.crashsdk.a.a.a(r0, r3)
            goto L_0x0190
        L_0x0310:
            r0 = move-exception
            com.uc.crashsdk.a.a.a(r0, r3)
            goto L_0x01c4
        L_0x0316:
            r0 = move-exception
            r1 = r3
        L_0x0318:
            com.uc.crashsdk.a.a.a(r0, r3)
            goto L_0x0021
        L_0x031d:
            r0 = move-exception
            com.uc.crashsdk.a.a.a(r0, r3)
            goto L_0x0055
        L_0x0323:
            r0 = move-exception
            r1 = r3
        L_0x0325:
            com.uc.crashsdk.a.a.a(r0, r3)
            goto L_0x008a
        L_0x032a:
            r0 = move-exception
            com.uc.crashsdk.a.a.a(r0, r3)
            goto L_0x00be
        L_0x0330:
            r0 = move-exception
            r1 = r3
        L_0x0332:
            com.uc.crashsdk.a.a.a(r0, r3)
            goto L_0x0127
        L_0x0337:
            r0 = move-exception
            com.uc.crashsdk.a.a.a(r0, r3)
            goto L_0x015b
        L_0x033d:
            r0 = move-exception
            r1 = r3
        L_0x033f:
            com.uc.crashsdk.a.a.a(r0, r3)
            goto L_0x025d
        L_0x0344:
            r0 = move-exception
            com.uc.crashsdk.a.a.a(r0, r3)
            goto L_0x0291
        L_0x034a:
            r0 = move-exception
            r1 = r2
            goto L_0x033f
        L_0x034d:
            r0 = move-exception
            r1 = r2
            goto L_0x0332
        L_0x0350:
            r0 = move-exception
            r1 = r2
            goto L_0x0325
        L_0x0353:
            r0 = move-exception
            r1 = r2
            goto L_0x0318
        L_0x0356:
            r0 = move-exception
            r1 = r2
            goto L_0x030b
        L_0x0359:
            r1 = move-exception
            r4 = r2
            goto L_0x0300
        L_0x035c:
            r2 = r0
            goto L_0x0267
        L_0x035f:
            r0 = r3
            goto L_0x025c
        L_0x0362:
            r2 = r0
            goto L_0x0131
        L_0x0365:
            r0 = r3
            goto L_0x0126
        L_0x0368:
            r2 = r0
            goto L_0x0094
        L_0x036b:
            r0 = r3
            goto L_0x0089
        L_0x036e:
            r2 = r0
            goto L_0x002b
        L_0x0371:
            r0 = r3
            goto L_0x0020
        L_0x0374:
            r2 = r0
            goto L_0x019a
        L_0x0377:
            r0 = r3
            goto L_0x018f
        L_0x037a:
            r2 = r1
            goto L_0x02c1
        L_0x037d:
            r1 = r3
            goto L_0x02b6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.crashsdk.f.a(java.lang.Thread, java.lang.Throwable, boolean):void");
    }

    public static Throwable m() {
        return z;
    }

    public static void n() {
        long o2 = (long) p.o();
        if (o2 >= 0 && b.q()) {
            boolean z2 = CrashApi.getInstance().getLastExitType() == 5;
            i.a(0, new d(1));
            if (z2) {
                B = new d(2);
                i.a(0, B, o2);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:?, code lost:
        return false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean o() {
        /*
            java.lang.Object r1 = C
            monitor-enter(r1)
            java.lang.Runnable r0 = B     // Catch:{ all -> 0x0019 }
            if (r0 == 0) goto L_0x0016
            boolean r0 = A     // Catch:{ all -> 0x0019 }
            if (r0 != 0) goto L_0x0016
            java.lang.Runnable r0 = B     // Catch:{ all -> 0x0019 }
            com.uc.crashsdk.a.i.a(r0)     // Catch:{ all -> 0x0019 }
            r0 = 0
            B = r0     // Catch:{ all -> 0x0019 }
            r0 = 1
            monitor-exit(r1)     // Catch:{ all -> 0x0019 }
        L_0x0015:
            return r0
        L_0x0016:
            monitor-exit(r1)     // Catch:{ all -> 0x0019 }
            r0 = 0
            goto L_0x0015
        L_0x0019:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0019 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.crashsdk.f.o():boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:7:0x0017 A[SYNTHETIC, Splitter:B:7:0x0017] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean c(java.lang.String r6) {
        /*
            r1 = 1
            r2 = 0
            java.io.File r3 = new java.io.File
            r3.<init>(r6)
            boolean r0 = r3.exists()     // Catch:{ Exception -> 0x0041 }
            if (r0 == 0) goto L_0x0045
            java.lang.String r0 = r3.getAbsolutePath()     // Catch:{ Exception -> 0x0041 }
            java.lang.System.load(r0)     // Catch:{ Exception -> 0x0041 }
            r0 = r1
        L_0x0015:
            if (r0 != 0) goto L_0x004c
            java.lang.String r3 = r3.getName()     // Catch:{ Exception -> 0x0047 }
            boolean r4 = com.uc.crashsdk.a.h.b(r3)     // Catch:{ Exception -> 0x0047 }
            if (r4 == 0) goto L_0x004c
            java.lang.String r4 = "lib"
            boolean r4 = r3.startsWith(r4)     // Catch:{ Exception -> 0x0047 }
            if (r4 == 0) goto L_0x004c
            java.lang.String r4 = ".so"
            boolean r4 = r3.endsWith(r4)     // Catch:{ Exception -> 0x0047 }
            if (r4 == 0) goto L_0x004c
            r4 = 3
            int r5 = r3.length()     // Catch:{ Exception -> 0x0047 }
            int r5 = r5 + -3
            java.lang.String r3 = r3.substring(r4, r5)     // Catch:{ Exception -> 0x0047 }
            java.lang.System.loadLibrary(r3)     // Catch:{ Exception -> 0x0047 }
        L_0x003f:
            r0 = r1
        L_0x0040:
            return r0
        L_0x0041:
            r0 = move-exception
            com.uc.crashsdk.a.a.a(r0, r2)
        L_0x0045:
            r0 = r2
            goto L_0x0015
        L_0x0047:
            r1 = move-exception
            com.uc.crashsdk.a.a.a(r1, r2)
            goto L_0x0040
        L_0x004c:
            r1 = r0
            goto L_0x003f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.crashsdk.f.c(java.lang.String):boolean");
    }

    /* access modifiers changed from: private */
    public static void B() {
        int i2;
        int i3;
        int i4;
        String P = p.P();
        File file = new File(P);
        if (file.isDirectory()) {
            try {
                File[] listFiles = file.listFiles();
                if (listFiles != null && listFiles.length > 150) {
                    Arrays.sort(listFiles, new c(0));
                    int length = listFiles.length - 150;
                    if (length < 0) {
                        i2 = 0;
                    } else {
                        i2 = length;
                    }
                    long currentTimeMillis = System.currentTimeMillis();
                    int i5 = 0;
                    i3 = 0;
                    int i6 = 0;
                    while (i5 < listFiles.length) {
                        File file2 = listFiles[i5];
                        boolean z2 = i5 < i2;
                        if (!z2 && currentTimeMillis - file2.lastModified() >= 432000000) {
                            z2 = true;
                        }
                        if (z2) {
                            file2.delete();
                            i6++;
                            i4 = 0;
                            if (i4 < 3) {
                                i5++;
                                i3 = i4;
                            }
                        }
                    }
                    com.uc.crashsdk.a.c.a("Removed " + i6 + " logs in " + P);
                }
            } catch (Exception e2) {
                i4 = i3 + 1;
                com.uc.crashsdk.a.a.a(e2, false);
            } catch (Throwable th) {
                com.uc.crashsdk.a.a.a(th, false);
            }
        }
    }

    public static void p() {
        if (p.p()) {
            i.a(0, new d(3), 10000);
        }
    }

    public static StringBuilder a(StackTraceElement[] stackTraceElementArr, String str) {
        boolean z2;
        int i2 = 0;
        StringBuilder sb = new StringBuilder();
        if (stackTraceElementArr != null && stackTraceElementArr.length > 0) {
            if (str == null) {
                z2 = true;
            } else {
                z2 = false;
            }
            int i3 = 0;
            for (StackTraceElement stackTraceElement : stackTraceElementArr) {
                i3++;
                sb.append("  at ");
                sb.append(stackTraceElement.toString());
                sb.append("\n");
                if (!z2 && stackTraceElement.getMethodName().indexOf(str) >= 0) {
                    sb.delete(0, sb.length());
                    z2 = true;
                    i3 = 0;
                }
            }
            i2 = i3;
        }
        if (i2 == 0) {
            sb.append("  (no java stack)\n");
        }
        return sb;
    }

    public static StringBuilder d(String str) {
        return a(Thread.currentThread().getStackTrace(), str);
    }
}
