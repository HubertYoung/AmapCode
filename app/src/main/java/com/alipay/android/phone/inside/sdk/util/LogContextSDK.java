package com.alipay.android.phone.inside.sdk.util;

import android.content.Context;
import com.alipay.android.phone.inside.log.api.LogContext;

public class LogContextSDK implements LogContext {
    private Context mContext;

    public String getInfo(String str) {
        return "";
    }

    public LogContextSDK(Context context) {
        if (context != null) {
            this.mContext = context.getApplicationContext();
        }
    }

    public Context getContext() {
        return this.mContext;
    }
}
