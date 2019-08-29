package com.alipay.mobile.common.transport.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import com.alibaba.analytics.core.sync.UploadQueueMgr;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileUtils {
    public static void delFiles(File filepath) {
        if (filepath != null && filepath.exists() && filepath.isDirectory()) {
            File[] files = filepath.listFiles();
            if (files != null && files.length == 0) {
                filepath.delete();
            } else if (files != null) {
                int i = files.length;
                for (int j = 0; j < i; j++) {
                    if (files[j].isDirectory()) {
                        delFiles(files[j]);
                    }
                    files[j].delete();
                }
            }
        } else if (filepath != null) {
            filepath.delete();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0048 A[SYNTHETIC, Splitter:B:22:0x0048] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x004d A[SYNTHETIC, Splitter:B:25:0x004d] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x006a A[SYNTHETIC, Splitter:B:33:0x006a] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x006f A[SYNTHETIC, Splitter:B:36:0x006f] */
    /* JADX WARNING: Removed duplicated region for block: B:49:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void saveBitmap2File(android.graphics.Bitmap r6, java.io.File r7) {
        /*
            if (r7 == 0) goto L_0x000b
            boolean r3 = r7.exists()
            if (r3 == 0) goto L_0x000b
            r7.delete()
        L_0x000b:
            r1 = 0
            java.io.BufferedOutputStream r2 = new java.io.BufferedOutputStream     // Catch:{ Exception -> 0x003c }
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x003c }
            r3.<init>(r7)     // Catch:{ Exception -> 0x003c }
            r2.<init>(r3)     // Catch:{ Exception -> 0x003c }
            android.graphics.Bitmap$CompressFormat r3 = android.graphics.Bitmap.CompressFormat.PNG     // Catch:{ Exception -> 0x008c, all -> 0x0089 }
            r4 = 100
            r6.compress(r3, r4, r2)     // Catch:{ Exception -> 0x008c, all -> 0x0089 }
            r2.flush()     // Catch:{ Exception -> 0x0025 }
        L_0x0020:
            r2.close()     // Catch:{ Exception -> 0x0030 }
            r1 = r2
        L_0x0024:
            return
        L_0x0025:
            r0 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r3 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r4 = "FileUtils"
            r3.warn(r4, r0)
            goto L_0x0020
        L_0x0030:
            r0 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r3 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r4 = "FileUtils"
            r3.warn(r4, r0)
            r1 = r2
            goto L_0x0024
        L_0x003c:
            r0 = move-exception
        L_0x003d:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r3 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x0067 }
            java.lang.String r4 = "FileUtils"
            r3.warn(r4, r0)     // Catch:{ all -> 0x0067 }
            if (r1 == 0) goto L_0x004b
            r1.flush()     // Catch:{ Exception -> 0x005c }
        L_0x004b:
            if (r1 == 0) goto L_0x0024
            r1.close()     // Catch:{ Exception -> 0x0051 }
            goto L_0x0024
        L_0x0051:
            r0 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r3 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r4 = "FileUtils"
            r3.warn(r4, r0)
            goto L_0x0024
        L_0x005c:
            r0 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r3 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r4 = "FileUtils"
            r3.warn(r4, r0)
            goto L_0x004b
        L_0x0067:
            r3 = move-exception
        L_0x0068:
            if (r1 == 0) goto L_0x006d
            r1.flush()     // Catch:{ Exception -> 0x0073 }
        L_0x006d:
            if (r1 == 0) goto L_0x0072
            r1.close()     // Catch:{ Exception -> 0x007e }
        L_0x0072:
            throw r3
        L_0x0073:
            r0 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r4 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r5 = "FileUtils"
            r4.warn(r5, r0)
            goto L_0x006d
        L_0x007e:
            r0 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r4 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r5 = "FileUtils"
            r4.warn(r5, r0)
            goto L_0x0072
        L_0x0089:
            r3 = move-exception
            r1 = r2
            goto L_0x0068
        L_0x008c:
            r0 = move-exception
            r1 = r2
            goto L_0x003d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.transport.utils.FileUtils.saveBitmap2File(android.graphics.Bitmap, java.io.File):void");
    }

    public static int calculateInSampleSize(Options options, int reqWidth) {
        int width = options.outWidth;
        if (width > reqWidth) {
            return Math.round(((float) width) / ((float) reqWidth));
        }
        return 1;
    }

    public static Options getFileOption(File imageFile) {
        if (imageFile == null || !imageFile.exists()) {
            return null;
        }
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
        return options;
    }

    public static float widthRetio(int reqWidth, File bitmap) {
        Options option = getFileOption(bitmap);
        if (option == null) {
            return 1.0f;
        }
        int width = option.outWidth;
        if (reqWidth > width) {
            return ((float) reqWidth) / ((float) width);
        }
        return 1.0f;
    }

    public static Bitmap getImageBitmap(int reqWidth, File bitmap) {
        Options option = getFileOption(bitmap);
        option.inSampleSize = calculateInSampleSize(option, reqWidth);
        option.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(bitmap.getAbsolutePath(), option);
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean copyFile(java.io.File r5, java.io.File r6) {
        /*
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch:{ IOException -> 0x0012 }
            r1.<init>(r5)     // Catch:{ IOException -> 0x0012 }
            boolean r2 = copyToFile(r1, r6)     // Catch:{ all -> 0x000d }
            r1.close()     // Catch:{ IOException -> 0x0012 }
        L_0x000c:
            return r2
        L_0x000d:
            r3 = move-exception
            r1.close()     // Catch:{ IOException -> 0x0012 }
            throw r3     // Catch:{ IOException -> 0x0012 }
        L_0x0012:
            r0 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r3 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r4 = "FileUtils"
            r3.warn(r4, r0)
            r2 = 0
            goto L_0x000c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.transport.utils.FileUtils.copyFile(java.io.File, java.io.File):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x004d A[SYNTHETIC, Splitter:B:29:0x004d] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0089 A[SYNTHETIC, Splitter:B:38:0x0089] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean copyToFile(java.io.FileInputStream r10, java.io.File r11) {
        /*
            boolean r1 = r11.exists()
            if (r1 == 0) goto L_0x002c
            r11.delete()
        L_0x0009:
            r7 = 0
            java.io.FileOutputStream r8 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x00aa }
            r8.<init>(r11)     // Catch:{ Exception -> 0x00aa }
            java.nio.channels.FileChannel r5 = r8.getChannel()     // Catch:{ Exception -> 0x0042, all -> 0x00a7 }
            java.nio.channels.FileChannel r0 = r10.getChannel()     // Catch:{ Exception -> 0x0042, all -> 0x00a7 }
            r1 = 0
            long r3 = r0.size()     // Catch:{ all -> 0x003a }
            r0.transferTo(r1, r3, r5)     // Catch:{ all -> 0x003a }
            r5.close()     // Catch:{ Exception -> 0x0042, all -> 0x00a7 }
            r0.close()     // Catch:{ Exception -> 0x0042, all -> 0x00a7 }
            r8.close()     // Catch:{ Exception -> 0x0052 }
        L_0x0029:
            r1 = 1
            r7 = r8
        L_0x002b:
            return r1
        L_0x002c:
            java.io.File r9 = r11.getParentFile()
            boolean r1 = r9.exists()
            if (r1 != 0) goto L_0x0009
            r9.mkdirs()
            goto L_0x0009
        L_0x003a:
            r1 = move-exception
            r5.close()     // Catch:{ Exception -> 0x0042, all -> 0x00a7 }
            r0.close()     // Catch:{ Exception -> 0x0042, all -> 0x00a7 }
            throw r1     // Catch:{ Exception -> 0x0042, all -> 0x00a7 }
        L_0x0042:
            r6 = move-exception
            r7 = r8
        L_0x0044:
            java.lang.String r1 = "FileUtils"
            java.lang.String r2 = "copy file error!"
            com.alipay.mobile.common.transport.utils.LogCatUtil.error(r1, r2, r6)     // Catch:{ all -> 0x0086 }
            if (r7 == 0) goto L_0x0050
            r7.close()     // Catch:{ Exception -> 0x006c }
        L_0x0050:
            r1 = 0
            goto L_0x002b
        L_0x0052:
            r6 = move-exception
            java.lang.String r1 = "FileUtils"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "outputStream.close exception. "
            r2.<init>(r3)
            java.lang.String r3 = r6.toString()
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r2 = r2.toString()
            com.alipay.mobile.common.transport.utils.LogCatUtil.warn(r1, r2)
            goto L_0x0029
        L_0x006c:
            r6 = move-exception
            java.lang.String r1 = "FileUtils"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "outputStream.close exception. "
            r2.<init>(r3)
            java.lang.String r3 = r6.toString()
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r2 = r2.toString()
            com.alipay.mobile.common.transport.utils.LogCatUtil.warn(r1, r2)
            goto L_0x0050
        L_0x0086:
            r1 = move-exception
        L_0x0087:
            if (r7 == 0) goto L_0x008c
            r7.close()     // Catch:{ Exception -> 0x008d }
        L_0x008c:
            throw r1
        L_0x008d:
            r6 = move-exception
            java.lang.String r2 = "FileUtils"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "outputStream.close exception. "
            r3.<init>(r4)
            java.lang.String r4 = r6.toString()
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            com.alipay.mobile.common.transport.utils.LogCatUtil.warn(r2, r3)
            goto L_0x008c
        L_0x00a7:
            r1 = move-exception
            r7 = r8
            goto L_0x0087
        L_0x00aa:
            r6 = move-exception
            goto L_0x0044
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.transport.utils.FileUtils.copyToFile(java.io.FileInputStream, java.io.File):boolean");
    }

    @Deprecated
    public static boolean copyToFile(InputStream inputStream, File destFile) {
        OutputStream out;
        try {
            if (destFile.exists()) {
                destFile.delete();
            }
            out = new FileOutputStream(destFile);
            byte[] buffer = new byte[4096];
            while (true) {
                int bytesRead = inputStream.read(buffer);
                if (bytesRead >= 0) {
                    out.write(buffer, 0, bytesRead);
                } else {
                    out.flush();
                    out.close();
                    return true;
                }
            }
        } catch (IOException e) {
            LoggerFactory.getTraceLogger().warn((String) "FileUtils", (Throwable) e);
            return false;
        } catch (Throwable th) {
            out.flush();
            out.close();
            throw th;
        }
    }

    public static boolean streamCopyFile(File srcFile, File destFile) {
        OutputStream out;
        InputStream inputStream;
        try {
            if (destFile.exists()) {
                destFile.delete();
            }
            out = new FileOutputStream(destFile);
            inputStream = new FileInputStream(srcFile);
            byte[] buffer = new byte[4096];
            while (true) {
                int bytesRead = inputStream.read(buffer);
                if (bytesRead < 0) {
                    break;
                }
                out.write(buffer, 0, bytesRead);
            }
            out.flush();
            out.close();
            inputStream.close();
            if (srcFile.length() == destFile.length()) {
                return true;
            }
            if (destFile.exists()) {
                destFile.delete();
            }
            LogCatUtil.warn((String) "FileUtils", "srcFile length=" + srcFile.length() + ", destFile length=" + destFile.length());
            return false;
        } catch (IOException e) {
            a(destFile);
            LoggerFactory.getTraceLogger().warn((String) "FileUtils", (Throwable) e);
            return false;
        } catch (Throwable th) {
            out.flush();
            out.close();
            inputStream.close();
            throw th;
        }
    }

    private static void a(File destFile) {
        try {
            if (destFile.exists()) {
                destFile.delete();
            }
        } catch (Throwable th) {
        }
    }

    public static final byte[] readFile(File file) {
        RandomAccessFile randomAccessFile;
        FileChannel channel;
        try {
            randomAccessFile = new RandomAccessFile(file, UploadQueueMgr.MSGTYPE_REALTIME);
            channel = randomAccessFile.getChannel();
            ByteBuffer byteBuff = ByteBuffer.allocate(1024);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            while (true) {
                int i = channel.read(byteBuff);
                if (i == -1) {
                    break;
                }
                byteBuff.flip();
                byteArrayOutputStream.write(byteBuff.array(), 0, i);
                byteBuff.clear();
            }
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            try {
                channel.close();
            } catch (Exception e) {
                LogCatUtil.warn((String) "FileUtils", "channel.close() exception. " + e.toString());
            }
            try {
                randomAccessFile.close();
                return byteArray;
            } catch (Exception e2) {
                LogCatUtil.warn((String) "FileUtils", "randomAccessFile.close() exception. " + e2.toString());
                return byteArray;
            }
        } catch (Throwable e3) {
            LogCatUtil.error("FileUtils", "readFile exception", e3);
            return null;
        }
    }

    public static boolean checkFileDirWRPermissions(File file) {
        File dirFile;
        if (file == null) {
            LogCatUtil.warn((String) "FileUtils", (String) "checkFileDirWritePermissions. nima! file is null, Are you kidding？");
            return false;
        }
        if (!file.exists()) {
            File parentFile = file.getParentFile();
            if (!parentFile.exists() && !parentFile.mkdirs()) {
                return false;
            }
            dirFile = parentFile;
        } else if (file.isDirectory()) {
            dirFile = file;
        } else {
            dirFile = file.getParentFile();
        }
        if (dirFile.canWrite()) {
            return true;
        }
        return false;
    }

    public static boolean checkDataOrSdcardAvailableSpace(File file, long contentLength) {
        if (file == null) {
            LogCatUtil.warn((String) "FileUtils", (String) "checkFileAvailableSpace. nima! file is null, Are you kidding？");
            return false;
        } else if (isInDataDir(file)) {
            return SDcardUtils.isDataDirAvailableSpace(contentLength);
        } else {
            return SDcardUtils.isSDcardAvailableSpace(contentLength);
        }
    }

    public static boolean checkDataAvailableSpace(File file, long contentLength) {
        if (isInDataDir(file)) {
            return SDcardUtils.isDataDirAvailableSpace(contentLength);
        }
        return true;
    }

    public static boolean isInDataDir(File file) {
        return file.getAbsolutePath().startsWith("/data");
    }

    public static boolean checkFileAvailableSpace(File file, long contentLength) {
        try {
            if (!checkFileDirWRPermissions(file)) {
                return false;
            }
            File parentFile = file.isDirectory() ? file : file.getParentFile();
            if (parentFile == null) {
                parentFile = file;
            }
            return SDcardUtils.isAppAvailableSpace(contentLength, parentFile);
        } catch (Throwable e) {
            LogCatUtil.warn("FileUtils", "checkFileAvailableSpace fail.", e);
            return true;
        }
    }

    public static void deleteFileNotDir(File file) {
        if (file != null) {
            try {
                if (file.exists() && file.isFile()) {
                    file.delete();
                }
            } catch (Throwable t) {
                LogCatUtil.error("FileUtils", "deleteFileNotDir:" + file.getAbsolutePath(), t);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x005b A[SYNTHETIC, Splitter:B:31:0x005b] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0060 A[Catch:{ Throwable -> 0x0064 }] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0069 A[SYNTHETIC, Splitter:B:38:0x0069] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x006e A[Catch:{ Throwable -> 0x0075 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized boolean saveByte2File(java.io.File r10, byte[] r11) {
        /*
            r6 = 0
            java.lang.Class<com.alipay.mobile.common.transport.utils.FileUtils> r7 = com.alipay.mobile.common.transport.utils.FileUtils.class
            monitor-enter(r7)
            r5 = 0
            if (r10 == 0) goto L_0x0009
            if (r11 != 0) goto L_0x000b
        L_0x0009:
            monitor-exit(r7)
            return r6
        L_0x000b:
            java.io.File r8 = r10.getParentFile()     // Catch:{ all -> 0x0072 }
            boolean r8 = r8.exists()     // Catch:{ all -> 0x0072 }
            if (r8 != 0) goto L_0x001f
            java.io.File r8 = r10.getParentFile()     // Catch:{ all -> 0x0072 }
            boolean r8 = r8.mkdirs()     // Catch:{ all -> 0x0072 }
            if (r8 == 0) goto L_0x0009
        L_0x001f:
            r3 = 0
            r0 = 0
            java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch:{ Throwable -> 0x0040 }
            r4.<init>(r10)     // Catch:{ Throwable -> 0x0040 }
            java.io.BufferedOutputStream r1 = new java.io.BufferedOutputStream     // Catch:{ Throwable -> 0x007e, all -> 0x0077 }
            r1.<init>(r4)     // Catch:{ Throwable -> 0x007e, all -> 0x0077 }
            r1.write(r11)     // Catch:{ Throwable -> 0x0081, all -> 0x007a }
            r1.flush()     // Catch:{ Throwable -> 0x0081, all -> 0x007a }
            r5 = 1
            r1.close()     // Catch:{ Throwable -> 0x003c }
            r4.close()     // Catch:{ Throwable -> 0x003c }
            r0 = r1
            r3 = r4
        L_0x003a:
            r6 = r5
            goto L_0x0009
        L_0x003c:
            r6 = move-exception
            r0 = r1
            r3 = r4
            goto L_0x003a
        L_0x0040:
            r2 = move-exception
        L_0x0041:
            java.lang.String r6 = "FileUtils"
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x0066 }
            java.lang.String r9 = "saveByte2File:"
            r8.<init>(r9)     // Catch:{ all -> 0x0066 }
            java.lang.String r9 = r2.toString()     // Catch:{ all -> 0x0066 }
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ all -> 0x0066 }
            java.lang.String r8 = r8.toString()     // Catch:{ all -> 0x0066 }
            com.alipay.mobile.common.transport.utils.LogCatUtil.error(r6, r8)     // Catch:{ all -> 0x0066 }
            if (r0 == 0) goto L_0x005e
            r0.close()     // Catch:{ Throwable -> 0x0064 }
        L_0x005e:
            if (r3 == 0) goto L_0x003a
            r3.close()     // Catch:{ Throwable -> 0x0064 }
            goto L_0x003a
        L_0x0064:
            r6 = move-exception
            goto L_0x003a
        L_0x0066:
            r6 = move-exception
        L_0x0067:
            if (r0 == 0) goto L_0x006c
            r0.close()     // Catch:{ Throwable -> 0x0075 }
        L_0x006c:
            if (r3 == 0) goto L_0x0071
            r3.close()     // Catch:{ Throwable -> 0x0075 }
        L_0x0071:
            throw r6     // Catch:{ all -> 0x0072 }
        L_0x0072:
            r6 = move-exception
            monitor-exit(r7)
            throw r6
        L_0x0075:
            r8 = move-exception
            goto L_0x0071
        L_0x0077:
            r6 = move-exception
            r3 = r4
            goto L_0x0067
        L_0x007a:
            r6 = move-exception
            r0 = r1
            r3 = r4
            goto L_0x0067
        L_0x007e:
            r2 = move-exception
            r3 = r4
            goto L_0x0041
        L_0x0081:
            r2 = move-exception
            r0 = r1
            r3 = r4
            goto L_0x0041
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.transport.utils.FileUtils.saveByte2File(java.io.File, byte[]):boolean");
    }
}
