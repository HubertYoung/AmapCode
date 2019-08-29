package com.alipay.android.phone.inside.bizadapter.securitysdk;

import android.app.Application;
import com.alipay.android.phone.inside.commonbiz.ids.RunningConfig;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.apmobilesecuritysdk.face.APSecuritySdk;
import com.alipay.mobile.common.logging.api.DeviceProperty;
import java.util.HashMap;
import java.util.Map;

public class SecuritySdkInit {
    public static void a() {
        LoggerFactory.f().b((String) "inside", (String) "SecuritySdkInit::initialize");
        new Thread(new Runnable() {
            public final void run() {
                try {
                    Application a = LauncherApplication.a();
                    TraceLogger f = LoggerFactory.f();
                    StringBuilder sb = new StringBuilder("SecuritySdkInit::initialize start:");
                    sb.append(System.currentTimeMillis());
                    f.b((String) "inside", sb.toString());
                    Map b = SecuritySdkInit.b();
                    TraceLogger f2 = LoggerFactory.f();
                    StringBuilder sb2 = new StringBuilder("SecuritySdkInit::initialize env end:");
                    sb2.append(System.currentTimeMillis());
                    f2.b((String) "inside", sb2.toString());
                    APSecuritySdk.getInstance(a).initToken(0, b, null);
                    TraceLogger f3 = LoggerFactory.f();
                    StringBuilder sb3 = new StringBuilder("SecuritySdkInit::initialize initToken end:");
                    sb3.append(System.currentTimeMillis());
                    f3.b((String) "inside", sb3.toString());
                } catch (Throwable th) {
                    LoggerFactory.f().c((String) "inside", th);
                }
            }
        }).start();
    }

    static /* synthetic */ Map b() {
        HashMap hashMap = new HashMap();
        hashMap.put("UTDID", RunningConfig.f());
        hashMap.put("TID", RunningConfig.a(true));
        hashMap.put("USERID", RunningConfig.e());
        hashMap.put("APPNAME", "");
        hashMap.put("APPKEYCLIENT", "");
        hashMap.put("APPCHANNEL", DeviceProperty.ALIAS_HUAWEI);
        hashMap.put("RPCVERSION", "");
        hashMap.put("UMID", "");
        TraceLogger f = LoggerFactory.f();
        StringBuilder sb = new StringBuilder("SecuritySdkInit::getEnvArgs args:");
        sb.append(hashMap.toString());
        f.b((String) "inside", sb.toString());
        return hashMap;
    }
}
