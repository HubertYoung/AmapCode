package com.uc.crashsdk;

import android.util.Log;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.autonavi.common.SuperId;
import com.uc.crashsdk.a.c;
import com.uc.crashsdk.a.h;
import com.uc.crashsdk.a.i;
import java.io.File;
import java.io.RandomAccessFile;

/* compiled from: ProGuard */
public final class b {
    private static Runnable A = new c();
    private static long B = 0;
    private static Object C = new Object();
    private static long D = 0;
    private static boolean E = false;
    private static boolean F = false;
    private static boolean G = false;
    public static boolean a = false;
    public static boolean b = false;
    public static boolean c = false;
    public static boolean d = false;
    public static boolean e = false;
    private static String f = null;
    private static String g = null;
    private static String h = null;
    private static String i = null;
    private static String j = null;
    private static String k = null;
    private static String l = null;
    private static boolean m = false;
    private static boolean n = false;
    private static volatile boolean o = false;
    private static boolean p = false;
    private static boolean q = false;
    private static boolean r = false;
    private static Object s = new Object();
    private static String t = null;
    private static int u = 0;
    private static RandomAccessFile v = null;
    /* access modifiers changed from: private */
    public static boolean w = false;
    private static Object x = new Object();
    private static String y = null;
    private static volatile Object[] z = null;

    /* compiled from: ProGuard */
    public abstract class a {
        protected String a;

        public abstract boolean a();

        public a(String str) {
            this.a = str;
        }
    }

    public static String a() {
        int i2;
        String str;
        if (t != null) {
            return t;
        }
        String d2 = f.d();
        if (h.a(d2)) {
            t = "LLUN";
        } else {
            if (d2.length() > 48) {
                i2 = d2.length() - 48;
                str = d2.substring(0, 48);
            } else {
                i2 = 0;
                str = d2;
            }
            StringBuilder sb = new StringBuilder();
            byte[] bytes = str.getBytes();
            for (int length = bytes.length - 1; length >= 0; length--) {
                byte b2 = bytes[length];
                if (b2 == 46) {
                    sb.append('0');
                } else if (b2 == 58) {
                    sb.append('1');
                } else if (b2 >= 97 && b2 <= 122) {
                    sb.append((char) ((b2 + 65) - 97));
                } else if (b2 >= 65 && b2 <= 90) {
                    sb.append((char) b2);
                } else if (b2 < 48 || b2 > 57) {
                    sb.append('2');
                } else {
                    sb.append((char) b2);
                }
            }
            if (i2 > 0) {
                sb.append(String.valueOf(i2));
            }
            t = sb.toString();
        }
        return t;
    }

    private static String a(String str) {
        return p.N() + a() + "." + str;
    }

    /* access modifiers changed from: private */
    public static String z() {
        if (f == null) {
            f = a((String) "ss");
        }
        return f;
    }

    public static String b() {
        if (g == null) {
            g = a((String) LogItem.MM_C43_K4_CAMERA_TIME);
        }
        return g;
    }

    public static String c() {
        if (h == null) {
            h = a((String) "st");
        }
        return h;
    }

    public static String d() {
        if (i == null) {
            i = a((String) "rt");
        }
        return i;
    }

    public static String e() {
        if (l == null) {
            l = a((String) "lgsuf");
        }
        return l;
    }

    public static String f() {
        if (j == null) {
            j = p.N() + "up";
        }
        return j;
    }

    public static String g() {
        if (k == null) {
            k = p.N() + "ver";
        }
        return k;
    }

    public static String h() {
        return p.N() + "bvu";
    }

    static String i() {
        return com.uc.crashsdk.a.b.a(new File(z()), 8, false);
    }

    private static void A() {
        if (!o && !n) {
            synchronized (s) {
                if (!o) {
                    b(p.N());
                    String i2 = i();
                    File file = new File(b());
                    p = "f".equals(i2);
                    q = SuperId.BIT_1_RQBXY.equals(i2);
                    r = file.exists();
                    try {
                        if (r) {
                            file.delete();
                        }
                        D();
                    } catch (Exception e2) {
                        com.uc.crashsdk.a.a.a(e2, false);
                    }
                    o = true;
                }
            }
        }
    }

    public static boolean j() {
        A();
        return p;
    }

    public static boolean k() {
        A();
        return q;
    }

    private static boolean B() {
        A();
        return r;
    }

