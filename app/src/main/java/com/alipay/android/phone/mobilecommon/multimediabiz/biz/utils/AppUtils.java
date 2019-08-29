package com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaFileService;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaMaterialService;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoUtils;
import com.alipay.mobile.base.config.ConfigService;
import com.alipay.mobile.common.logging.api.ProcessInfo;
import com.alipay.mobile.common.transport.utils.NetworkUtils;
import com.alipay.mobile.common.utils.load.LibraryLoadUtils;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.MicroApplicationContext;
import com.alipay.mobile.framework.service.common.TaskScheduleService;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import java.io.File;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class AppUtils {
    public static final String ALIPAY_WALLET_PACKG = "com.eg.android.AlipayGphone";
    private static int a = -1;
    private static int b = -1;
    private static int c = -1;
    private static Boolean d = null;
    private static Boolean e = null;
    private static AppEnum f;
    private static String g = null;
    private static final Handler h = new Handler(Looper.getMainLooper());
    private static MultimediaMaterialService i = null;
    private static Random j = new Random();
    private static final ConcurrentHashMap<String, Boolean> k = new ConcurrentHashMap<>();

    private enum AppEnum {
        UNKOWN(0),
        ALIPAY(1),
        DIPEI(2),
        WEALTH(3);
        
        private int value;

        private AppEnum(int val) {
            this.value = 0;
            this.value = val;
        }

        public final int getValue() {
            return this.value;
        }
    }

    public static Context getApplicationContext() {
        return getMicroApplicationContext().getApplicationContext();
    }

    public static MicroApplicationContext getMicroApplicationContext() {
        return LauncherApplicationAgent.getInstance().getMicroApplicationContext();
    }

    public static boolean isRC() {
        if (e == null) {
            Context context = getApplicationContext();
            if (context != null) {
                e = Boolean.valueOf(context.getPackageName().endsWith("RC"));
            }
        }
        return e.booleanValue();
    }

    public static boolean isDebug() {
        return isDebug(getApplicationContext());
    }

    public static boolean isDebug(Context context) {
        boolean z;
        try {
            if (d == null) {
                if (inMainLooper()) {
                    return false;
                }
                if ((context.getPackageManager().getApplicationInfo(context.getPackageName(), 16384).flags & 2) != 0) {
                    z = true;
                } else {
                    z = false;
                }
                d = Boolean.valueOf(z);
            }
            return d.booleanValue();
        } catch (Exception e2) {
            return false;
        }
    }

    public static String getProcessName() {
        try {
            if (!TextUtils.isEmpty(g)) {
                return g;
            }
            int pid = Process.myPid();
            for (RunningAppProcessInfo process : ((ActivityManager) getApplicationContext().getSystemService(WidgetType.ACTIVITY)).getRunningAppProcesses()) {
                if (process.pid == pid) {
                    g = process.processName;
                }
            }
            return g;
        } catch (Exception e2) {
            Logger.E((String) "AppUtils", (Throwable) e2, (String) "getProcessName exp", new Object[0]);
        }
    }

    public static boolean isInTinyProcess() {
        String currentProcessName = getProcessName();
        return !TextUtils.isEmpty(currentProcessName) && currentProcessName.contains(ProcessInfo.ALIAS_LITE);
    }

    public static Resources getResources() {
        Context context = getApplicationContext();
        if (context != null) {
            return context.getResources();
        }
        return null;
    }

    public static <T> T getService(Class<?> clazz) {
        return getMicroApplicationContext().findServiceByInterface(clazz.getName());
    }

    public static MultimediaFileService getFileService() {
        return (MultimediaFileService) getMicroApplicationContext().getExtServiceByInterface(MultimediaFileService.class.getName());
    }

    public static TaskScheduleService getTaskScheduleService() {
        return (TaskScheduleService) getMicroApplicationContext().findServiceByInterface(TaskScheduleService.class.getName());
    }

    public static MultimediaMaterialService getMaterialService() {
        if (i == null) {
            i = (MultimediaMaterialService) getMicroApplicationContext().findServiceByInterface(MultimediaMaterialService.class.getName());
        }
        return i;
    }

    public static void randomSleep(int max) {
        try {
            Thread.sleep((long) ((int) Math.min(j.nextDouble() * 500.0d, (double) max)));
        } catch (InterruptedException e2) {
        }
    }

    public static void randomSleep() {
        randomSleep(500);
    }

    public static void sleep(long sleepTime) {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e2) {
        }
    }

    public static int getMainVersion(Context context) {
        if (a >= 0) {
            return a;
        }
        int ver = 0;
        try {
            String version = getVersion(context);
            if (version.indexOf(46) > 0) {
                version = version.substring(0, version.indexOf(46));
            }
            ver = Integer.parseInt(version);
        } catch (Exception e2) {
            a = 0;
        }
        a = ver;
        Logger.I("AppUtils", "getMainVersion2: " + a, new Object[0]);
        return a;
    }

    public static int getMinorVersion(Context context) {
        if (b >= 0) {
            return b;
        }
        int ver = 0;
        try {
            String version = getVersion(context);
            if (version.indexOf(46) > 0) {
                String version2 = version.substring(version.indexOf(46) + 1);
                if (version2.indexOf(46) > 0) {
                    version2 = version2.substring(0, version2.indexOf(46));
                }
                ver = Integer.parseInt(version2);
            }
        } catch (Exception e2) {
            b = 0;
        }
        b = ver;
        Logger.I("AppUtils", "getMinorVersion2: " + b, new Object[0]);
        return b;
    }

    public static int getMinVersion(Context context) {
        if (c >= 0) {
            return c;
        }
        int ver = 0;
        try {
            String version = getVersion(context);
            if (version.contains(".")) {
                String[] vers = version.split(".");
                if (vers.length >= 3) {
                    ver = Integer.parseInt(vers[2]);
                }
            }
        } catch (Exception e2) {
            c = 0;
        }
        c = ver;
        Logger.I("AppUtils", "getMinorVersion3: " + c, new Object[0]);
        return c;
    }

    public static String getVersion(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception e2) {
            return null;
        }
    }

    public static int getHeapGrowthLimit() {
        int size;
        try {
            Class systemProperties = ReflectUtils.getClass("android.os.SystemProperties");
            String vmHeapSize = (String) ReflectUtils.invoke(systemProperties, ReflectUtils.getMethod(systemProperties, (String) "get", (Class<?>[]) new Class[]{String.class, String.class}), "dalvik.vm.heapgrowthlimit", "96m");
            size = Integer.parseInt(vmHeapSize.substring(0, vmHeapSize.length() - 1));
        } catch (Exception e2) {
            size = 96;
        }
        return size * 1024 * 1024;
    }

    public static void loadLibrary(String libName) {
        LibraryLoadUtils.loadLibrary(libName, false);
    }

    public static void loadLibraryOnce(String libName) {
        synchronized (k) {
            if (!k.containsKey(libName)) {
                k.put(libName, Boolean.valueOf(true));
                loadLibrary(libName);
            }
        }
    }

    public static boolean inMainLooper() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public static void runOnUiThread(Runnable runnable) {
        if (runnable != null) {
            if (inMainLooper()) {
                runnable.run();
                return;
            }
            for (int i2 = 3; i2 > 0 && !h.post(runnable); i2--) {
            }
        }
    }

    public static ConfigService getConfigService() {
        return (ConfigService) getMicroApplicationContext().findServiceByInterface(ConfigService.class.getName());
    }

    public static boolean localDebug(File config) {
        return isDebug(getApplicationContext()) && config != null && config.exists();
    }

    public static int getAppid() {
        if (f != null) {
            return f.getValue();
        }
        String pkgName = getApplicationContext().getPackageName();
        if ("com.eg.android.AlipayGphone".equalsIgnoreCase(pkgName)) {
            f = AppEnum.ALIPAY;
        } else if ("com.taobao.mobile.dipei".equalsIgnoreCase(pkgName)) {
            f = AppEnum.DIPEI;
        } else if ("com.antfortune.wealth".equalsIgnoreCase(pkgName)) {
            f = AppEnum.WEALTH;
        } else {
            f = AppEnum.UNKOWN;
        }
        return f.getValue();
    }

    public static byte[] getChannelBytes() {
        int netType = DjangoUtils.convertNetworkType(NetworkUtils.getNetworkType(getApplicationContext()));
        short minVer = (short) getMinVersion(getApplicationContext());
        return new byte[]{(byte) minVer, (byte) (minVer >> 4), (byte) getMinorVersion(getApplicationContext()), (byte) getMainVersion(getApplicationContext()), (byte) netType, 2};
    }

    public static ContentResolver getContentResolver() {
        return getApplicationContext().getContentResolver();
    }
}
