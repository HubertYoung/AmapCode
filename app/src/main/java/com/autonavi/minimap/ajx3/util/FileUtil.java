package com.autonavi.minimap.ajx3.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Build.VERSION;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import com.autonavi.minimap.ajx3.platform.ackor.AjxFileInfo;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class FileUtil {
    private static final String DOMAIN_PATH = "path://";

    /* JADX WARNING: Removed duplicated region for block: B:41:0x0067 A[SYNTHETIC, Splitter:B:41:0x0067] */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x0071 A[SYNTHETIC, Splitter:B:46:0x0071] */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x007d A[SYNTHETIC, Splitter:B:53:0x007d] */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x0087 A[SYNTHETIC, Splitter:B:58:0x0087] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean copyFile(java.lang.String r8, java.lang.String r9) {
        /*
            r0 = 0
            r1 = 0
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x0060, all -> 0x005d }
            r2.<init>(r8)     // Catch:{ Exception -> 0x0060, all -> 0x005d }
            java.io.File r8 = new java.io.File     // Catch:{ Exception -> 0x0060, all -> 0x005d }
            r8.<init>(r9)     // Catch:{ Exception -> 0x0060, all -> 0x005d }
            boolean r9 = r2.exists()     // Catch:{ Exception -> 0x0060, all -> 0x005d }
            if (r9 == 0) goto L_0x0015
            r2.delete()     // Catch:{ Exception -> 0x0060, all -> 0x005d }
        L_0x0015:
            long r3 = r8.length()     // Catch:{ Exception -> 0x0060, all -> 0x005d }
            r5 = 0
            int r9 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r9 != 0) goto L_0x0023
            r8.delete()     // Catch:{ Exception -> 0x0060, all -> 0x005d }
            return r1
        L_0x0023:
            java.io.FileInputStream r9 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0060, all -> 0x005d }
            r9.<init>(r8)     // Catch:{ Exception -> 0x0060, all -> 0x005d }
            java.io.FileOutputStream r8 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x005b }
            r8.<init>(r2)     // Catch:{ Exception -> 0x005b }
            r0 = 2048(0x800, float:2.87E-42)
            byte[] r0 = new byte[r0]     // Catch:{ Exception -> 0x0056, all -> 0x0051 }
        L_0x0031:
            int r2 = r9.read(r0)     // Catch:{ Exception -> 0x0056, all -> 0x0051 }
            r3 = -1
            if (r2 == r3) goto L_0x003c
            r8.write(r0, r1, r2)     // Catch:{ Exception -> 0x0056, all -> 0x0051 }
            goto L_0x0031
        L_0x003c:
            r8.flush()     // Catch:{ Exception -> 0x0056, all -> 0x0051 }
            r8.close()     // Catch:{ Exception -> 0x0043 }
            goto L_0x0047
        L_0x0043:
            r8 = move-exception
            r8.printStackTrace()
        L_0x0047:
            r9.close()     // Catch:{ Exception -> 0x004b }
            goto L_0x004f
        L_0x004b:
            r8 = move-exception
            r8.printStackTrace()
        L_0x004f:
            r8 = 1
            return r8
        L_0x0051:
            r0 = move-exception
            r7 = r0
            r0 = r8
            r8 = r7
            goto L_0x007b
        L_0x0056:
            r0 = move-exception
            r7 = r0
            r0 = r8
            r8 = r7
            goto L_0x0062
        L_0x005b:
            r8 = move-exception
            goto L_0x0062
        L_0x005d:
            r8 = move-exception
            r9 = r0
            goto L_0x007b
        L_0x0060:
            r8 = move-exception
            r9 = r0
        L_0x0062:
            r8.printStackTrace()     // Catch:{ all -> 0x007a }
            if (r0 == 0) goto L_0x006f
            r0.close()     // Catch:{ Exception -> 0x006b }
            goto L_0x006f
        L_0x006b:
            r8 = move-exception
            r8.printStackTrace()
        L_0x006f:
            if (r9 == 0) goto L_0x0079
            r9.close()     // Catch:{ Exception -> 0x0075 }
            goto L_0x0079
        L_0x0075:
            r8 = move-exception
            r8.printStackTrace()
        L_0x0079:
            return r1
        L_0x007a:
            r8 = move-exception
        L_0x007b:
            if (r0 == 0) goto L_0x0085
            r0.close()     // Catch:{ Exception -> 0x0081 }
            goto L_0x0085
        L_0x0081:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0085:
            if (r9 == 0) goto L_0x008f
            r9.close()     // Catch:{ Exception -> 0x008b }
            goto L_0x008f
        L_0x008b:
            r9 = move-exception
            r9.printStackTrace()
        L_0x008f:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.util.FileUtil.copyFile(java.lang.String, java.lang.String):boolean");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0023, code lost:
        r3 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0025, code lost:
        r3 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0026, code lost:
        r2 = null;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0023 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:4:0x000c] */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0033 A[SYNTHETIC, Splitter:B:22:0x0033] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0039 A[SYNTHETIC, Splitter:B:25:0x0039] */
    /* JADX WARNING: Removed duplicated region for block: B:33:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String readLiteFile(java.lang.String r3) {
        /*
            boolean r0 = checkFileInvalid(r3)
            r1 = 0
            if (r0 == 0) goto L_0x003d
            java.io.FileInputStream r0 = new java.io.FileInputStream     // Catch:{ Exception -> 0x002c }
            r0.<init>(r3)     // Catch:{ Exception -> 0x002c }
            int r3 = r0.available()     // Catch:{ Exception -> 0x0025, all -> 0x0023 }
            byte[] r3 = new byte[r3]     // Catch:{ Exception -> 0x0025, all -> 0x0023 }
            r0.read(r3)     // Catch:{ Exception -> 0x0025, all -> 0x0023 }
            java.lang.String r2 = new java.lang.String     // Catch:{ Exception -> 0x0025, all -> 0x0023 }
            r2.<init>(r3)     // Catch:{ Exception -> 0x0025, all -> 0x0023 }
            r0.close()     // Catch:{ Exception -> 0x0021, all -> 0x0023 }
            r0.close()     // Catch:{ IOException -> 0x003e }
            goto L_0x003e
        L_0x0021:
            r3 = move-exception
            goto L_0x0027
        L_0x0023:
            r3 = move-exception
            goto L_0x0037
        L_0x0025:
            r3 = move-exception
            r2 = r1
        L_0x0027:
            r1 = r0
            goto L_0x002e
        L_0x0029:
            r3 = move-exception
            r0 = r1
            goto L_0x0037
        L_0x002c:
            r3 = move-exception
            r2 = r1
        L_0x002e:
            r3.printStackTrace()     // Catch:{ all -> 0x0029 }
            if (r1 == 0) goto L_0x003e
            r1.close()     // Catch:{ IOException -> 0x003e }
            goto L_0x003e
        L_0x0037:
            if (r0 == 0) goto L_0x003c
            r0.close()     // Catch:{ IOException -> 0x003c }
        L_0x003c:
            throw r3
        L_0x003d:
            r2 = r1
        L_0x003e:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.util.FileUtil.readLiteFile(java.lang.String):java.lang.String");
    }

    public static String getSoFileName(String str) {
        StringBuilder sb = new StringBuilder("lib");
        sb.append(str);
        sb.append(".so");
        return sb.toString();
    }

    public static boolean checkSDcard() {
        return "mounted".endsWith(Environment.getExternalStorageState());
    }

    public static boolean checkFileInvalid(String str) {
        File file = new File(str);
        return file.isFile() && file.length() > 0;
    }

    public static File getExternalFilesDir(Context context) {
        StringBuilder sb = new StringBuilder("/Android/data/");
        sb.append(context.getPackageName());
        sb.append("/files/");
        return new File(getExternalStorageDirectory(), sb.toString());
    }

    public static File getExternalCacheDir(Context context) {
        StringBuilder sb = new StringBuilder("/Android/data/");
        sb.append(context.getPackageName());
        sb.append("/cache/");
        return new File(getExternalStorageDirectory(), sb.toString());
    }

    public static String getExternalStorageDirectory() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    public static File getFilesDir(Context context) {
        return context.getFilesDir();
    }

    public static String getAbsolutePath(File file, String str) {
        return new File(file, str).getAbsolutePath();
    }

    public static Bitmap loadBitmap(@NonNull Context context, @NonNull String str, float f) {
        Options options = new Options();
        options.inScaled = true;
        options.inDensity = (int) (f * 160.0f);
        options.inTargetDensity = context.getResources().getDisplayMetrics().densityDpi;
        return BitmapFactory.decodeFile(str, options);
    }

    public static Bitmap loadBitmap(@NonNull Context context, @NonNull InputStream inputStream, float f) {
        Options options = new Options();
        options.inScaled = true;
        options.inDensity = (int) (f * 160.0f);
        options.inTargetDensity = context.getResources().getDisplayMetrics().densityDpi;
        return BitmapFactory.decodeStream(inputStream, null, options);
    }

    public static Bitmap loadBitmap(@NonNull Context context, @NonNull byte[] bArr, float f) {
        Bitmap bitmap;
        try {
            if (VERSION.SDK_INT >= 19) {
                Options options = new Options();
                options.inScaled = true;
                options.inDensity = (int) (f * 160.0f);
                options.inTargetDensity = context.getResources().getDisplayMetrics().densityDpi;
                bitmap = BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
            } else {
                float f2 = ((float) context.getResources().getDisplayMetrics().densityDpi) / (f * 160.0f);
                Bitmap decodeByteArray = BitmapFactory.decodeByteArray(bArr, 0, bArr.length);
                bitmap = Bitmap.createScaledBitmap(decodeByteArray, (int) (((float) decodeByteArray.getWidth()) * f2), (int) (((float) decodeByteArray.getHeight()) * f2), true);
                if (decodeByteArray != bitmap) {
                    decodeByteArray.recycle();
                }
            }
            return bitmap;
        } catch (Exception unused) {
            return null;
        }
    }

    public static byte[] getFileBytes(@NonNull String str) {
        FileInputStream fileInputStream;
        FileInputStream fileInputStream2 = null;
        try {
            fileInputStream = new FileInputStream(str);
            try {
                byte[] fileBytes = getFileBytes((InputStream) fileInputStream);
                CloseableUtils.close(fileInputStream);
                return fileBytes;
            } catch (Exception e) {
                e = e;
                try {
                    e.printStackTrace();
                    CloseableUtils.close(fileInputStream);
                    return null;
                } catch (Throwable th) {
                    th = th;
                    fileInputStream2 = fileInputStream;
                    CloseableUtils.close(fileInputStream2);
                    throw th;
                }
            }
        } catch (Exception e2) {
            e = e2;
            fileInputStream = null;
            e.printStackTrace();
            CloseableUtils.close(fileInputStream);
            return null;
        } catch (Throwable th2) {
            th = th2;
            CloseableUtils.close(fileInputStream2);
            throw th;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0030 A[SYNTHETIC, Splitter:B:24:0x0030] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x003d A[SYNTHETIC, Splitter:B:31:0x003d] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] getFileBytes(@android.support.annotation.NonNull java.io.InputStream r4) {
        /*
            r0 = 0
            r1 = 0
            java.io.ByteArrayOutputStream r2 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x002a }
            r2.<init>()     // Catch:{ Exception -> 0x002a }
            r1 = 1024(0x400, float:1.435E-42)
            byte[] r1 = new byte[r1]     // Catch:{ Exception -> 0x0024, all -> 0x0022 }
        L_0x000b:
            int r3 = r4.read(r1)     // Catch:{ Exception -> 0x0024, all -> 0x0022 }
            if (r3 < 0) goto L_0x0015
            r2.write(r1, r0, r3)     // Catch:{ Exception -> 0x0024, all -> 0x0022 }
            goto L_0x000b
        L_0x0015:
            byte[] r4 = r2.toByteArray()     // Catch:{ Exception -> 0x0024, all -> 0x0022 }
            r2.close()     // Catch:{ IOException -> 0x001d }
            goto L_0x0021
        L_0x001d:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0021:
            return r4
        L_0x0022:
            r4 = move-exception
            goto L_0x003b
        L_0x0024:
            r4 = move-exception
            r1 = r2
            goto L_0x002b
        L_0x0027:
            r4 = move-exception
            r2 = r1
            goto L_0x003b
        L_0x002a:
            r4 = move-exception
        L_0x002b:
            r4.printStackTrace()     // Catch:{ all -> 0x0027 }
            if (r1 == 0) goto L_0x0038
            r1.close()     // Catch:{ IOException -> 0x0034 }
            goto L_0x0038
        L_0x0034:
            r4 = move-exception
            r4.printStackTrace()
        L_0x0038:
            byte[] r4 = new byte[r0]
            return r4
        L_0x003b:
            if (r2 == 0) goto L_0x0045
            r2.close()     // Catch:{ IOException -> 0x0041 }
            goto L_0x0045
        L_0x0041:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0045:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.util.FileUtil.getFileBytes(java.io.InputStream):byte[]");
    }

    public static byte[] getAjxFileBytes(String str) throws FileNotFoundException {
        if (TextUtils.isEmpty(str)) {
            return new byte[0];
        }
        String substring = str.startsWith("path://") ? str.substring(7) : str;
        if (AjxFileInfo.isFileExists(substring)) {
            return AjxFileInfo.getFileDataByPath(substring);
        }
        StringBuilder sb = new StringBuilder("can not find file!!! path:[");
        sb.append(str);
        sb.append("]");
        throw new FileNotFoundException(sb.toString());
    }

    public static String getAjxFileString(String str) throws FileNotFoundException {
        byte[] ajxFileBytes = getAjxFileBytes(str);
        if (ajxFileBytes.length > 0) {
            try {
                return new String(ajxFileBytes, Charset.forName("utf-8"));
            } catch (UnsupportedOperationException e) {
                StringBuilder sb = new StringBuilder("getAjxFileString error!!! stack = \n");
                sb.append(Log.getStackTraceString(e));
                LogHelper.e(sb.toString());
            }
        }
        return "";
    }

    public static boolean makesureExist(File file) {
        if (file.exists()) {
            return true;
        }
        try {
            return file.mkdirs();
        } catch (SecurityException unused) {
            return file.exists();
        }
    }
}
