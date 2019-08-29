package com.alipay.mobile.common.transport.httpdns;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.SystemClock;
import android.text.TextUtils;
import com.alipay.android.phone.mobilesdk.socketcraft.monitor.MonitorItemConstants;
import com.alipay.mobile.common.logging.api.monitor.Performance;
import com.alipay.mobile.common.netsdkextdependapi.userinfo.UserInfoUtil;
import com.alipay.mobile.common.transport.config.TransportConfigureItem;
import com.alipay.mobile.common.transport.config.TransportConfigureManager;
import com.alipay.mobile.common.transport.context.TransportContext;
import com.alipay.mobile.common.transport.ext.ExtTransportOffice;
import com.alipay.mobile.common.transport.http.HttpContextExtend;
import com.alipay.mobile.common.transport.httpdns.downloader.ConfigSelector;
import com.alipay.mobile.common.transport.httpdns.downloader.HttpClient;
import com.alipay.mobile.common.transport.httpdns.downloader.StrategyItemParser;
import com.alipay.mobile.common.transport.httpdns.downloader.StrategyRequest;
import com.alipay.mobile.common.transport.httpdns.downloader.StrategyResponse;
import com.alipay.mobile.common.transport.iprank.AlipayDNSHelper;
import com.alipay.mobile.common.transport.monitor.MonitorLoggerUtils;
import com.alipay.mobile.common.transport.monitor.RPCDataItems;
import com.alipay.mobile.common.transport.monitor.RPCDataParser;
import com.alipay.mobile.common.transport.monitor.TransportPerformance;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.transport.utils.NetworkAsyncTaskExecutor;
import com.alipay.mobile.common.transport.utils.NetworkUtils;
import com.alipay.mobile.common.transport.utils.NwSharedSwitchUtil;
import com.alipay.mobile.common.transport.utils.SwitchMonitorLogUtil;
import com.alipay.mobile.common.transport.utils.TransportContextThreadLocalUtils;
import com.alipay.mobile.common.transport.utils.TransportEnvUtil;
import com.alipay.mobile.common.utils.config.fmk.ConfigureItem;
import com.autonavi.minimap.ajx3.loader.AjxHttpLoader;
import com.autonavi.widget.ui.BalloonLayout;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class HttpDns {
    public static final int REQ_STATUS_BUSY = 1;
    public static final int REQ_STATUS_IDLE = 0;
    public static final String TAG = "HTTP_DNS";
    public static long localDnsExpire = 300000;
    private ArrayList<String> a;
    private String b;
    private ArgType c;
    private DnsLocalManager d;
    private GetAllByNameHelper e;
    /* access modifiers changed from: private */
    public Context f;
    public boolean fail;
    private int g;
    private int h;
    public boolean hashost;
    public String httpServerUrl;
    public String httpdns_domain;
    public int httpdns_port;
    private long i;
    private long j;
    private boolean k;
    public String path;
    public final String schema;

    enum ArgType {
        LIST_INIT,
        ADD_NEW,
        HAS_FAIL
    }

    class DnsReqTask implements Runnable {
        DnsReqTask() {
        }

        public void run() {
            HttpDns.this.c();
        }
    }

    class DnsReqTaskStrong implements Runnable {
        private ConfigSelector a;

        public void run() {
            HttpDns.this.b(this.a);
        }

        public DnsReqTaskStrong(ConfigSelector opt) {
            this.a = opt;
        }
    }

    public class GetAllByNameHelper {
        private Map<String, SimpleLocalDnsModel> a = new HashMap(4);

        public GetAllByNameHelper() {
        }

        public InetAddress[] getAllByName(String host) {
            if (!TextUtils.equals("T", TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.IPRANK_MODEL_SWITCH))) {
                return a(host);
            }
            if (!MiscUtils.grayscaleUtdidForABTest(TransportConfigureItem.IPRANK_AB_SWITCH)) {
                return DnsUtil.getAllByName(host);
            }
            LogCatUtil.info(HttpDns.TAG, "httpdns getAllByName,use ip rank,host:" + host);
            return AlipayDNSHelper.getInstance().getAllByName(host);
        }

        public InetAddress[] getAllByName(String host, TransportContext transportContext) {
            String ipH5Switch = TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.IPRANK_H5_SWITCH);
            if (transportContext != null && transportContext.bizType == 2 && !TextUtils.equals("T", ipH5Switch)) {
                LogCatUtil.debug(HttpDns.TAG, "H5 don't use ip rank");
                return a(host);
            } else if (!TextUtils.equals("T", TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.IPRANK_MODEL_SWITCH))) {
                return a(host);
            } else {
                if (!MiscUtils.grayscaleUtdidForABTest(TransportConfigureItem.IPRANK_AB_SWITCH)) {
                    return DnsUtil.getAllByName(host);
                }
                LogCatUtil.info(HttpDns.TAG, "httpdns getAllByName,use ip rank,host:" + host);
                return AlipayDNSHelper.getInstance().getAllByName(host);
            }
        }

        private InetAddress[] a(String host) {
            InetAddress[] inetAddrCache = getCache(host);
            if (inetAddrCache != null) {
                LogCatUtil.info(HttpDns.TAG, "getAllByName. From memcache get " + host + " IP");
                return inetAddrCache;
            }
            synchronized (host) {
                InetAddress[] inetAddrCache2 = getCache(host);
                if (inetAddrCache2 != null) {
                    LogCatUtil.info(HttpDns.TAG, "getAllByName. From memcache get " + host + " IP");
                    return inetAddrCache2;
                }
                InetAddress[] inetAddrCache3 = b(host);
                return inetAddrCache3;
            }
        }

        public void asyncLocalDns2Cache(String host) {
            final Future requestFuture = NetworkAsyncTaskExecutor.submit((Callable<T>) new InetAddrGetAllByNameTask<T>(host));
            if (requestFuture != null && !requestFuture.isDone()) {
                NetworkAsyncTaskExecutor.schedule((Runnable) new Runnable() {
                    public void run() {
                        if (requestFuture != null && !requestFuture.isDone()) {
                            try {
                                requestFuture.cancel(true);
                            } catch (Throwable e) {
                                LogCatUtil.warn(HttpDns.TAG, "asyncRequestInetAddresses#run fail.", e);
                            }
                        }
                    }
                }, (long) TransportConfigureManager.getInstance().getIntValue(TransportConfigureItem.GET_ALL_BY_NAME_TIME_OUT), TimeUnit.SECONDS);
            }
        }

        private InetAddress[] b(String host) {
            Future future = null;
            try {
                TransportContextThreadLocalUtils.addDnsType(RPCDataItems.VALUE_DT_LOCALDNS);
                int timeout = TransportConfigureManager.getInstance().getIntValue(TransportConfigureItem.GET_ALL_BY_NAME_TIME_OUT);
                future = NetworkAsyncTaskExecutor.submit((Callable<T>) new InetAddrGetAllByNameTask<T>(host));
                InetAddress[] inetAddressArr = (InetAddress[]) future.get((long) timeout, TimeUnit.SECONDS);
                a(future);
                return inetAddressArr;
            } catch (Throwable th) {
                a(future);
                throw th;
            }
        }

        public void store2Cache(String host, InetAddress[] inetAddresses) {
            if (inetAddresses != null && inetAddresses.length > 0) {
                SimpleLocalDnsModel simpleLocalDnsModel = new SimpleLocalDnsModel();
                simpleLocalDnsModel.inetAddressesCache = inetAddresses;
                simpleLocalDnsModel.expirTime = System.currentTimeMillis() + HttpDns.localDnsExpire;
                synchronized (this) {
                    this.a.put(host, simpleLocalDnsModel);
                    LogCatUtil.printInfo(HttpDns.TAG, "store2Cache. host=" + host + ", inetAddresses len=" + inetAddresses.length);
                }
            }
        }

        private static void a(Future<InetAddress[]> future) {
            if (future != null) {
                try {
                    if (!future.isDone()) {
                        future.cancel(true);
                    }
                } catch (Throwable th) {
                    LogCatUtil.info(HttpDns.TAG, "requestInetAddresses exception");
                }
            }
        }

        private static UnknownHostException a(String host, Throwable e) {
            UnknownHostException newException = new UnknownHostException("original hostname: " + host);
            try {
                newException.initCause(e);
                return newException;
            } catch (Exception e2) {
                if (e instanceof UnknownHostException) {
                    throw ((UnknownHostException) e);
                }
                throw new UnknownHostException(" host:" + host + "  message: " + e.toString());
            }
        }

        /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.net.InetAddress[] getCache(java.lang.String r8) {
            /*
                r7 = this;
                r3 = 0
                java.util.Map<java.lang.String, com.alipay.mobile.common.transport.httpdns.HttpDns$SimpleLocalDnsModel> r4 = r7.a     // Catch:{ Throwable -> 0x0033 }
                java.lang.Object r2 = r4.get(r8)     // Catch:{ Throwable -> 0x0033 }
                com.alipay.mobile.common.transport.httpdns.HttpDns$SimpleLocalDnsModel r2 = (com.alipay.mobile.common.transport.httpdns.HttpDns.SimpleLocalDnsModel) r2     // Catch:{ Throwable -> 0x0033 }
                if (r2 != 0) goto L_0x000d
                r1 = r3
            L_0x000c:
                return r1
            L_0x000d:
                boolean r4 = r2.isExpiration()     // Catch:{ Throwable -> 0x0033 }
                if (r4 == 0) goto L_0x003d
                java.lang.String r4 = "HTTP_DNS"
                java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0033 }
                java.lang.String r6 = "getCache. cache expire host: "
                r5.<init>(r6)     // Catch:{ Throwable -> 0x0033 }
                java.lang.StringBuilder r5 = r5.append(r8)     // Catch:{ Throwable -> 0x0033 }
                java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x0033 }
                com.alipay.mobile.common.transport.utils.LogCatUtil.info(r4, r5)     // Catch:{ Throwable -> 0x0033 }
                monitor-enter(r7)     // Catch:{ Throwable -> 0x0033 }
                java.util.Map<java.lang.String, com.alipay.mobile.common.transport.httpdns.HttpDns$SimpleLocalDnsModel> r4 = r7.a     // Catch:{ all -> 0x0030 }
                r4.remove(r8)     // Catch:{ all -> 0x0030 }
                monitor-exit(r7)     // Catch:{ all -> 0x0030 }
                r1 = r3
                goto L_0x000c
            L_0x0030:
                r4 = move-exception
                monitor-exit(r7)     // Catch:{ all -> 0x0030 }
                throw r4     // Catch:{ Throwable -> 0x0033 }
            L_0x0033:
                r0 = move-exception
                java.lang.String r4 = "HTTP_DNS"
                java.lang.String r5 = "getCache fail"
                com.alipay.mobile.common.transport.utils.LogCatUtil.warn(r4, r5, r0)
                r1 = r3
                goto L_0x000c
            L_0x003d:
                java.net.InetAddress[] r1 = r2.inetAddressesCache     // Catch:{ Throwable -> 0x0033 }
                if (r1 == 0) goto L_0x0044
                int r4 = r1.length     // Catch:{ Throwable -> 0x0033 }
                if (r4 > 0) goto L_0x0046
            L_0x0044:
                r1 = r3
                goto L_0x000c
            L_0x0046:
                java.lang.String r4 = "localCacheDns"
                com.alipay.mobile.common.transport.utils.TransportContextThreadLocalUtils.addDnsType(r4)     // Catch:{ Throwable -> 0x0033 }
                java.lang.String r4 = "HTTP_DNS"
                java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0033 }
                java.lang.String r6 = "getCache. host="
                r5.<init>(r6)     // Catch:{ Throwable -> 0x0033 }
                java.lang.StringBuilder r5 = r5.append(r8)     // Catch:{ Throwable -> 0x0033 }
                java.lang.String r6 = ", address len="
                java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Throwable -> 0x0033 }
                int r6 = r1.length     // Catch:{ Throwable -> 0x0033 }
                java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Throwable -> 0x0033 }
                java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x0033 }
                com.alipay.mobile.common.transport.utils.LogCatUtil.printInfo(r4, r5)     // Catch:{ Throwable -> 0x0033 }
                goto L_0x000c
            */
            throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.transport.httpdns.HttpDns.GetAllByNameHelper.getCache(java.lang.String):java.net.InetAddress[]");
        }

        /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void clearCache() {
            /*
                r4 = this;
                java.util.Map<java.lang.String, com.alipay.mobile.common.transport.httpdns.HttpDns$SimpleLocalDnsModel> r1 = r4.a     // Catch:{ Throwable -> 0x0018 }
                if (r1 == 0) goto L_0x000c
                java.util.Map<java.lang.String, com.alipay.mobile.common.transport.httpdns.HttpDns$SimpleLocalDnsModel> r1 = r4.a     // Catch:{ Throwable -> 0x0018 }
                boolean r1 = r1.isEmpty()     // Catch:{ Throwable -> 0x0018 }
                if (r1 == 0) goto L_0x000d
            L_0x000c:
                return
            L_0x000d:
                monitor-enter(r4)     // Catch:{ Throwable -> 0x0018 }
                java.util.Map<java.lang.String, com.alipay.mobile.common.transport.httpdns.HttpDns$SimpleLocalDnsModel> r1 = r4.a     // Catch:{ all -> 0x0015 }
                r1.clear()     // Catch:{ all -> 0x0015 }
                monitor-exit(r4)     // Catch:{ all -> 0x0015 }
                goto L_0x000c
            L_0x0015:
                r1 = move-exception
                monitor-exit(r4)     // Catch:{ all -> 0x0015 }
                throw r1     // Catch:{ Throwable -> 0x0018 }
            L_0x0018:
                r0 = move-exception
                java.lang.String r1 = "HTTP_DNS"
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                java.lang.String r3 = "clearCache error, msg: "
                r2.<init>(r3)
                java.lang.String r3 = r0.toString()
                java.lang.StringBuilder r2 = r2.append(r3)
                java.lang.String r2 = r2.toString()
                com.alipay.mobile.common.transport.utils.LogCatUtil.error(r1, r2)
                goto L_0x000c
            */
            throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.transport.httpdns.HttpDns.GetAllByNameHelper.clearCache():void");
        }

        /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void removeCache(java.lang.String r5) {
            /*
                r4 = this;
                java.util.Map<java.lang.String, com.alipay.mobile.common.transport.httpdns.HttpDns$SimpleLocalDnsModel> r1 = r4.a     // Catch:{ Throwable -> 0x0018 }
                if (r1 == 0) goto L_0x000c
                java.util.Map<java.lang.String, com.alipay.mobile.common.transport.httpdns.HttpDns$SimpleLocalDnsModel> r1 = r4.a     // Catch:{ Throwable -> 0x0018 }
                boolean r1 = r1.isEmpty()     // Catch:{ Throwable -> 0x0018 }
                if (r1 == 0) goto L_0x000d
            L_0x000c:
                return
            L_0x000d:
                monitor-enter(r4)     // Catch:{ Throwable -> 0x0018 }
                java.util.Map<java.lang.String, com.alipay.mobile.common.transport.httpdns.HttpDns$SimpleLocalDnsModel> r1 = r4.a     // Catch:{ all -> 0x0015 }
                r1.remove(r5)     // Catch:{ all -> 0x0015 }
                monitor-exit(r4)     // Catch:{ all -> 0x0015 }
                goto L_0x000c
            L_0x0015:
                r1 = move-exception
                monitor-exit(r4)     // Catch:{ all -> 0x0015 }
                throw r1     // Catch:{ Throwable -> 0x0018 }
            L_0x0018:
                r0 = move-exception
                java.lang.String r1 = "HTTP_DNS"
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                java.lang.String r3 = "removeCache error, host:"
                r2.<init>(r3)
                java.lang.StringBuilder r2 = r2.append(r5)
                java.lang.String r3 = "  msg: "
                java.lang.StringBuilder r2 = r2.append(r3)
                java.lang.String r3 = r0.toString()
                java.lang.StringBuilder r2 = r2.append(r3)
                java.lang.String r2 = r2.toString()
                com.alipay.mobile.common.transport.utils.LogCatUtil.error(r1, r2)
                goto L_0x000c
            */
            throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.transport.httpdns.HttpDns.GetAllByNameHelper.removeCache(java.lang.String):void");
        }
    }

    public class HttpdnsIP {
        private String a;
        private long b;
        private long c;
        public String cname;
        private int d;
        public HttpdnsIPEntry[] ipEntries;
        public int ttd;

        public HttpdnsIP() {
            this.ttd = 3;
        }

        public HttpdnsIP(String _ip, long _ttl) {
            this.a = _ip;
            this.b = _ttl;
            this.c = System.currentTimeMillis();
            this.ttd = 3;
        }

        public boolean isTimeOut(long validTime) {
            if (System.currentTimeMillis() > this.c + validTime) {
                return true;
            }
            return false;
        }

        public boolean isTimeOut() {
            if (System.currentTimeMillis() > this.b) {
                return true;
            }
            return false;
        }

        public boolean isDie() {
            if (System.currentTimeMillis() > this.c + ((long) (this.ttd * 24 * 60 * 60 * 1000))) {
                return true;
            }
            return false;
        }

        public String getIp() {
            try {
                if (!TextUtils.isEmpty(this.cname)) {
                    return getIps()[0];
                }
                return this.a;
            } catch (Throwable ex) {
                LogCatUtil.error((String) HttpDns.TAG, "getIp ex:" + ex.toString());
                return "";
            }
        }

        public void setIp(String ip) {
            this.a = ip;
        }

        public long getTtl() {
            return this.b;
        }

        public void setTtl(long ttl) {
            this.b = ttl;
        }

        public long getTime() {
            return this.c;
        }

        public void setTime(long time) {
            this.c = time;
        }

        public String getCname() {
            return this.cname;
        }

        public void setCname(String cname2) {
            this.cname = cname2;
        }

        public String[] getIps() {
            try {
                HttpdnsIPEntry[] httpdnsIPEntries = getIpEntries();
                if (httpdnsIPEntries == null) {
                    return null;
                }
                String[] ips = new String[httpdnsIPEntries.length];
                for (int i = 0; i < httpdnsIPEntries.length; i++) {
                    ips[i] = httpdnsIPEntries[i].ip;
                }
                LogCatUtil.debug(HttpDns.TAG, "getIps :" + Arrays.toString(ips));
                return ips;
            } catch (Throwable ex) {
                LogCatUtil.error((String) HttpDns.TAG, "getIps ex:" + ex.toString());
                LogCatUtil.info(HttpDns.TAG, "getIps return null");
                return null;
            }
        }

        public HttpdnsIPEntry[] getHttpdnsIpEntrys() {
            try {
                if (!TextUtils.isEmpty(this.cname)) {
                    InetAddress[] ips = DnsUtil.getAllByName(this.cname);
                    HttpdnsIPEntry[] httpdnsIPEntries = new HttpdnsIPEntry[ips.length];
                    for (int i = 0; i < ips.length; i++) {
                        httpdnsIPEntries[i] = new HttpdnsIPEntry(String.valueOf(ips[i].getHostAddress()), -1);
                    }
                    LogCatUtil.debug(HttpDns.TAG, "getHttpdnsIpEntrys cname:" + this.cname + ",local dns result:" + Arrays.toString(httpdnsIPEntries));
                    setIpEntries(httpdnsIPEntries);
                    setIp(httpdnsIPEntries[0].ip);
                    return httpdnsIPEntries;
                }
                if (this.ipEntries != null && this.ipEntries.length > 0) {
                    ArrayList tmpGrList = new ArrayList();
                    List entryList = new ArrayList(this.ipEntries.length);
                    for (int i2 = 0; i2 < this.ipEntries.length; i2++) {
                        if (TextUtils.equals(this.ipEntries[i2].group, StrategyItemParser.GROUP_VALUE)) {
                            tmpGrList.add(this.ipEntries[i2]);
                            if (i2 != this.ipEntries.length - 1) {
                            }
                        }
                        int len = tmpGrList.size();
                        if (len > 0) {
                            entryList.add(tmpGrList.get(new Random().nextInt(len)));
                            tmpGrList = new ArrayList();
                        }
                        if (!TextUtils.equals(this.ipEntries[i2].group, StrategyItemParser.GROUP_VALUE)) {
                            entryList.add(this.ipEntries[i2]);
                        }
                    }
                    return (HttpdnsIPEntry[]) entryList.toArray(new HttpdnsIPEntry[entryList.size()]);
                }
                LogCatUtil.info(HttpDns.TAG, "getHttpdnsIpEntrys return null");
                return null;
            } catch (Throwable ex) {
                LogCatUtil.error((String) HttpDns.TAG, "getHttpdnsIpEntrys ex:" + ex.toString());
            }
        }

        public HttpdnsIPEntry[] getIpEntries() {
            if (this.ipEntries != null) {
                return this.ipEntries;
            }
            return getHttpdnsIpEntrys();
        }

        public void setIpEntries(HttpdnsIPEntry[] ipEntries2) {
            this.ipEntries = ipEntries2;
        }

        public int getNetType() {
            return this.d;
        }

        public void setNetType(int netType) {
            this.d = netType;
        }

        public int getTtd() {
            return this.ttd;
        }

        public void setTtd(int ttd2) {
            this.ttd = ttd2;
        }

        public String toString() {
            return "HttpdnsIP{ip='" + this.a + '\'' + ", ttl=" + this.b + ", time=" + this.c + ", netType=" + this.d + ", ipEntries=" + Arrays.toString(this.ipEntries) + ", cname='" + this.cname + '\'' + ", ttd=" + this.ttd + '}';
        }
    }

    class InetAddrGetAllByNameTask implements Callable<InetAddress[]> {
        private String a;

        InetAddrGetAllByNameTask(String host) {
            this.a = host;
        }

        public InetAddress[] call() {
            InetAddress[] inetAddresses = DnsUtil.getAllByName(this.a);
            if (inetAddresses != null) {
                try {
                    if (inetAddresses.length > 0) {
                        LogCatUtil.info(HttpDns.TAG, "InetAddrGetAllByNameTask#call. From local dns get " + this.a + ", ip count: " + inetAddresses.length);
                        HttpDns.this.getGetAllByNameHelper().store2Cache(this.a, inetAddresses);
                    }
                } catch (Throwable e) {
                    LogCatUtil.warn(HttpDns.TAG, "InetAddrGetAllByNameTask#call fail.", e);
                }
            }
            return inetAddresses;
        }
    }

    class SimpleLocalDnsModel {
        long expirTime = -1;
        InetAddress[] inetAddressesCache;

        SimpleLocalDnsModel() {
        }

        public boolean isExpiration() {
            return System.currentTimeMillis() > this.expirTime;
        }
    }

    class Singleton {
        static HttpDns instance = new HttpDns();

        private Singleton() {
        }
    }

    public static HttpDns getInstance() {
        return Singleton.instance;
    }

    public ArrayList<String> getHosts() {
        return this.a;
    }

    public void init(Context context) {
        this.f = context;
        HttpContextExtend.createInstance(context);
    }

    private HttpDns() {
        this.schema = AjxHttpLoader.DOMAIN_HTTP;
        this.g = 0;
        this.httpServerUrl = null;
        this.h = 0;
        this.i = System.currentTimeMillis();
        this.j = 3600000;
        this.k = false;
        this.httpdns_domain = DnsUtil.getAmdcHost();
        this.httpServerUrl = new StringBuilder(AjxHttpLoader.DOMAIN_HTTP).append(this.httpdns_domain).append("/query").toString();
        this.path = "/query";
        this.httpdns_port = 80;
        this.fail = false;
        this.b = "";
        this.hashost = true;
        this.c = ArgType.LIST_INIT;
        this.a = new ArrayList<>();
        this.h = 0;
        this.i = System.currentTimeMillis();
        this.k = false;
    }

    private synchronized String a(int count) {
        String reqUrl;
        try {
            reqUrl = a();
            if (TextUtils.isEmpty(reqUrl)) {
                reqUrl = b(count);
            }
            if (DnsUtil.isUseSign()) {
                reqUrl = reqUrl.replace("query", "squery");
            }
            if (!this.a.contains(this.httpdns_domain)) {
                this.a.add(this.httpdns_domain);
            }
        } catch (Throwable th) {
            if (!this.a.contains(this.httpdns_domain)) {
                this.a.add(this.httpdns_domain);
            }
            throw th;
        }
        return reqUrl;
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String b(int r8) {
        /*
            r7 = this;
            com.alipay.mobile.common.transport.httpdns.AlipayHttpDnsClient r4 = com.alipay.mobile.common.transport.httpdns.AlipayHttpDnsClient.getDnsClient()     // Catch:{ Throwable -> 0x00a5 }
            java.lang.String r5 = r7.httpdns_domain     // Catch:{ Throwable -> 0x00a5 }
            com.alipay.mobile.common.transport.httpdns.HttpDns$HttpdnsIP r3 = r4.queryLocalIPByHost(r5)     // Catch:{ Throwable -> 0x00a5 }
            if (r3 == 0) goto L_0x0011
            java.lang.String r1 = r7.a(r8, r3)     // Catch:{ Throwable -> 0x00a5 }
        L_0x0010:
            return r1
        L_0x0011:
            android.content.Context r4 = com.alipay.mobile.common.transport.utils.TransportEnvUtil.getContext()     // Catch:{ Throwable -> 0x00a5 }
            java.lang.String r4 = com.alipay.mobile.common.transport.utils.MpaasPropertiesUtil.getWorkspaceId(r4)     // Catch:{ Throwable -> 0x00a5 }
            boolean r4 = android.text.TextUtils.isEmpty(r4)     // Catch:{ Throwable -> 0x00a5 }
            if (r4 != 0) goto L_0x0022
            java.lang.String r1 = r7.httpServerUrl     // Catch:{ Throwable -> 0x00a5 }
            goto L_0x0010
        L_0x0022:
            com.alipay.mobile.common.transport.config.TransportConfigureManager r4 = com.alipay.mobile.common.transport.config.TransportConfigureManager.getInstance()     // Catch:{ Throwable -> 0x00a5 }
            com.alipay.mobile.common.transport.config.TransportConfigureItem r5 = com.alipay.mobile.common.transport.config.TransportConfigureItem.HTTP_DNS_AMDC_IP     // Catch:{ Throwable -> 0x00a5 }
            java.lang.String r0 = r4.getStringValue(r5)     // Catch:{ Throwable -> 0x00a5 }
            boolean r4 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Throwable -> 0x00a5 }
            if (r4 == 0) goto L_0x0035
            java.lang.String r1 = r7.httpServerUrl     // Catch:{ Throwable -> 0x00a5 }
            goto L_0x0010
        L_0x0035:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00a5 }
            java.lang.String r5 = "http://"
            r4.<init>(r5)     // Catch:{ Throwable -> 0x00a5 }
            java.lang.StringBuilder r4 = r4.append(r0)     // Catch:{ Throwable -> 0x00a5 }
            java.lang.String r5 = ":"
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Throwable -> 0x00a5 }
            int r5 = r7.httpdns_port     // Catch:{ Throwable -> 0x00a5 }
            java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch:{ Throwable -> 0x00a5 }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Throwable -> 0x00a5 }
            java.lang.String r5 = r7.path     // Catch:{ Throwable -> 0x00a5 }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Throwable -> 0x00a5 }
            java.lang.String r1 = r4.toString()     // Catch:{ Throwable -> 0x00a5 }
            java.lang.String r4 = r7.httpdns_domain     // Catch:{ UnknownHostException -> 0x008b }
            java.net.InetAddress[] r4 = java.net.InetAddress.getAllByName(r4)     // Catch:{ UnknownHostException -> 0x008b }
            java.util.List r4 = java.util.Arrays.asList(r4)     // Catch:{ UnknownHostException -> 0x008b }
            java.lang.String r5 = "0.0.0.0"
            java.net.InetAddress r5 = java.net.InetAddress.getByName(r5)     // Catch:{ UnknownHostException -> 0x008b }
            boolean r4 = r4.contains(r5)     // Catch:{ UnknownHostException -> 0x008b }
            if (r4 != 0) goto L_0x0073
            java.lang.String r1 = r7.httpServerUrl     // Catch:{ UnknownHostException -> 0x008b }
            goto L_0x0010
        L_0x0073:
            java.lang.String r4 = "HTTP_DNS"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ UnknownHostException -> 0x008b }
            java.lang.String r6 = "isp break,use embedded ip,url= "
            r5.<init>(r6)     // Catch:{ UnknownHostException -> 0x008b }
            java.lang.StringBuilder r5 = r5.append(r1)     // Catch:{ UnknownHostException -> 0x008b }
            java.lang.String r5 = r5.toString()     // Catch:{ UnknownHostException -> 0x008b }
            com.alipay.mobile.common.transport.utils.LogCatUtil.debug(r4, r5)     // Catch:{ UnknownHostException -> 0x008b }
            r4 = 1
            r7.k = r4     // Catch:{ UnknownHostException -> 0x008b }
            goto L_0x0010
        L_0x008b:
            r4 = move-exception
            java.lang.String r4 = "HTTP_DNS"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00a5 }
            java.lang.String r6 = "UnknownHostException,use embedded ip,url= "
            r5.<init>(r6)     // Catch:{ Throwable -> 0x00a5 }
            java.lang.StringBuilder r5 = r5.append(r1)     // Catch:{ Throwable -> 0x00a5 }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x00a5 }
            com.alipay.mobile.common.transport.utils.LogCatUtil.debug(r4, r5)     // Catch:{ Throwable -> 0x00a5 }
            r4 = 1
            r7.k = r4     // Catch:{ Throwable -> 0x00a5 }
            goto L_0x0010
        L_0x00a5:
            r2 = move-exception
            java.lang.String r4 = "HTTP_DNS"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "getOnlieUrl ex:"
            r5.<init>(r6)
            java.lang.String r6 = r2.toString()
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r5 = r5.toString()
            com.alipay.mobile.common.transport.utils.LogCatUtil.error(r4, r5)
            java.lang.String r1 = r7.httpServerUrl
            goto L_0x0010
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.transport.httpdns.HttpDns.b(int):java.lang.String");
    }

    private String a(int count, HttpdnsIP httpdnsIP) {
        String[] ips = httpdnsIP.getIps();
        int length = ips.length;
        if (length <= 0) {
            LogCatUtil.info(TAG, "length <= 0,use host " + this.httpdns_domain);
            return this.httpServerUrl;
        } else if (count == 1) {
            return new StringBuilder(AjxHttpLoader.DOMAIN_HTTP).append(ips[0]).append(":").append(String.valueOf(this.httpdns_port)).append(this.path).toString();
        } else if (count != 2) {
            return this.httpServerUrl;
        } else {
            if (length < 2) {
                return this.httpServerUrl;
            }
            return new StringBuilder(AjxHttpLoader.DOMAIN_HTTP).append(ips[1]).append(":").append(String.valueOf(this.httpdns_port)).append(this.path).toString();
        }
    }

    private String a() {
        try {
            if (!MiscUtils.isDebugger(this.f)) {
                return "";
            }
            String urlFromSetting = DnsUtil.getHttpdnsServerUrl(this.f);
            if (TextUtils.isEmpty(urlFromSetting)) {
                return null;
            }
            String host = new URL(urlFromSetting).getHost();
            if (!this.a.contains(host)) {
                this.a.add(host);
            }
            LogCatUtil.debug(TAG, "debug mode,url:" + urlFromSetting);
            return urlFromSetting;
        } catch (Throwable ex) {
            LogCatUtil.error((String) TAG, ex);
            return null;
        }
    }

    public synchronized void setHosts(String mutli_host, boolean updateFromServer) {
        if (mutli_host != null) {
            if (this.a != null) {
                a(mutli_host);
                if (this.hashost && updateFromServer) {
                    this.c = ArgType.LIST_INIT;
                    if (AlipayHttpDnsClient.getDnsClient().getFlag() == 1) {
                        ExtTransportOffice.getInstance().notifyPush2UpdateInfo(this.f, true);
                        LogCatUtil.warn((String) TAG, (String) "setHosts, workMode: NO_COMPLETE, not allowed httpdns request,notify push to update ...");
                    } else {
                        delayRequestStrong(1);
                        LogCatUtil.debug(TAG, "Start polling everything");
                    }
                }
            }
        }
    }

    private void a(String mutli_host) {
        String[] arr_host = mutli_host.split(",");
        if (arr_host != null) {
            for (int i2 = 0; i2 < arr_host.length; i2++) {
                if (DnsUtil.isLogicHost(arr_host[i2])) {
                    this.hashost = true;
                    if (!this.a.contains(arr_host[i2])) {
                        this.a.add(arr_host[i2]);
                    }
                }
            }
        }
    }

    public synchronized void setHost(String host) {
        if (this.a != null) {
            if (DnsUtil.isLogicHost(host) && (host.contains("cdn") || host.contains("mobilegw") || host.contains("alipayobjects"))) {
                this.c = ArgType.ADD_NEW;
                this.hashost = true;
                if (!this.a.contains(host)) {
                    this.a.add(host);
                }
                httpDnsRequest();
            }
        }
    }

    public synchronized List<String> getDomains() {
        List domains;
        domains = new ArrayList();
        if (this.c == ArgType.LIST_INIT || this.c == ArgType.HAS_FAIL) {
            domains = this.a;
        } else if (this.c == ArgType.ADD_NEW) {
            int n = this.a.size();
            if (n > 1) {
                domains.add(this.a.get(n - 1));
            }
        }
        return domains;
    }

    private StrategyRequest a(ConfigSelector opt) {
        StrategyRequest requestObj = new StrategyRequest();
        if (opt != ConfigSelector.GET_ALL) {
            requestObj.setS(opt.ordinal());
            LogCatUtil.debug(TAG, "Request Opt:" + opt);
        }
        if (TextUtils.equals(TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.HTTP_DNS_V2), "T")) {
            requestObj.setDg(DnsUtil.getDomainGroup());
        } else {
            List domains = getDomains();
            if (domains == null || domains.size() == 0) {
                LogCatUtil.info(TAG, "domains is null");
            } else {
                requestObj.setDomains(domains);
            }
        }
        String uid = UserInfoUtil.getLastUserId();
        if (!TextUtils.isEmpty(uid)) {
            requestObj.setUid(uid);
        }
        requestObj.setClientVersion(a(this.f));
        try {
            requestObj.setUtdid(HttpContextExtend.getInstance().getDid());
        } catch (Exception ex) {
            LogCatUtil.error((String) TAG, (Throwable) ex);
        }
        String wsid = DnsUtil.getWsid();
        if (!TextUtils.isEmpty(wsid)) {
            requestObj.setWsid(wsid);
        }
        String configVersion = DnsUtil.getConfigVersion();
        if (!TextUtils.isEmpty(configVersion)) {
            requestObj.setConfigVersion(configVersion);
        }
        requestObj.setNetType(NetworkUtils.getNetworkType(this.f));
        return requestObj;
    }

    public void sendRequest() {
        sendRequest(ConfigSelector.GET_ALL);
    }

    public void sendRequest(ConfigSelector opt) {
        if (b()) {
            long dnsRTT = 0;
            long dnsStoreTime = 0;
            String clientIp = "";
            boolean oversea = false;
            int ttd = 0;
            String urlStr = this.httpServerUrl;
            int retryCount = TransportConfigureManager.getInstance().getIntValue(TransportConfigureItem.HTTPDNS_RETRY_COUNT);
            for (int i2 = 1; i2 <= retryCount; i2++) {
                if (i2 != 1) {
                    try {
                        Thread.sleep(BalloonLayout.DEFAULT_DISPLAY_DURATION);
                    } catch (Throwable th) {
                        Throwable th2 = th;
                        a(dnsRTT, dnsStoreTime, clientIp, oversea, ttd, urlStr);
                        throw th2;
                    }
                }
                LogCatUtil.info(TAG, "SEND HTTP_DNS REQUEST : " + i2);
                HttpClient httpClient = new HttpClient(this.f);
                dnsRTT = System.currentTimeMillis();
                urlStr = a(i2);
                LogCatUtil.debug(TAG, "请求url: " + urlStr);
                StrategyResponse resp = httpClient.getStrategyFromServer(urlStr, a(opt));
                if (resp != null) {
                    dnsRTT = System.currentTimeMillis() - dnsRTT;
                    LogCatUtil.debug(TAG, "RECEIVED DNS INFO");
                    clientIp = resp.getClientIp();
                    oversea = resp.isOversea();
                    ttd = resp.getTtd();
                    notifyUpdateConfig(resp.getConf(), opt);
                    Map parseResult = resp.getDns();
                    if (parseResult == null) {
                        LogCatUtil.info(TAG, "解析结果为null");
                        a(dnsRTT, dnsStoreTime, clientIp, oversea, ttd, urlStr);
                        return;
                    }
                    dnsStoreTime = a(dnsStoreTime, parseResult);
                    this.d.saveLastUpdateTime();
                    a(dnsRTT, dnsStoreTime, clientIp, oversea, ttd, urlStr);
                    return;
                }
                a(dnsRTT, dnsStoreTime, clientIp, oversea, ttd, urlStr);
            }
        }
    }

    private long a(long dnsStoreTime, Map<String, HttpdnsIP> parseResult) {
        if (this.d == null) {
            return dnsStoreTime;
        }
        this.fail = false;
        this.b = "";
        long dnsStoreTime2 = SystemClock.uptimeMillis();
        this.d.storeIp2CacheAndDB(parseResult);
        return SystemClock.uptimeMillis() - dnsStoreTime2;
    }

    private boolean b() {
        if (!TextUtils.equals(TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.DNS_SWITCH), "T")) {
            LogCatUtil.debug(TAG, "dnsSwitch is off");
            return false;
        } else if (!NetworkUtils.isNetworkAvailable(this.f)) {
            LogCatUtil.info(TAG, "network is not available...");
            return false;
        } else if (!MiscUtils.isInAlipayClient(this.f)) {
            return true;
        } else {
            if (AlipayHttpDnsClient.getDnsClient().getFlag() == 1) {
                ExtTransportOffice.getInstance().notifyPush2UpdateInfo(this.f, false);
                LogCatUtil.warn((String) TAG, (String) "sendRequest,workMode: NO_COMPLETE, not allowed httpdns request,notify push to update...");
                return false;
            } else if (!MiscUtils.isRealPushProcess(this.f)) {
                LogCatUtil.debug(TAG, "not push process, ingore httpdns request");
                return false;
            } else {
                if (this.h > 20) {
                    if (this.i + this.j < System.currentTimeMillis()) {
                        this.h = 0;
                        this.i = System.currentTimeMillis();
                    } else {
                        LogCatUtil.debug(TAG, "already request 20 an hour,return");
                        return false;
                    }
                }
                this.h++;
                return true;
            }
        }
    }

    public void notifyUpdateConfig(final String coreConfig, ConfigSelector opt) {
        if (!TextUtils.isEmpty(coreConfig) && opt != ConfigSelector.GET_IPLIST_ONLY) {
            NetworkAsyncTaskExecutor.executeLowPri(new Runnable() {
                public void run() {
                    SwitchMonitorLogUtil.monitorCoreSwitchRecvLog(HttpDns.this.f, coreConfig, SwitchMonitorLogUtil.SRC_AMDC);
                    NwSharedSwitchUtil.putSwitchSrc(SwitchMonitorLogUtil.SRC_AMDC);
                    TransportConfigureManager.getInstance().updateConfig(HttpDns.this.f, coreConfig, true);
                    SwitchMonitorLogUtil.monitorSwitchUpdatedLog(HttpDns.this.f, SwitchMonitorLogUtil.SRC_AMDC);
                    LogCatUtil.debug(HttpDns.TAG, "收到服务器紧急开关：" + coreConfig);
                }
            });
        }
    }

    private void a(long dnsRTT, long dnsStoreTime, String clientIp, boolean oversea, int ttd, String reqUrl) {
        try {
            Performance pf = new TransportPerformance();
            pf.setSubType("HTTPDNS");
            pf.setParam1(MonitorLoggerUtils.getLogBizType("HTTPDNS"));
            pf.setParam2("INFO");
            pf.setParam3("http");
            pf.getExtPramas().put(RPCDataItems.DNS_STORE_TIME, dnsStoreTime + RPCDataParser.TIME_MS);
            pf.getExtPramas().put(RPCDataItems.DNS_RTT, dnsRTT + RPCDataParser.TIME_MS);
            pf.getExtPramas().put("RESULT", this.fail ? "F" : "T");
            pf.getExtPramas().put(RPCDataItems.CLIENT_IP, clientIp);
            pf.getExtPramas().put("OVERSEA", String.valueOf(oversea));
            pf.getExtPramas().put("TTD", String.valueOf(ttd));
            pf.getExtPramas().put("REQNUM", String.valueOf(this.h));
            pf.getExtPramas().put(MonitorItemConstants.KEY_URL, reqUrl);
            pf.getExtPramas().put("BACKUP", String.valueOf(this.k));
            if (!TextUtils.isEmpty(DnsUtil.getConfigVersion())) {
                pf.getExtPramas().put("CONFIGVER", DnsUtil.getConfigVersion());
            }
            if (MiscUtils.isAtFrontDesk(TransportEnvUtil.getContext())) {
                pf.getExtPramas().put(RPCDataItems.GROUND, "FG");
            } else {
                pf.getExtPramas().put(RPCDataItems.GROUND, "BG");
            }
            if (!TextUtils.isEmpty(this.b)) {
                pf.getExtPramas().put("ERROR", this.b);
            }
            this.fail = false;
            this.b = "";
            this.k = false;
            LogCatUtil.debug(TAG, pf.toString());
            MonitorLoggerUtils.uploadPerfLog(pf);
        } catch (Throwable ex) {
            LogCatUtil.error((String) TAG, ex);
        }
    }

    private static String a(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            if (info != null) {
                return info.versionName;
            }
            return "";
        } catch (Exception ex) {
            LogCatUtil.error(TAG, "getApplicationVersion exception", ex);
            return "";
        }
    }

    public InetAddress[] getAllByNameFromInetAddr(String host) {
        return getGetAllByNameHelper().getAllByName(host);
    }

    public InetAddress[] getAllByNameFromInetAddr(String host, TransportContext transportContext) {
        return getGetAllByNameHelper().getAllByName(host, transportContext);
    }

    public synchronized void setErrorByHost(String host) {
        AlipayHttpDnsClient.getDnsClient().httpdnsStorage.deleteIpByHost(host);
        this.c = ArgType.HAS_FAIL;
        httpDnsRequestStrong();
    }

    public synchronized void cleanErrorIp(String host, String ip) {
        AlipayHttpDnsClient.getDnsClient().httpdnsStorage.deleteSingleIpFromDB(host, ip);
        this.c = ArgType.HAS_FAIL;
        httpDnsRequest();
    }

    public void requestWeak(ThreadType type) {
        if (type == ThreadType.HTTPDNSREQUEST_TIMEOUT || type == ThreadType.HTTPDNSREQUEST_INIT) {
            if (this.a != null && this.a.size() != 0) {
                this.hashost = true;
                this.c = ArgType.LIST_INIT;
                httpDnsRequest();
            }
        } else if (type == ThreadType.HTTPDNSREQUEST_NEWADD && this.a != null && this.a.size() != 0) {
            this.hashost = true;
            this.c = ArgType.ADD_NEW;
            httpDnsRequest();
        }
    }

    public void httpDnsRequest() {
        if (this.hashost) {
            addTaskToPool();
        }
    }

    public void addTaskToPool() {
        NetworkAsyncTaskExecutor.execute(new DnsReqTask());
    }

    public void requestStrong() {
        if (this.a != null && this.a.size() != 0) {
            this.hashost = true;
            this.c = ArgType.LIST_INIT;
            httpDnsRequestStrong();
        }
    }

    public void delayRequestStrong(int delaySec) {
        delayRequestStrong(delaySec, ConfigSelector.GET_ALL);
    }

    public void delayRequestStrong(final int delaySec, final ConfigSelector opt) {
        if (this.a != null && this.a.size() != 0) {
            this.hashost = true;
            this.c = ArgType.LIST_INIT;
            NetworkAsyncTaskExecutor.execute(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep((long) (delaySec * 1000));
                    } catch (InterruptedException e) {
                        LogCatUtil.warn((String) HttpDns.TAG, e.toString());
                    }
                    HttpDns.this.httpDnsRequestStrong(opt);
                }
            });
        }
    }

    public void httpDnsRequestStrong() {
        httpDnsRequestStrong(ConfigSelector.GET_ALL);
    }

    public void httpDnsRequestStrong(ConfigSelector opt) {
        if (this.hashost) {
            addStrongTaskToPool(opt);
        }
    }

    public void addStrongTaskToPool() {
        addStrongTaskToPool(ConfigSelector.GET_ALL);
    }

    public void addStrongTaskToPool(ConfigSelector opt) {
        NetworkAsyncTaskExecutor.execute(new DnsReqTaskStrong(opt));
    }

    /* access modifiers changed from: private */
    public void c() {
        if (System.currentTimeMillis() - this.d.getLastUpdateTime() < Long.valueOf(TransportConfigureManager.getInstance().getLongValue(TransportConfigureItem.HTTPDNS_REQUEST_INTERVAL)).longValue()) {
            LogCatUtil.info(TAG, "weakSyncSendRequest ,(System.currentTimeMillis() - reqLastTime) < 10m, return.");
            return;
        }
        synchronized (this) {
            if (this.g == 1) {
                LogCatUtil.info(TAG, "REQ_STATUS_BUSY, return.");
                return;
            }
            this.g = 1;
            try {
                LogCatUtil.info(TAG, "开始执行弱请求...");
                sendRequest();
            } finally {
                this.g = 0;
            }
        }
    }

    /* access modifiers changed from: private */
    public void b(ConfigSelector opt) {
        synchronized (this) {
            if (this.g == 1) {
                LogCatUtil.info(TAG, "REQ_STATUS_BUSY, return.");
                return;
            }
            this.g = 1;
            try {
                sendRequest(opt);
            } finally {
                this.g = 0;
            }
        }
    }

    public void setDnsLocalManager(DnsLocalManager dnsLocalManager) {
        this.d = dnsLocalManager;
    }

    public DnsLocalManager getDnsLocalManager() {
        return this.d;
    }

    public GetAllByNameHelper getGetAllByNameHelper() {
        if (this.e == null) {
            this.e = new GetAllByNameHelper();
        }
        return this.e;
    }
}
