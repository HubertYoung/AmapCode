package com.alipay.apmobilesecuritysdk.storage;

import android.content.Context;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.apmobilesecuritysdk.loggers.LoggerUtil;
import com.alipay.rdssecuritysdk.constant.CONST;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.alipay.security.mobile.module.commonutils.CommonUtils;
import com.alipay.security.mobile.module.localstorage.SecurityStorageUtils;
import org.json.JSONObject;

public class ApdidStorageV4 {
    public static void a(Context context) {
        synchronized (ApdidStorageV4.class) {
            SecurityStorageUtils.writeToSharedPreference(context, "vkeyid_profiles_v4", "key_deviceid_v4", "");
            SecurityStorageUtils.writeToFile("wxcasxx_v4", "key_wxcasxx_v4", "");
        }
    }

    public static void a(Context context, ApdidStorageModelV4 apdidStorageModelV4) {
        synchronized (ApdidStorageV4.class) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put(DictionaryKeys.DEV_APDIDTOKEN, apdidStorageModelV4.a());
                jSONObject.put("deviceInfoHash", apdidStorageModelV4.b());
                jSONObject.put("timestamp", apdidStorageModelV4.c());
                jSONObject.put("tid", apdidStorageModelV4.d());
                jSONObject.put("utdid", apdidStorageModelV4.e());
                String jSONObject2 = jSONObject.toString();
                SecurityStorageUtils.writeToSharedPreference(context, "vkeyid_profiles_v4", "key_deviceid_v4", jSONObject2);
                SecurityStorageUtils.writeToFile("wxcasxx_v4", "key_wxcasxx_v4", jSONObject2);
            } catch (Exception e) {
                LoggerUtil.a((Throwable) e);
                TraceLogger f = LoggerFactory.f();
                StringBuilder sb = new StringBuilder("[-] V4 saveApdid happed exception：");
                sb.append(CommonUtils.getStackString(e));
                f.b((String) CONST.LOG_TAG, sb.toString());
            }
        }
    }

    public static ApdidStorageModelV4 b(Context context) {
        ApdidStorageModelV4 a;
        synchronized (ApdidStorageV4.class) {
            String readFromSharedPreference = SecurityStorageUtils.readFromSharedPreference(context, "vkeyid_profiles_v4", "key_deviceid_v4");
            if (CommonUtils.isBlank(readFromSharedPreference)) {
                readFromSharedPreference = SecurityStorageUtils.readFromFile("wxcasxx_v4", "key_wxcasxx_v4");
            }
            a = a(readFromSharedPreference);
        }
        return a;
    }

    public static ApdidStorageModelV4 c(Context context) {
        synchronized (ApdidStorageV4.class) {
            String readFromSharedPreference = SecurityStorageUtils.readFromSharedPreference(context, "vkeyid_profiles_v4", "key_deviceid_v4");
            if (CommonUtils.isBlank(readFromSharedPreference)) {
                return null;
            }
            ApdidStorageModelV4 a = a(readFromSharedPreference);
            return a;
        }
    }

    public static ApdidStorageModelV4 a() {
        synchronized (ApdidStorageV4.class) {
            String readFromFile = SecurityStorageUtils.readFromFile("wxcasxx_v4", "key_wxcasxx_v4");
            if (CommonUtils.isBlank(readFromFile)) {
                return null;
            }
            ApdidStorageModelV4 a = a(readFromFile);
            return a;
        }
    }

    private static ApdidStorageModelV4 a(String str) {
        try {
            if (CommonUtils.isBlank(str)) {
                return null;
            }
            JSONObject jSONObject = new JSONObject(str);
            ApdidStorageModelV4 apdidStorageModelV4 = new ApdidStorageModelV4(jSONObject.optString(DictionaryKeys.DEV_APDIDTOKEN), jSONObject.optString("deviceInfoHash"), jSONObject.optString("timestamp"), jSONObject.optString("tid"), jSONObject.optString("utdid"));
            return apdidStorageModelV4;
        } catch (Exception e) {
            LoggerUtil.a((Throwable) e);
            return null;
        }
    }
}
