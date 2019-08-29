package com.alibaba.sdk.trade.container.license;

import com.alibaba.baichuan.android.trade.AlibcContext;
import com.alibaba.baichuan.android.trade.AlibcContext.Environment;
import com.alibaba.baichuan.android.trade.utils.cache.CacheUtils;
import com.alibaba.baichuan.android.trade.utils.http.HttpHelper;
import com.alibaba.baichuan.android.trade.utils.log.AlibcLogger;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AlibcContainerLicenseManager {
    private static final long FAIL_TIME_STEP = 600000;
    private static final String ONLINE_CDN_URL = "https://nbsdk-baichuan.alicdn.com/%s/component_config.htm";
    private static final String PRE_CDN_URL = "http://nbsdk-baichuan.taobao.com/%s/component_config.htm";
    private static final String TEST_CDN_URL = "http://100.69.205.47/%s/component_config.htm";
    private static final long UPDATA_TIME_STEP = 86400000;
    private static final String WANT_LICENSE = "BC_Want_License";
    private static List<String> mLicenseList = null;
    /* access modifiers changed from: private */
    public static String mLicenseString = "";
    /* access modifiers changed from: private */
    public static long mRefresTime;
    private static AlibcContainerLicenseListener mWantLicenseListener;

    public static void initLicense(AlibcContainerLicenseListener alibcContainerLicenseListener) {
        mWantLicenseListener = alibcContainerLicenseListener;
        mLicenseList = Collections.synchronizedList(new ArrayList());
        mRefresTime = System.currentTimeMillis();
        getLicense2Cache();
        getLicense2CDN();
    }

    public static boolean checkLicense(String str) {
        if (System.currentTimeMillis() > mRefresTime + 86400000) {
            getLicense2CDN();
        }
        return mLicenseList != null && mLicenseList.indexOf(str) >= 0;
    }

    public static synchronized List<String> getLicense() {
        synchronized (AlibcContainerLicenseManager.class) {
            try {
                if (System.currentTimeMillis() > mRefresTime + 86400000) {
                    getLicense2CDN();
                }
                if (mLicenseList == null) {
                    ArrayList arrayList = new ArrayList();
                    return arrayList;
                }
                ArrayList arrayList2 = new ArrayList(mLicenseList);
                return arrayList2;
            }
        }
    }

    public static synchronized void destroy() {
        synchronized (AlibcContainerLicenseManager.class) {
            mWantLicenseListener = null;
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x006e, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0070, code lost:
        return false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized boolean setLicense(java.lang.String r7) {
        /*
            java.lang.Class<com.alibaba.sdk.trade.container.license.AlibcContainerLicenseManager> r0 = com.alibaba.sdk.trade.container.license.AlibcContainerLicenseManager.class
            monitor-enter(r0)
            java.lang.String r1 = "Alibc"
            java.lang.String r2 = "setLicense ："
            java.lang.String r3 = java.lang.String.valueOf(r7)     // Catch:{ all -> 0x0077 }
            java.lang.String r2 = r2.concat(r3)     // Catch:{ all -> 0x0077 }
            com.alibaba.baichuan.android.trade.utils.log.AlibcLogger.e(r1, r2)     // Catch:{ all -> 0x0077 }
            boolean r1 = android.text.TextUtils.isEmpty(r7)     // Catch:{ all -> 0x0077 }
            r2 = 0
            if (r1 == 0) goto L_0x001c
            monitor-exit(r0)
            return r2
        L_0x001c:
            org.json.JSONObject r1 = com.alibaba.baichuan.android.trade.utils.json.JSONUtils.getJsonObject(r7)     // Catch:{ Exception -> 0x0071 }
            if (r1 != 0) goto L_0x0024
            monitor-exit(r0)
            return r2
        L_0x0024:
            java.lang.String r3 = "appKey"
            java.lang.String r3 = r1.getString(r3)     // Catch:{ Exception -> 0x0071 }
            java.lang.String r4 = com.alibaba.baichuan.android.trade.AlibcContext.getAppKey()     // Catch:{ Exception -> 0x0071 }
            boolean r3 = android.text.TextUtils.equals(r3, r4)     // Catch:{ Exception -> 0x0071 }
            if (r3 != 0) goto L_0x0036
            monitor-exit(r0)
            return r2
        L_0x0036:
            java.lang.String r3 = "componentList"
            org.json.JSONArray r1 = r1.getJSONArray(r3)     // Catch:{ Exception -> 0x0071 }
            if (r1 == 0) goto L_0x006f
            int r3 = r1.length()     // Catch:{ Exception -> 0x0071 }
            if (r3 > 0) goto L_0x0045
            goto L_0x006f
        L_0x0045:
            java.util.ArrayList r3 = new java.util.ArrayList     // Catch:{ Exception -> 0x0071 }
            r3.<init>()     // Catch:{ Exception -> 0x0071 }
            int r4 = r1.length()     // Catch:{ Exception -> 0x0071 }
            r5 = 0
        L_0x004f:
            if (r5 >= r4) goto L_0x005f
            java.lang.Object r6 = r1.get(r5)     // Catch:{ Exception -> 0x0071 }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x0071 }
            r3.add(r6)     // Catch:{ Exception -> 0x0071 }
            int r5 = r5 + 1
            goto L_0x004f
        L_0x005f:
            mLicenseList = r3     // Catch:{ Exception -> 0x0071 }
            mLicenseString = r7     // Catch:{ Exception -> 0x0071 }
            com.alibaba.sdk.trade.container.license.AlibcContainerLicenseListener r7 = mWantLicenseListener     // Catch:{ Exception -> 0x0071 }
            if (r7 == 0) goto L_0x006c
            com.alibaba.sdk.trade.container.license.AlibcContainerLicenseListener r7 = mWantLicenseListener     // Catch:{ Exception -> 0x0071 }
            r7.updataLicense()     // Catch:{ Exception -> 0x0071 }
        L_0x006c:
            monitor-exit(r0)
            r7 = 1
            return r7
        L_0x006f:
            monitor-exit(r0)
            return r2
        L_0x0071:
            r7 = move-exception
            r7.printStackTrace()     // Catch:{ all -> 0x0077 }
            monitor-exit(r0)
            return r2
        L_0x0077:
            r7 = move-exception
            monitor-exit(r0)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.trade.container.license.AlibcContainerLicenseManager.setLicense(java.lang.String):boolean");
    }

    private static void getLicense2Cache() {
        setLicense(CacheUtils.getCache(WANT_LICENSE));
    }

    /* access modifiers changed from: private */
    public static void getLicense2CDN() {
        new Thread() {
            public final void run() {
                String str;
                if (AlibcContext.environment == Environment.TEST) {
                    str = String.format(AlibcContainerLicenseManager.TEST_CDN_URL, new Object[]{AlibcContext.getAppKey()});
                } else if (AlibcContext.environment == Environment.PRE) {
                    str = String.format(AlibcContainerLicenseManager.PRE_CDN_URL, new Object[]{AlibcContext.getAppKey()});
                } else {
                    str = String.format(AlibcContainerLicenseManager.ONLINE_CDN_URL, new Object[]{AlibcContext.getAppKey()});
                }
                try {
                    AlibcLogger.e("alibc", "getLicense2CDN : url  ".concat(String.valueOf(str)));
                    String str2 = HttpHelper.get(str, null);
                    StringBuilder sb = new StringBuilder("getLicense2CDN : url  ");
                    sb.append(str);
                    sb.append(Token.SEPARATOR);
                    sb.append(str2);
                    AlibcLogger.e("alibc", sb.toString());
                    if (AlibcContainerLicenseManager.setLicense(str2)) {
                        CacheUtils.asyncPutCache(AlibcContainerLicenseManager.WANT_LICENSE, AlibcContainerLicenseManager.mLicenseString);
                        AlibcContainerLicenseManager.mRefresTime = System.currentTimeMillis();
                        return;
                    }
                    new Timer().schedule(new TimerTask() {
                        public void run() {
                            AlibcContainerLicenseManager.getLicense2CDN();
                        }
                    }, AlibcContainerLicenseManager.FAIL_TIME_STEP);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
