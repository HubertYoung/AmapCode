package com.alipay.inside.android.phone.mrpc.core.gwprotocol.util;

public class RPCProtoDesc {
    public static final byte PROTO_TYPE_JSON_V1 = 0;
    public static final byte PROTO_TYPE_JSON_V2 = 2;
    public static final byte PROTO_TYPE_PB_V1 = 3;
    public static final byte PROTO_TYPE_SIMPLE_JSON_V1 = 1;
    public static final byte PROTO_TYPE_SIMPLE_PB_V1 = 4;
    public byte protoType;

    public boolean isJsonV1() {
        return this.protoType == 0;
    }

    public boolean isJsonV2() {
        return this.protoType == 2;
    }

    public boolean isPBV1() {
        return this.protoType == 3;
    }

    public boolean isSimpleJsonV1() {
        return this.protoType == 1;
    }

    public boolean isSimplePBV1() {
        return this.protoType == 4;
    }
}
