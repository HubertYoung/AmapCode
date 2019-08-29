package com.alipay.ma.common.b;

/* compiled from: Ma4GResult */
public final class a extends c {
    private String a;

    private a(e type, String text) {
        super(type, text);
    }

    public a(e type, String text, String signature) {
        this(type, text);
        this.a = signature;
    }
}
