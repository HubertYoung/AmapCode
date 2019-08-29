package com.alp.o.mzx.a;

public final class a {
    int a;
    int b;

    a(int b2, int r) {
        this.a = b2;
        this.b = r;
    }

    public final String toString() {
        return String.format("b:%d R:%d", new Object[]{Integer.valueOf(this.a), Integer.valueOf(this.b)});
    }
}
