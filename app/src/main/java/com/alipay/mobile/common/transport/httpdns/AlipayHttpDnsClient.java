package com.alipay.mobile.common.transport.httpdns;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.monitor.Performance;
import com.alipay.mobile.common.transport.TransportStrategy;
import com.alipay.mobile.common.transport.config.TransportConfigureItem;
import com.alipay.mobile.common.transport.config.TransportConfigureManager;
import com.alipay.mobile.common.transport.context.TransportContext;
import com.alipay.mobile.common.transport.httpdns.HttpDns.HttpdnsIP;
import com.alipay.mobile.common.transport.httpdns.downloader.ConfigSelector;
import com.alipay.mobile.common.transport.monitor.MonitorLoggerUtils;
import com.alipay.mobile.common.transport.monitor.RPCDataItems;
import com.alipay.mobile.common.transport.monitor.TransportPerformance;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.transport.utils.NetworkAsyncTaskExecutor;
import com.alipay.mobile.common.transport.utils.NetworkUtils;
import com.alipay.mobile.common.transport.utils.SharedPreUtils;
import com.alipay.mobile.common.transport.utils.TransportContextThreadLocalUtils;
import com.alipay.mobile.common.transport.utils.TransportEnvUtil;
import com.alipay.mobile.common.utils.config.ConfigureChangedListener;
import com.alipay.mobile.common.utils.config.fmk.ConfigureItem;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;
import java.util.StringTokenizer;

public class AlipayHttpDnsClient implements DnsLocalManager, DnsService {
    public static final String TAG = "HTTP_DNS_Ahds";
    public static final byte WORK_MODEL_COMPLETE = 0;
    public static final byte WORK_MODEL_NO_COMPLETE = 1;
    public static AlipayHttpDnsClient alipayHttpDnsClient;
    static boolean dnsSwitch = false;
    private int a;
    private HttpDnsUpdateObservable b;
    private boolean c = false;
    private long d = 0;
    public HttpDns dnsClient;
    private String e = "mygw.alipay.com";
    public HttpdnsStorage httpdnsStorage;
    public Context mContext;
    NetworkManager networkManager;

    class HttpDnsUpdateObservable extends Observable {
        HttpDnsUpdateObservable() {
        }

        public void notifyObservers(Object data) {
            setChanged();
            super.notifyObservers(data);
        }
    }

    public int getFlag() {
        return this.a;
    }

    public boolean isInit() {
        return this.c;
    }

    public long getInitTime() {
        return this.d;
    }

    public static AlipayHttpDnsClient getDnsClient() {
        return alipayHttpDnsClient;
    }

    public static void dnsClientInit(Context mContext2, String hosts, int pFlag) {
        dnsClientInit(mContext2, hosts, pFlag, true);
    }

    public static void dnsClientInit(Context mContext2, String hosts, int pFlag, boolean updateFromServer) {
        if (alipayHttpDnsClient == null) {
            AlipayHttpDnsClient alipayHttpDnsClient2 = new AlipayHttpDnsClient(mContext2, pFlag);
            alipayHttpDnsClient = alipayHttpDnsClient2;
            alipayHttpDnsClient2.dnsClient.setHosts(hosts, updateFromServer);
            alipayHttpDnsClient.networkManager.setNetworkContext(mContext2);
            a();
        }
    }

    private static void a() {
        try {
            TransportConfigureManager configMgr = TransportConfigureManager.getInstance();
            if (configMgr.equalsString(TransportConfigureItem.DNS_SWITCH, "T")) {
                dnsSwitch = true;
            }
            if (dnsSwitch && MiscUtils.isDebugger(TransportEnvUtil.getContext()) && !configMgr.equalsString(TransportConfigureItem.DNS_SWITCH_DEBUG, "T")) {
                dnsSwitch = false;
                LogCatUtil.info(TAG, "AlipayHttpDnsClient init, In the closed HTTPDNS development environment");
            }
            LogCatUtil.printInfo(TAG, "AlipayHttpDnsClient init, dnsSwitch=" + dnsSwitch);
        } catch (Exception ex) {
            LogCatUtil.error(TAG, "httpdns switch exception", ex);
        }
        d();
    }

