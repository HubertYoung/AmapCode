package com.alipay.mobile.common.transportext.biz.sync;

import com.alipay.mobile.common.amnet.api.AmnetListenerAdpter;
import com.alipay.mobile.common.amnet.api.model.AcceptedData;
import com.alipay.mobile.common.transportext.biz.util.LogUtilAmnet;

public class SyncDataListanerImplDirect extends AmnetListenerAdpter {
    private static final String LOGTAG = "amnet_SyncDataListanerImplDirect";
    private static volatile SyncDataListanerImplDirect instance;

    private SyncDataListanerImplDirect() {
    }

    public static synchronized SyncDataListanerImplDirect getInstance() {
        SyncDataListanerImplDirect syncDataListanerImplDirect;
        synchronized (SyncDataListanerImplDirect.class) {
            try {
                LogUtilAmnet.d(LOGTAG, "SyncDataListanerImpl: getInstance ");
                if (instance == null) {
                    instance = new SyncDataListanerImplDirect();
                }
                syncDataListanerImplDirect = instance;
            }
        }
        return syncDataListanerImplDirect;
    }

    public void onAcceptedDataEvent(AcceptedData acceptedData) {
        LogUtilAmnet.d(LOGTAG, "onAcceptedDataEvent: ");
        SyncManager.onAcceptedDataEvent(acceptedData);
    }
}
