package com.alipay.mobile.security.bio.service.impl;

import com.alipay.mobile.security.bio.exception.BioIllegalArgumentException;
import com.alipay.mobile.security.bio.service.BioServiceManager;
import com.alipay.mobile.security.bio.service.BioStoreService;
import com.alipay.mobile.security.bio.service.BioUploadItem;
import com.alipay.mobile.security.bio.service.BioUploadResult;
import com.alipay.mobile.security.bio.service.local.rpc.BioRPCService;

public abstract class BioUploadGW {
    public static final int BASE64_FLAGS = 10;
    protected BioServiceManager a;
    protected BioRPCService b;
    protected BioStoreService c;

    public abstract BioUploadResult upload(String str, BioUploadItem bioUploadItem);

    public BioUploadGW(BioServiceManager bioServiceManager) {
        if (bioServiceManager == null) {
            throw new BioIllegalArgumentException("BioServiceManager can't be null");
        }
        this.a = bioServiceManager;
        this.b = (BioRPCService) bioServiceManager.getBioService(BioRPCService.class);
        this.c = (BioStoreService) bioServiceManager.getBioService(BioStoreService.class);
    }
}
