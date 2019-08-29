package com.alipay.android.phone.wallet.minizxing;

public final class o {
    private m a;
    private ErrorCorrectionLevel b;
    private t c;
    private int d = -1;
    private d e;

    public final d a() {
        return this.e;
    }

    public final String toString() {
        StringBuilder result = new StringBuilder(200);
        result.append("<<\n");
        result.append(" mode: ");
        result.append(this.a);
        result.append("\n ecLevel: ");
        result.append(this.b);
        result.append("\n version: ");
        result.append(this.c);
        result.append("\n maskPattern: ");
        result.append(this.d);
        if (this.e == null) {
            result.append("\n matrix: null\n");
        } else {
            result.append("\n matrix:\n");
            result.append(this.e);
        }
        result.append(">>\n");
        return result.toString();
    }

    public final void a(m value) {
        this.a = value;
    }

    public final void a(ErrorCorrectionLevel value) {
        this.b = value;
    }

    public final void a(t version) {
        this.c = version;
    }

    public final void a(int value) {
        this.d = value;
    }

    public final void a(d value) {
        this.e = value;
    }

    public static boolean b(int maskPattern) {
        return maskPattern >= 0 && maskPattern < 8;
    }
}
