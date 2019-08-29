package com.alipay.mobile.common.transportext.biz.mmtp.amnetadapt;

import com.alipay.mobile.common.amnet.ipcapi.pushproc.OutEventNotifyService;
import com.alipay.mobile.common.ipc.api.IPCApiFactory;
import com.alipay.mobile.common.ipc.api.IPCCallManager;
import com.alipay.mobile.common.ipc.api.IPCContextManager;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.NetworkAsyncTaskExecutor;
import com.alipay.mobile.common.transportext.biz.appevent.EventInterfaceAdapter;
import com.alipay.mobile.common.transportext.biz.util.SecureRunnable;
import java.util.Map;

public class AmnetAppEventListener extends EventInterfaceAdapter {
    private static final String TAG = "AmnetAppEventListener";
    private OutEventNotifyService eventNotifyService;

    public void onSeceenOnEvent() {
        NetworkAsyncTaskExecutor.execute(new SecureRunnable() {
            public void call() {
                AmnetAppEventListener.this.getOutEventNotifyService().notifySeceenOnEvent();
            }
        });
    }

    public void onSeceenOffEvent() {
        NetworkAsyncTaskExecutor.execute(new SecureRunnable() {
            public void call() {
                AmnetAppEventListener.this.getOutEventNotifyService().notifySeceenOffEvent();
            }
        });
    }

    public void onAppLeaveEvent() {
        NetworkAsyncTaskExecutor.execute(new SecureRunnable() {
            public void call() {
                AmnetAppEventListener.this.getOutEventNotifyService().notifyAppLeaveEvent();
            }
        });
    }

    public void onAppResumeEvent() {
        NetworkAsyncTaskExecutor.execute(new SecureRunnable() {
            public void call() {
                AmnetAppEventListener.this.getOutEventNotifyService().notifyAppResumeEvent();
            }
        });
    }

    public void onSyncInitChanged(final Map<String, String> initMap) {
        NetworkAsyncTaskExecutor.execute(new SecureRunnable() {
            public void call() {
                AmnetAppEventListener.this.getOutEventNotifyService().onSyncInitChanged(initMap);
            }
        });
    }

    public IPCCallManager getCallManager() {
        return getIpcContextManager().getIpcCallManager();
    }

    public OutEventNotifyService getOutEventNotifyService() {
        if (this.eventNotifyService == null) {
            this.eventNotifyService = (OutEventNotifyService) getCallManager().getIpcProxy(OutEventNotifyService.class);
        }
        return this.eventNotifyService;
    }

    private IPCContextManager getIpcContextManager() {
        try {
            return IPCApiFactory.getSingletonIPCContextManager();
        } catch (Exception e) {
            LogCatUtil.error((String) TAG, (Throwable) e);
            return null;
        }
    }
}
