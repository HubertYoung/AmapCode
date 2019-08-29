package com.alipay.mobile.common.transport.http.inner;

import org.apache.http.HttpHost;

public class HttpProxyWrapper {
    public byte lastGoodProxy = -1;
    public HttpHost proxy = null;
}
