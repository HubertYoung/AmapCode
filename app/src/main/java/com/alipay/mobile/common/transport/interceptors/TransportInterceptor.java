package com.alipay.mobile.common.transport.interceptors;

import java.util.Map;

public interface TransportInterceptor {
    void preRequestInterceptor(String str, Map<String, String> map);
}
