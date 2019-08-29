package com.autonavi.minimap.offline.nativesupport.compat;

import com.autonavi.minimap.offline.nativesupport.AmapCompatData;

public class V6DataCompat extends DataCompat {
    public V6DataCompat(String str) {
        super(str);
    }

    public AmapCompatData check() {
        return getCompatData();
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x00b4  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00bc  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.autonavi.minimap.offline.nativesupport.AmapCompatData getCompatData() {
        /*
            r11 = this;
            android.database.sqlite.SQLiteDatabase r0 = r11.db
            r1 = 0
            if (r0 != 0) goto L_0x0006
            return r1
        L_0x0006:
            android.database.sqlite.SQLiteDatabase r0 = r11.db     // Catch:{ Exception -> 0x00ab, all -> 0x00a6 }
            java.lang.String r2 = "SELECT cityId, mapStatus, routeStatus, FileType, mapSubUrl, mapMd5, mapVersion, mapZipSize, mapBaseUrl, routeSubUrl, routeMd5, routeVersion, routeZipSize, routeBaseUrl, cityBitMask  FROM downloadcity"
            android.database.Cursor r0 = r0.rawQuery(r2, r1)     // Catch:{ Exception -> 0x00ab, all -> 0x00a6 }
            if (r0 == 0) goto L_0x00a0
            com.autonavi.minimap.offline.nativesupport.AmapCompatData r2 = new com.autonavi.minimap.offline.nativesupport.AmapCompatData     // Catch:{ Exception -> 0x009b }
            r2.<init>()     // Catch:{ Exception -> 0x009b }
            int r1 = r0.getCount()     // Catch:{ Exception -> 0x0099 }
            r3 = 2
            int r1 = r1 * 2
            com.autonavi.minimap.offline.nativesupport.CompatDataItem[] r1 = new com.autonavi.minimap.offline.nativesupport.CompatDataItem[r1]     // Catch:{ Exception -> 0x0099 }
            r2.items = r1     // Catch:{ Exception -> 0x0099 }
            r1 = 0
            r4 = 0
        L_0x0022:
            boolean r5 = r0.moveToNext()     // Catch:{ Exception -> 0x0099 }
            r6 = 1
            if (r5 == 0) goto L_0x0084
            int r5 = r0.getInt(r1)     // Catch:{ Exception -> 0x0099 }
            int r7 = r0.getInt(r6)     // Catch:{ Exception -> 0x0099 }
            int r8 = r0.getInt(r3)     // Catch:{ Exception -> 0x0099 }
            if (r7 == 0) goto L_0x005c
            com.autonavi.minimap.offline.nativesupport.CompatDataItem[] r7 = r2.items     // Catch:{ Exception -> 0x0099 }
            com.autonavi.minimap.offline.nativesupport.CompatDataItem r9 = new com.autonavi.minimap.offline.nativesupport.CompatDataItem     // Catch:{ Exception -> 0x0099 }
            r9.<init>()     // Catch:{ Exception -> 0x0099 }
            r7[r4] = r9     // Catch:{ Exception -> 0x0099 }
            com.autonavi.minimap.offline.nativesupport.CompatDataItem[] r7 = r2.items     // Catch:{ Exception -> 0x0099 }
            r7 = r7[r4]     // Catch:{ Exception -> 0x0099 }
            r7.cityId = r5     // Catch:{ Exception -> 0x0099 }
            com.autonavi.minimap.offline.nativesupport.CompatDataItem[] r7 = r2.items     // Catch:{ Exception -> 0x0099 }
            r7 = r7[r4]     // Catch:{ Exception -> 0x0099 }
            r7.packageType = r1     // Catch:{ Exception -> 0x0099 }
            com.autonavi.minimap.offline.nativesupport.CompatDataItem[] r7 = r2.items     // Catch:{ Exception -> 0x0099 }
            r7 = r7[r4]     // Catch:{ Exception -> 0x0099 }
            r7.status = r1     // Catch:{ Exception -> 0x0099 }
            com.autonavi.minimap.offline.nativesupport.CompatDataItem[] r7 = r2.items     // Catch:{ Exception -> 0x0099 }
            r7 = r7[r4]     // Catch:{ Exception -> 0x0099 }
            java.lang.String r9 = ""
            r7.dataContent = r9     // Catch:{ Exception -> 0x0099 }
            int r4 = r4 + 1
        L_0x005c:
            if (r8 == 0) goto L_0x0022
            com.autonavi.minimap.offline.nativesupport.CompatDataItem[] r7 = r2.items     // Catch:{ Exception -> 0x0099 }
            com.autonavi.minimap.offline.nativesupport.CompatDataItem r8 = new com.autonavi.minimap.offline.nativesupport.CompatDataItem     // Catch:{ Exception -> 0x0099 }
            r8.<init>()     // Catch:{ Exception -> 0x0099 }
            r7[r4] = r8     // Catch:{ Exception -> 0x0099 }
            com.autonavi.minimap.offline.nativesupport.CompatDataItem[] r7 = r2.items     // Catch:{ Exception -> 0x0099 }
            r7 = r7[r4]     // Catch:{ Exception -> 0x0099 }
            r7.cityId = r5     // Catch:{ Exception -> 0x0099 }
            com.autonavi.minimap.offline.nativesupport.CompatDataItem[] r5 = r2.items     // Catch:{ Exception -> 0x0099 }
            r5 = r5[r4]     // Catch:{ Exception -> 0x0099 }
            r5.packageType = r6     // Catch:{ Exception -> 0x0099 }
            com.autonavi.minimap.offline.nativesupport.CompatDataItem[] r5 = r2.items     // Catch:{ Exception -> 0x0099 }
            r5 = r5[r4]     // Catch:{ Exception -> 0x0099 }
            r5.status = r1     // Catch:{ Exception -> 0x0099 }
            com.autonavi.minimap.offline.nativesupport.CompatDataItem[] r5 = r2.items     // Catch:{ Exception -> 0x0099 }
            r5 = r5[r4]     // Catch:{ Exception -> 0x0099 }
            java.lang.String r6 = ""
            r5.dataContent = r6     // Catch:{ Exception -> 0x0099 }
            int r4 = r4 + 1
            goto L_0x0022
        L_0x0084:
            r2.itemLength = r4     // Catch:{ Exception -> 0x0099 }
            r2.result = r6     // Catch:{ Exception -> 0x0099 }
            com.autonavi.minimap.offline.OfflineSDK r1 = com.autonavi.minimap.offline.OfflineSDK.getInstance()     // Catch:{ Exception -> 0x0099 }
            r1.setIsUpgradeAe8Version(r6)     // Catch:{ Exception -> 0x0099 }
            com.autonavi.minimap.offline.nativesupport.compat.V6DataCompat$1 r1 = new com.autonavi.minimap.offline.nativesupport.compat.V6DataCompat$1     // Catch:{ Exception -> 0x0099 }
            r1.<init>()     // Catch:{ Exception -> 0x0099 }
            defpackage.ahm.a(r1)     // Catch:{ Exception -> 0x0099 }
            r1 = r2
            goto L_0x00a0
        L_0x0099:
            r1 = move-exception
            goto L_0x00af
        L_0x009b:
            r2 = move-exception
            r10 = r2
            r2 = r1
            r1 = r10
            goto L_0x00af
        L_0x00a0:
            if (r0 == 0) goto L_0x00b8
            r0.close()
            goto L_0x00b8
        L_0x00a6:
            r0 = move-exception
            r10 = r1
            r1 = r0
            r0 = r10
            goto L_0x00ba
        L_0x00ab:
            r0 = move-exception
            r2 = r1
            r1 = r0
            r0 = r2
        L_0x00af:
            r1.printStackTrace()     // Catch:{ all -> 0x00b9 }
            if (r0 == 0) goto L_0x00b7
            r0.close()
        L_0x00b7:
            r1 = r2
        L_0x00b8:
            return r1
        L_0x00b9:
            r1 = move-exception
        L_0x00ba:
            if (r0 == 0) goto L_0x00bf
            r0.close()
        L_0x00bf:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.offline.nativesupport.compat.V6DataCompat.getCompatData():com.autonavi.minimap.offline.nativesupport.AmapCompatData");
    }
}
