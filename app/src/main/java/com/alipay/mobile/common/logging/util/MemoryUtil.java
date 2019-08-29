package com.alipay.mobile.common.logging.util;

import android.content.Context;
import android.os.Debug;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MemoryUtil {
    private static volatile boolean a = false;

    public static boolean isShouldDumpOOMHeap() {
        return a;
    }

    public static void setShouldDumpOOMHeap(boolean shouldDumpOOMHeap) {
        a = shouldDumpOOMHeap;
    }

    public static synchronized void dumpMemHeap(Context context) {
        synchronized (MemoryUtil.class) {
            if (!a) {
                LoggerFactory.getTraceLogger().info("MemoryUtil", "mShouldDumpOOMHeap:false, just return.");
            } else if (context == null) {
                LoggerFactory.getTraceLogger().info("MemoryUtil", "dumpMemHeap() context:null, just return.");
            } else {
                File targetDir = a(context);
                long nowTime = System.currentTimeMillis();
                File hprofFile = new File(targetDir, nowTime + "_dump.hprof");
                File zipFile = new File(targetDir, nowTime + "_dump.zip");
                clearExpiredFiles(context, true);
                try {
                    long start = System.currentTimeMillis();
                    LoggerFactory.getTraceLogger().info("MemoryUtil", "dump start time = " + start);
                    try {
                        Debug.dumpHprofData(hprofFile.getAbsolutePath());
                    } catch (IOException e) {
                        LoggerFactory.getTraceLogger().warn((String) "MemoryUtil", (Throwable) e);
                    }
                    long end = System.currentTimeMillis();
                    LoggerFactory.getTraceLogger().info("MemoryUtil", "dump end time = " + end + " use time : " + (end - start));
                    if (hprofFile.exists()) {
                        long start2 = System.currentTimeMillis();
                        LoggerFactory.getTraceLogger().info("MemoryUtil", "zip start time = " + start2);
                        List willZipFiles = new ArrayList();
                        willZipFiles.add(hprofFile);
                        ZipUtil.zipFile(willZipFiles, zipFile.getAbsolutePath(), null, null);
                        long end2 = System.currentTimeMillis();
                        LoggerFactory.getTraceLogger().info("MemoryUtil", "zip end time = " + end2 + " use time : " + (end2 - start2));
                    } else {
                        LoggerFactory.getTraceLogger().info("MemoryUtil", "dump nothing");
                    }
                    LoggerFactory.getTraceLogger().info("MemoryUtil", "hprofFile:" + hprofFile.getAbsolutePath() + " delete:" + hprofFile.delete());
                } catch (Throwable tr) {
                    LoggerFactory.getTraceLogger().warn((String) "MemoryUtil", tr);
                    LoggerFactory.getTraceLogger().info("MemoryUtil", "hprofFile:" + hprofFile.getAbsolutePath() + " delete:" + hprofFile.delete());
                    LoggerFactory.getTraceLogger().info("MemoryUtil", "hprofFile:" + zipFile.getAbsolutePath() + " delete:" + zipFile.delete());
                }
            }
        }
        return;
    }

    public static synchronized void clearExpiredFiles(Context context, boolean clearAll) {
        File file;
        boolean shouldDelete;
        synchronized (MemoryUtil.class) {
            if (context == null) {
                LoggerFactory.getTraceLogger().info("MemoryUtil", "clearExpiredFiles() context:null, just return.");
            } else {
                try {
                    File[] files = a(context).listFiles();
                    if (files != null && files.length > 0) {
                        long nowTime = System.currentTimeMillis();
                        long effectiveStartTime = nowTime - TimeUnit.DAYS.toMillis(3);
                        long effectiveEndTime = nowTime + TimeUnit.DAYS.toMillis(3);
                        int length = files.length;
                        for (int i = 0; i < length; i++) {
                            file = files[i];
                            if (file != null && file.exists() && file.isFile()) {
                                shouldDelete = clearAll;
                                long timeStamp = Long.parseLong(file.getName().split("_")[0]);
                                if (timeStamp < effectiveStartTime || timeStamp > effectiveEndTime) {
                                    shouldDelete = true;
                                }
                                if (shouldDelete) {
                                    if (file.delete()) {
                                        LoggerFactory.getTraceLogger().warn((String) "MemoryUtil", "cleanExpiresFile: " + file.getName() + " success!");
                                    } else {
                                        LoggerFactory.getTraceLogger().warn((String) "MemoryUtil", "cleanExpiresFile: " + file.getName() + " failed !");
                                    }
                                }
                            }
                        }
                    }
                } catch (Throwable tr) {
                    LoggerFactory.getTraceLogger().warn((String) "MemoryUtil", tr);
                }
            }
        }
        return;
    }

    private static File a(Context context) {
        File rDir = null;
        try {
            rDir = new File(new File(LoggingUtil.getCommonExternalStorageDir(), context.getPackageName()), "memHeaps");
        } catch (Throwable t) {
            LoggerFactory.getTraceLogger().error("MemoryUtil", "getMemHeapDir", t);
        }
        if (rDir == null) {
            return rDir;
        }
        try {
            if (rDir.exists() || rDir.mkdirs()) {
                return rDir;
            }
            return null;
        } catch (Throwable t2) {
            LoggerFactory.getTraceLogger().error("MemoryUtil", "getMemHeapDir", t2);
            return rDir;
        }
    }
}
