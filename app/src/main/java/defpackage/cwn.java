package defpackage;

import android.os.Process;
import android.system.OsConstants;
import com.autonavi.minimap.bundle.apm.data.DeviceInfoManager.a;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import tv.danmaku.ijk.media.encode.FFmpegSessionConfig;

/* renamed from: cwn reason: default package */
/* compiled from: CpuTracker */
public final class cwn {
    public static volatile cvm a;
    static final int[] b = {288, 8224, 8224, 8224, 8224, 8224, 8224, 8224};
    static final int[] c = {32, FFmpegSessionConfig.VIDEO_SOFTENCODE_W, 32, 32, 32, 32, 32, 32, 32, 8224, 32, 8224, 32, 8224, 8224, 32, 32, 32, 32, 8224, 32, 8224, 32};
    static long d;
    static long e;
    static long f;
    public static long g;
    static long h;
    static long i;
    static long j;
    static long k;
    static long l;
    static long m;
    static int n;
    static String o;
    static Method p;

    static {
        try {
            int myPid = Process.myPid();
            StringBuilder sb = new StringBuilder("/proc/");
            sb.append(myPid);
            sb.append("/stat");
            o = sb.toString();
            Method method = Process.class.getMethod("readProcFile", new Class[]{String.class, int[].class, String[].class, long[].class, float[].class});
            p = method;
            method.setAccessible(true);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            if (a.a.s.intValue() < 23 || n != 0) {
                n = 10;
            } else {
                Class<?> cls = Class.forName("libcore.io.Libcore");
                Class<?> cls2 = Class.forName("libcore.io.Os");
                Field declaredField = cls.getDeclaredField("os");
                declaredField.setAccessible(true);
                Object obj = declaredField.get(cls);
                Method method2 = cls2.getMethod("sysconf", new Class[]{Integer.TYPE});
                method2.setAccessible(true);
                n = (int) (1000 / ((Long) method2.invoke(obj, new Object[]{Integer.valueOf(OsConstants._SC_CLK_TCK)})).longValue());
            }
            if (n == 0) {
                n = 10;
            }
        } catch (Exception e3) {
            e3.printStackTrace();
            n = 10;
        }
    }

    public static synchronized cvm a() {
        cvm cvm;
        cvm cvm2;
        int i2;
        int i3;
        long j2;
        long j3;
        synchronized (cwn.class) {
            try {
                e = new cvm();
                e.a = cwm.a();
                if (p != null) {
                    if (o != null) {
                        long[] jArr = new long[7];
                        long[] jArr2 = new long[6];
                        boolean booleanValue = ((Boolean) p.invoke(null, new Object[]{"/proc/stat", b, 0, jArr, 0})).booleanValue();
                        if (booleanValue) {
                            booleanValue = ((Boolean) p.invoke(null, new Object[]{o, c, 0, jArr2, 0})).booleanValue();
                        }
                        if (booleanValue) {
                            long j4 = jArr2[2];
                            long j5 = jArr2[3];
                            f = jArr2[4];
                            g = jArr2[5] * ((long) n);
                            if (j4 < d || j5 < e) {
                                i3 = 0;
                                i2 = 0;
                            } else {
                                i2 = (int) (j4 - d);
                                i3 = (int) (j5 - e);
                            }
                            long j6 = jArr[0] + jArr[1];
                            long j7 = jArr[2];
                            long j8 = jArr[3];
                            long j9 = jArr[4];
                            long j10 = jArr[5];
                            long j11 = jArr[6];
                            if (j6 < h || j7 < i || j9 < j || j10 < k || j11 < l || j8 < m) {
                                cvm2 = e;
                                j3 = j6;
                                j2 = j9;
                                cvm2.b = 0;
                                cvm2.c = 0;
                            } else {
                                cvm cvm3 = e;
                                j3 = j6;
                                j2 = j9;
                                int i4 = ((int) (j6 - h)) + ((int) (j7 - i));
                                long j12 = (long) (((int) (j9 - j)) + i4 + ((int) (j10 - k)) + ((int) (j11 - l)) + ((int) (j8 - m)));
                                if (j12 > 1) {
                                    cvm2 = cvm3;
                                    cvm2.b = (short) ((int) (((long) ((i2 + i3) * 100)) / j12));
                                    cvm2.c = (short) ((int) (((long) (i4 * 100)) / j12));
                                    if (cvm2.c > 100) {
                                        cvm2.c = 100;
                                    }
                                    if (cvm2.b > cvm2.c) {
                                        cvm2.b = cvm2.c;
                                    }
                                } else {
                                    cvm2 = cvm3;
                                }
                            }
                            d = j4;
                            e = j5;
                            h = j3;
                            i = j7;
                            j = j2;
                            k = j10;
                            l = j11;
                            m = j8;
                        }
                        a = cvm2;
                        return cvm2;
                    }
                }
                StringBuilder sb = new StringBuilder("readProcFile : ");
                sb.append(p);
                sb.append(", mPidStatFile : ");
                sb.append(o);
                a = cvm;
                return cvm;
            } catch (Exception e2) {
                cvm cvm4 = e2;
                e2.printStackTrace();
                a = cvm4;
                return cvm4;
            } finally {
                cvm = e2;
            }
        }
    }
}
