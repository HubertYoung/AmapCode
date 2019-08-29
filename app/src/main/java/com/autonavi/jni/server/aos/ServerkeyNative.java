package com.autonavi.jni.server.aos;

import android.app.Application;

public class ServerkeyNative {
    private static final String TAG = "serverkey";

    @Deprecated
    public static native String amapDecode(String str);

    @Deprecated
    public static native String amapDecode(String str, String str2);

    public static native String amapDecodeV2(String str);

    public static native String amapDecodeV2(String str, String str2);

    @Deprecated
    public static native String amapEncode(String str);

    @Deprecated
    public static native String amapEncode(String str, String str2);

    @Deprecated
    public static native byte[] amapEncodeBinary(byte[] bArr);

    public static native byte[] amapEncodeBinaryV2(byte[] bArr);

    public static native String amapEncodeV2(String str);

    public static native String amapEncodeV2(String str, String str2);

    public static native String get360CustomKey();

    public static native String get360Secret();

    public static native String getAosChannel();

    public static native String getAosKey();

    public static native String getLWCustomKey();

    public static native String getLWSecret();

    public static native String getQQCustomKey();

    public static native String getQQSecret();

    public static native String getSinaCustomKey();

    public static native String getSinaSecret();

    public static native String getSpm(String str, String str2, String str3, String str4, String str5);

    public static native String getSsoKey();

    public static native String getTaobaoCustomKey();

    public static native String getTaobaoSecret();

    public static native String getVersion();

    public static native String getWXCustomKey();

    public static native String getWXSecret();

    public static native String getYXCustomKey();

    public static native String getYXSecret();

    public static native String sign(byte[] bArr);

    public static native int translatePointLocal(int i, int i2, ServerkeyGeoPoint serverkeyGeoPoint);

    static {
        if (ServerkeyConfig.APP_APPLICATION == null) {
            throw new RuntimeException("must init ServerkeyConfig.APP_APPLICATION!!!");
        } else if (ServerkeyConfig.IS_DEBUG) {
            System.loadLibrary("serverkey-debug");
            new StringBuilder("采纳测网签名的serverkey: ").append(getVersion());
        } else {
            System.loadLibrary(TAG);
            if (ServerkeyConfig.IS_LOG_ON) {
                new StringBuilder("采纳正式签名的serverkey: ").append(getVersion());
            }
        }
    }

    public static Application getApplication() {
        return ServerkeyConfig.APP_APPLICATION;
    }
}
