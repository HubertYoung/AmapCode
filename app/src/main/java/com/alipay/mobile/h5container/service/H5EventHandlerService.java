package com.alipay.mobile.h5container.service;

import android.os.Bundle;
import com.alipay.mobile.framework.service.ext.ExternalService;
import com.alipay.mobile.nebula.process.H5EventHandler;

public abstract class H5EventHandlerService extends ExternalService implements H5EventHandler {
    public abstract Bundle getStartParams();

    public abstract void setStartParams(Bundle bundle);
}
