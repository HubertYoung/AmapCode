package com.alipay.mobile.common.netsdkextdependapi.deviceinfo;

public final class DeviceInfoUtil {
    public static final String getDeviceId() {
        return a().getDeviceId();
    }

    public static final String getClientId() {
        return a().getClientId();
    }

    public static final String getLatitude() {
        return a().getLatitude();
    }

    public static final String getLongitude() {
        return a().getLongitude();
    }

    private static final DeviceInfoManager a() {
        return (DeviceInfoManager) DeviceInfoManagerFactory.getInstance().getDefaultBean();
    }
}
