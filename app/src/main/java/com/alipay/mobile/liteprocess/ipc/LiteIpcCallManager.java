package com.alipay.mobile.liteprocess.ipc;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import com.alipay.mobile.common.ipc.api.IPCApiFactory;
import com.alipay.mobile.common.ipc.api.IPCContextManager;
import com.alipay.mobile.common.ipc.api.ServiceBeanManager;
import com.alipay.mobile.common.ipc.api.aidl.IIPCManager;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.liteprocess.Const;

public class LiteIpcCallManager {
    private static LiteIpcCallManager d;
    private IPCContextManager a;
    private IIPCManager b;
    private ServiceBeanManager c;

    private LiteIpcCallManager() {
    }

    static final LiteIpcCallManager a() {
        if (d != null) {
            return d;
        }
        synchronized (LiteIpcCallManager.class) {
            try {
                if (d != null) {
                    LiteIpcCallManager liteIpcCallManager = d;
                    return liteIpcCallManager;
                }
                d = new LiteIpcCallManager();
                return d;
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a(Context context) {
        if (c() != null) {
            c().init(context, b());
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a(Context context, IIPCManager ipcManager) {
        this.b = ipcManager;
        if (c() != null) {
            c().init(context, ipcManager);
        }
    }

    /* access modifiers changed from: 0000 */
    @Nullable
    public final IIPCManager b() {
        if (this.b == null) {
            try {
                this.b = IPCApiFactory.getIPCManager();
            } catch (Throwable t) {
                LoggerFactory.getTraceLogger().error((String) Const.TAG, Log.getStackTraceString(t));
            }
        }
        return this.b;
    }

    /* access modifiers changed from: 0000 */
    @Nullable
    public final IPCContextManager c() {
        if (this.a == null) {
            try {
                this.a = IPCApiFactory.getIPCContextManager();
            } catch (Throwable t) {
                LoggerFactory.getTraceLogger().error((String) Const.TAG, Log.getStackTraceString(t));
            }
        }
        return this.a;
    }

    /* access modifiers changed from: 0000 */
    @Nullable
    public final ServiceBeanManager d() {
        if (this.c == null && c() != null) {
            this.c = c().getServiceBeanManager();
        }
        return this.c;
    }
}
