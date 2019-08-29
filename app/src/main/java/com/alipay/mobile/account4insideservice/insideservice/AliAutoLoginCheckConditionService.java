package com.alipay.mobile.account4insideservice.insideservice;

import com.alipay.android.phone.inside.framework.service.IInsideService;
import com.alipay.android.phone.inside.framework.service.IInsideServiceCallback;
import com.alipay.mobile.account4insideservice.common.Account4InsideBehaviorLogger;
import com.alipay.mobile.account4insideservice.common.Account4InsideTraceLogger;
import com.alipay.mobile.securitycommon.aliauth.AliAuthService;
import org.json.JSONObject;

public class AliAutoLoginCheckConditionService implements IInsideService<JSONObject, Boolean> {
    public /* synthetic */ Object startForResult(Object obj) throws Exception {
        return a((JSONObject) obj);
    }

    private static Boolean a(JSONObject jSONObject) throws Exception {
        boolean z;
        try {
            z = AliAuthService.getService().canAutoLogin(jSONObject.getString("targetUrl"));
        } catch (Throwable th) {
            Account4InsideBehaviorLogger.a("aliautologin", "AliAutoLoginCheckConditionService_startForResult", th);
            Account4InsideTraceLogger.a("AliAutoLoginCheckConditionService", "AliAutoLoginCheckConditionService startForResult", th);
            z = false;
        }
        return Boolean.valueOf(z);
    }

    public /* synthetic */ void start(IInsideServiceCallback iInsideServiceCallback, Object obj) throws Exception {
        throw new UnsupportedOperationException();
    }

    public /* synthetic */ void start(Object obj) throws Exception {
        throw new UnsupportedOperationException();
    }
}
