package com.alipay.mobile.core.impl;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import com.alipay.mobile.framework.msg.MsgCodeConstants;
import com.alipay.mobile.quinox.utils.TraceLogger;

public class ActivityAllStoppedCallback {
    private Context a;

    public ActivityAllStoppedCallback(Context context) {
        this.a = context;
    }

    public void onBackground() {
        try {
            LocalBroadcastManager.getInstance(this.a).sendBroadcast(new Intent(MsgCodeConstants.FRAMEWORK_ACTIVITY_ALL_STOPPED));
        } catch (Throwable e) {
            TraceLogger.w((String) "ActivityAllStoppedCallback", e);
        }
    }
}
