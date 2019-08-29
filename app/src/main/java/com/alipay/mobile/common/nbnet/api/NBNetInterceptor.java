package com.alipay.mobile.common.nbnet.api;

public interface NBNetInterceptor {
    void intercept(byte b, String str);

    void reportException(byte b, String str, Exception exc);

    void reportReceivedTraffic(byte b, String str, long j);

    void reportTransmittedTraffic(byte b, String str, long j);
}
