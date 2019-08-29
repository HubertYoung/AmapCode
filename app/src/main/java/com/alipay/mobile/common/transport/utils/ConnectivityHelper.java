package com.alipay.mobile.common.transport.utils;

import com.alipay.mobile.common.transport.ext.ExtTransportOffice;

public class ConnectivityHelper {
    public static final String SCENE_QUICK = "quick";
    private static boolean a = false;

    public static boolean isConnAvailable() {
        return ExtTransportOffice.getInstance().isConnectionAvailable();
    }

    public static void notifyRedText(boolean show) {
        a = show;
    }

    public static boolean isShowRedText() {
        return a;
    }

    public static void notifyScene(String scene, boolean pass) {
        ExtTransportOffice.getInstance().setScene(scene, pass);
    }

    public static int getConnState() {
        return ExtTransportOffice.getInstance().getConnState();
    }
}
