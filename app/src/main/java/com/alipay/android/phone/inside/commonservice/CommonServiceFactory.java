package com.alipay.android.phone.inside.commonservice;

import android.os.Bundle;

public class CommonServiceFactory {
    private static CommonServiceFactory INSTANCE = null;
    public static final String KEY_IS_ALIPAY = "KEY_IS_ALIPAY";
    private boolean mIsAlipay;
    private RpcService rpcService;

    public static CommonServiceFactory getInstance() {
        if (INSTANCE != null) {
            return INSTANCE;
        }
        synchronized (CommonServiceFactory.class) {
            try {
                if (INSTANCE == null) {
                    INSTANCE = new CommonServiceFactory();
                }
            }
        }
        return INSTANCE;
    }

    private CommonServiceFactory() {
    }

    private RpcService createRpcService() {
        if (this.mIsAlipay) {
            return new RpcServiceForAlipay();
        }
        return new RpcServiceForSdk();
    }

    public void init(Bundle bundle) {
        this.mIsAlipay = bundle.getBoolean(KEY_IS_ALIPAY, false);
    }

    public RpcService getRpcService() {
        if (this.rpcService == null) {
            this.rpcService = createRpcService();
        }
        return this.rpcService;
    }
}
