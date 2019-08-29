package com.autonavi.miniapp.monitor.biz;

import android.content.Context;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.autonavi.miniapp.monitor.api.AmapMonitorFactory;
import com.autonavi.miniapp.monitor.biz.logmonitor.util.MonitorSPCache;
import com.autonavi.miniapp.monitor.biz.logmonitor.util.MonitorSPMulti;
import com.autonavi.miniapp.monitor.biz.logmonitor.util.MonitorSPPrivate;
import com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician;

public class MonitorFactoryBinder {
    private static final String TAG = "AmapMonitorFactory";
    private static boolean sIsBound;

    public static void bind(Context context) {
        if (sIsBound) {
            LoggerFactory.getTraceLogger().error((String) "AmapMonitorFactory", (Throwable) new IllegalStateException("AmapMonitorFactory.bind repeated"));
            return;
        }
        sIsBound = true;
        MonitorSPCache.createInstance(context);
        MonitorSPMulti.createInstance(context);
        MonitorSPPrivate.createInstance(context);
        UserDiagnostician.createInstance(context);
        MonitorContextImpl monitorContextImpl = new MonitorContextImpl(context);
        AmapMonitorFactory.bind(monitorContextImpl, new TimestampInfoImpl(context));
        monitorContextImpl.onProcessLaunch();
    }
}
