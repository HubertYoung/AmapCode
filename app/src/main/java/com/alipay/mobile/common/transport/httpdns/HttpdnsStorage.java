package com.alipay.mobile.common.transport.httpdns;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.mobile.common.transport.httpdns.HttpDns.HttpdnsIP;
import com.alipay.mobile.common.transport.httpdns.db.HttpdnsDBService;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class HttpdnsStorage {
    public static final String TAG = "HTTP_DNS_Storage";
    private static HttpdnsStorage d = null;
    private Map<String, HttpdnsIP> a = null;
    private HttpdnsDBService b = null;
    private Context c;

    public static HttpdnsStorage getInstance(Context context) {
        if (d != null) {
            return d;
        }
        synchronized (HttpdnsStorage.class) {
            try {
                if (d == null) {
                    d = new HttpdnsStorage(context);
                }
            }
        }
        return d;
    }

    private HttpdnsStorage(Context context) {
        this.c = context;
        this.a = new HashMap();
        this.b = new HttpdnsDBService(this.c);
    }

    public Map<String, HttpdnsIP> getCache() {
        return this.a;
    }

    public HttpdnsIP getIpInfoByHttpdns(String host) {
        int netType = AlipayHttpDnsClient.getDnsClient().getNetworkType();
        if (netType == -1 || netType == 0) {
            netType = 3;
        }
        HttpdnsIP httpdnsIP = getIpFromCache(host, netType);
        if (httpdnsIP != null) {
            return httpdnsIP;
        }
        HttpdnsIP httpdnsIP2 = getIpFromDB(host, netType);
        if (httpdnsIP2 != null) {
            this.a.put(host, httpdnsIP2);
        }
        return httpdnsIP2;
    }

    public HttpdnsIP getIpFromCache(String domain, int netType) {
        if (TextUtils.isEmpty(domain)) {
            return null;
        }
        HttpdnsIP httpdnsIP = this.a.get(domain);
        if (httpdnsIP == null) {
            LogCatUtil.debug(TAG, "getIpFromCache netType: " + netType + ",no ipinfo in cache,domain:" + domain);
            return null;
        } else if (httpdnsIP.getNetType() == netType) {
            LogCatUtil.debug(TAG, "getIpFromCache netType: " + netType + ",hit it, domain: " + domain);
            return httpdnsIP;
        } else {
            LogCatUtil.debug(TAG, "getIpFromCache ,netType is different,will get from DB, domain : " + domain);
            return null;
        }
    }

    public HttpdnsIP getIpFromDB(String domain, int netType) {
        if (TextUtils.isEmpty(domain)) {
            return null;
        }
        return this.b.queryIpInfoFromDB(domain, netType);
    }

    public Map<String, HttpdnsIP> getAllIpInfo() {
        int netType = AlipayHttpDnsClient.getDnsClient().getNetworkType();
        Map result = a(netType);
        return result != null ? result : getAllIpFromDB(netType);
    }

    private Map<String, HttpdnsIP> a(int netType) {
        HttpdnsIP httpdnsIP = null;
        try {
            if (this.a.size() <= 0) {
                return null;
            }
            Iterator<Entry<String, HttpdnsIP>> it = this.a.entrySet().iterator();
            if (it.hasNext()) {
                httpdnsIP = (HttpdnsIP) it.next().getValue();
            }
            if (httpdnsIP.getNetType() == netType) {
                return this.a;
            }
            return null;
        } catch (Exception ex) {
            LogCatUtil.warn((String) TAG, "getAllIpFromCache exception" + ex.toString());
            return null;
        }
    }

    public Map<String, HttpdnsIP> getAllIpFromDB(int netType) {
        return this.b.getAllIPFromDB(netType);
    }

    public void deleteIpByHost(String host) {
        int netType = AlipayHttpDnsClient.getDnsClient().getNetworkType();
        a(host);
        a(host, netType);
    }

    public void deleteSingleIpFromDB(String host, String ip) {
        int netType = AlipayHttpDnsClient.getDnsClient().getNetworkType();
        a(host);
        a(host, ip, netType);
    }

    private void a(String host) {
        if (!TextUtils.isEmpty(host) && this.a.containsKey(host)) {
            this.a.remove(host);
        }
    }

    private void a(String host, int netType) {
        if (!TextUtils.isEmpty(host)) {
            this.b.removeIpInfoFromDB(host, netType);
        }
    }

    private void a(String host, String ip, int netType) {
        if (!TextUtils.isEmpty(host)) {
            this.b.removeSingleIpInfoFromDB(host, ip, netType);
        }
    }

    public void updateIp2CacheAndDB(Map<String, HttpdnsIP> parseResult) {
        int netType = AlipayHttpDnsClient.getDnsClient().getNetworkType();
        a(parseResult, netType);
        a(parseResult);
        b(parseResult, netType);
    }

    private static void a(Map<String, HttpdnsIP> parseResult, int netType) {
        if (parseResult != null && !parseResult.isEmpty()) {
            for (Entry entry : parseResult.entrySet()) {
                if (entry.getValue() != null) {
                    ((HttpdnsIP) entry.getValue()).setNetType(netType);
                }
            }
        }
    }

    private void a(Map<String, HttpdnsIP> parseResult) {
        if (parseResult != null && parseResult.size() != 0) {
            try {
                this.a.putAll(parseResult);
                for (Entry entry : parseResult.entrySet()) {
                    if (!HttpDns.getInstance().getHosts().contains(entry.getKey())) {
                        HttpDns.getInstance().getHosts().add(entry.getKey());
                    }
                }
            } catch (Exception ex) {
                LogCatUtil.error(TAG, "updateIPInfo2Cache Exception", ex);
            }
        }
    }

    private void b(Map<String, HttpdnsIP> parseResult, int netType) {
        if (parseResult != null && parseResult.size() != 0) {
            this.b.updateIp2DB(parseResult, netType);
        }
    }

    public void storeIp2CacheAndDB(Map<String, HttpdnsIP> parseResult) {
        int netType = AlipayHttpDnsClient.getDnsClient().getNetworkType();
        a(parseResult, netType);
        storeIp2Cache(parseResult);
        storeIp2DB(parseResult, netType);
    }

    public void storeIp2Cache(Map<String, HttpdnsIP> parseResult) {
        if (parseResult == null || parseResult.isEmpty()) {
            LogCatUtil.debug(TAG, "putIpInfo2Cache param is null");
            return;
        }
        this.a.clear();
        this.a.putAll(parseResult);
    }

    public void storeIp2DB(Map<String, HttpdnsIP> parseResult, int netType) {
        if (parseResult == null || parseResult.isEmpty()) {
            LogCatUtil.debug(TAG, "putIpInfo2DB param is null");
        } else {
            this.b.storeIp2DB(parseResult, netType);
        }
    }

    public void putSingleIp2CacheAndDB(String host, HttpdnsIP httpdnsIP) {
        int netType = AlipayHttpDnsClient.getDnsClient().getNetworkType();
        httpdnsIP.setNetType(netType);
        a(host, httpdnsIP);
        a(host, httpdnsIP, netType);
    }

    private void a(String host, HttpdnsIP httpdnsIP) {
        if (TextUtils.isEmpty(host) || httpdnsIP == null) {
            LogCatUtil.debug(TAG, "putSingleIp2Cache param is null");
        } else {
            this.a.put(host, httpdnsIP);
        }
    }

    private void a(String host, HttpdnsIP httpdnsIP, int netType) {
        if (TextUtils.isEmpty(host) || httpdnsIP == null) {
            LogCatUtil.debug(TAG, "putSingleIp2DB param is null");
        } else {
            this.b.insertIpinfo2DB(host, httpdnsIP, netType);
        }
    }
}
