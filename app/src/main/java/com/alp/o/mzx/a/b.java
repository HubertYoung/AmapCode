package com.alp.o.mzx.a;

import com.alipay.android.phone.wallet.minizxing.m;

public enum b {
    NUMERIC(0),
    ALPHANUMERIC(1),
    BYTE(2),
    KANJI(3);
    
    private final int e;

    private b(int id) {
        this.e = id;
    }

    public final int a() {
        return this.e;
    }

    public final m b() {
        switch (this.e) {
            case 0:
                return m.NUMERIC;
            case 1:
                return m.ALPHANUMERIC;
            case 2:
                return m.BYTE;
            case 3:
                return m.KANJI;
            default:
                return m.BYTE;
        }
    }

    public static b a(m mode) {
        if (mode == m.NUMERIC) {
            return NUMERIC;
        }
        if (mode == m.ALPHANUMERIC) {
            return ALPHANUMERIC;
        }
        if (mode == m.BYTE) {
            return BYTE;
        }
        if (mode == m.KANJI) {
            return KANJI;
        }
        return BYTE;
    }
}
