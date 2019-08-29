package com.autonavi.minimap.offline.model.compat;

import android.text.TextUtils;
import com.amap.bundle.blutils.PathManager;
import com.autonavi.minimap.offline.OfflineSDK;
import com.autonavi.minimap.offline.model.compat.compatdata.CompatData;
import com.autonavi.minimap.offline.model.compat.compatdb.CompatDb;
import com.autonavi.minimap.offline.utils.OfflineLog;
import com.autonavi.minimap.offline.utils.OfflineUtil;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CompatHelper {
    public static final String DB_ASSETS_PATH_DBV6 = "common/OfflineDbV6.db";
    public static final String DB_OFFLINE_NAME_ACKOR = "ackor_offline.db";
    private static final String DB_OFFLINE_NAME_V2 = "offline.db";
    private static final String DB_OFFLINE_NAME_V3 = "offlineDbV3.db";
    private static final String DB_OFFLINE_NAME_V4 = "offlineDbV4.db";
    private static final String DB_OFFLINE_NAME_V5_DOWNLOAD_CITY = "downloadcity.db";
    public static final String DB_OFFLINE_NAME_V6 = "OfflineDbV6.db";
    private static final String DB_OFFLINE_SDCARD_DIR = "/autonavi/db/";
    public static final String DB_OFFLINE_SDCARD_PATH_DBACKOR = "/autonavi/db/ackor_offline.db";
    public static final String DB_OFFLINE_SDCARD_PATH_DBV6 = "/autonavi/db/OfflineDbV6.db";
    public static final int DOWNLOAD_CITY_DATABASE_V5_VERSION1 = 1;
    public static final int DOWNLOAD_CITY_DATABASE_V5_VERSION2 = 2;
    public static final int DOWNLOAD_CITY_DATABASE_V5_VERSION3 = 3;
    public static final int DOWNLOAD_CITY_DATABASE_V5_VERSION4 = 4;
    public static final int DOWNLOAD_CITY_DATABASE_V5_VERSION5 = 5;
    public static final String TAG = "CompatHelper";
    private static CompatHelper helper;
    private CompatDb compatDb;
    private Lock lock = new ReentrantLock();
    private CompatData mCompatData;
    private String mCurrentSDCardPath;

    private CompatHelper(String str) {
        this.mCurrentSDCardPath = str;
    }

    public static synchronized CompatHelper createInstance() {
        CompatHelper compatHelper;
        synchronized (CompatHelper.class) {
            if (helper == null) {
                helper = new CompatHelper(PathManager.a().b());
            }
            compatHelper = helper;
        }
        return compatHelper;
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x0089 A[LOOP:0: B:23:0x0089->B:41:0x010f, LOOP_START, PHI: r3 
      PHI: (r3v1 int) = (r3v0 int), (r3v2 int) binds: [B:22:0x0087, B:41:0x010f] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x012c  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x0135  */
    /* JADX WARNING: Removed duplicated region for block: B:62:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void compat() {
        /*
            r8 = this;
            java.lang.String r0 = "downloadcity.db"
            java.lang.String r1 = "offlineDbV4.db"
            java.lang.String r2 = "offlineDbV3.db"
            java.lang.String[] r0 = new java.lang.String[]{r0, r1, r2}
            java.lang.String r1 = r8.mCurrentSDCardPath
            java.lang.String r1 = com.autonavi.minimap.offline.utils.OfflineUtil.getStringMD5(r1)
            boolean r2 = r8.isCurrentDbV6DatabaseExist()
            r3 = 0
            r4 = 1
            r5 = 0
            if (r2 != 0) goto L_0x0042
            java.lang.String r2 = com.autonavi.minimap.offline.OfflineSDK.getCurrentSDCardOfflineDbPath()
            boolean r6 = r8.isFileExist(r2)
            if (r6 == 0) goto L_0x003d
            java.lang.String r6 = com.autonavi.minimap.offline.OfflineSDK.getCurrentOfflineDbPath()
            r8.copyDatabase(r2, r6)
            java.lang.String r2 = com.autonavi.minimap.offline.OfflineSDK.getCurrentOfflineDbPath()
            com.autonavi.minimap.offline.model.compat.compatdata.V6DataCompat r6 = new com.autonavi.minimap.offline.model.compat.compatdata.V6DataCompat     // Catch:{ Exception -> 0x0038 }
            r6.<init>(r2)     // Catch:{ Exception -> 0x0038 }
            r8.mCompatData = r6     // Catch:{ Exception -> 0x0038 }
            r8.compatDb = r5     // Catch:{ Exception -> 0x0038 }
            goto L_0x0086
        L_0x0038:
            r8.mCompatData = r5
            r8.compatDb = r5
            goto L_0x0086
        L_0x003d:
            r8.copyAssetDbV6ToDatabases()
            r2 = 0
            goto L_0x0087
        L_0x0042:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r6 = com.autonavi.minimap.offline.utils.OfflineUtil.getDatabasesDir()
            r2.append(r6)
            r2.append(r1)
            java.lang.String r6 = "downloadcity.db"
            r2.append(r6)
            java.lang.String r2 = r2.toString()
            boolean r6 = r8.isFileExist(r2)
            if (r6 == 0) goto L_0x0074
            com.autonavi.minimap.offline.model.compat.compatdata.V5ToV6DataCompat r6 = new com.autonavi.minimap.offline.model.compat.compatdata.V5ToV6DataCompat     // Catch:{ Exception -> 0x006f }
            r6.<init>(r2)     // Catch:{ Exception -> 0x006f }
            r8.mCompatData = r6     // Catch:{ Exception -> 0x006f }
            com.autonavi.minimap.offline.model.compat.compatdb.V5ToV6DbCompat r6 = new com.autonavi.minimap.offline.model.compat.compatdb.V5ToV6DbCompat     // Catch:{ Exception -> 0x006f }
            r6.<init>(r2)     // Catch:{ Exception -> 0x006f }
            r8.compatDb = r6     // Catch:{ Exception -> 0x006f }
            goto L_0x0086
        L_0x006f:
            r8.mCompatData = r5
            r8.compatDb = r5
            goto L_0x0086
        L_0x0074:
            java.lang.String r2 = com.autonavi.minimap.offline.OfflineSDK.getCurrentOfflineDbPath()
            com.autonavi.minimap.offline.model.compat.compatdata.V6DataCompat r6 = new com.autonavi.minimap.offline.model.compat.compatdata.V6DataCompat     // Catch:{ Exception -> 0x0082 }
            r6.<init>(r2)     // Catch:{ Exception -> 0x0082 }
            r8.mCompatData = r6     // Catch:{ Exception -> 0x0082 }
            r8.compatDb = r5     // Catch:{ Exception -> 0x0082 }
            goto L_0x0086
        L_0x0082:
            r8.mCompatData = r5
            r8.compatDb = r5
        L_0x0086:
            r2 = 1
        L_0x0087:
            if (r2 != 0) goto L_0x0128
        L_0x0089:
            r2 = 3
            if (r3 >= r2) goto L_0x0113
            r2 = r0[r3]
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = com.autonavi.minimap.offline.utils.OfflineUtil.getDatabasesDir()
            r6.append(r7)
            r6.append(r1)
            r6.append(r2)
            java.lang.String r6 = r6.toString()
            boolean r7 = r8.isFileExist(r6)
            if (r7 == 0) goto L_0x00cf
            if (r3 != 0) goto L_0x00bb
            com.autonavi.minimap.offline.model.compat.compatdata.V5ToV6DataCompat r2 = new com.autonavi.minimap.offline.model.compat.compatdata.V5ToV6DataCompat     // Catch:{ Exception -> 0x00ca }
            r2.<init>(r6)     // Catch:{ Exception -> 0x00ca }
            r8.mCompatData = r2     // Catch:{ Exception -> 0x00ca }
            com.autonavi.minimap.offline.model.compat.compatdb.V5ToV6DbCompat r2 = new com.autonavi.minimap.offline.model.compat.compatdb.V5ToV6DbCompat     // Catch:{ Exception -> 0x00ca }
            r2.<init>(r6)     // Catch:{ Exception -> 0x00ca }
            r8.compatDb = r2     // Catch:{ Exception -> 0x00ca }
            goto L_0x0113
        L_0x00bb:
            com.autonavi.minimap.offline.model.compat.compatdata.V2V3V4ToV6DataCompat r2 = new com.autonavi.minimap.offline.model.compat.compatdata.V2V3V4ToV6DataCompat     // Catch:{ Exception -> 0x00ca }
            r2.<init>(r6)     // Catch:{ Exception -> 0x00ca }
            r8.mCompatData = r2     // Catch:{ Exception -> 0x00ca }
            com.autonavi.minimap.offline.model.compat.compatdb.V2V3V4ToV6DbCompat r2 = new com.autonavi.minimap.offline.model.compat.compatdb.V2V3V4ToV6DbCompat     // Catch:{ Exception -> 0x00ca }
            r2.<init>(r6)     // Catch:{ Exception -> 0x00ca }
            r8.compatDb = r2     // Catch:{ Exception -> 0x00ca }
            goto L_0x0113
        L_0x00ca:
            r8.mCompatData = r5
            r8.compatDb = r5
            goto L_0x010f
        L_0x00cf:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = r8.mCurrentSDCardPath
            r6.append(r7)
            java.lang.String r7 = "/autonavi/db/"
            r6.append(r7)
            r6.append(r2)
            java.lang.String r2 = r6.toString()
            boolean r6 = r8.isFileExist(r2)
            if (r6 == 0) goto L_0x010f
            if (r3 != 0) goto L_0x00fc
            com.autonavi.minimap.offline.model.compat.compatdata.V5ToV6DataCompat r6 = new com.autonavi.minimap.offline.model.compat.compatdata.V5ToV6DataCompat     // Catch:{ Exception -> 0x010b }
            r6.<init>(r2)     // Catch:{ Exception -> 0x010b }
            r8.mCompatData = r6     // Catch:{ Exception -> 0x010b }
            com.autonavi.minimap.offline.model.compat.compatdb.V5ToV6DbCompat r6 = new com.autonavi.minimap.offline.model.compat.compatdb.V5ToV6DbCompat     // Catch:{ Exception -> 0x010b }
            r6.<init>(r2)     // Catch:{ Exception -> 0x010b }
            r8.compatDb = r6     // Catch:{ Exception -> 0x010b }
            goto L_0x0113
        L_0x00fc:
            com.autonavi.minimap.offline.model.compat.compatdata.V2V3V4ToV6DataCompat r6 = new com.autonavi.minimap.offline.model.compat.compatdata.V2V3V4ToV6DataCompat     // Catch:{ Exception -> 0x010b }
            r6.<init>(r2)     // Catch:{ Exception -> 0x010b }
            r8.mCompatData = r6     // Catch:{ Exception -> 0x010b }
            com.autonavi.minimap.offline.model.compat.compatdb.V2V3V4ToV6DbCompat r6 = new com.autonavi.minimap.offline.model.compat.compatdb.V2V3V4ToV6DbCompat     // Catch:{ Exception -> 0x010b }
            r6.<init>(r2)     // Catch:{ Exception -> 0x010b }
            r8.compatDb = r6     // Catch:{ Exception -> 0x010b }
            goto L_0x0113
        L_0x010b:
            r8.mCompatData = r5
            r8.compatDb = r5
        L_0x010f:
            int r3 = r3 + 1
            goto L_0x0089
        L_0x0113:
            com.autonavi.minimap.offline.model.compat.compatdata.CompatData r0 = r8.mCompatData
            if (r0 == 0) goto L_0x0128
            com.autonavi.minimap.offline.model.compat.compatdb.CompatDb r0 = r8.compatDb
            if (r0 == 0) goto L_0x0128
            java.lang.Class<com.amap.bundle.tripgroup.api.IVoicePackageManager> r0 = com.amap.bundle.tripgroup.api.IVoicePackageManager.class
            java.lang.Object r0 = defpackage.ank.a(r0)
            com.amap.bundle.tripgroup.api.IVoicePackageManager r0 = (com.amap.bundle.tripgroup.api.IVoicePackageManager) r0
            if (r0 == 0) goto L_0x0128
            r0.setIsUpgradeAe8TTSVersion(r4)
        L_0x0128:
            com.autonavi.minimap.offline.model.compat.compatdb.CompatDb r0 = r8.compatDb
            if (r0 == 0) goto L_0x0131
            com.autonavi.minimap.offline.model.compat.compatdb.CompatDb r0 = r8.compatDb
            r0.dataRestore()
        L_0x0131:
            com.autonavi.minimap.offline.model.compat.compatdata.CompatData r0 = r8.mCompatData
            if (r0 == 0) goto L_0x013a
            com.autonavi.minimap.offline.model.compat.compatdata.CompatData r0 = r8.mCompatData
            r0.dataCheck()
        L_0x013a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.offline.model.compat.CompatHelper.compat():void");
    }

    private boolean copyAssetDbV6ToDatabases() {
        FileOutputStream fileOutputStream;
        this.lock.lock();
        try {
            String currentOfflineDbPath = OfflineSDK.getCurrentOfflineDbPath();
            InputStream inputStream = null;
            File file = !TextUtils.isEmpty(currentOfflineDbPath) ? new File(currentOfflineDbPath) : null;
            if (file == null || (file.exists() && file.isFile())) {
                this.lock.unlock();
                return true;
            }
            File parentFile = file.getParentFile();
            if (parentFile != null && (!parentFile.exists() || !parentFile.isDirectory())) {
                parentFile.mkdirs();
            }
            OfflineLog.d(TAG, "copyAssetDbV6ToDatabases copy");
            try {
                InputStream open = OfflineUtil.getContext().getAssets().open(DB_ASSETS_PATH_DBV6);
                try {
                    fileOutputStream = new FileOutputStream(file);
                    try {
                        ahe.a(open, fileOutputStream);
                        ahe.a((Closeable) open);
                        ahe.a((Closeable) fileOutputStream);
                        return true;
                    } catch (IOException e) {
                        e = e;
                        inputStream = open;
                        try {
                            e.printStackTrace();
                            ahe.a((Closeable) inputStream);
                            ahe.a((Closeable) fileOutputStream);
                            this.lock.unlock();
                            return true;
                        } catch (Throwable th) {
                            th = th;
                            ahe.a((Closeable) inputStream);
                            ahe.a((Closeable) fileOutputStream);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        inputStream = open;
                        ahe.a((Closeable) inputStream);
                        ahe.a((Closeable) fileOutputStream);
                        throw th;
                    }
                } catch (IOException e2) {
                    e = e2;
                    fileOutputStream = null;
                    inputStream = open;
                    e.printStackTrace();
                    ahe.a((Closeable) inputStream);
                    ahe.a((Closeable) fileOutputStream);
                    this.lock.unlock();
                    return true;
                } catch (Throwable th3) {
                    th = th3;
                    fileOutputStream = null;
                    inputStream = open;
                    ahe.a((Closeable) inputStream);
                    ahe.a((Closeable) fileOutputStream);
                    throw th;
                }
            } catch (IOException e3) {
                e = e3;
                fileOutputStream = null;
                e.printStackTrace();
                ahe.a((Closeable) inputStream);
                ahe.a((Closeable) fileOutputStream);
                this.lock.unlock();
                return true;
            } catch (Throwable th4) {
                th = th4;
                fileOutputStream = null;
                ahe.a((Closeable) inputStream);
                ahe.a((Closeable) fileOutputStream);
                throw th;
            }
        } finally {
            this.lock.unlock();
        }
    }

    private boolean copyDatabase(String str, String str2) {
        return copyDatabase(new File(str), new File(str2));
    }

    private boolean copyDatabase(File file, File file2) {
        FileOutputStream fileOutputStream;
        if (!file.exists() || !file.isFile() || !file.canRead()) {
            OfflineLog.e((String) TAG, (String) "copyDatabase sourceFile may be not existed.");
            return false;
        }
        if ((!file2.exists() || !file2.isFile()) && ((!file2.getParentFile().exists() || !file2.getParentFile().isDirectory()) && file2.getParentFile().mkdirs())) {
            OfflineLog.d(TAG, "copyDatabase create target dirs success.");
        }
        this.lock.lock();
        FileInputStream fileInputStream = null;
        try {
            FileInputStream fileInputStream2 = new FileInputStream(file);
            try {
                fileOutputStream = new FileOutputStream(file2);
                try {
                    ahe.a(fileInputStream2, fileOutputStream);
                    this.lock.unlock();
                    ahe.a((Closeable) fileInputStream2);
                    ahe.a((Closeable) fileOutputStream);
                    return true;
                } catch (Exception e) {
                    e = e;
                    fileInputStream = fileInputStream2;
                    try {
                        e.printStackTrace();
                        this.lock.unlock();
                        ahe.a((Closeable) fileInputStream);
                        ahe.a((Closeable) fileOutputStream);
                        return false;
                    } catch (Throwable th) {
                        th = th;
                        this.lock.unlock();
                        ahe.a((Closeable) fileInputStream);
                        ahe.a((Closeable) fileOutputStream);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    fileInputStream = fileInputStream2;
                    this.lock.unlock();
                    ahe.a((Closeable) fileInputStream);
                    ahe.a((Closeable) fileOutputStream);
                    throw th;
                }
            } catch (Exception e2) {
                e = e2;
                fileOutputStream = null;
                fileInputStream = fileInputStream2;
                e.printStackTrace();
                this.lock.unlock();
                ahe.a((Closeable) fileInputStream);
                ahe.a((Closeable) fileOutputStream);
                return false;
            } catch (Throwable th3) {
                th = th3;
                fileOutputStream = null;
                fileInputStream = fileInputStream2;
                this.lock.unlock();
                ahe.a((Closeable) fileInputStream);
                ahe.a((Closeable) fileOutputStream);
                throw th;
            }
        } catch (Exception e3) {
            e = e3;
            fileOutputStream = null;
            e.printStackTrace();
            this.lock.unlock();
            ahe.a((Closeable) fileInputStream);
            ahe.a((Closeable) fileOutputStream);
            return false;
        } catch (Throwable th4) {
            th = th4;
            fileOutputStream = null;
            this.lock.unlock();
            ahe.a((Closeable) fileInputStream);
            ahe.a((Closeable) fileOutputStream);
            throw th;
        }
    }

    private boolean isCurrentDbV6DatabaseExist() {
        String currentOfflineDbPath = OfflineSDK.getCurrentOfflineDbPath();
        if (TextUtils.isEmpty(currentOfflineDbPath)) {
            return false;
        }
        File file = new File(currentOfflineDbPath);
        if (!file.exists() || !file.isFile()) {
            return false;
        }
        return true;
    }

    private boolean isFileExist(String str) {
        File file = new File(str);
        return file.exists() && file.isFile() && file.canRead();
    }
}
