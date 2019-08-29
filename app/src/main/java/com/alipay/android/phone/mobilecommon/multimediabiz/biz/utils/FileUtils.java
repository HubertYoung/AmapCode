package com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.text.format.Formatter;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.disc.CleanController;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.common.PermissionHelper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.common.TaskScheduleManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.SimpleConfigProvider;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.IOConf;
import com.alipay.android.phone.mobilesdk.storage.file.ZFile;
import com.alipay.mobile.beehive.service.PhotoParam;
import com.alipay.mobile.common.utils.HexStringUtil;
import com.alipay.mobile.common.utils.MD5Util;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.tinyappcustom.h5plugin.ocr.tools.BehavorReporter;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class FileUtils {
    public static final String GROUP_ID = (AppUtils.isRC() ? "multimediaRC" : "multimedia");
    public static final int JPEG_MARKER_EOI = 217;
    public static final int JPEG_MARKER_FIRST_BYTE = 255;
    private static final Logger a = Logger.getLogger((String) "FileUtils");
    private static final String b = (File.separator + BehavorReporter.PROVIDE_BY_ALIPAY);
    public static final HashMap<String, String> mFileTypes;

    public static class MMBoolean {
        boolean a = false;

        public MMBoolean(boolean value) {
            this.a = value;
        }

        public boolean getValue() {
            return this.a;
        }

        public void setValue(boolean value) {
            this.a = value;
        }
    }

    static {
        HashMap<String, String> hashMap = new HashMap<>();
        mFileTypes = hashMap;
        hashMap.put("FFD8FFE0", ".jpg");
        mFileTypes.put("FFD8FFE1", ".jpg");
        mFileTypes.put("89504E47", ".png");
        mFileTypes.put("47494638", ".gif");
        mFileTypes.put("49492A00", ".tif");
    }

    public static boolean safeCopyToFile(File src, File dst) {
        BufferedInputStream in = null;
        try {
            BufferedInputStream in2 = new BufferedInputStream(new FileInputStream(src));
            try {
                boolean safeCopyToFile = safeCopyToFile((InputStream) in2, dst);
                IOUtils.closeQuietly((InputStream) in2);
                return safeCopyToFile;
            } catch (Throwable th) {
                th = th;
                in = in2;
                IOUtils.closeQuietly((InputStream) in);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            IOUtils.closeQuietly((InputStream) in);
            throw th;
        }
    }

    public static boolean safeCopyToFile(byte[] data, File dstFile) {
        if (data == null) {
            return false;
        }
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        try {
            return safeCopyToFile((InputStream) in, dstFile);
        } finally {
            IOUtils.closeQuietly((InputStream) in);
        }
    }

    public static boolean safeCopyToFile(InputStream in, File dstFile) {
        if (dstFile == null) {
            return false;
        }
        a(dstFile);
        File tmpFile = b(dstFile);
        boolean copied = copyToFile(in, tmpFile);
        if (!copied) {
            return copied;
        }
        a(dstFile);
        boolean copied2 = tmpFile.renameTo(dstFile);
        a.d("safeCopyToFile tmpFile: " + tmpFile + ", dstFile: " + dstFile, new Object[0]);
        return copied2;
    }

    private static void a(File dstFile) {
        if (dstFile.exists() && dstFile.isFile()) {
            boolean deleted = dstFile.delete();
            if (!deleted) {
                a.d("deleteFile file: " + dstFile + ", ret? " + deleted, new Object[0]);
                throw new IOException("delete dstFile failed!!");
            }
        }
    }

    private static File b(File dstFile) {
        File tmpDstFile = new File(dstFile.getAbsolutePath() + "." + System.nanoTime());
        if (tmpDstFile.exists()) {
            boolean deleted = tmpDstFile.delete();
            if (!deleted) {
                a.d("createTmpFile del exists file: " + tmpDstFile + ", ret: " + deleted, new Object[0]);
                throw new IOException("delete tmp file error!!!");
            }
        } else {
            tmpDstFile.getParentFile().mkdirs();
        }
        tmpDstFile.createNewFile();
        return tmpDstFile;
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean copyFile(java.io.File r3, java.io.File r4) {
        /*
            java.io.FileInputStream r0 = new java.io.FileInputStream     // Catch:{ IOException -> 0x0012 }
            r0.<init>(r3)     // Catch:{ IOException -> 0x0012 }
            boolean r1 = copyToFile(r0, r4)     // Catch:{ all -> 0x000d }
            r0.close()     // Catch:{ IOException -> 0x0012 }
        L_0x000c:
            return r1
        L_0x000d:
            r2 = move-exception
            r0.close()     // Catch:{ IOException -> 0x0012 }
            throw r2     // Catch:{ IOException -> 0x0012 }
        L_0x0012:
            r2 = move-exception
            r1 = 0
            goto L_0x000c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils.copyFile(java.io.File, java.io.File):boolean");
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean copyToFile(java.io.InputStream r5, java.io.File r6) {
        /*
            r3 = 0
            a(r6)     // Catch:{ IOException -> 0x0020 }
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x0020 }
            r2.<init>(r6)     // Catch:{ IOException -> 0x0020 }
            r4 = 4096(0x1000, float:5.74E-42)
            byte[] r0 = new byte[r4]     // Catch:{ all -> 0x0018 }
        L_0x000d:
            int r1 = r5.read(r0)     // Catch:{ all -> 0x0018 }
            if (r1 < 0) goto L_0x0022
            r4 = 0
            r2.write(r0, r4, r1)     // Catch:{ all -> 0x0018 }
            goto L_0x000d
        L_0x0018:
            r4 = move-exception
            r2.flush()     // Catch:{ IOException -> 0x0020 }
            r2.close()     // Catch:{ IOException -> 0x0020 }
            throw r4     // Catch:{ IOException -> 0x0020 }
        L_0x0020:
            r4 = move-exception
        L_0x0021:
            return r3
        L_0x0022:
            r2.flush()     // Catch:{ IOException -> 0x0020 }
            r2.close()     // Catch:{ IOException -> 0x0020 }
            r3 = 1
            goto L_0x0021
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils.copyToFile(java.io.InputStream, java.io.File):boolean");
    }

    public static boolean checkFile(String path) {
        boolean z = false;
        if (TextUtils.isEmpty(path)) {
            return z;
        }
        try {
            return checkFile(new File(path));
        } catch (Exception e) {
            a.e(e, "checkFile exp path: " + path, new Object[z]);
            return z;
        }
    }

    public static boolean checkFile(File file) {
        return a(file, SimpleConfigProvider.get().getIOConfig().isEnableAsyncCheckFile());
    }

    private static boolean a(final File file, boolean canInterrupt) {
        boolean z = false;
        if (canInterrupt && AppUtils.inMainLooper()) {
            IOConf conf = SimpleConfigProvider.get().getIOConfig();
            if (conf.isEnableLockSync()) {
                final MMBoolean tempRet = new MMBoolean(z);
                final CountDownLatch waitLatch = new CountDownLatch(1);
                try {
                    TaskScheduleManager.get().commonExecutor().submit(new Callable<Boolean>() {
                        public final Boolean call() {
                            tempRet.setValue(file != null && file.exists() && file.isFile() && file.length() > 0);
                            waitLatch.countDown();
                            return Boolean.valueOf(tempRet.getValue());
                        }
                    });
                    waitLatch.await((long) conf.checkFileWaitTime, TimeUnit.MILLISECONDS);
                    return tempRet.getValue();
                } catch (Exception e) {
                    a.e(e, "checkFile exp1 canInterrupt: " + canInterrupt, new Object[0]);
                    return tempRet.getValue();
                } finally {
                    tempRet.getValue();
                }
            } else {
                try {
                    return ((Boolean) TaskScheduleManager.get().commonExecutor().submit(new Callable<Boolean>() {
                        public final Boolean call() {
                            return Boolean.valueOf(file != null && file.exists() && file.isFile() && file.length() > 0);
                        }
                    }).get((long) conf.checkFileWaitTime, TimeUnit.MILLISECONDS)).booleanValue();
                } catch (Exception e2) {
                    a.e(e2, "checkFile exp2 canInterrupt: " + canInterrupt, new Object[z]);
                    return z;
                }
            }
        } else if (file == null || !file.exists() || !file.isFile() || file.length() <= 0) {
            return z;
        } else {
            return true;
        }
    }

    public static String getMediaDir(String subPath) {
        return getMediaDir(subPath, true);
    }

    public static String getMediaDir(String subPath, boolean needReset) {
        File file;
        String baseDir = null;
        Context context = AppUtils.getApplicationContext();
        if (!PermissionHelper.hasPermission("android.permission.WRITE_EXTERNAL_STORAGE")) {
            return new ZFile(context, GROUP_ID, subPath).getAbsolutePath();
        }
        int tryTimes = 2;
        while (true) {
            if (!PermissionHelper.hasPermission("android.permission.WRITE_EXTERNAL_STORAGE")) {
                break;
            }
            tryTimes--;
            if (tryTimes < 0) {
                break;
            }
            String sdPath = com.alipay.android.phone.mobilesdk.storage.utils.FileUtils.getSDPath();
            if (TextUtils.isEmpty(sdPath)) {
                file = new ZFile(context, GROUP_ID, subPath);
            } else {
                file = new File(sdPath + b + File.separator + GROUP_ID, subPath);
            }
            boolean created = file.exists() && file.isDirectory();
            if (!created) {
                created = mkdirs(file);
                continue;
            }
            if (created) {
                baseDir = file.getAbsolutePath();
                break;
            }
        }
        if (TextUtils.isEmpty(baseDir)) {
            File file2 = new ZFile(context, GROUP_ID, subPath);
            baseDir = file2.getAbsolutePath();
            mkdirs(file2);
        }
        return baseDir;
    }

    public static boolean mkdirs(File dir) {
        if (dir == null) {
            return false;
        }
        if (dir.exists() && dir.isDirectory()) {
            return true;
        }
        boolean ret = false;
        File cur = dir;
        while (cur != null) {
            ret = dir.mkdirs();
            if (ret) {
                return ret;
            }
            if (!cur.exists() || !cur.isFile()) {
                cur = cur.getParentFile();
            } else if (!cur.renameTo(new File(cur.getParent(), cur.getName() + "_" + System.currentTimeMillis()))) {
                return ret;
            }
        }
        return ret;
    }

    public static String getSuffix(String file) {
        return getSuffix(file, true);
    }

    public static String getSuffix(String file, boolean withDot) {
        if (TextUtils.isEmpty(file)) {
            return null;
        }
        int index = file.lastIndexOf(46);
        if (index > 0 && withDot) {
            return file.substring(index, file.length());
        }
        if (index <= 0 || withDot || index + 1 >= file.length()) {
            return null;
        }
        return file.substring(index + 1);
    }

    public static String getSuffixWithoutSeparator(String suffix) {
        if (TextUtils.isEmpty(suffix) || !suffix.startsWith(".")) {
            return suffix;
        }
        return suffix.substring(1);
    }

    public static long getSdTotalSizeBytes() {
        StatFs sf = a(true);
        if (sf == null) {
            return 0;
        }
        return ((long) sf.getBlockSize()) * ((long) sf.getBlockCount());
    }

    public static String getSdTotalSize() {
        return formatFileSize(getSdTotalSizeBytes());
    }

    public static long getSdAvailableSizeBytes() {
        StatFs sf = a(true);
        if (sf == null) {
            return 0;
        }
        return ((long) sf.getBlockSize()) * ((long) sf.getAvailableBlocks());
    }

    public static String getSdAvailableSize() {
        return formatFileSize(getSdAvailableSizeBytes());
    }

    public static long getPhoneTotalSizeBytes() {
        StatFs sf = a(false);
        if (sf == null) {
            return 0;
        }
        return ((long) sf.getBlockSize()) * ((long) sf.getBlockCount());
    }

    public static String getPhoneTotalSize() {
        return formatFileSize(getPhoneTotalSizeBytes());
    }

    public static long getPhoneAvailableSizeBytes() {
        StatFs sf = a(false);
        if (sf == null) {
            return 0;
        }
        return ((long) sf.getBlockSize()) * ((long) sf.getAvailableBlocks());
    }

    public static String getPhoneAvailableSize() {
        return formatFileSize(getPhoneAvailableSizeBytes());
    }

    private static StatFs a(boolean sdcard) {
        String root;
        try {
            Context context = AppUtils.getApplicationContext();
            if (context == null) {
                return null;
            }
            if (sdcard) {
                root = com.alipay.android.phone.mobilesdk.storage.utils.FileUtils.getSDPath();
            } else {
                root = context.getCacheDir().getAbsolutePath();
            }
            if (!TextUtils.isEmpty(root)) {
                return new StatFs(root);
            }
            return null;
        } catch (Throwable t) {
            a.e(t, "getStatFs error, sdcard: " + sdcard, new Object[0]);
            return null;
        }
    }

    public static String formatFileSize(long size) {
        return Formatter.formatFileSize(AppUtils.getApplicationContext(), size);
    }

    public static boolean delete(String path) {
        if (!TextUtils.isEmpty(path)) {
            return delete(new File(path));
        }
        return false;
    }

    public static boolean delete(File file) {
        if (file == null) {
            return false;
        }
        if (file.isFile() && file.exists()) {
            return file.delete();
        }
        if (!file.isDirectory() || !file.exists()) {
            return false;
        }
        boolean delete = true;
        File[] files = file.listFiles();
        if (files != null && files.length > 0) {
            for (File f : files) {
                delete &= delete(f);
            }
        }
        return file.delete() & delete;
    }

    public static long deleteAllFiles(File file) {
        long deleteSize = 0;
        if (file == null || CleanController.get().isInterrupt()) {
            return 0;
        }
        if (file.isFile() && file.exists()) {
            long deleteSize2 = file.length();
            file.delete();
            return deleteSize2;
        } else if (!file.isDirectory() || !file.exists()) {
            return 0;
        } else {
            File[] files = file.listFiles();
            if (files == null || files.length <= 0) {
                return 0;
            }
            for (File f : files) {
                deleteSize += deleteAllFiles(f);
            }
            return deleteSize;
        }
    }

    public static long deleteAllCacheFilesNotInList(File file, HashSet<String> data, String ignoreSuffix, long expiredTime) {
        long deleteSize = 0;
        if (file == null || CleanController.get().isInterrupt()) {
            return 0;
        }
        if (!file.isFile() || !file.exists() || (data != null && data.contains(file.getAbsolutePath()))) {
            if (!file.isDirectory() || !file.exists()) {
                return 0;
            }
            File[] files = file.listFiles();
            if (files == null || files.length <= 0) {
                return 0;
            }
            for (File f : files) {
                if (TextUtils.isEmpty(f.getName()) || (f.isDirectory() && f.getName().length() != 2)) {
                    a.d("deleteAllCacheFilesNotInList white path: " + f.getAbsolutePath(), new Object[0]);
                } else {
                    deleteSize += deleteAllCacheFilesNotInList(f, data, ignoreSuffix, expiredTime);
                }
            }
            return deleteSize;
        } else if (file.length() <= 0 || file.lastModified() >= expiredTime || file.getName().startsWith(ignoreSuffix)) {
            return 0;
        } else {
            long deleteSize2 = file.length();
            file.delete();
            return deleteSize2;
        }
    }

    public static long deleteAllCacheFiles(File file, long time, String ignoreSuffix) {
        long oldTime = System.currentTimeMillis() - time;
        long deleteSize = 0;
        if (file == null || CleanController.get().isInterrupt()) {
            return 0;
        }
        if (!file.isFile() || !file.exists()) {
            if (!file.isDirectory() || !file.exists()) {
                return 0;
            }
            File[] files = file.listFiles();
            if (files == null || files.length <= 0) {
                return 0;
            }
            for (File f : files) {
                if (!TextUtils.isEmpty(f.getName()) && ((!f.isDirectory() || f.getName().length() == 2) && f.lastModified() < oldTime)) {
                    deleteSize += deleteAllCacheFiles(f, time, ignoreSuffix);
                }
            }
            return deleteSize;
        } else if (file.lastModified() >= oldTime || file.length() <= 0 || file.getName().startsWith(ignoreSuffix)) {
            return 0;
        } else {
            long deleteSize2 = file.length();
            file.delete();
            return deleteSize2;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0091 A[SYNTHETIC, Splitter:B:21:0x0091] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00e8 A[SYNTHETIC, Splitter:B:32:0x00e8] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void unzip(java.lang.String r24, java.lang.String r25) {
        /*
            long r16 = java.lang.System.currentTimeMillis()
            r18 = 0
            r20 = 2048(0x800, float:2.87E-42)
            r0 = r20
            byte[] r7 = new byte[r0]
            java.util.zip.ZipFile r19 = new java.util.zip.ZipFile     // Catch:{ Exception -> 0x01bd }
            r0 = r19
            r1 = r24
            r0.<init>(r1)     // Catch:{ Exception -> 0x01bd }
            java.util.Enumeration r10 = r19.entries()     // Catch:{ Exception -> 0x006c, all -> 0x00e3 }
        L_0x0019:
            boolean r20 = r10.hasMoreElements()     // Catch:{ Exception -> 0x006c, all -> 0x00e3 }
            if (r20 == 0) goto L_0x0148
            java.lang.Object r11 = r10.nextElement()     // Catch:{ Exception -> 0x006c, all -> 0x00e3 }
            java.util.zip.ZipEntry r11 = (java.util.zip.ZipEntry) r11     // Catch:{ Exception -> 0x006c, all -> 0x00e3 }
            java.lang.String r12 = r11.getName()     // Catch:{ Exception -> 0x006c, all -> 0x00e3 }
            boolean r20 = android.text.TextUtils.isEmpty(r12)     // Catch:{ Exception -> 0x006c, all -> 0x00e3 }
            if (r20 != 0) goto L_0x004d
            java.lang.String r20 = ".."
            r0 = r20
            boolean r20 = r12.contains(r0)     // Catch:{ Exception -> 0x006c, all -> 0x00e3 }
            if (r20 != 0) goto L_0x004d
            java.lang.String r20 = "\\"
            r0 = r20
            boolean r20 = r12.contains(r0)     // Catch:{ Exception -> 0x006c, all -> 0x00e3 }
            if (r20 != 0) goto L_0x004d
            java.lang.String r20 = "%"
            r0 = r20
            boolean r20 = r12.contains(r0)     // Catch:{ Exception -> 0x006c, all -> 0x00e3 }
            if (r20 == 0) goto L_0x00c9
        L_0x004d:
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r20 = a     // Catch:{ Exception -> 0x006c, all -> 0x00e3 }
            java.lang.StringBuilder r21 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x006c, all -> 0x00e3 }
            java.lang.String r22 = "unzip fail entryName:"
            r21.<init>(r22)     // Catch:{ Exception -> 0x006c, all -> 0x00e3 }
            r0 = r21
            java.lang.StringBuilder r21 = r0.append(r12)     // Catch:{ Exception -> 0x006c, all -> 0x00e3 }
            java.lang.String r21 = r21.toString()     // Catch:{ Exception -> 0x006c, all -> 0x00e3 }
            r22 = 0
            r0 = r22
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ Exception -> 0x006c, all -> 0x00e3 }
            r22 = r0
            r20.d(r21, r22)     // Catch:{ Exception -> 0x006c, all -> 0x00e3 }
            goto L_0x0019
        L_0x006c:
            r8 = move-exception
            r18 = r19
        L_0x006f:
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r20 = a     // Catch:{ all -> 0x01ba }
            java.lang.StringBuilder r21 = new java.lang.StringBuilder     // Catch:{ all -> 0x01ba }
            java.lang.String r22 = "unzip exception:"
            r21.<init>(r22)     // Catch:{ all -> 0x01ba }
            java.lang.String r22 = r8.getMessage()     // Catch:{ all -> 0x01ba }
            java.lang.StringBuilder r21 = r21.append(r22)     // Catch:{ all -> 0x01ba }
            java.lang.String r21 = r21.toString()     // Catch:{ all -> 0x01ba }
            r22 = 0
            r0 = r22
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x01ba }
            r22 = r0
            r20.d(r21, r22)     // Catch:{ all -> 0x01ba }
            if (r18 == 0) goto L_0x0094
            r18.close()     // Catch:{ IOException -> 0x0174 }
        L_0x0094:
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r20 = a
            java.lang.StringBuilder r21 = new java.lang.StringBuilder
            r21.<init>()
            r0 = r21
            r1 = r24
            java.lang.StringBuilder r21 = r0.append(r1)
            java.lang.String r22 = " decompress took "
            java.lang.StringBuilder r21 = r21.append(r22)
            long r22 = java.lang.System.currentTimeMillis()
            long r22 = r22 - r16
            java.lang.StringBuilder r21 = r21.append(r22)
            java.lang.String r22 = "ms"
            java.lang.StringBuilder r21 = r21.append(r22)
            java.lang.String r21 = r21.toString()
            r22 = 0
            r0 = r22
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r22 = r0
            r20.d(r21, r22)
            return
        L_0x00c9:
            boolean r20 = r11.isDirectory()     // Catch:{ Exception -> 0x006c, all -> 0x00e3 }
            if (r20 == 0) goto L_0x00ec
            java.io.File r20 = new java.io.File     // Catch:{ Exception -> 0x006c, all -> 0x00e3 }
            java.lang.String r21 = r11.getName()     // Catch:{ Exception -> 0x006c, all -> 0x00e3 }
            r0 = r20
            r1 = r25
            r2 = r21
            r0.<init>(r1, r2)     // Catch:{ Exception -> 0x006c, all -> 0x00e3 }
            r20.mkdirs()     // Catch:{ Exception -> 0x006c, all -> 0x00e3 }
            goto L_0x0019
        L_0x00e3:
            r20 = move-exception
            r18 = r19
        L_0x00e6:
            if (r18 == 0) goto L_0x00eb
            r18.close()     // Catch:{ IOException -> 0x0197 }
        L_0x00eb:
            throw r20
        L_0x00ec:
            java.io.BufferedInputStream r4 = new java.io.BufferedInputStream     // Catch:{ Exception -> 0x006c, all -> 0x00e3 }
            r0 = r19
            java.io.InputStream r20 = r0.getInputStream(r11)     // Catch:{ Exception -> 0x006c, all -> 0x00e3 }
            r0 = r20
            r4.<init>(r0)     // Catch:{ Exception -> 0x006c, all -> 0x00e3 }
            java.io.File r13 = new java.io.File     // Catch:{ Exception -> 0x006c, all -> 0x00e3 }
            java.lang.String r20 = r11.getName()     // Catch:{ Exception -> 0x006c, all -> 0x00e3 }
            r0 = r25
            r1 = r20
            r13.<init>(r0, r1)     // Catch:{ Exception -> 0x006c, all -> 0x00e3 }
            java.io.File r15 = r13.getParentFile()     // Catch:{ Exception -> 0x006c, all -> 0x00e3 }
            if (r15 == 0) goto L_0x0115
            boolean r20 = r15.exists()     // Catch:{ Exception -> 0x006c, all -> 0x00e3 }
            if (r20 != 0) goto L_0x0115
            r15.mkdirs()     // Catch:{ Exception -> 0x006c, all -> 0x00e3 }
        L_0x0115:
            java.io.FileOutputStream r14 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x006c, all -> 0x00e3 }
            r14.<init>(r13)     // Catch:{ Exception -> 0x006c, all -> 0x00e3 }
            java.io.BufferedOutputStream r5 = new java.io.BufferedOutputStream     // Catch:{ Exception -> 0x006c, all -> 0x00e3 }
            r20 = 2048(0x800, float:2.87E-42)
            r0 = r20
            r5.<init>(r14, r0)     // Catch:{ Exception -> 0x006c, all -> 0x00e3 }
        L_0x0123:
            r20 = 0
            r21 = 2048(0x800, float:2.87E-42)
            r0 = r20
            r1 = r21
            int r6 = r4.read(r7, r0, r1)     // Catch:{ Exception -> 0x006c, all -> 0x00e3 }
            r20 = -1
            r0 = r20
            if (r6 == r0) goto L_0x013d
            r20 = 0
            r0 = r20
            r5.write(r7, r0, r6)     // Catch:{ Exception -> 0x006c, all -> 0x00e3 }
            goto L_0x0123
        L_0x013d:
            r5.flush()     // Catch:{ Exception -> 0x006c, all -> 0x00e3 }
            r5.close()     // Catch:{ Exception -> 0x006c, all -> 0x00e3 }
            r4.close()     // Catch:{ Exception -> 0x006c, all -> 0x00e3 }
            goto L_0x0019
        L_0x0148:
            r19.close()     // Catch:{ IOException -> 0x014f }
            r18 = r19
            goto L_0x0094
        L_0x014f:
            r9 = move-exception
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r20 = a
            java.lang.StringBuilder r21 = new java.lang.StringBuilder
            java.lang.String r22 = "zip close exception:"
            r21.<init>(r22)
            java.lang.String r22 = r9.getMessage()
            java.lang.StringBuilder r21 = r21.append(r22)
            java.lang.String r21 = r21.toString()
            r22 = 0
            r0 = r22
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r22 = r0
            r20.d(r21, r22)
            r18 = r19
            goto L_0x0094
        L_0x0174:
            r9 = move-exception
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r20 = a
            java.lang.StringBuilder r21 = new java.lang.StringBuilder
            java.lang.String r22 = "zip close exception:"
            r21.<init>(r22)
            java.lang.String r22 = r9.getMessage()
            java.lang.StringBuilder r21 = r21.append(r22)
            java.lang.String r21 = r21.toString()
            r22 = 0
            r0 = r22
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r22 = r0
            r20.d(r21, r22)
            goto L_0x0094
        L_0x0197:
            r9 = move-exception
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r21 = a
            java.lang.StringBuilder r22 = new java.lang.StringBuilder
            java.lang.String r23 = "zip close exception:"
            r22.<init>(r23)
            java.lang.String r23 = r9.getMessage()
            java.lang.StringBuilder r22 = r22.append(r23)
            java.lang.String r22 = r22.toString()
            r23 = 0
            r0 = r23
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r23 = r0
            r21.d(r22, r23)
            goto L_0x00eb
        L_0x01ba:
            r20 = move-exception
            goto L_0x00e6
        L_0x01bd:
            r8 = move-exception
            goto L_0x006f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils.unzip(java.lang.String, java.lang.String):void");
    }

    public static long calcFileSize(String path) {
        if (TextUtils.isEmpty(path)) {
            return 0;
        }
        return calcFileSize(new File(path));
    }

    public static long calcFileSize(File file) {
        if (file.exists() && file.isFile()) {
            return file.length();
        }
        if (!file.exists() || !file.isDirectory()) {
            return 0;
        }
        File[] files = file.listFiles();
        if (files == null) {
            return 0;
        }
        long total = 0;
        for (File f : files) {
            total += calcFileSize(f);
        }
        return total;
    }

    public static boolean updateFileNewestModified(String file) {
        return !TextUtils.isEmpty(file) && updateFileNewestModified(new File(file));
    }

    public static boolean updateFileNewestModified(File file) {
        if (file == null || !file.exists() || !file.isFile()) {
            return false;
        }
        RandomAccessFile raf = null;
        try {
            RandomAccessFile raf2 = new RandomAccessFile(file, "rw");
            try {
                long length = raf2.length();
                raf2.setLength(1 + length);
                raf2.setLength(length);
                IOUtils.closeQuietly((Closeable) raf2);
                return true;
            } catch (Throwable th) {
                th = th;
                raf = raf2;
                IOUtils.closeQuietly((Closeable) raf);
                throw th;
            }
        } catch (Throwable th2) {
            t = th2;
            a.w("updateFileNewestModified error: " + t, new Object[0]);
            IOUtils.closeQuietly((Closeable) raf);
            return false;
        }
    }

    public static long fileSize(String path) {
        if (!TextUtils.isEmpty(path)) {
            return new File(path).length();
        }
        return 0;
    }

    public static long fileSize(File path) {
        if (path != null) {
            return path.length();
        }
        return 0;
    }

    public static String readFile(File file) {
        int read;
        FileInputStream fis = null;
        try {
            FileInputStream fis2 = new FileInputStream(file);
            try {
                BufferedInputStream bos = new BufferedInputStream(fis2);
                byte[] data = new byte[((int) file.length())];
                do {
                    read = bos.read(data);
                    if (read == -1) {
                        break;
                    }
                } while (((long) read) != file.length());
                String content = new String(data);
                IOUtils.closeQuietly((InputStream) fis2);
                return content;
            } catch (Throwable th) {
                th = th;
                fis = fis2;
                IOUtils.closeQuietly((InputStream) fis);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            IOUtils.closeQuietly((InputStream) fis);
            throw th;
        }
    }

    public static boolean makeDirNoMedia(File dir) {
        boolean ret;
        if (dir != null) {
            ret = true;
        } else {
            ret = false;
        }
        if (dir == null) {
            return ret;
        }
        File noMediaFile = new File(dir, ".nomedia");
        if (noMediaFile.exists()) {
            return ret;
        }
        mkdirs(dir);
        try {
            noMediaFile.createNewFile();
            return true;
        } catch (Exception e) {
            a.e(e, "makeDirNoMedia error", new Object[0]);
            return false;
        }
    }

    public static boolean makeDirNoMedia(String dir) {
        return !TextUtils.isEmpty(dir) && makeDirNoMedia(new File(dir));
    }

    public static File makeTakenPicturePath() {
        File dcimDir = new File(Environment.getExternalStorageDirectory(), "alipay/pictures");
        File picFile = new File(dcimDir, DateUtil.getCurrentDateFormat() + ".jpg");
        try {
            mkdirs(dcimDir);
            picFile.createNewFile();
        } catch (IOException e) {
            Logger.E((String) "FileUtils", (Throwable) e, "makeTakenPicturePath createNewFile error, " + picFile, new Object[0]);
        }
        return picFile;
    }

    public static File makeTakenPicturePrivatePath() {
        File dcimDir = new File(LauncherApplicationAgent.getInstance().getMicroApplicationContext().getApplicationContext().getExternalCacheDir() + File.separator + "mm/pictures");
        File picFile = new File(dcimDir, DateUtil.getCurrentDateFormat() + ".jpg");
        try {
            mkdirs(dcimDir);
            File nomediaFile = new File(dcimDir, ".nomedia");
            if (!nomediaFile.exists()) {
                nomediaFile.createNewFile();
            }
            picFile.createNewFile();
        } catch (IOException e) {
            Logger.E((String) "FileUtils", (Throwable) e, "makeTakenPicturePath createNewFile error, " + picFile, new Object[0]);
        }
        return picFile;
    }

    public static File makeTakenVideoPath() {
        File cameraDir = new File(new File(Environment.getExternalStorageDirectory(), Environment.DIRECTORY_DCIM), "Camera");
        cameraDir.mkdirs();
        return new File(cameraDir, DateUtil.getCurrentDateFormat() + PhotoParam.VIDEO_SUFFIX);
    }

    public static String getFileType(String filePath) {
        String suffix = mFileTypes.get(getFileHeader(filePath));
        a.d("getFileType suffix=" + suffix + ";filePath=" + filePath, new Object[0]);
        return suffix;
    }

    public static boolean isJpegFile(File file) {
        String header = getFileHeader(file);
        if (!TextUtils.isEmpty(header)) {
            return header.startsWith("FFD8FF");
        }
        return false;
    }

    public static String getFileHeader(String filePath) {
        FileInputStream is = null;
        String value = null;
        try {
            FileInputStream is2 = new FileInputStream(filePath);
            try {
                byte[] b2 = new byte[4];
                is2.read(b2, 0, 4);
                value = a(b2);
                IOUtils.closeQuietly((InputStream) is2);
                FileInputStream fileInputStream = is2;
            } catch (Exception e) {
                e = e;
                is = is2;
                try {
                    a.e(e, "getFileHeader exp", new Object[0]);
                    IOUtils.closeQuietly((InputStream) is);
                    return value;
                } catch (Throwable th) {
                    th = th;
                    IOUtils.closeQuietly((InputStream) is);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                is = is2;
                IOUtils.closeQuietly((InputStream) is);
                throw th;
            }
        } catch (Exception e2) {
            e = e2;
            a.e(e, "getFileHeader exp", new Object[0]);
            IOUtils.closeQuietly((InputStream) is);
            return value;
        }
        return value;
    }

    public static String getFileHeader(File file) {
        FileInputStream is = null;
        String value = null;
        try {
            FileInputStream is2 = new FileInputStream(file);
            try {
                byte[] b2 = new byte[4];
                is2.read(b2, 0, 4);
                value = a(b2);
                IOUtils.closeQuietly((InputStream) is2);
                FileInputStream fileInputStream = is2;
            } catch (Exception e) {
                e = e;
                is = is2;
                try {
                    a.e(e, "getFileHeader exp", new Object[0]);
                    IOUtils.closeQuietly((InputStream) is);
                    return value;
                } catch (Throwable th) {
                    th = th;
                    IOUtils.closeQuietly((InputStream) is);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                is = is2;
                IOUtils.closeQuietly((InputStream) is);
                throw th;
            }
        } catch (Exception e2) {
            e = e2;
            a.e(e, "getFileHeader exp", new Object[0]);
            IOUtils.closeQuietly((InputStream) is);
            return value;
        }
        return value;
    }

    private static String a(byte[] data) {
        String value = HexStringUtil.bytesToHexString(data);
        if (!TextUtils.isEmpty(value)) {
            return value.toUpperCase();
        }
        return value;
    }

    public static byte[] file2Bytes(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        return file2Bytes(new File(path));
    }

    public static byte[] file2Bytes(File path) {
        byte[] data = null;
        if (checkFile(path)) {
            FileInputStream fis = null;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                FileInputStream fis2 = new FileInputStream(path);
                try {
                    byte[] buf = new byte[4096];
                    while (true) {
                        int read = fis2.read(buf);
                        if (read == -1) {
                            break;
                        }
                        baos.write(buf, 0, read);
                    }
                    data = baos.toByteArray();
                    IOUtils.closeQuietly((InputStream) fis2);
                    IOUtils.closeQuietly((OutputStream) baos);
                } catch (Throwable th) {
                    th = th;
                    fis = fis2;
                    IOUtils.closeQuietly((InputStream) fis);
                    IOUtils.closeQuietly((OutputStream) baos);
                    throw th;
                }
            } catch (Throwable th2) {
                e = th2;
                a.w("read file: " + path + ", error, " + e, new Object[0]);
                IOUtils.closeQuietly((InputStream) fis);
                IOUtils.closeQuietly((OutputStream) baos);
                return data;
            }
        }
        return data;
    }

    public static boolean moveFile(File src, File dst) {
        if (src == null || dst == null || !src.exists() || !src.isFile()) {
            return false;
        }
        if (dst.exists()) {
            delete(dst);
        }
        mkdirs(dst.getParentFile());
        return src.renameTo(dst);
    }

    public static boolean moveFile(String src, String dst) {
        return !TextUtils.isEmpty(src) && !TextUtils.isEmpty(dst) && moveFile(new File(src), new File(dst));
    }

    public static boolean checkFileByMd5(String md5, String path, boolean delete) {
        if (TextUtils.isEmpty(md5) || !ConfigManager.getInstance().getCheckMd5Switch()) {
            return true;
        }
        try {
            String fileMd5 = MD5Util.getFileMD5String(new File(path));
            boolean ret = md5.equalsIgnoreCase(fileMd5);
            if (ret) {
                return ret;
            }
            a.d("checkMd5 fail, md5: " + md5 + ", path: " + path + ", fileMd5: " + fileMd5, new Object[0]);
            if (!delete) {
                return ret;
            }
            com.alipay.android.phone.mobilesdk.storage.utils.FileUtils.deleteFileByPath(path);
            return ret;
        } catch (Exception e) {
            a.e(e, "checkMd5 exp, md5: " + md5 + ", path: " + path + ", fileMd5: " + "", new Object[0]);
            if (delete) {
                com.alipay.android.phone.mobilesdk.storage.utils.FileUtils.deleteFileByPath(path);
            }
            return false;
        }
    }

    public static boolean copyFileWithRetry(File src, File dst) {
        boolean cpResult = a(src, dst);
        if (cpResult && src.length() == dst.length()) {
            return cpResult;
        }
        a.d("Use copyTransferToFile fail, Continue to use transferStream retry.", new Object[0]);
        if (dst.exists()) {
            dst.delete();
        }
        FileOutputStream out = new FileOutputStream(dst);
        FileInputStream in = new FileInputStream(src);
        try {
            cpResult = a((InputStream) in, (OutputStream) out);
            return cpResult;
        } finally {
            IOUtils.closeQuietly((InputStream) in);
            IOUtils.closeQuietly((OutputStream) out);
            if (!cpResult && dst.exists()) {
                r6 = "copyFileWithRetry retry fail, delete save file";
                a.d(r6, new Object[0]);
                dst.delete();
            }
        }
    }

    private static boolean a(InputStream in, OutputStream out) {
        boolean z = false;
        try {
            byte[] buffer = new byte[40960];
            int totalRead = 0;
            while (true) {
                int bytesRead = in.read(buffer);
                if (bytesRead < 0) {
                    break;
                }
                out.write(buffer, 0, bytesRead);
                totalRead += bytesRead;
            }
            if (totalRead > 0) {
                z = true;
            }
        } catch (Throwable e) {
            a.e(e, "transferStream fail exp!", new Object[0]);
        } finally {
            out.flush();
        }
        return z;
    }

    /* JADX WARNING: Removed duplicated region for block: B:32:0x0077 A[SYNTHETIC, Splitter:B:32:0x0077] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x007c A[SYNTHETIC, Splitter:B:35:0x007c] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00f4 A[SYNTHETIC, Splitter:B:48:0x00f4] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00f9 A[SYNTHETIC, Splitter:B:51:0x00f9] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean a(java.io.File r13, java.io.File r14) {
        /*
            r12 = 0
            boolean r2 = r14.exists()
            if (r2 == 0) goto L_0x0052
            r14.delete()
        L_0x000a:
            r7 = 0
            r9 = 0
            java.io.FileInputStream r8 = new java.io.FileInputStream     // Catch:{ Exception -> 0x013c }
            r8.<init>(r13)     // Catch:{ Exception -> 0x013c }
            java.io.FileOutputStream r10 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x013f, all -> 0x0135 }
            r10.<init>(r14)     // Catch:{ Exception -> 0x013f, all -> 0x0135 }
            java.nio.channels.FileChannel r6 = r10.getChannel()     // Catch:{ Exception -> 0x0068, all -> 0x0138 }
            java.nio.channels.FileChannel r1 = r8.getChannel()     // Catch:{ Exception -> 0x0068, all -> 0x0138 }
            r2 = 0
            long r4 = r1.size()     // Catch:{ all -> 0x0060 }
            r1.transferTo(r2, r4, r6)     // Catch:{ all -> 0x0060 }
            r6.close()     // Catch:{ Exception -> 0x0068, all -> 0x0138 }
            r1.close()     // Catch:{ Exception -> 0x0068, all -> 0x0138 }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r2 = a     // Catch:{ Exception -> 0x0068, all -> 0x0138 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0068, all -> 0x0138 }
            java.lang.String r4 = "copyTransferToFile finish.  src: "
            r3.<init>(r4)     // Catch:{ Exception -> 0x0068, all -> 0x0138 }
            java.lang.String r4 = r13.getAbsolutePath()     // Catch:{ Exception -> 0x0068, all -> 0x0138 }
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ Exception -> 0x0068, all -> 0x0138 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x0068, all -> 0x0138 }
            r4 = 0
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ Exception -> 0x0068, all -> 0x0138 }
            r2.d(r3, r4)     // Catch:{ Exception -> 0x0068, all -> 0x0138 }
            r10.close()     // Catch:{ Exception -> 0x0081 }
        L_0x004b:
            r8.close()     // Catch:{ Exception -> 0x009d }
        L_0x004e:
            r2 = 1
            r9 = r10
            r7 = r8
        L_0x0051:
            return r2
        L_0x0052:
            java.io.File r11 = r14.getParentFile()
            boolean r2 = r11.exists()
            if (r2 != 0) goto L_0x000a
            r11.mkdirs()
            goto L_0x000a
        L_0x0060:
            r2 = move-exception
            r6.close()     // Catch:{ Exception -> 0x0068, all -> 0x0138 }
            r1.close()     // Catch:{ Exception -> 0x0068, all -> 0x0138 }
            throw r2     // Catch:{ Exception -> 0x0068, all -> 0x0138 }
        L_0x0068:
            r0 = move-exception
            r9 = r10
            r7 = r8
        L_0x006b:
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r2 = a     // Catch:{ all -> 0x00f1 }
            java.lang.String r3 = "copy file error!"
            r4 = 0
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ all -> 0x00f1 }
            r2.e(r0, r3, r4)     // Catch:{ all -> 0x00f1 }
            if (r9 == 0) goto L_0x007a
            r9.close()     // Catch:{ Exception -> 0x00b9 }
        L_0x007a:
            if (r7 == 0) goto L_0x007f
            r7.close()     // Catch:{ Exception -> 0x00d5 }
        L_0x007f:
            r2 = r12
            goto L_0x0051
        L_0x0081:
            r0 = move-exception
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r2 = a
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "outputStream.close exception. "
            r3.<init>(r4)
            java.lang.String r4 = r0.toString()
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            java.lang.Object[] r4 = new java.lang.Object[r12]
            r2.w(r3, r4)
            goto L_0x004b
        L_0x009d:
            r0 = move-exception
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r2 = a
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "inputStream.close exception. "
            r3.<init>(r4)
            java.lang.String r4 = r0.toString()
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            java.lang.Object[] r4 = new java.lang.Object[r12]
            r2.w(r3, r4)
            goto L_0x004e
        L_0x00b9:
            r0 = move-exception
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r2 = a
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "outputStream.close exception. "
            r3.<init>(r4)
            java.lang.String r4 = r0.toString()
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            java.lang.Object[] r4 = new java.lang.Object[r12]
            r2.w(r3, r4)
            goto L_0x007a
        L_0x00d5:
            r0 = move-exception
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r2 = a
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "inputStream.close exception. "
            r3.<init>(r4)
            java.lang.String r4 = r0.toString()
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            java.lang.Object[] r4 = new java.lang.Object[r12]
            r2.w(r3, r4)
            goto L_0x007f
        L_0x00f1:
            r2 = move-exception
        L_0x00f2:
            if (r9 == 0) goto L_0x00f7
            r9.close()     // Catch:{ Exception -> 0x00fd }
        L_0x00f7:
            if (r7 == 0) goto L_0x00fc
            r7.close()     // Catch:{ Exception -> 0x0119 }
        L_0x00fc:
            throw r2
        L_0x00fd:
            r0 = move-exception
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r3 = a
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "outputStream.close exception. "
            r4.<init>(r5)
            java.lang.String r5 = r0.toString()
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r4 = r4.toString()
            java.lang.Object[] r5 = new java.lang.Object[r12]
            r3.w(r4, r5)
            goto L_0x00f7
        L_0x0119:
            r0 = move-exception
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r3 = a
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "inputStream.close exception. "
            r4.<init>(r5)
            java.lang.String r5 = r0.toString()
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r4 = r4.toString()
            java.lang.Object[] r5 = new java.lang.Object[r12]
            r3.w(r4, r5)
            goto L_0x00fc
        L_0x0135:
            r2 = move-exception
            r7 = r8
            goto L_0x00f2
        L_0x0138:
            r2 = move-exception
            r9 = r10
            r7 = r8
            goto L_0x00f2
        L_0x013c:
            r0 = move-exception
            goto L_0x006b
        L_0x013f:
            r0 = move-exception
            r7 = r8
            goto L_0x006b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils.a(java.io.File, java.io.File):boolean");
    }
}
