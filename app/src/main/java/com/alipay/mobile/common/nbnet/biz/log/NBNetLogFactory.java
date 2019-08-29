package com.alipay.mobile.common.nbnet.biz.log;

import com.alipay.mobile.common.nbnet.api.NBNetLog;

public class NBNetLogFactory {
    private static NBNetLog a;
    private static NBNetLog b = null;

    public static NBNetLog a() {
        if (b != null) {
            return b;
        }
        if (a != null) {
            return a;
        }
        synchronized (NBNetLogFactory.class) {
            try {
                if (a != null) {
                    NBNetLog nBNetLog = a;
                    return nBNetLog;
                }
                MWalletNBNetLog mWalletNBNetLog = new MWalletNBNetLog();
                a = mWalletNBNetLog;
                return mWalletNBNetLog;
            }
        }
    }
}
