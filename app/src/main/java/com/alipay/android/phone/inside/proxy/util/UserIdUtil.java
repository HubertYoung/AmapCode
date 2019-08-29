package com.alipay.android.phone.inside.proxy.util;

import com.alipay.android.phone.inside.storage.pref.EncryptPrefUtil;

public class UserIdUtil {
    public static String a(String str) {
        return EncryptPrefUtil.b("alipay_inside_auth", "user_id_of_".concat(String.valueOf(str)), null);
    }

    public static void a(String str, String str2) {
        EncryptPrefUtil.a("alipay_inside_auth", "user_id_of_".concat(String.valueOf(str)), str2);
    }
}
