package com.alipay.mobile.security.senative;

import android.content.Context;

public class APSE {
    public static APSE _instance = null;
    private static boolean isLoad = true;
    public static final String mVersion = "1.1.5";

    public static native int getVersion();

    public native byte[] encryptAndSignRdsWithWua(Object obj, Object obj2);

    public native String getErrorCode();

    public native long init(Object obj);

    public native boolean isX86Machine();

    public native String nativeHOTP(Object obj, byte[] bArr, long j, int i);

    public native byte[] zipEncryptAndSignRdsWithWua(Object obj, Object obj2);

    static {
        try {
            System.loadLibrary("APSE_1.1.5");
        } catch (Throwable unused) {
        }
    }

    private APSE() {
    }

    public static synchronized APSE getInstance(Context context) {
        APSE apse;
        synchronized (APSE.class) {
            if (_instance == null) {
                synchronized (APSE.class) {
                    if (_instance == null) {
                        APSE apse2 = new APSE();
                        _instance = apse2;
                        apse2.loadSo(context);
                    }
                }
            }
            apse = _instance;
        }
        return apse;
    }

    private void loadSo(Context context) {
        if (!isLoad) {
            try {
                new SEProtectLoaderEx(context).loadSo("APSE", mVersion);
            } catch (Throwable unused) {
            }
        }
    }
}
