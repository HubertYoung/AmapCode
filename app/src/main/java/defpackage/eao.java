package defpackage;

import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.autonavi.core.network.inter.response.ResponseException;
import com.autonavi.core.network.inter.response.StringResponse;
import com.autonavi.gdtaojin.camera.CameraControllerManager;
import com.autonavi.jni.eyrie.amap.tbt.NaviManager;
import com.autonavi.jni.eyrie.amap.tracker.TrackType;
import com.autonavi.jni.route.health.IHealthBike;
import com.autonavi.jni.route.health.IHealthBikeSharing;
import com.autonavi.jni.route.health.IHealthRun;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: eao reason: default package */
/* compiled from: ARouteLog */
public final class eao {
    /* access modifiers changed from: private */
    public static final boolean a = bno.a;
    /* access modifiers changed from: private */
    public static String b = "[%4d-%02d-%02d %02d:%02d:%02d]";
    private static int c = 1000;
    private static final Object d = new Object();
    /* access modifiers changed from: private */
    public static final String e;
    private static final SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
    private static final int g = 23;
    private static long h = 0;
    private static String i = "";
    private static final String j;
    private static final Object k = new Object();

    /* renamed from: eao$a */
    /* compiled from: ARouteLog */
    public static class a {
        private static String a = null;
        private static final String b;
        private static final String c;
        private static final Object d = new Object();
        /* access modifiers changed from: private */
        public static boolean e = false;
        private static boolean f = false;
        /* access modifiers changed from: private */
        public static Handler g = new C0088a();
        private static Timer h = null;
        private static Object i = new Object();
        private static TimerTask j = null;
        private static long k = 0;
        /* access modifiers changed from: private */
        public static int l = 6;
        /* access modifiers changed from: private */
        public static TrackType m;
        /* access modifiers changed from: private */
        public static IHealthRun n;
        /* access modifiers changed from: private */
        public static IHealthBike o;
        /* access modifiers changed from: private */
        public static IHealthBikeSharing p;
        private static String q = c;
        private static String r;
        private static String s;
        private static String t;
        private static String u;
        private static String v;
        private static String w;
        private static String x;

        /* renamed from: eao$a$a reason: collision with other inner class name */
        /* compiled from: ARouteLog */
        static class C0088a extends Handler {
            public C0088a() {
                super(Looper.getMainLooper());
            }

            public final void handleMessage(Message message) {
                Message message2 = message;
                if (a.e && message2.what == 0) {
                    try {
                        JSONObject jSONObject = new JSONObject((String) message2.obj);
                        JSONObject jSONObject2 = jSONObject.getJSONObject("coords");
                        long j = jSONObject.getLong("timestamp");
                        double d = jSONObject2.getDouble("longitude");
                        double d2 = jSONObject2.getDouble("latitude");
                        int i = jSONObject2.getInt(CameraControllerManager.MY_POILOCATION_ACR);
                        double d3 = jSONObject2.getDouble("altitude");
                        double d4 = jSONObject2.getDouble("heading");
                        double d5 = jSONObject2.getDouble("speed") * 3.6d;
                        Calendar instance = Calendar.getInstance();
                        instance.setTimeInMillis(1000 * j);
                        int i2 = instance.get(1);
                        int i3 = instance.get(2) + 1;
                        int i4 = instance.get(5);
                        int i5 = instance.get(10);
                        int i6 = instance.get(12);
                        int i7 = instance.get(13);
                        if (!(a.m == TrackType.RIDE || a.m == TrackType.WALK)) {
                            if (a.m != TrackType.BUS) {
                                if (a.n != null) {
                                    a.a("sim_setGpsinfo", d, d2, Double.valueOf(d5));
                                    double d6 = d;
                                    double d7 = d2;
                                    double d8 = d5;
                                    a.n.SetGPSInfo((double) i, d6, d7, d8, (int) d4, j);
                                    return;
                                }
                                long j2 = j;
                                double d9 = d4;
                                if (a.o != null) {
                                    a.a("sim_setGpsinfo", d, d2, Double.valueOf(d5));
                                    a.o.SetGPSInfo((double) i, d, d2, d5, (int) d9, j2);
                                    return;
                                }
                                if (a.p != null) {
                                    a.a("sim_setGpsinfo", d, d2, Double.valueOf(d5));
                                    a.p.SetGPSInfo((double) i, d, d2, d5, (int) d9, j2);
                                }
                                return;
                            }
                        }
                        int i8 = i2;
                        double d10 = d4;
                        if (d10 < 0.0d) {
                            d10 += 360.0d;
                        }
                        Object[] objArr = {Double.valueOf(d5)};
                        double d11 = d5;
                        double d12 = d10;
                        int i9 = i5;
                        int i10 = i6;
                        int i11 = i7;
                        Object[] objArr2 = objArr;
                        int i12 = i4;
                        double d13 = d2;
                        double d14 = d2;
                        int i13 = i3;
                        a.a("sim_setGpsinfo", d, d13, objArr2);
                        StringBuilder sb = new StringBuilder("(");
                        sb.append(i8);
                        sb.append("-");
                        sb.append(i13);
                        sb.append("-");
                        sb.append(i12);
                        sb.append(Token.SEPARATOR);
                        sb.append(i9);
                        sb.append(":");
                        sb.append(i10);
                        sb.append(":");
                        sb.append(i11);
                        sb.append(") gps:");
                        sb.append(a.l);
                        sb.append(", accuracy:");
                        sb.append(i);
                        sb.append(", (");
                        sb.append(d);
                        sb.append(", ");
                        double d15 = d14;
                        sb.append(d15);
                        sb.append("), speed:");
                        double d16 = d11;
                        sb.append(d16);
                        sb.append(", heading:");
                        double d17 = d12;
                        sb.append(d17);
                        eao.a((String) "emulation", sb.toString());
                        NaviManager.setGpsInfo(i, d3, d17, d16, d, d15, a.l, i8, i13, i12, i9, i10, i11);
                    } catch (JSONException unused) {
                    }
                }
            }
        }

