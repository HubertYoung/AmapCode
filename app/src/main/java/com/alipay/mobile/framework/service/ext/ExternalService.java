package com.alipay.mobile.framework.service.ext;

import android.os.Bundle;
import com.alipay.mobile.framework.service.MicroService;

public abstract class ExternalService extends MicroService {
    private boolean a = false;

    public final boolean isActivated() {
        return this.a;
    }

    public final void create(Bundle params) {
        onCreate(params);
        this.a = true;
    }

    public final void destroy(Bundle params) {
        getMicroApplicationContext().onDestroyContent(this);
        onDestroy(params);
        this.a = false;
    }
}
