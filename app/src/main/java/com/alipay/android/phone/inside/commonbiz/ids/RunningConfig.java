package com.alipay.android.phone.inside.commonbiz.ids;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.cashier.PhoneCashierPlugin;
import com.alipay.android.phone.inside.cashier.service.InsideServiceGetTid;
import com.alipay.android.phone.inside.common.info.AppInfo;
import com.alipay.android.phone.inside.common.info.DeviceInfo;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.android.phone.inside.framework.plugin.PluginManager;
import com.alipay.android.phone.inside.framework.service.IInsideService;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.ex.ExceptionLogger;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.apmobilesecuritysdk.face.APSecuritySdk;
import com.alipay.apmobilesecuritysdk.face.APSecuritySdk.TokenResult;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.alipay.mobile.tinyappcustom.h5plugin.ocr.tools.BehavorReporter;
import com.taobao.accs.common.Constants;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class RunningConfig {
    private static String a;
    private static boolean b;

    public static String a() {
        return "";
    }

    public static void a(String str) {
        a = str;
    }

    public static String b() {
        return a;
    }

    public static String c() {
        return AppInfo.a().e();
    }

    public static String a(boolean z) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("IsLoadLocal", z);
        try {
            if (PluginManager.b(PhoneCashierPlugin.KEY_SERVICE_GET_TID) != null) {
                return ((Bundle) ServiceExecutor.b(PhoneCashierPlugin.KEY_SERVICE_GET_TID, bundle)).getString(InsideServiceGetTid.CASHIER_TID, null);
            }
            return "";
        } catch (Throwable th) {
            LoggerFactory.f().c((String) "inside", th);
            return "";
        }
    }

    public static Map<String, String> d() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("IsLoadLocal", false);
        HashMap hashMap = new HashMap();
        try {
            IInsideService b2 = PluginManager.b(PhoneCashierPlugin.KEY_SERVICE_GET_TID);
            if (b2 != null) {
                Bundle bundle2 = (Bundle) b2.startForResult(bundle);
                hashMap.put("tid", bundle2.getString(InsideServiceGetTid.CASHIER_TID));
                hashMap.put(Constants.KEY_IMEI, bundle2.getString("IMEI"));
                hashMap.put(Constants.KEY_IMSI, bundle2.getString("IMSI"));
                hashMap.put("vimei", bundle2.getString(InsideServiceGetTid.CASHIER_TID_VIRTUALTMEI));
                hashMap.put("vimsi", bundle2.getString(InsideServiceGetTid.CASHIER_TID_VIRTUALIMSI));
                hashMap.put("clientKey", bundle2.getString(InsideServiceGetTid.CASHIER_TID_SEED));
                hashMap.put("utdid", DeviceInfo.a().e());
            }
        } catch (Throwable th) {
            LoggerFactory.f().c((String) "inside", th);
        }
        return hashMap;
    }

    public static String e() {
        try {
            IInsideService b2 = PluginManager.b("GET_USER_ID_SERVICE");
            if (b2 != null) {
                return (String) b2.startForResult(null);
            }
            return null;
        } catch (Exception e) {
            LoggerFactory.f().c((String) "inside", (Throwable) e);
            return null;
        }
    }

    public static String f() {
        return DeviceInfo.a().e();
    }

    public static String g() {
        try {
            TokenResult tokenResult = APSecuritySdk.getInstance(LauncherApplication.a()).getTokenResult();
            if (tokenResult != null) {
                return tokenResult.apdidToken;
            }
            return "";
        } catch (Throwable th) {
            LoggerFactory.e().a((String) "commonbiz", (String) "GetApdidTokenEx", th);
            return "";
        }
    }

    public static String h() throws Exception {
        Bundle bundle = (Bundle) ServiceExecutor.b("LOGIN_USERINFO_SERVICE", null);
        boolean z = bundle.getBoolean("isLogin");
        String string = bundle.getString("loginId");
        return (!z || TextUtils.isEmpty(string)) ? "" : string;
    }

    public static String i() {
        if (StaticConfig.k()) {
            return H5Param.SMART_TOOLBAR;
        }
        if (StaticConfig.j()) {
            return BehavorReporter.PROVIDE_BY_ALIPAY;
        }
        if (StaticConfig.l()) {
            return "inside";
        }
        ExceptionLogger e = LoggerFactory.e();
        StringBuilder sb = new StringBuilder("InsideChannel:");
        sb.append(StaticConfig.g());
        e.a((String) "commonbiz", (String) "StaticConfigModeEx", sb.toString());
        return "";
    }

    public static boolean j() {
        return b;
    }

    private static String b(String str) {
        String str2 = "";
        try {
            if (PluginManager.b("IOTPAY_SERVICE_GET_INFO") != null) {
                Bundle bundle = new Bundle();
                bundle.putBoolean(str, true);
                String string = ((Bundle) ServiceExecutor.b("IOTPAY_SERVICE_GET_INFO", bundle)).getString(str, "");
                try {
                    TraceLogger f = LoggerFactory.f();
                    StringBuilder sb = new StringBuilder("iotpay param ");
                    sb.append(str);
                    sb.append("=");
                    sb.append(string);
                    f.b((String) "inside", sb.toString());
                    return string;
                } catch (Throwable th) {
                    th = th;
                    str2 = string;
                    LoggerFactory.f().c((String) "inside", th);
                    return str2;
                }
            } else {
                LoggerFactory.f().b((String) "inside", (String) "iotpay no service:IOTPAY_SERVICE_GET_INFO");
                return str2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static String k() {
        return b((String) "bizTid");
    }

    public static String l() {
        return b((String) "pidToken");
    }

    public static String b(boolean z) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("insideModel", StaticConfig.b());
        jSONObject.put("insidePushKey", OutsideConfig.g());
        jSONObject.put("insideProductId", AppInfo.a().e());
        jSONObject.put("currentOperateMobile", OutsideConfig.h());
        jSONObject.put("isTrojan", OutsideConfig.j());
        jSONObject.put("isPrisonBreak", OutsideConfig.i());
        jSONObject.put("appKey", OutsideConfig.n());
        jSONObject.put("clientTime", String.valueOf(System.currentTimeMillis() / 1000));
        jSONObject.put("version", StaticConfig.c());
        if (!TextUtils.isEmpty("")) {
            jSONObject.put(H5AppUtil.scene, "");
        }
        if (z) {
            jSONObject.put("insideAttachAction", "initOtp");
        }
        return jSONObject.toString();
    }
}