    public static boolean l() {
        return m;
    }

    public static void a(boolean z2) {
        boolean z3;
        if (!m) {
            b(p.N());
            if (z2) {
                n = true;
                p = false;
                q = false;
                r = false;
                String[] strArr = {".st", ".wa", ".callback", ".threads", ".ct", ".signal", ".ff", ".bb"};
                String[] strArr2 = {"up"};
                File[] listFiles = new File(p.N()).listFiles();
                if (listFiles != null) {
                    for (File file : listFiles) {
                        String name = file.getName();
                        int i2 = 0;
                        while (true) {
                            if (i2 >= 8) {
                                z3 = false;
                                break;
                            } else if (name.endsWith(strArr[i2])) {
                                z3 = true;
                                break;
                            } else {
                                i2++;
                            }
                        }
                        if (!z3) {
                            int i3 = 0;
                            while (true) {
                                if (i3 > 0) {
                                    break;
                                } else if (name.equals(strArr2[0])) {
                                    z3 = true;
                                    break;
                                } else {
                                    i3++;
                                }
                            }
                        }
                        if (z3) {
                            c.b("delete file: " + file.getPath());
                            com.uc.crashsdk.a.b.a(file);
                        }
                    }
                }
            } else {
                m = true;
                C();
            }
            D();
        }
    }

    private static void C() {
        if (d) {
            JNIBridge.nativeSyncStatus("exit", null, m ? 1 : 0);
        }
    }

    private static void D() {
        Object[] F2 = F();
        if (F2[0].equals(y) || z != null) {
            e = true;
            E();
            return;
        }
        b(F2);
    }

