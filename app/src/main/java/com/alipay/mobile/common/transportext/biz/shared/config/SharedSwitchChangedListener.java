package com.alipay.mobile.common.transportext.biz.shared.config;

import com.alipay.mobile.common.ipc.api.IPCApiFactory;
import com.alipay.mobile.common.transport.config.CtrlNormalConfigChangedEvent;
import com.alipay.mobile.common.transport.config.TransportConfigureManager;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.transport.utils.NetworkAsyncTaskExecutor;
import com.alipay.mobile.common.transport.utils.SwitchMonitorLogUtil;
import com.alipay.mobile.common.transport.utils.TransportEnvUtil;
import java.util.Observable;
import java.util.Observer;

public class SharedSwitchChangedListener implements Observer {
    private static final String TAG = "SharedSwitchChangedListener";

    public void update(Observable observable, Object data) {
        NetworkAsyncTaskExecutor.executeLowPri(new Runnable() {
            public void run() {
                if (!MiscUtils.isInAlipayClient(TransportEnvUtil.getContext())) {
                    TransportConfigureManager.getInstance().updateConfig(TransportEnvUtil.getContext());
                    SwitchMonitorLogUtil.monitorSwitchUpdatedLog(TransportEnvUtil.getContext(), "rpc");
                    return;
                }
                NetworkAsyncTaskExecutor.executeLowPri(new Runnable() {
                    public void run() {
                        try {
                            ((CtrlNormalConfigChangedEvent) IPCApiFactory.getSingletonIPCContextManager().getIpcCallManager().getIpcProxy(CtrlNormalConfigChangedEvent.class)).notifyChanged();
                            LogCatUtil.info(SharedSwitchChangedListener.TAG, "IPC notifyChanged finish.");
                        } catch (Throwable e) {
                            LogCatUtil.error((String) SharedSwitchChangedListener.TAG, e);
                        }
                    }
                });
            }
        });
    }
}
