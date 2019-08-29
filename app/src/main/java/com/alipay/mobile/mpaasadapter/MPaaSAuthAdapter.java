package com.alipay.mobile.mpaasadapter;

import android.os.Bundle;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.service.common.IAuthService;

public class MPaaSAuthAdapter extends IAuthService {
    public String getUserId() {
        return LoggerFactory.getLogContext().getUserId();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle bundle) {
    }
}
