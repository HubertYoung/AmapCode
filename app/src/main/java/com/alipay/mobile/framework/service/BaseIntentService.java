package com.alipay.mobile.framework.service;

import android.app.IntentService;
import android.content.Context;
import android.content.res.Resources;
import com.alipay.mobile.framework.LauncherApplicationAgent;

public abstract class BaseIntentService extends IntentService {
    public BaseIntentService(String name) {
        super(name);
    }

    /* access modifiers changed from: protected */
    public void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        LauncherApplicationAgent.getInstance().getBundleContext().replaceResources(base, getClass().getName(), new String[0]);
    }

    public Resources getResources() {
        return super.getResources();
    }
}
