package com.alipay.android.phone.inside.commonbiz.service;

import android.os.Bundle;
import com.alipay.android.phone.inside.commonbiz.status.BizRunningStatus;
import com.alipay.android.phone.inside.framework.service.AbstractInsideService;

public class CommonGetRunningStatusService extends AbstractInsideService<Bundle, Bundle> {
    public /* synthetic */ Object startForResult(Object obj) throws Exception {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isRunning", BizRunningStatus.a());
        return bundle;
    }
}
