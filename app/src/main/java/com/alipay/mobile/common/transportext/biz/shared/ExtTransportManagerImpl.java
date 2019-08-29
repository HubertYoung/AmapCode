package com.alipay.mobile.common.transportext.biz.shared;

import android.content.Context;
import com.alipay.mobile.common.ipc.api.IPCApiFactory;
import com.alipay.mobile.common.ipc.api.ServiceBeanManager;
import com.alipay.mobile.common.ipc.api.push.BindPushServiceManager;
import com.alipay.mobile.common.ipc.api.push.BindPushServiceManager.BindPushServiceFactory;
import com.alipay.mobile.common.transport.context.TransportContext;
import com.alipay.mobile.common.transport.ext.ExtTransportClient;
import com.alipay.mobile.common.transport.ext.MainProcConfigListenService;
import com.alipay.mobile.common.transport.httpdns.ipc.MainProcReloadDnsService;
import com.alipay.mobile.common.transport.strategy.ExtTransportTunnelWatchdog;
import com.alipay.mobile.common.transport.strategy.NetworkTunnelChangedListener;
import com.alipay.mobile.common.transport.strategy.NetworkTunnelStrategy;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.transport.utils.NetBeanFactory;
import com.alipay.mobile.common.transport.utils.NwSharedSwitchUtil;
import com.alipay.mobile.common.transportext.api.ExtTransportManager;
import com.alipay.mobile.common.transportext.biz.diagnose.network.NetworkCheck;
import com.alipay.mobile.common.transportext.biz.httpdns.MainProcReloadDnsServiceImpl;
import com.alipay.mobile.common.transportext.biz.mmtp.BindEventListenerManger;
import com.alipay.mobile.common.transportext.biz.mmtp.MMTPTransportManager;
import com.alipay.mobile.common.transportext.biz.mmtp.MainProcNetInfoReceiver;
import com.alipay.mobile.common.transportext.biz.mmtp.amnetadapt.AmnetHelper;
import com.alipay.mobile.common.transportext.biz.shared.config.MainProcConfigListenServiceImpl;
import com.alipay.mobile.common.transportext.biz.shared.config.SharedSwitchChangedListener;
import com.alipay.mobile.common.transportext.biz.shared.spdy.SpdyAvalibleObservable;
import com.alipay.mobile.common.transportext.biz.spdy.SpdyTransportManager;
import com.alipay.mobile.common.transportext.biz.sync.SyncSpdyAvalibleListener;
import com.alipay.mobile.common.transportext.biz.sync.SyncTunnelChgListener;

public class ExtTransportManagerImpl extends ExtTransportManagerAdapter {
    private static final String TAG = "ExtTransportManagerImpl";
    private volatile boolean awaitAmnetActivation = true;
    private boolean inited = false;
    private ExtTransportManager mAmnetRpcManager;
    private ExtTransAppVisibleReceiver mAppVisibleReceiver;
    private Context mContext;
    private ExtTransportManager mSpdyRpcManager;

    public void init(Context context) {
        if (!this.inited) {
            this.inited = true;
            this.mContext = context;
            ExtTransportEnv.setAppContext(this.mContext);
            initMainProcModule(context);
            NwSharedSwitchUtil.addSwitchChangedListener(new SharedSwitchChangedListener());
            initExtTransAppVisibleReceiver();
            SpdyAvalibleObservable.getInstance().addSpdyAvalibleListener(new SyncSpdyAvalibleListener());
            NetworkCheck.initNetworkCheck(this.mContext);
        }
    }

    private void initMainProcModule(Context context) {
        if (MiscUtils.isOtherProcess(context)) {
            LogCatUtil.info(TAG, "init.  Other process don't first init transportManager");
            return;
        }
        initTransportManager(context);
        regProcBindedEvent();
        regTunnelChgEvents();
        setIPCRetryHandler();
        regCommonServiceBeans();
        bindService();
        MainProcNetInfoReceiver.getInstance(this.mContext).register();
    }

