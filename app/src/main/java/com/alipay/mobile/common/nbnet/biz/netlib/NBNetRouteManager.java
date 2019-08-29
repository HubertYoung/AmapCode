package com.alipay.mobile.common.nbnet.biz.netlib;

import android.os.SystemClock;
import android.support.annotation.Nullable;
import com.alipay.mobile.common.nbnet.biz.log.NBNetLogCat;
import com.alipay.mobile.common.nbnet.biz.util.AssertUtil;
import com.alipay.mobile.common.transport.httpdns.AlipayHttpDnsClient;
import com.alipay.mobile.common.transport.httpdns.DnsUtil;
import com.alipay.mobile.common.transport.httpdns.HttpDns.HttpdnsIP;
import com.alipay.mobile.common.transport.httpdns.HttpdnsIPEntry;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NBNetRouteManager {
    public static int a = 300000;
    private static NBNetRouteManager b;
    private Map<String, NBNetRouteMngModel> c;

    class NBNetRouteMngModel {
        String a;
        List<NBNetRoute> b;
        int c = 0;
        long d = -1;
        long e = -1;

        public NBNetRouteMngModel(List<NBNetRoute> nbNetRouteList, String hostName) {
            this.b = nbNetRouteList;
            this.a = hostName;
        }

        public final NBNetRoute a() {
            NBNetRoute nBNetRoute;
            if (this.b == null || this.b.isEmpty()) {
                return null;
            }
            synchronized (this.a) {
                if (this.c >= this.b.size()) {
                    this.c = 0;
                }
                List<NBNetRoute> list = this.b;
                int i = this.c;
                this.c = i + 1;
                nBNetRoute = list.get(i);
            }
            return nBNetRoute;
        }

        public final synchronized void a(int expirationTime) {
            this.e = SystemClock.elapsedRealtime() + ((long) expirationTime);
            this.d = System.currentTimeMillis() + ((long) expirationTime);
        }

        public final boolean b() {
            if (System.currentTimeMillis() > this.d) {
                LogCatUtil.info("NBNetRouteManager", "isExpirationTime.   sysExpirationTime.  hostName=" + this.a);
                c();
                return true;
            } else if (SystemClock.elapsedRealtime() <= this.e) {
                return false;
            } else {
                LogCatUtil.info("NBNetRouteManager", "isExpirationTime.   elapsedExpirationTime.  hostName=" + this.a);
                c();
                return true;
            }
        }

        private synchronized void c() {
            this.d = -1;
            this.e = -1;
        }
    }

    public static final NBNetRouteManager a() {
        if (b != null) {
            return b;
        }
        synchronized (NBNetRouteManager.class) {
            try {
                if (b != null) {
                    NBNetRouteManager nBNetRouteManager = b;
                    return nBNetRouteManager;
                }
                b = new NBNetRouteManager();
                return b;
            }
        }
    }

    public final NBNetRoute a(String hostName) {
        NBNetRouteMngModel nbNetRouteMngModel = b(hostName);
        if (nbNetRouteMngModel == null) {
            return null;
        }
        return nbNetRouteMngModel.a();
    }

    private NBNetRouteMngModel b(String hostName) {
        Map cacheNBNetRoute = b();
        NBNetRouteMngModel nbNetRouteMngModel1 = a(hostName, cacheNBNetRoute);
        if (nbNetRouteMngModel1 != null) {
            return nbNetRouteMngModel1;
        }
        synchronized (hostName) {
            NBNetRouteMngModel nbNetRouteMngModel2 = a(hostName, cacheNBNetRoute);
            if (nbNetRouteMngModel2 != null) {
                return nbNetRouteMngModel2;
            }
            List tmpNBNetRoutes = c(hostName);
            if (tmpNBNetRoutes == null) {
                NBNetLogCat.d("NBNetRouteManager", "getNBNetRouteList. loadNBNetRouteList result is null.");
                return null;
            }
            NBNetRouteMngModel nbNetRouteMngModel3 = new NBNetRouteMngModel(tmpNBNetRoutes, hostName);
            nbNetRouteMngModel3.a(a);
            cacheNBNetRoute.put(hostName, nbNetRouteMngModel3);
            NBNetLogCat.a((String) "NBNetRouteManager", "loadNBNetRouteList finish. hostName: " + hostName);
            return nbNetRouteMngModel3;
        }
    }

    @Nullable
    private static NBNetRouteMngModel a(String hostName, Map<String, NBNetRouteMngModel> cacheNBNetRoute) {
        NBNetRouteMngModel nbNetRouteMngModel = cacheNBNetRoute.get(hostName);
        if (nbNetRouteMngModel != null && !nbNetRouteMngModel.b()) {
            List nbNetRoutes = nbNetRouteMngModel.b;
            if (nbNetRoutes != null && !nbNetRoutes.isEmpty()) {
                NBNetLogCat.a((String) "NBNetRouteManager", "return cache. hostName: " + hostName);
                return nbNetRouteMngModel;
            }
        }
        return null;
    }

    private static List<NBNetRoute> c(String hostName) {
        List<NBNetRoute> e = e(hostName);
        if (e != null && !e.isEmpty()) {
            return e;
        }
        List<NBNetRoute> d = d(hostName);
        if (d != null) {
            return d;
        }
        return null;
    }

    @Nullable
    private static List<NBNetRoute> d(String hostName) {
        InetAddress[] inetAddresses = f(hostName);
        if (inetAddresses == null || inetAddresses.length <= 0) {
            return null;
        }
        List routes = new ArrayList(inetAddresses.length);
        for (InetAddress inetAddress : inetAddresses) {
            NBNetRoute nbNetRoute = new NBNetRoute(0, hostName, 443, NBNetworkUtil.a(), null);
            nbNetRoute.a(new InetSocketAddress(inetAddress, nbNetRoute.b()));
            routes.add(nbNetRoute);
        }
        return routes;
    }

    @Nullable
    private static List<NBNetRoute> e(String hostName) {
        List routes = null;
        HttpdnsIP httpdnsIP = g(hostName);
        if (httpdnsIP != null) {
            HttpdnsIPEntry[] httpdnsIpEntries = httpdnsIP.getHttpdnsIpEntrys();
            routes = new ArrayList(httpdnsIpEntries.length);
            int length = httpdnsIpEntries.length;
            for (int i = 0; i < length; i++) {
                HttpdnsIPEntry entry = httpdnsIpEntries[i];
                try {
                    NBNetRoute nbNetRoute = new NBNetRoute(0, hostName, entry.port == -1 ? 443 : entry.port, NBNetworkUtil.a(), null);
                    nbNetRoute.a(new InetSocketAddress(InetAddress.getByAddress(DnsUtil.ipToBytesByReg(entry.ip)), nbNetRoute.b()));
                    routes.add(nbNetRoute);
                } catch (UnknownHostException e) {
                    NBNetLogCat.b("NBNetRouteManager", "getByAddress fail. hostName:" + hostName.toString(), e);
                }
            }
            NBNetLogCat.a((String) "NBNetRouteManager", (String) "Loaded from HTTPDNS.");
        }
        return routes;
    }

    private static InetAddress[] f(String hostName) {
        try {
            return NBNetDnsHelper.a(hostName);
        } catch (Throwable e) {
            NBNetLogCat.d("NBNetRouteManager", "getInetAddresses fail. " + e.toString());
            return null;
        }
    }

    private static HttpdnsIP g(String hostName) {
        boolean z;
        boolean z2;
        boolean z3 = true;
        try {
            HttpdnsIP ipInfoByHost = AlipayHttpDnsClient.getDnsClient().getIpInfoByHost(hostName);
            if (ipInfoByHost != null) {
                z = true;
            } else {
                z = false;
            }
            AssertUtil.a("ipInfoByHost can't null", z);
            if (ipInfoByHost.getHttpdnsIpEntrys() != null) {
                z2 = true;
            } else {
                z2 = false;
            }
            AssertUtil.a("httpdnsIpEntries can't null ", z2);
            if (ipInfoByHost.getHttpdnsIpEntrys().length <= 0) {
                z3 = false;
            }
            AssertUtil.a("httpdnsIpEntries's length must be greater than zero", z3);
            return ipInfoByHost;
        } catch (Throwable e) {
            NBNetLogCat.d("NBNetRouteManager", "getHttpdnsIP fail. " + e.toString());
            return null;
        }
    }

    private Map<String, NBNetRouteMngModel> b() {
        if (this.c != null) {
            return this.c;
        }
        synchronized (this) {
            if (this.c != null) {
                Map<String, NBNetRouteMngModel> map = this.c;
                return map;
            }
            this.c = new HashMap();
            return this.c;
        }
    }
}
