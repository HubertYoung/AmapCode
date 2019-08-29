package com.alipay.android.phone.inside.storage.pref;

import android.text.TextUtils;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.security.util.DesCBC;
import com.alipay.android.phone.inside.security.util.EncryptUtil;
import com.alipay.android.phone.inside.storage.StorageInit;

public class EncryptPrefUtil {
    public static boolean a(String str, String str2, String str3) {
        String str4;
        if (!TextUtils.isEmpty(str3)) {
            try {
                str4 = DesCBC.a(EncryptUtil.a(StorageInit.a()), str3);
            } catch (Throwable th) {
                LoggerFactory.e().a((String) "storage", (String) "PrefEncryptEx", th);
                return false;
            }
        } else {
            str4 = null;
        }
        return PrefUtils.a(str, str2, str4);
    }

    public static String b(String str, String str2, String str3) {
        if (!PrefUtils.a(str, str2)) {
            return str3;
        }
        String b = PrefUtils.b(str, str2, null);
        if (!TextUtils.isEmpty(b)) {
            try {
                b = DesCBC.b(EncryptUtil.a(StorageInit.a()), b);
            } catch (Throwable th) {
                LoggerFactory.e().a((String) "storage", (String) "PrefDecryptEx", th);
                return null;
            }
        }
        return b;
    }
}
