package com.amap.location.common.d;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.amap.location.common.d.d.b;
import com.amap.location.common.d.d.c;
import com.amap.location.common.f.e;
import java.io.File;
import java.io.FileFilter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/* compiled from: ALLog */
public class a {
    private static String A = "";
    private static c B = null;
    /* access modifiers changed from: private */
    public static final Runnable C = new Runnable() {
        public final void run() {
            try {
                if (!a.l()) {
                    a.o();
                    return;
                }
                File[] b = a.f(a.f);
                if (b != null && b.length > 0) {
                    synchronized (a.t) {
                        for (File offer : b) {
                            a.t.offer(offer);
                        }
                    }
                }
                a.y = e.a(a.x);
                a.r = a.m();
                if (a.r == null) {
                    a.o();
                    return;
                }
                a.z = true;
                a.p.sendMessageDelayed(a.p.obtainMessage(2), 20000);
            } catch (Exception e) {
                a.b("ALLog", "InitLogFileTask  error ", e);
            }
        }
    };
    private static volatile boolean a = false;
    private static volatile boolean b = false;
    private static volatile boolean c = false;
    private static boolean d = false;
    private static boolean e = true;
    /* access modifiers changed from: private */
    public static volatile String f = "";
    private static c g = c.SDK;
    private static String h = "sdk";
    private static long i = 1048576;
    private static long j = 20;
    private static long k = 204800;
    private static final SimpleDateFormat l = new SimpleDateFormat("MM-dd HH:mm:ss:SSS", Locale.US);
    private static final SimpleDateFormat m = new SimpleDateFormat("yyyyMMdd-HHmmss-SSS", Locale.US);
    private static final Date n = new Date();
    private static b o = null;
    /* access modifiers changed from: private */
    public static volatile Handler p = null;
    /* access modifiers changed from: private */
    public static volatile HandlerThread q = null;
    /* access modifiers changed from: private */
    public static volatile File r = null;
    private static long s = 0;
    /* access modifiers changed from: private */
    public static final ArrayDeque<File> t = new ArrayDeque<>();
    private static volatile LinkedList<String> u = new LinkedList<>();
    private static LinkedList<LinkedList<String>> v = new LinkedList<>();
    private static final Object w = new Object();
    /* access modifiers changed from: private */
    public static volatile Context x = null;
    /* access modifiers changed from: private */
    public static String y = "";
    /* access modifiers changed from: private */
    public static volatile boolean z = false;

    public static String a(String str) {
        return str;
    }

    public static void a(Throwable th) {
    }

    public static void a(Context context, d dVar) {
        if (x == null) {
            x = context.getApplicationContext();
            a = dVar.a();
            b = dVar.b();
            c = dVar.c();
            f = dVar.i();
            o = dVar.k();
            e = dVar.d();
            d = dVar.e();
            k = (long) dVar.g();
            j = (long) dVar.f();
            i = (long) dVar.h();
            a(dVar.j());
            A = String.valueOf(Process.myPid());
            if (b) {
                k();
            }
        }
    }

    public static void a(c cVar) {
        if (cVar != null) {
            B = cVar;
        }
    }

    public static void a(String str, String str2) {
        if (B != null) {
            B.d(str, str2);
        }
    }

    public static void b(String str, String str2) {
        if (B != null) {
            B.a(str, str2);
        }
        a(1, str, str2, d, e);
    }

    public static void c(String str, String str2) {
        if (B != null) {
            B.b(str, str2);
        }
        a(2, str, str2, d, e);
    }

    public static void a(String str, String str2, Throwable th) {
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        sb.append(Token.SEPARATOR);
        sb.append(Log.getStackTraceString(th));
        c(str, sb.toString());
    }

    public static void d(String str, String str2) {
        if (B != null) {
            B.c(str, str2);
        }
        a(4, str, str2, d, e);
    }

    public static void b(String str, String str2, Throwable th) {
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        sb.append(Token.SEPARATOR);
        sb.append(Log.getStackTraceString(th));
        d(str, sb.toString());
    }

    private static void a(int i2, String str, String str2, boolean z2, boolean z3) {
        boolean z4 = z2 && b && z;
        boolean z5 = z3 && c && o != null && o.a();
        if (z4 || z5) {
            StringBuilder sb = new StringBuilder();
            sb.append(String.valueOf(System.currentTimeMillis()));
            sb.append(MergeUtil.SEPARATOR_KV);
            if (i2 != 4) {
                switch (i2) {
                    case 1:
                        sb.append("info|");
                        break;
                    case 2:
                        sb.append("warn|");
                        break;
                }
            } else {
                sb.append("error|");
            }
            if (i2 != 8) {
                StringBuilder sb2 = new StringBuilder("@@_");
                sb2.append(com.amap.location.common.f.a.a(str2));
                sb2.append("_@@");
                str2 = sb2.toString();
            }
            sb.append(A);
            sb.append(MergeUtil.SEPARATOR_KV);
            sb.append(String.valueOf((long) Process.myTid()));
            sb.append(MergeUtil.SEPARATOR_KV);
            sb.append(str);
            sb.append(MergeUtil.SEPARATOR_KV);
            sb.append(str2);
            sb.append("\n");
            if (z4) {
                d(sb.toString());
            }
            if (z5) {
                e(sb.substring(0, sb.length() - 1));
            }
        }
    }

