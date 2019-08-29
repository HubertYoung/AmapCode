package com.alipay.mobile.monitor.api;

import android.content.Context;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.monitor.DataflowID;
import com.alipay.mobile.common.logging.api.monitor.DataflowModel;
import com.alipay.mobile.monitor.analysis.power.TrafficConsumeInfo;
import java.lang.reflect.Method;

@Deprecated
public class ClientMonitor {
    @Deprecated
    private static ClientMonitor INSTANCE = null;
    @Deprecated
    private static final String TAG = "MonitorFactory";

    @Deprecated
    private ClientMonitor(Context context) {
        try {
            Method declaredMethod = ClientMonitor.class.getClassLoader().loadClass("com.alipay.android.phone.mobilesdk.monitor.MonitorFactoryBinder").getDeclaredMethod("bind", new Class[]{Context.class});
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(null, new Object[]{context});
        } catch (Throwable th) {
            LoggerFactory.getTraceLogger().error("MonitorFactory", "create", th);
        }
    }

    @Deprecated
    public static synchronized ClientMonitor createInstance(Context context) {
        ClientMonitor clientMonitor;
        synchronized (ClientMonitor.class) {
            if (INSTANCE == null) {
                INSTANCE = new ClientMonitor(context);
            }
            clientMonitor = INSTANCE;
        }
        return clientMonitor;
    }

    @Deprecated
    public static ClientMonitor getInstance() {
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
            MonitorFactory.getMonitorContext().noteTraficConsume(obtain);
        } catch (Throwable th) {
            LoggerFactory.getTraceLogger().error((String) "MonitorFactory", th);
        }
    }

    @Deprecated
    public boolean isTraficConsumeAccept(String str) {
        return MonitorFactory.getMonitorContext().isTraficConsumeAccept(str);
    }

    @Deprecated
    public void updateTraficDegradeCfg(String str) {
        MonitorFactory.getMonitorContext().updateTraficDegradeCfg(str);
    }

    @Deprecated
    public TrafficConsumeInfo loadTrafficConsumeInfo() {
        return MonitorFactory.getMonitorContext().loadTrafficConsumeInfo();
    }

    @Deprecated
    public void autoWakeupReceiver() {
        MonitorFactory.getMonitorContext().autoWakeupReceiver();
    }
}
