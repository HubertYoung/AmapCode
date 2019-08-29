package com.alipay.android.phone.inside.commonbiz.report;

import com.alipay.android.phone.inside.framework.service.IInsideService;
import com.alipay.android.phone.inside.framework.service.IInsideServiceCallback;

public class ReportLocationPluginService implements IInsideService<String, Void> {
    public /* bridge */ /* synthetic */ Object startForResult(Object obj) throws Exception {
        return null;
    }

    public /* synthetic */ void start(IInsideServiceCallback iInsideServiceCallback, Object obj) throws Exception {
        try {
            a((String) obj);
            iInsideServiceCallback.onComplted(null);
        } catch (Throwable th) {
            iInsideServiceCallback.onException(th);
        }
    }

    public /* synthetic */ void start(Object obj) throws Exception {
        a((String) obj);
    }

    private static void a(String str) throws Exception {
        ReportLocationService.a().a("inside_".concat(String.valueOf(str)));
    }
}
