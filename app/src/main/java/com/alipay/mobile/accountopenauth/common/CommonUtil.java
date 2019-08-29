package com.alipay.mobile.accountopenauth.common;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import com.alipay.android.phone.inside.common.setting.InsideSetting;
import com.alipay.android.phone.inside.commonbiz.util.ApkVerifyTool;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.apmobilesecuritysdk.face.APSecuritySdk;
import com.alipay.apmobilesecuritysdk.face.APSecuritySdk.TokenResult;
import com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool;
import java.util.Map;
import java.util.UUID;
import org.json.JSONObject;

public class CommonUtil {
    private static long a;
    private static APSecuritySdk b;

    public static boolean a() {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        if (elapsedRealtime - a < 1000) {
            return true;
        }
        a = elapsedRealtime;
        return false;
    }

    public static boolean a(Context context) {
        try {
            return ApkVerifyTool.a(context);
        } catch (Throwable th) {
            OAuthTraceLogger.a(com.alipay.mobile.nebulauc.util.CommonUtil.TAG, "isAlipayAppInstalled ex", th);
            return false;
        }
    }

    public static boolean a(Context context, int i) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo("com.eg.android.AlipayGphone", 64);
            if (packageInfo == null || packageInfo.versionCode >= i) {
                return true;
            }
            return false;
        } catch (Throwable th) {
            LoggerFactory.f().a(com.alipay.mobile.nebulauc.util.CommonUtil.TAG, "isAlipayVersionMatch error", th);
            return true;
        }
    }

    public static String a(Map<String, String> map) {
        if (map == null) {
            return "";
        }
        JSONObject jSONObject = new JSONObject();
        for (String next : map.keySet()) {
            try {
                jSONObject.put(next, map.get(next));
            } catch (Throwable th) {
                LoggerFactory.f().c((String) com.alipay.mobile.nebulauc.util.CommonUtil.TAG, th);
            }
        }
        return jSONObject.toString();
    }

    public static String b() {
        return UUID.randomUUID().toString();
    }

    public static JSONObject a(Bundle bundle) throws Exception {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("ssoToken", bundle.getString("token"));
        jSONObject.put("loginId", bundle.getString("loginId"));
        return jSONObject;
    }

    public static String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        boolean z = false;
        if (str.contains(AUScreenAdaptTool.PREFIX_ID)) {
            int indexOf = str.indexOf(64);
            String substring = str.substring(0, indexOf);
            String substring2 = str.substring(indexOf, str.length());
            if (substring.length() >= 3) {
                StringBuilder sb = new StringBuilder();
                sb.append(substring.substring(0, 3));
                sb.append("***");
                sb.append(substring2);
                return sb.toString();
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(substring);
            sb2.append("***");
            sb2.append(substring2);
            return sb2.toString();
        }
        if (str.matches("^(86){0,1}1\\d{10}$")) {
            String substring3 = str.substring(0, 3);
            String substring4 = str.substring(str.length() - 2, str.length());
            StringBuilder sb3 = new StringBuilder();
            sb3.append(substring3);
            sb3.append("******");
            sb3.append(substring4);
            str = sb3.toString();
        } else if (str.matches("^(00){0,1}(852-){1}0{0,1}(?:\\d{8}|\\d{9}|\\d{13})$")) {
            str = b(str);
        } else if (str.matches("^(00){0,1}(853-){1}6\\d{7}$")) {
            str = b(str);
        } else if (str.matches("^(00){0,1}(886-){1}0{0,1}[6,7,9](?:\\d{7}|\\d{8}|\\d{10})$")) {
            StringBuilder sb4 = new StringBuilder(str);
            int indexOf2 = str.indexOf(45);
            if (indexOf2 > 0) {
                sb4.replace(indexOf2 + 3, str.length() - 3, "****");
            } else {
                sb4.replace(2, str.length() - 3, "****");
            }
            str = sb4.toString();
        } else {
            int indexOf3 = str.indexOf(45);
            if (indexOf3 > 0 && indexOf3 < str.length() - 1) {
                String[] split = str.split("-");
                if (split[0].matches("\\d{1,}") && split[1].matches("\\d{1,}")) {
                    z = true;
                }
            }
            if (z) {
                str = c(str);
            }
        }
        return str;
    }

    private static String b(String str) {
        StringBuilder sb = new StringBuilder(str);
        int indexOf = str.indexOf(45);
        if (indexOf > 0) {
            sb.replace(indexOf + 3, str.length() - 2, "****");
        } else {
            sb.replace(2, str.length() - 2, "****");
        }
        return sb.toString();
    }

    private static String c(String str) {
        int indexOf = str.indexOf(45);
        if (indexOf <= 0 || indexOf >= str.length() - 1) {
            return str;
        }
        String[] split = str.split("-");
        if (2 != split.length) {
            return str;
        }
        int length = split[1].length() / 3;
        int i = split[1].length() % 3 != 0 ? length + 1 : length;
        int length2 = (split[1].length() - i) - length;
        StringBuilder sb = new StringBuilder(str.length());
        sb.append(split[0]);
        sb.append('-');
        sb.append(split[1].substring(0, i));
        for (int i2 = 0; i2 < length2; i2++) {
            sb.append('*');
        }
        sb.append(split[1].substring(i + length2));
        return sb.toString();
    }

    public static String c() {
        String str = "";
        try {
            TokenResult tokenResult = b((Context) LauncherApplication.a()).getTokenResult();
            if (tokenResult != null) {
                str = tokenResult.apdid;
            }
            OAuthTraceLogger.a((String) com.alipay.mobile.nebulauc.util.CommonUtil.TAG, "tokenResult == null, return mApdid:".concat(String.valueOf(str)));
        } catch (Throwable th) {
            OAuthTraceLogger.a(com.alipay.mobile.nebulauc.util.CommonUtil.TAG, "getApdid error", th);
        }
        return str;
    }

    public static String d() {
        String str = "";
        try {
            TokenResult tokenResult = b((Context) LauncherApplication.a()).getTokenResult();
            if (tokenResult != null) {
                str = tokenResult.apdidToken;
            }
            OAuthTraceLogger.a((String) com.alipay.mobile.nebulauc.util.CommonUtil.TAG, "tokenResult == null, return mApdidToken:".concat(String.valueOf(str)));
        } catch (Throwable th) {
            OAuthTraceLogger.a(com.alipay.mobile.nebulauc.util.CommonUtil.TAG, "getApdid error", th);
        }
        return str;
    }

    private static APSecuritySdk b(Context context) {
        if (b == null) {
            b = APSecuritySdk.getInstance(context);
        }
        return b;
    }

    public static void b(Bundle bundle) {
        if (bundle != null) {
            JSONObject jSONObject = new JSONObject();
            for (String str : bundle.keySet()) {
                try {
                    jSONObject.put(str, bundle.get(str));
                } catch (Throwable th) {
                    LoggerFactory.f().c((String) com.alipay.mobile.nebulauc.util.CommonUtil.TAG, th);
                }
            }
            StringBuilder sb = new StringBuilder("打印cd的数据：");
            sb.append(jSONObject.toString());
            OAuthTraceLogger.a((String) com.alipay.mobile.nebulauc.util.CommonUtil.TAG, sb.toString());
        }
    }

    public static String e() {
        String str;
        try {
            str = InsideSetting.a();
        } catch (Throwable th) {
            OAuthTraceLogger.b((String) com.alipay.mobile.nebulauc.util.CommonUtil.TAG, "getAlipayRegH5Url error:".concat(String.valueOf(th)));
            str = null;
        }
        if (TextUtils.isEmpty(str)) {
            OAuthTraceLogger.a((String) com.alipay.mobile.nebulauc.util.CommonUtil.TAG, (String) "getAlipayRegH5Url: mobilegw empty,default online");
            return "https://custweb.alipay.com/register/h5/quick/index?goto=%s&scene=MINIMAL_REGISTRATION&mobile=%s";
        } else if (str.contains("mobilegw.alipay.com") || str.contains("mobilegwpre.alipay.com")) {
            OAuthTraceLogger.a((String) com.alipay.mobile.nebulauc.util.CommonUtil.TAG, (String) "getAlipayRegH5Url online");
            return "https://custweb.alipay.com/register/h5/quick/index?goto=%s&scene=MINIMAL_REGISTRATION&mobile=%s";
        } else if (str.contains("mobilegw-1-64.test.alipay.net") || str.contains("mobilegw.test.alipay.net")) {
            OAuthTraceLogger.a((String) com.alipay.mobile.nebulauc.util.CommonUtil.TAG, (String) "getAlipayRegH5Url sit");
            return "https://custweb.test.alipay.net/register/h5/quick/index?goto=%s&scene=MINIMAL_REGISTRATION&mobile=%s";
        } else {
            OAuthTraceLogger.a((String) com.alipay.mobile.nebulauc.util.CommonUtil.TAG, (String) "getAlipayRegH5Url dev");
            return "http://custweb.alipay.net/register/h5/quick/index?goto=%s&scene=MINIMAL_REGISTRATION&mobile=%s";
        }
    }
}
