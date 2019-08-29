package com.alipay.ma.common.b;

/* compiled from: MaTBAntiFakeResult */
public final class d extends c {
    private String a;

    private d(e type, String text) {
        super(type, text);
    }

    public d(e type, String text, String hiddenData) {
        this(type, text);
        this.a = hiddenData;
    }
}