    /* access modifiers changed from: protected */
    public void initTransportManager(Context context) {
        if (MiscUtils.isOtherProcess(context)) {
            LogCatUtil.info(TAG, "init.  Other process don't first init transportManager");
            return;
        }
        getMMTPTransportManager().init(context);
        if (!NetworkTunnelStrategy.getInstance().isCanUseAmnet() && NetworkTunnelStrategy.getInstance().isCanUseSpdy()) {
            getSpdyTransportManager().init(context);
        }
    }

    public ExtTransportClient getExtTransportClient(Context context, TransportContext transportContext) {
        init(context);
        ExtTransportStrategy.configInit(context, transportContext);
        if (transportContext.currentReqInfo == null || !transportContext.currentReqInfo.use) {
            if (MiscUtils.isDebugger(context)) {
                LogCatUtil.debug(TAG, "getExtTransportClient.   transportContext.currentReqInfo == null || transportContext.currentReqInfo.use == false. return null.");
            }
            return null;
        }
        ExtTransportClient extTransportClient = obtainExtTransportClient(context, transportContext);
        fillCurrentReqInfo(transportContext, extTransportClient);
        return extTransportClient;
    }

    /* access modifiers changed from: protected */
    public void fillCurrentReqInfo(TransportContext transportContext, ExtTransportClient extTransportClient) {
        if (extTransportClient != null) {
            if (extTransportClient.getModuleCategory() == 0) {
                transportContext.currentReqInfo.protocol = ExtTransportStrategy.EXT_PROTO_SPDY;
            } else {
                transportContext.currentReqInfo.protocol = ExtTransportStrategy.EXT_PROTO_MRPC;
            }
        }
    }

    private ExtTransportClient obtainExtTransportClient(Context context, TransportContext transportContext) {
        if (2 != transportContext.choseExtLinkType) {
            ExtTransportManager extRpcManager = getExtRpcManager();
            if (extRpcManager != null) {
                return extRpcManager.getExtTransportClient(context, transportContext);
            }
            return null;
        } else if (NetworkTunnelStrategy.getInstance().isCanUseSpdy()) {
            return getSpdyTransportManager().getExtTransportClient(context, transportContext);
        } else {
            return null;
        }
    }

    public ExtTransportManager getMMTPTransportManager() {
        if (this.mAmnetRpcManager == null) {
            this.mAmnetRpcManager = new MMTPTransportManager();
        }
        return this.mAmnetRpcManager;
    }

    public ExtTransportManager getSpdyTransportManager() {
        if (this.mSpdyRpcManager == null) {
            this.mSpdyRpcManager = new SpdyTransportManager();
        }
        return this.mSpdyRpcManager;
    }

