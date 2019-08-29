package com.autonavi.minimap.offline.nativesupport;

import android.os.Build.VERSION;
import android.text.TextUtils;
import com.amap.bundle.blutils.PathManager;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.offline.nativesupport.compat.DataCompat;
import com.autonavi.minimap.offline.nativesupport.compat.DataCompat.OldDbName;
import com.autonavi.minimap.offline.utils.OfflineSpUtil;
import com.autonavi.minimap.offline.utils.OfflineUtil;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class DataCompatHelper {
    /* access modifiers changed from: private */
    public static final String[] AUTONAVI_DATAS = {AUTONAVI_DATA_VMAP_VMAPZIP, AUTONAVI_DATA_VMAP, AUTONAVI_DATA_POIV5, AUTONAVI_DATA_ROADENLARGE, AUTONAVI_DATA_400_400, AUTONAVI_DATA_MAP_MAPZIP, AUTONAVI_DATA_MAP, AUTONAVI_DATA_POI, AUTONAVI_DATA_BUS, AUTONAVI_DATA_ROUTE_ZIP, AUTONAVI_DATA_ROUTE, AUTONAVI_DATA_CROSSS, AUTONAVI_DATA_3D_CROSSS};
    private static final String AUTONAVI_DATA_3D_CROSSS = "autonavi/data/3dcross";
    private static final String AUTONAVI_DATA_400_400 = "autonavi/400_400";
    private static final String AUTONAVI_DATA_BUS = "autonavi/data/bus";
    private static final String AUTONAVI_DATA_CROSSS = "autonavi/data/cross";
    private static final String AUTONAVI_DATA_MAP = "autonavi/data/map";
    private static final String AUTONAVI_DATA_MAP_MAPZIP = "autonavi/data/map/mapzip";
    private static final String AUTONAVI_DATA_POI = "autonavi/data/poi";
    private static final String AUTONAVI_DATA_POIV5 = "autonavi/data/poiv5";
    private static final String AUTONAVI_DATA_ROADENLARGE = "autonavi/data/roadenlarge";
    private static final String AUTONAVI_DATA_ROUTE = "autonavi/data/route";
    private static final String AUTONAVI_DATA_ROUTE_ZIP = "autonavi/data/route/routezip";
    private static final String AUTONAVI_DATA_VMAP = "autonavi/data/vmap";
    private static final String AUTONAVI_DATA_VMAP_VMAPZIP = "autonavi/data/vmap/vmapzip";
    /* access modifiers changed from: private */
    public static final String[] DB_NAMES = {"OfflineDbV6.db", OldDbName.DB_OFFLINE_NAME_V5, OldDbName.DB_OFFLINE_NAME_V4, OldDbName.DB_OFFLINE_NAME_V3};
    private static final String DB_OFFLINE_SDCARD_DIR = "/autonavi/db/";

    public AmapCompatData getCompatData() {
        AmapCompatData amapCompatData;
        String[] scanOldDatabasesPath = scanOldDatabasesPath();
        int length = scanOldDatabasesPath.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                amapCompatData = null;
                break;
            }
            String str = scanOldDatabasesPath[i];
            if (!TextUtils.isEmpty(str)) {
                File file = new File(str);
                if (file.exists() && file.canRead()) {
                    DataCompat createDataCompat = DataCompat.createDataCompat(str);
                    if (createDataCompat != null) {
                        amapCompatData = createDataCompat.check();
                        break;
                    }
                }
            }
            i++;
        }
        if (amapCompatData != null) {
            if (amapCompatData.result == 2) {
                OfflineSpUtil.setAe8RedNeedShowSp(true);
            }
            deleteUselessFiles(amapCompatData.result);
        }
        return amapCompatData;
    }

    private void deleteUselessFiles(final int i) {
        new Thread(new Runnable() {
            public void run() {
                String[] access$100;
                String[] access$000;
                if (i == 2) {
                    String b = PathManager.a().b();
                    if (!TextUtils.isEmpty(b)) {
                        for (String str : DataCompatHelper.AUTONAVI_DATAS) {
                            if (str != null) {
                                StringBuilder sb = new StringBuilder();
                                sb.append(b);
                                sb.append(File.separator);
                                sb.append(str);
                                DataCompatHelper.deleteFolder(sb.toString(), true);
                            }
                        }
                    } else {
                        return;
                    }
                }
                for (String str2 : DataCompatHelper.DB_NAMES) {
                    String stringMD5 = OfflineUtil.getStringMD5(PathManager.a().b());
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(DataCompatHelper.this.getDatabasesDirPath());
                    sb2.append(stringMD5);
                    sb2.append(str2);
                    String sb3 = sb2.toString();
                    if (!TextUtils.isEmpty(sb3)) {
                        File file = new File(sb3);
                        if (file.exists()) {
                            file.delete();
                        }
                    }
                    String backupSdcardPath = DataCompatHelper.getBackupSdcardPath(str2);
                    if (!TextUtils.isEmpty(backupSdcardPath)) {
                        File file2 = new File(backupSdcardPath);
                        if (file2.exists()) {
                            file2.delete();
                        }
                    }
                }
            }
        }, "delteUselessFiles").start();
    }

    private String[] scanOldDatabasesPath() {
        String databasesDirPath = getDatabasesDirPath();
        String[] strArr = {databasesDirPath, getBackupSdcardDirPath()};
        String[] strArr2 = new String[(DB_NAMES.length * 2)];
        String a = agy.a(PathManager.a().b());
        String[] strArr3 = DB_NAMES;
        int length = strArr3.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            String str = strArr3[i];
            int i3 = i2;
            for (int i4 = 0; i4 < 2; i4++) {
                String str2 = strArr[i4];
                if (!TextUtils.isEmpty(str2)) {
                    if (!str2.equals(databasesDirPath) || TextUtils.isEmpty(a) || "OfflineDbV6.db".equals(str)) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(str2);
                        sb.append(str);
                        strArr2[i3] = sb.toString();
                    } else {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(str2);
                        sb2.append(a);
                        sb2.append(str);
                        strArr2[i3] = sb2.toString();
                    }
                    i3++;
                }
            }
            i++;
            i2 = i3;
        }
        return strArr2;
    }

    /* access modifiers changed from: private */
    public String getDatabasesDirPath() {
        if (VERSION.SDK_INT >= 17) {
            StringBuilder sb = new StringBuilder();
            sb.append(AMapAppGlobal.getApplication().getApplicationContext().getApplicationInfo().dataDir);
            sb.append("/databases/");
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder("/data/data/");
        sb2.append(AMapAppGlobal.getApplication().getApplicationContext().getPackageName());
        sb2.append("/databases/");
        return sb2.toString();
    }

    public static String getCurrentSdcardPath() {
        return AMapAppGlobal.getApplication().getSharedPreferences("base_path", 0).getString("offline_data_storage", "");
    }

    private static String getBackupSdcardDirPath() {
        String b = PathManager.a().b();
        if (TextUtils.isEmpty(b)) {
            return b;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(b);
        sb.append(DB_OFFLINE_SDCARD_DIR);
        return sb.toString();
    }

    public static String getBackupSdcardPath(String str) {
        String backupSdcardDirPath = getBackupSdcardDirPath();
        StringBuilder sb = new StringBuilder();
        sb.append(backupSdcardDirPath);
        sb.append(str);
        return sb.toString();
    }

    public static boolean copyDatabase(File file, File file2) {
        FileOutputStream fileOutputStream;
        if (!file.exists() || !file.isFile() || !file.canRead()) {
            return false;
        }
        if ((!file2.exists() || !file2.isFile()) && (!file2.getParentFile().exists() || !file2.getParentFile().isDirectory())) {
            file2.getParentFile().mkdirs();
        }
        FileInputStream fileInputStream = null;
        try {
            FileInputStream fileInputStream2 = new FileInputStream(file);
            try {
                fileOutputStream = new FileOutputStream(file2);
                try {
                    ahe.a(fileInputStream2, fileOutputStream);
                    ahe.a((Closeable) fileInputStream2);
                    ahe.a((Closeable) fileOutputStream);
                    return true;
                } catch (Exception e) {
                    e = e;
                    fileInputStream = fileInputStream2;
                    try {
                        e.printStackTrace();
                        ahe.a((Closeable) fileInputStream);
                        ahe.a((Closeable) fileOutputStream);
                        return false;
                    } catch (Throwable th) {
                        th = th;
                        ahe.a((Closeable) fileInputStream);
                        ahe.a((Closeable) fileOutputStream);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    fileInputStream = fileInputStream2;
                    ahe.a((Closeable) fileInputStream);
                    ahe.a((Closeable) fileOutputStream);
                    throw th;
                }
            } catch (Exception e2) {
                e = e2;
                fileOutputStream = null;
                fileInputStream = fileInputStream2;
                e.printStackTrace();
                ahe.a((Closeable) fileInputStream);
                ahe.a((Closeable) fileOutputStream);
                return false;
            } catch (Throwable th3) {
                th = th3;
                fileOutputStream = null;
                fileInputStream = fileInputStream2;
                ahe.a((Closeable) fileInputStream);
                ahe.a((Closeable) fileOutputStream);
                throw th;
            }
        } catch (Exception e3) {
            e = e3;
            fileOutputStream = null;
            e.printStackTrace();
            ahe.a((Closeable) fileInputStream);
            ahe.a((Closeable) fileOutputStream);
            return false;
        } catch (Throwable th4) {
            th = th4;
            fileOutputStream = null;
            ahe.a((Closeable) fileInputStream);
            ahe.a((Closeable) fileOutputStream);
            throw th;
        }
    }

    public static void deleteFolder(String str, boolean z) {
        deleteFolder(new File(str), z);
    }

    public static void deleteFolder(File file, boolean z) {
        if (file != null && file.exists()) {
            if (file.isDirectory()) {
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                arrayList.add(file);
                while (arrayList.size() > 0) {
                    File file2 = (File) arrayList.remove(0);
                    if (file2 != null && file2.exists() && file2.isDirectory()) {
                        String[] list = file2.list();
                        if (list != null && list.length > 0) {
                            for (String str : list) {
                                if (str != null) {
                                    File file3 = new File(file2, str);
                                    if (file3.exists()) {
                                        if (file3.isDirectory()) {
                                            arrayList.add(file3);
                                        } else {
                                            deleteFileInSafely(file3);
                                        }
                                    }
                                }
                            }
                        }
                        arrayList2.add(file2);
                    }
                }
                int i = !z;
                for (int size = arrayList2.size() - 1; size >= i; size--) {
                    File file4 = (File) arrayList2.get(size);
                    if (file4 != null && file4.exists()) {
                        deleteFileInSafely(file4);
                    }
                }
                return;
            }
            deleteFileInSafely(file);
        }
    }

    public static boolean deleteFileInSafely(File file) {
        if (file == null) {
            return false;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(file.getAbsolutePath());
        sb.append(System.currentTimeMillis());
        File file2 = new File(sb.toString());
        file.renameTo(file2);
        return file2.delete();
    }
}
