package com.alipay.mobile.common.transportext.biz.mmtp.amnetadapt;

import com.alipay.mobile.common.amnet.api.AmnetBeanFactory;
import com.alipay.mobile.common.amnet.api.AmnetManager;
import com.alipay.mobile.common.amnet.api.model.AmnetPost;
import com.alipay.mobile.common.amnet.api.model.ResultFeedback;
import com.alipay.mobile.common.amnet.ipcapi.pushproc.AmnetClientService;
import com.alipay.mobile.common.ipc.api.IPCApiFactory;
import com.alipay.mobile.common.transport.context.TransportContext;
import com.alipay.mobile.common.transport.ext.MMTPException;
import com.alipay.mobile.common.transport.monitor.RPCDataItems;
import com.alipay.mobile.common.transport.strategy.ExtTransportTunnelWatchdog;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.NetworkAsyncTaskExecutor;
import com.alipay.mobile.common.transport.utils.TransportContextThreadLocalUtils;
import com.alipay.mobile.common.transportext.biz.mmtp.BindEventListenerManger;
import com.alipay.mobile.common.transportext.biz.mmtp.amnetadapt.foreign.GeneralEventListenServiceImpl;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class AmnetHelper {
    private static AmnetManager AMNET_MANAGER = null;
    private static final int MAX_RETRY_TIMES = 15;
    private static boolean MMTP_INITED = false;
    private static final String TAG = "AmnetHelper";
    private static ThreadPoolExecutor asyncPostExector;

    class AmnetPostRunnable implements Runnable {
        AmnetPost amnetPost;

        public AmnetPostRunnable(AmnetPost amnetPost2) {
            this.amnetPost = amnetPost2;
        }

        public void run() {
            try {
                AmnetHelper.post(this.amnetPost);
            } catch (Throwable e) {
                LogCatUtil.warn((String) AmnetHelper.TAG, "post fail. " + e.toString());
            }
        }
    }

    public static final AmnetManager getAmnetManager() {
        if (AMNET_MANAGER != null) {
            return AMNET_MANAGER;
        }
        AmnetManager amnetManager = AmnetBeanFactory.getAmnetManager();
        AMNET_MANAGER = amnetManager;
        return amnetManager;
    }

    public static final void asyncPost(AmnetPost amnetPost) {
        getAsyncPostExector().execute(new AmnetPostRunnable(amnetPost));
    }

    public static final void post(AmnetPost amnetPost) {
        try {
            waitToBind();
            initMmtp();
            amnetPost.secret = true;
            amnetPost.ts = System.nanoTime();
            amnetPost.ipcM2p = System.currentTimeMillis();
            addIpcLog(amnetPost, ((AmnetClientService) IPCApiFactory.getSingletonIPCContextManager().getIpcCallManager().getIpcProxy(AmnetClientService.class)).postWithResult(amnetPost));
        } catch (MMTPException e) {
            throw e;
        } catch (Exception e2) {
            LogCatUtil.error((String) TAG, (Throwable) e2);
        }
    }

    private static void addIpcLog(AmnetPost amnetPost, Map<String, String> result) {
        if (result != null && amnetPost.channel == 1) {
            LogCatUtil.debug(TAG, "RPCID:" + amnetPost.reqSeqId + ",IPC_TIME1:" + result.get(RPCDataItems.IPC_TIME1));
            TransportContext transportContext = TransportContextThreadLocalUtils.getValue();
            if (transportContext != null) {
                transportContext.getCurrentDataContainer().putDataItem(RPCDataItems.IPC_TIME1, result.get(RPCDataItems.IPC_TIME1));
                result.clear();
            }
        }
    }

    private static void waitToBind() {
        BindEventListenerManger bindEventListen = BindEventListenerManger.getInstance();
        bindEventListen.waitToBinded();
        if (!bindEventListen.isBinded() || !isAmnetActivite()) {
            int retryCount = 0;
            while (true) {
                if (retryCount >= 15) {
                    break;
                }
                Thread.sleep(1000);
                if (bindEventListen.isBinded() && isAmnetActivite()) {
                    LogCatUtil.debug(TAG, "Service is binded and Amnet is activated after:" + (retryCount * 1000) + " secs;Bind status:" + bindEventListen.isBinded() + " AMNET active:" + isAmnetActivite());
                    break;
                } else {
                    LogCatUtil.verbose(TAG, "waiting for bindservice and amnet to be ready... [" + retryCount + "]; Bind status:" + bindEventListen.isBinded() + " AMNET active:" + isAmnetActivite());
                    retryCount++;
                }
            }
            if (retryCount == 15) {
                LogCatUtil.debug(TAG, "Bind status:" + bindEventListen.isBinded() + " AMNET active:" + isAmnetActivite());
                LogCatUtil.debug(TAG, "AMNET is not activated while post, will downgrade the Ext-tunnel");
                ExtTransportTunnelWatchdog.getInstance().startTunnelDowngrade(ExtTransportTunnelWatchdog.DOWNGRADE_REASON_AMNETPOST);
                throw new MMTPException(1003, (String) "AMNET fails to active, will switch to SPDY");
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0015, code lost:
        r0 = ((com.alipay.mobile.common.transportext.biz.shared.ExtTransportManagerImpl) com.alipay.mobile.common.transport.ext.ExtTransportOffice.getInstance().getExtTransportManager()).getMMTPTransportManager();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0023, code lost:
        if (r0 == null) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0029, code lost:
        if (r0.isInited() != false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x002b, code lost:
        r0.init(com.alipay.mobile.common.transportext.biz.shared.ExtTransportEnv.getAppContext());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected static void initMmtp() {
        /*
            boolean r1 = MMTP_INITED
            if (r1 == 0) goto L_0x0005
        L_0x0004:
            return
        L_0x0005:
            java.lang.Class<com.alipay.mobile.common.transportext.biz.mmtp.amnetadapt.AmnetHelper> r2 = com.alipay.mobile.common.transportext.biz.mmtp.amnetadapt.AmnetHelper.class
            monitor-enter(r2)
            boolean r1 = MMTP_INITED     // Catch:{ all -> 0x000e }
            if (r1 == 0) goto L_0x0011
            monitor-exit(r2)     // Catch:{ all -> 0x000e }
            goto L_0x0004
        L_0x000e:
            r1 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x000e }
            throw r1
        L_0x0011:
            r1 = 1
            MMTP_INITED = r1     // Catch:{ all -> 0x000e }
            monitor-exit(r2)     // Catch:{ all -> 0x000e }
            com.alipay.mobile.common.transport.ext.ExtTransportOffice r1 = com.alipay.mobile.common.transport.ext.ExtTransportOffice.getInstance()
            java.lang.Object r1 = r1.getExtTransportManager()
            com.alipay.mobile.common.transportext.biz.shared.ExtTransportManagerImpl r1 = (com.alipay.mobile.common.transportext.biz.shared.ExtTransportManagerImpl) r1
            com.alipay.mobile.common.transportext.api.ExtTransportManager r0 = r1.getMMTPTransportManager()
            if (r0 == 0) goto L_0x0004
            boolean r1 = r0.isInited()
            if (r1 != 0) goto L_0x0004
            android.content.Context r1 = com.alipay.mobile.common.transportext.biz.shared.ExtTransportEnv.getAppContext()
            r0.init(r1)
            goto L_0x0004
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.transportext.biz.mmtp.amnetadapt.AmnetHelper.initMmtp():void");
    }

    public static final int getConnState() {
        try {
            BindEventListenerManger.getInstance().waitToBinded();
            return ((AmnetClientService) IPCApiFactory.getSingletonIPCContextManager().getIpcCallManager().getIpcProxy(AmnetClientService.class)).getConnState();
        } catch (Exception e) {
            LogCatUtil.error((String) TAG, (Throwable) e);
            return -1;
        }
    }

    public static final boolean isAmnetActivite() {
        GeneralEventListenServiceImpl generalEventListenServiceImpl = GeneralEventListenServiceImpl.getInstance();
        if (BindEventListenerManger.getInstance().isBinded() && generalEventListenServiceImpl.isAmnetActived()) {
            return true;
        }
        try {
            AmnetClientService amnetClientService = (AmnetClientService) IPCApiFactory.getSingletonIPCContextManager().getIpcCallManager().getIpcProxy(AmnetClientService.class);
            if (!BindEventListenerManger.getInstance().isBinded() || !amnetClientService.isAmnetActiveted()) {
                LogCatUtil.debug(TAG, "isAmnetActivite return false");
                generalEventListenServiceImpl.setAmnetLifeState(1);
                return false;
            }
            generalEventListenServiceImpl.setAmnetLifeState(2);
            return true;
        } catch (Exception e) {
            LogCatUtil.error((String) TAG, (Throwable) e);
            return false;
        }
    }

    public static void asyncNotifyResultFeedback(final ResultFeedback resultFeedback) {
        NetworkAsyncTaskExecutor.execute(new Runnable() {
            public final void run() {
                AmnetHelper.notifyResultFeedback(resultFeedback);
            }
        });
    }

    public static void notifyResultFeedback(ResultFeedback resultFeedback) {
        try {
            BindEventListenerManger.getInstance().waitToBinded();
            ((AmnetClientService) IPCApiFactory.getSingletonIPCContextManager().getIpcCallManager().getIpcProxy(AmnetClientService.class)).notifyResultFeedback(resultFeedback);
        } catch (Exception e) {
            LogCatUtil.error((String) TAG, (Throwable) e);
        }
    }

    private static ThreadPoolExecutor getAsyncPostExector() {
        if (asyncPostExector != null) {
            return asyncPostExector;
        }
        synchronized (AmnetHelper.class) {
            if (asyncPostExector != null) {
                ThreadPoolExecutor threadPoolExecutor = asyncPostExector;
                return threadPoolExecutor;
            }
            asyncPostExector = new ThreadPoolExecutor(1, 1, 2, TimeUnit.SECONDS, new LinkedBlockingQueue());
            try {
                asyncPostExector.allowCoreThreadTimeOut(true);
            } catch (Throwable e) {
                LogCatUtil.warn(TAG, "allowCoreThreadTimeOut fail", e);
            }
            return asyncPostExector;
        }
    }
}