    private static String a(DateFormat dateFormat) {
        String format;
        synchronized (n) {
            n.setTime(System.currentTimeMillis());
            format = dateFormat.format(n);
        }
        return format;
    }

    private static void d(String str) {
        synchronized (w) {
            u.add(str);
            s += (long) str.length();
            if (((long) u.size()) >= 5000 || s > k) {
                if (p != null) {
                    v.add(u);
                    while (((long) v.size()) > 5) {
                        v.removeFirst();
                    }
                    p.obtainMessage(1).sendToTarget();
                    p.removeMessages(2);
                    u = new LinkedList<>();
                    s = 0;
                } else {
                    u.clear();
                    s = 0;
                }
            }
        }
    }

    private static void e(String str) {
        if (o != null) {
            o.a(str);
        }
    }

    private static void k() {
        StringBuilder sb = new StringBuilder("allog");
        sb.append(Process.myPid());
        AnonymousClass1 r0 = new HandlerThread(sb.toString()) {
            /* access modifiers changed from: protected */
            public final void onLooperPrepared() {
                Looper looper = a.q.getLooper();
                if (looper != null) {
                    a.p = new Handler(looper) {
                        public void handleMessage(Message message) {
                            a.b(message);
                        }
                    };
                    a.p.post(a.C);
                }
            }
        };
        q = r0;
        r0.start();
    }

    /* access modifiers changed from: private */
    public static void b(Message message) {
        switch (message.what) {
            case 1:
                LinkedList linkedList = null;
                synchronized (w) {
                    if (v.size() > 0) {
                        linkedList = v.removeFirst();
                    }
                }
                System.currentTimeMillis();
                a((List<String>) linkedList);
                System.currentTimeMillis();
                if (p != null) {
                    p.sendMessageDelayed(p.obtainMessage(2), 20000);
                    return;
                }
                break;
            case 2:
                synchronized (w) {
                    if (p != null) {
                        v.add(u);
                        while (((long) v.size()) > 5) {
                            v.removeFirst();
                        }
                        p.obtainMessage(1).sendToTarget();
                        u = new LinkedList<>();
                        s = 0;
                    } else {
                        u.clear();
                        s = 0;
                    }
                }
                return;
        }
    }

    private static void a(List<String> list) {
        if (list != null) {
            try {
                if (list.size() != 0) {
                    StringBuilder sb = new StringBuilder();
                    for (String append : list) {
                        sb.append(append);
                    }
                    if (!a(sb.toString(), r)) {
                        o();
                        return;
                    }
                    synchronized (t) {
                        while (((long) (t.size() + 1)) > j) {
                            File poll = t.poll();
                            if (poll != null && poll.exists()) {
                                try {
                                    poll.delete();
                                } catch (Exception e2) {
                                    b("ALLog", "MAX_FILE_NUM delete  error ", e2);
                                }
                            }
                        }
                    }
                    if (r.length() > i) {
                        synchronized (t) {
                            t.offer(r);
                        }
                        File m2 = m();
                        r = m2;
                        if (m2 == null) {
                            o();
                        }
                    }
                }
            } catch (Exception e3) {
                b("ALLog", "DumpTask  error ", e3);
            }
        }
    }

    /* access modifiers changed from: private */
    public static boolean l() {
        File file = new File(f);
        if (file.exists() && file.isFile()) {
            file.delete();
        }
        boolean exists = file.exists();
        if (!exists) {
            exists = file.mkdirs();
        }
        if (!exists || !file.canWrite()) {
            return false;
        }
        File file2 = new File(file, com.amap.location.common.b.e());
        if (file2.exists() || file2.mkdir()) {
            f = file2.getAbsolutePath();
        }
        return true;
    }

