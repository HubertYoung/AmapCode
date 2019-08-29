package com.autonavi.minimap.offline.koala;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.StatFs;
import java.io.File;
import java.net.URI;
import java.util.Locale;

public class KoalaUtils {
    public static long getFileSize(String str) {
        File file = new File(str);
        if (file.exists()) {
            return file.length();
        }
        return 0;
    }

    public static long getFreeSpaceBytes(String str) {
        StatFs statFs = new StatFs(str);
        if (VERSION.SDK_INT >= 18) {
            return statFs.getAvailableBytes();
        }
        return ((long) statFs.getAvailableBlocks()) * ((long) statFs.getBlockSize());
    }

    public static boolean isNetworkConnected(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static String formatString(String str, Object... objArr) {
        return String.format(Locale.ENGLISH, str, objArr);
    }

    public static String getFileExt(String str) {
        try {
            String[] split = new File(URI.create(str).getPath()).getName().split("\\.");
            if (split.length <= 0) {
                return "";
            }
            StringBuilder sb = new StringBuilder(".");
            sb.append(split[split.length - 1]);
            return sb.toString();
        } catch (Throwable th) {
            th.printStackTrace();
            return "";
        }
    }

    public static String getCacheFilePath(Context context) {
        if (!"mounted".equals(Environment.getExternalStorageState())) {
            return context.getFilesDir().getAbsolutePath();
        }
        File externalFilesDir = context.getExternalFilesDir(null);
        if (externalFilesDir != null) {
            return externalFilesDir.getAbsolutePath();
        }
        return context.getFilesDir().getAbsolutePath();
    }
}
