package com.alipay.mobile.common.transportext.biz.mmtp;

import com.alipay.mobile.common.ipc.api.push.BindEventListener;
import com.alipay.mobile.common.ipc.api.push.BindPushServiceManager.BindPushServiceFactory;
import com.alipay.mobile.common.transport.strategy.ExtTransportTunnelWatchdog;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transportext.biz.appevent.AmnetEventNotify;

public class BindEventListenerManger implements BindEventListener {
    private static BindEventListenerManger bindEventListenerManger;
    private Object bindLock;
    private boolean binded;
    private Object unbindLonk;

    public static final BindEventListenerManger getInstance() {
        if (bindEventListenerManger == null) {
            synchronized (BindEventListenerManger.class) {
                try {
                    if (bindEventListenerManger == null) {
                        bindEventListenerManger = new BindEventListenerManger();
                    }
                }
            }
        }
        return bindEventListenerManger;
    }

    private BindEventListenerManger() {
        this.binded = false;
        this.bindLock = new Object();
        this.unbindLonk = new Object();
        this.binded = BindPushServiceFactory.getInstance().isBindedService();
    }

    public void binded() {
        this.binded = true;
        synchronized (this.bindLock) {
            try {
                this.bindLock.notifyAll();
            } catch (Exception e) {
                LogCatUtil.error((String) "BindEventListenerManger", (Throwable) e);
            }
        }
        AmnetEventNotify.onSyncConnState();
    }

    public void unBinde() {
        this.binded = false;
        synchronized (this.unbindLonk) {
            try {
                this.unbindLonk.notifyAll();
            } catch (Exception e) {
                LogCatUtil.error((String) "BindEventListenerManger", (Throwable) e);
            }
        }
        LogCatUtil.debug("EXT_Watchdog", "Unbind event captured, ticking watchdog");
        ExtTransportTunnelWatchdog.getInstance().bindFailureTick();
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void waitToBinded() {
        /*
            r5 = this;
            boolean r1 = r5.binded
            if (r1 != 0) goto L_0x000f
            java.lang.Object r2 = r5.bindLock
            monitor-enter(r2)
            java.lang.Object r1 = r5.bindLock     // Catch:{ InterruptedException -> 0x0010 }
            r3 = 3000(0xbb8, double:1.482E-320)
            r1.wait(r3)     // Catch:{ InterruptedException -> 0x0010 }
        L_0x000e:
            monitor-exit(r2)     // Catch:{ all -> 0x0017 }
        L_0x000f:
            return
        L_0x0010:
            r0 = move-exception
            java.lang.String r1 = "BindEventListenerManger"
            com.alipay.mobile.common.transport.utils.LogCatUtil.error(r1, r0)     // Catch:{ all -> 0x0017 }
            goto L_0x000e
        L_0x0017:
            r1 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0017 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.transportext.biz.mmtp.BindEventListenerManger.waitToBinded():void");
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void waitToUnBinded() {
        /*
            r5 = this;
            boolean r1 = r5.binded
            if (r1 == 0) goto L_0x000f
            java.lang.Object r2 = r5.unbindLonk
            monitor-enter(r2)
            java.lang.Object r1 = r5.unbindLonk     // Catch:{ InterruptedException -> 0x0010 }
            r3 = 3000(0xbb8, double:1.482E-320)
            r1.wait(r3)     // Catch:{ InterruptedException -> 0x0010 }
        L_0x000e:
            monitor-exit(r2)     // Catch:{ all -> 0x0017 }
        L_0x000f:
            return
        L_0x0010:
            r0 = move-exception
            java.lang.String r1 = "BindEventListenerManger"
            com.alipay.mobile.common.transport.utils.LogCatUtil.error(r1, r0)     // Catch:{ all -> 0x0017 }
            goto L_0x000e
        L_0x0017:
            r1 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0017 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.transportext.biz.mmtp.BindEventListenerManger.waitToUnBinded():void");
    }

    public boolean isBinded() {
        return this.binded;
    }
}
