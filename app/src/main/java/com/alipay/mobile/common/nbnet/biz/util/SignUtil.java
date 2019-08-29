package com.alipay.mobile.common.nbnet.biz.util;

import com.alipay.mobile.common.nbnet.biz.log.NBNetLogCat;
import com.taobao.wireless.security.sdk.SecurityGuardManager;
import com.taobao.wireless.security.sdk.SecurityGuardParamContext;
import com.taobao.wireless.security.sdk.securesignature.ISecureSignatureComponent;
import java.util.HashMap;

public class SignUtil {
    public static String a(String content) {
        String sign = "";
        try {
            ISecureSignatureComponent signComp = SecurityGuardManager.getInstance(NBNetEnvUtils.a()).getSecureSignatureComp();
            HashMap paramMap = new HashMap();
            paramMap.put("INPUT", content);
            SecurityGuardParamContext paramContext = new SecurityGuardParamContext();
            paramContext.appKey = NBNetEnvUtils.c();
            paramContext.paramMap = paramMap;
            paramContext.requestType = 16;
            return signComp.signRequest(paramContext);
        } catch (Throwable e) {
            NBNetLogCat.b("SignUtil", "genSignature exp ", e);
            return sign;
        }
    }
}
