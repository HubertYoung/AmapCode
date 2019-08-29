package com.alipay.mobile.liteprocess.ipc;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Process;
import android.support.v4.app.BundleCompat;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.liteprocess.Const;
import com.alipay.mobile.liteprocess.LiteProcessClientManager;
import com.alipay.mobile.liteprocess.Util;
import java.util.HashMap;

public class IpcMsgClient {
    /* access modifiers changed from: private */
    public static IpcClient a;
    /* access modifiers changed from: private */
    public static Messenger b = null;
    /* access modifiers changed from: private */
    public static Messenger c = null;
    /* access modifiers changed from: private */
    public static HashMap<String, Handler> d = new HashMap<>();
    private static Handler e;
    /* access modifiers changed from: private */
    public static MsgerHandler f;
    private static boolean g = false;

    class IpcConn implements ServiceConnection {
        IpcConn() {
        }

        public void onServiceConnected(ComponentName className, IBinder service) {
            LoggerFactory.getTraceLogger().debug(Const.TAG, "IpcMsgClient onServiceConnected");
            if (service == null) {
                IpcMsgClient.a.rebind();
                return;
            }
            IpcMsgClient.b = new Messenger(service);
            IpcMsgClient.c = new Messenger(IpcMsgClient.f);
            IpcMsgClient.a.setBind(true);
            IpcMsg ipcMsg = new IpcMsg();
            ipcMsg.a = Process.myPid();
            ipcMsg.b = Util.getLpid();
            ipcMsg.d = IpcMsgClient.getClientId();
            String appId = Util.getSp().getString("TARGETAPPID", "");
            if (!TextUtils.isEmpty(appId)) {
                ipcMsg.c = appId;
                LoggerFactory.getTraceLogger().debug(Const.TAG, "ipcMsg.appId = " + ipcMsg.c);
            }
            Bundle bundle = new Bundle();
            try {
                BundleCompat.putBinder(bundle, "ClientBinder", IpcMsgClient.c.getBinder());
                bundle.putByteArray("IpcMsg", IpcMsg.a(ipcMsg));
                Message transMsg = new Message();
                transMsg.what = 0;
                transMsg.replyTo = IpcMsgClient.c;
                transMsg.setData(bundle);
                IpcMsgClient.b.send(transMsg);
            } catch (Throwable e) {
                LoggerFactory.getTraceLogger().error((String) Const.TAG, "IpcMsgClient conn send error " + Log.getStackTraceString(e));
            }
            synchronized (IpcMsgClient.class) {
                IpcMsgClient.class.notifyAll();
            }
        }

        public void onServiceDisconnected(ComponentName className) {
            try {
                LoggerFactory.getTraceLogger().debug(Const.TAG, "IpcMsgClient onServiceDisconnected");
                IpcMsgClient.b = null;
                IpcMsgClient.c = null;
                IpcMsgClient.a.setBind(false);
                IpcMsgClient.a.rebind();
            } catch (Throwable t) {
                LoggerFactory.getTraceLogger().error((String) Const.TAG, Log.getStackTraceString(t));
            }
        }
    }

    class MsgerHandler extends Handler {
        MsgerHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message transMsg) {
            Bundle bundle = transMsg.getData();
            if (bundle == null) {
                LoggerFactory.getTraceLogger().debug(Const.TAG, "IpcMsgClient bundle == null");
                return;
            }
            switch (transMsg.what) {
                case 1:
                    a(bundle);
                    return;
                default:
                    return;
            }
        }

