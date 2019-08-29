package com.alipay.edge.impl;

import com.alipay.mobile.security.senative.APSE;

public class EdgeNativeBridge {
    private static final int CALLBACK_REPORT_DEVICE_DATA = 2;
    private static boolean isSoLoadesSuccess = false;
    private static T0DeviceDataListener mDeviceDataComingListener;

    public interface T0DeviceDataListener {
        void a(byte[] bArr);
    }

    private static native byte[] getDeviceDataAsync(int i);

    public static native byte[] getDeviceDataSync(int i, byte[] bArr);

    public static native int getRiskResult(String str, String str2, Object obj, int i);

    public static native boolean getSync();

    public static native int initialize(Object obj, byte[] bArr, String str, int i);

    public static native int postUserAction(String str, String str2);

    public static native int updateResource(byte[] bArr);

    public static boolean isLoadOk() {
        return isSoLoadesSuccess;
    }

    public static void getDeviceDataAsync(int i, T0DeviceDataListener t0DeviceDataListener) {
        mDeviceDataComingListener = t0DeviceDataListener;
        getDeviceDataAsync(i);
    }

    private static void onCallbackFromNativeWorld(byte[] bArr, int i) {
        if (i == 2 && mDeviceDataComingListener != null) {
            mDeviceDataComingListener.a(bArr);
        }
    }

    static {
        try {
            APSE.getVersion();
            isSoLoadesSuccess = true;
        } catch (Throwable unused) {
            isSoLoadesSuccess = false;
        }
    }
}
