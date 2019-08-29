package com.alipay.apmobilesecuritysdk.type;

public class DevTypeString extends DevType<String> {
    public DevTypeString(String str) {
        super(str, 6);
    }

    public final byte[] a() {
        return ((String) this.a).getBytes();
    }
}
