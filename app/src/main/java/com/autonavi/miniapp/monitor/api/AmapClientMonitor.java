package com.autonavi.miniapp.monitor.api;

import android.content.Context;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.monitor.DataflowID;
import com.alipay.mobile.common.logging.api.monitor.DataflowModel;
import com.autonavi.miniapp.monitor.api.analysis.power.TrafficConsumeInfo;
import java.lang.reflect.Method;

@Deprecated
public class AmapClientMonitor {
    @Deprecated
    private static AmapClientMonitor INSTANCE = null;
    @Deprecated
    private static final String TAG = "AmapMonitorFactory";

    @Deprecated
    private AmapClientMonitor(Context context) {
        try {
            Method declaredMethod = AmapClientMonitor.class.getClassLoader().loadClass("com.autonavi.miniapp.monitor.biz.MonitorFactoryBinder").getDeclaredMethod("bind", new Class[]{Context.class});
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(null, new Object[]{context});
        } catch (Throwable th) {
            LoggerFactory.getTraceLogger().error("AmapMonitorFactory", "create", th);
        }
    }

    @Deprecated
    public static synchronized AmapClientMonitor createInstance(Context context) {
        AmapClientMonitor amapClientMonitor;
        synchronized (AmapClientMonitor.class) {
            if (INSTANCE == null) {
                INSTANCE = new AmapClientMonitor(context);
            }
            amapClientMonitor = INSTANCE;
        }
        return amapClientMonitor;
    }

    @Deprecated
    public static AmapClientMonitor getInstance() {
        if (INSTANCE != null) {
            return INSTANCE;
        }
        throw new IllegalStateException("need createInstance befor use");
    }

    @Deprecated
    public void noteTraficConsume(String str, long j, long j2) {
        noteTraficConsume(null, str, j, j2, DataflowID.ASPECT_V1, null);
    }

    @Deprecated
    public void noteTraficConsume(String str, String str2, long j, long j2) {
        noteTraficConsume(str, str2, j, j2, DataflowID.ASPECT_V1, null);
    }

    @Deprecated
    public void noteTraficConsume(String str, String str2, long j, long j2, DataflowID dataflowID, String str3) {
        try {
            DataflowModel obtain = DataflowModel.obtain(dataflowID, str2, j, j2, str3);
            obtain.host = str;
            AmapMonitorFactory.getMonitorContext().noteTraficConsume(obtain);
        } catch (Throwable th) {
            LoggerFactory.getTraceLogger().error((String) "AmapMonitorFactory", th);
        }
    }

    @Deprecated
    public boolean isTraficConsumeAccept(String str) {
        return AmapMonitorFactory.getMonitorContext().isTraficConsumeAccept(str);
    }

    @Deprecated
    public void updateTraficDegradeCfg(String str) {
        AmapMonitorFactory.getMonitorContext().updateTraficDegradeCfg(str);
    }

    @Deprecated
    public TrafficConsumeInfo loadTrafficConsumeInfo() {
        return AmapMonitorFactory.getMonitorContext().loadTrafficConsumeInfo();
    }

    @Deprecated
    public void autoWakeupReceiver() {
        AmapMonitorFactory.getMonitorContext().autoWakeupReceiver();
    }
}
