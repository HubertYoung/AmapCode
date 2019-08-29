package com.sina.weibo.sdk;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;
import com.sina.weibo.sdk.auth.WbAppInfo;
import java.util.List;

public class WeiboAppManager {
    private static final String SDK_INT_FILE_NAME = "weibo_for_sdk.json";
    private static final String TAG = "com.sina.weibo.sdk.WeiboAppManager";
    private static final String WEIBO_4G_PACKAGENAME = "com.sina.weibog3";
    public static final String WEIBO_IDENTITY_ACTION = "com.sina.weibo.action.sdkidentity";
    private static final String WEIBO_PACKAGENAME = "com.sina.weibo";
    private static WeiboAppManager sInstance;
    private Context mContext;
    private WbAppInfo wbAppInfo;

    private WeiboAppManager(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public static synchronized WeiboAppManager getInstance(Context context) {
        WeiboAppManager weiboAppManager;
        synchronized (WeiboAppManager.class) {
            try {
                if (sInstance == null) {
                    sInstance = new WeiboAppManager(context);
                }
                weiboAppManager = sInstance;
            }
        }
        return weiboAppManager;
    }

    public synchronized WbAppInfo getWbAppInfo() {
        try {
        }
        return queryWbInfoInternal(this.mContext);
    }

    public static WbAppInfo queryWbInfoInternal(Context context) {
        return queryWbInfoByAsset(context);
    }

    @Deprecated
    public boolean hasWbInstall() {
        Intent intent = new Intent(WEIBO_IDENTITY_ACTION);
        intent.addCategory("android.intent.category.DEFAULT");
        List<ResolveInfo> queryIntentServices = this.mContext.getPackageManager().queryIntentServices(intent, 0);
        if (queryIntentServices == null || queryIntentServices.isEmpty()) {
            return false;
        }
        return true;
    }

    private static WbAppInfo queryWbInfoByAsset(Context context) {
        Intent intent = new Intent(WEIBO_IDENTITY_ACTION);
        intent.addCategory("android.intent.category.DEFAULT");
        List<ResolveInfo> queryIntentServices = context.getPackageManager().queryIntentServices(intent, 0);
        WbAppInfo wbAppInfo2 = null;
        if (queryIntentServices == null || queryIntentServices.isEmpty()) {
            return null;
        }
        for (ResolveInfo next : queryIntentServices) {
            if (!(next.serviceInfo == null || next.serviceInfo.applicationInfo == null || TextUtils.isEmpty(next.serviceInfo.packageName))) {
                String str = next.serviceInfo.packageName;
                WbAppInfo parseWbInfoByAsset = parseWbInfoByAsset(context, str);
                if (parseWbInfoByAsset != null) {
                    wbAppInfo2 = parseWbInfoByAsset;
                }
                if ("com.sina.weibo".equals(str) || WEIBO_4G_PACKAGENAME.equals(str)) {
                    break;
                }
            }
        }
        return wbAppInfo2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:40:0x0094 A[SYNTHETIC, Splitter:B:40:0x0094] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00a0 A[SYNTHETIC, Splitter:B:47:0x00a0] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.sina.weibo.sdk.auth.WbAppInfo parseWbInfoByAsset(android.content.Context r8, java.lang.String r9) {
        /*
            boolean r0 = android.text.TextUtils.isEmpty(r9)
            r1 = 0
            if (r0 == 0) goto L_0x0008
            return r1
        L_0x0008:
            r0 = 2
            android.content.Context r0 = r8.createPackageContext(r9, r0)     // Catch:{ Exception -> 0x0087, all -> 0x0084 }
            r2 = 4096(0x1000, float:5.74E-42)
            byte[] r3 = new byte[r2]     // Catch:{ Exception -> 0x0087, all -> 0x0084 }
            android.content.res.AssetManager r0 = r0.getAssets()     // Catch:{ Exception -> 0x0087, all -> 0x0084 }
            java.lang.String r4 = "weibo_for_sdk.json"
            java.io.InputStream r0 = r0.open(r4)     // Catch:{ Exception -> 0x0087, all -> 0x0084 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0082 }
            r4.<init>()     // Catch:{ Exception -> 0x0082 }
        L_0x0021:
            r5 = 0
            int r6 = r0.read(r3, r5, r2)     // Catch:{ Exception -> 0x0082 }
            r7 = -1
            if (r6 == r7) goto L_0x0032
            java.lang.String r7 = new java.lang.String     // Catch:{ Exception -> 0x0082 }
            r7.<init>(r3, r5, r6)     // Catch:{ Exception -> 0x0082 }
            r4.append(r7)     // Catch:{ Exception -> 0x0082 }
            goto L_0x0021
        L_0x0032:
            java.lang.String r2 = r4.toString()     // Catch:{ Exception -> 0x0082 }
            boolean r2 = android.text.TextUtils.isEmpty(r2)     // Catch:{ Exception -> 0x0082 }
            if (r2 != 0) goto L_0x003f
            com.sina.weibo.sdk.ApiUtils.validateWeiboSign(r8, r9)     // Catch:{ Exception -> 0x0082 }
        L_0x003f:
            org.json.JSONObject r8 = new org.json.JSONObject     // Catch:{ Exception -> 0x0082 }
            java.lang.String r2 = r4.toString()     // Catch:{ Exception -> 0x0082 }
            r8.<init>(r2)     // Catch:{ Exception -> 0x0082 }
            java.lang.String r2 = "support_api"
            int r2 = r8.optInt(r2, r7)     // Catch:{ Exception -> 0x0082 }
            java.lang.String r3 = "authActivityName"
            java.lang.String r8 = r8.optString(r3, r1)     // Catch:{ Exception -> 0x0082 }
            if (r2 == r7) goto L_0x0077
            boolean r3 = android.text.TextUtils.isEmpty(r8)     // Catch:{ Exception -> 0x0082 }
            if (r3 == 0) goto L_0x005e
            goto L_0x0077
        L_0x005e:
            com.sina.weibo.sdk.auth.WbAppInfo r3 = new com.sina.weibo.sdk.auth.WbAppInfo     // Catch:{ Exception -> 0x0082 }
            r3.<init>()     // Catch:{ Exception -> 0x0082 }
            r3.setPackageName(r9)     // Catch:{ Exception -> 0x0082 }
            r3.setSupportVersion(r2)     // Catch:{ Exception -> 0x0082 }
            r3.setAuthActivityName(r8)     // Catch:{ Exception -> 0x0082 }
            if (r0 == 0) goto L_0x0076
            r0.close()     // Catch:{ IOException -> 0x0072 }
            goto L_0x0076
        L_0x0072:
            r8 = move-exception
            r8.printStackTrace()
        L_0x0076:
            return r3
        L_0x0077:
            if (r0 == 0) goto L_0x0081
            r0.close()     // Catch:{ IOException -> 0x007d }
            goto L_0x0081
        L_0x007d:
            r8 = move-exception
            r8.printStackTrace()
        L_0x0081:
            return r1
        L_0x0082:
            r8 = move-exception
            goto L_0x0089
        L_0x0084:
            r8 = move-exception
            r0 = r1
            goto L_0x009e
        L_0x0087:
            r8 = move-exception
            r0 = r1
        L_0x0089:
            java.lang.String r9 = TAG     // Catch:{ all -> 0x009d }
            java.lang.String r8 = r8.getMessage()     // Catch:{ all -> 0x009d }
            com.sina.weibo.sdk.utils.LogUtil.e(r9, r8)     // Catch:{ all -> 0x009d }
            if (r0 == 0) goto L_0x009c
            r0.close()     // Catch:{ IOException -> 0x0098 }
            goto L_0x009c
        L_0x0098:
            r8 = move-exception
            r8.printStackTrace()
        L_0x009c:
            return r1
        L_0x009d:
            r8 = move-exception
        L_0x009e:
            if (r0 == 0) goto L_0x00a8
            r0.close()     // Catch:{ IOException -> 0x00a4 }
            goto L_0x00a8
        L_0x00a4:
            r9 = move-exception
            r9.printStackTrace()
        L_0x00a8:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sina.weibo.sdk.WeiboAppManager.parseWbInfoByAsset(android.content.Context, java.lang.String):com.sina.weibo.sdk.auth.WbAppInfo");
    }
}
