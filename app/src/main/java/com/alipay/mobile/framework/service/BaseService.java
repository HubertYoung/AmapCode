package com.alipay.mobile.framework.service;

import android.app.Service;
import android.content.Context;
import android.content.res.Resources;
import com.alipay.mobile.framework.LauncherApplicationAgent;

public abstract class BaseService extends Service {
    /* access modifiers changed from: protected */
    public void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        LauncherApplicationAgent.getInstance().getBundleContext().replaceResources(base, getClass().getName(), new String[0]);
    }

    public Resources getResources() {
        return super.getResources();
    }
}
