package com.alipay.mobile.common.transport.httpdns;

import com.alipay.mobile.common.transport.httpdns.HttpDns.HttpdnsIP;
import java.util.Map;

public interface DnsLocalManager {
    void deleteIpByHost(String str);

    long getLastUpdateTime();

    HttpdnsIP queryLocalIPByHost(String str);

    void reloadDns();

    void saveLastUpdateTime();

    void storeIp2CacheAndDB(Map<String, HttpdnsIP> map);

    void storeIp2DB(Map<String, HttpdnsIP> map);

    void updateIp2CacheAndDB(Map<String, HttpdnsIP> map);
}
