package com.alipay.mobile.common.transport.httpdns.downloader;

public enum ConfigSelector {
    GET_ALL(0),
    GET_IPLIST_ONLY(1),
    GET_CONF_ONLY(2);
    
    private final int a;

    private ConfigSelector(int value) {
        this.a = value;
    }
}
