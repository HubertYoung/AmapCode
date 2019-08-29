package defpackage;

import android.os.Build.VERSION;
import com.alipay.mobile.nebula.appcenter.openapi.H5AppHttpRequest;

/* renamed from: za reason: default package */
/* compiled from: UserAgentFilter */
public final class za implements bpd {
    private static final String a;

    public final bpk a(bpk bpk) {
        return bpk;
    }

    static {
        StringBuilder sb = new StringBuilder("Android ");
        sb.append(VERSION.RELEASE);
        a = sb.toString();
    }

    public final bph a(bph bph) {
        if (bph != null && !bph.getHeaders().containsKey(H5AppHttpRequest.HEADER_UA)) {
            bph.addHeader(H5AppHttpRequest.HEADER_UA, a);
        }
        return bph;
    }
}