    private static void E() {
        if (!i.b(A)) {
            i.a(1, A);
            return;
        }
        Object[] objArr = z;
        if (objArr == null || !F()[0].equals(objArr[0])) {
            i.a(A);
            i.a(1, A);
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void b(java.lang.Object[] r8) {
        /*
            r7 = 0
            z = r8
            java.lang.Object r2 = x
            monitor-enter(r2)
            r0 = 0
            r0 = r8[r0]     // Catch:{ all -> 0x00af }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ all -> 0x00af }
            r1 = 1
            r1 = r8[r1]     // Catch:{ all -> 0x00af }
            java.lang.Long r1 = (java.lang.Long) r1     // Catch:{ all -> 0x00af }
            long r3 = r1.longValue()     // Catch:{ all -> 0x00af }
            long r5 = B     // Catch:{ all -> 0x00af }
            int r1 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r1 >= 0) goto L_0x003a
            java.lang.String r0 = "crashsdk"
            java.lang.String r1 = "Update state generation %d, last is: %d"
            r5 = 2
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ all -> 0x00af }
            r6 = 0
            java.lang.Long r3 = java.lang.Long.valueOf(r3)     // Catch:{ all -> 0x00af }
            r5[r6] = r3     // Catch:{ all -> 0x00af }
            r3 = 1
            long r6 = B     // Catch:{ all -> 0x00af }
            java.lang.Long r4 = java.lang.Long.valueOf(r6)     // Catch:{ all -> 0x00af }
            r5[r3] = r4     // Catch:{ all -> 0x00af }
            java.lang.String r1 = java.lang.String.format(r1, r5)     // Catch:{ all -> 0x00af }
            com.uc.crashsdk.a.c.b(r0, r1)     // Catch:{ all -> 0x00af }
            monitor-exit(r2)     // Catch:{ all -> 0x00af }
        L_0x0039:
            return
        L_0x003a:
            B = r3     // Catch:{ all -> 0x00af }
            java.lang.String r1 = z()     // Catch:{ all -> 0x00af }
            boolean r3 = d     // Catch:{ all -> 0x00af }
            if (r3 == 0) goto L_0x0073
            java.io.RandomAccessFile r3 = v     // Catch:{ all -> 0x00af }
            if (r3 == 0) goto L_0x0050
            java.io.RandomAccessFile r3 = v     // Catch:{ all -> 0x00af }
            com.uc.crashsdk.a.b.a(r3)     // Catch:{ all -> 0x00af }
            r3 = 0
            v = r3     // Catch:{ all -> 0x00af }
        L_0x0050:
            boolean r3 = w     // Catch:{ all -> 0x00af }
            boolean r1 = com.uc.crashsdk.JNIBridge.nativeChangeState(r1, r0, r3)     // Catch:{ all -> 0x00af }
            r3 = 0
            w = r3     // Catch:{ all -> 0x00af }
            if (r1 != 0) goto L_0x006d
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x00af }
            java.lang.String r3 = "write state failed: "
            r1.<init>(r3)     // Catch:{ all -> 0x00af }
            java.lang.StringBuilder r1 = r1.append(r0)     // Catch:{ all -> 0x00af }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x00af }
            com.uc.crashsdk.a.c.c(r1)     // Catch:{ all -> 0x00af }
        L_0x006d:
            y = r0     // Catch:{ all -> 0x00af }
            monitor-exit(r2)     // Catch:{ all -> 0x00af }
            z = r7
            goto L_0x0039
        L_0x0073:
            java.io.RandomAccessFile r3 = v     // Catch:{ all -> 0x00af }
            if (r3 == 0) goto L_0x007b
            boolean r3 = w     // Catch:{ all -> 0x00af }
            if (r3 == 0) goto L_0x0098
        L_0x007b:
            java.io.RandomAccessFile r3 = v     // Catch:{ all -> 0x00af }
            if (r3 == 0) goto L_0x0087
            java.io.RandomAccessFile r3 = v     // Catch:{ all -> 0x00af }
            com.uc.crashsdk.a.b.a(r3)     // Catch:{ all -> 0x00af }
            r3 = 0
            v = r3     // Catch:{ all -> 0x00af }
        L_0x0087:
            java.io.RandomAccessFile r3 = new java.io.RandomAccessFile     // Catch:{ Exception -> 0x00b2 }
            java.lang.String r4 = "rw"
            r3.<init>(r1, r4)     // Catch:{ Exception -> 0x00b2 }
            v = r3     // Catch:{ Exception -> 0x00b2 }
            r4 = 0
            r3.seek(r4)     // Catch:{ Exception -> 0x00b2 }
            r1 = 0
            w = r1     // Catch:{ Exception -> 0x00b2 }
        L_0x0098:
            java.io.RandomAccessFile r1 = v     // Catch:{ Exception -> 0x00a9 }
            byte[] r3 = r0.getBytes()     // Catch:{ Exception -> 0x00a9 }
            r1.write(r3)     // Catch:{ Exception -> 0x00a9 }
            java.io.RandomAccessFile r1 = v     // Catch:{ Exception -> 0x00a9 }
            r3 = 0
            r1.seek(r3)     // Catch:{ Exception -> 0x00a9 }
            goto L_0x006d
        L_0x00a9:
            r1 = move-exception
            r3 = 0
            com.uc.crashsdk.a.a.a(r1, r3)     // Catch:{ all -> 0x00af }
            goto L_0x006d
        L_0x00af:
            r0 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x00af }
            throw r0
        L_0x00b2:
            r1 = move-exception
            r3 = 0
            com.uc.crashsdk.a.a.a(r1, r3)     // Catch:{ all -> 0x00af }
            goto L_0x0098
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.crashsdk.b.b(java.lang.Object[]):void");
    }

    /* access modifiers changed from: private */
    public static Object[] F() {
        Object[] objArr;
        synchronized (C) {
            D++;
            objArr = m ? new Object[]{"e", Long.valueOf(D)} : E ? new Object[]{"f", Long.valueOf(D)} : new Object[]{SuperId.BIT_1_RQBXY, Long.valueOf(D)};
        }
        return objArr;
    }

    public static boolean m() {
        return b(p.N());
    }

    public static boolean n() {
        return b(p.O());
    }

    private static boolean b(String str) {
        File file = new File(str);
        if (!file.exists()) {
            file.mkdirs();
        }
        if (file.isDirectory()) {
            return true;
        }
        c.a("crashsdk", "Crash log directory was placed by a file!!!");
        if (!file.delete()) {
            return false;
        }
        file.mkdirs();
        return true;
    }

    public static void b(boolean z2) {
        if (z2 && m) {
            if (p.G()) {
                Log.v("crashsdk", "setForeground, reset sExited to false!!!");
            }
            m = false;
            C();
        }
        if (!m) {
            A();
            E = z2;
            if (d) {
                JNIBridge.nativeSetForeground(z2);
            }
            D();
            if (z2) {
                a.a(false);
            }
            if (!w) {
                E();
            }
        }
    }

    public static boolean o() {
        return E;
    }

    public static void p() {
        F = true;
    }

    public static boolean q() {
        return F;
    }

    public static void r() {
        G = true;
    }

    public static boolean s() {
        return G;
    }

    public static boolean t() {
        return G || (!h.a(a.a) && a.a.equals(f.d()));
    }

    public static int u() {
        boolean B2 = B();
        if (k()) {
            if (B2) {
                return 3;
            }
            return 6;
        } else if (j()) {
            if (B2) {
                return 2;
            }
            return 5;
        } else if (B2) {
            return 4;
        } else {
            return 1;
        }
    }

    public static void a(int i2) {
        u = i2;
        v();
    }

    public static void v() {
        if (d) {
            JNIBridge.nativeSyncStatus("logType", "12", u);
        }
    }

    public static boolean b(int i2) {
        return (u & i2) != 0;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:63:0x0085, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:?, code lost:
        com.uc.crashsdk.a.a.a(r3, false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x00a1, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x00a2, code lost:
        r5 = r1;
        r1 = r0;
        r0 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:0x00ab, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:90:0x00ac, code lost:
        com.uc.crashsdk.a.a.a(r1, false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:93:0x00b7, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:?, code lost:
        com.uc.crashsdk.a.a.a(r1, false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:97:0x00bd, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:98:0x00be, code lost:
        com.uc.crashsdk.a.a.a(r1, false);
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x00a1 A[ExcHandler: all (r1v5 'th' java.lang.Throwable A[CUSTOM_DECLARE]), Splitter:B:43:0x0064] */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x00a7 A[SYNTHETIC, Splitter:B:85:0x00a7] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:19:0x0033=Splitter:B:19:0x0033, B:87:0x00aa=Splitter:B:87:0x00aa} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean a(java.lang.Object r6, java.lang.String r7, com.uc.crashsdk.b.a r8) {
        /*
            r1 = 0
            r2 = 0
            monitor-enter(r6)
            boolean r0 = d     // Catch:{ all -> 0x0039 }
            if (r0 == 0) goto L_0x0049
            int r1 = com.uc.crashsdk.JNIBridge.nativeOpenFile(r7)     // Catch:{ all -> 0x0039 }
            if (r1 >= 0) goto L_0x0024
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x0039 }
            java.lang.String r1 = "Can not open file: "
            r0.<init>(r1)     // Catch:{ all -> 0x0039 }
            java.lang.StringBuilder r0 = r0.append(r7)     // Catch:{ all -> 0x0039 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0039 }
            java.lang.String r1 = "crashsdk"
            com.uc.crashsdk.a.c.a(r1, r0)     // Catch:{ all -> 0x0039 }
            monitor-exit(r6)     // Catch:{ all -> 0x0039 }
            r0 = r2
        L_0x0023:
            return r0
        L_0x0024:
            r0 = 1
            boolean r0 = com.uc.crashsdk.JNIBridge.nativeLockFile(r1, r0)     // Catch:{ all -> 0x0044 }
            boolean r2 = r8.a()     // Catch:{ all -> 0x003c }
            if (r0 == 0) goto L_0x0033
            r0 = 0
            com.uc.crashsdk.JNIBridge.nativeLockFile(r1, r0)     // Catch:{ all -> 0x0044 }
        L_0x0033:
            com.uc.crashsdk.JNIBridge.nativeCloseFile(r1)     // Catch:{ all -> 0x0039 }
            r0 = r2
        L_0x0037:
            monitor-exit(r6)     // Catch:{ all -> 0x0039 }
            goto L_0x0023
        L_0x0039:
            r0 = move-exception
            monitor-exit(r6)     // Catch:{ all -> 0x0039 }
            throw r0
        L_0x003c:
            r2 = move-exception
            if (r0 == 0) goto L_0x0043
            r0 = 0
            com.uc.crashsdk.JNIBridge.nativeLockFile(r1, r0)     // Catch:{ all -> 0x0044 }
        L_0x0043:
            throw r2     // Catch:{ all -> 0x0044 }
        L_0x0044:
            r0 = move-exception
            com.uc.crashsdk.JNIBridge.nativeCloseFile(r1)     // Catch:{ all -> 0x0039 }
            throw r0     // Catch:{ all -> 0x0039 }
        L_0x0049:
            java.io.File r0 = new java.io.File     // Catch:{ all -> 0x0039 }
            r0.<init>(r7)     // Catch:{ all -> 0x0039 }
            boolean r3 = r0.exists()     // Catch:{ all -> 0x0039 }
            if (r3 != 0) goto L_0x0057
            r0.createNewFile()     // Catch:{ Exception -> 0x0078 }
        L_0x0057:
            java.io.RandomAccessFile r3 = new java.io.RandomAccessFile     // Catch:{ Exception -> 0x007e }
            java.lang.String r4 = "rw"
            r3.<init>(r0, r4)     // Catch:{ Exception -> 0x007e }
            java.nio.channels.FileChannel r0 = r3.getChannel()     // Catch:{ Exception -> 0x007e }
        L_0x0062:
            if (r0 == 0) goto L_0x0068
            java.nio.channels.FileLock r1 = r0.lock()     // Catch:{ Exception -> 0x0085, all -> 0x00a1 }
        L_0x0068:
            boolean r2 = r8.a()     // Catch:{ all -> 0x009a }
            if (r1 == 0) goto L_0x0071
            r1.release()     // Catch:{ Exception -> 0x00bd, all -> 0x00a1 }
        L_0x0071:
            if (r0 == 0) goto L_0x0076
            r0.close()     // Catch:{ Exception -> 0x00c3 }
        L_0x0076:
            r0 = r2
            goto L_0x0037
        L_0x0078:
            r3 = move-exception
            r4 = 0
            com.uc.crashsdk.a.a.a(r3, r4)     // Catch:{ all -> 0x0039 }
            goto L_0x0057
        L_0x007e:
            r0 = move-exception
            r3 = 0
            com.uc.crashsdk.a.a.a(r0, r3)     // Catch:{ Exception -> 0x00cb }
            r0 = r1
            goto L_0x0062
        L_0x0085:
            r3 = move-exception
            r4 = 0
            com.uc.crashsdk.a.a.a(r3, r4)     // Catch:{ Exception -> 0x008b, all -> 0x00a1 }
            goto L_0x0068
        L_0x008b:
            r1 = move-exception
            r5 = r1
            r1 = r0
            r0 = r5
        L_0x008f:
            r3 = 0
            com.uc.crashsdk.a.a.a(r0, r3)     // Catch:{ all -> 0x00c9 }
            if (r1 == 0) goto L_0x0098
            r1.close()     // Catch:{ Exception -> 0x00b1 }
        L_0x0098:
            r0 = r2
            goto L_0x0037
        L_0x009a:
            r3 = move-exception
            if (r1 == 0) goto L_0x00a0
            r1.release()     // Catch:{ Exception -> 0x00b7, all -> 0x00a1 }
        L_0x00a0:
            throw r3     // Catch:{ Exception -> 0x008b, all -> 0x00a1 }
        L_0x00a1:
            r1 = move-exception
            r5 = r1
            r1 = r0
            r0 = r5
        L_0x00a5:
            if (r1 == 0) goto L_0x00aa
            r1.close()     // Catch:{ Exception -> 0x00ab }
        L_0x00aa:
            throw r0     // Catch:{ all -> 0x0039 }
        L_0x00ab:
            r1 = move-exception
            r2 = 0
            com.uc.crashsdk.a.a.a(r1, r2)     // Catch:{ all -> 0x0039 }
            goto L_0x00aa
        L_0x00b1:
            r0 = move-exception
            r1 = 0
            com.uc.crashsdk.a.a.a(r0, r1)     // Catch:{ all -> 0x0039 }
            goto L_0x0098
        L_0x00b7:
            r1 = move-exception
            r4 = 0
            com.uc.crashsdk.a.a.a(r1, r4)     // Catch:{ Exception -> 0x008b, all -> 0x00a1 }
            goto L_0x00a0
        L_0x00bd:
            r1 = move-exception
            r3 = 0
            com.uc.crashsdk.a.a.a(r1, r3)     // Catch:{ Exception -> 0x008b, all -> 0x00a1 }
            goto L_0x0071
        L_0x00c3:
            r0 = move-exception
            r1 = 0
            com.uc.crashsdk.a.a.a(r0, r1)     // Catch:{ all -> 0x0039 }
            goto L_0x0076
        L_0x00c9:
            r0 = move-exception
            goto L_0x00a5
        L_0x00cb:
            r0 = move-exception
            goto L_0x008f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.crashsdk.b.a(java.lang.Object, java.lang.String, com.uc.crashsdk.b$a):boolean");
    }
}
