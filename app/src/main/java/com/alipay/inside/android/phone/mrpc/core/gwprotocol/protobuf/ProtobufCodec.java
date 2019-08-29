package com.alipay.inside.android.phone.mrpc.core.gwprotocol.protobuf;

import java.lang.reflect.Type;

public interface ProtobufCodec {
    Object deserialize(Type type, byte[] bArr);

    boolean isPBBean(Class cls);

    boolean isPBBean(Object obj);

    byte[] serialize(Object obj);

    String toString(Object obj);
}
