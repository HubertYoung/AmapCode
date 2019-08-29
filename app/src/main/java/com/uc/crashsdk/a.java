package com.uc.crashsdk;

import android.util.SparseArray;
import com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Base;
import com.uc.crashsdk.a.c;
import com.uc.crashsdk.a.d;
import com.uc.crashsdk.a.h;
import com.uc.crashsdk.a.i;
import com.uc.crashsdk.export.LogType;
import java.io.File;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;

/* compiled from: ProGuard */
public class a {
    private static SparseArray<e> A = new SparseArray<>();
    private static List<Integer> B = new ArrayList();
    private static HashMap<String, C0105a> C = new HashMap<>();
    private static List<String> D = new ArrayList();
    private static int E = 0;
    private static int F = 0;
    private static Runnable G = new d(1);
    private static boolean H = false;
    private static boolean I = false;
    public static String a = "";
    public static String b = "";
    public static int c = 10;
    public static int d = 8;
    public static int e = 6;
    public static int f = 2;
    public static int g = 6;
    public static int h = 8;
    public static int i = 2048;
    static final /* synthetic */ boolean j = (!a.class.desiredAssertionStatus());
    private static Map<String, String> k = new HashMap();
    private static List<String> l = new ArrayList();
    private static String m = "";
    private static String n = null;
    private static HashMap<String, c> o = new HashMap<>();
    private static List<String> p = new ArrayList();
    private static int q = 0;
    private static int r = 0;
    private static int s = 0;
    private static HashMap<String, b> t = new HashMap<>();
    private static List<String> u = new ArrayList();
    private static int v = 0;
    private static int w = 0;
    private static int x = 0;
    private static int y = 0;
    private static int z = 0;

    /* renamed from: com.uc.crashsdk.a$a reason: collision with other inner class name */
    /* compiled from: ProGuard */
    final class C0105a {
        int a;
        int b;
        List<String> c = new ArrayList();

        public C0105a(int i, int i2) {
            this.a = i;
            this.b = i2;
        }
    }

    /* compiled from: ProGuard */
    final class b {
        public int a;
        public Callable<String> b;
        public long c;

        public b(int i, Callable<String> callable, long j) {
            this.a = i;
            this.b = callable;
            this.c = j;
        }
    }

    /* compiled from: ProGuard */
    final class c {
        public int a;
        public String b;
        public boolean c;
        public boolean d;
        public boolean e;

        public c(int i, String str, boolean z, boolean z2, boolean z3) {
            this.a = i;
            this.b = str;
            this.c = z;
            this.d = z2;
            this.e = z3;
        }
    }

    /* compiled from: ProGuard */
    final class d implements Runnable {
        static final /* synthetic */ boolean a = (!a.class.desiredAssertionStatus());
        private int b = 0;

        d(int i) {
            this.b = i;
        }

