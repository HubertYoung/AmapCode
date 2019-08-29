package com.amap.bundle.blutils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.text.TextUtils;
import android.text.format.Formatter;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.amap.bundle.blutils.SdCardInfo.SDCardType;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.autonavi.minimap.offline.preference.PathPreference;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

public class FileUtil {
    public static final String FILE_PROVIDER = "com.amap.takephoto.fileprovider";
    @Deprecated
    public static final File PHOTO_DIR;
    private static volatile ArrayList<SdCardInfo> a;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory());
        sb.append("/DCIM/Camera");
        PHOTO_DIR = new File(sb.toString());
    }

    public static ArrayList<SdCardInfo> getSDCardInfoList(Context context, boolean z) {
        if (a != null) {
            if (z) {
                a = enumExternalSDcardInfo(context);
            }
            return a;
        }
        ArrayList<SdCardInfo> enumExternalSDcardInfo = enumExternalSDcardInfo(context);
        a = enumExternalSDcardInfo;
        return enumExternalSDcardInfo;
    }

    private static String a(Context context, boolean z, boolean z2) {
        ArrayList<SdCardInfo> sDCardInfoList = getSDCardInfoList(context, z2);
        for (int i = 0; i < sDCardInfoList.size(); i++) {
            SdCardInfo sdCardInfo = sDCardInfoList.get(i);
            if (sdCardInfo != null) {
                if (z) {
                    if (sdCardInfo.a != SDCardType.INNERCARD && sdCardInfo.a == SDCardType.EXTERNALCARD) {
                        return sdCardInfo.b;
                    }
                } else if (sdCardInfo.a != SDCardType.EXTERNALCARD && sdCardInfo.a == SDCardType.INNERCARD) {
                    return sdCardInfo.b;
                }
            }
        }
        return null;
    }

    public static void setMapBaseDBStorage(Context context, String str) {
        int i = VERSION.SDK_INT;
        String str2 = PathPreference.KEY_MAP_BASE_PATH;
        if (i > 18) {
            str2 = PathPreference.KEY_MAP_BASE_PATH_V44;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences("base_path", 0);
        if (sharedPreferences != null) {
            Editor edit = sharedPreferences.edit();
            if (edit != null) {
                edit.putString(str2, str).commit();
            }
        }
    }

    public static String getInternalSDCardPath(Context context) {
        return a(context, false, false);
    }

    public static String getInternalRefreshSDCardPath(Context context) {
        return a(context, false, true);
    }

    public static String getExternalSDCardPath(Context context) {
        return a(context, true, false);
    }

    public static String getExternalRefreshSDCardPath(Context context) {
        return a(context, true, true);
    }

    private static void a(String str) {
        AMapAppGlobal.getApplication().getSharedPreferences("base_path", 0).edit().putString("offline_data_storage_sdcard", str).commit();
    }

    public static String getInnerSDCardPath(Context context) {
        ArrayList<SdCardInfo> enumExternalSDcardInfo = enumExternalSDcardInfo(context);
        for (int i = 0; i < enumExternalSDcardInfo.size(); i++) {
            SdCardInfo sdCardInfo = enumExternalSDcardInfo.get(i);
            if (sdCardInfo != null && sdCardInfo.a != null && sdCardInfo.a == SDCardType.INNERCARD) {
                return sdCardInfo.b;
            }
        }
        return null;
    }

    public static String getExterSDCardPath(Context context) {
        ArrayList<SdCardInfo> enumExternalSDcardInfo = enumExternalSDcardInfo(context);
        for (int i = 0; i < enumExternalSDcardInfo.size(); i++) {
            SdCardInfo sdCardInfo = enumExternalSDcardInfo.get(i);
            if (sdCardInfo != null && sdCardInfo.a != null && sdCardInfo.a == SDCardType.EXTERNALCARD) {
                return sdCardInfo.b;
            }
        }
        return null;
    }

    public static boolean iSHasSdcardPath(Context context) {
        ArrayList<SdCardInfo> enumExternalSDcardInfo = enumExternalSDcardInfo(context);
        if (enumExternalSDcardInfo == null || enumExternalSDcardInfo.size() == 0 || enumExternalSDcardInfo == null || enumExternalSDcardInfo.size() <= 0) {
            return false;
        }
        boolean z = false;
        for (int i = 0; i < enumExternalSDcardInfo.size(); i++) {
            SdCardInfo sdCardInfo = enumExternalSDcardInfo.get(i);
            if (!(sdCardInfo == null || sdCardInfo.b == null)) {
                File file = new File(sdCardInfo.b);
                if (file.exists() && file.isDirectory()) {
                    z = file.canWrite() ? z | true : z | false;
                }
            }
        }
        return z;
    }

    public static String getCurrentOfflineDataStorage() {
        return AMapAppGlobal.getApplication().getSharedPreferences("base_path", 0).getString("offline_data_storage", "");
    }

    @Deprecated
    public static String getCurrentOfflineDataStorage(Context context) {
        return getCurrentOfflineDataStorage();
    }

    public static boolean checkPathIsCanUse(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        File file = new File(str);
        if (!file.exists() || !file.isDirectory() || !file.canWrite() || !file.canRead()) {
            return false;
        }
        return true;
    }

    public static String canWritePath(Context context) {
        ArrayList<SdCardInfo> enumExternalSDcardInfo = enumExternalSDcardInfo(context);
        if (enumExternalSDcardInfo == null || enumExternalSDcardInfo.size() == 0 || enumExternalSDcardInfo == null || enumExternalSDcardInfo.size() <= 0) {
            return null;
        }
        for (int i = 0; i < enumExternalSDcardInfo.size(); i++) {
            SdCardInfo sdCardInfo = enumExternalSDcardInfo.get(i);
            if (!TextUtils.isEmpty(sdCardInfo.b)) {
                File file = new File(sdCardInfo.b);
                if (file.isDirectory() && file.canWrite()) {
                    return sdCardInfo.b;
                }
            }
        }
        return null;
    }

    public static String getMapBaseDBStorage(Context context) {
        int i = VERSION.SDK_INT;
        String str = PathPreference.KEY_MAP_BASE_PATH;
        if (i > 18) {
            str = PathPreference.KEY_MAP_BASE_PATH_V44;
        }
        return AMapAppGlobal.getApplication().getSharedPreferences("base_path", 0).getString(str, "");
    }

    public static boolean getPathIsCanWrite(String str) {
        if (str != null && str.length() > 2) {
            File file = new File(str);
            if (file.isDirectory()) {
                if (file.canWrite()) {
                    return true;
                }
            } else if (file.delete() && file.mkdirs() && file.canWrite()) {
                return true;
            }
        }
        return false;
    }

    public static void setClearDataOperation(boolean z) {
        SharedPreferences sharedPreferences = AMapAppGlobal.getApplication().getSharedPreferences("base_path", 0);
        if (VERSION.SDK_INT >= 9) {
            sharedPreferences.edit().putBoolean("clear_local_data", z).apply();
        } else {
            sharedPreferences.edit().putBoolean("clear_local_data", z).commit();
        }
    }

    public static boolean getIsClearDataOperation() {
        return AMapAppGlobal.getApplication().getSharedPreferences("base_path", 0).getBoolean("clear_local_data", false);
    }

    public static String getMapBaseStorage() {
        return getMapBaseStorage(AMapAppGlobal.getApplication());
    }

    public static String getMapBaseStorage(Context context) {
        int i = VERSION.SDK_INT;
        String str = PathPreference.KEY_MAP_BASE_PATH;
        if (i > 18) {
            str = PathPreference.KEY_MAP_BASE_PATH_V44;
        }
        SharedPreferences sharedPreferences = AMapAppGlobal.getApplication().getSharedPreferences("base_path", 0);
        String string = sharedPreferences.getString(str, "");
        if (string != null && string.length() > 2) {
            File file = new File(string);
            if (file.isDirectory()) {
                if (file.canWrite()) {
                    c(string);
                    return string;
                }
                String file2 = getCacheDir().toString();
                if (file2 != null && file2.length() > 2 && new File(file2).isDirectory()) {
                    return file2;
                }
            }
        }
        String externalStroragePath = getExternalStroragePath(AMapAppGlobal.getApplication());
        if (externalStroragePath == null || externalStroragePath.length() <= 2 || !new File(externalStroragePath).isDirectory()) {
            String file3 = getCacheDir().toString();
            return (file3 == null || file3.length() <= 2 || new File(file3).isDirectory()) ? file3 : file3;
        }
        Editor edit = sharedPreferences.edit();
        edit.putString(str, externalStroragePath);
        if (VERSION.SDK_INT >= 9) {
            edit.apply();
        } else {
            edit.commit();
        }
        c(externalStroragePath);
        return externalStroragePath;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x009d, code lost:
        if (r1 > 18) goto L_0x00a0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        r0 = r0.getExternalFilesDirs(null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x00a5, code lost:
        if (r0 == null) goto L_0x00c8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x00a8, code lost:
        if (r0.length <= 0) goto L_0x00c8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00aa, code lost:
        r2 = r0.length;
        r3 = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x00ac, code lost:
        if (r6 >= r2) goto L_0x00c6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00ae, code lost:
        r4 = r0[r6];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00b0, code lost:
        if (r4 == null) goto L_0x00c3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00b2, code lost:
        r4 = r4.getAbsolutePath();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00ba, code lost:
        if (android.text.TextUtils.isEmpty(r4) != false) goto L_0x00c3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00c0, code lost:
        if (r4.contains(r14) == false) goto L_0x00c3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00c2, code lost:
        r3 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00c3, code lost:
        r6 = r6 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00c6, code lost:
        r2 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00c8, code lost:
        r2 = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00c9, code lost:
        r14 = r2;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00d9 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00e9 A[ADDED_TO_REGION, Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f7 }] */
    /* JADX WARNING: Removed duplicated region for block: B:59:? A[ExcHandler: IllegalAccessException | NoSuchMethodException | InvocationTargetException (unused java.lang.Throwable), SYNTHETIC, Splitter:B:3:0x000b] */
    @android.annotation.SuppressLint({"NewApi"})
    @android.annotation.TargetApi(19)
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getExternalStroragePath(android.content.Context r17) {
        /*
            r0 = r17
            int r1 = android.os.Build.VERSION.SDK_INT
            r3 = 12
            if (r1 < r3) goto L_0x00f7
            java.lang.String r3 = "storage"
            java.lang.Object r3 = r0.getSystemService(r3)     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f7 }
            android.os.storage.StorageManager r3 = (android.os.storage.StorageManager) r3     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f7 }
            java.lang.Class<android.os.storage.StorageManager> r4 = android.os.storage.StorageManager.class
            java.lang.String r5 = "getVolumeList"
            r6 = 0
            java.lang.Class[] r7 = new java.lang.Class[r6]     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f7 }
            java.lang.reflect.Method r4 = r4.getMethod(r5, r7)     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f7 }
            java.lang.Class<android.os.storage.StorageManager> r5 = android.os.storage.StorageManager.class
            java.lang.String r7 = "getVolumeState"
            r8 = 1
            java.lang.Class[] r9 = new java.lang.Class[r8]     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f7 }
            java.lang.Class<java.lang.String> r10 = java.lang.String.class
            r9[r6] = r10     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f7 }
            java.lang.reflect.Method r5 = r5.getMethod(r7, r9)     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f7 }
            java.lang.Object[] r7 = new java.lang.Object[r6]     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f7 }
            java.lang.Object r4 = r4.invoke(r3, r7)     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f7 }
            java.lang.Object[] r4 = (java.lang.Object[]) r4     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f7 }
            java.lang.Boolean r7 = java.lang.Boolean.FALSE     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f7 }
            java.lang.String r7 = ""
            java.lang.String r9 = ""
            int r10 = r4.length     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f7 }
            r11 = r9
            r9 = r7
            r7 = 0
        L_0x003d:
            if (r7 >= r10) goto L_0x00d4
            r13 = r4[r7]     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f7 }
            java.lang.Class r14 = r13.getClass()     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f7 }
            java.lang.String r15 = "getPath"
            java.lang.Class[] r2 = new java.lang.Class[r6]     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f7 }
            java.lang.reflect.Method r2 = r14.getMethod(r15, r2)     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f7 }
            java.lang.Class r14 = r13.getClass()     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f7 }
            java.lang.String r15 = "isRemovable"
            java.lang.Class[] r12 = new java.lang.Class[r6]     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f7 }
            java.lang.reflect.Method r12 = r14.getMethod(r15, r12)     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f7 }
            java.lang.Object[] r14 = new java.lang.Object[r6]     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f7 }
            java.lang.Object r14 = r2.invoke(r13, r14)     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f7 }
            java.lang.String r14 = (java.lang.String) r14     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f7 }
            java.lang.Object[] r15 = new java.lang.Object[r8]     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f7 }
            java.lang.Object[] r8 = new java.lang.Object[r6]     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f7 }
            java.lang.Object r2 = r2.invoke(r13, r8)     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f7 }
            r15[r6] = r2     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f7 }
            java.lang.Object r2 = r5.invoke(r3, r15)     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f7 }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f7 }
            java.lang.Object[] r8 = new java.lang.Object[r6]     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f7 }
            java.lang.Object r8 = r12.invoke(r13, r8)     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f7 }
            java.lang.Boolean r8 = (java.lang.Boolean) r8     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f7 }
            if (r14 == 0) goto L_0x0089
            java.util.Locale r12 = java.util.Locale.US     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f7 }
            java.lang.String r12 = r14.toLowerCase(r12)     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f7 }
            java.lang.String r13 = "private"
            boolean r12 = r12.contains(r13)     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f7 }
            if (r12 != 0) goto L_0x00cf
        L_0x0089:
            boolean r8 = r8.booleanValue()     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f7 }
            if (r8 == 0) goto L_0x00cd
            if (r14 == 0) goto L_0x00cf
            if (r2 == 0) goto L_0x00cf
            java.lang.String r8 = "mounted"
            boolean r2 = r2.equals(r8)     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f7 }
            if (r2 == 0) goto L_0x00cf
            r2 = 18
            if (r1 > r2) goto L_0x00a0
            goto L_0x00ca
        L_0x00a0:
            r2 = 0
            java.io.File[] r0 = r0.getExternalFilesDirs(r2)     // Catch:{ Throwable -> 0x00ca, IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f7, IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f7 }
            if (r0 == 0) goto L_0x00c8
            int r2 = r0.length     // Catch:{ Throwable -> 0x00ca, IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f7, IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f7 }
            if (r2 <= 0) goto L_0x00c8
            int r2 = r0.length     // Catch:{ Throwable -> 0x00ca, IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f7, IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f7 }
            r3 = r14
        L_0x00ac:
            if (r6 >= r2) goto L_0x00c6
            r4 = r0[r6]     // Catch:{ Throwable -> 0x00ca, IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f7, IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f7 }
            if (r4 == 0) goto L_0x00c3
            java.lang.String r4 = r4.getAbsolutePath()     // Catch:{ Throwable -> 0x00ca, IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f7, IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f7 }
            boolean r5 = android.text.TextUtils.isEmpty(r4)     // Catch:{ Throwable -> 0x00ca, IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f7, IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f7 }
            if (r5 != 0) goto L_0x00c3
            boolean r5 = r4.contains(r14)     // Catch:{ Throwable -> 0x00ca, IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f7, IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f7 }
            if (r5 == 0) goto L_0x00c3
            r3 = r4
        L_0x00c3:
            int r6 = r6 + 1
            goto L_0x00ac
        L_0x00c6:
            r2 = r3
            goto L_0x00c9
        L_0x00c8:
            r2 = r14
        L_0x00c9:
            r14 = r2
        L_0x00ca:
            r0 = 18
            goto L_0x00d7
        L_0x00cd:
            r11 = r2
            r9 = r14
        L_0x00cf:
            int r7 = r7 + 1
            r8 = 1
            goto L_0x003d
        L_0x00d4:
            r0 = 18
            r14 = 0
        L_0x00d7:
            if (r1 > r0) goto L_0x00e9
            if (r14 != 0) goto L_0x00e8
            if (r9 == 0) goto L_0x00e8
            if (r11 == 0) goto L_0x00e8
            java.lang.String r0 = "mounted"
            boolean r0 = r11.equals(r0)     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f7 }
            if (r0 == 0) goto L_0x00e8
            r14 = r9
        L_0x00e8:
            return r14
        L_0x00e9:
            if (r9 == 0) goto L_0x00f6
            if (r11 == 0) goto L_0x00f6
            java.lang.String r0 = "mounted"
            boolean r0 = r11.equals(r0)     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00f7 }
            if (r0 == 0) goto L_0x00f6
            r14 = r9
        L_0x00f6:
            return r14
        L_0x00f7:
            java.lang.String r0 = a()
            java.lang.String r1 = "mounted"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x010c
            java.io.File r0 = android.os.Environment.getExternalStorageDirectory()
            java.lang.String r0 = r0.toString()
            return r0
        L_0x010c:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.bundle.blutils.FileUtil.getExternalStroragePath(android.content.Context):java.lang.String");
    }

    @SuppressLint({"NewApi"})
    @TargetApi(19)
    public static ArrayList<SdCardInfo> enumExternalSDcardInfo(Context context) {
        a = new ArrayList<>();
        if (context == null) {
            return a;
        }
        int i = VERSION.SDK_INT;
        if (i >= 12) {
            try {
                StorageManager storageManager = (StorageManager) context.getSystemService("storage");
                Method method = StorageManager.class.getMethod("getVolumeList", new Class[0]);
                Method method2 = StorageManager.class.getMethod("getVolumeState", new Class[]{String.class});
                Object[] objArr = (Object[]) method.invoke(storageManager, new Object[0]);
                for (Object obj : objArr) {
                    Method method3 = obj.getClass().getMethod("getPath", new Class[0]);
                    String str = (String) method3.invoke(obj, new Object[0]);
                    String str2 = (String) method2.invoke(storageManager, new Object[]{method3.invoke(obj, new Object[0])});
                    boolean booleanValue = ((Boolean) obj.getClass().getMethod("isRemovable", new Class[0]).invoke(obj, new Object[0])).booleanValue();
                    if (!(str == null || str2 == null || !str2.equals("mounted"))) {
                        if (i > 18 && objArr.length > 1 && booleanValue) {
                            SdCardInfo a2 = a(context, a(context, str), SDCardType.EXTERNALCARD);
                            if (a2 != null) {
                                a.add(a2);
                            }
                            a(str);
                        } else if (booleanValue) {
                            SdCardInfo a3 = a(context, str, SDCardType.EXTERNALCARD);
                            if (a3 != null) {
                                a.add(a3);
                            }
                            a(str);
                        } else {
                            SdCardInfo a4 = a(context, str, SDCardType.INNERCARD);
                            if (a4 != null) {
                                a.add(a4);
                            }
                        }
                    }
                }
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException unused) {
            }
        } else if (a().equals("mounted")) {
            a.add(new SdCardInfo(SDCardType.INNERCARD, Environment.getExternalStorageDirectory().toString()));
        }
        return a;
    }

    private static SdCardInfo a(Context context, String str, SDCardType sDCardType) {
        long j;
        long j2;
        long j3;
        SdCardInfo sdCardInfo = new SdCardInfo(sDCardType, str);
        try {
            StatFs statFs = new StatFs(str);
            if (VERSION.SDK_INT >= 18) {
                j3 = statFs.getBlockSizeLong();
                j2 = statFs.getBlockCountLong();
                j = statFs.getAvailableBlocksLong();
            } else {
                j3 = (long) statFs.getBlockSize();
                j2 = (long) statFs.getBlockCount();
                j = (long) statFs.getAvailableBlocks();
            }
            if (j > j2) {
                j = j2;
            }
            sdCardInfo.c = b(Formatter.formatFileSize(context, j2 * j3));
            sdCardInfo.e = b(Formatter.formatFileSize(context, j * j3));
            sdCardInfo.d = b(Formatter.formatFileSize(context, (j2 - j) * j3));
            return sdCardInfo;
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    private static String b(String str) {
        return str.replace("吉字节", "GB");
    }

    private static String a(Context context, String str) {
        String str2;
        if (VERSION.SDK_INT >= 19) {
            File[] externalFilesDirs = context.getExternalFilesDirs(null);
            if (externalFilesDirs == null) {
                return str;
            }
            int length = externalFilesDirs.length;
            int i = 0;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    str2 = str;
                    break;
                }
                File file = externalFilesDirs[i2];
                if (file != null) {
                    str2 = file.getAbsolutePath();
                    if (!TextUtils.isEmpty(str2) && str2.startsWith(str)) {
                        break;
                    }
                }
                i2++;
            }
            if (!TextUtils.equals(str2, str)) {
                return str2;
            }
            try {
                File[] fileArr = (File[]) Environment.class.getMethod("buildExternalStorageAppFilesDirs", new Class[]{String.class}).invoke(null, new Object[]{context.getPackageName()});
                if (fileArr != null) {
                    int length2 = fileArr.length;
                    while (true) {
                        if (i >= length2) {
                            str = str2;
                            break;
                        }
                        File file2 = fileArr[i];
                        if (file2 != null) {
                            String absolutePath = file2.getAbsolutePath();
                            if (!TextUtils.isEmpty(absolutePath) && absolutePath.startsWith(str)) {
                                str = absolutePath;
                                break;
                            }
                        }
                        i++;
                    }
                } else {
                    return str2;
                }
            } catch (Exception unused) {
                return str2;
            }
        }
        return str;
    }

    public static void writeTextFile(File file, String str) {
        WriteLock writeLock = new ReentrantReadWriteLock().writeLock();
        writeLock.lock();
        FileOutputStream fileOutputStream = null;
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
            }
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            byte[] bytes = str.getBytes();
            FileOutputStream fileOutputStream2 = new FileOutputStream(file);
            try {
                fileOutputStream2.write(bytes);
                fileOutputStream2.flush();
                writeLock.unlock();
                ahe.a((Closeable) fileOutputStream2);
            } catch (Exception e) {
                e = e;
                fileOutputStream = fileOutputStream2;
                try {
                    kf.a((Throwable) e);
                    writeLock.unlock();
                    ahe.a((Closeable) fileOutputStream);
                } catch (Throwable th) {
                    th = th;
                    writeLock.unlock();
                    ahe.a((Closeable) fileOutputStream);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                fileOutputStream = fileOutputStream2;
                writeLock.unlock();
                ahe.a((Closeable) fileOutputStream);
                throw th;
            }
        } catch (Exception e2) {
            e = e2;
            kf.a((Throwable) e);
            writeLock.unlock();
            ahe.a((Closeable) fileOutputStream);
        }
    }

    public static String getTmpFilePath(Context context) {
        if (a().equals("mounted")) {
            StringBuilder sb = new StringBuilder();
            sb.append(Environment.getExternalStorageDirectory());
            sb.append("/autonavi/tmp");
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(getFilesDir());
        sb2.append("/tmp");
        return sb2.toString();
    }

    private static void c(String str) {
        try {
            d(str);
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("/autonavi/.nomedia");
            File file = new File(sb.toString());
            if (!file.exists()) {
                file.createNewFile();
            }
            if (file.lastModified() > 0) {
                file.setLastModified(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void d(String str) {
        try {
            File file = new File(str);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception unused) {
        }
    }

    public static String readData(String str) {
        FileInputStream fileInputStream;
        ReadLock readLock = new ReentrantReadWriteLock().readLock();
        File file = new File(str);
        String str2 = "";
        FileInputStream fileInputStream2 = null;
        try {
            readLock.lock();
            if (file.exists()) {
                fileInputStream = new FileInputStream(file);
                try {
                    byte[] bArr = new byte[fileInputStream.available()];
                    fileInputStream.read(bArr);
                    str2 = new String(bArr, "UTF-8");
                } catch (FileNotFoundException | IOException unused) {
                } catch (Throwable th) {
                    th = th;
                    readLock.unlock();
                    ahe.a((Closeable) fileInputStream);
                    throw th;
                }
                fileInputStream2 = fileInputStream;
            }
        } catch (FileNotFoundException | IOException unused2) {
        } catch (Throwable th2) {
            th = th2;
            fileInputStream = null;
            readLock.unlock();
            ahe.a((Closeable) fileInputStream);
            throw th;
        }
        readLock.unlock();
        ahe.a((Closeable) fileInputStream2);
        return str2;
    }

    public static void saveLogToFile(String str, String str2) {
        String appSDCardFileDir = getAppSDCardFileDir();
        if (appSDCardFileDir != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(appSDCardFileDir);
            sb.append("/");
            sb.append(str2);
            String sb2 = sb.toString();
            try {
                File parentFile = new File(sb2).getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                FileWriter fileWriter = new FileWriter(sb2, true);
                fileWriter.write(str);
                fileWriter.write("\r\n-------------------\r\n");
                fileWriter.close();
            } catch (Exception unused) {
            }
        }
    }

    public static void saveLogToPath(String str, String str2) {
        try {
            File parentFile = new File(str2).getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            FileWriter fileWriter = new FileWriter(str2, true);
            fileWriter.write(str);
            fileWriter.write("\r\n-------------------\r\n");
            fileWriter.close();
        } catch (Exception unused) {
        }
    }

    public static String getAppSDCardFileDir() {
        File file;
        if (a().equals("mounted")) {
            file = new File(Environment.getExternalStorageDirectory(), "autonavi");
            if (!file.exists()) {
                file.mkdir();
            }
        } else {
            file = null;
        }
        if (file == null) {
            return null;
        }
        return file.toString();
    }

    public static String saveBitmap(Bitmap bitmap) throws IOException {
        return saveBitmap(bitmap, 90);
    }

    public static String saveBitmap(Bitmap bitmap, int i) throws IOException {
        FileOutputStream fileOutputStream = null;
        if (bitmap == null) {
            return null;
        }
        String appSDCardFileDir = getAppSDCardFileDir();
        if (appSDCardFileDir == null) {
            appSDCardFileDir = Environment.getExternalStorageDirectory().toString();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(appSDCardFileDir);
        sb.append("/saved_images");
        File file = new File(sb.toString());
        if (!file.exists()) {
            file.mkdirs();
        }
        StringBuilder sb2 = new StringBuilder("Image-");
        sb2.append(System.currentTimeMillis());
        sb2.append("-");
        sb2.append(i);
        sb2.append(".jpg");
        File file2 = new File(file, sb2.toString());
        if (file2.exists()) {
            file2.delete();
        }
        try {
            file2.createNewFile();
            FileOutputStream fileOutputStream2 = new FileOutputStream(file2);
            if (i <= 0 || i > 100) {
                bitmap.compress(CompressFormat.JPEG, 90, fileOutputStream2);
            } else {
                try {
                    bitmap.compress(CompressFormat.JPEG, i, fileOutputStream2);
                } catch (IOException e) {
                    e = e;
                    fileOutputStream = fileOutputStream2;
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    fileOutputStream = fileOutputStream2;
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.flush();
                            fileOutputStream.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                    throw th;
                }
            }
            try {
                fileOutputStream2.flush();
                fileOutputStream2.close();
            } catch (IOException e3) {
                e3.printStackTrace();
            }
            return file2.getAbsolutePath();
        } catch (IOException e4) {
            e = e4;
            throw e;
        }
    }

    public static File getCacheDir() {
        Application application = AMapAppGlobal.getApplication();
        File cacheDir = application.getCacheDir();
        if (cacheDir == null) {
            cacheDir = application.getDir("cache", 0);
        }
        if (cacheDir == null) {
            StringBuilder sb = new StringBuilder("/data/data/");
            sb.append(application.getPackageName());
            sb.append("/app_cache");
            cacheDir = new File(sb.toString());
        }
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        return cacheDir;
    }

    public static File getFilesDir() {
        Application application = AMapAppGlobal.getApplication();
        File filesDir = application.getFilesDir();
        if (filesDir == null) {
            filesDir = application.getDir(AutoJsonUtils.JSON_FILES, 0);
        }
        if (filesDir == null) {
            StringBuilder sb = new StringBuilder("/data/data/");
            sb.append(application.getPackageName());
            sb.append("/app_files");
            filesDir = new File(sb.toString());
        }
        if (!filesDir.exists()) {
            filesDir.mkdirs();
        }
        return filesDir;
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x004b A[SYNTHETIC, Splitter:B:20:0x004b] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0059 A[SYNTHETIC, Splitter:B:27:0x0059] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void writeStrToFileByAppend(java.lang.String r5, java.lang.String r6) {
        /*
            java.util.concurrent.locks.ReentrantReadWriteLock r0 = new java.util.concurrent.locks.ReentrantReadWriteLock
            r0.<init>()
            java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r0 = r0.writeLock()
            r0.lock()
            r1 = 0
            java.io.RandomAccessFile r2 = new java.io.RandomAccessFile     // Catch:{ Exception -> 0x0045 }
            java.lang.String r3 = "rw"
            r2.<init>(r5, r3)     // Catch:{ Exception -> 0x0045 }
            long r3 = r2.length()     // Catch:{ Exception -> 0x003f, all -> 0x003d }
            r2.seek(r3)     // Catch:{ Exception -> 0x003f, all -> 0x003d }
            java.lang.String r5 = "UTF-8"
            java.lang.String r5 = java.net.URLEncoder.encode(r6, r5)     // Catch:{ Exception -> 0x003f, all -> 0x003d }
            java.lang.String r6 = "UTF-8"
            java.lang.String r5 = java.net.URLDecoder.decode(r5, r6)     // Catch:{ Exception -> 0x003f, all -> 0x003d }
            java.lang.String r6 = "UTF-8"
            byte[] r5 = r5.getBytes(r6)     // Catch:{ Exception -> 0x003f, all -> 0x003d }
            r2.write(r5)     // Catch:{ Exception -> 0x003f, all -> 0x003d }
            r2.close()     // Catch:{ IOException -> 0x0035 }
            goto L_0x0039
        L_0x0035:
            r5 = move-exception
            r5.printStackTrace()
        L_0x0039:
            r0.unlock()
            return
        L_0x003d:
            r5 = move-exception
            goto L_0x0057
        L_0x003f:
            r5 = move-exception
            r1 = r2
            goto L_0x0046
        L_0x0042:
            r5 = move-exception
            r2 = r1
            goto L_0x0057
        L_0x0045:
            r5 = move-exception
        L_0x0046:
            r5.printStackTrace()     // Catch:{ all -> 0x0042 }
            if (r1 == 0) goto L_0x0053
            r1.close()     // Catch:{ IOException -> 0x004f }
            goto L_0x0053
        L_0x004f:
            r5 = move-exception
            r5.printStackTrace()
        L_0x0053:
            r0.unlock()
            return
        L_0x0057:
            if (r2 == 0) goto L_0x0061
            r2.close()     // Catch:{ IOException -> 0x005d }
            goto L_0x0061
        L_0x005d:
            r6 = move-exception
            r6.printStackTrace()
        L_0x0061:
            r0.unlock()
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.bundle.blutils.FileUtil.writeStrToFileByAppend(java.lang.String, java.lang.String):void");
    }

    public static void writeLogToFile(String str, String str2) {
        if (!TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str)) {
            String b = PathManager.a().b();
            if (checkPathIsCanUse(b)) {
                StringBuilder sb = new StringBuilder();
                sb.append(b);
                sb.append("/autonavi/");
                final File file = new File(sb.toString(), str2);
                String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date());
                StringBuilder sb2 = new StringBuilder();
                sb2.append(format);
                sb2.append(MergeUtil.SEPARATOR_KV);
                sb2.append(str);
                sb2.append("\r\n-------------------\r\n");
                try {
                    final String str3 = new String(sb2.toString().getBytes(), "utf-8");
                    new Thread(new Runnable() {
                        public final void run() {
                            FileUtil.writeStrToFileByAppend(file.getAbsolutePath(), str3);
                        }
                    }).start();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static String a() {
        try {
            return Environment.getExternalStorageState();
        } catch (Exception unused) {
            return "";
        }
    }

    public static void writeDatasToFile(String str, byte[] bArr) {
        WriteLock writeLock = new ReentrantReadWriteLock().writeLock();
        writeLock.lock();
        FileOutputStream fileOutputStream = null;
        if (bArr != null) {
            try {
                if (bArr.length != 0) {
                    File file = new File(str);
                    File parentFile = file.getParentFile();
                    if (!parentFile.exists()) {
                        parentFile.mkdirs();
                    }
                    if (file.exists()) {
                        file.delete();
                    }
                    file.createNewFile();
                    FileOutputStream fileOutputStream2 = new FileOutputStream(file);
                    try {
                        fileOutputStream2.write(bArr);
                        fileOutputStream2.flush();
                        writeLock.unlock();
                        ahe.a((Closeable) fileOutputStream2);
                        return;
                    } catch (Exception e) {
                        fileOutputStream = fileOutputStream2;
                        e = e;
                        try {
                            kf.a((Throwable) e);
                            writeLock.unlock();
                            ahe.a((Closeable) fileOutputStream);
                            return;
                        } catch (Throwable th) {
                            th = th;
                            writeLock.unlock();
                            ahe.a((Closeable) fileOutputStream);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        fileOutputStream = fileOutputStream2;
                        th = th2;
                        writeLock.unlock();
                        ahe.a((Closeable) fileOutputStream);
                        throw th;
                    }
                }
            } catch (Exception e2) {
                e = e2;
                kf.a((Throwable) e);
                writeLock.unlock();
                ahe.a((Closeable) fileOutputStream);
                return;
            }
        }
        writeLock.unlock();
        ahe.a((Closeable) null);
    }
}
