package com.autonavi.drivelicense;

public class LicenseRecogJni {
    public static native int license_rotate_yuv_90(byte[] bArr, int i, int i2, int i3);

    public static native int license_verify(byte[] bArr, int i);

    static {
        System.loadLibrary("license_recog");
    }
}
