package com.alipay.mobile.common.transport.httpdns;

import com.alipay.mobile.common.transport.httpdns.HttpDns.HttpdnsIP;

public interface DnsService {
    void feedback(String str, String str2, boolean z, int i);

    HttpdnsIP getIpInfoByHost(String str);

    void refreshAll();

    void refreshIPListOnly();

    void setErrorByHost(String str);
}
