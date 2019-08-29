package com.alipay.mobile.common.logging.util;

import android.content.Context;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;

public class LogcatUtil {
    public static void dumpLogcatForException(Context context) {
        try {
            File logFileDir = b(context);
            if (logFileDir != null) {
                a(logFileDir);
                dumpLogcat(new File(logFileDir, System.currentTimeMillis() + "_logcat"), 3000);
                a(context);
            }
        } catch (Throwable tr) {
            LoggerFactory.getTraceLogger().warn("LogcatUtil", "dumpLogcatForException", tr);
        }
    }

    private static void a(File targetDir) {
        b(targetDir);
        c(targetDir);
        d(targetDir);
    }

    private static void b(File targetDir) {
        File[] files = targetDir.listFiles();
        if (files != null && files.length > 0) {
            long nowTime = System.currentTimeMillis();
            long effectiveStartTime = nowTime - TimeUnit.DAYS.toMillis(7);
            long effectiveEndTime = nowTime + TimeUnit.DAYS.toMillis(7);
            for (File file : files) {
                if (file != null && file.exists() && file.isFile()) {
                    try {
                        long timeStamp = Long.parseLong(file.getName().split("_")[0]);
                        if (timeStamp < effectiveStartTime || timeStamp > effectiveEndTime) {
                            if (file.delete()) {
                                LoggerFactory.getTraceLogger().warn((String) "LogcatUtil", "cleanExpiresFile: " + file.getName() + " is too old !");
                            } else {
                                LoggerFactory.getTraceLogger().warn((String) "LogcatUtil", "cleanExpiresFile: " + file.getName() + " failed !");
                            }
                        }
                    } catch (Throwable e) {
                        LoggerFactory.getTraceLogger().warn("LogcatUtil", file.getName(), e);
                    }
                }
            }
        }
    }

    private static void c(File targetDir) {
        targetDir.listFiles();
        Comparator cleanFileComparator = new Comparator<File>() {
            public final int compare(File o1, File o2) {
                return o1.getName().compareTo(o2.getName());
            }
        };
        File[] files = targetDir.listFiles();
        if (files != null && files.length >= 5) {
            Arrays.sort(files, cleanFileComparator);
            for (int i = 0; i < files.length - 4; i++) {
                File file = files[i];
                if (file != null && file.exists() && file.isFile()) {
                    if (file.delete()) {
                        LoggerFactory.getTraceLogger().warn((String) "LogcatUtil", "cleanExpiresFile: " + file.getName() + " is over 5 !");
                    } else {
                        LoggerFactory.getTraceLogger().warn((String) "LogcatUtil", "cleanExpiresFile: " + file.getName() + " failed !");
                    }
                }
            }
        }
    }

