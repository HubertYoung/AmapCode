package com.alipay.android.phone.inside.commonbiz.service;

import com.alipay.android.phone.inside.commonbiz.status.BizRunningStatus;
import com.alipay.android.phone.inside.framework.service.AbstractInsideService;

public class CommonSetRunningStatusService extends AbstractInsideService<Boolean, Boolean> {
    public /* synthetic */ Object startForResult(Object obj) throws Exception {
        BizRunningStatus.a(((Boolean) obj).booleanValue());
        return Boolean.TRUE;
    }
}
