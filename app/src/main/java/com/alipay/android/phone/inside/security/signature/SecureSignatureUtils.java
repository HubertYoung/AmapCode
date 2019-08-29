package com.alipay.android.phone.inside.security.signature;

import android.content.Context;
import com.alibaba.wireless.security.open.SecException;
import com.alibaba.wireless.security.open.SecurityGuardManager;
import com.alibaba.wireless.security.open.SecurityGuardParamContext;
import com.alibaba.wireless.security.open.securesignature.ISecureSignatureComponent;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.ex.ExceptionLogger;
import com.alipay.android.phone.inside.security.api.SecurityGuardInit;
import com.alipay.android.phone.inside.security.model.SGParamContext;

public class SecureSignatureUtils {
    public static String a(Context context, SGParamContext sGParamContext) throws Exception {
        ISecureSignatureComponent secureSignatureComp = SecurityGuardManager.getInstance(context).getSecureSignatureComp();
        SecurityGuardParamContext securityGuardParamContext = new SecurityGuardParamContext();
        securityGuardParamContext.paramMap = sGParamContext.a;
        securityGuardParamContext.appKey = sGParamContext.b;
        securityGuardParamContext.requestType = sGParamContext.c;
        securityGuardParamContext.reserved1 = sGParamContext.d;
        securityGuardParamContext.reserved2 = sGParamContext.e;
        try {
            return secureSignatureComp.signRequest(securityGuardParamContext, SecurityGuardInit.a());
        } catch (SecException e) {
            ExceptionLogger e2 = LoggerFactory.e();
            StringBuilder sb = new StringBuilder("SecureSignatureUtils::signRequest > ErrorCode: ");
            sb.append(e.getErrorCode());
            e2.a("inside", sb.toString());
            LoggerFactory.f().c((String) "inside", (Throwable) e);
            throw e;
        }
    }
}