    private static void d(File targetDir) {
        targetDir.listFiles();
        Comparator cleanFileComparator = new Comparator<File>() {
            public final int compare(File o1, File o2) {
                return o1.getName().compareTo(o2.getName());
            }
        };
        long totalSize = FileUtil.getFolderSize(targetDir);
        if (totalSize >= 2097152) {
            File[] files = targetDir.listFiles();
            if (files != null && files.length > 0) {
                long sizeToRemove = totalSize - 1048576;
                Arrays.sort(files, cleanFileComparator);
                for (File file : files) {
                    if (file != null && file.exists() && file.isFile()) {
                        long fileLength = file.length();
                        if (file.delete()) {
                            sizeToRemove -= fileLength;
                            LoggerFactory.getTraceLogger().warn((String) "LogcatUtil", "cleanExpiresFile: " + file.getName() + " is too large !");
                        } else {
                            LoggerFactory.getTraceLogger().warn((String) "LogcatUtil", "cleanExpiresFile: " + file.getName() + " failed !");
                        }
                        if (sizeToRemove <= 0) {
                            return;
                        }
                    }
                }
            }
        }
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void a(android.content.Context r12) {
        /*
            java.io.File r3 = b(r12)     // Catch:{ Throwable -> 0x009d }
            java.io.File r2 = c(r12)     // Catch:{ Throwable -> 0x009d }
            if (r3 == 0) goto L_0x000c
            if (r2 != 0) goto L_0x000d
        L_0x000c:
            return
        L_0x000d:
            a(r2)     // Catch:{ Throwable -> 0x009d }
            java.io.File[] r6 = r3.listFiles()     // Catch:{ Throwable -> 0x009d }
            int r7 = r6.length     // Catch:{ Throwable -> 0x009d }
            r5 = 0
        L_0x0016:
            if (r5 >= r7) goto L_0x000c
            r1 = r6[r5]     // Catch:{ Throwable -> 0x009d }
            if (r1 == 0) goto L_0x0059
            boolean r8 = r1.exists()     // Catch:{ Throwable -> 0x009d }
            if (r8 == 0) goto L_0x0059
            boolean r8 = r1.isFile()     // Catch:{ Throwable -> 0x009d }
            if (r8 == 0) goto L_0x0059
            long r8 = r1.length()     // Catch:{ Throwable -> 0x009d }
            r10 = 0
            int r8 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r8 == 0) goto L_0x0059
            java.io.File r0 = new java.io.File     // Catch:{ Throwable -> 0x009d }
            java.lang.String r8 = r1.getName()     // Catch:{ Throwable -> 0x009d }
            r0.<init>(r2, r8)     // Catch:{ Throwable -> 0x009d }
            boolean r8 = r0.isDirectory()     // Catch:{ Throwable -> 0x009d }
            if (r8 == 0) goto L_0x005c
            com.alipay.mobile.common.logging.api.trace.TraceLogger r8 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x009d }
            java.lang.String r9 = "LogcatUtil"
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x009d }
            java.lang.String r11 = "backupLogcatFiles, bakFile should not be directory: "
            r10.<init>(r11)     // Catch:{ Throwable -> 0x009d }
            java.lang.StringBuilder r10 = r10.append(r0)     // Catch:{ Throwable -> 0x009d }
            java.lang.String r10 = r10.toString()     // Catch:{ Throwable -> 0x009d }
            r8.error(r9, r10)     // Catch:{ Throwable -> 0x009d }
        L_0x0059:
            int r5 = r5 + 1
            goto L_0x0016
        L_0x005c:
            boolean r8 = r0.exists()     // Catch:{ Throwable -> 0x009d }
            if (r8 == 0) goto L_0x0074
            boolean r8 = r0.isFile()     // Catch:{ Throwable -> 0x009d }
            if (r8 == 0) goto L_0x0074
            long r8 = r0.length()     // Catch:{ Throwable -> 0x009d }
            long r10 = r1.length()     // Catch:{ Throwable -> 0x009d }
            int r8 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r8 == 0) goto L_0x0059
        L_0x0074:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r8 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x009d }
            java.lang.String r9 = "LogcatUtil"
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x009d }
            java.lang.String r11 = "backupLogcatFiles: "
            r10.<init>(r11)     // Catch:{ Throwable -> 0x009d }
            java.lang.StringBuilder r10 = r10.append(r1)     // Catch:{ Throwable -> 0x009d }
            java.lang.String r10 = r10.toString()     // Catch:{ Throwable -> 0x009d }
            r8.info(r9, r10)     // Catch:{ Throwable -> 0x009d }
            com.alipay.mobile.common.logging.util.FileUtil.copyFile(r1, r0)     // Catch:{ Throwable -> 0x0090 }
            goto L_0x0059
        L_0x0090:
            r4 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r8 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x009d }
            java.lang.String r9 = "LogcatUtil"
            java.lang.String r10 = "backupLogcatFiles: copyFile"
            r8.error(r9, r10, r4)     // Catch:{ Throwable -> 0x009d }
            goto L_0x0059
        L_0x009d:
            r4 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r5 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r6 = "LogcatUtil"
            java.lang.String r7 = "backupOtherFiles"
            r5.error(r6, r7, r4)
            goto L_0x000c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.logging.util.LogcatUtil.a(android.content.Context):void");
    }

    private static File b(Context context) {
        File rFile = new File(context.getFilesDir(), "logcat");
        if (rFile.exists() || rFile.mkdirs()) {
            return rFile;
        }
        return null;
    }

    private static File c(Context context) {
        File rDir = null;
        try {
            rDir = new File(new File(LoggingUtil.getCommonExternalStorageDir(), context.getPackageName()), "logcatic");
        } catch (Throwable t) {
            LoggerFactory.getTraceLogger().error("LogcatUtil", "getBackupLogcatsDir", t);
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
            LoggerFactory.getTraceLogger().error("LogcatUtil", "getBackupLogcatsDir", t2);
            return rDir;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00b7, code lost:
        r3 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00b8, code lost:
        r4 = r5;
        r0 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:?, code lost:
        r4.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:?, code lost:
        r7.destroy();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x015f, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x0160, code lost:
        com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger().warn("LogcatUtil", "close fileWriter", r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x016c, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x016d, code lost:
        com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger().warn("LogcatUtil", "close logcatProc", r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x0179, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x017a, code lost:
        com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger().warn("LogcatUtil", "close bufferedReader", r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x0189, code lost:
        r9 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x018a, code lost:
        r4 = r5;
        r0 = r1;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00c7 A[SYNTHETIC, Splitter:B:37:0x00c7] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00cc A[SYNTHETIC, Splitter:B:40:0x00cc] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00d1 A[SYNTHETIC, Splitter:B:43:0x00d1] */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x0151 A[SYNTHETIC, Splitter:B:68:0x0151] */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x0156 A[SYNTHETIC, Splitter:B:71:0x0156] */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x015b A[SYNTHETIC, Splitter:B:74:0x015b] */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x0189 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:23:0x008b] */
    /* JADX WARNING: Removed duplicated region for block: B:95:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void dumpLogcat(java.io.File r13, int r14) {
        /*
            if (r14 > 0) goto L_0x0004
            r14 = 3000(0xbb8, float:4.204E-42)
        L_0x0004:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r9 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r10 = "LogcatUtil"
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            java.lang.String r12 = "start logcatDump for "
            r11.<init>(r12)
            java.lang.StringBuilder r11 = r11.append(r14)
            java.lang.String r12 = " lines."
            java.lang.StringBuilder r11 = r11.append(r12)
            java.lang.String r11 = r11.toString()
            r9.info(r10, r11)
            if (r13 == 0) goto L_0x002a
            boolean r9 = r13.isDirectory()
            if (r9 == 0) goto L_0x0036
        L_0x002a:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r9 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r10 = "LogcatUtil"
            java.lang.String r11 = "targetFile is null or dir."
            r9.info(r10, r11)
        L_0x0035:
            return
        L_0x0036:
            boolean r9 = r13.exists()
            if (r9 == 0) goto L_0x003f
            com.alipay.mobile.common.logging.util.FileUtil.deleteFileNotDir(r13)
        L_0x003f:
            java.io.File r8 = r13.getParentFile()
            if (r8 == 0) goto L_0x005d
            boolean r9 = r8.exists()
            if (r9 != 0) goto L_0x005d
            boolean r9 = r8.mkdirs()
            if (r9 != 0) goto L_0x005d
            com.alipay.mobile.common.logging.api.trace.TraceLogger r9 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r10 = "LogcatUtil"
            java.lang.String r11 = "targetFile's parent make failed."
            r9.info(r10, r11)
            goto L_0x0035
        L_0x005d:
            r7 = 0
            r0 = 0
            r4 = 0
            java.lang.Runtime r9 = java.lang.Runtime.getRuntime()     // Catch:{ Throwable -> 0x018d }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x018d }
            java.lang.String r11 = "logcat -v time -d -t "
            r10.<init>(r11)     // Catch:{ Throwable -> 0x018d }
            java.lang.StringBuilder r10 = r10.append(r14)     // Catch:{ Throwable -> 0x018d }
            java.lang.String r10 = r10.toString()     // Catch:{ Throwable -> 0x018d }
            java.lang.Process r7 = r9.exec(r10)     // Catch:{ Throwable -> 0x018d }
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x018d }
            java.io.InputStreamReader r9 = new java.io.InputStreamReader     // Catch:{ Throwable -> 0x018d }
            java.io.InputStream r10 = r7.getInputStream()     // Catch:{ Throwable -> 0x018d }
            r9.<init>(r10)     // Catch:{ Throwable -> 0x018d }
            r1.<init>(r9)     // Catch:{ Throwable -> 0x018d }
            java.io.FileWriter r5 = new java.io.FileWriter     // Catch:{ Throwable -> 0x0190, all -> 0x0186 }
            r5.<init>(r13)     // Catch:{ Throwable -> 0x0190, all -> 0x0186 }
            r2 = 0
        L_0x008b:
            java.lang.String r6 = r1.readLine()     // Catch:{ Throwable -> 0x00b7, all -> 0x0189 }
            if (r6 == 0) goto L_0x00e4
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00aa, all -> 0x0189 }
            r9.<init>()     // Catch:{ Throwable -> 0x00aa, all -> 0x0189 }
            java.lang.StringBuilder r9 = r9.append(r6)     // Catch:{ Throwable -> 0x00aa, all -> 0x0189 }
            java.lang.String r10 = "\n"
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ Throwable -> 0x00aa, all -> 0x0189 }
            java.lang.String r9 = r9.toString()     // Catch:{ Throwable -> 0x00aa, all -> 0x0189 }
            r5.write(r9)     // Catch:{ Throwable -> 0x00aa, all -> 0x0189 }
            int r2 = r2 + 1
            goto L_0x008b
        L_0x00aa:
            r3 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r9 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x00b7, all -> 0x0189 }
            java.lang.String r10 = "LogcatUtil"
            java.lang.String r11 = "skip"
            r9.warn(r10, r11, r3)     // Catch:{ Throwable -> 0x00b7, all -> 0x0189 }
            goto L_0x008b
        L_0x00b7:
            r3 = move-exception
            r4 = r5
            r0 = r1
        L_0x00ba:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r9 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x014e }
            java.lang.String r10 = "LogcatUtil"
            java.lang.String r11 = "dumpLog"
            r9.error(r10, r11, r3)     // Catch:{ all -> 0x014e }
            if (r4 == 0) goto L_0x00ca
            r4.close()     // Catch:{ Throwable -> 0x0134 }
        L_0x00ca:
            if (r7 == 0) goto L_0x00cf
            r7.destroy()     // Catch:{ Throwable -> 0x0141 }
        L_0x00cf:
            if (r0 == 0) goto L_0x0035
            r0.close()     // Catch:{ Throwable -> 0x00d6 }
            goto L_0x0035
        L_0x00d6:
            r3 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r9 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r10 = "LogcatUtil"
            java.lang.String r11 = "close bufferedReader"
            r9.warn(r10, r11, r3)
            goto L_0x0035
        L_0x00e4:
            r5.flush()     // Catch:{ Throwable -> 0x00b7, all -> 0x0189 }
            com.alipay.mobile.common.logging.api.trace.TraceLogger r9 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x00b7, all -> 0x0189 }
            java.lang.String r10 = "LogcatUtil"
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00b7, all -> 0x0189 }
            java.lang.String r12 = "end logcatDump: "
            r11.<init>(r12)     // Catch:{ Throwable -> 0x00b7, all -> 0x0189 }
            java.lang.StringBuilder r11 = r11.append(r2)     // Catch:{ Throwable -> 0x00b7, all -> 0x0189 }
            java.lang.String r11 = r11.toString()     // Catch:{ Throwable -> 0x00b7, all -> 0x0189 }
            r9.info(r10, r11)     // Catch:{ Throwable -> 0x00b7, all -> 0x0189 }
            r5.close()     // Catch:{ Throwable -> 0x011a }
        L_0x0102:
            if (r7 == 0) goto L_0x0107
            r7.destroy()     // Catch:{ Throwable -> 0x0127 }
        L_0x0107:
            r1.close()     // Catch:{ Throwable -> 0x010c }
            goto L_0x0035
        L_0x010c:
            r3 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r9 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r10 = "LogcatUtil"
            java.lang.String r11 = "close bufferedReader"
            r9.warn(r10, r11, r3)
            goto L_0x0035
        L_0x011a:
            r3 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r9 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r10 = "LogcatUtil"
            java.lang.String r11 = "close fileWriter"
            r9.warn(r10, r11, r3)
            goto L_0x0102
        L_0x0127:
            r3 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r9 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r10 = "LogcatUtil"
            java.lang.String r11 = "close logcatProc"
            r9.warn(r10, r11, r3)
            goto L_0x0107
        L_0x0134:
            r3 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r9 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r10 = "LogcatUtil"
            java.lang.String r11 = "close fileWriter"
            r9.warn(r10, r11, r3)
            goto L_0x00ca
        L_0x0141:
            r3 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r9 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r10 = "LogcatUtil"
            java.lang.String r11 = "close logcatProc"
            r9.warn(r10, r11, r3)
            goto L_0x00cf
        L_0x014e:
            r9 = move-exception
        L_0x014f:
            if (r4 == 0) goto L_0x0154
            r4.close()     // Catch:{ Throwable -> 0x015f }
        L_0x0154:
            if (r7 == 0) goto L_0x0159
            r7.destroy()     // Catch:{ Throwable -> 0x016c }
        L_0x0159:
            if (r0 == 0) goto L_0x015e
            r0.close()     // Catch:{ Throwable -> 0x0179 }
        L_0x015e:
            throw r9
        L_0x015f:
            r3 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r10 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r11 = "LogcatUtil"
            java.lang.String r12 = "close fileWriter"
            r10.warn(r11, r12, r3)
            goto L_0x0154
        L_0x016c:
            r3 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r10 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r11 = "LogcatUtil"
            java.lang.String r12 = "close logcatProc"
            r10.warn(r11, r12, r3)
            goto L_0x0159
        L_0x0179:
            r3 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r10 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r11 = "LogcatUtil"
            java.lang.String r12 = "close bufferedReader"
            r10.warn(r11, r12, r3)
            goto L_0x015e
        L_0x0186:
            r9 = move-exception
            r0 = r1
            goto L_0x014f
        L_0x0189:
            r9 = move-exception
            r4 = r5
            r0 = r1
            goto L_0x014f
        L_0x018d:
            r3 = move-exception
            goto L_0x00ba
        L_0x0190:
            r3 = move-exception
            r0 = r1
            goto L_0x00ba
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.logging.util.LogcatUtil.dumpLogcat(java.io.File, int):void");
    }
}
