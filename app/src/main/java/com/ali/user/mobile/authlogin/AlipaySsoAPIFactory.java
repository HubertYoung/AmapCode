package com.ali.user.mobile.authlogin;

import android.content.Context;
import com.ali.user.mobile.authlogin.auth.IAlipaySSOAuthLoginAPI;
import com.ali.user.mobile.authlogin.auth.impl.AlipaySSOAuthLoginApiImpl;
import com.ali.user.mobile.authlogin.exception.ParamNullException;
import com.ali.user.mobile.log.AliUserLog;

public class AlipaySsoAPIFactory {
    public static IAlipaySSOAuthLoginAPI a(Context context) throws ParamNullException {
        if (context == null) {
            AliUserLog.c("AliAuth_AlipaySsoAPIFactory", "createSsoApi param is null");
            throw new ParamNullException((String) "creatSsoApi param context is null");
        }
        AliUserLog.c("AliAuth_AlipaySsoAPIFactory", "createSsoApi normal");
        return new AlipaySSOAuthLoginApiImpl(context);
    }
}
