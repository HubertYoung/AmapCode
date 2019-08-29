package com.autonavi.minimap.offline.nativesupport.compat;

import com.autonavi.minimap.offline.nativesupport.AmapCompatData;

public class V5DataCompat extends DataCompat {
    private static final int LINZHI_ADCODE = 540400;
    private static final int LINZHI_CITYID = 542600;

    public V5DataCompat(String str) {
        super(str);
    }

    public AmapCompatData check() {
        return getCompatData();
    }

    /* JADX WARNING: Removed duplicated region for block: B:34:0x00ac  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00b4  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.autonavi.minimap.offline.nativesupport.AmapCompatData getCompatData() {
        /*
            r11 = this;
            android.database.sqlite.SQLiteDatabase r0 = r11.db
            r1 = 0
            if (r0 != 0) goto L_0x0006
            return r1
        L_0x0006:
            android.database.sqlite.SQLiteDatabase r0 = r11.db     // Catch:{ Exception -> 0x00a3, all -> 0x009e }
            java.lang.String r2 = "SELECT _id, MAP_DOWNLOAD_STATUS, NAVI_DOWNLOAD_STATUS FROM DOWNLOAD_CITY"
            android.database.Cursor r0 = r0.rawQuery(r2, r1)     // Catch:{ Exception -> 0x00a3, all -> 0x009e }
            if (r0 == 0) goto L_0x0098
            com.autonavi.minimap.offline.nativesupport.AmapCompatData r2 = new com.autonavi.minimap.offline.nativesupport.AmapCompatData     // Catch:{ Exception -> 0x0093 }
            r2.<init>()     // Catch:{ Exception -> 0x0093 }
            int r1 = r0.getCount()     // Catch:{ Exception -> 0x0091 }
            r3 = 2
            int r1 = r1 * 2
            com.autonavi.minimap.offline.nativesupport.CompatDataItem[] r1 = new com.autonavi.minimap.offline.nativesupport.CompatDataItem[r1]     // Catch:{ Exception -> 0x0091 }
            r2.items = r1     // Catch:{ Exception -> 0x0091 }
            r1 = 0
            r4 = 0
        L_0x0022:
            boolean r5 = r0.moveToNext()     // Catch:{ Exception -> 0x0091 }
            r6 = 1
            if (r5 == 0) goto L_0x007c
            int r5 = r0.getInt(r1)     // Catch:{ Exception -> 0x0091 }
            r7 = 540400(0x83ef0, float:7.57262E-40)
            if (r5 != r7) goto L_0x0035
            r5 = 542600(0x84788, float:7.60345E-40)
        L_0x0035:
            int r7 = r0.getInt(r6)     // Catch:{ Exception -> 0x0091 }
            int r8 = r0.getInt(r3)     // Catch:{ Exception -> 0x0091 }
            if (r7 == 0) goto L_0x005c
            com.autonavi.minimap.offline.nativesupport.CompatDataItem[] r7 = r2.items     // Catch:{ Exception -> 0x0091 }
            com.autonavi.minimap.offline.nativesupport.CompatDataItem r9 = new com.autonavi.minimap.offline.nativesupport.CompatDataItem     // Catch:{ Exception -> 0x0091 }
            r9.<init>()     // Catch:{ Exception -> 0x0091 }
            r7[r4] = r9     // Catch:{ Exception -> 0x0091 }
            com.autonavi.minimap.offline.nativesupport.CompatDataItem[] r7 = r2.items     // Catch:{ Exception -> 0x0091 }
            r7 = r7[r4]     // Catch:{ Exception -> 0x0091 }
            r7.cityId = r5     // Catch:{ Exception -> 0x0091 }
            com.autonavi.minimap.offline.nativesupport.CompatDataItem[] r7 = r2.items     // Catch:{ Exception -> 0x0091 }
            r7 = r7[r4]     // Catch:{ Exception -> 0x0091 }
            r7.packageType = r1     // Catch:{ Exception -> 0x0091 }
            com.autonavi.minimap.offline.nativesupport.CompatDataItem[] r7 = r2.items     // Catch:{ Exception -> 0x0091 }
            r7 = r7[r4]     // Catch:{ Exception -> 0x0091 }
            r7.status = r1     // Catch:{ Exception -> 0x0091 }
            int r4 = r4 + 1
        L_0x005c:
            if (r8 == 0) goto L_0x0022
            com.autonavi.minimap.offline.nativesupport.CompatDataItem[] r7 = r2.items     // Catch:{ Exception -> 0x0091 }
            com.autonavi.minimap.offline.nativesupport.CompatDataItem r8 = new com.autonavi.minimap.offline.nativesupport.CompatDataItem     // Catch:{ Exception -> 0x0091 }
            r8.<init>()     // Catch:{ Exception -> 0x0091 }
            r7[r4] = r8     // Catch:{ Exception -> 0x0091 }
            com.autonavi.minimap.offline.nativesupport.CompatDataItem[] r7 = r2.items     // Catch:{ Exception -> 0x0091 }
            r7 = r7[r4]     // Catch:{ Exception -> 0x0091 }
            r7.cityId = r5     // Catch:{ Exception -> 0x0091 }
            com.autonavi.minimap.offline.nativesupport.CompatDataItem[] r5 = r2.items     // Catch:{ Exception -> 0x0091 }
            r5 = r5[r4]     // Catch:{ Exception -> 0x0091 }
            r5.packageType = r6     // Catch:{ Exception -> 0x0091 }
            com.autonavi.minimap.offline.nativesupport.CompatDataItem[] r5 = r2.items     // Catch:{ Exception -> 0x0091 }
            r5 = r5[r4]     // Catch:{ Exception -> 0x0091 }
            r5.status = r1     // Catch:{ Exception -> 0x0091 }
            int r4 = r4 + 1
            goto L_0x0022
        L_0x007c:
            r2.itemLength = r4     // Catch:{ Exception -> 0x0091 }
            r2.result = r3     // Catch:{ Exception -> 0x0091 }
            com.autonavi.minimap.offline.OfflineSDK r1 = com.autonavi.minimap.offline.OfflineSDK.getInstance()     // Catch:{ Exception -> 0x0091 }
            r1.setIsUpgradeAe8Version(r6)     // Catch:{ Exception -> 0x0091 }
            com.autonavi.minimap.offline.nativesupport.compat.V5DataCompat$1 r1 = new com.autonavi.minimap.offline.nativesupport.compat.V5DataCompat$1     // Catch:{ Exception -> 0x0091 }
            r1.<init>()     // Catch:{ Exception -> 0x0091 }
            defpackage.ahm.a(r1)     // Catch:{ Exception -> 0x0091 }
            r1 = r2
            goto L_0x0098
        L_0x0091:
            r1 = move-exception
            goto L_0x00a7
        L_0x0093:
            r2 = move-exception
            r10 = r2
            r2 = r1
            r1 = r10
            goto L_0x00a7
        L_0x0098:
            if (r0 == 0) goto L_0x00b0
            r0.close()
            goto L_0x00b0
        L_0x009e:
            r0 = move-exception
            r10 = r1
            r1 = r0
            r0 = r10
            goto L_0x00b2
        L_0x00a3:
            r0 = move-exception
            r2 = r1
            r1 = r0
            r0 = r2
        L_0x00a7:
            r1.printStackTrace()     // Catch:{ all -> 0x00b1 }
            if (r0 == 0) goto L_0x00af
            r0.close()
        L_0x00af:
            r1 = r2
        L_0x00b0:
            return r1
        L_0x00b1:
            r1 = move-exception
        L_0x00b2:
            if (r0 == 0) goto L_0x00b7
            r0.close()
        L_0x00b7:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.offline.nativesupport.compat.V5DataCompat.getCompatData():com.autonavi.minimap.offline.nativesupport.AmapCompatData");
    }
}
