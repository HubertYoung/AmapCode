package com.uc.crashsdk;

import android.util.SparseArray;
import com.uc.crashsdk.a.c;
import com.uc.crashsdk.a.h;
import com.uc.crashsdk.b.a;
import java.io.File;

/* compiled from: ProGuard */
public final class k {
    private static Object a = new Object();
    private static SparseArray<String> b = new SparseArray<>();
    private static boolean c = false;
    private static volatile Object d = new Object();

    public static void a(int i) {
        a(i, 1);
    }

    public static void a(int i, int i2) {
        if (i2 == 0) {
            c.c("Add stat for type " + i + " with count 0!");
            return;
        }
        String c2 = b.c();
        a(c2, (a) new l(c2, i, i2));
    }

    /* access modifiers changed from: private */
    public static boolean c(int i, int i2) {
        try {
            b.m();
        } catch (Throwable th) {
            com.uc.crashsdk.a.a.a(th, false);
        }
        try {
            String b2 = b(i);
            if (b2 == null) {
                c.a("crashsdk", "Stat type not exists: " + i);
                return false;
            }
            File file = new File(b.c());
            try {
                if (!file.exists()) {
                    file.createNewFile();
                }
            } catch (Throwable th2) {
                com.uc.crashsdk.a.a.a(th2, false);
            }
            StringBuffer a2 = a(file);
            if (h.a(a2)) {
                if (a2 == null) {
                    a2 = new StringBuffer();
                }
                a2.append("[" + f.d() + "]\n");
            }
            a(a2, b2, a(a2, b2) + i2);
            return a(file, a2);
        } catch (Exception e) {
            com.uc.crashsdk.a.a.a(e, false);
            return false;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x003c A[SYNTHETIC, Splitter:B:22:0x003c] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x004a A[SYNTHETIC, Splitter:B:29:0x004a] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.StringBuffer a(java.io.File r7) {
        /*
            r0 = 0
            r5 = 0
            boolean r1 = r7.exists()
            if (r1 != 0) goto L_0x0009
        L_0x0008:
            return r0
        L_0x0009:
            char[] r3 = d()
            if (r3 != 0) goto L_0x0017
            java.lang.String r1 = "readCrashStatData alloc buffer failed!"
            java.lang.String r2 = "crashsdk"
            com.uc.crashsdk.a.c.a(r2, r1)
            goto L_0x0008
        L_0x0017:
            java.lang.StringBuffer r1 = new java.lang.StringBuffer
            r1.<init>()
            java.io.FileReader r2 = new java.io.FileReader     // Catch:{ Exception -> 0x0032, all -> 0x0045 }
            r2.<init>(r7)     // Catch:{ Exception -> 0x0032, all -> 0x0045 }
            int r0 = r2.read(r3)     // Catch:{ Exception -> 0x0057 }
            if (r0 <= 0) goto L_0x002b
            r4 = 0
            r1.append(r3, r4, r0)     // Catch:{ Exception -> 0x0057 }
        L_0x002b:
            if (r2 == 0) goto L_0x0030
            r2.close()     // Catch:{ Exception -> 0x0053 }
        L_0x0030:
            r0 = r1
            goto L_0x0008
        L_0x0032:
            r2 = move-exception
            r6 = r2
            r2 = r0
            r0 = r6
        L_0x0036:
            r3 = 0
            com.uc.crashsdk.a.a.a(r0, r3)     // Catch:{ all -> 0x0055 }
            if (r2 == 0) goto L_0x0030
            r2.close()     // Catch:{ Exception -> 0x0040 }
            goto L_0x0030
        L_0x0040:
            r0 = move-exception
        L_0x0041:
            com.uc.crashsdk.a.a.a(r0, r5)
            goto L_0x0030
        L_0x0045:
            r1 = move-exception
            r2 = r0
            r0 = r1
        L_0x0048:
            if (r2 == 0) goto L_0x004d
            r2.close()     // Catch:{ Exception -> 0x004e }
        L_0x004d:
            throw r0
        L_0x004e:
            r1 = move-exception
            com.uc.crashsdk.a.a.a(r1, r5)
            goto L_0x004d
        L_0x0053:
            r0 = move-exception
            goto L_0x0041
        L_0x0055:
            r0 = move-exception
            goto L_0x0048
        L_0x0057:
            r0 = move-exception
            goto L_0x0036
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.crashsdk.k.a(java.io.File):java.lang.StringBuffer");
    }

    private static int a(StringBuffer stringBuffer, String str) {
        int i;
        int indexOf = stringBuffer.indexOf(str);
        if (indexOf < 0) {
            return 0;
        }
        int indexOf2 = stringBuffer.indexOf("=", indexOf + str.length());
        if (indexOf2 < 0) {
            c.c(str + " line not contain '='!");
            return 0;
        }
        int i2 = indexOf2 + 1;
        int indexOf3 = stringBuffer.indexOf("\n", i2);
        if (indexOf3 < 0) {
            indexOf3 = stringBuffer.length();
        }
        try {
            i = Integer.parseInt(stringBuffer.substring(i2, indexOf3));
            if (i < 0) {
                i = 0;
            }
        } catch (NumberFormatException e) {
            com.uc.crashsdk.a.a.a(e, false);
            i = 0;
        }
        return i;
    }

    private static void a(StringBuffer stringBuffer, String str, int i) {
        int indexOf = stringBuffer.indexOf(str);
        if (indexOf >= 0) {
            int indexOf2 = stringBuffer.indexOf("\n", indexOf);
            if (indexOf2 < 0) {
                indexOf2 = stringBuffer.length();
            }
            stringBuffer.replace(indexOf, indexOf2, str + "=" + String.valueOf(i));
        } else if (i > 0) {
            stringBuffer.append(str).append("=").append(i).append("\n");
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0022 A[SYNTHETIC, Splitter:B:15:0x0022] */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x002b A[SYNTHETIC, Splitter:B:21:0x002b] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean a(java.io.File r5, java.lang.StringBuffer r6) {
        /*
            r1 = 0
            r3 = 0
            java.io.FileWriter r2 = new java.io.FileWriter     // Catch:{ Exception -> 0x001a, all -> 0x0027 }
            r2.<init>(r5)     // Catch:{ Exception -> 0x001a, all -> 0x0027 }
            java.lang.String r0 = r6.toString()     // Catch:{ Exception -> 0x0040 }
            r3 = 0
            int r4 = r0.length()     // Catch:{ Exception -> 0x0040 }
            r2.write(r0, r3, r4)     // Catch:{ Exception -> 0x0040 }
            r0 = 1
            if (r2 == 0) goto L_0x0019
            r2.close()     // Catch:{ Exception -> 0x0039 }
        L_0x0019:
            return r0
        L_0x001a:
            r0 = move-exception
            r2 = r3
        L_0x001c:
            r3 = 0
            com.uc.crashsdk.a.a.a(r0, r3)     // Catch:{ all -> 0x003e }
            if (r2 == 0) goto L_0x0025
            r2.close()     // Catch:{ Exception -> 0x0034 }
        L_0x0025:
            r0 = r1
            goto L_0x0019
        L_0x0027:
            r0 = move-exception
            r2 = r3
        L_0x0029:
            if (r2 == 0) goto L_0x002e
            r2.close()     // Catch:{ Exception -> 0x002f }
        L_0x002e:
            throw r0
        L_0x002f:
            r2 = move-exception
            com.uc.crashsdk.a.a.a(r2, r1)
            goto L_0x002e
        L_0x0034:
            r0 = move-exception
            com.uc.crashsdk.a.a.a(r0, r1)
            goto L_0x0025
        L_0x0039:
            r2 = move-exception
            com.uc.crashsdk.a.a.a(r2, r1)
            goto L_0x0019
        L_0x003e:
            r0 = move-exception
            goto L_0x0029
        L_0x0040:
            r0 = move-exception
            goto L_0x001c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.crashsdk.k.a(java.io.File, java.lang.StringBuffer):boolean");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:54:?, code lost:
        return true;
     */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0090 A[SYNTHETIC, Splitter:B:35:0x0090] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean d(java.lang.String r11) {
        /*
            r2 = 1
            r0 = 0
            e()
            android.util.SparseArray<java.lang.String> r4 = b
            monitor-enter(r4)
            java.io.File r5 = new java.io.File     // Catch:{ all -> 0x0094 }
            r5.<init>(r11)     // Catch:{ all -> 0x0094 }
            java.lang.StringBuffer r6 = a(r5)     // Catch:{ all -> 0x0094 }
            boolean r1 = com.uc.crashsdk.a.h.a(r6)     // Catch:{ all -> 0x0094 }
            if (r1 == 0) goto L_0x001a
            monitor-exit(r4)     // Catch:{ all -> 0x0094 }
            r2 = r0
        L_0x0019:
            return r2
        L_0x001a:
            java.lang.String r1 = "["
            int r1 = r6.indexOf(r1)     // Catch:{ all -> 0x0094 }
            if (r1 >= 0) goto L_0x002c
            java.lang.String r1 = "Can not found process name start!"
            java.lang.String r2 = "crashsdk"
            com.uc.crashsdk.a.c.a(r2, r1)     // Catch:{ all -> 0x0094 }
            monitor-exit(r4)     // Catch:{ all -> 0x0094 }
            r2 = r0
            goto L_0x0019
        L_0x002c:
            int r1 = r1 + 1
            java.lang.String r3 = "]"
            int r3 = r6.indexOf(r3, r1)     // Catch:{ all -> 0x0094 }
            if (r3 >= 0) goto L_0x0040
            java.lang.String r1 = "Can not found process name end!"
            java.lang.String r2 = "crashsdk"
            com.uc.crashsdk.a.c.a(r2, r1)     // Catch:{ all -> 0x0094 }
            monitor-exit(r4)     // Catch:{ all -> 0x0094 }
            r2 = r0
            goto L_0x0019
        L_0x0040:
            java.lang.String r7 = r6.substring(r1, r3)     // Catch:{ all -> 0x0094 }
            r3 = r0
            r1 = r0
        L_0x0046:
            android.util.SparseArray<java.lang.String> r0 = b     // Catch:{ all -> 0x008d }
            int r0 = r0.size()     // Catch:{ all -> 0x008d }
            if (r3 >= r0) goto L_0x0097
            android.util.SparseArray<java.lang.String> r0 = b     // Catch:{ all -> 0x008d }
            int r8 = r0.keyAt(r3)     // Catch:{ all -> 0x008d }
            android.util.SparseArray<java.lang.String> r0 = b     // Catch:{ all -> 0x008d }
            java.lang.Object r0 = r0.get(r8)     // Catch:{ all -> 0x008d }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ all -> 0x008d }
            int r9 = a(r6, r0)     // Catch:{ all -> 0x008d }
            if (r9 <= 0) goto L_0x00a2
            boolean r10 = com.uc.crashsdk.d.a(r7, r8, r9)     // Catch:{ all -> 0x008d }
            com.uc.crashsdk.a.k.a(r7, r8, r9)     // Catch:{ all -> 0x008d }
            if (r10 != 0) goto L_0x0071
            boolean r9 = com.uc.crashsdk.p.I()     // Catch:{ all -> 0x008d }
            if (r9 == 0) goto L_0x00a2
        L_0x0071:
            r1 = 0
            a(r6, r0, r1)     // Catch:{ all -> 0x009f }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x009f }
            java.lang.String r1 = "Clear stat item: "
            r0.<init>(r1)     // Catch:{ all -> 0x009f }
            java.lang.StringBuilder r0 = r0.append(r8)     // Catch:{ all -> 0x009f }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x009f }
            com.uc.crashsdk.a.c.b(r0)     // Catch:{ all -> 0x009f }
            r0 = r2
        L_0x0088:
            int r1 = r3 + 1
            r3 = r1
            r1 = r0
            goto L_0x0046
        L_0x008d:
            r0 = move-exception
        L_0x008e:
            if (r1 == 0) goto L_0x0093
            a(r5, r6)     // Catch:{ all -> 0x0094 }
        L_0x0093:
            throw r0     // Catch:{ all -> 0x0094 }
        L_0x0094:
            r0 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0094 }
            throw r0
        L_0x0097:
            if (r1 == 0) goto L_0x009c
            a(r5, r6)     // Catch:{ all -> 0x0094 }
        L_0x009c:
            monitor-exit(r4)     // Catch:{ all -> 0x0094 }
            goto L_0x0019
        L_0x009f:
            r0 = move-exception
            r1 = r2
            goto L_0x008e
        L_0x00a2:
            r0 = r1
            goto L_0x0088
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.crashsdk.k.d(java.lang.String):boolean");
    }

    private static char[] d() {
        char[] cArr = null;
        int i = 1024;
        while (cArr == null && i > 0) {
            try {
                cArr = new char[i];
            } catch (Throwable th) {
                i /= 2;
                if (i < 512) {
                    break;
                }
            }
        }
        return cArr;
    }

    private static boolean a(String str, a aVar) {
        return b.a(a, str, aVar);
    }

    public static boolean a(String str) {
        return a(str, (a) new m(str));
    }

    private static String b(int i) {
        String str;
        e();
        synchronized (b) {
            str = b.get(i);
        }
        return str;
    }

    private static void e() {
        synchronized (b) {
            if (b.size() == 0) {
                b.put(100, "start_pv");
                b.put(1, "all_all");
                b.put(2, "all_fg");
                b.put(3, "java_fg");
                b.put(4, "java_bg");
                b.put(7, "native_fg");
                b.put(8, "native_bg");
                b.put(27, "native_anr_fg");
                b.put(28, "native_anr_bg");
                b.put(9, "native_ok");
                b.put(10, "unexp_anr");
                b.put(29, "unexp_lowmem");
                b.put(30, "unexp_killed");
                b.put(31, "unexp_exit");
                b.put(11, "unexp_fg");
                b.put(12, "unexp_bg");
                b.put(13, "log_up_succ");
                b.put(14, "log_up_fail");
                b.put(15, "log_empty");
                b.put(16, "log_abd_all");
                b.put(22, "log_abd_crash");
                b.put(23, "log_abd_custom");
                b.put(17, "log_large");
                b.put(18, "log_up_all");
                b.put(19, "log_up_bytes");
                b.put(20, "log_up_crash");
                b.put(21, "log_up_custom");
                b.put(24, "log_zipped");
                b.put(25, "log_renamed");
                b.put(26, "log_safe_skip");
            }
        }
    }

    private static File[] f() {
        return new File(p.N()).listFiles(new n());
    }

    public static int a() {
        int i = 0;
        File[] f = f();
        if (f != null) {
            for (File absolutePath : f) {
                if (a(absolutePath.getAbsolutePath())) {
                    i++;
                }
            }
        }
        return i;
    }

    public static boolean b(String str) {
        return a(str, (a) new o(str));
    }

    public static int b() {
        int i = 0;
        File[] f = f();
        if (f != null) {
            for (File absolutePath : f) {
                if (b(absolutePath.getAbsolutePath())) {
                    i++;
                }
            }
        }
        return i;
    }

    public static void c() {
        if (!c) {
            synchronized (d) {
                if (!c) {
                    c = true;
                    if (b.j() || b.k()) {
                        a(1, 1);
                        if (b.j()) {
                            a(2, 1);
                        }
                    }
                    a(100, 1);
                }
            }
        }
    }
}
