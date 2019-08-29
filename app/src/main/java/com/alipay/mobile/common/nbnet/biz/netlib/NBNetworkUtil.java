package com.alipay.mobile.common.nbnet.biz.netlib;

import android.content.Context;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Pair;
import com.alipay.mobile.common.nbnet.biz.constants.NBNetConfigureItem;
import com.alipay.mobile.common.nbnet.biz.log.NBNetLogCat;
import com.alipay.mobile.common.nbnet.biz.util.NBNetEnvUtils;
import com.alipay.mobile.common.transport.TransportStrategy;
import com.alipay.mobile.common.transport.config.TransportConfigureManager;
import com.alipay.mobile.common.transport.utils.NetworkUtils;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;

public class NBNetworkUtil {
    public static final Proxy a() {
        Proxy proxy = Proxy.NO_PROXY;
        Pair proxyOfEnhanced = a(NBNetEnvUtils.a());
        if (proxyOfEnhanced == null) {
            return proxy;
        }
        NBNetLogCat.a((String) "NBNetworkUtil", "getProxyObject. " + ((String) proxyOfEnhanced.first) + ":" + proxyOfEnhanced.second);
        return new Proxy(Type.HTTP, InetSocketAddress.createUnresolved((String) proxyOfEnhanced.first, ((Integer) proxyOfEnhanced.second).intValue()));
    }

    private static Pair<String, Integer> a(Context context) {
        Pair proxy = b(context);
        if (proxy == null) {
            return null;
        }
        if (!TransportStrategy.isMobileWapProxyIp((String) proxy.first) || NetworkUtils.getNetworkType(context) != 3) {
            return proxy;
        }
        NBNetLogCat.d("NetworkUtils", " The proxy ip is wap = [" + ((String) proxy.first) + "], but now it's wifi network !");
        return null;
    }

    private static Pair<String, Integer> b(Context context) {
        NetworkInfo ni = null;
        try {
            ni = NetworkUtils.getActiveNetworkInfo(context);
        } catch (Throwable var5) {
            NBNetLogCat.b((String) "NetworkUtils", var5);
        }
        if (ni == null || !ni.isAvailable()) {
            return null;
        }
        String proxyHost = android.net.Proxy.getDefaultHost();
        int port = android.net.Proxy.getDefaultPort();
        if (TextUtils.isEmpty(proxyHost) || port <= 0 || port >= 65535) {
            return null;
        }
        return new Pair(proxyHost, Integer.valueOf(port));
    }

    public static final boolean a(Proxy p1, Proxy p2) {
        if (p1 == null && p2 == null) {
            return true;
        }
        if (p1 != null || p2 == null) {
            if (p2 != null || p1 == null) {
                return p1.equals(p2);
            }
            if (p1.type() != Proxy.NO_PROXY.type()) {
                return false;
            }
            return true;
        } else if (p2.type() != Proxy.NO_PROXY.type()) {
            return false;
        } else {
            return true;
        }
    }

    public static final int b() {
        TransportConfigureManager mng = TransportConfigureManager.getInstance();
        Context context = NBNetEnvUtils.a();
        if (context == null) {
            NBNetLogCat.e("NBNetworkUtil", "context is null. reivew code please !");
            return mng.getIntValue(NBNetConfigureItem.G3_SO_TIMEOUT);
        }
        int networkType = NetworkUtils.getNetworkType(context);
        NBNetLogCat.c("NBNetworkUtil", "getReadTimeout networkType=" + networkType);
        switch (networkType) {
            case 1:
                return mng.getIntValue(NBNetConfigureItem.G2_SO_TIMEOUT);
            case 2:
                return mng.getIntValue(NBNetConfigureItem.G3_SO_TIMEOUT);
            case 3:
            case 4:
                return mng.getIntValue(NBNetConfigureItem.WIFI_4G_SO_TIMEOUT);
            default:
                return mng.getIntValue(NBNetConfigureItem.G3_SO_TIMEOUT);
        }
    }
}
