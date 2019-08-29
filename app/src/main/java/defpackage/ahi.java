package defpackage;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import com.autonavi.amap.app.AMapAppGlobal;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;

/* renamed from: ahi reason: default package */
/* compiled from: ProxyUtil */
public final class ahi {
    private static Proxy a() {
        int defaultPort = android.net.Proxy.getDefaultPort();
        String defaultHost = android.net.Proxy.getDefaultHost();
        if (!TextUtils.isEmpty(defaultHost) && defaultPort > 0) {
            try {
                return new Proxy(Type.HTTP, new InetSocketAddress(defaultHost, defaultPort));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Proxy a(boolean z) {
        NetworkInfo networkInfo;
        try {
            networkInfo = ((ConnectivityManager) AMapAppGlobal.getApplication().getSystemService("connectivity")).getActiveNetworkInfo();
        } catch (Throwable unused) {
            networkInfo = null;
        }
        if (networkInfo == null || networkInfo.getType() != 0 || !z) {
            return null;
        }
        return a();
    }
}