    private ExtTransportManager getExtRpcManager() {
        if (NetworkTunnelStrategy.getInstance().isCanUseAmnet()) {
            awaitAmnetActivation();
            if (AmnetHelper.isAmnetActivite()) {
                ExtTransportTunnelWatchdog.getInstance().mrpcFailureRest();
                return getMMTPTransportManager();
            }
            LogCatUtil.info(TAG, "isAmnetActivite==false");
            ExtTransportTunnelWatchdog.getInstance().mrpcFailureTick();
        }
        if (NetworkTunnelStrategy.getInstance().isCanUseSpdy()) {
            return getSpdyTransportManager();
        }
        return null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:62:0x00c1, code lost:
        r6.awaitAmnetActivation = false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void awaitAmnetActivation() {
        /*
            r6 = this;
            r5 = 0
            android.content.Context r2 = r6.mContext     // Catch:{ Throwable -> 0x007f }
            boolean r2 = com.alipay.mobile.common.transport.utils.MiscUtils.isOtherProcess(r2)     // Catch:{ Throwable -> 0x007f }
            if (r2 == 0) goto L_0x0013
            java.lang.String r2 = "ExtTransportManagerImpl"
            java.lang.String r3 = "Other process don't awaitAmnetActivation"
            com.alipay.mobile.common.transport.utils.LogCatUtil.warn(r2, r3)     // Catch:{ Throwable -> 0x007f }
            r6.awaitAmnetActivation = r5
        L_0x0012:
            return
        L_0x0013:
            boolean r2 = com.alipay.mobile.common.transportext.biz.mmtp.amnetadapt.AmnetHelper.isAmnetActivite()     // Catch:{ Throwable -> 0x007f }
            if (r2 == 0) goto L_0x001c
            r6.awaitAmnetActivation = r5
            goto L_0x0012
        L_0x001c:
            boolean r2 = r6.awaitAmnetActivation     // Catch:{ Throwable -> 0x007f }
            if (r2 != 0) goto L_0x0023
            r6.awaitAmnetActivation = r5
            goto L_0x0012
        L_0x0023:
            monitor-enter(r6)     // Catch:{ Throwable -> 0x007f }
            java.lang.String r2 = "ExtTransportManagerImpl"
            java.lang.String r3 = " start awaitAmnetActivation "
            com.alipay.mobile.common.transport.utils.LogCatUtil.info(r2, r3)     // Catch:{ all -> 0x007c }
            boolean r2 = r6.awaitAmnetActivation     // Catch:{ all -> 0x007c }
            if (r2 != 0) goto L_0x0033
            monitor-exit(r6)     // Catch:{ all -> 0x007c }
            r6.awaitAmnetActivation = r5
            goto L_0x0012
        L_0x0033:
            boolean r2 = com.alipay.mobile.common.transportext.biz.mmtp.amnetadapt.AmnetHelper.isAmnetActivite()     // Catch:{ all -> 0x007c }
            if (r2 == 0) goto L_0x003d
            monitor-exit(r6)     // Catch:{ all -> 0x007c }
            r6.awaitAmnetActivation = r5
            goto L_0x0012
        L_0x003d:
            r1 = 0
        L_0x003e:
            boolean r2 = r6.awaitAmnetActivation     // Catch:{ all -> 0x007c }
            if (r2 == 0) goto L_0x00bd
            boolean r2 = com.alipay.mobile.common.transportext.biz.mmtp.amnetadapt.AmnetHelper.isAmnetActivite()     // Catch:{ all -> 0x007c }
            if (r2 != 0) goto L_0x00bd
            r2 = 10
            if (r1 >= r2) goto L_0x00bd
            java.lang.String r2 = "ExtTransportManagerImpl"
            java.lang.String r3 = "isAmnetActivite==false await..."
            com.alipay.mobile.common.transport.utils.LogCatUtil.info(r2, r3)     // Catch:{ Throwable -> 0x0062 }
            com.alipay.mobile.common.transport.strategy.ExtTransportTunnelWatchdog r2 = com.alipay.mobile.common.transport.strategy.ExtTransportTunnelWatchdog.getInstance()     // Catch:{ Throwable -> 0x0062 }
            r2.mrpcFailureTick()     // Catch:{ Throwable -> 0x0062 }
        L_0x005a:
            r2 = 1000(0x3e8, double:4.94E-321)
            java.lang.Thread.sleep(r2)     // Catch:{ Throwable -> 0x00a3 }
        L_0x005f:
            int r1 = r1 + 1
            goto L_0x003e
        L_0x0062:
            r0 = move-exception
            java.lang.String r2 = "ExtTransportManagerImpl"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x007c }
            java.lang.String r4 = "awaitAmnetActivation mrpcFailureTick exception:"
            r3.<init>(r4)     // Catch:{ all -> 0x007c }
            java.lang.String r4 = r0.toString()     // Catch:{ all -> 0x007c }
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x007c }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x007c }
            com.alipay.mobile.common.transport.utils.LogCatUtil.warn(r2, r3)     // Catch:{ all -> 0x007c }
            goto L_0x005a
        L_0x007c:
            r2 = move-exception
            monitor-exit(r6)     // Catch:{ all -> 0x007c }
            throw r2     // Catch:{ Throwable -> 0x007f }
        L_0x007f:
            r0 = move-exception
            java.lang.String r2 = "ExtTransportManagerImpl"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x00c4 }
            java.lang.String r4 = "awaitAmnetActivation exception"
            r3.<init>(r4)     // Catch:{ all -> 0x00c4 }
            java.lang.String r4 = r0.toString()     // Catch:{ all -> 0x00c4 }
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x00c4 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x00c4 }
            com.alipay.mobile.common.transport.utils.LogCatUtil.warn(r2, r3)     // Catch:{ all -> 0x00c4 }
            r6.awaitAmnetActivation = r5
        L_0x009a:
            java.lang.String r2 = "ExtTransportManagerImpl"
            java.lang.String r3 = " awaitAmnetActivation finish "
            com.alipay.mobile.common.transport.utils.LogCatUtil.info(r2, r3)
            goto L_0x0012
        L_0x00a3:
            r0 = move-exception
            java.lang.String r2 = "ExtTransportManagerImpl"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x007c }
            java.lang.String r4 = "awaitAmnetActivation sleep exception:"
            r3.<init>(r4)     // Catch:{ all -> 0x007c }
            java.lang.String r4 = r0.toString()     // Catch:{ all -> 0x007c }
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x007c }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x007c }
            com.alipay.mobile.common.transport.utils.LogCatUtil.warn(r2, r3)     // Catch:{ all -> 0x007c }
            goto L_0x005f
        L_0x00bd:
            r2 = 0
            r6.awaitAmnetActivation = r2     // Catch:{ all -> 0x007c }
            monitor-exit(r6)     // Catch:{ all -> 0x007c }
            r6.awaitAmnetActivation = r5
            goto L_0x009a
        L_0x00c4:
            r2 = move-exception
            r6.awaitAmnetActivation = r5
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.transportext.biz.shared.ExtTransportManagerImpl.awaitAmnetActivation():void");
    }

    public boolean isInited() {
        return this.inited;
    }

    private void setIPCRetryHandler() {
        if (MiscUtils.isOtherProcess(this.mContext)) {
            LogCatUtil.info(TAG, "init.  Other process don't setIPCRetryHandler");
            return;
        }
        try {
            IPCApiFactory.getSingletonIPCContextManager().getIpcCallManager().setIPCCallRetryHandler(new ExtTransIPCRetryHandler());
            IPCApiFactory.getSingletonIPCContextManager().getLocalCallManager().setLocalCallRetryHandler(new ExtTransLocalCallRetryHandler());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void regTunnelChgEvents() {
        NetworkTunnelStrategy.getInstance().addNetworkTunnelChangedListener((NetworkTunnelChangedListener) NetBeanFactory.getBean(ExtTunnelChgListener.class));
        NetworkTunnelStrategy.getInstance().addNetworkTunnelChangedListener((NetworkTunnelChangedListener) NetBeanFactory.getBean(SyncTunnelChgListener.class));
    }

    private void regCommonServiceBeans() {
        ServiceBeanManager serviceBeanManager = IPCApiFactory.getSingletonServiceBeanManager();
        serviceBeanManager.register(MainProcConfigListenService.class.getName(), MainProcConfigListenServiceImpl.getInstance());
        serviceBeanManager.register(MainProcReloadDnsService.class.getName(), new MainProcReloadDnsServiceImpl());
    }

    private void initExtTransAppVisibleReceiver() {
        if (this.mAppVisibleReceiver == null) {
            this.mAppVisibleReceiver = new ExtTransAppVisibleReceiver();
            this.mAppVisibleReceiver.regiester();
        }
    }

    private void regProcBindedEvent() {
        if (MiscUtils.isOtherProcess(this.mContext)) {
            LogCatUtil.info(TAG, "init.  Other process don't execute 'regProcBindedEvent'");
            return;
        }
        BindPushServiceFactory.getInstance().addBindEventListener(BindEventListenerManger.getInstance());
        LogCatUtil.info(TAG, "regProcBindedEvent finish");
    }

    /* access modifiers changed from: protected */
    public void bindService() {
        int processors = Runtime.getRuntime().availableProcessors();
        if (processors <= 2) {
            LogCatUtil.info(TAG, " Don't start push process ahead of time. cpu proc = " + processors);
            return;
        }
        BindPushServiceManager bindPushServiceManager = BindPushServiceFactory.getInstance();
        if (!bindPushServiceManager.isBindedService()) {
            bindPushServiceManager.bindService();
            LogCatUtil.info(TAG, "Invoked bindService");
        }
    }
}
