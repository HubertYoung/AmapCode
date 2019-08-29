package com.ali.user.mobile.authlogin.common;

import android.content.Context;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.log.LoggerUtils;
import com.alibaba.wireless.security.open.SecurityGuardManager;
import com.alibaba.wireless.security.open.staticdataencrypt.IStaticDataEncryptComponent;
import com.alipay.android.phone.inside.commonbiz.ids.StaticConfig;

public class AuthLoginSecurityEncryptor {
    public static String a(Context context, String str, String str2) {
        String str3;
        try {
            StringBuilder sb = new StringBuilder("encrypt param appkey=");
            sb.append(str);
            sb.append(" data=");
            sb.append(str2);
            AliUserLog.c("AuthLoginSecurityEncryptor", sb.toString());
            SecurityGuardManager instance = SecurityGuardManager.getInstance(context);
            if (instance != null) {
                AliUserLog.c("AuthLoginSecurityEncryptor", "sgMgr != null");
                IStaticDataEncryptComponent staticDataEncryptComp = instance.getStaticDataEncryptComp();
                if (staticDataEncryptComp != null) {
                    AliUserLog.c("AuthLoginSecurityEncryptor", "comp != null");
                    str3 = staticDataEncryptComp.staticSafeEncrypt(16, str, str2, StaticConfig.a());
                    try {
                        AliUserLog.c("AuthLoginSecurityEncryptor", "encryptData=".concat(String.valueOf(str3)));
                        return str3;
                    } catch (Throwable th) {
                        th = th;
                    }
                }
            }
            r4 = "";
            return "";
        } catch (Throwable th2) {
            th = th2;
            str3 = "";
            AliUserLog.b((String) "AuthLoginSecurityEncryptor", th);
            LoggerUtils.a("event", "AliAuthLoginSDK_EncUuidFailure", "ALIAUTH_0415_11", "");
            return str3;
        }
    }
}