    /* access modifiers changed from: private */
    public static File[] f(String str) {
        File[] listFiles = new File(str).listFiles(new FileFilter() {
            public final boolean accept(File file) {
                return !file.isDirectory();
            }
        });
        if (listFiles == null || listFiles.length == 0) {
            return null;
        }
        Arrays.sort(listFiles, new Comparator<File>() {
            /* renamed from: a */
            public final int compare(File file, File file2) {
                int i = ((file.lastModified() - file2.lastModified()) > 0 ? 1 : ((file.lastModified() - file2.lastModified()) == 0 ? 0 : -1));
                if (i > 0) {
                    return 1;
                }
                return i < 0 ? -1 : 0;
            }
        });
        return listFiles;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0031, code lost:
        r1 = f;
        r3 = new java.lang.StringBuilder();
        r3.append(n());
        r3.append("_log_");
        r3.append(a((java.text.DateFormat) m));
        r3.append(".txt");
        r0 = new java.io.File(r1, r3.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
        r0.createNewFile();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0064, code lost:
        if (android.text.TextUtils.isEmpty(y) != false) goto L_0x007d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0066, code lost:
        r1 = new java.lang.StringBuilder();
        r1.append(y);
        r1.append("\r\n-------------------\r\n");
        com.amap.location.common.f.e.a(r1.toString(), r0, true);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x007d, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x007e, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.io.File m() {
        /*
            java.util.ArrayDeque<java.io.File> r0 = t
            monitor-enter(r0)
            java.util.ArrayDeque<java.io.File> r1 = t     // Catch:{ all -> 0x007f }
            int r1 = r1.size()     // Catch:{ all -> 0x007f }
            r2 = 0
            if (r1 <= 0) goto L_0x0015
            java.util.ArrayDeque<java.io.File> r1 = t     // Catch:{ all -> 0x007f }
            java.lang.Object r1 = r1.getLast()     // Catch:{ all -> 0x007f }
            java.io.File r1 = (java.io.File) r1     // Catch:{ all -> 0x007f }
            goto L_0x0016
        L_0x0015:
            r1 = r2
        L_0x0016:
            if (r1 == 0) goto L_0x0030
            long r3 = r1.length()     // Catch:{ all -> 0x007f }
            long r5 = i     // Catch:{ all -> 0x007f }
            r7 = 2
            long r5 = r5 * r7
            r7 = 3
            long r5 = r5 / r7
            int r3 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r3 >= 0) goto L_0x0030
            java.util.ArrayDeque<java.io.File> r2 = t     // Catch:{ all -> 0x007f }
            r2.removeLast()     // Catch:{ all -> 0x007f }
            monitor-exit(r0)     // Catch:{ all -> 0x007f }
            return r1
        L_0x0030:
            monitor-exit(r0)     // Catch:{ all -> 0x007f }
            java.io.File r0 = new java.io.File
            java.lang.String r1 = f
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = n()
            r3.append(r4)
            java.lang.String r4 = "_log_"
            r3.append(r4)
            java.text.SimpleDateFormat r4 = m
            java.lang.String r4 = a(r4)
            r3.append(r4)
            java.lang.String r4 = ".txt"
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            r0.<init>(r1, r3)
            r0.createNewFile()     // Catch:{ IOException -> 0x007e }
            java.lang.String r1 = y
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            if (r1 != 0) goto L_0x007d
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = y
            r1.append(r2)
            java.lang.String r2 = "\r\n-------------------\r\n"
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r2 = 1
            com.amap.location.common.f.e.a(r1, r0, r2)
        L_0x007d:
            return r0
        L_0x007e:
            return r2
        L_0x007f:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x007f }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.common.d.a.m():java.io.File");
    }

    private static boolean a(String str, File file) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("\r\n-------------------\r\n");
        if (e.a(sb.toString(), file, true)) {
            return true;
        }
        if (!file.exists()) {
            File parentFile = file.getParentFile();
            File file2 = parentFile;
            int i2 = 0;
            while (true) {
                if (file2 != null) {
                    if (!file2.exists()) {
                        file2 = file2.getParentFile();
                        i2++;
                        if (i2 >= 2) {
                            break;
                        }
                    } else if (file2.isFile()) {
                        file2.delete();
                    }
                } else {
                    break;
                }
            }
            if (parentFile != null) {
                try {
                    if (!parentFile.exists()) {
                        parentFile.mkdirs();
                    }
                    file.createNewFile();
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(str);
                    sb2.append("\r\n-------------------\r\n");
                    return e.a(sb2.toString(), file, true);
                } catch (Exception unused) {
                }
            }
        }
        return false;
    }

    private static String n() {
        return h;
    }

    private static void a(c cVar) {
        g = cVar;
        switch (cVar) {
            case FLP:
                h = "flp";
                return;
            case NLP:
                h = "nlp";
                break;
        }
    }

    /* access modifiers changed from: private */
    public static void o() {
        z = false;
        try {
            if (q != null) {
                if (VERSION.SDK_INT > 18) {
                    q.quitSafely();
                } else {
                    q.quit();
                }
            }
            p = null;
            q = null;
            synchronized (t) {
                t.clear();
            }
            synchronized (w) {
                u.clear();
                v.clear();
            }
        } catch (Exception e2) {
            try {
                b("ALLog", "dispose error ", e2);
                p = null;
                q = null;
                synchronized (t) {
                    t.clear();
                    synchronized (w) {
                        u.clear();
                        v.clear();
                    }
                }
            } catch (Throwable th) {
                p = null;
                q = null;
                synchronized (t) {
                    t.clear();
                    synchronized (w) {
                        u.clear();
                        v.clear();
                        throw th;
                    }
                }
            }
        }
    }
}
