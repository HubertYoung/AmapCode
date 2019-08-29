package com.alipay.mobile.common.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.util.Log;
import com.alibaba.analytics.core.sync.UploadQueueMgr;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileUtils {
    public FileUtils() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    @Deprecated
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
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.utils.FileUtils.saveBitmap2File(android.graphics.Bitmap, java.io.File):void");
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
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.utils.FileUtils.copyFile(java.io.File, java.io.File):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x004d A[SYNTHETIC, Splitter:B:29:0x004d] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0089 A[SYNTHETIC, Splitter:B:38:0x0089] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean copyToFile(java.io.FileInputStream r10, java.io.File r11) {
        /*
            boolean r2 = r11.exists()
            if (r2 == 0) goto L_0x002c
            r11.delete()
        L_0x0009:
            r7 = 0
            java.io.FileOutputStream r8 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x00aa }
            r8.<init>(r11)     // Catch:{ Exception -> 0x00aa }
            java.nio.channels.FileChannel r6 = r8.getChannel()     // Catch:{ Exception -> 0x0042, all -> 0x00a7 }
            java.nio.channels.FileChannel r1 = r10.getChannel()     // Catch:{ Exception -> 0x0042, all -> 0x00a7 }
            r2 = 0
            long r4 = r1.size()     // Catch:{ all -> 0x003a }
            r1.transferTo(r2, r4, r6)     // Catch:{ all -> 0x003a }
            r6.close()     // Catch:{ Exception -> 0x0042, all -> 0x00a7 }
            r1.close()     // Catch:{ Exception -> 0x0042, all -> 0x00a7 }
            r8.close()     // Catch:{ Exception -> 0x0052 }
        L_0x0029:
            r2 = 1
            r7 = r8
        L_0x002b:
            return r2
        L_0x002c:
            java.io.File r9 = r11.getParentFile()
            boolean r2 = r9.exists()
            if (r2 != 0) goto L_0x0009
            r9.mkdirs()
            goto L_0x0009
        L_0x003a:
            r2 = move-exception
            r6.close()     // Catch:{ Exception -> 0x0042, all -> 0x00a7 }
            r1.close()     // Catch:{ Exception -> 0x0042, all -> 0x00a7 }
            throw r2     // Catch:{ Exception -> 0x0042, all -> 0x00a7 }
        L_0x0042:
            r0 = move-exception
            r7 = r8
        L_0x0044:
            java.lang.String r2 = "FileUtils"
            java.lang.String r3 = "copy file error!"
            com.alipay.mobile.common.utils.LogCatUtil.error(r2, r3, r0)     // Catch:{ all -> 0x0086 }
            if (r7 == 0) goto L_0x0050
            r7.close()     // Catch:{ Exception -> 0x006c }
        L_0x0050:
            r2 = 0
            goto L_0x002b
        L_0x0052:
            r0 = move-exception
            java.lang.String r2 = "FileUtils"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "outputStream.close exception. "
            r3.<init>(r4)
            java.lang.String r4 = r0.toString()
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            com.alipay.mobile.common.utils.LogCatUtil.warn(r2, r3)
            goto L_0x0029
        L_0x006c:
            r0 = move-exception
            java.lang.String r2 = "FileUtils"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "outputStream.close exception. "
            r3.<init>(r4)
            java.lang.String r4 = r0.toString()
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            com.alipay.mobile.common.utils.LogCatUtil.warn(r2, r3)
            goto L_0x0050
        L_0x0086:
            r2 = move-exception
        L_0x0087:
            if (r7 == 0) goto L_0x008c
            r7.close()     // Catch:{ Exception -> 0x008d }
        L_0x008c:
            throw r2
        L_0x008d:
            r0 = move-exception
            java.lang.String r3 = "FileUtils"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "outputStream.close exception. "
            r4.<init>(r5)
            java.lang.String r5 = r0.toString()
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r4 = r4.toString()
            com.alipay.mobile.common.utils.LogCatUtil.warn(r3, r4)
            goto L_0x008c
        L_0x00a7:
            r2 = move-exception
            r7 = r8
            goto L_0x0087
        L_0x00aa:
            r0 = move-exception
            goto L_0x0044
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.utils.FileUtils.copyToFile(java.io.FileInputStream, java.io.File):boolean");
    }

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
                    out.close();
                    return true;
                }
            }
        } catch (IOException e) {
            LoggerFactory.getTraceLogger().warn((String) "FileUtils", (Throwable) e);
            return false;
        } catch (Throwable th) {
            out.close();
            throw th;
        }
    }

    public static String md5sum(File file) {
        return MD5Util.getFileMD5String(file);
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
}
