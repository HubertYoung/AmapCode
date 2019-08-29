package com.autonavi.minimap.offline.model.compat.compatdata;

public class V5ToV6DataCompat extends CompatData {
    private static final String TAG = "V5ToV6DataCompat";
    private int databaseV5Version = 0;
    private final String filePath;

    public V5ToV6DataCompat(String str) {
        this.filePath = str;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x002a, code lost:
        if (r1 != null) goto L_0x0014;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0012, code lost:
        if (r1 != null) goto L_0x0014;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0014, code lost:
        r1.close();
     */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0030  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void dataCheck() {
        /*
            r5 = this;
            java.lang.String r0 = r5.filePath
            if (r0 == 0) goto L_0x0034
            r0 = 0
            java.lang.String r1 = r5.filePath     // Catch:{ Exception -> 0x001f, all -> 0x001a }
            r2 = 1
            android.database.sqlite.SQLiteDatabase r1 = android.database.sqlite.SQLiteDatabase.openDatabase(r1, r0, r2)     // Catch:{ Exception -> 0x001f, all -> 0x001a }
            int r0 = r1.getVersion()     // Catch:{ Exception -> 0x0018 }
            r5.databaseV5Version = r0     // Catch:{ Exception -> 0x0018 }
            if (r1 == 0) goto L_0x0034
        L_0x0014:
            r1.close()
            goto L_0x0034
        L_0x0018:
            r0 = move-exception
            goto L_0x0023
        L_0x001a:
            r1 = move-exception
            r4 = r1
            r1 = r0
            r0 = r4
            goto L_0x002e
        L_0x001f:
            r1 = move-exception
            r4 = r1
            r1 = r0
            r0 = r4
        L_0x0023:
            java.lang.String r2 = "V5ToV6DataCompat"
            java.lang.String r3 = "dataCheck openOldDb"
            com.autonavi.minimap.offline.utils.OfflineLog.e(r2, r3, r0)     // Catch:{ all -> 0x002d }
            if (r1 == 0) goto L_0x0034
            goto L_0x0014
        L_0x002d:
            r0 = move-exception
        L_0x002e:
            if (r1 == 0) goto L_0x0033
            r1.close()
        L_0x0033:
            throw r0
        L_0x0034:
            java.lang.String r0 = "V5ToV6DataCompat"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "dataCheck() databaseV5Version:"
            r1.<init>(r2)
            int r2 = r5.databaseV5Version
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            com.autonavi.minimap.offline.utils.OfflineLog.d(r0, r1)
            int r0 = r5.databaseV5Version
            switch(r0) {
                case 1: goto L_0x0068;
                case 2: goto L_0x0068;
                case 3: goto L_0x0064;
                case 4: goto L_0x0068;
                case 5: goto L_0x0064;
                default: goto L_0x004e;
            }
        L_0x004e:
            java.lang.String r0 = "V5ToV6DataCompat"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "dataCheck error v"
            r1.<init>(r2)
            int r2 = r5.databaseV5Version
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            com.autonavi.minimap.offline.utils.OfflineLog.e(r0, r1)
            return
        L_0x0064:
            r5.deleteVoicetData()
            return
        L_0x0068:
            r5.deleteVoicetData()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.offline.model.compat.compatdata.V5ToV6DataCompat.dataCheck():void");
    }
}
