package com.alipay.mobile.common.rpc.protocol;

public interface Serializer {
    byte[] packet();

    void setExtParam(Object obj);
}
