package com.alipay.mobile.common.utils.load;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.utils.FileUtils;
import com.alipay.mobile.quinox.utils.MonitorLogger;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class LibraryLoadUtils {
    public static final String TAG = LibraryLoadUtils.class.getSimpleName();
    private static String a = "lib";
    private static String[] b = {Build.CPU_ABI, Build.CPU_ABI2, "armeabi"};
    private static ZipFile c = null;

    public LibraryLoadUtils() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public static void loadLibrary(String library, boolean v7aOptimize, ClassLoader classLoader) {
        String finalLibraryName = library;
        if (v7aOptimize && a(getArchitecture())) {
            finalLibraryName = finalLibraryName + "-v7a";
        }
        LoggerFactory.getTraceLogger().debug(TAG, "loadLibrary:" + library + ",v7aOptimize:" + v7aOptimize + ",classLoader:" + classLoader);
        if (classLoader == null) {
            try {
                System.loadLibrary(finalLibraryName);
            } catch (Throwable exp) {
                MonitorLogger.exception((String) "load_library", e, Log.getStackTraceString(exp));
            }
        } else {
            Runtime runtime = Runtime.getRuntime();
            Method loadLibraryMethod = runtime.getClass().getDeclaredMethod("loadLibrary", new Class[]{String.class, ClassLoader.class});
            loadLibraryMethod.setAccessible(true);
            loadLibraryMethod.invoke(runtime, new Object[]{finalLibraryName, classLoader});
        }
    }

    public static void loadLibrary(String library) {
        loadLibrary(library, true, null);
    }

    public static void loadLibrary(String library, boolean v7aOptimize) {
        loadLibrary(library, v7aOptimize, null);
    }

    public static void loadLibrary(String library, ClassLoader classLoader) {
        loadLibrary(library, true, classLoader);
    }

    private static synchronized void a(Context context, String libName, File destFile) {
        synchronized (LibraryLoadUtils.class) {
            String path = context.getApplicationInfo().sourceDir;
            if (c == null) {
                c = new ZipFile(path);
            }
            LoggerFactory.getTraceLogger().debug(TAG, "extractLibFromSrcApk: " + path + ",lib:" + libName);
            InputStream in = null;
            String[] strArr = b;
            int length = strArr.length;
            for (int i = 0; i < length; i++) {
                String entryName = a + File.separator + strArr[i] + File.separator + libName;
                try {
                    ZipEntry zipEntry = c.getEntry(entryName);
                    if (zipEntry != null) {
                        in = c.getInputStream(zipEntry);
                        if (in != null) {
                            break;
                        }
                    }
                    in = LibraryLoadUtils.class.getClassLoader().getResourceAsStream(entryName);
                    if (in == null) {
                        LoggerFactory.getTraceLogger().warn(TAG, "load entry fail:" + entryName);
                    }
                } catch (IOException e) {
                    LoggerFactory.getTraceLogger().warn(TAG, "get entry fail:" + entryName);
                }
            }
            if (in == null) {
                throw new IOException("find lib entry fail");
            } else if (!FileUtils.copyToFile(in, destFile)) {
                throw new IOException("copy lib entry fail");
            }
        }
    }

    private static Context a() {
        Object context = LibraryLoadUtils.class.getClassLoader().loadClass("com.alipay.mobile.quinox.LauncherApplication").getMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
        if (context instanceof Context) {
            return (Context) context;
        }
        return null;
    }

    public static String getArchitecture() {
        String tempArchitecture = Build.CPU_ABI;
        if (tempArchitecture != null) {
            if (tempArchitecture.equalsIgnoreCase("armeabi-v7a")) {
                return "ARMv7";
            }
            return "ARM";
        } else if (Build.CPU_ABI2.equalsIgnoreCase("armeabi-v7a")) {
            return "ARMv7";
        } else {
            return "ARM";
        }
    }

    private static boolean a(String architecture) {
        if (architecture != null && architecture.equals("ARMv7")) {
            return true;
        }
        return false;
    }
}
