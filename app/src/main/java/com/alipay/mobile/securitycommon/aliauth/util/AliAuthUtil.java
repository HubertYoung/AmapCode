package com.alipay.mobile.securitycommon.aliauth.util;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import com.alipay.apmobilesecuritysdk.face.APSecuritySdk;
import com.alipay.apmobilesecuritysdk.face.APSecuritySdk.TokenResult;
import com.alipay.mobile.account.adapter.LogAdapter;
import org.json.JSONObject;

public class AliAuthUtil {
    public static final String Domain = "Domain";
    public static final String HttpOnly = "httpOnly";
    public static final String MaxAge = "maxAge";
    public static final String Name = "name";
    public static final String Path = "path";
    public static final String Secure = "secure";
    public static final String Value = "value";
    public static final String Version = "version";

    public static String getUmidToken(Context context) {
        TokenResult tokenResult = APSecuritySdk.getInstance(context).getTokenResult();
        return tokenResult != null ? tokenResult.umidToken : "tokenResult=null";
    }

    public static String getWifiMac(Context context) {
        WifiInfo connectionInfo = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo();
        if (connectionInfo == null) {
            return "";
        }
        return connectionInfo.getMacAddress();
    }

    public static boolean getBoolean(JSONObject jSONObject, String str, boolean z) {
        try {
            if (jSONObject.has(str)) {
                return jSONObject.getBoolean(str);
            }
            return z;
        } catch (Exception e) {
            LogAdapter.a((String) "AliAuthUtil", (Throwable) e);
            return z;
        }
    }

    public static String getString(JSONObject jSONObject, String str) {
        try {
            if (jSONObject.has(str)) {
                return jSONObject.getString(str);
            }
            return "";
        } catch (Exception e) {
            LogAdapter.a((String) "AliAuthUtil", (Throwable) e);
            return "";
        }
    }

    public static int getInt(JSONObject jSONObject, String str, int i) {
        try {
            if (jSONObject.has(str)) {
                return jSONObject.getInt(str);
            }
            return i;
        } catch (Exception e) {
            LogAdapter.a((String) "AliAuthUtil", (Throwable) e);
            return i;
        }
    }
}
