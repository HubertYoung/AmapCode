package com.alipay.apmobilesecuritysdk.type;

public class DevTypeInt extends DevType<Integer> {
    public DevTypeInt(Integer num) {
        super(num, 4);
    }

    public final byte[] a() {
        return new byte[]{(byte) ((((Integer) this.a).intValue() >> 0) & 255), (byte) ((((Integer) this.a).intValue() >> 8) & 255), (byte) ((((Integer) this.a).intValue() >> 16) & 255), (byte) ((((Integer) this.a).intValue() >> 24) & 255)};
    }
}
