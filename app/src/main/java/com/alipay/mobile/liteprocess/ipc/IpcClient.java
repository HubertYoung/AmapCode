package com.alipay.mobile.liteprocess.ipc;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.ServiceConnection;
import android.util.Log;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.liteprocess.Config;
import com.alipay.mobile.liteprocess.Const;
import com.alipay.mobile.liteprocess.LiteProcessClientManager;
import com.alipay.mobile.liteprocess.Util;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;

class IpcClient {
    private static boolean f = false;
    private Context a = Util.getContext();
    private Class b;
    private ServiceConnection c;
    private boolean d = false;
    private int e = 0;

    public IpcClient(Class serviceClass, ServiceConnection conn) {
        this.b = serviceClass;
        this.c = conn;
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void bind() {
        /*
            r7 = this;
            com.alipay.mobile.common.logging.api.trace.TraceLogger r3 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r4 = "LiteProcess"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.Class r6 = r7.b
            java.lang.String r6 = r6.getSimpleName()
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r6 = " bind"
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r5 = r5.toString()
            r3.debug(r4, r5)
            android.content.Intent r1 = new android.content.Intent
            android.content.Context r3 = r7.a
            java.lang.Class r4 = r7.b
            r1.<init>(r3, r4)
            com.alipay.mobile.common.logging.api.trace.TraceLogger r3 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ IllegalStateException -> 0x004f }
            java.lang.String r4 = "LiteProcess"
            java.lang.String r5 = "IpcClient start service begin!"
            r3.info(r4, r5)     // Catch:{ IllegalStateException -> 0x004f }
            android.content.Context r3 = r7.a     // Catch:{ IllegalStateException -> 0x004f }
            r3.startService(r1)     // Catch:{ IllegalStateException -> 0x004f }
            com.alipay.mobile.common.logging.api.trace.TraceLogger r3 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ IllegalStateException -> 0x004f }
            java.lang.String r4 = "LiteProcess"
            java.lang.String r5 = "IpcClient start service end!"
            r3.info(r4, r5)     // Catch:{ IllegalStateException -> 0x004f }
        L_0x0046:
            android.content.Context r3 = r7.a     // Catch:{ Throwable -> 0x0065 }
            android.content.ServiceConnection r4 = r7.c     // Catch:{ Throwable -> 0x0065 }
            r5 = 0
            r3.bindService(r1, r4, r5)     // Catch:{ Throwable -> 0x0065 }
        L_0x004e:
            return
        L_0x004f:
            r0 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r3 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x0065 }
            java.lang.String r4 = "LiteProcess"
            java.lang.String r5 = "IpcClient start service failed!"
            r3.error(r4, r5)     // Catch:{ Throwable -> 0x0065 }
            com.alipay.mobile.common.logging.api.trace.TraceLogger r3 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x0065 }
            java.lang.String r4 = "LiteProcess"
            r3.error(r4, r0)     // Catch:{ Throwable -> 0x0065 }
            goto L_0x0046
        L_0x0065:
            r2 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r3 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r4 = "LiteProcess"
            java.lang.String r5 = android.util.Log.getStackTraceString(r2)
            r3.error(r4, r5)
            goto L_0x004e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.liteprocess.ipc.IpcClient.bind():void");
    }

    public void unbind() {
        if (this.d) {
            this.a.unbindService(this.c);
            this.d = false;
        }
    }

    public void rebind() {
        if (a()) {
            unbind();
            if (this.e < Config.MAX_RETRY_BIND_TIMES) {
                LoggerFactory.getTraceLogger().debug(Const.TAG, this.b.getSimpleName() + " retry bind " + this.e);
                this.e++;
                bind();
                return;
            }
            LoggerFactory.getTraceLogger().debug(Const.TAG, this.b.getSimpleName() + " can not retry bind " + this.e);
        } else if (!f) {
            f = true;
            LoggerFactory.getTraceLogger().warn((String) Const.TAG, this.b.getSimpleName() + " server not alive and stop self");
            LiteProcessClientManager.stopSelfByClient();
        }
    }

    public void setBind(boolean bound) {
        this.d = bound;
    }

    public boolean isBind() {
        return this.d;
    }

    private boolean a() {
        try {
            for (RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) this.a.getSystemService(WidgetType.ACTIVITY)).getRunningAppProcesses()) {
                if (runningAppProcessInfo.processName.equalsIgnoreCase(this.a.getPackageName())) {
                    return true;
                }
            }
        } catch (Throwable t) {
            LoggerFactory.getTraceLogger().error((String) Const.TAG, Log.getStackTraceString(t));
        }
        return false;
    }
}
