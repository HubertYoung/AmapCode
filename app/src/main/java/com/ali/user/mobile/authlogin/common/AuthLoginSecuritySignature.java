package com.ali.user.mobile.authlogin.common;

import android.content.Context;
import android.text.TextUtils;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.log.LoggerUtils;
import com.alibaba.wireless.security.open.SecException;
import com.alibaba.wireless.security.open.SecurityGuardManager;
import com.alibaba.wireless.security.open.SecurityGuardParamContext;
import com.alibaba.wireless.security.open.securesignature.ISecureSignatureComponent;
import com.alipay.android.phone.inside.commonbiz.ids.StaticConfig;
import java.util.HashMap;

public class AuthLoginSecuritySignature {
    public static String a(Context context, String str, String str2, String str3) {
        String str4;
        String str5 = "";
        try {
            SecurityGuardManager instance = SecurityGuardManager.getInstance(context);
            AliUserLog.c("AuthLoginSecuritySignature", "获取sgMgr对象");
            if (instance != null) {
                AliUserLog.c("AuthLoginSecuritySignature", "sgMgr not null");
                ISecureSignatureComponent secureSignatureComp = instance.getSecureSignatureComp();
                HashMap hashMap = new HashMap();
                hashMap.put("INPUT", str3);
                if (TextUtils.isEmpty(str2)) {
                    str2 = "a";
                }
                hashMap.put("ATLAS", str2);
                SecurityGuardParamContext securityGuardParamContext = new SecurityGuardParamContext();
                securityGuardParamContext.appKey = str;
                securityGuardParamContext.paramMap = hashMap;
                securityGuardParamContext.requestType = 5;
                str4 = secureSignatureComp.signRequest(securityGuardParamContext, StaticConfig.a());
                try {
                    AliUserLog.c("AuthLoginSecuritySignature", "sign = ".concat(String.valueOf(str4)));
                } catch (SecException e) {
                    str5 = str4;
                    e = e;
                } catch (Throwable th) {
                    str5 = str4;
                    th = th;
                    AliUserLog.b((String) "AuthLoginSecuritySignature", th);
                    LoggerUtils.a("event", "AliAuthLoginSDK_SignFailure", "ALIAUTH_0415_13", "Sign_2");
                    AliUserLog.d("AuthLoginSecuritySignature", "end sign = ".concat(String.valueOf(str5)));
                    return str5;
                }
            } else {
                str4 = str5;
            }
            str5 = str4;
        } catch (SecException e2) {
            e = e2;
            AliUserLog.b((String) "AuthLoginSecuritySignature", (Throwable) e);
            LoggerUtils.a("event", "AliAuthLoginSDK_SignFailure", "ALIAUTH_0415_13", "Sign_1");
            AliUserLog.d("AuthLoginSecuritySignature", "end sign = ".concat(String.valueOf(str5)));
            return str5;
        } catch (Throwable th2) {
            th = th2;
            AliUserLog.b((String) "AuthLoginSecuritySignature", th);
            LoggerUtils.a("event", "AliAuthLoginSDK_SignFailure", "ALIAUTH_0415_13", "Sign_2");
            AliUserLog.d("AuthLoginSecuritySignature", "end sign = ".concat(String.valueOf(str5)));
            return str5;
        }
        AliUserLog.d("AuthLoginSecuritySignature", "end sign = ".concat(String.valueOf(str5)));
        return str5;
    }
}
