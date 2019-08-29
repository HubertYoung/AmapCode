package com.alipay.mobile.common.logging.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import java.util.UUID;

public class HybridEncryption {
    private static HybridEncryption a;
    private static String b = (AESUtil.class.getName() + RSAUtil.class.getName() + Base64.class.getName() + LoggingUtil.class.getName() + MD5Util.class.getName());
    private String c;
    private byte[] d;
    private byte[] e;
    private boolean f;
    private boolean g;
    private boolean h;

    public static HybridEncryption getInstance() {
        if (a != null) {
            return a;
        }
        throw new IllegalStateException("need createInstance befor use");
    }

    public static synchronized HybridEncryption createInstance(Context context) {
        HybridEncryption hybridEncryption;
        synchronized (HybridEncryption.class) {
            if (a == null) {
                a = new HybridEncryption(context);
            }
            hybridEncryption = a;
        }
        return hybridEncryption;
    }

    private HybridEncryption(Context context) {
        this.c = a(context);
    }

    private static String a(Context context) {
        ApplicationInfo appInfo = null;
        try {
            appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
        } catch (Throwable e2) {
            Log.e("Hybrid", "obtainPublicKey", e2);
        }
        if (appInfo == null || appInfo.metaData == null) {
            return null;
        }
        return appInfo.metaData.getString("setting.logging.encryption.pubkey");
    }

    public byte[] getSecureSeed() {
        return this.e;
    }

    public byte[] encrypt(byte[] source, int index, int length) {
        byte[] bArr = null;
        if (this.d == null || this.e == null) {
            byte[] seed = null;
            try {
                seed = UUID.randomUUID().toString().getBytes();
            } catch (Throwable e2) {
                if (!this.f) {
                    this.f = true;
                    Log.e("Hybrid", DriveUtil.SCHEME_PARAM_ENCRYPT, e2);
                }
            }
            if (seed == null) {
                try {
                    seed = String.valueOf(System.currentTimeMillis()).getBytes();
                } catch (Throwable e3) {
                    if (!this.g) {
                        this.g = true;
                        Log.e("Hybrid", DriveUtil.SCHEME_PARAM_ENCRYPT, e3);
                    }
                }
            }
            this.d = AESUtil.getRawKey(seed);
            this.e = RSAUtil.encrypt(this.d, this.c);
        }
        if (this.d == null || this.e == null) {
            return bArr;
        }
        try {
            return AESUtil.encrypt(this.d, source, index, length);
        } catch (Throwable e4) {
            if (this.h) {
                return bArr;
            }
            this.h = true;
            Log.e("Hybrid", DriveUtil.SCHEME_PARAM_ENCRYPT, e4);
            return bArr;
        }
    }
}
