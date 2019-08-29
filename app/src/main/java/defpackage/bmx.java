package defpackage;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Debug;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.autonavi.common.tool.CrashLogUtil;
import com.autonavi.common.tool.dumpcrash;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.zip.CRC32;

/* renamed from: bmx reason: default package */
/* compiled from: Utils */
public final class bmx {
    private static int a;

    /* renamed from: bmx$a */
    /* compiled from: Utils */
    public interface a {
        boolean a(String str);
    }

    public static boolean a(Throwable th, Class<?> cls) {
        while (!cls.isInstance(th)) {
            th = th.getCause();
            if (th == null) {
                return false;
            }
        }
        return true;
    }

    public static void a(File file, a aVar) {
        BufferedReader bufferedReader;
        String readLine;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            do {
                readLine = bufferedReader.readLine();
                if (readLine != null) {
                }
                break;
            } while (!aVar.a(readLine));
            break;
            bufferedReader.close();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public static void a() {
        try {
            Activity y = CrashLogUtil.getControler().y();
            if (y != null) {
                y.moveTaskToBack(true);
            }
        } catch (Throwable unused) {
        }
    }

    public static void b() {
        a();
        System.exit(0);
    }

    public static List<String> a(boolean z) {
        ArrayList arrayList = new ArrayList();
        try {
            File[] listFiles = new File("/proc/self/fd").listFiles();
            if (listFiles != null) {
                for (File file : listFiles) {
                    String canonicalPath = file.getCanonicalPath();
                    if (!z || canonicalPath.contains("autonavi")) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(file.getName());
                        sb.append(" -> ");
                        sb.append(canonicalPath);
                        arrayList.add(sb.toString());
                    }
                }
            }
        } catch (Throwable unused) {
        }
        return arrayList;
    }

    public static String a(String str) {
        StringBuilder sb = new StringBuilder();
        File file = new File(str);
        try {
            sb.append("APK=");
            sb.append(str);
            sb.append(" size=");
            sb.append(file.length());
            sb.append("  md5=");
            sb.append(bmq.a(file));
            sb.append(" [");
            int[] apkInfo = dumpcrash.getApkInfo(str, null);
            if (apkInfo != null) {
                boolean z = true;
                for (int i : apkInfo) {
                    if (z) {
                        z = false;
                    } else {
                        sb.append(",");
                    }
                    sb.append(a(i));
                }
            }
            sb.append("]\n");
        } catch (Throwable unused) {
        }
        return sb.toString();
    }

    public static String a(Context context) {
        StringBuilder sb = new StringBuilder();
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 64);
            if (!(packageInfo == null || packageInfo.signatures == null || packageInfo.signatures.length <= 0)) {
                for (int i = 0; i < packageInfo.signatures.length; i++) {
                    sb.append(bmq.a(packageInfo.signatures[i].toByteArray()));
                    if (packageInfo.signatures.length - i > 1) {
                        sb.append(";");
                    }
                }
            }
        } catch (Throwable unused) {
        }
        return sb.toString();
    }

    public static void a(File file) {
        if (file != null) {
            File[] listFiles = file.listFiles();
            if (file.isDirectory() && listFiles != null) {
                for (File a2 : listFiles) {
                    a(a2);
                }
            }
            file.delete();
        }
    }

    private static int c() {
        int i = 1;
        try {
            if (a != 0) {
                return a;
            }
            File file = new File("/sys/devices/system/cpu");
            if (file.exists()) {
                if (file.isDirectory()) {
                    String[] list = file.list(new FilenameFilter() {
                        public final boolean accept(File file, String str) {
                            if (TextUtils.isEmpty(str)) {
                                return false;
                            }
                            return Pattern.matches("cpu\\d+", str);
                        }
                    });
                    if (list != null) {
                        if (list.length != 0) {
                            a = list.length;
                            if (a > 0) {
                                i = a;
                            }
                            a = i;
                            return i;
                        }
                    }
                    a = 1;
                    return 1;
                }
            }
            a = 1;
            return 1;
        } catch (Throwable unused) {
        }
    }

    public static <T> List<Future<T>> a(List<Callable<T>> list, long j, int i) {
        if (i <= 0) {
            i = 0;
        }
        if (i <= 0) {
            i = c() + 1;
        }
        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(i, new ThreadFactory() {
            int a = 1;

            public final Thread newThread(Runnable runnable) {
                StringBuilder sb = new StringBuilder("DC-SOINF#");
                int i = this.a;
                this.a = i + 1;
                sb.append(i);
                return new Thread(runnable, sb.toString());
            }
        });
        if (j <= 0) {
            return newFixedThreadPool.invokeAll(list);
        }
        try {
            return newFixedThreadPool.invokeAll(list, j, TimeUnit.MILLISECONDS);
        } catch (Throwable th) {
            th.printStackTrace();
            newFixedThreadPool.shutdownNow();
            return null;
        }
    }

    public static int b(File file) {
        try {
            CRC32 crc32 = new CRC32();
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] bArr = new byte[1024];
            while (true) {
                int read = fileInputStream.read(bArr);
                if (read != -1) {
                    crc32.update(bArr, 0, read);
                } else {
                    fileInputStream.close();
                    return (int) crc32.getValue();
                }
            }
        } catch (Exception unused) {
            return 0;
        }
    }

    public static String a(int i) {
        return String.format("0x%08X", new Object[]{Integer.valueOf(i)});
    }

    public static boolean a(Application application) {
        if ((Build.FINGERPRINT.startsWith("generic") || Build.FINGERPRINT.toLowerCase().contains("vbox") || Build.FINGERPRINT.toLowerCase().contains("test-keys") || Build.MODEL.contains("google_sdk") || Build.MODEL.contains("Emulator") || Build.SERIAL.equalsIgnoreCase("unknown") || Build.SERIAL.equalsIgnoreCase("android") || Build.MODEL.contains("Android SDK built for x86") || Build.MANUFACTURER.contains("Genymotion") || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")) || "google_sdk".equals(Build.PRODUCT)) || Debug.isDebuggerConnected()) {
            return true;
        }
        String str = "";
        TelephonyManager telephonyManager = (TelephonyManager) application.getSystemService("phone");
        if (telephonyManager != null) {
            String networkOperatorName = telephonyManager.getNetworkOperatorName();
            if (networkOperatorName != null) {
                str = networkOperatorName;
            }
        }
        if (str.toLowerCase().equals("android")) {
            return true;
        }
        Intent intent = new Intent();
        intent.setData(Uri.parse("tel:123456"));
        intent.setAction("android.intent.action.DIAL");
        return intent.resolveActivity(application.getPackageManager()) != null;
    }

    public static String a(Throwable th) {
        StackTraceElement[] stackTrace;
        StringBuilder sb = new StringBuilder();
        if (th.getStackTrace().length > 0) {
            if (th.getLocalizedMessage() != null) {
                return th.getLocalizedMessage();
            }
            if (th.getMessage() != null) {
                return th.getMessage();
            }
            for (StackTraceElement stackTraceElement : th.getStackTrace()) {
                sb.append("[");
                sb.append(stackTraceElement.getLineNumber());
                sb.append("]");
                sb.append(stackTraceElement.getClassName());
                sb.append(":");
                sb.append(stackTraceElement.getMethodName());
                sb.append("###");
            }
        }
        return sb.toString();
    }
}
