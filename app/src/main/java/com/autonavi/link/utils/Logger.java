package com.autonavi.link.utils;

import android.os.Environment;
import android.os.Process;
import android.os.SystemClock;
import com.autonavi.link.protocol.http.MultipartUtility;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Logger {
    private static SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    /* access modifiers changed from: private */
    public static boolean b = true;
    /* access modifiers changed from: private */
    public static a c = new a();
    private static Calendar d;

    static class a {
        private String a;
        private boolean b;
        private boolean c;
        private File d;
        private StringBuffer e;
        private long f;

        public a() {
            this.b = false;
            this.c = false;
            this.e = new StringBuffer();
            this.f = 0;
            this.f = SystemClock.elapsedRealtime();
        }

        public void a() {
            StringBuilder sb = new StringBuilder();
            sb.append(Environment.getExternalStorageDirectory().getPath());
            sb.append("/Amapautolog/autolog/");
            this.a = sb.toString();
            Logger.b = new File(this.a).isDirectory();
        }

        /* JADX WARNING: Removed duplicated region for block: B:14:0x0018 A[SYNTHETIC, Splitter:B:14:0x0018] */
        /* JADX WARNING: Removed duplicated region for block: B:20:0x001e A[SYNTHETIC, Splitter:B:20:0x001e] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private boolean a(byte[] r4, boolean r5) {
            /*
                r3 = this;
                r0 = 0
                java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x001c, all -> 0x0015 }
                java.io.File r2 = r3.d     // Catch:{ Exception -> 0x001c, all -> 0x0015 }
                r1.<init>(r2, r5)     // Catch:{ Exception -> 0x001c, all -> 0x0015 }
                r1.write(r4)     // Catch:{ Exception -> 0x0013, all -> 0x0010 }
                r4 = 1
                r1.close()     // Catch:{ IOException -> 0x0022 }
                goto L_0x0022
            L_0x0010:
                r4 = move-exception
                r0 = r1
                goto L_0x0016
            L_0x0013:
                r0 = r1
                goto L_0x001c
            L_0x0015:
                r4 = move-exception
            L_0x0016:
                if (r0 == 0) goto L_0x001b
                r0.close()     // Catch:{ IOException -> 0x001b }
            L_0x001b:
                throw r4
            L_0x001c:
                if (r0 == 0) goto L_0x0021
                r0.close()     // Catch:{ IOException -> 0x0021 }
            L_0x0021:
                r4 = 0
            L_0x0022:
                return r4
            */
            throw new UnsupportedOperationException("Method not decompiled: com.autonavi.link.utils.Logger.a.a(byte[], boolean):boolean");
        }

        /* access modifiers changed from: private */
        public void b() {
            if (this.d == null) {
                this.d = new File(this.a, "linkSdk.log");
            }
            try {
                if (!this.d.exists()) {
                    this.d.createNewFile();
                    return;
                }
                if (this.d.length() > 20971520) {
                    c();
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }

        private void c() throws IOException {
            String parent = this.d.getParent();
            String name = this.d.getName();
            StringBuilder sb = new StringBuilder();
            sb.append(this.d.getName());
            sb.append(".51");
            File file = new File(parent, sb.toString());
            StringBuilder sb2 = new StringBuilder("deleteFile =");
            sb2.append(file.getAbsolutePath());
            sb2.append(",");
            sb2.append(file.exists());
            if (file.exists()) {
                file.delete();
            }
            for (int i = 50; i > 0; i--) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(this.d.getName());
                sb3.append(".");
                sb3.append(i);
                File file2 = new File(parent, sb3.toString());
                if (file2.exists()) {
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append(this.d.getName());
                    sb4.append(".");
                    sb4.append(i + 1);
                    file2.renameTo(new File(parent, sb4.toString()));
                }
            }
            File file3 = this.d;
            StringBuilder sb5 = new StringBuilder();
            sb5.append(this.d.getName());
            sb5.append(".1");
            file3.renameTo(new File(parent, sb5.toString()));
            this.d = new File(parent, name);
            this.d.createNewFile();
        }

        public void a(String str, long j, String str2, Object... objArr) {
            StringBuffer stringBuffer = this.e;
            stringBuffer.append(new Date());
            stringBuffer.append("##");
            StringBuffer stringBuffer2 = this.e;
            stringBuffer2.append("/");
            stringBuffer2.append(str);
            stringBuffer2.append(":");
            Logger.b(this.e, str2, objArr);
            this.e.append(MultipartUtility.LINE_FEED);
            a(this.e.toString().getBytes(), true);
            this.f = SystemClock.elapsedRealtime();
            this.e.setLength(0);
        }
    }

    public static class b {
        private static b a;
        private List<Runnable> b = Collections.synchronizedList(new LinkedList());
        private Object c = new Object();
        /* access modifiers changed from: private */
        public boolean d;
        private boolean e = false;

        private b() {
            c();
        }

        public static b a() {
            if (a == null) {
                a = new b();
            }
            return a;
        }

        public void a(final String str, final String str2, final Object... objArr) {
            System.currentTimeMillis();
            this.b.add(new Runnable() {
                public void run() {
                    Logger.b(new StringBuffer(), str2, objArr);
                }
            });
            e();
        }

        public void a(String str, String str2, Throwable th, Object... objArr) {
            System.currentTimeMillis();
            List<Runnable> list = this.b;
            final String str3 = str2;
            final Object[] objArr2 = objArr;
            final String str4 = str;
            final Throwable th2 = th;
            AnonymousClass2 r1 = new Runnable() {
                public void run() {
                    Logger.b(new StringBuffer(), str3, objArr2);
                }
            };
            list.add(r1);
            e();
        }

        public void b(String str, String str2, Object... objArr) {
            final long currentTimeMillis = System.currentTimeMillis();
            List<Runnable> list = this.b;
            final String str3 = str;
            final String str4 = str2;
            final Object[] objArr2 = objArr;
            AnonymousClass3 r0 = new Runnable() {
                public void run() {
                    b.this.b();
                    Logger.c.b();
                    Logger.c.a(str3, currentTimeMillis, str4, objArr2);
                }
            };
            list.add(r0);
            e();
        }

        /* access modifiers changed from: private */
        public void b() {
            try {
                Process.setThreadPriority(19);
                Thread.currentThread().setPriority(1);
            } catch (Throwable unused) {
            }
        }

        private void c() {
            this.d = true;
            new Thread(new Runnable() {
                public void run() {
                    b.this.b();
                    while (b.this.d) {
                        try {
                            b.this.d();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, "LoggerThread").start();
        }

        /* access modifiers changed from: private */
        public void d() throws InterruptedException {
            if (this.b.size() > 0) {
                try {
                    this.b.remove(0).run();
                } catch (Throwable unused) {
                }
                return;
            }
            synchronized (this.c) {
                this.e = true;
                this.c.wait();
                this.e = false;
            }
        }

        private void e() {
            if (this.e) {
                try {
                    synchronized (this.c) {
                        this.c.notify();
                    }
                } catch (Throwable unused) {
                }
            }
        }
    }

    public static void setLog(boolean z) {
    }

    public static boolean getIsLog() {
        return b;
    }

    public static void init() {
        c.a();
    }

    public static void d(String str, String str2, Object... objArr) {
        if (b) {
            b.a().a(str, str2, objArr);
        }
        if (b) {
            b.a().b(str, str2, objArr);
        }
    }

    public static void e(String str, String str2, Throwable th, Object... objArr) {
        if (b) {
            b.a().a(str, str2, th, objArr);
        }
        if (b) {
            b.a().b(str, str2, objArr);
        }
    }

    public static void D(String str, Object... objArr) {
        d((String) "link_sdk", str, objArr);
    }

    public static void E(String str, Throwable th, Object... objArr) {
        e("link_sdk", str, th, objArr);
    }

    /* access modifiers changed from: private */
    public static void b(StringBuffer stringBuffer, String str, Object... objArr) {
        if (str != null) {
            if (objArr == null) {
                stringBuffer.append(str);
                return;
            }
            String[] split = str.split("\\{\\?\\}");
            int min = Math.min(split.length, objArr.length);
            for (int i = 0; i < min; i++) {
                Object obj = objArr[i];
                stringBuffer.append(split[i]);
                stringBuffer.append(obj);
            }
            while (min < split.length) {
                stringBuffer.append(split[min]);
                min++;
            }
        }
    }

    public static void d(Class cls, String str, Object... objArr) {
        d(cls.getSimpleName(), str, objArr);
    }

    public static void pd(String str, Object... objArr) {
        d((String) "pino", str, objArr);
    }

    public static void main(String[] strArr) {
        a(new File("F:\\log", "autolog.log"));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x006b, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
        r0.append(r3);
        r4.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00b1, code lost:
        r9 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x00df, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x00e0, code lost:
        r0.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:?, code lost:
        r2.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x00e9, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x00ea, code lost:
        r0.printStackTrace();
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00b1 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:5:0x0029] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00c3 A[SYNTHETIC, Splitter:B:43:0x00c3] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00cd A[SYNTHETIC, Splitter:B:48:0x00cd] */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x00d6 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x00db A[SYNTHETIC, Splitter:B:58:0x00db] */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x00e5 A[SYNTHETIC, Splitter:B:63:0x00e5] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void a(java.io.File r9) {
        /*
            r0 = 0
            java.io.File r1 = new java.io.File     // Catch:{ Exception -> 0x00bc, all -> 0x00b8 }
            java.lang.String r2 = r9.getParent()     // Catch:{ Exception -> 0x00bc, all -> 0x00b8 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00bc, all -> 0x00b8 }
            r3.<init>()     // Catch:{ Exception -> 0x00bc, all -> 0x00b8 }
            java.lang.String r4 = r9.getName()     // Catch:{ Exception -> 0x00bc, all -> 0x00b8 }
            r3.append(r4)     // Catch:{ Exception -> 0x00bc, all -> 0x00b8 }
            java.lang.String r4 = ".new"
            r3.append(r4)     // Catch:{ Exception -> 0x00bc, all -> 0x00b8 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x00bc, all -> 0x00b8 }
            r1.<init>(r2, r3)     // Catch:{ Exception -> 0x00bc, all -> 0x00b8 }
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x00bc, all -> 0x00b8 }
            r2.<init>(r1)     // Catch:{ Exception -> 0x00bc, all -> 0x00b8 }
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch:{ Exception -> 0x00b6 }
            r1.<init>(r9)     // Catch:{ Exception -> 0x00b6 }
            java.io.BufferedReader r9 = new java.io.BufferedReader     // Catch:{ Exception -> 0x00b3, all -> 0x00b1 }
            java.io.InputStreamReader r0 = new java.io.InputStreamReader     // Catch:{ Exception -> 0x00b3, all -> 0x00b1 }
            r0.<init>(r1)     // Catch:{ Exception -> 0x00b3, all -> 0x00b1 }
            r9.<init>(r0)     // Catch:{ Exception -> 0x00b3, all -> 0x00b1 }
            java.lang.StringBuffer r0 = new java.lang.StringBuffer     // Catch:{ Exception -> 0x00b3, all -> 0x00b1 }
            r0.<init>()     // Catch:{ Exception -> 0x00b3, all -> 0x00b1 }
        L_0x0038:
            java.lang.String r3 = r9.readLine()     // Catch:{ Exception -> 0x00b3, all -> 0x00b1 }
            if (r3 == 0) goto L_0x0092
            java.lang.String r4 = "##/"
            int r4 = r3.indexOf(r4)     // Catch:{ Exception -> 0x00b3, all -> 0x00b1 }
            r5 = 0
            if (r4 <= 0) goto L_0x0073
            java.lang.String r4 = r3.substring(r5, r4)     // Catch:{ Exception -> 0x006b, all -> 0x00b1 }
            long r6 = java.lang.Long.parseLong(r4)     // Catch:{ Exception -> 0x006b, all -> 0x00b1 }
            java.lang.String r4 = "["
            r0.append(r4)     // Catch:{ Exception -> 0x006b, all -> 0x00b1 }
            java.text.SimpleDateFormat r4 = a     // Catch:{ Exception -> 0x006b, all -> 0x00b1 }
            java.util.Date r8 = new java.util.Date     // Catch:{ Exception -> 0x006b, all -> 0x00b1 }
            r8.<init>(r6)     // Catch:{ Exception -> 0x006b, all -> 0x00b1 }
            java.lang.String r4 = r4.format(r8)     // Catch:{ Exception -> 0x006b, all -> 0x00b1 }
            r0.append(r4)     // Catch:{ Exception -> 0x006b, all -> 0x00b1 }
            java.lang.String r4 = "]"
            r0.append(r4)     // Catch:{ Exception -> 0x006b, all -> 0x00b1 }
            r0.append(r3)     // Catch:{ Exception -> 0x006b, all -> 0x00b1 }
            goto L_0x0076
        L_0x006b:
            r4 = move-exception
            r0.append(r3)     // Catch:{ Exception -> 0x00b3, all -> 0x00b1 }
            r4.printStackTrace()     // Catch:{ Exception -> 0x00b3, all -> 0x00b1 }
            goto L_0x0076
        L_0x0073:
            r0.append(r3)     // Catch:{ Exception -> 0x00b3, all -> 0x00b1 }
        L_0x0076:
            java.lang.String r3 = "\r\n"
            r0.append(r3)     // Catch:{ Exception -> 0x00b3, all -> 0x00b1 }
            int r3 = r0.length()     // Catch:{ Exception -> 0x00b3, all -> 0x00b1 }
            r4 = 10000(0x2710, float:1.4013E-41)
            if (r3 <= r4) goto L_0x0038
            java.lang.String r3 = r0.toString()     // Catch:{ Exception -> 0x00b3, all -> 0x00b1 }
            byte[] r3 = r3.getBytes()     // Catch:{ Exception -> 0x00b3, all -> 0x00b1 }
            r2.write(r3)     // Catch:{ Exception -> 0x00b3, all -> 0x00b1 }
            r0.setLength(r5)     // Catch:{ Exception -> 0x00b3, all -> 0x00b1 }
            goto L_0x0038
        L_0x0092:
            java.lang.String r9 = r0.toString()     // Catch:{ Exception -> 0x00b3, all -> 0x00b1 }
            byte[] r9 = r9.getBytes()     // Catch:{ Exception -> 0x00b3, all -> 0x00b1 }
            r2.write(r9)     // Catch:{ Exception -> 0x00b3, all -> 0x00b1 }
            r2.flush()     // Catch:{ Exception -> 0x00b3, all -> 0x00b1 }
            r1.close()     // Catch:{ IOException -> 0x00a4 }
            goto L_0x00a8
        L_0x00a4:
            r9 = move-exception
            r9.printStackTrace()
        L_0x00a8:
            r2.close()     // Catch:{ IOException -> 0x00ac }
            return
        L_0x00ac:
            r9 = move-exception
            r9.printStackTrace()
            return
        L_0x00b1:
            r9 = move-exception
            goto L_0x00d9
        L_0x00b3:
            r9 = move-exception
            r0 = r1
            goto L_0x00be
        L_0x00b6:
            r9 = move-exception
            goto L_0x00be
        L_0x00b8:
            r9 = move-exception
            r1 = r0
            r2 = r1
            goto L_0x00d9
        L_0x00bc:
            r9 = move-exception
            r2 = r0
        L_0x00be:
            r9.printStackTrace()     // Catch:{ all -> 0x00d7 }
            if (r0 == 0) goto L_0x00cb
            r0.close()     // Catch:{ IOException -> 0x00c7 }
            goto L_0x00cb
        L_0x00c7:
            r9 = move-exception
            r9.printStackTrace()
        L_0x00cb:
            if (r2 == 0) goto L_0x00d6
            r2.close()     // Catch:{ IOException -> 0x00d1 }
            return
        L_0x00d1:
            r9 = move-exception
            r9.printStackTrace()
            return
        L_0x00d6:
            return
        L_0x00d7:
            r9 = move-exception
            r1 = r0
        L_0x00d9:
            if (r1 == 0) goto L_0x00e3
            r1.close()     // Catch:{ IOException -> 0x00df }
            goto L_0x00e3
        L_0x00df:
            r0 = move-exception
            r0.printStackTrace()
        L_0x00e3:
            if (r2 == 0) goto L_0x00ed
            r2.close()     // Catch:{ IOException -> 0x00e9 }
            goto L_0x00ed
        L_0x00e9:
            r0 = move-exception
            r0.printStackTrace()
        L_0x00ed:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.link.utils.Logger.a(java.io.File):void");
    }
}
