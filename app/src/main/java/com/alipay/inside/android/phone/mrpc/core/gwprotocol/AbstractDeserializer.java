package com.alipay.inside.android.phone.mrpc.core.gwprotocol;

import java.lang.reflect.Type;

public abstract class AbstractDeserializer implements Deserializer {
    protected byte[] mData;
    protected Type mType;

    public AbstractDeserializer(Type type, byte[] bArr) {
        this.mType = type;
        this.mData = bArr;
    }
}
