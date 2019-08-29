package com.alipay.apmobilesecuritysdk.type;

public class DevTypeBoolean extends DevType<Boolean> {
    public DevTypeBoolean(Boolean bool) {
        super(bool, 1);
    }

    public final byte[] a() {
        byte[] bArr = new byte[1];
        if (((Boolean) this.a).booleanValue()) {
            bArr[0] = 1;
        } else {
            bArr[0] = 0;
        }
        return bArr;
    }
}
