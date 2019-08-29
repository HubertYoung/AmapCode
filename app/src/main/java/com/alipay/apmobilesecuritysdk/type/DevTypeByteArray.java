package com.alipay.apmobilesecuritysdk.type;

public class DevTypeByteArray extends DevType<byte[]> {
    public DevTypeByteArray(byte[] bArr) {
        super(bArr, 2);
    }

    public final byte[] a() {
        return (byte[]) this.a;
    }
}
