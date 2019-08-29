package com.alipay.mobile.common.nbnet.biz.util;

import java.nio.ByteBuffer;
import java.util.UUID;

public class ByteUtil {
    public static byte[] a() {
        return ByteBuffer.allocate(4).putInt(35).array();
    }

    public static byte[] a(long value) {
        return ByteBuffer.allocate(8).putLong(value).array();
    }

    public static byte[] a(UUID value) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(value.getMostSignificantBits());
        bb.putLong(value.getLeastSignificantBits());
        return bb.array();
    }
}
