package org.android.spdy;

import java.security.cert.CertificateFactory;
import java.security.cert.TrustAnchor;
import java.util.HashSet;
import java.util.Set;

class AndroidTrustAnchors {
    public static volatile boolean a = false;
    static String b = "/system/etc/security/cacerts/";
    private static AndroidTrustAnchors e = new AndroidTrustAnchors();
    Set<TrustAnchor> c;
    CertificateFactory d;

    public static AndroidTrustAnchors a() {
        return e;
    }

    private AndroidTrustAnchors() {
        this.c = null;
        this.d = null;
        this.c = new HashSet();
    }
}
