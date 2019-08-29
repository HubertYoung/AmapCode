package com.alipay.mobile.security.bio.service.impl;

import com.alipay.mobile.security.bio.service.BioExtService;
import com.alipay.mobile.security.bio.service.BioService;
import com.alipay.mobile.security.bio.utils.BioLog;

/* compiled from: BioServiceManagerImpl */
final class a implements Runnable {
    final /* synthetic */ BioServiceManagerImpl a;

    a(BioServiceManagerImpl bioServiceManagerImpl) {
        this.a = bioServiceManagerImpl;
    }

    public final void run() {
        for (BioService bioService : this.a.c.values()) {
            if (bioService instanceof BioExtService) {
                BioExtService bioExtService = (BioExtService) bioService;
                if (!bioExtService.isPreparing()) {
                    BioLog.i("loadingResource:" + bioExtService.getClass().getName());
                    bioExtService.loadingResource();
                }
            }
        }
        BioServiceManagerImpl.g = false;
    }
}
