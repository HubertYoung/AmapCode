package com.alipay.mobile.security.zim.api;

import android.app.Application;
import android.content.Context;
import com.alibaba.fastjson.JSON;
import com.alipay.bis.common.service.facade.gw.zim.ZimInitGwResponse;
import com.alipay.mobile.security.bio.api.BioDetectorBuilder;
import com.alipay.mobile.security.bio.runtime.Runtime;
import com.alipay.mobile.security.bio.service.BioServiceManager;
import com.alipay.mobile.security.bio.service.local.apsecurity.ApSecurityService;
import com.alipay.mobile.security.bio.service.local.monitorlog.MonitorLogService;
import com.alipay.mobile.security.bio.utils.BioLog;
import com.alipay.mobile.security.zim.a.a;
import com.alipay.mobile.security.zim.a.b;
import com.alipay.mobile.security.zim.a.c;
import java.util.Map;

public abstract class ZIMFacade {
    public static final int COMMAND_CANCLE = 4099;
    public static final int COMMAND_SERVER_CONTINUE = 4098;
    public static final int COMMAND_SERVER_FAIL = 8193;
    public static final int COMMAND_SERVER_RETRY = 8194;
    public static final int COMMAND_SERVER_SUCCESS = 4097;
    public static final int COMMAND_VALIDATE_FAIL = 4100;
    public static final String KEY_AUTO_CLOSE = "zimAutoClose";
    public static final String KEY_AVATAR = "avatar";
    public static final String KEY_BIO_RESPONSE = "bioResponse";
    public static final String KEY_ENV_NAME = "env_name";
    public static final String KEY_FACE_PAY_INFO = "facepayInfoMap";
    public static final String KEY_FACE_TOKEN = "ftoken";
    public static final String KEY_INIT_RESP = "zimInitResp";
    private static String a = "1.0.0";

    public abstract void command(int i);

    public abstract ZimInitGwResponse parse(String str);

    public abstract void retry();

    public abstract void verify(String str, ZimInitGwResponse zimInitGwResponse, Map<String, String> map, ZIMCallback zIMCallback);

    public abstract void verify(String str, Map<String, String> map, ZIMCallback zIMCallback);

    public static void install(Context context) {
        MonitorLogService monitorLogService = (MonitorLogService) BioServiceManager.getLocalService(context, MonitorLogService.class);
        if (monitorLogService != null) {
            monitorLogService.install(context);
        }
        ApSecurityService apSecurityService = (ApSecurityService) BioServiceManager.getLocalService(context, ApSecurityService.class);
        if (apSecurityService != null) {
            apSecurityService.init(context);
        }
        if (!Runtime.isRunningOnQuinox(context)) {
            Application application = (Application) context.getApplicationContext();
            b.a(application);
            c.a(application);
        }
    }

    public static ZIMMetaInfo getZimMetaInfo(Context context) {
        a aVar = new a();
        ZIMMetaInfo zIMMetaInfo = new ZIMMetaInfo();
        zIMMetaInfo.setApdidToken(aVar.a(context));
        zIMMetaInfo.setAppName(aVar.b(context));
        zIMMetaInfo.setAppVersion(aVar.c(context));
        zIMMetaInfo.setDeviceModel(aVar.b());
        zIMMetaInfo.setDeviceType(aVar.a());
        zIMMetaInfo.setOsVersion(aVar.c());
        zIMMetaInfo.setBioMetaInfo(BioDetectorBuilder.getMetaInfos(context));
        zIMMetaInfo.setZimVer(a);
        return zIMMetaInfo;
    }

    public static String getMetaInfos(Context context) {
        return getMetaInfos(context, true);
    }

