package com.alipay.mobile.common.transportext.biz.spdy.internal;

import java.net.InetAddress;

public interface Dns {
    public static final Dns DEFAULT = new Dns() {
        public final InetAddress[] getAllByName(String host) {
            return InetAddress.getAllByName(host);
        }
    };

    InetAddress[] getAllByName(String str);
}
