package com.alipay.mobile.liteprocess.ipc;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import com.alipay.mobile.common.ipc.api.IPCContextManager;
import com.alipay.mobile.common.ipc.api.aidl.IIPCManager;
import com.alipay.mobile.common.ipc.api.aidl.IIPCManager.Stub;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.liteprocess.Const;
import com.alipay.mobile.liteprocess.LiteProcessClientManager;
import com.alipay.mobile.liteprocess.Util;

public class IpcCallClient {
    /* access modifiers changed from: private */
    public static IpcClient a;
    private static boolean b = false;

    class IpcCallConn implements ServiceConnection {
        /* access modifiers changed from: private */
        public IIPCManager a = null;

        IpcCallConn() {
        }

        public void onServiceConnected(ComponentName className, IBinder service) {
            LoggerFactory.getTraceLogger().debug(Const.TAG, "IpcCallClient onServiceConnected");
            if (service == null) {
                IpcCallClient.a.rebind();
                return;
            }
            this.a = Stub.asInterface(service);
            IpcCallClient.a.setBind(true);
            LiteProcessClientManager.getAsyncHandler().post(new Runnable() {
                public void run() {
                    try {
                        LiteIpcCallManager.a().a(Util.getContext(), IpcCallConn.this.a);
                        LoggerFactory.getTraceLogger().debug(Const.TAG, "IpcCallClient init ipcManager " + IpcCallConn.this.a);
                    } catch (Exception e) {
                        LoggerFactory.getTraceLogger().error((String) Const.TAG, "IpcCallClient init error " + Log.getStackTraceString(e));
                    }
                    synchronized (IpcCallClient.class) {
                        IpcCallClient.class.notifyAll();
                    }
                }
            });
        }

        public void onServiceDisconnected(ComponentName className) {
            LoggerFactory.getTraceLogger().debug(Const.TAG, "IpcCallClient onServiceDisconnected");
            this.a = null;
            IpcCallClient.a.setBind(false);
            IpcCallClient.a.rebind();
        }
    }

    public static synchronized void prepare() {
        synchronized (IpcCallClient.class) {
            if (!Util.isLiteProcess()) {
                LoggerFactory.getTraceLogger().error((String) Const.TAG, "IpcCallClient must be in lite process. " + Log.getStackTraceString(new Throwable()));
            } else if (!b) {
                b = true;
                LoggerFactory.getTraceLogger().debug(Const.TAG, "IpcCallClient prepare");
                IpcClient ipcClient = new IpcClient(IpcCallServer.class, new IpcCallConn());
                a = ipcClient;
                ipcClient.bind();
                LoggerFactory.getTraceLogger().debug(Const.TAG, "IpcCallClient prepare finish");
            }
        }
    }

    private static void b() {
        if (a == null) {
            LoggerFactory.getTraceLogger().error((String) Const.TAG, (String) "IpcCallClient waitBindedIfNeed but ipcClient is null");
            return;
        } else if (!a.isBind()) {
            synchronized (IpcCallClient.class) {
                if (!a.isBind()) {
                    a.bind();
                    try {
                        LoggerFactory.getTraceLogger().debug(Const.TAG, "IpcCallClient begin wait bind");
                        IpcCallClient.class.wait();
                        LoggerFactory.getTraceLogger().debug(Const.TAG, "IpcCallClient end wait bind");
                    } catch (Exception e) {
                        LoggerFactory.getTraceLogger().error((String) Const.TAG, "IpcCallClient wait error " + Log.getStackTraceString(e));
                    }
                }
            }
            return;
        } else {
            return;
        }
    }

    public static <T> T getIpcProxy(Class<T> t) {
        LiteProcessClientManager.waitIfNeeded();
        b();
        IPCContextManager ipcContextManager = LiteIpcCallManager.a().c();
        if (ipcContextManager == null || ipcContextManager.getIpcCallManager() == null) {
            return null;
        }
        return ipcContextManager.getIpcCallManager().getIpcProxy(t);
    }
}
