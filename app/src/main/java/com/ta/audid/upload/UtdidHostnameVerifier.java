package com.ta.audid.upload;

import android.text.TextUtils;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

class UtdidHostnameVerifier implements HostnameVerifier {
    public String bizHost;

    public UtdidHostnameVerifier(String str) {
        this.bizHost = str;
    }

    public boolean verify(String str, SSLSession sSLSession) {
        return HttpsURLConnection.getDefaultHostnameVerifier().verify(this.bizHost, sSLSession);
    }

    public boolean equals(Object obj) {
        if (TextUtils.isEmpty(this.bizHost) || !(obj instanceof UtdidHostnameVerifier)) {
            return false;
        }
        String str = ((UtdidHostnameVerifier) obj).bizHost;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return this.bizHost.equals(str);
    }
}
