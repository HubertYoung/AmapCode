package com.alipay.mobile.common.transportext.biz.mmtp;

import android.content.Context;
import com.alipay.mobile.common.amnet.api.AmnetEnvHelper;
import com.alipay.mobile.common.amnet.biz.AmnetBifrostDynamicLibFileManager;
import com.alipay.mobile.common.amnet.ipcapi.mainproc.MainProcDataListenService;
import com.alipay.mobile.common.amnet.ipcapi.mainproc.MainProcGeneralListenService;
import com.alipay.mobile.common.ipc.api.IPCApiFactory;
import com.alipay.mobile.common.ipc.api.ServiceBeanManager;
import com.alipay.mobile.common.transport.context.TransportContext;
import com.alipay.mobile.common.transport.ext.ExtTransportClient;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.transportext.biz.appevent.AppEventManager;
import com.alipay.mobile.common.transportext.biz.mmtp.amnetadapt.AmnetAppEventListener;
import com.alipay.mobile.common.transportext.biz.mmtp.amnetadapt.AmnetHelper;
import com.alipay.mobile.common.transportext.biz.mmtp.amnetadapt.foreign.AcceptDataListenServiceImpl;
import com.alipay.mobile.common.transportext.biz.mmtp.amnetadapt.foreign.GeneralEventListenServiceImpl;
import com.alipay.mobile.common.transportext.biz.mmtp.mrpc.MRpcClient;
import com.alipay.mobile.common.transportext.biz.mmtp.mrpc.internal.MRpcConnection;
import com.alipay.mobile.common.transportext.biz.shared.ExtTransportEnv;
import com.alipay.mobile.common.transportext.biz.shared.ExtTransportManagerAdapter;
import com.alipay.mobile.common.transportext.biz.sync.SyncDataListanerImpl;
import com.alipay.mobile.common.transportext.biz.sync.SyncDataListanerImplDirect;
import com.alipay.mobile.common.transportext.biz.sync.SyncGeneralListenerImpl;
import com.alipay.mobile.common.transportext.biz.sync.SyncManager;

public class MMTPTransportManager extends ExtTransportManagerAdapter {
    private static final String TAG = "MMTPTransportManager";
    private volatile boolean initd = false;
    private Context mContext;

    public synchronized void init(Context context) {
        this.mContext = context;
        if (!this.initd) {
            this.initd = true;
            if (MiscUtils.isOtherProcess(context)) {
                LogCatUtil.info(TAG, "init.  Other process don't init mmtp");
            } else {
                AmnetEnvHelper.setAppContext(ExtTransportEnv.getAppContext());
                AppEventManager.register(new AmnetAppEventListener());
                AmnetHelper.getAmnetManager().addGeneraEventListener(MMTPGeneralListenerImpl.getInstance());
                SyncManager.onInitialize();
                AmnetHelper.getAmnetManager().addSyncAcceptDataListener(SyncDataListanerImpl.getInstance());
                AmnetHelper.getAmnetManager().addSyncDirectAcceptDataListener(SyncDataListanerImplDirect.getInstance());
                AmnetHelper.getAmnetManager().addGeneraEventListener(SyncGeneralListenerImpl.getInstance());
                MRpcConnection.getInstance();
                regAmnetServiceBean();
                AmnetBifrostDynamicLibFileManager.getInstance().asynTryDeleteOldBifrostLibFile();
                LogCatUtil.info(TAG, "Main Process init finish!!");
            }
        }
    }

    private void regAmnetServiceBean() {
        ServiceBeanManager serviceBeanManager = IPCApiFactory.getSingletonServiceBeanManager();
        serviceBeanManager.register(MainProcDataListenService.class.getName(), AcceptDataListenServiceImpl.getInstance());
        serviceBeanManager.register(MainProcGeneralListenService.class.getName(), GeneralEventListenServiceImpl.getInstance());
    }

    public ExtTransportClient getExtTransportClient(Context context, TransportContext transportContext) {
        if (!this.initd) {
            synchronized (this) {
                if (!this.initd) {
                    init(context);
                }
            }
        }
        return MRpcClient.getInstance(context);
    }

    public boolean isInited() {
        return this.initd;
    }
}
