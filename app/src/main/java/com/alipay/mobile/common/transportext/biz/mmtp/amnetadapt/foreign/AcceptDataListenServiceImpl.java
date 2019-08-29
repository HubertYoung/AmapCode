package com.alipay.mobile.common.transportext.biz.mmtp.amnetadapt.foreign;

import com.alipay.mobile.common.amnet.api.AcceptDataManager;
import com.alipay.mobile.common.amnet.api.model.AcceptedData;
import com.alipay.mobile.common.amnet.ipcapi.mainproc.MainProcDataListenService;
import com.alipay.mobile.common.transport.utils.NetworkAsyncTaskExecutor;
import com.alipay.mobile.common.transportext.biz.mmtp.amnetadapt.AmnetHelper;
import java.util.Map;

public class AcceptDataListenServiceImpl implements MainProcDataListenService {
    private static AcceptDataListenServiceImpl ACCEPT_DATA_SERVICE;
    private AcceptDataManager acceptDataManager;

    public static final MainProcDataListenService getInstance() {
        if (ACCEPT_DATA_SERVICE == null) {
            synchronized (MainProcDataListenService.class) {
                try {
                    if (ACCEPT_DATA_SERVICE == null) {
                        ACCEPT_DATA_SERVICE = new AcceptDataListenServiceImpl();
                    }
                }
            }
        }
        return ACCEPT_DATA_SERVICE;
    }

    private AcceptDataListenServiceImpl() {
    }

    public void onAcceptedDataEvent(final AcceptedData acceptedData) {
        NetworkAsyncTaskExecutor.execute(new Runnable() {
            public void run() {
                AcceptDataListenServiceImpl.this.getAcceptDataManager().notifyAcceptedData(acceptedData);
            }
        });
    }

    public void recycle(final byte channelType, final Map<String, String> header, final byte[] body) {
        NetworkAsyncTaskExecutor.execute(new Runnable() {
            public void run() {
                AcceptDataListenServiceImpl.this.getAcceptDataManager().notifyRecycle(channelType, header, body);
            }
        });
    }

    public void tell(byte channelType, long reqId, int uncompressSize, int compressSize) {
        final byte b = channelType;
        final long j = reqId;
        final int i = uncompressSize;
        final int i2 = compressSize;
        NetworkAsyncTaskExecutor.execute(new Runnable() {
            public void run() {
                AcceptDataListenServiceImpl.this.getAcceptDataManager().notifyReqPacketSize(b, j, i, i2);
            }
        });
    }

    public AcceptDataManager getAcceptDataManager() {
        if (this.acceptDataManager == null) {
            this.acceptDataManager = AmnetHelper.getAmnetManager().getAcceptDataManager();
        }
        return this.acceptDataManager;
    }
}
