package com.alipay.mobile.common.transportext.biz.shared;

import android.content.Context;

public final class ExtTransportEnv {
    private static volatile Context CONTEXT;

    public static final void setAppContext(Context context) {
        CONTEXT = context.getApplicationContext();
    }

    public static final Context getAppContext() {
        return CONTEXT;
    }
}
