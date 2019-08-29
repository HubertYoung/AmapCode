package com.alipay.mobile.beehive.capture.service;

import android.os.Bundle;
import com.alipay.mobile.framework.app.MicroApplication;
import com.alipay.mobile.framework.service.ext.ExternalService;

public abstract class CaptureService extends ExternalService {
    public abstract void capture(MicroApplication microApplication, CaptureListener captureListener, String str, Bundle bundle);
}