        private static void a(Bundle bundle) {
            byte[] bytes = bundle.getByteArray("IpcMsg");
            if (bytes != null) {
                IpcMsg ipcMsg = IpcMsg.a(bytes);
                if (ipcMsg == null) {
                    LoggerFactory.getTraceLogger().debug(Const.TAG, "IpcMsgClient ipcMsg == null");
                    return;
                }
                Handler bizHandler = (Handler) IpcMsgClient.d.get(ipcMsg.e);
                if (bizHandler == null) {
                    LoggerFactory.getTraceLogger().debug(Const.TAG, "IpcMsgClient bizHandler == null");
                } else {
                    bizHandler.sendMessage(ipcMsg.f);
                }
            }
        }
    }

    public static synchronized void prepare() {
        synchronized (IpcMsgClient.class) {
            if (!Util.isLiteProcess()) {
                LoggerFactory.getTraceLogger().error((String) Const.TAG, "IpcMsgClient must be in lite process. " + Log.getStackTraceString(new Throwable()));
            } else if (!g) {
                g = true;
                LoggerFactory.getTraceLogger().debug(Const.TAG, "IpcMsgClient prepare");
                HandlerThread processThread = new HandlerThread("IpcMsgClientProcess: " + getClientId());
                processThread.start();
                e = new Handler(processThread.getLooper());
                HandlerThread msgerThread = new HandlerThread("IpcMsgClientMsger: " + getClientId());
                msgerThread.start();
                f = new MsgerHandler(msgerThread.getLooper());
                IpcClient ipcClient = new IpcClient(IpcMsgServer.class, new IpcConn());
                a = ipcClient;
                ipcClient.bind();
                LoggerFactory.getTraceLogger().debug(Const.TAG, "IpcMsgClient prepare finish");
            }
        }
    }

    public static void registerRspBizHandler(String biz, Handler bizHandler) {
        LoggerFactory.getTraceLogger().debug(Const.TAG, "IpcMsgClient registerBizHandler");
        d.put(biz, bizHandler);
    }

    public static void unregisterRspBizHandler(String biz) {
        LoggerFactory.getTraceLogger().debug(Const.TAG, "IpcMsgClient unregisterBizHandler");
        d.remove(biz);
    }

    public static void send(final String biz, final Message bizMsg) {
        if (!TextUtils.isEmpty(biz) && bizMsg != null && e != null) {
            e.post(new Runnable() {
                public final void run() {
                    LiteProcessClientManager.waitIfNeeded();
                    IpcMsgClient.g();
                    IpcMsg ipcMsg = new IpcMsg();
                    ipcMsg.a = Process.myPid();
                    ipcMsg.b = Util.getLpid();
                    ipcMsg.d = IpcMsgClient.getClientId();
                    ipcMsg.e = biz;
                    ipcMsg.f = bizMsg;
                    Bundle bundle = new Bundle();
                    try {
                        bundle.putByteArray("IpcMsg", IpcMsg.a(ipcMsg));
                        Message transMsg = Message.obtain();
                        transMsg.what = 1;
                        transMsg.replyTo = IpcMsgClient.c;
                        transMsg.setData(bundle);
                        IpcMsgClient.b.send(transMsg);
                    } catch (Exception e) {
                        LoggerFactory.getTraceLogger().error((String) Const.TAG, "IpcMsgClient send error " + Log.getStackTraceString(e));
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public static void g() {
        if (a == null) {
            LoggerFactory.getTraceLogger().error((String) Const.TAG, (String) "IpcMsgClient waitBindedIfNeed but ipcClient is null");
            return;
        } else if (!a.isBind()) {
            synchronized (IpcMsgClient.class) {
                if (!a.isBind()) {
                    a.bind();
                    try {
                        LoggerFactory.getTraceLogger().debug(Const.TAG, "IpcMsgClient begin wait bind");
                        IpcMsgClient.class.wait();
                        LoggerFactory.getTraceLogger().debug(Const.TAG, "IpcMsgClient end wait bind");
                    } catch (Exception e2) {
                        LoggerFactory.getTraceLogger().error((String) Const.TAG, "IpcMsgClient wait error " + Log.getStackTraceString(e2));
                    }
                }
            }
            return;
        } else {
            return;
        }
    }

    public static String getClientId() {
        return Util.getCurrentProcessName();
    }
}
