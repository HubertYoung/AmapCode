package com.alipay.mobile.common.transport.http.selfencrypt;

import android.text.TextUtils;
import com.alipay.mobile.common.mpaas_crypto.Client;
import com.alipay.mobile.common.transport.config.TransportConfigureItem;
import com.alipay.mobile.common.transport.config.TransportConfigureManager;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.utils.config.fmk.ConfigureItem;

public class Sm4Client {
    private static Sm4Client a = null;
    private Client b;

    public static Sm4Client getInstance() {
        if (a != null) {
            return a;
        }
        synchronized (Sm4Client.class) {
            if (a == null) {
                a = new Sm4Client();
            }
        }
        return a;
    }

    private Sm4Client() {
        if (a()) {
            this.b = new Client();
            this.b.init(null, null, null);
        }
    }

    private static boolean a() {
        if (TextUtils.equals(TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.SM4_ENCRYPT), "T")) {
            return true;
        }
        return false;
    }

    public byte[] encryptSm4(byte[] key, byte[] data) {
        try {
            if (!a()) {
                return data;
            }
            return this.b.encryptSm4(key, data);
        } catch (Throwable ex) {
            LogCatUtil.error((String) "Sm4Client", "encryptSm4 ex: " + ex.toString());
            return null;
        }
    }

    public byte[] decryptSm4(byte[] key, byte[] data) {
        try {
            if (!a()) {
                return data;
            }
            return this.b.decryptSm4(key, data);
        } catch (Throwable ex) {
            LogCatUtil.error((String) "Sm4Client", "decryptSm4 ex: " + ex.toString());
            return null;
        }
    }
}
