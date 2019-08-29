package com.alipay.apmobilesecuritysdk.storage;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import com.alipay.security.mobile.module.commonutils.CommonUtils;
import com.alipay.security.mobile.module.crypto.SecurityUtils;
import com.alipay.security.mobile.module.localstorage.SharePreferenceStorage;

public class OpenApdidTokenStorage {
    public static synchronized void a(Context context, String str, String str2) {
        synchronized (OpenApdidTokenStorage.class) {
            try {
                Editor edit = context.getSharedPreferences("openapi_file_pri", 0).edit();
                if (edit != null) {
                    edit.putString("openApi".concat(String.valueOf(str)), SecurityUtils.encrypt(SecurityUtils.getSeed(), str2));
                    edit.commit();
                }
            } catch (Throwable unused) {
            }
        }
    }

    public static synchronized String a(Context context, String str) {
        synchronized (OpenApdidTokenStorage.class) {
            String dataFromSharePreference = SharePreferenceStorage.getDataFromSharePreference(context, "openapi_file_pri", "openApi".concat(String.valueOf(str)), "");
            if (CommonUtils.isBlank(dataFromSharePreference)) {
                return "";
            }
            String decrypt = SecurityUtils.decrypt(SecurityUtils.getSeed(), dataFromSharePreference);
            if (CommonUtils.isBlank(decrypt)) {
                return "";
            }
            return decrypt;
        }
    }

    public static synchronized void a(Context context) {
        synchronized (OpenApdidTokenStorage.class) {
            Editor edit = context.getSharedPreferences("openapi_file_pri", 0).edit();
            if (edit != null) {
                edit.clear();
                edit.commit();
            }
        }
    }
}
