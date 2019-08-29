package com.alibaba.analytics.core.sync;

import android.text.TextUtils;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

class UtHostnameVerifier implements HostnameVerifier {
    public String bizHost;

    public UtHostnameVerifier(String str) {
        this.bizHost = str;
    }

    public boolean verify(String str, SSLSession sSLSession) {
        return HttpsURLConnection.getDefaultHostnameVerifier().verify(this.bizHost, sSLSession);
    }

    public boolean equals(Object obj) {
        if (TextUtils.isEmpty(this.bizHost) || !(obj instanceof UtHostnameVerifier)) {
            return false;
        }
        String str = ((UtHostnameVerifier) obj).bizHost;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return this.bizHost.equals(str);
    }

    public String getHost() {
        return this.bizHost;
    }
}
