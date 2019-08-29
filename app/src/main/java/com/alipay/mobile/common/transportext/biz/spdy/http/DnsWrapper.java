package com.alipay.mobile.common.transportext.biz.spdy.http;

import com.alipay.mobile.common.transport.httpdns.AlipayHttpDnsClient;
import com.alipay.mobile.common.transport.httpdns.HttpDns;
import com.alipay.mobile.common.transportext.biz.spdy.OkHttpClient;
import com.alipay.mobile.common.transportext.biz.spdy.internal.Dns;
import java.net.InetAddress;

public class DnsWrapper implements Dns {
    private OkHttpClient mClient;

    public DnsWrapper(OkHttpClient client) {
        this.mClient = client;
    }

    public InetAddress[] getAllByName(String host) {
        return getInetAddresses(host);
    }

    private InetAddress[] getInetAddresses(String host) {
        AlipayHttpDnsClient dnsClient = AlipayHttpDnsClient.getDnsClient();
        if (dnsClient != null) {
            return dnsClient.getAllByName(host);
        }
        return HttpDns.getInstance().getGetAllByNameHelper().getAllByName(host);
    }
}