        static {
            StringBuilder sb = new StringBuilder();
            sb.append(eao.e);
            sb.append("emulatorlogs/");
            b = sb.toString();
            StringBuilder sb2 = new StringBuilder();
            sb2.append(b);
            sb2.append("noengine.log");
            c = sb2.toString();
            if (eao.a) {
                n();
                m();
            }
        }

        public static boolean a() {
            if (!eao.a) {
                return false;
            }
            boolean o2 = o();
            f = o2;
            return o2;
        }

        public static void a(IHealthRun iHealthRun) {
            if (eao.a) {
                m();
                b(TrackType.HEALTH_RUN);
                if (iHealthRun != null && e) {
                    m = TrackType.HEALTH_RUN;
                    n = iHealthRun;
                    l();
                }
            }
        }

        public static void a(IHealthBike iHealthBike) {
            if (eao.a) {
                m();
                b(TrackType.HEALTH_RIDE);
                if (iHealthBike != null && e) {
                    m = TrackType.HEALTH_RIDE;
                    o = iHealthBike;
                    l();
                }
            }
        }

        private static void l() {
            synchronized (i) {
                if (h == null) {
                    a((String) "startEmulation");
                    h = new Timer("EmulatorTools.Timer");
                    j = new TimerTask() {
                        public final void run() {
                            a.j();
                        }
                    };
                    h.schedule(j, 0, 1000);
                }
            }
        }

        public static void a(TrackType trackType) {
            if (eao.a) {
                m();
                b(trackType);
                if (e) {
                    m = trackType;
                    k = System.currentTimeMillis();
                    l();
                }
            }
        }

        public static void b() {
            a((String) "stopEmulation");
            if (eao.a) {
                m();
            }
        }

        private static void m() {
            o();
            a = null;
            if (g != null) {
                g.removeMessages(0);
            }
            synchronized (i) {
                if (h != null) {
                    h.cancel();
                    if (j != null) {
                        j.cancel();
                    }
                    h = null;
                }
            }
            j = null;
            n = null;
            o = null;
            b(null);
        }

        public static void b(TrackType trackType) {
            if (eao.a) {
                if (trackType == null) {
                    q = c;
                    return;
                }
                switch (trackType) {
                    case BUS:
                        q = r;
                        return;
                    case WALK:
                        q = s;
                        return;
                    case HEALTH_RIDE:
                        q = t;
                        return;
                    case HEALTH_RUN:
                        q = u;
                        return;
                    case RIDE:
                        q = v;
                        return;
                    case SHAREBIKE:
                        q = w;
                        return;
                    case ELEBIKE:
                        q = x;
                        return;
                    default:
                        q = c;
                        return;
                }
            }
        }

