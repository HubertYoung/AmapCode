package com.alipay.multimedia.common.adapter;

import com.alipay.multimedia.adapter.AdapterFactory;
import com.alipay.multimedia.adapter.alipay.AlipayAdapterFactory;

public class AdapterInitial {
    private static final int ADAPTER_TYPE_ALIPAY = 2;
    private static final int ADAPTER_TYPE_ANDROID = 1;
    private static AdapterInitial sInstance = new AdapterInitial();
    private AdapterFactory mAdapterFactory;
    private final int mAdapterType = 2;

    private AdapterInitial() {
        initAdapterFactory();
    }

    private void initAdapterFactory() {
        try {
            this.mAdapterFactory = new AlipayAdapterFactory();
        } catch (Exception e) {
            throw new RuntimeException("init adapter factory error!");
        }
    }

    public static AdapterFactory getAdapterFactory() {
        return sInstance.mAdapterFactory;
    }
}
