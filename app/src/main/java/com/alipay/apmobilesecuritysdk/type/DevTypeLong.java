package com.alipay.apmobilesecuritysdk.type;

public class DevTypeLong extends DevType<Long> {
    public DevTypeLong(Long l) {
        super(l, 5);
    }

    public final byte[] a() {
        long longValue = ((Long) this.a).longValue();
        return new byte[]{(byte) ((int) ((longValue >> 0) & 255)), (byte) ((int) ((longValue >> 8) & 255)), (byte) ((int) ((longValue >> 16) & 255)), (byte) ((int) ((longValue >> 24) & 255)), (byte) ((int) ((longValue >> 32) & 255)), (byte) ((int) ((longValue >> 40) & 255)), (byte) ((int) ((longValue >> 48) & 255)), (byte) ((int) ((longValue >> 56) & 255))};
    }
}
