package com.alipay.mobile.common.nbnet.biz.platform;

public class DeviceInfoManagerFactory {
    private static DeviceInfoManager a;

    public static final DeviceInfoManager a() {
        if (a != null) {
            return a;
        }
        synchronized (DeviceInfoManager.class) {
            try {
                if (a != null) {
                    DeviceInfoManager deviceInfoManager = a;
                    return deviceInfoManager;
                }
                if (PlatformUtil.a()) {
                    a = b();
                } else {
                    a = new DefaultDeviceInfoManager();
                }
                DeviceInfoManager deviceInfoManager2 = a;
                return deviceInfoManager2;
            }
        }
    }

    private static DeviceInfoManager b() {
        try {
            return (DeviceInfoManager) Class.forName("com.alipay.mobile.common.nbnet.integration.MPaaSDeviceInfoManager").newInstance();
        } catch (Throwable th) {
            return new DefaultDeviceInfoManager();
        }
    }
}
