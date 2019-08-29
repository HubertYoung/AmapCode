package com.alipay.apmobilesecuritysdk.storage;

import android.content.Context;
import com.alipay.apmobilesecuritysdk.loggers.LoggerUtil;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.alipay.security.mobile.module.commonutils.CommonUtils;
import com.alipay.security.mobile.module.localstorage.SecurityStorageUtils;
import org.json.JSONObject;

public class ApdidStorage {
    public static synchronized void a(Context context) {
        synchronized (ApdidStorage.class) {
            SecurityStorageUtils.writeToSharedPreference(context, "vkeyid_profiles_v3", "deviceid", "");
            SecurityStorageUtils.writeToFile("wxcasxx_v3", "wxcasxx", "");
        }
    }

    public static synchronized void a(Context context, ApdidStorageModel apdidStorageModel) {
        synchronized (ApdidStorage.class) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put(DictionaryKeys.DEV_APDIDTOKEN, apdidStorageModel.a());
                jSONObject.put("deviceInfoHash", apdidStorageModel.b());
                jSONObject.put("timestamp", apdidStorageModel.c());
                String jSONObject2 = jSONObject.toString();
                SecurityStorageUtils.writeToSharedPreference(context, "vkeyid_profiles_v3", "deviceid", jSONObject2);
                SecurityStorageUtils.writeToFile("wxcasxx_v3", "wxcasxx", jSONObject2);
            } catch (Exception e) {
                LoggerUtil.a((Throwable) e);
            }
        }
    }

    public static synchronized ApdidStorageModel b(Context context) {
        ApdidStorageModel a;
        synchronized (ApdidStorage.class) {
            String readFromSharedPreference = SecurityStorageUtils.readFromSharedPreference(context, "vkeyid_profiles_v3", "deviceid");
            if (CommonUtils.isBlank(readFromSharedPreference)) {
                readFromSharedPreference = SecurityStorageUtils.readFromFile("wxcasxx_v3", "wxcasxx");
            }
            a = a(readFromSharedPreference);
        }
        return a;
    }

    public static synchronized ApdidStorageModel c(Context context) {
        synchronized (ApdidStorage.class) {
            String readFromSharedPreference = SecurityStorageUtils.readFromSharedPreference(context, "vkeyid_profiles_v3", "deviceid");
            if (CommonUtils.isBlank(readFromSharedPreference)) {
                return null;
            }
            ApdidStorageModel a = a(readFromSharedPreference);
            return a;
        }
    }

    public static synchronized ApdidStorageModel a() {
        synchronized (ApdidStorage.class) {
            String readFromFile = SecurityStorageUtils.readFromFile("wxcasxx_v3", "wxcasxx");
            if (CommonUtils.isBlank(readFromFile)) {
                return null;
            }
            ApdidStorageModel a = a(readFromFile);
            return a;
        }
    }

    private static ApdidStorageModel a(String str) {
        try {
            if (CommonUtils.isBlank(str)) {
                return null;
            }
            JSONObject jSONObject = new JSONObject(str);
            return new ApdidStorageModel(jSONObject.optString(DictionaryKeys.DEV_APDIDTOKEN), jSONObject.optString("deviceInfoHash"), jSONObject.optString("timestamp"));
        } catch (Exception e) {
            LoggerUtil.a((Throwable) e);
            return null;
        }
    }
}
