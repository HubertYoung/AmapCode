package com.alipay.mobile.nebula.networksupervisor;

import com.alipay.mobile.nebula.util.H5Utils;
import java.util.ArrayList;
import java.util.List;

public class H5NetworkSuScheduler {
    private static final String TAG = "H5NetworkSuScheduler";
    private static volatile H5NetworkSuScheduler sInstance;
    private List<H5NetworkSuEntity> entityList = new ArrayList();

    private H5NetworkSuScheduler() {
    }

    public static H5NetworkSuScheduler getInstance() {
        if (sInstance == null) {
            synchronized (H5NetworkSuScheduler.class) {
                if (sInstance == null) {
                    sInstance = new H5NetworkSuScheduler();
                }
            }
        }
        return sInstance;
    }

    public void post(H5NetworkSuEntity entity) {
        synchronized (this.entityList) {
            this.entityList.add(entity);
        }
    }

    public void exec() {
        H5NetworkSupervisor h5NetworkSupervisor = (H5NetworkSupervisor) H5Utils.getH5ProviderManager().getProvider(H5NetworkSupervisor.class.getName());
        if (h5NetworkSupervisor != null) {
            execInternal(h5NetworkSupervisor);
        }
    }

    private void execInternal(final H5NetworkSupervisor h5NetworkSupervisor) {
        synchronized (this.entityList) {
            if (!this.entityList.isEmpty()) {
                final List tmpEntityList = new ArrayList(this.entityList);
                this.entityList.clear();
                H5Utils.executeOrdered(TAG, new Runnable() {
                    public void run() {
                        for (H5NetworkSuEntity entity : tmpEntityList) {
                            if (entity != null) {
                                if (entity instanceof H5NetworkSuRequest) {
                                    h5NetworkSupervisor.onSendReq((H5NetworkSuRequest) entity);
                                } else {
                                    h5NetworkSupervisor.onReceiveRsp((H5NetworkSuResponse) entity);
                                }
                            }
                        }
                    }
                });
            }
        }
    }
}
