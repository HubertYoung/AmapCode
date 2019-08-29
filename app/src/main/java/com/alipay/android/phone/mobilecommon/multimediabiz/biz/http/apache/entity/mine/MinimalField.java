package com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.apache.entity.mine;

public class MinimalField {
    private final String a;
    private final String b;

    public MinimalField(String name, String value) {
        this.a = name;
        this.b = value;
    }

    public String getName() {
        return this.a;
    }

    public String getBody() {
        return this.b;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(this.a);
        buffer.append(": ");
        buffer.append(this.b);
        return buffer.toString();
    }
}
