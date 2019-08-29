package com.alipay.ma.common.b;

/* compiled from: MaResult */
public class c {
    private final e a;
    private final String b;
    public char g;
    public int h;
    public int i;
    public int j;

    public c(e type, String text) {
        this.a = type;
        this.b = text;
    }

    public c(e type, String text, char ecLevel, int bitErrors, int version, int strategy) {
        this.a = type;
        this.b = text;
        this.g = ecLevel;
        this.h = bitErrors;
        this.i = version;
        this.j = strategy;
    }

    public final e a() {
        return this.a;
    }

    public final String b() {
        return this.b;
    }

    public String toString() {
        return "MaResult [type=" + this.a + ", text=" + this.b + "]";
    }
}
