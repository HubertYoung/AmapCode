package defpackage;

import com.alipay.mobile.common.transport.utils.TransportConstants;
import java.io.Closeable;
import java.util.List;
import java.util.Map;

/* renamed from: fgp reason: default package */
/* compiled from: NetworkUtils */
public final class fgp {
    public static void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception unused) {
            }
        }
    }

    public static boolean a(Map<String, List<String>> map) {
        try {
            if ("gzip".equalsIgnoreCase(fcz.a(map, TransportConstants.KEY_X_CONTENT_ENCODING))) {
                return true;
            }
        } catch (Exception unused) {
        }
        return false;
    }
}
