package defpackage;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.alipay.mobile.beehive.photo.util.DiskFormatter;
import com.amap.bundle.blutils.PathManager;
import com.amap.bundle.blutils.PathManager.DirType;
import com.amap.bundle.network.request.param.NetworkParam;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.R;
import com.autonavi.minimap.map.DPoint;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.server.aos.serverkey;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* renamed from: dhd reason: default package */
/* compiled from: OfflineUtil */
public final class dhd {
    public static final String a = Build.MANUFACTURER;
    public static final String b = Build.MODEL;
    private static dhf c = ((dhf) ank.a(dhf.class));
    private static String d = null;
    private static Lock e = new ReentrantLock();
    private static boolean f = false;
    private static boolean g = false;

    public static long a(long j, long j2) {
        return j <= j2 ? j : j2;
    }

    public static String a() {
        if (!TextUtils.isEmpty(d)) {
            return d;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("diu:");
        stringBuffer.append(NetworkParam.getDiu());
        stringBuffer.append(";");
        stringBuffer.append("adiu:");
        stringBuffer.append(NetworkParam.getAdiu());
        stringBuffer.append(";");
        stringBuffer.append("tid:");
        stringBuffer.append(NetworkParam.getTaobaoID());
        stringBuffer.append(";");
        stringBuffer.append("div:");
        stringBuffer.append(NetworkParam.getDiv());
        stringBuffer.append(";");
        stringBuffer.append("dibv:");
        stringBuffer.append(NetworkParam.getDibv());
        stringBuffer.append(";");
        stringBuffer.append("dic:");
        stringBuffer.append(NetworkParam.getDic());
        stringBuffer.append(";");
        stringBuffer.append("lon:");
        stringBuffer.append(LocationInstrument.getInstance().getLatestPosition().getLongitude());
        stringBuffer.append(";");
        stringBuffer.append("lat:");
        stringBuffer.append(LocationInstrument.getInstance().getLatestPosition().getLatitude());
        stringBuffer.append(";");
        stringBuffer.append("manufacture:");
        stringBuffer.append(a);
        stringBuffer.append(";");
        stringBuffer.append("networktype:");
        stringBuffer.append(a(b()) ? "Wifi" : "Mobile");
        stringBuffer.append(";");
        stringBuffer.append("model:");
        stringBuffer.append(b);
        stringBuffer.append(";");
        String amapEncode = serverkey.amapEncode(stringBuffer.toString());
        d = amapEncode;
        return amapEncode;
    }

    public static String a(int i) {
        if (c != null) {
            return c.c().getString(i);
        }
        return null;
    }

    public static float a(float f2) {
        float f3 = (f2 / 1024.0f) / 1024.0f;
        if (f3 == 0.0f) {
            f3 = 0.01f;
        }
        float round = ((float) Math.round(f3 * 100.0f)) / 100.0f;
        if (Float.compare(round, 0.1f) < 0) {
            return 0.1f;
        }
        return round;
    }

    public static Context b() {
        if (c != null) {
            return c.c().getApplicationContext();
        }
        return AMapAppGlobal.getApplication().getApplicationContext();
    }

    public static boolean a(Context context) {
        NetworkInfo d2 = d(context);
        if (d2 == null || !d2.isConnected() || d2.getType() != 0) {
            return false;
        }
        return true;
    }

    public static boolean b(Context context) {
        NetworkInfo d2 = d(context);
        if (d2 == null || !d2.isConnected() || d2.getType() != 1) {
            return false;
        }
        return true;
    }

    private static NetworkInfo d(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager == null) {
                return null;
            }
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                return activeNetworkInfo;
            }
            NetworkInfo[] allNetworkInfo = connectivityManager.getAllNetworkInfo();
            if (allNetworkInfo == null) {
                return null;
            }
            int i = 0;
            while (true) {
                if (i < allNetworkInfo.length) {
                    if (allNetworkInfo[i] != null && allNetworkInfo[i].isConnected()) {
                        activeNetworkInfo = allNetworkInfo[i];
                        break;
                    }
                    i++;
                } else {
                    break;
                }
            }
            return activeNetworkInfo;
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static boolean c(Context context) {
        NetworkInfo d2 = d(context);
        return d2 != null && d2.isConnected();
    }

    public static double[] c() {
        double[] dArr = {0.0d, 0.0d};
        GeoPoint b2 = (c == null || c.a() == null) ? null : c.b();
        if (b2 != null) {
            DPoint a2 = cfg.a((long) b2.x, (long) b2.y);
            dArr[0] = a2.x;
            dArr[1] = a2.y;
        }
        return dArr;
    }

