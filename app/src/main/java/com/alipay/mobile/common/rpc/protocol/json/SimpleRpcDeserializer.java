package com.alipay.mobile.common.rpc.protocol.json;

import java.lang.reflect.Type;

public class SimpleRpcDeserializer extends JsonDeserializer {
    public SimpleRpcDeserializer(Type type, byte[] data) {
        super(type, data);
    }
}
