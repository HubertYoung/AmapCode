package com.uc.crashsdk.a;

import android.os.Build;
import android.os.Build.VERSION;
import android.os.Process;
import android.util.Log;
import android.util.SparseArray;
import com.alipay.android.phone.inside.sdk.util.GlobalConstants;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.alipay.mobile.security.bio.workspace.Env;
import com.taobao.accs.common.Constants;
import com.uc.crashsdk.f;
import com.uc.crashsdk.p;
import java.io.File;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

/* compiled from: ProGuard */
public class k {
    /* access modifiers changed from: private */
    public static final Object a = new Object();
    private static Map<String, String> b = new HashMap();
    private static int c = 0;
    private static Map<String, b> d = new HashMap();
    private static Object e = new Object();
    private static SparseArray<String> f = new SparseArray<>();
    private static boolean g = false;
    private static boolean h = false;
    private static final char[] i = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /* compiled from: ProGuard */
    final class a implements Runnable {
        static final /* synthetic */ boolean a = (!k.class.desiredAssertionStatus());
        private int b = 0;

        a(int i) {
            this.b = i;
        }

        public final void run() {
            switch (this.b) {
                case 1:
                    k.f();
                    return;
                case 2:
                    synchronized (k.a) {
                        k.h();
                    }
                    return;
                case 3:
                    k.i();
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
    final class b {
        long a = 0;
        int b = 0;
        Map<String, String> c = new HashMap();
        private String d;
        private boolean e = false;
        private boolean f = false;

        b(String str, boolean z, boolean z2) {
            this.d = str;
            this.e = z;
            this.f = z2;
        }

        /* access modifiers changed from: 0000 */
        public final void a(String str, String str2) {
            this.c.put(str, str2);
        }

        /* access modifiers changed from: 0000 */
        public final void a(String str, long j) {
            a(str, String.valueOf(c(str) + j));
        }

        /* access modifiers changed from: 0000 */
        public final boolean a(b bVar) {
            if (!this.f) {
                c.a("crashsdk", String.format("WaItem '%s' is not mergable!", new Object[]{this.d}));
                return false;
            }
            for (String next : bVar.c.keySet()) {
                if (next.startsWith("c_")) {
                    a(next, bVar.a(next));
                } else {
                    long c2 = bVar.c(next);
                    if (c2 != 0) {
                        a(next, c2);
                    } else {
                        a(next, bVar.a(next));
                    }
                }
            }
            return true;
        }

        /* access modifiers changed from: 0000 */
        public final String a(String str) {
            return this.c.get(str);
        }

        private long c(String str) {
            return h.c(a(str));
        }

        /* access modifiers changed from: 0000 */
        public final String a(boolean z, boolean z2, boolean z3) {
            if (this.d == null) {
                return null;
            }
            StringBuilder sb = new StringBuilder();
            if (z) {
                String str = "-";
                try {
                    str = Build.HARDWARE;
                } catch (Throwable th) {
                    a.a(th, false);
                }
                k.b(sb, "lt", "uc");
                k.b(sb, Env.NAME_PRE, p.e());
                k.b(sb, "pkg", com.uc.crashsdk.a.a);
                k.b(sb, "rom", VERSION.RELEASE);
                k.b(sb, Constants.KEY_MODEL, Build.MODEL);
                k.b(sb, GlobalConstants.EXCEPTIONTYPE, String.valueOf((long) VERSION.SDK_INT));
                k.b(sb, "cpu", Build.CPU_ABI);
                k.b(sb, "hdw", str);
                k.b(sb, "ram", String.valueOf((long) k.k()));
                k.b(sb, "cver", "2.0.0.4");
                k.b(sb, "cseq", "170706161743");
                k.b(sb, "aver", com.uc.crashsdk.a.a());
                k.b(sb, "ver", p.K());
                k.b(sb, "sver", p.L());
                k.b(sb, "seq", p.M());
                k.b(sb, "os", "android");
                sb.append("\n");
            }
            k.b(sb, "lt", this.d);
            k.a(sb, this.c);
            if (this.e && !z2) {
                if (this.a != 0) {
                    k.b(sb, "up", String.valueOf(this.a));
                }
                if (z3) {
                    k.b(sb, "pid", String.format(Locale.US, "%d", new Object[]{Integer.valueOf(Process.myPid())}));
                } else if (this.b != 0) {
                    k.b(sb, "pid", String.format(Locale.US, "%d", new Object[]{Integer.valueOf(this.b)}));
                }
            }
            sb.append("\n");
            return sb.toString();
        }

        /* access modifiers changed from: 0000 */
        public final boolean b(String str) {
            if (h.a(str)) {
                return false;
            }
            HashMap hashMap = new HashMap();
            Map<String, String> a2 = k.a(str);
            long j = 0;
            String str2 = null;
            int i = 0;
            for (String next : a2.keySet()) {
                String str3 = a2.get(next);
                if (next.equals("lt")) {
                    str2 = str3;
                } else if (this.e && next.equals("up")) {
                    j = h.c(str3);
                } else if (!this.e || !next.equals("pid")) {
                    hashMap.put(next, str3);
                } else {
                    i = (int) h.c(str3);
                }
            }
            if (this.d != null && !this.d.equals(str2)) {
                return false;
            }
            this.a = j;
            this.b = i;
            this.d = str2;
            this.c = hashMap;
            return true;
        }
    }

    static /* synthetic */ boolean c(String str) {
        if (h) {
            return false;
        }
        h = true;
        File file = new File(str);
        ArrayList<b> a2 = a(file, (String) "crp", 100);
        b bVar = new b("crp", false, false);
        bVar.a((String) "et", String.valueOf(com.uc.crashsdk.b.u()));
        bVar.a((String) "prc", f.d());
        bVar.a((String) "imp", com.uc.crashsdk.b.t() ? "1" : "0");
        a(bVar);
        a2.add(0, bVar);
        boolean b2 = b(f.j(), a((Iterable<b>) a2, true, false).toString());
        b.a(file);
        if (!b2) {
            b.a(file, a((Iterable<b>) a2, false, true).toString());
        }
        return true;
    }

    static /* synthetic */ boolean d(String str) {
        File file = new File(str);
        Iterator<b> it = a(file, (String) LogItem.MM_C43_K4_CAMERA_SURFACE_READY_TIME, 30).iterator();
        while (it.hasNext()) {
            b next = it.next();
            String a2 = next.a((String) "prc");
            if (!h.a(a2)) {
                b bVar = d.get(a2);
                if (bVar != null) {
                    bVar.a(next);
                } else {
                    d.put(a2, next);
                }
            }
        }
        boolean b2 = b(f.j(), a((Iterable<b>) d.values(), true, false).toString());
        b.a(file);
        if (!b2) {
            b.a(file, a((Iterable<b>) d.values(), false, true).toString());
        }
        return true;
    }

    static /* synthetic */ void f() {
        String str = p.N() + "cr.wa";
        com.uc.crashsdk.b.a(a, str, new l(str, str));
    }

    static /* synthetic */ boolean h() {
        if (g) {
            return false;
        }
        g = true;
        if (com.uc.crashsdk.a.b.equals("2.0") && com.uc.crashsdk.b.b(268435456)) {
            return false;
        }
        File file = new File(j());
        String b2 = b.b(file);
        b bVar = new b("pv", true, true);
        if (!h.a(b2)) {
            bVar.b(b2);
        }
        if (bVar.b == Process.myPid()) {
            return false;
        }
        bVar.a((String) "pv", 1);
        bVar.a((String) "fjv", 1);
        String j = f.j();
        long j2 = bVar.a;
        if ((j2 == 0 || System.currentTimeMillis() - j2 >= 28800000) ? b(j, bVar.a(true, true, false)) : false) {
            bVar.c = new HashMap();
            bVar.a = System.currentTimeMillis();
            bVar.b = Process.myPid();
        }
        b.a(file, bVar.a(false, false, true));
        return true;
    }

    static /* synthetic */ void i() {
        String str = p.N() + "dt.wa";
        com.uc.crashsdk.b.a(e, str, new m(str, str));
    }

    public static Map<String, String> a(String str) {
        String[] split;
        HashMap hashMap = new HashMap();
        for (String str2 : str.split("`")) {
            if (str2.length() > 1) {
                String[] split2 = str2.split("=");
                if (split2.length == 2) {
                    hashMap.put(split2[0], split2[1]);
                }
            }
        }
        return hashMap;
    }

    public static void a(StringBuilder sb, Map<String, String> map) {
        for (String next : map.keySet()) {
            b(sb, next, map.get(next));
        }
    }

    /* access modifiers changed from: private */
    public static void b(StringBuilder sb, String str, String str2) {
        sb.append(str).append("=").append(str2).append("`");
    }

    public static void b(String str) {
        synchronized (a) {
            File file = new File(j());
            b bVar = new b("pv", true, true);
            String b2 = b.b(file);
            if (!h.a(b2)) {
                bVar.b(b2);
            }
            bVar.a(str, 1);
            bVar.a((String) "aujv", 1);
            b.a(file, bVar.a(false, false, false));
        }
    }

    public static void a() {
        if (com.uc.crashsdk.b.t()) {
            i.a(0, new a(2), 36000);
        }
    }

    public static boolean a(String str, String str2) {
        String replaceAll;
        try {
            String str3 = "c_" + str.replaceAll("[^0-9a-zA-Z-_]", "-");
            if (h.a(str2)) {
                replaceAll = "";
            } else {
                replaceAll = str2.replaceAll("[`=]", "-");
            }
            synchronized (b) {
                try {
                    if (b.get(str3) == null) {
                        if (c >= 20) {
                            return false;
                        }
                        c++;
                    }
                    b.put(str3, replaceAll);
                    return true;
                }
            }
        } catch (Throwable th) {
            a.a(th, false);
            return false;
        }
    }

    private static void a(b bVar) {
        synchronized (b) {
            for (String next : b.keySet()) {
                bVar.a(next, b.get(next));
            }
        }
    }

    public static void b() {
        if (p.I()) {
            i.a(1, new a(1), 2000);
        }
    }

    private static StringBuilder a(Iterable<b> iterable, boolean z, boolean z2) {
        StringBuilder sb = new StringBuilder();
        boolean z3 = true;
        for (b next : iterable) {
            if (z3) {
                sb.append(next.a(z, z, z2));
                z3 = false;
            } else {
                sb.append(next.a(false, z, z2));
            }
        }
        return sb;
    }

    public static void a(String str, int i2, int i3) {
        b bVar;
        if (p.I()) {
            synchronized (e) {
                b bVar2 = d.get(str);
                if (bVar2 == null) {
                    b bVar3 = new b(LogItem.MM_C43_K4_CAMERA_SURFACE_READY_TIME, false, true);
                    d.put(str, bVar3);
                    a(bVar3);
                    bVar = bVar3;
                } else {
                    bVar = bVar2;
                }
                synchronized (f) {
                    if (f.size() == 0) {
                        a(100, (String) "pv");
                        a(1, (String) "all");
                        a(2, (String) "afg");
                        a(3, (String) "jfg");
                        a(4, (String) "jbg");
                        a(7, (String) "nfg");
                        a(8, (String) "nbg");
                        a(27, (String) "nafg");
                        a(28, (String) "nabg");
                        a(9, (String) "nho");
                        a(10, (String) "uar");
                        a(29, (String) "ulm");
                        a(30, (String) "ukt");
                        a(31, (String) "uet");
                        a(11, (String) "ufg");
                        a(12, (String) "ubg");
                        a(13, (String) "lup");
                        a(14, (String) "luf");
                        a(15, (String) "lef");
                        a(16, (String) "laf");
                        a(22, (String) "lac");
                        a(23, (String) "lau");
                        a(17, (String) "llf");
                        a(18, (String) "lul");
                        a(19, (String) "lub");
                        a(20, (String) "luc");
                        a(21, (String) "luu");
                        a(24, (String) "lzc");
                        a(25, (String) "lrc");
                        a(26, (String) "lss");
                    }
                }
                String str2 = f.get(i2);
                if (str2 == null) {
                    c.a("crashsdk", "map key is not set with: " + i2);
                }
                bVar.a((String) "prc", str);
                if (str2 != null) {
                    bVar.a(str2, String.valueOf(i3));
                }
            }
        }
    }

    public static void c() {
        if (p.I()) {
            i.a(1, new a(3));
        }
    }

    private static void a(int i2, String str) {
        f.put(i2, str);
    }

    private static ArrayList<b> a(File file, String str, int i2) {
        ArrayList<String> a2 = b.a(file, i2);
        ArrayList<b> arrayList = new ArrayList<>();
        Iterator<String> it = a2.iterator();
        while (it.hasNext()) {
            b bVar = new b(str, false, false);
            if (bVar.b(it.next())) {
                arrayList.add(bVar);
            }
        }
        return arrayList;
    }

    private static String j() {
        return p.N() + "pv.wa";
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0077  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x00ad  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x00bc  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x00c6  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x00cc  */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0043  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean b(java.lang.String r10, java.lang.String r11) {
        /*
            r1 = 1
            r2 = 0
            boolean r0 = com.uc.crashsdk.a.h.a(r11)
            if (r0 == 0) goto L_0x0009
        L_0x0008:
            return r1
        L_0x0009:
            byte[] r3 = r11.getBytes()
            r0 = 16
            byte[] r0 = new byte[r0]     // Catch:{ Throwable -> 0x00bf }
            r4 = 0
            byte[] r5 = com.uc.crashsdk.a.e.c()     // Catch:{ Throwable -> 0x00bf }
            com.uc.crashsdk.a.e.a(r0, r4, r5)     // Catch:{ Throwable -> 0x00bf }
            r4 = 4
            byte[] r5 = d()     // Catch:{ Throwable -> 0x00bf }
            com.uc.crashsdk.a.e.a(r0, r4, r5)     // Catch:{ Throwable -> 0x00bf }
            r4 = 8
            byte[] r5 = com.uc.crashsdk.a.c()     // Catch:{ Throwable -> 0x00bf }
            com.uc.crashsdk.a.e.a(r0, r4, r5)     // Catch:{ Throwable -> 0x00bf }
            r4 = 12
            byte[] r5 = com.uc.crashsdk.a.f.c()     // Catch:{ Throwable -> 0x00bf }
            com.uc.crashsdk.a.e.a(r0, r4, r5)     // Catch:{ Throwable -> 0x00bf }
            byte[] r0 = com.uc.crashsdk.a.e.a(r3, r0)     // Catch:{ Throwable -> 0x00bf }
            if (r0 == 0) goto L_0x00c3
            r3 = r0
            r0 = r1
        L_0x003b:
            java.lang.String r4 = "28ef1713347d"
            boolean r5 = com.uc.crashsdk.p.J()
            if (r5 == 0) goto L_0x0045
            java.lang.String r4 = "4ea4e41a3993"
        L_0x0045:
            long r5 = java.lang.System.currentTimeMillis()
            java.lang.String r5 = java.lang.String.valueOf(r5)
            java.lang.String r6 = "AppChk#2014"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.StringBuilder r7 = r7.append(r4)
            java.lang.StringBuilder r7 = r7.append(r10)
            java.lang.StringBuilder r7 = r7.append(r5)
            java.lang.StringBuilder r6 = r7.append(r6)
            java.lang.String r6 = r6.toString()
            java.lang.String r6 = e(r6)
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            boolean r8 = com.uc.crashsdk.p.J()
            if (r8 == 0) goto L_0x00c6
            java.lang.String r8 = "https://gjapplog.uc.cn/collect?chk="
            r7.append(r8)
        L_0x007c:
            int r8 = r6.length()
            int r8 = r8 + -8
            int r9 = r6.length()
            java.lang.String r6 = r6.substring(r8, r9)
            java.lang.StringBuilder r6 = r7.append(r6)
            java.lang.String r8 = "&vno="
            java.lang.StringBuilder r6 = r6.append(r8)
            java.lang.StringBuilder r5 = r6.append(r5)
            java.lang.String r6 = "&uuid="
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r10)
            java.lang.String r6 = "&app="
            java.lang.StringBuilder r5 = r5.append(r6)
            r5.append(r4)
            if (r0 == 0) goto L_0x00b2
            java.lang.String r0 = "&enc=aes"
            r7.append(r0)
        L_0x00b2:
            java.lang.String r0 = r7.toString()
            byte[] r0 = com.uc.crashsdk.a.e.a(r0, r3)
            if (r0 != 0) goto L_0x00cc
            r1 = r2
            goto L_0x0008
        L_0x00bf:
            r0 = move-exception
            com.uc.crashsdk.a.a.a(r0, r2)
        L_0x00c3:
            r0 = r2
            goto L_0x003b
        L_0x00c6:
            java.lang.String r8 = "https://applog.uc.cn/collect?chk="
            r7.append(r8)
            goto L_0x007c
        L_0x00cc:
            java.lang.String r3 = new java.lang.String
            r3.<init>(r0)
            java.lang.String r0 = "retcode=0"
            boolean r0 = r3.contains(r0)
            if (r0 != 0) goto L_0x0008
            r1 = r2
            goto L_0x0008
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.crashsdk.a.k.b(java.lang.String, java.lang.String):boolean");
    }

    /* access modifiers changed from: private */
    public static int k() {
        Iterator<String> it = b.a(new File("/proc/meminfo"), 2).iterator();
        while (it.hasNext()) {
            String next = it.next();
            if (next.indexOf("MemTotal:") >= 0) {
                try {
                    return Integer.parseInt(next.replaceAll("\\D+", ""));
                } catch (NumberFormatException e2) {
                    a.a(e2, false);
                    return 0;
                }
            }
        }
        return 0;
    }

    public static byte[] d() {
        return new byte[]{Byte.MAX_VALUE, 100, 110, 31};
    }

    private static String e(String str) {
        try {
            byte[] bytes = str.getBytes("utf-8");
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(bytes);
            byte[] digest = instance.digest();
            int length = digest.length;
            StringBuilder sb = new StringBuilder(length * 2);
            int i2 = length + 0;
            for (int i3 = 0; i3 < i2; i3++) {
                byte b2 = digest[i3];
                char c2 = i[(b2 & 240) >> 4];
                char c3 = i[b2 & 15];
                sb.append(c2);
                sb.append(c3);
            }
            return sb.toString();
        } catch (Exception e2) {
            if (!p.G()) {
                return null;
            }
            Log.d("crashsdk", "getMD5: ", e2);
            return null;
        }
    }
}
