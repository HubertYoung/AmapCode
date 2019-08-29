package com.alipay.mobile.common.transport.sys.telephone;

public class NetTelephonyManagerFactory {
    private static NetTelephonyManager a;
    private static NetTelephonyManager b;

    public static final NetTelephonyManager getInstance() {
        if (a != null) {
            return a;
        }
        if (b != null) {
            return b;
        }
        DefaultNetTelephonyManager defaultNetTelephonyManager = new DefaultNetTelephonyManager();
        b = defaultNetTelephonyManager;
        return defaultNetTelephonyManager;
    }

    public static final void setNetTelephonyManager(NetTelephonyManager pNetTelephonyManager) {
        a = pNetTelephonyManager;
    }
}