    public static String[] d() {
        if (c == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        StringBuilder sb3 = new StringBuilder();
        a[] b2 = kx.b(c.c());
        for (int i = 0; i <= 0; i++) {
            a aVar = b2[0];
            if (aVar != null) {
                String str = "";
                switch (aVar.c) {
                    case 0:
                        str = "";
                        break;
                    case 1:
                        str = "cu";
                        break;
                    case 2:
                        str = LogItem.MM_C43_K4_CAMERA_TIME;
                        break;
                    case 3:
                        str = "cm";
                        break;
                }
                sb.append(str);
                sb2.append(aVar.a);
                sb3.append(aVar.b);
            }
        }
        return new String[]{sb.toString(), sb2.toString(), sb3.toString()};
    }

    public static String a(long j) {
        DecimalFormat decimalFormat = new DecimalFormat(DiskFormatter.FORMAT);
        long j2 = (j / 1024) / 1024;
        if (j2 >= 1024) {
            StringBuilder sb = new StringBuilder();
            sb.append(j2 / 1024);
            sb.append(decimalFormat.format((double) ((((float) j2) % 1024.0f) / 1024.0f)));
            sb.append(a(R.string.offline_storage_unit_gb));
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(j2);
        sb2.append(a(R.string.offline_storage_unit_mb));
        return sb2.toString();
    }

    public static String a(int i, Object... objArr) {
        if (c != null) {
            return c.c().getString(i, objArr);
        }
        return null;
    }

    public static long e() {
        return ahh.c(new SimpleDateFormat("yyyyMMdd", Locale.US).format(new Date(System.currentTimeMillis())));
    }

    public static boolean a(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        File file = new File(str);
        if (!file.exists() || !file.isDirectory() || !file.canWrite() || !file.canRead()) {
            return false;
        }
        return true;
    }

    public static int g() {
        return ahh.b(new SimpleDateFormat("yyyyMMdd", Locale.US).format(Calendar.getInstance().getTime()));
    }

    public static double a(double d2) {
        try {
            return ahh.a(new DecimalFormat("#.#").format(d2));
        } catch (Throwable th) {
            th.printStackTrace();
            return 0.0d;
        }
    }

    public static long h() {
        try {
            StatFs statFs = new StatFs(PathManager.a().b(DirType.DRIVE_VOICE));
            if (VERSION.SDK_INT >= 18) {
                return (statFs.getAvailableBytes() / 1024) / 1024;
            }
            return ((((long) statFs.getAvailableBlocks()) * ((long) statFs.getBlockSize())) / 1024) / 1024;
        } catch (Throwable th) {
            th.printStackTrace();
            return 0;
        }
    }

    public static void i() {
        try {
            Field declaredField = Class.forName("android.text.TextLine").getDeclaredField("sCached");
            if (!declaredField.isAccessible()) {
                declaredField.setAccessible(true);
            }
            Object obj = declaredField.get(null);
            if (obj != null) {
                int length = Array.getLength(obj);
                for (int i = 0; i < length; i++) {
                    Array.set(obj, i, null);
                }
            }
        } catch (Exception e2) {
            StringBuilder sb = new StringBuilder("clearTextLineLeak(): ");
            sb.append(Log.getStackTraceString(e2));
            dhb.a("OfflineUtil", sb.toString());
        }
    }

    public static boolean a(String str, String str2) {
        return a(new File(str), new File(str2));
    }

    private static boolean a(File file, File file2) {
        FileOutputStream fileOutputStream;
        if (!file.exists() || !file.isFile() || !file.canRead()) {
            StringBuilder sb = new StringBuilder("[");
            sb.append("OfflineUtil");
            sb.append("] ");
            sb.append("copyDatabase sourceFile may be not existed.");
            return false;
        }
        if ((!file2.exists() || !file2.isFile()) && ((!file2.getParentFile().exists() || !file2.getParentFile().isDirectory()) && file2.getParentFile().mkdirs())) {
            dhb.a("OfflineUtil", "copyDatabase create target dirs success.");
        }
        e.lock();
        FileInputStream fileInputStream = null;
        try {
            FileInputStream fileInputStream2 = new FileInputStream(file);
            try {
                fileOutputStream = new FileOutputStream(file2);
                try {
                    ahe.a(fileInputStream2, fileOutputStream);
                    e.unlock();
                    ahe.a((Closeable) fileInputStream2);
                    ahe.a((Closeable) fileOutputStream);
                    return true;
                } catch (Exception e2) {
                    e = e2;
                    fileInputStream = fileInputStream2;
                    try {
                        e.printStackTrace();
                        e.unlock();
                        ahe.a((Closeable) fileInputStream);
                        ahe.a((Closeable) fileOutputStream);
                        return false;
                    } catch (Throwable th) {
                        th = th;
                        e.unlock();
                        ahe.a((Closeable) fileInputStream);
                        ahe.a((Closeable) fileOutputStream);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    fileInputStream = fileInputStream2;
                    e.unlock();
                    ahe.a((Closeable) fileInputStream);
                    ahe.a((Closeable) fileOutputStream);
                    throw th;
                }
            } catch (Exception e3) {
                e = e3;
                fileOutputStream = null;
                fileInputStream = fileInputStream2;
                e.printStackTrace();
                e.unlock();
                ahe.a((Closeable) fileInputStream);
                ahe.a((Closeable) fileOutputStream);
                return false;
            } catch (Throwable th3) {
                th = th3;
                fileOutputStream = null;
                fileInputStream = fileInputStream2;
                e.unlock();
                ahe.a((Closeable) fileInputStream);
                ahe.a((Closeable) fileOutputStream);
                throw th;
            }
        } catch (Exception e4) {
            e = e4;
            fileOutputStream = null;
            e.printStackTrace();
            e.unlock();
            ahe.a((Closeable) fileInputStream);
            ahe.a((Closeable) fileOutputStream);
            return false;
        } catch (Throwable th4) {
            th = th4;
            fileOutputStream = null;
            e.unlock();
            ahe.a((Closeable) fileInputStream);
            ahe.a((Closeable) fileOutputStream);
            throw th;
        }
    }

    public static String a(File file) {
        if (c != null) {
            return c.a(file);
        }
        return null;
    }

    public static String b(String str) {
        if (c != null) {
            return c.b(str);
        }
        return null;
    }

    public static String f() {
        if (c == null) {
            return null;
        }
        Application c2 = c.c();
        if (VERSION.SDK_INT >= 17) {
            StringBuilder sb = new StringBuilder();
            sb.append(c2.getApplicationInfo().dataDir);
            sb.append("/databases/");
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder("/data/data/");
        sb2.append(c2.getPackageName());
        sb2.append("/databases/");
        return sb2.toString();
    }
}
