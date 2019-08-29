package com.alipay.mobile.liteprocess.ipc;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.IBinder.DeathRecipient;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.app.BundleCompat;
import android.util.Log;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.liteprocess.Const;
import com.alipay.mobile.liteprocess.LiteProcessServerManager;
import com.alipay.mobile.liteprocess.Util;
import java.util.HashMap;

public class IpcMsgServer extends IpcService {
    /* access modifiers changed from: private */
    public static HashMap<String, Handler> a = new HashMap<>();
    private static Messenger b;

    class ReqHandler extends Handler {
        ReqHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message transMsg) {
            Bundle bundle = transMsg.getData();
            if (bundle == null) {
                LoggerFactory.getTraceLogger().debug(Const.TAG, "IpcMsgServer bundle == null");
                return;
            }
            switch (transMsg.what) {
                case 0:
                    a(bundle, transMsg.replyTo);
                    return;
                case 1:
                    b(bundle, transMsg.replyTo);
                    return;
                default:
                    return;
            }
        }

        private void a(Bundle bundle, Messenger replyTo) {
            LoggerFactory.getTraceLogger().debug(Const.TAG, "IpcMsgServer ReqHandler handleConn");
            final IBinder clientBinder = BundleCompat.getBinder(bundle, "ClientBinder");
            if (clientBinder == null || !clientBinder.isBinderAlive()) {
                LoggerFactory.getTraceLogger().debug(Const.TAG, "IpcMsgServer handleConn clientBinder = " + clientBinder);
                return;
            }
            byte[] bytes = bundle.getByteArray("IpcMsg");
            if (bytes != null) {
                final IpcMsg ipcMsg = IpcMsg.a(bytes);
                if (ipcMsg == null) {
                    LoggerFactory.getTraceLogger().debug(Const.TAG, "IpcMsgServer ipcMsg == null");
                    return;
                }
                IpcMsgServer.b(ipcMsg, replyTo);
                try {
                    LoggerFactory.getTraceLogger().debug(Const.TAG, "IpcMsgServer handleConn clientBinder linkToDeath");
                    clientBinder.linkToDeath(new DeathRecipient() {
                        public void binderDied() {
                            LoggerFactory.getTraceLogger().debug(Const.TAG, "IpcMsgServer handleConn clientBinder binderDied");
                            clientBinder.unlinkToDeath(this, 0);
                            IpcMsgServer.b(ipcMsg);
                        }
                    }, 0);
                } catch (RemoteException e) {
                    LoggerFactory.getTraceLogger().error((String) Const.TAG, "IpcMsgServer handleConn linkToDeath error " + Log.getStackTraceString(e));
                }
            }
        }

        private static void b(Bundle bundle, Messenger replyTo) {
            byte[] bytes = bundle.getByteArray("IpcMsg");
            if (bytes != null) {
                IpcMsg ipcMsg = IpcMsg.a(bytes);
                if (ipcMsg == null) {
                    LoggerFactory.getTraceLogger().debug(Const.TAG, "IpcMsgServer ipcMsg == null");
                    return;
                }
                Handler bizHandler = (Handler) IpcMsgServer.a.get(ipcMsg.e);
                if (bizHandler == null) {
                    LoggerFactory.getTraceLogger().debug(Const.TAG, "IpcMsgServer bizHandler == null biz = " + ipcMsg.e);
                    return;
                }
                Message bizMsg = ipcMsg.f;
                bizMsg.replyTo = replyTo;
                bizHandler.sendMessage(bizMsg);
            }
        }
    }

    public void onCreate() {
        super.onCreate();
        LoggerFactory.getTraceLogger().debug(Const.TAG, "IpcMsgServer onCreate");
        Util.setContext(getApplicationContext());
        if (!Util.isMainProcess()) {
            LoggerFactory.getTraceLogger().error((String) Const.TAG, "IpcMsgServer must be in main process. " + Log.getStackTraceString(new Throwable()));
        } else {
            b();
        }
    }

    private static synchronized void b() {
        synchronized (IpcMsgServer.class) {
            if (b == null) {
                HandlerThread handlerThread = new HandlerThread("IpcMsgServer");
                handlerThread.start();
                b = new Messenger(new ReqHandler(handlerThread.getLooper()));
            }
        }
    }

    public IBinder onBind(Intent intent) {
        LoggerFactory.getTraceLogger().debug(Const.TAG, "IpcMsgServer onBind");
        if (b != null) {
            return b.getBinder();
        }
        return null;
    }

    public static void registerReqBizHandler(String biz, Handler bizHandler) {
        LoggerFactory.getTraceLogger().debug(Const.TAG, "IpcMsgServer registerBizHandler biz = " + biz);
        a.put(biz, bizHandler);
    }

    public static void unregisterReqBizHandler(String biz) {
        LoggerFactory.getTraceLogger().debug(Const.TAG, "IpcMsgServer unregisterBizHandler");
        a.remove(biz);
    }

    public static void reply(Messenger replyTo, String biz, Message bizMsg) {
        if (replyTo != null) {
            IpcMsg ipcMsg = new IpcMsg();
            ipcMsg.e = biz;
            ipcMsg.f = bizMsg;
            Bundle bundle = new Bundle();
            bundle.putByteArray("IpcMsg", IpcMsg.a(ipcMsg));
            Message transMsg = Message.obtain();
            transMsg.what = 1;
            transMsg.setData(bundle);
            try {
                replyTo.send(transMsg);
            } catch (RemoteException e) {
                LoggerFactory.getTraceLogger().error((String) Const.TAG, "IpcMsgServer send error " + Log.getStackTraceString(e));
            }
        }
    }

    /* access modifiers changed from: private */
    public static synchronized void b(IpcMsg ipcMsg, Messenger replyTo) {
        synchronized (IpcMsgServer.class) {
            LoggerFactory.getTraceLogger().debug(Const.TAG, "IpcMsgServer addClient " + ipcMsg.b);
            LiteProcessServerManager.g().onProcessAdd(ipcMsg.a, ipcMsg.b, ipcMsg.c, ipcMsg.d, replyTo);
        }
    }

    /* access modifiers changed from: private */
    public static synchronized void b(IpcMsg ipcMsg) {
        synchronized (IpcMsgServer.class) {
            LoggerFactory.getTraceLogger().debug(Const.TAG, "IpcMsgServer removeClient " + ipcMsg.b);
            LiteProcessServerManager.g().onProcessRemove(ipcMsg.a, ipcMsg.b);
        }
    }
}
