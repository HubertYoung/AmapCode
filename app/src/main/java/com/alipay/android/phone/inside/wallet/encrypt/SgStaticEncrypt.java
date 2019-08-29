package com.alipay.android.phone.inside.wallet.encrypt;

import android.content.Context;
import com.alibaba.wireless.security.open.SecException;
import com.alibaba.wireless.security.open.SecurityGuardManager;
import com.alipay.android.phone.inside.commonbiz.ids.StaticConfig;

public class SgStaticEncrypt {
    static final String KEY_STATIC_ENCRYPT = "inside_trans_aes128";

    public static String encrypt(Context context, String str) throws SecException {
        return SecurityGuardManager.getInstance(context).getStaticDataEncryptComp().staticSafeEncrypt(16, KEY_STATIC_ENCRYPT, str, StaticConfig.a());
    }

    public static String decrypt(Context context, String str) throws SecException {
        return SecurityGuardManager.getInstance(context).getStaticDataEncryptComp().staticSafeDecrypt(16, KEY_STATIC_ENCRYPT, str, StaticConfig.a());
    }
}
