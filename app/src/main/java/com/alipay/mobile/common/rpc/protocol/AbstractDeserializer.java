package com.alipay.mobile.common.rpc.protocol;

import java.lang.reflect.Type;

public abstract class AbstractDeserializer implements Deserializer {
    protected byte[] mData;
    protected Type mType;

    public AbstractDeserializer(Type type, byte[] data) {
        this.mType = type;
        this.mData = data;
    }
}
