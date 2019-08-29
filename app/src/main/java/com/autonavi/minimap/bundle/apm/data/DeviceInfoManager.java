package com.autonavi.minimap.bundle.apm.data;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import com.alipay.mobile.beehive.rpc.action.ActionConstant;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.autonavi.minimap.bundle.apm.util.NetworkUtils;
import com.squareup.leakcanary.internal.LeakCanaryInternals;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public final class DeviceInfoManager {
    Boolean a = null;
    Boolean b = null;
    Long c = Long.valueOf(0);
    Integer d = Integer.valueOf(0);
    public String e = null;
    public String f = null;
    public String g = null;
    public Integer h = Integer.valueOf(0);
    public Float i = Float.valueOf(0.0f);
    public Float j = Float.valueOf(0.0f);
    public float[] k = null;
    public String l = "";
    public Long m = Long.valueOf(0);
    Integer n = Integer.valueOf(0);
    Integer o = Integer.valueOf(0);
    Float p = Float.valueOf(0.0f);
    public String q = Build.MODEL;
    public String r = Build.BRAND;
    public Integer s = Integer.valueOf(VERSION.SDK_INT);
    public String t = VERSION.RELEASE;
    Integer u = null;
    public volatile Context v;
    ViewGroup w;
    TeleGLSurfaceView x;
    public String y;
    boolean z;

    class TeleGLSurfaceView extends GLSurfaceView {
        b mRenderer;

        public TeleGLSurfaceView(Context context) {
            super(context);
            setEGLConfigChooser(8, 8, 8, 8, 0, 0);
            this.mRenderer = new b();
            setRenderer(this.mRenderer);
        }
    }

    public static final class a {
        public static final DeviceInfoManager a = new DeviceInfoManager();
    }

    class b implements Renderer {
        public final void onDrawFrame(GL10 gl10) {
        }

        public final void onSurfaceChanged(GL10 gl10, int i, int i2) {
        }

        b() {
        }

        public final void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
            try {
                DeviceInfoManager deviceInfoManager = DeviceInfoManager.this;
                deviceInfoManager.x = null;
                deviceInfoManager.w = null;
                DeviceInfoManager.this.C();
            } catch (Throwable unused) {
            }
        }
    }

    public final void a() {
        switch (NetworkUtils.a(this.v)) {
            case NETWORK_2G:
                this.l = "2G";
                return;
            case NETWORK_3G:
                this.l = "3G";
                return;
            case NETWORK_4G:
                this.l = "4G";
                return;
            case NETWORK_WIFI:
                this.l = "WiFi";
                return;
            case NETWORK_NO:
                this.l = H5Utils.NETWORK_TYPE_NOTREACHABLE;
                return;
            case NETWORK_UNKNOWN:
                this.l = "UnKnown";
                return;
            case NETWORK_ETHERNET:
                this.l = "Ethernet";
                break;
        }
    }

    public final boolean b() {
        if (this.a == null) {
            r();
        }
        return this.a.booleanValue();
    }

    public final boolean c() {
        if (this.b == null) {
            s();
        }
        return this.b.booleanValue();
    }

    public final long d() {
        if (this.c == null || this.c.longValue() <= 0) {
            t();
        }
        return this.c.longValue();
    }

    public final long e() {
        if (this.d == null || this.d.intValue() <= 0) {
            t();
        }
        return (long) this.d.intValue();
    }

    public final String f() {
        if (this.f == null) {
            u();
        }
        return this.f;
    }

    public final String g() {
        if (this.e == null) {
            u();
        }
        return this.e;
    }

    public final String h() {
        if (this.g == null) {
            v();
        }
        return this.g;
    }

    public final int i() {
        if (this.h == null || this.h.intValue() <= 0) {
            w();
        }
        return this.h.intValue();
    }

    public final float j() {
        if (this.i == null || this.i.floatValue() <= 0.0f) {
            x();
        }
        return this.i.floatValue();
    }

    public final float k() {
        if (this.j == null || this.j.floatValue() <= 0.0f) {
            x();
        }
        return this.j.floatValue();
    }

    public final float[] l() {
        if (this.k == null || this.k.length == 0) {
            x();
        }
        return this.k;
    }

    public final long m() {
        if (this.m == null || this.m.longValue() <= 0) {
            A();
        }
        return this.m.longValue();
    }

    public final int n() {
        if (this.n == null || this.n.intValue() <= 0) {
            y();
        }
        return this.n.intValue();
    }

    public final int o() {
        if (this.o == null || this.o.intValue() <= 0) {
            y();
        }
        return this.o.intValue();
    }

    public final float p() {
        if (this.p == null || this.p.floatValue() <= 0.0f) {
            y();
        }
        return this.p.floatValue();
    }

    public final int q() {
        if (this.u == null || this.u.intValue() <= 0) {
            z();
        }
        return this.u.intValue();
    }

    public final void r() {
        this.a = Boolean.FALSE;
        String[] strArr = {"/system/bin/su", "/system/xbin/su", "/system/sbin/su", "/sbin/su", "/vendor/bin/su"};
        int i2 = 0;
        while (i2 < 5) {
            try {
                if (new File(strArr[i2]).exists()) {
                    this.a = Boolean.TRUE;
                    return;
                }
                i2++;
            } catch (Exception unused) {
                return;
            }
        }
    }

    public final void s() {
        try {
            this.b = Boolean.valueOf("1".equals(Class.forName("android.os.SystemProperties").getMethod("get", new Class[]{String.class}).invoke(null, new Object[]{"ro.kernel.qemu"}).toString()));
        } catch (ClassNotFoundException e2) {
            e2.printStackTrace();
        } catch (NoSuchMethodException e3) {
            e3.printStackTrace();
        } catch (IllegalAccessException e4) {
            e4.printStackTrace();
        } catch (InvocationTargetException e5) {
            e5.printStackTrace();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x003c  */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x002e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void t() {
        /*
            r8 = this;
            android.app.ActivityManager$MemoryInfo r0 = new android.app.ActivityManager$MemoryInfo
            r0.<init>()
            android.content.Context r1 = r8.v
            java.lang.String r2 = "activity"
            java.lang.Object r1 = r1.getSystemService(r2)
            android.app.ActivityManager r1 = (android.app.ActivityManager) r1
            r1.getMemoryInfo(r0)
            java.lang.Integer r1 = r8.s
            int r1 = r1.intValue()
            r2 = 0
            r4 = 16
            if (r1 < r4) goto L_0x0029
            long r4 = r0.totalMem     // Catch:{ Throwable -> 0x0025 }
            r6 = 1024(0x400, double:5.06E-321)
            long r4 = r4 / r6
            long r4 = r4 / r6
            goto L_0x002a
        L_0x0025:
            r1 = move-exception
            r1.printStackTrace()
        L_0x0029:
            r4 = r2
        L_0x002a:
            int r1 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r1 != 0) goto L_0x0033
            int r1 = D()
            long r4 = (long) r1
        L_0x0033:
            r1 = 256(0x100, double:1.265E-321)
            int r3 = (r4 > r1 ? 1 : (r4 == r1 ? 0 : -1))
            r6 = 512(0x200, double:2.53E-321)
            if (r3 >= 0) goto L_0x003c
            goto L_0x0054
        L_0x003c:
            int r1 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r1 >= 0) goto L_0x0042
            r1 = r6
            goto L_0x0054
        L_0x0042:
            r1 = 1
        L_0x0043:
            r2 = 20
            if (r1 > r2) goto L_0x0053
            int r2 = r1 * 1024
            long r2 = (long) r2
            int r6 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r6 >= 0) goto L_0x0050
            r1 = r2
            goto L_0x0054
        L_0x0050:
            int r1 = r1 + 1
            goto L_0x0043
        L_0x0053:
            r1 = r4
        L_0x0054:
            java.lang.Long r1 = java.lang.Long.valueOf(r1)
            r8.c = r1
            long r0 = r0.threshold     // Catch:{ Throwable -> 0x0068 }
            int r0 = (int) r0     // Catch:{ Throwable -> 0x0068 }
            int r0 = r0 / 1024
            int r0 = r0 / 1024
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ Throwable -> 0x0068 }
            r8.d = r0     // Catch:{ Throwable -> 0x0068 }
            return
        L_0x0068:
            r0 = 64
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            r8.d = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.bundle.apm.data.DeviceInfoManager.t():void");
    }

    public final void u() {
        if (TextUtils.isEmpty(this.e) || TextUtils.isEmpty(this.f)) {
            String str = null;
            String upperCase = Build.HARDWARE.toUpperCase();
            if (!TextUtils.isEmpty(upperCase)) {
                if (upperCase.contains("EXYNOS")) {
                    str = upperCase.replace("samsung", "");
                } else {
                    try {
                        Method declaredMethod = Class.forName("android.os.Build").getDeclaredMethod("getString", new Class[]{String.class});
                        declaredMethod.setAccessible(true);
                        String str2 = (String) declaredMethod.invoke(Build.class, new Object[]{"ro.board.platform"});
                        if (str2 != null) {
                            try {
                                if (str2.equals("mtk")) {
                                    str2 = upperCase;
                                }
                            } catch (Exception unused) {
                            }
                        }
                        str = str2;
                    } catch (Exception unused2) {
                    }
                    if (upperCase != null && upperCase.length() >= 4 && (TextUtils.isEmpty(str) || str.equals("unknown") || str.contains("samsungexynos") || str.contains("mrvl"))) {
                        str = upperCase;
                    }
                }
                if (str != null) {
                    str = str.toUpperCase();
                }
                if (str != null) {
                    if (str.contains("EXYNOS") || str.contains("SMDK") || str.contains("UNIVERSAL")) {
                        this.f = "三星";
                    } else if (str.contains("MSM") || str.contains("APQ") || str.contains("QSD")) {
                        this.f = "高通";
                    } else if (str.contains("REDHOOKBAY") || str.contains("MOOREFIELD") || str.contains("MERRIFIELD")) {
                        this.f = "英特尔";
                    } else if (str.contains("MT")) {
                        this.f = "联发科";
                    } else if (str.contains("OMAP")) {
                        this.f = "德州仪器";
                    } else if (str.contains("PXA")) {
                        this.f = "Marvell";
                    } else if (str.contains("HI") || str.contains("K3")) {
                        this.f = "华为海思";
                    } else if (str.contains("SP") || str.contains("SC")) {
                        this.f = "展讯";
                    } else if (str.contains("TEGRA") || str.contains(LeakCanaryInternals.NVIDIA)) {
                        this.f = LeakCanaryInternals.NVIDIA;
                    } else if (str.startsWith("LC")) {
                        this.f = "联芯科技";
                    } else {
                        this.f = upperCase;
                    }
                    this.e = str;
                }
            }
        }
    }

    public final void v() {
        Throwable th;
        int i2;
        this.g = "UnKnown";
        BufferedReader bufferedReader = null;
        try {
            BufferedReader bufferedReader2 = new BufferedReader(new FileReader("/proc/cpuinfo"));
            try {
                String readLine = bufferedReader2.readLine();
                while (true) {
                    if (readLine == null) {
                        break;
                    }
                    if (!readLine.contains("AArch") && !readLine.contains("ARM") && !readLine.contains("Intel(R)")) {
                        if (!readLine.contains("model name")) {
                            readLine = bufferedReader2.readLine();
                        }
                    }
                    int indexOf = readLine.indexOf(": ");
                    if (indexOf >= 0) {
                        readLine = readLine.substring(indexOf + 2);
                        if (!readLine.contains("Intel(R)")) {
                            i2 = readLine.indexOf(32);
                        } else {
                            i2 = readLine.lastIndexOf(41) + 1;
                        }
                        if (i2 > 0) {
                            this.g = readLine.substring(0, i2);
                            break;
                        }
                    } else {
                        continue;
                    }
                }
                cwf.a((Closeable) bufferedReader2);
            } catch (Exception unused) {
                bufferedReader = bufferedReader2;
                try {
                    this.g = "UnKnown";
                    cwf.a((Closeable) bufferedReader);
                } catch (Throwable th2) {
                    bufferedReader2 = bufferedReader;
                    th = th2;
                    cwf.a((Closeable) bufferedReader2);
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                cwf.a((Closeable) bufferedReader2);
                throw th;
            }
        } catch (Exception unused2) {
            this.g = "UnKnown";
            cwf.a((Closeable) bufferedReader);
        }
    }

    public final void w() {
        this.h = Integer.valueOf(Runtime.getRuntime().availableProcessors());
    }

    public final void x() {
        if (this.k == null) {
            this.k = new float[i()];
        }
        int i2 = 0;
        while (i2 < i()) {
            try {
                StringBuilder sb = new StringBuilder("/sys/devices/system/cpu/cpu");
                sb.append(i2);
                sb.append("/cpufreq/cpuinfo_max_freq");
                File file = new File(sb.toString());
                if (file.exists()) {
                    FileReader fileReader = new FileReader(file);
                    String readLine = new BufferedReader(fileReader).readLine();
                    fileReader.close();
                    if (readLine != null) {
                        float parseLong = ((float) Long.parseLong(readLine)) / 1000000.0f;
                        this.k[i2] = parseLong;
                        if (this.i.floatValue() < parseLong) {
                            this.i = Float.valueOf(parseLong);
                        }
                        if (this.j.floatValue() <= 0.0f) {
                            this.j = Float.valueOf(parseLong);
                        } else if (this.j.floatValue() > parseLong) {
                            this.j = Float.valueOf(parseLong);
                        }
                    }
                }
                i2++;
            } catch (Exception unused) {
            }
        }
        if (this.j.floatValue() <= 0.0f) {
            this.j = this.i;
        }
        if (this.z) {
            this.z = false;
            C();
        }
    }

    public final void y() {
        DisplayMetrics displayMetrics = this.v.getResources().getDisplayMetrics();
        if (displayMetrics != null) {
            this.p = Float.valueOf(displayMetrics.density);
            this.n = Integer.valueOf(displayMetrics.widthPixels);
            this.o = Integer.valueOf(displayMetrics.heightPixels);
        }
    }

    public final void z() {
        long j2;
        try {
            StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
            long j3 = 0;
            if (VERSION.SDK_INT >= 18) {
                j2 = statFs.getBlockSizeLong();
            } else {
                j2 = (long) statFs.getBlockSize();
            }
            if (VERSION.SDK_INT >= 18) {
                j3 = statFs.getBlockCountLong();
            } else {
                j2 = (long) statFs.getBlockCount();
            }
            this.u = Integer.valueOf((int) (((j2 * j3) / 1024) / 1024));
        } catch (Exception unused) {
        }
    }

    public final void A() {
        this.m = Long.valueOf(E());
    }

    private static int D() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("/proc/meminfo"));
            String readLine = bufferedReader.readLine();
            bufferedReader.close();
            return (readLine != null ? Integer.parseInt(readLine.replace("MemTotal:", "").replace("kB", "").replace(Token.SEPARATOR, "")) : 0) / 1024;
        } catch (Exception unused) {
            return 1024;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0071, code lost:
        r9 = new java.lang.StringBuilder();
        r9.append(r2[r3].getAbsolutePath());
        r9.append("/max_freq");
        r8 = new java.io.File(r9.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0091, code lost:
        if (r8.exists() != false) goto L_0x00af;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0093, code lost:
        r9 = new java.lang.StringBuilder();
        r9.append(r2[r3].getAbsolutePath());
        r9.append("/max_gpuclk");
        r8 = new java.io.File(r9.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00b3, code lost:
        if (r8.exists() == false) goto L_0x00dc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00b5, code lost:
        r2 = new java.io.FileReader(r8);
        r3 = new java.io.BufferedReader(r2).readLine();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00c3, code lost:
        if (r3 == null) goto L_0x00d4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00c5, code lost:
        r8 = java.lang.Long.parseLong(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00cb, code lost:
        if (r8 <= 0) goto L_0x00d3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:?, code lost:
        r6 = (r8 / 1000) / 1000;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00d1, code lost:
        r6 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00d3, code lost:
        r6 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:?, code lost:
        r2.close();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private long E() {
        /*
            r11 = this;
            r0 = 0
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x00db }
            java.lang.String r3 = "/sys/devices/platform/gpusysfs/gpu_max_clock"
            r2.<init>(r3)     // Catch:{ Exception -> 0x00db }
            boolean r3 = r2.exists()     // Catch:{ Exception -> 0x00db }
            if (r3 != 0) goto L_0x0016
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x00db }
            java.lang.String r3 = "/sys/devices/platform/gpusysfs/max_freq"
            r2.<init>(r3)     // Catch:{ Exception -> 0x00db }
        L_0x0016:
            boolean r3 = r2.exists()     // Catch:{ Exception -> 0x00db }
            r4 = 1000(0x3e8, double:4.94E-321)
            if (r3 == 0) goto L_0x0044
            java.io.FileReader r3 = new java.io.FileReader     // Catch:{ Exception -> 0x00db }
            r3.<init>(r2)     // Catch:{ Exception -> 0x00db }
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch:{ Exception -> 0x00db }
            r2.<init>(r3)     // Catch:{ Exception -> 0x00db }
            java.lang.String r2 = r2.readLine()     // Catch:{ Exception -> 0x00db }
            if (r2 == 0) goto L_0x003b
            long r6 = java.lang.Long.parseLong(r2)     // Catch:{ Exception -> 0x00db }
            int r2 = (r6 > r0 ? 1 : (r6 == r0 ? 0 : -1))
            if (r2 <= 0) goto L_0x003c
            long r8 = r6 / r4
            long r8 = r8 / r4
            r6 = r8
            goto L_0x003c
        L_0x003b:
            r6 = r0
        L_0x003c:
            r3.close()     // Catch:{ Exception -> 0x00dc }
            int r2 = (r6 > r0 ? 1 : (r6 == r0 ? 0 : -1))
            if (r2 <= 0) goto L_0x0045
            return r6
        L_0x0044:
            r6 = r0
        L_0x0045:
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x00dc }
            java.lang.String r3 = "/sys/class/devfreq/"
            r2.<init>(r3)     // Catch:{ Exception -> 0x00dc }
            boolean r3 = r2.exists()     // Catch:{ Exception -> 0x00dc }
            if (r3 == 0) goto L_0x00dc
            boolean r3 = r2.isDirectory()     // Catch:{ Exception -> 0x00dc }
            if (r3 == 0) goto L_0x00dc
            java.io.File[] r2 = r2.listFiles()     // Catch:{ Exception -> 0x00dc }
            if (r2 != 0) goto L_0x005f
            return r0
        L_0x005f:
            r3 = 0
        L_0x0060:
            int r8 = r2.length     // Catch:{ Exception -> 0x00dc }
            if (r3 >= r8) goto L_0x00dc
            r8 = r2[r3]     // Catch:{ Exception -> 0x00dc }
            java.lang.String r8 = r8.getName()     // Catch:{ Exception -> 0x00dc }
            java.lang.String r9 = "kgsl"
            boolean r8 = r8.contains(r9)     // Catch:{ Exception -> 0x00dc }
            if (r8 == 0) goto L_0x00d8
            java.io.File r8 = new java.io.File     // Catch:{ Exception -> 0x00dc }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00dc }
            r9.<init>()     // Catch:{ Exception -> 0x00dc }
            r10 = r2[r3]     // Catch:{ Exception -> 0x00dc }
            java.lang.String r10 = r10.getAbsolutePath()     // Catch:{ Exception -> 0x00dc }
            r9.append(r10)     // Catch:{ Exception -> 0x00dc }
            java.lang.String r10 = "/max_freq"
            r9.append(r10)     // Catch:{ Exception -> 0x00dc }
            java.lang.String r9 = r9.toString()     // Catch:{ Exception -> 0x00dc }
            r8.<init>(r9)     // Catch:{ Exception -> 0x00dc }
            boolean r9 = r8.exists()     // Catch:{ Exception -> 0x00dc }
            if (r9 != 0) goto L_0x00af
            java.io.File r8 = new java.io.File     // Catch:{ Exception -> 0x00dc }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00dc }
            r9.<init>()     // Catch:{ Exception -> 0x00dc }
            r2 = r2[r3]     // Catch:{ Exception -> 0x00dc }
            java.lang.String r2 = r2.getAbsolutePath()     // Catch:{ Exception -> 0x00dc }
            r9.append(r2)     // Catch:{ Exception -> 0x00dc }
            java.lang.String r2 = "/max_gpuclk"
            r9.append(r2)     // Catch:{ Exception -> 0x00dc }
            java.lang.String r2 = r9.toString()     // Catch:{ Exception -> 0x00dc }
            r8.<init>(r2)     // Catch:{ Exception -> 0x00dc }
        L_0x00af:
            boolean r2 = r8.exists()     // Catch:{ Exception -> 0x00dc }
            if (r2 == 0) goto L_0x00dc
            java.io.FileReader r2 = new java.io.FileReader     // Catch:{ Exception -> 0x00dc }
            r2.<init>(r8)     // Catch:{ Exception -> 0x00dc }
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch:{ Exception -> 0x00dc }
            r3.<init>(r2)     // Catch:{ Exception -> 0x00dc }
            java.lang.String r3 = r3.readLine()     // Catch:{ Exception -> 0x00dc }
            if (r3 == 0) goto L_0x00d4
            long r8 = java.lang.Long.parseLong(r3)     // Catch:{ Exception -> 0x00dc }
            int r3 = (r8 > r0 ? 1 : (r8 == r0 ? 0 : -1))
            if (r3 <= 0) goto L_0x00d3
            long r6 = r8 / r4
            long r6 = r6 / r4
            goto L_0x00d4
        L_0x00d1:
            r6 = r8
            goto L_0x00dc
        L_0x00d3:
            r6 = r8
        L_0x00d4:
            r2.close()     // Catch:{ Exception -> 0x00dc }
            goto L_0x00dc
        L_0x00d8:
            int r3 = r3 + 1
            goto L_0x0060
        L_0x00db:
            r6 = r0
        L_0x00dc:
            int r0 = (r6 > r0 ? 1 : (r6 == r0 ? 0 : -1))
            if (r0 != 0) goto L_0x00e6
            java.lang.String r0 = "/sys/devices/"
            long r6 = r11.a(r0)
        L_0x00e6:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.bundle.apm.data.DeviceInfoManager.E():long");
    }

    private long a(String str) {
        long j2;
        long j3 = 0;
        try {
            File file = new File(str);
            if (!file.exists() || !file.isDirectory()) {
                j2 = 0;
            } else {
                File[] listFiles = file.listFiles();
                if (listFiles == null) {
                    return 0;
                }
                int i2 = 0;
                j2 = 0;
                while (i2 < listFiles.length) {
                    try {
                        File file2 = listFiles[i2];
                        if (file2 != null && file2.getName().contains("kgsl") && file2.isDirectory()) {
                            long a2 = a(file2.getAbsolutePath());
                            if (a2 > 0) {
                                return a2;
                            }
                            j2 = a2;
                        }
                        i2++;
                    } catch (Exception unused) {
                    }
                }
            }
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("/max_freq");
            File file3 = new File(sb.toString());
            if (!file3.exists()) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str);
                sb2.append("/max_gpuclk");
                file3 = new File(sb2.toString());
            }
            if (file3.exists()) {
                FileReader fileReader = new FileReader(file3);
                String readLine = new BufferedReader(fileReader).readLine();
                if (readLine != null) {
                    long parseLong = Long.parseLong(readLine);
                    if (parseLong > 0) {
                        try {
                            j2 = (parseLong / 1000) / 1000;
                        } catch (Exception unused2) {
                            j3 = parseLong;
                        }
                    } else {
                        j3 = parseLong;
                        fileReader.close();
                        return j3;
                    }
                }
                j3 = j2;
                fileReader.close();
                return j3;
            }
            j3 = j2;
        } catch (Exception unused3) {
        }
        return j3;
    }

    public final void B() {
        BufferedReader bufferedReader;
        Exception e2;
        StringBuilder sb = new StringBuilder();
        sb.append(this.y);
        sb.append(ActionConstant.TYPE_RETRY);
        File file = new File(this.y);
        if (file.exists()) {
            this.z = true;
            try {
                bufferedReader = new BufferedReader(new FileReader(file));
                try {
                    this.f = bufferedReader.readLine();
                    this.e = bufferedReader.readLine();
                    this.g = bufferedReader.readLine();
                    String readLine = bufferedReader.readLine();
                    if (readLine != null) {
                        this.h = Integer.valueOf(Integer.parseInt(readLine));
                    }
                    String readLine2 = bufferedReader.readLine();
                    if (readLine2 != null) {
                        this.i = Float.valueOf(Float.parseFloat(readLine2));
                    }
                    String readLine3 = bufferedReader.readLine();
                    if (readLine3 != null) {
                        this.m = Long.valueOf(Long.parseLong(readLine3));
                    }
                    String readLine4 = bufferedReader.readLine();
                    if (readLine4 != null) {
                        this.j = Float.valueOf(Float.parseFloat(readLine4));
                        if (this.j.floatValue() <= 0.0f) {
                            this.j = this.i;
                        }
                    }
                    String readLine5 = bufferedReader.readLine();
                    if (this.k == null) {
                        this.k = new float[i()];
                    }
                    if (readLine5 != null) {
                        try {
                            String[] split = readLine5.split(",");
                            if (split != null && split.length > 0) {
                                for (int i2 = 0; i2 < split.length; i2++) {
                                    this.k[i2] = Float.parseFloat(split[i2]);
                                }
                            }
                        } catch (Exception e3) {
                            e3.printStackTrace();
                        }
                    }
                    cwf.a((Closeable) bufferedReader);
                } catch (Exception e4) {
                    e2 = e4;
                    try {
                        e2.printStackTrace();
                        cwf.a((Closeable) bufferedReader);
                    } catch (Throwable th) {
                        th = th;
                        cwf.a((Closeable) bufferedReader);
                        throw th;
                    }
                }
            } catch (Exception e5) {
                e2 = e5;
                bufferedReader = null;
                e2.printStackTrace();
                cwf.a((Closeable) bufferedReader);
            } catch (Throwable th2) {
                th = th2;
                bufferedReader = null;
                cwf.a((Closeable) bufferedReader);
                throw th;
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final synchronized void C() {
        if (!this.z) {
            this.z = true;
            BufferedWriter bufferedWriter = null;
            File file = new File(this.y);
            if (file.exists()) {
                file.delete();
            }
            try {
                BufferedWriter bufferedWriter2 = new BufferedWriter(new FileWriter(file));
                try {
                    bufferedWriter2.write(this.f);
                    bufferedWriter2.newLine();
                    bufferedWriter2.write(this.e);
                    bufferedWriter2.newLine();
                    bufferedWriter2.write(this.g);
                    bufferedWriter2.newLine();
                    bufferedWriter2.write(String.valueOf(this.h));
                    bufferedWriter2.newLine();
                    bufferedWriter2.write(String.valueOf(this.i));
                    bufferedWriter2.newLine();
                    bufferedWriter2.write(String.valueOf(this.m));
                    bufferedWriter2.newLine();
                    bufferedWriter2.write(String.valueOf(this.j));
                    bufferedWriter2.newLine();
                    StringBuilder sb = new StringBuilder(50);
                    if (this.k != null && this.k.length > 0) {
                        for (int i2 = 0; i2 < this.k.length; i2++) {
                            sb.append(this.k[i2]);
                            if (i2 < this.k.length - 1) {
                                sb.append(',');
                            }
                        }
                    }
                    bufferedWriter2.write(sb.toString());
                    bufferedWriter2.flush();
                    cwf.a((Closeable) bufferedWriter2);
                } catch (Exception e2) {
                    e = e2;
                    bufferedWriter = bufferedWriter2;
                    try {
                        e.printStackTrace();
                        this.z = false;
                        cwf.a((Closeable) bufferedWriter);
                    } catch (Throwable th) {
                        th = th;
                        bufferedWriter2 = bufferedWriter;
                        cwf.a((Closeable) bufferedWriter2);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    cwf.a((Closeable) bufferedWriter2);
                    throw th;
                }
            } catch (Exception e3) {
                e = e3;
                e.printStackTrace();
                this.z = false;
                cwf.a((Closeable) bufferedWriter);
            }
        }
    }
}
