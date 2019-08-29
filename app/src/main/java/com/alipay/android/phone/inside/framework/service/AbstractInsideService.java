package com.alipay.android.phone.inside.framework.service;

import android.content.Context;
import com.alipay.android.phone.inside.framework.LauncherApplication;

public abstract class AbstractInsideService<Params, Result> implements IInsideService<Params, Result> {
    protected static final String TAG = "inside";

    public void start(Params params) throws Exception {
        start(null, params);
    }

    public void start(IInsideServiceCallback<Result> iInsideServiceCallback, Params params) throws Exception {
        Object startForResult = startForResult(params);
        if (iInsideServiceCallback != null) {
            iInsideServiceCallback.onComplted(startForResult);
        }
    }

    public Context getContext() {
        return LauncherApplication.a();
    }
}
