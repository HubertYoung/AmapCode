package com.alipay.mobile.common.cleancache;

import android.os.Environment;
import android.os.StatFs;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import java.io.File;

public class CacheCleanerUtil {
    public static long sizeOfFile(File file) {
        try {
            if (file.isFile()) {
                return file.length();
            }
            long total = 0;
            for (File tmp : file.listFiles()) {
                total += sizeOfFile(tmp);
            }
            return total;
        } catch (Exception e) {
            return 0;
        }
    }

    public static void deleteFile(File file) {
        try {
            if (file.isFile()) {
                file.delete();
                return;
            }
            for (File deleteFile : file.listFiles()) {
                deleteFile(deleteFile);
            }
            file.delete();
        } catch (Exception e) {
        }
    }

    private static boolean isExternalStorageWritable() {
        try {
            if ("mounted".equals(Environment.getExternalStorageState())) {
                return true;
            }
            return false;
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().error((String) "CacheCleanerUtil", e);
            return true;
        }
    }

    public static long getTotalSize() {
        if (!isExternalStorageWritable()) {
            return 0;
        }
        StatFs sf = new StatFs(Environment.getExternalStorageDirectory().getPath());
        return ((long) sf.getBlockSize()) * ((long) sf.getBlockCount());
    }

    public static long getAvailableSize() {
        long j = 0;
        if (!isExternalStorageWritable()) {
            return j;
        }
        try {
            StatFs sf = new StatFs(Environment.getExternalStorageDirectory().getPath());
            return ((long) sf.getBlockSize()) * ((long) sf.getAvailableBlocks());
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().error((String) "CacheCleanerUtil", e);
            return j;
        }
    }
}
