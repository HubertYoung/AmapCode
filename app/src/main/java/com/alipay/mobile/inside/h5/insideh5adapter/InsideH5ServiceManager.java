package com.alipay.mobile.inside.h5.insideh5adapter;

public class InsideH5ServiceManager {
    private static InsideH5ServiceManager sInstance;
    private IInsideH5Service mInsideH5Service;

    public static InsideH5ServiceManager getInstance() {
        if (sInstance == null) {
            synchronized (InsideH5ServiceManager.class) {
                try {
                    if (sInstance == null) {
                        sInstance = new InsideH5ServiceManager();
                    }
                }
            }
        }
        return sInstance;
    }

    public void setInsideH5Service(IInsideH5Service iInsideH5Service) {
        this.mInsideH5Service = iInsideH5Service;
    }

    public IInsideH5Service getInsideH5Service() {
        if (this.mInsideH5Service == null) {
            new DefaultInsideH5ServiceImpl();
        }
        return this.mInsideH5Service;
    }
}
