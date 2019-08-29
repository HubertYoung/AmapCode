package com.alipay.mobile.monitor.track.spm;

import android.content.Context;
import com.alipay.mobile.common.logging.api.LoggerFactory;

public class SpmMonitorBinder {
    public static void bind(Context context) {
        SpmMonitor.INTANCE.setContext(context);
        LoggerFactory.getLogContext().setSpmMonitor(SpmMonitor.INTANCE);
        LoggerFactory.getLogContext().setSemMonitor(SemMonitor.INSTANCE);
    }
}
