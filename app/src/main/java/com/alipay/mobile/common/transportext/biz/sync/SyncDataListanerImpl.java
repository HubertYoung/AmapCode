package com.alipay.mobile.common.transportext.biz.sync;

import com.alipay.mobile.common.amnet.api.AmnetListenerAdpter;
import com.alipay.mobile.common.amnet.api.model.AcceptedData;
import com.alipay.mobile.common.transportext.biz.util.LogUtilAmnet;

public class SyncDataListanerImpl extends AmnetListenerAdpter {
    private static final String LOGTAG = "amnet_SyncDataListanerImpl";
    private static volatile SyncDataListanerImpl instance;

    private SyncDataListanerImpl() {
    }

    public static SyncDataListanerImpl getInstance() {
        LogUtilAmnet.d(LOGTAG, "SyncDataListanerImpl: getInstance ");
        if (instance == null) {
            synchronized (SyncDataListanerImpl.class) {
                try {
                    instance = new SyncDataListanerImpl();
                }
            }
        }
        return instance;
    }

    public void onAcceptedDataEvent(AcceptedData acceptedData) {
        LogUtilAmnet.d(LOGTAG, "onAcceptedDataEvent: ");
        SyncManager.onAcceptedDataEvent(acceptedData);
    }
}