        public static void a(String str, double d2, double d3) {
            if (eao.a && f && !TextUtils.isEmpty(q)) {
                a(null, String.format("[GPS] [%.5f,%.5f,] [%s]", new Object[]{Double.valueOf(d2), Double.valueOf(d3), str}));
            }
        }

        public static void a(String str, double d2, double d3, Object... objArr) {
            if (!eao.a || !f || TextUtils.isEmpty(q)) {
                return;
            }
            if (objArr.length == 0) {
                a(str, d2, d3);
                return;
            }
            StringBuilder sb = new StringBuilder(String.format("[GPS] [%.5f,%.5f,] [%s]", new Object[]{Double.valueOf(d2), Double.valueOf(d3), str}));
            sb.append(", [");
            for (Object valueOf : objArr) {
                sb.append(String.valueOf(valueOf));
                sb.append(",");
            }
            sb.append("]");
            a(null, sb);
        }

        public static void a(String str, int i2, float f2, double d2, double d3, double d4, double d5, int i3, int i4, int i5, int i6, int i7, int i8) {
            if (eao.a && f && !TextUtils.isEmpty(q)) {
                a(str, d2, d3, Integer.valueOf(i2), Float.valueOf(f2), Double.valueOf(d4), Double.valueOf(d5), String.format(eao.b, new Object[]{Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5), Integer.valueOf(i6), Integer.valueOf(i7), Integer.valueOf(i8)}));
            }
        }