        public final void run() {
            switch (this.b) {
                case 1:
                    a.i();
                    return;
                case 2:
                    a.j();
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
        WeakReference<Thread> a;
        public String b;

        public e(Thread thread, String str) {
            this.a = new WeakReference<>(thread);
            this.b = str;
        }
    }

    static /* synthetic */ void i() {
        c.b("Begin update unexp info ...");
        long currentTimeMillis = System.currentTimeMillis();
        if (b.d) {
            JNIBridge.nativeUpdateUnexpInfo(p.C());
        }
        c.b("Update unexp info took " + (System.currentTimeMillis() - currentTimeMillis) + " ms");
        a(false);
    }

    static /* synthetic */ void j() {
        String format = String.format("%s/%s/%s", new Object[]{p.K(), p.L(), p.M()});
        m = g();
        if (b.d) {
            JNIBridge.nativeSyncStatus("ver", m, 0);
        }
        d.a(b.g(), format);
        if ((m == null || !m.equals(format)) && p.t()) {
            c.b(String.format("Is new version ('%s' -> '%s'), deleting old stats data!", new Object[]{m, format}));
            b.a(true);
        }
    }

    public static String a() {
        if (n != null) {
            return n;
        }
        try {
            String str = e.a().getPackageManager().getPackageInfo(a, 0).versionName;
            n = str;
            return str;
        } catch (Exception e2) {
            com.uc.crashsdk.a.a.a(e2, true);
            return "";
        }
    }

    public static void a(String str, String str2) {
        synchronized (k) {
            if (!k.containsKey(str)) {
                l.add(str);
            }
            k.put(str, str2);
            if (b.d) {
                JNIBridge.nativeAddHeaderInfo(str, str2);
            }
        }
    }

    public static void b() {
        if (j || b.d) {
            synchronized (k) {
                for (String next : l) {
                    JNIBridge.nativeAddHeaderInfo(next, k.get(next));
                }
            }
            return;
        }
        throw new AssertionError();
    }

    public static void a(OutputStream outputStream, String str) {
        synchronized (k) {
            for (String next : l) {
                try {
                    outputStream.write(next.getBytes(str));
                    outputStream.write(": ".getBytes(str));
                    String str2 = k.get(next);
                    if (str2 != null) {
                        outputStream.write(str2.getBytes(str));
                    }
                    outputStream.write("\n".getBytes(str));
                } catch (Throwable th) {
                    f.a(th, outputStream);
                }
            }
        }
    }

    public static byte[] c() {
        return new byte[]{24, 99, 121, 60};
    }

    public static int a(String str, String str2, boolean z2, boolean z3, int i2, boolean z4) {
        int i3;
        int i4;
        int i5;
        int i6;
        boolean z5 = false;
        if (str == null || str2 == null) {
            return 0;
        }
        synchronized (o) {
            try {
                if (o.containsKey(str)) {
                    i3 = o.get(str).a;
                    i4 = LogType.addType(i3, i2);
                } else {
                    i3 = 0;
                    i4 = i2;
                }
                if (LogType.isForJava(i4) && !LogType.isForJava(i3)) {
                    if (q >= c) {
                        i4 = LogType.removeType(i4, 16);
                    } else {
                        q++;
                    }
                }
                if (LogType.isForNative(i4) && !LogType.isForNative(i3)) {
                    if (r >= c) {
                        i4 = LogType.removeType(i4, 1);
                    } else {
                        r++;
                    }
                }
                if (LogType.isForUnexp(i4) && !LogType.isForUnexp(i3)) {
                    if (s >= c) {
                        i4 = LogType.removeType(i4, 256);
                    } else {
                        s++;
                    }
                }
                if ((i4 & LZMA_Base.kMatchMaxLen) != 0) {
                    if (i3 == 0) {
                        p.add(str);
                    }
                    z5 = true;
                }
                if (z5) {
                    if (b.d && (LogType.isForNative(i2) || LogType.isForUnexp(i2))) {
                        int nativeAddDumpFile = JNIBridge.nativeAddDumpFile(str, str2, z2, z3, LogType.isForNative(i2), LogType.isForUnexp(i2), z4);
                        if (!LogType.isForNative(nativeAddDumpFile)) {
                            i4 = LogType.removeType(i4, 1);
                        }
                        if (!LogType.isForUnexp(nativeAddDumpFile)) {
                            i6 = LogType.removeType(i4, 256);
                            o.put(str, new c(i6, str2, z2, z3, z4));
                            i5 = i6;
                        }
                    }
                    i6 = i4;
                    o.put(str, new c(i6, str2, z2, z3, z4));
                    i5 = i6;
                } else {
                    i5 = i4;
                }
            }
        }
        return i5;
    }

    public static void d() {
        if (j || b.d) {
            synchronized (o) {
                for (String next : p) {
                    c cVar = o.get(next);
                    int i2 = cVar.a;
                    if (LogType.isForNative(i2) || LogType.isForUnexp(i2)) {
                        JNIBridge.nativeAddDumpFile(next, cVar.b, cVar.c, cVar.d, LogType.isForNative(i2), LogType.isForUnexp(i2), cVar.e);
                    }
                }
            }
            return;
        }
        throw new AssertionError();
    }

    public static void a(OutputStream outputStream, String str, ArrayList<String> arrayList) {
        boolean z2;
        if (arrayList == null) {
            z2 = true;
        } else {
            z2 = false;
        }
        synchronized (o) {
            for (String next : p) {
                try {
                    c cVar = o.get(next);
                    if (arrayList == null) {
                        if (!LogType.isForJava(cVar.a)) {
                        }
                    } else if (!a(arrayList, next)) {
                    }
                    if (cVar.d) {
                        outputStream.write((next + "\n").getBytes(str));
                    }
                    if (cVar.c) {
                        f.a(outputStream, cVar.b);
                    } else {
                        f.b(outputStream, cVar.b);
                    }
                    if (cVar.e && z2) {
                        File file = new File(cVar.b);
                        if (file.exists()) {
                            file.delete();
                        }
                    }
                } catch (Throwable th) {
                    f.a(th, outputStream);
                }
            }
        }
    }

    private static boolean a(ArrayList<String> arrayList, String str) {
        if (h.a(str)) {
            return false;
        }
        Iterator<String> it = arrayList.iterator();
        while (it.hasNext()) {
            if (str.equals(it.next())) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:96:?, code lost:
        return r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int a(java.lang.String r11, int r12, java.util.concurrent.Callable<java.lang.String> r13, long r14) {
        /*
            r8 = 0
            r1 = 0
            r2 = 1
            if (r11 != 0) goto L_0x0008
            r0 = r1
        L_0x0007:
            return r0
        L_0x0008:
            java.util.HashMap<java.lang.String, com.uc.crashsdk.a$b> r7 = t
            monitor-enter(r7)
            java.util.HashMap<java.lang.String, com.uc.crashsdk.a$b> r0 = t     // Catch:{ all -> 0x0025 }
            boolean r0 = r0.containsKey(r11)     // Catch:{ all -> 0x0025 }
            if (r0 == 0) goto L_0x015f
            java.util.HashMap<java.lang.String, com.uc.crashsdk.a$b> r0 = t     // Catch:{ all -> 0x0025 }
            java.lang.Object r0 = r0.get(r11)     // Catch:{ all -> 0x0025 }
            com.uc.crashsdk.a$b r0 = (com.uc.crashsdk.a.b) r0     // Catch:{ all -> 0x0025 }
            int r0 = r0.a     // Catch:{ all -> 0x0025 }
            int r4 = com.uc.crashsdk.export.LogType.addType(r0, r12)     // Catch:{ all -> 0x0025 }
            if (r0 != r4) goto L_0x0028
            monitor-exit(r7)     // Catch:{ all -> 0x0025 }
            goto L_0x0007
        L_0x0025:
            r0 = move-exception
            monitor-exit(r7)     // Catch:{ all -> 0x0025 }
            throw r0
        L_0x0028:
            r6 = r0
        L_0x0029:
            boolean r0 = com.uc.crashsdk.export.LogType.isForJava(r4)     // Catch:{ all -> 0x0025 }
            if (r0 == 0) goto L_0x0049
            boolean r0 = com.uc.crashsdk.export.LogType.isForJava(r6)     // Catch:{ all -> 0x0025 }
            if (r0 != 0) goto L_0x0049
            int r0 = d     // Catch:{ all -> 0x0025 }
            int r3 = f     // Catch:{ all -> 0x0025 }
            int r0 = r0 - r3
            int r3 = v     // Catch:{ all -> 0x0025 }
            int r5 = d     // Catch:{ all -> 0x0025 }
            if (r3 < r5) goto L_0x00e5
            r0 = r2
        L_0x0041:
            if (r0 == 0) goto L_0x0049
            r0 = 16
            int r4 = com.uc.crashsdk.export.LogType.removeType(r4, r0)     // Catch:{ all -> 0x0025 }
        L_0x0049:
            boolean r0 = com.uc.crashsdk.export.LogType.isForNative(r4)     // Catch:{ all -> 0x0025 }
            if (r0 == 0) goto L_0x015a
            boolean r0 = com.uc.crashsdk.export.LogType.isForNative(r6)     // Catch:{ all -> 0x0025 }
            if (r0 != 0) goto L_0x015a
            int r0 = e     // Catch:{ all -> 0x0025 }
            int r3 = f     // Catch:{ all -> 0x0025 }
            int r0 = r0 - r3
            int r3 = w     // Catch:{ all -> 0x0025 }
            int r5 = e     // Catch:{ all -> 0x0025 }
            if (r3 < r5) goto L_0x0109
            r5 = r2
            r0 = r1
            r3 = r1
        L_0x0063:
            if (r5 == 0) goto L_0x0154
            r5 = 1
            int r4 = com.uc.crashsdk.export.LogType.removeType(r4, r5)     // Catch:{ all -> 0x0025 }
            r5 = r3
            r10 = r0
            r0 = r4
            r4 = r10
        L_0x006e:
            boolean r3 = com.uc.crashsdk.export.LogType.isForUnexp(r0)     // Catch:{ all -> 0x0025 }
            if (r3 == 0) goto L_0x0151
            boolean r3 = com.uc.crashsdk.export.LogType.isForUnexp(r6)     // Catch:{ all -> 0x0025 }
            if (r3 != 0) goto L_0x0151
            int r3 = x     // Catch:{ all -> 0x0025 }
            int r8 = g     // Catch:{ all -> 0x0025 }
            if (r3 < r8) goto L_0x013e
            r3 = 256(0x100, float:3.59E-43)
            int r0 = com.uc.crashsdk.export.LogType.removeType(r0, r3)     // Catch:{ all -> 0x0025 }
            r3 = r1
        L_0x0087:
            r8 = r0 & 273(0x111, float:3.83E-43)
            if (r8 != 0) goto L_0x0147
        L_0x008b:
            if (r1 == 0) goto L_0x00e2
            boolean r1 = com.uc.crashsdk.b.d     // Catch:{ all -> 0x0025 }
            if (r1 == 0) goto L_0x00d8
            boolean r1 = com.uc.crashsdk.export.LogType.isForNative(r12)     // Catch:{ all -> 0x0025 }
            if (r1 != 0) goto L_0x009d
            boolean r1 = com.uc.crashsdk.export.LogType.isForUnexp(r12)     // Catch:{ all -> 0x0025 }
            if (r1 == 0) goto L_0x00d8
        L_0x009d:
            boolean r1 = com.uc.crashsdk.export.LogType.isForNative(r12)     // Catch:{ all -> 0x0025 }
            boolean r2 = com.uc.crashsdk.export.LogType.isForUnexp(r12)     // Catch:{ all -> 0x0025 }
            int r1 = com.uc.crashsdk.JNIBridge.nativeAddCallbackInfo(r11, r1, r2, r14)     // Catch:{ all -> 0x0025 }
            boolean r2 = com.uc.crashsdk.export.LogType.isForNative(r1)     // Catch:{ all -> 0x0025 }
            if (r2 != 0) goto L_0x00c4
            r2 = 1
            int r0 = com.uc.crashsdk.export.LogType.removeType(r0, r2)     // Catch:{ all -> 0x0025 }
            if (r5 == 0) goto L_0x00bc
            int r2 = w     // Catch:{ all -> 0x0025 }
            int r2 = r2 + -1
            w = r2     // Catch:{ all -> 0x0025 }
        L_0x00bc:
            if (r4 == 0) goto L_0x00c4
            int r2 = z     // Catch:{ all -> 0x0025 }
            int r2 = r2 + -1
            z = r2     // Catch:{ all -> 0x0025 }
        L_0x00c4:
            boolean r1 = com.uc.crashsdk.export.LogType.isForUnexp(r1)     // Catch:{ all -> 0x0025 }
            if (r1 != 0) goto L_0x00d8
            r1 = 256(0x100, float:3.59E-43)
            int r0 = com.uc.crashsdk.export.LogType.removeType(r0, r1)     // Catch:{ all -> 0x0025 }
            if (r3 == 0) goto L_0x00d8
            int r1 = x     // Catch:{ all -> 0x0025 }
            int r1 = r1 + -1
            x = r1     // Catch:{ all -> 0x0025 }
        L_0x00d8:
            java.util.HashMap<java.lang.String, com.uc.crashsdk.a$b> r1 = t     // Catch:{ all -> 0x0025 }
            com.uc.crashsdk.a$b r2 = new com.uc.crashsdk.a$b     // Catch:{ all -> 0x0025 }
            r2.<init>(r0, r13, r14)     // Catch:{ all -> 0x0025 }
            r1.put(r11, r2)     // Catch:{ all -> 0x0025 }
        L_0x00e2:
            monitor-exit(r7)     // Catch:{ all -> 0x0025 }
            goto L_0x0007
        L_0x00e5:
            int r3 = (r14 > r8 ? 1 : (r14 == r8 ? 0 : -1))
            if (r3 == 0) goto L_0x00ff
            int r3 = y     // Catch:{ all -> 0x0025 }
            if (r3 < r0) goto L_0x00f0
            r0 = r2
            goto L_0x0041
        L_0x00f0:
            int r0 = y     // Catch:{ all -> 0x0025 }
            int r0 = r0 + 1
            y = r0     // Catch:{ all -> 0x0025 }
        L_0x00f6:
            int r0 = v     // Catch:{ all -> 0x0025 }
            int r0 = r0 + 1
            v = r0     // Catch:{ all -> 0x0025 }
            r0 = r1
            goto L_0x0041
        L_0x00ff:
            int r3 = v     // Catch:{ all -> 0x0025 }
            int r5 = y     // Catch:{ all -> 0x0025 }
            int r3 = r3 - r5
            if (r3 < r0) goto L_0x00f6
            r0 = r2
            goto L_0x0041
        L_0x0109:
            int r3 = (r14 > r8 ? 1 : (r14 == r8 ? 0 : -1))
            if (r3 == 0) goto L_0x0127
            int r3 = z     // Catch:{ all -> 0x0025 }
            if (r3 < r0) goto L_0x0116
            r5 = r2
            r0 = r1
            r3 = r1
            goto L_0x0063
        L_0x0116:
            int r0 = z     // Catch:{ all -> 0x0025 }
            int r0 = r0 + 1
            z = r0     // Catch:{ all -> 0x0025 }
            int r0 = w     // Catch:{ all -> 0x0025 }
            int r0 = r0 + 1
            w = r0     // Catch:{ all -> 0x0025 }
            r5 = r1
            r0 = r2
            r3 = r2
            goto L_0x0063
        L_0x0127:
            int r3 = w     // Catch:{ all -> 0x0025 }
            int r5 = z     // Catch:{ all -> 0x0025 }
            int r3 = r3 - r5
            if (r3 < r0) goto L_0x0133
            r5 = r2
            r0 = r1
            r3 = r1
            goto L_0x0063
        L_0x0133:
            int r0 = w     // Catch:{ all -> 0x0025 }
            int r0 = r0 + 1
            w = r0     // Catch:{ all -> 0x0025 }
            r5 = r1
            r0 = r1
            r3 = r2
            goto L_0x0063
        L_0x013e:
            int r3 = x     // Catch:{ all -> 0x0025 }
            int r3 = r3 + 1
            x = r3     // Catch:{ all -> 0x0025 }
            r3 = r2
            goto L_0x0087
        L_0x0147:
            if (r6 != 0) goto L_0x014e
            java.util.List<java.lang.String> r1 = u     // Catch:{ all -> 0x0025 }
            r1.add(r11)     // Catch:{ all -> 0x0025 }
        L_0x014e:
            r1 = r2
            goto L_0x008b
        L_0x0151:
            r3 = r1
            goto L_0x0087
        L_0x0154:
            r5 = r3
            r10 = r0
            r0 = r4
            r4 = r10
            goto L_0x006e
        L_0x015a:
            r5 = r1
            r0 = r4
            r4 = r1
            goto L_0x006e
        L_0x015f:
            r6 = r1
            r4 = r12
            goto L_0x0029
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.crashsdk.a.a(java.lang.String, int, java.util.concurrent.Callable, long):int");
    }

    public static void e() {
        if (j || b.d) {
            synchronized (t) {
                for (String next : u) {
                    b bVar = t.get(next);
                    int i2 = bVar.a;
                    if (LogType.isForNative(i2) || LogType.isForUnexp(i2)) {
                        JNIBridge.nativeAddCallbackInfo(next, LogType.isForNative(i2), LogType.isForUnexp(i2), bVar.c);
                    }
                }
            }
            return;
        }
        throw new AssertionError();
    }

    public static void a(OutputStream outputStream, String str, String str2, ArrayList<String> arrayList) {
        String sb;
        synchronized (t) {
            for (String next : u) {
                try {
                    b bVar = t.get(next);
                    int i2 = bVar.a;
                    if (arrayList == null) {
                        if (!LogType.isForJava(i2)) {
                            continue;
                        }
                    } else if (!a(arrayList, next)) {
                    }
                    outputStream.write((next + "\n").getBytes(str));
                    if (bVar.c != 0) {
                        sb = JNIBridge.nativeGetCallbackInfo(next, bVar.c);
                    } else {
                        sb = c(next).toString();
                    }
                    if (sb == null || sb.length() <= 0) {
                        outputStream.write("(data is null)\n".getBytes(str));
                    } else {
                        outputStream.write(sb.getBytes(str));
                    }
                } catch (Throwable th) {
                    f.a(th, outputStream);
                }
                try {
                    outputStream.write("\n".getBytes(str));
                    outputStream.write(str2.getBytes(str));
                } catch (Throwable th2) {
                    f.a(th2, outputStream);
                }
            }
        }
    }

    public static String a(String str) {
        String sb;
        synchronized (t) {
            try {
                sb = c(str).toString();
            }
        }
        return sb;
    }

    private static StringBuilder c(String str) {
        String a2;
        StringBuilder sb = new StringBuilder();
        try {
            b bVar = t.get(str);
            if (bVar == null) {
                a2 = "Unknown callback: " + str;
            } else if (bVar.b != null) {
                a2 = bVar.b.call();
            } else {
                a2 = d.a(str);
            }
            if (a2 != null) {
                sb.append(a2);
            }
        } catch (Throwable th) {
            com.uc.crashsdk.a.a.a(th, false);
        }
        try {
            if (sb.length() == 0) {
                sb.append("(data is null)\n");
            }
        } catch (Throwable th2) {
            com.uc.crashsdk.a.a.a(th2, false);
        }
        return sb;
    }

    private static boolean a(String str, Thread thread) {
        if (thread == null) {
            return false;
        }
        synchronized (A) {
            int id = (int) thread.getId();
            if (A.get(id) == null) {
                B.add(Integer.valueOf(id));
            }
            A.put(id, new e(thread, str));
        }
        return true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0075 A[Catch:{ Throwable -> 0x00f4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x00b7 A[Catch:{ Throwable -> 0x00fc }] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00c5 A[Catch:{ Throwable -> 0x00fc }] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00f1  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void a(java.io.OutputStream r11, java.lang.String r12, java.lang.String r13) {
        /*
            android.util.SparseArray<com.uc.crashsdk.a$e> r4 = A
            monitor-enter(r4)
            java.lang.Thread r5 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x00f9 }
            java.util.List<java.lang.Integer> r0 = B     // Catch:{ all -> 0x00f9 }
            java.util.Iterator r6 = r0.iterator()     // Catch:{ all -> 0x00f9 }
        L_0x000d:
            boolean r0 = r6.hasNext()     // Catch:{ all -> 0x00f9 }
            if (r0 == 0) goto L_0x0118
            java.lang.Object r0 = r6.next()     // Catch:{ all -> 0x00f9 }
            java.lang.Integer r0 = (java.lang.Integer) r0     // Catch:{ all -> 0x00f9 }
            int r7 = r0.intValue()     // Catch:{ all -> 0x00f9 }
            r3 = 0
            r2 = 0
            android.util.SparseArray<com.uc.crashsdk.a$e> r0 = A     // Catch:{ Throwable -> 0x011a }
            java.lang.Object r0 = r0.get(r7)     // Catch:{ Throwable -> 0x011a }
            com.uc.crashsdk.a$e r0 = (com.uc.crashsdk.a.e) r0     // Catch:{ Throwable -> 0x011a }
            if (r0 == 0) goto L_0x000d
            java.lang.ref.WeakReference<java.lang.Thread> r1 = r0.a     // Catch:{ Throwable -> 0x011a }
            java.lang.Object r1 = r1.get()     // Catch:{ Throwable -> 0x011a }
            java.lang.Thread r1 = (java.lang.Thread) r1     // Catch:{ Throwable -> 0x011a }
            java.lang.String r2 = r0.b     // Catch:{ Throwable -> 0x011f }
            if (r1 != 0) goto L_0x00ed
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0058 }
            java.lang.String r3 = "Thread ("
            r0.<init>(r3)     // Catch:{ Throwable -> 0x0058 }
            java.lang.StringBuilder r0 = r0.append(r2)     // Catch:{ Throwable -> 0x0058 }
            java.lang.String r3 = ", "
            java.lang.StringBuilder r0 = r0.append(r3)     // Catch:{ Throwable -> 0x0058 }
            java.lang.StringBuilder r0 = r0.append(r7)     // Catch:{ Throwable -> 0x0058 }
            java.lang.String r3 = ") has exited!"
            java.lang.StringBuilder r0 = r0.append(r3)     // Catch:{ Throwable -> 0x0058 }
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x0058 }
            com.uc.crashsdk.a.c.c(r0)     // Catch:{ Throwable -> 0x0058 }
            goto L_0x000d
        L_0x0058:
            r0 = move-exception
        L_0x0059:
            com.uc.crashsdk.f.a(r0, r11)     // Catch:{ all -> 0x00f9 }
        L_0x005c:
            java.lang.String r0 = "Thread Name: '%s'\n"
            r3 = 1
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x00f4 }
            r8 = 0
            r3[r8] = r2     // Catch:{ Throwable -> 0x00f4 }
            java.lang.String r0 = java.lang.String.format(r0, r3)     // Catch:{ Throwable -> 0x00f4 }
            byte[] r0 = r0.getBytes(r12)     // Catch:{ Throwable -> 0x00f4 }
            r11.write(r0)     // Catch:{ Throwable -> 0x00f4 }
            boolean r0 = r1.isDaemon()     // Catch:{ Throwable -> 0x00f4 }
            if (r0 == 0) goto L_0x00f1
            java.lang.String r0 = " daemon"
        L_0x0077:
            java.util.Locale r2 = java.util.Locale.US     // Catch:{ Throwable -> 0x00f4 }
            java.lang.String r3 = "\"%s\"%s prio=%d tid=%d %s\n"
            r8 = 5
            java.lang.Object[] r8 = new java.lang.Object[r8]     // Catch:{ Throwable -> 0x00f4 }
            r9 = 0
            java.lang.String r10 = r1.getName()     // Catch:{ Throwable -> 0x00f4 }
            r8[r9] = r10     // Catch:{ Throwable -> 0x00f4 }
            r9 = 1
            r8[r9] = r0     // Catch:{ Throwable -> 0x00f4 }
            r0 = 2
            int r9 = r1.getPriority()     // Catch:{ Throwable -> 0x00f4 }
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)     // Catch:{ Throwable -> 0x00f4 }
            r8[r0] = r9     // Catch:{ Throwable -> 0x00f4 }
            r0 = 3
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch:{ Throwable -> 0x00f4 }
            r8[r0] = r7     // Catch:{ Throwable -> 0x00f4 }
            r0 = 4
            java.lang.Thread$State r7 = r1.getState()     // Catch:{ Throwable -> 0x00f4 }
            java.lang.String r7 = r7.toString()     // Catch:{ Throwable -> 0x00f4 }
            r8[r0] = r7     // Catch:{ Throwable -> 0x00f4 }
            java.lang.String r0 = java.lang.String.format(r2, r3, r8)     // Catch:{ Throwable -> 0x00f4 }
            byte[] r0 = r0.getBytes(r12)     // Catch:{ Throwable -> 0x00f4 }
            r11.write(r0)     // Catch:{ Throwable -> 0x00f4 }
        L_0x00b0:
            java.lang.StackTraceElement[] r2 = r1.getStackTrace()     // Catch:{ Throwable -> 0x00fc }
            int r0 = r2.length     // Catch:{ Throwable -> 0x00fc }
            if (r0 != 0) goto L_0x00c0
            java.lang.String r0 = "  (no stack frames)"
            byte[] r0 = r0.getBytes(r12)     // Catch:{ Throwable -> 0x00fc }
            r11.write(r0)     // Catch:{ Throwable -> 0x00fc }
        L_0x00c0:
            r1 = 1
            int r3 = r2.length     // Catch:{ Throwable -> 0x00fc }
            r0 = 0
        L_0x00c3:
            if (r0 >= r3) goto L_0x0100
            r7 = r2[r0]     // Catch:{ Throwable -> 0x00fc }
            if (r1 != 0) goto L_0x00d2
            java.lang.String r1 = "\n"
            byte[] r1 = r1.getBytes(r12)     // Catch:{ Throwable -> 0x00fc }
            r11.write(r1)     // Catch:{ Throwable -> 0x00fc }
        L_0x00d2:
            r1 = 0
            java.lang.String r8 = "  at %s"
            r9 = 1
            java.lang.Object[] r9 = new java.lang.Object[r9]     // Catch:{ Throwable -> 0x00fc }
            r10 = 0
            java.lang.String r7 = r7.toString()     // Catch:{ Throwable -> 0x00fc }
            r9[r10] = r7     // Catch:{ Throwable -> 0x00fc }
            java.lang.String r7 = java.lang.String.format(r8, r9)     // Catch:{ Throwable -> 0x00fc }
            byte[] r7 = r7.getBytes(r12)     // Catch:{ Throwable -> 0x00fc }
            r11.write(r7)     // Catch:{ Throwable -> 0x00fc }
            int r0 = r0 + 1
            goto L_0x00c3
        L_0x00ed:
            if (r5 == r1) goto L_0x000d
            goto L_0x005c
        L_0x00f1:
            java.lang.String r0 = ""
            goto L_0x0077
        L_0x00f4:
            r0 = move-exception
            com.uc.crashsdk.f.a(r0, r11)     // Catch:{ all -> 0x00f9 }
            goto L_0x00b0
        L_0x00f9:
            r0 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x00f9 }
            throw r0
        L_0x00fc:
            r0 = move-exception
            com.uc.crashsdk.f.a(r0, r11)     // Catch:{ all -> 0x00f9 }
        L_0x0100:
            java.lang.String r0 = "\n"
            byte[] r0 = r0.getBytes(r12)     // Catch:{ Throwable -> 0x0112 }
            r11.write(r0)     // Catch:{ Throwable -> 0x0112 }
            byte[] r0 = r13.getBytes(r12)     // Catch:{ Throwable -> 0x0112 }
            r11.write(r0)     // Catch:{ Throwable -> 0x0112 }
            goto L_0x000d
        L_0x0112:
            r0 = move-exception
            com.uc.crashsdk.f.a(r0, r11)     // Catch:{ all -> 0x00f9 }
            goto L_0x000d
        L_0x0118:
            monitor-exit(r4)     // Catch:{ all -> 0x00f9 }
            return
        L_0x011a:
            r0 = move-exception
            r1 = r2
            r2 = r3
            goto L_0x0059
        L_0x011f:
            r0 = move-exception
            r2 = r3
            goto L_0x0059
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.crashsdk.a.a(java.io.OutputStream, java.lang.String, java.lang.String):void");
    }

    public static int a(String str, int i2, int i3) {
        int i4;
        int i5;
        boolean z2 = false;
        if (str == null || i2 <= 0) {
            return 0;
        }
        synchronized (C) {
            try {
                if (C.containsKey(str)) {
                    int i6 = C.get(str).b;
                    int i7 = i6;
                    i5 = LogType.addType(i6, i3);
                    i4 = i7;
                } else {
                    i4 = 0;
                    i5 = i3;
                }
                if (LogType.isForJava(i5) && !LogType.isForJava(i4)) {
                    if (E >= h) {
                        i5 = LogType.removeType(i5, 16);
                    } else {
                        E++;
                    }
                }
                if (LogType.isForNative(i5) && !LogType.isForNative(i4)) {
                    if (F >= h) {
                        i5 = LogType.removeType(i5, 1);
                    } else {
                        F++;
                    }
                }
                if ((i5 & LZMA_Base.kMatchMaxLen) != 0) {
                    if (i4 == 0) {
                        D.add(str);
                    }
                    z2 = true;
                }
                if (z2) {
                    if (b.d && LogType.isForNative(i3) && !JNIBridge.nativeCreateCachedInfo(str, i2)) {
                        i5 = LogType.removeType(i5, 1);
                    }
                    C.put(str, new C0105a(i2, i5));
                }
            }
        }
        return i5;
    }

    public static int b(String str, String str2) {
        int i2;
        int i3 = 0;
        if (str == null || str2 == null) {
            return 0;
        }
        if (str2.length() > i) {
            str2 = str2.substring(0, i);
        }
        synchronized (C) {
            try {
                C0105a aVar = C.get(str);
                if (aVar != null) {
                    if (aVar.c.size() >= aVar.a) {
                        aVar.c.remove(0);
                    }
                    aVar.c.add(str2);
                    i3 = LogType.addType(0, 16);
                    if (!b.d && LogType.isForNative(aVar.b)) {
                        i2 = LogType.addType(i3, 1);
                        if (b.d && JNIBridge.nativeAddCachedInfo(str, str2)) {
                            i2 = LogType.addType(i2, 1);
                        }
                    }
                }
                i2 = i3;
                i2 = LogType.addType(i2, 1);
            }
        }
        return i2;
    }

    public static void f() {
        if (j || b.d) {
            synchronized (C) {
                for (String next : D) {
                    C0105a aVar = C.get(next);
                    if (LogType.isForNative(aVar.b) && JNIBridge.nativeCreateCachedInfo(next, aVar.a)) {
                        Iterator<String> it = aVar.c.iterator();
                        while (true) {
                            if (it.hasNext()) {
                                if (!JNIBridge.nativeAddCachedInfo(next, it.next())) {
                                    break;
                                }
                            } else {
                                break;
                            }
                        }
                    }
                }
            }
            return;
        }
        throw new AssertionError();
    }

    public static void b(OutputStream outputStream, String str, String str2, ArrayList<String> arrayList) {
        synchronized (C) {
            for (String next : D) {
                C0105a aVar = C.get(next);
                int i2 = aVar.b;
                if (arrayList == null) {
                    if (!LogType.isForJava(i2)) {
                        continue;
                    }
                } else if (!a(arrayList, next)) {
                    continue;
                }
                try {
                    outputStream.write(String.format(Locale.US, "%s (%d/%d)\n", new Object[]{next, Integer.valueOf(aVar.c.size()), Integer.valueOf(aVar.a)}).getBytes(str));
                } catch (Throwable th) {
                    f.a(th, outputStream);
                }
                try {
                    for (String bytes : aVar.c) {
                        outputStream.write(bytes.getBytes(str));
                        outputStream.write("\n".getBytes(str));
                    }
                } catch (Throwable th2) {
                    f.a(th2, outputStream);
                }
                try {
                    outputStream.write("\n".getBytes(str));
                    outputStream.write(str2.getBytes(str));
                } catch (Throwable th3) {
                    f.a(th3, outputStream);
                }
            }
        }
        return;
    }

    public static int a(int i2, String str) {
        if (h.a(str)) {
            str = Thread.currentThread().getName();
        }
        int i3 = 0;
        if (LogType.isForNative(i2)) {
            if (b.d) {
                synchronized (A) {
                    try {
                        JNIBridge.nativeRegisterCurrentThread(str);
                    }
                }
                i3 = 1;
            } else {
                c.a("crashsdk", "crashsdk so has not loaded!");
            }
        }
        if (!LogType.isForJava(i2)) {
            return i3;
        }
        a(str, Thread.currentThread());
        return i3 | 16;
    }

    public static boolean a(boolean z2) {
        int C2;
        if (!b.c || !b.q()) {
            c.b("Unexp log or unexp stats not enabled, skip update unexp info!");
            return false;
        }
        if (z2) {
            i.a(G);
            C2 = 0;
        } else if (!b.o()) {
            c.b("Stop update unexp info in background!");
            return false;
        } else if (p.C() <= 0) {
            return false;
        } else {
            if (i.b(G)) {
                return true;
            }
            C2 = p.C() * 1000;
        }
        i.a(0, G, (long) C2);
        return true;
    }

    public static ArrayList<String> b(String str) {
        if (h.a(str)) {
            return null;
        }
        String[] split = str.split(";");
        ArrayList<String> arrayList = new ArrayList<>();
        for (String str2 : split) {
            if (!h.a(str2)) {
                arrayList.add(str2);
            }
        }
        return arrayList;
    }

    public static String g() {
        if (!H) {
            m = d.a(b.g());
            H = true;
            if (m == null) {
                m = "";
            }
        }
        return m;
    }

    public static void h() {
        if (!I) {
            I = true;
            i.a(0, new d(2));
        } else if (b.d) {
            JNIBridge.nativeSyncStatus("ver", m, 0);
        }
    }
}
