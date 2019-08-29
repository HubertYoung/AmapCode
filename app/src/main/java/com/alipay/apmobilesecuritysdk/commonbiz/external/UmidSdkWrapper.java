package com.alipay.apmobilesecuritysdk.commonbiz.external;

import android.content.Context;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.apmobilesecuritysdk.commonbiz.OnlineHostConfig;
import com.alipay.security.mobile.module.commonutils.CommonUtils;
import com.alipay.security.mobile.module.localstorage.SecurityStorageUtils;
import com.taobao.dp.DeviceSecuritySDK;
import com.taobao.dp.client.IInitResultListener;

public class UmidSdkWrapper {
    private static final String TAG = "APSecuritySdk";
    private static final String UMIDTOKEN_FILE_NAME = "xxxwww_v2";
    private static final String UMIDTOKEN_KEY_NAME = "umidtk";
    private static volatile String cachedUmidToken = "";
    /* access modifiers changed from: private */
    public static volatile boolean initUmidFinished = false;
    /* access modifiers changed from: private */
    public static final TraceLogger logger = LoggerFactory.f();

    public static String getLocalUmidToken(Context context) {
        if (CommonUtils.isBlank(cachedUmidToken)) {
            String readFromSharedPreference = SecurityStorageUtils.readFromSharedPreference(context, UMIDTOKEN_FILE_NAME, UMIDTOKEN_KEY_NAME);
            cachedUmidToken = readFromSharedPreference;
            if (CommonUtils.isBlank(readFromSharedPreference)) {
                String str = "";
                try {
                    str = DeviceSecuritySDK.getInstance(context).getSecurityToken();
                } catch (Throwable unused) {
                }
                cachedUmidToken = compatUmidBug(context, str);
            }
        }
        return cachedUmidToken;
    }

    public static String intializeSyncAndGetUmidToken(Context context) {
        String str = "";
        initUmidFinished = false;
        try {
            OnlineHostConfig.a();
            OnlineHostConfig.b();
            logger.b((String) "APSecuritySdk", (String) "startUmidTaskSync, wallet env mode:0");
            DeviceSecuritySDK.getInstance(context).initAsync("", convert2UMIDEnv(0), null, new IInitResultListener() {
                public final void onInitFinished(String str, int i) {
                    UmidSdkWrapper.initUmidFinished = true;
                    TraceLogger access$100 = UmidSdkWrapper.logger;
                    StringBuilder sb = new StringBuilder("umidToken = ");
                    sb.append(str);
                    sb.append(", errorCode = ");
                    sb.append(i);
                    access$100.b((String) "APSecuritySdk", sb.toString());
                }
            });
            int i = 3000;
            while (!initUmidFinished && i > 0) {
                Thread.sleep(10);
                i -= 10;
            }
            String securityToken = DeviceSecuritySDK.getInstance(context).getSecurityToken();
            if (CommonUtils.isNotBlank(securityToken)) {
                str = securityToken;
            }
        } catch (Throwable unused) {
            logger.b((String) "APSecuritySdk", (String) "[-] Umid request error.");
        }
        String compatUmidBug = compatUmidBug(context, str);
        updateLocalUmidToken(context, compatUmidBug);
        return compatUmidBug;
    }

    private static synchronized void updateLocalUmidToken(Context context, String str) {
        synchronized (UmidSdkWrapper.class) {
            if (CommonUtils.isNotBlank(str)) {
                SecurityStorageUtils.writeToSharedPreference(context, UMIDTOKEN_FILE_NAME, UMIDTOKEN_KEY_NAME, str);
                cachedUmidToken = str;
            }
        }
    }

    private static String compatUmidBug(Context context, String str) {
        if (!CommonUtils.isBlank(str) && !CommonUtils.equals(str, "000000000000000000000000")) {
            return str;
        }
        String utdid = UtdidWrapper.getUtdid(context);
        if (utdid != null && utdid.contains("?")) {
            utdid = "";
        }
        if (CommonUtils.isBlank(utdid)) {
            utdid = "";
        }
        return utdid;
    }

    private static int convert2UMIDEnv(int i) {
        switch (i) {
            case 1:
            case 3:
            case 4:
                logger.b((String) "APSecuritySdk", (String) "convert2UMIDEnv(), umid env: daily!!!");
                return 2;
            case 2:
                logger.b((String) "APSecuritySdk", (String) "convert2UMIDEnv(), umid env: pre!!!");
                return 1;
            default:
                logger.b((String) "APSecuritySdk", (String) "convert2UMIDEnv(), umid env: online!!!");
                return 0;
        }
    }
}
