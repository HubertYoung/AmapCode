package com.alipay.mobile.nebulacore.appcenter.parse;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.alipay.mobile.nebula.provider.H5AppProvider;
import com.alipay.mobile.nebula.provider.H5PublicRsaProvider;
import com.alipay.mobile.nebula.util.H5FileUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.api.H5ParseResult;
import com.alipay.mobile.nebulacore.env.H5Environment;
import java.io.File;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class H5PackageParser {
    public static final String DEFAULT_TAR_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC2y61svV7Q0gmvxprTt6YX76rps8R+q+C+Qtkkk2+njIABsf10sHnl/5aQBh2s+kdo6YGlJrnKdxVso2JRzy+QbRBUgTdJmKfm5uGPdcqYuO0ur4b/QCyHTMoKJjBT8iI3hYIGhn0hACDao4xIsgzJ39grRKUa6120WbInlOLWSQIDAQAB";
    public static final String ENTRY_HEADER_JSON = "header.json";
    public static final String ENTRY_TABBAR_JSON = "tabBar.json";
    public static final String NEW_H5APP_SIGN_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAl96KRuzoQDgt3q3478MYKwTGDV0Fz5w+sKOfz+Ar+/XkwqLjVW7bAk+/nOD9Z4mnwM+BsgU/G5KGQ9WMjcXAow/eRBSf93iqcBX5+DdlkbneNyQP7Mvcy8EwOAa3y7AetEpTeYrv5cppFUjq4TVu9w+DwV1qegfvJEAA+6gFJEcJPxD9fxJggCF02tL3k9/WDnaNYVN3dCq8fN4jWZLr6KWlAX5UW5ZVtXP9IWObFnvRNjgXQhW/LzJLdbcDlQ5U6ImFyIFf//vn3vEhzlpU6EkxdGr+FWwsRiMTY9aZ1fJiFlgAZQpInV6cbDM8LgNGPtDsYUibIi3rVFtYtHAxQwIDAQAB";
    public static final String TAG = "H5PackageParser";
    public static final String TAR_PUBLIC_KEY = a();
    public static final String TAR_VERIFY_FAIL = "tarVerifyFail";
    public static boolean sHasSetLastModifiedFail = false;

    private static String a() {
        H5PublicRsaProvider h5PublicRsaProvider = (H5PublicRsaProvider) Nebula.getProviderManager().getProvider(H5PublicRsaProvider.class.getName());
        if (h5PublicRsaProvider != null) {
            String rsa = h5PublicRsaProvider.getPublicRsa();
            if (!TextUtils.isEmpty(rsa)) {
                return rsa;
            }
        }
        return "";
    }

    private static H5ParseResult a(int code, String msg) {
        H5ParseResult h5ParseResult = new H5ParseResult();
        h5ParseResult.code = code;
        h5ParseResult.msg = msg;
        return h5ParseResult;
    }

    /* JADX WARNING: Removed duplicated region for block: B:157:0x0717  */
    /* JADX WARNING: Removed duplicated region for block: B:186:0x07e9  */
    /* JADX WARNING: Removed duplicated region for block: B:238:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.alipay.mobile.nebulacore.api.H5ParseResult parsePackage(android.os.Bundle r78, java.util.Map<java.lang.String, byte[]> r79) {
        /*
            if (r78 != 0) goto L_0x0012
            java.lang.String r71 = "H5PackageParser"
            java.lang.String r72 = "invalid params "
            com.alipay.mobile.nebula.util.H5Log.e(r71, r72)
            r71 = 1
            java.lang.String r72 = "INVALID_PARAM"
            com.alipay.mobile.nebulacore.api.H5ParseResult r71 = a(r71, r72)
        L_0x0011:
            return r71
        L_0x0012:
            com.alibaba.fastjson.JSONObject r63 = new com.alibaba.fastjson.JSONObject
            r63.<init>()
            long r66 = java.lang.System.currentTimeMillis()
            java.lang.String r71 = "appId"
            r0 = r78
            r1 = r71
            java.lang.String r5 = com.alipay.mobile.nebula.util.H5Utils.getString(r0, r1)
            java.lang.String r71 = "offlineHost"
            r0 = r78
            r1 = r71
            java.lang.String r37 = com.alipay.mobile.nebula.util.H5Utils.getString(r0, r1)
            java.lang.String r71 = "onlineHost"
            r0 = r78
            r1 = r71
            java.lang.String r42 = com.alipay.mobile.nebula.util.H5Utils.getString(r0, r1)
            java.lang.String r71 = "appType"
            r0 = r78
            r1 = r71
            int r71 = com.alipay.mobile.nebula.util.H5Utils.getInt(r0, r1)
            r72 = 2
            r0 = r71
            r1 = r72
            if (r0 != r1) goto L_0x009f
            r44 = 1
        L_0x004d:
            java.lang.String r71 = "mapHost"
            r72 = 0
            r0 = r78
            r1 = r71
            r2 = r72
            boolean r29 = com.alipay.mobile.nebula.util.H5Utils.getBoolean(r0, r1, r2)
            java.lang.String r71 = "appVersion"
            r0 = r78
            r1 = r71
            java.lang.String r70 = com.alipay.mobile.nebula.util.H5Utils.getString(r0, r1)
            java.lang.String r71 = "isNebulaApp"
            r72 = 0
            r0 = r78
            r1 = r71
            r2 = r72
            boolean r27 = com.alipay.mobile.nebula.util.H5Utils.getBoolean(r0, r1, r2)
            java.lang.String r71 = "needForceVerify"
            r72 = 0
            r0 = r78
            r1 = r71
            r2 = r72
            boolean r36 = com.alipay.mobile.nebula.util.H5Utils.getBoolean(r0, r1, r2)
            java.lang.String r71 = "customPublicKey"
            java.lang.String r72 = ""
            r0 = r78
            r1 = r71
            r2 = r72
            java.lang.String r43 = com.alipay.mobile.nebula.util.H5Utils.getString(r0, r1, r2)
            boolean r71 = android.text.TextUtils.isEmpty(r5)
            if (r71 == 0) goto L_0x00a2
            r71 = 1
            java.lang.String r72 = "appIdIsEmpty"
            com.alipay.mobile.nebulacore.api.H5ParseResult r71 = a(r71, r72)
            goto L_0x0011
        L_0x009f:
            r44 = 0
            goto L_0x004d
        L_0x00a2:
            java.lang.String r71 = "H5PackageParser"
            java.lang.StringBuilder r72 = new java.lang.StringBuilder
            java.lang.String r73 = "parse package appId "
            r72.<init>(r73)
            r0 = r72
            java.lang.StringBuilder r72 = r0.append(r5)
            java.lang.String r73 = " mapHost "
            java.lang.StringBuilder r72 = r72.append(r73)
            r0 = r72
            r1 = r29
            java.lang.StringBuilder r72 = r0.append(r1)
            java.lang.String r73 = " offlineHost:"
            java.lang.StringBuilder r72 = r72.append(r73)
            r0 = r72
            r1 = r37
            java.lang.StringBuilder r72 = r0.append(r1)
            java.lang.String r73 = " onlineHost:"
            java.lang.StringBuilder r72 = r72.append(r73)
            r0 = r72
            r1 = r42
            java.lang.StringBuilder r72 = r0.append(r1)
            java.lang.String r73 = " version:"
            java.lang.StringBuilder r72 = r72.append(r73)
            r0 = r72
            r1 = r70
            java.lang.StringBuilder r72 = r0.append(r1)
            java.lang.String r73 = " isNebula "
            java.lang.StringBuilder r72 = r72.append(r73)
            r0 = r72
            r1 = r27
            java.lang.StringBuilder r72 = r0.append(r1)
            java.lang.String r72 = r72.toString()
            com.alipay.mobile.nebula.util.H5Log.d(r71, r72)
            boolean r71 = android.text.TextUtils.isEmpty(r37)
            if (r71 == 0) goto L_0x017e
            com.alipay.mobile.nebula.provider.H5ProviderManager r71 = com.alipay.mobile.nebulacore.Nebula.getProviderManager()
            java.lang.Class<com.alipay.mobile.nebula.provider.H5AppProvider> r72 = com.alipay.mobile.nebula.provider.H5AppProvider.class
            java.lang.String r72 = r72.getName()
            java.lang.Object r7 = r71.getProvider(r72)
            com.alipay.mobile.nebula.provider.H5AppProvider r7 = (com.alipay.mobile.nebula.provider.H5AppProvider) r7
            if (r7 == 0) goto L_0x017e
            r0 = r70
            java.lang.String r26 = r7.getInstallPath(r5, r0)
            boolean r71 = android.text.TextUtils.isEmpty(r26)
            if (r71 != 0) goto L_0x0158
            java.lang.StringBuilder r71 = new java.lang.StringBuilder
            java.lang.String r72 = "file://"
            r71.<init>(r72)
            r0 = r71
            r1 = r26
            java.lang.StringBuilder r71 = r0.append(r1)
            java.lang.String r37 = r71.toString()
            java.lang.String r71 = "/"
            r0 = r37
            r1 = r71
            boolean r71 = r0.endsWith(r1)
            if (r71 != 0) goto L_0x0158
            java.lang.StringBuilder r71 = new java.lang.StringBuilder
            r71.<init>()
            r0 = r71
            r1 = r37
            java.lang.StringBuilder r71 = r0.append(r1)
            java.lang.String r72 = "/"
            java.lang.StringBuilder r71 = r71.append(r72)
            java.lang.String r37 = r71.toString()
        L_0x0158:
            java.lang.String r71 = "H5PackageParser"
            java.lang.StringBuilder r72 = new java.lang.StringBuilder
            java.lang.String r73 = "installPath : "
            r72.<init>(r73)
            r0 = r72
            r1 = r26
            java.lang.StringBuilder r72 = r0.append(r1)
            java.lang.String r73 = " offlineHost : "
            java.lang.StringBuilder r72 = r72.append(r73)
            r0 = r72
            r1 = r37
            java.lang.StringBuilder r72 = r0.append(r1)
            java.lang.String r72 = r72.toString()
            com.alipay.mobile.nebula.util.H5Log.d(r71, r72)
        L_0x017e:
            boolean r71 = com.alipay.mobile.nebulacore.Nebula.DEBUG
            if (r71 == 0) goto L_0x01e0
            com.alipay.mobile.nebula.provider.H5ProviderManager r71 = com.alipay.mobile.nebulacore.Nebula.getProviderManager()
            java.lang.Class<com.alipay.mobile.nebula.provider.H5AppProvider> r72 = com.alipay.mobile.nebula.provider.H5AppProvider.class
            java.lang.String r72 = r72.getName()
            java.lang.Object r7 = r71.getProvider(r72)
            com.alipay.mobile.nebula.provider.H5AppProvider r7 = (com.alipay.mobile.nebula.provider.H5AppProvider) r7
            if (r7 == 0) goto L_0x01e0
            boolean r71 = android.text.TextUtils.isEmpty(r70)
            if (r71 != 0) goto L_0x01e0
            r0 = r70
            long r54 = r7.getPackageSize(r5, r0)
            java.lang.String r71 = "H5PackageParser"
            java.lang.StringBuilder r72 = new java.lang.StringBuilder
            java.lang.String r73 = "inject data size is "
            r72.<init>(r73)
            r0 = r72
            r1 = r54
            java.lang.StringBuilder r72 = r0.append(r1)
            java.lang.String r73 = ", version is "
            java.lang.StringBuilder r72 = r72.append(r73)
            r0 = r72
            r1 = r70
            java.lang.StringBuilder r72 = r0.append(r1)
            java.lang.String r72 = r72.toString()
            com.alipay.mobile.nebula.util.H5Log.d(r71, r72)
            java.lang.String r71 = "size"
            java.lang.Long r72 = java.lang.Long.valueOf(r54)
            r0 = r63
            r1 = r71
            r2 = r72
            r0.put(r1, r2)
            java.lang.String r71 = "version"
            r0 = r63
            r1 = r71
            r2 = r70
            r0.put(r1, r2)
        L_0x01e0:
            android.net.Uri r41 = com.alipay.mobile.nebula.util.H5UrlHelper.parseUrl(r37)
            if (r41 == 0) goto L_0x01f0
            java.lang.String r71 = r41.getPath()
            boolean r71 = android.text.TextUtils.isEmpty(r71)
            if (r71 == 0) goto L_0x01fa
        L_0x01f0:
            r71 = 1
            java.lang.String r72 = "INVALID_PARAM"
            com.alipay.mobile.nebulacore.api.H5ParseResult r71 = a(r71, r72)
            goto L_0x0011
        L_0x01fa:
            java.lang.String r40 = r41.getPath()
            boolean r71 = com.alipay.mobile.nebula.util.H5FileUtil.exists(r40)
            if (r71 != 0) goto L_0x022c
            java.lang.String r71 = "H5PackageParser"
            java.lang.StringBuilder r72 = new java.lang.StringBuilder
            java.lang.String r73 = "offlinePath "
            r72.<init>(r73)
            r0 = r72
            r1 = r40
            java.lang.StringBuilder r72 = r0.append(r1)
            java.lang.String r73 = " not exists!"
            java.lang.StringBuilder r72 = r72.append(r73)
            java.lang.String r72 = r72.toString()
            com.alipay.mobile.nebula.util.H5Log.e(r71, r72)
            r71 = 2
            java.lang.String r72 = "OFFLINE_PATH_NOT_EXIST"
            com.alipay.mobile.nebulacore.api.H5ParseResult r71 = a(r71, r72)
            goto L_0x0011
        L_0x022c:
            java.io.File r25 = new java.io.File
            r0 = r25
            r1 = r40
            r0.<init>(r1)
            java.lang.String r71 = "no"
            java.lang.String r72 = "h5_setLastModified"
            java.lang.String r72 = com.alipay.mobile.nebulacore.env.H5Environment.getConfigWithProcessCache(r72)
            boolean r71 = r71.equalsIgnoreCase(r72)
            if (r71 != 0) goto L_0x02a1
            long r38 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x031c }
            long r30 = r25.lastModified()     // Catch:{ Throwable -> 0x031c }
            r0 = r25
            r1 = r38
            boolean r46 = r0.setLastModified(r1)     // Catch:{ Throwable -> 0x031c }
            if (r46 != 0) goto L_0x0259
            r71 = 1
            sHasSetLastModifiedFail = r71     // Catch:{ Throwable -> 0x031c }
        L_0x0259:
            java.lang.String r71 = "H5PackageParser"
            java.lang.StringBuilder r72 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x031c }
            java.lang.String r73 = "lastModified "
            r72.<init>(r73)     // Catch:{ Throwable -> 0x031c }
            r0 = r72
            r1 = r30
            java.lang.StringBuilder r72 = r0.append(r1)     // Catch:{ Throwable -> 0x031c }
            java.lang.String r73 = " setResult:"
            java.lang.StringBuilder r72 = r72.append(r73)     // Catch:{ Throwable -> 0x031c }
            r0 = r72
            r1 = r46
            java.lang.StringBuilder r72 = r0.append(r1)     // Catch:{ Throwable -> 0x031c }
            java.lang.String r73 = " newTime:"
            java.lang.StringBuilder r72 = r72.append(r73)     // Catch:{ Throwable -> 0x031c }
            r0 = r72
            r1 = r38
            java.lang.StringBuilder r72 = r0.append(r1)     // Catch:{ Throwable -> 0x031c }
            java.lang.String r73 = " cost:"
            java.lang.StringBuilder r72 = r72.append(r73)     // Catch:{ Throwable -> 0x031c }
            long r74 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x031c }
            long r74 = r74 - r38
            r0 = r72
            r1 = r74
            java.lang.StringBuilder r72 = r0.append(r1)     // Catch:{ Throwable -> 0x031c }
            java.lang.String r72 = r72.toString()     // Catch:{ Throwable -> 0x031c }
            com.alipay.mobile.nebula.util.H5Log.d(r71, r72)     // Catch:{ Throwable -> 0x031c }
        L_0x02a1:
            java.lang.Class<com.alipay.mobile.nebula.provider.H5TinyAppProvider> r71 = com.alipay.mobile.nebula.provider.H5TinyAppProvider.class
            java.lang.String r71 = r71.getName()
            java.lang.Object r21 = com.alipay.mobile.nebula.util.H5Utils.getProvider(r71)
            com.alipay.mobile.nebula.provider.H5TinyAppProvider r21 = (com.alipay.mobile.nebula.provider.H5TinyAppProvider) r21
            r58 = r5
            if (r21 == 0) goto L_0x02c1
            r0 = r21
            r1 = r78
            java.lang.String r62 = r0.getTemplateAppId(r5, r1)
            boolean r71 = android.text.TextUtils.isEmpty(r62)
            if (r71 != 0) goto L_0x02c1
            r58 = r62
        L_0x02c1:
            java.lang.StringBuilder r71 = new java.lang.StringBuilder
            r71.<init>()
            r0 = r71
            r1 = r40
            java.lang.StringBuilder r71 = r0.append(r1)
            java.lang.String r72 = "/"
            java.lang.StringBuilder r71 = r71.append(r72)
            r0 = r71
            r1 = r58
            java.lang.StringBuilder r71 = r0.append(r1)
            java.lang.String r72 = ".tar"
            java.lang.StringBuilder r71 = r71.append(r72)
            java.lang.String r53 = r71.toString()
            java.lang.String r71 = "H5PackageParser"
            java.lang.StringBuilder r72 = new java.lang.StringBuilder
            java.lang.String r73 = "tarPath "
            r72.<init>(r73)
            r0 = r72
            r1 = r53
            java.lang.StringBuilder r72 = r0.append(r1)
            java.lang.String r72 = r72.toString()
            com.alipay.mobile.nebula.util.H5Log.d(r71, r72)
            boolean r71 = com.alipay.mobile.nebula.util.H5FileUtil.exists(r53)
            if (r71 != 0) goto L_0x0328
            java.lang.String r71 = "H5PackageParser"
            java.lang.String r72 = "tar package not exists!"
            com.alipay.mobile.nebula.util.H5Log.w(r71, r72)
            r0 = r37
            r1 = r78
            a(r5, r0, r1)
            r71 = 3
            java.lang.String r72 = "TAR_PATH_NOT_EXIST"
            com.alipay.mobile.nebulacore.api.H5ParseResult r71 = a(r71, r72)
            goto L_0x0011
        L_0x031c:
            r64 = move-exception
            java.lang.String r71 = "H5PackageParser"
            r0 = r71
            r1 = r64
            com.alipay.mobile.nebula.util.H5Log.e(r0, r1)
            goto L_0x02a1
        L_0x0328:
            java.lang.StringBuilder r71 = new java.lang.StringBuilder
            r71.<init>()
            r0 = r71
            r1 = r40
            java.lang.StringBuilder r71 = r0.append(r1)
            java.lang.String r72 = "/CERT.json"
            java.lang.StringBuilder r71 = r71.append(r72)
            java.lang.String r11 = r71.toString()
            java.lang.StringBuilder r71 = new java.lang.StringBuilder
            r71.<init>()
            r0 = r71
            r1 = r40
            java.lang.StringBuilder r71 = r0.append(r1)
            java.lang.String r72 = "/SIGN.json"
            java.lang.StringBuilder r71 = r71.append(r72)
            java.lang.String r50 = r71.toString()
            boolean r71 = com.alipay.mobile.nebula.util.H5FileUtil.exists(r11)
            if (r71 != 0) goto L_0x037a
            boolean r71 = com.alipay.mobile.nebula.util.H5FileUtil.exists(r50)
            if (r71 != 0) goto L_0x037a
            java.lang.String r71 = "H5PackageParser"
            java.lang.String r72 = "cert not exists!"
            com.alipay.mobile.nebula.util.H5Log.w(r71, r72)
            r0 = r37
            r1 = r78
            a(r5, r0, r1)
            r71 = 4
            java.lang.String r72 = "CERT_PATH_NOT_EXIST"
            com.alipay.mobile.nebulacore.api.H5ParseResult r71 = a(r71, r72)
            goto L_0x0011
        L_0x037a:
            long r60 = java.lang.System.currentTimeMillis()
            boolean r71 = isNeedVerify()
            if (r71 != 0) goto L_0x0386
            if (r36 == 0) goto L_0x062e
        L_0x0386:
            java.io.File r71 = new java.io.File
            r0 = r71
            r1 = r40
            r0.<init>(r1)
            java.io.File[] r12 = r71.listFiles()
            if (r12 == 0) goto L_0x039a
            int r0 = r12.length
            r71 = r0
            if (r71 != 0) goto L_0x03ab
        L_0x039a:
            java.lang.String r71 = "H5PackageParser"
            java.lang.String r72 = "childrenFiles length == 0"
            com.alipay.mobile.nebula.util.H5Log.e(r71, r72)
            r71 = 2
            java.lang.String r72 = "OFFLINE_PATH_NOT_EXIST"
            com.alipay.mobile.nebulacore.api.H5ParseResult r71 = a(r71, r72)
            goto L_0x0011
        L_0x03ab:
            boolean r69 = com.alipay.mobile.nebula.util.H5FileUtil.exists(r50)
            r51 = r69
            if (r69 == 0) goto L_0x03c1
            boolean r71 = android.text.TextUtils.isEmpty(r43)
            if (r71 == 0) goto L_0x03c1
            java.lang.String r71 = TAR_PUBLIC_KEY
            boolean r71 = android.text.TextUtils.isEmpty(r71)
            if (r71 != 0) goto L_0x03f5
        L_0x03c1:
            java.lang.String r71 = "H5PackageParser"
            java.lang.StringBuilder r72 = new java.lang.StringBuilder
            java.lang.String r73 = "signPath is exist : "
            r72.<init>(r73)
            r0 = r72
            r1 = r51
            java.lang.StringBuilder r72 = r0.append(r1)
            java.lang.String r73 = ", publicKey : "
            java.lang.StringBuilder r72 = r72.append(r73)
            r0 = r72
            r1 = r43
            java.lang.StringBuilder r72 = r0.append(r1)
            java.lang.String r73 = ", H5PackageParser.TAR_PUBLIC_KEY : "
            java.lang.StringBuilder r72 = r72.append(r73)
            java.lang.String r73 = TAR_PUBLIC_KEY
            java.lang.StringBuilder r72 = r72.append(r73)
            java.lang.String r72 = r72.toString()
            com.alipay.mobile.nebula.util.H5Log.d(r71, r72)
            r69 = 0
        L_0x03f5:
            java.lang.String r71 = "H5PackageParser"
            java.lang.StringBuilder r72 = new java.lang.StringBuilder
            java.lang.String r73 = "useNewSignKey : "
            r72.<init>(r73)
            r0 = r72
            r1 = r69
            java.lang.StringBuilder r72 = r0.append(r1)
            java.lang.String r72 = r72.toString()
            com.alipay.mobile.nebula.util.H5Log.d(r71, r72)
            if (r69 == 0) goto L_0x0437
            java.lang.String r71 = com.alipay.mobile.nebula.util.H5Utils.read(r50)
            com.alibaba.fastjson.JSONObject r28 = com.alipay.mobile.nebula.util.H5Utils.parseObject(r71)
        L_0x0417:
            if (r28 == 0) goto L_0x041f
            boolean r71 = r28.isEmpty()
            if (r71 == 0) goto L_0x0440
        L_0x041f:
            java.lang.String r71 = "H5PackageParser"
            java.lang.String r72 = "joCert is empty"
            com.alipay.mobile.nebula.util.H5Log.e(r71, r72)
            r0 = r37
            r1 = r78
            a(r5, r0, r1)
            r71 = 5
            java.lang.String r72 = "TAR_SIGNATURE_IS_EMPTY"
            com.alipay.mobile.nebulacore.api.H5ParseResult r71 = a(r71, r72)
            goto L_0x0011
        L_0x0437:
            java.lang.String r71 = com.alipay.mobile.nebula.util.H5Utils.read(r11)
            com.alibaba.fastjson.JSONObject r28 = com.alipay.mobile.nebula.util.H5Utils.parseObject(r71)
            goto L_0x0417
        L_0x0440:
            int r0 = r12.length     // Catch:{ Exception -> 0x05dc }
            r72 = r0
            r71 = 0
        L_0x0445:
            r0 = r71
            r1 = r72
            if (r0 >= r1) goto L_0x05e4
            r73 = r12[r71]     // Catch:{ Exception -> 0x05dc }
            java.lang.String r47 = r73.getName()     // Catch:{ Exception -> 0x05dc }
            long r48 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x05dc }
            boolean r73 = android.text.TextUtils.isEmpty(r47)     // Catch:{ Exception -> 0x05dc }
            if (r73 != 0) goto L_0x05d8
            java.lang.String r73 = "CERT.json"
            r0 = r47
            r1 = r73
            boolean r73 = android.text.TextUtils.equals(r0, r1)     // Catch:{ Exception -> 0x05dc }
            if (r73 != 0) goto L_0x05d8
            java.lang.String r73 = "SIGN.json"
            r0 = r47
            r1 = r73
            boolean r73 = android.text.TextUtils.equals(r0, r1)     // Catch:{ Exception -> 0x05dc }
            if (r73 != 0) goto L_0x05d8
            java.lang.String r73 = "ios"
            r0 = r47
            r1 = r73
            boolean r73 = r0.contains(r1)     // Catch:{ Exception -> 0x05dc }
            if (r73 != 0) goto L_0x05d8
            java.lang.String r73 = "android.tiny.tar"
            r0 = r47
            r1 = r73
            boolean r73 = r0.contains(r1)     // Catch:{ Exception -> 0x05dc }
            if (r73 != 0) goto L_0x05d8
            r0 = r28
            r1 = r47
            java.lang.Object r73 = r0.get(r1)     // Catch:{ Exception -> 0x05dc }
            java.lang.String r52 = r73.toString()     // Catch:{ Exception -> 0x05dc }
            if (r69 == 0) goto L_0x0545
            java.lang.StringBuilder r73 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x05dc }
            r73.<init>()     // Catch:{ Exception -> 0x05dc }
            r0 = r73
            r1 = r40
            java.lang.StringBuilder r73 = r0.append(r1)     // Catch:{ Exception -> 0x05dc }
            java.lang.String r74 = "/"
            java.lang.StringBuilder r73 = r73.append(r74)     // Catch:{ Exception -> 0x05dc }
            r0 = r73
            r1 = r47
            java.lang.StringBuilder r73 = r0.append(r1)     // Catch:{ Exception -> 0x05dc }
            java.lang.String r73 = r73.toString()     // Catch:{ Exception -> 0x05dc }
            java.lang.String r74 = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAl96KRuzoQDgt3q3478MYKwTGDV0Fz5w+sKOfz+Ar+/XkwqLjVW7bAk+/nOD9Z4mnwM+BsgU/G5KGQ9WMjcXAow/eRBSf93iqcBX5+DdlkbneNyQP7Mvcy8EwOAa3y7AetEpTeYrv5cppFUjq4TVu9w+DwV1qegfvJEAA+6gFJEcJPxD9fxJggCF02tL3k9/WDnaNYVN3dCq8fN4jWZLr6KWlAX5UW5ZVtXP9IWObFnvRNjgXQhW/LzJLdbcDlQ5U6ImFyIFf//vn3vEhzlpU6EkxdGr+FWwsRiMTY9aZ1fJiFlgAZQpInV6cbDM8LgNGPtDsYUibIi3rVFtYtHAxQwIDAQAB"
            r0 = r73
            r1 = r74
            r2 = r52
            boolean r45 = com.alipay.mobile.nebula.util.H5RsaUtil.verify(r0, r1, r2)     // Catch:{ Exception -> 0x05dc }
        L_0x04c4:
            java.lang.String r73 = "H5PackageParser"
            java.lang.StringBuilder r74 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x05dc }
            java.lang.String r75 = "signKey "
            r74.<init>(r75)     // Catch:{ Exception -> 0x05dc }
            r0 = r74
            r1 = r47
            java.lang.StringBuilder r74 = r0.append(r1)     // Catch:{ Exception -> 0x05dc }
            java.lang.String r75 = " signValue "
            java.lang.StringBuilder r74 = r74.append(r75)     // Catch:{ Exception -> 0x05dc }
            r0 = r74
            r1 = r52
            java.lang.StringBuilder r74 = r0.append(r1)     // Catch:{ Exception -> 0x05dc }
            java.lang.String r75 = " result:"
            java.lang.StringBuilder r74 = r74.append(r75)     // Catch:{ Exception -> 0x05dc }
            r0 = r74
            r1 = r45
            java.lang.StringBuilder r74 = r0.append(r1)     // Catch:{ Exception -> 0x05dc }
            java.lang.String r75 = " cost:"
            java.lang.StringBuilder r74 = r74.append(r75)     // Catch:{ Exception -> 0x05dc }
            long r76 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x05dc }
            long r76 = r76 - r48
            r0 = r74
            r1 = r76
            java.lang.StringBuilder r74 = r0.append(r1)     // Catch:{ Exception -> 0x05dc }
            java.lang.String r74 = r74.toString()     // Catch:{ Exception -> 0x05dc }
            com.alipay.mobile.nebula.util.H5Log.d(r73, r74)     // Catch:{ Exception -> 0x05dc }
            if (r45 != 0) goto L_0x05d8
            boolean r71 = com.alipay.mobile.nebulacore.Nebula.DEBUG     // Catch:{ Exception -> 0x05dc }
            if (r71 == 0) goto L_0x0534
            long r72 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x05dc }
            long r56 = r72 - r60
            java.lang.String r71 = "verifyResult"
            java.lang.String r72 = "fail"
            r0 = r63
            r1 = r71
            r2 = r72
            r0.put(r1, r2)     // Catch:{ Exception -> 0x05dc }
            java.lang.String r71 = "verifyDuration"
            java.lang.Long r72 = java.lang.Long.valueOf(r56)     // Catch:{ Exception -> 0x05dc }
            r0 = r63
            r1 = r71
            r2 = r72
            r0.put(r1, r2)     // Catch:{ Exception -> 0x05dc }
        L_0x0534:
            r0 = r37
            r1 = r78
            a(r5, r0, r1)     // Catch:{ Exception -> 0x05dc }
            r71 = 6
            java.lang.String r72 = "VERIFY_FAIL"
            com.alipay.mobile.nebulacore.api.H5ParseResult r71 = a(r71, r72)     // Catch:{ Exception -> 0x05dc }
            goto L_0x0011
        L_0x0545:
            boolean r73 = android.text.TextUtils.isEmpty(r43)     // Catch:{ Exception -> 0x05dc }
            if (r73 != 0) goto L_0x0576
            java.lang.StringBuilder r73 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x05dc }
            r73.<init>()     // Catch:{ Exception -> 0x05dc }
            r0 = r73
            r1 = r40
            java.lang.StringBuilder r73 = r0.append(r1)     // Catch:{ Exception -> 0x05dc }
            java.lang.String r74 = "/"
            java.lang.StringBuilder r73 = r73.append(r74)     // Catch:{ Exception -> 0x05dc }
            r0 = r73
            r1 = r47
            java.lang.StringBuilder r73 = r0.append(r1)     // Catch:{ Exception -> 0x05dc }
            java.lang.String r73 = r73.toString()     // Catch:{ Exception -> 0x05dc }
            r0 = r73
            r1 = r43
            r2 = r52
            boolean r45 = com.alipay.mobile.nebula.util.H5RsaUtil.verify(r0, r1, r2)     // Catch:{ Exception -> 0x05dc }
            goto L_0x04c4
        L_0x0576:
            java.lang.String r73 = TAR_PUBLIC_KEY     // Catch:{ Exception -> 0x05dc }
            boolean r73 = android.text.TextUtils.isEmpty(r73)     // Catch:{ Exception -> 0x05dc }
            if (r73 != 0) goto L_0x05ab
            java.lang.StringBuilder r73 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x05dc }
            r73.<init>()     // Catch:{ Exception -> 0x05dc }
            r0 = r73
            r1 = r40
            java.lang.StringBuilder r73 = r0.append(r1)     // Catch:{ Exception -> 0x05dc }
            java.lang.String r74 = "/"
            java.lang.StringBuilder r73 = r73.append(r74)     // Catch:{ Exception -> 0x05dc }
            r0 = r73
            r1 = r47
            java.lang.StringBuilder r73 = r0.append(r1)     // Catch:{ Exception -> 0x05dc }
            java.lang.String r73 = r73.toString()     // Catch:{ Exception -> 0x05dc }
            java.lang.String r74 = TAR_PUBLIC_KEY     // Catch:{ Exception -> 0x05dc }
            r0 = r73
            r1 = r74
            r2 = r52
            boolean r45 = com.alipay.mobile.nebula.util.H5RsaUtil.verify(r0, r1, r2)     // Catch:{ Exception -> 0x05dc }
            goto L_0x04c4
        L_0x05ab:
            java.lang.StringBuilder r73 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x05dc }
            r73.<init>()     // Catch:{ Exception -> 0x05dc }
            r0 = r73
            r1 = r40
            java.lang.StringBuilder r73 = r0.append(r1)     // Catch:{ Exception -> 0x05dc }
            java.lang.String r74 = "/"
            java.lang.StringBuilder r73 = r73.append(r74)     // Catch:{ Exception -> 0x05dc }
            r0 = r73
            r1 = r47
            java.lang.StringBuilder r73 = r0.append(r1)     // Catch:{ Exception -> 0x05dc }
            java.lang.String r73 = r73.toString()     // Catch:{ Exception -> 0x05dc }
            java.lang.String r74 = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC2y61svV7Q0gmvxprTt6YX76rps8R+q+C+Qtkkk2+njIABsf10sHnl/5aQBh2s+kdo6YGlJrnKdxVso2JRzy+QbRBUgTdJmKfm5uGPdcqYuO0ur4b/QCyHTMoKJjBT8iI3hYIGhn0hACDao4xIsgzJ39grRKUa6120WbInlOLWSQIDAQAB"
            r0 = r73
            r1 = r74
            r2 = r52
            boolean r45 = com.alipay.mobile.nebula.util.H5RsaUtil.verify(r0, r1, r2)     // Catch:{ Exception -> 0x05dc }
            goto L_0x04c4
        L_0x05d8:
            int r71 = r71 + 1
            goto L_0x0445
        L_0x05dc:
            r15 = move-exception
            java.lang.String r71 = "H5PackageParser"
            r0 = r71
            com.alipay.mobile.nebula.util.H5Log.e(r0, r15)
        L_0x05e4:
            long r72 = java.lang.System.currentTimeMillis()
            long r56 = r72 - r60
            java.lang.String r71 = "H5PackageParser"
            java.lang.StringBuilder r72 = new java.lang.StringBuilder
            java.lang.String r73 = "verify tar signature succeed elapse:"
            r72.<init>(r73)
            r0 = r72
            r1 = r56
            java.lang.StringBuilder r72 = r0.append(r1)
            java.lang.String r73 = " appId:"
            java.lang.StringBuilder r72 = r72.append(r73)
            r0 = r72
            java.lang.StringBuilder r72 = r0.append(r5)
            java.lang.String r72 = r72.toString()
            com.alipay.mobile.nebula.util.H5Log.d(r71, r72)
            boolean r71 = com.alipay.mobile.nebulacore.Nebula.DEBUG
            if (r71 == 0) goto L_0x062e
            java.lang.String r71 = "verifyResult"
            java.lang.String r72 = "success"
            r0 = r63
            r1 = r71
            r2 = r72
            r0.put(r1, r2)
            java.lang.String r71 = "verifyDuration"
            java.lang.Long r72 = java.lang.Long.valueOf(r56)
            r0 = r63
            r1 = r71
            r2 = r72
            r0.put(r1, r2)
        L_0x062e:
            if (r79 != 0) goto L_0x0641
            java.lang.String r71 = "H5PackageParser"
            java.lang.String r72 = "invalid resPkg"
            com.alipay.mobile.nebula.util.H5Log.e(r71, r72)
            r71 = 8
            java.lang.String r72 = "INVALID_PARAM"
            com.alipay.mobile.nebulacore.api.H5ParseResult r71 = a(r71, r72)
            goto L_0x0011
        L_0x0641:
            r23 = 0
            r22 = 0
            if (r27 == 0) goto L_0x068c
            java.lang.Class<com.alipay.mobile.nebula.provider.H5AppProvider> r71 = com.alipay.mobile.nebula.provider.H5AppProvider.class
            java.lang.String r71 = r71.getName()
            java.lang.Object r20 = com.alipay.mobile.nebula.util.H5Utils.getProvider(r71)
            com.alipay.mobile.nebula.provider.H5AppProvider r20 = (com.alipay.mobile.nebula.provider.H5AppProvider) r20
            if (r20 == 0) goto L_0x068c
            r0 = r20
            r1 = r70
            com.alipay.mobile.nebula.appcenter.model.AppInfo r6 = r0.getAppInfo(r5, r1)
            if (r6 == 0) goto L_0x068c
            java.lang.String r0 = r6.extend_info_jo
            r71 = r0
            com.alibaba.fastjson.JSONObject r71 = com.alipay.mobile.nebula.util.H5Utils.parseObject(r71)
            java.lang.String r72 = "api_permission"
            java.lang.String r71 = com.alipay.mobile.nebula.util.H5Utils.getString(r71, r72)
            com.alibaba.fastjson.JSONObject r4 = com.alipay.mobile.nebula.util.H5Utils.parseObject(r71)
            if (r4 == 0) goto L_0x068c
            boolean r71 = r4.isEmpty()
            if (r71 != 0) goto L_0x068c
            com.alipay.mobile.nebula.tinypermission.H5ApiManager r71 = com.alipay.mobile.nebulacore.Nebula.getH5TinyAppService()
            if (r71 == 0) goto L_0x0688
            com.alipay.mobile.nebula.tinypermission.H5ApiManager r71 = com.alipay.mobile.nebulacore.Nebula.getH5TinyAppService()
            r0 = r71
            r0.putJson(r5, r4)
        L_0x0688:
            r23 = 1
            r22 = 1
        L_0x068c:
            long r32 = java.lang.System.currentTimeMillis()
            if (r29 == 0) goto L_0x074c
            r24 = r42
        L_0x0694:
            r65 = 0
            r71 = 2048(0x800, float:2.87E-42)
            byte[] r10 = com.alipay.mobile.nebula.util.H5IOUtils.getBuf(r71)
            java.io.FileInputStream r19 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x0922 }
            r0 = r19
            r1 = r53
            r0.<init>(r1)     // Catch:{ Throwable -> 0x0922 }
            java.io.BufferedInputStream r9 = new java.io.BufferedInputStream     // Catch:{ Throwable -> 0x0922 }
            r0 = r19
            r9.<init>(r0)     // Catch:{ Throwable -> 0x0922 }
            com.alipay.mobile.nebula.util.tar.TarInputStream r68 = new com.alipay.mobile.nebula.util.tar.TarInputStream     // Catch:{ Throwable -> 0x0922 }
            r0 = r68
            r0.<init>(r9)     // Catch:{ Throwable -> 0x0922 }
        L_0x06b3:
            com.alipay.mobile.nebula.util.tar.TarEntry r59 = r68.getNextEntry()     // Catch:{ Throwable -> 0x06ee, all -> 0x07dc }
            if (r59 == 0) goto L_0x0885
            java.lang.String r18 = r59.getName()     // Catch:{ Throwable -> 0x06ee, all -> 0x07dc }
            boolean r71 = r59.isDirectory()     // Catch:{ Throwable -> 0x06ee, all -> 0x07dc }
            if (r71 != 0) goto L_0x06b3
            boolean r71 = android.text.TextUtils.isEmpty(r18)     // Catch:{ Throwable -> 0x06ee, all -> 0x07dc }
            if (r71 != 0) goto L_0x06b3
            java.lang.String r71 = "hpmfile.json"
            r0 = r18
            r1 = r71
            boolean r71 = android.text.TextUtils.equals(r0, r1)     // Catch:{ Throwable -> 0x06ee, all -> 0x07dc }
            if (r71 != 0) goto L_0x06b3
            com.alipay.mobile.nebula.io.PoolingByteArrayOutputStream r8 = new com.alipay.mobile.nebula.io.PoolingByteArrayOutputStream     // Catch:{ Throwable -> 0x06ee, all -> 0x07dc }
            r8.<init>()     // Catch:{ Throwable -> 0x06ee, all -> 0x07dc }
        L_0x06da:
            r0 = r68
            int r13 = r0.read(r10)     // Catch:{ Throwable -> 0x06ee, all -> 0x07dc }
            r71 = -1
            r0 = r71
            if (r13 == r0) goto L_0x0750
            r71 = 0
            r0 = r71
            r8.write(r10, r0, r13)     // Catch:{ Throwable -> 0x06ee, all -> 0x07dc }
            goto L_0x06da
        L_0x06ee:
            r15 = move-exception
            r65 = r68
        L_0x06f1:
            java.lang.String r71 = "H5PackageParser"
            java.lang.String r72 = "parse package exception"
            r0 = r71
            r1 = r72
            com.alipay.mobile.nebula.util.H5Log.e(r0, r1, r15)     // Catch:{ all -> 0x091f }
            r0 = r37
            r1 = r78
            a(r5, r0, r1)     // Catch:{ all -> 0x091f }
            r71 = 7
            java.lang.String r72 = r15.toString()     // Catch:{ all -> 0x091f }
            com.alipay.mobile.nebulacore.api.H5ParseResult r71 = a(r71, r72)     // Catch:{ all -> 0x091f }
            com.alipay.mobile.nebula.util.H5IOUtils.returnBuf(r10)
            com.alipay.mobile.nebula.util.H5IOUtils.closeQuietly(r65)
            boolean r72 = com.alipay.mobile.nebulacore.Nebula.DEBUG
            if (r72 == 0) goto L_0x0011
            long r72 = java.lang.System.currentTimeMillis()
            long r34 = r72 - r32
            boolean r72 = android.text.TextUtils.isEmpty(r70)
            if (r72 != 0) goto L_0x0011
            java.lang.String r72 = "mapDuration"
            java.lang.Long r73 = java.lang.Long.valueOf(r34)
            r0 = r63
            r1 = r72
            r2 = r73
            r0.put(r1, r2)
            java.lang.StringBuilder r72 = new java.lang.StringBuilder
            java.lang.String r73 = "package|"
            r72.<init>(r73)
            r0 = r72
            java.lang.StringBuilder r72 = r0.append(r5)
            java.lang.String r72 = r72.toString()
            r0 = r72
            r1 = r63
            com.alipay.mobile.nebula.util.TestDataUtils.storeJSParams(r0, r1)
            goto L_0x0011
        L_0x074c:
            r24 = r37
            goto L_0x0694
        L_0x0750:
            byte[] r14 = r8.toByteArray()     // Catch:{ Throwable -> 0x06ee, all -> 0x07dc }
            com.alipay.mobile.nebula.util.H5IOUtils.closeQuietly(r8)     // Catch:{ Throwable -> 0x06ee, all -> 0x07dc }
            if (r14 == 0) goto L_0x06b3
            java.lang.String r71 = "H5PackageParser"
            java.lang.StringBuilder r72 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x06ee, all -> 0x07dc }
            java.lang.String r73 = "entryName "
            r72.<init>(r73)     // Catch:{ Throwable -> 0x06ee, all -> 0x07dc }
            r0 = r72
            r1 = r18
            java.lang.StringBuilder r72 = r0.append(r1)     // Catch:{ Throwable -> 0x06ee, all -> 0x07dc }
            java.lang.String r72 = r72.toString()     // Catch:{ Throwable -> 0x06ee, all -> 0x07dc }
            com.alipay.mobile.nebula.util.H5Log.d(r71, r72)     // Catch:{ Throwable -> 0x06ee, all -> 0x07dc }
            java.lang.String r71 = "_animation"
            r0 = r18
            r1 = r71
            boolean r71 = r0.startsWith(r1)     // Catch:{ Throwable -> 0x06ee, all -> 0x07dc }
            if (r71 == 0) goto L_0x081d
            r0 = r79
            r1 = r18
            r0.put(r1, r14)     // Catch:{ Throwable -> 0x06ee, all -> 0x07dc }
        L_0x0784:
            java.lang.String r71 = "api_permission"
            r0 = r18
            r1 = r71
            boolean r71 = android.text.TextUtils.equals(r0, r1)     // Catch:{ Throwable -> 0x06ee, all -> 0x07dc }
            if (r71 == 0) goto L_0x07a3
            if (r23 != 0) goto L_0x07a3
            r22 = 1
            com.alipay.mobile.nebula.tinypermission.H5ApiManager r71 = com.alipay.mobile.nebulacore.Nebula.getH5TinyAppService()     // Catch:{ Throwable -> 0x06ee, all -> 0x07dc }
            if (r71 == 0) goto L_0x07a3
            com.alipay.mobile.nebula.tinypermission.H5ApiManager r71 = com.alipay.mobile.nebulacore.Nebula.getH5TinyAppService()     // Catch:{ Throwable -> 0x06ee, all -> 0x07dc }
            r0 = r71
            r0.put(r5, r14)     // Catch:{ Throwable -> 0x06ee, all -> 0x07dc }
        L_0x07a3:
            java.lang.String r71 = "appConfig.json"
            r0 = r71
            r1 = r18
            boolean r71 = r0.equalsIgnoreCase(r1)     // Catch:{ Throwable -> 0x06ee, all -> 0x07dc }
            if (r71 == 0) goto L_0x07bc
            com.alipay.mobile.nebula.startParam.H5StartParamManager r71 = com.alipay.mobile.nebula.startParam.H5StartParamManager.getInstance()     // Catch:{ Throwable -> 0x06ee, all -> 0x07dc }
            r0 = r71
            r0.put(r5, r14)     // Catch:{ Throwable -> 0x06ee, all -> 0x07dc }
            r71 = 1
            com.alipay.mobile.nebulacore.Nebula.isDSL = r71     // Catch:{ Throwable -> 0x06ee, all -> 0x07dc }
        L_0x07bc:
            java.lang.String r71 = "tabBar.json"
            r0 = r71
            r1 = r18
            boolean r71 = r0.equalsIgnoreCase(r1)     // Catch:{ Throwable -> 0x06ee, all -> 0x07dc }
            if (r71 == 0) goto L_0x07cb
            com.alipay.mobile.nebula.util.H5TabbarUtils.setTabData(r5, r14)     // Catch:{ Throwable -> 0x06ee, all -> 0x07dc }
        L_0x07cb:
            java.lang.String r71 = "header.json"
            r0 = r71
            r1 = r18
            boolean r71 = r0.equalsIgnoreCase(r1)     // Catch:{ Throwable -> 0x06ee, all -> 0x07dc }
            if (r71 == 0) goto L_0x06b3
            com.alipay.mobile.nebulacore.appcenter.center.H5GlobalPackage.addHeader(r5, r14)     // Catch:{ Throwable -> 0x06ee, all -> 0x07dc }
            goto L_0x06b3
        L_0x07dc:
            r71 = move-exception
            r65 = r68
        L_0x07df:
            com.alipay.mobile.nebula.util.H5IOUtils.returnBuf(r10)
            com.alipay.mobile.nebula.util.H5IOUtils.closeQuietly(r65)
            boolean r72 = com.alipay.mobile.nebulacore.Nebula.DEBUG
            if (r72 == 0) goto L_0x081c
            long r72 = java.lang.System.currentTimeMillis()
            long r34 = r72 - r32
            boolean r72 = android.text.TextUtils.isEmpty(r70)
            if (r72 != 0) goto L_0x081c
            java.lang.String r72 = "mapDuration"
            java.lang.Long r73 = java.lang.Long.valueOf(r34)
            r0 = r63
            r1 = r72
            r2 = r73
            r0.put(r1, r2)
            java.lang.StringBuilder r72 = new java.lang.StringBuilder
            java.lang.String r73 = "package|"
            r72.<init>(r73)
            r0 = r72
            java.lang.StringBuilder r72 = r0.append(r5)
            java.lang.String r72 = r72.toString()
            r0 = r72
            r1 = r63
            com.alipay.mobile.nebula.util.TestDataUtils.storeJSParams(r0, r1)
        L_0x081c:
            throw r71
        L_0x081d:
            if (r44 == 0) goto L_0x0863
            java.lang.String r71 = "yes"
            java.lang.String r72 = "h5_parse_http"
            java.lang.String r72 = com.alipay.mobile.nebulacore.env.H5Environment.getConfigWithProcessCache(r72)     // Catch:{ Throwable -> 0x06ee, all -> 0x07dc }
            boolean r71 = r71.equalsIgnoreCase(r72)     // Catch:{ Throwable -> 0x06ee, all -> 0x07dc }
            if (r71 == 0) goto L_0x0847
            java.lang.StringBuilder r71 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x06ee, all -> 0x07dc }
            java.lang.String r72 = "http://"
            r71.<init>(r72)     // Catch:{ Throwable -> 0x06ee, all -> 0x07dc }
            r0 = r71
            r1 = r18
            java.lang.StringBuilder r71 = r0.append(r1)     // Catch:{ Throwable -> 0x06ee, all -> 0x07dc }
            java.lang.String r71 = r71.toString()     // Catch:{ Throwable -> 0x06ee, all -> 0x07dc }
            r0 = r79
            r1 = r71
            r0.put(r1, r14)     // Catch:{ Throwable -> 0x06ee, all -> 0x07dc }
        L_0x0847:
            java.lang.StringBuilder r71 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x06ee, all -> 0x07dc }
            java.lang.String r72 = "https://"
            r71.<init>(r72)     // Catch:{ Throwable -> 0x06ee, all -> 0x07dc }
            r0 = r71
            r1 = r18
            java.lang.StringBuilder r71 = r0.append(r1)     // Catch:{ Throwable -> 0x06ee, all -> 0x07dc }
            java.lang.String r71 = r71.toString()     // Catch:{ Throwable -> 0x06ee, all -> 0x07dc }
            r0 = r79
            r1 = r71
            r0.put(r1, r14)     // Catch:{ Throwable -> 0x06ee, all -> 0x07dc }
            goto L_0x0784
        L_0x0863:
            java.lang.StringBuilder r71 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x06ee, all -> 0x07dc }
            r71.<init>()     // Catch:{ Throwable -> 0x06ee, all -> 0x07dc }
            r0 = r71
            r1 = r24
            java.lang.StringBuilder r71 = r0.append(r1)     // Catch:{ Throwable -> 0x06ee, all -> 0x07dc }
            r0 = r71
            r1 = r18
            java.lang.StringBuilder r71 = r0.append(r1)     // Catch:{ Throwable -> 0x06ee, all -> 0x07dc }
            java.lang.String r71 = r71.toString()     // Catch:{ Throwable -> 0x06ee, all -> 0x07dc }
            r0 = r79
            r1 = r71
            r0.put(r1, r14)     // Catch:{ Throwable -> 0x06ee, all -> 0x07dc }
            goto L_0x0784
        L_0x0885:
            if (r21 == 0) goto L_0x0890
            r0 = r21
            r1 = r78
            r2 = r79
            r0.mergeTemplateConfigIfNeed(r5, r1, r2)     // Catch:{ Throwable -> 0x06ee, all -> 0x07dc }
        L_0x0890:
            if (r22 != 0) goto L_0x08a1
            com.alipay.mobile.nebula.tinypermission.H5ApiManager r71 = com.alipay.mobile.nebulacore.Nebula.getH5TinyAppService()     // Catch:{ Throwable -> 0x06ee, all -> 0x07dc }
            if (r71 == 0) goto L_0x08a1
            com.alipay.mobile.nebula.tinypermission.H5ApiManager r71 = com.alipay.mobile.nebulacore.Nebula.getH5TinyAppService()     // Catch:{ Throwable -> 0x06ee, all -> 0x07dc }
            r0 = r71
            r0.clear(r5)     // Catch:{ Throwable -> 0x06ee, all -> 0x07dc }
        L_0x08a1:
            com.alipay.mobile.nebula.util.H5IOUtils.returnBuf(r10)
            com.alipay.mobile.nebula.util.H5IOUtils.closeQuietly(r68)
            boolean r71 = com.alipay.mobile.nebulacore.Nebula.DEBUG
            if (r71 == 0) goto L_0x08de
            long r72 = java.lang.System.currentTimeMillis()
            long r34 = r72 - r32
            boolean r71 = android.text.TextUtils.isEmpty(r70)
            if (r71 != 0) goto L_0x08de
            java.lang.String r71 = "mapDuration"
            java.lang.Long r72 = java.lang.Long.valueOf(r34)
            r0 = r63
            r1 = r71
            r2 = r72
            r0.put(r1, r2)
            java.lang.StringBuilder r71 = new java.lang.StringBuilder
            java.lang.String r72 = "package|"
            r71.<init>(r72)
            r0 = r71
            java.lang.StringBuilder r71 = r0.append(r5)
            java.lang.String r71 = r71.toString()
            r0 = r71
            r1 = r63
            com.alipay.mobile.nebula.util.TestDataUtils.storeJSParams(r0, r1)
        L_0x08de:
            long r72 = java.lang.System.currentTimeMillis()
            long r16 = r72 - r66
            java.lang.String r71 = "H5PackageParser"
            java.lang.StringBuilder r72 = new java.lang.StringBuilder
            java.lang.String r73 = "parse package elapse "
            r72.<init>(r73)
            r0 = r72
            r1 = r16
            java.lang.StringBuilder r72 = r0.append(r1)
            java.lang.String r73 = " appId:"
            java.lang.StringBuilder r72 = r72.append(r73)
            r0 = r72
            java.lang.StringBuilder r72 = r0.append(r5)
            java.lang.String r72 = r72.toString()
            com.alipay.mobile.nebula.util.H5Log.d(r71, r72)
            java.lang.String r71 = "prepare_app"
            java.lang.String r72 = "parser_app"
            r0 = r71
            r1 = r72
            r2 = r66
            com.alipay.mobile.nebulacore.util.H5TimeUtil.timeLog(r0, r1, r2)
            r71 = 0
            java.lang.String r72 = "SUCCESS"
            com.alipay.mobile.nebulacore.api.H5ParseResult r71 = a(r71, r72)
            goto L_0x0011
        L_0x091f:
            r71 = move-exception
            goto L_0x07df
        L_0x0922:
            r15 = move-exception
            goto L_0x06f1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.nebulacore.appcenter.parse.H5PackageParser.parsePackage(android.os.Bundle, java.util.Map):com.alipay.mobile.nebulacore.api.H5ParseResult");
    }

    private static void a(final String appId, final String offlineHost, final Bundle bundle) {
        if (BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5Environment.getConfigWithProcessCache("h5_schedule_delete_fail_app"))) {
            notifyFail(appId, offlineHost, bundle);
            return;
        }
        H5Log.d(TAG, "notifyVerifyFailed appId " + appId);
        ScheduledThreadPoolExecutor executor = H5Utils.getScheduledExecutor();
        if (executor != null) {
            executor.schedule(new Runnable() {
                public final void run() {
                    H5ParseResult h5ParseResult = H5PackageParser.parsePackage(bundle, null);
                    if (h5ParseResult == null || h5ParseResult.code != 8) {
                        H5PackageParser.notifyFail(appId, offlineHost, bundle);
                    } else {
                        H5Log.d(H5PackageParser.TAG, "parsePackage again is ok not to delete");
                    }
                }
            }, 2, TimeUnit.SECONDS);
        }
    }

    public static void notifyFail(String appId, String offlineHost, Bundle bundle) {
        if (!TextUtils.isEmpty(appId) && !TextUtils.isEmpty(offlineHost)) {
            H5AppProvider appProvider = (H5AppProvider) Nebula.getProviderManager().getProvider(H5AppProvider.class.getName());
            if (appProvider == null || !appProvider.isNebulaApp(appId)) {
                LocalBroadcastManager manager = LocalBroadcastManager.getInstance(H5Utils.getContext());
                Intent intent = new Intent();
                H5Log.d(TAG, appId + " is not nebulaapp send verify failed broadcast to app center.");
                intent.setAction(TAR_VERIFY_FAIL);
                intent.putExtra("appId", appId);
                intent.putExtra("localPath", offlineHost);
                manager.sendBroadcast(intent);
            } else {
                H5Log.d(TAG, appId + " is nebulaapp,verify failed ,delete localPath:" + offlineHost);
                String deletePath = Uri.parse(offlineHost).getPath();
                H5Log.d(TAG, "deletePath:" + deletePath);
                File file = new File(deletePath);
                String name = "";
                if (H5FileUtil.exists(file)) {
                    try {
                        File[] listFiles = file.listFiles();
                        if (listFiles != null) {
                            for (File f : listFiles) {
                                H5Log.d(TAG, "file  " + f.getName());
                                name = name + "_" + f.getName();
                            }
                        } else {
                            H5Log.d(TAG, "listFiles==null");
                        }
                    } catch (Exception e) {
                        H5Log.e((String) TAG, (Throwable) e);
                    }
                }
                if (deletePath != null && deletePath.contains("nebulaInstallApps")) {
                    H5AppUtil.deleteNebulaInstallFileAndDB(deletePath, appId);
                }
            }
            if (H5Utils.isInWifi() && appProvider != null) {
                appProvider.downloadApp(appId, H5Utils.getString(bundle, (String) "appVersion"));
            }
        }
    }

    public static boolean isNeedVerify() {
        boolean isNeedVerifyFromConfig = b();
        H5Log.d(TAG, "parsePackage isNeedVerifyFromConfig " + isNeedVerifyFromConfig);
        return isNeedVerifyFromConfig || Nebula.isRooted();
    }

    private static boolean b() {
        if (!TextUtils.equals("NO", H5Environment.getConfigWithProcessCache("h5_shouldverifyapp"))) {
            return true;
        }
        return false;
    }

    public static String getPublicKey(boolean newSignKey) {
        if (newSignKey) {
            return NEW_H5APP_SIGN_PUBLIC_KEY;
        }
        return DEFAULT_TAR_PUBLIC_KEY;
    }
}
