package com.alibaba.analytics.core.sync;

import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

class UtTrustManager implements X509TrustManager {
    private static final String TAG = "X509TrustManager";
    private static TrustManager[] trustManagers;

    public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
    }

    UtTrustManager() {
    }

    static synchronized TrustManager[] getTrustManagers() {
        TrustManager[] trustManagerArr;
        synchronized (UtTrustManager.class) {
            try {
                if (trustManagers == null) {
                    trustManagers = new TrustManager[]{new UtTrustManager()};
                }
                trustManagerArr = trustManagers;
            }
        }
        return trustManagerArr;
    }

    public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
        if (x509CertificateArr == null || x509CertificateArr.length <= 0) {
            throw new IllegalArgumentException("checkServerTrusted: X509Certificate array is null");
        }
        try {
            TrustManagerFactory instance = TrustManagerFactory.getInstance("X509");
            instance.init(null);
            if (!(instance == null || instance.getTrustManagers() == null)) {
                TrustManager[] trustManagers2 = instance.getTrustManagers();
                int length = trustManagers2.length;
                int i = 0;
                while (i < length) {
                    try {
                        ((X509TrustManager) trustManagers2[i]).checkServerTrusted(x509CertificateArr, str);
                        i++;
                    } catch (CertificateException e) {
                        Throwable th = e;
                        while (th != null) {
                            if (!(th instanceof CertificateExpiredException) && !(th instanceof CertificateNotYetValidException)) {
                                th = th.getCause();
                            } else {
                                return;
                            }
                        }
                        throw e;
                    }
                }
            }
        } catch (NoSuchAlgorithmException e2) {
            throw new CertificateException(e2);
        } catch (KeyStoreException e3) {
            throw new CertificateException(e3);
        }
    }

    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }
}
