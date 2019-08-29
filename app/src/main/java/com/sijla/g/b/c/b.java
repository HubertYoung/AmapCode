package com.sijla.g.b.c;

import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;

public class b implements HostnameVerifier {
    public boolean verify(String str, SSLSession sSLSession) {
        String[] split;
        try {
            String peerHost = sSLSession.getPeerHost();
            for (X509Certificate subjectX500Principal : (X509Certificate[]) sSLSession.getPeerCertificates()) {
                for (String str2 : subjectX500Principal.getSubjectX500Principal().getName().split(",")) {
                    if (str2.startsWith("CN") && peerHost.equals(str) && str2.contains("qchannel")) {
                        return true;
                    }
                }
            }
        } catch (SSLPeerUnverifiedException e) {
            e.printStackTrace();
        }
        return false;
    }
}
