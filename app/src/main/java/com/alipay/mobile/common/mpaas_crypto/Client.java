package com.alipay.mobile.common.mpaas_crypto;

import com.alipay.mobile.common.transport.http.HttpWorker;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import java.lang.reflect.Method;

public class Client {
    public static final int CT_ecc_aes = 1;
    public static final int CT_rsa_aes = 0;
    public static final int CT_sm2_sm4 = 2;
    public long handle;

    public native byte[] decode(byte[] bArr, byte[] bArr2, int i);

    public native byte[] decryptSm4(byte[] bArr, byte[] bArr2);

    public native byte[][] encode(byte[] bArr, byte[] bArr2, int i);

    public native byte[] encryptSm4(byte[] bArr, byte[] bArr2);

    public native String error();

    public native void exit();

    public native boolean init(String str, String str2, String str3);

    public Client() {
        this.handle = 0;
        this.handle = 0;
    }

    static {
        try {
            Method method = Class.forName("com.alipay.mobile.common.utils.load.LibraryLoadUtils").getMethod("loadLibrary", new Class[]{String.class, Boolean.TYPE});
            method.invoke(null, new Object[]{"openssl", Boolean.valueOf(false)});
            method.invoke(null, new Object[]{"mpaas_crypto", Boolean.valueOf(false)});
            LogCatUtil.info(HttpWorker.TAG, "LibraryLoadUtils load success.");
        } catch (Throwable e2) {
            LogCatUtil.warn((String) HttpWorker.TAG, "System.loadLibrary load fail. " + e2.toString());
        }
    }
}