    private AlipayHttpDnsClient(Context context, int flag) {
        LogCatUtil.info(TAG, "AlipayHttpDnsClient create.");
        this.mContext = context;
        this.dnsClient = HttpDns.getInstance();
        this.dnsClient.init(this.mContext);
        this.dnsClient.setDnsLocalManager(this);
        this.networkManager = NetworkManager.getInstance();
        this.a = flag;
        this.c = true;
        this.d = System.currentTimeMillis();
        this.httpdnsStorage = HttpdnsStorage.getInstance(this.mContext);
        if (TextUtils.equals(TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.DNS_PRE_LOAD), "T")) {
            LogCatUtil.debug(TAG, "preload dns");
            reloadDns();
        }
    }

    public void registerNetworkListener(Context mContext2) {
        LogCatUtil.info(TAG, "registerNetworkListener ...");
        getDnsClient().networkManager.setNetworkContext(mContext2);
    }

    private boolean a(String host) {
        if (TextUtils.isEmpty(host)) {
            LogCatUtil.warn((String) TAG, (String) "isInHosts : host is null.");
            return false;
        }
        ArrayList localHosts = this.dnsClient.getHosts();
        if (localHosts == null) {
            LogCatUtil.warn((String) TAG, (String) "localHosts is null.");
            return false;
        } else if (localHosts.contains(host) || TransportStrategy.isAlipayHost(host) || TransportStrategy.isRpcCdnHost(host)) {
            return true;
        } else {
            return false;
        }
    }

    public HttpdnsIP getIpInfoByHost(String host) {
        if (dnsSwitch) {
            return queryLocalIPByHost(host);
        }
        LogCatUtil.info(TAG, "getIpInfoByHost. dnsSwitch is false.");
        return null;
    }

    public void refreshAll() {
        LogCatUtil.info(TAG, "refreshAll ,refreshIPList and Conf...");
        this.dnsClient.delayRequestStrong(1);
    }

    public void refreshIPListOnly() {
        LogCatUtil.info(TAG, "refreshIPList ONLY...");
        this.dnsClient.delayRequestStrong(1, ConfigSelector.GET_IPLIST_ONLY);
    }

    public void setErrorByHost(final String host) {
        try {
            if (!a(host)) {
                LogCatUtil.debug(TAG, "setErrorByHost,host:" + host + " isn't in list,ignore");
            } else if (!NetworkUtils.isNetworkAvailable(TransportEnvUtil.getContext())) {
                LogCatUtil.debug(TAG, "network isn't available,do nothing");
            } else {
                LogCatUtil.debug(TAG, "setErrorByHost,host=[" + host + "]");
                NetworkAsyncTaskExecutor.executeIO(new Runnable() {
                    public void run() {
                        AlipayHttpDnsClient.this.dnsClient.setErrorByHost(host);
                    }
                });
            }
        } catch (Throwable ex) {
            LogCatUtil.error((String) TAG, "setErrorByHost ex:" + ex.toString());
        }
    }

    public void feedback(String domain, String ip, boolean isSuccess, int rtt) {
        try {
            if (!TextUtils.equals(TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.DNS_FEEDBACK), "T") || TextUtils.isEmpty(domain) || TextUtils.isEmpty(ip)) {
                return;
            }
            if (!NetworkUtils.isNetworkAvailable(TransportEnvUtil.getContext())) {
                LogCatUtil.debug(TAG, "network unavailable,do nothing");
            } else if (isSuccess) {
            } else {
                if (a(domain)) {
                    a(domain, ip);
                }
            }
        } catch (Throwable ex) {
            LogCatUtil.error((String) TAG, "feedback ex:" + ex.toString());
        }
    }

    private static void a(String host, String ip) {
        try {
            Performance pf = new TransportPerformance();
            pf.setSubType("HTTPDNS");
            pf.setParam1(MonitorLoggerUtils.getLogBizType("HTTPDNS"));
            pf.setParam2("INFO");
            pf.setParam3("ERROR");
            pf.getExtPramas().put("domain", host);
            pf.getExtPramas().put("errIp", ip);
            MonitorLoggerUtils.uploadPerfLog(pf);
            LogCatUtil.debug(TAG, "httpdns error perf:" + pf.toString());
        } catch (Throwable ex) {
            LogCatUtil.error((String) TAG, ex);
        }
    }

    public InetAddress[] getAllByName(String host) {
        c(host);
        InetAddress[] inetAddresses = b(host);
        if (inetAddresses != null) {
            return inetAddresses;
        }
        try {
            HttpdnsIP httpdnsIP = queryLocalIPByHost(host);
            if (httpdnsIP == null || TextUtils.isEmpty(httpdnsIP.getCname())) {
                return this.dnsClient.getAllByNameFromInetAddr(host);
            }
            return this.dnsClient.getAllByNameFromInetAddr(httpdnsIP.getCname());
        } catch (UnknownHostException e2) {
            InetAddress[] presetInetAddresses = d(host);
            if (presetInetAddresses != null) {
                LogCatUtil.printInfo(TAG, "getAllByNameFromPreset Success.");
                return presetInetAddresses;
            }
            throw e2;
        }
    }

    public InetAddress[] getAllByName(String host, TransportContext transportContext) {
        c(host);
        InetAddress[] inetAddresses = b(host);
        if (inetAddresses != null) {
            return inetAddresses;
        }
        try {
            return this.dnsClient.getAllByNameFromInetAddr(host, transportContext);
        } catch (UnknownHostException e2) {
            LogCatUtil.error((String) TAG, "getAllByName,e:" + e2.toString());
            InetAddress[] presetInetAddresses = d(host);
            if (presetInetAddresses != null) {
                LogCatUtil.printInfo(TAG, "getAllByNameFromPreset Success.");
                return presetInetAddresses;
            }
            throw e2;
        }
    }

    private InetAddress[] b(String host) {
        if (dnsSwitch) {
            InetAddress[] inetAddresses = getAllByNameFromHttpDns(host);
            if (inetAddresses != null) {
                TransportContextThreadLocalUtils.addDnsType(RPCDataItems.VALUE_DT_HTTPDNS);
                return inetAddresses;
            }
        }
        return null;
    }

    private static void c(String host) {
        if (TextUtils.isEmpty(host)) {
            throw new UnknownHostException("host is null");
        } else if (TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.BLACK_LIST_DNS_HOST_NAME).contains(host)) {
            throw new UnknownHostException("Blacklist host:" + host);
        }
    }

    public InetAddress[] getAllByNameFromInetAddr(String host) {
        try {
            return this.dnsClient.getAllByNameFromInetAddr(host);
        } catch (UnknownHostException e2) {
            LogCatUtil.warn((String) TAG, (Throwable) e2);
            return null;
        }
    }

    private static InetAddress[] d(String host) {
        try {
            if (!TextUtils.equals(host, "mobilegw.alipay.com") && !TextUtils.equals(host, "mobilegwspdy.alipay.com")) {
                return null;
            }
            List addressList = b();
            if (addressList == null || addressList.isEmpty()) {
                return null;
            }
            return (InetAddress[]) addressList.toArray(new InetAddress[addressList.size()]);
        } catch (Exception ee) {
            LogCatUtil.warn((String) TAG, "getAllByNameFromPreset exception" + ee.toString());
            return null;
        }
    }

    private static List<InetAddress> b() {
        String gwIps = TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.MOBILEGW_PRE_SET_IPS);
        if (TextUtils.isEmpty(gwIps)) {
            return null;
        }
        StringTokenizer tokenizer = new StringTokenizer(gwIps, ",");
        List addressList = new ArrayList(tokenizer.countTokens());
        while (tokenizer.hasMoreTokens()) {
            String ip = tokenizer.nextToken();
            if (DnsUtil.isLogicIP(ip)) {
                addressList.add(InetAddress.getByAddress(DnsUtil.ipToBytesByReg(ip)));
            }
        }
        return addressList;
    }

    public InetAddress[] getAllByNameFromHttpDns(String host) {
        try {
            String[] ips = getIpsByHttpDns(host);
            if (ips != null && ips.length > 0) {
                LogCatUtil.printInfo(TAG, "getAllByNameFromHttpDns ,use httpdns," + host + " : " + Arrays.deepToString(ips));
                InetAddress[] inetAddresses = new InetAddress[ips.length];
                for (int i = 0; i < ips.length; i++) {
                    inetAddresses[i] = InetAddress.getByAddress(DnsUtil.ipToBytesByReg(ips[i]));
                }
                return inetAddresses;
            }
        } catch (Exception ex) {
            LogCatUtil.error(TAG, "getAllByNameFromHttpDns exception", ex);
        }
        return null;
    }

    public String[] getIpsByHttpDns(String host) {
        try {
            HttpdnsIP httpdnsIP = queryLocalIPByHost(host);
            if (httpdnsIP == null) {
                LogCatUtil.debug(TAG, "getIpsByHttpDns host : " + host + " ,HttpdnsIP=[ null ]");
                return null;
            }
            String[] ips = httpdnsIP.getIps();
            LogCatUtil.debug(TAG, "getIpsByHttpDns host : " + host + " ,ips=[" + Arrays.toString(ips) + "]");
            return ips;
        } catch (Throwable ex) {
            LogCatUtil.error((String) TAG, "getIpsByHttpDns ex" + ex.toString());
            return null;
        }
    }

    public HttpdnsIP queryLocalIPByHost(String hostName) {
        if (!dnsSwitch) {
            LogCatUtil.info(TAG, "queryLocalIPByHost. dnsSwitch is false.");
            return null;
        }
        try {
            HttpdnsIP httpdnsIP = this.httpdnsStorage.getIpInfoByHttpdns(hostName);
            if (httpdnsIP == null) {
                LogCatUtil.debug(TAG, "queryLocalIPByHost hostName=" + hostName + " , ipInfo=[null]");
                return null;
            } else if (httpdnsIP.isDie()) {
                LogCatUtil.info(TAG, "queryLocalIPByHost ,httpdns die, request again");
                alipayHttpDnsClient.dnsClient.requestStrong();
                return null;
            } else {
                LogCatUtil.debug(TAG, "queryLocalIPByHost hostName=" + hostName + " , ipInfo=" + httpdnsIP.toString());
                if (!httpdnsIP.isTimeOut()) {
                    return httpdnsIP;
                }
                LogCatUtil.info(TAG, "queryLocalIPByHost ,httpdns cache expired, request again...");
                alipayHttpDnsClient.dnsClient.requestWeak(ThreadType.HTTPDNSREQUEST_TIMEOUT);
                return httpdnsIP;
            }
        } catch (Throwable ex) {
            LogCatUtil.warn((String) TAG, "queryLocalIPByHost ex:" + ex.toString());
            return null;
        }
    }

    public void storeIp2DB(Map<String, HttpdnsIP> result) {
        if (result != null && result.size() != 0) {
            try {
                this.httpdnsStorage.storeIp2DB(result, getNetworkType());
                LogCatUtil.info(TAG, "storeIp2DB complete...");
                c();
            } catch (Exception ex) {
                LogCatUtil.error(TAG, "storeIp2DB exception ", ex);
            }
        }
    }

    public void storeIp2CacheAndDB(Map<String, HttpdnsIP> result) {
        if (result != null && result.size() != 0) {
            try {
                this.httpdnsStorage.storeIp2Cache(result);
                storeIp2DB(result);
            } catch (Exception e2) {
                LogCatUtil.debug(TAG, "storeIp2CacheAndDB exception");
            }
        }
    }

    public void updateIp2CacheAndDB(Map<String, HttpdnsIP> result) {
        if (result != null && !result.isEmpty()) {
            this.httpdnsStorage.updateIp2CacheAndDB(result);
        }
    }

    @Deprecated
    public void updateIp2CacheAndFile(Map<String, HttpdnsIP> result) {
        if (result != null && !result.isEmpty()) {
            this.httpdnsStorage.updateIp2CacheAndDB(result);
        }
    }

    private void c() {
        if (getHttpDnsUpdateObservable().countObservers() > 0) {
            NetworkAsyncTaskExecutor.executeLowPri(new Runnable() {
                public void run() {
                    AlipayHttpDnsClient.this.getHttpDnsUpdateObservable().notifyObservers();
                }
            });
        }
    }

    public void reloadDns() {
        try {
            Map ipMap = this.httpdnsStorage.getAllIpFromDB(getNetworkType());
            if (ipMap != null && !ipMap.isEmpty()) {
                this.httpdnsStorage.storeIp2Cache(ipMap);
                LogCatUtil.info(TAG, "reloadDns success...");
            }
        } catch (Exception ex) {
            LogCatUtil.warn((String) TAG, "reloadDns exception " + ex.toString());
        }
    }

    public void updateDnsTtl(int ttlUnit) {
        try {
            long time = System.currentTimeMillis();
            if (this.httpdnsStorage.getCache().isEmpty()) {
                reloadDns();
            }
            if (this.httpdnsStorage.getCache().size() > 0) {
                for (Entry<String, HttpdnsIP> value : this.httpdnsStorage.getCache().entrySet()) {
                    HttpdnsIP httpdnsIP = (HttpdnsIP) value.getValue();
                    httpdnsIP.setTime(time);
                    httpdnsIP.setTtl(((long) (ttlUnit * 1000)) + time);
                }
                storeIp2DB(this.httpdnsStorage.getCache());
                LogCatUtil.info(TAG, "updateDnsTtl complete...");
                return;
            }
            LogCatUtil.info(TAG, "updateDnsTtl failure...");
        } catch (Exception ex) {
            LogCatUtil.error(TAG, "updateDnsTtl exception", ex);
        }
    }

    public void deleteIpByHost(String host) {
        this.httpdnsStorage.deleteIpByHost(host);
    }

    public void saveLastUpdateTime() {
        SharedPreUtils.putData(this.mContext, (String) "http_dns_last_time", System.currentTimeMillis());
    }

    public long getLastUpdateTime() {
        return SharedPreUtils.getLonggData(this.mContext, "http_dns_last_time");
    }

    public void addHttpDnsUpdateObserver(Observer observer) {
        LogCatUtil.printInfo(TAG, "addHttpDnsUpdateObserver:" + observer.getClass().getName());
        getHttpDnsUpdateObservable().addObserver(observer);
    }

    public HttpDnsUpdateObservable getHttpDnsUpdateObservable() {
        if (this.b == null) {
            this.b = new HttpDnsUpdateObservable();
        }
        return this.b;
    }

    private static void d() {
        TransportConfigureManager.getInstance().addConfigureChangedListener(new ConfigureChangedListener() {
            public final void update(Observable observable, Object data) {
                try {
                    LogCatUtil.printInfo(AlipayHttpDnsClient.TAG, "AlipayHttpDnsClient update config");
                    TransportConfigureManager configMgr = TransportConfigureManager.getInstance();
                    if (configMgr.equalsString(TransportConfigureItem.DNS_SWITCH, "T")) {
                        AlipayHttpDnsClient.dnsSwitch = true;
                    } else {
                        AlipayHttpDnsClient.dnsSwitch = false;
                    }
                    if (AlipayHttpDnsClient.dnsSwitch && MiscUtils.isDebugger(TransportEnvUtil.getContext()) && !configMgr.equalsString(TransportConfigureItem.DNS_SWITCH_DEBUG, "T")) {
                        AlipayHttpDnsClient.dnsSwitch = false;
                        LogCatUtil.printInfo(AlipayHttpDnsClient.TAG, "AlipayHttpDnsClient init, In the closed HTTPDNS development environment");
                    }
                    LogCatUtil.printInfo(AlipayHttpDnsClient.TAG, "AlipayHttpDnsClient update config, dnsSwitch=" + AlipayHttpDnsClient.dnsSwitch);
                } catch (Throwable e) {
                    LogCatUtil.error((String) AlipayHttpDnsClient.TAG, e);
                }
            }
        });
    }

    public int getNetworkType() {
        if (this.mContext != null) {
            return NetworkUtils.getNetworkType(this.mContext);
        }
        LogCatUtil.debug(TAG, "getNetworkType mContext is null");
        return -1;
    }

    public void putSingleHttpdnsIp(String host, HttpdnsIP httpdnsIP) {
        try {
            this.httpdnsStorage.putSingleIp2CacheAndDB(host, httpdnsIP);
        } catch (Exception e2) {
            LogCatUtil.warn((String) TAG, (String) "putSingleHttpdnsIp exception");
        }
    }

    public boolean isHttpdnsDie() {
        try {
            HttpdnsIP httpdnsIP = this.httpdnsStorage.getIpInfoByHttpdns(this.e);
            if (httpdnsIP != null && httpdnsIP.isDie()) {
                LogCatUtil.info(TAG, "httpdns die, request again");
                alipayHttpDnsClient.dnsClient.requestStrong();
                return true;
            }
        } catch (Throwable ex) {
            LogCatUtil.warn((String) TAG, "isHttpdnsDie ex:" + ex.toString());
        }
        return false;
    }
}
