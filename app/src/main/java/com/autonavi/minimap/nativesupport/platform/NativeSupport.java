package com.autonavi.minimap.nativesupport.platform;

public final class NativeSupport {
    private static NativeSupport mInstance;
    private NetworkMonitor mNetworkMonitor = ((NetworkMonitor) PlatformServiceManagerImpl.getInstance().getNetworkService().getNetworkMonitorInstance());

    public static NativeSupport getInstance() {
        if (mInstance == null) {
            synchronized (NativeSupport.class) {
                try {
                    if (mInstance == null) {
                        mInstance = new NativeSupport();
                    }
                }
            }
        }
        return mInstance;
    }

    private NativeSupport() {
    }

    public final NetworkMonitor getNetworkMonitor() {
        return this.mNetworkMonitor;
    }
}
