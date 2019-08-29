package com.sijla.g.b.c;

import android.text.TextUtils;
import java.security.SecureRandom;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

public class a {
    private String[] a = new String[0];
    private String b = "TLS";
    private KeyManager[] c = null;
    private TrustManager[] d = null;
    private SecureRandom e = new SecureRandom();

    public static a a() {
        return new a();
    }

    public a a(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.b = str;
        }
        return this;
    }

    public a a(TrustManager... trustManagerArr) {
        if (trustManagerArr != null && trustManagerArr.length > 0) {
            this.d = trustManagerArr;
        }
        return this;
    }

    public SSLSocketFactory b() {
        SSLContext instance = SSLContext.getInstance(this.b);
        instance.init(this.c, this.d, this.e);
        return instance.getSocketFactory();
    }
}