        /* JADX WARNING: Can't wrap try/catch for region: R(4:9|(14:10|11|(1:15)|16|(1:18)|19|20|21|(1:23)|24|(3:26|(3:28|(2:30|59)(2:31|58)|32)|57)|33|34|35)|51|52) */
        /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
        /* JADX WARNING: Missing exception handler attribute for start block: B:44:0x008c */
        /* JADX WARNING: Missing exception handler attribute for start block: B:51:0x0095 */
        /* JADX WARNING: Removed duplicated region for block: B:42:0x0089 A[SYNTHETIC, Splitter:B:42:0x0089] */
        /* JADX WARNING: Removed duplicated region for block: B:48:0x008f A[SYNTHETIC, Splitter:B:48:0x008f] */
        /* JADX WARNING: Unknown top exception splitter block from list: {B:51:0x0095=Splitter:B:51:0x0095, B:44:0x008c=Splitter:B:44:0x008c} */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public static void a(java.lang.String r5, java.lang.Object... r6) {
            /*
                boolean r0 = defpackage.eao.a
                if (r0 != 0) goto L_0x0007
                return
            L_0x0007:
                boolean r0 = f
                if (r0 == 0) goto L_0x0099
                java.lang.String r0 = q
                boolean r0 = android.text.TextUtils.isEmpty(r0)
                if (r0 == 0) goto L_0x0015
                goto L_0x0099
            L_0x0015:
                java.lang.Object r0 = d
                monitor-enter(r0)
                r1 = 0
                java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x008d, all -> 0x0085 }
                java.lang.String r3 = q     // Catch:{ Exception -> 0x008d, all -> 0x0085 }
                r2.<init>(r3)     // Catch:{ Exception -> 0x008d, all -> 0x0085 }
                java.io.File r3 = r2.getParentFile()     // Catch:{ Exception -> 0x008d, all -> 0x0085 }
                if (r3 == 0) goto L_0x002f
                boolean r4 = r3.exists()     // Catch:{ Exception -> 0x008d, all -> 0x0085 }
                if (r4 != 0) goto L_0x002f
                r3.mkdirs()     // Catch:{ Exception -> 0x008d, all -> 0x0085 }
            L_0x002f:
                boolean r3 = r2.exists()     // Catch:{ Exception -> 0x008d, all -> 0x0085 }
                if (r3 != 0) goto L_0x0038
                r2.createNewFile()     // Catch:{ Exception -> 0x008d, all -> 0x0085 }
            L_0x0038:
                java.io.FileWriter r3 = new java.io.FileWriter     // Catch:{ Exception -> 0x008d, all -> 0x0085 }
                r4 = 1
                r3.<init>(r2, r4)     // Catch:{ Exception -> 0x008d, all -> 0x0085 }
                java.lang.String r1 = defpackage.eao.e()     // Catch:{ Exception -> 0x0083, all -> 0x0081 }
                r3.write(r1)     // Catch:{ Exception -> 0x0083, all -> 0x0081 }
                java.lang.String r1 = " "
                r3.write(r1)     // Catch:{ Exception -> 0x0083, all -> 0x0081 }
                boolean r1 = android.text.TextUtils.isEmpty(r5)     // Catch:{ Exception -> 0x0083, all -> 0x0081 }
                if (r1 != 0) goto L_0x0058
                r3.write(r5)     // Catch:{ Exception -> 0x0083, all -> 0x0081 }
                java.lang.String r5 = " "
                r3.write(r5)     // Catch:{ Exception -> 0x0083, all -> 0x0081 }
            L_0x0058:
                int r5 = r6.length     // Catch:{ Exception -> 0x0083, all -> 0x0081 }
                if (r5 <= 0) goto L_0x0075
                int r5 = r6.length     // Catch:{ Exception -> 0x0083, all -> 0x0081 }
                r1 = 0
            L_0x005d:
                if (r1 >= r5) goto L_0x0075
                r2 = r6[r1]     // Catch:{ Exception -> 0x0083, all -> 0x0081 }
                if (r2 == 0) goto L_0x0068
                java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x0083, all -> 0x0081 }
                goto L_0x006a
            L_0x0068:
                java.lang.String r2 = "null"
            L_0x006a:
                r3.write(r2)     // Catch:{ Exception -> 0x0083, all -> 0x0081 }
                java.lang.String r2 = ", "
                r3.write(r2)     // Catch:{ Exception -> 0x0083, all -> 0x0081 }
                int r1 = r1 + 1
                goto L_0x005d
            L_0x0075:
                java.lang.String r5 = "\r\n"
                r3.write(r5)     // Catch:{ Exception -> 0x0083, all -> 0x0081 }
                r3.flush()     // Catch:{ Exception -> 0x0083, all -> 0x0081 }
                r3.close()     // Catch:{ IOException -> 0x0095 }
                goto L_0x0095
            L_0x0081:
                r5 = move-exception
                goto L_0x0087
            L_0x0083:
                r1 = r3
                goto L_0x008d
            L_0x0085:
                r5 = move-exception
                r3 = r1
            L_0x0087:
                if (r3 == 0) goto L_0x008c
                r3.close()     // Catch:{ IOException -> 0x008c }
            L_0x008c:
                throw r5     // Catch:{ all -> 0x0093 }
            L_0x008d:
                if (r1 == 0) goto L_0x0095
                r1.close()     // Catch:{ IOException -> 0x0095 }
                goto L_0x0095
            L_0x0093:
                r5 = move-exception
                goto L_0x0097
            L_0x0095:
                monitor-exit(r0)     // Catch:{ all -> 0x0093 }
                return
            L_0x0097:
                monitor-exit(r0)     // Catch:{ all -> 0x0093 }
                throw r5
            L_0x0099:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: defpackage.eao.a.a(java.lang.String, java.lang.Object[]):void");
        }

        public static void a(String str) {
            if (eao.a) {
                a(null, str);
            }
        }

        private static void n() {
            String format = new SimpleDateFormat("yyyy-MM-dd_HHmmss").format(new Date(System.currentTimeMillis()));
            q = c;
            StringBuilder sb = new StringBuilder();
            sb.append(b);
            sb.append("公交_");
            sb.append(format);
            sb.append(".log");
            r = sb.toString();
            StringBuilder sb2 = new StringBuilder();
            sb2.append(b);
            sb2.append("步导_");
            sb2.append(format);
            sb2.append(".log");
            s = sb2.toString();
            StringBuilder sb3 = new StringBuilder();
            sb3.append(b);
            sb3.append("健康骑行_");
            sb3.append(format);
            sb3.append(".log");
            t = sb3.toString();
            StringBuilder sb4 = new StringBuilder();
            sb4.append(b);
            sb4.append("健康跑步_");
            sb4.append(format);
            sb4.append(".log");
            u = sb4.toString();
            StringBuilder sb5 = new StringBuilder();
            sb5.append(b);
            sb5.append("骑导_");
            sb5.append(format);
            sb5.append(".log");
            v = sb5.toString();
            StringBuilder sb6 = new StringBuilder();
            sb6.append(b);
            sb6.append("共享单车_");
            sb6.append(format);
            sb6.append(".log");
            w = sb6.toString();
            StringBuilder sb7 = new StringBuilder();
            sb7.append(b);
            sb7.append("电动车_");
            sb7.append(format);
            sb7.append(".log");
            x = sb7.toString();
            File file = new File(b);
            if (!file.exists() || !file.isDirectory()) {
                file.mkdirs();
            }
            File file2 = new File(eao.e, "wtbt/gdtbtlog");
            if (!file2.exists()) {
                file2.mkdirs();
            }
        }

        private static boolean o() {
            if (TextUtils.isEmpty(r)) {
                n();
            }
            f = p();
            f = true;
            return true;
        }

        private static boolean p() {
            boolean z = !TextUtils.isEmpty(q());
            e = z;
            return z;
        }

        public static boolean c() {
            if (!eao.a) {
                return false;
            }
            boolean z = !TextUtils.isEmpty(q());
            e = z;
            return z;
        }

        private static String q() {
            if (!TextUtils.isEmpty(a)) {
                return a;
            }
            File file = new File(Environment.getExternalStorageDirectory().getPath());
            if (!file.exists() || !file.canRead()) {
                return null;
            }
            File[] listFiles = file.listFiles();
            if (listFiles == null || listFiles.length == 0) {
                return null;
            }
            for (File name : listFiles) {
                String name2 = name.getName();
                if (name2.startsWith("testroutesimtrue_")) {
                    String[] split = name2.split("_");
                    if (split.length != 2) {
                        return null;
                    }
                    return split[1];
                }
            }
            return null;
        }

        static /* synthetic */ void j() {
            if (g != null) {
                bpf bpf = new bpf();
                StringBuilder sb = new StringBuilder("http://11.164.31.72:9922/busnavi?Type=2&ent=-1&Usr=");
                sb.append(q());
                bpf.setUrl(sb.toString());
                yq.a();
                yq.a((bph) bpf, (bpl<T>) new bpl<StringResponse>() {
                    final /* synthetic */ int a = 0;

                    public final void onFailure(bph bph, ResponseException responseException) {
                    }

                    public final /* synthetic */ void onSuccess(bpk bpk) {
                        Message obtain = Message.obtain(a.g, this.a);
                        obtain.obj = ((StringResponse) bpk).getResult();
                        obtain.what = 0;
                        a.g.sendMessage(obtain);
                    }
                });
            }
        }
    }

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory());
        sb.append("/autonavi/a_routelog/");
        e = sb.toString();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(Environment.getExternalStorageDirectory());
        sb2.append("/autonavi/");
        j = sb2.toString();
    }

    public static void a(String str) {
        if (a) {
            a((String) "", str);
        }
    }

    public static void a(String str, String str2) {
        if (a) {
            a("route-".concat(String.valueOf(str)), "[d]", str2, null);
        }
    }

    public static void b(String str) {
        if (a) {
            b("", str);
        }
    }

    public static void b(String str, String str2) {
        if (a) {
            a("route-".concat(String.valueOf(str)), "[i]", str2, null);
        }
    }

    public static void c(String str, String str2) {
        if (a) {
            a("route-".concat(String.valueOf(str)), "[v]", str2, null);
        }
    }

    public static void d(String str, String str2) {
        if (a) {
            a("route-".concat(String.valueOf(str)), "[w]", str2, null);
        }
    }

    public static void c(String str) {
        if (a) {
            e("", str);
        }
    }

    public static void e(String str, String str2) {
        if (a) {
            a("route-".concat(String.valueOf(str)), "[e]", str2, null);
        }
    }

    public static void a(String str, Throwable th) {
        if (a) {
            a("", str, th);
        }
    }

    public static void a(String str, String str2, Throwable th) {
        if (a) {
            a("route-".concat(String.valueOf(str)), "[e]", str2, th);
        }
    }

    public static void f(String str, String str2) {
        if (a) {
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            sb.append("\n");
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            if (stackTrace != null) {
                for (StackTraceElement stackTraceElement : stackTrace) {
                    sb.append("    ");
                    sb.append(stackTraceElement.toString());
                    sb.append("\n");
                }
            }
            c("route-".concat(String.valueOf(str)), sb.toString());
        }
    }

    /* access modifiers changed from: private */
    public static synchronized String e() {
        String format;
        synchronized (eao.class) {
            format = f.format(Long.valueOf(System.currentTimeMillis()));
        }
        return format;
    }

    private static String a(long j2) {
        if (j2 - h <= 86400000) {
            return i;
        }
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(j2);
        instance.set(11, 0);
        instance.set(12, 0);
        instance.set(13, 0);
        instance.set(14, 0);
        h = instance.getTimeInMillis();
        return new SimpleDateFormat("yyyy.MM.dd").format(instance.getTime());
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(6:2|(12:3|4|(1:6)|7|(1:9)|10|(1:12)(1:13)|14|(1:16)(1:17)|18|(1:20)|21)|30|31|33|34) */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00b8, code lost:
        if (r1 == null) goto L_0x00c0;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:26:0x00b7 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:33:0x00c0 */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:33:0x00c0=Splitter:B:33:0x00c0, B:26:0x00b7=Splitter:B:26:0x00b7} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void a(java.lang.String r5, java.lang.String r6, java.lang.String r7, java.lang.Throwable r8) {
        /*
            java.lang.Object r0 = d
            monitor-enter(r0)
            r1 = 0
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x00b8, all -> 0x00b1 }
            java.lang.String r3 = e     // Catch:{ Exception -> 0x00b8, all -> 0x00b1 }
            r2.<init>(r3)     // Catch:{ Exception -> 0x00b8, all -> 0x00b1 }
            boolean r3 = r2.exists()     // Catch:{ Exception -> 0x00b8, all -> 0x00b1 }
            if (r3 != 0) goto L_0x0014
            r2.mkdirs()     // Catch:{ Exception -> 0x00b8, all -> 0x00b1 }
        L_0x0014:
            long r2 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x00b8, all -> 0x00b1 }
            java.lang.String r2 = a(r2)     // Catch:{ Exception -> 0x00b8, all -> 0x00b1 }
            java.lang.String r3 = i     // Catch:{ Exception -> 0x00b8, all -> 0x00b1 }
            boolean r3 = r2.equals(r3)     // Catch:{ Exception -> 0x00b8, all -> 0x00b1 }
            if (r3 != 0) goto L_0x0041
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00b8, all -> 0x00b1 }
            r3.<init>()     // Catch:{ Exception -> 0x00b8, all -> 0x00b1 }
            java.lang.String r4 = e     // Catch:{ Exception -> 0x00b8, all -> 0x00b1 }
            r3.append(r4)     // Catch:{ Exception -> 0x00b8, all -> 0x00b1 }
            java.lang.String r4 = "route_"
            r3.append(r4)     // Catch:{ Exception -> 0x00b8, all -> 0x00b1 }
            r3.append(r2)     // Catch:{ Exception -> 0x00b8, all -> 0x00b1 }
            java.lang.String r2 = ".txt"
            r3.append(r2)     // Catch:{ Exception -> 0x00b8, all -> 0x00b1 }
            java.lang.String r2 = r3.toString()     // Catch:{ Exception -> 0x00b8, all -> 0x00b1 }
            i = r2     // Catch:{ Exception -> 0x00b8, all -> 0x00b1 }
        L_0x0041:
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x00b8, all -> 0x00b1 }
            java.lang.String r3 = i     // Catch:{ Exception -> 0x00b8, all -> 0x00b1 }
            r2.<init>(r3)     // Catch:{ Exception -> 0x00b8, all -> 0x00b1 }
            boolean r3 = r2.exists()     // Catch:{ Exception -> 0x00b8, all -> 0x00b1 }
            if (r3 != 0) goto L_0x0057
            r2.createNewFile()     // Catch:{ Exception -> 0x00b8, all -> 0x00b1 }
            java.io.FileWriter r3 = new java.io.FileWriter     // Catch:{ Exception -> 0x00b8, all -> 0x00b1 }
            r3.<init>(r2)     // Catch:{ Exception -> 0x00b8, all -> 0x00b1 }
            goto L_0x005d
        L_0x0057:
            java.io.FileWriter r3 = new java.io.FileWriter     // Catch:{ Exception -> 0x00b8, all -> 0x00b1 }
            r4 = 1
            r3.<init>(r2, r4)     // Catch:{ Exception -> 0x00b8, all -> 0x00b1 }
        L_0x005d:
            r1 = r3
            java.lang.String r2 = e()     // Catch:{ Exception -> 0x00b8, all -> 0x00b1 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00b8, all -> 0x00b1 }
            r3.<init>()     // Catch:{ Exception -> 0x00b8, all -> 0x00b1 }
            r3.append(r2)     // Catch:{ Exception -> 0x00b8, all -> 0x00b1 }
            java.lang.String r2 = "|"
            r3.append(r2)     // Catch:{ Exception -> 0x00b8, all -> 0x00b1 }
            r3.append(r6)     // Catch:{ Exception -> 0x00b8, all -> 0x00b1 }
            java.lang.String r6 = "|"
            r3.append(r6)     // Catch:{ Exception -> 0x00b8, all -> 0x00b1 }
            r3.append(r5)     // Catch:{ Exception -> 0x00b8, all -> 0x00b1 }
            java.lang.String r5 = "|"
            r3.append(r5)     // Catch:{ Exception -> 0x00b8, all -> 0x00b1 }
            java.lang.String r5 = r3.toString()     // Catch:{ Exception -> 0x00b8, all -> 0x00b1 }
            r1.write(r5)     // Catch:{ Exception -> 0x00b8, all -> 0x00b1 }
            if (r7 == 0) goto L_0x0089
            goto L_0x008b
        L_0x0089:
            java.lang.String r7 = ""
        L_0x008b:
            r1.write(r7)     // Catch:{ Exception -> 0x00b8, all -> 0x00b1 }
            java.lang.String r5 = "\r\n"
            r1.write(r5)     // Catch:{ Exception -> 0x00b8, all -> 0x00b1 }
            if (r8 == 0) goto L_0x00ad
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00b8, all -> 0x00b1 }
            r5.<init>()     // Catch:{ Exception -> 0x00b8, all -> 0x00b1 }
            java.lang.String r6 = android.util.Log.getStackTraceString(r8)     // Catch:{ Exception -> 0x00b8, all -> 0x00b1 }
            r5.append(r6)     // Catch:{ Exception -> 0x00b8, all -> 0x00b1 }
            java.lang.String r6 = "\r\n"
            r5.append(r6)     // Catch:{ Exception -> 0x00b8, all -> 0x00b1 }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x00b8, all -> 0x00b1 }
            r1.write(r5)     // Catch:{ Exception -> 0x00b8, all -> 0x00b1 }
        L_0x00ad:
            r1.flush()     // Catch:{ Exception -> 0x00b8, all -> 0x00b1 }
            goto L_0x00ba
        L_0x00b1:
            r5 = move-exception
            if (r1 == 0) goto L_0x00b7
            r1.close()     // Catch:{ IOException -> 0x00b7 }
        L_0x00b7:
            throw r5     // Catch:{ all -> 0x00be }
        L_0x00b8:
            if (r1 == 0) goto L_0x00c0
        L_0x00ba:
            r1.close()     // Catch:{ IOException -> 0x00c0 }
            goto L_0x00c0
        L_0x00be:
            r5 = move-exception
            goto L_0x00c2
        L_0x00c0:
            monitor-exit(r0)     // Catch:{ all -> 0x00be }
            return
        L_0x00c2:
            monitor-exit(r0)     // Catch:{ all -> 0x00be }
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.eao.a(java.lang.String, java.lang.String, java.lang.String, java.lang.Throwable):void");
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(6:4|(8:5|6|(1:8)|9|(1:11)(1:12)|13|(1:15)(1:16)|17)|26|27|29|30) */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x008a, code lost:
        if (r3 == null) goto L_0x0092;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:22:0x0089 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:29:0x0092 */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:29:0x0092=Splitter:B:29:0x0092, B:22:0x0089=Splitter:B:22:0x0089} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void d(java.lang.String r7) {
        /*
            boolean r0 = com.autonavi.minimap.ajx3.AjxConstant.RELEASE
            if (r0 != 0) goto L_0x0096
            java.lang.String r0 = "route-performance-"
            java.lang.String r1 = "[i]"
            java.lang.Object r2 = k
            monitor-enter(r2)
            r3 = 0
            java.io.File r4 = new java.io.File     // Catch:{ Exception -> 0x008a, all -> 0x0083 }
            java.lang.String r5 = j     // Catch:{ Exception -> 0x008a, all -> 0x0083 }
            r4.<init>(r5)     // Catch:{ Exception -> 0x008a, all -> 0x0083 }
            boolean r5 = r4.exists()     // Catch:{ Exception -> 0x008a, all -> 0x0083 }
            if (r5 != 0) goto L_0x001c
            r4.mkdirs()     // Catch:{ Exception -> 0x008a, all -> 0x0083 }
        L_0x001c:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x008a, all -> 0x0083 }
            r4.<init>()     // Catch:{ Exception -> 0x008a, all -> 0x0083 }
            java.lang.String r5 = j     // Catch:{ Exception -> 0x008a, all -> 0x0083 }
            r4.append(r5)     // Catch:{ Exception -> 0x008a, all -> 0x0083 }
            java.lang.String r5 = "performance.log"
            r4.append(r5)     // Catch:{ Exception -> 0x008a, all -> 0x0083 }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x008a, all -> 0x0083 }
            java.io.File r5 = new java.io.File     // Catch:{ Exception -> 0x008a, all -> 0x0083 }
            r5.<init>(r4)     // Catch:{ Exception -> 0x008a, all -> 0x0083 }
            boolean r4 = r5.exists()     // Catch:{ Exception -> 0x008a, all -> 0x0083 }
            if (r4 != 0) goto L_0x0043
            r5.createNewFile()     // Catch:{ Exception -> 0x008a, all -> 0x0083 }
            java.io.FileWriter r4 = new java.io.FileWriter     // Catch:{ Exception -> 0x008a, all -> 0x0083 }
            r4.<init>(r5)     // Catch:{ Exception -> 0x008a, all -> 0x0083 }
            goto L_0x0049
        L_0x0043:
            java.io.FileWriter r4 = new java.io.FileWriter     // Catch:{ Exception -> 0x008a, all -> 0x0083 }
            r6 = 1
            r4.<init>(r5, r6)     // Catch:{ Exception -> 0x008a, all -> 0x0083 }
        L_0x0049:
            r3 = r4
            java.lang.String r4 = e()     // Catch:{ Exception -> 0x008a, all -> 0x0083 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x008a, all -> 0x0083 }
            r5.<init>()     // Catch:{ Exception -> 0x008a, all -> 0x0083 }
            r5.append(r4)     // Catch:{ Exception -> 0x008a, all -> 0x0083 }
            java.lang.String r4 = "|"
            r5.append(r4)     // Catch:{ Exception -> 0x008a, all -> 0x0083 }
            r5.append(r1)     // Catch:{ Exception -> 0x008a, all -> 0x0083 }
            java.lang.String r1 = "|"
            r5.append(r1)     // Catch:{ Exception -> 0x008a, all -> 0x0083 }
            r5.append(r0)     // Catch:{ Exception -> 0x008a, all -> 0x0083 }
            java.lang.String r0 = "|"
            r5.append(r0)     // Catch:{ Exception -> 0x008a, all -> 0x0083 }
            java.lang.String r0 = r5.toString()     // Catch:{ Exception -> 0x008a, all -> 0x0083 }
            r3.write(r0)     // Catch:{ Exception -> 0x008a, all -> 0x0083 }
            if (r7 == 0) goto L_0x0075
            goto L_0x0077
        L_0x0075:
            java.lang.String r7 = ""
        L_0x0077:
            r3.write(r7)     // Catch:{ Exception -> 0x008a, all -> 0x0083 }
            java.lang.String r7 = "\r\n"
            r3.write(r7)     // Catch:{ Exception -> 0x008a, all -> 0x0083 }
            r3.flush()     // Catch:{ Exception -> 0x008a, all -> 0x0083 }
            goto L_0x008c
        L_0x0083:
            r7 = move-exception
            if (r3 == 0) goto L_0x0089
            r3.close()     // Catch:{ IOException -> 0x0089 }
        L_0x0089:
            throw r7     // Catch:{ all -> 0x0090 }
        L_0x008a:
            if (r3 == 0) goto L_0x0092
        L_0x008c:
            r3.close()     // Catch:{ IOException -> 0x0092 }
            goto L_0x0092
        L_0x0090:
            r7 = move-exception
            goto L_0x0094
        L_0x0092:
            monitor-exit(r2)     // Catch:{ all -> 0x0090 }
            return
        L_0x0094:
            monitor-exit(r2)     // Catch:{ all -> 0x0090 }
            throw r7
        L_0x0096:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.eao.d(java.lang.String):void");
    }
}
