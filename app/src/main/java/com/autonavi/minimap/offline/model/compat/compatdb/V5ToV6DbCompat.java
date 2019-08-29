package com.autonavi.minimap.offline.model.compat.compatdb;

public class V5ToV6DbCompat extends CompatDb {
    protected static final String TAG = "V5ToV6DbCompat";
    private int dbVersion = 0;

    public V5ToV6DbCompat(String str) {
        super(str);
        if (this.db != null) {
            try {
                this.dbVersion = this.db.getVersion();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:33:0x00bb  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00c1  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean dataRestore() {
        /*
            r9 = this;
            android.database.sqlite.SQLiteDatabase r0 = r9.db
            r1 = 0
            if (r0 == 0) goto L_0x00c9
            r0 = 1
            r2 = 0
            boolean r3 = r9.isExistVoiceTable()     // Catch:{ Exception -> 0x00bf, all -> 0x00b7 }
            if (r3 == 0) goto L_0x00ad
            java.util.ArrayList r3 = new java.util.ArrayList     // Catch:{ Exception -> 0x00bf, all -> 0x00b7 }
            r3.<init>()     // Catch:{ Exception -> 0x00bf, all -> 0x00b7 }
            android.database.sqlite.SQLiteDatabase r4 = r9.db     // Catch:{ Exception -> 0x00bf, all -> 0x00b7 }
            java.lang.String r5 = "SELECT * FROM DOWNLOAD_VOICE_INFO"
            android.database.Cursor r4 = r4.rawQuery(r5, r2)     // Catch:{ Exception -> 0x00bf, all -> 0x00b7 }
        L_0x001a:
            boolean r2 = r4.moveToNext()     // Catch:{ Exception -> 0x00ab, all -> 0x00a9 }
            if (r2 == 0) goto L_0x006d
            ua r2 = new ua     // Catch:{ Exception -> 0x00ab, all -> 0x00a9 }
            r2.<init>()     // Catch:{ Exception -> 0x00ab, all -> 0x00a9 }
            java.lang.String r5 = "_id"
            int r5 = r4.getColumnIndex(r5)     // Catch:{ Exception -> 0x00ab, all -> 0x00a9 }
            long r5 = r4.getLong(r5)     // Catch:{ Exception -> 0x00ab, all -> 0x00a9 }
            java.lang.Long r5 = java.lang.Long.valueOf(r5)     // Catch:{ Exception -> 0x00ab, all -> 0x00a9 }
            r2.a = r5     // Catch:{ Exception -> 0x00ab, all -> 0x00a9 }
            java.lang.String r5 = "SUBNAME"
            int r5 = r4.getColumnIndex(r5)     // Catch:{ Exception -> 0x00ab, all -> 0x00a9 }
            java.lang.String r5 = r4.getString(r5)     // Catch:{ Exception -> 0x00ab, all -> 0x00a9 }
            r2.b = r5     // Catch:{ Exception -> 0x00ab, all -> 0x00a9 }
            r5 = 64
            r2.c = r5     // Catch:{ Exception -> 0x00ab, all -> 0x00a9 }
            java.lang.String r5 = "REAL_DATA_SIZE"
            int r5 = r4.getColumnIndex(r5)     // Catch:{ Exception -> 0x00ab, all -> 0x00a9 }
            long r5 = r4.getLong(r5)     // Catch:{ Exception -> 0x00ab, all -> 0x00a9 }
            r2.d = r5     // Catch:{ Exception -> 0x00ab, all -> 0x00a9 }
            java.lang.String r5 = "DOWNLOAD_DATA_SIZE"
            int r5 = r4.getColumnIndex(r5)     // Catch:{ Exception -> 0x00ab, all -> 0x00a9 }
            long r5 = r4.getLong(r5)     // Catch:{ Exception -> 0x00ab, all -> 0x00a9 }
            r2.e = r5     // Catch:{ Exception -> 0x00ab, all -> 0x00a9 }
            java.lang.String r5 = "DATA_PATH"
            int r5 = r4.getColumnIndex(r5)     // Catch:{ Exception -> 0x00ab, all -> 0x00a9 }
            java.lang.String r5 = r4.getString(r5)     // Catch:{ Exception -> 0x00ab, all -> 0x00a9 }
            r2.f = r5     // Catch:{ Exception -> 0x00ab, all -> 0x00a9 }
            r3.add(r2)     // Catch:{ Exception -> 0x00ab, all -> 0x00a9 }
            goto L_0x001a
        L_0x006d:
            com.autonavi.minimap.offline.model.OfflineDbHelper r2 = com.autonavi.minimap.offline.model.OfflineDbHelper.getInstance()     // Catch:{ Exception -> 0x00ab, all -> 0x00a9 }
            com.amap.bundle.drivecommon.voicesquare.DownloadVoiceDao r2 = r2.getDownloadVoiceDao()     // Catch:{ Exception -> 0x00ab, all -> 0x00a9 }
            if (r2 == 0) goto L_0x007a
            r2.deleteAll()     // Catch:{ Exception -> 0x00ab, all -> 0x00a9 }
        L_0x007a:
            int r5 = r3.size()     // Catch:{ Exception -> 0x00ab, all -> 0x00a9 }
            java.lang.String r6 = "V5ToV6DbCompat"
            java.lang.String r7 = "downloadVoiceList: "
            java.lang.String r8 = java.lang.String.valueOf(r5)     // Catch:{ Exception -> 0x00ab, all -> 0x00a9 }
            java.lang.String r7 = r7.concat(r8)     // Catch:{ Exception -> 0x00ab, all -> 0x00a9 }
            com.autonavi.minimap.offline.utils.OfflineLog.d(r6, r7)     // Catch:{ Exception -> 0x00ab, all -> 0x00a9 }
            if (r5 <= 0) goto L_0x00a7
            if (r2 == 0) goto L_0x0094
            r2.insertInTx(r3)     // Catch:{ Exception -> 0x00ab, all -> 0x00a9 }
        L_0x0094:
            com.autonavi.minimap.offline.utils.OfflineSpUtil.setOfflineTTSGuideTipShown(r1)     // Catch:{ Exception -> 0x00ab, all -> 0x00a9 }
            java.lang.Class<com.amap.bundle.tripgroup.api.IVoicePackageManager> r2 = com.amap.bundle.tripgroup.api.IVoicePackageManager.class
            java.lang.Object r2 = defpackage.ank.a(r2)     // Catch:{ Exception -> 0x00ab, all -> 0x00a9 }
            com.amap.bundle.tripgroup.api.IVoicePackageManager r2 = (com.amap.bundle.tripgroup.api.IVoicePackageManager) r2     // Catch:{ Exception -> 0x00ab, all -> 0x00a9 }
            if (r2 == 0) goto L_0x00a7
            r2.setIsUpgradeAe8TTSVersion(r0)     // Catch:{ Exception -> 0x00ab, all -> 0x00a9 }
            r2.restoreDefaultTTS()     // Catch:{ Exception -> 0x00ab, all -> 0x00a9 }
        L_0x00a7:
            r2 = r4
            goto L_0x00b0
        L_0x00a9:
            r0 = move-exception
            goto L_0x00b9
        L_0x00ab:
            r2 = r4
            goto L_0x00bf
        L_0x00ad:
            r9.restoreVoicesFromConfig()     // Catch:{ Exception -> 0x00bf, all -> 0x00b7 }
        L_0x00b0:
            if (r2 == 0) goto L_0x00b5
            r2.close()
        L_0x00b5:
            r1 = 1
            goto L_0x00c4
        L_0x00b7:
            r0 = move-exception
            r4 = r2
        L_0x00b9:
            if (r4 == 0) goto L_0x00be
            r4.close()
        L_0x00be:
            throw r0
        L_0x00bf:
            if (r2 == 0) goto L_0x00c4
            r2.close()
        L_0x00c4:
            android.database.sqlite.SQLiteDatabase r0 = r9.db
            r0.close()
        L_0x00c9:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.offline.model.compat.compatdb.V5ToV6DbCompat.dataRestore():boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0027  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x002d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isExistVoiceTable() {
        /*
            r4 = this;
            r0 = 0
            r1 = 0
            java.lang.String r2 = "select count(*) as c from sqlite_master where type ='table' and name ='DOWNLOAD_VOICE_INFO';"
            android.database.sqlite.SQLiteDatabase r3 = r4.db     // Catch:{ Exception -> 0x002b, all -> 0x0024 }
            android.database.Cursor r2 = r3.rawQuery(r2, r0)     // Catch:{ Exception -> 0x002b, all -> 0x0024 }
            boolean r0 = r2.moveToNext()     // Catch:{ Exception -> 0x0022, all -> 0x001e }
            if (r0 == 0) goto L_0x0018
            int r0 = r2.getInt(r1)     // Catch:{ Exception -> 0x0022, all -> 0x001e }
            if (r0 <= 0) goto L_0x0018
            r1 = 1
        L_0x0018:
            if (r2 == 0) goto L_0x0030
            r2.close()
            goto L_0x0030
        L_0x001e:
            r0 = move-exception
            r1 = r0
            r0 = r2
            goto L_0x0025
        L_0x0022:
            r0 = r2
            goto L_0x002b
        L_0x0024:
            r1 = move-exception
        L_0x0025:
            if (r0 == 0) goto L_0x002a
            r0.close()
        L_0x002a:
            throw r1
        L_0x002b:
            if (r0 == 0) goto L_0x0030
            r0.close()
        L_0x0030:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.offline.model.compat.compatdb.V5ToV6DbCompat.isExistVoiceTable():boolean");
    }
}
