package com.sina.deviceidjnisdk;

import android.content.Context;
import android.text.TextUtils;

public class DeviceIdFactory {
    private static volatile IDeviceId sInstance;

    public static native String calculateM(Context context, String str, String str2);

    private static native String getIValueNative(Context context, String str);

    private static native IDeviceId getInstanceNative(Context context, int i);

    static {
        System.loadLibrary("weibosdkcore");
    }

    private DeviceIdFactory() {
    }

    public static synchronized IDeviceId getInstance(Context context) {
        IDeviceId iDeviceId;
        synchronized (DeviceIdFactory.class) {
            if (sInstance == null) {
                sInstance = new DeviceId(context);
            }
            iDeviceId = sInstance;
        }
        return iDeviceId;
    }

    public static synchronized String getIValue(Context context) {
        synchronized (DeviceIdFactory.class) {
            try {
                String deviceId = DeviceInfo.getDeviceId(context);
                if (TextUtils.isEmpty(deviceId)) {
                    deviceId = DeviceInfo.getMacAddress(context);
                }
                if (TextUtils.isEmpty(deviceId)) {
                    deviceId = "000000000000000";
                }
                if (!TextUtils.isEmpty(deviceId)) {
                    String iValueNative = getIValueNative(context, deviceId);
                    return iValueNative;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
