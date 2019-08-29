package com.autonavi.minimap.offline.nativesupport.compat;

import android.database.sqlite.SQLiteDatabase;
import com.autonavi.minimap.offline.nativesupport.AmapCompatData;

public abstract class DataCompat {
    public static final int AE8_VERSION = 1;
    public static final int AE8_VERSION_BEFORE = 2;
    protected SQLiteDatabase db = null;

    public interface CompatDownloadStatus {
        public static final int DOWNLOAD_STATUS_COMPLETED = 1;
        public static final int DOWNLOAD_STATUS_PAUSE = 2;
        public static final int DOWNLOAD_STATUS_WAITING = 0;
    }

    public interface OldDbName {
        public static final String DB_OFFLINE_NAME_V3 = "offlineDbV3.db";
        public static final String DB_OFFLINE_NAME_V4 = "offlineDbV4.db";
        public static final String DB_OFFLINE_NAME_V5 = "downloadcity.db";
        public static final String DB_OFFLINE_NAME_V6 = "OfflineDbV6.db";
    }

    public interface OldStatus {
        public static final int DOWNLOAD_STATE_0_UNDOWNLOAD = 0;
        public static final int DOWNLOAD_STATE_10_NET_ERROR = 10;
        public static final int DOWNLOAD_STATE_11_MD5_ERROR = 11;
        public static final int DOWNLOAD_STATE_1_LOADING = 1;
        public static final int DOWNLOAD_STATE_2_WAITING = 2;
        public static final int DOWNLOAD_STATE_3_PAUSE = 3;
        public static final int DOWNLOAD_STATE_4_DOWNLOAD_COMPLETED = 4;
        public static final int DOWNLOAD_STATE_5_DATA_ERROR = 5;
        public static final int DOWNLOAD_STATE_64_UPGRADE = 64;
        public static final int DOWNLOAD_STATE_6_UNZIPPING_FINISH = 6;
        public static final int DOWNLOAD_STATE_7_UNZIPPING = 7;
        public static final int DOWNLOAD_STATE_8_UNZIPPING_ERROR = 8;
        public static final int DOWNLOAD_STATE_9_INSTALL_COMPLETED = 9;
    }

    public interface PackageType {
        public static final int PACKAGE_TYPE_MAP = 0;
        public static final int PACKAGE_TYPE_ROUTE = 1;
    }

    public abstract AmapCompatData check();

    public abstract AmapCompatData getCompatData();

    public DataCompat(String str) {
        this.db = openDatabase(str);
    }

    public static SQLiteDatabase openDatabase(String str) {
        if (str != null) {
            try {
                return SQLiteDatabase.openDatabase(str, null, 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static DataCompat createDataCompat(String str) {
        if (str.endsWith("OfflineDbV6.db")) {
            if (!hasOfflineTable(str)) {
                return null;
            }
            return new V6DataCompat(str);
        } else if (str.endsWith(OldDbName.DB_OFFLINE_NAME_V5)) {
            return new V5DataCompat(str);
        } else {
            return new BeforeV5DataCompat(str);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x002e, code lost:
        if (r6 != null) goto L_0x0030;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0030, code lost:
        r6.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0034, code lost:
        r1 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x003c, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0041, code lost:
        r6.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0049, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x004c, code lost:
        if (r6 != null) goto L_0x0030;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x004f, code lost:
        return r1;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0034 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:3:0x000d] */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x003c  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0041  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0049  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean hasOfflineTable(java.lang.String r6) {
        /*
            android.database.sqlite.SQLiteDatabase r6 = openDatabase(r6)
            java.lang.String r0 = "select name from sqlite_master where type='table';"
            r1 = 0
            r2 = 0
            android.database.Cursor r0 = r6.rawQuery(r0, r1)     // Catch:{ Exception -> 0x0045, all -> 0x0036 }
            boolean r1 = r0.moveToFirst()     // Catch:{ Exception -> 0x0046, all -> 0x0034 }
            if (r1 == 0) goto L_0x0028
            r1 = 0
        L_0x0014:
            boolean r3 = r0.moveToNext()     // Catch:{ Exception -> 0x0047, all -> 0x0034 }
            if (r3 == 0) goto L_0x0029
            java.lang.String r3 = r0.getString(r2)     // Catch:{ Exception -> 0x0047, all -> 0x0034 }
            java.lang.String r4 = "downloadcity"
            boolean r3 = r4.equals(r3)     // Catch:{ Exception -> 0x0047, all -> 0x0034 }
            if (r3 == 0) goto L_0x0014
            r1 = 1
            goto L_0x0014
        L_0x0028:
            r1 = 0
        L_0x0029:
            if (r0 == 0) goto L_0x002e
            r0.close()
        L_0x002e:
            if (r6 == 0) goto L_0x004f
        L_0x0030:
            r6.close()
            goto L_0x004f
        L_0x0034:
            r1 = move-exception
            goto L_0x003a
        L_0x0036:
            r0 = move-exception
            r5 = r1
            r1 = r0
            r0 = r5
        L_0x003a:
            if (r0 == 0) goto L_0x003f
            r0.close()
        L_0x003f:
            if (r6 == 0) goto L_0x0044
            r6.close()
        L_0x0044:
            throw r1
        L_0x0045:
            r0 = r1
        L_0x0046:
            r1 = 0
        L_0x0047:
            if (r0 == 0) goto L_0x004c
            r0.close()
        L_0x004c:
            if (r6 == 0) goto L_0x004f
            goto L_0x0030
        L_0x004f:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.offline.nativesupport.compat.DataCompat.hasOfflineTable(java.lang.String):boolean");
    }
}
