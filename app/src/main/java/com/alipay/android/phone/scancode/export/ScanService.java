package com.alipay.android.phone.scancode.export;

import android.app.Activity;
import android.content.Intent;
import com.alipay.mobile.framework.service.ext.ExternalService;

public abstract class ScanService extends ExternalService {
    public abstract void notifyScanResult(boolean z, Intent intent);

    public abstract void scan(Activity activity, ScanRequest scanRequest, ScanCallback scanCallback);
}
