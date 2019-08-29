package com.alipay.apmobilesecuritysdk.storage;

import android.content.Context;
import com.alipay.security.mobile.module.commonutils.CommonUtils;
import com.alipay.security.mobile.module.crypto.DigestUtil;
import com.alipay.security.mobile.module.crypto.SecurityUtils;
import com.alipay.security.mobile.module.localstorage.PublicStorage;
import com.alipay.security.mobile.module.localstorage.SecurityStorageUtils;
import com.alipay.security.mobile.module.localstorage.SharePreferenceStorage;
import java.util.UUID;

public class SettingsStorage {
    private static String a = "";

    public static synchronized void a(Context context, String str) {
        synchronized (SettingsStorage.class) {
            a(context, (String) "update_time_interval", str);
        }
    }

    public static synchronized long a(Context context) {
        long j;
        synchronized (SettingsStorage.class) {
            String h = h(context, "update_time_interval");
            j = 86400000;
            if (CommonUtils.isNotBlank(h)) {
                try {
                    j = Long.parseLong(h);
                } catch (Exception unused) {
                }
            }
        }
        return j;
    }

    public static synchronized void b(Context context, String str) {
        synchronized (SettingsStorage.class) {
            a(context, (String) "last_machine_boot_time", str);
        }
    }

    public static synchronized String b(Context context) {
        String h;
        synchronized (SettingsStorage.class) {
            h = h(context, "last_machine_boot_time");
        }
        return h;
    }

    public static synchronized void c(Context context, String str) {
        synchronized (SettingsStorage.class) {
            a(context, (String) "last_apdid_env", str);
        }
    }

    public static synchronized String c(Context context) {
        String h;
        synchronized (SettingsStorage.class) {
            try {
                h = h(context, "last_apdid_env");
            }
        }
        return h;
    }

    public static synchronized void d(Context context, String str) {
        synchronized (SettingsStorage.class) {
            a(context, (String) "agent_switch", str);
        }
    }

    public static synchronized String d(Context context) {
        String h;
        synchronized (SettingsStorage.class) {
            h = h(context, "agent_switch");
        }
        return h;
    }

    public static synchronized void a(Context context, boolean z) {
        synchronized (SettingsStorage.class) {
            a(context, (String) "log_switch", z ? "1" : "0");
        }
    }

    public static synchronized boolean e(Context context) {
        synchronized (SettingsStorage.class) {
            String h = h(context, "log_switch");
            if (h == null || !"1".equals(h)) {
                return false;
            }
            return true;
        }
    }

    public static synchronized void e(Context context, String str) {
        synchronized (SettingsStorage.class) {
            a(context, (String) "dynamic_key", str);
        }
    }

    public static synchronized String f(Context context) {
        String h;
        synchronized (SettingsStorage.class) {
            h = h(context, "dynamic_key");
        }
        return h;
    }

    public static synchronized void f(Context context, String str) {
        synchronized (SettingsStorage.class) {
            a(context, (String) "webrtc_url", str);
        }
    }

    public static synchronized String g(Context context) {
        String h;
        synchronized (SettingsStorage.class) {
            h = h(context, "webrtc_url");
        }
        return h;
    }

    private static synchronized String h(Context context, String str) {
        String readFromSharedPreference;
        synchronized (SettingsStorage.class) {
            readFromSharedPreference = SecurityStorageUtils.readFromSharedPreference(context, "vkeyid_settings", str);
        }
        return readFromSharedPreference;
    }

    private static synchronized void a(Context context, String str, String str2) {
        synchronized (SettingsStorage.class) {
            SecurityStorageUtils.writeToSharedPreference(context, "vkeyid_settings", str, str2);
        }
    }

    public static synchronized long g(Context context, String str) {
        synchronized (SettingsStorage.class) {
            try {
                String readFromSharedPreference = SecurityStorageUtils.readFromSharedPreference(context, "vkeyid_settings", "vkey_valid".concat(String.valueOf(str)));
                if (CommonUtils.isBlank(readFromSharedPreference)) {
                    return 0;
                }
                long parseLong = Long.parseLong(readFromSharedPreference);
                return parseLong;
            } catch (Throwable unused) {
                return 0;
            }
        }
    }

    public static synchronized void a(Context context, String str, long j) {
        synchronized (SettingsStorage.class) {
            SecurityStorageUtils.writeToSharedPreference(context, "vkeyid_settings", "vkey_valid".concat(String.valueOf(str)), String.valueOf(j));
        }
    }

    public static synchronized String h(Context context) {
        synchronized (SettingsStorage.class) {
            try {
                if (CommonUtils.isNotBlank(a)) {
                    String str = a;
                    return str;
                }
                String dataFromSharePreference = SharePreferenceStorage.getDataFromSharePreference(context, "alipay_vkey_random", "random_key", "");
                a = dataFromSharePreference;
                if (CommonUtils.isNotBlank(dataFromSharePreference)) {
                    String str2 = a;
                    return str2;
                }
                String readDataFromPublicArea = PublicStorage.readDataFromPublicArea("wxxzyy_v1");
                if (CommonUtils.isNotBlank(readDataFromPublicArea)) {
                    String decrypt = SecurityUtils.decrypt(SecurityUtils.getSeed(), readDataFromPublicArea);
                    if (CommonUtils.isNotBlank(decrypt)) {
                        a = decrypt;
                        return decrypt;
                    }
                }
                a = DigestUtil.sha1ByString(UUID.randomUUID().toString());
                SharePreferenceStorage.writeDataToSharePreference(context, "alipay_vkey_random", "random_key", a);
                String encrypt = SecurityUtils.encrypt(SecurityUtils.getSeed(), a);
                if (CommonUtils.isNotBlank(encrypt)) {
                    PublicStorage.writeDataToPublicArea("wxxzyy_v1", encrypt);
                }
                String str3 = a;
                return str3;
            }
        }
    }
}
