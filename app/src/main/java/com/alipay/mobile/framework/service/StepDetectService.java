package com.alipay.mobile.framework.service;

import android.content.Context;
import com.alipay.mobile.framework.service.ext.ExternalService;

public abstract class StepDetectService extends ExternalService {
    public abstract long getSteps(Context context);

    public abstract void resetSteps(Context context);

    public abstract void stepDetectStart(Context context);

    public abstract void stepDetectStop(Context context);
}
