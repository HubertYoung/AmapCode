package com.alipay.mobile.common.transport.iprank.mng;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.mobile.common.transport.iprank.dao.IpLbsDao;
import com.alipay.mobile.common.transport.utils.LogCatUtil;

public class IpLbsManager {
    public static final String TAG = "IPR_IpLbsManager";
    public IpLbsDao ipLbsDao = new IpLbsDao(this.mContext);
    public Context mContext;

    public IpLbsManager(Context context) {
        this.mContext = context;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:28:?, code lost:
        com.alipay.mobile.common.transport.utils.LogCatUtil.info(TAG, "当前位置: " + r15 + " ,不在表中,将其插入lbs表");
        r3 = r14.ipLbsDao.insert2LBS(r15);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized long getLbsIdAnyway(java.lang.String r15) {
        /*
            r14 = this;
            r8 = -1
            monitor-enter(r14)
            boolean r10 = android.text.TextUtils.isEmpty(r15)     // Catch:{ all -> 0x008b }
            if (r10 == 0) goto L_0x0013
            java.lang.String r10 = "IPR_IpLbsManager"
            java.lang.String r11 = "getLbsId ,location is null"
            com.alipay.mobile.common.transport.utils.LogCatUtil.debug(r10, r11)     // Catch:{ all -> 0x008b }
            r3 = r8
        L_0x0011:
            monitor-exit(r14)
            return r3
        L_0x0013:
            com.alipay.mobile.common.transport.iprank.dao.IpLbsDao r10 = r14.ipLbsDao     // Catch:{ Exception -> 0x005d }
            java.util.ArrayList r6 = r10.getLbsModelsFromTable()     // Catch:{ Exception -> 0x005d }
            r7 = 0
            if (r6 == 0) goto L_0x0020
            int r7 = r6.size()     // Catch:{ Exception -> 0x005d }
        L_0x0020:
            r1 = 0
        L_0x0021:
            if (r1 >= r7) goto L_0x006a
            java.lang.Object r2 = r6.get(r1)     // Catch:{ Exception -> 0x005d }
            com.alipay.mobile.common.transport.iprank.dao.models.IpLbsModel r2 = (com.alipay.mobile.common.transport.iprank.dao.models.IpLbsModel) r2     // Catch:{ Exception -> 0x005d }
            java.lang.String r5 = r2.latlng     // Catch:{ Exception -> 0x005d }
            boolean r10 = android.text.TextUtils.isEmpty(r5)     // Catch:{ Exception -> 0x005d }
            if (r10 != 0) goto L_0x0067
            double r10 = com.alipay.mobile.common.transport.iprank.utils.IpRankUtil.getDistance(r15, r5)     // Catch:{ Exception -> 0x005d }
            r12 = 4617315517961601024(0x4014000000000000, double:5.0)
            int r10 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
            if (r10 >= 0) goto L_0x0067
            int r10 = r2.id     // Catch:{ Exception -> 0x005d }
            long r3 = (long) r10     // Catch:{ Exception -> 0x005d }
            java.lang.String r10 = "IPR_IpLbsManager"
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x005d }
            java.lang.String r12 = "当前位置: "
            r11.<init>(r12)     // Catch:{ Exception -> 0x005d }
            java.lang.StringBuilder r11 = r11.append(r15)     // Catch:{ Exception -> 0x005d }
            java.lang.String r12 = " ,已经存在表中,LBS_id: "
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ Exception -> 0x005d }
            java.lang.StringBuilder r11 = r11.append(r3)     // Catch:{ Exception -> 0x005d }
            java.lang.String r11 = r11.toString()     // Catch:{ Exception -> 0x005d }
            com.alipay.mobile.common.transport.utils.LogCatUtil.info(r10, r11)     // Catch:{ Exception -> 0x005d }
            goto L_0x0011
        L_0x005d:
            r0 = move-exception
            java.lang.String r10 = "IPR_IpLbsManager"
            java.lang.String r11 = "getLbsId exception"
            com.alipay.mobile.common.transport.utils.LogCatUtil.error(r10, r11, r0)     // Catch:{ all -> 0x008b }
            r3 = r8
            goto L_0x0011
        L_0x0067:
            int r1 = r1 + 1
            goto L_0x0021
        L_0x006a:
            java.lang.String r10 = "IPR_IpLbsManager"
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x005d }
            java.lang.String r12 = "当前位置: "
            r11.<init>(r12)     // Catch:{ Exception -> 0x005d }
            java.lang.StringBuilder r11 = r11.append(r15)     // Catch:{ Exception -> 0x005d }
            java.lang.String r12 = " ,不在表中,将其插入lbs表"
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ Exception -> 0x005d }
            java.lang.String r11 = r11.toString()     // Catch:{ Exception -> 0x005d }
            com.alipay.mobile.common.transport.utils.LogCatUtil.info(r10, r11)     // Catch:{ Exception -> 0x005d }
            com.alipay.mobile.common.transport.iprank.dao.IpLbsDao r10 = r14.ipLbsDao     // Catch:{ Exception -> 0x005d }
            long r3 = r10.insert2LBS(r15)     // Catch:{ Exception -> 0x005d }
            goto L_0x0011
        L_0x008b:
            r8 = move-exception
            monitor-exit(r14)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.transport.iprank.mng.IpLbsManager.getLbsIdAnyway(java.lang.String):long");
    }

    public long insert2LBS(String location) {
        if (!TextUtils.isEmpty(location)) {
            return this.ipLbsDao.insert2LBS(location);
        }
        LogCatUtil.debug(TAG, "insert2LBS,latlng is null");
        return -1;
    }

    public int getTableSize() {
        return this.ipLbsDao.getTableSize();
    }
}