    public static String getMetaInfos(Context context, boolean z) {
        if (z) {
            a.a(context);
        }
        String str = "";
        try {
            return JSON.toJSONString(getZimMetaInfo(context));
        } catch (Throwable th) {
            BioLog.e(th);
            return str;
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x008a, code lost:
        if (android.text.TextUtils.isEmpty(r0) == false) goto L_0x008c;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String b(android.content.Context r7) {
        /*
            java.lang.String r1 = ""
            boolean r0 = com.alipay.mobile.security.bio.runtime.Runtime.isRunningOnQuinox(r7)
            if (r0 == 0) goto L_0x00db
            java.lang.String r0 = "com.alipay.mobile.framework.LauncherApplicationAgent"
            java.lang.Class r0 = java.lang.Class.forName(r0)     // Catch:{ Throwable -> 0x00c2 }
            java.lang.String r2 = "getInstance"
            r3 = 0
            java.lang.Class[] r3 = new java.lang.Class[r3]     // Catch:{ Throwable -> 0x00c2 }
            java.lang.reflect.Method r2 = r0.getMethod(r2, r3)     // Catch:{ Throwable -> 0x00c2 }
            r3 = 1
            r2.setAccessible(r3)     // Catch:{ Throwable -> 0x00c2 }
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x00c2 }
            java.lang.Object r2 = r2.invoke(r2, r3)     // Catch:{ Throwable -> 0x00c2 }
            java.lang.String r3 = "getMicroApplicationContext"
            r4 = 0
            java.lang.Class[] r4 = new java.lang.Class[r4]     // Catch:{ Throwable -> 0x00c2 }
            java.lang.reflect.Method r0 = r0.getMethod(r3, r4)     // Catch:{ Throwable -> 0x00c2 }
            r3 = 1
            r0.setAccessible(r3)     // Catch:{ Throwable -> 0x00c2 }
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x00c2 }
            java.lang.Object r0 = r0.invoke(r2, r3)     // Catch:{ Throwable -> 0x00c2 }
            java.lang.Class r2 = r0.getClass()     // Catch:{ Throwable -> 0x00c2 }
            java.lang.String r3 = "findServiceByInterface"
            r4 = 1
            java.lang.Class[] r4 = new java.lang.Class[r4]     // Catch:{ Throwable -> 0x00c2 }
            r5 = 0
            java.lang.Class<java.lang.String> r6 = java.lang.String.class
            r4[r5] = r6     // Catch:{ Throwable -> 0x00c2 }
            java.lang.reflect.Method r2 = r2.getMethod(r3, r4)     // Catch:{ Throwable -> 0x00c2 }
            r3 = 1
            r2.setAccessible(r3)     // Catch:{ Throwable -> 0x00c2 }
            r3 = 1
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x00c2 }
            r4 = 0
            java.lang.String r5 = "com.alipay.apmobilesecuritysdk.DeviceFingerprintService"
            r3[r4] = r5     // Catch:{ Throwable -> 0x00c2 }
            java.lang.Object r0 = r2.invoke(r0, r3)     // Catch:{ Throwable -> 0x00c2 }
            if (r0 == 0) goto L_0x013e
            java.lang.Class r2 = r0.getClass()     // Catch:{ Throwable -> 0x00c2 }
            java.lang.String r3 = "getApdidToken"
            r4 = 0
            java.lang.Class[] r4 = new java.lang.Class[r4]     // Catch:{ Throwable -> 0x00c2 }
            java.lang.reflect.Method r2 = r2.getMethod(r3, r4)     // Catch:{ Throwable -> 0x00c2 }
            r3 = 1
            r2.setAccessible(r3)     // Catch:{ Throwable -> 0x00c2 }
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x00c2 }
            java.lang.Object r0 = r2.invoke(r0, r3)     // Catch:{ Throwable -> 0x00c2 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Throwable -> 0x00c2 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00c2 }
            java.lang.String r3 = "DeviceFingerprintService.getApdidToken(): "
            r2.<init>(r3)     // Catch:{ Throwable -> 0x00c2 }
            java.lang.StringBuilder r2 = r2.append(r0)     // Catch:{ Throwable -> 0x00c2 }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x00c2 }
            com.alipay.mobile.security.bio.utils.BioLog.d(r2)     // Catch:{ Throwable -> 0x00c2 }
            boolean r2 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Throwable -> 0x00c2 }
            if (r2 != 0) goto L_0x013e
        L_0x008c:
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 == 0) goto L_0x00af
            java.lang.String r1 = com.alipay.mobile.security.bio.service.local.apsecurity.ApSecurityService.getStaticApDidToken()
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "ApSecurityService.getStaticApDidToken(): "
            r2.<init>(r3)
            java.lang.StringBuilder r2 = r2.append(r1)
            java.lang.String r2 = r2.toString()
            com.alipay.mobile.security.bio.utils.BioLog.d(r2)
            boolean r2 = android.text.TextUtils.isEmpty(r1)
            if (r2 != 0) goto L_0x00af
            r0 = r1
        L_0x00af:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "apDidToken="
            r1.<init>(r2)
            java.lang.StringBuilder r1 = r1.append(r0)
            java.lang.String r1 = r1.toString()
            com.alipay.mobile.security.bio.utils.BioLog.v(r1)
            return r0
        L_0x00c2:
            r0 = move-exception
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "Failed to get apdidToken by calling DeviceFingerprintService.getApdidToken() : "
            r2.<init>(r3)
            java.lang.String r0 = r0.getMessage()
            java.lang.StringBuilder r0 = r2.append(r0)
            java.lang.String r0 = r0.toString()
            com.alipay.mobile.security.bio.utils.BioLog.w(r0)
            r0 = r1
            goto L_0x008c
        L_0x00db:
            java.lang.String r0 = "com.alipay.deviceid.DeviceTokenClient"
            java.lang.Class r0 = java.lang.Class.forName(r0)     // Catch:{ Throwable -> 0x0127 }
            java.lang.String r2 = "getInstance"
            r3 = 1
            java.lang.Class[] r3 = new java.lang.Class[r3]     // Catch:{ Throwable -> 0x0127 }
            r4 = 0
            java.lang.Class<android.content.Context> r5 = android.content.Context.class
            r3[r4] = r5     // Catch:{ Throwable -> 0x0127 }
            java.lang.reflect.Method r2 = r0.getMethod(r2, r3)     // Catch:{ Throwable -> 0x0127 }
            r3 = 1
            r2.setAccessible(r3)     // Catch:{ Throwable -> 0x0127 }
            r3 = 1
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x0127 }
            r4 = 0
            r3[r4] = r7     // Catch:{ Throwable -> 0x0127 }
            java.lang.Object r2 = r2.invoke(r2, r3)     // Catch:{ Throwable -> 0x0127 }
            java.lang.String r3 = "getTokenResult"
            r4 = 0
            java.lang.Class[] r4 = new java.lang.Class[r4]     // Catch:{ Throwable -> 0x0127 }
            java.lang.reflect.Method r0 = r0.getMethod(r3, r4)     // Catch:{ Throwable -> 0x0127 }
            r3 = 1
            r0.setAccessible(r3)     // Catch:{ Throwable -> 0x0127 }
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x0127 }
            java.lang.Object r0 = r0.invoke(r2, r3)     // Catch:{ Throwable -> 0x0127 }
            java.lang.Class r2 = r0.getClass()     // Catch:{ Throwable -> 0x0127 }
            java.lang.String r3 = "apdidToken"
            java.lang.reflect.Field r2 = r2.getDeclaredField(r3)     // Catch:{ Throwable -> 0x0127 }
            r3 = 1
            r2.setAccessible(r3)     // Catch:{ Throwable -> 0x0127 }
            java.lang.Object r0 = r2.get(r0)     // Catch:{ Throwable -> 0x0127 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Throwable -> 0x0127 }
            goto L_0x008c
        L_0x0127:
            r0 = move-exception
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "Failed to get apdidToken by calling DeviceTokenClient.getTokenResult().apdidToken : "
            r2.<init>(r3)
            java.lang.String r0 = r0.getMessage()
            java.lang.StringBuilder r0 = r2.append(r0)
            java.lang.String r0 = r0.toString()
            com.alipay.mobile.security.bio.utils.BioLog.w(r0)
        L_0x013e:
            r0 = r1
            goto L_0x008c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.security.zim.api.ZIMFacade.b(android.content.Context):java.lang.String");
    }
}
