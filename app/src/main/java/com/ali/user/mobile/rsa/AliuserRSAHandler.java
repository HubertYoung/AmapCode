package com.ali.user.mobile.rsa;

import android.content.Context;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.rpc.facade.RSAService;
import com.ali.user.mobile.rpc.vo.mobilegw.RSAPKeyResult;
import com.alipay.android.phone.inside.commonservice.CommonServiceFactory;

public class AliuserRSAHandler implements RSAHandler {
    private final RSAService a = ((RSAService) CommonServiceFactory.getInstance().getRpcService().getRpcProxy(RSAService.class));
    private RSAPKeyResult b;

    public AliuserRSAHandler(Context context) {
        AliUserLog.c("AliuserRSAHandler", "context:".concat(String.valueOf(context)));
    }

    public final RSAPKeyResult a() {
        AliUserLog.c("AliuserRSAHandler", "getRSAKey");
        if (this.b != null) {
            return this.b;
        }
        this.b = this.a.getRSAKey();
        return this.b;
    }
}
