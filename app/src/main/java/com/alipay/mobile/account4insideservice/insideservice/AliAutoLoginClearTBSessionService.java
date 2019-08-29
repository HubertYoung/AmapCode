package com.alipay.mobile.account4insideservice.insideservice;

import android.os.Bundle;
import com.alipay.android.phone.inside.framework.service.IInsideService;
import com.alipay.android.phone.inside.framework.service.IInsideServiceCallback;
import com.alipay.mobile.account4insideservice.common.Account4InsideBehaviorLogger;
import com.alipay.mobile.account4insideservice.common.Account4InsideTraceLogger;
import com.alipay.mobile.securitycommon.aliauth.AliAuthService;

public class AliAutoLoginClearTBSessionService implements IInsideService<Bundle, Bundle> {
    public /* synthetic */ void start(Object obj) throws Exception {
        Bundle bundle = (Bundle) obj;
        try {
            Account4InsideTraceLogger.a("AliAutoLoginClearTBSessionService", "AliAutoLoginClearTBSessionService start");
            AliAuthService.getService().clearCache(bundle);
        } catch (Throwable th) {
            Account4InsideTraceLogger.a("AliAutoLoginClearTBSessionService", "clear tb sid error", th);
            Account4InsideBehaviorLogger.a("aliautologin", "AliAutoLoginService_start", th);
        }
    }

    public /* synthetic */ Object startForResult(Object obj) throws Exception {
        throw new UnsupportedOperationException();
    }

    public /* synthetic */ void start(IInsideServiceCallback iInsideServiceCallback, Object obj) throws Exception {
        throw new UnsupportedOperationException();
    }
}
