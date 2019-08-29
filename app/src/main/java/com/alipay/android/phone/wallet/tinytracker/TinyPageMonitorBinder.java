package com.alipay.android.phone.wallet.tinytracker;

import android.content.Context;
import com.alipay.mobile.common.logging.api.LoggerFactory;

public class TinyPageMonitorBinder {
    public static void bind(Context context) {
        TinyPageMonitor.INTANCE.setContext(context);
        LoggerFactory.getLogContext().setTinyPageMonitor(TinyPageMonitor.INTANCE);
    }
}
