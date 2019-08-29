package com.autonavi.minimap.offline.nativesupport.compat;

import com.autonavi.minimap.offline.nativesupport.AmapCompatData;

public class BeforeV5DataCompat extends DataCompat {
    public BeforeV5DataCompat(String str) {
        super(str);
    }

    public AmapCompatData check() {
        return getCompatData();
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x009c  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x00a4  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.autonavi.minimap.offline.nativesupport.AmapCompatData getCompatData() {
        /*
            r9 = this;
            android.database.sqlite.SQLiteDatabase r0 = r9.db
            r1 = 0
            if (r0 != 0) goto L_0x0006
            return r1
        L_0x0006:
            android.database.sqlite.SQLiteDatabase r0 = r9.db     // Catch:{ Exception -> 0x0093, all -> 0x008e }
            java.lang.String r2 = "SELECT * FROM MATERIAL_METADATA"
            android.database.Cursor r0 = r0.rawQuery(r2, r1)     // Catch:{ Exception -> 0x0093, all -> 0x008e }
            if (r0 == 0) goto L_0x0088
            com.autonavi.minimap.offline.nativesupport.AmapCompatData r2 = new com.autonavi.minimap.offline.nativesupport.AmapCompatData     // Catch:{ Exception -> 0x0083 }
            r2.<init>()     // Catch:{ Exception -> 0x0083 }
            int r1 = r0.getCount()     // Catch:{ Exception -> 0x0081 }
            com.autonavi.minimap.offline.nativesupport.CompatDataItem[] r1 = new com.autonavi.minimap.offline.nativesupport.CompatDataItem[r1]     // Catch:{ Exception -> 0x0081 }
            r2.items = r1     // Catch:{ Exception -> 0x0081 }
            r1 = 0
            r3 = 0
        L_0x001f:
            boolean r4 = r0.moveToNext()     // Catch:{ Exception -> 0x0081 }
            if (r4 == 0) goto L_0x006a
            java.lang.String r4 = "ADMIN_CODE"
            int r4 = r0.getColumnIndex(r4)     // Catch:{ Exception -> 0x0081 }
            int r4 = r0.getInt(r4)     // Catch:{ Exception -> 0x0081 }
            java.lang.String r5 = "CITY_ID"
            int r5 = r0.getColumnIndex(r5)     // Catch:{ Exception -> 0x0081 }
            r6 = -1
            if (r6 == r5) goto L_0x0042
            java.lang.String r4 = "CITY_ID"
            int r4 = r0.getColumnIndex(r4)     // Catch:{ Exception -> 0x0081 }
            int r4 = r0.getInt(r4)     // Catch:{ Exception -> 0x0081 }
        L_0x0042:
            java.lang.String r5 = "CATEGORY"
            int r5 = r0.getColumnIndex(r5)     // Catch:{ Exception -> 0x0081 }
            int r5 = r0.getInt(r5)     // Catch:{ Exception -> 0x0081 }
            com.autonavi.minimap.offline.nativesupport.CompatDataItem[] r6 = r2.items     // Catch:{ Exception -> 0x0081 }
            com.autonavi.minimap.offline.nativesupport.CompatDataItem r7 = new com.autonavi.minimap.offline.nativesupport.CompatDataItem     // Catch:{ Exception -> 0x0081 }
            r7.<init>()     // Catch:{ Exception -> 0x0081 }
            r6[r3] = r7     // Catch:{ Exception -> 0x0081 }
            com.autonavi.minimap.offline.nativesupport.CompatDataItem[] r6 = r2.items     // Catch:{ Exception -> 0x0081 }
            r6 = r6[r3]     // Catch:{ Exception -> 0x0081 }
            r6.cityId = r4     // Catch:{ Exception -> 0x0081 }
            com.autonavi.minimap.offline.nativesupport.CompatDataItem[] r4 = r2.items     // Catch:{ Exception -> 0x0081 }
            r4 = r4[r3]     // Catch:{ Exception -> 0x0081 }
            r4.packageType = r5     // Catch:{ Exception -> 0x0081 }
            com.autonavi.minimap.offline.nativesupport.CompatDataItem[] r4 = r2.items     // Catch:{ Exception -> 0x0081 }
            r4 = r4[r3]     // Catch:{ Exception -> 0x0081 }
            r4.status = r1     // Catch:{ Exception -> 0x0081 }
            int r3 = r3 + 1
            goto L_0x001f
        L_0x006a:
            r2.itemLength = r3     // Catch:{ Exception -> 0x0081 }
            r1 = 2
            r2.result = r1     // Catch:{ Exception -> 0x0081 }
            com.autonavi.minimap.offline.OfflineSDK r1 = com.autonavi.minimap.offline.OfflineSDK.getInstance()     // Catch:{ Exception -> 0x0081 }
            r3 = 1
            r1.setIsUpgradeAe8Version(r3)     // Catch:{ Exception -> 0x0081 }
            com.autonavi.minimap.offline.nativesupport.compat.BeforeV5DataCompat$1 r1 = new com.autonavi.minimap.offline.nativesupport.compat.BeforeV5DataCompat$1     // Catch:{ Exception -> 0x0081 }
            r1.<init>()     // Catch:{ Exception -> 0x0081 }
            defpackage.ahm.a(r1)     // Catch:{ Exception -> 0x0081 }
            r1 = r2
            goto L_0x0088
        L_0x0081:
            r1 = move-exception
            goto L_0x0097
        L_0x0083:
            r2 = move-exception
            r8 = r2
            r2 = r1
            r1 = r8
            goto L_0x0097
        L_0x0088:
            if (r0 == 0) goto L_0x00a0
            r0.close()
            goto L_0x00a0
        L_0x008e:
            r0 = move-exception
            r8 = r1
            r1 = r0
            r0 = r8
            goto L_0x00a2
        L_0x0093:
            r0 = move-exception
            r2 = r1
            r1 = r0
            r0 = r2
        L_0x0097:
            r1.printStackTrace()     // Catch:{ all -> 0x00a1 }
            if (r0 == 0) goto L_0x009f
            r0.close()
        L_0x009f:
            r1 = r2
        L_0x00a0:
            return r1
        L_0x00a1:
            r1 = move-exception
        L_0x00a2:
            if (r0 == 0) goto L_0x00a7
            r0.close()
        L_0x00a7:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.offline.nativesupport.compat.BeforeV5DataCompat.getCompatData():com.autonavi.minimap.offline.nativesupport.AmapCompatData");
    }
}
